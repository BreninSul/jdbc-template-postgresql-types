package io.github.breninsul.jdbctemplatepostgresqltypes

import io.github.breninsul.jdbctemplatepostgresqltypes.configuration.JdbcTypesDefaultMapperHolderAutoConfiguration
import io.github.breninsul.jdbctemplatepostgresqltypes.type.date.*
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
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.util.logging.Logger

@EnableTransactionManagement
@ExtendWith(SpringExtension::class)
@Import(DataSourceAutoConfiguration::class, TestConfiguration::class, JdbcTypesDefaultMapperHolderAutoConfiguration::class)
class TimeTypesTests {
    protected val logger = Logger.getLogger(this.javaClass.name)

    @Autowired
    lateinit var jdbcClient: JdbcClient

    @Test
    fun testDuration() {
        jdbcClient.sql("create table test_en(id int,tst interval)")
            .update()
        val dur=Duration.ofDays(1)
        val updated =
            jdbcClient.sql("insert into test_en(id,tst) values (1,:tst)")
                .param("tst", dur.toPGDuration())
                .update()
        Assertions.assertEquals(1, updated)
        val nullTime:Duration? = null
        val nullUpdated =
            jdbcClient.sql("insert into test_en(id,tst) values (2,:tst)")
                .param("tst", nullTime.toPGDuration())
                .update()
        Assertions.assertEquals(1, nullUpdated)

        val idByValue =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", dur.toPGDuration())
                .query(Int::class.java)
                .single()
        Assertions.assertEquals(1, idByValue)

        val idsByNullSize =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", nullTime.toPGDuration())
                .query(Int::class.java)
                .list().size
        Assertions.assertEquals(2, idsByNullSize)
    }

    @Test
    fun testLocalDateRange() {
        jdbcClient.sql("create table test_en(id int,tst daterange)")
            .update()
        val now=LocalDate.now()..LocalDate.now().plusDays(1)
        val updated =
            jdbcClient.sql("insert into test_en(id,tst) values (1,:tst)")
                .param("tst", now.toPGLocalDateRange())
                .update()
        Assertions.assertEquals(1, updated)
        val nullTime: ClosedRange<LocalDate>? = null
        val nullUpdated =
            jdbcClient.sql("insert into test_en(id,tst) values (2,:tst)")
                .param("tst", nullTime.toPGLocalDateRange())
                .update()
        Assertions.assertEquals(1, nullUpdated)

        val idByValue =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", now.toPGLocalDateRange())
                .query(Int::class.java)
                .single()
        Assertions.assertEquals(1, idByValue)

        val idsByNullSize =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", nullTime.toPGLocalDateRange())
                .query(Int::class.java)
                .list().size
        Assertions.assertEquals(2, idsByNullSize)
    }
    @Test
    fun testLocalTime() {
        jdbcClient.sql("create table test_en(id int,tst time)")
            .update()
        val now=LocalTime.now()
        val updated =
            jdbcClient.sql("insert into test_en(id,tst) values (1,:tst)")
                .param("tst", now.toPGLocalTime())
                .update()
        Assertions.assertEquals(1, updated)
        val nullTime: LocalTime? = null
        val nullUpdated =
            jdbcClient.sql("insert into test_en(id,tst) values (2,:tst)")
                .param("tst", nullTime.toPGLocalTime())
                .update()
        Assertions.assertEquals(1, nullUpdated)

        val idByValue =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", now.toPGLocalTime())
                .query(Int::class.java)
                .single()
        Assertions.assertEquals(1, idByValue)

        val idsByNullSize =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", nullTime.toPGLocalTime())
                .query(Int::class.java)
                .list().size
        Assertions.assertEquals(2, idsByNullSize)
    }

    @Test
    fun testZonedDateTime() {
        jdbcClient.sql("create table test_en(id int,tst timestamptz)")
            .update()
        val now=ZonedDateTime.now()
        val updated =
            jdbcClient.sql("insert into test_en(id,tst) values (1,:tst)")
                .param("tst", now.toPGZonedDateTime())
                .update()
        Assertions.assertEquals(1, updated)
        val nullTime: ZonedDateTime? = null
        val nullUpdated =
            jdbcClient.sql("insert into test_en(id,tst) values (2,:tst)")
                .param("tst", nullTime.toPGZonedDateTime())
                .update()
        Assertions.assertEquals(1, nullUpdated)

        val idByValue =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", now.toPGZonedDateTime())
                .query(Int::class.java)
                .single()
        Assertions.assertEquals(1, idByValue)

        val idsByNullSize =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", nullTime.toPGZonedDateTime())
                .query(Int::class.java)
                .list().size
        Assertions.assertEquals(2, idsByNullSize)
    }

    @Test
    fun testLocalDateTime() {
        jdbcClient.sql("create table test_en(id int,tst timestamp)")
            .update()
        val now=LocalDateTime.now()
        val updated =
            jdbcClient.sql("insert into test_en(id,tst) values (1,:tst)")
                .param("tst", now.toPGLocalDate())
                .update()
        Assertions.assertEquals(1, updated)
        val nullTime: LocalDateTime? = null
        val nullUpdated =
            jdbcClient.sql("insert into test_en(id,tst) values (2,:tst)")
                .param("tst", nullTime.toPGLocalDate())
                .update()
        Assertions.assertEquals(1, nullUpdated)

        val idByValue =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", now.toPGLocalDate())
                .query(Int::class.java)
                .single()
        Assertions.assertEquals(1, idByValue)

        val idsByNullSize =
            jdbcClient.sql("select id from test_en where true and case when :tst is null then true else tst=:tst end ")
                .param("tst", nullTime.toPGLocalDate())
                .query(Int::class.java)
                .list().size
        Assertions.assertEquals(2, idsByNullSize)
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
