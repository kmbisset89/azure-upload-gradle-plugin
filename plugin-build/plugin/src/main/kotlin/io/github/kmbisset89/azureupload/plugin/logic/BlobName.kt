package io.github.kmbisset89.azureupload.plugin.logic

import org.gradle.api.tasks.Input

/**
 * Wraps a blob storage name in a value class, providing strong typing and reducing errors associated with raw strings.
 * This class makes it clear when a string is intended to represent the name of a blob in storage.
 *
 * @property name The name of the blob in blob storage.
 */
@JvmInline
value class BlobName(
    @Input

    val name: String
)
