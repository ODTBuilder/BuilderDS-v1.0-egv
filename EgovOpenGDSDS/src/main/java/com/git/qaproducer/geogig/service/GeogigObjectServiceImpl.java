/**
 * 
 */
package com.git.qaproducer.geogig.service;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import com.git.gdsbuilder.geogig.GeogigCommandException;
import com.git.gdsbuilder.geogig.command.object.CatObject;
import com.git.gdsbuilder.geogig.type.GeogigCat;
import com.git.gdsbuilder.geogig.type.GeogigCat.Attribute;
import com.git.gdsbuilder.geogig.type.GeogigCat.Feature;
import com.git.gdsbuilder.geogig.type.GeogigCat.Subtree;
import com.git.gdsbuilder.geogig.type.GeogigFeatureAttribute;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @author GIT
 *
 */
@Service("objectService")
public class GeogigObjectServiceImpl extends EgovAbstractServiceImpl implements GeogigObjectService {

	@Override
	public GeogigCat catObject(DTGeoserverManager geoserverManager, String repoName, String objectid)
			throws JAXBException {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		CatObject catObj = new CatObject();
		GeogigCat cat = null;
		try {
			cat = catObj.executeCommand(url, user, pw, repoName, objectid);
		} catch (GeogigCommandException e) {
			JAXBContext jaxbContext = JAXBContext.newInstance(GeogigCat.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			cat = (GeogigCat) unmarshaller.unmarshal(new StringReader(e.getMessage()));
		}
		return cat;
	}

	@Override
	public GeogigFeatureAttribute catFeatureObject(DTGeoserverManager geoserverManager, String repoName, String path,
			String commitId, String featureId) throws JAXBException {

		String url = geoserverManager.getRestURL();
		String user = geoserverManager.getUsername();
		String pw = geoserverManager.getPassword();

		String[] ids = path.split("/");
		String layerName = ids[0];
		String featureName = ids[1];
		GeogigFeatureAttribute featureAtt = null;
		try {
			CatObject catObj = new CatObject();
			List<Attribute> attrTypes = null;
			GeogigCat catOurs = catObj.executeCommand(url, user, pw, repoName, commitId);
			String treeId = catOurs.getCommit().getTree();
			GeogigCat catTree = catObj.executeCommand(url, user, pw, repoName, treeId);
			List<Subtree> subTrees = catTree.getTree().getSubtree();
			for (Subtree subTree : subTrees) {
				if (subTree.getName().equals(layerName)) {
					String metaDataObjId = subTree.getMetadataid();
					GeogigCat catFeatureType = catObj.executeCommand(url, user, pw, repoName, metaDataObjId);
					attrTypes = catFeatureType.getFeaturetype().getAttribute();
				}
			}
			if (attrTypes != null) {
				GeogigCat catFeature = catObj.executeCommand(url, user, pw, repoName, featureId);
				Feature feature = catFeature.getFeature();
				List<Attribute> attrValues = feature.getAttribute();
				for (int i = 0; i < attrTypes.size(); i++) {
					Attribute type = attrTypes.get(i);
					type.setValue(attrValues.get(i).getValue());
				}
				featureAtt = new GeogigFeatureAttribute();
				featureAtt.setLayerName(layerName);
				featureAtt.setFeatureId(featureName);
				featureAtt.setAttributes(attrTypes);
				featureAtt.setSuccess("true");
			}
		} catch (GeogigCommandException e) {
			JAXBContext jaxbContext = JAXBContext.newInstance(GeogigFeatureAttribute.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			featureAtt = (GeogigFeatureAttribute) unmarshaller.unmarshal(new StringReader(e.getMessage()));
		}
		return featureAtt;
	}
}
