package com.example.vertagelabtestapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vertagelabtestapp.MainActivity
import com.example.vertagelabtestapp.data.models.Place
import com.example.vertagelabtestapp.databinding.FragmentMapBinding
import com.example.vertagelabtestapp.ui.adapters.PlacesAdapter
import com.example.vertagelabtestapp.ui.viewmodels.MapViewModel
import com.example.vertagelabtestapp.util.Resource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var placesAdapter: PlacesAdapter
    private val args: MapFragmentArgs by navArgs()
    private var map: GoogleMap? = null

    private val TAG = "MapFragment"

    private val viewModel: MapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)

        setupAdapters()
        setupMap()
        setupToolbar()

        viewModel.places.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        placesAdapter.items = it.places
                        setupMarkers(it.places)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Log.d(TAG, "An error occured: $it")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun setupToolbar() {
        val toolbar = (requireActivity() as MainActivity).toolbar
        toolbar.title = args.email
    }

    private fun setupMarkers(list: List<Place>) {
        list.forEach {
            map?.addMarker(MarkerOptions().position(LatLng(it.lat, it.lng)))
        }
        map?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(list[0].lat, list[0].lng)))
    }

    private fun setupMap() {
        binding.mapView.getMapAsync {
            it.animateCamera(CameraUpdateFactory.zoomTo(14.0f))
            map = it
        }
    }

    private fun setupAdapters() {
        placesAdapter = PlacesAdapter()
        binding.rvPlaces.apply {
            adapter = placesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}