package dto

import reflection.annotations.Bind

case class ConnectionParams(@Bind("CONNECTION_URL")    url      : String,
                            @Bind("LOGIN")             login    : String,
                            @Bind("PASS")              password : String,
                            @Bind("TIMEOUT")           timeout  : Long,
                            @Bind("TUNNEL_PORT")       tunnel   : Option[Int],
                            @Bind("USE_PROXY")         useProxy : Boolean)