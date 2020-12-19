package com.app.itunesapisample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.itunesapisample.models.JSONData

@Database(entities = [JSONData::class], version = 1, exportSchema = false)
abstract class JSONDatabase : RoomDatabase() {
    abstract val jsonDao: JSONDao

    companion object {
        @Volatile
        private var INSTANCE: JSONDatabase? = null

        fun getInstance(context: Context): JSONDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JSONDatabase::class.java,
                        "json_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}