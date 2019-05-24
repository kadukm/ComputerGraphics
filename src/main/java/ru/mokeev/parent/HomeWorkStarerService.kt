package ru.mokeev.parent

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class HomeWorkStarerService {

    @EventListener(value = [ApplicationReadyEvent::class])
    fun drawFirstHomeWork() {
        DrawingComponent().draw()
    }
}