/**
 * 
 */
package com.git.qaproducer.geogig.service;

import javax.xml.bind.JAXBException;

import com.git.gdsbuilder.geogig.type.GeogigAdd;
import com.git.gdsbuilder.geogig.type.GeogigCommit;
import com.git.gdsbuilder.geogig.type.GeogigFetch;
import com.git.gdsbuilder.geogig.type.GeogigPull;
import com.git.gdsbuilder.geogig.type.GeogigPush;
import com.git.gdsbuilder.geogig.type.GeogigRemoteRepository;
import com.git.gdsbuilder.geogig.type.GeogigRepositoryDelete;
import com.git.gdsbuilder.geogig.type.GeogigRepositoryInit;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.user.domain.User;

/**
 * @author GIT
 *
 */
public interface GeogigRepositoryService {

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param transactionId
	 * @return
	 */
	GeogigAdd addRepository(DTGeoserverManager geoserverManager, String repoName, String transactionId)
			throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param transactionId
	 * @param message
	 * @param loginUser
	 * @return
	 * @throws JAXBException
	 */
	GeogigCommit commitRepository(DTGeoserverManager geoserverManager, String repoName, String transactionId,
			String message, User loginUser) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param verbose
	 * @return
	 * @throws JAXBException
	 */
	GeogigRemoteRepository listRemoteRepository(DTGeoserverManager geoserverManager, String repoName, Boolean verbose)
			throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param remoteName
	 * @param remoteURL
	 * @param loginUser
	 * @return
	 * @throws JAXBException
	 */
	GeogigRemoteRepository addRemoteRepository(DTGeoserverManager geoserverManager, String repoName, String remoteName,
			String remoteURL, User loginUser) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param remoteName
	 * @return
	 * @throws JAXBException
	 */
	GeogigRemoteRepository removeRemoteRepository(DTGeoserverManager geoserverManager, String repoName,
			String remoteName) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param remoteName
	 * @return
	 * @throws JAXBException
	 */
	GeogigRemoteRepository pingRemoteRepository(DTGeoserverManager geoserverManager, String repoName, String remoteName)
			throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param transactionId
	 * @param remoteName
	 * @param branchName
	 * @param remoteBranchName
	 * @param user
	 * @return
	 * @throws JAXBException
	 */
	GeogigPull pullRepository(DTGeoserverManager geoserverManager, String repoName, String transactionId,
			String remoteName, String branchName, String remoteBranchName, User user) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @param remoteName
	 * @param branchName
	 * @param remoteBranchName
	 * @return
	 * @throws JAXBException
	 */
	GeogigPush pushRepository(DTGeoserverManager geoserverManager, String repoName, String remoteName,
			String branchName, String remoteBranchName) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @return GeogigFetch
	 * @throws JAXBException
	 */
	GeogigFetch fetchRepository(DTGeoserverManager geoserverManager, String repoName) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param loginUser
	 * @param repoName
	 * @param dbHost
	 * @param dbPort
	 * @param dbName
	 * @param dbSchema
	 * @param dbUser
	 * @param dbPassword
	 * @param remoteURL
	 * @param remoteName
	 * @return
	 * @throws JAXBException
	 */
	GeogigRepositoryInit initRepository(DTGeoserverManager geoserverManager, User loginUser, String repoName,
			String dbHost, String dbPort, String dbName, String dbSchema, String dbUser, String dbPassword,
			String remoteName, String remoteURL) throws JAXBException;

	/**
	 * @param geoserverManager
	 * @param repoName
	 * @return
	 * @throws JAXBException
	 */
	GeogigRepositoryDelete deleteRepository(DTGeoserverManager geoserverManager, String repoName) throws JAXBException;

}
