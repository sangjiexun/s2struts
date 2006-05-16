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
package org.seasar.struts.processor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.seasar.struts.Constants;
import org.seasar.struts.servlet.http.S2ServletRequestWrapper;

/**
 * @author Satoshi Kimura
 */
public class PopulateProcessorImpl implements PopulateProcessor {
	private ExternalRequestProcessor requestProcessor;

	public void processPopulate(HttpServletRequest request, HttpServletResponse response, ActionForm form, ActionMapping mapping)
			throws ServletException {
		request = new S2ServletRequestWrapper(request);
		addParameterForCheckBox(request);
		this.requestProcessor.processPopulate(request, response, form, mapping);
	}

	private void addParameterForCheckBox(HttpServletRequest request) {
		Set paramNameSet = new HashSet();
		paramNameSet.addAll(request.getParameterMap().keySet());
		for (Iterator paramNames = paramNameSet.iterator(); paramNames.hasNext();) {
			String paramName = (String) paramNames.next();
			if (paramName.startsWith(Constants.CHECKBOX_NAME)) {
				String checkboxParamName = paramName.substring(Constants.CHECKBOX_NAME.length());
				String checkboxValue = request.getParameter(checkboxParamName);
				if (checkboxValue == null) {
					addParam(request, checkboxParamName, Boolean.FALSE.toString());
				}
			}
		}
	}

	private void addParam(HttpServletRequest request, String paramName, String value) {
		if (request instanceof S2ServletRequestWrapper) {
			S2ServletRequestWrapper requestWrapper = (S2ServletRequestWrapper) request;
			requestWrapper.addParameterValue(paramName, value);
		}
	}

	public void setRequestProcessor(ExternalRequestProcessor requestProcessor) {
		this.requestProcessor = requestProcessor;
	}

}
