/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.ui.factory.business;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.rulez.magwas.zenta.editor.diagram.editparts.business.BusinessObjectEditPart;
import org.rulez.magwas.zenta.editor.ui.IArchimateImages;
import org.rulez.magwas.zenta.model.IArchimatePackage;



/**
 * Business Object UI Provider
 * 
 * @author Phillip Beauvoir
 */
public class BusinessObjectUIProvider extends AbstractBusinessUIProvider {

    public EClass providerFor() {
        return IArchimatePackage.eINSTANCE.getBusinessObject();
    }
    
    @Override
    public EditPart createEditPart() {
        return new BusinessObjectEditPart();
    }

    @Override
    public String getDefaultName() {
        return Messages.BusinessObjectUIProvider_0;
    }

    @Override
    public String getDefaultShortName() {
        return Messages.BusinessObjectUIProvider_1;
    }

    @Override
    public Image getImage() {
        return IArchimateImages.ImageFactory.getImage(IArchimateImages.ICON_BUSINESS_OBJECT_16);
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return IArchimateImages.ImageFactory.getImageDescriptor(IArchimateImages.ICON_BUSINESS_OBJECT_16);
    }
}
