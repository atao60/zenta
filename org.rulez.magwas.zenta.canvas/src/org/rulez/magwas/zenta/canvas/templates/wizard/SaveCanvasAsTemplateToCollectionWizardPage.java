/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.canvas.templates.wizard;

import org.rulez.magwas.zenta.templates.model.TemplateManager;
import org.rulez.magwas.zenta.templates.wizard.SaveModelAsTemplateToCollectionWizardPage;


/**
 * Save Canvas As Template Wizard Page
 * 
 * @author Phillip Beauvoir
 */
public class SaveCanvasAsTemplateToCollectionWizardPage extends SaveModelAsTemplateToCollectionWizardPage {

    private static String HELP_ID = "org.rulez.magwas.zenta.help.SaveCanvasAsTemplateToCollectionWizardPage"; //$NON-NLS-1$

    public SaveCanvasAsTemplateToCollectionWizardPage(TemplateManager templateManager) {
        super("SaveCanvasAsTemplateWizardPage2", templateManager); //$NON-NLS-1$
    }
    
    @Override
    protected void init() {
        setTitle(Messages.SaveCanvasAsTemplateToCollectionWizardPage_0);
        setDescription(Messages.SaveCanvasAsTemplateToCollectionWizardPage_1);
    }

    
	@Override
    protected String getHelpID() {
        return HELP_ID;
    }
}
