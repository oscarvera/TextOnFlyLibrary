package com.oscarvera.textonflylibrary.entities

import org.simpleframework.xml.*

@Root(name = "resources", strict = false)
class LanguageStrings {

    @field:ElementMap(entry = "string", key = "name", attribute = true, inline = true)
    var map: Map<String, String>? = null

}

data class Language(var key : String, var subfix : String)