package dto

import annotations.Bind

case class TableNames(@Bind("SOURCE_INTEGRATOR_PAYMENT") integratorPayment: String,
                      @Bind("SOURCE_DEPOSIT_PD")         depositPd: String,
                      @Bind("SOURCE_DEPOSIT_DEPOHIST")   depositDepohist: String,
                      @Bind("SOURCE_EVENT_LOG")          eventLog: String)