package services

import model._

trait SearchSvc extends DAO {

  type Result = (Airport, Seq[Runway])

  private lazy val runwaysByAirportRef =
    runways.groupBy(_.airportRef).withDefaultValue(Seq())

  private lazy val airportsByCountryCode =
    airports.groupBy(_.isoCountry).withDefaultValue(Seq())

  def findCountriesByName(name: String): Seq[Country] = {
    val nameUpper = name.toUpperCase()
    countries.filter(_.name.toUpperCase().startsWith(nameUpper))
  }

  def getCountryByCode(code: String): Option[Country] = {
    val codeUpper = code.toUpperCase()
    countries.find(_.code.toUpperCase() == codeUpper)
  }

  def getAirportsAndRunwaysByCountry(country: Country): Seq[Result] = {
    airportsByCountryCode(country.code)
      .map(a => (a, runwaysByAirportRef(a.id)))
  }
}
