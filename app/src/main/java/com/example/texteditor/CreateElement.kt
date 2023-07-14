package com.example.texteditor

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.ComponentActivity

class CreateElement : ComponentActivity() {

    lateinit var nameSave: EditText
    lateinit var textSave: EditText

    lateinit var data: SharedReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_element)

        data = SharedReference(this)

        nameSave = findViewById(R.id.editTextTextPersonName)
        textSave = findViewById(R.id.editTextTextMultiLine)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.creator_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.save -> {
                saved()
                return true
            }
            R.id.back -> {
                saved()
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun saved() {
        data.save(nameSave.text.toString(), textSave.text.toString())
    }

    fun load(name: String) {

    }
}