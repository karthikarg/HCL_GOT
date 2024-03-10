package com.hcl.got.ui.utils

import android.view.View

/**
 * Sets the visibility of the view to VISIBLE.
 */
fun View.visible() {
    // Set the visibility of the view to VISIBLE
    this.visibility = View.VISIBLE
}

/**
 * Sets the visibility of the view to GONE, making it invisible and not taking up any space in the layout.
 */
fun View.gone() {
    // Set the visibility of the view to GONE
    this.visibility = View.GONE
}
