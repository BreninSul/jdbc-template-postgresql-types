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

import java.math.BigInteger

/**
 * Class representing PostgreSQL's BigInteger type for use in JDBC operations.
 *
 * @property valueObject The BigInteger value stored in the database.
 * @constructor Creates a new PGBigInteger object.
 */
open class PGBigInteger(valueObject: BigInteger?) : PGAbstractObject<BigInteger?>(valueObject, "numeric") {
    /**
     * Map the BigInteger value to a String
     * @param obj BigInteger? to be mapped.
     * @return A String representation of the BigInteger value.
     */
    override fun mapValue(obj: BigInteger?): String? {
        return obj?.toString(10)
    }
}

/**
 * Extension function to convert a BigInteger? to a PGBigInteger.
 *
 * @return A new PGBigInteger instance.
 */
fun BigInteger?.toPGBigInteger(): PGBigInteger {
    return PGBigInteger(this)
}
