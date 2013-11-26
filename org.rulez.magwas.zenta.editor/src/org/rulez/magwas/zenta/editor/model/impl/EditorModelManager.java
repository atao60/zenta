/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.model.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.rulez.magwas.zenta.editor.ZentaEditorPlugin;
import org.rulez.magwas.zenta.editor.Logger;
import org.rulez.magwas.zenta.editor.diagram.util.AnimationUtil;
import org.rulez.magwas.zenta.editor.model.IZentaveManager;
import org.rulez.magwas.zenta.editor.model.IEditorModelManager;
import org.rulez.magwas.zenta.editor.model.compatibility.CompatibilityHandlerException;
import org.rulez.magwas.zenta.editor.model.compatibility.IncompatibleModelException;
import org.rulez.magwas.zenta.editor.model.compatibility.LaterModelVersionException;
import org.rulez.magwas.zenta.editor.model.compatibility.ModelCompatibility;
import org.rulez.magwas.zenta.editor.preferences.IPreferenceConstants;
import org.rulez.magwas.zenta.editor.preferences.Preferences;
import org.rulez.magwas.zenta.editor.ui.services.EditorManager;
import org.rulez.magwas.zenta.editor.utils.FileUtils;
import org.rulez.magwas.zenta.metamodel.MetamodelFactory;
import org.rulez.magwas.zenta.model.FolderType;
import org.rulez.magwas.zenta.model.IZentaFactory;
import org.rulez.magwas.zenta.model.IZentaModel;
import org.rulez.magwas.zenta.model.IDiagramModel;
import org.rulez.magwas.zenta.model.ModelVersion;
import org.rulez.magwas.zenta.model.util.ZentaResourceFactory;

import uk.ac.bolton.jdom.JDOMUtils;


/**
 * Editor Model Manager.<p>
 * <p>
 * Acts as an adapter to the Zenta Models passing on notifications to listeners
 * so that clients only have to register here once rather than for each model.<p>
 * Also can pass on arbitrary PropertyChangeEvents to registered listeners.<br>
 * Also manages CommandStacks for models.<br>
 * Also handles persistence of models.
 * 
 * @author Phillip Beauvoir
 */
