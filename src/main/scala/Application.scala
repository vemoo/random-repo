import services._
import ui._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object Application {

  val svc = new SearchSvc with FileSystemDAO

  def main(args: Array[String]): Unit = {
    Await.result(loop(), Duration.Inf)
  }

  def loop(): Future[Unit] = {
    chooseFrom(EnterCode, SearchByName, Exit) match {
      case EnterCode =>
        showConsole("Enter country code:")
        val code = io.StdIn.readLine()
        svc.getCountryByCode(code).flatMap {
          case Some(country) =>
            svc.getAirportsAndRunwaysByCountry(country).flatMap { res =>
              showConsole(res)
              loop()
            }
          case None => showConsole("Country not found")
            loop()
        }
      case SearchByName =>
        showConsole("Enter country name:")
        val name = io.StdIn.readLine()
        svc.findCountriesByName(name).flatMap {
          case cs if cs.isEmpty => showConsole("No countries found")
            loop()
          case Seq(country) =>
            svc.getAirportsAndRunwaysByCountry(country).flatMap { res =>
              showConsole(res)
              loop()
            }
          case cs =>
            val country = chooseFrom(cs)
            svc.getAirportsAndRunwaysByCountry(country).flatMap { res =>
              showConsole(res)
              loop()
            }
        }
      case Exit => Future.successful(())
    }
  }
}
