package compiletime

class MacroInitializer(map: Map[String, Any]) {

  def initialize[T: Mappable]: T = implicitly[Mappable[T]].fromMap(map)

  def mapify[T: Mappable](t: T): Map[String, Any] = implicitly[Mappable[T]].toMap(t)

  def apply[T: Mappable]: T = initialize[T]
}