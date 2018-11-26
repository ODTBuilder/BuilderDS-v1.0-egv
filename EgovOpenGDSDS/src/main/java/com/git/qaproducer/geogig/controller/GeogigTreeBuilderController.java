/**
 * 
 */
package com.git.qaproducer.geogig.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.git.gdsbuilder.geogig.tree.GeogigRemoteRepositoryTree.EnGeogigRemoteRepositoryTreeType;
import com.git.gdsbuilder.geogig.tree.GeogigRepositoryTree.EnGeogigRepositoryTreeType;
import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.geogig.service.GeogigTreeBuilderService;
import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.domain.User.EnUserType;

/**
 * @author GIT
 *
 */
@Controller
@RequestMapping("/geogig")
public class GeogigTreeBuilderController extends AbstractController {

	@Autowired
	@Qualifier("treeService")
	GeogigTreeBuilderService treeService;

	/**
	 * @param request       HttpServletRequest
	 * @param loginUser     User
	 * @param node          node ex) server, server:repository,
	 *                      server:repository:branch
	 * @param type          node type
	 * @param serverName    geoserver 이름
	 * @param transactionId geogig transactionId
	 * @return JSONArray
	 */
	@RequestMapping(value = "/getWorkingTree.ajax")
	@ResponseBody
	public JSONArray getWorkingTree(HttpServletRequest request,
			@RequestParam(value = "node", required = false) String node,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "transactionId", required = false) String transactionId) {

		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		EnGeogigRepositoryTreeType enType = null;

		if (type.equals(EnGeogigRepositoryTreeType.SERVER.getType())) {
			enType = EnGeogigRepositoryTreeType.SERVER;
		} else if (type.equals(EnGeogigRepositoryTreeType.REPOSITORY.getType())) {
			enType = EnGeogigRepositoryTreeType.REPOSITORY;
		} else if (type.equals(EnGeogigRepositoryTreeType.BRANCH.getType())) {
			enType = EnGeogigRepositoryTreeType.BRANCH;
		} else if (type.equals(EnGeogigRepositoryTreeType.LAYER.getType())) {
			enType = EnGeogigRepositoryTreeType.LAYER;
		} else {
			enType = EnGeogigRepositoryTreeType.UNKNOWN;
		}

		DTGeoserverManagerList geoserverManagers = super.getGeoserverManagersToSession(request, loginUser);
		return treeService.getWorkingTree(geoserverManagers, serverName, enType, node, transactionId);
	}

	/**
	 * @param request
	 * @param loginUser
	 * @param node
	 * @param type
	 * @param serverName
	 * @param local
	 * @return
	 */
	@RequestMapping(value = "/getRemoteRepoTree.ajax")
	@ResponseBody
	public JSONArray getRemoteRepoTree(HttpServletRequest request,
			@RequestParam(value = "node", required = false) String node,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "local", required = false) String local,
			@RequestParam(value = "fetch", required = false) boolean fetch) {

		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		
		EnGeogigRemoteRepositoryTreeType enType = null;

		if (type.equals(EnGeogigRemoteRepositoryTreeType.REMOTEREPOSITORY.getType())) {
			enType = EnGeogigRemoteRepositoryTreeType.REMOTEREPOSITORY;
		} else if (type.equals(EnGeogigRemoteRepositoryTreeType.REMOTEBRANCH.getType())) {
			enType = EnGeogigRemoteRepositoryTreeType.REMOTEBRANCH;
		} else {
			enType = EnGeogigRemoteRepositoryTreeType.UNKNOWN;
		}
		DTGeoserverManagerList geoserverManagers = super.getGeoserverManagersToSession(request, loginUser);
		return treeService.getRemoteRepoTree(geoserverManagers, serverName, enType, node, local, fetch);
	}
}
