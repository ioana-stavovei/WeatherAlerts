package com.test.weatheralerts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.weatheralerts.databinding.ActivityMainBinding
import com.test.weatheralerts.ui.AlertsAdapter
import com.test.weatheralerts.viewmodels.WeatherAlertViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherAlertViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var alertsAdapter: AlertsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alertsAdapter = AlertsAdapter()
        viewModel.getAlerts()
        observeData()
        binding.alertsRV.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = alertsAdapter
        }
    }

    private fun observeData() {
        viewModel.alertsList.observe(this)
        {
            if (it.isNotEmpty())
                alertsAdapter.submitList(it)
        }
        viewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
        }
        viewModel.hasError.observe(this) {
            if (it)
                Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show()
        }
    }
}