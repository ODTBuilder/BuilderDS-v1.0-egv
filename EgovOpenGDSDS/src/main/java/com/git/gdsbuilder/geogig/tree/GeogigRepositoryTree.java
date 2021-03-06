/**
 * 
 */
package com.git.gdsbuilder.geogig.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.gdsbuilder.geogig.GeogigCommandException;
import com.git.gdsbuilder.geogig.command.repository.ConfigRepository;
import com.git.gdsbuilder.geogig.command.repository.ListRepository;
import com.git.gdsbuilder.geogig.command.repository.LsTreeRepository;
import com.git.gdsbuilder.geogig.command.repository.StatusRepository;
import com.git.gdsbuilder.geogig.command.repository.branch.ListBranch;
import com.git.gdsbuilder.geogig.type.GeogigBranch;
import com.git.gdsbuilder.geogig.type.GeogigBranch.Branch;
import com.git.gdsbuilder.geogig.type.GeogigConfig;
import com.git.gdsbuilder.geogig.type.GeogigConfig.Config;
import com.git.gdsbuilder.geogig.type.GeogigRepository;
import com.git.gdsbuilder.geogig.type.GeogigRepository.Repo;
import com.git.gdsbuilder.geogig.type.GeogigRevisionTree;
import com.git.gdsbuilder.geogig.type.GeogigRevisionTree.Node;
import com.git.gdsbuilder.geogig.type.GeogigStatus;
import com.git.gdsbuilder.geogig.type.GeogigStatus.Header;
import com.git.gdsbuilder.geoserver.DTGeoserverManager;
import com.git.gdsbuilder.geoserver.DTGeoserverReader;
import com.git.gdsbuilder.geoserver.data.DTGeoserverManagerList;

import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTDataStoreList;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList;

/**
 * @author GIT
 *
 */
@SuppressWarnings("serial")
public class GeogigRepositoryTree extends JSONArray {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public enum EnGeogigRepositoryTreeType {
		SERVER("server"), REPOSITORY("repository"), BRANCH("branch"), LAYER("layer"), UNKNOWN(null);

		String type;

		private EnGeogigRepositoryTreeType(String type) {
			this.type = type;
		}

