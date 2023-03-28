package com.example.foodfacts

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi

class ResultActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        createAndAddFragments()

        val resultItemData: Parcelable? = intent.getParcelableExtra("RESULT_ITEM") // TODO: do all of these in model (mainrepo)
        println("==== in result activity ====")
        println(resultItemData)
    }

    private fun createAndAddFragments() {
        val fm = supportFragmentManager.beginTransaction()
        fm.add(R.id.fragmentContainerView, ResultFragment())
        fm.commit()
    }
}