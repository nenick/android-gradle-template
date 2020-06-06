// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

dependencyManagement {
    dependencies {
        dependency("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
    }
}

buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    register("clean", Delete::class) {
        group = "cleanup"
        delete = setOf(buildDir)
    }
}
