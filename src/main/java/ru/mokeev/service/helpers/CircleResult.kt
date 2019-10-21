package ru.mokeev.service.helpers

import kotlin.math.PI

data class CircleResult(
    val point: Point,
    val radius: Int
) {
    fun getArea(): Double {
        return PI * radius * radius
    }
}