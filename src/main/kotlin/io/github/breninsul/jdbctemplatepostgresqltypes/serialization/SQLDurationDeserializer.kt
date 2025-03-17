package io.github.breninsul.jdbctemplatepostgresqltypes.serialization

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.time.Duration
import java.util.regex.Pattern

/**
 * A deserializer for converting SQL interval format representations into Java's `Duration` instance.
 *
 * This class extends `JsonDeserializer` for handling deserialization of SQL interval strings typically
 * found in database interactions. The expected format matches SQL standard interval representations,
 * supporting years, months, days, hours, minutes, seconds, and fractional seconds.
 *
 * The deserialization process involves parsing the given SQL interval string, extracting its
 * components (e.g., years, months, etc.), and converting them into a `Duration` object.
 *
 * This class provides the following functionalities:
 * - Custom regex pattern matching for SQL interval formats.
 * - Internal helper method for safely parsing numeric components of the string.
 * - Extendable behavior through protected members for alternative interval patterns.
 *
 * Key features:
 * - Supports interval formats like `1 year 2 mons 3 days 04:05:06.123`.
 * - Handles missing components (e.g., if certain parts like months or seconds are omitted).
 * - Produces `Duration` instances in terms of seconds and milliseconds.
 */
open class SQLDurationDeserializer : JsonDeserializer<Duration>() {
    protected open fun getIntervalPattern(): Pattern = INTERVAL_PATTERN
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Duration {
        val interval = p.text
        val matcher = getIntervalPattern().matcher(interval)

        if (!matcher.matches()) {
            throw IOException("Invalid SQL interval format: $interval")
        }

        val years = matcher.group(1).parseInt()
        val months = matcher.group(2).parseInt()
        val days = matcher.group(3).parseInt()
        val hours = matcher.group(4).parseInt()
        val minutes = matcher.group(5).parseInt()
        val seconds = matcher.group(6).parseInt()
        val millis = matcher.group(7).parseInt()/1000

        val yearsDays = years * 365L
        val monthDays = months * 30L
        val totalDays = yearsDays + monthDays + days
        val totalDaysSeconds = totalDays * 86400L
        val hoursSeconds = hours * 3600L
        val minutesSeconds = minutes * 60L
        val totalSeconds = totalDaysSeconds + hoursSeconds + minutesSeconds + seconds
        return Duration.ofSeconds(totalSeconds).plusMillis(millis.toLong())
    }

    protected open fun String?.parseInt(): Int {
        return this?.toInt() ?: 0
    }
    companion object{
        protected val INTERVAL_PATTERN: Pattern = Pattern.compile("""(?:(\d+) years? ?)?(?:(\d+) mons? ?)?(?:(\d+) days? ?)?(?:(\d+):(\d+):(\d+)(?:\.(\d+))?)?""")
    }
}