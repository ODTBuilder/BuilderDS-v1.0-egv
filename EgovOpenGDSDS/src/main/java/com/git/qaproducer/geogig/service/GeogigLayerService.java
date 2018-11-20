/**
 * 
 */
package com.git.qaproducer.geogig.service;

import javax.xml.bind.JAXBException;

import com.git.gdsbuilder.geogig.type.GeogigDiff;
import com.git.gdsbuilder.geogig.type.GeogigRepositoryLog;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;

/**
 * @author GIT
 *
 */
public interface GeogigLayerService {

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param layerName
	 * @param limint
	 * @param until
	 * @param head
	 * @return
	 * @throws JAXBException
	 */
//	GeogigRepositoryLog logLayer(DTGeoserverManager geoserverManager, String repoName, String layerName, String limit,
//			String until, String head) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param oldIndex
	 * @param newIndex
	 * @param layerName
	 * @return GeogigDiff
	 * @throws JAXBException
	 */
	GeogigDiff diffLayer(DTGeoserverManager geoserverManager, String repoName, int oldIndex, int newIndex,
			String layerName) throws JAXBException;

}
