package pl.infoshare.clinicweb.passwordAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.infoshare.clinicweb.user.registration.UserDto;

final class PasswordMatcher implements ConstraintValidator<PasswordMatcherValidator, UserDto> {

    @Override
    public void initialize(PasswordMatcherValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {

        if (userDto == null) {
            return true;
        }
        boolean isPasswordValid = userDto.getPassword().equals(userDto.getConfirmPassword());
        if (!isPasswordValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Hasła muszą być identyczne")
                    .addPropertyNode("password")
                    .addConstraintViolation();
        }
        return isPasswordValid;
    }
}
