package io.github.kmbisset89.azureupload.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A Gradle plugin class that applies configuration for uploading files to Azure Blob Storage.
 * This plugin adds an extension to the project for configuration and registers a task
 * for the actual upload process.
 *
 * The plugin allows project-specific configuration of Azure Blob Storage credentials,
 * container details, and mappings of local files to their corresponding blob names
 * through a DSL defined in [AzureUploadPluginExtension].
 */
abstract class AzureUploadPlugin : Plugin<Project> {

    /**
     * Applies the plugin to the given project. This method is called by the Gradle runtime
     * to configure the project with this plugin's functionality.
     *
     * It creates an instance of [AzureUploadPluginExtension] to hold configuration properties,
     * and registers an [UploadTask] which performs the file upload operation based on the
     * configuration specified.
     *
     * @param project The Gradle [Project] to which the plugin is applied.
     */
    override fun apply(project: Project) {
        // Create an extension for this plugin to allow configuration via the build script.
        val extension = project.extensions.create(EXTENSION_NAME, AzureUploadPluginExtension::class.java, project)

        // Register a task to upload files to Azure Blob Storage. The task's configuration
        // is derived from the plugin extension.
        val task =
            project.tasks.register(CREATE_UPLOAD_TASK, UploadTask::class.java) {
                // Set the task's properties based on the extension's configuration.
                it.packageName.set(extension.packageName)
                it.config = extension.config
                it.container.set(extension.containerName)
                it.connectionString.set(extension.connectionString)
            }
    }

    companion object {
        // Constant names for the extension and the task provided by this plugin.
        const val EXTENSION_NAME = "azureUpload"
        const val CREATE_UPLOAD_TASK = "uploadFiles"
    }
}
