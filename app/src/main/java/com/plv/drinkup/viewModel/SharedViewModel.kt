package com.plv.drinkup.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.plv.drinkup.data.DrinkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class SharedViewModel : ViewModel() {

    val name = MutableLiveData<String>()
    val category = MutableLiveData<String>()
    val alcohol = MutableLiveData<String>()
    val instructions = MutableLiveData<String>()
    val ingredients =  MutableLiveData<String>()
    val image = MutableLiveData<String>()


    @RequiresApi(Build.VERSION_CODES.O)
    fun randomDrink(drinkType:String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = process(drinkType)
            val gson = GsonBuilder().create()
            if (result != null) {
                try {
                    // Parse result string JSON to data class
                    val  blogInfo = gson.fromJson(result, DrinkData::class.java)
                    withContext(Dispatchers.Main) {
                        name.value = blogInfo.drinks[0].strDrink
                        category.value = blogInfo.drinks[0].strCategory
                        alcohol.value = blogInfo.drinks[0].strAlcoholic
                        instructions.value = blogInfo.drinks[0].strInstructions
                        image.value = blogInfo.drinks[0].strDrinkThumb
                    }
                } catch (err: Error) {
                    print("Error when parsing JSON: " + err.localizedMessage)
                }
            } else {
                print("Error: Get request returned no response")
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun process(category:String): String? {
        var result: String? = null

        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://the-cocktail-db.p.rapidapi.com/${category}.php")
                .get()
                .addHeader("X-RapidAPI-Host", "the-cocktail-db.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "f7893c3365msh9f7c977902a6dd6p16501fjsnb987c6345ee5")
                .build()
            val response = client.newCall(request).execute()
            result = response.body?.string();
            //println(result)
        } catch (e: IOException) {
            e.printStackTrace();
        }
        return result
    }

}

