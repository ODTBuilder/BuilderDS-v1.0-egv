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

import com.git.gdsbuilder.geogig.type.GeogigFetch;
import com.git.gdsbuilder.geogig.type.GeogigPull;
import com.git.gdsbuilder.geogig.type.GeogigPush;
import com.git.gdsbuilder.geogig.type.GeogigRemoteRepository;
import com.git.gdsbuilder.geogig.type.GeogigRepositoryDelete;
import com.git.gdsbuilder.geogig.type.GeogigRepositoryInit;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.common.security.LoginUser;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.geogig.service.GeogigRepositoryService;

/**
 * @author GIT
 *
 */
@Controller
@RequestMapping("/geogig")
public class GeogigRepositoryController extends AbstractController {

	@Autowired
	@Qualifier("reposService")
	GeogigRepositoryService reposService;

	@RequestMapping(value = "/initRepository.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigRepositoryInit initRepository(HttpServletRequest request, Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "dbHost", required = false) String dbHost,
			@RequestParam(value = "dbPort", required = false) String dbPort,
			@RequestParam(value = "dbName", required = false) String dbName,
			@RequestParam(value = "dbSchema", required = false) String dbSchema,
			@RequestParam(value = "dbUser", required = false) String dbUser,
			@RequestParam(value = "dbPassword", required = false) String dbPassword,
			@RequestParam(value = "remoteName", required = false) String remoteName,
			@RequestParam(value = "remoteURL", required = false) String remoteURL) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return reposService.initRepository(geoserverManager, loginUser, repoName, dbHost, dbPort, dbName, dbSchema,
				dbUser, dbPassword, remoteName, remoteURL);
	}

	@RequestMapping(value = "/deleteRepository.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigRepositoryDelete initRepository(HttpServletRequest request,
			Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return reposService.deleteRepository(geoserverManager, repoName);
	}

	@RequestMapping(value = "/listRemoteRepository.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigRemoteRepository listRemoteRepository(HttpServletRequest request,
			Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "verbose", required = false) Boolean verbose) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return reposService.listRemoteRepository(geoserverManager, repoName, verbose);
	}

	@RequestMapping(value = "/addRemoteRepository.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigRemoteRepository addRemoteRepository(HttpServletRequest request,
			Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "remoteName", required = false) String remoteName,
			@RequestParam(value = "remoteURL", required = false) String remoteURL) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return reposService.addRemoteRepository(geoserverManager, repoName, remoteName, remoteURL, loginUser);
	}

	@RequestMapping(value = "/removeRemoteRepository.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigRemoteRepository removeRemoteRepository(HttpServletRequest request,
			Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "remoteName", required = false) String remoteName) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return reposService.removeRemoteRepository(geoserverManager, repoName, remoteName);
	}

	@RequestMapping(value = "/pingRemoteRepository.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigRemoteRepository pingRemoteRepository(HttpServletRequest request,
			Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "remoteName", required = false) String remoteName) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return reposService.pingRemoteRepository(geoserverManager, repoName, remoteName);
	}

	@RequestMapping(value = "/pullRepository.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigPull pullRepository(HttpServletRequest request, Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "branchName", required = false) String branchName,
			@RequestParam(value = "remoteName", required = false) String remoteName,
			@RequestParam(value = "remoteBranchName", required = false) String remoteBranchName,
			@RequestParam(value = "transactionId", required = false) String transactionId) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return reposService.pullRepository(geoserverManager, repoName, transactionId, remoteName, branchName,
				remoteBranchName, loginUser);
	}

	@RequestMapping(value = "/pushRepository.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigPush pushRepository(HttpServletRequest request, Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "branchName", required = false) String branchName,
			@RequestParam(value = "remoteName", required = false) String remoteName,
			@RequestParam(value = "remoteBranchName", required = false) String remoteBranchName) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return reposService.pushRepository(geoserverManager, repoName, remoteName, branchName, remoteBranchName);
	}

	@RequestMapping(value = "/fetchRepository.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigFetch fetchRepository(HttpServletRequest request, Principal principal,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName) throws JAXBException {

		LoginUser loginUser = null;
		if(principal!=null){
			loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		}
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return reposService.fetchRepository(geoserverManager, repoName);
	}

}
