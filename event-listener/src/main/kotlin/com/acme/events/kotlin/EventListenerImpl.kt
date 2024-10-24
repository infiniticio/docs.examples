package com.acme.events.kotlin

import io.infinitic.cloudEvents.CloudEventListener
import io.cloudevents.CloudEvent
import io.cloudevents.jackson.JsonFormat

@Suppress("unused")
class EventListenerImpl : CloudEventListener {
    override fun onEvents(cloudEvents: List<CloudEvent>) {
        cloudEvents.forEach { event ->
            println(String(JsonFormat().serialize(event)))
        }
    }
}
