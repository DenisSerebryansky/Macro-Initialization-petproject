import dto.{ConnectionParams, TableNames}
import reflection.compiletime.MacroInitializer

object Main {

  def main(args: Array[String]): Unit = {

    val initializer = new MacroInitializer(args)

    println("\nPrint parameters from initializer:\n")
    initializer.printParameters(println)

    val connectionParams = initializer.init[ConnectionParams]
    val tableNames       = initializer.init[TableNames]

    println("\nInitialized classes:\n")

    println(tableNames)
    println(connectionParams)
  }
}