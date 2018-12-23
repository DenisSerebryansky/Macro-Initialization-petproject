import dto.{StatisticsHandlerParams, TableNames}
import reflection.compiletime.MacroInitializer

object Main {

  def main(args: Array[String]): Unit = {

    val argsMap = Utils.args2Map(args)

    println("Args map:\n")
    argsMap.foreach(println)

    println("\nInitialized classes:\n")

    val initializer = new MacroInitializer(argsMap)

    val shParams   = initializer.init[StatisticsHandlerParams]
    val tableNames = initializer.init[TableNames]

    println(tableNames)
    println(initializer.toString(shParams))
  }
}