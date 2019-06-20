package ru.mokeev

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import ru.mokeev.Constants.height
import ru.mokeev.Constants.width
import ru.mokeev.Constants.xMax
import ru.mokeev.Constants.xMin
import ru.mokeev.service.DrawingService
import java.awt.Dimension

@Service
class HomeWorkStarerService {

    @EventListener(value = [ApplicationReadyEvent::class])
    fun drawFirstHomeWork() {
        val drawingService = DrawingService(xMin, xMax, width, height)

        val frame = javax.swing.JFrame()
        frame.contentPane.preferredSize = Dimension(width.toInt(), height.toInt())
        frame.pack()
        frame.isVisible = true
        frame.contentPane.add(drawingService)
    }
}