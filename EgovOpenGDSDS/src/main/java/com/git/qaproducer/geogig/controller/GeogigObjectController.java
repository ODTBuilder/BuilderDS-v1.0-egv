/**
 * 
 */
package com.git.qaproducer.geogig.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.gdsbuilder.geogig.type.GeogigCat;
import com.git.gdsbuilder.geogig.type.GeogigFeatureAttribute;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.common.security.LoginUser;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.geogig.service.GeogigObjectService;

/**
 * @author GIT
 *
 */
@Controller
@RequestMapping("/geogig")
public class GeogigObjectController extends AbstractController {

	@Autowired
	@Qualifier("objectService")
	GeogigObjectService objectService;

	@RequestMapping(value = "/catObject.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigCat catObject(HttpServletRequest request, Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "objectid", required = false) String objectid) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return objectService.catObject(geoserverManager, repoName, objectid);
	}

//	@RequestMapping(value = "/catFeatureObject.do", method = RequestMethod.POST)
	@RequestMapping(value = "/catConflictFeatureObject.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigFeatureAttribute catConflictFeatureObject(HttpServletRequest request,
			Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "commitId", required = false) String commitId,
			@RequestParam(value = "featureId", required = false) String featureId) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return objectService.catConflictFeatureObject(geoserverManager, repoName, path, commitId, featureId);
	}

	@RequestMapping(value = "/catFeatureObject.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigFeatureAttribute catFeatureObject(HttpServletRequest request,
			Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "commitId", required = false) String commitId,
			@RequestParam(value = "path", required = false) String path) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return objectService.catFeatureObject(geoserverManager, repoName, path, commitId);
	}
}
