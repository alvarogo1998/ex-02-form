package com.agalobr.ex02form.domain

import com.agalobr.ex02form.app.Either
import com.agalobr.ex02form.app.ErrorApp

class SaveUserUseCase(private val repository: UserRepository) {
    operator fun invoke(user: User): Either<ErrorApp, User>{
        return repository.save(user)
    }
}