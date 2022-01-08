package com.prateek.airquality.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.prateek.airquality.R
import com.prateek.airquality.model.DataManager
import com.prateek.airquality.model.HeaderModel
import com.prateek.airquality.model.interfaces.IBaseModel
import com.prateek.airquality.model.interfaces.IDataManager
import com.prateek.airquality.viewModel.CitiesAQIViewModel
import com.prateek.airquality.viewModel.CitiesAQIViewModelFactory
import kotlinx.android.synthetic.main.fragment_city_aqis.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [CitiesAqiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CitiesAqiFragment : Fragment(), CitiesAQIHolder.IItemClickListener {

    private lateinit var mViewModel: CitiesAQIViewModel
    private var mAQIAdapter: CitiesAQIAdapter? = null
    private lateinit var dataManager: IDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(
            this,
            CitiesAQIViewModelFactory(dataManager)
        ).get(CitiesAQIViewModel::class.java)

        getCityAqis()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataManager = DataManager()
        (activity as MainActivity).setDataManager(dataManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city_aqis, container, false)
    }

    private fun getCityAqis() {
        mViewModel.getCityAqis().observe(this,
            { t ->
                Timber.d("onChanged size : ${t.size}")
                refreshUI(t)
            })
    }

    private fun refreshUI(cityAqis: MutableList<IBaseModel>) {
        Timber.d("refreshUI cityAqis : $cityAqis")
        // Adding Header Row
        cityAqis.add(
            0,
            HeaderModel(
                getString(R.string.city),
                getString(R.string.current_aqi),
                getString(R.string.last_updated)
            ),
        )
        if (mAQIAdapter == null) {
            mAQIAdapter = CitiesAQIAdapter(cityAqis, this)
            val layoutManager = LinearLayoutManager(requireContext())
            rv_city_aqis.layoutManager = layoutManager
            rv_city_aqis.adapter = mAQIAdapter
            rv_city_aqis.addItemDecoration(
                DividerItemDecoration(requireContext(), layoutManager.orientation)
                    .apply { setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.item_divider)!!) }
            )
        } else {
            mAQIAdapter!!.update(cityAqis)
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.subscribeToAQIUpdates()
    }

    override fun onPause() {
        mViewModel.unSubscribeToAQIUpdates()
        super.onPause()
        mAQIAdapter = null
    }

    override fun onClick(city: String) {
        Timber.d("onClick city : $city")
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CityAqiGraphFragment.newInstance(city), CityAqiGraphFragment.TAG)
            .addToBackStack(CityAqiGraphFragment.TAG)
            .commit()
    }

    companion object {

        val TAG: String = CitiesAqiFragment::class.java.canonicalName
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CityAqisFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = CitiesAqiFragment()
    }
}