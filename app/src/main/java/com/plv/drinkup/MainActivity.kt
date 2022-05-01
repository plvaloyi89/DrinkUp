package com.plv.drinkup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.plv.drinkup.view.randomPick


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val options = listOf<String>("Random Choice","Browse Drinks","Non-Alcoholic","Diet Friendly")

        findViewById<Button>(R.id.random_choice).apply {
            text = "Random Choice"
        }.setOnClickListener {
            val randomChoice = Intent(this, randomPick::class.java)
            startActivity(randomChoice)
        }

        findViewById<Button>(R.id.browse_drinks).apply {
            text = "Browse Drinks"
        }.setOnClickListener {

        }

        findViewById<Button>(R.id.non_alcoholic).apply {
            text = "Non-Alcoholic"
        }.setOnClickListener {

        }

        findViewById<Button>(R.id.diet_friendly).apply {
            text = "Diet Friendly"
        }.setOnClickListener {

        }

    }

}


