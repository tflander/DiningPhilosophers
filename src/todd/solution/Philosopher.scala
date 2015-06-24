package todd.solution
import scala.concurrent.Future
import scala.concurrent.future
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.locks.ReentrantLock

case class Philosopher(name: String) {

  val fork = new ReentrantLock() 
  
  def eat(philosophers: Seq[Philosopher]) = {
    future {
      val threadId = Thread.currentThread.getId
      val nextGuy = findNextGuyForFork(philosophers)
      philosophers.synchronized {
    	  fork.lock()
    	  nextGuy.fork.lock()
      }
      println(name + " is eating " + threadId)
      println("  " + name + " got fork from " + nextGuy.name)
      Thread.sleep(1000)
      println(name + " is done." + threadId)
      fork.unlock()
      nextGuy.fork.unlock()
    }
  }
  
  def findNextGuyForFork(philosophers: Seq[Philosopher]) = {
    val myIndex = (for(i <- 0 to philosophers.size - 1) yield {
      philosophers(i).name match {
        case `name` => Some(i)
        case _ => None
      }
    }).flatten.head
    
     val nextGuy = if(myIndex < philosophers.size-1) myIndex + 1 else 0
//    val nextGuy = if(myIndex == 0 ) philosophers.size - 1 else myIndex - 1
    philosophers(nextGuy)
  }
}

