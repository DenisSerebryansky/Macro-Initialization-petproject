package reflection.compiletime

class MacroInitializer(map: Map[String, String]) {

  def init[T: Mappable]: T = implicitly[Mappable[T]].fromMap(map)

  def mapify[T: Mappable](t: T): Map[String, Any] = implicitly[Mappable[T]].toMap(t)

  def toString[T: Mappable](t: T): String = {

    val contentMap       = implicitly[Mappable[T]].toMap(t)
    val longestFieldName = contentMap.maxBy { case (key, _) ⇒ key.length }._1.length

    s"""
       |${t.getClass.getSimpleName.init}(
       |  ${contentMap.map { case (key, value) ⇒ s"${key.padTo(longestFieldName, " ").mkString} -> $value" }.mkString("\n  ")}
       |)
     """.stripMargin
  }
}