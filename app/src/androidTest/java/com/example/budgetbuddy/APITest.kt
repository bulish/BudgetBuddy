package com.example.budgetbuddy

import android.util.Log
import com.example.budgetbuddy.fake.MockCurrencyAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import org.junit.Assert.assertEquals

class MockAPITest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: MockCurrencyAPI

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://v6.exchangerate-api.com/v6/86278e047e3e495c7e8e0949/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MockCurrencyAPI::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test1_apiReturnsCorrectCurrency() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """
                {
                 "result":"success",
                 "documentation":"https://www.exchangerate-api.com/docs",
                 "terms_of_use":"https://www.exchangerate-api.com/terms",
                 "time_last_update_unix":1734912002,
                 "time_last_update_utc":"Mon, 23 Dec 2024 00:00:02 +0000",
                 "time_next_update_unix":1734998402,
                 "time_next_update_utc":"Tue, 24 Dec 2024 00:00:02 +0000",
                 "base_code":"CZK",
                 "conversion_rates":{
                      "CZK":1,
                      "AED":0.1525,
                      "AFN":2.9112,
                      "ALL":3.9190,
                      "AMD":16.3871,
                      "ANG":0.07433,
                      "AOA":38.4875,
                      "ARS":42.5779,
                      "AUD":0.06638,
                      "AWG":0.07433,
                      "AZN":0.07041,
                      "BAM":0.07793,
                      "BBD":0.08304,
                      "BDT":4.9601,
                      "BGN":0.07793,
                      "BHD":0.01561,
                      "BIF":122.9038,
                      "BMD":0.04152,
                      "BND":0.05631,
                      "BOB":0.2865,
                      "BRL":0.2523,
                      "BSD":0.04152,
                      "BTN":3.5193,
                      "BWP":0.5704,
                      "BYN":0.1363,
                      "BZD":0.08304,
                      "CAD":0.05967,
                      "CDF":118.3519,
                      "CHF":0.03710,
                      "CLP":41.0351,
                      "CNY":0.3028,
                      "COP":182.5639,
                      "CRC":20.9924,
                      "CUP":0.9965,
                      "CVE":4.3936,
                      "DJF":7.3794,
                      "DKK":0.2970,
                      "DOP":2.5137,
                      "DZD":5.5827,
                      "EGP":2.1089,
                      "ERN":0.6228,
                      "ETB":5.2840,
                      "EUR":0.03983,
                      "FJD":0.09607,
                      "FKP":0.03306,
                      "FOK":0.2970,
                      "GBP":0.03306,
                      "GEL":0.1163,
                      "GGP":0.03306,
                      "GHS":0.6149,
                      "GIP":0.03306,
                      "GMD":3.0084,
                      "GNF":355.5379,
                      "GTQ":0.3191,
                      "GYD":8.6834,
                      "HKD":0.3223,
                      "HNL":1.0515,
                      "HRK":0.3002,
                      "HTG":5.4299,
                      "HUF":16.4900,
                      "IDR":672.0626,
                      "ILS":0.1516,
                      "IMP":0.03306,
                      "INR":3.5193,
                      "IQD":54.3915,
                      "IRR":1802.1022,
                      "ISK":5.7688,
                      "JEP":0.03306,
                      "JMD":6.4709,
                      "JOD":0.02944,
                      "JPY":6.5003,
                      "KES":5.3533,
                      "KGS":3.6060,
                      "KHR":166.0000,
                      "KID":0.06635,
                      "KMF":19.6027,
                      "KRW":59.8993,
                      "KWD":0.01276,
                      "KYD":0.03460,
                      "KZT":21.8097,
                      "LAK":908.9215,
                      "LBP":3716.2547,
                      "LKR":12.1298,
                      "LRD":7.5108,
                      "LSL":0.7602,
                      "LYD":0.2040,
                      "MAD":0.4166,
                      "MDL":0.7591,
                      "MGA":196.6462,
                      "MKD":2.4536,
                      "MMK":86.8622,
                      "MNT":142.7689,
                      "MOP":0.3319,
                      "MRU":1.6589,
                      "MUR":1.9550,
                      "MVR":0.6400,
                      "MWK":71.9280,
                      "MXN":0.8346,
                      "MYR":0.1866,
                      "MZN":2.6513,
                      "NAD":0.7602,
                      "NGN":64.0499,
                      "NIO":1.5239,
                      "NOK":0.4713,
                      "NPR":5.6309,
                      "NZD":0.07345,
                      "OMR":0.01597,
                      "PAB":0.04152,
                      "PEN":0.1549,
                      "PGK":0.1673,
                      "PHP":2.4351,
                      "PKR":11.5542,
                      "PLN":0.1697,
                      "PYG":321.2532,
                      "QAR":0.1511,
                      "RON":0.1981,
                      "RSD":4.6576,
                      "RUB":4.2686,
                      "RWF":57.3819,
                      "SAR":0.1557,
                      "SBD":0.3488,
                      "SCR":0.6047,
                      "SDG":18.5515,
                      "SEK":0.4579,
                      "SGD":0.05631,
                      "SHP":0.03306,
                      "SLE":0.9500,
                      "SLL":950.0577,
                      "SOS":23.7143,
                      "SRD":1.4675,
                      "SSP":161.6648,
                      "STN":0.9762,
                      "SYP":534.8553,
                      "SZL":0.7602,
                      "THB":1.4208,
                      "TJS":0.4514,
                      "TMT":0.1447,
                      "TND":0.1320,
                      "TOP":0.09740,
                      "TRY":1.4620,
                      "TTD":0.2804,
                      "TVD":0.06635,
                      "TWD":1.3533,
                      "TZS":99.8320,
                      "UAH":1.7291,
                      "UGX":152.2329,
                      "USD":0.04152,
                      "UYU":1.8537,
                      "UZS":531.8703,
                      "VES":2.1399,
                      "VND":1057.2769,
                      "VUV":4.8555,
                      "WST":0.1145,
                      "XAF":26.1369,
                      "XCD":0.1121,
                      "XDR":0.03169,
                      "XOF":26.1369,
                      "XPF":4.7548,
                      "YER":10.3402,
                      "ZAR":0.7602,
                      "ZMW":1.1505,
                      "ZWL":1.0697
                     }
                    }
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)

        val response = api.getCurrentCurrency("CZK")

        if (response.isSuccessful) {
            val currencies = response.body()
            assertEquals("CZK", currencies?.base_code)
        } else {
            println("API call failed: ${response.code()}")
        }

        val rates = response.body()?.conversion_rates
        assertEquals(1.0, rates?.get("CZK"))
        assertEquals("success", response.body()?.result)
    }

    @Test
    fun test2_apiHandlesErrorResponse() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody(
                """
            {
             "result": "error",
             "error-type": "unsupported-code"
            }
            """.trimIndent()
            )

        mockWebServer.enqueue(mockResponse)
        val response = api.getCurrentCurrency("AAA")
        assertEquals(404, response.code())
    }
}
