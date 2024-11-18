package edu.ucam.myapp.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import edu.ucam.myapp.Helper.ManagmentCart
import edu.ucam.myapp.databinding.ActivityPaymentBinding
import java.io.File

class PaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentBinding
    lateinit var managment: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managment = ManagmentCart(this)


        processPayment()
    }

    private fun processPayment() {

        Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show()


        Thread {
            val pdfGenerated = generatePdf()
            runOnUiThread {
                if (pdfGenerated) {
                    Toast.makeText(this, "PDF generated successfully", Toast.LENGTH_SHORT).show()
                    managment.clearCart()
                } else {
                    Toast.makeText(this, "Failed to generate PDF", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(this@PaymentActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun generatePdf(): Boolean {
        return try {
            val pdfPath = getExternalFilesDir(null)?.absolutePath + "/Purchase_Ticket.pdf"
            Log.d("PaymentActivity", "PDF Path: $pdfPath")
            val writer = PdfWriter(pdfPath)
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc)

            document.add(Paragraph("Purchase List"))
            document.add(Paragraph(""))

            managment.getListCart().forEach { item ->
                document.add(Paragraph("${item.title} - ${item.price}"))
            }

            document.close()
            val fileExists = File(pdfPath).exists()
            Log.d("PaymentActivity", "PDF exists: $fileExists")
            fileExists
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("PaymentActivity", "Error generating PDF", e)
            false
        }
    }
}