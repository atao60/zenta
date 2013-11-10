/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.ui.factory.connections;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.rulez.magwas.zenta.editor.diagram.editparts.connections.SpecialisationConnectionEditPart;
import org.rulez.magwas.zenta.editor.ui.IZentamateImages;
import org.rulez.magwas.zenta.model.IZentamatePackage;



/**
 * Specialisation Connection UI Provider
 * 
 * @author Phillip Beauvoir
 */
public class SpecialisationConnectionUIProvider extends AbstractConnectionUIProvider {

    public EClass providerFor() {
        return IZentamatePackage.eINSTANCE.getSpecialisationRelationship();
    }
    
    @Override
    public EditPart createEditPart() {
        return new SpecialisationConnectionEditPart();
    }

    @Override
    public String getDefaultName() {
        return Messages.SpecialisationConnectionUIProvider_0;
    }

    @Override
    public String getDefaultShortName() {
        return Messages.SpecialisationConnectionUIProvider_1;
    }

    @Override
    public Image getImage() {
        return IZentamateImages.ImageFactory.getImage(IZentamateImages.ICON_SPECIALISATION_CONNECTION_16);
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return IZentamateImages.ImageFactory.getImageDescriptor(IZentamateImages.ICON_SPECIALISATION_CONNECTION_16);
    }
}