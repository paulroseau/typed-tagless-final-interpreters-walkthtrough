package exp

object DeserializationProblem {

  import Final._

  // Json-like format
  sealed trait Tree
  case class Leaf(value: String) extends Tree
  case class Node(value: String, children: List[Tree]) extends Tree

  val serializer: ExpSym[Tree] = new ExpSym[Tree] {
    def lit(n: Int): Tree = Node("Lit", List(Leaf(s"$n")))
    def neg(e: Tree): Tree = Node("Neg", List(e))
    def add(e1: Tree, e2: Tree): Tree = Node("Add", List(e1, e2))
  }

  type Error = String

  // The task is to write the function deserializer which converts a Tree to a
  // term that can be interpreted with any existing or future interpreter

  // analog to safeRead
  def parseInt(str: String): Either[Error, Int] =
    try {
      Right(str.toInt)
    } catch {
      case _: Exception => Left(s"$str could not be parsed to an Int")
    }

  def fromTree[R](t: Tree)(implicit expSym: ExpSym[R]): Either[Error, R] =
    t match {
      case Node("Lit", Leaf(n) :: Nil) =>
        parseInt(n).map(expSym.lit)
      case Node("Neg", child :: Nil) =>
        fromTree(child).map(e => expSym.neg(e))
      case Node("Add", child1 :: child2 :: Nil) =>
        for {
          e1 <- fromTree(child1)
          e2 <- fromTree(child2)
        } yield expSym.add(e1, e2)
      case _ => Left("Tree does not represent a valid expression")
    }

  // making serializer implicit makes `fromTree` and `deserializeAndInterpret`
  // be confused with implicit resolution (both the implicit argument `expSym`
  // and `serializer` would be valid)
  val serialized = test[Tree](serializer)

  def deserializeAndInterpret[R, S](t: Tree)(f: R => S)(implicit expSym: ExpSym[R]): Either[Error, S] =
    fromTree(t).map(f)

  val deserialized = deserializeAndInterpret[Int, Int](serialized)(x => x)

  def main(args: Array[String]): Unit = {
    println(s"Deserialization problem")
    println(s"serialized = $serialized")
    println(s"deserialized = $deserialized")
  }
}
