package model

case class Country(id: Int, code: String, name: String, continent: String,
                   wikipediaLink: String, keywords: Seq[String])

object Country {

  import util.Convert._

  def fromCsv(vals: Seq[String]): Country = {

    val id = vals(0).toInt
    val code = vals(1)
    val name = vals(2)
    val continent = vals(3)
    val wikiLink = vals(4)
    val keywords = toSeq(vals(5))

    Country(id, code, name, continent, wikiLink, keywords)
  }
}
