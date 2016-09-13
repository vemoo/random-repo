package services

import model._
import util.CSV

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait DAO {
  def getAirports: Future[Seq[Airport]]

  def getCountries: Future[Seq[Country]]

  def getRunways: Future[Seq[Runway]]
}

trait FileSystemDAO extends DAO {
  override def getAirports = Future {
    val uri = getClass.getResource("airports.csv").toURI
    val csv = CSV.parse(uri, ',')
    csv.tail.map(Airport.fromCsv).toVector
  }

  override def getCountries = Future {
    val uri = getClass.getResource("countries.csv").toURI
    val csv = CSV.parse(uri, ',')
    csv.tail.map(Country.fromCsv).toVector
  }

  override def getRunways = Future {
    val uri = getClass.getResource("runways.csv").toURI
    val csv = CSV.parse(uri, ',')
    csv.tail.map(Runway.fromCsv).toVector
  }
}
