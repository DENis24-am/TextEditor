package com.example.texteditor

import android.content.Context

class SharedReference(ctx: Context) {
    val data = ctx.getSharedPreferences("SAVED", Context.MODE_PRIVATE)
    lateinit var arr: Map<String, *>
    public fun save(name: String, text: String) {
        data.edit().putString(name, text).apply()
    }

    public fun getText(name: String): String? {
        return data.getString(name, "")
    }

    fun getAll() : Map<String, *> {
        return data.all
    }

    public fun clear(name: String) {
        data.edit().remove(name)
    }
}

