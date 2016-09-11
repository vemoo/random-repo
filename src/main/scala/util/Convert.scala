package util

object Convert {
  def toOption(str: String): Option[String] =
    if (str.isEmpty) None else Some(str)


}