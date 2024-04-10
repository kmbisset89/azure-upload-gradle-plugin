package io.github.kmbisset89.azureupload.plugin.logic

import org.gradle.api.tasks.Nested

/**
 * Represents a collection of configuration properties for an application, each consisting of a [BlobName] and a [Path].
 * These properties are intended to map specific blob storage names to their corresponding file system paths,
 * facilitating the association of external resources with their local representations or destinations.
 *
 * @property properties A list of pairs, where each pair contains a [BlobName] representing the name of the blob
 * in blob storage and a [Path] representing the corresponding file system path.
 */
data class ConfigProperties(

    @Nested
    val properties: List<Pair<BlobName, Path>>,
)
