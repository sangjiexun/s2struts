<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <html:base />
  <link href="../../css/global.css" rel="stylesheet" type="text/css"></link>
  <script type="text/javascript" src="../../js/execute.js"></script>
  <title id="pageTitle">
    Edit - Employee Management Demo
  </title>
</head>
<body>
<html:errors/>
<html:form action="employee_edit">
  <s2struts:init action="#{employee_editInitAction.initialize}"/>
  <bean:define id="form" name="editForm" />
  <html:hidden property="crudType" value="${crudType}" />
  <table class="tablebg">
    <tr>
      <td class="label"><span id="labelEmpno">EmployeeNo</span></td>
      <td>
        <c:if test="${crudType == 'c'}">
            <html:text property="empno" styleClass="number" errorStyleClass="number-error" />
        </c:if>
        <c:if test="${crudType == 'u'}">
            <html:hidden property="versionNo" />
            <html:hidden property="empno" />
            <bean:write  name="form" property="empno" />
        </c:if>
      </td>
    </tr>
    <tr>
      <td class="label"><span id="labelEname">EmployeeName</span></td>
      <td>
        <html:text property="ename" styleClass="text" errorStyleClass="text-error" />
      </td>
    </tr>
    <tr>
      <td class="label"><span id="labelJob">Job</span></td>
      <td>
        <html:text property="job" styleClass="text" errorStyleClass="text-error" />
      </td>
    </tr>
    <tr>
      <td class="label"><span id="labelMgr">Manager</span></td>
      <td>
        <html:text property="mgr" styleClass="text" errorStyleClass="text-error" />
      </td>
    </tr>
    <tr>
      <td class="label"><span id="labelHiredate">HireDate</span></td>
      <td>
        <html:text property="hiredate" styleClass="date" errorStyleClass="date-error" />
      </td>
    </tr>
    <tr>
      <td class="label"><span id="labelSal">Salary</span></td>
      <td>
        <html:text property="sal" styleClass="number" errorStyleClass="number-error" />
      </td>
    </tr>
    <tr>
      <td class="label"><span id="labelComm">Commission</span></td>
      <td>
        <html:text property="comm" styleClass="number" errorStyleClass="number-error" />
      </td>
    </tr>
    <tr>
      <td class="label"><span id="labelDeptno">Department</span></td>
      <td>
	    <html:select property="deptno" errorStyleClass="error">
	        <html:option value="" key="messages.select.space" />
	        <html:options collection="deptItems" property="deptno" labelProperty="dname" />
	    </html:select>
      </td>
    </tr>
  </table>
  <s2struts:submit action="#{employee_editAction.goConfirm}"><bean:message key="button.confirm"/></s2struts:submit>
  <s2struts:submit action="#{employee_editAction.goPrevious}" cancel="true"><bean:message key="button.prev"/></s2struts:submit>
</html:form>
</body>
</html>