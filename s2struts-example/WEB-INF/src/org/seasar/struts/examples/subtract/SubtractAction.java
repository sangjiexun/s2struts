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
package org.seasar.struts.examples.subtract;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.seasar.struts.examples.form.CalculationForm;

/**
 * @author Satoshi Kimura
 */
public class SubtractAction extends Action {
    private SubtractService subtractService_;

    public SubtractAction(SubtractService subtractService) {
        subtractService_ = subtractService;
        System.out.println("created new SubtractAction");
    }

	@Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {

        CalculationForm calForm = (CalculationForm) form;
        int result = subtractService_.subtract(calForm.getArg1(), calForm.getArg2());
        calForm.setResult(result);
        return (mapping.findForward("success"));
    }
}