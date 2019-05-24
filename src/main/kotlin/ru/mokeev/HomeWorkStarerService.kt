package ru.mokeev

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import ru.mokeev.service.DrawingService

@Service
class HomeWorkStarerService {

    @EventListener(value = [ApplicationReadyEvent::class])
    fun drawFirstHomeWork() {
        DrawingService().draw()
    }
}