/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.diagram.figures.junctions;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.rulez.magwas.zenta.editor.diagram.figures.AbstractDiagramModelObjectFigure;
import org.rulez.magwas.zenta.editor.model.viewpoints.ViewpointsManager;
import org.rulez.magwas.zenta.model.IDiagramModelZentaObject;



/**
 * Junction Figure
 * 
 * @author Phillip Beauvoir
 */
public class JunctionFigure extends AbstractDiagramModelObjectFigure {
    
    private static final Dimension SIZE = new Dimension(15, 15);
    
    public JunctionFigure(IDiagramModelZentaObject diagramModelObject) {
        super(diagramModelObject);
    }

    @Override
    public Dimension getPreferredSize(int wHint, int hHint) {
        return SIZE;
    }
    
    @Override
    public void paintFigure(Graphics graphics) {
        graphics.pushState();
        
        graphics.setAntialias(SWT.ON);
        
        if(!isEnabled()) {
            setDisabledState(graphics);
        }
        
        graphics.setBackgroundColor(getFillColor());
        graphics.fillOval(bounds.getCopy());
        
        graphics.popState();
    }
    
    @Override
    public void refreshVisuals() {
        // Set Enabled according to current Viewpoint
        boolean enabled = ViewpointsManager.INSTANCE.isAllowedType(getDiagramModelObject());
        setEnabled(enabled);
    }
    
    @Override
    public IFigure getTextControl() {
        return null;
    }

    @Override
    public void dispose() {
    }

    @Override
    public Dimension getDefaultSize() {
        return SIZE;
    }

    @Override
    public Color getFillColor() {
        return ColorConstants.black;
    }

    @Override
    public boolean didClickTextControl(Point requestLoc) {
        return false;
    }

    @Override
    protected void setUI() {
    }
}
