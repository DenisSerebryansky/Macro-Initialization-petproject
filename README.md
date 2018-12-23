## Macro initializer of case classes

This project can be useful when it is necessary to pass many arguments into your application.
Macro initializer allows to group params by entities as case classes.

### Installation

Compile project and publish it (for example) locally:

```
sbt publishLocal
```

Then you can use it in your projects by adding the following line in the library dependencies:

```
libraryDependencies += "org.serebryansky" % "macro-initialization" % 0.1-SNAPSHOT
```

### Usage example

Lets consider your application has the following arguments list:

`java -jar MyApp.jar SOURCE_FIRST_TABLE=default.first_table SOURCE_SECOND_TABLE=default.second_table SOURCE_THIRD_TABLE=default.third_table 
SOURCE_FOURTH_TABLE=default.fourth_table CONNECTION_URL=connection_url LOGIN=login PASS=password TIMEOUT=1234 TUNNEL_PORT=111 USE_PROXY=true`

In order to conveniently use them, you can group them by case classes.
First of all, you have to define a case classes which represent parameters group:

```
import reflection.annotations.Bind

case class ConnectionParams(@Bind("CONNECTION_URL")    url      : String,
                            @Bind("LOGIN")             login    : String,
                            @Bind("PASS")              password : String,
                            @Bind("TIMEOUT")           timeout  : Long,
                            @Bind("TUNNEL_PORT")       tunnel   : Option[Int],
                            @Bind("USE_PROXY")         useProxy : Boolean)

case class TableNames(@Bind("SOURCE_FIRST_TABLE")  firstTable  : String,
                      @Bind("SOURCE_SECOND_TABLE") secondTable : String,
                      @Bind("SOURCE_THIRD_TABLE")  thirdTable  : String,
                      @Bind("SOURCE_FOURTH_TABLE") fourthTable : String)
                          
```

Annotation `@Bind` allows to bind field name of case class with key in arguments map.
Macro initializer searches for a field value in arguments map firstly by its annotation value and then by its name.

Then you have to create a `MacroInitializer` instance and use its method `init` in order to instantiate your case class which contains appropriate parameters:

```
import reflection.compiletime.MacroInitializer

object Main {

  def main(args: Array[String]): Unit = {

    val initializer = new MacroInitializer(args)

    println("Print parameters from initializer:")
    initializer.printParameters(println)

    val connectionParams = initializer.init[ConnectionParams]
    val tableNames       = initializer.init[TableNames]

    println("Initialized classes:")

    println(tableNames)
    println(connectionParams)
  }
}
```

Result:
```
Print parameters from initializer:

CONNECTION_URL      = connection_url
LOGIN               = login
PASS                = password
SOURCE_FIRST_TABLE  = default.first_table
SOURCE_FOURTH_TABLE = default.fourth_table
SOURCE_SECOND_TABLE = default.second_table
SOURCE_THIRD_TABLE  = default.third_table
TIMEOUT             = 1234
TUNNEL_PORT         = 111
USE_PROXY           = true

Initialized classes:

TableNames(default.first_table,default.second_table,default.third_table,default.fourth_table)
ConnectionParams(connection_url,login,password,1234,Some(111),true)
```