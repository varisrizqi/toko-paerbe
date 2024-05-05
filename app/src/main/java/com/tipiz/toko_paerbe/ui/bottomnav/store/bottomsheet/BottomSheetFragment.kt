package com.tipiz.toko_paerbe.ui.bottomnav.store.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentBottomSheetBinding
import com.tipiz.toko_paerbe.ui.utils.Constant
import com.tipiz.toko_paerbe.ui.utils.formatBottomSheet
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
    private val viewModel: BottomSheetViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper =
            ContextThemeWrapper(requireContext(), R.style.Theme_Tokopaerbe)
        binding = FragmentBottomSheetBinding.inflate(inflater.cloneInContext(contextThemeWrapper), container, false)
        return binding.root
    }

    private fun onTextChangedListener(editText: TextInputEditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                editText.removeTextChangedListener(this)
                try {
                    var originalString = s.toString()
                    if (originalString.contains(",")) {
                        originalString = originalString.replace(",".toRegex(), "")
                    }
                    val longVal = originalString.toLong()
                    val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
                    formatter.applyPattern("#,###,###,###")
                    val formattedString = formatter.format(longVal)

                    //setting text after format to EditText
                    editText.setText(formattedString)
                    editText.setSelection(editText.text?.length ?: 0)
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
                editText.addTextChangedListener(this)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //untuk mengubah nilai

        initView()

    }

    private fun initView() {
        with(binding) {

            tvFilter.text = getString(R.string.filter)
            tvCategory.text = getString(R.string.category)
            tvPrice.text = getString(R.string.textPrice)
            tvSort.text = getString(R.string.sort)
            btnReset.text = getString(R.string.reset)
            btnShow.text = getString(R.string.show_products)


            viewModel.category = arguments?.getString(Constant.BUNDLE_KEY_CATEGORY)
            viewModel.sort = arguments?.getString(Constant.BUNDLE_KEY_SORT)
            viewModel.lowest = if (arguments?.getString(Constant.BUNDLE_KEY_LOWEST) != "null") {
                arguments?.getString(Constant.BUNDLE_KEY_LOWEST)
            } else {
                null
            }
            viewModel.highest = if (arguments?.getString(Constant.BUNDLE_KEY_HIGHEST) != "null") {
                arguments?.getString(Constant.BUNDLE_KEY_HIGHEST)
            } else {
                null
            }
            Log.e(
                "BottomSheet",
                " varis : ${viewModel.sort},  ${viewModel.category},  ${viewModel.lowest},  ${viewModel.highest} "
            )
            resetButtonVisibility()
            when (viewModel.sort) {
                resources.getString(R.string.review) -> cpReview.isChecked = true
                resources.getString(R.string.sale) -> cpSale.isChecked = true
                resources.getString(R.string.lowest_price) -> cpLowest.isChecked = true
                resources.getString(R.string.highest_price) -> cpHighest.isChecked = true
            }
            when (viewModel.category) {
                resources.getString(R.string.apple) -> cpApple.isChecked = true
                resources.getString(R.string.dell) -> cpDell.isChecked = true
                resources.getString(R.string.asus) -> cpAsus.isChecked = true
                resources.getString(R.string.lenovo) -> cpLenovo.isChecked = true
            }

            edLowest.setText(
                if (viewModel.lowest != "null" && viewModel.lowest != null) {
                    val lowesText = viewModel.lowest.toString()
                    formatBottomSheet(lowesText)
                } else {
                    null
                }
            )

            edHighest.setText(
                if (viewModel.highest != "null" && viewModel.highest != null) {
                    val highestText = viewModel.highest.toString()

                    formatBottomSheet(highestText)
                } else {
                    null
                }
            )

            //akan menempatkan kursor di akhir teks
            edHighest.setSelection(edHighest.text?.length ?: 0)


            binding.cgSort.setOnCheckedStateChangeListener { _, _ ->
                viewModel.sort = when (binding.cgSort.checkedChipId) {
                    R.id.cp_review -> resources.getString(R.string.review)
                    R.id.cp_sale -> resources.getString(R.string.sale)
                    R.id.cp_lowest -> resources.getString(R.string.lowest_price)
                    R.id.cp_highest -> resources.getString(R.string.highest_price)
                    else -> null
                }
               resetButtonVisibility()
            }

            cgCategory.setOnCheckedStateChangeListener { _, _ ->
                viewModel.category = when (cgCategory.checkedChipId) {
                    R.id.cp_apple -> getString(R.string.apple)
                    R.id.cp_dell -> getString(R.string.dell)
                    R.id.cp_asus -> getString(R.string.asus)
                    R.id.cp_lenovo -> getString(R.string.lenovo)
                    else -> null
                }
                resetButtonVisibility()
            }

            edLowest.doOnTextChanged { _, _, _, _ ->
                if (edLowest.text.isNullOrEmpty()) {
                    viewModel.lowest = null
                } else {
                    var originalString = edLowest.text.toString()
                    if (originalString.contains(",")) {
                        originalString = originalString.replace(",".toRegex(), "")
                    }
                    viewModel.lowest = originalString
                }
                resetButtonVisibility()
            }

            edLowest.addTextChangedListener { onTextChangedListener(edLowest) }

            edHighest.doOnTextChanged { _, _, _, _ ->
                if (edHighest.text.isNullOrEmpty()) {
                    viewModel.highest = null
                } else {
                    var originalString = edHighest.text.toString()
                    if (originalString.contains(",")) {
                        originalString = originalString.replace(",".toRegex(), "")
                    }
                    viewModel.highest = originalString
                }
                resetButtonVisibility()
            }

            edHighest.addTextChangedListener { onTextChangedListener(edHighest) }

            btnShow.setOnClickListener {
                setFragmentResult(
                    Constant.REQUEST_KEY_BOTTOM_SHEET,
                    bundleOf(
                        Constant.BUNDLE_KEY_SORT to viewModel.sort,
                        Constant.BUNDLE_KEY_CATEGORY to viewModel.category,
                        Constant.BUNDLE_KEY_LOWEST to viewModel.lowest,
                        Constant.BUNDLE_KEY_HIGHEST to viewModel.highest
                    )
                )

                dismiss()
            }

            btnReset.setOnClickListener {
                cgSort.clearCheck()
                cgCategory.clearCheck()
                edLowest.text?.clear()
                edHighest.text?.clear()
            }


        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState)
        if (bottomSheetDialog is BottomSheetDialog) {
            bottomSheetDialog.behavior.skipCollapsed = true
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    private fun resetButtonVisibility() {
        binding.btnReset.visibility =
            if (viewModel.sort != null || viewModel.category != null || viewModel.lowest != null || viewModel.highest != null) View.VISIBLE else View.GONE
    }


}
