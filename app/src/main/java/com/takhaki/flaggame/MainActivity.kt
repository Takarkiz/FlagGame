package com.takhaki.flaggame

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val orderContents = listOf("赤上げて", "白上げて", "赤下げて", "白下げて")
    var isRedFlagUp: Boolean = false
    var isWhiteFlagUp: Boolean = false
    var randomNumber = 0
    var point = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        whiteFlagUpImageView.visibility = View.INVISIBLE
        redFlagUpImageView.visibility = View.INVISIBLE

        gameSet()

        whiteFragButton.setOnClickListener {

            if (isWhiteFlagUp) {
                whiteFlagUpImageView.visibility = View.INVISIBLE
                whiteFlagDownImageView.visibility = View.VISIBLE
            } else {
                whiteFlagDownImageView.visibility = View.INVISIBLE
                whiteFlagUpImageView.visibility = View.VISIBLE
            }

            when (randomNumber) {
                0, 2 -> incorrect()
                1 -> if (isWhiteFlagUp) incorrect() else correct()
                3 -> if (isWhiteFlagUp) correct() else incorrect()
            }

            gameSet()
            isWhiteFlagUp = !isWhiteFlagUp
        }

        redFragButton.setOnClickListener {

            if (isRedFlagUp) {
                redFlagUpImageView.visibility = View.INVISIBLE
                redFlagDownImageView.visibility = View.VISIBLE
            } else {
                redFlagDownImageView.visibility = View.INVISIBLE
                redFlagUpImageView.visibility = View.VISIBLE
            }

            when (randomNumber) {
                1, 3 -> incorrect()
                0 -> if (isRedFlagUp) incorrect() else correct()
                2 -> if (isRedFlagUp) correct() else incorrect()
            }

            gameSet()
            isRedFlagUp = !isRedFlagUp
        }

        nothingButton.setOnClickListener {
            when (randomNumber) {
                0 -> if (isRedFlagUp) correct() else incorrect()
                1 -> if (isWhiteFlagUp) correct() else incorrect()
                2 -> if (isRedFlagUp) incorrect() else correct()
                3 -> if (isWhiteFlagUp) incorrect() else correct()
            }
            gameSet()
        }
    }

    private fun gameSet() {
        scoreText.text = point.toString()
        randomNumber = (0..3).shuffled().first()
        flagOrderTextView.text = orderContents[randomNumber]
    }

    private fun correct() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.correct2)
        point++
        mediaPlayer.start()
    }

    private fun incorrect() {
        val pref: SharedPreferences = getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        val defaultBestValue = pref.getInt("BestScore", 0)
        if (point > defaultBestValue) {
            pref.edit().apply {
                putInt("BestScore", point)
                apply()
            }

            showDialog("新記録達成！", "最高記録は今回の${point}です")
        } else {
            showDialog("失敗!", "今回の記録は${point}\n最高記録は${defaultBestValue}です")
        }

        point = 0
        val mediaPlayer = MediaPlayer.create(this, R.raw.incorrect1)
        mediaPlayer.start()
    }

    private fun showDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this).setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
