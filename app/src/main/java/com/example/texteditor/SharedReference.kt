package com.example.texteditor

import android.content.Context
import android.text.Editable

class SharedReference(ctx: Context) {
    val data = ctx.getSharedPreferences("SAVED", Context.MODE_PRIVATE)
    fun save(name: String, text: String) {
        data.edit().putString(name, text).apply()
    }

    fun getText(name: String): String? {
        return data.getString(name, "")
    }

    fun getAll() : Map<String, *> {
        return data.all
    }

    fun clear(name: String) {
        data.edit().remove(name).commit()
    }
}

