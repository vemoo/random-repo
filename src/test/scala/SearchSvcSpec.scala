import model._
import org.scalatest.AsyncFunSuite
import services._

import scala.concurrent.Future

class SearchSvcSpec extends AsyncFunSuite {

  trait TestDAO extends DAO {
    override def getCountries: Future[Seq[Country]] = Future.successful {
      Seq(
        Country(1, "ES", "Spain", "Europe", "", Seq()),
        Country(2, "CH", "Switzerland", "Europe", "", Seq())
      )
    }

    private def testAirport(id: Int, countryCode: String) =
      Airport(id, s"AIR$id", s"type $id", s"Airport number $id", 0, 0, None, "Europe",
        countryCode, "EU", None, false, None, None, None, None, None, Seq())

    override def getAirports: Future[Seq[Airport]] = Future.successful {
      Seq(
        testAirport(1, "ES"),
        testAirport(2, "ES"),
        testAirport(3, "CH")
      )
    }

    private def testRunWay(id: Int, airportId: Int) = {
      val dn = DontKnow("XX", None, None, None, None, None)
      Runway(id, airportId, "XX", None, None, None, false, false, dn, dn)
    }

    override def getRunways: Future[Seq[Runway]] = Future.successful {
      Seq(
        testRunWay(1, 1),
        testRunWay(2, 1),
        testRunWay(3, 3)
      )
    }
  }

  val svc = new SearchSvc with TestDAO

  test("Find by name should be case insensitive") {
    svc.findCountriesByName("spain").map {
      case Seq(country) => assert(country.code == "ES")
      case cs => fail(s"returned ${cs.length} countries instead of 1")
    }
  }

  test("Find by name should return all countries starting with the argument") {
    svc.findCountriesByName("s").map { countries =>
      val names = countries.map(_.code).sorted
      assert(names == Seq("CH", "ES"))
    }
  }

  test("Find by code shol be case insesitive") {
    svc.getCountryByCode("CH").map { country =>
      val name = country.map(_.name)
      assert(name.contains("Switzerland"))
    }
  }

  test("Should return expected Airports and Runways") {
    svc.getCountryByCode("ES").flatMap { optCountry =>
      val country = optCountry.getOrElse(fail("country not found"))
      svc.getAirportsAndRunwaysByCountry(country).map { result =>
        val airportIds = result.map(_._1.id).sorted
        assert(airportIds == Seq(1, 2))
        val runways1ids = result.find(_._1.id == 1).map(_._2).head.map(_.id).sorted
        assert(runways1ids == Seq(1, 2))
        val runways2 = result.find(_._1.id == 2).map(_._2).head
        assert(runways2.isEmpty)
      }
    }
  }
}
