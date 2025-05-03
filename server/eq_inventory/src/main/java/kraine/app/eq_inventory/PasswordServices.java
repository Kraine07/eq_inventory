package kraine.app.eq_inventory;

import java.lang.reflect.Field;
import java.security.SecureRandom;

public class PasswordServices {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()";
    private static final String ALL_CHARACTERS = UPPER + LOWER + DIGITS + SPECIAL;

    private static final SecureRandom random = new SecureRandom();



    public static String generatePassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Password length should be at least 8 characters.");
        }

        StringBuilder password = new StringBuilder(length);

        // Ensure at least one character of each type
        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(LOWER.charAt(random.nextInt(LOWER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Fill the remaining with random characters
        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // Shuffle the characters
        char[] pwdArray = password.toString().toCharArray();
        for (int i = pwdArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = pwdArray[i];
            pwdArray[i] = pwdArray[index];
            pwdArray[index] = temp;
        }

        return new String(pwdArray);
    }




    public static boolean validateFieldAgainstPattern(Object object, String fieldName, String input) {
        try {
            Class<?> clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);

            if (field.isAnnotationPresent(jakarta.validation.constraints.Pattern.class)) {

                // Fully qualified name to resolve collision
                jakarta.validation.constraints.Pattern patternAnnotation = field
                        .getAnnotation(jakarta.validation.constraints.Pattern.class);

                String regex = patternAnnotation.regexp();
                return java.util.regex.Pattern.compile(regex).matcher(input).matches(); // For regex matching
            } else {
                System.out.println("Field does not have @Pattern annotation.");
            }
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

}
