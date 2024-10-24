package com.acme.common;

import io.infinitic.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractService {
    static final SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss.SSS");

    public static void log(String msg) {
        if (Task.getContext()!=null) {
            System.out.println(dateFormatter.format(new Date()) + " - " + Task.getWorkflowId() + " - " + Task.getServiceName() + " - " + msg);
        } else {
            System.out.println(dateFormatter.format(new Date()) + " - "  + msg);
        }
    }
}
