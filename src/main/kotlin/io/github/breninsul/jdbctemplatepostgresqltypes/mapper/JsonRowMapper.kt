package io.github.breninsul.jdbctemplatepostgresqltypes.mapper

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.breninsul.jdbctemplatepostgresqltypes.configuration.PGDefaultMapperHolder
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

open class JsonRowsMapper<T : Any>(protected val types: List<JsonRow>, protected val specificMapper: ObjectMapper? = null,val isOneRow:Boolean=false) : RowMapper<T> {
    constructor(row:JsonRow,specificMapper: ObjectMapper?=null):this(listOf(row),specificMapper,true)
    constructor(javaClass:Class<*>,specificMapper: ObjectMapper?=null):this(JsonRow(null,javaClass),specificMapper)

    open class JsonOrderedRow(val id: Int, val name: String?, val javaType: JavaType,val rawSqlType:Boolean)

    protected open val orderedTypes = types.mapIndexed { i, v -> JsonOrderedRow(i+1, v.name, v.javaType,v.rawSqlType) }


    override fun mapRow(
        rs: ResultSet,
        rowNum: Int,
    ): T? {
        val mapper = getMapper()
        if (isOneRow){
            val type=orderedTypes.first()
           return mapOneType(type, rs, mapper) as T?
        }
       return orderedTypes.map {
            val key= it.name ?: it.id as Any
            return@map key to mapOneType(it, rs, mapper)
        }.toMap() as T?
    }

    protected open fun mapOneType(
        type: JsonOrderedRow,
        rs: ResultSet,
        mapper: ObjectMapper
    ):Any? {
        if (type.name != null) {
            if (type.rawSqlType) {
               return rs.getObject(type.name, type.javaType.rawClass)
            } else {
                return rs.getString(type.name)?.let {   mapper.readValue(it, type.javaType)}
            }
        } else {
            if (type.rawSqlType) {
                return  rs.getObject(type.id, type.javaType.rawClass)
            } else {
                return rs.getString(type.id)?.let {   mapper.readValue(it, type.javaType)}
            }
        }
    }

    protected open fun getMapper() = specificMapper ?: PGDefaultMapperHolder.getMapper()


}