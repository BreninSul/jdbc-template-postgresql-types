package io.github.breninsul.jdbctemplatepostgresqltypes.type.date

import io.github.breninsul.jdbctemplatepostgresqltypes.type.PGAbstractObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * PGLocalDateTime is a Kotlin class that represents a Postgresql timestamp type.
 * It holds a [LocalDateTime] instance and provides formatting to a PostgreSQL compatible string representation.
 *
 * @property rawVal holds the original LocalDateTime value
 * @property localDateFormatPattern sets the date format pattern to be used to format [rawVal] to PostgreSQL compatible string
 * @property dateTimeFormatter provides DateTimeFormatter based on the specified [localDateFormatPattern]
 *
 * @constructor Create new PGLocalDateTime with given [rawVal]
 */
open class PGLocalDateTime(rawVal: LocalDateTime?) : PGAbstractObject<LocalDateTime?>(rawVal, "timestamp") {

    /**
     * The format pattern used for the local date.
     */
    protected open val localDateFormatPattern: String get() = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"

    /**
     * A DateTimeFormatter instance created using the local date format pattern.
     */
    protected open val dateTimeFormatter: DateTimeFormatter get() = DateTimeFormatter.ofPattern(localDateFormatPattern)

    /**
     * Map a LocalDateTime instance to its string representation.
     * @param obj a LocalDateTime instance.
     * @return String representation of the LocalDateTime instance.
     */
    override fun mapValue(obj: LocalDateTime?): String? {
        if (obj == null) {
            return null
        }
        return obj.format(dateTimeFormatter)
    }
}

/**
 * Converts a LocalDateTime instance to a PGLocalDateTime instance.
 *
 * @return PGLocalDateTime - The converted LocalDateTime instance.
 */
fun LocalDateTime?.toPGLocalDate(): PGLocalDateTime {
    return PGLocalDateTime(this)
}
