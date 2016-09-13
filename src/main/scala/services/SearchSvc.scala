package services

import model._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait SearchSvc extends DAO {

  type Result = (Airport, Seq[Runway])

  private val countries = getCountries

  private val runwaysByAirportRef =
    getRunways.map(_.groupBy(_.airportRef).withDefaultValue(Seq()))

  private val airportsByCountryCode =
    getAirports.map(_.groupBy(_.isoCountry).withDefaultValue(Seq()))

  def findCountriesByName(name: String): Future[Seq[Country]] = {
    val nameUpper = name.toUpperCase()
    countries.map(_.filter(_.name.toUpperCase().startsWith(nameUpper)))
  }

  def getCountryByCode(code: String): Future[Option[Country]] = {
    val codeUpper = code.toUpperCase()
    countries.map(_.find(_.code.toUpperCase() == codeUpper))
  }

  def getAirportsAndRunwaysByCountry(country: Country): Future[Seq[Result]] = {
    for {
      aByCC <- airportsByCountryCode
      rByARef <- runwaysByAirportRef
    } yield aByCC(country.code)
      .map(a => (a, rByARef(a.id)))
  }
}
