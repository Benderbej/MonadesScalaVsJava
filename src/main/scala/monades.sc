sealed trait MaybeInt {
  def map(f: Int => Int): MaybeInt
  def flatMap(f: Int => MaybeInt): MaybeInt
}

case class SomeInt(i: Int) extends MaybeInt {
  def map(f: Int => Int): MaybeInt = SomeInt(f(i))
  def flatMap(f: Int => MaybeInt): MaybeInt = f(i)
}

case object NoInt extends MaybeInt {
  def map(f: Int => Int): MaybeInt = NoInt
  def flatMap(f: Int => MaybeInt): MaybeInt = NoInt
}


val maybe = SomeInt(1)
val no = NoInt

for {
  a <- maybe
  b <- no
} yield a + b

for {
  a <- maybe
  b <- maybe
} yield a + b




@inline final def flatMap[B](f: A => Option[B]): Option[B] =
  if (isEmpty) None else f(this.get)