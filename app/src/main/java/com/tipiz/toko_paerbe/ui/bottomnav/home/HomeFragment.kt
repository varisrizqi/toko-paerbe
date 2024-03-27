package com.tipiz.toko_paerbe.ui.bottomnav.home

import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentHomeBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import com.tipiz.toko_paerbe.ui.utils.Constant.key_in
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()

    override fun initView() {
        getTheme()
//        getLocalize()
        binding.btnLogout.text = getString(R.string.logout)
        binding.btnLogout.setOnClickListener {
//            viewModel.clearSession()
            viewModel.resetAll()
            activity?.supportFragmentManager?.findFragmentById(R.id.container_main_nav_host)
                ?.findNavController()?.navigate(R.id.action_dashBoardFragment_to_loginFragment)
        }

    }

    override fun initViewModel() {
        with(viewModel){
            binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
                when(isChecked){
                    true-> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        setTheme(true)
                        binding.switchTheme.isChecked = true
                    }
                    false ->{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        setTheme(false)
                        binding.switchTheme.isChecked = false
                    }
                }
            }

//            binding.switchLocalize.setOnCheckedChangeListener { _, isChecked ->
//                val lang: String
//                when (isChecked) {
//                    true -> {
//                        AppCompatDelegate.setApplicationLocales(
//                            LocaleListCompat.forLanguageTags(key_in)
//                        )
//                        lang = key_in
//                    }
//
//                    false -> {
//                        AppCompatDelegate.setApplicationLocales(
//                            LocaleListCompat.forLanguageTags(key_en)
//                        )
//                        lang = key_en
//
//                    }
//                }
//                context?.let {  viewModel.setLocalize(lang) }
//            }
        }

    }

    private fun getTheme(){
        val themeChecker = viewModel.getTheme()
        binding.switchTheme.isChecked = themeChecker
    }

    private fun getLocalize() {
        val languageCode = viewModel.getLocalize()
        binding.switchLocalize.isChecked = languageCode.equals(key_in, ignoreCase = false)
    }

}