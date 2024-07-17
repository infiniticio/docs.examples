package com.acme.workflows.utils;

import io.infinitic.common.workflows.data.workflows.WorkflowTag;

import java.util.Arrays;

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
     * Retrieves a Long value from the given array of strings.
     *
     * @param args an array of strings from which the Long value will be retrieved
     * @return the Long value retrieved from the array of strings
     */
    protected static Long getLong(String[] args) {
        return (args.length==0) ? 1 : Long.parseLong(args[0]);
    }

    /**
     * Retrieves an array of custom IDs based on the given arguments.
     *
     * @param args an array of arguments from which the custom IDs will be generated
     * @return an array of custom IDs
     */
    protected static String[] getCustomIds(String[] args) {
        if (args.length == 0) {
            return new String[]{getCustomId("null")};
        }
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

}
