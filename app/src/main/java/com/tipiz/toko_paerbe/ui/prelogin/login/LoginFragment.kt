package com.tipiz.toko_paerbe.ui.prelogin.login

import android.os.Build
import android.util.Patterns
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tipiz.core.data.network.data.login.LoginRequest
import com.tipiz.core.utils.state.UiState
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentLoginBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import com.tipiz.toko_paerbe.ui.utils.CustomToast
import com.tipiz.toko_paerbe.ui.utils.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initViewModel() {
        with(viewModel) {
            lifecycleScope.launch {
                responseLogin.collectLatest { state ->
                    when (state) {
                        is UiState.Loading -> {
                            binding.pbLogin.visibility = View.VISIBLE
                            binding.btnLogin.visibility = View.INVISIBLE
                        }

                        is UiState.Success -> {
                            saveLogin(state.data)
                            showToast(requireContext(),getString(R.string.login_successfully))
                            findNavController().navigate(R.id.action_loginFragment_to_dashBoardFragment)
                        }

                        is UiState.Error -> {
                            context?.let { CustomToast.showSnackBar(it,binding.root, getString(R.string.wrong_email_or_password),) }
                            binding.pbLogin.visibility = View.INVISIBLE
                            binding.btnLogin.visibility = View.VISIBLE
                        }


                        else -> {}
                    }
                }
            }
        }
    }


    private fun login() {
        with(binding) {
            val emailInputLayout = inputEmail
            val passwordInputLayout = inputPassword
            edEmail.doAfterTextChanged { validateEmail() }
            edPassword.doAfterTextChanged { validatePassword() }
            btnLogin.setOnClickListener {
                val email = edEmail.text.toString().trim()
                val password = edPassword.text.toString().trim()
                val request = LoginRequest(
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
                        viewModel.fetchLogin(request)
                    }
                }
            }
        }
    }

    private fun validateEmail() {
        with(binding.inputEmail) {
            isHelperTextEnabled = true
            isErrorEnabled = false
        }
    }

    private fun validatePassword() {
        with(binding.inputPassword) {
            isHelperTextEnabled = true
            isErrorEnabled = false
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
            binding.inputPassword.isErrorEnabled = true
            binding.inputPassword.isHelperTextEnabled = true
            binding.inputPassword.helperText = getString(R.string.minimum_8_characters)
            true
        }
    }


}