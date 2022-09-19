package com.JJ.weathercodechallenge.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.JJ.weathercodechallenge.R
import com.JJ.weathercodechallenge.databinding.FragmentEntryCityBinding
import com.JJ.weathercodechallenge.viewmodel.WeatherViewModel

const val ARG_CITY = "CITY"

class EntryCityFragment : Fragment() {

    private lateinit var binding: FragmentEntryCityBinding

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntryCityBinding.inflate(layoutInflater)

        binding.lookupBtn.setOnClickListener {
            if (binding.etCityName.text.isNullOrEmpty()) {
                showError(true)
            } else {
                navigateToForecastFragment()
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.etCityName.requestFocus()
        showError(viewModel.isError)
    }

    private fun showError(error: Boolean) {
        if (error) {
            Snackbar.make(
                requireView(),
                resources.getString(R.string.error_message),
                Snackbar.LENGTH_LONG)
                .show()

            viewModel.isError = false
        }
    }

    private fun navigateToForecastFragment() {
        val city = binding.etCityName.text.toString()
        findNavController().navigate(R.id.navigate_from_entryCity_to_forecast, bundleOf(
            Pair(ARG_CITY, city)))
    }

    companion object {
        fun newInstance() = EntryCityFragment()
    }
}