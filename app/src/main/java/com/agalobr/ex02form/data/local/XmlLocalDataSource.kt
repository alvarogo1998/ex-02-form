package com.agalobr.ex02form.data.local

import android.content.Context
import com.agalobr.ex02form.app.Either
import com.agalobr.ex02form.app.ErrorApp
import com.agalobr.ex02form.app.left
import com.agalobr.ex02form.app.right
import com.agalobr.ex02form.domain.User
import com.google.gson.Gson
import kotlin.Exception

class XmlLocalDataSource(context: Context) {

    private val gson = Gson()
    private val sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE)

    fun saveUser(user: User): Either<ErrorApp, User> {

        return try {
            with(sharedPref.edit()) {
                putString(user.name, gson.toJson(user)).apply()
            }
            user.right()
        } catch (ex: Exception) {
            ErrorApp.UnKonowError.left()
        }
    }

    fun getUser(username: String): Either<ErrorApp, User> {

        return try {
            val user = sharedPref.getString(username, "{}")
            user.let {
                gson.fromJson(it, User::class.java)
            }.right()
        } catch (ex: Exception) {
            ErrorApp.UnKonowError.left()
        }
    }

    fun delete(): Either<ErrorApp, Boolean> {

        sharedPref.edit().clear().apply()
        return true.right()
    }
}