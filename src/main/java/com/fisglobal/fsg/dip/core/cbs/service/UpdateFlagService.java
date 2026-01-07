package com.fisglobal.fsg.dip.core.cbs.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.fri.cbs.service.FriAccountService;
import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.impl.FriCbsDataImpl;
import com.fisglobal.fsg.dip.entity.impl.MnrlCbsDataImpl;

@Service
public class UpdateFlagService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateFlagService.class);

	@Value("#{'${cbs.debit.freeze.reason.code}'.split(',')}")
	List<String> debitFreezeList;

	@Value("#{'${cbs.cif.block.reason.code}'.split(',')}")
	List<String> cifBlockList;

	@Value("#{'${cbs.mob.removal.reason.code}'.split(',')}")
	List<String> mobRemovalList;

	@Value("#{'${cbs.atr.reason.code}'.split(',')}")
	List<String> AtrList;

	@Value("#{'${fri.cbs.cif.block.reason.code}'.split(',')}")
	List<String> friCifBlockList;

	@Value("#{'${fri.cbs.atr.reason.code}'.split(',')}")
	List<String> friAtrList;

	@Inject
	private FriCbsDataImpl friCbsDataImpl;

	@Inject
	private AccountService accountService;

	@Inject
	private FriAccountService friAccountService;

	@Inject
	private MnrlCbsDataImpl mnrlCbsImpl;

	/*
	 * public MnrlCbsData_DAO setFlags(MnrlCbsData_DAO mnrlCbsData, boolean
	 * exitDataflag, boolean mobileRemovalFlag) { String mrnlReasonId =
	 * mnrlCbsData.getMnrlReason(); if (debitFreezeList.contains(mrnlReasonId)) {
	 * mnrlCbsData.setDebitFreezeFlag("Y"); } else {
	 * mnrlCbsData.setDebitFreezeFlag("N"); } if (exitDataflag) { if
	 * (cifBlockList.contains(mrnlReasonId)) { if
	 * (!accountService.cifBlockMap.containsKey(mnrlCbsData.getMobileNo())) { //
	 * LOGGER.info("CIFBLOCK is Y"); mnrlCbsData.setCifBlockFlag("Y");
	 * accountService.cifBlockMap.put(mnrlCbsData.getMobileNo(),
	 * mnrlCbsData.getMobileNo()); } else { // LOGGER.info("CIFBLOCK is X");
	 * mnrlCbsData.setCifBlockFlag("N"); } } else { // LOGGER.info("CIFBLOCK is N");
	 * mnrlCbsData.setCifBlockFlag("N"); } } else { // LOGGER.info("CIFBLOCK is Z");
	 * mnrlCbsData.setCifBlockFlag("N"); } if (mobileRemovalFlag) { if
	 * (mobRemovalList.contains(mrnlReasonId)) {
	 * mnrlCbsData.setMobileRemovalFlag("Y"); } else {
	 * mnrlCbsData.setMobileRemovalFlag("N"); } } else {
	 * mnrlCbsData.setMobileRemovalFlag("N"); }
	 * 
	 * if (exitDataflag) { if (AtrList.contains(mrnlReasonId)) { if
	 * (!accountService.atrMap.containsKey(mnrlCbsData.getMobileNo())) { //
	 * LOGGER.info("ATR is Y"); mnrlCbsData.setAtrUploadFlag("Y");
	 * accountService.atrMap.put(mnrlCbsData.getMobileNo(),
	 * mnrlCbsData.getMobileNo()); } else { // LOGGER.info("ATR is X");
	 * mnrlCbsData.setAtrUploadFlag("N"); } } else { // LOGGER.info("ATR is N");
	 * mnrlCbsData.setAtrUploadFlag("N"); } } else { // LOGGER.info("ATR is Z");
	 * mnrlCbsData.setAtrUploadFlag("N"); } return mnrlCbsData; }
	 */

	public MnrlCbsData_DAO setFlags(MnrlCbsData_DAO mnrlCbsData, boolean mobileRemovalFlag) {
		String mrnlReasonId = mnrlCbsData.getMnrlReason();
		if (debitFreezeList.contains(mrnlReasonId)) {
			mnrlCbsData.setDebitFreezeFlag("Y");
		} else {
			mnrlCbsData.setDebitFreezeFlag("N");
		}

		if (cifBlockList.contains(mrnlReasonId)) {
			List<MnrlCbsData_DAO> cnt = mnrlCbsImpl.getFlagCount(mnrlCbsData.getMobileNo(), mnrlCbsData.getDataUuid(),
					"Y", null);

			LOGGER.debug("cif block flag count[{}]", cnt.size());
			if (cnt.size() == 0) {

				mnrlCbsData.setCifBlockFlag("Y");

			} else {

				mnrlCbsData.setCifBlockFlag("N");
			}
		} else {
			// LOGGER.info("CIFBLOCK is N");
			mnrlCbsData.setCifBlockFlag("N");
		}

		if (mobileRemovalFlag) {
			if (mobRemovalList.contains(mrnlReasonId)) {
				mnrlCbsData.setMobileRemovalFlag("Y");
			} else {
				mnrlCbsData.setMobileRemovalFlag("N");
			}
		} else {
			mnrlCbsData.setMobileRemovalFlag("N");
		}

		if (AtrList.contains(mrnlReasonId)) {
			List<MnrlCbsData_DAO> cnt = mnrlCbsImpl.getFlagCount(mnrlCbsData.getMobileNo(), mnrlCbsData.getDataUuid(),
					null, "Y");

			LOGGER.debug("atr flag count[{}]", cnt.size());
			if (cnt.size() == 0) {
				// LOGGER.info("ATR is Y");
				mnrlCbsData.setAtrUploadFlag("Y");
				// accountService.atrMap.put(mnrlCbsData.getMobileNo(),
				// mnrlCbsData.getMobileNo());
			} else {
				// LOGGER.info("ATR is X");
				mnrlCbsData.setAtrUploadFlag("N");
			}
		} else {
			// LOGGER.info("ATR is N");
			mnrlCbsData.setAtrUploadFlag("N");
		}

		return mnrlCbsData;
	}

	/*
	 * public FriCbsData_DAO setFriFlags(FriCbsData_DAO friCbsData, boolean
	 * exitDataflag) { String friReasonId = friCbsData.getFriReason();
	 * LOGGER.info("FRI indicator [{}]", friReasonId); if (exitDataflag) { if
	 * (friCifBlockList.contains(friReasonId)) { if
	 * (!friAccountService.cifBlockMap.containsKey(friCbsData.getMobileNo())) { //
	 * LOGGER.info("CIFBLOCK is Y"); friCbsData.setCifBlockFlag("Y");
	 * friAccountService.cifBlockMap.put(friCbsData.getMobileNo(),
	 * friCbsData.getMobileNo()); } else { // LOGGER.info("CIFBLOCK is X");
	 * friCbsData.setCifBlockFlag("N"); } } else { // LOGGER.info("CIFBLOCK is N");
	 * friCbsData.setCifBlockFlag("N"); } } else { // LOGGER.info("CIFBLOCK is Z");
	 * friCbsData.setCifBlockFlag("N"); }
	 * 
	 * if (exitDataflag) { if (friAtrList.contains(friReasonId)) { if
	 * (!friAccountService.atrMap.containsKey(friCbsData.getMobileNo())) { //
	 * LOGGER.info("ATR is Y"); friCbsData.setAtrUploadFlag("Y");
	 * friAccountService.atrMap.put(friCbsData.getMobileNo(),
	 * friCbsData.getMobileNo()); } else { // LOGGER.info("ATR is X");
	 * friCbsData.setAtrUploadFlag("N"); } } else { // LOGGER.info("ATR is N");
	 * friCbsData.setAtrUploadFlag("N"); } } else { // LOGGER.info("ATR is Z");
	 * friCbsData.setAtrUploadFlag("N"); }
	 * LOGGER.info("CifBlockFlag [{}],AtrFlag [{}]", friCbsData.getCifBlockFlag(),
	 * friCbsData.getAtrUploadFlag()); return friCbsData; }
	 */

	public FriCbsData_DAO setFriFlags(FriCbsData_DAO friCbsData) {
		String friReasonId = friCbsData.getFriReason();
		LOGGER.info("FRI indicator [{}]", friReasonId);

		if (friCifBlockList.contains(friReasonId)) {
			List<FriCbsData_DAO> cnt = friCbsDataImpl.getFlagCount(friCbsData.getMobileNo(), friCbsData.getDataUuid(),
					"Y", null);
			LOGGER.debug("cif block flag count[{}]", cnt.size());
			if (cnt.size() == 0) {
				// LOGGER.info("CIFBLOCK is Y");
				friCbsData.setCifBlockFlag("Y");

			} else {
				// LOGGER.info("CIFBLOCK is X");
				friCbsData.setCifBlockFlag("N");
			}
		} else {
			// LOGGER.info("CIFBLOCK is N");
			friCbsData.setCifBlockFlag("N");
		}

		if (friAtrList.contains(friReasonId)) {
			List<FriCbsData_DAO> cnt = friCbsDataImpl.getFlagCount(friCbsData.getMobileNo(), friCbsData.getDataUuid(),
					null, "Y");
			LOGGER.debug("atr block flag count[{}]", cnt.size());
			if (cnt.size() == 0) {
				// LOGGER.info("ATR is Y");
				friCbsData.setAtrUploadFlag("Y");
				// friAccountService.atrMap.put(friCbsData.getMobileNo(),
				// friCbsData.getMobileNo());
			} else {
				// LOGGER.info("ATR is X");
				friCbsData.setAtrUploadFlag("N");
			}
		} else {
			// LOGGER.info("ATR is N");
			friCbsData.setAtrUploadFlag("N");
		}

		LOGGER.info("CifBlockFlag [{}],AtrFlag [{}]", friCbsData.getCifBlockFlag(), friCbsData.getAtrUploadFlag());
		return friCbsData;
	}
}
