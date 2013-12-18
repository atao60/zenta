package org.rulez.magwas.zenta.metamodel.handmade;

import org.eclipse.emf.ecore.EObject;
import org.rulez.magwas.zenta.metamodel.MetamodelFactory;
import org.rulez.magwas.zenta.metamodel.RelationClassBase;

public class RootRelationClass extends RelationClassImpl {
	
	protected RootRelationClass() {
		super();
	}
	public void setReference(EObject newReference) {
		throw new MetamodelFactory.BuiltinClassShouldNotHaveReference();
	}

	@Override
	public String getName() {
		return "Basic Relation";
	}
	
	@Override
	public String getId() {
		return "basicrelation";
	}

	@Override
	public void setAncestor(RelationClassBase ancestor) {
		throw new MetamodelFactory.BuiltinClassShouldNotHaveAncestor();
	}
}