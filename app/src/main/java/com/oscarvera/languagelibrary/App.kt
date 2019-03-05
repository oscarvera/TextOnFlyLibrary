package com.oscarvera.languagelibrary

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.oscarvera.textonflylibrary.Build
import com.oscarvera.textonflylibrary.TextOnFly
import com.oscarvera.textonflylibrary.entities.Language

class App : Application() {

    companion object {
        lateinit var textonfly : TextOnFly
    }

    override fun onCreate() {
        super.onCreate()
        val build = Build(this,"https://s3-eu-west-1.amazonaws.com/mrjeff-public/","strings.xml")
        build.setLanguages(listOf(Language("ES","strings.xml"),Language("EN","strings-en.xml")))
        build.setAppWakeupListener(this)
        build.setIntervalWakeUpRefresh(2)
        build.setCallHeaders(hashMapOf("Postman-Token" to "ab377f56-dd23-4fdb-9639-d324408acf75"))
        textonfly = TextOnFly(build)
    }

}