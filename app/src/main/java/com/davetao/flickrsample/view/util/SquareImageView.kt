package com.davetao.flickrsample.view.util

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView


/**
 * Added a square image view because of asthetic reasons, otherwise would need to use a staggered grid imageview
 */
class SquareImageView : ImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }
}