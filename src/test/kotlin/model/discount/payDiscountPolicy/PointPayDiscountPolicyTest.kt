package model.discount.payDiscountPolicy

import model.seat.Price
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PointPayDiscountPolicyTest {
    @Test
    fun `사용자가 사용하는 포인트가 결제 금액보다 크다면 오류를 반환한다`() {
        val usePoint = 60000
        val pointPayDiscountPolicy = PointPayDiscountPolicy(usePoint)
        val price = Price(55000)

        assertThrows<IllegalArgumentException> {
            pointPayDiscountPolicy.calculatePrice(price)
        }
    }

    @Test
    fun `사용자가 사용하는 포인트가 결제 금액과 같다면 결제할 금액이 0원이다`() {
        val usePoint = 55000
        val pointPayDiscountPolicy = PointPayDiscountPolicy(usePoint)
        val price = Price(55000)

        assertThat(pointPayDiscountPolicy.calculatePrice(price)).isEqualTo(Price(0))
    }

    @Test
    fun `사용자가 2000 포인트를 사용하면 해당 포인트만큼 금액에서 차감된 Price가 반환된다`() {
        val usePoint = 2000
        val pointPayDiscountPolicy = PointPayDiscountPolicy(usePoint)
        val price = Price(55000)

        assertThat(pointPayDiscountPolicy.calculatePrice(price)).isEqualTo(Price(53000))
    }
}
