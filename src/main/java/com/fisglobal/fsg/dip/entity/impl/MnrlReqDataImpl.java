package com.fisglobal.fsg.dip.entity.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.fisglobal.fsg.dip.entity.MnrlReqData_DAO;

@Repository
public class MnrlReqDataImpl {

	@PersistenceContext
	EntityManager em;
	
	
	public List<MnrlReqData_DAO> getReqData(String lsaIdParam) {
		CriteriaBuilder cb = null;
		CriteriaQuery<MnrlReqData_DAO> cq = null;
		List<Predicate> predicates = null;
		Root<MnrlReqData_DAO> rootBk = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(MnrlReqData_DAO.class);
			rootBk = cq.from(MnrlReqData_DAO.class);
			cq.select(rootBk);
			TypedQuery<MnrlReqData_DAO> query = em.createQuery(cq);
			return query.getResultList();

		} catch (Exception e) {
			return null;
		} finally {
			cb = null; cq = null; predicates = null; rootBk = null;
		}
	}
	
}
