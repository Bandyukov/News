package com.example.qnews.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.qnews.R

class Toolbar1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : LinearLayout(context, attrs, defStyleAttrs)
{

    val imageViewLoop: ImageView

    init {
        inflate(context, R.layout.toolbar_type1, this)
        imageViewLoop = findViewById(R.id.imageViewLoopIcon)
    }
}