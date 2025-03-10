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
package org.seasar.struts.lessconfig.autoregister.impl;

import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.seasar.struts.lessconfig.autoregister.ActionFormConfigCreator;
import org.seasar.struts.lessconfig.config.AutoStrutsConfigRule;
import org.seasar.struts.lessconfig.config.NullStrutsActionFormConfig;
import org.seasar.struts.lessconfig.config.StrutsActionFormConfig;
import org.seasar.struts.lessconfig.config.rule.ActionFormNamingRule;
import org.seasar.struts.lessconfig.config.rule.ZeroConfigActionFormRule;
import org.seasar.struts.lessconfig.factory.StrutsConfigAnnotationHandler;
import org.seasar.struts.lessconfig.factory.StrutsConfigAnnotationHandlerFactory;

/**
 * {@link ActionFormConfigCreator}の実装クラスです。
 * 
 * @author Katsuhiko Nagashima
 */
public class ActionFormConfigCreatorImpl implements ActionFormConfigCreator {

    private ZeroConfigActionFormRule defaultRule;

    /**
     * {@link ZeroConfigActionFormRule}を設定します。
     * 
     * @param defaultRule
     */
    public void setDefaultRule(ZeroConfigActionFormRule defaultRule) {
        this.defaultRule = defaultRule;
    }

    private AutoStrutsConfigRule configRule;

    /**
     * {@link AutoStrutsConfigRule}を設定します。
     * 
     * @param configRule
     */
    public void setConfigRule(AutoStrutsConfigRule configRule) {
        this.configRule = configRule;
    }

    private ActionFormNamingRule namingRule;

    /**
     * {@link ActionFormNamingRule}を設定します。
     * 
     * @param namingRule
     */
    public void setNamingRule(ActionFormNamingRule namingRule) {
        this.namingRule = namingRule;
    }

    public FormBeanConfig createFormBeanConfig(ModuleConfig config, String name) {
        Class formClass = this.namingRule.toComponentClass(name);
        if (formClass == null) {
            return null;
        }
        return createFormBeanConfig(config, formClass, name);
    }

    public FormBeanConfig createFormBeanConfig(ModuleConfig config, Class formClass) {
        String name = this.namingRule.toActionFormName(formClass);
        if (name == null) {
            return null;
        }
        return createFormBeanConfig(config, formClass, name);
    }

    private FormBeanConfig createFormBeanConfig(ModuleConfig config, Class formClass, String name) {
        StrutsConfigAnnotationHandler annHandler = StrutsConfigAnnotationHandlerFactory.getAnnotationHandler();
        StrutsActionFormConfig strutsActionForm = annHandler.createStrutsActionFormConfig(formClass);
        if (!formClass.getName().matches(this.configRule.getFormClassPattern())) {
            return null;
        }
        if (strutsActionForm == null) {
            strutsActionForm = new NullStrutsActionFormConfig();
        }

        FormBeanConfig formBeanConfig = this.defaultRule
                .createFormBeanConfig(config, formClass, name, strutsActionForm);

        return formBeanConfig;
    }

}
