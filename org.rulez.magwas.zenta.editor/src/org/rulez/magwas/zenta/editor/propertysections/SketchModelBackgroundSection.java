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
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.rulez.magwas.zenta.editor.diagram.sketch.ISketchEditor;
import org.rulez.magwas.zenta.editor.diagram.sketch.editparts.SketchDiagramPart;
import org.rulez.magwas.zenta.editor.model.commands.EObjectFeatureCommand;
import org.rulez.magwas.zenta.model.IZentaPackage;
import org.rulez.magwas.zenta.model.ISketchModel;



/**
 * Property Section for a Diagram Model's Connection type
 * 
 * @author Phillip Beauvoir
 */
public class SketchModelBackgroundSection extends AbstractZentaPropertySection {
    
    private static final String HELP_ID = "org.rulez.magwas.zenta.help.sketchModelDiagramSection"; //$NON-NLS-1$

    /**
     * Filter to show or reject this section depending on input value
     */
    public static class Filter implements IFilter {
        @Override
        public boolean select(Object object) {
            return object instanceof ISketchModel || object instanceof SketchDiagramPart;
        }
    }

    /*
     * Adapter to listen to changes made elsewhere (including Undo/Redo commands)
     */
    private Adapter eAdapter = new AdapterImpl() {
        @Override
        public void notifyChanged(Notification msg) {
            Object feature = msg.getFeature();
            // Change made from Menu Action
            if(feature == IZentaPackage.Literals.SKETCH_MODEL__BACKGROUND) {
                refreshControls();
            }
        }
    };
    
    private Combo fComboBackground;
    
    private ISketchModel fSketchModel;
    
    @Override
    protected void createControls(Composite parent) {
        createBackgroundControl(parent);
        
        // Help ID
        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HELP_ID);
    }
    
    private void createBackgroundControl(Composite parent) {
        // Label
        createLabel(parent, Messages.SketchModelBackgroundSection_0, ITabbedLayoutConstants.BIG_LABEL_WIDTH, SWT.CENTER);
        
        // Combo
        fComboBackground = new Combo(parent, SWT.READ_ONLY);
        fComboBackground.setItems(ISketchEditor.BACKGROUNDS);
        fComboBackground.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(isAlive()) {
                    fIsExecutingCommand = true;
                    getCommandStack().execute(new EObjectFeatureCommand(Messages.SketchModelBackgroundSection_1,
                            fSketchModel,
                            IZentaPackage.Literals.SKETCH_MODEL__BACKGROUND,
                            fComboBackground.getSelectionIndex()));
                    fIsExecutingCommand = false;
                }
            }
        });
        
        GridData gd = new GridData(SWT.NONE, SWT.NONE, true, false);
        gd.minimumWidth = ITabbedLayoutConstants.COMBO_WIDTH;
        fComboBackground.setLayoutData(gd);
    }

    @Override
    protected void setElement(Object element) {
        if(element instanceof SketchDiagramPart) {
            fSketchModel = ((SketchDiagramPart)element).getModel();
        }
        else if(element instanceof ISketchModel) {
            fSketchModel = (ISketchModel)element;
        }
        else {
            System.err.println("Section wants to display for " + element); //$NON-NLS-1$
        }
        
        refreshControls();
    }
    
    protected void refreshControls() {
        if(fIsExecutingCommand) {
            return; 
        }
        
        fComboBackground.select(fSketchModel.getBackground());
    }

    @Override
    protected Adapter getECoreAdapter() {
        return eAdapter;
    }

    @Override
    protected EObject getEObject() {
        return fSketchModel;
    }
}
