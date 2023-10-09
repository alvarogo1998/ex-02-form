package com.agalobr.ex02form.domain

import com.agalobr.ex02form.app.Either
import com.agalobr.ex02form.app.ErrorApp

interface UserRepository {

    fun save(user: User): Either<ErrorApp, User>
    fun get(username: String): Either<ErrorApp, User>
    fun delete(): Either<ErrorApp, Boolean>
}