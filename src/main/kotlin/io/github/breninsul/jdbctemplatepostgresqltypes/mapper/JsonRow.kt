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
import io.github.breninsul.jdbctemplatepostgresqltypes.configuration.PGDefaultMapperHolder

/**
 * Represents the row in a JSON format
 *
 * @property name The name of the row
 * @property javaType The type of the row data
 * @property rawSqlType Flag to determine if the row data is of raw SQL type or not
 * @constructor Create a new JSON row
 *
 * @param name The name of the row
 * @param javaType The type of the row data
 * @param rawSqlType Flag to determine if the row data is of raw SQL type or not
 */
open class JsonRow(val name: String? = null, val javaType: JavaType, val rawSqlType: Boolean = false) {
    constructor(name: String? = null, javaClass: Class<*>, rawSqlType: Boolean = false) : this(name, javaClass.toJavaType(), rawSqlType)

    constructor(name: String? = null, typeRef: TypeReference<*>, rawSqlType: Boolean = false) : this(name, typeRef.toJavaType(), rawSqlType)
}

/**
 * Extension function to convert a Class instance to its corresponding JavaType representation.
 *
 * @return The JavaType representation of the current class.
 */
fun Class<*>.toJavaType(): JavaType {
    return PGDefaultMapperHolder.getMapper().constructType(this)
}

/**
 * Extension function to convert a TypeReference instance to its corresponding JavaType representation.
 *
 * @return The JavaType representation of the current class.
 */
fun TypeReference<*>.toJavaType(): JavaType {
    return PGDefaultMapperHolder.getMapper().constructType(this)
}
