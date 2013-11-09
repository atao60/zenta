/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.templates.impl;

import org.eclipse.jface.wizard.WizardDialog;
import org.rulez.magwas.zenta.editor.actions.AbstractModelSelectionDelegateAction;
import org.rulez.magwas.zenta.editor.ui.components.ExtendedWizardDialog;
import org.rulez.magwas.zenta.model.IZentamateModel;
import org.rulez.magwas.zenta.templates.impl.wizard.SaveZentamateModelAsTemplateWizard;



/**
 * Save As Template Action
 * 
 * @author Phillip Beauvoir
 */
public class SaveAsTemplateAction extends AbstractModelSelectionDelegateAction {
    
    @Override
    public void run() {
        IZentamateModel model = getActiveZentamateModel();
        if(model != null) {
            WizardDialog dialog = new ExtendedWizardDialog(workbenchWindow.getShell(),
                    new SaveZentamateModelAsTemplateWizard(model),
                    "SaveModelAsTemplateWizard"); //$NON-NLS-1$
            dialog.open();
        }
    }
    
}
