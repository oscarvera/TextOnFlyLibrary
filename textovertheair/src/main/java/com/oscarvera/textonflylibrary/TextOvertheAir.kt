package com.oscarvera.textonflylibrary

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.oscarvera.textonflylibrary.data.Config
import com.oscarvera.textonflylibrary.data.online.CallsService
import com.oscarvera.textonflylibrary.data.online.ClientRetrofit
import com.oscarvera.textonflylibrary.entities.Language
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import com.oscarvera.textonflylibrary.entities.LanguageStrings
import io.paperdb.Paper
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class TextOnFly(build: Build) : Application.ActivityLifecycleCallbacks {

    val calls: CallsService
    var languages: List<Language> = listOf(Language("DEF", build.pointerUrl))
    var languageChoose: Int = 0
    var interval = 1
    var intervalDone = 0

    init {

        Paper.init(build.context)

        build.languages?.let {
            languages = it
        }

        build.application?.let {
            it.registerActivityLifecycleCallbacks(this)
        }

        build.intervalRefresh?.let {
            interval = it
        }

        build.languageSelected?.let { code->
            languages.find {
                it.key == code
            }?.let {
                languageChoose = languages.indexOf(it)
            }
        }

        Paper.book().write("language", languages[languageChoose])

        val retrofit = Retrofit.Builder()
            .baseUrl(build.baseUrl)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(ClientRetrofit.getHttpClient(build.callHeaders).build())
            .build()


        calls = retrofit.create(CallsService::class.java)
    }

    fun changeLanguage(code: String) {
        languages.find {
            it.key == code
        }?.let {
            languageChoose = languages.indexOf(it)
            Paper.book().write("language", it)
        }
    }

    fun refreshLanguages(code : String? = null) {

        languages.filter {
            code?.let { code ->
                it.key==code
            }?:kotlin.run {
                true
            }
        }.onEach {

            val call = calls.getLanguage(it.subfix)
            call.enqueue(object : Callback<LanguageStrings> {
                override fun onFailure(call: Call<LanguageStrings>, t: Throwable) {
                    Log.e("refreshLanguage", "Call error: ${t.message}")
                }

                override fun onResponse(call: Call<LanguageStrings>, response: Response<LanguageStrings>) {
                    val language = response.body()
                    if (language != null) {
                        if (language.map != null) {
                            Paper.book().write(it.key, language.map)
                        }
                    }

                }
            })

        }
    }


    override fun onActivityStarted(activity: Activity?) {
        if (++intervalDone == interval) {
            intervalDone = 0
            refreshLanguages()
        }
    }

    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity?) {}

    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}
}

class Build(var context: Context, internal var baseUrl: String, internal var pointerUrl: String) {

    internal var languages: List<Language>? = null
    internal var application: Application? = null
    internal var intervalRefresh: Int? = null
    internal var callHeaders: HashMap<String,String>? = null
    internal var languageSelected: String? = null


    fun setLanguages(listlanguages: List<Language>): Build {
        languages = listlanguages
        return this
    }

    fun setLanguageSelected(codeLanguage: String): Build {
        languageSelected = codeLanguage
        return this
    }

    fun setAppWakeupListener(contextApplication: Application): Build {
        application = contextApplication
        return this
    }

    fun setIntervalWakeUpRefresh(interval: Int): Build {
        intervalRefresh = interval
        return this
    }

    fun setCallHeaders(headers: HashMap<String,String>): Build {
        callHeaders = headers
        return this
    }
}