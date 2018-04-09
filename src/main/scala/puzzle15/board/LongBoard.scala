package puzzle15.board

import puzzle15.board.Board.index
import puzzle15.board.LongBoard.solvedTiles

import scala.language.postfixOps

/**
  * Another Board implementation. (Just for fun 8P)
  * Since every tile can hold one of 16 values it is enough the single Long to keep the board state
  * @param emptyRow row of the empty place
  * @param emptyCol column of the empty place
  * @param tiles state of tiles
  */
final class LongBoard private(emptyRow: Int, emptyCol: Int, tiles: Long) extends Board(emptyRow, emptyCol) {

  /**
    * Calculates mask for given index
    * @param index index (0..15)
    * @return mask
    */
  private[this] def mask(index: Int) = 0xFL << (index << 2)

  /**
    * Returns the hex digit at given index shifted to the newIndex
    * @param index index of the hex digit
    * @param newIndex index of the new position
    * @return
    */
  private[this] def shift(index: Int, newIndex: Int) = {
    val hexDigit = tiles & mask(index)
    val bitsToShift = (index - newIndex) << 2
    if (bitsToShift < 0) hexDigit << -bitsToShift else
    if (bitsToShift > 0) hexDigit >>> bitsToShift else hexDigit
  }

  override protected def newBoard(row: Int, col: Int): Board = {
    val i = index(row, col)
    new LongBoard(row, col, (tiles | shift(i, index(emptyRow, emptyCol))) & ~mask(i))
  }

  override def apply(row: Int, col: Int): Int = {
    val i = index(row, col)
    shift(i, 0) intValue
  }

  override def solved: Boolean = tiles == solvedTiles
}

object LongBoard {
  private val solvedTiles = 0x0FEDCBA987654321L

  def apply(): Board = new LongBoard(3, 3, solvedTiles)
}
