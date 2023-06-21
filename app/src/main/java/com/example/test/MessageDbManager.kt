package com.example.test

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class MessageDbManager {

    //database name
    var dbName = "MyMessages"
    //table name
    var dbTable = "Messages"
    //columns
    var colID = "ID"
    var colName = "name"
    var colContent = "content"
    var colConversation = "conversation"
    var colSentTime = "sentTime"
    //database version
    var dbVersion = 1

    //CREATE TABLE IF NOT EXISTS MyMessages (ID INTEGER PRIMARY KEY,title TEXT, Description TEXT);"
    val sqlCreateTable ="CREATE TABLE IF NOT EXISTS " + dbTable + " (" + colID + " INTEGER PRIMARY KEY," + colContent + " TEXT," + colConversation + " TEXT," + colSentTime + " TEXT," + colName + " TEXT );"
    var sqlDB: SQLiteDatabase? = null

    //note that according to normal execution of the application, the DB will not be created here
    //rather it would be created by ConversationDbManager class since conversation activity is loaded first
    constructor(context: Context) {
        var db = DatabaseHelperMessages(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperMessages : SQLiteOpenHelper {
        var context: Context? = null

        constructor(context: Context) : super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "Database Created!", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table if Exists$dbTable")
        }

    }

    fun insert(values: ContentValues): Long {
        val ID = sqlDB!!.insert(dbTable, "", values)
        return ID
    }

    fun Query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor {
        val qb = SQLiteQueryBuilder();
        qb.tables = dbTable
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.delete(dbTable, selection, selectionArgs)
        return count
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.update(dbTable, values, selection, selectionArgs)
        return count
    }
}