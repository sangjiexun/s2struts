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
package org.seasar.struts.examples.web.employee.impl;

import java.util.List;

import org.seasar.framework.beans.util.Beans;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.struts.examples.dto.EmployeeDto;
import org.seasar.struts.examples.entity.Department;
import org.seasar.struts.examples.web.CrudType;
import org.seasar.struts.examples.web.employee.EditInitAction;
import org.seasar.struts.examples.web.employee.EmployeeLogic;

/**
 * @author taedium
 * 
 */
public class EditInitActionImpl implements EditInitAction {

    private EmployeeLogic employeeLogic;

    private ListForm listForm;

    private ConfirmForm confirmForm;

    private EditForm editForm;

    private EmployeeDto employeeDto;

    private List<Department> deptItems;

    private String crudType;

    @Binding(bindingType = BindingType.MUST)
    public void setEmployeeLogic(EmployeeLogic employeeLogic) {
        this.employeeLogic = employeeLogic;
    }

    public void setListForm(ListForm listForm) {
        this.listForm = listForm;
    }

    public void setEditForm(EditForm editForm) {
        this.editForm = editForm;
    }

    public void setConfirmForm(ConfirmForm confirmForm) {
        this.confirmForm = confirmForm;
    }

    public List<Department> getDeptItems() {
        return deptItems;
    }

    public void setCrudType(String crudType) {
        this.crudType = crudType;
    }

    public String getCrudType() {
        return crudType;
    }

    public void initialize() {
        if (CrudType.UPDATE.equals(crudType) && listForm != null) {
            int empno = Integer.parseInt(listForm.getEmpno());
            employeeDto = employeeLogic.getEmployeeDto(empno);
            Beans.copy(employeeDto, editForm).dateConverter("yyyy/MM/dd",
                    "hiredate").execute();
        } else if (confirmForm != null) {
            Beans.copy(confirmForm, editForm).execute();
        }
        deptItems = employeeLogic.getDepartmentList();
    }
}
