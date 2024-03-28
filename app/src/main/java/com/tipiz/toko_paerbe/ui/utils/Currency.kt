package com.tipiz.toko_paerbe.ui.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun currency(number: Int): String {
    val currencyIn = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp."
    formatRp.groupingSeparator = '.'

    currencyIn.decimalFormatSymbols = formatRp
    return currencyIn.format(number).dropLast(3)
}
