package ru.mokeev.parent

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ComputerGraphicsApplication

fun main(args: Array<String>) {
    runApplication<ComputerGraphicsApplication>(*args){
        setBannerMode(Banner.Mode.OFF)
        setHeadless(false)
    }
}