public class EditorModelManager
implements IEditorModelManager {
    
    /**
     * Listener list
     */
    private PropertyChangeSupport fListeners = new PropertyChangeSupport(this);
    
    /**
     * Models Open
     */
    private List<IZentaModel> fModels;
    
    /**
     * Backing File
     */
    private File backingFile = new File(ZentaEditorPlugin.INSTANCE.getUserDataFolder(), "models.xml"); //$NON-NLS-1$
    
    /**
     * Listen to the App closing so we can ask to save
     */
    private IWorkbenchListener workBenchListener = new IWorkbenchListener() {
        public void postShutdown(IWorkbench workbench) {
        }

        public boolean preShutdown(IWorkbench  workbench, boolean forced) {
            // Handle modified models
            for(IZentaModel model : getModels()) {
                if(isModelDirty(model)) {
                    try {
                        boolean result = askSaveModel(model);
                        if(!result) {
                            return false;
                        }
                    }
                    catch(IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
            return true;
        }
    };
    
    public EditorModelManager() {
        PlatformUI.getWorkbench().addWorkbenchListener(workBenchListener);
    }
    
    @Override
    public List<IZentaModel> getModels() {
        if(fModels == null) {
            fModels = new ArrayList<IZentaModel>();
            
            try {
                loadState();
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        
        return fModels;
    }

    @Override
    public IZentaModel createNewModel() {
        IZentaModel model = IZentaFactory.eINSTANCE.createZentaModel();
        model.setName(Messages.EditorModelManager_0);
        model.setDefaults();
        
        // Add one default diagram
        IDiagramModel diagramModel = IZentaFactory.eINSTANCE.createZentaDiagramModel();
        diagramModel.setName(Messages.EditorModelManager_1);
        model.getFolder(FolderType.DIAGRAMS).getElements().add(diagramModel);
        
        // Register
        registerModel(model);
        
        return model;
    }
    
    @Override
    public void registerModel(IZentaModel model) {
        // Add to Models
        getModels().add(model);
        
        // New Command Stack
        createNewCommandStack(model);
        
        // New Zentave Manager
        createNewZentaveManager(model);
        
        firePropertyChange(this, PROPERTY_MODEL_CREATED, null, model);
        model.eAdapters().add(new ECoreAdapter());
    }
    
    @Override
    public IZentaModel openModel(File file) {
        if(file == null || !file.exists() || isModelLoaded(file)) {
            return null;
        }
        
        IZentaModel model = loadModel(file);
        if(model != null) {
            // Open Views of newly opened model if set in Preferences
            if(Preferences.doOpenDiagramsOnLoad()) {
                for(IDiagramModel dm : model.getDiagramModels()) {
                    EditorManager.openDiagramEditor(dm);
                }
            }
            
            firePropertyChange(this, PROPERTY_MODEL_OPENED, null, model);
        }
        
        return model;
    }
    
    @Override
    public void openModel(IZentaModel model) {
        // Add to Models
        getModels().add(model);
        
        // New Command Stack
        createNewCommandStack(model);
        
        // New Zentave Manager
        createNewZentaveManager(model);
        
        model.eAdapters().add(new ECoreAdapter());

        firePropertyChange(this, PROPERTY_MODEL_OPENED, null, model);
    }
    
    @Override
    public IZentaModel loadModel(File file) {
    	//FIXME move to ...zenta.model package
        if(file == null || !file.exists()) {
            return null;
        }
        
        // Ascertain if this is an archive file
        boolean useZentaveFormat = IZentaveManager.FACTORY.isZentaveFile(file);
        
        // Create the Resource
        ResourceSet resourceSet = ZentaResourceFactory.createResourceSet();
        Resource resource = resourceSet.createResource(useZentaveFormat ?
                                                       IZentaveManager.FACTORY.createZentaveModelURI(file) :
                                                       URI.createFileURI(file.getAbsolutePath()));

        // Load the model file
        try {
            resource.load(null);
        }
        catch(IOException ex) {
            // Error occured loading model. Was it a disaster?
            try {
                ModelCompatibility.checkErrors(resource);
            }
            // Incompatible, don't load it
            catch(IncompatibleModelException ex1) {
                MessageDialog.openError(Display.getCurrent().getActiveShell(),
                        Messages.EditorModelManager_2,
                        NLS.bind(Messages.EditorModelManager_3, file)
                        + "\n" + ex1.getMessage()); //$NON-NLS-1$
                return null;
            }
        }
        
        // Once loaded - Check version number compatibility with user
        try {
            ModelCompatibility.checkVersion(resource);
        }
        catch(LaterModelVersionException ex) {
            boolean answer = MessageDialog.openQuestion(Display.getCurrent().getActiveShell(),
                    Messages.EditorModelManager_4,
                    NLS.bind(Messages.EditorModelManager_5,
                            file, ex.getVersion()));
            if(!answer) {
                return null;
            }
        }
        
        // And then fix any backward compatibility issues
        try {
            ModelCompatibility.fixCompatibility(resource);
        }
        catch(CompatibilityHandlerException ex) {
        }


        IZentaModel model = (IZentaModel)resource.getContents().get(0);
        MetamodelFactory.eINSTANCE.createMetamodel(model);
        model.setFile(file);
        model.setDefaults();
        getModels().add(model);
        model.eAdapters().add(new ECoreAdapter());

        // New Command Stack
        createNewCommandStack(model);
        
        // New Zentave Manager
        createNewZentaveManager(model);
        
        // Initiate all diagram models to be marked as "saved" - this is for the editor view persistence
        markDiagramModelsAsSaved(model);

        // This last
        firePropertyChange(this, PROPERTY_MODEL_LOADED, null, model);

        return model;
    }
    
    @Override
    public boolean closeModel(IZentaModel model) throws IOException {
        // Check if model needs saving
        if(isModelDirty(model)) {
            boolean result = askSaveModel(model);
            if(!result) {
                return false;
            }
        }
        
        // Close the corresponding GEF editor(s) for this model *FIRST* before removing from model
        EditorManager.closeDiagramEditors(model);
        
        getModels().remove(model);
        model.eAdapters().clear();
        firePropertyChange(this, PROPERTY_MODEL_REMOVED, null, model);
        
        // Delete the CommandStack *LAST* because GEF Editor(s) will still reference it!
        deleteCommandStack(model);
        
        // Delete Zentave Manager
        deleteZentaveManager(model);

        return true;
    }
    
    /**
     * Show dialog to save modified model
     * @param model
     * @return true if the user chose to save the model or chose not to save the model, false if cancelled
     * @throws IOException 
     */
    private boolean askSaveModel(IZentaModel model) throws IOException {
        MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(),
                Messages.EditorModelManager_6,
                null,
                NLS.bind(Messages.EditorModelManager_7, model.getName()),
                MessageDialog.QUESTION,
                new String[] {
                    IDialogConstants.YES_LABEL,
                    IDialogConstants.NO_LABEL,
                    IDialogConstants.CANCEL_LABEL },
                0);
        
        
        int result = dialog.open();
        
        // Yes
        if(result == 0) {
            return saveModel(model);
        }
        // No
        if(result == 1) {
            return true;
        }
        // Cancel
        return false;
    }

    @Override
    public boolean saveModel(IZentaModel model) throws IOException {
        // First time to save...
        if(model.getFile() == null) {
            File file = askSaveModel();
            if(file == null) { // cancelled
                return false;
            }
            model.setFile(file);
        }
        
        File file = model.getFile();
        
        // Save backup (if set in Preferences)
        if(Preferences.STORE.getBoolean(IPreferenceConstants.BACKUP_ON_SAVE) && file.exists()) {
            FileUtils.copyFile(file, new File(model.getFile().getAbsolutePath() + ".bak"), false); //$NON-NLS-1$
        }
        
        // Set model version
        model.setVersion(ModelVersion.VERSION);
        
        // Use Zentave Manager to save contents
        IZentaveManager archiveManager = (IZentaveManager)model.getAdapter(IZentaveManager.class);
        archiveManager.saveModel();
        
        // Set CommandStack Save point
        CommandStack stack = (CommandStack)model.getAdapter(CommandStack.class);
        stack.markSaveLocation();
        // Send notification to Tree
        firePropertyChange(model, COMMAND_STACK_CHANGED, true, false);
        
        // Set all diagram models to be marked as "saved" - this is for the editor view persistence
        markDiagramModelsAsSaved(model);
        
        firePropertyChange(this, PROPERTY_MODEL_SAVED, null, model);
        
        return true;
    }
    
    @Override
    public boolean saveModelAs(IZentaModel model) throws IOException {
        File file = askSaveModel();
        if(file == null) {
            return false;
        }
        
        // Set new file
        model.setFile(file);
        
        // And save it to new file
        return saveModel(model);
    }
    
    @Override
    public boolean isModelLoaded(File file) {
        if(file != null) {
            for(IZentaModel model : getModels()) {
                if(file.equals(model.getFile())) {
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public boolean isModelDirty(IZentaModel model) {
        if(model == null) {
            return false;
        }
        
        CommandStack stack = (CommandStack)model.getAdapter(CommandStack.class);
        return stack != null && stack.isDirty();
    }

    /**
     * Ask user for file name to save model
     * @return the file or null
     */
    private File askSaveModel() {
        // On Mac if the app is minimised in the dock Display.getCurrent().getActiveShell() will return null
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        shell.setActive(); // Get focus on Mac
        
        FileDialog dialog = new FileDialog(shell, SWT.SAVE);
        dialog.setFilterExtensions(new String[] { ZENTA_FILE_WILDCARD, "*.*" } ); //$NON-NLS-1$
        String path = dialog.open();
        if(path == null) {
            return null;
        }
        
        // Only Windows adds the extension by default
        if(dialog.getFilterIndex() == 0 && !path.endsWith(ZENTA_FILE_EXTENSION)) {
            path += ZENTA_FILE_EXTENSION;
        }
        
        File file = new File(path);
        
        // Make sure we don't already have it open
        for(IZentaModel m : getModels()) {
            if(file.equals(m.getFile())) {
                MessageDialog.openWarning(shell,
                        Messages.EditorModelManager_8,
                        NLS.bind(Messages.EditorModelManager_9, file));
                return null;
            }
        }
        
        // Make sure the file does not already exist
        if(file.exists()) {
            boolean result = MessageDialog.openQuestion(shell,
                    Messages.EditorModelManager_10,
                    NLS.bind(Messages.EditorModelManager_11, file));
            if(!result) {
                return null;
            }
        }
        
        return file;
    }
    
    /**
     * Create a new ComandStack for the Model
     * @param model
     */
    private void createNewCommandStack(final IZentaModel model) {
        CommandStack cmdStack = new CommandStack();
        
        // Forward on CommandStack Event to Tree
        cmdStack.addCommandStackListener(new CommandStackListener() {
            public void commandStackChanged(EventObject event) {
                // Send notification to Tree
                firePropertyChange(model, COMMAND_STACK_CHANGED, false, true);
            }
        });
        
        // Animate Commands
        AnimationUtil.registerCommandStack(cmdStack);
        
        model.setAdapter(CommandStack.class, cmdStack);
    }
    
    /**
     * Remove a CommandStack
     * @param model
     */
    private void deleteCommandStack(IZentaModel model) {
        CommandStack stack = (CommandStack)model.getAdapter(CommandStack.class);
        if(stack != null) {
            stack.dispose();
        }
    }
    
    /**
     * Set all diagram models in a model to be marked as "saved" - this for the editor view persistence
     */
    private void markDiagramModelsAsSaved(IZentaModel model) {
        for(IDiagramModel dm : model.getDiagramModels()) {
            dm.setAdapter(ADAPTER_PROPERTY_MODEL_SAVED, true);
        }
    }
    
    /**
     * Create a new ZentaveManager for the model
     */
    private IZentaveManager createNewZentaveManager(IZentaModel model) {
        IZentaveManager archiveManager = IZentaveManager.FACTORY.createZentaveManager(model);
        model.setAdapter(IZentaveManager.class, archiveManager);
        
        // Load images now
        try {
            archiveManager.loadImages();
        }
        catch(IOException ex) {
            Logger.logError("Could not load images", ex); //$NON-NLS-1$
            ex.printStackTrace();
        }
        
        return archiveManager;
    }
    
    /**
     * Remove the model's ZentaveManager
     */
    private void deleteZentaveManager(IZentaModel model) {
        IZentaveManager archiveManager = (IZentaveManager)model.getAdapter(IZentaveManager.class);
        if(archiveManager != null) {
            archiveManager.dispose();
        }
    }

    //========================== Persist backing file  ==========================

    public void saveState() throws IOException {
        Document doc = new Document();
        Element rootElement = new Element("models"); //$NON-NLS-1$
        doc.setRootElement(rootElement);
        for(IZentaModel model : getModels()) {
            File file = model.getFile(); // has been saved
            if(file != null) {
                Element modelElement = new Element("model"); //$NON-NLS-1$
                modelElement.setAttribute("file", file.getAbsolutePath()); //$NON-NLS-1$
                rootElement.addContent(modelElement);
            }
        }
        JDOMUtils.write2XMLFile(doc, backingFile);
    }
    
    private void loadState() throws IOException, JDOMException {
        if(backingFile.exists()) {
            Document doc = JDOMUtils.readXMLFile(backingFile);
            if(doc.hasRootElement()) {
                Element rootElement = doc.getRootElement();
                for(Object e : rootElement.getChildren("model")) { //$NON-NLS-1$
                    Element modelElement = (Element)e;
                    String filePath = modelElement.getAttributeValue("file"); //$NON-NLS-1$
                    if(filePath != null) {
                        loadModel(new File(filePath));
                    }
                }
            }
        }
    }
    
    //========================== Model Listener events  ==========================

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        fListeners.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        fListeners.removePropertyChangeListener(listener);
    }
    
    public void firePropertyChange(Object source, String prop, Object oldValue, Object newValue) {
        fListeners.firePropertyChange(new PropertyChangeEvent(source, prop, oldValue, newValue));
    }
    
    // ======================= ECore Adapter =========================================
    
    /**
     * Adapter listener class.
     * Forwards on messages so that listeners don't have to adapt to ECore objects
     */
    private class ECoreAdapter extends EContentAdapter {
        @Override
        public void notifyChanged(Notification msg) {
            super.notifyChanged(msg);
            // Forward on to listeners...
            firePropertyChange(this, PROPERTY_ECORE_EVENT, null, msg);
        }
    }
}
