
import org.scalatest._
import scala.collection.mutable


class TennisKataTest extends FlatSpec with Matchers {


  "play" should "be" in {
   val scores = Main.play(Main.serve_bernoulli,0.5,List((0,0)))

    // (Love,Love)
    assert(scores(0) === (0,0))

    val aggregated_scores =
      scores.aggregate(0, 0)((acc, score) => (acc._1 + score._1, acc._2 + score._2),
        (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc1._2))

    // Winning condition
    assert( (aggregated_scores._1 >= 4 || aggregated_scores._2 >= 4) &&
      ((aggregated_scores._1 >= aggregated_scores._2+2) || (aggregated_scores._2 >= aggregated_scores._1+2) ) )

  }

  // Test case 2
  "serve" should "be" in {

    val point = Main.serve_bernoulli(0.5)
    assert(point == 0 || point == 1)

  }

  // Test case 3
  "score_display" should "be" in {
    val scores = Main.play(Main.serve_bernoulli,0.5,List((0,0)))
    val score_board = Main.score_display(scores)

    assert(score_board(0) == ("0","0"))
    assert((score_board(score_board.length-1) == ("W","L")) || (score_board(score_board.length-1) == ("L","W")))

  }



}
