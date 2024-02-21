/*
 * MIT License
 *
 * Copyright (c) 2024 BreninSul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.breninsul.jdbctemplatepostgresqltypes.type

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.breninsul.jdbctemplatepostgresqltypes.configuration.PGDefaultMapperHolder

/**
 * Abstract class that handles type mapping between PostgreSQL and the corresponding Java objects.
 *
 * @property specificMapper The specific object mapper that is used for the JSON serialization and deserialization. If no mapper is specified, a default mapper is used.
 */
abstract class PGAbstractJson(valueObject: Any?, pgTypeName: String, protected val specificMapper: ObjectMapper? = null) :
    PGAbstractObject<Any?>(valueObject, pgTypeName) {
    /**
     * Maps the value to a JSON string.
     *
     * @param obj Object to be mapped.
     * @return The JSON string representation of the object.
     */
    override fun mapValue(obj: Any?): String? {
        return obj?.let { getMapper().writeValueAsString(it) }
    }

    /**
     * Converts the JSON string to a specified type of object.
     *
     * @param T The type that the JSON string is expected to be converted to.
     * @param javaClass The class of T.
     * @return The T type object converted from the JSON string.
     */
    fun <T : Any> toObject(javaClass: Class<T>): T? {
        return toObject(getMapper().constructType(javaClass))
    }

    /**
     * Converts the JSON string to a specified type of object.
     *
     * @param T The type that the JSON string is expected to be converted to.
     * @param javaType The Java type of T.
     * @return The T type object converted from the JSON string.
     */
    fun <T : Any> toObject(javaType: JavaType): T? {
        return getValue()?.let { getMapper().readValue(it, javaType) }
    }

    /**
     * Converts the JSON string to a specified type of object.
     *
     * @param T The type that the JSON string is expected to be converted to.
     * @return The T type object converted from the JSON string.
     */
    inline fun <reified T : Any> toObject(): T? {
        return toObject(T::class.java)
    }

    /**
     * Returns the specific ObjectMapper if it exists. If it doesn't, return the default mapper.
     *
     * @return The specific or default ObjectMapper.
     */
    protected open fun getMapper(): ObjectMapper {
        return specificMapper ?: PGDefaultMapperHolder.getMapper()
    }
}
