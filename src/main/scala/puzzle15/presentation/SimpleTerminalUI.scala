package puzzle15.presentation

import puzzle15.board.Board
import puzzle15.presentation.SimpleTerminalUI.{solved, usage}

/**
  * Simple terminal based presentation
  */
final class SimpleTerminalUI(osName: String) extends UI {
  private[this] val linux = "Linux" == osName

  private[puzzle15] def getPresentation(board: Board): String =
      usage + "\n" +
      (if (board.solved) solved else "") + "\n" +
      ((0 until 4) map { row =>
        (0 until 4) map { col =>
          board(row, col) match {
            case x if x == 0 => "[]"
            case x if x <= 9 => " " + x
            case x if x > 9 => "" + x
          }
        } mkString " "
      } mkString "\n") + "\n"

  override def show(board: Board): Unit = {
    if (linux) print("\033[H\033[2J")
    println(getPresentation(board))
  }
}

object SimpleTerminalUI {
  private[puzzle15] val usage = "Press (U)p, (D)own, (L)eft, (R)ight, (S)tart or (Q)uit and 'Enter'"
  private[puzzle15] val solved = "Solved!"
}