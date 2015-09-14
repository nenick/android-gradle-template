[Back to Index](../index.md)

# Fluent Assertion with Android Support

I prefer the fluent style because you must not think so much.
Just use the smart code completion by pressing "." and select what to check.

## Android Support

The assertJ-android provides extra some checks for android elements like `assertThat( textField ).hasText( "expected text" )`

## Setup

Add to your test dependencies `'com.squareup.assertj:assertj-android:1.1.0'` and use `assertThat( actual ).isEqualTo( expected )` instead of `assertThat( actual, is( expected ))`

Import both assertThat methods to use automatic the correct one.

    import static org.assertj.android.api.Assertions.assertThat;
    import static org.assertj.core.api.Assertions.assertThat;

---

[Back to Index](../index.md)