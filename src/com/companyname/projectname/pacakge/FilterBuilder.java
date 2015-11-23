package com.companyname.projectname.pacakge;

import java.util.ArrayList;
import java.util.List;

import com.companyname.projectname.pacakge.model.MyFilter;
import com.companyname.projectname.pacakge.model.MyFilterExpression;

//This class basically takes a Flexicious Filter, and converts it to an HSQL statment.
public class FilterBuilder {

	public FilterBuilder() {
		parameters = new ArrayList<ObjectParameter>();
	}

	public static String FILTER_OPERATION_TYPE_NONE = "None";
	public static String FILTER_OPERATION_TYPE_EQUALS = "Equals";
	public static String FILTER_OPERATION_TYPE_NOT_EQUALS = "NotEquals";
	public static String FILTER_OPERATION_TYPE_BEGINS_WITH = "BeginsWith";
	public static String FILTER_OPERATION_TYPE_ENDS_WITH = "EndsWith";
	public static String FILTER_OPERATION_TYPE_CONTAINS = "Contains";
	public static String FILTER_OPERATION_TYPE_GREATER_THAN = "GreaterThan";
	public static String FILTER_OPERATION_TYPE_LESS_THAN = "LessThan";
	public static String FILTER_OPERATION_TYPE_GREATERTHANEQUALS = "GreaterThanEquals";
	public static String FILTER_OPERATION_TYPE_LESS_THAN_EQUALS = "LessThanEquals";
	public static String FILTER_OPERATION_TYPE_IN_LIST = "InList";
	public static String FILTER_OPERATION_TYPE_NOT_IN_LIST = "NotInList";
	public static String FILTER_OPERATION_TYPE_BETWEEN = "Between";
	public static String FILTER_OPERATION_TYPE_IS_NOT_NULL = "IsNotNull";
	public static String FILTER_OPERATION_TYPE_IS_NULL = "IsNull";

	private int _paramStartIndex = 0;
	public List<ObjectParameter> parameters;

