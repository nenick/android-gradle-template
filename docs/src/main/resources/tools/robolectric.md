[Back to Index](../index.md)

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

### debugging with shadows

They may a bit confuse when you try to debug.

When you see are in RobolectricInternals#methodInvoked(...) then use step out to reach the expected method.

When you reach ShadowWrangler#ShadowMethodPlan#run then step over until *return shadowMethod.invoke(shadow, params);*
and then step into then you reach the shadowed method and can debug there.

### Create shadows for non android classes

Register a class which could be shadowed (android classes can be shadowed without extra registration)

    class CustomRobolectricTestRunner {
        public InstrumentationConfiguration createClassLoaderConfig() {
            InstrumentationConfiguration.Builder builder = InstrumentationConfiguration.newBuilder();
            builder.addInstrumentedClass(MyClass.class.getName());
            return builder.build();
        }
    }

Register a Shadow for the class

    @Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowMyClass.class})
    class RobolectricTestCase {}

More informations at <http://robolectric.org/custom-shadows/>

### Avoid jumping virtual desktops

I detected this behaviour just on mac os. Every time i run robolectric tests at IDE or on command line it was jumping to another desktop.

On command line you can avoid it with `export JAVA_TOOL_OPTIONS='-Djava.awt.headless=true'`

In your IDE set as VM option in your run configuration `-Djava.awt.headless=true`

---

See also

* [Testing Concept](../concepts/testing.md)

---

[Back to Index](../index.md)