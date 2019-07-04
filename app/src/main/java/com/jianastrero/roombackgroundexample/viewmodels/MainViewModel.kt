package com.jianastrero.roombackgroundexample.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jianastrero.roombackgroundexample.models.Message
import com.jianastrero.roombackgroundexample.repositories.MessageRepository
import com.jianastrero.roombackgroundexample.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val messages: LiveData<List<Message>>
    val repository: MessageRepository

    val buttonText = ObservableField("Start")
    var isInserting = false

    init {
        repository = MessageRepository(AppDatabase.getDatabase(application).getMessageDao())
        messages = repository.findAll()
    }

    fun loopingInsert() = viewModelScope.launch(Dispatchers.IO) {
        while (isInserting) {
            val number = repository.count() + 1
            val message = Message(
                username = "Username $number",
                message = generateRandomString(number),
                time = Date()
            )
            repository.insert(message)
            delay(400)
        }
    }

    fun toggleInsert() {
        isInserting = !isInserting
        buttonText.set(
            if (isInserting)
                "Stop"
            else
                "Start"
        )
        if (isInserting)
            loopingInsert()
    }

    private val letters = "qwertyuiop asdfghjklzxcvbnm QWERTYUIOP ASDFGHJKLZXCVBNM"
    private fun generateRandomString(length: Long): String {
        var x = ""
        val random = Random(System.currentTimeMillis() * 24)
        for (i in 0..length)
            x += letters[random.nextInt(letters.length)]
        return x
    }
}