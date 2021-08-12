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

    val imageViewStartSearching: ImageView
    val imageViewGetBack: ImageView
    val editTextSearchNewsAndArticles: EditText

    init {
        inflate(context, R.layout.toolbar_type2, this)
        imageViewGetBack = findViewById(R.id.imageViewGetBack)
        imageViewStartSearching = findViewById(R.id.imageViewStartSearching)
        editTextSearchNewsAndArticles = findViewById(R.id.editTextSearchNewsAndArticles)
    }
}