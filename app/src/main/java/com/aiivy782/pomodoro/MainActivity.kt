package com.aiivy782.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.*
import java.util.concurrent.TimeUnit
import android.view.View
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var isTimerRunning = false
    private var timer: CountDownTimer? = null

    private val workTimeMillis = TimeUnit.MINUTES.toMillis(52)
    private val breakTimeMillis = TimeUnit.MINUTES.toMillis(17)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        startPauseButton.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        isTimerRunning = true
        startPauseButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pause, 0, 0, 0) // Иконка "Пауза"
        resetButton.visibility = View.VISIBLE
        startPauseButton.backgroundTintList = getColorStateList(R.color.material_you_accent) // Изменение цвета под Material You

        timer = object : CountDownTimer(workTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                // Здесь можно добавить обработку смены режима (работа/перерыв)
                // и включить таймер для следующего интервала
            }
        }.start()
    }

    private fun pauseTimer() {
        isTimerRunning = false
        startPauseButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play_arrow, 0, 0, 0) // Иконка "Старт"
        resetButton.visibility = View.VISIBLE
        startPauseButton.backgroundTintList = getColorStateList(R.color.material_you_accent) // Изменение цвета под Material You

        timer?.cancel()
    }

    private fun resetTimer() {
        isTimerRunning = false
        startPauseButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stop, 0, 0, 0) // Иконка "Старт"
        resetButton.visibility = View.GONE
        startPauseButton.backgroundTintList = getColorStateList(R.color.material_you_accent) // Изменение цвета под Material You

        timer?.cancel()
        timerTextView.text = "00:00"
    }
}
