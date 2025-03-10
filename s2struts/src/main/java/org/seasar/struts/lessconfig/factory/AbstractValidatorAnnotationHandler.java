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
package org.seasar.struts.lessconfig.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.lessconfig.config.rule.CommonNamingRule;
import org.seasar.struts.lessconfig.validator.config.ConfigRegister;

/**
 * {@link ValidatorAnnotationHandler}の実装のための抽象クラスです。
 * 
 * @author Katsuhiko Nagashima
 */
public abstract class AbstractValidatorAnnotationHandler implements ValidatorAnnotationHandler {

    private static final String VALIDATOR_TYPE_PREFIX_RE = "Type$";

    public Form createForm(String formName, Class formClass) {
        Form form = new Form();
        form.setName(formName);

        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(formClass);
        registerFields(form, new Field(), beanDesc);
        return form;
    }

    protected void registerFields(Form form, Field field, BeanDesc beanDesc) {
        List propDescs = new ArrayList();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); i++) {
            PropertyDesc propDesc = beanDesc.getPropertyDesc(i);
            if (!hasMethodForValidation(propDesc)) {
                continue;
            }
            if (registeredField(form, field, propDesc)) {
                continue;
            }
            if (noValidate(beanDesc, propDesc)) {
                continue;
            }
            propDescs.add(propDesc);
        }
        Comparator comparator = getPropertyDescComparator(beanDesc);
        Collections.sort(propDescs, comparator);

        for (Iterator itr = propDescs.iterator(); itr.hasNext();) {
            PropertyDesc propDesc = (PropertyDesc) itr.next();
            registerField(form, field, beanDesc, propDesc);
        }
    }

    protected void registerField(Form form, Field field, BeanDesc beanDesc, PropertyDesc propDesc) {
        String depends = getDepends(beanDesc, propDesc);
        if (depends == null) {
            if (isNestedValidate(propDesc)) {
                Field nestedField = createField(field, propDesc, "");
                Class nestedClass = propDesc.getPropertyType();
                if (nestedClass.isArray()) {
                    nestedClass = nestedClass.getComponentType();
                }
                BeanDesc nestedBeanDesc = BeanDescFactory.getBeanDesc(nestedClass);
                registerFields(form, nestedField, nestedBeanDesc);
            }
            return;
        }

        Field newField = createField(field, propDesc, depends);
        registerMessage(newField, beanDesc, propDesc);

        if (hasArgsAnnotation(beanDesc, propDesc)) {
            registerArgs(newField, beanDesc, propDesc);
        } else if (hasArgAnnotation(beanDesc, propDesc)) {
            registerArg(newField, beanDesc, propDesc);
        } else {
            registerDefaultArgs(newField, propDesc);
        }

        registerConfig(newField, beanDesc, propDesc);

        form.addField(newField);
    }

    protected boolean registeredField(Form form, Field field, PropertyDesc propDesc) {
        String key = getFieldKey(field, propDesc);
        return form.getField(key) != null;
    }

    protected boolean isNestedValidate(PropertyDesc propDesc) {
        Method method = getMethodForValidation(propDesc);
        if (method.getDeclaringClass().isAssignableFrom(ActionForm.class)) {
            return false;
        }
        if (method.getDeclaringClass().isAssignableFrom(ValidatorForm.class)) {
            return false;
        }

        Class paramType = propDesc.getPropertyType();
        if (paramType.isArray()) {
            paramType = paramType.getComponentType();
        }

        if (Date.class.isAssignableFrom(paramType)) {
            return false;
        } else if (Number.class.isAssignableFrom(paramType)) {
            return false;
        } else if (paramType.equals(String.class)) {
            return false;
        }

        return true;
    }

    protected Field createField(Field field, PropertyDesc propDesc, String depends) {
        Field newField = new Field();
        newField.setDepends(depends);

        String property = getFieldProperty(field, propDesc);
        if (propDesc.getPropertyType().isArray()) {
            newField.setProperty("");
            newField.setIndexedListProperty(property);
        } else {
            newField.setProperty(property);
            newField.setIndexedListProperty(field.getIndexedListProperty());
        }

        return newField;
    }

    protected String getFieldProperty(Field field, PropertyDesc propDesc) {
        if (StringUtil.isEmpty(field.getProperty())) {
            return propDesc.getPropertyName();
        } else {
            return field.getProperty() + "." + propDesc.getPropertyName();
        }
    }

    protected String getFieldKey(Field field, PropertyDesc propDesc) {
        String key = getFieldProperty(field, propDesc);
        if (!StringUtil.isEmpty(field.getIndexedListProperty())) {
            key = field.getIndexedListProperty() + "[]." + key;
        }
        return key;
    }

    protected abstract Comparator getPropertyDescComparator(BeanDesc beanDesc);

    protected abstract boolean noValidate(BeanDesc beanDesc, PropertyDesc propDesc);

    protected abstract String getDepends(BeanDesc beanDesc, PropertyDesc propDesc);

    protected abstract void registerMessage(Field field, BeanDesc beanDesc, PropertyDesc propDesc);

    protected abstract boolean hasArgsAnnotation(BeanDesc beanDesc, PropertyDesc propDesc);

    protected abstract void registerArgs(Field field, BeanDesc beanDesc, PropertyDesc propDesc);

    protected abstract boolean hasArgAnnotation(BeanDesc beanDesc, PropertyDesc propDesc);

    protected abstract void registerArg(Field field, BeanDesc beanDesc, PropertyDesc propDesc);

    protected abstract void registerConfig(Field field, BeanDesc beanDesc, PropertyDesc propDesc);

    // -----------------------------------------------------------------------

    protected boolean hasMethodForValidation(PropertyDesc propDesc) {
        return (propDesc.hasWriteMethod() && propDesc.hasReadMethod());
    }

    protected Method getMethodForValidation(PropertyDesc propDesc) {
        return propDesc.getWriteMethod();
    }

    protected String getAutoTypeValidatorName(PropertyDesc propDesc) {
        Class paramType = propDesc.getPropertyType();
        if (paramType.isArray()) {
            paramType = paramType.getComponentType();
        }

        if (paramType.equals(Byte.class) || paramType.equals(Byte.TYPE)) {
            return "byte";
        } else if (Date.class.isAssignableFrom(paramType)) {
            return "date";
        } else if (paramType.equals(Double.class) || paramType.equals(Double.TYPE)) {
            return "double";
        } else if (paramType.equals(Float.class) || paramType.equals(Float.TYPE)) {
            return "float";
        } else if (paramType.equals(Integer.class) || paramType.equals(Integer.TYPE)) {
            return "integer";
        } else if (paramType.equals(Long.class) || paramType.equals(Long.TYPE)) {
            return "long";
        } else if (paramType.equals(Short.class) || paramType.equals(Short.TYPE)) {
            return "short";
        }

        return null;
    }

    protected void registerAutoTypeValidatorConfig(Field field, PropertyDesc propDesc) {
        String autoTypeValidatorName = getAutoTypeValidatorName(propDesc);
        if (StringUtil.isEmpty(autoTypeValidatorName)) {
            return;
        }

        if (hasConfigRegister(autoTypeValidatorName)) {
            executeConfigRegister(field, autoTypeValidatorName, new HashMap());
        }
    }

    protected boolean hasConfigRegister(String validatorName) {
        String registerName = getConfigRegisterName(validatorName);
        S2Container container = SingletonS2ContainerFactory.getContainer();
        return container.hasComponentDef(registerName);
    }

    protected void executeConfigRegister(Field field, String validatorName, Map parameters) {
        String registerName = getConfigRegisterName(validatorName);
        S2Container container = SingletonS2ContainerFactory.getContainer();
        ConfigRegister register = (ConfigRegister) container.getComponent(registerName);
        register.register(field, parameters);
    }

    protected void executeArgsConfigRegister(Field field, Map parameters) {
        S2Container container = SingletonS2ContainerFactory.getContainer();
        ConfigRegister register = (ConfigRegister) container.getComponent("argsConfigRegister");
        register.register(field, parameters);
    }

    protected void executeArgConfigRegister(Field field, Map parameters) {
        S2Container container = SingletonS2ContainerFactory.getContainer();
        ConfigRegister register = (ConfigRegister) container.getComponent("argConfigRegister");
        register.register(field, parameters);
    }

    protected void executeMessageConfigRegister(Field field, Map parameters) {
        S2Container container = SingletonS2ContainerFactory.getContainer();
        ConfigRegister register = (ConfigRegister) container.getComponent("messageConfigRegister");
        register.register(field, parameters);
    }

    private String getConfigRegisterName(String validatorName) {
        return validatorName + "ConfigRegister";
    }

    protected String getValidatorName(Class clazz) {
        String validatorName = CommonNamingRule.decapitalizeName(ClassUtil.getShortClassName(clazz));
        return validatorName.replaceFirst(VALIDATOR_TYPE_PREFIX_RE, "");
    }

    protected Map getDefaultArgsConfigParameter(PropertyDesc propDesc) {
        Map result = new HashMap();
        result.put("keys", propDesc.getPropertyName());
        result.put("resource", "false");
        return result;
    }

    protected void registerDefaultArgs(Field field, PropertyDesc propDesc) {
        Map parameter = getDefaultArgsConfigParameter(propDesc);
        executeArgsConfigRegister(field, parameter);
    }

}
