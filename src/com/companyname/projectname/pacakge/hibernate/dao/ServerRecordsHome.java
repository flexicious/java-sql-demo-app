package com.companyname.projectname.pacakge.hibernate.dao;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.companyname.projectname.pacakge.FilterBuilder;
import com.companyname.projectname.pacakge.ObjectParameter;
import com.companyname.projectname.pacakge.hibernate.persistence.ServerRecords;
import com.companyname.projectname.pacakge.model.MyFilter;
import com.companyname.projectname.pacakge.model.MyFilterSort;
import com.companyname.projectname.pacakge.model.MyResponse;
@Transactional("mysqlTxManager")
public class ServerRecordsHome extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ServerRecordsHome.class);

	public ServerRecordsHome(SessionFactory factory) {
		setSessionFactory(factory);
	}
	
	public ServerRecordsHome() {
		
	}
	
	 

	/**
	 * method builded using native sql queries,
	 * because the hql the not yet support subquery alias 
	 * @param filterPageSort
	 * @return
	 */
	public MyResponse getTopLevelData(MyFilter filterPageSort) {
		MyResponse response = new MyResponse();
		if(null == filterPageSort)
			filterPageSort = new MyFilter();
		try {
			FilterBuilder filterBuilder = new FilterBuilder();

			String sql = "select *, (select count(*) from server_records as cs WHERE cs.record_id = s.id) as childCounts" + " from server_records as s ";
			if (filterPageSort.arguments.size() > 0) {
				sql += " WHERE " + filterBuilder.buildSql(filterPageSort, "s");
				sql += " AND s.record_id = 0 ";
			} else {
				sql += " WHERE s.record_id = 0 ";
			}

			// create a count query before we apply sort
			Query countQuery = getSessionFactory().getCurrentSession().createSQLQuery("select count(*) from server_records as s "+ (
					filterPageSort.arguments.size() > 0 ? 
							" WHERE " + filterBuilder.buildSql(filterPageSort, "s") + " AND s.record_id = 0 " :
								" WHERE s.record_id = 0 "
					));
			// we need to always have some sort for paging to work
			if (filterPageSort.sorts.size() == 0)
				sql += " ORDER BY s.id";
			else {
				// the advanced grid sends multiple sorts.
				sql += " ORDER BY ";
				int sortIndex = 0;
				for (MyFilterSort myFilterSort : filterPageSort.sorts) {
					sql += String.format("  s.%s %s %s", myFilterSort.sortColumn, myFilterSort.isAscending ? "" : "DESC", (++sortIndex == filterPageSort.sorts.size()) ? "" : ",");
				}

			}
			// now create the actual query
			Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ServerRecords.class));

			/*// add the parameters to both the actual query and the count query
			for (ObjectParameter parameter : filterBuilder.parameters) {
				query.setParameter(parameter.name, parameter.value);
				countQuery.setParameter(parameter.name, parameter.value);
			}*/
			Object recordCount = countQuery.uniqueResult();

			int pageStart = (filterPageSort.pageIndex) * filterPageSort.pageSize;

			@SuppressWarnings("unchecked")
			List<ServerRecords> results = query.list();
			List<ServerRecords> pagedServerRecords = new ArrayList<ServerRecords>();
			if (filterPageSort.pageSize > 0) {
				int i = 0;
				for (ServerRecords severRecord : results) {
					i++;
					if (i > pageStart) {
						pagedServerRecords.add(severRecord);
						if (pagedServerRecords.size() == filterPageSort.pageSize)
							break;
					}
				}
			} else {
				pagedServerRecords = results;
			}
			// end workaround
			log.debug("find by sql, result size: " + results.size());

			response.success = true;
			response.data = pagedServerRecords;
			response.details.totalRecords = recordCount;
			response.message = "Data Feteched Successfully";
			return response;
		} catch (RuntimeException re) {
			log.error("get top level data failed", re);
			response.success = false;
			response.data = null;
			response.message = re.getLocalizedMessage();
			return response;
		}
	}

	@SuppressWarnings("rawtypes")
	public MyResponse getChildLevelData(MyFilter filterPageSort, Object parentId) {
		MyResponse response = new MyResponse();
		if(null == filterPageSort)
			filterPageSort = new MyFilter();
		try {
			if (parentId == null)
				throw new Exception("Parent Id require to fetch the child level data.");
			if (parentId instanceof List) {
				try {
					parentId = Integer.parseInt(((List) parentId).get(0).toString());
				} catch (Exception e) {
					throw new Exception("Invalid Parent Id");
				}
			}

			FilterBuilder filterBuilder = new FilterBuilder();

			String hql = " from ServerRecords s ";
			if (filterPageSort.arguments.size() > 0) {
				hql += " WHERE " + filterBuilder.buildHql(filterPageSort, "s");
				hql += " AND s.recordId = " + parentId;
			} else {
				hql += " WHERE s.recordId = " + parentId;
			}

			// create a count query before we apply sort
			Query countQuery = getSessionFactory().getCurrentSession().createQuery("select count(*) " + hql);
			// we need to always have some sort for paging to work
			if (filterPageSort.sorts.size() == 0)
				hql += " ORDER BY s.id";
			else {
				// the advanced grid sends multiple sorts.
				hql += " ORDER BY ";
				int sortIndex = 0;
				for (MyFilterSort myFilterSort : filterPageSort.sorts) {
					hql += String.format("  s.%s %s %s", myFilterSort.sortColumn, myFilterSort.isAscending ? "" : "DESC", (++sortIndex == filterPageSort.sorts.size()) ? "" : ",");
				}

			}
			// now create the actual query
			Query query = getSessionFactory().getCurrentSession().createQuery(hql);

			// add the parameters to both the actual query and the count query
			for (ObjectParameter parameter : filterBuilder.parameters) {
				query.setParameter(parameter.name, parameter.value);
				countQuery.setParameter(parameter.name, parameter.value);
			}
			Object recordCount = countQuery.uniqueResult();

			int pageStart = (filterPageSort.pageIndex) * filterPageSort.pageSize;

			@SuppressWarnings("unchecked")
			List<ServerRecords> results = query.list();
			List<ServerRecords> pagedServerRecords = new ArrayList<ServerRecords>();
			if (filterPageSort.pageSize > 0) {
				int i = 0;
				for (ServerRecords severRecord : results) {
					i++;
					if (i > pageStart) {
						pagedServerRecords.add(severRecord);
						if (pagedServerRecords.size() == filterPageSort.pageSize)
							break;
					}
				}
			} else {
				pagedServerRecords = results;
			}
			// end workaround
			log.debug("find by hql, result size: " + results.size());

			response.success = true;
			response.data = pagedServerRecords;
			response.details.totalRecords = recordCount;
			response.message = "Data Feteched Successfully";
			return response;
		} catch (Exception re) {
			log.error("get child data for parent id '" + parentId + "' is failed", re);
			response.success = false;
			response.data = null;
			response.message = re.getLocalizedMessage();
			return response;
		}

	}

}
