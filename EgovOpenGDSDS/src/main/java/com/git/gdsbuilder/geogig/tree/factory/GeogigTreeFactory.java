/**
 * 
 */
package com.git.gdsbuilder.geogig.tree.factory;

import com.git.gdsbuilder.geogig.tree.GeogigRemoteRepositoryTree;
import com.git.gdsbuilder.geogig.tree.GeogigRemoteRepositoryTree.EnGeogigRemoteRepositoryTreeType;
import com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree;
import com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree.EnGeogigRepositoryTreeType;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;

/**
 * @author GIT
 *
 */
public interface GeogigTreeFactory {

	/**
	 * type이 Server 타입일 경우에
	 * 
	 * @param dtGeoManagers
	 * @param type
	 * @return
	 */
	public GeogigRepositoryTree createGeogigRepositoryTree(DTGeoserverManagerList dtGeoManagers,
			EnGeogigRepositoryTreeType type);

	/**
	 * Server type외
	 * 
	 * @param dtGeoserver
	 * @param serverName
	 * @param type
	 * @param parent
	 * @param transactionId
	 * @return
	 */
	public GeogigRepositoryTree createGeogigRepositoryTree(DTGeoserverManager dtGeoserver, String serverName,
			EnGeogigRepositoryTreeType type, String parent, String transactionId);

	/**
	 * @param dtGeoserver
	 * @param serverName
	 * @param type
	 * @param parent
	 * @param local
	 * @param fetch
	 * @return
	 */
	public GeogigRemoteRepositoryTree createGeogigRemoteRepositoryTree(DTGeoserverManager dtGeoserver,
			String serverName, EnGeogigRemoteRepositoryTreeType type, String parent, String local, boolean fetch);

}
