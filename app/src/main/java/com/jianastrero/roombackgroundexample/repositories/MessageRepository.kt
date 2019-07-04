package com.jianastrero.roombackgroundexample.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.jianastrero.roombackgroundexample.dao.MessageDao
import com.jianastrero.roombackgroundexample.models.Message

class MessageRepository(
    private val dao: MessageDao
) {
    fun findAll(): LiveData<List<Message>> = dao.getAll()

    @WorkerThread
    suspend fun insert(vararg messages: Message) {
        dao.add(*messages)
    }

    @WorkerThread
    suspend fun count() = dao.count()
}