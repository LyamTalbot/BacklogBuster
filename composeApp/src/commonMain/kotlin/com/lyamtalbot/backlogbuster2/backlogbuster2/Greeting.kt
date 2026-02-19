package com.lyamtalbot.backlogbuster2.backlogbuster2

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}