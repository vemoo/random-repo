package services

import model._
import util.CSV

trait DAO {
  def airports: Seq[Airport]

  def countries: Seq[Country]

  def runways: Seq[Runway]
}

trait FileSystemDAO extends DAO {
  override lazy val airports = {
    val uri = getClass.getResource("airports.csv").toURI
    val csv = CSV.parse(uri, ',')
    csv.tail.map(Airport.fromCsv).toVector
  }

  override lazy val countries = {
    val uri = getClass.getResource("countries.csv").toURI
    val csv = CSV.parse(uri, ',')
    csv.tail.map(Country.fromCsv).toVector
  }

  override lazy val runways = {
    val uri = getClass.getResource("runways.csv").toURI
    val csv = CSV.parse(uri, ',')
    csv.tail.map(Runway.fromCsv).toVector
  }
}
