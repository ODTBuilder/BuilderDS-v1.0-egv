/**
 * 
 */
package com.git.qaproducer.geogig.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.git.gdsbuilder.geogig.type.GeogigCommandResponse;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;

/**
 * @author GIT
 *
 */
public interface GeogigGeoserverService {

	JSONObject getDataStoreList(DTGeoserverManager geoserverManager, String repoName, String branchName);

	GeogigCommandResponse publishGeogigLayer(DTGeoserverManager geoserverManager, String workspace, String datastore,
			String layer, String reposName, String branchName);

	JSONArray listGeoserverLayer(DTGeoserverManager geoserverManager, String workspace, String datastore);

}
