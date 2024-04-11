package io.github.breninsul.jdbctemplatepostgresqltypes.type.date

import io.github.breninsul.jdbctemplatepostgresqltypes.type.PGAbstractObject
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * PGAbstractObject subclass to handle Postgres LocalTime type.
 * @param rawVal is the LocalTime object
 */
open class PGLocalTime(rawVal: LocalTime?) : PGAbstractObject<LocalTime?>(rawVal, "time") {
    /**
     * The pattern string to be used to format/postgres compliant LocalTime.
     */
    protected open val localTimeFormatPattern: String get() = "HH:mm:ss.SSSSSSS"

    /**
     * The DateTimeFormatter instance to be used for formatting LocalTime object.
     */
    protected open val dateTimeFormatter: DateTimeFormatter
        get() =
            DateTimeFormatter.ofPattern(localTimeFormatPattern)

    /**
     * @see PGAbstractObject.mapValue
     * Overrides the base implementation to generate a String representing
     * the postgres compliant LocalTime from a LocalTime object.
     * @param obj the LocalTime object to format.
     * @return formatted string if LocalTime is not null, null otherwise.
     */
    override fun mapValue(obj: LocalTime?): String? {
        if (obj == null) {
            return null
        }
        return obj.format(dateTimeFormatter)
    }
}

/**
 * Kotlin Extension function on LocalTime to convert a LocalTime
 * to a PGLocalTime which is postgres compliant.
 * @return Returns corresponding PGLocalTime object.
 */
fun LocalTime?.toPGLocalTime(): PGLocalTime {
    return PGLocalTime(this)
}
