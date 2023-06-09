package com.example.kotlintest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class OtherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        var dataFromActivityMain=intent.getStringExtra("DataFromMainActivity")
        var dataTextView:TextView=findViewById(R.id.dataTextView)
        dataTextView.text=dataFromActivityMain
    }
}
