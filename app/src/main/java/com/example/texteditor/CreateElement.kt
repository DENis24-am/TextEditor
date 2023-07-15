package com.example.texteditor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class CreateElement : ComponentActivity() {

    lateinit var nameSave: EditText
    lateinit var textSave: EditText

    lateinit var data: SharedReference
    var nameFile = ""
    var textFile = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_element)

        data = SharedReference(this)

        nameSave = findViewById(R.id.editTextTextPersonName)
        textSave = findViewById(R.id.editTextTextMultiLine)

        var loading = intent.extras

        nameFile = nameSave.text.toString()

        if(loading?.getString("NAME")!!.length > 0) {
            nameFile = loading.getString("NAME")!!
            textFile = data.getText("$nameFile").toString()
            load(nameFile, textFile)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.creator_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.save -> {
                saved()
                recreate()
                return true
            }
            R.id.del -> {
                data.clear(nameFile)
                exit()
                return true
            }
            R.id.savefile -> {
                saveToFile()
                return true
            }
            R.id.openfile -> {
                openFile()
                return true;
            }
            R.id.back -> {
                saved()
                var i: Intent = Intent(this, MainActivity::class.java)
                exit()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    val writeCode = 12 //write
    val readCode = 45 //read
    fun saveToFile() {
        nameFile = nameSave.text.toString()
        textFile = data.getText("$nameFile").toString()
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/txt"
            putExtra(Intent.EXTRA_TITLE, "${nameFile}.txt")
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, writeCode)
        }
        startActivityForResult(intent, writeCode)
    }


    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == writeCode && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    val textToWrite = textFile
                    outputStream.write(textToWrite.toByteArray())
                }
            }
        }
        if (requestCode == readCode && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                var text = ""
                var name = ""
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val fileContent = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        fileContent.append(line).append("\n")
                    }
                    text = fileContent.toString()
                }

                val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val name1 = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                        name = name1.replace(".txt", "")
                    }
                }

                load(name, text)
            }

        }
    }

    fun openFile() {
        nameFile = nameSave.text.toString()
        textFile = data.getText("$nameFile").toString()
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/*"
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, readCode)
        }

        startActivityForResult(intent, readCode)
    }

    fun exit() {
        val ans = Intent()
        setResult(RESULT_OK)
        finish()
    }

    fun saved() {
        data.save(nameSave.text.toString(), textSave.text.toString())
        nameFile = nameSave.text.toString()
        textFile = data.getText("$nameFile").toString()
    }

    fun load(name1: String, text: String) {
        nameSave.setText(name1)
        textSave.setText(text)
    }
}