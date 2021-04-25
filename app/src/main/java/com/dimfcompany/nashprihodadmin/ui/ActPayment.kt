package com.dimfcompany.nashprihodadmin.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getIntExtraMy
import com.dimfcompany.nashprihodadmin.base.extensions.runActionWithDelay
import com.dimfcompany.nashprihodadmin.databinding.ActPaymentBinding
import java.net.URLEncoder

class ActPayment : BaseActivity()
{
    lateinit var bnd_payment: ActPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        bnd_payment = DataBindingUtil.setContentView(this, R.layout.act_payment)

        setWebViewClient()
        makePay()
//        runActionWithDelay(lifecycleScope, 4000,
//            {
//
//            })
    }

    fun setNavStatus()
    {
        is_full_screen = false
        is_below_nav_bar = false
        color_status_bar = getColorMy(R.color.white)
        color_nav_bar = getColorMy(R.color.white)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    private fun makePay()
    {
        val order_id = getExtraOrderId()
//        val sum = getExtraOrderSum()
        val sum = 10
        val url = "https://yoomoney.ru/quickpay/confirm.xml"
        val postData = "receiver=410016630036254" +
                "&formcomment=" + "Пожертовования на записки".toUrlEncodePost() +
                "&short-dest=" + "Пожертовования на записки".toUrlEncodePost() +
                "&label=$order_id" +
                "&quickpay-form=donate" +
                "&targets=" + "Оплата записок из приложения НашПриход" +
                "&sum=$sum" +
                "&comment=" + "Пожертовования на записки".toUrlEncodePost() +
                "&need-fio=false" +
                "&need-email=false" +
                "&need-phone=false" +
                "&need-address=false" +
                "&paymentType=AC"

        bnd_payment.webView.postUrl(url, postData.toByteArray())
    }

    private fun getExtraOrderId(): String
    {
        val order_id = intent.getStringExtra(Constants.Extras.DONATION_ID)
        return order_id!!
    }

    private fun getExtraOrderSum(): Int
    {
        val sum = intent.getIntExtraMy(Constants.Extras.DONATION_SUM)
        return sum!!
    }

    private fun setWebViewClient()
    {
        val web_view = bnd_payment.webView
        val settings = web_view.settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true

        bnd_payment.webView.webViewClient = object : WebViewClient()
        {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean
            {
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?)
            {
                Log.e("ActPayment", "WebPage finished $url")
//                if (url?.contains("success") == true)
                if (url?.startsWith("https://yoomoney.ru/transfer/process/") == true)
                {
                    finishWithResultOk()
                }
                super.onPageFinished(view, url)
            }
        }
    }
}

fun String.toUrlEncodePost(): String
{
    return URLEncoder.encode(this, "UTF-8")
}