package com.companyname.projectname.pacakge.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companyname.projectname.pacakge.hibernate.persistence.Department;

/**
 * Home object for domain model class Department.
 * @see com.companyname.projectname.pacakge.hibernate.Department
 * @author Hibernate Tools
 */
public class DepartmentHome extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(DepartmentHome.class);

	
	public List<Department> findAll() {
		log.debug("finding Department instance by example");
		try {
			@SuppressWarnings("unchecked")
			List<Department> results = getSessionFactory().getCurrentSession().createCriteria(
					"com.companyname.projectname.pacakge.hibernate.persistence.Department")
					.list();
			log.debug("find All successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
