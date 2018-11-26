package com.git.qaproducer.geogig.service;

import javax.xml.bind.JAXBException;

import com.git.gdsbuilder.geogig.type.GeogigBlame;
import com.git.gdsbuilder.geogig.type.GeogigDiff;
import com.git.gdsbuilder.geogig.type.GeogigFeatureRevert;
import com.git.gdsbuilder.geogig.type.GeogigFeatureSimpleLog;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.user.domain.User;

public interface GeogigFeatureService {

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param path
	 * @param newCommitId
	 * @param oldCommitId
	 * @return
	 * @throws JAXBException
	 */
	GeogigDiff featureDiff(DTGeoserverManager geoserverManager, String repoName, String path, String newCommitId,
			String oldCommitId) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param path
	 * @param branch
	 * @return
	 * @throws JAXBException
	 */
	GeogigBlame featureBlame(DTGeoserverManager geoserverManager, String repoName, String path, String branch)
			throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param path
	 * @param head
	 * @param until
	 * @param limit
	 * @param index
	 * @return
	 */
	GeogigFeatureSimpleLog featureLog(DTGeoserverManager geoserverManager, String repoName, String path, int limit,
			String until, String head, int index) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param path
	 * @param oldCommitId
	 * @param newCommitId
	 * @param commitMessage
	 * @param mergeMessage
	 * @param loginUser
	 * @return
	 * @throws JAXBException
	 */
	GeogigFeatureRevert featureRevert(DTGeoserverManager geoserverManager, String repoName, String path,
			String oldCommitId, String newCommitId, String commitMessage, String mergeMessage, User loginUser)
			throws JAXBException;

}
