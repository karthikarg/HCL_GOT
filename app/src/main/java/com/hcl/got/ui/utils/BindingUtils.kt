package com.hcl.got.ui.utils

import android.view.View
import androidx.databinding.BindingAdapter

object BindingUtils {

    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibility(view: View, value: String) {
        if (value.isNotEmpty())
            view.visible()
        else
            view.gone()

    }
}