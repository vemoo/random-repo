package util

/**
  * Functions to analize the CSV data, and find the apropiate types
  * for each field
  */
object Analize {

  /**
    * Finds optional fields in the csv
    *
    * @param csv
    * @return header names of the optional fields
    */
  def findOptionals(csv: Seq[Seq[String]]): Set[String] = {
    val header = csv.head
    val indices = csv.tail.foldLeft(Set[Int]()) { (acc, line) =>
      acc ++ line.zipWithIndex.filter(_._1.isEmpty).map(_._2)
    }
    indices.map(header(_))
  }

  def findRange(csv: Seq[Seq[String]], col: Int): Set[String] = {
    csv.tail.map(l => l(col)).toSet
  }
}
