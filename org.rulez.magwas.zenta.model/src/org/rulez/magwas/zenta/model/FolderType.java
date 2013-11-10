/**
 * Copyright (c) 2010-2012 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Folder Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.rulez.magwas.zenta.model.IZentamatePackage#getFolderType()
 * @model
 * @generated
 */
public enum FolderType implements Enumerator {
	/**
	 * The '<em><b>User</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USER_VALUE
	 * @generated
	 * @ordered
	 */
	USER(0, "user", "user"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Business</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BUSINESS_VALUE
	 * @generated
	 * @ordered
	 */
	BUSINESS(1, "business", "business"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Connectors</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONNECTORS_VALUE
	 * @generated
	 * @ordered
	 */
	CONNECTORS(4, "connectors", "connectors"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Relations</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RELATIONS_VALUE
	 * @generated
	 * @ordered
	 */
	RELATIONS(5, "relations", "relations"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Diagrams</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIAGRAMS_VALUE
	 * @generated
	 * @ordered
	 */
	DIAGRAMS(6, "diagrams", "diagrams"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Derived</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DERIVED_VALUE
	 * @generated
	 * @ordered
	 */
	DERIVED(7, "derived", "derived"); //$NON-NLS-1$ //$NON-NLS-2$


	/**
	 * The '<em><b>User</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>User</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USER
	 * @model name="user"
	 * @generated
	 * @ordered
	 */

	public static final int USER_VALUE = 0;

	/**
	 * The '<em><b>Business</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Business</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BUSINESS
	 * @model name="business"
	 * @generated
	 * @ordered
	 */
	public static final int BUSINESS_VALUE = 1;

	/**
	 * The '<em><b>Connectors</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Connectors</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONNECTORS
	 * @model name="connectors"
	 * @generated
	 * @ordered
	 */
	public static final int CONNECTORS_VALUE = 4;

	/**
	 * The '<em><b>Relations</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Relations</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RELATIONS
	 * @model name="relations"
	 * @generated
	 * @ordered
	 */
	public static final int RELATIONS_VALUE = 5;

	/**
	 * The '<em><b>Diagrams</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Diagrams</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DIAGRAMS
	 * @model name="diagrams"
	 * @generated
	 * @ordered
	 */
	public static final int DIAGRAMS_VALUE = 6;

	/**
	 * The '<em><b>Derived</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Derived</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DERIVED
	 * @model name="derived"
	 * @generated
	 * @ordered
	 */
	public static final int DERIVED_VALUE = 7;

	/**
	 * An array of all the '<em><b>Folder Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FolderType[] VALUES_ARRAY =
		new FolderType[] {
			USER,
			BUSINESS,
			CONNECTORS,
			RELATIONS,
			DIAGRAMS,
			DERIVED,
		};

	/**
	 * A public read-only list of all the '<em><b>Folder Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FolderType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Folder Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FolderType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FolderType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Folder Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FolderType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FolderType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Folder Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FolderType get(int value) {
		switch (value) {
			case USER_VALUE: return USER;
			case BUSINESS_VALUE: return BUSINESS;
			case CONNECTORS_VALUE: return CONNECTORS;
			case RELATIONS_VALUE: return RELATIONS;
			case DIAGRAMS_VALUE: return DIAGRAMS;
			case DERIVED_VALUE: return DERIVED;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private FolderType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //FolderType