package com.acme.events.java;

import io.cloudevents.CloudEvent;
import io.cloudevents.jackson.JsonFormat;
import io.infinitic.cloudEvents.CloudEventListener;
import java.util.List;

public class EventListenerImpl implements CloudEventListener {
    @Override
    public void onEvents(List<CloudEvent> cloudEvents) {
        for (CloudEvent event : cloudEvents) {
            System.out.println(new String(new JsonFormat().serialize(event)));
        }
    }
}
