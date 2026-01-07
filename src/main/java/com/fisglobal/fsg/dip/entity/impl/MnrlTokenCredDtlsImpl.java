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

import com.fisglobal.fsg.dip.entity.MnrlTokenCredDtls_DAO;

@Repository
public class MnrlTokenCredDtlsImpl {

	@PersistenceContext
	EntityManager em;
	
	public MnrlTokenCredDtls_DAO getAuthDetails(Long id) {
		CriteriaBuilder cb = null;
		CriteriaQuery<MnrlTokenCredDtls_DAO> cq = null;
		List<Predicate> predicates = null;
		Root<MnrlTokenCredDtls_DAO> rootBk = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(MnrlTokenCredDtls_DAO.class);
			predicates = new ArrayList<Predicate>();

			rootBk = cq.from(MnrlTokenCredDtls_DAO.class);
			predicates.add(cb.equal(rootBk.get("id"), id));

			cq.where(predicates.toArray(new Predicate[] {}));
			TypedQuery<MnrlTokenCredDtls_DAO> query = em.createQuery(cq);
			return query.getSingleResult();

		} catch (Exception e) {
			return null;
		} finally {
			cb = null; cq = null; predicates = null; rootBk = null;
		}
	}
}
