package edu.ucam.myapp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ucam.myapp.Adapter.CartAdapter
import edu.ucam.myapp.Helper.ChangeNumberItemsListener
import edu.ucam.myapp.Helper.ManagmentCart
import edu.ucam.myapp.R
import edu.ucam.myapp.databinding.ActivityCartBinding
import edu.ucam.myapp.databinding.ActivityDetailBinding

class CartActivity : BaseActivity() {
    lateinit var binding: ActivityCartBinding
    lateinit var managment: ManagmentCart
    private var tax : Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managment = ManagmentCart(this)

        calculateCart()
        setVariable()
        initCartList()
        setupCheckoutButton()

    }

    private fun setupCheckoutButton() {
        binding.checkoutBtn.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initCartList() {
        with(binding){
            cartView.layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
            cartView.adapter = CartAdapter(managment.getListCart(),
                this@CartActivity,
                object : ChangeNumberItemsListener{
                    override fun onChanged() {
                        calculateCart()
                    }
            })
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun calculateCart() {
        val percentTax = 0.2
        val delivery = 15.0
        tax = Math.round((managment.getTotalFee()*percentTax)*100) / 100.0
        val total = Math.round((managment.getTotalFee() + tax + delivery)*100) / 100.0
        val itemTotal = Math.round(managment.getTotalFee()*100) / 100.0

        with(binding){
            totalFeeTxt.text = "$$itemTotal"
            taxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }
}