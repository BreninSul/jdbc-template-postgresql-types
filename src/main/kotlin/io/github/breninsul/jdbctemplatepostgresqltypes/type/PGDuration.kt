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

import java.time.Duration

/**
 * This class represents a PostgreSQL interval type.
 *
 * @property valueObject The Duration object represented as a PostgreSQL interval.
 */
open class PGDuration(valueObject: Duration?) : PGAbstractObject<Duration?>(valueObject, "interval") {
    /**
     * This method maps a Duration object to a string representation suitable for PostgreSQL.
     * The duration is mapped to a string in millis.
     *
     * @param obj The Duration object to map.
     * @return The string representation of the duration in millis or null if the Duration object is null.
     */
    override fun mapValue(obj: Duration?): String? {
        return obj?.toMillis()?.let { "${it}ms" }
    }
}

/**
 * Extension function to map a nullable Duration object to a PGDuration object.
 *
 * @receiver The nullable Duration object.
 * @return The PGDuration object.
 */
fun Duration?.PGDuration(): PGDuration {
    return PGDuration(this)
}
