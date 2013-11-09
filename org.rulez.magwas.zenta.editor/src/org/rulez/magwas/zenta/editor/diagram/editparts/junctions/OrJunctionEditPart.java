/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.diagram.editparts.junctions;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.rulez.magwas.zenta.editor.diagram.figures.junctions.OrJunctionFigure;


/**
 * Junction Edit Part
 * 
 * @author Phillip Beauvoir
 */
public class OrJunctionEditPart
extends JunctionEditPart {            
    
    @Override
    protected IFigure createFigure() {
        return new OrJunctionFigure(getModel());
    }

    @Override
    protected ConnectionAnchor getDefaultConnectionAnchor() {
        return new ChopboxAnchor(getFigure());
    }
}