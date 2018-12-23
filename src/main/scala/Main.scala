import dto.{ConnectionParams, TableNames}
import reflection.compiletime.MacroInitializer

object Main {

  def main(args: Array[String]): Unit = {

    val argsMap = Utils.args2Map(args)

    println("Args map:\n")
    argsMap.foreach(println)

    val initializer = new MacroInitializer(argsMap)

    println("\nPrint parameters from initializer:\n")
    initializer.printParameters(println)

    val connectionParams = initializer.init[ConnectionParams]
    val tableNames       = initializer.init[TableNames]

    println("\nInitialized classes:\n")

    println(tableNames)
    println(connectionParams)
  }
}