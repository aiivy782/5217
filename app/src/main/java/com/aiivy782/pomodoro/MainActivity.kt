package com.aiivy782.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit
import com.aiivy782.pomodoro.databinding.ActivityMainBinding

    class MainActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMainBinding
        private var isTimerRunning = false
        var timer: CountDownTimer? = null
        val workTimeMillis = TimeUnit.MINUTES.toMillis(52)
        val breakTimeMillis = TimeUnit.MINUTES.toMillis(17)
        override fun onCreate(savedInstanceState: Bundle?) {

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding = ActivityMainBinding.inflate(layoutInflater)
            super.onCreate(savedInstanceState)
            setContentView(binding.root)

            binding.startPauseButton.setOnClickListener {
                if (isTimerRunning) {
                    pauseTimer()
                } else {
                    startTimer()
                }
            }

        binding.resetButton.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        isTimerRunning = true
        binding.startPauseButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pause, 0, 0, 0) // Иконка "Пауза"
        binding.resetButton.visibility = View.VISIBLE
        binding.startPauseButton.backgroundTintList = getColorStateList(R.color.material_you_accent) // Изменение цвета под Material You

        var timer = object : CountDownTimer(workTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                binding.timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                // Здесь можно добавить обработку смены режима (работа/перерыв)
                // и включить таймер для следующего интервала
            }
        }.start()
    }

    private fun pauseTimer() {
        isTimerRunning = false
        binding.startPauseButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play_arrow, 0, 0, 0) // Иконка "Старт"
        binding.resetButton.visibility = View.VISIBLE
        binding.startPauseButton.backgroundTintList = getColorStateList(R.color.material_you_accent) // Изменение цвета под Material You

        timer?.cancel()
    }

    private fun resetTimer() {
        isTimerRunning = false
        binding.startPauseButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stop, 0, 0, 0) // Иконка "Старт"
        binding.resetButton.visibility = View.GONE
        binding.startPauseButton.backgroundTintList = getColorStateList(R.color.material_you_accent) // Изменение цвета под Material You

        timer?.cancel()
        binding.timerTextView.text = "00:00"
    }
}