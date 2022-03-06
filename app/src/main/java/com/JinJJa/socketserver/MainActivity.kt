package com.JinJJa.socketserver

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import com.JinJJa.socketserver.databinding.ActivityMainBinding
import com.JinJJa.socketserver.dataprocess.VisualProcessingManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var serverServiceIntent: Intent? = null
    var drawChart = false
    var chart: LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        chart = binding.ppgChart

        chart!!.setDrawGridBackground(true)
        chart!!.setBackgroundColor(Color.BLACK)
        chart!!.setGridBackgroundColor(Color.BLACK)

        //description text
        var des: Description = chart!!.description
        des.isEnabled=true
        des.text = "Real-Time PPG"
        des.textSize = 15f
        des.textColor = Color.WHITE

        //auto scale
        chart!!.isAutoScaleMinMaxEnabled = true

        //x축
        chart!!.xAxis.setDrawGridLines(true)
        chart!!.xAxis.setDrawAxisLine(false)

        //y축
        chart!!.axisLeft.isEnabled = true
        chart!!.axisLeft.textColor = Color.WHITE
        chart!!.axisLeft.setDrawGridLines(true)
        chart!!.axisLeft.gridColor = Color.WHITE

        chart!!.invalidate()


        binding.button2.setOnClickListener {
            //serverServiceIntent= Intent(this, ServerService::class.java)
            //startService(serverServiceIntent)
            drawChart = true
            WaitForTwoSecThread().start()


        }


        binding.button.setOnClickListener {
            //stopService(serverServiceIntent)
            DrawChartThread().interrupt()
            drawChart = false
            Log.d("test1", "차트 그리기 정지")
        }

    }

    //two seconds to get first ppg data (suppose)
    inner class WaitForTwoSecThread : Thread() {
        override fun run() {
            SystemClock.sleep(2000L)
            DrawChartThread().start()
        }
    }


    fun createSet(): LineDataSet{
        var dataSet: LineDataSet? = null
        dataSet = LineDataSet(null,"Real-time PPG Data")
        dataSet.lineWidth=1f
        dataSet.color = Color.GREEN
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 10f
        dataSet.setDrawCircles(false)

        return dataSet
    }

    fun addEntry(number: Float){
        var data: LineData?= null
        data = chart!!.data
        if(data == null) {
            Log.d("test1", "들어옴")
            data = LineData()
            chart!!.data = data
        }

        var dataSet: ILineDataSet? = null
        dataSet = data.getDataSetByIndex(0)


        if(dataSet == null){
            dataSet = createSet()
            data.addDataSet(dataSet)
        }

        data.addEntry(Entry(dataSet.entryCount.toFloat(),
            number
        ), 0
        )
        data.notifyDataChanged()
        chart!!.notifyDataSetChanged()

        chart!!.setVisibleXRangeMaximum(10f)
        chart!!.moveViewTo(data.entryCount.toFloat(), 50f, YAxis.AxisDependency.LEFT)



    }

    inner class DrawChartThread: Thread(){
        override fun run() {
            while(drawChart){
                var number = Math.random().toFloat()*40 + 30
                runOnUiThread {
                    addEntry(number)
                }
                Thread.sleep(250)
                Log.d("test1", "포인트")
            }
        }
    }

}