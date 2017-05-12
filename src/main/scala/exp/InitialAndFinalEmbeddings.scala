package exp

object Initial {

  // This trait has to be sealed since adding other instances of Exp in client
  // code would break the implementation of the folloing interpretors with
  // MatchErrors
  sealed trait Exp
  case class Lit(n: Int) extends Exp
  case class Neg(e: Exp) extends Exp
  case class Add(e1: Exp, e2: Exp) extends Exp

  final def intIntr(e: Exp): Int = e match {
    case Lit(n) => n
    case Neg(e0) => -intIntr(e0)
    case Add(e1, e2) => intIntr(e1) + intIntr(e2)
  }

  final def strIntr(e: Exp): String = e match {
    case Lit(n) => s"$n"
    case Neg(e0) => s"-(${strIntr(e0)})"
    case Add(e1, e2) => s"(${strIntr(e1)}) + (${strIntr(e2)})"
  }

  val exp = Add(Lit(1), Neg(Lit(-2)))
  val intTest = intIntr(exp)
  val strTest = strIntr(exp)

  def main(args: Array[String]): Unit = {
    println(s"Initial :")
    println(s"intTest = $intTest")
    println(s"strTest = $strTest")
  }
}

object Final {
  // This trait does not need to be sealed anymore since we precisely want user
  // code to create new instances to interpret it.
  trait ExpSym[R] {
    def lit(n: Int): R
    def neg(e: R): R
    def add(e1: R, e2: R): R
  }

  object ExpSym {

    implicit val intIntr: ExpSym[Int] = new ExpSym[Int] {
      def lit(n: Int): Int = n
      def neg(e: Int): Int = -e
      def add(e1: Int, e2: Int): Int = e1 + e2
    }

    implicit val strIntr: ExpSym[String] = new ExpSym[String] {
      def lit(n: Int): String = s"$n"
      def neg(e0: String): String = s"-($e0)"
      def add(e1: String, e2: String): String = s"($e1) + ($e2)"
    }
  }

  def test[R](implicit exp: ExpSym[R]): R =
    exp.add(exp.lit(1), exp.neg(exp.lit(-2)))

  val intTest = test[Int]
  val strTest = test[String]

  def main(args: Array[String]): Unit = {
    println(s"Final :")
    println(s"intTest = $intTest")
    println(s"strTest = $strTest")
  }
}
