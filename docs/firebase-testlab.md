# Android tests on Firebase TestLab

CircleCi has no good emulator support yet because only arm is available which is unstable and slow.
Just a few steps for free stable emulators with Firebase TestLab.

## Prerequisites

*   Have a google account.
*   Create a project at [Firebase Console](https://console.firebase.google.com/u/1/)
*   Enable the **Google Cloud Testing API** and the **Cloud Tool Results API** by typing their names into the search box at the top of the console and clicking Enable API. [Google Libraries](https://console.developers.google.com/apis/library)
*   Create a service account by following Steps 1-3 of [Google’s instructions](https://cloud.google.com/sdk/docs/authorizing#authorizing_with_a_service_account). Remember to download the JSON-formatted key file.
    *   Hint: Click on "All" and select your project

## CircleCi project environment variables

#### GOOGLE_PROJECT_ID

You can find the correct ID in your TestLab project settings [Firebase Console](https://console.firebase.google.com/u/1/)
It's the same name as the project when it not existed already before.

#### GCLOUD_SERVICE_KEY

The content of the previous downloaded JSON file.

#### GOOGLE_COMPUTE_ZONE

Use this identifiers [regions-zones](https://cloud.google.com/compute/docs/regions-zones/)

#### GOOGLE_TESTLAB_RESULTS

It's tricky. First run a manuel test by uploading your apk to Firebase TestLab.
Equal whether tests are success or not, open the test results "VIEW SOURCE FILES" and use the `test-lab-*` part from the URL.

## Cleanup firebase test result storage

The CircleCi script tries to downloads the latest test results but the latest is determined on datetime in directory name.
This strategy has a conflict with the manuel executed test runs which is often taken as latest.
Delete this folders without timestamps to have related test results. (Open the test results "VIEW SOURCE FILES")

## More details

*   [CircleCi Firebase](https://medium.com/@ayltai/all-you-need-to-know-about-circleci-2-0-with-firebase-test-lab-2a66785ff3c2)
