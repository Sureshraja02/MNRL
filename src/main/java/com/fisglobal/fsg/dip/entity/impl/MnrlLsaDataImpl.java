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

import com.fisglobal.fsg.dip.entity.MnrlLsaData_DAO;

@Repository
public class MnrlLsaDataImpl {

	@PersistenceContext
	EntityManager em;
	
	public MnrlLsaData_DAO getLSAData(String lsaIdParam) {
		CriteriaBuilder cb = null;
		CriteriaQuery<MnrlLsaData_DAO> cq = null;
		List<Predicate> predicates = null;
		Root<MnrlLsaData_DAO> rootBk = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(MnrlLsaData_DAO.class);
			predicates = new ArrayList<Predicate>();

			rootBk = cq.from(MnrlLsaData_DAO.class);
			predicates.add(cb.equal(rootBk.get("lsaId"), lsaIdParam));

			cq.where(predicates.toArray(new Predicate[] {}));
			TypedQuery<MnrlLsaData_DAO> query = em.createQuery(cq);
			return query.getSingleResult();

		} catch (Exception e) {
			return null;
		} finally {
			cb = null; cq = null; predicates = null; rootBk = null;
		}
	}
	public MnrlLsaData_DAO getLSADataUseCode(String lsaCodeParam) {
		CriteriaBuilder cb = null;
		CriteriaQuery<MnrlLsaData_DAO> cq = null;
		List<Predicate> predicates = null;
		Root<MnrlLsaData_DAO> rootBk = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(MnrlLsaData_DAO.class);
			predicates = new ArrayList<Predicate>();

			rootBk = cq.from(MnrlLsaData_DAO.class);
			predicates.add(cb.equal(rootBk.get("lsaCode"), lsaCodeParam));

			cq.where(predicates.toArray(new Predicate[] {}));
			TypedQuery<MnrlLsaData_DAO> query = em.createQuery(cq);
			return query.getSingleResult();

		} catch (Exception e) {
			return null;
		} finally {
			cb = null; cq = null; predicates = null; rootBk = null;
		}
	}
}
