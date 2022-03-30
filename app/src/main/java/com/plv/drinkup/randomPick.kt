package com.plv.drinkup

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.beust.klaxon.Klaxon
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class randomPick : AppCompatActivity() {

    val viewModel: SharedViewModel by viewModels()
    private val client = OkHttpClient()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_pick)
        fetch("https://www.thecocktaildb.com/api/json/v1/1/random.php")
        val cocktail = findViewById<TextView>(R.id.randomCocktailName)

        viewModel.name.observe(this, Observer {
            cocktail?.text = it
        })

        viewModel.category.observe(this, Observer {
            findViewById<TextView>(R.id.randomCocktailCategory).text = it
        })

        viewModel.alcohol.observe(this, Observer {
            findViewById<TextView>(R.id.random_alcoholic_non_alcoholic).text = it
        })


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetch(sUrl: String): Drink? {
        var blogInfo: Drink? = null
        lifecycleScope.launch(Dispatchers.IO) {
            val result = testThis(sUrl)
            println(result)
            val gson = GsonBuilder().create()
            if (result != null) {
                try {
                    // Parse result string JSON to data class
                    blogInfo = gson.fromJson(result, DrinkData::class.java)
                    println(blogInfo)
                    withContext(Dispatchers.Main) {
                        viewModel.name.value = blogInfo?.strDrink
                        viewModel.category.value = blogInfo?.strCategory
                        viewModel.alcohol.value = blogInfo?.strAlcoholic
                    }
                } catch (err: Error) {
                    print("Error when parsing JSON: " + err.localizedMessage)
                }
            } else {
                print("Error: Get request returned no response")
            }
        }
        print(blogInfo)
        return blogInfo
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun testThis(url: String) {
        try {
            val request = Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            val result = response.body?.string();
            println(result)
        } catch (e: IOException) {
            e.printStackTrace();
        }


    }
}
