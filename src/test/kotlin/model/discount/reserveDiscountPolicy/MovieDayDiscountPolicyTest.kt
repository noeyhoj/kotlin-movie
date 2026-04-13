package model.discount.reserveDiscountPolicy

import model.seat.Price
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MovieDayDiscountPolicyTest {
    @Test
    fun `영화의 상영 날짜가 10 또는 20 또는 30일이라면 10퍼센트 할인된 금액을 가진 Price가 반환된다`() {
        val movieDayDiscountPolicy = MovieDayDiscountPolicy()
        val reservedDateTimes =
            listOf(
                LocalDateTime.of(2026, 4, 10, 11, 0),
                LocalDateTime.of(2026, 4, 20, 16, 0),
                LocalDateTime.of(2026, 4, 30, 12, 0),
            )
        val price = Price(60000)

        reservedDateTimes.forEach { reservedDateTime ->
            assertThat(movieDayDiscountPolicy.calculatePrice(price, reservedDateTime)).isEqualTo(Price(54000))
        }
    }

    @Test
    fun `영화의 상영 날짜가 10 또는 20 또는 30일이 아니라면 할인을 받지 않은 Price가 반환된다`() {
        val movieDayDiscountPolicy = MovieDayDiscountPolicy()
        val reservedDateTimes =
            listOf(
                LocalDateTime.of(2026, 4, 11, 11, 0),
                LocalDateTime.of(2026, 4, 26, 16, 0),
                LocalDateTime.of(2026, 4, 4, 12, 0),
            )
        val price = Price(60000)

        reservedDateTimes.forEach { reservedDateTime ->
            assertThat(movieDayDiscountPolicy.calculatePrice(price, reservedDateTime)).isEqualTo(Price(60000))
        }
    }
}
