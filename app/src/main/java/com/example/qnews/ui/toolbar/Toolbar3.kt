package com.example.qnews.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.qnews.R

class Toolbar3 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : LinearLayout(context, attrs, defStyleAttrs) {

    val textViewSearchedTitle: TextView
    val imageVIewGetBack: ImageView

    init {
        inflate(context, R.layout.toolbar_type3, this)

        imageVIewGetBack = findViewById(R.id.imageViewGetBack)
        textViewSearchedTitle = findViewById(R.id.textViewSearchedTitle)
    }
}