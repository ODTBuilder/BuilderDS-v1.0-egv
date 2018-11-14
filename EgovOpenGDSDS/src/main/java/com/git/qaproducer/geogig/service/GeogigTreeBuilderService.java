/**
 * 
 */
package com.git.qaproducer.geogig.service;

import com.git.gdsbuilder.geogig.tree.GeogigRemoteRepositoryTree;
import com.git.gdsbuilder.geogig.tree.GeogigRemoteRepositoryTree.EnGeogigRemoteRepositoryTreeType;
import com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree;
import com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree.EnGeogigRepositoryTreeType;
import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;

/**
 * @author GIT
 *
 */
public interface GeogigTreeBuilderService {

	/**
	 * @param dtGeoservers  DTGeoserverManagerList
	 * @param serverName    geoserver 이름
	 * @param type          node type
	 * @param node          node ex) server, server:repository,
	 *                      server:repository:branch
	 * @param transactionId geogig transactionId
	 * @return GeogigRepositoryTree
	 */
	public GeogigRepositoryTree getWorkingTree(DTGeoserverManagerList dtGeoservers, String serverName,
			EnGeogigRepositoryTreeType type, String node, String transactionId);

	/**
	 * @param dtGeoservers DTGeoserverManagerList
	 * @param serverName   geoserver 이름
	 * @param type         node type
	 * @param node         node ex) server, server:repository,
	 *                     server:repository:branch
	 * @param fetch
	 * @return GeogigRemoteRepositoryTree
	 */
	public GeogigRemoteRepositoryTree getRemoteRepoTree(DTGeoserverManagerList dtGeoservers, String serverName,
			EnGeogigRemoteRepositoryTreeType type, String node, String local, boolean fetch);
}
