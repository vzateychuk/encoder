# myproject.java-conventions

configures conventions that are generic for any Java project in the organization. It applies the core java and checkstyle plugins as well as an external com.github.spotbugs plugin, configures common compiler options as well as code quality checks.

# myproject.library-conventions 

adds publishing configuration to publish to the organization’s repository and checks for mandatory content in a README. It applies java-library and maven-publish plugins as well as the myproject.java-conventions plugin.

Gradle task name is 'readmeCheck'

run example:
```shell
gradlew :api:readmeCheck
```

## see 
https://docs.gradle.org/current/samples/sample_convention_plugins.html