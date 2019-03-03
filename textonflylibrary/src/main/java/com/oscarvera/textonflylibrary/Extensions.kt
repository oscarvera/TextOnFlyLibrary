package com.oscarvera.textonflylibrary

import android.support.v4.content.res.ResourcesCompat
import android.widget.TextView
import com.oscarvera.textonflylibrary.entities.Language
import io.paperdb.Paper

fun TextView.setTextFly(id: String) {

    var maptext = Paper.book().read((Paper.book().read("language") as Language).key) as Map<String,String>
    var text = maptext[id]

    if (text!=null){
        //Find on the translations
        this.text = text
    }else{
        //Set the system defaults translations
        this.text = context.getText(resources.getIdentifier(id, "string", context.packageName))
    }
}