	public String buildHql(MyFilter filter, String tablePrefix) {
		if (filter == null)
			filter = new MyFilter();

		parameters = new ArrayList<ObjectParameter>();
		int parameterIndex = _paramStartIndex;
		String parameterPrefix = ":";
		StringBuilder sqlStatementBuilder = new StringBuilder(100);

		for (int nFilterExpression = 0; nFilterExpression < filter.arguments.size(); nFilterExpression++) {
			MyFilterExpression filterExpression = (MyFilterExpression) filter.arguments.get(nFilterExpression);

			if (nFilterExpression != 0) {
				sqlStatementBuilder.append(" AND ");
			}
			sqlStatementBuilder.append(tablePrefix + "." + filterExpression.columnName);

			if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_BEGINS_WITH)) {
				String paramName = String.format("param%s", parameterIndex++);
				sqlStatementBuilder.append(" like " + parameterPrefix + paramName);
				parameters.add(new ObjectParameter(paramName, filterExpression.expression + "%", filterExpression.columnName));
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_CONTAINS)) {
				String paramName = String.format("param%s", parameterIndex++);
				sqlStatementBuilder.append(" like " + parameterPrefix + paramName);
				parameters.add(new ObjectParameter(paramName, "%" + filterExpression.expression + "%", filterExpression.columnName));
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_ENDS_WITH)) {
				String paramName = String.format("param%s", parameterIndex++);
				sqlStatementBuilder.append(" like " + parameterPrefix + paramName);
				parameters.add(new ObjectParameter(paramName, "%" + filterExpression.expression, filterExpression.columnName));
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_EQUALS)) {
				String paramName = String.format("param%s", parameterIndex++);
				sqlStatementBuilder.append(" = " + parameterPrefix + paramName);
				parameters.add(new ObjectParameter(paramName, filterExpression.expression, filterExpression.columnName));
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_IS_NOT_NULL)) {
				sqlStatementBuilder.append(" is not null");
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_IS_NULL)) {
				sqlStatementBuilder.append(" is null");
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_NOT_EQUALS)) {
				String paramName = String.format("param%s", parameterIndex++);
				sqlStatementBuilder.append(" <> " + parameterPrefix + paramName);
				parameters.add(new ObjectParameter(paramName, filterExpression.expression, filterExpression.columnName));
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_GREATER_THAN)) {
				String paramName = String.format("param%s", parameterIndex++);
				sqlStatementBuilder.append(" > " + parameterPrefix + paramName);
				parameters.add(new ObjectParameter(paramName, filterExpression.expression, filterExpression.columnName));
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_GREATERTHANEQUALS)) {
				String paramName = String.format("param%s", parameterIndex++);
				sqlStatementBuilder.append(" >= " + parameterPrefix + paramName);
				parameters.add(new ObjectParameter(paramName, filterExpression.expression, filterExpression.columnName));
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_LESS_THAN)) {
				String paramName = String.format("param%s", parameterIndex++);
				sqlStatementBuilder.append(" < " + parameterPrefix + paramName);
				parameters.add(new ObjectParameter(paramName, filterExpression.expression, filterExpression.columnName));
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_LESS_THAN_EQUALS)) {
				String paramName = String.format("param%s", parameterIndex++);
				sqlStatementBuilder.append(" <= " + parameterPrefix + paramName);
				parameters.add(new ObjectParameter(paramName, filterExpression.expression, filterExpression.columnName));
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_IN_LIST) || filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_NOT_IN_LIST)) {
				Object[] inList = (Object[]) filterExpression.expression;
				if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_NOT_IN_LIST)) {
					sqlStatementBuilder.append(" NOT ");
				}

				sqlStatementBuilder.append(" IN (");
				for (int i = 0; i < inList.length; i++) {
					String paramName = String.format("param%s", parameterIndex++);
					sqlStatementBuilder.append(parameterPrefix + paramName + ((i != (inList.length - 1)) ? "," : ""));
					parameters.add(new ObjectParameter(paramName, inList[i], filterExpression.columnName));
				}
				sqlStatementBuilder.append(" )");
			} else if (filterExpression.filterOperation.equals(FILTER_OPERATION_TYPE_BETWEEN)) {
				String rangeStartParamName = String.format("param%s", parameterIndex++);
				String rangeEndParamName = String.format("param%s", parameterIndex++);
				Object[] betweenList = (Object[]) filterExpression.expression;
				if (betweenList.length != 2)
					throw new UnsupportedOperationException("Expression for between operation should only contain two operands: " + filterExpression.columnName);
				sqlStatementBuilder.append(" BETWEEN ");
				sqlStatementBuilder.append(String.format(" %s AND %s", parameterPrefix + rangeStartParamName, parameterPrefix + rangeEndParamName));
				sqlStatementBuilder.append(" ");
				parameters.add(new ObjectParameter(rangeStartParamName, betweenList[0], filterExpression.columnName));
				parameters.add(new ObjectParameter(rangeEndParamName, betweenList[1], filterExpression.columnName));
			} else
				throw new UnsupportedOperationException("FilterExpression not implemented: " + filterExpression.filterOperation);
		}
		return sqlStatementBuilder.toString();
	}

	@SuppressWarnings("rawtypes")
	public String buildSql(MyFilter myFilter, String tablePrefix) {
		String filterQuery = "";
		if (myFilter == null || myFilter.arguments.size() == 0)
			return filterQuery;
		int i = 0;
		for (MyFilterExpression filter : myFilter.arguments) {
			if (i != 0) {
				filterQuery += (" AND ");
			}
			i++;
			filterQuery += tablePrefix+"."+(filter.columnName);

			if (FILTER_OPERATION_TYPE_BEGINS_WITH.equals(filter.filterOperation)) {
				filterQuery += (" LIKE '" + filter.expression + "%' ");
			} else if (FILTER_OPERATION_TYPE_CONTAINS.equals(filter.filterOperation)) {
				filterQuery += (" LIKE '%" + filter.expression + "%' ");
			} else if (FILTER_OPERATION_TYPE_ENDS_WITH.equals(filter.filterOperation)) {
				filterQuery += (" LIKE '%" + filter.expression + "' ");
			} else if (FILTER_OPERATION_TYPE_EQUALS.equals(filter.filterOperation)) {
				filterQuery += (" LIKE '%" + filter.expression + "' ");
			} else if (FILTER_OPERATION_TYPE_IS_NOT_NULL.equals(filter.filterOperation)) {
				filterQuery += (" is not null ");
			} else if (FILTER_OPERATION_TYPE_IS_NULL.equals(filter.filterOperation)) {
				filterQuery += (" is null ");
			} else if (FILTER_OPERATION_TYPE_NOT_EQUALS.equals(filter.filterOperation)) {
				filterQuery += " <> '" + filter.expression + "' ";
			} else if (FILTER_OPERATION_TYPE_GREATER_THAN.equals(filter.filterOperation)) {
				filterQuery += " > " + filter.expression + " ";
			} else if (FILTER_OPERATION_TYPE_GREATERTHANEQUALS.equals(filter.filterOperation)) {
				filterQuery += " >= " + filter.expression + " ";
			} else if (FILTER_OPERATION_TYPE_LESS_THAN.equals(filter.filterOperation)) {
				filterQuery += " < " + filter.expression + " ";
			} else if (FILTER_OPERATION_TYPE_LESS_THAN_EQUALS.equals(filter.filterOperation)) {
				filterQuery += " <= " + filter.expression + " ";
			} else if (FILTER_OPERATION_TYPE_IN_LIST.equals(filter.filterOperation) || FILTER_OPERATION_TYPE_NOT_IN_LIST.equals(filter.filterOperation)) {
				if (!(filter.expression instanceof List))
					continue;
				if (FILTER_OPERATION_TYPE_NOT_IN_LIST.equals(filter.filterOperation)) {
					filterQuery += (" NOT ");
				}
				filterQuery += (" IN (");
				List list = (List) filter.expression;
				int count = list.size();
				for (int k = 0; k < count; k++) {
					filterQuery += "'"+list.get(k)+"'" + ((k != (count - 1)) ? ", " : " ");
				}
				filterQuery += (" ) ");
			} else if (filter.filterOperation.equals(FILTER_OPERATION_TYPE_BETWEEN)) {
				if (!(filter.expression instanceof List))
					continue;
				List betweenList = (List) filter.expression;
				if (betweenList.size() != 2)
					continue;
				filterQuery += (" BETWEEN ");
				if ((filter.filterComparisonType + "").toLowerCase().equals("date"))
					filterQuery += ("'" + betweenList.get(0) + "'" + " AND " + "'" + betweenList.get(1) + "'");
				else
					filterQuery += (betweenList.get(0) + " AND " + betweenList.get(1));
			}
		}
		return filterQuery;
	}

}
