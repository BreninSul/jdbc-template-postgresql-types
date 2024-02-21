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

import java.util.UUID


/**
 * Custom class for handling UUID type in PostgreSQL.
 *
 * @property valueObject The UUID to be handled.
 */
open class PGUuid(valueObject: UUID?) : PGAbstractObject<UUID?>(valueObject, "uuid") {

    /**
     * Maps the UUID value to a String value.
     *
     * @param obj The UUID object.
     * @return The string representation of the UUID.
     */
    override fun mapValue(obj: UUID?): String? {
        return obj?.toString()
    }
}

/**
 * Converts a nullable UUID to a PGUuid object.
 *
 * @return The PGUuid representation of the UUID.
 */
fun UUID?.toPGUuid(): PGUuid {
    return PGUuid(this)
}

/**
 * Converts a nullable String to a PGUuid object.
 *
 * @return The PGUuid representation of the UUID.
 */
fun String?.toPGUuid(): PGUuid {
    return PGUuid(this?.let { UUID.fromString(it)})
}