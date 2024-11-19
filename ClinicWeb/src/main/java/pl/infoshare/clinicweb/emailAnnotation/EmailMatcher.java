package pl.infoshare.clinicweb.emailAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import pl.infoshare.clinicweb.user.registration.UserDto;
import pl.infoshare.clinicweb.user.service.UserService;

@Component
public final class EmailMatcher implements ConstraintValidator<EmailMatcherValidator, UserDto> {

        private final UserService userService;

        public EmailMatcher(UserService userService){
            this.userService = userService;
        }

        @Override
        public void initialize(EmailMatcherValidator constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {


            if (userDto == null) {
                return true;
            }

            boolean isEmailValid = !userService.isUserAlreadyRegistered(userDto.getEmail());

            if (!isEmailValid) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Podany adres email istnieje już w bazie.")
                        .addPropertyNode("email")
                        .addConstraintViolation();
            }

            return isEmailValid ;
        }
    }
