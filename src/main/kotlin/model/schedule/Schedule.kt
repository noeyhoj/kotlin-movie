package model.schedule

import model.movie.Movie
import java.time.LocalDate
import java.time.LocalTime

class Schedule(
    val openTime: LocalTime,
    val closeTime: LocalTime,
    val screenings: List<Screening>,
) {
    init {
        require(openTime < closeTime) { "시작 시간이 종료 시간보다 클 수 없습니다" }
    }

    fun updateScreening(
        old: Screening,
        new: Screening,
    ): Schedule =
        Schedule(
            openTime = openTime,
            closeTime = closeTime,
            screenings =
                screenings.map {
                    if (it == old) new else it
                },
        )

    fun getScreeningsByMovieAndDate(movie: Movie, date: LocalDate): List<Screening> {
        val filterScreenings = screenings
            .filter { it.movie == movie && it.startDateTime.toLocalDate() == date }
        if(filterScreenings.isEmpty()) throw IllegalArgumentException("해당 날짜에 상영 중인 영화가 없습니다.")
        return filterScreenings
    }
}
