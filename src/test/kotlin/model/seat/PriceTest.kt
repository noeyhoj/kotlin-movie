package model.seat

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PriceTest {
    @Test
    fun `금액은 0 미만이면 오류가 발생한다`() {
        val value = -1000

        assertThrows<IllegalArgumentException> {
            Price(value)
        }
    }
}
