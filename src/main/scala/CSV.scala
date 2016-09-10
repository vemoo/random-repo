import java.net.URI


object CSV {
  def parse(path: URI, delim: Char): Vector[Vector[String]] = {
    io.Source.fromFile(path).getLines()
      .map(l => parseLine(l, delim))
      .toVector
  }

  def parseLine(line: String, delim: Char): Vector[String] = {
    val sb = new StringBuilder
    val vb = Vector.newBuilder[String]

    var pos = 0
    var inQuote = false

    while (pos < line.length) {
      val c = line(pos)
      if (inQuote)
        c match {
          case '"' =>
            if (pos + 1 < line.length && line(pos + 1) == '"') {
              //escaped quote
              sb.append(c)
              pos += 1
            } else //end of quote
              inQuote = false
          case _ =>
            sb.append(c)
        }
      else
        c match {
          case `delim` =>
            //delimiter, add word and restart
            vb += sb.toString()
            sb.clear()
          case '"' => //start of quote
            inQuote = true
          case _ =>
            sb.append(c)
        }
      pos += 1
    }

    vb += sb.toString()
    sb.clear()

    vb.result()
  }
}
