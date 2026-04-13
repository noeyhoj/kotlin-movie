package model.discount.reserveDiscountPolicy

import model.seat.Price
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime

class MovieDayDiscountPolicyTest {
    @ParameterizedTest
    @ValueSource(ints = [10, 20, 30])
    fun `영화의 상영 날짜가 10 또는 20 또는 30일이라면 10퍼센트 할인된 금액을 가진 Price가 반환된다`(day: Int) {
        val movieDayDiscountPolicy = MovieDayDiscountPolicy()
        val reservedDateTime = LocalDateTime.of(2026, 4, day, 11, 0)

        val price = Price(60000)

        assertThat(movieDayDiscountPolicy.calculatePrice(price, reservedDateTime)).isEqualTo(Price(54000))
    }

    @ParameterizedTest
    @ValueSource(ints = [4, 11, 19, 23, 29])
    fun `영화의 상영 날짜가 10 또는 20 또는 30일이 아니라면 할인을 받지 않은 Price가 반환된다`(day: Int) {
        val movieDayDiscountPolicy = MovieDayDiscountPolicy()
        val reservedDateTime = LocalDateTime.of(2026, 4, day, 11, 0)
        val price = Price(60000)

        assertThat(movieDayDiscountPolicy.calculatePrice(price, reservedDateTime)).isEqualTo(Price(60000))
    }
}
