/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.canvas.editparts;

import org.eclipse.gef.EditPolicy;
import org.rulez.magwas.zenta.canvas.policies.CanvasConnectionPolicy;
import org.rulez.magwas.zenta.editor.diagram.editparts.diagram.DiagramModelReferenceEditPart;
import org.rulez.magwas.zenta.editor.diagram.policies.PartComponentEditPolicy;



/**
 * Diagram Model Reference Edit Part
 * 
 * @author Phillip Beauvoir
 */
public class CanvasDiagramModelReferenceEditPart extends DiagramModelReferenceEditPart {

    @Override
    protected void createEditPolicies() {
        // Allow parts to be connected
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new CanvasConnectionPolicy());

        // Add a policy to handle editing the Parts (for example, deleting a part)
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new PartComponentEditPolicy());
    }

}
