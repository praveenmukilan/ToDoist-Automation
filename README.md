# ToDoist App automation 
 Pre-requisites to run the code :
 -
 - Java preferably 8. Set JAVA_HOME in ~/.bash_profile to point to the JDK installation
 - Install Android SDK and point the path to the tools, platform-tools, emulator binary in the PATH env variable
 - set ANDROID_HOME env variable to point to the Android SDK root directory
 - Maven 3.5.4 to be included in the PATH env variable
 - Install Appium app from https://github.com/appium/appium-desktop/releases/tag/v1.13.0
 - Start the server on a port, default is 4723. It changed be changed to a custom port but needs to be passed as cli argument
 - Install Android emulator with x86 Intel HAXM acceleration
 - Run `adb devices` to get the emulator name which need to be passed as a command line argument `emulator`
 	eg. List of devices attached
	emulator-5554	device
	`emulator-5554` is the emulator in this example
 - Register at https://todoist.com and get the token for api access. this can be passed as cli argument.

 To run the solution :
 - 
 - Start Appium Server on a port, preferrably, 4723. Start an emulator, run `adb devices` and note down the emulator device name.
 - Get the api token, user email & password for the todoist account
 - cd to project root directory and run `mvn test -Demulator="emulator-5554" -Dserver.port="4723" -Demail="xxx@xxx.com" -Dpwd="idontknow" -Dtoken="19f95cac30653664f60a119ec4b3b1465bb1e9fa" -Dserver.ip="127.0.0.1"`
 - Maven will download all the dependencies (if required) compile the source code. Maven surefireplugin will run the test suite
 - This solution is tested already in Nexus S on API27, Pixel3 emulator on API28.
