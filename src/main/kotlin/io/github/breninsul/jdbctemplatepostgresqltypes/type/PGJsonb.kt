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

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * A class to handle JSON binary (jsonb) data type of PostgreSQL
 *
 * @property valueObject the underlying object to represent the JSON data.
 * @property specificMapper the specific [ObjectMapper] to be used. If null, a new instance will be created.
 */
open class PGJsonb(valueObject: Any?, specificMapper: ObjectMapper? = null) : PGAbstractJson(valueObject, "jsonb", specificMapper)

/**
 * Extension function to convert any nullable object into a [PGJsonb].
 *
 * @receiver the object to be converted.
 * @param specificMapper the specific [ObjectMapper] to be used. If null, a new instance will be created.
 * @return the [PGJsonb] instance.
 */
fun Any?.toPGJsonb(specificMapper: ObjectMapper? = null): PGJsonb {
    return PGJsonb(this, specificMapper)
}
