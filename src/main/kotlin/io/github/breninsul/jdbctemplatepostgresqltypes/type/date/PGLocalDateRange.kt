package io.github.breninsul.jdbctemplatepostgresqltypes.type.date

import io.github.breninsul.jdbctemplatepostgresqltypes.type.PGAbstractObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * This class is responsible for managing PostgreSQL date range type with Kotlin's ClosedRange<LocalDate> class.
 * @property rawVal is the initial range value passed to it.
 * @property dateTimeFormatter gets the DateTimeFormatter to format date to ISO 8601 format.
 */
open class PGLocalDateRange(rawVal: ClosedRange<LocalDate>?) :
    PGAbstractObject<ClosedRange<LocalDate>?>(rawVal, "daterange") {

    /**
     * The dateTimeFormatter is a protected property for the PGLocalDateRange class.
     * It is used to specify the date format as ISO_LOCAL_DATE while converting the local date.
     *
     * @return DateTimeFormatter Returns the DateTimeFormatter in ISO_LOCAL_DATE format.
     */
    protected open val dateTimeFormatter: DateTimeFormatter get() = DateTimeFormatter.ISO_LOCAL_DATE

    /**
     * The dateTimeFormatter is the date formatting tool for this class.
     *
     * It returns or sets the [DateTimeFormatter] in the ISO_LOCAL_DATE format. This is used to format the LocalDate object
     * in the ISO_LOCAL_DATE format when calling the [mapValue] method.
     *
     * @return [DateTimeFormatter] Returns a [DateTimeFormatter] that is set to the ISO_LOCAL_DATE format.
     */
    override fun mapValue(obj: ClosedRange<LocalDate>?): String? {
        if (obj == null) {
            return null
        }
        val start = if (obj.start == LocalDate.MIN) "" else obj.start.format(dateTimeFormatter)
        val end = if (obj.endInclusive == LocalDate.MAX) "" else obj.endInclusive.format(dateTimeFormatter)
        return "[${start},${end}]"
    }
}

/**
 * This is an extension function for ClosedRange<LocalDate>? type to convert it to PGLocalDateRange type.
 * @return PGLocalDateRange object that corresponds to the calling ClosedRange<LocalDate>? object.
 */
fun ClosedRange<LocalDate>?.toPGLocalDateRange(): PGLocalDateRange {
    return PGLocalDateRange(this)
}
