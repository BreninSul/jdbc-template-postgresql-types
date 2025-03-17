package io.github.breninsul.jdbctemplatepostgresqltypes.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.Duration

/**
 * A serializer for converting Java's `Duration` instances into a specific string format.
 *
 * This class extends `JsonSerializer` to handle serialization of `Duration` objects when
 * interacting with JSON. It customizes how `Duration` objects are represented as strings
 * in the output JSON, ensuring compatibility with specific formats or standards.
 *
 * The default implementation converts a `Duration` instance to its ISO-8601 string representation
 * by calling the `toString()` method. This behavior can be customized by overriding the protected
 * `format` method.
 *
 * Key Features:
 * - Converts `Duration` instances into a string format suitable for JSON serialization.
 * - Provides an extendable design allowing customization of the serialized format.
 */
open class SQLDurationSerializer : JsonSerializer<Duration>() {
    override fun serialize(duration: Duration, generator: JsonGenerator, provider: SerializerProvider) {
        generator.writeString(duration.format())
    }

    protected open fun Duration.format() = this.toString()

}