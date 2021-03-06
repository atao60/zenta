/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.diagram.editparts;

import org.eclipse.gef.EditPart;
import org.rulez.magwas.zenta.editor.model.DiagramModelUtils;
import org.rulez.magwas.zenta.editor.preferences.ConnectionPreferences;
import org.rulez.magwas.zenta.model.IDiagramModelZentaConnection;
import org.rulez.magwas.zenta.model.IDiagramModelConnection;


/**
 * Nested Connection Filter for EditParts
 * 
 * @author Phillip Beauvoir
 */
public class NestedConnectionEditPartFilter implements IConnectionEditPartFilter {
    
    @Override
    public boolean isConnectionVisible(EditPart editPart, IDiagramModelConnection connection) {
        // If the connection is an Zenta type and its target element is an Zenta type
        // and this box contains that box and that box qualifies, don't show the connection
        if(ConnectionPreferences.useNestedConnections() && connection instanceof IDiagramModelZentaConnection) {
            return !DiagramModelUtils.shouldBeHiddenConnection((IDiagramModelZentaConnection)connection);
        }
        
        return true;
    }
}