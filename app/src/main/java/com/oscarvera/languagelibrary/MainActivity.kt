package com.oscarvera.languagelibrary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView
import com.oscarvera.textonflylibrary.Build
import com.oscarvera.textonflylibrary.TextOnFly
import com.oscarvera.textonflylibrary.entities.Language
import com.oscarvera.textonflylibrary.utils.setTextAir

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var textTest = findViewById<TextView>(R.id.textTest)


        fab.setOnClickListener { view ->
                textTest.setTextAir("addressbill")
        }
    }

}
