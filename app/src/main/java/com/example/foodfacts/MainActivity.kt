package com.example.foodfacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: ApiViewModel by viewModels()

        val button = findViewById<Button>(R.id.button_main)
        val text = findViewById<TextView>(R.id.textView_main).text.toString()

        button.setOnClickListener {
            viewModel.goToResultActivity(this, text)
        }
    }

}