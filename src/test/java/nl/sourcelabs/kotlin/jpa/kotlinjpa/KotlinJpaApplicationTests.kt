package nl.sourcelabs.kotlin.jpa.kotlinjpa

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class KotlinJpaApplicationTests {

    @Autowired
    lateinit var myRepository: MyRepository

    @Test
    fun test() {
        val myEntity0 = myRepository.save(MyEntity(prop1 = "Hello world"))

        val myEntity1 = myEntity0.copy(prop1 = "F#ck the world")
        println(myEntity1)

        val myEntity2 = myRepository.save(myEntity1)
        println(myEntity2)
    }
}