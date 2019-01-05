# Don't using data binding (yet)

**Short:** It will produce time consuming errors and isn't as comfortable how it looks.

It's a interesting technology to connect your View components where you defined them.
But at this time there are few annoying issues which bring more headache than comfort.

Please tell when issues are gone or you know a workaround.

## Hard to find errors

It comes the time when compilation will fail and you must find the error.

### Missing methods/variables

The missing part is printed but not easy to find.

* no jump location info where it is missing or you could fix it
* miss leading jump location a general generated `DataBindingMapper` class
* wrong line info (prints linenumber above the real issue) for XML file

```
* What went wrong:
Execution failed for task ':app:compileDebugJavaWithJavac'.
> android.databinding.tool.util.LoggedErrorException: Found data binding errors.
  ****/ data binding error ****msg:Could not find accessor com.example.MyViewModel.myProperty file:/workspace/project/app/src/main/res/layout/fragment_sample.xml loc:99:41 - 99:59 ****\ data binding error ****
```
*But it was on line 100*

### Forgot to bind instance

For defined XML binding variables you must create and set them.
But what happen when you forget it? Nothing, no info, no crash, just no view functionality.
Really weird when you click a Button and just nothing does happen.

## Who is responsible for UI logic?

Often it's enough when your Fragment and View observing ViewModels LiveData.
How would you solve it when your view wants to show a Dialog (e.g. DatePicker)?

### Pure ViewModel binding solution:

* XML observes ViewModels LiveData
* Fragment observes ViewModels LiveData

```
Click on date text
-> XML "data binding informs the ViewModel about the request to change the date"
-> ViewModel "informs the Fragment to enable date changes"
-> Fragment "shows a DatePicker"
-> Fragment "tells the ViewModel the new data"
-> ViewModel "updates the LiveData which stores the date value"
-> XML "data binding is observing the date LiveData"
```

I guess you will agree that the view details shouldn't bother the ViewModel and the DatePicker is more Ui than business logic.

### Binding with ViewModel und Fragment

* XML observes ViewModels LiveData
* Fragment observes ViewModels LiveData

```
Click on date text
-> XML "data binding informs the Fragment about the request to change the date"
-> Fragment "shows a DatePicker"
-> Fragment "tells the ViewModel the new data"
-> ViewModel "updates the LiveData which stores the date value"
-> XML "data binding is observing the date LiveData"
```

Still a mix up of responsibilities

* who will observe this LiveData?
* who will update this LiveData?
* who must be called (ViewModel/Fragment)?

### Without data binding

* Fragment observes ViewModels LiveData and updates View components

```
Click on date text
-> Fragment "shows a DatePicker on request"
-> Fragment "tells the ViewModel the new data"
-> ViewModel "updates the LiveData which stores the date value"
```

One argument could be the great two way binding feature, but this is fast replicated.
And you will be much more flexible when you must transform the data first.

```
fun observeTwoWay(data: MutableLiveData<String>, view: EditText) {
    // update View component
    data.observe(this, Observer { if (view.text.toString() != it /* avoid loops */) view.setText(it) })

    // update LiveData
    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) { /* no need */ }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (data.value != s.toString() /* avoid loops */) data.value = s.toString()
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { /* no need */ }
    })
}
```

## Bad code completion

Looks like not fully working inside XML files and creating new methods/variables per shortcut also.

## Increased compile time

It should be mentioned that using Kapt increase compile time.