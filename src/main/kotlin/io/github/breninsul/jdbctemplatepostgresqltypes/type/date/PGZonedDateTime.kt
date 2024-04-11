package io.github.breninsul.jdbctemplatepostgresqltypes.type.date

import io.github.breninsul.jdbctemplatepostgresqltypes.type.PGAbstractObject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * This open class PGZonedDateTime is a child of the abstract class PGAbstractObject,
 * and it represents a Postgres specific data type for ZonedDateTime.
 *
 * @property rawVal is the raw value of ZonedDateTime that needs to be manipulated.
 * @constructor creates a new instance of PGZonedDateTime.
 */
open class PGZonedDateTime(rawVal: ZonedDateTime?) : PGAbstractObject<ZonedDateTime?>(rawVal, "timestamptz") {

    /**
     * @property zonedDateFormatPattern is a String which defines the format in which the date and time should be represented.
     */
    protected open val zonedDateFormatPattern: String get() = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"

    /**
     * @property dateTimeFormatter is a DateTimeFormatter object which converts the date and time based on the provided pattern.
     */
    protected open val dateTimeFormatter: DateTimeFormatter get() = DateTimeFormatter.ofPattern(zonedDateFormatPattern)

    /**
     * This function takes a ZonedDateTime object and maps it to a string representation based on the dateTimeFormatter.
     *
     * @param obj The given ZonedDateTime object
     * @return A string representation of ZonedDateTime object or null if the given object is null.
     */
    override fun mapValue(obj: ZonedDateTime?): String? {
        if (obj == null) {
            return null
        }
        return obj.format(dateTimeFormatter)
    }
}

/**
 * This function is acting as an extension function for ZonedDateTime objects or its nullable forms.
 * It converts the ZonedDateTime or its nullable form into an instance of PGZonedDateTime.
 *
 * @receiver ZonedDateTime? The ZonedDateTime or its nullable form to be converted.
 * @return The converted instance of PGZonedDateTime.
 */
fun ZonedDateTime?.toPGZonedDateTime(): PGZonedDateTime {
    return PGZonedDateTime(this)
}
