package model.movie

import model.MockData
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MoviesTest {
    @Test
    fun `생성자 파라미터의 입력 값이 빈 리스트라면 오류를 발생시킨다`() {
        val moviesValue = emptyList<Movie>()
        assertThrows<IllegalArgumentException> {
            Movies(moviesValue)
        }
    }

    @Test
    fun `입력한 영화가 영화 리스트에 존재하지 않으면 오류를 발생시킨다`() {
        val movies = MockData.movies
        val inputMovieTitle = "하로의 대모험"
        assertThrows<IllegalArgumentException> {
            movies.getMovie(inputMovieTitle)
        }
    }

    @Test
    fun `입력한 영화가 영화 리스트에 존재한다면 영화 객체를 반환한다`() {
        val movies = MockData.movies
        val inputMovieTitle = "탑건: 매버릭"
        assertDoesNotThrow {
            movies.getMovie(inputMovieTitle)
        }
    }
}
