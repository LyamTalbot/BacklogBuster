package com.lyamtalbot.backlogbuster2.backlogbuster2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform