package nl.sourcelabs.kotlin.jpa.kotlinjpa

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class KotlinJpaApplicationTests {

    @Autowired
    lateinit var myRepository: MyRepository

    @Test
    fun `invoking save after copy() should update and not insert new`() {
        val myEntity = myRepository.save(MyEntity(prop1 = "Hello world"))
        println(myEntity)

        val myEntityCopy = myRepository.save(myEntity.copy(prop1 = "F#ck the world"))
        println(myEntityCopy)
    }
}