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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.rulez.magwas.zenta.canvas.model.ICanvasPackage;
import org.rulez.magwas.zenta.canvas.model.IHintProvider;
import org.rulez.magwas.zenta.editor.model.commands.EObjectFeatureCommand;
import org.rulez.magwas.zenta.editor.propertysections.AbstractArchimatePropertySection;
import org.rulez.magwas.zenta.editor.propertysections.ITabbedLayoutConstants;
import org.rulez.magwas.zenta.editor.propertysections.PropertySectionTextControl;
import org.rulez.magwas.zenta.editor.ui.components.StyledTextControl;
import org.rulez.magwas.zenta.model.IArchimatePackage;
import org.rulez.magwas.zenta.model.ILockable;



/**
 * Property Section for a Hint Content
 * 
 * @author Phillip Beauvoir
 */
public class HintContentSection extends AbstractArchimatePropertySection {
    
    private static final String HELP_ID = "org.rulez.magwas.zenta.help.elementPropertySection"; //$NON-NLS-1$

    /**
     * Filter to show or reject this section depending on input value
     */
    public static class Filter implements IFilter {
        @Override
        public boolean select(Object object) {
            return (object instanceof IHintProvider) ||
                    ((object instanceof EditPart) && ((EditPart)object).getModel() instanceof IHintProvider);
        }
    }

    /*
     * Adapter to listen to changes made elsewhere (including Undo/Redo commands)
     */
    private Adapter eAdapter = new AdapterImpl() {
        @Override
        public void notifyChanged(Notification msg) {
            Object feature = msg.getFeature();
            // Model Name event (Undo/Redo and here!)
            if(feature == ICanvasPackage.Literals.HINT_PROVIDER__HINT_TITLE || 
                    feature == ICanvasPackage.Literals.HINT_PROVIDER__HINT_CONTENT ||
                    feature == IArchimatePackage.Literals.LOCKABLE__LOCKED) {
                refreshControls();
            }
        }
    };
    
    private IHintProvider fHintProvider;
    
    private PropertySectionTextControl fTextTitleControl;
    private PropertySectionTextControl fTextContentControl;

    @Override
    protected void createControls(Composite parent) {
        createLabel(parent, Messages.HintContentSection_0, ITabbedLayoutConstants.STANDARD_LABEL_WIDTH, SWT.CENTER);
        Text text = createSingleTextControl(parent, SWT.NONE);
        fTextTitleControl = new PropertySectionTextControl(text, ICanvasPackage.Literals.HINT_PROVIDER__HINT_TITLE) {
            @Override
            protected void textChanged(String oldText, String newText) {
                if(isAlive()) {
                    fIsExecutingCommand = true;
                    getCommandStack().execute(new EObjectFeatureCommand(Messages.HintContentSection_1, fHintProvider,
                            ICanvasPackage.Literals.HINT_PROVIDER__HINT_TITLE, newText));
                    fIsExecutingCommand = false;
                }
            }
        };
        fTextTitleControl.setHint(Messages.HintContentSection_2);
        
        createLabel(parent, Messages.HintContentSection_3, ITabbedLayoutConstants.STANDARD_LABEL_WIDTH, SWT.NONE);
        StyledTextControl styledTextControl = createStyledTextControl(parent, SWT.NONE);
        fTextContentControl = new PropertySectionTextControl(styledTextControl.getControl(), ICanvasPackage.Literals.HINT_PROVIDER__HINT_CONTENT) {
            @Override
            protected void textChanged(String oldText, String newText) {
                if(isAlive()) {
                    fIsExecutingCommand = true;
                    getCommandStack().execute(new EObjectFeatureCommand(Messages.HintContentSection_4, fHintProvider,
                            ICanvasPackage.Literals.HINT_PROVIDER__HINT_CONTENT, newText));
                    fIsExecutingCommand = false;
                }
            }
        };
        fTextContentControl.setHint(Messages.HintContentSection_5);
        
        // Help
        PlatformUI.getWorkbench().getHelpSystem().setHelp(fTextContentControl.getTextControl(), HELP_ID);
    }
    
    @Override
    protected void setElement(Object element) {
        if(element instanceof IHintProvider) {
            fHintProvider = (IHintProvider)element;
        }
        else if(element instanceof EditPart && ((EditPart)element).getModel() instanceof IHintProvider) {
            fHintProvider = (IHintProvider)((EditPart)element).getModel();
        }
        else {
            System.err.println("Hint Provider was null in " + getClass()); //$NON-NLS-1$
        }
        
        refreshControls();
    }
    
    protected void refreshControls() {
        if(fIsExecutingCommand) {
            return; 
        }
        
        fTextTitleControl.refresh(fHintProvider);
        fTextContentControl.refresh(fHintProvider);
        
        boolean enabled = fHintProvider instanceof ILockable ? !((ILockable)fHintProvider).isLocked() : true;
        fTextTitleControl.setEditable(enabled);
        fTextContentControl.setEditable(enabled);
    }
    
    @Override
    protected Adapter getECoreAdapter() {
        return eAdapter;
    }

    @Override
    protected EObject getEObject() {
        return fHintProvider;
    }
    
    @Override
    public boolean shouldUseExtraSpace() {
        return true;
    }
    
}
