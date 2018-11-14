package com.git.qaproducer.upload.service;

import com.git.gdsbuilder.fileread.FileMeta;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;

public interface SHPFileUploadService {

	public FileMeta shpFileUpload(FileMeta fileMeta, DTGeoserverManager dtGeoManager, String wsName, String dsName, String style) throws Exception, Throwable;
	
}
