package nl.sourcelabs.kotlin.jpa.spring_data_jpa

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame

@Entity
data class MyEntity(@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null, val prop1: String)

interface MyRepository : JpaRepository<MyEntity, Long>

@DataJpaTest
class KotlinSpringDataJpaTests {

    @Autowired
    lateinit var myRepository: MyRepository

    @Test
    fun `JPA and immutable data classes dont play nicely`() {
        val myEntity = MyEntity(prop1 = "Hello world")
        val myEntitySaved = myRepository.save(myEntity)
        assertSame(myEntity, myEntitySaved)

        // Create a copy of myEntity (new instance).
        val myEntityCopy = myEntitySaved.copy(prop1 = ".... the world")
        assertNotSame(myEntity, myEntityCopy)

        // This one is SUPER tricky, because when saving this copy we also update the myEntity reference from above.
        // Because the id is the same, JPA updates myEntity associated with the session/unit of work.
        val myEntityCopySaved = myRepository.save(myEntityCopy)

        assertNotSame(myEntityCopy, myEntityCopySaved) // <--------- They are NOT the same object reference!!!!!
        assertEquals(myEntitySaved.id, myEntityCopySaved.id)
    }
}