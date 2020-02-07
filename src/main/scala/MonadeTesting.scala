object MonadeTesting extends App {

  //SRC
  //https://habr.com/ru/post/209510/

  val xxx = Option(4)

  def f1(i: Int): Int = {
    i + 1
  }

  def f2(i: Int): Int = {
    i * 2
  }

  println(xxx.map(z => f1(z)).map(z => f2(z)))
  println(xxx.map(z => f2(z)).map(z => f1(z)))
//  assert(x.map(z => f1(z)).map(z => f2(z)) == x.map(z => f2(z)).map(z => f1(z)))
//
//  x.map(z => z.map)
//  assert(x.flatMap(z => f1(z)) == f1(x.get))

  def squareFunction(x: Int): Option[Int] = Some(x * x)
  def incrementFunction(x: Int): Option[Int] = Some(x + 1)

//LEFT IDENTITY (left unit law)
//unit(x) flatMap f == f(x)
  def leftIdentity() {
    val x = 5
    val monad: Option[Int] = Some(x) // unit
    val result = monad.flatMap(squareFunction) == squareFunction(x)
    println(result)
  }

//RIGHT IDENTITY (right unit law)
//monad flatMap unit == monad
  def rightIdentity() {
    val x = 5
    val monad: Option[Int] = Some(x)
    val result = monad.flatMap(x => Some(x)) == monad
    println(result)
  }

//COMPOSITION
//(monad flatMap f) flatMap g == monad flatMap(x => f(x) flatMap g)
  def composition() {
    val x = 5
    val monad: Option[Int] = Some(x)
    val left = monad flatMap squareFunction flatMap incrementFunction
    val right = monad flatMap (x => squareFunction(x) flatMap incrementFunction)
    assert(left == right)
  }
  //https://habr.com/ru/post/209510/

//  И этот соблюдение этого закона дает нам право использовать for comprehension в обычном для нас виде, то есть вместо:
//  for (square <- for (x <- monad; sq <- squareFunction(x)) yield sq;
//       result <- incrementFunction(square)) yield result

//  Мы можем записать:
//  for (x <- monad;
//       square <- squareFunction(x);
//       result <- incrementFunction(square)) yield result

}
