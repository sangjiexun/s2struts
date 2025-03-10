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
package org.seasar.struts.lessconfig.validator.config.impl;

import org.apache.commons.validator.Field;
import org.seasar.struts.lessconfig.validator.config.AbstractRangeConfigRegister;

/**
 * 標準検証ルールのlongRange用の設定を{@link Field}に登録します。
 * 
 * @author Katsuhiko Nagashima
 */
public class LongRangeConfigRegisterImpl extends AbstractRangeConfigRegister {

    protected String getType() {
        return "longRange";
    }

}
