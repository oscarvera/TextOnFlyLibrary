# Text Over the fly

The most easy library for replace string dynamically with different languages. Powered with Retrofit & Paper. Programmed 100% in Kotlin. 

## Installation
Very easy! The library is hosted on jcenter. You just have to add this to your project in **build.gradle** :

```gradle
repositories {
      jcenter()
}
    
dependencies {
      compile 'com.oscarvera:textovertheair:0.4.2'
}
```

## Usage

1. First initialize the library with the initial configuration in your Application class: 

 Kotlin:
```kotlin
class App : Application() {

    companion object {
        lateinit var textonfly : TextOnFly 
    }

    override fun onCreate() {
        super.onCreate()
        val build = Build( //Basic configuration
        	this, //Context
        	"https://server/service/", //Base Url of string files
        	"strings.xml") //Name of default file language

        textonfly = TextOnFly(build)
    }

}
```
2. Add language with custom pointer.

```kotlin
   ...
   build.setLanguages(
   	listOf(
   		Language("ES","strings.xml"), //lagunage Code and custom url pointer 
   		Language("EN","strings-en.xml",
   		...
   		)
   	)
   )
   ...
```

3. Add call header of language service

```kotlin
   ...
   build.setCallHeaders(
   	hashMapOf(
   		"key" to "value",
   		...
   	)
   )
   ...
```


## Customization
You can customize when and how often the language dictionary is updated.

- The library offers you the possibility to update whenever the application opens:

```kotlin
   ...
   build.setAppWakeupListener(this) //With application context
   ...
```

And for how many times you want me to do it:

```kotlin
   ...
   build.setIntervalWakeUpRefresh(2) //Default is 1 (Allways do it)
   ...
```

If you want to choose when to update laguages:
```kotlin
   ...
   textonfly.refreshLanguages()
   ...
```

Or specific language:
```kotlin
   ...
   textonfly.refreshLanguages("code")
   ...
```
 
## Versions
We are currently in the process of development. **The current version is in beta phase**.
 
## License
```
Copyright 2019
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


