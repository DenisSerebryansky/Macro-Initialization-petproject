import dto.{StatisticsHandlerParams, TableNames}

object Main {

  def main(args: Array[String]): Unit = {

    val argsMap = Utils.args2Map(args)

    println("Args map:\n")
    argsMap.foreach(println)

    println("\n\nInitialized classes:\n")

    val runtimeInitializer = new RuntimeReflectionInitializer(argsMap)

    val shParams = runtimeInitializer.initialize[StatisticsHandlerParams]
    println(shParams)

    val tableNames = runtimeInitializer.initialize[TableNames]
    println(tableNames)
  }
}