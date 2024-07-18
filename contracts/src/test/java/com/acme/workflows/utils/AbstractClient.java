package com.acme.workflows.utils;

import io.infinitic.common.workflows.data.workflows.WorkflowTag;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractClient {
    protected static final String DISPATCH_MESSAGE = "%s dispatched (%s) %n";
    protected static final String DISPATCH_ERROR_MESSAGE = "Failed to dispatch %s: %s%n";

    protected static Void printDispatched(String strI, String workflowId) {
        System.out.printf(DISPATCH_MESSAGE, strI, workflowId);
        return null;
    }

    protected static Void printError(String strI, Throwable error) {
        System.err.printf(DISPATCH_ERROR_MESSAGE, strI, error);
        error.printStackTrace();
        return null;
    }

    /**
     * Retrieves an array of custom IDs based on the given arguments.
     *
     * @param args an array of arguments from which the custom IDs will be generated
     * @return an array of custom IDs
     */
    protected static String[] getCustomIds(String[] args) {
        return Arrays.stream(args)
                .map(AbstractClient::getCustomId)
                .toArray(String[]::new);
    }

    /**
     * Retrieves a custom ID by appending the given string to the custom ID prefix.
     *
     * @param str the string to append to the custom ID prefix
     * @return the custom ID
     */
    protected static String getCustomId(String str) {
        return WorkflowTag.CUSTOM_ID_PREFIX + str;
    }

    protected static <T extends Enum<T>> T[] getEnums(Class<T> enumType, String[] args) {
        List<T> enumList = Arrays.stream(args)
                .map(arg -> tryParse(enumType, arg))
                .filter(Objects::nonNull)
                .toList();

        @SuppressWarnings("unchecked")
        T[] enums = (T[]) Array.newInstance(enumType, enumList.size());

        return enumList.toArray(enums);
    }

    protected static <T extends Enum<T>> String[] excludeEnums(Class<T> enumType, String[] args) {
        return Arrays.stream(args)
                .filter(arg -> tryParse(enumType, arg) == null)
                .toArray(String[]::new);
    }

    protected static String[] excludeValue(String value, String[] args) {
        return Arrays.stream(args)
                .filter(arg -> !Objects.equals(arg, value))
                .toArray(String[]::new);
    }

    protected static long[] getLongs(String[] args) {
        return Arrays.stream(args)
                .filter(arg -> {
                    try {
                        Long.parseLong(arg);
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .mapToLong(Long::parseLong)
                .toArray();
    }

    protected static String[] excludeLongs(String[] args) {
        return Arrays.stream(args)
                .filter(arg -> {
                    try {
                        Long.parseLong(arg);
                        return false;
                    } catch (NumberFormatException e) {
                        return true;
                    }
                })
                .toArray(String[]::new);
    }

    private static <T extends Enum<T>> T tryParse(Class<T> enumType, String value) {
        try {
            return Enum.valueOf(enumType, value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
