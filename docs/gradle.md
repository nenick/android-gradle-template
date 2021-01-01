## Kotlin DSL

* Language we are familiar with.
* More familiar code completion results.
* Navigate into all classes and functions.

## Folder gradle-plugins

* Reduce build.gradle size and complexity.

See also
* Blog about the advantages https://quickbirdstudios.com/blog/gradle-kotlin-buildsrc-plugin-android/
* Switch to composite builds https://proandroiddev.com/stop-using-gradle-buildsrc-use-composite-builds-instead-3c38ac7a2ab3
* Basic gradle file structure https://docs.gradle.org/current/userguide/kotlin_dsl.html#type-safe-accessors

## Share dependency versions

* Use spring dependency management to keep newer version warnings compared to solutions with variables.
    This keeps the quick fix for newer version compatible.

TODO keep warnings for all newer available versions e.g. buildScript classpath dependencies, plugins versions
perhaps this can help https://github.com/ben-manes/gradle-versions-plugin

## Custom Tasks
* **clean** all modules is located in the cleanup group.

## Java Libraries
* Big goal is to keep as much as possible modules in plain java