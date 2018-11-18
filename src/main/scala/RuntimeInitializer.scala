import scala.reflect.runtime.{universe => ru}

class RuntimeInitializer(argsMap: Map[String, String]) {

  private val mirror = ru.runtimeMirror(getClass.getClassLoader)

  def initialize[T: ru.TypeTag]: T = {

    val initializedClass = ru.typeOf[T].typeSymbol.asClass

    val ctor = ru.typeOf[T].decl(ru.termNames.CONSTRUCTOR).asMethod

    val ctorParameterNamesAndTypes = ctor.typeSignature.paramLists.head.map { ctorParamSymbol =>
      ctorParamSymbol.name.toString -> ctorParamSymbol.asTerm.typeSignature
    }

    val ctorParameterValuesTyped =
      for { (pName, pType) <- ctorParameterNamesAndTypes if argsMap contains pName } yield {

        val paramValueStr = argsMap(pName).toLowerCase

        pType match {
          case t if t =:= ru.typeOf[Long]    => paramValueStr.toLong
          case t if t =:= ru.typeOf[Int]     => paramValueStr.toInt
          case t if t =:= ru.typeOf[Boolean] => paramValueStr.toBoolean
          case _ => paramValueStr
        }
      }

    val classMirror = mirror.reflectClass(initializedClass)
    val ctorMirror  = classMirror.reflectConstructor(ctor)

    ctorMirror(ctorParameterValuesTyped: _*).asInstanceOf[T]
  }
}