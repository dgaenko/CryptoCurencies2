package ru.familion.cryptocurencies.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue

import ru.familion.cryptocurencies.R


class Utility(val context: Context) {

    fun getItemDrawable(resourceName: String): Drawable {
        var resId = context.resources.getIdentifier(resourceName.toLowerCase(), "drawable", context.packageName)
        resId = if (resId != 0) resId else R.drawable.fail
        return context.resources.getDrawable(resId)
    }

    fun getValueColor(value: Double): Int {
        return context.resources.getColor(if (value > 0) R.color.colorValueDown else R.color.colorValueUp)
    }

    fun getResIdFromAttribute(attr: Int): Int {
        var res = 0
        if (attr > 0) {
            val typedvalueattr = TypedValue()
            context.getTheme().resolveAttribute(attr, typedvalueattr, true);
            res = typedvalueattr.resourceId
        }
        return res
    }

}