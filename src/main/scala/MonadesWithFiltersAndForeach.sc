//https://docs.scala-lang.org/tour/self-types.html

sealed trait MaybeInt { self =>
  def map(f: Int => Int): MaybeInt
  def flatMap(f: Int => MaybeInt): MaybeInt
  def filter(f: Int => Boolean): MaybeInt
  def foreach[U](f: Int => U): Unit
  def withFilter(p: Int => Boolean): WithFilter = new WithFilter(p)

  // Based on Option#withFilter
  class WithFilter(p: Int => Boolean) {
    def map(f: Int => Int): MaybeInt = self filter p map f
    def flatMap(f: Int => MaybeInt): MaybeInt = self filter p flatMap f
    def foreach[U](f: Int => U): Unit = self filter p foreach f
    def withFilter(q: Int => Boolean): WithFilter =
      new WithFilter(x => p(x) && q(x))
  }
}

case class SomeInt(i: Int) extends MaybeInt {
  def map(f: Int => Int): MaybeInt = SomeInt(f(i))
  def flatMap(f: Int => MaybeInt): MaybeInt = f(i)
  def filter(f: Int => Boolean): MaybeInt = if (f(i)) this else NoInt
  def foreach[U](f: Int => U): Unit = f(i)
}

case object NoInt extends MaybeInt {
  def map(f: Int => Int): MaybeInt = NoInt
  def flatMap(f: Int => MaybeInt): MaybeInt = NoInt
  def filter(f: Int => Boolean): MaybeInt = NoInt
  def foreach[U](f: Int => U): Unit = ()
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

//Additionally, you can add foreach and filter. If you want to also handle this (no yield):
for(a <- maybe) println(a)

//You would add foreach. And if you want to use if guards:
for(a <- maybe if a > 2) yield a

//You would need filter or withFilter