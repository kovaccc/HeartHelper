# HeartHelper - Android Application using Azure Machine Learning model
HeartHelper is  android application that predicts the outcome of a possible heart attack with regard to the entered parameters of the health condition.

## About

Android project that has it's own API that allows communication of the application and Azure Machine Learning services available on the web server. The API is based on the REST architecture and it is using HTTP protocol with method POST to send requests and get response from Azure service. The data sent to the web service and the results retrieved are structured in a **JSON** transport format. Use your own **API key** and **endpoint URL** if you want to use your own AzureML services. 

**Android version 30.0.2**
## Features
The android app lets you: 

 - Enter parameters of health condition
 - Seek processing and get the result from your own **AzureML model** (or
   use a model accessed by the application)

## Screenshots

![image](https://user-images.githubusercontent.com/75457058/108057333-c9c9b200-7052-11eb-9d6d-2fe99230a750.png)
![image](https://user-images.githubusercontent.com/75457058/108057397-dea64580-7052-11eb-9fce-b14e94ce9cb1.png)


## Permissions

HealtHelper requires the following permissions in AndroidManifest.xml: 

-  Internet permissions are used for sending request and getting response from Azure web service

## Setup
1. Clone the repository
```
git clone https://github.com/kovaccc/HeartHelper.git
```
2. Open the project with your IDE/Code Editor
3. Run it on simulator or real Android device 
4. To run Tests open test package in java directory 
5. Right click on test class or package that you want to run -> Run or Ctrl + Shift + F10

## Tests 
Two test packages
- com.example.hearthelper (androidTest) -> use to test code that use Android framework
- com.example.hearthelper (test) -> pure unit test that do not involve Android framework

![image](https://user-images.githubusercontent.com/75457058/109014959-eb5f1500-76b4-11eb-82a7-bb79a05d2886.png)


### Unit tests

Tests of single units of app (e.g. testing the functions of a class) - 70% of Tests.
JUnit 4 framework, Robolectric environment, Mockito framework are used for Unit tests.
You don't need emulator or device to run this tests. 
Test cases:
- Updating anemia LiveData object
- Creating user object when LiveData is null
- Parsing expected/unexpected JSON response 
- ViewModel function for reach result from Azure Service and updating LiveData 
- ...

![image](https://user-images.githubusercontent.com/75457058/109015124-1ba6b380-76b5-11eb-98fb-c64e0f8e109e.png)
### UI tests
They can be found in androidTest package.
Espresso is used for UI and Integration Tests - 30% of Tests.
You need emulator or real device to run this tests. 
Test cases:
- Button navigate to ResultActivity if EditText fields are not empty 
- Button doesn't navigate to ResultActivity if any of EditText fields are empty and Toast message is shown






