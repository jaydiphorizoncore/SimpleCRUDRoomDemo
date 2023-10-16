package com.example.simplecrudroomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Book::class], version = 3)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        private const val DATABASE_NAME = "book_database"

        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books_table ADD COLUMN price Integer")
                database.execSQL("ALTER TABLE books_table RENAME TO books_tableTwo")
                database.execSQL("ALTER TABLE books_tableTwo RENAME COLUMN price TO Group_price")
            }
        }
        private val migration_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books_table RENAME COLUMN name TO nameUpdate")
            }
        }

        //
        fun buildDatabase(context: Context): BookDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                BookDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .addMigrations(
                    migration_1_2, migration_2_3
                )
                .build()
        }

    }
}