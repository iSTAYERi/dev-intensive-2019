package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.TypedValue
import ru.skillbranch.devintensive.R

class InitialsDrawable(
    private val initials: String,
    private val context: Context
) : Drawable() {

    private val paint = Paint()

    init {
        val size = 112 * context.resources.displayMetrics.density
        setBounds(0, 0, size.toInt(), size.toInt())

        val textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 48f, context.resources.displayMetrics)
        paint.textSize = textSize
        paint.isAntiAlias = true
        paint.isFakeBoldText = true
        paint.setShadowLayer(6f, 0f, 0f, Color.BLACK)
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        paint.color = context.resources.getColor(R.color.color_accent, context.theme)
        canvas.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), paint)

        paint.color = Color.WHITE
        canvas.drawText(
            initials, 0, initials.length, bounds.centerX().toFloat(),
            bounds.centerY().toFloat() - paint.ascent() / 2, paint
        )
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

}