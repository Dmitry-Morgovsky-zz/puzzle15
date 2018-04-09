package puzzle15

import puzzle15.board.{Board, LongBoard}
import puzzle15.board.Board._
import puzzle15.presentation.SimpleTerminalUI

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.language.postfixOps
/**
 * Hello world!
 *
 */
object Puzzle15 extends App {
  private[this] val moves =
    Map[String, Transform]("u" -> (_.up), "d" -> (_.down), "l" -> (_.left), "r" -> (_.right), "s" -> (_.scramble()))
  private[this] val presentation = new SimpleTerminalUI(System.getProperty("os.name"))

  continueWith(LongBoard().scramble())

  /**
    * Apply the user input to the given board
    * @param board Board to use
    */
  @tailrec
  private[this] def continueWith(board: Board): Unit = {
    presentation.show(board)

    val key = readLine.toLowerCase
    if (key == "q")
      System.exit(0)

    // If mapping for key exists, continue with the board after applying the transformation corresponding to key.
    // Otherwise continue with the same board
    continueWith(moves get key map { _(board) } getOrElse board)
  }
}
