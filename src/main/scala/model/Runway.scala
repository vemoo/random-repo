package model

import util.Convert._

case class DontKnow(ident: String, latitudeDeg: Option[Double], longitudeDeg: Option[Double],
                    elevationFt: Option[Int], headingDegT: Option[Double],
                    displacedThresholdFt: Option[Int])

object DontKnow {

  def fromCsv(vals: Seq[String]): DontKnow = {

    val ident = vals(0)
    val lat = toOption(vals(1)).map(_.toDouble)
    val lon = toOption(vals(2)).map(_.toDouble)
    val elevation = toOption(vals(3)).map(_.toInt)
    val heading = toOption(vals(4)).map(_.toDouble)
    val displacedThres = toOption(vals(5)).map(_.toInt)

    DontKnow(ident, lat, lon, elevation, heading, displacedThres)
  }
}

case class Runway(id: Int, airportRef: Int, airportIdent: String,
                  lengthFt: Option[Int], widthFt: Option[Int], surface: Option[String],
                  lighted: Boolean, closed: Boolean, le: DontKnow, he: DontKnow)

object Runway {

  def fromCsv(vals: Seq[String]): Runway = {

    val id = vals(0).toInt
    val airportRef = vals(1).toInt
    val airportIdent = vals(2)
    val length = toOption(vals(3)).map(_.toInt)
    val width = toOption(vals(4)).map(_.toInt)
    val surface = toOption(vals(5))
    val lighted = vals(6) == "1"
    val closed = vals(7) == "1"
    val le = DontKnow.fromCsv(vals.slice(8, 14))
    val he = DontKnow.fromCsv(vals.slice(14, 20))

    Runway(id, airportRef, airportIdent, length, width,
      surface, lighted, closed, le, he)
  }
}