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
 * Class to handle the operations of PostgreSQL array.
 *
 * @param T the type parameter, T should be of PGobject class
 * @param valueObject the list of valueObject of type T
 */
open class PGArray<T : PGobject>(valueObject: List<T>?) : PGAbstractObject<List<T>?>(valueObject, "${valueObject!!.first().type}[]") {

    /**
     * Map the list of 'T' objects to string
     *
     * @param obj list of objects
     * @return list of 'T' objects as string
     */
    override fun mapValue(obj: List<T>?): String? {
        val stringList = obj?.map { mapStringElement(it.value) }?.joinToString(",")
        return "{${stringList}}"
    }

    /**
     * Map a string element
     *
     * @param value the string to map
     * @return mapped string
     */
    private fun mapStringElement(value: String?): String {
        if (value.equals("null", true)) {
            return "\"null\""
        } else if (value == null) {
            return "null"
        } else {
            return "\"${value}\""
        }
    }
}

/**
 * Extension function to convert list of 'T' to PGArray
 *
 * @return PGArray of type 'T'
 */
fun <T:PGobject> List<T>?.toPGArray(): PGArray<T> {
    return PGArray(this)
}

/**
 * Extension function to convert list of String to PGArray
 *
 * @return PGArray of PGText
 */
fun  List<String>?.toPGTextArray(): PGArray<PGText> {
    return PGArray(this?.map { it.toPGText() })
}