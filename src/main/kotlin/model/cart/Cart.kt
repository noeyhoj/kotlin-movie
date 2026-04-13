package model.cart

import model.discount.reserveDiscountPolicy.ReserveDiscountPolicy
import model.schedule.Screening
import model.seat.Price
class Cart(
    val items: List<CartItem> = emptyList(),
) {
    fun parseOverlapping(selectedScreening: Screening) {
        if (items.any {
                it.screening.movie.title != selectedScreening.movie.title &&
                    it.screening.isOverlapping(selectedScreening)
            }
        ) {
            throw IllegalArgumentException("선택하신 상영 시간이 겹칩니다. 다른 시간을 선택해 주세요.")
        }
    }

    fun addItem(cartItem: CartItem): Cart = Cart(items + cartItem)

    fun calculateItemsPrice(reserveDiscountPolicy: ReserveDiscountPolicy): Price =
        Price(
            items.sumOf { item ->
                reserveDiscountPolicy
                    .calculatePrice(
                        price = item.screening.calculatePrice(item.seatNames),
                        reservedDateTime = item.screening.startDateTime,
                    ).value
            },
        )
}
