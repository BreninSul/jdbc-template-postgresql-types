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
 * Represents a PostgreSQL JSON type.
 *
 * @property valueObject the value of the JSON type.
 * @property specificMapper a specific object mapper that can be used for JSON serialization/deserialization.
 */
open class PGJson(valueObject: Any?, specificMapper: ObjectMapper? = null) : PGAbstractJson(valueObject, "json", specificMapper)

/**
 * Extension function to convert any nullable value to a [PGJson] instance.
 *
 * @param specificMapper a specific object mapper that can be used for JSON serialization/deserialization.
 * @return a [PGJson] instance.
 */
fun Any?.toPGJson(specificMapper: ObjectMapper? = null): PGJson {
    return PGJson(this, specificMapper)
}
