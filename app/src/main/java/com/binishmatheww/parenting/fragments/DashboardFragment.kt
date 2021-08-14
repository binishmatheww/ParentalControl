package com.binishmatheww.parenting.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.binishmatheww.parenting.ParentalControl
import com.binishmatheww.parenting.R
import com.binishmatheww.parenting.databinding.FragmentDashboardBinding
import com.binishmatheww.parenting.stringToJsonObject
import com.binishmatheww.parenting.toMinutes
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.gson.JsonObject
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray


class DashboardFragment : Fragment() {

    private lateinit var layout : FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        layout = FragmentDashboardBinding.inflate(inflater,container,false)

        return layout.root

    }


    override fun onResume() {
        super.onResume()

        render()

    }

    private fun render(){

        layout.dashboardLayout.alpha = 0f
        layout.freeTimeUsageLayout.alpha = 0f
        layout.devicesLayout.alpha = 0f

        context?.let {

            /*
            val pieChartLegend = PieChartLegend(context)
            addLegend(pieChartLegend,layout.dashboardLegend)
            pieChartLegend.setTitle("Class")
            pieChartLegend.setDescription("1h 40m")
             */

            getData { isSuccess, response ->


                if(isSuccess){

                    if(response.has("chartData") && response.has("deviceUsage") && response.has("freeTimeMaxUsage")){

                        layout.dashboardLayout.animate().alpha(1f).setDuration(1000).start()
                        layout.freeTimeUsageLayout.animate().alpha(1f).setDuration(1000).start()
                        layout.devicesLayout.animate().alpha(1f).setDuration(1000).start()

                        val chartData = response.getAsJsonObject("chartData")

                        val entries = ArrayList<PieEntry>()
                        entries.add(PieEntry(chartData.get("classTime").asJsonObject.get("total").asFloat))
                        entries.add(PieEntry(chartData.get("studyTime").asJsonObject.get("total").asFloat))
                        entries.add(PieEntry(chartData.get("freeTime").asJsonObject.get("total").asFloat))

                        if(chartData.has("totalTime")){
                            layout.dashboardChartDescription.text = chartData.get("totalTime").asJsonObject.get("total").asString.toMinutes()
                        }

                        if(chartData.has("classTime")){
                            layout.classDescription.text = chartData.get("classTime").asJsonObject.get("total").asString.toMinutes()
                        }

                        if(chartData.has("studyTime")){
                            layout.studyDescription.text = chartData.get("studyTime").asJsonObject.get("total").asString.toMinutes()
                        }

                        if(chartData.has("freeTime")){
                            layout.freeTimeDescription.text = chartData.get("freeTime").asJsonObject.get("total").asString.toMinutes()
                        }

                        setPieChart(entries)

                        val deviceUsage = response.get("deviceUsage").asJsonObject

                        val freeTimeMaxUsage = response.get("freeTimeMaxUsage").asInt

                        val freeTime = deviceUsage.get("freeTime").asJsonObject.get("mobile").asInt +
                                deviceUsage.get("freeTime").asJsonObject.get("laptop").asInt


                        layout.freeTimeUsageProgressBar.max = freeTimeMaxUsage

                        CoroutineScope(Main).launch {

                            for (i in 0 until freeTime){
                                layout.freeTimeUsageProgressBar.progress = i
                                delay(20)
                            }

                        }

                        layout.maxTime.text = freeTimeMaxUsage.toString().toMinutes()
                        layout.usedTime.text = freeTime.toString().toMinutes()

                        layout.adisPhoneUsage.text = deviceUsage.get("totalTime").asJsonObject.get("mobile").asString.toMinutes()
                        layout.adisLaptopUsage.text = deviceUsage.get("totalTime").asJsonObject.get("laptop").asString.toMinutes()

                    }

                }

            }

        }

    }

    private fun getData(onCompleted : (Boolean,JsonObject) -> Unit){

        CoroutineScope(IO).launch {

            var response: JsonObject
            try {
                val responseString : String = ParentalControl.instance().getHttpClient().get {
                    url("https://api.mocklets.com/mock68182/screentime")
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    contentType(ContentType.Application.Json)
                }
                response = stringToJsonObject(JSONArray(responseString).getJSONObject(0).toString())

                Log.wtf("Response", response.toString())
                withContext(Main){
                    onCompleted(true,response)
                }
            } catch (e: ClientRequestException) {
                Log.wtf("ClientRequest Exception", e.localizedMessage)
                response = JsonObject()
                withContext(Main){
                    onCompleted(false,response)
                }
            } catch (e: Exception) {
                Log.wtf("Exception", e.message)
                e.printStackTrace()
                response = JsonObject()
                withContext(Main){
                    onCompleted(false,response)
                }
            }

        }

    }


    private fun setPieChart(entries: ArrayList<PieEntry>) {

        layout.dashboardChart.setUsePercentValues(true)
        layout.dashboardChart.description.isEnabled = false
        layout.dashboardChart.setExtraOffsets(5f, 10f, 5f, 5f)

        layout.dashboardChart.dragDecelerationFrictionCoef = 0.95f

        //layout.dashboardChart.setCenterTextTypeface()
        //layout.dashboardChart.setCenterText()

        layout.dashboardChart.isDrawHoleEnabled = true
        layout.dashboardChart.setHoleColor(Color.WHITE)

        layout.dashboardChart.setTransparentCircleColor(Color.WHITE)
        layout.dashboardChart.setTransparentCircleAlpha(110)

        layout.dashboardChart.holeRadius = 90f
        layout.dashboardChart.transparentCircleRadius = 0f

        layout.dashboardChart.setDrawCenterText(true)

        layout.dashboardChart.rotationAngle = 0f

        layout.dashboardChart.isRotationEnabled = false
        layout.dashboardChart.isHighlightPerTapEnabled = true


        layout.dashboardChart.animateY(1400, Easing.EaseInOutQuad)
        layout.dashboardChart.spin(2000, 0f, 120f,Easing.EaseInOutQuad)

        layout.dashboardChart.setEntryLabelColor(Color.BLACK)
        //layout.dashboardChart.setEntryLabelTypeface()
        layout.dashboardChart.setEntryLabelTextSize(10f)

        layout.dashboardChart.setDrawMarkers(false)
        layout.dashboardChart.setDrawEntryLabels(false)
        layout.dashboardChart.description.isEnabled = false

        layout.dashboardChart.legend.isEnabled = false
        val legend = layout.dashboardChart.legend
        legend.form = Legend.LegendForm.CIRCLE
        legend.textSize = 10f
        legend.formSize = 10f
        legend.formToTextSpace = 2f
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
        legend.xEntrySpace = 7f
        legend.yEntrySpace = 0f
        legend.yOffset = 0f


        val dataSet = PieDataSet(entries,"Screen on time")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 0.5f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f


        dataSet.setColors(
            ResourcesCompat.getColor(resources, R.color.class_chart_color,null),
            ResourcesCompat.getColor(resources, R.color.study_chart_color,null),
            ResourcesCompat.getColor(resources, R.color.free_time_chart_color,null)
        )

        val data = PieData(dataSet)
        data.setDrawValues(false)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        //data.setValueTypeface()
        layout.dashboardChart.data = data

        layout.dashboardChart.highlightValues(null)
        layout.dashboardChart.invalidate()
    }

}