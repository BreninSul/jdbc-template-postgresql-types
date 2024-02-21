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

import org.postgresql.util.PGobject

/**
 * PGAbstractObject is an abstract base type for managing PostgreSQL complex types
 *
 * @param T the type of the value that the PGObject represents.
 * @param valueObject the actual value of PostgreSQL object.
 * @param pgTypeName the PostgreSQL type name.
 *
 * @property valueObject the actual value of PostgreSQL object.
 * @property pgTypeName the PostgreSQL type name.
 */
abstract class PGAbstractObject<T : Any?>(protected val valueObject: T, protected val pgTypeName: String) : PGobject() {
    init {
        type = pgTypeName
        value = mapValue(valueObject)
    }

    /**
     * mapValue is a function to transform the valueObject of type T to a String format used in PostgreSQL.
     *
     * @param obj The object which is to be transformed to String
     * @return the String representation of obj for PostgreSQL.
     */
    protected abstract fun mapValue(obj: T): String?
}
