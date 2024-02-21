package io.github.breninsul.jdbctemplatepostgresqltypes.type

import java.math.BigDecimal

/**
 * Defines a class that represents a BigDecimal type that is specifically formatted for PostgreSQL databases.
 * @property valueObject The BigDecimal object that this class wraps around.
 */
open class PGBigDecimal(valueObject: BigDecimal?) : PGAbstractObject<BigDecimal?>(valueObject, "numeric") {
    /**
     * Returns the plain string representation of the BigDecimal.
     * @param obj The BigDecimal object to be converted to a string.
     * @return The plain string representation of the BigDecimal, or null if the provided BigDecimal is null.
     */
    override fun mapValue(obj: BigDecimal?): String? {
        return obj?.toPlainString()
    }
}

/**
 * Extension function to the BigDecimal class. It converts the BigDecimal to a PGBigDecimal.
 * @return A PGBigDecimal object with the same numerical value.
 */
fun BigDecimal?.toPGBigDecimal(): PGBigDecimal {
    return PGBigDecimal(this)
}