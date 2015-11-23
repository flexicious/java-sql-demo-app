package com.companyname.projectname.pacakge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.companyname.projectname.pacakge.hibernate.dao.DepartmentHome;
import com.companyname.projectname.pacakge.hibernate.dao.EmployeeHome;
import com.companyname.projectname.pacakge.hibernate.persistence.Department;
import com.companyname.projectname.pacakge.model.DepartmentDTO;
import com.companyname.projectname.pacakge.model.MyFilter;
import com.companyname.projectname.pacakge.model.MyFilterResult;


public class FlexService {

	private EmployeeHome employeeHome;
	private DepartmentHome departmentHome;
	
	public EmployeeHome getEmployeeHome() {
		return employeeHome;
	}

	public void setEmployeeHome(EmployeeHome employeeHome) {
		this.employeeHome = employeeHome;
	}

	public DepartmentHome getDepartmentHome() {
		return departmentHome;
	}

	public void setDepartmentHome(DepartmentHome departmentHome) {
		this.departmentHome = departmentHome;
	}

	@Transactional
	public MyFilterResult getEmployees(MyFilter filter)
    {
		/// There are 2 different methods of handling our flex filter
        /// 1) Filtering Explicitly, where we manually handle each of the 
        /// filters arguments - the filterExplicit function does this.
        /// 2) Filtering Automatically, where we use Entity SQL generation (This example)
        /// or HQL Generation (Refer to the Spring/Hibernate example), or 
        /// raw SQL generation (Refer to the PHP example)
        
        //return filterExplicit(filter);
        // OR we could also use
        return filterDynamic(filter);
    }
	
	private MyFilterResult filterDynamic(MyFilter filter)
    {
        return employeeHome.findByFilter(filter);
    }
    @SuppressWarnings("unused")
	private MyFilterResult filterExplicit(MyFilter filter)
    {
    	//Look at the .net example for an sample implementation
    	return null;
    }
    //supporting methods
    @Transactional
    public List<DepartmentDTO> getAllDepartments()
    {
    	List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
    	for(Object departmentObject :  departmentHome.findAll()){
    		Department department = (Department) departmentObject;
    		departments.add(new DepartmentDTO(department.getDepartmentName(), department.getDepartmentId()));
    	}
    	return departments;
    }
}
