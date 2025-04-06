package com.enefit.metering.utils;

/**
 * Utility class for masking sensitive information.
 */
public final class MaskingUtil {

    private MaskingUtil() {
    }

    /**
     * Masks a sensitive string by keeping the first and last characters intact and replacing the middle characters with asterisks.
     * If the input is null or too short, a default masked value is returned.
     *
     * @param input the sensitive string to mask.
     * @return the masked string.
     */
    public static String mask(String input) {
        if (input == null || input.length() <= 2) {
            return "****";
        }
        return input.charAt(0) +
                "*".repeat(input.length() - 2) +
                input.charAt(input.length() - 1);
    }
}
