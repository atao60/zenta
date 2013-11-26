package org.rulez.magwas.zenta.model.tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.rulez.magwas.zenta.model.IDiagramModelObject;
import org.rulez.magwas.zenta.model.IDiagramModelZentaConnection;
import org.rulez.magwas.zenta.model.IRelationship;
import org.rulez.magwas.zenta.model.IZentaElement;
import org.rulez.magwas.zenta.model.IZentaModel;
import org.rulez.magwas.zenta.model.IZentaDiagramModel;
import org.rulez.magwas.zenta.model.util.ZentaModelUtils;

public class ModelTestData {

	public Resource resource;
	public IZentaModel model;
	
	public ModelTestData() {
		resource = ModelTestUtils.getZentaModelResource("test.zenta");
	}
	
	public IZentaModel getModel() {
		if( null != model )
			return model;
		return (IZentaModel)resource.getContents().get(0);
	}
	
	public void saveResource() throws IOException {
		URI backup_uri = resource.getURI().appendFileExtension("bak");
		resource.setURI(backup_uri);
		Map<String, Object> options = new HashMap<String,Object>();
		resource.save(options);
	}

	public IZentaDiagramModel getTestDiagramModel() {
		IZentaModel model = getModel();
		IZentaDiagramModel diagramModel = (IZentaDiagramModel) ZentaModelUtils.getObjectByID(model, "2ea99535");
		return diagramModel;
	}

	public Resource getResource() {
		return resource;
	}

	public EObject getById(String id) {
		System.out.printf("resource is %s\n",resource.toString());
		return ZentaModelUtils.getObjectByID(getModel(), id);
	}

	public IZentaDiagramModel getZDiagramModelById(String id) {
		return (IZentaDiagramModel) ZentaModelUtils.getObjectByID(getModel(),id);
	}

	public IZentaElement getElementById(String id) {
		return (IZentaElement) ZentaModelUtils.getObjectByID(getModel(), id);
	}

	public IDiagramModelObject getDMOById(String id) {
		return (IDiagramModelObject) ZentaModelUtils.getObjectByID(getModel(),id);
	}

	public IRelationship getRelationByID(String id) {
		return (IRelationship) ZentaModelUtils.getObjectByID(getModel(), id);
	}

	public IDiagramModelZentaConnection getDMRById(String id3) {
		return (IDiagramModelZentaConnection) ZentaModelUtils.getObjectByID(getModel(),id3);
	}

	public static void assertNotEquals(String string, String string2) {
		assertFalse(string.equals(string2));
	}

	public static void assertNotEquals(int i, int j) {
		assertFalse(i == j);
	}

	public static void assertOnePropertyWithNameAndValue(IZentaElement userObject,
			String propname, String value) {
		List<String> properties = userObject.getPropertyNamed(propname);
		assertEquals(1,properties.size());
		assertEquals(value, properties.get(0));
	}
}
