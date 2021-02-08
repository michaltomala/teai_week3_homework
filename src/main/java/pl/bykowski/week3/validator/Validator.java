package pl.bykowski.week3.validator;


import org.apache.commons.lang3.StringUtils;

public class Validator {

    public static boolean validateString(String value) {
        return StringUtils.isNotEmpty(value);
    }

}
