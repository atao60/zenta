/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.views.tree.commands;

import org.eclipse.gef.commands.CommandStack;
import org.rulez.magwas.zenta.editor.model.commands.EObjectFeatureCommand;
import org.rulez.magwas.zenta.model.FolderType;
import org.rulez.magwas.zenta.model.IAdapter;
import org.rulez.magwas.zenta.model.IArchimatePackage;
import org.rulez.magwas.zenta.model.IFolder;
import org.rulez.magwas.zenta.model.INameable;



/**
 * Handles Rename Commands for the Tree Model View
 * 
 * @author Phillip Beauvoir
 */
public class RenameCommandHandler {

    /**
     * @param element
     * @return True if element can be renamed
     */
    public static boolean canRename(Object element) {
        if(element instanceof IFolder) {
            return ((IFolder)element).getType() == FolderType.USER;
        }
        
        return (element instanceof INameable) && (element instanceof IAdapter);
    }
    
    /**
     * Rename element to newText by issuing a Command on the CommandStack
     * @param element
     * @param newText
     */
    public static void doRenameCommand(INameable element, String newText) {
        CommandStack stack = (CommandStack)((IAdapter)element).getAdapter(CommandStack.class);
        if(stack != null) {
            stack.execute(new EObjectFeatureCommand(Messages.RenameCommandHandler_0 + " " + element.getName(), element, //$NON-NLS-1$
                    IArchimatePackage.Literals.NAMEABLE__NAME, newText));
        }
    }
}
