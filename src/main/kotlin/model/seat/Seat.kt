package model.seat

data class Seat(
    val row: String,
    val column: Int,
    val isReserved: Boolean = false,
    val seatRank: SeatRank,
) {
    fun getSeatName(): String = "$row$column"

    fun reserve(): Seat {
        require(!isReserved) { "이미 예약된 좌석입니다" }
        return copy(
            isReserved = true,
        )
    }
}
