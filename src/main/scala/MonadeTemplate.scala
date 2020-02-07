trait MonadeTemplate[T] {

  def flatMap[U](f: T => MonadeTemplate[U]): MonadeTemplate[U]

  def unit[T](x: T): MonadeTemplate[T]

}
