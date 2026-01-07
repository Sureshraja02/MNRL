package com.fisglobal.fsg.dip.entity.impl;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fisglobal.fsg.dip.core.cbs.service.MnrlAsyncService;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;

@Repository
public class MnrlCbsDataImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlCbsDataImpl.class);
	
	@PersistenceContext
	EntityManager em;

	public MnrlCbsData_DAO getMnrlCbsData(String mobileNo, String cif, String accountNo, String uuid) {
		MnrlCbsData_DAO suspdtslDaoObj = null;
		CriteriaBuilder cb = null;
		CriteriaQuery<MnrlCbsData_DAO> cq = null;
		Root<MnrlCbsData_DAO> rootCrtObj = null;
		List<Predicate> predicates = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(MnrlCbsData_DAO.class);
			rootCrtObj = cq.from(MnrlCbsData_DAO.class);
			predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotBlank(mobileNo))
				predicates.add(cb.equal(rootCrtObj.get("mobileNo"), mobileNo));
			if (StringUtils.isNotBlank(cif))
				predicates.add(cb.equal(rootCrtObj.get("cif"), cif));
			if (StringUtils.isNotBlank(accountNo))
				predicates.add(cb.equal(rootCrtObj.get("accountNo"), accountNo));
			if (StringUtils.isNotBlank(uuid))
				predicates.add(cb.equal(rootCrtObj.get("uuid"), uuid));

			cq.where(predicates.toArray(new Predicate[] {}));

			TypedQuery<MnrlCbsData_DAO> query = em.createQuery(cq);

			suspdtslDaoObj = query.getSingleResult();

		} catch (NoResultException e) {
			LOGGER.info("No Result found");
			suspdtslDaoObj = null;
		} finally {
			cb = null;
			cq = null;
			rootCrtObj = null;
			predicates = null;
		}
		return suspdtslDaoObj;
	}

	public List<MnrlCbsData_DAO> getFlagCount(String mobileNo, String dataUuid, String cifBlockFlag,
			String atrUploadFlag) {
		List<MnrlCbsData_DAO> suspdtslDaoObj = null;
		CriteriaBuilder cb = null;
		CriteriaQuery<MnrlCbsData_DAO> cq = null;
		Root<MnrlCbsData_DAO> rootCrtObj = null;
		List<Predicate> predicates = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(MnrlCbsData_DAO.class);
			rootCrtObj = cq.from(MnrlCbsData_DAO.class);
			predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotBlank(mobileNo))
				predicates.add(cb.equal(rootCrtObj.get("mobileNo"), mobileNo));
			if (StringUtils.isNotBlank(dataUuid))
				predicates.add(cb.equal(rootCrtObj.get("dataUuid"), dataUuid));
			if (StringUtils.isNotBlank(cifBlockFlag))
				predicates.add(cb.equal(rootCrtObj.get("cifBlockFlag"), cifBlockFlag));
			if (StringUtils.isNotBlank(atrUploadFlag))
				predicates.add(cb.equal(rootCrtObj.get("atrUploadFlag"), atrUploadFlag));

			cq.where(predicates.toArray(new Predicate[] {}));

			TypedQuery<MnrlCbsData_DAO> query = em.createQuery(cq);
			
				suspdtslDaoObj = query.getResultList();
			

		} finally {
			cb = null;
			cq = null;
			rootCrtObj = null;
			predicates = null;
		}
		return suspdtslDaoObj;
	}

}
