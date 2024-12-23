package com.example.budgetbuddy.analyzers

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ReceiptAnalyzer(
    private val onExtractedData: (totalAmount: Pair<Double, String>?, date: String?, place: String?) -> Unit
) : ImageAnalysis.Analyzer {

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val text = visionText.text
                val totalAmount = extractTotalAmount(text)
                val date = extractDate(text)
                val place = extractPlace(text)

                onExtractedData(totalAmount, date, place)

                imageProxy.close()
            }
            .addOnFailureListener { e ->
                Log.e("ReceiptAnalyzer", "Text recognition failed", e)
                imageProxy.close()
            }
    }

    private val currencyRegex = Regex("""(\d+[.,]?\d*)\s*([A-Z]{3}|[a-zA-Z]*[Kk][čČ])""")

    private val currencySymbols = setOf(
        "USD", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD",
        "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP", "CNY", "COP", "CRC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR",
        "FJD", "FKP", "FOK", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD",
        "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KID", "KMF", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD",
        "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN",
        "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SLL", "SOS", "SRD", "SSP",
        "STN", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TVD", "TWD", "TZS", "UAH", "UGX", "UYU", "UZS", "VES", "VND", "VUV", "WST", "XAF",
        "XCD", "XDR", "XOF", "XPF", "YER", "ZAR", "ZMW", "ZWL"
    )

    private fun extractTotalAmount(text: String): Pair<Double, String>? {
        val matchResult = currencyRegex.find(text)

        return if (matchResult != null) {

            val (priceString, currency) = matchResult.destructured
            val price = priceString.replace(",", ".").toDoubleOrNull()

            if (price != null && currency.uppercase() in currencySymbols) {
                Pair(price, currency.trim())
            } else {
                null
            }
        } else {
            null
        }
    }

    private fun extractDate(text: String): String? {
        val regex = Regex("""\b(\d{2}[./-]\d{2}[./-]\d{4})\b""")
        return regex.find(text)?.value
    }

    private fun extractPlace(text: String): String? {
        val regex = Regex("^[A-Z\\s]+$")
        return text.lines().firstOrNull { regex.matches(it.trim()) }?.trim()
    }
}

