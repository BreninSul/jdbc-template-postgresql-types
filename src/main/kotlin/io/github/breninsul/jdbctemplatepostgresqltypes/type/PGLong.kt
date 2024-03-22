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
 * A class that represents the PostgreSQL `bigint` type.
 * @property valueObject A nullable Long value that is the object of this type.
 * @constructor Creates an instance of PGLong.
 */
open class PGLong(valueObject: Long?) : PGAbstractObject<Long?>(valueObject, "bigint") {
    /**
     * Maps the given Long object to its string representation.
     * @param obj A nullable Long object.
     * @return A nullable string representation of obj.
     */
    override fun mapValue(obj: Long?): String? {
        return obj?.toString(10)
    }
}

/**
 * Extension function to convert a Long object to PGLong.
 * @receiver A nullable Long.
 * @return An instance of PGLong with the receiver as its value object.
 */
fun Long?.toPGLong(): PGLong {
    return PGLong(this)
}
