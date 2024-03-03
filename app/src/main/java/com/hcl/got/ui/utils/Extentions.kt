package com.hcl.got.ui.utils

import android.view.View

/**Visible view*/
fun View.visible()
{
   this.visibility = View.VISIBLE
}

/**Hide view*/
fun View.gone()
{
    this.visibility = View.GONE
}
