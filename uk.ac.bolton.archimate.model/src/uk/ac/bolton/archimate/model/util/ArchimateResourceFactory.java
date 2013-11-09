/**
 * Copyright (c) 2010-2012 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package uk.ac.bolton.archimate.model.util;

import java.io.File;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see uk.ac.bolton.archimate.model.util.ArchimateResource
 * @generated
 */
public class ArchimateResourceFactory extends ResourceFactoryImpl {
	/**
	 * Creates an instance of the resource factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchimateResourceFactory() {
		super();
	}

 /**
  * @return a ResourceSet that allows saving and loading files with any type of extension
  */
 public static ResourceSet createResourceSet() {
         ResourceSet resourceSet = new ResourceSetImpl();
         // Register the * extension on the ResourceSet to over-ride the ECore global one
         resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new ArchimateResourceFactory());  //$NON-NLS-1$
         return resourceSet;
   }

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource createResource(URI uri) {
		XMLResource result = new ArchimateResource(uri);
		result.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		result.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

		result.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

		result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
		result.getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);

		result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);
		return result;
	}

    /**
     * @return a Resource that allows saving and loading files with any type of file extension
     *          as registered in plugin.xml
     */
    public static Resource createResource(File file) {
        ResourceSet resourceSet = createResourceSet();
        
        // This will return an ArchimateResource as registered in plugin.xml
        Resource resource = resourceSet.createResource(URI.createFileURI(file.getAbsolutePath()));
        return resource;
    }

} //ArchimateResourceFactory
