package model.discount.reserveDiscountPolicy

import model.seat.Price
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MovieDiscountPolicyTest {
    @Test
    fun `영화의 날짜가 2026-04-10 11-00에 시작하는 영화라면 무비데이 할인만 적용된다`() {
        val movieDayDiscountPolicy = MovieDayDiscountPolicy()
        val timeDiscountPolicy = TimeDiscountPolicy()
        val movieDiscountPolicy =
            MovieDiscountPolicy(
                listOf(
                    movieDayDiscountPolicy,
                    timeDiscountPolicy,
                ),
            )

        val price = Price(60000)
        val reserveDateTime = LocalDateTime.of(2026, 4, 10, 11, 0)
        assertThat(movieDiscountPolicy.calculatePrice(price, reserveDateTime)).isEqualTo(Price(54000))
    }

    @Test
    fun `영화의 날짜가 2026-04-10 10-00에 시작하는 영화라면 무비데이와 시간 할인이 둘 다 적용된다`() {
        val movieDayDiscountPolicy = MovieDayDiscountPolicy()
        val timeDiscountPolicy = TimeDiscountPolicy()
        val movieDiscountPolicy =
            MovieDiscountPolicy(
                listOf(
                    movieDayDiscountPolicy,
                    timeDiscountPolicy,
                ),
            )

        val price = Price(60000)
        val reserveDateTime = LocalDateTime.of(2026, 4, 10, 10, 0)
        assertThat(movieDiscountPolicy.calculatePrice(price, reserveDateTime)).isEqualTo(Price(52000))
    }

    @Test
    fun `영화의 날짜가 2026-04-10 10-00에 시작하는 영화라면 무비데이와 시간 할인이 적용될 때 무비데이 할인부터 적용된다`() {
        val movieDayDiscountPolicy = MovieDayDiscountPolicy()
        val timeDiscountPolicy = TimeDiscountPolicy()
        val movieDiscountPolicy =
            MovieDiscountPolicy(
                listOf(
                    movieDayDiscountPolicy,
                    timeDiscountPolicy,
                ),
            )

        val price = Price(60000)
        val reserveDateTime = LocalDateTime.of(2026, 4, 10, 10, 0)
        assertThat(movieDiscountPolicy.calculatePrice(price, reserveDateTime)).isNotEqualTo(Price(52200))
        assertThat(movieDiscountPolicy.calculatePrice(price, reserveDateTime)).isEqualTo(Price(52000))
    }
}
