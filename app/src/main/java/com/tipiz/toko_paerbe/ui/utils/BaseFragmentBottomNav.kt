package com.tipiz.toko_paerbe.ui.utils

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.tipiz.toko_paerbe.ui.MainActivity
import com.tipiz.toko_paerbe.ui.bottomnav.dashboard.DashBoardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragmentBottomNav<VB : ViewBinding, VM : ViewModel>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private val viewModel2: DashBoardViewModel by viewModel()

    protected lateinit var binding: VB
    protected abstract val viewModel: VM


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingFactory(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel2.getIsLogin().observe(viewLifecycleOwner){
            if (!it) {
                println("varis BaseFragmentBottomNav 1")
                val intent = Intent((requireActivity()), MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }



        initView()
        initViewModel()

    }


    abstract fun initView()
    abstract fun initViewModel()
}