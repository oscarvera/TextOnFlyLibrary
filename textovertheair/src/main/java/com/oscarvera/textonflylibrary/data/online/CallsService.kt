package com.oscarvera.textonflylibrary.data.online

import com.oscarvera.textonflylibrary.entities.LanguageStrings
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CallsService {

    @GET("{file}")
    fun getLanguage(@Path("file") file : String): Call<LanguageStrings>

}