package io.github.kmbisset89.azureupload.plugin.logic

import java.io.File


/**
 * A builder class to construct a list of configuration properties. This class allows defining properties
 * in a domain-specific language (DSL) manner, making it easy to specify key-value pairs where the value can be
 * a path or file location. It's designed to simplify the creation of configuration settings for a project.
 *
 * @param initBlock The initialization block where properties are defined using the DSL.
 */
open class ConfigPropertiesBuilder(initBlock: ConfigPropertiesBuilder.() -> Unit) {

    /**
     * The list that holds all configuration properties as pairs of strings, where the first string is the key,
     * and the second string is the value. The value can represent a path or file location.
     */
    val allConfigProperties: MutableList<Pair<BlobName, Path>> = mutableListOf()

    // Initialize the builder with the provided block of configuration properties.
    init {
        initBlock()
    }

    /**
     * Associates a file path with a configuration key. This function allows specifying a path as a string
     * as the value for a given key in the configuration.
     *
     * @param value The file path to associate with the key.
     */
    infix fun String.withPath(value: String) {
        allConfigProperties.add(Pair(BlobName(this), Path(value)))
    }

    /**
     * Associates a file with a configuration key. This function allows specifying a [File] object,
     * using its absolute path as the value for a given key in the configuration.
     *
     * @param value The [File] whose absolute path is to be associated with the key.
     */
    infix fun String.withFile(value: File) {
        allConfigProperties.add(Pair(BlobName(this), Path(value.absolutePath)))
    }
}
