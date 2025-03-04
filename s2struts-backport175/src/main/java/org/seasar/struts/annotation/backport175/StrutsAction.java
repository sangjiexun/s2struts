/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.struts.annotation.backport175;

/**
 * @author Katsuhiko Nagashima
 */
public interface StrutsAction {

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String path();

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String name();

    /**
     * @org.codehaus.backport175.DefaultValue ("request")
     */
    String scope();

    /**
     * @org.codehaus.backport175.DefaultValue (true)
     */
    boolean validate();

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String input();

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String parameter();

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String attribute();

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String forward();

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String include();

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String prefix();

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String suffix();

    /**
     * @org.codehaus.backport175.DefaultValue (false)
     */
    boolean unknown();

    /**
     * @org.codehaus.backport175.DefaultValue ("")
     */
    String roles();
    
    /**
     * @org.codehaus.backport175.DefaultValue (false)
     */
    boolean cancellable();

}
