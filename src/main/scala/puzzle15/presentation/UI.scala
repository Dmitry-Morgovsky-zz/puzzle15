package puzzle15.presentation

import puzzle15.board.Board

trait UI {
  def show(board: Board): Unit
}
