/**
 * 
 */
package com.git.gdsbuilder.geogig.tree.factory.impl;

import com.git.gdsbuilder.geogig.tree.GeogigRemoteRepositoryTree;
import com.git.gdsbuilder.geogig.tree.GeogigRemoteRepositoryTree.EnGeogigRemoteRepositoryTreeType;
import com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree;
import com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree.EnGeogigRepositoryTreeType;
import com.git.gdsbuilder.geogig.tree.factory.GeogigTreeFactory;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;

/**
 * @author GIT
 *
 */
public class GeogigTreeFactoryImpl implements GeogigTreeFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.git.gdsbuilder.geogig.tree.factory.GeogigTreeFactory#
	 * createGeogigRepositoryTree(com.git.gdsbuilder.geoserver.data.
	 * DTGeoserverManagerList,
	 * com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree.
	 * EnGeogigRepositoryTreeType)
	 */
	public GeogigRepositoryTree createGeogigRepositoryTree(DTGeoserverManagerList dtGeoManagers,
			EnGeogigRepositoryTreeType type) {
		return new GeogigRepositoryTree(dtGeoManagers, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.git.gdsbuilder.geogig.tree.factory.GeogigTreeFactory#
	 * createGeogigRepositoryTree(com.git.gdsbuilder.geoserver.
	 * DTGeoserverManager, java.lang.String,
	 * com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree.
	 * EnGeogigRepositoryTreeType, java.lang.String, java.lang.String)
	 */
	public GeogigRepositoryTree createGeogigRepositoryTree(DTGeoserverManager dtGeoserver, String serverName,
			EnGeogigRepositoryTreeType type, String parent, String transactionId) {
		return new GeogigRepositoryTree(dtGeoserver, serverName, type, parent, transactionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.git.gdsbuilder.geogig.tree.factory.GeogigTreeFactory#
	 * createGeogigRemoteRepositoryTree(com.git.gdsbuilder.geoserver.
	 * DTGeoserverManager, java.lang.String,
	 * com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree.
	 * EnGeogigRepositoryTreeType, java.lang.String)
	 */
	@Override
	public GeogigRemoteRepositoryTree createGeogigRemoteRepositoryTree(DTGeoserverManager dtGeoserver,
			String serverName, EnGeogigRemoteRepositoryTreeType type, String parent, String local, boolean fetch) {
		// TODO Auto-generated method stub
		return new GeogigRemoteRepositoryTree(dtGeoserver, serverName, type, parent, local, fetch);
	}

}
