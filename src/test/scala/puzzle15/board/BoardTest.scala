package puzzle15.board

import org.mockito.Mockito.when
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar
import puzzle15.board.Board.Transform

import scala.util.Random

abstract class BoardTest(board: Board) extends FunSuite with MockitoSugar {

  test("Initial state") {
    check(board, true,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11, 12,
      13, 14, 15,  0)
  }

  test("Move down") {
    val board1 = board.down

    check(board1, false,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11,  0,
      13, 14, 15, 12)

    val board2 = board1.down
    check(board2, false,
       1,  2,  3,  4,
       5,  6,  7,  0,
       9, 10, 11,  8,
      13, 14, 15, 12)

    val board3 = board2.down
    check(board3, false,
       1,  2,  3,  0,
       5,  6,  7,  4,
       9, 10, 11,  8,
      13, 14, 15, 12)

    assertResult(board3) { board3.down }
  }

  test("Move up") {
    assertResult(board) { board.up }

    val board0 = board.down.down.down
    check(board0, false,
       1,  2,  3,  0,
       5,  6,  7,  4,
       9, 10, 11,  8,
      13, 14, 15, 12)

    val board1 = board0.up
    check(board1, false,
       1,  2,  3,  4,
       5,  6,  7,  0,
       9, 10, 11,  8,
      13, 14, 15, 12)

    val board2 = board1.up
    check(board2, false,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11,  0,
      13, 14, 15, 12)

    val board3 = board2.up
    check(board3, true,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11, 12,
      13, 14, 15,  0)
  }

  test("Move right") {
    val board1 = board.right
    check(board1, false,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11, 12,
      13, 14,  0, 15)

    val board2 = board1.right
    check(board2, false,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11, 12,
      13,  0, 14, 15)

    val board3 = board2.right
    check(board3, false,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11, 12,
       0, 13, 14, 15)

    assertResult(board3) { board3.right }
  }

  test("Move left") {
    assertResult(board) { board.left }

    val board0 = board.right.right.right
    check(board0, false,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11, 12,
       0, 13, 14, 15)

    val board1 = board0.left
    check(board1, false,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11, 12,
      13,  0, 14, 15)

    val board2 = board1.left
    check(board2, false,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11, 12,
      13, 14,  0, 15)

    val board4 = board2.left
    check(board4, true,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9, 10, 11, 12,
      13, 14, 15,  0)
  }

  test("Scramble with random") {
    val random = mock[Random]

    when(random nextInt 256) thenReturn 8 // number of moves
    when(random nextInt 4) thenReturn(1, 3, 3, 0, 3, 0, 2, 1)

    val scrambledBoard = board.scramble(random)
    check(scrambledBoard, false,
      1,  2,  3,  4,
      5,  6,  7,  8,
      9,  0, 10, 11,
      13, 14, 15, 12)
  }

  test("Scramble by moves") {
    val scrambledBoard = board.scramble(Seq[Transform](_.down, _.right, _.right, _.up, _.right, _.up, _.left, _.down))
    check(scrambledBoard, false,
       1,  2,  3,  4,
       5,  6,  7,  8,
       9,  0, 10, 11,
      13, 14, 15, 12)
  }

  private[this] def check(board: Board, expectedSolved: Boolean, expected: Int*): Unit = {
    assertResult(expectedSolved) { board.solved }
    require(expected.size == 16)
    assertResult(expected( 0)) { board(0, 0) }
    assertResult(expected( 1)) { board(0, 1) }
    assertResult(expected( 2)) { board(0, 2) }
    assertResult(expected( 3)) { board(0, 3) }
    assertResult(expected( 4)) { board(1, 0) }
    assertResult(expected( 5)) { board(1, 1) }
    assertResult(expected( 6)) { board(1, 2) }
    assertResult(expected( 7)) { board(1, 3) }
    assertResult(expected( 8)) { board(2, 0) }
    assertResult(expected( 9)) { board(2, 1) }
    assertResult(expected(10)) { board(2, 2) }
    assertResult(expected(11)) { board(2, 3) }
    assertResult(expected(12)) { board(3, 0) }
    assertResult(expected(13)) { board(3, 1) }
    assertResult(expected(14)) { board(3, 2) }
    assertResult(expected(15)) { board(3, 3) }
  }
}