		public static EnGeogigRepositoryTreeType getFromType(String type) {
			for (EnGeogigRepositoryTreeType tt : values()) {
				if (tt == UNKNOWN)
					continue;
				if (tt.type.equals(type))
					return tt;
			}
			return UNKNOWN;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	/**
	 * type이 EnTreeType.SERVER 일경우에
	 * 
	 * @param dtGeoManagers
	 * @param type
	 */
	public GeogigRepositoryTree(DTGeoserverManagerList dtGeoManagers, EnGeogigRepositoryTreeType type) {
		if (type == EnGeogigRepositoryTreeType.SERVER) {
			build(dtGeoManagers, type);
		} else {
			logger.error("TreeType이 Server가 아닙니다.");
		}
	}

	public GeogigRepositoryTree(DTGeoserverManager dtGeoserver, String serverName, EnGeogigRepositoryTreeType type,
			String parent, String transactionId) {
		this.build(dtGeoserver, serverName, type, parent, transactionId);
	}

	@SuppressWarnings("unchecked")
	public GeogigRepositoryTree build(DTGeoserverManagerList dtGeoserverMList, EnGeogigRepositoryTreeType type) {
		if (dtGeoserverMList != null && type != null) {
			Iterator<String> keys = dtGeoserverMList.keySet().iterator();
			if (type == EnGeogigRepositoryTreeType.SERVER) {
				while (keys.hasNext()) {
					String server = (String) keys.next();
					DTGeoserverManager dtGeoManager = dtGeoserverMList.get(server);

					if (dtGeoManager != null) {
						DTGeoserverReader dtGeoserverReader = dtGeoManager.getReader();
						if (dtGeoserverReader != null) {
							ListRepository listRepos = new ListRepository();
							try {
								GeogigRepository geogigRepo = listRepos.executeCommand(dtGeoManager.getRestURL(),
										dtGeoManager.getUid(), dtGeoManager.getPassword());
								List<Repo> repos = geogigRepo.getRepos();
								if (repos == null) {
									this.addServer(server, false);
								} else {
									if (repos.size() > 0) {
										this.addServer(server, true);
									} else {
										this.addServer(server, false);
									}
								}
							} catch (GeogigCommandException e) {
								this.addServer(server, false);
							}
						}
					}
				}
				if (dtGeoserverMList.size() == 0) {
					JSONObject errorJSON = new JSONObject();
					errorJSON.put("id", 500);
					errorJSON.put("parent", "#");
					errorJSON.put("text", "Geoserver를 추가해주세요");
					errorJSON.put("type", "error");

					this.add(errorJSON);
					logger.warn("Geoserver를 다시 추가해주세요");
				}
			}
		} else {
			JSONObject errorJSON = new JSONObject();
			errorJSON.put("id", 500);
			errorJSON.put("parent", "#");
			errorJSON.put("text", "Geoserver를 다시 추가해주세요");
			errorJSON.put("type", "error");

			this.add(errorJSON);
			logger.warn("Geoserver를 다시 추가해주세요");
		}
		return this;
	}

	public GeogigRepositoryTree build(DTGeoserverManager dtGeoserver, String serverName,
			EnGeogigRepositoryTreeType type, String parent, String transactionId) {
		String[] param = parent.split(":");// ex)
											// serverName_repository_brench_layer

		if (param != null && dtGeoserver != null) {
			String baseURL = dtGeoserver.getRestURL();
			String username = dtGeoserver.getUid();
			String password = dtGeoserver.getPassword();

			if (type == EnGeogigRepositoryTreeType.REPOSITORY) {
				if (param.length > 0) {
					String server = param[0];
					// repository
					ListRepository listRepos = new ListRepository();
					GeogigRepository geogigRepo = listRepos.executeCommand(baseURL, username, password);
					List<Repo> repos = geogigRepo.getRepos();
					for (Repo repo : repos) {
						String name = repo.getName();
						String storageType = null;
						// repos type
						ConfigRepository configRepos = new ConfigRepository();
						GeogigConfig geogigConfig = configRepos.executeCommand(baseURL, username, password, name, null);
						List<Config> configs = geogigConfig.getConfigs();
						for (Config config : configs) {
							if (config.getName().equals("storage.refs")) {
								storageType = config.getValue();
							}
						}
						String reposId = server + ":" + name;
						ListBranch listBranch = new ListBranch();
						GeogigBranch branches = listBranch.executeCommand(baseURL, username, password, name, false);
						List<Branch> localList = branches.getLocalBranchList();

						if (localList != null) {
							if (localList.size() > 0) {
								this.addRepo(parent, reposId, name, storageType, true);
							} else {
								this.addRepo(parent, reposId, name, storageType, false);
							}
						} else {
							this.addRepo(parent, reposId, name, storageType, false);
						}
					}
				}
			} else if (type == EnGeogigRepositoryTreeType.BRANCH) {
				if (param.length > 1) {
					String repository = param[1];
					ListBranch listBranch = new ListBranch();
					GeogigBranch branches = listBranch.executeCommand(baseURL, username, password, repository, false);
					List<Branch> localList = branches.getLocalBranchList();
					for (Branch localBranch : localList) {
						String branchName = localBranch.getName();

						boolean geoserver = false;
						// geoserver 저장소 존재 여부 확인
						DTGeoserverReader dtGeoserverReader = dtGeoserver.getReader();
						RESTWorkspaceList restWorkspaceList = dtGeoserverReader.getWorkspaces();
						if (restWorkspaceList != null) {
							for (RESTWorkspaceList.RESTShortWorkspace item : restWorkspaceList) {
								String wsName = item.getName();
								RESTDataStoreList dataStoreList = dtGeoserverReader.getDatastores(wsName);
								if (dataStoreList != null) {
									List<String> dsNames = dataStoreList.getNames();
									for (String dsName : dsNames) {
										RESTDataStore dStore = dtGeoserverReader.getDatastore(wsName, dsName);
										if (dStore != null) {
											String storeType = dStore.getStoreType();
											if (storeType.equals("GeoGIG")) {
												Map<String, String> connetParams = dStore.getConnectionParameters();
												String geogigRepos = connetParams.get("geogig_repository");
												String reposName = geogigRepos.replace("geoserver://", "");
												String reposBranch = connetParams.get("branch");
												if (repository.equals(reposName)
														&& branchName.equalsIgnoreCase(reposBranch)) {
													geoserver = true;
												}
											}
										}
									}
								}
							}
						}
						String branchId = parent + ":" + branchName;
						boolean children = false;
						LsTreeRepository lsTree = new LsTreeRepository();
						GeogigRevisionTree revisionTree = lsTree.executeCommand(baseURL, username, password, repository,
								branchName, false);
						List<Node> nodes = revisionTree.getNodes();
						if (nodes != null) {
							if (nodes.size() > 0) {
								children = true;
							}
						}
						if (transactionId != null) {
							StatusRepository stausCommand = new StatusRepository();
							GeogigStatus status = stausCommand.executeCommand(baseURL, username, password, repository,
									transactionId);
							Header header = status.getHeader();
							String headerBranch = header.getBranch();
							if (branchName.equalsIgnoreCase(headerBranch)) {
								if (status.getUnstaged() != null) {
									this.addBranch(parent, branchId, branchName, "Unstaged", children, geoserver);
								} else if (status.getStaged() != null) {
									this.addBranch(parent, branchId, branchName, "Staged", children, geoserver);
								} else if (status.getUnmerged() != null) {
									this.addBranch(parent, branchId, branchName, "UnMerged", children, geoserver);
								} else {
									this.addBranch(parent, branchId, branchName, "Merged", children, geoserver);
								}
							} else {
								this.addBranch(parent, branchId, branchName, null, children, geoserver);
							}
						} else {
							this.addBranch(parent, branchId, branchName, null, children, geoserver);
						}
					}
				}
			} else if (type == EnGeogigRepositoryTreeType.LAYER) {
				if (param.length > 2) {
					String repository = param[1];
					String branch = param[2];

					// branch ls-tree : default master
					LsTreeRepository lsTree = new LsTreeRepository();
					GeogigRevisionTree revisionTree = lsTree.executeCommand(baseURL, username, password, repository,
							branch, false);
					List<Node> nodes = revisionTree.getNodes();
					for (Node node : nodes) {
						String path = node.getPath();
						String pathId = parent + ":" + path;
						this.addTree(parent, pathId, path);
					}
				}
			} else {
				JSONObject errorJSON = new JSONObject();
				errorJSON.put("id", 500);
				errorJSON.put("parent", "#");
				errorJSON.put("text", "요청이 잘못되었습니다.");
				errorJSON.put("type", "error");

				this.add(errorJSON);
				logger.warn("요청이 잘못되었습니다.");
			}
		} else {
			JSONObject errorJSON = new JSONObject();
			errorJSON.put("id", 500);
			errorJSON.put("parent", "#");
			errorJSON.put("text", "요청이 잘못되었습니다.");
			errorJSON.put("type", "error");

			this.add(errorJSON);
			logger.warn("요청이 잘못되었습니다.");
		}
		return this;
	}

	/**
	 * @param server
	 * @param serverName
	 */
	private void addServer(String serverName, boolean children) {
		JSONObject geoserver = new JSONObject(); // baseURL
		geoserver.put("id", serverName);
		geoserver.put("parent", "#");
		geoserver.put("text", serverName);
		geoserver.put("type", "geoserver");
		geoserver.put("children", children);
		super.add(geoserver);
	}

	/**
	 * @param parent
	 * @param text
	 */
	private void addTree(String parent, String id, String text) {
		JSONObject repoJson = new JSONObject();
		repoJson.put("parent", parent);
		repoJson.put("id", id);
		repoJson.put("text", text);
		repoJson.put("type", "layer");
		repoJson.put("children", false);
		super.add(repoJson);
	}

	/**
	 * @param parent
	 * @param text
	 * @param type
	 */
	private void addRepo(String parent, String id, String text, String type, boolean children) {
		JSONObject repoJson = new JSONObject();
		repoJson.put("parent", parent);
		repoJson.put("id", id);
		repoJson.put("text", text);
		repoJson.put("repoType", type);
		repoJson.put("type", "repository");
		repoJson.put("children", children);
		super.add(repoJson);
	}

	/**
	 * @param parent
	 * @param text
	 * @param status
	 */
	private void addBranch(String parent, String id, String text, String status, boolean children, boolean geoserver) {
		JSONObject branchJson = new JSONObject();
		branchJson.put("parent", parent);
		branchJson.put("id", id);
		branchJson.put("text", text);
		branchJson.put("status", status);
		branchJson.put("type", "branch");
		branchJson.put("children", children);
		branchJson.put("geoserver", geoserver);
		super.add(branchJson);
	}
}
