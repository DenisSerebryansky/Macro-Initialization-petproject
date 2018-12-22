package compiletime

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

trait Mappable[T] {
  def toMap(t: T): Map[String, Any]
  def fromMap(map: Map[String, Any]): T
}

object Mappable {

  implicit def materializeMappable[T]: Mappable[T] = macro materializeMappableImpl[T]

  def materializeMappableImpl[T: c.WeakTypeTag](c: blackbox.Context): c.Expr[Mappable[T]] = {

    import c.universe._

    def getAnnotationValue(annotation: Option[c.universe.Annotation]): Option[String] = {
      annotation.flatMap {
        _.tree.children.collectFirst { case Literal(Constant(value: String)) ⇒ value }
      }
    }

    val tpe       = weakTypeOf[T]
    val companion = tpe.typeSymbol.companion

    val fields =
      tpe.decls.collectFirst { case m: MethodSymbol if m.isPrimaryConstructor ⇒ m }
        .get.paramLists.head

    val (toMapParams, fromMapParams) = fields.map { field ⇒

      val name         = field.asTerm.name
      val key          = name.decodedName.toString
      val returnType   = tpe.decl(name).typeSignature
      val annotation   = getAnnotationValue(field.annotations.find(_.tree.tpe =:= typeOf[annotations.Bind]))
      val annotatedKey = annotation getOrElse key

      println(s"returnType = $returnType")

      (q"$key -> t.$name", q"map($annotatedKey).asInstanceOf[$returnType]")

    }.unzip

    c.Expr[Mappable[T]] { q"""
      new initialization.Mappable[$tpe] {
        def toMap(t: $tpe): Map[String, Any] = Map(..$toMapParams)
        def fromMap(map: Map[String, Any]): $tpe = $companion(..$fromMapParams)
      }
    """ }
  }
}