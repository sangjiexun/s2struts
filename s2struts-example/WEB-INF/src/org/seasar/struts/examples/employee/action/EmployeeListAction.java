/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.struts.examples.employee.action;

import org.seasar.struts.annotation.tiger.StrutsAction;
import org.seasar.struts.annotation.tiger.StrutsActionForward;

/**
 * @author Katsuhiko Nagashima
 */
@StrutsAction(name = "employeeForm", parameter = "method")
public interface EmployeeListAction {

    @StrutsActionForward(path = "/pages/employee/employeeEdit.html")
    public String EDIT = "edit";

    @StrutsActionForward(path = "/pages/employee/employeeConfirm.html")
    public String CONFIRM = "confirm";

    public String goEditForUpdate();

    public String goDelete();

    public String goInquire();

}