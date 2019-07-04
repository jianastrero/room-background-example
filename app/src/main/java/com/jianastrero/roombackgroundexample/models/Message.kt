package com.jianastrero.roombackgroundexample.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var username: String,
    var message: String,
    var time: Date
)