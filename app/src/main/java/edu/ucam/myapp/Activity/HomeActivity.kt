package edu.ucam.myapp.Activity

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ucam.myapp.Adapter.OfferAdapter
import edu.ucam.myapp.Adapter.PopularAdpater
import edu.ucam.myapp.ViewModel.MainViewModel
import edu.ucam.myapp.databinding.ActivityHomeBinding
import android.hardware.SensorManager
import android.content.Context
import edu.ucam.myapp.R

class HomeActivity : BaseActivity(), SensorEventListener {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel = MainViewModel()
    private lateinit var sensorManager: SensorManager
    private var temperatureSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        if (temperatureSensor == null) {
            binding.temperatureText.text = "Sensor no disponible"
        }

        initPopular()
        initOffer()

    }

    private fun initOffer() {
        binding.progressBarOffers.visibility = View.VISIBLE
        viewModel.offer.observe(this, Observer {
            binding.recyclerViewOffers.layoutManager = LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.recyclerViewOffers.adapter = OfferAdapter(it)
            binding.progressBarOffers.visibility = View.GONE
        })

        viewModel.loadOffer()
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.popular.observe(this, Observer {
            binding.recyclerViewPopular.layoutManager = LinearLayoutManager(
                this@HomeActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.recyclerViewPopular.adapter = PopularAdpater(it)
            binding.progressBarPopular.visibility = View.GONE
        })

        viewModel.loadPopular()
    }

    override fun onResume() {
        super.onResume()
        temperatureSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
//            val temperature = event.values[0]
            val temperature = 20.0
            Log.d("HomeActivity", "Valor del sensor de temperatura: $temperature")

            if (temperature in -50.0..50.0) {
                binding.temperatureText.text = "Temperatura: ${temperature}°C"

                when {
                    temperature < 15 -> {
                        binding.coffeeIcon.setImageResource(R.drawable.coffee)
                    }
                    temperature in 15.0..25.0 -> {
                        binding.coffeeIcon.setImageResource(R.drawable.coffee)
                    }
                    temperature > 25 -> {
                        binding.coffeeIcon.setImageResource(R.drawable.coffee)
                    }
                }
            } else {
                binding.temperatureText.text = "Temperatura no disponible"
                binding.coffeeIcon.setImageResource(R.drawable.coffee)
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
