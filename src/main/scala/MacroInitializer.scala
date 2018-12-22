//import scala.language.experimental.macros
//import scala.reflect.macros.blackbox
//
//object MacroInitializer {
//
//  def initialize[A]: A = macro initializeImpl[A]
//
//  val argsMap: Map[String, String] =
//    Map(
//      "SOURCE_INTEGRATOR_PAYMENT" → "core_internal_cod.integrator_payment",
//      "SOURCE_DEPOSIT_PD" → "core_internal_cod.deposit_pd",
//      "SOURCE_DEPOSIT_DEPOHIST" → "core_internal_cod.deposit_depohist",
//      "SOURCE_EVENT_LOG" → "custom_rsa_pc.event_log",
//      "CTL" → "ctl_url",
//      "LOGIN" → "ctl_login",
//      "PASS" → "password",
//      "CURRENT_CTL_LOADING" → "1234",
//      "WF_ID" → "111"
//    )
//
//  def initializeImpl[A](c: blackbox.Context)(A: c.WeakTypeTag[A]): c.Expr[A] = {
//
//    import c.universe._
//
//    case class CtorParameterInfo(name: String, annotationValue: Option[String], typeTag: Type)
//
//    def getAnnotationValues(annotation: Option[c.universe.Annotation]): Option[String] = {
//      annotation.flatMap {
//        _.tree.children.collectFirst { case Literal(Constant(value: String)) ⇒ value }
//      }
//    }
//
//    val tpe = weakTypeOf[dto.TableNames]
//
//    val ctorParams =
//      tpe.decls.collectFirst { case m: MethodSymbol if m.isPrimaryConstructor ⇒ m }
//        .get.paramLists.head
//
//    val ctorParameterValuesTyped =
//      ctorParams.map { param ⇒
//        CtorParameterInfo(
//          name            = param.name.toString,
//          annotationValue = getAnnotationValues(param.annotations.find(_.tree.tpe =:= typeOf[annotations.Bind])),
//          typeTag         = param.asTerm.typeSignature
//        )
//      }
//        .map { case CtorParameterInfo(name, annotationValue, typeTag) ⇒
//
//          val parameterValueStr = annotationValue.fold(argsMap(name))(argsMap)
//
//          typeTag match {
//            case t if t =:= typeOf[Long]    ⇒ parameterValueStr.toLong
//            case t if t =:= typeOf[Int]     ⇒ parameterValueStr.toInt
//            case t if t =:= typeOf[Boolean] ⇒ parameterValueStr.toBoolean
//            case _ ⇒ parameterValueStr
//          }
//        }
//
//    c.Expr[A](q"new $A(${ctorParameterValuesTyped.mkString(", ")})")
//  }
//}
