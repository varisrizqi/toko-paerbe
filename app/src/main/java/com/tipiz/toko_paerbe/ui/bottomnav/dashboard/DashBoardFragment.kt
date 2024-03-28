package com.tipiz.toko_paerbe.ui.bottomnav.dashboard

import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentDashBoardBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import com.tipiz.toko_paerbe.ui.utils.Constant.FLAG_TRANSACTION
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashBoardFragment :
    BaseFragment<FragmentDashBoardBinding, DashBoardViewModel>(FragmentDashBoardBinding::inflate) {
    override val viewModel: DashBoardViewModel by viewModel()
    private lateinit var navController: NavController

    override fun initView() {
        // Navigation Setup
        val navHostFragment =
            this.childFragmentManager.findFragmentById(R.id.nav_bottom_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNav?.setupWithNavController(navController)
        binding.nrMain?.setupWithNavController(navController)
        binding.lnMain?.setupWithNavController(navController)
        binding.nvMain?.setupWithNavController(navController)

        val args = DashBoardFragmentArgs.fromBundle(requireArguments()).flag

        Log.d("TAG", "onViewCreated: $args")
        if (args == FLAG_TRANSACTION) {
            binding.bottomNav?.selectedItemId = R.id.navigation_transaction
            binding.nrMain?.selectedItemId = R.id.navigation_transaction
            binding.lnMain?.selectedItemId = R.id.navigation_transaction
            binding.nvMain?.menu?.performIdentifierAction(R.id.navigation_transaction, 0)
        }

        val container: ViewGroup = binding.containerMainFragment

        container.addView(object : View(requireContext()) {
            override fun onConfigurationChanged(newConfig: Configuration?) {
                super.onConfigurationChanged(newConfig)
                computeWindowSizeClasses()
            }
        })

        computeWindowSizeClasses()

    }

    private fun computeWindowSizeClasses() {
        val metrics =
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(requireActivity())
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density
        val windowSizeClass = WindowSizeClass.compute(width / density, height / density)
        // COMPACT, MEDIUM, or EXPANDED
        val widthWindowSizeClass = windowSizeClass.windowWidthSizeClass

    }

    override fun initViewModel() {
        lifecycleScope.launch {
            val username = viewModel.getUserName()
            binding.toolbar.title = username
        }
    }

    /*
    * original bottom nav
    * for remember me
    * */
    private fun setNavigation(){
        val navView: BottomNavigationView? = binding.bottomNav
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_bottom_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navView?.setupWithNavController(navController)
    }


}