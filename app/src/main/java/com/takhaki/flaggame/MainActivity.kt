package com.takhaki.flaggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val orderContents = listOf("赤上げて", "白上げて", "赤下げて", "白下げて")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        whiteFragButton.setOnClickListener {

        }

        redFragButton.setOnClickListener {

        }

        nothingButton.setOnClickListener {

        }
    }

    private fun randomNum(): Int {
        val random = Random(3).nextInt()
        flagOrderTextView.text = orderContents[random]

        return random
    }
}
