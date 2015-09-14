[Back to Index](../index.md)

https://github.com/joelittlejohn/jsonschema2pojo
https://github.com/joelittlejohn/jsonschema2pojo/wiki/Reference
https://github.com/joelittlejohn/jsonschema2pojo/tree/master/jsonschema2pojo-gradle-plugin

classpath 'org.jsonschema2pojo:jsonschema2pojo-gradle-plugin:0.4.14'

apply plugin: 'jsonschema2pojo'

// Required if generating equals, hashCode, or toString methods
    compile 'commons-lang:commons-lang:3.4'
    compile 'com.fasterxml.jackson.core:jackson-databind:2.6.1'

jsonSchema2Pojo {
    source = files("${project.rootDir}/json/network/schema")
    targetDirectory = file("${project.rootDir}/src/gen/java")
    targetPackage = 'com.example.project.network'
    useCommonsLang3 = true
}

https://github.com/joelittlejohn/jsonschema2pojo/blob/37a98588312679391f7e660962a8d40de3c826b5/jsonschema2pojo-gradle-plugin/example/android/app/build.gradle

---

[Back to Index](../index.md)