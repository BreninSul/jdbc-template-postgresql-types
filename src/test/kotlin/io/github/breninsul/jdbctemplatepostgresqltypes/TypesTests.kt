package io.github.breninsul.jdbctemplatepostgresqltypes

import io.github.breninsul.jdbctemplatepostgresqltypes.configuration.JdbcTypesDefaultMapperHolderAutoConfiguration
import io.github.breninsul.jdbctemplatepostgresqltypes.mapper.JsonRow
import io.github.breninsul.jdbctemplatepostgresqltypes.mapper.JsonRowMapper
import io.github.breninsul.jdbctemplatepostgresqltypes.mapper.toRowMapper
import io.github.breninsul.jdbctemplatepostgresqltypes.type.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.testcontainers.containers.PostgreSQLContainer
import java.util.logging.Logger

@EnableTransactionManagement
@ExtendWith(SpringExtension::class)
@Import(DataSourceAutoConfiguration::class, TestConfiguration::class, JdbcTypesDefaultMapperHolderAutoConfiguration::class)
class TypesTests {
    protected val logger = Logger.getLogger(this.javaClass.name)

    @Autowired
    lateinit var jdbcClient: JdbcClient

    data class TestJson(val testOne: String, val testTwo: String)

    @Test
    fun testJsonb() {
        jdbcClient.sql("create table test_jsonb(id int,tst jsonb)")
            .update()
        val testJson = TestJson("t1", "t2")
        val tst = PGJsonb(testJson)
        val tstNull = PGJsonb(null)
        jdbcClient.sql("insert into test_jsonb(id,tst) values (1,:tst)")
            .param("tst", tst)
            .update()
        val idByValue =
            jdbcClient.sql("select id from test_jsonb where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", testJson.toPGJsonb())
                .query(Int::class.java)
                .single()
        Assertions.assertEquals(1, idByValue)
        val idByNull =
            jdbcClient.sql("select id from test_jsonb where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", tstNull)
                .query(Int::class.java)
                .single()
        Assertions.assertEquals(1, idByNull)
    }

    @Test
    fun testJson() {
        jdbcClient.sql("create table test_json(id int,tst json)")
            .update()
        val tst = PGJson(TestJson("t1", "t2"))
        val tstNull = PGJson(null)
        val updated =
            jdbcClient.sql("insert into test_json(id,tst) values (1,:tst)")
                .param("tst", TestJson("t1", "t2").toPGJsonb())
                .update()
        Assertions.assertEquals(1, updated)
        val nullUpdated =
            jdbcClient.sql("insert into test_json(id,tst) values (2,:tst)")
                .param("tst", tstNull)
                .update()
        Assertions.assertEquals(1, nullUpdated)
    }

    @Test
    fun testArray() {
        jdbcClient.sql("create table test_text_array(id int,tst text[])")
            .update()
        val tst = listOf("One", "Second")
        val updated =
            jdbcClient.sql("insert into test_text_array(id,tst) values (1,:tst)")
                .param("tst", tst.toPGTextArray())
                .update()
        Assertions.assertEquals(1, updated)
        val selectedId =
            jdbcClient.sql("select id from test_text_array where tst=:tst")
                .param("tst", tst.toPGTextArray())
                .query(Int::class.java)
                .single()
        Assertions.assertEquals(1, selectedId)
        val selected =
            jdbcClient.sql("select to_jsonb(tst) from test_text_array where tst=:tst")
                .param("tst", tst.toPGTextArray())
                .query(String::class.java)
                .single()
        Assertions.assertEquals("[\"One\", \"Second\"]", selected)
    }

    enum class TestEnum { ONE, TWO }

    @Test
    fun testEnum() {
        jdbcClient.sql(" create type tst_enum as enum ('ONE','TWO')")
            .update()
        jdbcClient.sql("create table test_en(id int,tst tst_enum)")
            .update()
        val tstNull = PGEnum(null, "tst_enum")
        val updated =
            jdbcClient.sql("insert into test_en(id,tst) values (1,:tst)")
                .param("tst", TestEnum.ONE.toPGEnum("tst_enum"))
                .update()
        Assertions.assertEquals(1, updated)
        val nullEnum: TestEnum? = null
        val nullUpdated =
            jdbcClient.sql("insert into test_en(id,tst) values (2,:tst)")
                .param("tst", nullEnum.toPGEnum("tst_enum"))
                .update()
        Assertions.assertEquals(1, nullUpdated)

        val idByValue =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", TestEnum.ONE.toPGEnum("tst_enum"))
                .query(Int::class.java)
                .single()
        Assertions.assertEquals(1, idByValue)

        val idsByNullSize =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", tstNull)
                .query(Int::class.java)
                .list().size
        Assertions.assertEquals(2, idsByNullSize)
    }

    @Test
    fun testJsonbMapper() {
        jdbcClient.sql("create table test_jsonb(id int,tst1 jsonb,tst2 jsonb)")
            .update()
        val testObject1 = TestJson("t1", "t2")
        val testObject2 = TestJson("1", "2")

        val tst1 = PGJsonb(testObject1)
        val tst2 = PGJsonb(testObject2)

        jdbcClient.sql("insert into test_jsonb(id,tst1,tst2) values (1,:tst1,:tst2)")
            .param("tst1", tst1)
            .param("tst2", tst2)
            .update()
        val oneJson =
            jdbcClient.sql("select tst1 from test_jsonb where true and case when :tst1 is null then true else tst1=:tst1 end ")
                .param("tst1", tst1)
                .query(TestJson::class.toRowMapper())
                .single()
        Assertions.assertEquals(testObject1, oneJson)
        val twoJsonByName =
            jdbcClient.sql("select tst1 as tst1,tst2 as tst2,id as id from test_jsonb where true and case when :tst1 is null then true else tst1=:tst1 end ")
                .param("tst1", tst1)
                .query(JsonRowMapper(listOf(JsonRow("tst1", TestJson::class.java), JsonRow("tst2", TestJson::class.java), JsonRow("id", Int::class.java, true))))
                .single() as Map<String, Any?>
        Assertions.assertEquals(twoJsonByName["tst1"], oneJson)
        Assertions.assertEquals(twoJsonByName["tst2"], testObject2)
        Assertions.assertEquals(twoJsonByName["id"], 1)
        val twoJsonById =
            jdbcClient.sql("select tst1 as tst1,tst2 as tst2,id as id from test_jsonb where true and case when :tst1 is null then true else tst1=:tst1 end ")
                .param("tst1", tst1)
                .query(JsonRowMapper(listOf(JsonRow(null, TestJson::class.java), JsonRow(null, TestJson::class.java), JsonRow(null, Int::class.java, true))))
                .single() as Map<Int, Any?>
        Assertions.assertEquals(twoJsonById[1], oneJson)
        Assertions.assertEquals(twoJsonById[2], testObject2)
        Assertions.assertEquals(twoJsonById[3], 1)
    }

    companion object {
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16.1-alpine3.19")

        /**
         * Configures dynamic properties for the test environment.
         */
        @DynamicPropertySource
        @JvmStatic
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { postgres.jdbcUrl }
            registry.add("spring.datasource.username") { postgres.username }
            registry.add("spring.datasource.password") { postgres.password }
        }

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            postgres.start()
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            postgres.stop()
        }
    }
}
