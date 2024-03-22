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
 * A class representing a PostgreSQL Boolean type with encapsulation of nullable Kotlin Boolean.
 * Extends PGAbstractObject to handle nullable Boolean type specifically for PostgreSQL usage.
 *
 * @property valueObject The nullable Boolean value to represent in PostgreSQL.
 * @constructor Creates a [PGBoolean] instance encapsulating the given value.
 */
open class PGBoolean(valueObject: Boolean?) : PGAbstractObject<Boolean?>(valueObject, "boolean") {
    /**
     * Transform the nullable integer to a string representation.
     * Returns null if the value is null, else returns the string of the integer value.
     *
     * @return The string representation of the integer value.
     */
    override fun mapValue(obj: Boolean?): String? {
        return obj?.toString()
    }
}

/**
 * Extension function to transform a nullable Boolean to a [PGBoolean] instance.
 *
 * @receiver The nullable Boolean.
 * @return The resulting transformed [PGBoolean] instance.
 */
fun Boolean?.toPGBoolean(): PGBoolean {
    return PGBoolean(this)
}
