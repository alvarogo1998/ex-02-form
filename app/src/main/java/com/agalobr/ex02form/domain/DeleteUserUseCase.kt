package com.agalobr.ex02form.domain

import com.agalobr.ex02form.app.Either
import com.agalobr.ex02form.app.ErrorApp

class DeleteUserUseCase(private val repository: UserRepository) {
    operator fun invoke():Either<ErrorApp, Boolean>{
        return repository.delete()
    }
}