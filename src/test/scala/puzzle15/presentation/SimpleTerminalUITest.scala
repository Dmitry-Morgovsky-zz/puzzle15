package puzzle15.presentation

import org.scalatest.FunSuite
import puzzle15.board.LongBoard
import puzzle15.presentation.SimpleTerminalUI.{solved, usage}

import scala.language.postfixOps

class SimpleTerminalUITest extends FunSuite {

  private[this] val linuxUI = new SimpleTerminalUI("Linux")
  private[this] val otherUI = new SimpleTerminalUI("non Linux")
  private[this] val board = LongBoard()

  test("Get presentation -- Linux, solved board") {
    assertResult(
      usage + "\n" +
      solved + "\n" +
      " 1  2  3  4\n" +
      " 5  6  7  8\n" +
      " 9 10 11 12\n" +
      "13 14 15 []\n") { linuxUI getPresentation board }
  }

  test("Get presentation -- Linux, not solved board") {
    assertResult(
      usage + "\n\n" +
      " 1  2  3  4\n" +
      " 5  6  7  8\n" +
      " 9 10 11 12\n" +
      "13 14 [] 15\n") { linuxUI getPresentation (board right) }
  }

  test("Get presentation -- non Linux, solved board") {
    assertResult(
      usage + "\n" +
      solved + "\n" +
      " 1  2  3  4\n" +
      " 5  6  7  8\n" +
      " 9 10 11 12\n" +
      "13 14 15 []\n") { linuxUI getPresentation board }
  }

  test("Get presentation -- non Linux, not solved board") {
    assertResult(
      usage + "\n\n" +
      " 1  2  3  4\n" +
      " 5  6  7  8\n" +
      " 9 10 11 12\n" +
      "13 14 [] 15\n") { linuxUI getPresentation (board right) }
  }

}
