/**
 * 
 */
package com.git.gdsbuilder.geogig.command.repository;

import java.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.git.gdsbuilder.geogig.GeogigCommandException;
import com.git.gdsbuilder.geogig.type.GeogigPull;

/**
 * Geogig Repository Pull Command Execution Class
 * 
 * @author GIT
 *
 */
public class PullRepository {

	private static final Log logger = LogFactory.getLog(PullRepository.class);

	private static final String geogig = "geogig";
	private static final String command = "pull";
	private static final String param_transactionId = "transactionId=";
	private static final String param_remoteName = "remoteName=";
	private static final String param_ref = "ref=";
	private static final String param_authorName = "authorName="; // optional
	private static final String param_authorEmail = "authorEmail="; // optional

	public GeogigPull executeCommand(String baseURL, String username, String password, String repository,
			String transactionId, String remoteName, String branchName, String remoteBranchName, String authorName,
			String authorEmail) {

		// restTemplate
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(5000);
		factory.setConnectTimeout(3000);
		CloseableHttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(100).setMaxConnPerRoute(5).build();
		factory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(factory);

		// header
		HttpHeaders headers = new HttpHeaders();
		String user = username + ":" + password;
		String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(user.getBytes());
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.add("Authorization", encodedAuth);

		// url
		String url = baseURL + "/" + geogig + "/repos/" + repository + "/" + command + "?" + param_transactionId
				+ transactionId + "&" + param_remoteName + remoteName + "&" + param_ref + branchName + ":"
				+ remoteBranchName;

		if (authorName != null) {
			url += "&" + param_authorName + authorName;
		}
		if (authorEmail != null) {
			url += "&" + param_authorEmail + authorEmail;
		}

		// request
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<GeogigPull> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, GeogigPull.class);
		} catch (HttpClientErrorException e) {
			throw new GeogigCommandException(e.getResponseBodyAsString(), e.getStatusCode());
		} catch (HttpServerErrorException e) {
			throw new GeogigCommandException(e.getResponseBodyAsString(), e.getStatusCode());
		}
		return responseEntity.getBody();
	}

}
