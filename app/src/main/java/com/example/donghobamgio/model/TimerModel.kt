package com.example.donghobamgio.model

class TimerModel {
    private var time = 0L

    fun getTime(): Long {
        return time
    }

    fun setTime(newTime: Long) {
        time = newTime
    }

    fun incrementTime(increment: Long) {
        time += increment
    }

    fun resetTime() {
        time = 0L
    }
}
