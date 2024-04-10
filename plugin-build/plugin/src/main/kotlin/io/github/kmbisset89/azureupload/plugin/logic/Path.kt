package io.github.kmbisset89.azureupload.plugin.logic

import org.gradle.api.tasks.Input

/**
 * Wraps a file system path in a value class, providing strong typing and reducing errors associated with raw strings.
 *
 * @property path The file system path represented as a string.
 */
@JvmInline
value class Path(
    @Input
    val path: String
)
