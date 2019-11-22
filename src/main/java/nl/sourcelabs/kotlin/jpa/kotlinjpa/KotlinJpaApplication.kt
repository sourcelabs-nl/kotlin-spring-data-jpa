package nl.sourcelabs.kotlin.jpa.kotlinjpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@SpringBootApplication
class KotlinJpaApplication

@Entity
data class MyEntity(@Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null, val prop1: String)

interface MyRepository : JpaRepository<MyEntity, Long>

fun main(args: Array<String>) {
    runApplication<KotlinJpaApplication>(*args)
}

