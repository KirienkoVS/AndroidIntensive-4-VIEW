package com.example.androidintensiveview_2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import java.util.*

class CustomAnalogClock(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val displayMetrics: DisplayMetrics = context.resources.displayMetrics

    private val centerX = displayMetrics.widthPixels / 2f
    private val centerY = displayMetrics.heightPixels / 2f

    private val circlePaint = Paint()
    private val secondPaint = Paint()
    private val minutePaint = Paint()
    private val hourPaint = Paint()

    init {
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.CustomAnalogClock)

        circlePaint.apply {
            color = Color.BLACK
            strokeWidth = 20f
            isAntiAlias = true
            style = Paint.Style.STROKE
        }

        secondPaint.apply {
            color = attributes.getColor(R.styleable.CustomAnalogClock_second_hand_color, Color.BLUE)
            strokeWidth = attributes.getFloat(R.styleable.CustomAnalogClock_second_hand_width, 5f)
            isAntiAlias = true
        }

        minutePaint.apply {
            color = attributes.getColor(R.styleable.CustomAnalogClock_minute_hand_color, Color.BLACK)
            strokeWidth = attributes.getFloat(R.styleable.CustomAnalogClock_minute_hand_width, 10f)
            isAntiAlias = true
        }

        hourPaint.apply {
            color = attributes.getColor(R.styleable.CustomAnalogClock_hour_hand_color, Color.RED)
            strokeWidth = attributes.getFloat(R.styleable.CustomAnalogClock_hour_hand_width, 15f)
            isAntiAlias = true
        }

        attributes.recycle()
    }

    private fun drawClockCircle(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, 400f, circlePaint)
        for (i in 0 until 12) {
            canvas.save()
            canvas.rotate(360f / 12f * i, centerX, centerY)
            circlePaint.strokeWidth = 25f
            canvas.drawLine(centerX + 400f, centerY, centerX + 340f, centerY, circlePaint)
            circlePaint.strokeWidth = 20f
            canvas.restore()
        }
    }

    private fun drawHands(canvas: Canvas) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR).toFloat()
        val minute = calendar.get(Calendar.MINUTE).toFloat()
        val second = calendar.get(Calendar.SECOND).toFloat()

        drawHourHand(canvas, hour)
        drawMinuteHand(canvas, minute)
        drawSecondHand(canvas, second)
    }

    private fun drawSecondHand(canvas: Canvas, second: Float) {
        canvas.save()
        canvas.rotate(360f / 60f * second, centerX, centerY)
        canvas.drawLine(centerX, centerY + 80f, centerX, centerY - 290f, secondPaint)
        canvas.restore()
    }

    private fun drawMinuteHand(canvas: Canvas, minute: Float) {
        canvas.save()
        canvas.rotate(360f / 60f * minute, centerX, centerY)
        canvas.drawLine(centerX, centerY + 80f, centerX, centerY - 250f, minutePaint)
        canvas.restore()
    }

    private fun drawHourHand(canvas: Canvas, hour: Float) {
        canvas.save()
        canvas.rotate(360f / 12f * hour, centerX, centerY)
        canvas.drawLine(centerX, centerY + 80f, centerX, centerY - 200f, hourPaint)
        canvas.restore()
    }

    override fun onDraw(canvas: Canvas) {
        drawClockCircle(canvas)
        drawHands(canvas)
        postInvalidateDelayed(1000)
    }
}