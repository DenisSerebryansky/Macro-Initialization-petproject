package reflection.runtime

import reflection.annotations.Bind

import scala.reflect.runtime.universe._

class RuntimeReflectionInitializer(argsMap: Map[String, String]) {

  private case class CtorParameterInfo(name: String, annotationValue: Option[String], typeTag: Type)

  private val mirror = runtimeMirror(getClass.getClassLoader)

  private def getBindAnnotationValue(annotation: Option[Annotation]): Option[String] = {
    annotation.flatMap {
      _.tree.children.collectFirst { case Literal(Constant(value: String)) ⇒ value }
    }
  }

  def initialize[T: TypeTag]: T = {

    val initializedClass = typeOf[T].typeSymbol.asClass
    val ctor = typeOf[T].decl(termNames.CONSTRUCTOR).asMethod

    val ctorParameterValuesTyped =
      ctor.typeSignature.paramLists.head
        .map { ctorParamSymbol ⇒
          CtorParameterInfo(
            name            = ctorParamSymbol.name.toString,
            annotationValue = getBindAnnotationValue(ctorParamSymbol.annotations.find(_.tree.tpe =:= typeOf[Bind])),
            typeTag         = ctorParamSymbol.asTerm.typeSignature
          )
        }
        .map { case CtorParameterInfo(name, annotationValue, typeTag) ⇒

          val parameterValueStr = annotationValue.fold(argsMap(name))(argsMap)

          typeTag match {
            case t if t =:= typeOf[Long]    ⇒ parameterValueStr.toLong
            case t if t =:= typeOf[Int]     ⇒ parameterValueStr.toInt
            case t if t =:= typeOf[Boolean] ⇒ parameterValueStr.toBoolean
            case _ ⇒ parameterValueStr
          }
        }

    val classMirror = mirror.reflectClass(initializedClass)
    val ctorMirror  = classMirror.reflectConstructor(ctor)

    ctorMirror(ctorParameterValuesTyped: _*).asInstanceOf[T]
  }
}