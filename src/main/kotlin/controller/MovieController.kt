package controller

import model.MockData
import model.cart.Cart
import model.cart.CartItem
import model.discount.PaymentMethod
import model.discount.payDiscountPolicy.PayDiscountBenefits
import model.discount.payDiscountPolicy.PaymentPayDiscountPolicy
import model.discount.payDiscountPolicy.PointPayDiscountPolicy
import model.discount.reserveDiscountPolicy.MovieDayDiscountPolicy
import model.discount.reserveDiscountPolicy.MovieDiscountPolicy
import model.discount.reserveDiscountPolicy.TimeDiscountPolicy
import model.movie.Movie
import model.schedule.Screening
import view.InputView
import view.OutputView
import java.time.LocalDate

class MovieController {
    val inputView = InputView()
    val outputView = OutputView()

    val movies = MockData.movies
    var schedule = MockData.mockSchedule
    var cart = Cart()

    fun checkMovieReserve(): Boolean {
        return try {
            val answer = inputView.movieReserveInput()

            return answer
        } catch (e: IllegalArgumentException) {
            outputView.printErrorMessage(e.message.toString())
            checkMovieReserve()
        }
    }

    fun checkMovieAdd(): Boolean =
        try {
            val answer = inputView.againMovieReserveInput()

            return answer
        } catch (e: IllegalArgumentException) {
            outputView.printErrorMessage(e.message.toString())
            checkMovieAdd()
        }

    fun searchMovie(): Movie =
        try {
            val input = inputView.movieTitleInput()
            movies.getMovie(input)
        } catch (e: IllegalArgumentException) {
            outputView.printErrorMessage(e.message.toString())
            searchMovie()
        }

    fun inputDate(): LocalDate {
        return try {
            val date = inputView.dateInput()

            return date
        } catch (e: Exception) {
            outputView.printErrorMessage(e.message.toString())
            inputDate()
        }
    }

    fun getScreeningsOfDateAndTitle(): Screening {
        val movie = searchMovie()
        var screenings: List<Screening>

        return try {
            val date = inputDate()
            screenings = schedule.getScreeningsByMovieAndDate(movie = movie, date = date)
            outputView.printScreenings(screenings)
            return selectMovieTime(cart, screenings)
        } catch (e: IllegalArgumentException) {
            outputView.printErrorMessage(e.message.toString())
            getScreeningsOfDateAndTitle()
        }
    }

    fun selectMovieTime(
        cart: Cart,
        screenings: List<Screening>,
    ): Screening {
        var selectedScreening: Screening

        return try {
            val number = inputView.screeningNumberInput(screenings.size)
            selectedScreening = screenings[number - 1]

            cart.parseOverlapping(selectedScreening)

            selectedScreening
        } catch (e: IllegalArgumentException) {
            outputView.printErrorMessage(e.message.toString())
            selectMovieTime(cart, screenings)
        }
    }

    fun inputSeats(): List<String> {
        return try {
            return inputView
                .reserveSeatsInput()
                .split(",")
                .map { it.trim() }
        } catch (e: IllegalArgumentException) {
            outputView.printErrorMessage(e.message.toString())
            inputSeats()
        }
    }

    fun reservedScreening(selectedScreening: Screening): CartItem {
        var seatNames: List<String>
        var reservedScreening: Screening
        while (true) {
            try {
                seatNames = inputSeats()
                reservedScreening = getReservedScreening(selectedScreening, seatNames)
                break
            } catch (e: IllegalArgumentException) {
                outputView.printErrorMessage(e.message.toString())
            }
        }
        schedule =
            schedule.updateScreening(
                selectedScreening,
                reservedScreening,
            )

        return CartItem(
            reservedScreening,
            seatNames,
        )
    }

    fun getReservedScreening(
        selectedScreening: Screening,
        seatNames: List<String>,
    ): Screening = selectedScreening.reserveSeats(seatNames)

    fun usePoint(): Int =
        try {
            inputView.pointInput().toIntOrNull() ?: throw IllegalArgumentException("숫자를 입력해주세요")
        } catch (e: IllegalArgumentException) {
            outputView.printErrorMessage(e.message.toString())
            usePoint()
        }

    fun selectPaymentMethod(): PaymentMethod =
        try {
            return inputView.paymentMethodInput()
        } catch (e: IllegalArgumentException) {
            outputView.printErrorMessage(e.message.toString())
            selectPaymentMethod()
        }

    fun checkPayment(): Boolean {
        return try {
            val answer = inputView.paymentConfirmInput()

            return answer
        } catch (e: IllegalArgumentException) {
            outputView.printErrorMessage(e.message.toString())
            checkPayment()
        }
    }

    fun run() {
        if (!checkMovieReserve()) return

        do {
            val selectedScreening: Screening = getScreeningsOfDateAndTitle()

            outputView.printSeatInventory(selectedScreening.seatInventory)

            val cartItem = reservedScreening(selectedScreening)
            cart = cart.addItem(cartItem)

            outputView.printCartItemAdded(cartItem)
        } while (checkMovieAdd())

        outputView.printCart(cart)

        val usePoint = usePoint()
        val paymentMethod = selectPaymentMethod()

        val moviePrice =
            cart.calculateItemsPrice(
                reserveDiscountPolicy =
                    MovieDiscountPolicy(
                        movieDiscountPolicies =
                            listOf(
                                MovieDayDiscountPolicy(),
                                TimeDiscountPolicy(),
                            ),
                    ),
            )

        val totalPrice =
            PayDiscountBenefits(
                payDiscountPolicies =
                    listOf(
                        PointPayDiscountPolicy(usePoint),
                        PaymentPayDiscountPolicy(paymentMethod),
                    ),
            ).calculatePrice(moviePrice)

        outputView.printTotalPrice(totalPrice.value)

        if (checkPayment()) {
            outputView.printReservationComplete(
                cart,
                totalPrice.value,
                usePoint,
            )
        }
    }
}
