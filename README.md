## Macro initializer of case classes

This project can be useful when it is necessary to pass many arguments into your application.
Macro initializer allows to group params by entities as case classes.

### Usage example

Lets consider your application has the following arguments list:

`java -jar MyApp.jar CONNECTION_URL=connection_url LOGIN=login PASS=password TIMEOUT=1234 TUNNEL_PORT=111 USE_PROXY=true`

First of all, you have to define a case class which represents parameters group:

```
import reflection.annotations.Bind

case class ConnectionParams(@Bind("CONNECTION_URL")    url      : String,
                            @Bind("LOGIN")             login    : String,
                            @Bind("PASS")              password : String,
                            @Bind("TIMEOUT")           timeout  : Long,
                            @Bind("TUNNEL_PORT")       tunnel   : Option[Int],
                            @Bind("USE_PROXY")         useProxy : Boolean)
```

Annotation `@Bind` allows to bind field name of case class with key in arguments map.
Macro initializer searches for a field value in arguments map firstly by its annotation value and then by its name.

Then you have to create a `MacroInitializer` instance and use its method `init` in order to instantiate your case class:

```
import reflection.compiletime.MacroInitializer

object Main {

  def main(args: Array[String]): Unit = {

    val initializer = new MacroInitializer(args)

    println("Print parameters from initializer:\n")
    initializer.printParameters(println)

    val connectionParams = initializer.init[ConnectionParams]

    println(connectionParams)
  }
}
```

Result:
```
Print parameters from initializer:

CONNECTION_URL = connection_url
LOGIN          = login
PASS           = password
TIMEOUT        = 1234
TUNNEL_PORT    = 111
USE_PROXY      = true

ConnectionParams(connection_url,login,password,1234,Some(111),true)
```