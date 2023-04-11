package se.magictechnology.pia11weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import se.magictechnology.pia11weather.databinding.FragmentDetailBinding
import se.magictechnology.pia11weather.databinding.FragmentPlacelistBinding

class PlacedetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    val viewModel : WeatherViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherObserver = Observer<WeatherData> { currentWeather ->
            binding.cityNameTV.text = currentWeather.cityName
            binding.tempTV.text = currentWeather.temperature
            binding.windTV.text = currentWeather.wind
            binding.descriptionTV.text = currentWeather.description


        }

        viewModel.currentCityWeather.observe(viewLifecycleOwner, weatherObserver)

        viewModel.loadWeather()
    }

}