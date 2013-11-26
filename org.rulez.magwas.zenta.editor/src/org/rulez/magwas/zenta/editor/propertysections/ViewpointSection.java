/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.propertysections;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.rulez.magwas.zenta.editor.diagram.editparts.ZentaDiagramPart;
import org.rulez.magwas.zenta.editor.model.commands.EObjectFeatureCommand;
import org.rulez.magwas.zenta.editor.model.viewpoints.IViewpoint;
import org.rulez.magwas.zenta.editor.model.viewpoints.ViewpointsManager;
import org.rulez.magwas.zenta.editor.ui.services.ComponentSelectionManager;
import org.rulez.magwas.zenta.model.IZentaDiagramModel;
import org.rulez.magwas.zenta.model.IZentaPackage;
import org.rulez.magwas.zenta.model.IDiagramModel;



/**
 * Property Section for an Zenta Element
 * 
 * @author Phillip Beauvoir
 */
public class ViewpointSection extends AbstractZentaPropertySection {
    
    private static final String HELP_ID = "org.rulez.magwas.zenta.help.diagramModelSection"; //$NON-NLS-1$
    
    /**
     * Filter to show or reject this section depending on input value
     */
    public static class Filter implements IFilter {
        @Override
        public boolean select(Object object) {
            return object instanceof IZentaDiagramModel || object instanceof ZentaDiagramPart;
        }
    }

    /*
     * Adapter to listen to changes made elsewhere (including Undo/Redo commands)
     */
    private Adapter eAdapter = new AdapterImpl() {
        @Override
        public void notifyChanged(Notification msg) {
            Object feature = msg.getFeature();
            // Viewpoint event (Undo/Redo and here)
            if(feature == IZentaPackage.Literals.ZENTA_DIAGRAM_MODEL__VIEWPOINT) {
                if(!fIsExecutingCommand) {
                    refreshControls();
                }
            }
        }
    };
    
    private IZentaDiagramModel fDiagramModel;

    private ComboViewer fComboViewer;
    
    /**
     * Set this to true when updating control to stop recursive update
     */
    private boolean fIsRefreshing;
    
    /**
     * Flag for updating Hints View
     */
    private boolean fIsShowing;
    
    @Override
    protected void createControls(Composite parent) {
        createLabel(parent, Messages.ViewpointSection_0, ITabbedLayoutConstants.STANDARD_LABEL_WIDTH, SWT.CENTER);
        
        fComboViewer = new ComboViewer(new Combo(parent, SWT.READ_ONLY | SWT.BORDER));
        fComboViewer.getCombo().setVisibleItemCount(12);
        fComboViewer.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        getWidgetFactory().adapt(fComboViewer.getControl(), true, true);
        
        fComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                if(fIsRefreshing) { // A Viewer will get a selectionChanged event when setting it
                    return;
                }
                
                if(isAlive()) {
                    IViewpoint viewPoint = (IViewpoint)((IStructuredSelection)event.getSelection()).getFirstElement();
                    if(viewPoint != null) {
                        fIsExecutingCommand = true;
                        getCommandStack().execute(new EObjectFeatureCommand(Messages.ViewpointSection_1,
                                fDiagramModel, IZentaPackage.Literals.ZENTA_DIAGRAM_MODEL__VIEWPOINT,
                                viewPoint.getIndex()));
                        fIsExecutingCommand = false;
                        // update hints view
                        updateComponentSelection(viewPoint); 
                    }
                }
            }
        });
        
        fComboViewer.setContentProvider(new IStructuredContentProvider() {
            @Override
            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            }
            
            @Override
            public void dispose() {
            }
            
            @Override
            public Object[] getElements(Object inputElement) {
                return ViewpointsManager.INSTANCE.getAllViewpoints().toArray();
            }
        });
        
        fComboViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                return ((IViewpoint)element).getName();
            }
        });
        
        fComboViewer.setInput(""); //$NON-NLS-1$

        // Help ID
        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HELP_ID);
    }

    @Override
    protected void setElement(Object element) {
        // IDiagramModel
        if(element instanceof IZentaDiagramModel) {
            fDiagramModel = (IZentaDiagramModel)element;
        }
        // IDiagramModel in Diagram Part
        else if(element instanceof ZentaDiagramPart) {
            fDiagramModel = (IZentaDiagramModel)((ZentaDiagramPart)element).getAdapter(IDiagramModel.class);
        }
        else {
            System.err.println(getClass() + " wants to display for " + element); //$NON-NLS-1$
        }
        
        refreshControls();
    }
    
    protected void refreshControls() {
        IViewpoint viewPoint = ViewpointsManager.INSTANCE.getViewpoint(fDiagramModel);
        
        fIsRefreshing = true; // A Viewer will get a selectionChanged event when setting it
        fComboViewer.setSelection(new StructuredSelection(viewPoint));
        fIsRefreshing = false;
        
        if(fIsShowing) {
            updateComponentSelection(viewPoint); 
        }
    }

    /**
     * Update Hints View
     */
    protected void updateComponentSelection(IViewpoint viewPoint) {
        ComponentSelectionManager.INSTANCE.fireSelectionEvent(this, viewPoint);
    }

    @Override
    protected Adapter getECoreAdapter() {
        return eAdapter;
    }
    
    @Override
    protected EObject getEObject() {
        return fDiagramModel;
    }
    
    @Override
    public void aboutToBeHidden() {
        fIsShowing = false;
    }
    
    @Override
    public void aboutToBeShown() {
        fIsShowing = true;
    }
}
