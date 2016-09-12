import model._
import services._
import util._

object Application {

  val svc = new SearchSvc with FileSystemDAO

  def main(args: Array[String]): Unit = {
    loop()
  }

  def chooseFrom[T](first: T, rest: T*): T = chooseFrom(first +: rest)

  def chooseFrom[T](source: Seq[T]): T = {
    for ((el, i) <- source.zipWithIndex) {
      println(s"$i) $el")
    }
    var selection: Int = -1
    while (!source.indices.contains(selection)) {
      print("Selection: ")
      selection = io.StdIn.readInt()
    }
    source(selection)
  }

  sealed trait Action

  object SearchByCode extends Action {
    override def toString = "Search by code"
  }

  object SearchByName extends Action {
    override def toString = "Search by name"
  }

  object Exit extends Action {
    override def toString = "Exit"
  }

  def loop(): Unit = {

    chooseFrom(SearchByCode, SearchByName, Exit) match {
      case SearchByCode =>
        println("Enter country code:")
        val code = io.StdIn.readLine()
        svc.getCountryByCode(code) match {
          case Some(country) =>
            val res = svc.getAirportsAndRunwaysByCountry(country)
            println(res)
          case None => println("Country not found")
        }
      case SearchByName =>
        println("Enter country name:")
        val name = io.StdIn.readLine()
        svc.findCountriesByName(name) match {
          case cs if cs.isEmpty => println("No countries found")
          case Seq(country) =>
            val res = svc.getAirportsAndRunwaysByCountry(country)
            println(res)
          case cs =>
            val country = chooseFrom(cs)
            val res = svc.getAirportsAndRunwaysByCountry(country)
            println(res)
        }
      case Exit => return
    }

    loop()
  }
}
