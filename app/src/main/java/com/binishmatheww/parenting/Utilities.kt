package com.binishmatheww.parenting

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.binishmatheww.parenting.views.PieChartLegend
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement


const val MESSAGE = "message"

fun stringToJsonObject(text: String): JsonObject {
    //Log.wtf("stringToJsonObject",text)
    var element = JsonObject()

    try {
        element = Gson().fromJson(text, JsonObject::class.java)
    }catch (e : Exception){
        Log.wtf("stringToJsonObject","exception ${e.message}")
    }

    return element
}

fun String?.toMinutes() : String {

    this?.let {
        var int = this.toInt()

        var hours = int/60
        var minutes = int%60

        return when {
            hours > 0 && minutes > 0 -> {
                "${hours}h ${minutes}m"
            }
            hours > 0 && minutes == 0 -> {
                "${hours}h"
            }
            else -> {
                "${minutes}m"
            }
        }
    }

    return ""
}

fun AppCompatActivity?.replaceFragment(fragment: Fragment){

    this?.let {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fContainer,fragment,fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

}

fun FragmentActivity?.replaceFragment(fragment: Fragment){

    this?.let {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fContainer,fragment,fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }

}

fun FragmentActivity?.popBackStack(){

    this?.supportFragmentManager?.popBackStack()

}


fun addLegend(pieChartLegend: PieChartLegend,parent : ViewGroup){

    parent.addView(pieChartLegend)
    val layoutParams = pieChartLegend.layoutParams as LinearLayout.LayoutParams
    layoutParams.setMargins(50,0,0,0)
    layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
    layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
    layoutParams.weight = 1f
    pieChartLegend.layoutParams = layoutParams

}