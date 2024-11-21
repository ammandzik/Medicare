package pl.infoshare.clinicweb.passwordAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordMatcher.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatcherValidator {
    String message() default "Hasła muszą być identyczne.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}