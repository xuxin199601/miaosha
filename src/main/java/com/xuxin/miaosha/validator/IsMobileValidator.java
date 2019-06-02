package com.xuxin.miaosha.validator;

import com.xuxin.miaosha.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        // 是否必须
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 若为必须，则进行校验
        if (required) {
            return ValidatorUtil.isMobile(value);
        } else {
            // 如非必须，则返回为真
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                // 非必须，但不为空，但需要进行校验
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
