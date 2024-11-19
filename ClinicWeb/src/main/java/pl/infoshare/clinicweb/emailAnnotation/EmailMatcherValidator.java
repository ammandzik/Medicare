package pl.infoshare.clinicweb.emailAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailMatcher.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailMatcherValidator {
    String message() default "Podany adres email istnieje już w bazie.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
