import services._
import ui._

object Application {

  val svc = new SearchSvc with FileSystemDAO

  def main(args: Array[String]): Unit = {
    loop()
  }

  def loop(): Unit = {
    chooseFrom(EnterCode, SearchByName, Exit) match {
      case EnterCode =>
        showConsole("Enter country code:")
        val code = io.StdIn.readLine()
        svc.getCountryByCode(code) match {
          case Some(country) =>
            val res = svc.getAirportsAndRunwaysByCountry(country)
            showConsole(res)
          case None => showConsole("Country not found")
        }
      case SearchByName =>
        showConsole("Enter country name:")
        val name = io.StdIn.readLine()
        svc.findCountriesByName(name) match {
          case cs if cs.isEmpty => showConsole("No countries found")
          case Seq(country) =>
            val res = svc.getAirportsAndRunwaysByCountry(country)
            showConsole(res)
          case cs =>
            val country = chooseFrom(cs)
            val res = svc.getAirportsAndRunwaysByCountry(country)
            showConsole(res)
        }
      case Exit => return
    }

    loop()
  }
}
