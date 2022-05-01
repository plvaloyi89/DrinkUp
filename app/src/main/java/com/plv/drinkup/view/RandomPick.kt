package com.plv.drinkup.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import com.plv.drinkup.R
import com.plv.drinkup.data.DrinkData
import com.plv.drinkup.viewModel.SharedViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class randomPick : AppCompatActivity() {

    val viewModel: SharedViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_pick)
        supportActionBar?.hide()
       viewModel.randomDrink("random")
        val cocktail = findViewById<TextView>(R.id.randomCocktailName)
        val imageHolder = findViewById<ImageView>(R.id.randomCocktailImage)

        viewModel.image.observe(this) {
            val image = it
            Picasso.get().load(image).fit().into(imageHolder)
        }


        viewModel.name.observe(this, Observer {
            cocktail?.text = it
        })

        viewModel.category.observe(this, Observer {
            findViewById<TextView>(R.id.randomCocktailCategory).text = it
        })

        viewModel.alcohol.observe(this, Observer {
            findViewById<TextView>(R.id.random_alcoholic_non_alcoholic).text = it
        })


        viewModel.instructions.observe(this, Observer {
            findViewById<TextView>(R.id.random_instructions).text = it
        })


    }
}

