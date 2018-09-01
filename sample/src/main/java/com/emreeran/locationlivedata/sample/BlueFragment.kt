package com.emreeran.locationlivedata.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.emreeran.locationlivedata.R
import com.emreeran.locationlivedata.databinding.BlueFragmentBinding
import com.emreeran.locationlivedata.sample.util.Permissions
import com.emreeran.locationlivedata.sample.util.autoCleared

/**
 * Created by Emre Eran on 31.08.2018.
 */
class BlueFragment : Fragment() {

    var binding by autoCleared<BlueFragmentBinding>()

    lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.blue_fragment, container, false)
        binding.handler = BlueHandler()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        Permissions.requestLocationPermissionsIfNecessary(this, object : Permissions.HasPermissionsCallback {
            override fun hasPermissions() {
                observeLocation()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Permissions.REQUEST_CODE_PERMISSIONS) {
            observeLocation()
        }
    }

    private fun observeLocation() {
        locationViewModel.getLocation().observe(this, Observer {
            binding.location = it
        })
    }
}