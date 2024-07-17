package com.acme.utils;

import io.infinitic.workflows.Workflow;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractWorkflow extends Workflow {
    static final SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss.SSS");

    protected void log(String msg) {
        inlineVoid(() -> System.out.println(
                dateFormatter.format(new Date()) + " - " + getWorkflowId() + getTagsString() + " - " + getWorkflowName() + " - " + msg)
        );
    }

    private String getTagsString() {
        if (getTags().isEmpty()) {
            return "";
        } else {
            return " (" + String.join(", ", getTags()) + ")";
        }
    }
}
