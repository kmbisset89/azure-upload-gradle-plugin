# Azure Upload Plugin

[![License](https://img.shields.io/github/license/cortinico/kotlin-android-template.svg)](LICENSE) ![Language](https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?color=blue&logo=kotlin)

 A Gradle plugin to be used to upload files to Azure Blob Storage. This plugin allows build item to be uploaded to Azure Blob Storage. This is useful for storing build artifacts in a central location that are not useful for maven.

## How to use 👣

### Installation

To use the plugin, add the following to your project's `build.gradle.kts` file:

```kotlin
plugins {
    id("io.github.kmbisset89.azureupload.plugin") version "1.0.0"
}
```

For libs.toml:

```toml
[versions]
  azure-upload = "1.0.0"

[plugins]
azure-upload-plugin = { id = "io.github.kmbisset89.azureupload.plugin", version.ref = "azure-upload" }
```

### Configuration

After applying the plugin, configure it by setting the necessary properties in the plugin's extension block:

```kotlin
azureUpload {
    val localProps = Properties().also {
        it.load(file(rootProject.file("local.properties").path).inputStream())
    }
    packageName.set("com.example.yourpackage")
    connectionString.set(localProps.getProperty("connectionString"))
    containerName.set("yourcontainer")
    fileProperties {
        "${project.version}-debug.apk" withPath "app/build/outputs/apk/debug/app-debug.apk"
    }
}
```

#### Set to uploadToRunAfterBuild

```kotlin
tasks.build{
    finalizedBy(tasks.uploadFiles)
}
```

## Contributing 🤝

Feel free to open an issue or submit a pull request for any bugs/improvements.

## License 📄

This template is licensed under the MIT License - see the [License](License) file for details.
Please note that the generated template is offering to start with a MIT license but you can change it to whatever you
wish, as long as you attribute under the MIT terms that you're using the template.
