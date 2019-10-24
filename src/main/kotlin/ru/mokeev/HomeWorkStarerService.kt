package ru.mokeev

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import ru.mokeev.Constants.height
import ru.mokeev.Constants.width
import ru.mokeev.Constants.xMax
import ru.mokeev.Constants.xMin
import ru.mokeev.service.helpers.Point
import ru.mokeev.service.tasks.FirstTask
import ru.mokeev.service.tasks.FourthTask
import ru.mokeev.service.tasks.SecondTask
import ru.mokeev.service.tasks.ThirdTask
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants.EXIT_ON_CLOSE


@Service
class HomeWorkStarerService {


    @EventListener(value = [ApplicationReadyEvent::class])
    fun init() {

//        drawFirstHomeWork()
//        second()
//        third()
        fourth()
    }
}

fun fourth() {
    val f = JFrame()
    f.pack()
    f.defaultCloseOperation = EXIT_ON_CLOSE
    f.contentPane.preferredSize = Dimension(width.toInt(), height.toInt())
    f.isVisible = true



//    f.contentPane.add(FourthTask { x, y -> (x-y)*y})

//    f.contentPane.add(FourthTask { x, y -> x * x * x })
//    f.contentPane.add(FourthTask { x, y -> 10.0 })
    f.contentPane.add(FourthTask { x, y -> Math.cos(x * y) })
    f.pack()

}

fun third() {
    val f = JFrame()
    f.defaultCloseOperation = EXIT_ON_CLOSE
    f.contentPane.preferredSize = Dimension(width.toInt(), height.toInt())
    f.pack()
    f.isVisible = true


    f.contentPane.add(ThirdTask(Point(200, 100), Point(150, 500), Point(400, 600), Point(600, 490), Point(700, 200), Point(300, 100)))


//    f.contentPane.add(ThirdTask(Point(200, 100), Point(150, 200), Point(200, 300), Point(300, 300), Point(350, 200), Point(300, 100)))

//    f.contentPane.add(ThirdTask(Point(300, 300), Point(300, 600), Point(600, 600), Point(600, 300)))

//    f.contentPane.add(ThirdTask(Point(100, 110), Point(500, 600), Point(700, 500), Point(750, 300), Point(500, 150)))

//    f.setLocationRelativeTo(null)

}

fun second() {
    val f = JFrame()
    f.defaultCloseOperation = EXIT_ON_CLOSE
    f.contentPane.preferredSize = Dimension(width.toInt(), height.toInt())
    f.pack()
    f.isVisible = true
    f.isResizable = false

    f.contentPane.add(SecondTask(10, 5, -2))

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