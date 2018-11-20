/**
 * 
 */
package com.git.qaproducer.geogig.service;

import javax.xml.bind.JAXBException;

import com.git.gdsbuilder.geogig.type.GeogigCat;
import com.git.gdsbuilder.geogig.type.GeogigFeatureAttribute;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;

/**
 * @author GIT
 *
 */
public interface GeogigObjectService {

	GeogigCat catObject(DTGeoserverManager geoserverManager, String repoName, String objectid) throws JAXBException;

	GeogigFeatureAttribute catConflictFeatureObject(DTGeoserverManager geoserverManager, String repoName, String path,
			String commitId, String featureId) throws JAXBException;

	GeogigFeatureAttribute catFeatureObject(DTGeoserverManager geoserverManager, String repoName, String path,
			String objectid) throws JAXBException;

}
