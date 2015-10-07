[Back to Index](index.md)

# Adjust the project to your needs

Start by cloning the project `git clone https://github.com/nenick/android-gradle-template.git myAppName` and navigate into it `cd myAppName`.

### Create fresh git history

Remove the git folder `rm -rf .git` and create a new history by `git init`.

You can connect it with an already existing remote reository by:

```
git remote add origin <remote_repo_url>
git push --all --set-upstream origin
```

### Remove database support

* clean up the app/build.gradle file from stuff which is added for [database example](database.md)
* delete the app/src/main/java/com/example/project/database package
* now delete or adjust other code with compile errors

### Remove network support

* clean up the app/build.gradle file from stuff which is added for [network example](network.md)
* delete the app/src/main/java/com/example/project/database package
* now delete or adjust other code with compile errors

---

[Back to Index](index.md)
