package org.atinject.api.usercredential.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AcceptableUsernameValidator.class)
@Documented
public @interface AcceptableUsername {

	String message() default "not an acceptable username";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    
}
