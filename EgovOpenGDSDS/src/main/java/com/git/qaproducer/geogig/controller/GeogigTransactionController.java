/**
 * 
 */
package com.git.qaproducer.geogig.controller;

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

import com.git.gdsbuilder.geogig.type.GeogigTransaction;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.controller.AbstractController;
import com.git.qaproducer.geogig.service.GeogigTransactionService;
import com.git.qaproducer.user.domain.User;
import com.git.qaproducer.user.domain.User.EnUserType;

/**
 * @author GIT
 *
 */
@Controller
@RequestMapping("/geogig")
public class GeogigTransactionController extends AbstractController {

	@Autowired
	@Qualifier("transactionService")
	GeogigTransactionService transactionService;

	@RequestMapping(value = "/beginTransaction.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigTransaction beginTransaction(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName) throws JAXBException {

		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return transactionService.beginTransaction(geoserverManager, repoName);
	}

	@RequestMapping(value = "/endTransaction.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigTransaction endTransaction(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "transactionId", required = false) String transactionId) throws JAXBException {

		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return transactionService.endTransaction(geoserverManager, repoName, transactionId);
	}

	@RequestMapping(value = "/cancelTransaction.do", method = RequestMethod.POST)
	@ResponseBody
	public GeogigTransaction cancelTransaction(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "transactionId", required = false) String transactionId) throws JAXBException {

		User loginUser = (User) getSession(request,EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return transactionService.cancelTransaction(geoserverManager, repoName, transactionId);
	}
}
