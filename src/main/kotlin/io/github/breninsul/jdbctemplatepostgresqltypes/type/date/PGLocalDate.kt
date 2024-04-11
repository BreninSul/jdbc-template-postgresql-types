package io.github.breninsul.jdbctemplatepostgresqltypes.type.date

import io.github.breninsul.jdbctemplatepostgresqltypes.type.PGAbstractObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * This class is responsible for holding and formatting LocalDate objects to suit
 * PostgreSQL "date" type.
 *
 * @property rawVal Nullable LocalDate that is to be handled and formatted by this class.
 */
open class PGLocalDate(rawVal: LocalDate?) : PGAbstractObject<LocalDate?>(rawVal, "date") {
    /**
     * The date formatter used to format the LocalDate objects to string.
     * By default it uses the ISO_LOCAL_DATE format.
     */
    protected open val dateTimeFormatter: DateTimeFormatter get() = DateTimeFormatter.ISO_LOCAL_DATE

    /**
     * This function accepts a nullable LocalDate and returns a formatted string
     * suitable for PostgreSQL "date" type. If the given value is null, the function returns null.
     *
     * @param obj Nullable LocalDate that needs to be formatted.
     * @return A String representation of the given LocalDate or null if the original value is null.
     */
    override fun mapValue(obj: LocalDate?): String? {
        if (obj == null) {
            return null
        }
        return obj.format(dateTimeFormatter)
    }
}

/**
 * Extension function to the LocalDate class to convert LocalDate objects into PGLocalDate.
 *
 * @receiver Nullable LocalDate to be converted into PGLocalDate.
 * @return A PGLocalDate object containing the receiver LocalDate.
 */
fun LocalDate?.toPGLocalDate(): PGLocalDate {
    return PGLocalDate(this)
}

