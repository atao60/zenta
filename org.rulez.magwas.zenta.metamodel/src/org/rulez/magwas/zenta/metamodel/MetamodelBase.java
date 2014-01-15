/**
 */
package org.rulez.magwas.zenta.metamodel;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.rulez.magwas.zenta.metamodel.handmade.BuiltinTemplate;
import org.rulez.magwas.zenta.model.IDiagramModel;
import org.rulez.magwas.zenta.model.IDiagramModelComponent;
import org.rulez.magwas.zenta.model.IIdentifier;
import org.rulez.magwas.zenta.model.IRelationship;
import org.rulez.magwas.zenta.model.IZentaElement;
import org.rulez.magwas.zenta.model.IZentaModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>MetamodelBase Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.rulez.magwas.zenta.metamodel.MetamodelBase#getTemplates <em>Templates</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.rulez.magwas.zenta.metamodel.MetamodelBasePackage#getMetamodelBase()
 * @model
 * @generated
 */
public interface MetamodelBase extends EObject {
	/**
	 * Returns the value of the '<em><b>Templates</b></em>' containment reference list.
	 * The list contents are of type {@link org.rulez.magwas.zenta.metamodel.ITemplate}.
	 * It is bidirectional and its opposite is '{@link org.rulez.magwas.zenta.metamodel.ITemplate#getMetamodel <em>MetamodelBase</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Templates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Templates</em>' containment reference list.
	 * @see org.rulez.magwas.zenta.metamodel.MetamodelBasePackage#getMetamodelBase_Templates()
	 * @see org.rulez.magwas.zenta.metamodel.ITemplate#getMetamodel
	 * @model opposite="metamodel" containment="true"
	 * @generated
	 */
	EList<ITemplate> getTemplates();

    IZentaModel getModel();
    
    ReferencesModelObject getClassById(String classId);
	ObjectClass getBuiltinObjectClass();
	IRelationClass getBuiltinRelationClass();
	
    BuiltinTemplate getBuiltinTemplate();
    ITemplate getTemplateFor(IDiagramModel dm);
    ITemplate getTemplateFor(IDiagramModelComponent element);
    
    ReferencesModelObject getClassOf(IIdentifier rel);
    ReferencesModelObject getClassReferencing(IIdentifier modelElement);
    ObjectClass getObjectClassReferencing(IZentaElement element);
    IRelationClass getRelationClassReferencing(IRelationship relation);
	boolean hasRelationClassReferencing(IRelationship relation);
	boolean hasObjectClassReferencing(IZentaElement elementToAdd);

	List<ObjectClass> getObjectClasses();
	List<IRelationClass> getRelationClasses();
	List<ObjectClass> getConnectorClasses();
	
	Collection<IRelationClass> getRelationships(IZentaElement object);
	List<IRelationClass> getWeaklist();
	boolean isValidRelationship(IZentaElement element1, IZentaElement element2,
			IRelationClass relationshipClass);

} // MetamodelBase
