import model._

import scala.util.Try

package object ui {

  trait Show[T] {
    def show(t: T): String
  }

  implicit val countryShow = new Show[Country] {
    def show(country: Country): String = s"${country.code} - ${country.name} (${country.continent})"
  }

  implicit val airportShow = new Show[Airport] {
    def show(airport: Airport): String = airport.name
  }

  implicit val runwayShow = new Show[Runway] {
    def show(runway: Runway): String = {
      val dim = Seq(
        runway.widthFt.map("width: " + _.toString + " ft"),
        runway.lengthFt.map("length: " + _.toString + " ft")
      ).flatten.mkString(", ")
      if (dim.isEmpty)
        runway.id.toString
      else
        runway.id + " - " + dim
    }
  }

  implicit val resultShow = new Show[(Airport, Seq[Runway])] {
    def show(res: (Airport, Seq[Runway])): String = {
      val (airport, runways) = res
      val sb = new StringBuilder
      sb.append("-" + airportShow.show(airport) + "\n")
      if (runways.isEmpty)
        sb.append("\tNo runways\n")
      else {
        sb.append("\tRunways: \n")
        for (r <- runways) {
          sb.append("\t-" + runwayShow.show(r) + "\n")
        }
      }
      sb.toString()
    }
  }

  implicit def seqShow[T](implicit st: Show[T]) = new Show[Seq[T]] {
    def show(seq: Seq[T]): String =
      seq.map(st.show).mkString("\n")
  }

  implicit def stringShow = new Show[String] {
    def show(str: String): String = str
  }


  sealed trait Action
  object EnterCode extends Action
  object SearchByName extends Action
  object Exit extends Action

  implicit val actionShow = new Show[Action] {
    def show(action: Action): String = action match {
      case SearchByName => "Search by name"
      case EnterCode => "Enter code"
      case Exit => "Exit"
    }
  }

  def chooseFrom[T](first: T, rest: T*)(implicit st: Show[T]): T = chooseFrom(first +: rest)(st)

  def chooseFrom[T](source: Seq[T])(implicit st: Show[T]): T = {
    for ((el, i) <- source.zipWithIndex) {
      println(s"$i) ${st.show(el)}:")
    }
    var selection: Int = -1
    while (!source.indices.contains(selection)) {
      print("Selection: ")
      selection = Try(io.StdIn.readLine().toInt).getOrElse(-1)
    }
    source(selection)
  }

  def showConsole[T](t: T)(implicit st: Show[T]) = println(st.show(t))
}
