package edu.ucam.myapp.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ucam.myapp.Adapter.CategoryAdapter
import edu.ucam.myapp.Adapter.OfferAdapter
import edu.ucam.myapp.Adapter.PopularAdpater
import edu.ucam.myapp.ViewModel.MainViewModel
import edu.ucam.myapp.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        initCategory()
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

//    private fun initCategory() {
//        binding.recyclerViewCategory.layoutManager = LinearLayoutManager(
//            this@HomeActivity,
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
//        binding.progressBarCategory.visibility = View.VISIBLE
//
//        viewModel.category.observe(this, Observer { categories ->
//            if (categories.isNotEmpty()) {
//                binding.recyclerViewCategory.adapter = CategoryAdapter(categories)
//            } else {
//                // Opcional: muestra un mensaje o maneja el caso de lista vacía
//                Log.d("HomeActivity", "La lista de categorías está vacía")
//            }
//            binding.progressBarCategory.visibility = View.GONE
//        })
//
//        viewModel.loadCategory()
//    }

}
