package com.getir.components


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.getir.R
import com.getir.databinding.CustomToolbarBinding
import com.getir.utils.clickWithDebounce


class CustomToolBar(context: Context, attrs: AttributeSet) : ConstraintLayout(
    context, attrs
) {
    private var textViewPrice:TextView
    init {
        inflate(context, R.layout.custom_toolbar, this)
        val binding = CustomToolbarBinding.bind(this)


        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar)

        val textColor = attributes.getColor(
            R.styleable.CustomToolBar_toolBarTextColor, context.getColor(R.color.white)
        )
        textViewPrice=binding.tvPrice

        val backgroundColor = attributes.getColor(
            R.styleable.CustomToolBar_toolBarBackgroundColor,
            ContextCompat.getColor(context, R.color.app_purple)
        )
        setBackgroundColor(backgroundColor)

        binding.toolbarText.apply {
            text = attributes.getString(R.styleable.CustomToolBar_toolBarText)
            setTextColor(textColor)
        }

        attributes.recycle()


    }
    fun setPrice(price:String){
        textViewPrice.text = price

    }



}