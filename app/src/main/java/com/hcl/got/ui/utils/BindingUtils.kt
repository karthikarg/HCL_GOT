package com.hcl.got.ui.utils

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Utility object containing binding-related methods for Android data binding.
 * These methods are commonly used to define custom binding adapters and other utilities
 * to facilitate data binding in Android applications.
 */
object BindingUtils {

    /**
     * Sets the visibility of a View based on a String value.
     * @param view The View whose visibility will be set.
     * @param value The String value determining the visibility.
     */
    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibility(view: View, value: String) {
         /** If the value is not empty, set the visibility of the view to VISIBLE*/
        if (value.isNotEmpty())
            view.visible()
        /** Otherwise, set the visibility of the view to GONE*/
        else
            view.gone()
    }
}