/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.diagram.commands;

import org.eclipse.gef.commands.Command;
import org.eclipse.osgi.util.NLS;
import org.rulez.magwas.zenta.model.IZentaFactory;
import org.rulez.magwas.zenta.model.IDiagramModelZentaConnection;
import org.rulez.magwas.zenta.model.IDiagramModelObject;
import org.rulez.magwas.zenta.model.IBasicRelationship;



/**
 * Add Connection Command
 * Used when dragging and dropping an Zenta Relationship from the tree to the diagram.
 * Called from {@link org.rulez.magwas.zenta.editor.diagram.policies.ZentaDNDEditPolicy}
 * 
 * @author Phillip Beauvoir
 */
public class AddDiagramZentaConnectionCommand extends Command {
    
    private IDiagramModelZentaConnection fConnection;
    private IDiagramModelObject fSource, fTarget;
    
    public AddDiagramZentaConnectionCommand(IDiagramModelObject src, IDiagramModelObject tgt, IBasicRelationship relationship) {
        setLabel(NLS.bind(Messages.AddDiagramZentaConnectionCommand_0, relationship.getName()));

        fSource = src;
        fTarget = tgt;
        fConnection = IZentaFactory.eINSTANCE.createDiagramModelZentaConnection();
        fConnection.setRelationship(relationship);
    }

    @Override
    public void execute() {
        fConnection.connect(fSource, fTarget);
    }

    @Override
    public void undo() {
        fConnection.disconnect();
    }

    @Override
    public void redo() {
        fConnection.reconnect();
    }
}