/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.struts.examples.web.employee;

import java.util.List;

import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.struts.examples.dao.DepartmentDao;
import org.seasar.struts.examples.dao.EmployeeDao;
import org.seasar.struts.examples.dto.EmployeeDto;
import org.seasar.struts.examples.dto.EmployeeSearchDto;
import org.seasar.struts.examples.entity.Department;
import org.seasar.struts.examples.entity.Employee;

/**
 * 
 * @author taedium
 * 
 */
public class EmployeeService {

    private EmployeeDao employeeDao;

    private DepartmentDao departmentDao;

    @Binding(bindingType = BindingType.MUST)
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Binding(bindingType = BindingType.MUST)
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public List<EmployeeDto> getEmployeeDtoList(EmployeeSearchDto dto) {
        return employeeDao.getEmployeeDtoList(dto);
    }

    public EmployeeDto getEmployeeDto(int empno) {
        return employeeDao.getEmployeeDto(empno);
    }

    public List<Department> getDepartmentList() {
        return departmentDao.getDepartmentList();
    }

    public String getDeptno(int deptno) {
        return departmentDao.getDepartment(deptno).getDname();
    }

    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }

    public void insert(Employee employee) {
        employeeDao.insert(employee);
    }

    public void update(Employee employee) {
        employeeDao.update(employee);
    }
}
