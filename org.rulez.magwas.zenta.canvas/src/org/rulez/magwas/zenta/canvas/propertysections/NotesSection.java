/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.canvas.propertysections;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.rulez.magwas.zenta.canvas.model.ICanvasPackage;
import org.rulez.magwas.zenta.canvas.model.INotesContent;
import org.rulez.magwas.zenta.editor.model.commands.EObjectFeatureCommand;
import org.rulez.magwas.zenta.editor.propertysections.AbstractZentaPropertySection;
import org.rulez.magwas.zenta.editor.propertysections.ITabbedLayoutConstants;
import org.rulez.magwas.zenta.editor.propertysections.PropertySectionTextControl;
import org.rulez.magwas.zenta.editor.ui.components.StyledTextControl;
import org.rulez.magwas.zenta.model.IZentaPackage;
import org.rulez.magwas.zenta.model.ILockable;



/**
 * Property Section for Notes
 * 
 * @author Phillip Beauvoir
 */
public class NotesSection extends AbstractZentaPropertySection {
    
    private static final String HELP_ID = "org.rulez.magwas.zenta.help.elementPropertySection"; //$NON-NLS-1$

    /**
     * Filter to show or reject this section depending on input value
     */
    public static class Filter implements IFilter {
        @Override
        public boolean select(Object object) {
            return (object instanceof EditPart) && ((EditPart)object).getModel() instanceof INotesContent;
        }
    }

    /*
     * Adapter to listen to changes made elsewhere (including Undo/Redo commands)
     */
    private Adapter eAdapter = new AdapterImpl() {
        @Override
        public void notifyChanged(Notification msg) {
            Object feature = msg.getFeature();
            // Model event
            if(feature == ICanvasPackage.Literals.NOTES_CONTENT__NOTES 
                    || feature == IZentaPackage.Literals.LOCKABLE__LOCKED) {
                refreshControls();
            }
        }
    };
    
    private INotesContent fNotesContent;
    
    private PropertySectionTextControl fTextNotesControl;
    
    @Override
    protected void createControls(Composite parent) {
        createLabel(parent, Messages.NotesSection_0, ITabbedLayoutConstants.STANDARD_LABEL_WIDTH, SWT.NONE);
        
        StyledTextControl styledTextControl = createStyledTextControl(parent, SWT.NONE);
        
        fTextNotesControl = new PropertySectionTextControl(styledTextControl.getControl(), ICanvasPackage.Literals.NOTES_CONTENT__NOTES) {
            @Override
            protected void textChanged(String oldText, String newText) {
                if(isAlive()) {
                    fIsExecutingCommand = true;
                    getCommandStack().execute(new EObjectFeatureCommand(Messages.NotesSection_1, fNotesContent,
                                                ICanvasPackage.Literals.NOTES_CONTENT__NOTES, newText));
                    fIsExecutingCommand = false;
                }
            }
        };
        fTextNotesControl.setHint(Messages.NotesSection_2);
        
        // Help
        PlatformUI.getWorkbench().getHelpSystem().setHelp(fTextNotesControl.getTextControl(), HELP_ID);
    }

    @Override
    protected void setElement(Object element) {
        if(element instanceof INotesContent) {
            fNotesContent = (INotesContent)element;
        }
        else if(element instanceof EditPart && ((EditPart)element).getModel() instanceof INotesContent) {
            fNotesContent = (INotesContent)((EditPart)element).getModel();
        }

        if(fNotesContent == null) {
            throw new RuntimeException("Notes Content was null"); //$NON-NLS-1$
        }
        
        refreshControls();
    }
    
    protected void refreshControls() {
        if(fIsExecutingCommand) {
            return; 
        }
        fTextNotesControl.refresh(fNotesContent);
        
        boolean enabled = fNotesContent instanceof ILockable ? !((ILockable)fNotesContent).isLocked() : true;
        fTextNotesControl.setEditable(enabled);
    }
    
    @Override
    protected Adapter getECoreAdapter() {
        return eAdapter;
    }

    @Override
    protected EObject getEObject() {
        return fNotesContent;
    }
    
    @Override
    public boolean shouldUseExtraSpace() {
        return true;
    }
}
