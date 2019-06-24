package ru.mokeev

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import ru.mokeev.Constants.height
import ru.mokeev.Constants.width
import ru.mokeev.Constants.xMax
import ru.mokeev.Constants.xMin
import ru.mokeev.service.tasks.FirstTask
import ru.mokeev.service.tasks.SecondTask
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE


@Service
class HomeWorkStarerService {


    @EventListener(value = [ApplicationReadyEvent::class])
    fun init() {
        second()

//        ThirdTask(Point(1, 1), Point(10, 1), Point(2, 2))

//        drawFirstHomeWork()
    }
}

fun second() {
    val f = JFrame()
    f.defaultCloseOperation = EXIT_ON_CLOSE
    f.contentPane.preferredSize = Dimension(width.toInt(), height.toInt())
    f.pack()
    f.isVisible = true

    f.contentPane.add(SecondTask(10, 10, 1))

    f.setLocationRelativeTo(null)

}

fun drawFirstHomeWork() {
    val frame = javax.swing.JFrame()
    frame.contentPane.preferredSize = Dimension(width.toInt(), height.toInt())
    frame.pack()
    frame.isVisible = true
    frame.defaultCloseOperation = EXIT_ON_CLOSE
    frame.contentPane.add(FirstTask(xMin, xMax, width, height))
}