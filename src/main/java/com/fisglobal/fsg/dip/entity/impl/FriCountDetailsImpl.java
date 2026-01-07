package com.fisglobal.fsg.dip.entity.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.fisglobal.fsg.dip.entity.FriCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCountDetails_DAO;
@Repository
public class FriCountDetailsImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriCountDetailsImpl.class);

	@PersistenceContext
	EntityManager em;

	/*
	 * public FriCountDetails_DAO getMaxFetchDate() { FriCountDetails_DAO
	 * suspdtslDaoObj = null; CriteriaBuilder cb = null;
	 * CriteriaQuery<FriCountDetails_DAO> cq = null; Root<FriCountDetails_DAO>
	 * rootCrtObj = null; List<Predicate> predicates = null; try { cb =
	 * em.getCriteriaBuilder(); cq = cb.createQuery(FriCountDetails_DAO.class);
	 * rootCrtObj = cq.from(FriCountDetails_DAO.class); predicates = new
	 * ArrayList<Predicate>();
	 * 
	 * cq.select(cb.greatest(rootCrtObj.get("fetchDate")));
	 * cq.where(predicates.toArray(new Predicate[] {}));
	 * TypedQuery<FriCountDetails_DAO> query = em.createQuery(cq);
	 * 
	 * suspdtslDaoObj = query.getSingleResult();
	 * 
	 * } catch (NoResultException e) { LOGGER.info("No Result found");
	 * suspdtslDaoObj = null; } finally { cb = null; cq = null; rootCrtObj = null;
	 * predicates = null; } return suspdtslDaoObj; }
	 */
	
	public List<FriCountDetails_DAO> getFriCountData(String fetchDate, String status,LocalDate date) {
		List<FriCountDetails_DAO> suspdtslDaoObj = null;
		CriteriaBuilder cb = null;
		CriteriaQuery<FriCountDetails_DAO> cq = null;
		Root<FriCountDetails_DAO> rootCrtObj = null;
		List<Predicate> predicates = null;
		try {
			cb = em.getCriteriaBuilder();
			cq = cb.createQuery(FriCountDetails_DAO.class);
			rootCrtObj = cq.from(FriCountDetails_DAO.class);
			predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotBlank(fetchDate))
				predicates.add(cb.equal(rootCrtObj.get("fetchDate"), fetchDate));
			if (StringUtils.isNotBlank(status))
				predicates.add(cb.equal(rootCrtObj.get("status"), status));
			if (date != null) {
				LocalDateTime fromDateTime = date.atStartOfDay(); // 2026-01-05 00:00:00
				LocalDateTime toDateTime = date.plusDays(1).atStartOfDay(); // 2026-01-06 00:00:00
				Predicate entryDatePredicate = cb.and(
						cb.greaterThanOrEqualTo(rootCrtObj.get("entryDate"), fromDateTime),
						cb.lessThan(rootCrtObj.get("entryDate"), toDateTime));
				predicates.add(entryDatePredicate);
			}
			

			cq.where(predicates.toArray(new Predicate[] {}));

			TypedQuery<FriCountDetails_DAO> query = em.createQuery(cq);

			suspdtslDaoObj = query.getResultList();

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
}
