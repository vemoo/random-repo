package model

case class Airport(id: Int, ident: String, name: String,
                   latitudeDeg: Double, longitudeDeg: Double, elevationFt: Int,
                   continent: String, isoCountry: String, isoRegion: String, municipality: String,
                   scheduledService: String, gpsCode: String, iataCode: String,
                   homeLink: String, wikipediaLink: String, keywords: List[String])
