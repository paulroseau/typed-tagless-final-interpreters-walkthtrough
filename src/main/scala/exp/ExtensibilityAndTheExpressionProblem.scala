package exp

// Expression problem :
//
// Library :
// sealed trait T
// case object T1 extends T
// case object T2 extends T
//
// Code :
// It is easy to add other method that handle Ts
// def evalA(t: T): A = ???
// But what if we want to make some type T3 a T as well ? => expression problem

object Final2 {

  // Same remark as for ExpSym about not sealing this trait
  trait MulSym[R] {
    def mul(e1: R, e2: R): R
  }

  object MulSym {
    implicit def intIntr: MulSym[Int] = new MulSym[Int] {
      def mul(e1: Int, e2: Int): Int = e1 * e2
    }
    implicit def strIntr: MulSym[String] = new MulSym[String] {
      def mul(e1: String, e2: String): String = s"($e1) * ($e2)"
    }
  }

  def test[R](implicit exp: Final.ExpSym[R], mul: MulSym[R]): R = 
    exp.add(exp.lit(7), exp.neg(mul.mul(exp.lit(1), exp.lit(2))))

  val intTest = test[Int]
  val strTest = test[String]

  def main(args: Array[String]): Unit = {
    println(s"Final2 :")
    println(s"intTest = $intTest")
    println(s"strTest = $strTest")
  }
}



