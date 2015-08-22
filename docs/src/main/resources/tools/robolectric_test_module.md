For Robolectric basics see also [Robolectrc](robolectric.md)

### avoid release builds for test runs, and conflict between lint and test runs

** check if issue is gone**

    afterEvaluate {

        def isLintRun = false
        def isTestRun = false

        gradle.startParameter.taskNames.each {
            if (it.contains("lint")) {
                isLintRun = true
            }
            if (it.contains("test")) {
                isTestRun = true
            }
        }

        if (isLintRun && isTestRun) {
            println "WARNING: tests for release type are disabled for supporting jacoco"
            println "WARNING: run test and lint at same time is not supported"
            exit 1
        }

        if (isTestRun) {
            tasks.each {
                if (it.name.contains("Release")) {
                    it.enabled = false
                }
            }
        }
    }