package com.agalobr.ex02form.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agalobr.ex02form.app.ErrorApp
import com.agalobr.ex02form.domain.DeleteUserUseCase
import com.agalobr.ex02form.domain.GetUserUseCase
import com.agalobr.ex02form.domain.SaveUserUseCase
import com.agalobr.ex02form.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase, private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<Uistate>()
    val uiState: LiveData<Uistate> = _uiState

    fun save(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserUseCase(user).fold(
                { responseError(it) },
                { responseSaveUserSuccess(it) }
            )
        }
    }

    fun get(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase(username).fold(
                { responseError(it) },
                { responseGetUserSuccess(it) }
            )
        }
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserUseCase().fold(
                { responseError(it) },
                { responseDeleteUserSuccess() }
            )
        }
    }

    private fun responseError(errorApp: ErrorApp) {

    }

    private fun responseSaveUserSuccess(user: User) {
        _uiState.postValue(Uistate(user = user))
    }

    private fun responseGetUserSuccess(username: User) {
        _uiState.postValue(Uistate(user = username))
    }

    private fun responseDeleteUserSuccess() = deleteUserUseCase.invoke()


    data class Uistate(
        val errorApp: ErrorApp? = null,
        val isLoading: Boolean = false,
        val user: User? = null
    )

}