package org.rulez.magwas.zenta.metamodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.rulez.magwas.zenta.metamodel.Attribute;
import org.rulez.magwas.zenta.metamodel.Metamodel;
import org.rulez.magwas.zenta.metamodel.MetamodelFactory;
import org.rulez.magwas.zenta.metamodel.MetamodelPackage;
import org.rulez.magwas.zenta.metamodel.ObjectClass;
import org.rulez.magwas.zenta.metamodel.RelationClass;
import org.rulez.magwas.zenta.metamodel.Template;
import org.rulez.magwas.zenta.model.impl.AssociationRelationship;
import org.rulez.magwas.zenta.model.impl.BusinessObject;
import org.rulez.magwas.zenta.model.impl.ZentaDiagramModel;

public class MetamodelFactoryImpl extends EFactoryImpl implements MetamodelFactory {

	public static MetamodelFactory init() {
		try {
			MetamodelFactory theMetamodelFactory = (MetamodelFactory)EPackage.Registry.INSTANCE.getEFactory("http://magwas.rulez.org/zenta/metamodel"); 
			if (theMetamodelFactory != null) {
				return theMetamodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MetamodelFactoryImpl();
	}

	public MetamodelFactoryImpl() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MetamodelPackage.METAMODEL: return createMetamodel();
			case MetamodelPackage.TEMPLATE: return getBuiltinTemplate();
			case MetamodelPackage.OBJECT_CLASS: return this.getBuiltinObjectClass();
			case MetamodelPackage.ATTRIBUTE: return createAttribute();
			case MetamodelPackage.RELATION_CLASS: return getBuiltinRelationClass();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	public Metamodel createMetamodel() {
		MetamodelImpl metamodel = new MetamodelImpl();
		return metamodel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Template createTemplate() {
		TemplateImpl template = new TemplateImpl();
		return template;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectClass createObjectClass() {
		ObjectClassImpl objectClass = new ObjectClassImpl();
		return objectClass;
	}

	public Template createTemplate(ZentaDiagramModel reference) {
		TemplateImpl template = new TemplateImpl(reference);
		return template;
	}

	public ObjectClass createObjectClass(BusinessObject reference) {
		ObjectClassImpl objectClass = new ObjectClassImpl(reference);
		return objectClass;
	}

	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationClass createRelationClass() {
		RelationClassImpl relationClass = new RelationClassImpl();
		return relationClass;
	}

	public RelationClass createRelationClass(AssociationRelationship referenced) {
		RelationClassImpl relationClass = new RelationClassImpl(referenced);
		return relationClass;
	}

	public MetamodelPackage getMetamodelPackage() {
		return (MetamodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MetamodelPackage getPackage() {
		return MetamodelPackage.eINSTANCE;
	}

	@Override
	public ObjectClass getBuiltinObjectClass() {
		return RootObjectClass.getInstance();
	}

	@Override
	public RelationClass getBuiltinRelationClass() {
		return RootRelationClass.getInstance();
	}

	@Override
	public BuiltinTemplate getBuiltinTemplate() {
		return BuiltinTemplate.getInstance();
	}

}