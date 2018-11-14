package com.git.gdsbuilder.geoserver.factory.impl;

import java.net.MalformedURLException;

import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.gdsbuilder.geoserver.DTGeoserverPublisher;
import com.git.gdsbuilder.geoserver.DTGeoserverReader;
import com.git.gdsbuilder.geoserver.factory.DTGeoserverFactory;

public class DTGeoserverFactoryImpl implements DTGeoserverFactory {

	public DTGeoserverManager createDTGeoserverManager(String restURL, String userName, String password) throws MalformedURLException{
		return new DTGeoserverManager(restURL, userName, password);
	};
	
	public DTGeoserverPublisher createDTGeoserverPublisher(String restURL, String userName, String password) throws MalformedURLException{
		return new DTGeoserverPublisher(restURL, userName, password);
	};
	
	public DTGeoserverReader createDTGeoserverReader(String restURL, String userName, String password) throws MalformedURLException{
		return new DTGeoserverReader(restURL, userName, password);
	};
	
}
