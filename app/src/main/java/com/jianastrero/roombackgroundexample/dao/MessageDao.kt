package com.jianastrero.roombackgroundexample.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jianastrero.roombackgroundexample.models.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY id")
    fun getAll(): LiveData<List<Message>>

    @Query("SELECT * FROM messages ORDER BY id")
    fun getAllBlocking(): List<Message>

    @Insert
    fun add(vararg messages: Message)

    @Query("DELETE FROM messages")
    fun clear();
}