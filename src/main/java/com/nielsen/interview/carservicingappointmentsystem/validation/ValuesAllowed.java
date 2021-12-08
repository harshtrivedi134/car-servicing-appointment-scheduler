package com.nielsen.interview.carservicingappointmentsystem.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValuesAllowed.Validator.class})
public @interface ValuesAllowed {
    String message() default "Field value should be from list of ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String propName();

    String[] values();

    class Validator implements ConstraintValidator<ValuesAllowed, String> {
        private List<String> expectedValues;
        private String returnMessage;

        @Override
        public void initialize(ValuesAllowed requiredIfChecked) {
            expectedValues = Arrays.asList(requiredIfChecked.values());
            returnMessage = requiredIfChecked.message().concat(expectedValues.toString());
        }

        public boolean isValid(String value, ConstraintValidatorContext context) {
            boolean valid = expectedValues.contains(value);

            if (!valid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(returnMessage)
                        .addConstraintViolation();
            }
            return valid;
        }
    }
}
