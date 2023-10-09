package com.agalobr.ex02form.domain

import com.agalobr.ex02form.app.Either
import com.agalobr.ex02form.app.ErrorApp

class GetUserUseCase(private val repository: UserRepository) {
    operator fun invoke(username: String): Either<ErrorApp, User> {
        return repository.get(username)
    }
}