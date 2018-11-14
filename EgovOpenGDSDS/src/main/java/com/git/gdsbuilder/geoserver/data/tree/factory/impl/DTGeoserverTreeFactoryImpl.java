package com.git.gdsbuilder.geoserver.data.tree.factory.impl;

import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;
import com.git.gdsbuilder.geoserver.data.tree.DTGeoserverTree;
import com.git.gdsbuilder.geoserver.data.tree.DTGeoserverTree.EnTreeType;
import com.git.gdsbuilder.geoserver.data.tree.DTGeoserverTrees;
import com.git.gdsbuilder.geoserver.data.tree.factory.DTGeoserverTreeFactory;

public class DTGeoserverTreeFactoryImpl implements DTGeoserverTreeFactory {

	public DTGeoserverTree createDTGeoserverTree(DTGeoserverManagerList dtGeoManagers, EnTreeType type) {
		return new DTGeoserverTree(dtGeoManagers, type);
	}

	public DTGeoserverTree createDTGeoserverTree(DTGeoserverManagerList dtGeoManagers, String parent, String serverName,
			EnTreeType type) {
		return new DTGeoserverTree(dtGeoManagers, parent, serverName, type);
	}

	public DTGeoserverTrees createDTGeoserverTrees(DTGeoserverManagerList dtGeoManagers) {
		return new DTGeoserverTrees(dtGeoManagers);
	}
}
