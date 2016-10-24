package NCR.APIHandler;

import java.util.ArrayList;
import java.util.Date;

import BISTel.PeakPerformance.Common.API_LIST;
import BISTel.PeakPerformance.Common.NCRCommonFunction;
import BISTel.PeakPerformance.Common.NCRDefine;
import BISTel.PeakPerformance.Common.NCRTaskProgress;
import BISTel.PeakPerformance.Common.ReplyQueueWF;
import BISTel.PeakPerformance.Common.WaitObjectWF;
import BISTel.PeakPerformance.Common.Transaction.FolderInfoDBHandler;
import BISTel.PeakPerformance.Common.Transaction.RecipeDBHandler;
import BISTel.PeakPerformance.DAS2.DBHandler.DAS2;
import BISTel.PeakPerformance.DataAccessService.DataService;
import BISTel.PeakPerformance.FMAgent.util.StringUtil;
import BISTel.PeakPerformance.NCR.AbstractNCRAPIHandler;
import BISTel.PeakPerformance.NCR.NCRUtility;
import BISTel.PeakPerformance.NCR.Json.MsgData;
import BISTel.PeakPerformance.NCR.Json.MsgHeader;
import BISTel.PeakPerformance.NCR.Json.MsgResult;
import BISTel.PeakPerformance.NCR.Json.NCRMessage;
import BISTel.PeakPerformance.NCR.Json.Data.RecipeData;
import BISTel.PeakPerformance.NCR.Json.Data.TaskProgress;
import BISTel.PeakPerformance.NCR.Json.Data.Obj.FolderInfo;
import BISTel.PeakPerformance.NCR.Json.Data.Obj.RecipeFile;
import BISTel.PeakPerformance.NCR.Json.Data.Obj.RecipeInfo;
import BISTel.PeakPerformance.NCR.Json.Data.Obj.RecipeRevision;
import BISTel.PeakPerformance.NCR.Json.Data.Obj.RecipeUnit;
import BISTel.PeakPerformance.Utility.StringHandler;
import BISTel.PeakPerformance.Utility.Logger.LogExecuter;

public class NCRRecipeUnit extends AbstractNCRAPIHandler {

	public NCRRecipeUnit(String clientId, String loggerName, String das2Name, Integer taskId) {
		super(clientId, loggerName, das2Name, taskId);
	}

	@Override
	public void onCallAPI(NCRMessage ncrMessage) {

		String command = ncrMessage.getHeader().getMsg_command();
		String transactionId = ncrMessage.getHeader().getTransaction_id();

		LOG.writeLog(transactionId, "onCallAPI Start : ", command, LogExecuter.RUN);

		NCRUtility utility = new NCRUtility(clientId, loggerName, das2Name);

		try {

			switch (API_LIST.Element(command)) {
				case STARTSENDRECIPE:
					this.saveRecipe(ncrMessage);
					break;
				case GETRECIPELIST:
					this.getRecipeList(ncrMessage);
					break;
				case STARTGETRECIPE:
					this.getRecipe(ncrMessage);
					break;
				case GETRECIPEREVISIONTREE:
					this.getRecipeRevisionTree(ncrMessage);
					break;
				case GETRECIPESTATUS:
					this.getRecipeStatus(ncrMessage);
					break;
				case SENDRECIPESTATUS:
					this.sendRecipeStatus(ncrMessage);
					break;
				default:
					ncrMessage.setData(null);
					utility.setErrorCodeDescription(1003, "", ncrMessage);
					this.sendMessage(ncrMessage);
					break;
			}

		} catch (Exception e) {
			//ncrMessage.setData(null);
			//utility.setErrorCodeDescription(8901, e.getMessage(), ncrMessage);
			LOG.writeErrorLog(e);
			//this.sendMessage(ncrMessage);
			this.sendMessage(NCRCommonFunction.makeExceptionMessge(ncrMessage, commonDBHandler, e.getMessage()));
		}

		LOG.writeLog(transactionId, "onCallAPI End : ", command, LogExecuter.RUN);
	}

	public NCRMessage getRecipeStatus(NCRMessage pReqMsg) throws Exception {

		RecipeDBHandler recipeDBHandler = new RecipeDBHandler(clientId, loggerName, das2Name);

		if (NCRCommonFunction.apiMessageMandatoryCheck(pReqMsg, commonDBHandler)) {
			this.sendMessage(pReqMsg);
			return pReqMsg;
		}

		try {

			ArrayList<RecipeUnit> startRecipeUnitList = recipeDBHandler.getRecipeRawIdWithConditions(true,
					pReqMsg.getData().getRecipe().getGet_recipe_unit());

			if (startRecipeUnitList == null || startRecipeUnitList.size() == 0) {

				NCRMessage resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 5012, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);

				return resultMsg;
			}

			//getRecipeStatus reply

			MsgData msgData = new MsgData();
			RecipeData recipeData = new RecipeData();

			//this api result is only one, because guid, version guid is mandatory data
			RecipeUnit getRecipeUnit = startRecipeUnitList.get(0);

			//remove unnecessariness item
			getRecipeUnit.setSequence(null);
			getRecipeUnit.setRecipe_unit_role(null);
			getRecipeUnit.setLinked_app_id(null);
			getRecipeUnit.setLinked_recipe_unit_type_id(null);

			recipeData.setGet_recipe_unit(getRecipeUnit);

			msgData.setRecipe(recipeData);

			pReqMsg.getHeader().setReply(true);
			NCRMessage replyMsg = this.getReturnMessage(pReqMsg.getHeader(), msgData, 0, 3, "");

			this.sendMessage(replyMsg);

		} catch (Exception ex) {

			pReqMsg.getHeader().setReply(true);
			LOG.writeErrorLog(ex);

			throw ex;
		}

