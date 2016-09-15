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

  /**
    * Finds countries begining by name
    * @param name Name or begining of the name of the country, case insensitive
    * @return
    */
  def findCountriesByName(name: String): Future[Seq[Country]] = {
    val nameUpper = name.toUpperCase()
    countries.map(_.filter(_.name.toUpperCase().startsWith(nameUpper)))
  }

  /**
    * Finds countries by code exactly
    * @param code ISO country code, case insensitive
    * @return
    */
  def getCountryByCode(code: String): Future[Option[Country]] = {
    val codeUpper = code.toUpperCase()
    countries.map(_.find(_.code.toUpperCase() == codeUpper))
  }

  /**
    * Get the airports and its runways for a five country
    * @param country
    * @return
    */
  def getAirportsAndRunwaysByCountry(country: Country): Future[Seq[Result]] = {
    for {
      aByCC <- airportsByCountryCode
      rByARef <- runwaysByAirportRef
    } yield aByCC(country.code)
      .map(a => (a, rByARef(a.id)))
  }
}
