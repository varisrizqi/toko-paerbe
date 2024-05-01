package com.tipiz.toko_paerbe.ui.bottomnav.store.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentBottomSheetBinding


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        with(binding) {


            cgSort.setOnCheckedStateChangeListener { _, _ ->

            }
            tvFilter.text = getString(R.string.filter)
            tvCategory.text = getString(R.string.category)
            tvPrice.text = getString(R.string.textPrice)
            tvSort.text = getString(R.string.sort)
            btnReset.text = getString(R.string.reset)
            btnShow.text = getString(R.string.show_products)
            cpReview.text = getString(R.string.review)
            cpSale.text = getString(R.string.sale)
            cpLowest.text = getString(R.string.lowest_price)
            cpHighest.text = getString(R.string.highest_price)
            cpApple.text = getString(R.string.apple)
            cpAsus.text = getString(R.string.asus)
            cpDell.text = getString(R.string.dell)
            cpLenovo.text = getString(R.string.lenovo)
            inputLowest.hint = getString(R.string.lowest)
            inputHighest.hint = getString(R.string.highest)

        }
    }

}
