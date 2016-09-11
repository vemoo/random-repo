package services

import model._

trait SearchSvc extends DAO {

  type Result = (Airport, Seq[Runway])

  private lazy val runwaysByAirportRef =
    runways.groupBy(_.airportRef).withDefaultValue(Seq())

  private lazy val airportsByCountryCode =
    airports.groupBy(_.isoCountry).withDefaultValue(Seq())

  def findByCountryCode(code: String): Seq[Result] = {
    airportsByCountryCode(code)
      .map(a => (a, runwaysByAirportRef(a.id)))
  }

  def findByCountryName(name: String): Seq[Result] = {
    for {
      country <- countries.filter(_.name == name)
      airport <- airportsByCountryCode(country.code)
      runways = runwaysByAirportRef(airport.id)
    } yield (airport, runways)
  }
}