		return pReqMsg;
	}

	public NCRMessage sendRecipeStatus(NCRMessage pReqMsg) throws Exception {

		RecipeDBHandler recipeDBHandler = new RecipeDBHandler(clientId, loggerName, das2Name);

		if (NCRCommonFunction.apiMessageMandatoryCheck(pReqMsg, commonDBHandler)) {
			this.sendMessage(pReqMsg);
			return pReqMsg;
		}

		try {

			RecipeUnit getRecipeUnit = pReqMsg.getData().getRecipe().getGet_recipe_unit();

			Integer appId = getRecipeUnit.getApp_id();
			String guid = getRecipeUnit.getRecipe_unit_guid();
			String versionGuid = getRecipeUnit.getRecipe_unit_version_guid();
			Integer status = getRecipeUnit.getRecipe_unit_status();

			Integer clientAppId = commonDBHandler.getAppIdByClientId(pReqMsg.getHeader().getClient_id());

			if (clientAppId == null || !clientAppId.equals(appId)) {
				NCRMessage resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 3504, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);

				return resultMsg;
			}

			int rcpRawId = recipeDBHandler.getRecipeRawid(appId, guid, versionGuid);

			if (rcpRawId == 0) {

				NCRMessage resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 5001, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);

				return resultMsg;
			}

			boolean hasParentRecipe = recipeDBHandler.hasParentRecipe(appId, guid, versionGuid);

			if (hasParentRecipe) {

				NCRMessage resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 5011, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);

				return resultMsg;
			}

			Integer rcpStatus = recipeDBHandler.getRecipeStatus(appId, guid, versionGuid);

			if (rcpStatus != null && rcpStatus.equals(status)) {

				// result true reply , and not send notify message
				NCRMessage resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 0, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);

				return resultMsg;
			}

			//update recipe status
			int result = recipeDBHandler.updateRecipeStatus(rcpRawId, status, pReqMsg.getHeader().getUser_id(),
					pReqMsg.getHeader().getClient_id());

			if (result != 0) {

				// result false reply
				NCRMessage resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 8102, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);

			} else {

				// result true reply
				NCRMessage resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 0, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);

				//notify
				RecipeInfo tempRecipeInfo = new RecipeInfo();
				ArrayList<RecipeUnit> recipeUintInfoList = recipeDBHandler.getRecipeInfoByRawid(rcpRawId);
				pReqMsg.getData().getRecipe().addRecipe_unit_list(
						recipeUintInfoList != null && recipeUintInfoList.size() > 0 ? recipeUintInfoList.get(0) : null);
				pReqMsg.getData().setRecipe_info(tempRecipeInfo);
				this.notifyRecipeUnitChanged(pReqMsg, 0);
			}

		} catch (Exception ex) {

			pReqMsg.getHeader().setReply(true);
			LOG.writeErrorLog(ex);

			throw ex;
		}

		return pReqMsg;
	}

	public NCRMessage saveRecipe(NCRMessage pReqMsg) throws Exception {

		FolderInfoDBHandler folderDBHandler = new FolderInfoDBHandler(clientId, loggerName, das2Name);
		RecipeDBHandler recipeDbHandler = new RecipeDBHandler(clientId, loggerName, das2Name);

		if (NCRCommonFunction.apiMessageMandatoryCheck(pReqMsg, commonDBHandler)) {
			this.sendMessage(pReqMsg);
			return pReqMsg;
		}
		

		NCRMessage resultMsg = null;

		boolean isSavedRecipe = false;
		int result = 0;

		Integer recipeTempRawId = null;

		RecipeInfo recipeInfo = null;
		ArrayList<RecipeUnit> recipeUnitList = null;

		NCRTaskProgress taskProgress = null;

		int rootRecipeIndex = 0;
		//TRMS-1042
		ArrayList<String> extraErrorList = new ArrayList<String>();
		try {

			RecipeData recipeData = pReqMsg.getData().getRecipe();

			recipeUnitList = recipeData.getRecipe_unit_list();

			recipeInfo = pReqMsg.getData().getRecipe_info();
			
			//TRMS-1140
			if(!recipeInfo.getSend_type().equals("S")){
				resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 5013, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);
				return resultMsg;
			}
			
			

			Integer rootRecipeUnitTypeId = recipeInfo.getRoot_recipe_unit_type_id();
			if (rootRecipeUnitTypeId != null && rootRecipeUnitTypeId != 0) {

				for (int i = 0; i < recipeUnitList.size(); i++) {
					RecipeUnit recipeUnit = recipeUnitList.get(i);
					Integer recipeUnitTypeId = recipeUnit.getRecipe_unit_type_id();
					if (rootRecipeUnitTypeId.equals(recipeUnitTypeId)) {
						rootRecipeIndex = i;
					}
				}
			}

			MsgResult msgResult = pReqMsg.getResult();
			if (msgResult == null) {
				msgResult = new MsgResult();
				pReqMsg.setResult(msgResult);
			}
			
			// check exist recipe and folder linked
			RecipeUnit startRecipeUnit = recipeUnitList.get(rootRecipeIndex);
			Integer appId = startRecipeUnit.getApp_id();
						

			// exist folder check
//			FolderInfo folderInfo = folderDBHandler.getFolderInfo(recipeInfo.getGlobal_folder_id().toString());
			
			
			// exist folder check by app_id TRMS-1190
			FolderInfo folderInfo = folderDBHandler.getFolderInfoByAppid(recipeInfo.getGlobal_folder_id().toString(), appId);
			Integer globalFolerId = folderInfo.getGlobal_folder_id();
			if (folderInfo == null || globalFolerId == null
					|| !globalFolerId.equals(recipeInfo.getGlobal_folder_id())) {
				resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 4001, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);
				return resultMsg;
			}

			
			//Integer unitTypeId = startRecipeUnit.getRecipe_unit_type_id();
			String guid = startRecipeUnit.getRecipe_unit_guid();
			String versionGuid = startRecipeUnit.getRecipe_unit_version_guid();

			int rcpRawId = recipeDbHandler.getRecipeRawid(appId, guid, versionGuid);
			if (rcpRawId != 0) {

				isSavedRecipe = true;

				if (recipeDbHandler.existRecipeFolderLink(rcpRawId)) {
					//exist recipe and exist folder link
					resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 5002, 3, "");
					resultMsg.getHeader().setReply(true);
					this.sendMessage(resultMsg);
					return resultMsg;
				} else {
					//save recipe folder link
					resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, 0, 4, "");
					resultMsg.getHeader().setReply(true);
					this.sendMessage(resultMsg);

					LOG.writeLog("", "saveRecipe", "insertRecipeFolderLink", LogExecuter.DEBUG);
					recipeDbHandler.insertRecipeFolderLink(rcpRawId, globalFolerId, pReqMsg.getHeader().getUser_id());
				}
			}

			if (!isSavedRecipe) {

				String saveType = recipeInfo.getAction_type();

				//check json data and make recipe rawid
				result = this.makeRecipeRawId(recipeDbHandler, saveType, recipeUnitList, rootRecipeIndex,
						extraErrorList);
				if (result != 0) {
					// error case
					resultMsg = this.getReturnMessage(pReqMsg.getHeader(), null, result, 3, "");
					//TRMS-1042
					if (result == 5008 && extraErrorList.size() > 0) {
						resultMsg.getResult().addMessage(extraErrorList);
					}
					resultMsg.getHeader().setReply(true);
					this.sendMessage(resultMsg);
					return resultMsg;
				}

				// startSendRecipe reply
				MsgHeader header = new MsgHeader();
				header.setClient_id(pReqMsg.getHeader().getClient_id());
				header.setMsg_command(pReqMsg.getHeader().getMsg_command());
				header.setSend_msg_number(pReqMsg.getHeader().getSend_msg_number());
				header.setReply(true);
				header.setSource(pReqMsg.getHeader().getSource());
				header.setTask_id(pReqMsg.getHeader().getTask_id());
				header.setTransaction_id(pReqMsg.getHeader().getTransaction_id());
				header.setUser_id(pReqMsg.getHeader().getUser_id());

				NCRMessage replyMsg = this.getReturnMessage(header, null, 0, 4, "");
				this.sendMessage(replyMsg);

				taskProgress = new NCRTaskProgress(clientId, taskId, header.getUser_id(), commonDBHandler);
				// task count = list size + create real recipe data job (1)
				taskProgress.startTaskProgress(header.getUser_id(), this.getTaskCount(recipeUnitList));

				// set compatibilityVerison
				String compatibilityVersion = commonDBHandler
						.getCompatibilityVersion(pReqMsg.getHeader().getClient_id());
				for (RecipeUnit recipeUnit : recipeUnitList) {
					recipeUnit.setRecipe_compatibility_version(compatibilityVersion);
				}

				//Save Recipe Data
				LOG.writeLog("", "saveRecipe", "saveRecipeDataToTemp", LogExecuter.DEBUG);
				recipeTempRawId = this.saveRecipeDataToTemp(pReqMsg.getHeader(), recipeInfo, recipeUnitList,
						taskProgress);

				if (recipeTempRawId != null && recipeTempRawId > 0) {
					result = this.requestSendFileList(pReqMsg, taskProgress);
				} else {
					//error case
					// saveRecipeDataToTemp return error code (result)

					result = Math.abs(recipeTempRawId);

				}

			}

			if (!isSavedRecipe && result == 0) {

				//move recipe data from temp table to real table, save recipe folder link
				LOG.writeLog("", "saveRecipe", "saveRecipeDataToReal", LogExecuter.DEBUG);
				result = this.saveRecipeDataToReal(pReqMsg.getHeader(), recipeTempRawId,
						recipeInfo.getGlobal_folder_id(), recipeUnitList, recipeDbHandler, rootRecipeIndex);

				taskProgress.updateTaskCount();

				if (result == 0) {
					recipeTempRawId = 0;
				}
			}

			if (taskProgress != null) {
				taskProgress.stopTaskProgress();
			}

			//not use result
			resultMsg = this.sendEndSendRecipe(pReqMsg, result, "");

			if (result == 0) {
				this.notifyRecipeUnitChanged(pReqMsg, rootRecipeIndex);
			}

		} catch (Exception ex) {

			if (taskProgress != null) {
				taskProgress.stopTaskProgress();
			}

			//not use result
			resultMsg = this.sendEndSendRecipe(pReqMsg, 8901, ex.getMessage());

			LOG.writeErrorLog(ex);

		} finally {

			if (recipeTempRawId != null && recipeTempRawId > 0) {
				LOG.writeLog("", "saveRecipe", "deleteTempTable", LogExecuter.DEBUG);
				recipeDbHandler.deleteTempTable(recipeTempRawId);
			}
		}

		return resultMsg;
	}

	private int getTaskCount(ArrayList<RecipeUnit> recipeUnitList) {

		int count = 1;

		if (recipeUnitList != null) {

			count += recipeUnitList.size(); //base control recipe size

			for (RecipeUnit recipeUnit : recipeUnitList) {

				Integer recipeUnitRole = recipeUnit.getRecipe_unit_role();

				if (recipeUnitRole != null && recipeUnitRole.equals(NCRDefine.RECIPE_UNIT_ROLE_FILE)) {
					//add file count
					count++;
				}
			}
		}

		return count;

	}

	private void notifyRecipeUnitChanged(NCRMessage ncrMessage, int rootRecipeIndex) throws Exception {
		FolderInfoDBHandler folderDBHandler = new FolderInfoDBHandler(clientId, loggerName, das2Name);

		String userId = ncrMessage.getHeader().getUser_id();
		Integer globalUserId = null;
		Date date = new Date();
		if (!API_LIST.SENDRECIPESTATUS.getElement().equals(ncrMessage.getHeader().getMsg_command())) {
			globalUserId = commonDBHandler.getGlobalUserId(userId);
		}
		NCRMessage sendMessage = new NCRMessage();

		MsgHeader msgHeader = new MsgHeader();

		msgHeader.setClient_id(ncrMessage.getHeader().getClient_id());
		msgHeader.setTask_id(ncrMessage.getHeader().getTask_id());
		msgHeader.setMsg_command(API_LIST.NOTIFYRECIPEUNITCHANGED.getElement());
		msgHeader.setSource(NCRDefine.MESSAGE_HEADER_SOURCE_NCRSERVER);
		msgHeader.setReply(false);
		msgHeader.setUser_id(userId);

		sendMessage.setHeader(msgHeader);

		MsgData msgData = new MsgData();
		RecipeData recipeData = new RecipeData();

		RecipeData reciveRecipe = ncrMessage.getData().getRecipe();
		RecipeInfo recipeInfo = ncrMessage.getData().getRecipe_info();

		ArrayList<RecipeRevision> revisionList = new ArrayList<RecipeRevision>();

		for (int i = 0; i < reciveRecipe.getRecipe_unit_list().size(); i++) {

			RecipeUnit recipeUnit = reciveRecipe.getRecipe_unit_list().get(i);

			String existRecipe = recipeUnit.getExist_recipe();
			//TRMS-1091
			if (!"Y".equals(existRecipe) && 1 == recipeUnit.getRecipe_unit_role()) {
				RecipeRevision revision = new RecipeRevision();
				revision.setApp_id(recipeUnit.getApp_id());
				revision.setRecipe_unit_type_id(recipeUnit.getRecipe_unit_type_id());
				revision.setRecipe_unit_name(recipeUnit.getRecipe_unit_name());
				revision.setRecipe_unit_status(recipeUnit.getRecipe_unit_status());
				revision.setRecipe_unit_guid(recipeUnit.getRecipe_unit_guid());
				revision.setRecipe_unit_version_guid(recipeUnit.getRecipe_unit_version_guid());
				revision.setRecipe_compatibility_version(recipeUnit.getRecipe_compatibility_version());
				//TRMS-1096
				if (!API_LIST.SENDRECIPESTATUS.getElement().equals(ncrMessage.getHeader().getMsg_command())) {
					revision.setTime_stamp(date.toString());
					revision.setRecipe_folder_path(folderDBHandler
							.getFolderFullPath((NCRCommonFunction.integerToString(recipeInfo.getGlobal_folder_id()))));
					revision.setGlobal_folder_id(recipeInfo.getGlobal_folder_id());// ??
				} else {
					globalUserId = commonDBHandler.getGlobalUserId(recipeUnit.getCreateBy());
					revision.setTime_stamp(recipeUnit.getCreateDtts());
					revision.setRecipe_folder_path(
							folderDBHandler.getFolderFullPath((recipeUnit.getRecipe_folder_id())));
					revision.setGlobal_folder_id(NCRCommonFunction.stringToInteger(recipeUnit.getRecipe_folder_id()));
				}
				revision.setGlobal_user_id(globalUserId);
				//TRMS-1096
				/*
				  if (i == rootRecipeIndex) {
								revision.setRecipe_folder_path(folderDBHandler
										.getFolderFullPath((NCRCommonFunction.integerToString(recipeInfo.getGlobal_folder_id()))));
								revision.setGlobal_folder_id(recipeInfo.getGlobal_folder_id());// ??
								}
				*/

				revisionList.add(revision);
			}
		}

		recipeData.setRecipe_revision_list(revisionList);
		msgData.setRecipe(recipeData);

		sendMessage.setData(msgData);
		ncrMessage.setData(msgData);

		this.sendMessage(sendMessage);

	}

	private NCRMessage sendEndSendRecipe(NCRMessage ncrMessage, Integer result, String extraMessage) throws Exception {

		NCRMessage receiveMessage = null;

		try {

			MsgHeader msgHeader = ncrMessage.getHeader();
			msgHeader.setTask_id(ncrMessage.getHeader().getTask_id());
			msgHeader.setTransaction_id(NCRCommonFunction.currStrDataTime(""));
			msgHeader.setSource(NCRDefine.MESSAGE_HEADER_SOURCE_NCRSERVER);
			msgHeader.setReply(false);
			msgHeader.setMsg_command(API_LIST.ENDSENDRECIPE.getElement());

			NCRMessage repMsg = this.getReturnMessage(msgHeader, null, result, 3, extraMessage);

			//			this.sendMessage(repMsg);
			//			return repMsg;

			//not use result
			String receiveStr = this.sendWaitMessage(repMsg);

			if (receiveStr != null && receiveStr.equals(NCRDefine.MESSAGE_TIMEOUT)) {

				receiveMessage = this.getReturnMessage(msgHeader, null, 8001, 3, "");
				//receiveMessage = this.taskTimeout(msgHeader);
			} else {
				receiveMessage = NCRCommonFunction.JsonStringToNCRMsg(receiveStr);
			}

		} catch (Exception e) {
			LOG.writeErrorLog(e);
			throw e;
		}

		return receiveMessage;

	}

	private int requestSendFileList(NCRMessage pReqMsg, NCRTaskProgress taskProgress) throws Exception {

		int result = 0;

		//send file list to nano client
		NCRMessage sendFileList = this.makeRequestSendFileListMessage(pReqMsg);
		String sendFileListStr = NCRCommonFunction.NCRMsgToString(sendFileList);

		ArrayList<RecipeFile> fileList = sendFileList.getData().getFile_list();

		LOG.writeLog(pReqMsg.getHeader().getTransaction_id(), "requestSendFileList",
				"fileList size : " + fileList.size(), LogExecuter.DEBUG);

		if (fileList != null && fileList.size() > 0) {

			boolean isProcessing = true;

			// TRMS-702
			//			long timeout = NCRCommonFunction.getConnectivityTimeOut(NCRDefine.DEFAULT_CONNECTIVITY_NAME);
			long timeout = NCRCommonFunction.get_configTimeOut();

			String waitKey = NCRCommonFunction.mkWaitObjKey(pReqMsg.getHeader().getClient_id(),
					pReqMsg.getHeader().getTask_id() + "");
			WaitObjectWF waitObject = new WaitObjectWF(waitKey, sendFileListStr, timeout);
			ReplyQueueWF.GetInstance().addReqToHash(waitObject);

			this.sendMessage(sendFileList);

			while (isProcessing) {

				try {

					String json = ReplyQueueWF.GetInstance().addRequestData(waitObject);

					LOG.writeLog(pReqMsg.getHeader().getTransaction_id(), "requestSendFileList", json,
							LogExecuter.DEBUG);

					if (json != null && json.equals(NCRDefine.MESSAGE_TIMEOUT)) {
						result = 8001;
						isProcessing = false;
						MsgResult msgResult = new MsgResult();
						msgResult.setCode(result);

						sendFileList.setResult(msgResult);

						sendFileList.getHeader().setMsg_command(API_LIST.RESULTSENDFILELIST.getElement());

						//no result use
						this.sendWaitMessage(sendFileList);
						//TRMS-1005
						this.sendMessage(this.taskTimeout(pReqMsg.getHeader()));

					} else {

						NCRMessage saveFileResult = NCRCommonFunction.JsonStringToNCRMsg(json);

						if (saveFileResult == null) {
							LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "sendFileResult is null : 6003", json,
									LogExecuter.DEBUG);
							result = 6003;
							isProcessing = false;
							break;
						}

						String command = saveFileResult.getHeader().getMsg_command();

						LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "requestSendFileList", json,
								LogExecuter.DEBUG);

						if (API_LIST.REQUESTSENDFILELIST.getElement().equals(command)) {

							// reply message ...
							if (saveFileResult.getResult().getCode() != 0) {
								LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "the result code of saveFileResult (REQUESTSENDFILELIST) is [ "+saveFileResult.getResult().getCode()+" ]", json,
										LogExecuter.DEBUG);
								result = 6003;
								isProcessing = false;
							}

						} else if (API_LIST.ENDSENDFILELIST.getElement().equals(command)) {

							Integer resultCode = saveFileResult.getResult().getCode();

							if (resultCode == null || resultCode != 0) {
								LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "the result code of endSendFileList ( ENDSENDFILELIST: Client -> Server) is null or not 0 [ the result ]: "+resultCode, json,
										LogExecuter.DEBUG);
								
								resultCode = 6003;// file save error
							}

							if (resultCode == 0) {
								// normal case : check file list
								boolean isAllchecked = true;
								for (RecipeFile recipeFile : fileList) {

									if (!recipeFile.getCheckFile()) {
										LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "Fail recipe check : file GUID :[ "+recipeFile.getRecipe_unit_guid()+" ] , file version GUID : [ "+recipeFile.getRecipe_unit_version_guid()+" ]", json,
												LogExecuter.DEBUG);
										
										isAllchecked = false;
									}
								}
								if (!isAllchecked) {
									
									resultCode = 6003;
								}
							}

							//send client reply
							NCRMessage replyMsg = this.getReturnMessage(saveFileResult.getHeader(), null, resultCode, 4,
									"");
							replyMsg.getHeader().setReply(true);
							this.sendMessage(replyMsg);

							// send internal message : check File list
							MsgResult msgResult = new MsgResult();
							msgResult.setCode(resultCode);
							sendFileList.setResult(msgResult);

							sendFileList.getHeader().setMsg_command(API_LIST.RESULTSENDFILELIST.getElement());
							String checkResult = this.sendWaitMessage(sendFileList);

							if (checkResult != null && !checkResult.equals("")) {

								isProcessing = false;

								NCRMessage checkResultMsg = NCRCommonFunction.JsonStringToNCRMsg(checkResult);
								Integer checkResultCode = checkResultMsg.getResult().getCode();

								if (resultCode != 0) {// && checkResultCode ==1
														// 0){
									result = 6003;
									LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "resultcode is not 0 :[ "+resultCode+" ]", json,
											LogExecuter.DEBUG);
									
								} else if (resultCode == 0 && checkResultCode != 0) {
									result = 6003;// file check error code
									LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "resultcode :[ "+resultCode+" ], checkResultcode:[ "+checkResultCode+" ]", json,
											LogExecuter.DEBUG);
								} else if (resultCode == 0 && checkResultCode == 0) {
									// normal case
								}

							} else {

								// check error : unknown
								result = 6003;// file check error code
								isProcessing = false;
								
								LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "File save nuKnown Error ", json,
										LogExecuter.DEBUG);
								
							}

						} else if (API_LIST.UPDATEFILEPROGRESS.getElement().equals(command)) {

							taskProgress.updateTaskCount();

							MsgData msgData = saveFileResult.getData();
							TaskProgress fileProgress = msgData.getFile_progress();

							for (RecipeFile recipeFile : fileList) {

								Integer sendAppId = recipeFile.getApp_id();
								String sendGuid = recipeFile.getRecipe_unit_guid();
								String sendVersionGuid = StringHandler
										.replaceNull(recipeFile.getRecipe_unit_version_guid(), "");

								Integer receiveAppId = fileProgress.getApp_id();
								String receiveGuid = fileProgress.getRecipe_unit_guid();
								String receiveVersionGuid = StringHandler
										.replaceNull(fileProgress.getRecipe_unit_version_guid(), "");

								if (sendAppId.equals(receiveAppId) && sendGuid.equals(receiveGuid)
										&& sendVersionGuid.equals(receiveVersionGuid)) {
									recipeFile.setCheckFile(true);
									
									LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "check File is true ( requestFileList - Send )      S_app_id:[ "+sendAppId+" ], S_guid:[ "+sendGuid+" ], S_versionGUID:[ "+sendVersionGuid+" ]", json,LogExecuter.DEBUG);
									LOG.writeLog(saveFileResult.getHeader().getTransaction_id(), "check File is true ( UpdateFileProgress - Receive) R_app_id:[ "+receiveAppId+" ], R_guid:[ "+receiveGuid+" ], R_versionGUID:[ "+receiveVersionGuid+" ]", json,LogExecuter.DEBUG);
									
								}
							}

							//							if (saveFileResult.getResult().getCode() != 0) {
							//								isProcessing = false;
							//								result = saveFileResult.getResult().getCode();
							//							} else {
							//								// check file list
							//
							//								for (RecipeFile recipeFile : fileList) {
							//
							//									Integer sendAppId = recipeFile.getApp_id();
							//									String sendGuid = recipeFile.getRecipe_unit_guid();
							//									String sendVersionGuid = StringHandler
							//											.replaceNull(recipeFile.getRecipe_unit_version_guid(), "");
							//
							//									Integer receiveAppId = fileProgress.getApp_id();
							//									String receiveGuid = fileProgress.getRecipe_unit_guid();
							//									String receiveVersionGuid = StringHandler
							//											.replaceNull(fileProgress.getRecipe_unit_version_guid(), "");
							//
							//									if (sendAppId.equals(receiveAppId) && sendGuid.equals(receiveGuid)
							//											&& sendVersionGuid.equals(receiveVersionGuid)) {
							//										recipeFile.setCheckFile(true);
							//									}
							//								}
							//							}
						} else if (API_LIST.TASKTIMEOUT.getElement().equals(command)) {//TRMS-1005
							isProcessing = false;
							result = 8001;// file check error code
							MsgResult msgResult = new MsgResult();
							msgResult.setCode(result);

							sendFileList.setResult(msgResult);

							sendFileList.getHeader().setMsg_command(API_LIST.RESULTSENDFILELIST.getElement());

							//no result use
							this.sendWaitMessage(sendFileList);
						}
					}

					if (!isProcessing) {
						ReplyQueueWF.GetInstance().removeObject(waitObject);
					}

				} catch (Exception e) {

					if (isProcessing) {
						ReplyQueueWF.GetInstance().removeObject(waitObject);
					}
					LOG.writeErrorLog(e);
					throw e;
				}
			}
		}

		return result;

	}

	private NCRMessage getReturnMessage(MsgHeader pHeader, MsgData pData, int pCode, int pStatus, String extraMessage)
			throws Exception {

		String errorDesc = commonDBHandler.getErrorDescription(pCode);

		NCRMessage returnMessageObj = new NCRMessage();

		returnMessageObj.setHeader(pHeader);
		returnMessageObj.setData(pData);

		MsgResult result = new MsgResult();
		result.setCode(pCode);
		result.setStatus(pStatus);
		result.setMessage(errorDesc);

		if (extraMessage != null && !extraMessage.equals("")) {
			ArrayList<String> messageList = new ArrayList<String>();
			messageList.add(extraMessage);

			result.addMessage(messageList);
		}

		returnMessageObj.setResult(result);

		return returnMessageObj;
	}

	private NCRMessage makeRequestSendFileListMessage(NCRMessage ncrMessage) throws Exception {

		NCRMessage sendMessage = new NCRMessage();

		//create header
		MsgHeader msgHeader = new MsgHeader();
		msgHeader.setClient_id(ncrMessage.getHeader().getClient_id());
		msgHeader.setMsg_command(API_LIST.REQUESTSENDFILELIST.getElement());
		msgHeader.setReply(false);
		msgHeader.setSource(NCRDefine.MESSAGE_HEADER_SOURCE_NCRSERVER);
		msgHeader.setTask_id(ncrMessage.getHeader().getTask_id());
		msgHeader.setUser_id(ncrMessage.getHeader().getUser_id());

		sendMessage.setHeader(msgHeader);

		// create msg data
		MsgData msgData = new MsgData();

		ArrayList<RecipeUnit> recipeUnitList = ncrMessage.getData().getRecipe().getRecipe_unit_list();

		ArrayList<RecipeFile> fileList = this.getFileList(recipeUnitList, false);
		msgData.setFile_list(fileList);

		sendMessage.setData(msgData);

		return sendMessage;
	}

	private int makeRecipeRawId(RecipeDBHandler recipeDbHandler, String saveType, ArrayList<RecipeUnit> recipeUnitList,
			int rootRecipeIndex, ArrayList<String> extraErrorList) throws Exception {

		int errorCode = 0;

		if (NCRDefine.ACTION_TYPE_P.equals(saveType)) {

			RecipeUnit rcpUnit = recipeUnitList.get(rootRecipeIndex);

			Integer appId = rcpUnit.getApp_id();
			String guid = rcpUnit.getRecipe_unit_guid();
			String versionGuid = rcpUnit.getRecipe_unit_version_guid();

			Integer rcpRawid = recipeDbHandler.getRecipeTempRawid(appId, guid, versionGuid);
			rcpUnit.setRaw_id(rcpRawid);
			rcpUnit.setExist_recipe("N");

			ArrayList<RecipeUnit> entryList = recipeUnitList.get(rootRecipeIndex).getRecipe_unit_list();
			int result = this.makeRecursiveRecipeRawId(recipeDbHandler, recipeUnitList, entryList, saveType,
					extraErrorList);
			if (result != 0) {
				return result;
			}

		} else {

			for (int i = 0; i < recipeUnitList.size(); i++) {
				RecipeUnit recipeUnit = recipeUnitList.get(i);
				Integer rcpRawId = recipeUnit.getRaw_id();

				if (rcpRawId == null || rcpRawId == 0) {
					this.addRecipeRawId(recipeDbHandler, recipeUnit, false);
				}

				ArrayList<RecipeUnit> entryList = recipeUnit.getRecipe_unit_list();

				int result = this.makeRecursiveRecipeRawId(recipeDbHandler, recipeUnitList, entryList, saveType,
						extraErrorList);
				if (result != 0) {
					return result;
				}
			}
		}

		// check recipe structure
		int rootRecipeCount = 0;
		for (RecipeUnit recipeUnit : recipeUnitList) {
			Integer rawId = recipeUnit.getRaw_id();
			Boolean linkedControlRecipe = recipeUnit.getLinked_control_recipe();

			if (linkedControlRecipe == null || !linkedControlRecipe) {
				rootRecipeCount++;
			}
			if (rawId == null || rawId == 0) {
				//error case json data is wrong
				return 1003;
			}
		}

		if (rootRecipeCount > 1) {
			errorCode = 1003;
		}

		return errorCode;
	}

	private int makeRecursiveRecipeRawId(RecipeDBHandler recipeDbHandler, ArrayList<RecipeUnit> recipeUnitList,
			ArrayList<RecipeUnit> entryList, String saveType, ArrayList<String> extraErrorList) throws Exception {

		int returnCode = 0;

		if (entryList != null && entryList.size() > 0) {

			for (RecipeUnit rcpUnit : entryList) {
				Integer role = rcpUnit.getRecipe_unit_role();

				//recipe link
				if (role.equals(NCRDefine.RECIPE_UNIT_ROLE_LINKED_RECIPE) || role.equals(NCRDefine.RECIPE_UNIT_ROLE_FILE_LINK)) {
					//find control recipe rawid
					String guid = rcpUnit.getRecipe_unit_guid();
					String versionGuid = rcpUnit.getRecipe_unit_version_guid();

					Integer appId = rcpUnit.getApp_id();
					Integer linkedAppId = rcpUnit.getLinked_app_id();

					if (linkedAppId != null && linkedAppId != 0 && !linkedAppId.equals(appId)) {

						Integer rcpRawId = null;

						if (guid != null && !"".equals(guid)) {
							rcpRawId = recipeDbHandler.getRecipeRawid(linkedAppId, guid, versionGuid);
						} else {
							//TRMS-1121
							Integer linkedUnitTypeId = rcpUnit.getLinked_recipe_unit_type_id();
							String recipeName = rcpUnit.getRecipe_unit_name();
							rcpRawId = recipeDbHandler.getRecipeRawidByRecipeName(linkedAppId, linkedUnitTypeId,
									recipeName);
						}
						if (rcpRawId == null || rcpRawId == 0) {
							//error case : not exist child recipe
							//TRMS-1042
							if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_name(), ""))) {
								extraErrorList.add("recipe_unit_name:" + rcpUnit.getRecipe_unit_name());
							}
							if (rcpUnit.getLinked_app_id() != null) {
								extraErrorList.add("linked_app_id:"
										+ NCRCommonFunction.integerToString(rcpUnit.getLinked_app_id()));
							}
							if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_guid(), ""))) {
								extraErrorList.add("recipe_unit_guid:" + rcpUnit.getRecipe_unit_guid());
							}
							if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_version_guid(), ""))) {
								extraErrorList.add("recipe_unit_version_guid:" + rcpUnit.getRecipe_unit_version_guid());
							}
							if (rcpUnit.getRecipe_unit_role() != null) {
								extraErrorList.add("recipe_unit_role:" + rcpUnit.getRecipe_unit_role());
							}
							if (rcpUnit.getLinked_recipe_unit_type_id() != null) {
								extraErrorList
										.add("linked_recipe_unit_type_id:" + rcpUnit.getLinked_recipe_unit_type_id());
							}
							returnCode = 5008;
							return returnCode;
						} else {
							rcpUnit.setRaw_id(rcpRawId);
							rcpUnit.setExist_recipe("Y");
						}

					} else {

						if (NCRDefine.ACTION_TYPE_P.equals(saveType)) {

//							Integer rcpRawId = recipeDbHandler.getRecipeRawid(linkedAppId, guid, versionGuid);
							// TRMS-1183
							Integer rcpRawId = recipeDbHandler.getRecipeRawid(appId, guid, versionGuid);
							
							// File Link TRMS-1142
							if (role.equals(NCRDefine.RECIPE_UNIT_ROLE_FILE_LINK) && (rcpRawId == null || rcpRawId == 0) ) {
								
								Integer tmpRcpRawId = this.setRecipeRawId(recipeDbHandler, guid, versionGuid, recipeUnitList);
								if (tmpRcpRawId == 0) {
									//error case : not find control recipe in get json data
									returnCode = 1003;
									return returnCode;
								} else {
									rcpUnit.setRaw_id(tmpRcpRawId);
									// TRMS-1183
									rcpRawId = tmpRcpRawId ;  
								}
							}
							if (( rcpRawId == null || rcpRawId == 0 ) && !role.equals(NCRDefine.RECIPE_UNIT_ROLE_FILE_LINK) ) {
								//error case : not exist child recipe
								//TRMS-1042
								if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_name(), ""))) {
									extraErrorList.add("recipe_unit_name:" + rcpUnit.getRecipe_unit_name());
								}
								if (rcpUnit.getLinked_app_id() != null) {
									extraErrorList.add("linked_app_id:"
											+ NCRCommonFunction.integerToString(rcpUnit.getLinked_app_id()));
								}
								if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_guid(), ""))) {
									extraErrorList.add("recipe_unit_guid:" + rcpUnit.getRecipe_unit_guid());
								}
								if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_version_guid(), ""))) {
									extraErrorList
											.add("recipe_unit_version_guid:" + rcpUnit.getRecipe_unit_version_guid());
								}
								if (rcpUnit.getRecipe_unit_role() != null) {
									extraErrorList.add("recipe_unit_role:" + rcpUnit.getRecipe_unit_role());
								}
								if (rcpUnit.getLinked_recipe_unit_type_id() != null) {
									extraErrorList.add(
											"linked_recipe_unit_type_id:" + rcpUnit.getLinked_recipe_unit_type_id());
								}
								returnCode = 5008;
								return returnCode;
							} else {
								rcpUnit.setRaw_id(rcpRawId);
								rcpUnit.setExist_recipe("Y");
							}

						} else {

							Integer rcpRawId = this.setRecipeRawId(recipeDbHandler, guid, versionGuid, recipeUnitList);
							if (rcpRawId == 0) {
								//error case : not find control recipe in get json data
								returnCode = 1003;
								return returnCode;
							} else {
								rcpUnit.setRaw_id(rcpRawId);
							}

						}
					}
				}

				ArrayList<RecipeUnit> subEntryList = rcpUnit.getRecipe_unit_list();
				if (subEntryList != null && subEntryList.size() > 0) {
					returnCode = this.makeRecursiveRecipeRawId(recipeDbHandler, recipeUnitList, subEntryList, saveType,
							extraErrorList);
					if (returnCode != 0) {
						return returnCode;
					}
				}
			}
		}

		return returnCode;
	}
	
	private int makeRecursiveRecipeRawId_backup(RecipeDBHandler recipeDbHandler, ArrayList<RecipeUnit> recipeUnitList,
			ArrayList<RecipeUnit> entryList, String saveType, ArrayList<String> extraErrorList) throws Exception {

		int returnCode = 0;

		if (entryList != null && entryList.size() > 0) {

			for (RecipeUnit rcpUnit : entryList) {
				Integer role = rcpUnit.getRecipe_unit_role();

				//recipe link
				if (role.equals(NCRDefine.RECIPE_UNIT_ROLE_LINKED_RECIPE)
						|| role.equals(NCRDefine.RECIPE_UNIT_ROLE_FILE_LINK)) {
					//find control recipe rawid
					String guid = rcpUnit.getRecipe_unit_guid();
					String versionGuid = rcpUnit.getRecipe_unit_version_guid();

					Integer appId = rcpUnit.getApp_id();
					Integer linkedAppId = rcpUnit.getLinked_app_id();

					if (linkedAppId != null && linkedAppId != 0 && !linkedAppId.equals(appId)) {

						Integer rcpRawId = null;

						if (guid != null && !"".equals(guid)) {
							rcpRawId = recipeDbHandler.getRecipeRawid(linkedAppId, guid, versionGuid);
						} else {
							//TRMS-1121
							Integer linkedUnitTypeId = rcpUnit.getLinked_recipe_unit_type_id();
							String recipeName = rcpUnit.getRecipe_unit_name();
							rcpRawId = recipeDbHandler.getRecipeRawidByRecipeName(linkedAppId, linkedUnitTypeId,
									recipeName);
						}
						if (rcpRawId == null || rcpRawId == 0) {
							//error case : not exist child recipe
							//TRMS-1042
							if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_name(), ""))) {
								extraErrorList.add("recipe_unit_name:" + rcpUnit.getRecipe_unit_name());
							}
							if (rcpUnit.getLinked_app_id() != null) {
								extraErrorList.add("linked_app_id:"
										+ NCRCommonFunction.integerToString(rcpUnit.getLinked_app_id()));
							}
							if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_guid(), ""))) {
								extraErrorList.add("recipe_unit_guid:" + rcpUnit.getRecipe_unit_guid());
							}
							if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_version_guid(), ""))) {
								extraErrorList.add("recipe_unit_version_guid:" + rcpUnit.getRecipe_unit_version_guid());
							}
							if (rcpUnit.getRecipe_unit_role() != null) {
								extraErrorList.add("recipe_unit_role:" + rcpUnit.getRecipe_unit_role());
							}
							if (rcpUnit.getLinked_recipe_unit_type_id() != null) {
								extraErrorList
										.add("linked_recipe_unit_type_id:" + rcpUnit.getLinked_recipe_unit_type_id());
							}
							returnCode = 5008;
							return returnCode;
						} else {
							rcpUnit.setRaw_id(rcpRawId);
							rcpUnit.setExist_recipe("Y");
						}

					} else {

						if (NCRDefine.ACTION_TYPE_P.equals(saveType)) {

							Integer rcpRawId = recipeDbHandler.getRecipeRawid(linkedAppId, guid, versionGuid);
							if (rcpRawId == null || rcpRawId == 0) {
								//error case : not exist child recipe
								//TRMS-1042
								if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_name(), ""))) {
									extraErrorList.add("recipe_unit_name:" + rcpUnit.getRecipe_unit_name());
								}
								if (rcpUnit.getLinked_app_id() != null) {
									extraErrorList.add("linked_app_id:"
											+ NCRCommonFunction.integerToString(rcpUnit.getLinked_app_id()));
								}
								if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_guid(), ""))) {
									extraErrorList.add("recipe_unit_guid:" + rcpUnit.getRecipe_unit_guid());
								}
								if (!"".equals(StringUtil.replaceNull(rcpUnit.getRecipe_unit_version_guid(), ""))) {
									extraErrorList
											.add("recipe_unit_version_guid:" + rcpUnit.getRecipe_unit_version_guid());
								}
								if (rcpUnit.getRecipe_unit_role() != null) {
									extraErrorList.add("recipe_unit_role:" + rcpUnit.getRecipe_unit_role());
								}
								if (rcpUnit.getLinked_recipe_unit_type_id() != null) {
									extraErrorList.add(
											"linked_recipe_unit_type_id:" + rcpUnit.getLinked_recipe_unit_type_id());
								}
								returnCode = 5008;
								return returnCode;
							} else {
								rcpUnit.setRaw_id(rcpRawId);
								rcpUnit.setExist_recipe("Y");
							}

						} else {

							Integer rcpRawId = this.setRecipeRawId(recipeDbHandler, guid, versionGuid, recipeUnitList);
							if (rcpRawId == 0) {
								//error case : not find control recipe in get json data
								returnCode = 1003;
								return returnCode;
							} else {
								rcpUnit.setRaw_id(rcpRawId);
							}

						}
					}
				} 

				ArrayList<RecipeUnit> subEntryList = rcpUnit.getRecipe_unit_list();
				if (subEntryList != null && subEntryList.size() > 0) {
					returnCode = this.makeRecursiveRecipeRawId(recipeDbHandler, recipeUnitList, subEntryList, saveType,
							extraErrorList);
					if (returnCode != 0) {
						return returnCode;
					}
				}
			}
		}

		return returnCode;
	}

	private Integer setRecipeRawId(RecipeDBHandler recipeDbHandler, String guid, String versionGuid,
			ArrayList<RecipeUnit> recipeUnitList) throws Exception {

		int rcpRawId = 0;

		for (RecipeUnit recipeUnit : recipeUnitList) {
			String tmpGuid = recipeUnit.getRecipe_unit_guid();
			String tmpVersionGuid = recipeUnit.getRecipe_unit_version_guid();

			Integer tmpRcpRawId = recipeUnit.getRaw_id();

			if (versionGuid != null && !versionGuid.equals("")) {

				if (guid.equals(tmpGuid) && versionGuid.equals(tmpVersionGuid)) {

					if (tmpRcpRawId == null || tmpRcpRawId == 0) {
						tmpRcpRawId = this.addRecipeRawId(recipeDbHandler, recipeUnit, true);
					} else {
						recipeUnit.setLinked_control_recipe(true);
					}
					return tmpRcpRawId;
				}

			} else {
				if (guid.equals(tmpGuid)) {
					if (tmpRcpRawId == null || tmpRcpRawId == 0) {
						tmpRcpRawId = this.addRecipeRawId(recipeDbHandler, recipeUnit, true);
					} else {
						recipeUnit.setLinked_control_recipe(true);
					}
					return tmpRcpRawId;
				}
			}
		}

		return rcpRawId;
	}

	private Integer addRecipeRawId(RecipeDBHandler recipeDbHandler, RecipeUnit recipeUnit, boolean isLinkedRecipe)
			throws Exception {

		String existRecipe = "Y";

		Boolean linkedControlRecipe = recipeUnit.getLinked_control_recipe();

		if (linkedControlRecipe == null || !linkedControlRecipe) {
			recipeUnit.setLinked_control_recipe(isLinkedRecipe);
		}

		Integer rcpRawId = recipeDbHandler.getRecipeRawid(recipeUnit.getApp_id(), recipeUnit.getRecipe_unit_guid(),
				recipeUnit.getRecipe_unit_version_guid());
		if (rcpRawId == 0) {
			existRecipe = "N";
			rcpRawId = recipeDbHandler.getRecipeTempRawid(recipeUnit.getApp_id(), recipeUnit.getRecipe_unit_guid(),
					recipeUnit.getRecipe_unit_version_guid());
		}

		recipeUnit.setRaw_id(rcpRawId);
		recipeUnit.setExist_recipe(existRecipe);

		return rcpRawId;
	}

	private Integer saveRecipeDataToTemp(MsgHeader header, RecipeInfo recipeInfo, ArrayList<RecipeUnit> recipeUnitList,
			NCRTaskProgress taskProgress) throws Exception {

		RecipeDBHandler recipeDbHandler = new RecipeDBHandler(clientId, loggerName, das2Name);

		int clientRawid = commonDBHandler.getClientRawId(header.getClient_id());

		DAS2 das = null;

		int result = 0;

		try {

			das = DataService.getInstance().getDAS2(das2Name);
			das.beginTrans();

			LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToTemp", "recipeDbHandler.insertTempMST",
					LogExecuter.DEBUG);
			Integer tempRawId = recipeDbHandler.insertTempMST(clientRawid, header.getTask_id(), header.getMsg_command(),
					header.getUser_id(), das);
			if (tempRawId == 0) {
				result = -8102;
				das.rollback();
				return result;
			}

			//save recipe data
			for (RecipeUnit recipeUnit : recipeUnitList) {

				LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToTemp", "recipeDbHandler.insertRecipeToTemp",
						LogExecuter.DEBUG);
				result = recipeDbHandler.insertRecipeToTemp(tempRawId, header.getClient_id(), header.getUser_id(),
						recipeUnit, das);

				taskProgress.updateTaskCount();

				if (result != 0) {
					//error case
					result = -8102;
					LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToTemp",
							"insertRecipeToTemp Error / DAS Rollback", LogExecuter.RUN);
					das.rollback();
					return result;
				}
			}

			if (!recipeDbHandler.isAllExistDictionaryEntry(tempRawId, das)) {
				// error case
				result = -2001;
				LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToTemp",
						"isAllExistDictionaryEntry / DAS Rollback", LogExecuter.RUN);
				das.rollback();
				return result;
			}

			if (!recipeDbHandler.isAllExistRecipeUnitType(tempRawId, das)) {
				// error case
				result = -2101;
				LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToTemp",
						"isAllExistRecipeUnitType / DAS Rollback", LogExecuter.RUN);
				das.rollback();
				return result;
			}

			if (!recipeDbHandler.isAllExistRecipeEntryUnitType(tempRawId, das)) {
				// error case
				result = -2101;
				LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToTemp",
						"isAllExistRecipeEntryUnitType / DAS Rollback", LogExecuter.RUN);
				das.rollback();
				return result;
			}

			if (result != 0) {
				// error case
				//tempRawId = 0;
				LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToTemp",
						"result is " + result + "/ DAS Rollback", LogExecuter.RUN);
				das.rollback();
			} else {
				result = tempRawId;
				das.commit();
			}

		} catch (Exception e) {

			try {
				LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToTemp", "DAS Rollback", LogExecuter.RUN);
				das.rollback();
			} catch (Exception e1) {
				LOG.writeErrorLog(e1);
			}
			LOG.writeErrorLog(e);
			throw e;
		} finally {
			if (das != null) {
				das.close();
			}
		}

		return result;
	}

	private int saveRecipeDataToReal(MsgHeader header, Integer tempRawId, Integer globalFolderId,
			ArrayList<RecipeUnit> recipeUnitList, RecipeDBHandler recipeDbHandler, int rootRecipeIndex)
					throws Exception {

		DAS2 das = null;

		int result = 0;

		try {

			das = DataService.getInstance().getDAS2(das2Name);

			das.beginTrans();

			LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToReal", "recipeDbHandler.moveRecipeFromTemp",
					LogExecuter.DEBUG);
			result = recipeDbHandler.moveRecipeFromTemp(tempRawId, das);

			if (result == 0) {
				//create recipe and folder link
				int recipeRawId = recipeUnitList.get(rootRecipeIndex).getRaw_id();
				LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToReal", "saveFolderLink", LogExecuter.DEBUG);
				result = this.saveFolderLink(globalFolderId, recipeRawId, header.getUser_id(), recipeDbHandler, das);
				
				// TRMS-1212
//				for (RecipeUnit recipeUnit : recipeUnitList) {
//					int rcpRawid = recipeUnit.getRaw_id();
//					LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToReal", "saveFolderLink:"+rcpRawid, LogExecuter.DEBUG);
//					result = this.saveFolderLink(globalFolderId, rcpRawid, header.getUser_id(), recipeDbHandler, das);
//				}
			}

			if (result != 0) {
				// error case
				result = 8102;
				LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToReal", "DAS Rollback", LogExecuter.DEBUG);
				das.rollback();
			} else {
				das.commit();
			}

		} catch (Exception e) {

			try {
				LOG.writeLog(header.getTransaction_id(), "saveRecipeDataToReal", "DAS Rollback", LogExecuter.DEBUG);
				das.rollback();
			} catch (Exception e1) {
				LOG.writeErrorLog(e1);
			}
			LOG.writeErrorLog(e);
			throw e;

		} finally {

			if (tempRawId > 0) {
				recipeDbHandler.deleteTempTable(tempRawId);
				tempRawId = 0;
			}

			if (das != null) {
				das.close();
			}
		}

		return result;
	}

	public void getRecipeList(NCRMessage ncrMessage) throws Exception {

		try {

			NCRRecipeList rList = new NCRRecipeList(clientId, loggerName, das2Name, taskId);
			rList.onCallAPI(ncrMessage);
		} catch (Exception ex) {
			LOG.writeErrorLog(ex);
			throw ex;
		}

	}

	public NCRMessage getRecipe(NCRMessage ncrMessage) throws Exception {

		RecipeDBHandler recipeDBHandler = new RecipeDBHandler(clientId, loggerName, das2Name);

		if (NCRCommonFunction.apiMessageMandatoryCheck(ncrMessage, commonDBHandler)) {
			this.sendMessage(ncrMessage);
			return ncrMessage;
		}

		try {

			String actionType = ncrMessage.getData().getRecipe_info().getAction_type();

			boolean includeFileInfo = ncrMessage.getData().getRecipe_info().getInclude_link_file_info();

			//ArrayList<RecipeUnit> recipeUnitList = null;

			boolean isParatalRecipe = actionType.equals(NCRDefine.ACTION_TYPE_P)
					|| actionType.equals(NCRDefine.ACTION_TYPE_PR) || actionType.equals(NCRDefine.ACTION_TYPE_PM) ? true
							: false;

			ArrayList<RecipeUnit> startRecipeUnitList = recipeDBHandler.getRecipeRawIdWithConditions(isParatalRecipe,
					ncrMessage.getData().getRecipe().getGet_recipe_unit());

			if (startRecipeUnitList == null || startRecipeUnitList.size() == 0) {

				NCRMessage resultMsg = this.getReturnMessage(ncrMessage.getHeader(), null, 5012, 3, "");
				resultMsg.getHeader().setReply(true);
				this.sendMessage(resultMsg);

				return resultMsg;
			}

			for (RecipeUnit recipeUnit : startRecipeUnitList) {

				//set entry list
				recipeUnit.setRecipe_unit_list(recipeDBHandler.getStructuredRecipeEntryList(recipeUnit.getRaw_id()));

				this.setRecipeData(actionType, recipeUnit, recipeDBHandler);
			}

			//startGetRecipe reply
			MsgHeader header = new MsgHeader();
			header.setClient_id(ncrMessage.getHeader().getClient_id());
			header.setMsg_command(ncrMessage.getHeader().getMsg_command());
			header.setReply(true);
			header.setSend_msg_number(ncrMessage.getHeader().getSend_msg_number());
			header.setSource(ncrMessage.getHeader().getSource());
			header.setTask_id(ncrMessage.getHeader().getTask_id());
			header.setTransaction_id(ncrMessage.getHeader().getTransaction_id());
			header.setUser_id(ncrMessage.getHeader().getUser_id());

			MsgData msgData = new MsgData();
			RecipeData recipeData = new RecipeData();

			recipeData.setRecipe_unit_list(startRecipeUnitList);

			if (includeFileInfo) {
				ArrayList<RecipeFile> fileList = this.getFileList(startRecipeUnitList, true);
				msgData.setFile_list(fileList);
			}

			msgData.setRecipe(recipeData);

			NCRMessage replyMsg = this.getReturnMessage(header, msgData, 0, 4, "");

			boolean isProcessing = true;

			// TRMS-702
			//			long timeout = NCRCommonFunction.getConnectivityTimeOut(NCRDefine.DEFAULT_CONNECTIVITY_NAME);
			long timeout = NCRCommonFunction.get_configTimeOut();

			String waitKey = NCRCommonFunction.mkWaitObjKey(ncrMessage.getHeader().getClient_id(),
					ncrMessage.getHeader().getTask_id() + "");
			WaitObjectWF waitObject = new WaitObjectWF(waitKey, replyMsg, timeout);
			ReplyQueueWF.GetInstance().addReqToHash(waitObject);

			this.sendMessage(replyMsg);

			NCRMessage result = null;

			while (isProcessing) {

				try {

					String json = ReplyQueueWF.GetInstance().addRequestData(waitObject);

					LOG.writeLog(replyMsg.getHeader().getTransaction_id(), "getRecipe", json, LogExecuter.DEBUG);

					if (json != null && json.equals(NCRDefine.MESSAGE_TIMEOUT)) {

						isProcessing = false;

						LOG.writeLog(header.getTransaction_id(), "getRecipe", header.getMsg_command() + ":" + json,
								LogExecuter.DEBUG);
						//TRMS-1005
						this.sendMessage(this.taskTimeout(header));

						result = new NCRMessage();
						MsgHeader resultHeader = replyMsg.getHeader();
						resultHeader.setMsg_command(API_LIST.ENDGETRECIPE.getElement());
						resultHeader.setSend_msg_number(null);
						resultHeader.setTransaction_id(null);
						resultHeader.setReply(false);

						result.setHeader(resultHeader);

						MsgResult msgResult = new MsgResult();
						msgResult.setCode(8001);
						msgResult.setStatus(3);
						msgResult.setMessage(commonDBHandler.getErrorDescription(8001));

						result.setResult(msgResult);
					} else {

						result = NCRCommonFunction.JsonStringToNCRMsg(json);
						if (result != null) {

							String command = result.getHeader().getMsg_command();

							if (API_LIST.UPDATEFILEPROGRESS.getElement().equals(command)) {

								//not action : only refresh timeout value - connectivity
							} else if (API_LIST.TASKTIMEOUT.getElement().equals(command)) {
								isProcessing = false;
								LOG.writeLog(header.getTransaction_id(), "getRecipe", "TASKTIMEOUT: " + json,
										LogExecuter.DEBUG);
								result = new NCRMessage();
								MsgHeader resultHeader = replyMsg.getHeader();
								resultHeader.setMsg_command(API_LIST.ENDGETRECIPE.getElement());
								resultHeader.setSend_msg_number(null);
								resultHeader.setReply(false);
								resultHeader.setTransaction_id(null);
								result.setHeader(resultHeader);

								MsgResult msgResult = new MsgResult();
								msgResult.setCode(8001);
								msgResult.setStatus(3);
								msgResult.setMessage(commonDBHandler.getErrorDescription(8001));

								result.setResult(msgResult);
							} else {

								//not define error process : only return message
								LOG.writeLog(header.getTransaction_id(), "getRecipe", json, LogExecuter.DEBUG);

								isProcessing = false;
								result.getHeader().setReply(true);

								if (result.getResult() == null) {
									MsgResult msgResult = new MsgResult();
									msgResult.setCode(0);
									msgResult.setStatus(3);
									msgResult.setMessage(commonDBHandler.getErrorDescription(0));

									result.setResult(msgResult);
								}

								this.sendMessage(result);
							}
						}
					}

					if (!isProcessing) {
						ReplyQueueWF.GetInstance().removeObject(waitObject);
					}

				} catch (Exception e) {

					if (isProcessing) {
						ReplyQueueWF.GetInstance().removeObject(waitObject);
					}

					isProcessing = false;
					LOG.writeErrorLog(e);
				}
			}

			return result;

		} catch (Exception ex) {
			LOG.writeErrorLog(ex);

			throw ex;
		}

	}

	private ArrayList<RecipeFile> getFileList(ArrayList<RecipeUnit> recipeUnitList, boolean isAllFile)
			throws Exception {

		ArrayList<RecipeFile> fileList = null;

		if (recipeUnitList == null || recipeUnitList.size() == 0) {
			return fileList;
		} else {
			fileList = new ArrayList<RecipeFile>();
		}

		for (RecipeUnit recipeUnit : recipeUnitList) {

			Integer unitRole = recipeUnit.getRecipe_unit_role();

			boolean addFile = false;
			if (!isAllFile) {
				String existRecipe = recipeUnit.getExist_recipe();

				if (existRecipe == null || !existRecipe.equals("Y")) {
					addFile = true;
				}
			} else {
				addFile = true;
			}

			if (unitRole != null && unitRole.equals(NCRDefine.RECIPE_UNIT_ROLE_FILE) && addFile) {

				RecipeFile recipeFile = new RecipeFile();
				recipeFile.setApp_id(recipeUnit.getApp_id());
				recipeFile.setRecipe_unit_crc(recipeUnit.getRecipe_unit_crc());
				recipeFile.setRecipe_unit_guid(recipeUnit.getRecipe_unit_guid());
				recipeFile.setRecipe_unit_name(recipeUnit.getRecipe_unit_name());
				recipeFile.setRecipe_unit_type_id(recipeUnit.getRecipe_unit_type_id());
				recipeFile.setRecipe_unit_version_guid(recipeUnit.getRecipe_unit_version_guid());
				recipeFile.setSequence(recipeUnit.getSequence());
				recipeFile.setServer_file_path(NCRCommonFunction.getFilePath(recipeUnit));

				fileList.add(recipeFile);
			}
		}

		return fileList;
	}

	private Integer saveFolderLink(Integer globalFolderId, Integer rcpRawId, String userId,
			RecipeDBHandler recipeDBHandler, DAS2 das) throws Exception {

		Integer result = -1;
		Integer folderLinkRawid = recipeDBHandler.getRecipeFolderLink(rcpRawId, globalFolderId, das);

		if (folderLinkRawid == null || folderLinkRawid == 0) {
			result = recipeDBHandler.insertRecipeFolderLink(rcpRawId, globalFolderId, userId, das);
		} else {
			result = recipeDBHandler.updateFolderLink(folderLinkRawid, rcpRawId, userId, das);
		}

		return result;
	}

	private void setRecipeData(String actionType, RecipeUnit recipeUnit, RecipeDBHandler recipeDBHandler)
			throws Exception {

		Integer recipeRawId = recipeUnit.getRaw_id();

		if (!actionType.equals(NCRDefine.ACTION_TYPE_FM) && !actionType.equals(NCRDefine.ACTION_TYPE_PM)) {

			//set member
			recipeUnit.setMember_list(recipeDBHandler.getRecipeMemberList(recipeRawId, actionType));

			//set log
			recipeUnit.setRecipe_unit_log_entry_list(recipeDBHandler.getRecipeLogList(recipeRawId));
			;
		}

		ArrayList<RecipeUnit> recipeUnitList = recipeUnit.getRecipe_unit_list();
		if (recipeUnitList != null && recipeUnitList.size() > 0) {

			for (RecipeUnit recipeEntry : recipeUnitList) {
				this.setRecipeData(actionType, recipeEntry, recipeDBHandler);
			}
		}
	}

	// TRMS-408
	// Recipe Revision Tree for Compatibility
	private void getRecipeRevisionTree(NCRMessage ncrMessage) throws Exception {
		try {
			NCRRecipeRevisionTree rcpRevisionTree = new NCRRecipeRevisionTree(clientId, loggerName, das2Name, taskId);
			rcpRevisionTree.onCallAPI(ncrMessage);

		} catch (Exception ex) {
			LOG.writeErrorLog(ex);
			throw ex;
		}

	}

	//TRMS-1005
	public NCRMessage taskTimeout(MsgHeader pHeader) {
		MsgHeader msgHeader = new MsgHeader();
		msgHeader.setClient_id(pHeader.getClient_id());
		msgHeader.setUser_id(pHeader.getUser_id());
		msgHeader.setTask_id(pHeader.getTask_id());
		msgHeader.setReply(false);
		msgHeader.setSource(NCRDefine.MESSAGE_HEADER_SOURCE_NCRSERVER);
		msgHeader.setMsg_command(API_LIST.TASKTIMEOUT.getElement());
		msgHeader.setSend_msg_number(null);
		msgHeader.setTransaction_id(null);

		NCRMessage returnMessage = new NCRMessage();
		returnMessage.setHeader(msgHeader);
		returnMessage.setData(null);
		returnMessage.setResult(null);

		return returnMessage;
	}
}
