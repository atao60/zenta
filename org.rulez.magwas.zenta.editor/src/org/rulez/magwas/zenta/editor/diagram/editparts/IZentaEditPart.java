/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.diagram.editparts;

import org.eclipse.gef.GraphicalEditPart;
import org.rulez.magwas.zenta.model.IDiagramModelZentaObject;



/**
 * IZentaEditPart
 * 
 * @author Phillip Beauvoir
 */
public interface IZentaEditPart extends GraphicalEditPart {

    IDiagramModelZentaObject getModel();
    
}
