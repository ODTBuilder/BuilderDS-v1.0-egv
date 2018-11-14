package com.git.gdsbuilder.geoserver.data.tree.factory;

import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;
import com.git.gdsbuilder.geoserver.data.tree.DTGeoserverTree;
import com.git.gdsbuilder.geoserver.data.tree.DTGeoserverTree.EnTreeType;
import com.git.gdsbuilder.geoserver.data.tree.DTGeoserverTrees;

public interface DTGeoserverTreeFactory {
	
	DTGeoserverTree createDTGeoserverTree(DTGeoserverManagerList dtGeoManagers, EnTreeType type);
	DTGeoserverTree createDTGeoserverTree(DTGeoserverManagerList dtGeoManagers, String parent, String serverName,
			EnTreeType type);
	DTGeoserverTrees createDTGeoserverTrees(DTGeoserverManagerList dtGeoserverList);
}
