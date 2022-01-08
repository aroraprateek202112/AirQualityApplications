package com.prateek.airquality.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prateek.airquality.R
import com.prateek.airquality.model.CityAQIModel
import com.prateek.airquality.model.interfaces.IBaseModel
import com.prateek.airquality.model.interfaces.IDataManager

class MainActivity : AppCompatActivity() {

//    private lateinit var mViewModel: AQIViewModel
//    private var mAQIAdapter: AQIAdapter? = null
    private var dataManager: IDataManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, CitiesAqiFragment.newInstance(), CitiesAqiFragment.TAG)
            .commit()
//        mViewModel = ViewModelProvider(
//            this,
//            AQIViewModelFactory(DataManager())
//        ).get(AQIViewModel::class.java)
//
////        startWebSocket()
////        refreshUI(DUMMY_LIST)
//        getCityAqis()
    }

    fun getDataManager() : IDataManager? = dataManager

    fun setDataManager(dataManager: IDataManager) {
        this.dataManager = dataManager
    }

//    override fun onResume() {
//        super.onResume()
//        mViewModel.subscribeToAQIUpdates()
//    }
//
//    override fun onPause() {
//        mViewModel.unSubscribeToAQIUpdates()
//        super.onPause()
//    }
//
//    private fun getCityAqis() {
//        mViewModel.getCityAqis().observe(this, object : Observer<MutableList<IBaseModel>> {
//            override fun onChanged(t: MutableList<IBaseModel>) {
//                Timber.d("Prateek onChanged size : "+t.size)
//                Timber.d("Prateek onChanged list : $t")
//                Timber.d("Prateek onChanged size : ${Thread.currentThread()}")
//                refreshUI(t)
//            }
//        })
//    }

//    private fun refreshUI(cityAqis: MutableList<IBaseModel>) {
//        // Header Row
//        cityAqis.add(
//            0,
//            HeaderModel(
//                getString(R.string.city),
//                getString(R.string.current_aqi),
//                getString(R.string.last_updated)
//            ),
//        )
//        if (mAQIAdapter == null) {
//            mAQIAdapter = AQIAdapter(cityAqis, this)
//            val layoutManager = LinearLayoutManager(this)
//            rv_city_aqis.layoutManager = LinearLayoutManager(this)
//            rv_city_aqis.adapter = mAQIAdapter
//            rv_city_aqis.addItemDecoration(
//                DividerItemDecoration(this, layoutManager.orientation)
//                    .apply { setDrawable(getDrawable(R.drawable.item_divider)!!) }
//            )
//        } else {
//            mAQIAdapter!!.update(cityAqis)
//        }
//    }

//    val DUMMY_LIST = mutableListOf<IBaseModel>(
//        CityAQIModel("Mumbai", 179.6798636328982, 1641595388000),
//        CityAQIModel("Bhubaneswar", 101.6244395671856, 1641595388000),
//        CityAQIModel("Chennai", 141.28907870138144, 1641595388000),
//        CityAQIModel("Pune", 223.77033558700106, 1641595388000),
//        CityAQIModel("Hyderabad", 199.58587376533836, 1641595388000),
//        CityAQIModel("Jaipur", 142.11425979267057, 1641595388000),
//        CityAQIModel("Chandigarh", 47.760571351010476, 1641595388000)
//    )

//    private fun startWebSocket() {
//        Timber.d("startWebSocket")
//        val request: Request = Request.Builder().url(Constants.URL).build()
//        val okHttpClient: OkHttpClient = OkHttpClient();
//
//        val webSocketListener = AQIWebSocketListener()
//        val webSocket = okHttpClient.newWebSocket(request, webSocketListener)
//        okHttpClient.dispatcher.executorService.shutdown()
//    }

//    override fun onClick(city: String) {
//        Timber.d("onClick city : $city")
//    }
}