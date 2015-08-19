# Package structure

The project package structure follows mainly the package by feature approach.

**With strict package by feature approach it becomes hard to distinguish between business feature and view packages.**

For me I recognised that my work is mostly view and business driven so I need to find fast the related view
or business package which need to be changed.

With pure package by layer its mostly very easy to decide where to put new classes (new Activity into package activities)
but for package by feature it can become complicated.
Often you will be confronted with the problem that different features need the same classes.
Create new feature package for shared classes may a solution but will end in many packages
inside one package or in a more structured way with sub packages.
After this step it looks like many developer must think long time about where to put new code because
they can't distinguish easy between business and view logic related packages and this may result in a bad mix.

*It's the nature of a layers to use many different features of the lower layer.*

I recommend to use a mix of package by layer and by feature in the following way:

![main package structure](http://yuml.me/a737ade0)

### ..view.concrete_view
*(replace 'concrete_view' with a describing name for your view, e.g. contact_editor.)*

Shows the reader all kinds of views which this application present.
The main entry point for UI related development.
Don't put any business logic into view layer, presentation logic is never business logic but decision logic may be business logic.
Can be the a full view (activity, fragment, etc) or a partial view (dialog, view group, etc).
When something is changed here the changed view and depending views must be checked.

### ..business.business_feature
*(replace 'business_feature' with a describing name for your feature, e.g. contact.)*

Its often that multiple views need the same business features.
Shows all the business features supported by this application.
The main entry point for business related development.
When something is changed here the changed business feature must checked with the related views.

### ..database.table
*(replace 'table' with a describing name for your table, e.g. contact.)*

Its often that multiple business features access the same database tables.
When something is changed here there must be a migration script and the database version must be increased.
The app update process should be tested for data issues and all related business features.
Content should not change often like other do. The most parts of this package content may be generated.

### ..network.api
*(replace 'api' with a describing name for your used api, e.g. facebook.)*

Its not often that two business feature use the same network calls but sometimes they do.
When something is changed here the interaction with real services must be tested and all related business features.
Content should not change often like other do. The most parts of this package content may be generated.

## Some articles about the package topic

* [CodingTheArchitecture.com - Package by component and architecturally-aligned testing](http://www.codingthearchitecture.com/2015/03/08/package_by_component_and_architecturally_aligned_testing.html
* [Oracle.com - Naming a Package](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html
* [JavaPractices.com - Package by feature, not layer](http://www.javapractices.com/topic/TopicAction.do?Id=205
* [StackOverflow.com - What strategy do you use for package naming in Java projects and why?](http://stackoverflow.com/questions/533102/what-strategy-do-you-use-for-package-naming-in-java-projects-and-why

## Additional suggestions

### ..view.common.stuff
*(replace 'stuff' with a describing name for your , e.g. contact.)*

Put here basic stuff used by different views.
