/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.diagram;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorPart;
import org.rulez.magwas.zenta.editor.preferences.IPreferenceConstants;
import org.rulez.magwas.zenta.editor.preferences.Preferences;
import org.rulez.magwas.zenta.editor.ui.ZentaLabelProvider;
import org.rulez.magwas.zenta.editor.ui.ColorFactory;
import org.rulez.magwas.zenta.model.IZentaElement;
import org.rulez.magwas.zenta.model.IZentaFactory;
import org.rulez.magwas.zenta.model.IDiagramModelZentaConnection;
import org.rulez.magwas.zenta.model.IDiagramModelZentaObject;
import org.rulez.magwas.zenta.model.IDiagramModelGroup;
import org.rulez.magwas.zenta.model.IRelationship;



/**
 * Diagram Model Factory for creating objects from the Palette in the Zenta Diagram Editor
 * 
 * @author Phillip Beauvoir
 */
public class ZentaDiagramModelFactory implements ICreationFactory {
    
    /**
     * Factory method for creating a new IDiagramModelZentaObject for an IZentaElement
     * @param element
     * @return a new IDiagramModelZentaObject
     */
    public static IDiagramModelZentaObject createDiagramModelZentaObject(IZentaElement element) {
        IDiagramModelZentaObject dmo = IZentaFactory.eINSTANCE.createDiagramModelZentaObject();
        dmo.setZentaElement(element);
        dmo.setType(Preferences.getDefaultFigureType(dmo));
        
        // Set user fill color
        if(Preferences.STORE.getBoolean(IPreferenceConstants.SAVE_USER_DEFAULT_FILL_COLOR)) {
            Color fillColor = ColorFactory.getDefaultFillColor(dmo);
            if(fillColor != null) {
                dmo.setFillColor(ColorFactory.convertRGBToString(fillColor.getRGB()));
            }
        }
        
        return dmo;
    }

    
    private EClass fTemplate;
    
    /**
     * Constructor for creating a new Ecore type model
     * @param eClass
     */
    public ZentaDiagramModelFactory(EClass template) {
        fTemplate = template;
    }
    
    public boolean isUsedFor(IEditorPart editor) {
        return editor instanceof IZentaDiagramEditor;
    }
    
    public Object getNewObject() {
        if(fTemplate == null) {
            return null;
        }
        
        Object object = IZentaFactory.eINSTANCE.create(fTemplate);
        
        // Connection created from Relationship Template
        if(object instanceof IRelationship) {
            IDiagramModelZentaConnection connection = IZentaFactory.eINSTANCE.createDiagramModelZentaConnection();
            connection.setRelationship((IRelationship)object);
            return connection;
        }
        
        // Zenta Diagram Object created from Zenta Element Template
        else if(object instanceof IZentaElement) {
            IZentaElement element = (IZentaElement)object;
            element.setName(ZentaLabelProvider.INSTANCE.getDefaultName(fTemplate));
            return createDiagramModelZentaObject(element);
        }
        
        // Group
        else if(object instanceof IDiagramModelGroup) {
            ((IDiagramModelGroup)object).setName(Messages.ZentaDiagramModelFactory_0);
        }
        
        return object;
    }

    public Object getObjectType() {
        return fTemplate;
    }
}