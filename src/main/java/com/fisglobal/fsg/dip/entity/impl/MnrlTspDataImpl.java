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

import org.springframework.stereotype.Repository;

import com.fisglobal.fsg.dip.entity.MnrlTspData_DAO;

@Repository
public class MnrlTspDataImpl {

	@PersistenceContext
	EntityManager em;
	
	public MnrlTspData_DAO getTspDataByTSPId(String tspIdParam) {
		CriteriaBuilder cb = null;
		CriteriaQuery<MnrlTspData_DAO> cq = null;
		List<Predicate> predicates = null;
		Root<MnrlTspData_DAO> rootBk = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(MnrlTspData_DAO.class);
			predicates = new ArrayList<Predicate>();

			rootBk = cq.from(MnrlTspData_DAO.class);
			predicates.add(cb.equal(rootBk.get("tspId"), tspIdParam));

			cq.where(predicates.toArray(new Predicate[] {}));
			TypedQuery<MnrlTspData_DAO> query = em.createQuery(cq);
			return query.getSingleResult();

		} catch (Exception e) {
			return null;
		} finally {
			cb = null; cq = null; predicates = null; rootBk = null;
		}
	}
}
