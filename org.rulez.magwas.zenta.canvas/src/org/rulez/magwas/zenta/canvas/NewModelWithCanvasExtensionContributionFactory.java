/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.canvas;

import java.io.IOException;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;
import org.rulez.magwas.zenta.canvas.preferences.IPreferenceConstants;
import org.rulez.magwas.zenta.canvas.templates.wizard.NewCanvasFromTemplateWizard;
import org.rulez.magwas.zenta.editor.model.IEditorModelManager;
import org.rulez.magwas.zenta.editor.ui.components.ExtendedWizardDialog;
import org.rulez.magwas.zenta.model.IZentaFactory;
import org.rulez.magwas.zenta.model.IZentaModel;
import org.rulez.magwas.zenta.model.IFolder;
import org.rulez.magwas.zenta.model.util.LogUtil;



/**
 * New Model with Canvas ExtensionContributionFactory
 * Add menu item to New menu
 * 
 * @author Phillip Beauvoir
 */
public class NewModelWithCanvasExtensionContributionFactory extends ExtensionContributionFactory {
    
    // TODO Do we really want this menu item?
    private final static boolean ENABLED = true;
    
    public NewModelWithCanvasExtensionContributionFactory() {
    }

    @Override
    public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {
        if(!ENABLED) {
            return;
        }
        
        IContributionItem item = new ActionContributionItem(new NewModelWithCanvasAction());
        additions.addContributionItem(item, visibleExpression);
    }

    /**
     * Action to create new Model with Canvas
     */
    private class NewModelWithCanvasAction extends Action {
        @Override
        public String getText() {
            return Messages.NewModelWithCanvasExtensionContributionFactory_0;
        }
        
        @Override
        public void run() {
            IZentaModel model = IZentaFactory.eINSTANCE.createZentaModel();
            model.setName(Messages.NewModelWithCanvasExtensionContributionFactory_1);
            IEditorModelManager.INSTANCE.registerModel(model);
            
            IFolder folder = model.getFolders().get(0);
            
            WizardDialog dialog = new ExtendedWizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    new NewCanvasFromTemplateWizard(folder),
                    "NewCanvasFromTemplateWizard"); //$NON-NLS-1$
            int result = dialog.open();
            
            // User cancelled
            if(result == Window.CANCEL) {
                try {
                    IEditorModelManager.INSTANCE.closeModel(model);
                }
                catch(IOException ex) {
                    LogUtil.logException(ex);
                }
            }
        }
        
        @Override
        public String getId() {
            return "newModelWithCanvasAction"; //$NON-NLS-1$
        };
        
        @Override
        public ImageDescriptor getImageDescriptor() {
            return ICanvasImages.ImageFactory.getImageDescriptor(ICanvasImages.ICON_NEWFILE_16);
        }
    };
    
    /**
     * Evaluate whether the menu item is visible
     */
    private Expression visibleExpression = new Expression() {
        @Override
        public EvaluationResult evaluate(IEvaluationContext context) throws CoreException {
            boolean isVisible = CanvasEditorPlugin.INSTANCE.getPreferenceStore().getBoolean(IPreferenceConstants.CANVAS_EDITOR_ENABLED);
            return isVisible ? EvaluationResult.TRUE : EvaluationResult.FALSE;
        }
    };
}
