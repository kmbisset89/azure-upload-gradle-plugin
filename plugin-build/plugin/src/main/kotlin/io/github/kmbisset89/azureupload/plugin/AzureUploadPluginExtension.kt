package io.github.kmbisset89.azureupload.plugin

import io.github.kmbisset89.azureupload.plugin.logic.ConfigProperties
import io.github.kmbisset89.azureupload.plugin.logic.ConfigPropertiesBuilder
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.Internal
import javax.inject.Inject

/**
 * Extension class for the Azure Upload Gradle Plugin. It allows the configuration of properties
 * necessary for uploading files to Azure Blob Storage within the Gradle project.
 *
 * This class provides a DSL for configuring the Azure Blob Storage connection string,
 * the container name where files will be uploaded, and the package name associated with the files.
 * Additionally, it allows defining file properties, mapping blob names to local file system paths
 * through a custom DSL implemented in [ConfigPropertiesBuilder].
 */
@Suppress("UnnecessaryAbstractClass")
abstract class AzureUploadPluginExtension(project: Project) {
    // Injection of the Project's ObjectFactory to create property instances.
    @Inject
    private val objects = project.objects

    /**
     * The Azure Blob Storage connection string. This property is required to authenticate
     * and connect to the storage account where the blobs will be uploaded.
     */
    val connectionString = objects.property(String::class.java)

    /**
     * The name of the Azure Blob Storage container to which the files will be uploaded.
     * The container must already exist or be created before uploading files.
     */
    val containerName = objects.property(String::class.java)

    /**
     * The package name associated with the files being uploaded. This can be used to
     * logically group or identify files within the project.
     */
    val packageName = objects.property(String::class.java)

    /**
     * A late-initialized property for configuring blob to path mappings.
     * Use [fileProperties] function to configure this property.
     */
    @Internal
    lateinit var config: ConfigProperties

    /**
     * Defines a DSL for configuring file properties. This method initializes [ConfigPropertiesBuilder]
     * and applies the given action to it. The result is stored in [config], providing a mapping
     * of blob names to file system paths.
     *
     * @param action The action to be applied to the [ConfigPropertiesBuilder].
     */
    fun fileProperties(action: Action<ConfigPropertiesBuilder>) {
        val builder = ConfigPropertiesBuilder {
            action.execute(this)
        }
        config = ConfigProperties(builder.allConfigProperties)
    }
}
