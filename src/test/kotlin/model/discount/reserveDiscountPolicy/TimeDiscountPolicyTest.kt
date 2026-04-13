package model.discount.reserveDiscountPolicy

import model.seat.Price
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TimeDiscountPolicyTest {
    @Test
    fun `영화의 시작 시간이 11시부터 8시 사이에 시작한다면 할인받지 않은 Price가 반환된다`() {
        val price = Price(60000)
        val reservedDateTimes =
            listOf(
                LocalDateTime.of(2026, 4, 10, 11, 0),
                LocalDateTime.of(2026, 4, 20, 19, 59),
                LocalDateTime.of(2026, 4, 30, 12, 0),
            )
        val timeDiscountPolicy = TimeDiscountPolicy()

        reservedDateTimes.forEach { reservedDateTime ->
            assertThat(timeDiscountPolicy.calculatePrice(price, reservedDateTime)).isEqualTo(Price(60000))
        }
    }

    @Test
    fun `영화의 시작 시간이 11시부터 8시 사이에 시작한다면 할인을 받은 Price가 반환된다`() {
        val price = Price(60000)
        val reservedDateTimes =
            listOf(
                LocalDateTime.of(2026, 4, 10, 9, 0),
                LocalDateTime.of(2026, 4, 20, 10, 59),
                LocalDateTime.of(2026, 4, 30, 20, 0),
            )
        val timeDiscountPolicy = TimeDiscountPolicy()

        reservedDateTimes.forEach { reservedDateTime ->
            assertThat(timeDiscountPolicy.calculatePrice(price, reservedDateTime)).isEqualTo(Price(58000))
        }
    }
}
