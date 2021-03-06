/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.diagram.figures.connections;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.rulez.magwas.zenta.model.IDiagramModelZentaConnection;


/**
 * Basic Connection Figure
 * 
 * @author Phillip Beauvoir
 */
public class BasicConnectionFigure extends AbstractZentaConnectionFigure {
	
	private List<IConnectionDecoration> decorations;
	private String lineDecorationString;
	
    public BasicConnectionFigure(IDiagramModelZentaConnection connection) {
        super(connection);
        initDecorations(connection);
    }

    public String getlineDecorationString() {
    	return lineDecorationString;
    }
    public void initDecorations(IDiagramModelZentaConnection connection) {
        decorations = new ArrayList<IConnectionDecoration>();
        lineDecorationString = connection.getFinalLineDecoration();
        if (null == lineDecorationString)
        	return;
		List<String> decorNames = Arrays.asList(lineDecorationString.split(" "));
        for(String name : decorNames)
        	addDecoration(name);
        setFigureProperties();
    }
	    private void addDecoration(String decorName) {
	    	IConnectionDecoration decorObj = ConnectionDecorationFactory.getInstance().getByName(decorName);
	    	if(null != decorObj)
	    		decorations.add(decorObj);
	    }

	@Override
    protected void setFigureProperties() {
    	clearDecorations();
    	if(null!=decorations) {
        	for(IConnectionDecoration decor : decorations) {
        		decor.setFigureProperties(this);
        	}    		
    	}
    }
		private void clearDecorations() {
			setSourceDecoration(null);
			setTargetDecoration(null);
			setLineStyle(SWT.LINE_SOLID);
		}
}
