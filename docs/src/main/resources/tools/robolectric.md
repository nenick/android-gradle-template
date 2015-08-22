# Robolectric

Is a create tool for testing your app inside jvm insdead to deploy it on a device or emulator.

## Add Robolectric to your project

They have getting started site <http://robolectric.org/getting-started/> and some sample projects at <https://github.com/robolectric/robolectric-samples>.
Some more basic examples can be found at <https://github.com/nenick/AndroidStudioAndRobolectric>

Short overview:

* dependency testCompile 'org.robolectric:robolectric:3.0'
* use @RunWith(RobolectricGradleTestRunner.class)
* use @Config(constants = BuildConfig.class, sdk = 21)

### Base class for tests

Instead of adding the annotations every time to you test class you can use a base test.

Adding following snippet to your base class make writing tests more convenient.

    protected Context context;

    @Before
    public void roboSetup() {
        context = RuntimeEnvironment.application;
    }

### Reset Singletons

You will get strange behavior when you forget to reset your tests. Most times you will get them when you use a database in your tests.

    @After
    public void finishRobolectricTest() {
        resetSingleton(ExampleSQLiteOpenHelper.class, "sInstance");
    }

    private void resetSingleton(Class clazz, String fieldName) {
        java.lang.reflect.Field instance;
        try {
            instance = clazz.getDeclaredField(fieldName);
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

### Use extra shadow modules

List of available extra modules <http://robolectric.org/using-add-on-modules/>

For apps using classes from v4 support must add *testCompile 'org.robolectric:shadows-support-v4:3.0'* or it may result in unstable tests.

### robolectric in extra test module

** check if issue is gone**
avoid release builds for test runs, and conflict between lint and test runs

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