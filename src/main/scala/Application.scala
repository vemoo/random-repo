object Application {
  def main(args: Array[String]): Unit = {
//    val res = CSV.parseLine(
//      """S,"Hola ""pepe,"", Mundo",E""",
//      ',')
//    println(res)
//    println(res.length)

    val uri = getClass().getResource("airports.csv").toURI
    println(uri)
    val res = CSV.parse(uri, ',')
    println(res.length)
  }
}
