package com.example.thefortnightly.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.thefortnightly.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchAcitivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setUpUI()
    }

    private fun setUpUI() {

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }

}