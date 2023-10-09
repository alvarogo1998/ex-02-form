package com.agalobr.ex02form.data

import com.agalobr.ex02form.app.Either
import com.agalobr.ex02form.app.ErrorApp
import com.agalobr.ex02form.data.local.XmlLocalDataSource
import com.agalobr.ex02form.domain.User
import com.agalobr.ex02form.domain.UserRepository

class UserDataRepository(private val localDataSource: XmlLocalDataSource) : UserRepository {

    override fun save(user: User): Either<ErrorApp, User> {
        return localDataSource.saveUser(user)
    }

    override fun get(username: String): Either<ErrorApp, User> {
        return localDataSource.getUser(username)
    }

    override fun delete(): Either<ErrorApp, Boolean> {
        return localDataSource.delete()
    }
}