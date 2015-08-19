# Model View Presenter

* [Wikipedia.org - Model View Presenter](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter)
* [AntonioLeiva.com - MVP for Android](http://antonioleiva.com/mvp-android/)

Classic approach of MVP for android is to use the activity/fragment as the view which is controlled by a presenter.

## Android MVP - An Alternate Approach

Original article <http://blog.cainwong.com/android-mvp-an-alternate-approach> (until site is back see [cached version](http://webcache.googleusercontent.com/search?q=cache:OgfVapRjNRYJ:blog.cainwong.com/android-mvp-an-alternate-approach/))
and github project <https://github.com/wongcain/MVP-Simple-Demo>

**The statement about activities and fragments as view:**

*".. Activities send Intents, start Services, create and execute FragmentTransisitons, etc. All of these complexities are, in my opinion, outside of the scope of what a "View" should be concerned with. A View's job is to present data and get input from the user. Ideally, a View should be devoid of business logic, making a unit test of the View unnecessary. .."*

This results in activities/fragments/adapter are treated as presenters and moving all view code into a separated class.

## Following the alternate approach

I like the idea, often I wrote boilerplate code to inform the presenter about the current life cycle.
This approach give you still clean separated code but faster and more intuitive development thinking.

The base for the MVP can be found at *com.example.project.view.common.mvp* for the usage see the example implementations.


The activity is most time only the glue between fragments.
When fragments has something to show they will tell it back through a Show...Listener to the current activity.
The root activity may differ between portrait and landscape mode.