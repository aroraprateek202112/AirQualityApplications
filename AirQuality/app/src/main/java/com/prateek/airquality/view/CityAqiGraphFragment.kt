package com.prateek.airquality.view

import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.prateek.airquality.R
import com.prateek.airquality.viewModel.CitiesAQIViewModel
import com.prateek.airquality.viewModel.CitiesAQIViewModelFactory
import com.prateek.airquality.viewModel.CityAQILevelViewModel
import com.prateek.airquality.viewModel.CityAQILevelsViewModelFactory
import kotlinx.android.synthetic.main.fragment_city_aqi_graph.*
import timber.log.Timber
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_CITY = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CityAqiGraphFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityAqiGraphFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var city: String? = null

    private var mViewModel: CityAQILevelViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            city = it.getString(ARG_CITY)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val dataManager = (activity as MainActivity).getDataManager()
        if (dataManager != null) {
            mViewModel = ViewModelProvider(this, CityAQILevelsViewModelFactory(dataManager)).get(CityAQILevelViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city_aqi_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGraph()
    }

    override fun onResume() {
        super.onResume()
        if (mViewModel != null && city != null) {
            mViewModel!!.subscribeToAQIUpdates()
            getParticularCityAqiLevels(city!!)
        }
    }

    override fun onPause() {
        if (mViewModel != null) {
            mViewModel!!.hideAqiLevelForParticularCity()
            mViewModel!!.unSubscribeToAQIUpdates()
        }
        super.onPause()
    }

    private fun getParticularCityAqiLevels(city: String) {
        if (mViewModel == null) {
            Timber.d("getParticularCityAqiLevels city : $city return since viewModel null")
            return
        }
        mViewModel!!.showAqiLevelForParticularCity(city).observe(this,
            { t ->
                Timber.d("onChanged size : "+t.size)
                setData(t)
                // redraw
                chart1.invalidate()
            })
    }

    private fun initGraph() {
        // Background Color
        chart1.setBackgroundColor(Color.WHITE)

        // disable description text

        // disable description text
        chart1.description.isEnabled = false

        // enable touch gestures
        chart1.setTouchEnabled(false)
        chart1.setDrawGridBackground(false)

        // // X-Axis Style // //
        var xAxis: XAxis = chart1.getXAxis()

        // vertical grid lines
        xAxis.enableGridDashedLine(10f, 10f, 0f)

        // // Y-Axis Style // //
        var yAxis: YAxis = chart1.axisLeft

        // disable dual axis (only use LEFT axis)
        chart1.axisRight.isEnabled = false

        // horizontal grid lines

        // horizontal grid lines
        yAxis.enableGridDashedLine(10f, 10f, 0f)

        // axis range
        yAxis.axisMaximum = 500f
        yAxis.axisMinimum = 0f

//        setData(45, 180f)
    }

    private fun setData(count: Int, range: Float) {
        val values = ArrayList<Entry>()
        for (i in 0 until count) {
            val value = (Math.random() * range).toFloat() - 30
            values.add(Entry(i.toFloat(), value))
        }
        setData(values)
    }

    private fun setData(values: ArrayList<Entry>) {
        Timber.d("setData value : "+values)
        val set1: LineDataSet
        if (chart1.data != null &&
            chart1.data.dataSetCount > 0
        ) {
            Timber.d("setData if block")
            set1 = chart1.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart1.data.notifyDataChanged()
            chart1.notifyDataSetChanged()
        } else {
            Timber.d("setData else block")
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter =
                IFillFormatter { dataSet, dataProvider -> chart1.axisLeft.axisMinimum }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart1.data = data
        }
    }

    companion object {
        val TAG: String = CityAqiGraphFragment::class.java.canonicalName

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param city Parameter 1.
         * @return A new instance of fragment CityAqiGraphFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(city: String) =
            CityAqiGraphFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CITY, city)
                }
            }
    }
}