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
package org.seasar.struts.lessconfig.config;

import org.apache.struts.config.FormBeanConfig;
import org.seasar.struts.Constants;

/**
 * 無設定Strutsで{@link FormBeanConfig}に関する設定情報を扱うインタフェースです。
 * 
 * @author Satoshi Kimura
 * @author Katsuhiko Nagashima
 */
public interface StrutsActionFormConfig {

    /** {@link #name()}のデフォルト値 */
    String DEFAULT_NAME = Constants.UNDEFINED;

    /**
     * {@link FormBeanConfig#setName(String)}に設定すべき値を返します。
     * 
     * @return {@link FormBeanConfig#setName(String)}に設定すべき値
     */
    String name();

    /** {@link #restricted()}のデフォルト値 */
    Boolean DEFAULT_RESTRICTED = null;

    /**
     * {@link FormBeanConfig#setRestricted(boolean)}に設定すべき値を返します。
     * 
     * @return {@link FormBeanConfig#setRestricted(boolean)}に設定すべき値
     */
    Boolean restricted();

    /** {@link #inherit()}のデフォルト値 */
    String DEFAULT_INHERIT = Constants.UNDEFINED;

    /**
     * {@link FormBeanConfig#setExtends(String)}に設定すべき値を返します。
     * 
     * @return {@link FormBeanConfig#setExtends(String)}に設定すべき値
     */
    String inherit();

}
