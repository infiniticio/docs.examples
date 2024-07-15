package com.acme.utils;

import io.infinitic.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractService {
    static final SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss.SSS");

    public static void log(String msg) {
        System.out.println(dateFormatter.format(new Date()) + " - " + Task.getWorkflowId() + " - " + Task.getServiceName() + " - " + msg);
    }
}
