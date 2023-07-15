package com.example.texteditor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity() {

    lateinit var data: SharedReference

    var arrayNames = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = SharedReference(this)

        loadItems()
    }


    val startActivityLaunch: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        , ActivityResultCallback {
        recreate()
    })

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun loadItems() {
        var list = findViewById<ListView>(R.id.list)
        var arr = data.getAll()

        for (i in arr.keys) {
            arrayNames.add("${i}")
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayNames)
        list.adapter = adapter

        list.setOnItemClickListener(AdapterView.OnItemClickListener {
            adapterView, view, position, id ->
                var i: Intent = Intent(this, CreateElement::class.java)
                i.putExtra("NAME", "${arrayNames[position]}")
                startActivityLaunch.launch(i)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.newelem -> {
                var i: Intent = Intent(this, CreateElement::class.java)
                i.putExtra("NAME", "")
                startActivityLaunch.launch(i)
                return true
            }
            R.id.exit -> {
                finish()
                return true
            } else -> return super.onOptionsItemSelected(item)
        }
    }
}
