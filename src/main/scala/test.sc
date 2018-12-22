import compiletime.MacroInitializer
import dto.TableNames

//val map =
//  Map[String, Any](
//    "SOURCE_INTEGRATOR_PAYMENT" → "core_internal_cod.integrator_payment",
//    "SOURCE_DEPOSIT_PD" → "core_internal_cod.deposit_pd",
//    "SOURCE_DEPOSIT_DEPOHIST" → "core_internal_cod.deposit_depohist",
//    "SOURCE_EVENT_LOG" → "custom_rsa_pc.event_log",
//    "CTL" → "ctl_url",
//    "LOGIN" → "ctl_login",
//    "PASS" → "password",
//    "CURRENT_CTL_LOADING" → "1234",
//    "WF_ID" → "111"
//  )

val map =
  Map[String, Any](
    "SOURCE_INTEGRATOR_PAYMENT" → "core_internal_cod.integrator_payment",
    "SOURCE_DEPOSIT_PD" → "core_internal_cod.deposit_pd",
    "SOURCE_DEPOSIT_DEPOHIST" → "core_internal_cod.deposit_depohist",
    "SOURCE_EVENT_LOG" → "custom_rsa_pc.event_log",
    "CTL" → "ctl_url",
    "LOGIN" → "ctl_login",
    "PASS" → "password",
    "CURRENT_CTL_LOADING" → 1234,
    "WF_ID" → 111
  )

val initializer = new MacroInitializer(map)

initializer.initialize[TableNames]