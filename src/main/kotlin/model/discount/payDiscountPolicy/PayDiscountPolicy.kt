package model.discount.payDiscountPolicy

import model.seat.Price

interface PayDiscountPolicy {
    fun calculatePrice(price: Price): Price
}
