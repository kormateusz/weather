package pl.kormateusz.weather.domain.usecases

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateSearchQueryUseCaseTest {

    private val useCase = ValidateSearchQueryUseCase()

    @Test
    fun `should return true for valid city names`() {
        val validNames = listOf(
            "Warszawa",
            "New York",
            "Łódź",
            "Kraków",
            "Gdańsk",
            "San Francisco",
            "Bielsko-Biała"
        )

        validNames.forEach { name ->
            assertTrue("Expected valid for: $name", useCase.execute(name))
        }
    }

    @Test
    fun `should return false for invalid city names`() {
        val invalidNames = listOf(
            "12345",      // Same numbers
            "Warszawa!",  // Special characters
            "New@York",   // Special characters
            "Gdańsk_123", // Numbers and underscores
            "Berlin?",    // Special characters
        )

        invalidNames.forEach { name ->
            assertFalse("Expected invalid for: $name", useCase.execute(name))
        }
    }

    @Test
    fun `should allow spaces and dashes`() {
        val validNames = listOf(
            "San Francisco",
            "Bielsko-Biała",
            "Rio de Janeiro"
        )

        validNames.forEach { name ->
            assertTrue("Expected valid for: $name", useCase.execute(name))
        }
    }

    @Test
    fun `should return true for empty input`() {
        assertTrue("Expected valid for empty string", useCase.execute(""))
    }

    @Test
    fun `should return true for blank input`() {
        assertTrue("Expected valid for blank string", useCase.execute(" "))
    }
}