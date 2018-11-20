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

import com.git.gdsbuilder.geogig.type.GeogigBlame;
import com.git.gdsbuilder.geogig.type.GeogigDiff;
import com.git.gdsbuilder.geogig.type.GeogigFeatureRevert;
import com.git.gdsbuilder.geogig.type.GeogigFeatureSimpleLog;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.common.security.LoginUser;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.geogig.service.GeogigFeatureService;

/**
 * @author GIT
 *
 */
@Controller
@RequestMapping("/geogig")
public class GeogigFeatureController extends AbstractController {

	@Autowired
	@Qualifier("featureService")
	GeogigFeatureService featureService;

	@RequestMapping(value = "/featureBlame.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigBlame featureBlame(HttpServletRequest request, Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "branch", required = false) String branch) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return featureService.featureBlame(geoserverManager, repoName, path, branch);
	}

	@RequestMapping(value = "/featureLog.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigFeatureSimpleLog featureLog(HttpServletRequest request, Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "until", required = false) String until,
			@RequestParam(value = "limit", required = false) int limit,
			@RequestParam(value = "head", required = false) String head,
			@RequestParam(value = "index", required = false) int index) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return featureService.featureLog(geoserverManager, repoName, path, limit, until, head, index);
	}

	@RequestMapping(value = "/featureDiff.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigDiff featureDiff(HttpServletRequest request, Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "newIndex", required = false) int newIndex,
			@RequestParam(value = "oldIndex", required = false) int oldIndex) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return featureService.featureDiff(geoserverManager, repoName, path, newIndex, oldIndex);
	}

	@RequestMapping(value = "/featureRevert.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigFeatureRevert featureRevert(HttpServletRequest request, Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "path", required = false) String path,
			@RequestParam(value = "oldCommitId", required = false) String oldCommitId,
			@RequestParam(value = "newCommitId", required = false) String newCommitId,
			@RequestParam(value = "commitMessage", required = false) String commitMessage,
			@RequestParam(value = "mergeMessage", required = false) String mergeMessage) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return featureService.featureRevert(geoserverManager, repoName, path, oldCommitId, newCommitId, commitMessage,
				mergeMessage, loginUser);
	}
}
