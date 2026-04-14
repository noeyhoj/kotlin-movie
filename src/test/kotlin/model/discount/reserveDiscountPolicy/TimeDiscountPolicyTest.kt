package model.discount.reserveDiscountPolicy

import model.seat.Price
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime

class TimeDiscountPolicyTest {
    @ParameterizedTest
    @ValueSource(ints = [11, 12, 13, 14, 15, 16, 17, 18, 19])
    fun `영화의 시작 시간이 11시부터 8시 사이에 시작한다면 할인받지 않은 Price가 반환된다`(hour: Int) {
        val price = Price(60000)
        val reservedDateTime = LocalDateTime.of(2026, 4, 10, hour, 0)
        val timeDiscountPolicy = TimeDiscountPolicy()

        assertThat(timeDiscountPolicy.calculatePrice(price, reservedDateTime)).isEqualTo(Price(60000))
    }

    @ParameterizedTest
    @ValueSource(ints = [9, 10, 20, 21, 22])
    fun `영화의 시작 시간이 11시부터 8시 사이에 시작한다면 할인을 받은 Price가 반환된다`(hour: Int) {
        val price = Price(60000)
        val reservedDateTime = LocalDateTime.of(2026, 4, 10, hour, 0)
        val timeDiscountPolicy = TimeDiscountPolicy()

        assertThat(timeDiscountPolicy.calculatePrice(price, reservedDateTime)).isEqualTo(Price(58000))
    }
}
