package com.tipiz.toko_paerbe.ui.bottomnav.notification

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentNotificationBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment :
    BaseFragmentBottomNav<FragmentNotificationBinding, NotificationViewModel>(
        FragmentNotificationBinding::inflate
    ) {
    override val viewModel: NotificationViewModel by viewModel()
    private val adapter by lazy {
        NotificationAdapter(
            updateIsCheck = { int, boolean ->
                updateIsChecked(int,boolean)
            }
        )
    }

    override fun initView() {

        binding.rvNotification.adapter = adapter
        binding.rvNotification.layoutManager = LinearLayoutManager(context)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initViewModel() {
        with(viewModel){
            getPromoNotification().observe(viewLifecycleOwner){
                adapter.submitList(it.reversed())
                binding.ivNotificationError.isVisible = it.isNullOrEmpty()
                binding.tvNotificationErrorMessage.isVisible = it.isNullOrEmpty()
                binding.tvNotificationErrorCode.isVisible = it.isNullOrEmpty()
                binding.tvNotificationErrorCode.text = getString(R.string.http_exception_404_code)
            }

        }

    }

    private fun updateIsChecked(id: Int, state: Boolean) {
        viewModel.updateIsCheck(id, state)
    }
}