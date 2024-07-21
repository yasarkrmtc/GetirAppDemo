package com.getir.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.getir.R
import com.getir.databinding.CustomToolbarBinding
import com.getir.utils.clickWithDebounce

class CustomToolBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var textViewPrice: TextView
    private lateinit var closeButton: ImageView
    private lateinit var btnChart: View
    private lateinit var whiteTrashBtn: View

    fun navigationIconSetOnClickListener(function: (view: View) -> Unit) {
        navigationIconSetOnClickListener = function
    }

    fun chartIconSetOnClickListener(function: (view: View) -> Unit) {
        chartIconSetOnClickListener = function
    }

    fun whiteTrashBtnSetOnClickListener(function: (view: View) -> Unit) {
        whiteTrashBtnSetOnClickListener = function
    }

    private var chartIconSetOnClickListener: (view: View) -> Unit = {}
    private var navigationIconSetOnClickListener: (view: View) -> Unit = {}
    private var whiteTrashBtnSetOnClickListener: (view: View) -> Unit = {}

    init {
        inflate(context, R.layout.custom_toolbar, this)
        val binding = CustomToolbarBinding.bind(this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar)

        val textColor = attributes.getColor(
            R.styleable.CustomToolBar_toolBarTextColor, context.getColor(R.color.white)
        )
        val navigationIconVisibility =
            attributes.getBoolean(R.styleable.CustomToolBar_navigation_icon_visibility, true)
        val firstIconVisibility =
            attributes.getBoolean(R.styleable.CustomToolBar_action_first_icon_visibility, true)
        val secondIconVisibility =
            attributes.getBoolean(R.styleable.CustomToolBar_action_second_icon_visibility, true)

        textViewPrice = binding.tvPrice
        closeButton = binding.closeButton
        btnChart = binding.btnChart
        whiteTrashBtn = binding.whiteTrashBtn

        val backgroundColor = attributes.getColor(
            R.styleable.CustomToolBar_toolBarBackgroundColor,
            ContextCompat.getColor(context, R.color.app_purple)
        )
        setBackgroundColor(backgroundColor)

        binding.toolbarText.apply {
            text = attributes.getString(R.styleable.CustomToolBar_toolBarText)
            setTextColor(textColor)
        }

        closeButton.apply {
            visibility = if (navigationIconVisibility) View.VISIBLE else View.INVISIBLE
            setOnClickListener {
                navigationIconSetOnClickListener.invoke(it)
            }
        }

        btnChart.apply {
            visibility = if (firstIconVisibility) View.VISIBLE else View.INVISIBLE
            clickWithDebounce {
                chartIconSetOnClickListener.invoke(it)
            }
        }

        whiteTrashBtn.apply {
            visibility = if (secondIconVisibility) View.VISIBLE else View.INVISIBLE
            clickWithDebounce {
                whiteTrashBtnSetOnClickListener.invoke(it)
            }
        }

        attributes.recycle()
    }

    fun setPrice(price: String) {
        textViewPrice.text = price
    }
}
