package nl.sourcelabs.kotlin.jpa.kotlinjpa

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame

@DataJpaTest
internal class KotlinJpaApplicationTests {

    @Autowired
    lateinit var myRepository: MyRepository

    @Test
    fun `invoking save after copy() should update and not insert new`() {
        val myEntity = MyEntity(prop1 = "Hello world")
        val myEntitySaved  = myRepository.save(myEntity)
        assertSame(myEntity, myEntitySaved)

        val myEntityCopy = myEntitySaved.copy(prop1 = ".... the world")
        assertNotSame(myEntity, myEntityCopy)

        // This one is SUPER tricky, because when saving this copy we also update the myEntity reference from above.
        // Because the id is the same, JPA updates myEntity associated with the session/unit of work.
        val myEntityCopySaved = myRepository.save(myEntityCopy)

        assertNotSame(myEntityCopy, myEntityCopySaved) // <--------- They are NOT the same object reference!!!!!
        assertEquals(myEntitySaved.id, myEntityCopySaved.id)
    }
}