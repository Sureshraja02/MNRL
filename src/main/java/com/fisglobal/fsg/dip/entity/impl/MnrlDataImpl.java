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

import com.fisglobal.fsg.dip.entity.MnrlData_DAO;

@Repository
public class MnrlDataImpl {

	@PersistenceContext
	EntityManager em;

	public MnrlData_DAO getMNRLDataUseMobileNo(String mobNoParam) {
		CriteriaBuilder cb = null;
		CriteriaQuery<MnrlData_DAO> cq = null;
		List<Predicate> predicates = null;
		Root<MnrlData_DAO> rootBk = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(MnrlData_DAO.class);
			predicates = new ArrayList<Predicate>();

			rootBk = cq.from(MnrlData_DAO.class);
			predicates.add(cb.equal(rootBk.get("mobileNo"), mobNoParam));

			cq.where(predicates.toArray(new Predicate[] {}));
			TypedQuery<MnrlData_DAO> query = em.createQuery(cq);
			return query.getSingleResult();

		} catch (Exception e) {
			return null;
		} finally {
			cb = null;
			cq = null;
			predicates = null;
			rootBk = null;
		}
	}

}
