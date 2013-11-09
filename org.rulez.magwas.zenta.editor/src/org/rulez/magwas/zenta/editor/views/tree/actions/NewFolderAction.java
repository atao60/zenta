/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.views.tree.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.rulez.magwas.zenta.editor.ui.IZentamateImages;
import org.rulez.magwas.zenta.editor.views.tree.commands.NewFolderCommand;
import org.rulez.magwas.zenta.model.FolderType;
import org.rulez.magwas.zenta.model.IZentamateFactory;
import org.rulez.magwas.zenta.model.IFolder;



/**
 * New Folder Action
 * 
 * @author Phillip Beauvoir
 */
public class NewFolderAction extends ViewerAction {
    
    public NewFolderAction(ISelectionProvider selectionProvider) {
        super(selectionProvider);
        setText(Messages.NewFolderAction_0);
        setImageDescriptor(IZentamateImages.ImageFactory.getImageDescriptor(IZentamateImages.ECLIPSE_IMAGE_FOLDER));
    }
    
    @Override
    public void run() {
        Object selected = getSelection().getFirstElement();
        
        if(selected instanceof IFolder) {
            IFolder parent = (IFolder)selected;
            
            // Create a new Folder, set its name
            IFolder folder = IZentamateFactory.eINSTANCE.createFolder();
            folder.setName(Messages.NewFolderAction_1);
            folder.setType(FolderType.USER);
            
            // Execute Command
            Command cmd = new NewFolderCommand(parent, folder);
            CommandStack commandStack = (CommandStack)parent.getAdapter(CommandStack.class);
            commandStack.execute(cmd);
        }
    }

    @Override
    public void update(IStructuredSelection selection) {
        Object selected = selection.getFirstElement();
        setEnabled(selected instanceof IFolder);
    }
}
