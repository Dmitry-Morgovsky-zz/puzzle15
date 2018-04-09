package puzzle15.board

import puzzle15.board.Board._

import scala.annotation.tailrec
import scala.language.postfixOps
import scala.util.Random

/**
  * Abstract base class for different board implementations
  * @param emptyRow row of the empty place
  * @param emptyCol column of the empty place
  */
abstract class Board(emptyRow: Int, emptyCol: Int) {

  /**
    * Move up the tile below the empty place
    * @return The new board, if move performed. The original board otherwise
    */
  def up: Board = move(+1, 0)

  /**
    * Move down the tile above the empty place
    * @return The new board, if move performed. The original board otherwise
    */
  def down: Board = move(-1, 0)

  /**
    * Move left the tile from the right of the empty place
    * @return The new board, if move performed. The original board otherwise
    */
  def left: Board = move(0, +1)

  /**
    * Move right the tile from the left of the empty place
    * @return The new board, if move performed. The original board otherwise
    */
  def right: Board = move(0, -1)

  /**
    * Returns a new board after moving tile at (row, col)
    * @param row row of the tile to move
    * @param col column of the tile to move
    * @return a new board
    */
  protected def newBoard(row: Int, col: Int): Board

  /**
    * Returns the value of the tile at given (row, col)
    * @param row row of the tile in query
    * @param col column of the tile in query
    * @return
    */
  def apply(row: Int, col: Int): Int

  /**
    * Try to move the tile at (rowOffset, colOffset) from the (emptyRow, emptyCol)
    * @return The new board, if move performed. The original board otherwise
    */
  private def move(rowOffset: Int, colOffset: Int): Board = {
    val row = emptyRow + rowOffset
    val col = emptyCol + colOffset
    if (row < 0 || row > 3 || col < 0 || col > 3) this
    else newBoard(row, col)
  }

  /**
    * Scrambles the board by applying given moves
    * @param moves moves to apply
    * @return scrambled board
    */
  private[puzzle15] def scramble(moves: Seq[Transform]): Board = {
    @tailrec
    def loop(board: Board, moves: Seq[Transform]): Board =
      if (moves isEmpty)
        board
      else
        loop(moves.head(board), moves.tail)

    loop(this, moves)
  }

  /**
    * Scrambles the board by applying random moves (at most 256)
    * @return scrambled board
    */
  def scramble(random: Random = scala.util.Random): Board =
    scramble(0 until random.nextInt(256) map { _ =>  moves(random.nextInt(4)) })

  /**
    * Is this board solved?
    * @return
    */
  def solved: Boolean
}

object Board {
  type Transform = Board => Board

  /**
    * Calculates index of the hex digit (quartet of bits) for given row and col
    * @param row row (0..3)
    * @param col column (0..3)
    * @return index
    */
  private[puzzle15] def index(row: Int, col: Int) = row * 4 + col

  private[puzzle15] val moves = Seq[Transform](_.up, _.down, _.left, _.right)
}
