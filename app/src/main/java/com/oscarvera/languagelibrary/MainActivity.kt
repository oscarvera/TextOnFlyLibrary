package com.oscarvera.languagelibrary

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView
import com.oscarvera.textonflylibrary.Build
import com.oscarvera.textonflylibrary.TextOnFly
import com.oscarvera.textonflylibrary.entities.Language
import com.oscarvera.textonflylibrary.setTextFly

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var textTest = findViewById<TextView>(R.id.textTest)

        val textonfly = TextOnFly(Build(this,"https://s3-eu-west-1.amazonaws.com/mrjeff-public/","strings.xml"))
        textonfly.languages = listOf(Language("ES","strings.xml"),Language("EN","strings-en.xml"))


        fab.setOnClickListener { view ->
            textonfly.refreshLanguages {
                textTest.setTextFly("addressbill")
            }

        }
    }

}
