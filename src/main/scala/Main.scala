import scala.annotation.tailrec
import scala.util.Random


object Main {

  /**
    *
    * Maing method to run as a standalone app
    *
    * @param args winning probability of the player
    * @return
    */
  def main(args: Array[String]): Unit = {

    try {
      val winningProbability = if (args.length !=0 && (args(0).toDouble >=0 && args(0).toDouble <=1)) args(0).toDouble else -1
      if (winningProbability == -1) throw new Exception()

      val final_score = play(serve_bernoulli, winningProbability, List((0, 0)))

      final_score.foreach(x => println(x._1, x._2))
      println(" ===== Audience Score Boaord ====")
      score_display(final_score).foreach(x => println(x._1,x._2))

    }catch{
      case e:Exception => println("Error:The value of winning probability must be between 0 and 1")
    }


  }

  /**
    *
    * Implementing a single serve using a bernoullian trial
    *
    * @param winningProbability the winning probability of the player; default is 0.5
    * @return 0 (loss) 1 (win)
    */

  def serve_bernoulli(winningProbability: Double = 0.5): Int = {
    if (new Random().nextDouble() <= 1 - winningProbability) 0 else 1
  }

  /**
    *
    * Play a game until a winner is decided
    *
    * @param serve A function to implement the serve action
    * @param srvrWinProblty Winning probaility of the server
    * @param scores current score
    * @return Latest score after a service
    */

  @tailrec // to optimize the recurssion
  def play(serve: Double => Int, srvrWinProblty: Double, scores: List[(Int, Int)]): List[(Int, Int)] = {

    val point = serve(srvrWinProblty) // A service is awarded o or 1 point
    val new_scores = scores ++ List((point, 1 - point)) // update the score

    val aggregated_scores =
      new_scores.aggregate(0, 0)((acc, score) => (acc._1 + score._1, acc._2 + score._2),
        (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc1._2))
    val gameStatus =  if ((aggregated_scores._1 >= 4 && (aggregated_scores._1 >= aggregated_scores._2 + 2)) ||
      (aggregated_scores._2 >= 4 && (aggregated_scores._2 >= aggregated_scores._1 + 2))) {0} else {1} // over(0), NotOver(1)

    if (gameStatus == 0) new_scores else play(serve, srvrWinProblty, new_scores) // Continue to serve untill game is over
  }

  /**
    *
    * Function to print the final score board
    *
    * @param scores Binary coded scores
    * @return Interpreted score as per tennis jargons
    */
  def score_display(scores: List[(Int, Int)]): Seq[(String, String)] = {

    val scoreMapping = Map(0 -> "0", 1 -> "15", 2 -> "30", 3 -> "40")
    val accumulated_scores = for (i <- 1 to scores.length) yield {
      scores.slice(0, i).aggregate(0, 0)((acc, score) => (acc._1 + score._1, acc._2 + score._2), (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc1._2))
    }

     val score_board= for (i <- 0 to accumulated_scores.length - 1) yield {

      ({ if ((accumulated_scores(i)._1 == accumulated_scores(i)._2) &&  accumulated_scores(i)._1 >=3 ) "D" else
        if ((accumulated_scores(i)._1 == accumulated_scores(i)._2+1) && accumulated_scores(i)._1>3 ) "AD" else
        if ((accumulated_scores(i)._1+1 == accumulated_scores(i)._2) && accumulated_scores(i)._1>3 ) "DA" else
        if ((i == accumulated_scores.length - 1) && accumulated_scores(i)._1 > accumulated_scores(i)._2 ) "W" else
        if ((i == accumulated_scores.length - 1) && accumulated_scores(i)._1 < accumulated_scores(i)._2 ) "L" else
        scoreMapping.get(accumulated_scores(i)._1).get
      },{
        if ((accumulated_scores(i)._1 == accumulated_scores(i)._2) &&  accumulated_scores(i)._2 >=3 ) "D" else
        if ((accumulated_scores(i)._2 == accumulated_scores(i)._1+1) && accumulated_scores(i)._2>3 ) "AD" else
        if ((accumulated_scores(i)._2+1 == accumulated_scores(i)._1) && accumulated_scores(i)._2>3 ) "DA" else
        if ((i == accumulated_scores.length - 1) && accumulated_scores(i)._2 > accumulated_scores(i)._1 ) "W" else
        if ((i == accumulated_scores.length - 1) && accumulated_scores(i)._2 < accumulated_scores(i)._1 ) "L" else
          scoreMapping.get(accumulated_scores(i)._2).get
      })
    }
    score_board
  }
}


