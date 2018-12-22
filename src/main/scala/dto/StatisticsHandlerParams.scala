package dto

import annotations.Bind

case class StatisticsHandlerParams(@Bind("CTL")                 ctl               : String,
                                   @Bind("LOGIN")               login             : String,
                                   @Bind("PASS")                password          : String,
                                   @Bind("CURRENT_CTL_LOADING") currentCtlLoading : Long,
                                   @Bind("WF_ID")               wfId              : Option[Int])