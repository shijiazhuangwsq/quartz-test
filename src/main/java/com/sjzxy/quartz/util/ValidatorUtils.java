package com.sjzxy.quartz.util;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * hibernate-validator校验工具类
 *
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author asiainfo
 *  
 * @date 2017-03-15 10:50
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws RRException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws RRException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (!constraintViolations.isEmpty()) {
        	ConstraintViolation<Object> constraint = (ConstraintViolation<Object>)constraintViolations.iterator().next();
            throw new RRException(constraint.getMessage());
        }
    }

    /**
     * 校验对象（针对对象的所有约束条件进行校验）
     * @param object        待校验对象
     * @throws RRException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object)
            throws RRException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = (ConstraintViolation<Object>)constraintViolations.iterator().next();
            throw new RRException(constraint.getMessage());
        }
    }

    /**
     * 校验对象属性（针对对象的某个属性约束条件进行校验）
     * @param object         待校验对象
     * @param propertyName  待验证的属性名称（必须和业务实体bean的属性名称对应）
     * @throws RRException  校验不通过，则报RRException异常
     */
    public static void validateEntityProperty(Object object, String propertyName)
            throws RRException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validateProperty(object, propertyName);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = (ConstraintViolation<Object>)constraintViolations.iterator().next();
            throw new RRException(constraint.getMessage());
        }
    }

    public static Map<String, String> getValidateErrors(BindingResult result) {
        Map<String, String> map = new HashMap<String, String>();
        List<FieldError> list = result.getFieldErrors();
        for (FieldError error : list) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return map;
    }




}
