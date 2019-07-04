package com.jianastrero.roombackgroundexample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jianastrero.roombackgroundexample.converters.Converters
import com.jianastrero.roombackgroundexample.dao.MessageDao
import com.jianastrero.roombackgroundexample.models.Message
import com.jianastrero.roombackgroundexample.util.generateRandomString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Message::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMessageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database"
                        )
                        .addCallback(AppDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.getMessageDao())
                }
            }
        }

        fun populateDatabase(dao: MessageDao) {
            if (dao.getAllBlocking().isEmpty()) {
                val messages = mutableListOf<Message>()
                for (i in 1..1000) {
                    val message = Message(
                        username = "Username $i",
                        message = generateRandomString(i.toLong()),
                        time = Date()
                    )
                    messages.add(message)
                }
                dao.add(*messages.toTypedArray())
            }
        }
    }
}