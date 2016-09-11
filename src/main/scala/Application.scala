import model._
import util._

object Application {
  def main(args: Array[String]): Unit = {
    val uri = getClass.getResource("countries.csv").toURI
    println(uri)
    val raw = CSV.parse(uri, ',')
    println(Analize.findOptionals(raw))
    val res = raw.tail.map(Country.fromCsv)
    println(res.length)
    println(res.last)
  }
}
