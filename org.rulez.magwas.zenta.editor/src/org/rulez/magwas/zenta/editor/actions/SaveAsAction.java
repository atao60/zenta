/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.actions;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.rulez.magwas.zenta.editor.model.IEditorModelManager;
import org.rulez.magwas.zenta.model.IZentaModel;
import org.rulez.magwas.zenta.model.util.LogUtil;


/**
 * Global Save As Action
 * 
 * @author Phillip Beauvoir
 */
public class SaveAsAction extends AbstractModelSelectionAction {
    
    public SaveAsAction(IWorkbenchWindow window) {
        super(Messages.SaveAsAction_0, window);

        setActionDefinitionId(IWorkbenchCommandConstants.FILE_SAVE_AS);
        ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
        setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_ETOOL_SAVEAS_EDIT));
        setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_ETOOL_SAVEAS_EDIT_DISABLED));
    }
    
    @Override
    public void run() {
        IZentaModel model = getActiveZentaModel();
        if(model != null) {
            try {
                IEditorModelManager.INSTANCE.saveModelAs(model);
            }
            catch(IOException ex) {
                MessageDialog.openError(workbenchWindow.getShell(), Messages.SaveAsAction_1, ex.getMessage());
                LogUtil.logException(ex);
            }
        }
    }
    
    @Override
    protected void updateState() {
        setEnabled(getActiveZentaModel() != null);
    }
}