This is an example of incorporating Geb into a Gradle build. It shows the use of Spock and JUnit 4 tests.

The build is setup to work with HTMLUnit, FireFox and Chrome. Have a look at the `build.gradle` and the `src/test/resources/GebConfig.groovy` files.

The following commands will launch the tests with the individual browsers:

    ./gradlew htmlunitTest
    ./gradlew chromeTest
    ./gradlew firefoxTest

To run with all, you can run:

    ./gradlew test

For console output
    gradle -i test 
    
    
Run test:
gradle chromeTest -i

Gradle use version 1.8

Best performance of the test, update el chromedriver: 
http://chromedriver.storage.googleapis.com/2.12/chromedriver_mac32.zip or last

Option for chrome config:
http://code.google.com/p/selenium/wiki/ChromeDriver


http://peter.sh/experiments/chromium-command-line-switches/
