## Template for creating a Compose Hook as a module

### Things you have to edit/change
1. In the `build.gradle.kts` of the module level build.gradle file
   Line no 7 8 9 represents your compose hook as a library.
   If you want to publish it - Change the artifactory group, version and id/name of your library
   
2. Change the package name. The default one is `com.example.compose_hook_template`
3. Change the package name in `AndroidManifest.xml` file as well
4. Change the file name to your hook name. The default is `use_HookName_.kt`

### Misc
!Important - Don't forget to add the documentation and don't forget to format. 
Library internally uses Dokka to generate documentation and ktlint for formatting.


### Publishing your hook
If you want to publish the hook as a library, you need to have the following things in your either `local.properties` or `~/.gradle/gradle.properties`

`OSSRH_USERNAME`  
`OSSRH_PASSWORD`  
`SONATYPE_STAGING_PROFILE_ID`  
`SIGNING_KEY_ID`  
`SIGNING_PASSWORD`  
`SIGNING_SECRET_KEY_RING_FILE`  

If you've this set up ^ then you can release the library/hook to mavencentral with the following gradle task 
`./gradlew :your-hook-module:publishReleasePublicationToSonatypeRepository`
