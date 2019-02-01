

### Project root ...

Path | Details
---|---
`.circleci` | All what belongs to the continuous integration process (e.g. config, scripts).
`app` | App implementation details (e.g. View, ViewModel, Repository).
`app-tests` | Android tests integrating the whole application.
`data-local` | App local storage implementation details (e.g. Room).
`data-network` | App network implementation details (e.g. Retrofit).
`docs` | Hints for this project template.
`gradle` | Additional gradle scripts for build processes and gradle wrapper details.

#### ... App module

Path | Details
---|---
`src/test/` | Pure jvm based tests.
`src/androidTest/` | Small instrumentation based tests with much mocking.

#### ... App tests module

Path | Details
---|---
`src/main/` | Full integration instrumentation.

#### ... Data local module

Path | Details
---|---
`src/../entities` | Json object wrapper classes matching the api model.
`src/../tools` | Some internals to extend or improve the underlaying network library.
`src/test/` | Robolectric supported jvm tests.

#### ... Data network module

Path | Details
---|---
`src/../entities` | Json object wrapper classes matching the api model.
`src/../tools` | Some internals to extend or improve the underlaying network library.
`src/test/` | Pure jvm based tests. Also real network calls do work.