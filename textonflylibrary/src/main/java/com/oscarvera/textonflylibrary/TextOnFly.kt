package com.oscarvera.textonflylibrary

import android.content.Context
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


class TextOnFly(build: Build) {

    val calls: CallsService
    var languages : List<Language> = listOf(Language("DEF",build.pointerUrl))

    init {

        Paper.init(build.context)

        build.languages?.let {
            languages = it
        }

        Paper.book().write("language",languages.first())

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(ClientRetrofit.getHttpClient().build())
            .build()

        
        calls = retrofit.create(CallsService::class.java)




    }

    fun changeLanguage(code : String){
        Paper.book().write("language",code)
    }

    fun refreshLanguages(onLoad : ()->Unit) {

        languages.onEach {

            val call = calls.getLanguage(it.subfix)
            call.enqueue(object : Callback<LanguageStrings> {
                override fun onFailure(call: Call<LanguageStrings>, t: Throwable) {
                    Log.e("refreshLanguage","Call error: ${t.message}")
                }

                override fun onResponse(call: Call<LanguageStrings>, response: Response<LanguageStrings>) {
                    val language = response.body()
                    if (language!=null) {
                        if (language!!.map != null) {
                            Paper.book().write(it.key, language.map)
                            onLoad.invoke()
                        }
                    }
                }
            })

        }



    }

}

class Build(var context: Context,internal var baseUrl: String, internal var pointerUrl: String) {

    internal var languages: List<Language>? = null


    fun setLanguages(listlanguages: List<Language>): Build {
        languages = listlanguages
        return this
    }

}