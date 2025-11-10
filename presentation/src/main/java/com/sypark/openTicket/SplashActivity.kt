package com.sypark.openTicket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sypark.openTicket.base.BaseActivity
import com.sypark.openTicket.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val delayTime = if (BuildConfig.DEBUG) {
            300L
        } else {
            3000L
        }



        lifecycleScope.launch {
            delay(delayTime)

            Intent(this@SplashActivity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(this)
            }

            finish()
        }
    }

}