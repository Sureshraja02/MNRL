package com.fisglobal.fsg.dip.entity.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;

@Repository
public class FriCbsDataImpl {

	@PersistenceContext
	EntityManager em;
	
	public List<FriCbsData_DAO> getFlagCount(String mobileNo, String dataUuid,String cifBlockFlag, String atrUploadFlag) {
		List<FriCbsData_DAO> suspdtslDaoObj = null;
		CriteriaBuilder cb = null;
		CriteriaQuery<FriCbsData_DAO> cq = null;
		Root<FriCbsData_DAO> rootCrtObj = null;
		List<Predicate> predicates = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(FriCbsData_DAO.class);
			rootCrtObj = cq.from(FriCbsData_DAO.class);
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

			TypedQuery<FriCbsData_DAO> query = em.createQuery(cq);
			
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
