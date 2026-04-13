package model.schedule

import model.movie.Movie
import model.seat.SeatInventory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ScheduleTest {

    @Test
    fun `일정의 총 시각(시작 시각부터 종료 시각까지)은 영화의 상영 시간보다 크거나 같아야 한다`() {
        val movieRunningTime = Duration.ofMinutes(130)

        val schedule = createSchedule()

        val totalTime = Duration.between(schedule.openTime, schedule.closeTime)
        assertThat(totalTime).isGreaterThanOrEqualTo(movieRunningTime)
    }

    private fun createSchedule(
        openTime: LocalTime = LocalTime.of(9, 0),
        closeTime: LocalTime = LocalTime.of(23, 0),
        screenings: List<Screening> = listOf(createScreening()),
    ): Schedule = Schedule(openTime, closeTime, screenings)

    private fun createScreening(startDateTime: LocalDateTime = LocalDateTime.of(2026, 4, 10, 10, 0)): Screening =
        Screening(
            movie =
                Movie(
                    title = "인터스텔라",
                    runningTime = 130,
                    startDate = LocalDate.of(2026, 4, 1),
                    endDate = LocalDate.of(2026, 4, 30),
                ),
            startDateTime = startDateTime,
            seatInventory = SeatInventory.createDefaultSeatInventory(),
        )
}
