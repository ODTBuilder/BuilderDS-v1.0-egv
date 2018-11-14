package com.git.gdsbuilder.geoserver.factory;

import java.net.MalformedURLException;

import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.gdsbuilder.geoserver.DTGeoserverPublisher;
import com.git.gdsbuilder.geoserver.DTGeoserverReader;

public interface DTGeoserverFactory {
	DTGeoserverManager createDTGeoserverManager(String restURL, String userName, String password) throws MalformedURLException;
	
	DTGeoserverPublisher createDTGeoserverPublisher(String restURL, String userName, String password) throws MalformedURLException;
	
	DTGeoserverReader createDTGeoserverReader(String restURL, String userName, String password) throws MalformedURLException;
}
