package com.jianastrero.roombackgroundexample.util

import kotlin.random.Random

private val letters = "qwertyuiop asdfghjklzxcvbnm QWERTYUIOP ASDFGHJKLZXCVBNM"
fun generateRandomString(length: Long): String {
    var x = ""
    val random = Random(System.currentTimeMillis() * 24)
    for (i in 0..length)
        x += letters[random.nextInt(letters.length)]
    return x
}