package todd.solution

object DiningPhilospohers extends App {
  val philosophers = Seq(
    Philosopher("WL Craig"),
    Philosopher("Moreland"),
    Philosopher("Copan"),
    Philosopher("Lennox"),
    Philosopher("Plantinga"))

  val hungryFutures = for (p <- philosophers) yield p.eat(philosophers)

  while (!hungryFutures.foldLeft(true)((r, c) => r & c.isCompleted)) {
    Thread.sleep(100)
  }
}