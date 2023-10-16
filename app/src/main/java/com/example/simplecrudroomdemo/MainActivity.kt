package com.example.simplecrudroomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var bookDao: BookDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = BookDatabase.buildDatabase(this)

        bookDao = db.bookDao()
        testDB()
    }

    private fun testDB() {
        lifecycleScope.launch(Dispatchers.IO) {

            //Insert
            Log.i("MyTag", "*** Inserting 3 Books ***")
            bookDao.insertBook(Book(0, "Java", "Alex",10000))
            bookDao.insertBook(Book(0, "PHP", "Mike",20000))
            bookDao.insertBook(Book(0, "Kotlin", "Amelia",30000))
            Log.i("MyTag", "*** Inserted 3 Books ***")

            //Query
            val books = bookDao.getAllBooks()
            Log.e("MyTag", "*** ${books.size} books there *** ")
            for (book in books) {
                Log.i("MyTag", "id : ${book.id} name: ${book.name} author : ${book.author} price: ${book.price}")
            }

            //update
            Log.i("MyTAG", "*****      Updating a book      **********")
            bookDao.updateBook(Book(1, "PHPUpdated", "Mike",2000))
            //Query
            val books2 = bookDao.getAllBooks()
            Log.e("MyTag", "*** ${books2.size} books there *** ")
            for (book in books2) {
                Log.i("MyTag", "id : ${book.id} name: ${book.name} author : ${book.author}")
            }

            //Delete
            Log.i("MyTAG", "*****       Deleting a book      **********")
            bookDao.deleteBook(Book(2, "", "",10))
            //Query
            val books3 = bookDao.getAllBooks()
            Log.e("MyTag", "*** ${books3.size} books there *** ")
            for (book in books3) {
                Log.i("MyTag", "id : ${book.id} name: ${book.name} author : ${book.author}")
            }

        }
    }
}