package io.github.kmbisset89.azureupload.plugin.logic

import com.azure.storage.blob.BlobContainerClientBuilder
import com.azure.storage.blob.models.BlobStorageException
import org.gradle.api.Project

class WriteFilesToAzureStorageUseCase {

    operator fun invoke(
        connectionString: String,
        containerName: String,
        packageName: String,
        config: ConfigProperties,
        project : Project
    ) {

        val storageAccount = BlobContainerClientBuilder()
            .connectionString(connectionString)
            .containerName("$packageName-$containerName")
            .buildClient()

        storageAccount.createIfNotExists()

        config.properties.forEach { (blobName, path) ->
            try {
                val blob = storageAccount.getBlobClient(blobName)
                blob.uploadFromFile(path, true)
            } catch (e : BlobStorageException){
                project.logger.error("Error uploading file $path to Azure Blob Storage: ${e.message}")
            }
        }
    }
}
