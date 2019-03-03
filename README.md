# Audit Client

This is a android client for [audit desktop](https://github.com/alvarogf97/Audit)

## Getting Started

These instructions will get you a copy of the project up and running on your Android device. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You need to have Android 7.1+ to install it in your own device. Also you will need this software installed 
in your computer if you want to build it by yourself:

```
Android studio (optional)
Android SDKManager
Adb (optional)
Gradle
```

## Installing

You can download apk from [here](https://github.com/alvarogf97/Client_Audit/releases) or compile it by yourself. Next install it in your device.

## Build with

Clone this repository on a folder in your computer:

```
https://github.com/alvarogf97/Client_Audit
```

### Android studio

Open the project cloned previously with android studio and follow these steps:

- Click the dropdown menu in the toolbar at the top
- Select "Edit Configurations"
- Click the "+"
- Select "Gradle"
- Choose your module as Gradle project
- In Tasks: enter assemble
- Press Run

Your unsigned apk is now located in ProjectName\app\build\outputs\apk

### Command line
Go to folder that contains cloned project and run:

```
gradle assemble
```

## Authors

* **Álvaro García**

See also the [desktop audit application](https://github.com/alvarogf97/Audit) to use with this client.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

