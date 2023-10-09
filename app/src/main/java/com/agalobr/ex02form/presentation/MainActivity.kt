package com.agalobr.ex02form.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.agalobr.ex02form.R
import com.agalobr.ex02form.data.UserDataRepository
import com.agalobr.ex02form.data.local.XmlLocalDataSource
import com.agalobr.ex02form.domain.DeleteUserUseCase
import com.agalobr.ex02form.domain.GetUserUseCase
import com.agalobr.ex02form.domain.SaveUserUseCase
import com.agalobr.ex02form.domain.User

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by lazy {
        MainViewModel(
            SaveUserUseCase(UserDataRepository(XmlLocalDataSource(this))),
            GetUserUseCase(UserDataRepository(XmlLocalDataSource(this))),
            DeleteUserUseCase(UserDataRepository(XmlLocalDataSource(this)))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
        sepUpObserver()

    }

    fun setUpView() {
        val saveButton = findViewById<Button>(R.id.action_save)
        val getButton = findViewById<Button>(R.id.action_get)
        val deleteButton = findViewById<Button>(R.id.action_delete)

        saveButton.setOnClickListener {
            viewModel.save(User(getInputName(), getInputSurname()))
        }

        getButton.setOnClickListener {
            viewModel.get(getInputName())
            changeVisibilityOn()
        }

        deleteButton.setOnClickListener {
            viewModel.delete()
            changeVisibilityOff()
        }
    }

    fun changeVisibilityOn() {
        val nameView = findViewById<TextView>(R.id.nameList)
        val surnameView = findViewById<TextView>(R.id.surnameList)
        val buttonClearView = findViewById<Button>(R.id.action_delete)

        nameView.setVisibility(View.VISIBLE)
        surnameView.setVisibility(View.VISIBLE)
        buttonClearView.setVisibility(View.VISIBLE)

        nameView.setText(
            XmlLocalDataSource(this).getUser("").fold({ it.toString() }, { it.name })
        )
        surnameView.setText(
            XmlLocalDataSource(this).getUser("").fold({ it.toString() }, { it.surname })
        )
    }

    fun changeVisibilityOff() {
        val nameView = findViewById<TextView>(R.id.nameList)
        val surnameView = findViewById<TextView>(R.id.surnameList)
        val buttonClearView = findViewById<Button>(R.id.action_delete)

        nameView.setVisibility(View.INVISIBLE)
        surnameView.setVisibility(View.INVISIBLE)
        buttonClearView.setVisibility(View.INVISIBLE)

        nameView.setText(
            XmlLocalDataSource(this).getUser("").fold({ it.toString() }, { it.name })
        )
        surnameView.setText(
            XmlLocalDataSource(this).getUser("").fold({ it.toString() }, { it.surname })
        )
    }

    fun sepUpObserver() {
        val observer = Observer<MainViewModel.Uistate> {
            it.user?.apply {
                bindData(this)
            }
        }
        viewModel.uiState.observe(this, observer)
    }

    private fun bindData(user: User) {
        setInputName(user.name)
        setInputSurname(user.surname)
    }

    fun setInputName(name: String) = findViewById<EditText>(R.id.nameList).setText(name)

    fun setInputSurname(surname: String) =
        findViewById<EditText>(R.id.surnameList).setText(surname)

    fun getInputName() = findViewById<EditText>(R.id.name).text.toString()

    fun getInputSurname() = findViewById<EditText>(R.id.surname).text.toString()
}