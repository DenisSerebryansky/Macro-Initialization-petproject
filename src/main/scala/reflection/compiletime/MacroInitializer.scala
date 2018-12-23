package reflection.compiletime

class MacroInitializer(args: Array[String]) {

  val argsMap: Map[String, String] = args2Map

  private def args2Map: Map[String, String] = {
    args.map { arg ⇒ val splitted = arg.split('='); splitted.head → splitted.last }.toMap
  }

  def init[T: Mappable]: T = implicitly[Mappable[T]].fromMap(argsMap)

  def toMap[T: Mappable](t: T): Map[String, Any] = implicitly[Mappable[T]].toMap(t)

  def printParameters(printFunction: String ⇒ Unit): Unit = {

    val longestFieldName = argsMap.maxBy { case (key, _) ⇒ key.length }._1.length

    argsMap
      .map { case (key, value) ⇒ s"${key.padTo(longestFieldName, " ").mkString} = $value" }
      .toList
      .sorted
      .foreach(printFunction)
  }
}