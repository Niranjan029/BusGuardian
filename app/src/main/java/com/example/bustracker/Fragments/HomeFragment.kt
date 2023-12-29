package com.example.bustracker.Fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.bustracker.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import java.lang.reflect.InvocationTargetException
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home),OnMapReadyCallback {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
   private lateinit var mapView:MapView
   private lateinit var Useraddress:TextView
   private lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.fragment_home, container, false)

        mapView = root.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        Useraddress = root.findViewById(R.id.currLocation)
        return root
    }
    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Check if location permission is granted
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Companion.REQUEST_LOCATION_PERMISSION
            )
            return
        }

        // Enable location layer on the map
        googleMap.isMyLocationEnabled = true

        // Get the user's last known location
        try {
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    // Move the camera to the user's current location
                    val cameraPosition = CameraPosition.Builder()
                        .target(LatLng(location.latitude, location.longitude))
                        .zoom(16f)
                        .build()
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                    val markerOptions = MarkerOptions()
                        .position(LatLng(location.latitude, location.longitude))
                        .title("My Location")

                    val cameraPosition1 = CameraPosition.Builder()
                        .target(LatLng(26.8495, 75.8266))
                        .zoom(16f)
                        .build()
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1))
                    val markerOptions1 = MarkerOptions()
                        .position(LatLng(26.8495, 75.8266))
                        .title("DRIVER Location")


// Add the marker to the map
                    googleMap.addMarker(markerOptions)
                    googleMap.addMarker(markerOptions1)

// Create a new polyline
                    val polylineOptions = PolylineOptions().apply {
                        color(R.color.appcolor)
                        width(10f)
                        geodesic(true)
                    }

// Add points to the polyline
                    val startPoint = LatLng(26.8495, 75.8266)
                    val endPoint = LatLng(location.latitude, location.longitude)
                    polylineOptions.add(startPoint, endPoint)

// Set the properties of the polyline


// Add the polyline to the map
                    val polyline = googleMap.addPolyline(polylineOptions)


//                    try{
//                        val geocoder = Geocoder(root.context, Locale.getDefault())
//                        val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//
//                        if (addressList!!.isNotEmpty()) {
//                            val address = addressList[0]
//                            val locationText = "${address.thoroughfare}, ${address.subThoroughfare}, ${address.locality}, ${address.postalCode}, ${address.countryName}"
//                            // Do something with the locationText, such as display it in a TextView
//                            Useraddress.text = locationText
//                        } else {
//                            // Handle case where no address was found
//                        }
//                    }
//                    catch (e: InvocationTargetException)
//                    {
//                        Toast.makeText(root.context,"please try after some time",Toast.LENGTH_SHORT).show();
//                    }

                }
            }
        }
        catch (e:InvocationTargetException)
        {
            Toast.makeText(root.context,"please try after some time",Toast.LENGTH_SHORT).show()
        }


    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }




}