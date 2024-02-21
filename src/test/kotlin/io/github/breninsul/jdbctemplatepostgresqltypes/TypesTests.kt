package io.github.breninsul.jdbctemplatepostgresqltypes

import io.github.breninsul.jdbctemplatepostgresqltypes.configuration.JdbcTypesDefaultMapperHolderAutoConfiguration
import io.github.breninsul.jdbctemplatepostgresqltypes.type.PGJsonb
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.testcontainers.containers.PostgreSQLContainer
import java.util.logging.Logger

@EnableTransactionManagement
@ExtendWith(SpringExtension::class)
@Import(DataSourceAutoConfiguration::class,TestConfiguration::class, JdbcTypesDefaultMapperHolderAutoConfiguration::class)

class JdbcTemplatePostgresqlTypesApplicationTests {
    protected val logger = Logger.getLogger(this.javaClass.name)

    @Autowired
    lateinit var jdbcClient: JdbcClient

    data class TestJsonb(val testOne:String,val testTwo:String)
    @Test
    fun testJsonb() {
        jdbcClient.sql("create table test_jsonb(id int,tst jsonb)")
            .update()
        val tst = PGJsonb(TestJsonb("t1", "t2"))
        val tstNull = PGJsonb(null)
        jdbcClient.sql("insert into test_jsonb(id,tst) values (1,:tst)")
            .param("tst", tst)
            .update()
        val idByValue=jdbcClient.sql("select id from test_jsonb where true and case when :tst is null then true else tst=:tst end ")
            .param("tst",tst)
            .query(Int::class.java)
            .single()
        Assertions.assertEquals(1,idByValue)
        val idByNull=jdbcClient.sql("select id from test_jsonb where true and case when :tst is null then true else tst=:tst end ")
            .param("tst",tstNull)
            .query(Int::class.java)
            .single()
        Assertions.assertEquals(1,idByNull)
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

