package io.github.kmbisset89.azureupload.plugin.logic

import org.gradle.api.tasks.Input

data class TaskInfo(
    @Input
    val blobName : String,
    @Input
    val path : String
)
