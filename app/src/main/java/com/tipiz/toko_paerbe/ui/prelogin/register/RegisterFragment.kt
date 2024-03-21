package com.tipiz.toko_paerbe.ui.prelogin.register

import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.tipiz.core.network.data.register.RegisterRequest
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onError
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentRegisterBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, RegisterViewModel>(FragmentRegisterBinding::inflate) {
    override val viewModel: RegisterViewModel by viewModel() //ktx

    override fun initView() {
        register()

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun initViewModel() {
        with(viewModel) {
            responseRegister.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onSuccess { token ->
                    saveSession(token)
                    findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
                }.onError { Toast.makeText(context, "Gagal Register", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private fun register() {
        with(binding) {
            val emailInputLayout = inputEmail
            val passwordInputLayout = inputPassword
            edEmail.doAfterTextChanged { validateEmail(it.toString()) }
            edPassword.doAfterTextChanged { validatePassword(it.toString()) }
            btnRegister.setOnClickListener {
                val email = edEmail.text.toString().trim()
                val password = edPassword.text.toString().trim()
                val request = RegisterRequest(
                    email = email,
                    password = password,
                    firebaseToken = ""
                )
                when {
                    email.isEmpty() -> emailInputLayout.error =
                        getString(R.string.email_cannot_be_empty)

                    password.isEmpty() -> passwordInputLayout.error =
                        getString(R.string.password_cannot_be_empty)

                    isValidEmail(email) && isValidPassword(password) -> {
                        viewModel.fetchRegister(request)
                    }
                }
            }
        }
    }

    private fun validateEmail(email: String) {
        if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.inputEmail.isHelperTextEnabled = true
            binding.inputEmail.isErrorEnabled = false
        }
    }

    private fun validatePassword(password: String) {
        if (password.length <= 7 && password.isNotEmpty()) {
            binding.inputPassword.error = getString(R.string.minimum_8_characters)
        } else {
            binding.inputPassword.error = null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS

        return if (emailPattern.matcher(email).matches()) {
            binding.inputEmail.isErrorEnabled = false
            binding.inputEmail.isHelperTextEnabled = true
            binding.inputEmail.helperText = getString(R.string.example_test_gmail_com)
            true
        } else {
            binding.inputEmail.isHelperTextEnabled = true
            binding.inputEmail.isErrorEnabled = true
            binding.inputEmail.error = getString(R.string.invalid_email)
            false
        }

    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern1 = Pattern.compile(
            "^(?=.*[A-Z])" +
                    "(?=.*[a-z])" +
                    "(?=.*\\d)" +
                    "(?=.*[@#\$%^&+=!])" +
                    "[A-Za-z\\d@#\$%^&+=!]{8,}"
        )

        return if (password.length <= 7) {
            binding.inputPassword.isErrorEnabled = false
            binding.inputPassword.isHelperTextEnabled = true
            binding.inputPassword.error = getString(R.string.minimum_8_characters)
            false
        } else {
            binding.inputPassword.isHelperTextEnabled = true
            binding.inputPassword.isErrorEnabled = true
            binding.inputPassword.helperText = getString(R.string.minimum_8_characters)
            true
        }

    }


}