package com.git.qaproducer.geogig.service;

import java.io.StringReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geogig.GeogigCommandException;
import com.git.gdsbuilder.geogig.command.repository.DiffRepository;
import com.git.gdsbuilder.geogig.command.repository.LogRepository;
import com.git.gdsbuilder.geogig.command.repository.feature.FeatureBlame;
import com.git.gdsbuilder.geogig.command.repository.feature.RevertFeature;
import com.git.gdsbuilder.geogig.command.transaction.BeginTransaction;
import com.git.gdsbuilder.geogig.command.transaction.EndTransaction;
import com.git.gdsbuilder.geogig.type.GeogigBlame;
import com.git.gdsbuilder.geogig.type.GeogigDiff;
import com.git.gdsbuilder.geogig.type.GeogigFeatureRevert;
import com.git.gdsbuilder.geogig.type.GeogigFeatureSimpleLog;
import com.git.gdsbuilder.geogig.type.GeogigFeatureSimpleLog.SimpleCommit;
import com.git.gdsbuilder.geogig.type.GeogigRepositoryLog;
import com.git.gdsbuilder.geogig.type.GeogigRepositoryLog.Commit;
import com.git.gdsbuilder.geogig.type.GeogigRepositoryLog.Commit.ChangeType;
import com.git.gdsbuilder.geogig.type.GeogigTransaction;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.qaproducer.common.security.LoginUser;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("featureService")
public class GeogigFeatureServiceImpl extends EgovAbstractServiceImpl implements GeogigFeatureService {

	@Override
	public GeogigDiff featureDiff(DTGeoserverManager geoserverManager, String repoName, String path, int newIndex,
			int oldIndex) throws JAXBException {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		String oldTreeish = "HEAD";
		String newTreeish = "HEAD";

//		if (newIndex > 0) {
//			newTreeish += "~" + newIndex;
//		}
		oldTreeish += "~" + oldIndex;

		DiffRepository diffRepos = new DiffRepository();
		GeogigDiff geogigDiff = null;
		try {
			geogigDiff = diffRepos.executeCommand(url, user, pw, repoName, oldTreeish, newTreeish, path, null);
		} catch (GeogigCommandException e) {
			JAXBContext jaxbContext = JAXBContext.newInstance(GeogigDiff.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			geogigDiff = (GeogigDiff) unmarshaller.unmarshal(new StringReader(e.getMessage()));
		}
		return geogigDiff;
	}

	@Override
	public GeogigBlame featureBlame(DTGeoserverManager geoserverManager, String repoName, String path, String branch)
			throws JAXBException {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		FeatureBlame featureBlame = new FeatureBlame();
		GeogigBlame geogigBlame = null;
		try {
			geogigBlame = featureBlame.executeCommand(url, user, pw, repoName, path, branch);
		} catch (Exception e) {
			JAXBContext jaxbContext = JAXBContext.newInstance(GeogigBlame.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			geogigBlame = (GeogigBlame) unmarshaller.unmarshal(new StringReader(e.getMessage()));
		}
		return geogigBlame;
	}

	@Override
	public GeogigFeatureSimpleLog featureLog(DTGeoserverManager geoserverManager, String repoName, String path,
			int limit, String until, String head, int index) throws JAXBException {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		LogRepository logRepos = new LogRepository();
		GeogigFeatureSimpleLog simpleLog = new GeogigFeatureSimpleLog();
		try {
			List<SimpleCommit> simpleCommits = new ArrayList<>();
			List<Commit> commits = new ArrayList<>();
			GeogigRepositoryLog geogigLog = logRepos.executeCommand(url, user, pw, repoName, path,
					String.valueOf(limit), until, true);
			simpleLog.setSuccess(geogigLog.getSuccess());
			commits.addAll(geogigLog.getCommits());

			int commitSize = commits.size();
			for (int i = 0; i < commitSize; i++) {
				Commit newCommit = commits.get(i); // current
				SimpleCommit simpleCommit = new SimpleCommit();
				simpleCommit.setcIdx(i + index); // idx
				String commitId = newCommit.getCommitId(); // commit id
				simpleCommit.setCommitId(commitId);
				simpleCommit.setAuthorName(newCommit.getAuthor().getName()); // author
				simpleCommit.setMessage(newCommit.getMessage()); // message
				Timestamp timestamp = new Timestamp(Long.parseLong(newCommit.getAuthor().getTimestamp())); // time
				Date date = new Date(timestamp.getTime());
				DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = dateformat.format(date);

				ChangeType changeType = ChangeType.ADDED;
				int addedCount = Integer.parseInt(newCommit.getAdds());
				if (addedCount > 0) {
					changeType = ChangeType.ADDED;
				}
				int removedCount = Integer.parseInt(newCommit.getRemoves());
				if (removedCount > 0) {
					changeType = ChangeType.REMOVED;
				}
				int modifiedCount = Integer.parseInt(newCommit.getModifies());
				if (modifiedCount > 0) {
					changeType = ChangeType.MODIFIED;
				}
				simpleCommit.setChangeType(changeType);
				simpleCommit.setDate(dateStr);
				simpleCommits.add(simpleCommit);
			}
			simpleLog.setSimpleCommits(simpleCommits);
		} catch (GeogigCommandException e) {
			GeogigRepositoryLog geogigLog = null;
			if (e.isXml()) {
				JAXBContext jaxbContext = JAXBContext.newInstance(GeogigRepositoryLog.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				geogigLog = (GeogigRepositoryLog) unmarshaller.unmarshal(new StringReader(e.getMessage()));
				simpleLog.setSuccess(geogigLog.getSuccess());
				simpleLog.setError(geogigLog.getError());
			} else {
				simpleLog.setError(e.getMessage());
				simpleLog.setSuccess("false");
			}
		}

		return simpleLog;
	}

	@Override
	public GeogigFeatureRevert featureRevert(DTGeoserverManager geoserverManager, String repoName, String path,
			String oldCommitId, String newCommitId, String commitMessage, String mergeMessage, LoginUser loginUser)
			throws JAXBException {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		String authorName = loginUser.getUsername();
		String authorEmail = loginUser.getEmail();

		RevertFeature revertFeature = new RevertFeature();
		GeogigFeatureRevert geogigFeatureRevert = null;
		try {
			BeginTransaction beginTransaction = new BeginTransaction();
			GeogigTransaction transaction = beginTransaction.executeCommand(url, user, pw, repoName);
			String transactionId = transaction.getTransaction().getId();
			geogigFeatureRevert = revertFeature.executeCommand(url, user, pw, repoName, transactionId, oldCommitId,
					newCommitId, path, commitMessage, mergeMessage, authorName, authorEmail);
			if (geogigFeatureRevert.getMerge().getConflicts() != null) {
				geogigFeatureRevert.setTransactionId(transactionId);
			} else {
				EndTransaction endTransaction = new EndTransaction();
				endTransaction.executeCommand(url, user, pw, repoName, transactionId);
			}
		} catch (GeogigCommandException e) {
			JAXBContext jaxbContext = JAXBContext.newInstance(GeogigFeatureRevert.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			geogigFeatureRevert = (GeogigFeatureRevert) unmarshaller.unmarshal(new StringReader(e.getMessage()));
		}
		return geogigFeatureRevert;
	}

}