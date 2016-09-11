import org.scalatest.FunSuite
import util.CSV._

class CSVSpec extends FunSuite {

  def check(actual: Seq[String], expected: Seq[String]): Unit = {
    assert(actual == expected, s"$actual did not equal $expected")
  }

  test("Line without delimiters should return sigle element") {
    check(parseLine("foo", ','), Seq("foo"))
  }

  test("Should no split quoted delimiters") {
    val res = parseLine("\"foo|bar|baz\"|foo|bar|baz", '|')
    check(res, Seq("foo|bar|baz", "foo", "bar", "baz"))
  }

  test("Should interpret ecaped quotes") {
    val res = parseLine("\"some \"\"quoted\"\" text\",\"\"\"\",123", ',')
    check(res, Seq("some \"quoted\" text", "\"", "123"))
  }
}
