[Back to Index](../index.md)

# AndroidAnnotations

I decided me for androidannotations instead of dagger, butterknife and co because it just fit perfectly into all my need.

* dependency injection
* view injection
* view event
* background / ui thread
* REST
* and many more

## Infos

* [Documentation](https://github.com/excilys/androidannotations/wiki)
* [Available Annotations](https://github.com/excilys/androidannotations/wiki/AvailableAnnotations)
* [Latest Release Version](https://github.com/excilys/androidannotations/releases)

## Add AndroidAnnotations to your project

They have a create documentation so read <https://github.com/excilys/androidannotations/wiki/Building-Project-Gradle>

More is not necessary, all code generation is automatically be done by a build.

Short overview:

* **buildscript:** classpath 'com.neenbedankt.gradle.plugins:android-apt:1.7'
* **apply plugin:** 'com.neenbedankt.android-apt'
* **add config:**
    apt {
        arguments {
            androidManifestFile variant.outputs[0].processResources.manifestFile
        }
    }
* **dependency:** apt "org.androidannotations:androidannotations:3.3.2"
* **dependency:** compile "org.androidannotations:androidannotations-api:3.3.2"

When you don't use the http support then you must adjust your proguard config to avoid proguard issues.
-dontwarn org.androidannotations.api.rest.*

## REST Support

Andorid Annotations use Spring for network communication <http://projects.spring.io/spring-android/>
latest version <https://maven-repository.com/artifact/org.springframework.android/spring-android-rest-template>

* **dependency:** (optional) compile spring
https://github.com/excilys/androidannotations/wiki/Rest-API

---

[Back to Index](../index.md)