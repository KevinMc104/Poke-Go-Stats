package com.analytics.pokegostats.view.error

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.analytics.pokegostats.R
import com.analytics.pokegostats.service.PokemonHelper
import com.analytics.pokegostats.view.home.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.error_activity.*

/**
 * One way to do error handling better is to get an error interceptor on retrofit
 * Could not find a standard for error handling so there may be better ways to do this,
 * but this is at least a way to let users retry calls to the servers and simultaneously capture the error
 */
class ErrorActivity : AppCompatActivity() {

    private val helper: PokemonHelper = PokemonHelper.instance
    private lateinit var viewModel: ErrorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.error_activity)

        // Creates the View Model
        val factory = ErrorViewModel.Companion.Factory(this.application)
        viewModel = ViewModelProvider(this, factory).get(ErrorViewModel::class.java)

        val errorMessage = intent.extras!!.getString(helper.ERROR_MESSAGE)
        tv_error_message.text = errorMessage
        Snackbar.make(findViewById<View>(android.R.id.content), errorMessage.toString(), Snackbar.LENGTH_LONG).show()

        retry_button.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(helper.RETRY_CALLS, true)
                this.startActivity(intent)
        }
    }
}