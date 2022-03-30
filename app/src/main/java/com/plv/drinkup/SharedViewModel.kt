package com.plv.drinkup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val name = MutableLiveData<String>()
    val category = MutableLiveData<String>()
    val alcohol = MutableLiveData<String>()


}

