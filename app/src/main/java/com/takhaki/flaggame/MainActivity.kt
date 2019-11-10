package com.takhaki.flaggame

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val orderContents = listOf("赤上げて", "白上げて", "赤下げて", "白下げて")
    var isRedFlagUp: Boolean = false
    var isWhiteFlagUp: Boolean = false
    var randomNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameSet()

        whiteFragButton.setOnClickListener {
            when(randomNumber) {
                0, 2 -> incorrect()
                1 -> if (isWhiteFlagUp) incorrect() else correct()
                3 -> if (isWhiteFlagUp) correct() else incorrect()
            }

            gameSet()
        }

        redFragButton.setOnClickListener {
            when(randomNumber) {
                1, 3 -> incorrect()
                0 -> if (isRedFlagUp) incorrect() else correct()
                2 -> if (isRedFlagUp) correct() else incorrect()
            }

            gameSet()
        }

        nothingButton.setOnClickListener {

        }
    }

    private fun gameSet() {
        randomNumber = (0..3).shuffled().first()
        flagOrderTextView.text = orderContents[randomNumber]
    }

    private fun correct() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.correct2)
        mediaPlayer.start()
    }

    private fun incorrect() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.incorrect1)
        mediaPlayer.start()
    }
}
