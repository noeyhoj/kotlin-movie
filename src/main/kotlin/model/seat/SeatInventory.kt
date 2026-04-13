package model.seat

data class SeatInventory(
    val seats: List<Seat>,
) {
    init {
        require(seats.isNotEmpty()) { "좌석 정보가 없습니다" }
        require(seats.all { it.row in rows && it.column in columns }) { "존재하지 않는 좌석입니다" }
    }

    fun getSeat(seatName: String): Seat = seats.find { it.getSeatName() == seatName } ?: throw IllegalArgumentException("존재하지 않는 좌석입니다")

    fun reserveSeats(seatNames: List<String>): SeatInventory {
        val targetSeats = seatNames.map { getSeat(it) }

        return copy(
            seats =
                seats.map { seat ->
                    if (targetSeats.contains(seat)) {
                        seat.reserve()
                    } else {
                        seat
                    }
                },
        )
    }

    fun calculatePrice(seatNames: List<String>): Price =
        Price(
            seatNames.sumOf {
                getSeat(it).seatRank.price.value
            },
        )

    companion object {
        val rows = ('A'..'E').map { it.toString() }

        val columns = (1..4).toList()

        fun createDefaultSeatInventory(): SeatInventory =
            SeatInventory(
                seats =
                    rows.flatMap { row ->
                        columns.map { column ->
                            Seat(
                                row = row,
                                column = column,
                                seatRank =
                                    when (row) {
                                        "A", "B" -> SeatRank.B_RANK
                                        "C", "D" -> SeatRank.S_RANK
                                        else -> SeatRank.A_RANK
                                    },
                            )
                        }
                    },
            )
    }
}
