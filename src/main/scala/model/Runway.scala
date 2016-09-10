package model

case class Runway(id: Int, airportRef: Int, airportIdent: String,
                  lengthFt: Int, widthFt: Int, surface: String,
                  lighted: Boolean, closed: Boolean, leIdent: String,
                  leLatitudeDeg: Option[Double], leLongitudeDeg: Option[Double],
                  leElevationFt: Option[Int], leHeadingDegT: Option[Double],
                  leDisplacedThresholdFt: Option[Int], heIdent: Option[String],
                  heLatitudeDeg: Option[Double], heLongitudeDeg: Option[Double],
                  heElevationFt: Option[Int], heHeadingDegT: Option[Double],
                  heDisplacedThresholdFt: Option[Int])