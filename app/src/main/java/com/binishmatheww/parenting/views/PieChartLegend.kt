package com.binishmatheww.parenting.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import com.binishmatheww.parenting.R


/*
This is not implemented yet.
Added to demonstrate a custom view to show pie-chart legend.
 */

class PieChartLegend : ConstraintLayout {

    private val title : TextView
    private val description : TextView

    private var layoutParams: LayoutParams
    private val set = ConstraintSet()

    constructor(context: Context) : super(context) {

        title = TextView(context)
        title.id = generateViewId()
        title.typeface = ResourcesCompat.getFont(context, R.font.segoe_ui)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        title.setTextColor(ResourcesCompat.getColor(context.resources, R.color.black, null))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_CONSTRAINT)
        layoutParams.setMargins(10, 0, 0, 0)
        title.layoutParams = layoutParams

        super.addView(title,0)

        description = TextView(context)
        description.id = generateViewId()
        description.typeface = ResourcesCompat.getFont(context, R.font.red_hat_display)
        description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        description.setTextColor(ResourcesCompat.getColor(context.resources, R.color.black, null))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_CONSTRAINT)
        layoutParams.setMargins(0, 10, 0, 0)
        description.layoutParams = layoutParams

        super.addView(description,0)


        set.clone(this)

        set.connect(title.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP, 0)
        set.connect(title.id, ConstraintSet.LEFT, this.id, ConstraintSet.LEFT, 0)

        set.connect(description.id, ConstraintSet.START, title.id, ConstraintSet.START, 0)
        set.connect(description.id, ConstraintSet.TOP, title.id, ConstraintSet.BOTTOM, 0)


        set.applyTo(this)

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

        title = TextView(context)
        title.id = generateViewId()
        title.typeface = ResourcesCompat.getFont(context, R.font.segoe_ui)
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        title.setTextColor(ResourcesCompat.getColor(context.resources, R.color.black, null))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_CONSTRAINT)
        layoutParams.setMargins(10, 0, 0, 0)
        title.layoutParams = layoutParams


        super.addView(title,0)

        description = TextView(context)
        description.id = generateViewId()
        description.typeface = ResourcesCompat.getFont(context, R.font.red_hat_display)
        description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        description.setTextColor(ResourcesCompat.getColor(context.resources, R.color.black, null))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_CONSTRAINT)
        layoutParams.setMargins(0, 10, 0, 0)
        description.layoutParams = layoutParams

        super.addView(description,0)


        set.clone(this)

        set.connect(title.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP, 0)
        set.connect(title.id, ConstraintSet.LEFT, this.id, ConstraintSet.LEFT, 0)

        set.connect(description.id, ConstraintSet.START, title.id, ConstraintSet.START, 0)
        set.connect(description.id, ConstraintSet.TOP, title.id, ConstraintSet.BOTTOM, 0)


        set.applyTo(this)



    }

    fun setTitle(title : String){
        this.title.text = title
    }

    fun setDescription(description : String){
        this.description.text = description
    }

}