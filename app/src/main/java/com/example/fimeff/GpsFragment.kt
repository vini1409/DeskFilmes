package com.example.fimeff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.launch

class GpsFragment : Fragment() {
    private val locationService: LocationService = LocationService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gps, container, false)
        val tvLocation = view.findViewById<TextView>(R.id.tvLocation)
        val local_button = view.findViewById<Button>(R.id.local_button)

        local_button.setOnClickListener {
            lifecycleScope.launch {
                val result = locationService.getUserLocation(requireContext())
                if (result != null) {
                    tvLocation.text = "Latitude: ${result.latitude}, Longitude: ${result.longitude}"
                }
            }
        }

        return view
    }
}
