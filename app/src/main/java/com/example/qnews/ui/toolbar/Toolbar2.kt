package com.example.qnews.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.qnews.R

class Toolbar2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : LinearLayout(context, attrs, defStyleAttrs) {

    val imageViewstartSearching: ImageView
    val imageVIewGetBack: ImageView
    val editTextSeaechNewsAndAtricles: EditText

    init {
        inflate(context, R.layout.toolbar_type2, this)

        imageVIewGetBack = findViewById(R.id.imageViewGetBack)
        imageViewstartSearching = findViewById(R.id.imageViewStartSearching)
        editTextSeaechNewsAndAtricles = findViewById(R.id.editTextSearchNewsAndArticles)
    }
}