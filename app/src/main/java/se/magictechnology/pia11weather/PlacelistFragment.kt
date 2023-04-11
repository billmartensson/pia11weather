package se.magictechnology.pia11weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import se.magictechnology.pia11weather.databinding.FragmentPlacelistBinding

class PlacelistFragment : Fragment() {

    private var _binding: FragmentPlacelistBinding? = null
    private val binding get() = _binding!!

    val viewModel : WeatherViewmodel by activityViewModels()

    var placesadapter = PlacelistAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentPlacelistBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val placesRecview = binding.placelistRV

        placesRecview.adapter = placesadapter
        placesRecview.layoutManager = LinearLayoutManager(requireContext())

        binding.addCityButton.setOnClickListener {
            var cityname = binding.addCityET.text.toString()

            binding.addCityET.setText("")

            // TODO: Kolla om stad är felaktig

            viewModel.addCity(requireContext(), cityname)

            placesadapter.notifyDataSetChanged()

        }
    }

    fun clickPlace() {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainFragCon, PlacedetailFragment()).commit()
    }


}