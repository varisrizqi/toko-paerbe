package com.tipiz.toko_paerbe.ui.prelogin.login

import android.os.Build
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.tipiz.core.network.data.login.LoginRequest
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onError
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentLoginBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class LoginFragment :
    BaseFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {
    override val viewModel: LoginViewModel by viewModel()

    override fun initView() {
        login()
        binding.btnRegist.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    override fun initViewModel() {
        with(viewModel) {
            responseLogin.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onSuccess { token ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            saveLogin(token)
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_dashBoardFragment)
                    Toast.makeText(context, "Berhasil Login", Toast.LENGTH_SHORT).show()
                }.onError { Toast.makeText(context, "Gagal Login", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private fun login() {

        with(binding) {
            edEmail.doAfterTextChanged { validateEmail(it.toString()) }
            edPassword.doAfterTextChanged { validatePassword(it.toString()) }
            btnLogin.setOnClickListener {
                val email = edEmail.text.toString().trim()
                val password = edPassword.text.toString().trim()
                val request = LoginRequest(
                    email = email,
                    password = password,
                    firebaseToken = ""
                )

                when {
                    email.isEmpty() -> inputEmail.error = getString(R.string.email_cannot_be_empty)
                    password.isEmpty() -> inputPassword.error =
                        getString(R.string.password_cannot_be_empty)

                    isValidEmail(email) && isValidPassword(password) -> {
                        viewModel.fetchLogin(request)
                    }
                }
            }
        }
    }

    private fun validateEmail(email: String) {

        with(binding.inputEmail) {
            val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            if (!isValid) {
                error = getString(R.string.invalid_email)
            } else {
                helperText = getString(R.string.example_test_gmail_com)
            }

        }
    }

    private fun validatePassword(password: String) {
        with(binding.inputPassword) {
            val isValid = password.length >= 8
            isErrorEnabled = !isValid
            helperText = if (isValid) getString(R.string.minimum_8_characters) else null
            error = if (!isValid) getString(R.string.minimum_8_characters) else null
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