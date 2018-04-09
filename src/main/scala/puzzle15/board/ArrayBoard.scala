package puzzle15.board

import puzzle15.board.ArrayBoard.solvedTiles
import puzzle15.board.Board.index

import scala.language.postfixOps

/**
  * Board implementation that uses sequence of Int to keep board state
  * @param emptyRow row of the empty place
  * @param emptyCol column of the empty place
  * @param tiles state of tiles
  */
final class ArrayBoard private(emptyRow: Int, emptyCol: Int, tiles: Seq[Int]) extends Board(emptyRow, emptyCol) {

  protected def newBoard(row: Int, col: Int): Board = {
    val tileIndex = index(row, col)
    val emptyIndex = index(emptyRow, emptyCol)
    new ArrayBoard(row, col,
      tiles
        .updated(emptyIndex, tiles(tileIndex))
        .updated(tileIndex, 0))
  }

  def apply(row: Int, col: Int): Int = tiles(index(row, col))

  def solved: Boolean = tiles == solvedTiles
}

object ArrayBoard {
  private val solvedTiles = (1 until 16) :+ 0

  def apply(): Board = new ArrayBoard(3, 3, solvedTiles)
}