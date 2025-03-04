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
package org.seasar.struts.pojo.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.WrapDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.BeanValidatorForm;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * S2Strutsにて、{@link ActionForm}相当のPOJOをラップするクラスです。
 * <p>
 * ラップされるPOJOに引数なしのresetという名前のメソッドが存在する場合、{@link ActionForm#reset(ActionMapping, HttpServletRequest)}
 * が実行されるタイミングで自動的に呼び出されます。
 * </p>
 * 
 * @author Katsuhiko Nagashima
 */
public class S2BeanValidatorForm extends BeanValidatorForm {
    private static final long serialVersionUID = 190262930254589604L;

    /**
     * インスタンスを構築します。
     * 
     * @param form
     */
    public S2BeanValidatorForm(BeanValidatorForm form) {
        super(form.getDynaBean());
        if (form.getMultipartRequestHandler() != null) {
            setMultipartRequestHandler(form.getMultipartRequestHandler());
            setServlet(form.getMultipartRequestHandler().getServlet());
        }
    }

    /**
     * ラップ対象のPOJOを設定します。
     * 
     * @param bean
     *            {@link ActionForm}相当のPOJO
     */
    public void initBean(Object bean) {
        super.dynaBean = new WrapDynaBean(bean);
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        Object bean = getInstance();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        if (beanDesc.hasMethod("reset")) {
            beanDesc.invoke(bean, "reset", null);
        }
    }

}
