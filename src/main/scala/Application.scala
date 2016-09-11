import model._
import services._
import util._

object Application {

  val svc = new SearchSvc with FileSystemDAO

  def main(args: Array[String]): Unit = {
    loop()
  }

  def loop(): Unit = {
    print("Enter code: ")
    val code = io.StdIn.readLine()
    if (!code.isEmpty) {
      val res = svc.findByCountryCode(code)
      println(res)
      loop()
    }
  }
}
