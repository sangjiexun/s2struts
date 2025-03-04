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
package org.seasar.struts.hotdeploy;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;

/**
 * {@link ModuleConfig#findActionConfig(String)}実行時に{@link ActionConfig}を再ロードするインターセプタです。
 * 
 * @author Katsuhiko Nagashima
 */
public class ReloadFindActionConfigInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -4845448967172308669L;

    private ModuleConfigLoader moduleConfigLoader;

    /**
     * {@link ModuleConfigLoader}を設定します。
     * 
     * @param moduleConfigLoader
     */
    public void setModuleConfigLoader(ModuleConfigLoader moduleConfigLoader) {
        this.moduleConfigLoader = moduleConfigLoader;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        ModuleConfig config = (ModuleConfig) invocation.getThis();
        String path = (String) invocation.getArguments()[0];

        ModuleConfig reloadConfig = this.moduleConfigLoader.load(config.getPrefix());
        ActionConfig[] actionConfigs = reloadConfig.findActionConfigs();
        for (int i = 0; i < actionConfigs.length; i++) {
            if (path.equals(actionConfigs[i].getPath())) {
                return actionConfigs[i];
            }
        }
        return invocation.proceed();
    }

}
