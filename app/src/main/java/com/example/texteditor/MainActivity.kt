package com.example.texteditor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    final val NAMECOUNT = "COUNTOFITEMSID"
    lateinit var data: SharedReference
    var counter = 0
    var text = ""

    var arrayNames = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = SharedReference(this)

//        if(data.getText(NAMECOUNT)?.length != 0) {
//            var textFormat = data.getText(NAMECOUNT)
//            text = textFormat!!
//            if(text.length>0) {
//                counter = textFormat?.toInt()!!
//            }
//        } else {
//            data.save(NAMECOUNT, "$counter")
//        }
        loadItems()

        //test
        //Toast.makeText(this, "$counter + $text", Toast.LENGTH_LONG).show()
    }

    override fun onAttachedToWindow() {
        recreate()
        super.onAttachedToWindow()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun loadItems() {
        var list = findViewById<ListView>(R.id.list)
        var arr = data.getAll()

        arrayNames.add("${arr.size}")
        for (i in arr.keys) {
            arrayNames.add("${i}")
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayNames)
        list.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.newelem -> {
                var i: Intent = Intent(this, CreateElement::class.java)
                this.recreate()
                startActivity(i)
                return true
            }   R.id.exit -> {
                finish()
                return true
            } else -> return super.onOptionsItemSelected(item)
        }
    }
}
