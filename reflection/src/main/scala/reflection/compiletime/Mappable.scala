package reflection.compiletime

import reflection.annotations

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

trait Mappable[T] {
  def toMap(t: T): Map[String, Any]
  def fromMap(map: Map[String, String]): T
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

    def getPrimitiveTypeConvertMethod(typeSignature: Type): TermName = {
      TermName(
        typeSignature match {
          case t if t =:= typeOf[Int]     ⇒ "toInt"
          case t if t =:= typeOf[Long]    ⇒ "toLong"
          case t if t =:= typeOf[Double]  ⇒ "toDouble"
          case t if t =:= typeOf[Boolean] ⇒ "toBoolean"
          case _                          ⇒ "toString"
        }
      )
    }

    def getFieldValueTree(fieldName: String, typeSignature: Type): Tree = {
      typeSignature match {
        case optionalType if optionalType <:< typeOf[Option[_]] ⇒
          q"map.get($fieldName).map(_.${getPrimitiveTypeConvertMethod(optionalType.typeArgs.head)})"
        case primitiveType ⇒
          q"map($fieldName).${getPrimitiveTypeConvertMethod(primitiveType)}"
      }
    }

    val tpe       = weakTypeOf[T]
    val companion = tpe.typeSymbol.companion

    val fields =
      tpe.decls.collectFirst { case m: MethodSymbol if m.isPrimaryConstructor ⇒ m }
        .get.paramLists.head

    val (toMapParams, fromMapParams) = fields.map { field ⇒

      val nameAsTerm          = field.asTerm.name
      val nameAsString        = nameAsTerm.decodedName.toString
      val typeSignature       = tpe.decl(nameAsTerm).typeSignature.finalResultType
      val annotationValue     = getAnnotationValue(field.annotations.find(_.tree.tpe =:= typeOf[annotations.Bind]))
      val refinedNameAsString = annotationValue getOrElse nameAsString

      (q"$nameAsString -> t.$nameAsTerm", getFieldValueTree(refinedNameAsString, typeSignature))

    }.unzip

    c.Expr[Mappable[T]] { q"""
      new reflection.compiletime.Mappable[$tpe] {
        def toMap(t: $tpe): Map[String, Any] = Map(..$toMapParams)
        def fromMap(map: Map[String, String]): $tpe = $companion(..$fromMapParams)
      }
    """ }
  }
}