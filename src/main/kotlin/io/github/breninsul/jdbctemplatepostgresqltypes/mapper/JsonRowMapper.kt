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

package io.github.breninsul.jdbctemplatepostgresqltypes.mapper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.breninsul.jdbctemplatepostgresqltypes.configuration.PGDefaultMapperHolder
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import kotlin.reflect.KClass

/**
 * Row mapper for Json data
 *
 * @param T Any type for row data
 * @property types List of Json data
 * @property specificMapper ObjectMapper instance for converting Json data
 * @property isOneRow check if the data row is just one or multiple Json rows
 * @constructor Creates a RowMapper for the provided Json row structure
 */
open class JsonRowMapper<T : Any>(protected val types: List<JsonRow>, protected val specificMapper: ObjectMapper? = null, val isOneRow: Boolean = false) : RowMapper<T> {
    /**
     * Secondary Constructor with a single JsonRow
     */
    constructor(row: JsonRow, specificMapper: ObjectMapper? = null) : this(listOf(row), specificMapper, true)

    /**
     * Secondary constructor with a TypeReference type of the row
     */
    constructor(typeRef: TypeReference<T>, specificMapper: ObjectMapper? = null) : this(JsonRow(null, typeRef), specificMapper)

    /**
     * Secondary constructor with a JavaType type of the row
     */
    constructor(javaType: JavaType, specificMapper: ObjectMapper? = null) : this(JsonRow(null, javaType), specificMapper)

    /**
     * Secondary constructor with a class type of the row
     */
    constructor(javaClass: Class<T>, specificMapper: ObjectMapper? = null) : this(JsonRow(null, javaClass), specificMapper)

    /**
     * Secondary constructor with a KClass type of the row
     */
    constructor(kotlinClass: KClass<T>, specificMapper: ObjectMapper? = null) : this(kotlinClass.java, specificMapper)

    /**
     * Nested class for JsonOrderedRow
     */
    open class JsonOrderedRow(val id: Int, val name: String?, val javaType: JavaType, val rawSqlType: Boolean)

    protected open val orderedTypes = types.mapIndexed { i, v -> JsonOrderedRow(i + 1, v.name, v.javaType, v.rawSqlType) }

    /**
     * Mapping function to map a result set row to a model object
     *
     * @param rs The result set with the data
     * @param rowNum The number of the row to map
     * @return The mapped object
     */
    override fun mapRow(
        rs: ResultSet,
        rowNum: Int,
    ): T? {
        val mapper = getMapper()
        if (isOneRow) {
            val type = orderedTypes.first()
            return mapOneType(type, rs, mapper) as T?
        }
        return orderedTypes.map {
            val key = it.name ?: it.id as Any
            return@map key to mapOneType(it, rs, mapper)
        }.toMap() as T?
    }

    /**
     * Function to map a single type of data
     *
     * @param type The ordered row data
     * @param rs The result set with the data
     * @param mapper The ObjectMapper for the data conversion
     * @return The mapped data
     */
    protected open fun mapOneType(
        type: JsonOrderedRow,
        rs: ResultSet,
        mapper: ObjectMapper,
    ): Any? {
        val rawClass = javaWrapperType(type.javaType.rawClass.kotlin)
        if (type.name != null) {
            if (type.rawSqlType) {
                return rs.getObject(type.name, rawClass)
            } else {
                return rs.getString(type.name)?.let { mapper.readValue(it, type.javaType) }
            }
        } else {
            if (type.rawSqlType) {
                return rs.getObject(type.id, rawClass)
            } else {
                return rs.getString(type.id)?.let { mapper.readValue(it, type.javaType) }
            }
        }
    }

    /**
     * Function to get a wrapper type for a Kotlin primitive type
     *
     * @param original The original class type
     * @return The class of the corresponding java.lang wrapper type
     */
    protected open fun javaWrapperType(original: KClass<*>): Class<*> {
        return when (original) {
            Int::class -> java.lang.Integer::class.java
            Long::class -> java.lang.Long::class.java
            Double::class -> java.lang.Double::class.java
            Float::class -> java.lang.Float::class.java
            Boolean::class -> java.lang.Boolean::class.java
            Char::class -> java.lang.Character::class.java
            Byte::class -> java.lang.Byte::class.java
            Short::class -> java.lang.Short::class.java
            Void::class -> java.lang.Void::class.java
            else -> this.javaClass // For non-primitive types, returns the Java class itself
        }
    }

    /**
     * Function to get an ObjectMapper instance
     *
     * @return the specific ObjectMapper or a default one
     */
    protected open fun getMapper() = specificMapper ?: PGDefaultMapperHolder.getMapper()
}

fun <T : Any> Class<T>.toRowMapper(specificMapper: ObjectMapper? = null): JsonRowMapper<T>  {
    return JsonRowMapper(this, specificMapper)
}

fun <T : Any> KClass<T>.toRowMapper(specificMapper: ObjectMapper? = null): JsonRowMapper<T>  {
    return JsonRowMapper(this, specificMapper)
}

fun <T : Any> JavaType.toRowMapper(specificMapper: ObjectMapper? = null): JsonRowMapper<T>  {
    return JsonRowMapper<T>(this, specificMapper)
}

fun <T : Any> TypeReference<T>.toRowMapper(specificMapper: ObjectMapper? = null): JsonRowMapper<T>  {
    return JsonRowMapper<T>(this, specificMapper)
}
