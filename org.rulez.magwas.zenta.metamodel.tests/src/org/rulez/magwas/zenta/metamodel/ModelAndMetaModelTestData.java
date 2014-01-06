package org.rulez.magwas.zenta.metamodel;

import static org.junit.Assert.assertNotNull;
import org.rulez.magwas.zenta.metamodel.ObjectClass;
import org.rulez.magwas.zenta.metamodel.RelationClass;
import org.rulez.magwas.zenta.model.IDiagramModel;
import org.rulez.magwas.zenta.model.IDiagramModelZentaConnection;
import org.rulez.magwas.zenta.model.IDiagramModelZentaObject;
import org.rulez.magwas.zenta.model.IFolder;
import org.rulez.magwas.zenta.model.IRelationship;
import org.rulez.magwas.zenta.model.IZentaDiagramModel;
import org.rulez.magwas.zenta.model.IZentaElement;
import org.rulez.magwas.zenta.model.IZentaFactory;
import org.rulez.magwas.zenta.model.testutils.ModelTestData;
import org.rulez.magwas.zenta.model.util.ZentaModelUtils;

public class ModelAndMetaModelTestData extends ModelTestData {

	public IDiagramModelZentaConnection connection;
	public IZentaDiagramModel diagramModel;
	public IDiagramModelZentaConnection connection2;
	public Metamodel metamodel;

	public ModelAndMetaModelTestData() {
		super();
        setUpMetaModel();
		diagramModel = getTemplateDiagramModel();
		assertNotNull(diagramModel);
		connection = getDMRById("24e3c661");
		assertNotNull(connection);
		connection2 = getDMRById("99e9c255");
		assertNotNull(connection2);

	}
	public ModelAndMetaModelTestData(String resourcename) {
		super(resourcename);
		setUpMetaModel();
	}
		private void setUpMetaModel() {
			metamodel = MetamodelFactory.eINSTANCE.createMetamodel(model);
	        assertNotNull(metamodel);
	        assertNotNull(metamodel.getTemplates());
		}

	public IZentaElement createClassedTestElement() {
		String id = "ea94cf6c";//User
		IZentaElement user = getElementById(id);
		IFolder folder = ModelAndMetaModelTestData.getFolderByKid(user);
		ObjectClass oc = metamodel.getBuiltinObjectClass();
		IZentaElement newElement = (IZentaElement) oc.create(folder);
		return newElement;
	}
	public IZentaElement createClassedTestElement(ObjectClass baseClass) {
		String id = "ea94cf6c";//User
		IZentaElement user = getElementById(id);
		IFolder folder = ModelAndMetaModelTestData.getFolderByKid(user);
		IZentaElement newElement = (IZentaElement) baseClass.create(folder);
		return newElement;
	}

	public ObjectClass createTestObjectClass() {
		IZentaElement element = (IZentaElement) ZentaModelUtils.getObjectByID(model, "ea94cf6c");
		Template template = (Template) metamodel.getTemplates().get(0);
		assertNotNull(template);
		assertNotNull(template.getMetamodel());
		return MetamodelFactory.eINSTANCE
				.createObjectClass(
						element,
						template);
	}

	public IZentaElement createNewObjectClass(String elementName, ObjectClass baseClass) {
		IZentaElement newElement = createClassedTestElement(baseClass);
		IDiagramModel dm = getTemplateDiagramModel();
		IDiagramModelZentaObject dmo = ModelTestData.createDMOFor(newElement);
	
		dm.getChildren().add(dmo);
		newElement.setName(elementName);
		return newElement;
	}

	public IZentaElement createNewObjectClass(String elementName) {
		IZentaElement newElement = createClassedTestElement();
		IDiagramModel dm = getTemplateDiagramModel();
		IDiagramModelZentaObject dmo = ModelTestData.createDMOFor(newElement);
	
		dm.getChildren().add(dmo);
		newElement.setName(elementName);
		return newElement;
	}
	public IRelationship createNewRelationClass(String elementName) {
		RelationClass oc = metamodel.getBuiltinRelationClass();
		IDiagramModel dm = getTemplateDiagramModel();
		IRelationship rel = createNewConnection(elementName, oc, dm);
		return rel;
	}

	public IRelationship createNewNondefiningRelationBasedOn(RelationClass baseClass) {
		String name = "NonDefiningRelation";
		IDiagramModel dm = (IDiagramModel) this.getById("63f1b081");
		return createNewConnection(name, baseClass, dm);
	}
	public IRelationship createNewConnection(String name,
			RelationClass baseRelationClass, IDiagramModel diagram) {
		IRelationship rel = createUnnamedRelation(baseRelationClass, diagram);
		rel.setName(name);
		return rel;
	}

	public IRelationship createUnnamedRelation(
			RelationClass baseRelationClass, IDiagramModel diagram) {
		IZentaElement sourceElement = createClassedTestElement();
		IZentaElement targetElement = createClassedTestElement();
		IRelationship rel = (IRelationship) baseRelationClass.create((IFolder) sourceElement.eContainer());
		rel.setSource(sourceElement);
		rel.setTarget(targetElement);
		IDiagramModelZentaObject dmo = ModelTestData.createDMOFor(sourceElement);
		IDiagramModelZentaObject dmo2 = ModelTestData.createDMOFor(targetElement);
		sourceElement.setName("test OC 1");
		targetElement.setName("test OC 2");
		IDiagramModelZentaConnection diagramRelation =
				IZentaFactory.eINSTANCE.createDiagramModelZentaConnection();
		diagramRelation.setSource(dmo);
		diagramRelation.setTarget(dmo2);
		diagramRelation.setRelationship(rel);
	
		diagram.getChildren().add(dmo);
		diagram.getChildren().add(dmo2);
		dmo.addConnection(diagramRelation);
		return rel;
	}

}