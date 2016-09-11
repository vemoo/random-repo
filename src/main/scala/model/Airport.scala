package model

case class Airport(id: Int, ident: String, `type`: String, name: String,
                   latitudeDeg: Double, longitudeDeg: Double, elevationFt: Option[Int],
                   continent: String, isoCountry: String, isoRegion: String, municipality: Option[Boolean],
                   scheduledService: String, gpsCode: Option[String], iataCode: Option[String], localCode: Option[String],
                   homeLink: Option[String], wikipediaLink: Option[String], keywords: Seq[String])

object Airport {

  import util.Convert._

  def fromCsv(vals: Seq[String]): Airport = {

    val id = vals(0).toInt
    val ident = vals(1)
    val `type` = vals(2)
    val name = vals(3)
    val lat = vals(4).toDouble
    val lon = vals(5).toDouble
    val elevation = toOption(vals(6)).map(_.toInt)
    val continent = vals(7)
    val isoCountry = vals(8)
    val isoRegion = vals(9)
    val municipality = toOption(vals(10)).map(_ == "yes")
    val scheduledService = vals(11)
    val gpsCode = toOption(vals(12))
    val iataCode = toOption(vals(13))
    val localCode = toOption(vals(14))
    val homeLink = toOption(vals(15))
    val wikiLink = toOption(vals(16))
    val keywords = vals(17).split(',').toVector

    Airport(
      id, ident, `type`, name, lat, lon, elevation,
      continent, isoCountry, isoRegion, municipality,
      scheduledService, gpsCode, iataCode, localCode,
      homeLink, wikiLink, keywords)
  }
}