package io.github.kmbisset89.azureupload.plugin

import io.github.kmbisset89.azureupload.plugin.logic.ConfigProperties
import io.github.kmbisset89.azureupload.plugin.logic.WriteFilesToAzureStorageUseCase
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

/**
 * A Gradle task for uploading files to Azure Blob Storage as part of a build process.
 * This task leverages configuration provided through project extensions to determine
 * the target Azure storage container, connection details, and files to upload.
 *
 * The task is designed to work within the context of a larger build process, allowing
 * files generated during the build (e.g., compiled binaries, documentation) to be
 * uploaded to Azure Blob Storage for distribution or further processing.
 */
abstract class UploadTask : DefaultTask() {

    /**
     * Initializes task properties such as description and group for organization
     * within Gradle's task listing.
     */
    init {
        description = "Uploads file list to the specified Azure Blob Storage."
        group = BasePlugin.BUILD_GROUP
    }

    /**
     * The package name for the generated BuildConfig file. This property is input
     * to the task through command line options or task configuration in build scripts.
     */
    @get:Input
    @get:Option(
        option = "packageName",
        description = "The package name to set for the project in the build config file."
    )
    abstract val packageName: Property<String>

    /**
     * The name of the Azure Blob Storage container to which files will be uploaded.
     * This is configurable per-project to support different storage locations.
     */
    @get:Input
    @get:Option(
        option = "container",
        description = "The name of the container to made or used in the Azure Blob Storage."
    )
    abstract val container: Property<String>

    /**
     * The connection string for Azure Blob Storage, providing the necessary credentials
     * and endpoints for accessing the storage account.
     */
    @get:Input
    @get:Option(
        option = "connectionString",
        description = "The connection string to the Azure Blob Storage."
    )
    abstract val connectionString: Property<String>

    /**
     * A nested configuration property defining the list of files to be uploaded.
     * This is typically configured through the plugin extension in the build script,
     * allowing dynamic specification of files based on the build context.
     */
    @get:Nested
    lateinit var config: ConfigProperties

    /**
     * The action to be performed when the task executes. This method initiates the
     * upload process, utilizing the configured properties to determine what files
     * are uploaded and where they are stored within Azure Blob Storage.
     */
    @TaskAction
    fun executeTask() {
        WriteFilesToAzureStorageUseCase().invoke(
            connectionString = connectionString.get(),
            containerName = container.get(),
            packageName = packageName.get(),
            config = config,
            project = project
        )
    }
}
