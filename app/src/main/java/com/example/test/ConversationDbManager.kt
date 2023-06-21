package com.example.test

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class ConversationDbManager {

    //database name
    var dbName = "MyMessages"
    //table name
    var dbTable1 = "Conversations"
    var dbTable2 = "Messages"
    //columns
    var colID = "ID"
    var colName = "name"

    var colContent = "content"
    var colConversation = "conversation"
    var colSentTime = "sentTime"

    //database version
    var dbVersion = 1

    //We are creating both Tables in this class because onCreate() is only called here
    //since this class is called as soon as app is started
    //the DB is the same in both classes

    val sqlCreateTable1 = "CREATE TABLE IF NOT EXISTS " + dbTable1 + " (" + colID + " INTEGER PRIMARY KEY," + colName + " TEXT NOT NULL UNIQUE );"

    val sqlCreateTable2 ="CREATE TABLE IF NOT EXISTS " + dbTable2 + " (" + colID + " INTEGER PRIMARY KEY," + colContent + " TEXT," + colConversation + " TEXT ," + colSentTime + " TEXT," + colName + " TEXT );"

    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context) {
        var db = DatabaseHelperConversations(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperConversations : SQLiteOpenHelper {
        var context: Context? = null

        constructor(context: Context) : super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable1)
            db!!.execSQL(sqlCreateTable2)
            Toast.makeText(this.context, "Database Created!", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table if Exists$dbTable1")
            db!!.execSQL("Drop table if Exists$dbTable2")
        }

    }

    fun insert(values: ContentValues): Long {
        val ID = sqlDB!!.insert(dbTable1, "", values)
        return ID
    }

    fun Query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor {
        val qb = SQLiteQueryBuilder();
        qb.tables = dbTable1
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.delete(dbTable1, selection, selectionArgs)
        return count
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.update(dbTable1, values, selection, selectionArgs)
        return count
    }
}