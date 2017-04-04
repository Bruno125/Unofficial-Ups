# Demo-UPC
This is a fan made android version of UPC's official application, which is no longer available at the Play Store.

[Find out about why and how I developed this project.](https://medium.com/@bruno.aybar/developing-a-fan-made-version-of-my-universitys-app-9eec3baafcd3)

<img src="https://media.giphy.com/media/3o6ZtcNv6xQ8UgCKmA/source.gif" alt="App preview" width="200" />

## Build variants

There are two product flavors for this repo, you can find them on the [build.gradle](app/build.gradle). Each one of them uses different repositories to access their info.

- __demo:__ this variant is for you to test the app. 
   - It uses [fake repositories](app/src/main/java/com/brunoaybar/unofficialupc/data/repository/impl/demo) which will provide enough logic for you to test the basic app features. 
   - I've set up demo keys for you. You can change them if you want.
   - If you want to login, the username is "demo" and the password is "123456" (you can change it [here](app/src/main/java/com/brunoaybar/unofficialupc/data/repository/impl/demo/DemoLoginRepository.kt))
- __prod__: this is the variant that I use to create the app that I distribute to the users.
   - For it to work, you'll have to add the file `appconfig.properties` to the project root.
      - It contains the info about the base url and the secret keys for the frameworks I use. You can find a sample [here](https://gist.github.com/Bruno125/eb19aa6c595fdd5d82d141dc77d5f347)
      - It contains sensitive data, so for security, it's included on the `.gitignore`. It will never the pushed to this repository. 
   - It gets its data from [these repositories](app/src/main/java/com/brunoaybar/unofficialupc/data/repository/impl/upc). They connect with the university web services, so unless you have base url nothing will work.
   
   
## Project Structure

This app consists of a single module `app`. This may change in the future, though. That module is divided into packages:

- [__Analytics__](app/src/main/java/com/brunoaybar/unofficialupc/analytics): containts logic related to analytics tracking
- [__Components/Base__](app/src/main/java/com/brunoaybar/unofficialupc/components/base): just some base classes for my componnents
- [__Data__](app/src/main/java/com/brunoaybar/unofficialupc/data): contains the models, repositories, and sources from which the data is retreived.
- [__Modules__](app/src/main/java/com/brunoaybar/unofficialupc/modules): has a module for each app functionality. Most UI logic is here
- [__Utils__](app/src/main/java/com/brunoaybar/unofficialupc/utils): Self explanatory?

## Kotlin
There are a few classes written in Kotlin. The same as above, feel free to give any feedback.

## Tests
The project also have tests (just a few). I'm still working on my testing skills so feel free to give any feedback. I'll appreaciate.

## RxJava + MVVM
One of my main goals with this project was to get introduced to RxJava + MVVM. Once again, feel free to give feedback.
