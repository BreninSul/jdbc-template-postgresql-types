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

/**
 * A class that represents a PostgreSQL enumeration as an object in Java.
 *
 * @property valueObject The enumerated object's value.
 * @property pgTypeName The name of the PostgreSQL enumeration type.
 * @constructor Creates a new instance of [PGEnum].
 */
open class PGEnum(valueObject: Enum<*>?, pgTypeName: String) : PGAbstractObject<Enum<*>?>(valueObject, pgTypeName) {
    /**
     * Maps an enumerated object to a string.
     *
     * @param obj The enumerated object.
     * @return The string representation of the enumerated object's value.
     */
    override fun mapValue(obj: Enum<*>?): String? {
        return obj?.name
    }

    /**
     * Converts this [PGEnum] instance into an enumerated object of a specific enum class.
     *
     * @return The enumerated object of the given enum class that matches this [PGEnum] instance's value, or `null`
     * if no such enumerated object exists.
     */
    inline fun <reified T : Enum<T>> toEnum(): T? {
        return T::class.java.enumConstants.firstOrNull { it.name.equals(getValue(), true) }
    }
}

/**
 * Converts an optional enumerated object into a [PGEnum] instance.
 *
 * @property pgTypeName The name of the PostgreSQL enumeration type.
 * @return A [PGEnum] instance representing the optional enumerated object.
 */
fun Enum<*>?.toPGEnum(pgTypeName: String): PGEnum {
    return PGEnum(this, pgTypeName)
}
