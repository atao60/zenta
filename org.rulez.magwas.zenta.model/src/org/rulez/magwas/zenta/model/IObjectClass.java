/**
 */
package org.rulez.magwas.zenta.model;

import java.util.List;
import java.util.Map;

import org.rulez.magwas.zenta.model.IAttribute.Direction;

public interface IObjectClass extends IHelpHintProvider {
	
	IBasicObject create(IFolder folder);
	
	boolean isObject();
	boolean isRelation();
	boolean isTemplate();
	void setAsTemplate(ITemplate template);
	IBasicObject getDefiningElement();

	public boolean isAllowedRelation(IBasicRelationship relclass, Direction direction);
	public Map<Direction,List<IBasicRelationship>> getAllowedRelations();


	ITemplate getTemplate();

	IMetamodel getMetamodel();

	String getId();
	
	public abstract List<IBasicObject> getAncestry(List<IBasicObject> ancestry);

	public abstract List<IBasicObject> getAncestry();



} // ObjectClass
