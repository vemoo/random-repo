package util

object Convert {
  def toOption(value: String): Option[String] =
    if (value.isEmpty) None else Some(value)

  def toSeq(value: String, separator: Char = ','): Seq[String] =
    value.split(separator).filterNot(_ == "").toVector
}