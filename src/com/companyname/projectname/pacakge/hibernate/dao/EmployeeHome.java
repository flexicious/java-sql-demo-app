package com.companyname.projectname.pacakge.hibernate.dao;

// Generated Dec 26, 2009 1:14:46 AM by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companyname.projectname.pacakge.FilterBuilder;
import com.companyname.projectname.pacakge.ObjectParameter;
import com.companyname.projectname.pacakge.hibernate.persistence.Employee;
import com.companyname.projectname.pacakge.model.EmployeeDTO;
import com.companyname.projectname.pacakge.model.MyFilter;
import com.companyname.projectname.pacakge.model.MyFilterResult;
import com.companyname.projectname.pacakge.model.MyFilterSort;

/**
 * Home object for domain model class Employee.
 * @see com.companyname.projectname.pacakge.hibernate.Employee
 * @author Hibernate Tools
 */
public class EmployeeHome extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(EmployeeHome.class);
	
	/**
	 * Here we take a Flexicious filter and make an HQL 
	 * query out of it.
	 */
	public MyFilterResult findByFilter(MyFilter filter) {
		try {
			FilterBuilder filterBuilder = new FilterBuilder();
			
	        String hql = " from Employee e ";
	        if(filter.arguments.size()>0)
	        	hql += " WHERE " + filterBuilder.buildHql(filter,"e");

	        //create a count query before we apply sort
	        Query countQuery = getSessionFactory().getCurrentSession().createQuery("select count(*) " + hql);
			//we need to always have some sort for paging to work
            if (filter.sorts.size() == 0)
            	hql += " ORDER BY e.employeeId";
            else
            {
            	//the advanced grid sends multiple sorts.
            	hql += " ORDER BY ";
                int sortIndex = 0;
                for(MyFilterSort myFilterSort : filter.sorts)
                {
    	        	hql += String.format("  e.%s %s %s", myFilterSort.sortColumn, myFilterSort.isAscending ? "" : "DESC",(++sortIndex == filter.sorts.size()) ? "" : ",");
                }
                
            }
	        //now create the actual query
			Query query = getSessionFactory().getCurrentSession().createQuery(hql);
			
			//add the parameters to both the actual query and the count query
			for(ObjectParameter parameter : filterBuilder.parameters){
				if(parameter.fieldName.equals("annualSalary")){
					//blazeDs sends the value as int, but we need big decimal
					parameter.value = new BigDecimal((Integer)parameter.value);
				}
				
				query.setParameter(parameter.name, parameter.value);
				countQuery.setParameter(parameter.name, parameter.value);
			}
			int recordCount = (Integer) countQuery.uniqueResult();
			
			
			int pageStart = (filter.pageIndex)*filter.pageSize;
			
			
			@SuppressWarnings("unchecked")
			List<Employee> results = query.list();
			List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();
			
			/* Code to use when sql server driver issue is resolved/
			query.setFirstResult((filter.pageIndex-1)*filter.pageSize);
			query.setFetchSize(filter.pageSize);
			 
			for(Object employeeObject : results ){
				Employee employee = (Employee) employeeObject;
				employees.add(new EmployeeDTO(employee));
			}
			 */
			//workaround for sql server jdbc driver issue
			int i=0;
			for(Employee employee : results ){
				i++;
				if(i>pageStart){
					employees.add(new EmployeeDTO(employee));
					if(employees.size()==filter.pageSize)
						break;
				}
			}
			//end workaround
			log.debug("find by hql, result size: "
					+ results.size());
			
			return new MyFilterResult((ArrayList<EmployeeDTO>) employees,recordCount) ;
		} catch (RuntimeException re) {
			log.error("find by hql failed", re);
			throw re;
		}
		
	}
}
