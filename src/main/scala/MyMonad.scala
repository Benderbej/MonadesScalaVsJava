trait MyMonad[T <: Int] {

//  def bind[U](x: T)(f: T => MyMonad[U]): MyMonad[U] = {
//
//  }

  def flatMap[U <: Int](f: T => MyMonad[U]): MyMonad[U]

  def unit[T](x: T): MyMonad[T] = {
    MyMonadImplS(x + 5)
  }

}

object MyMonadImplS extends MyMonad {}

object MyMonadImplN extends MyMonad {}
