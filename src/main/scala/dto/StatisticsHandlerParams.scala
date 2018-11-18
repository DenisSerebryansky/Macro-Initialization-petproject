package dto

case class StatisticsHandlerParams(CTL: String,
                                   LOGIN: String,
                                   PASS: String,
                                   CURRENT_CTL_LOADING: Long,
                                   WF_ID: Int)