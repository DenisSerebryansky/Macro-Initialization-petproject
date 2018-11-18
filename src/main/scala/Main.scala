import dto.{StatisticsHandlerParams, TableNamesCapitalized}

object Main {

  def main(args: Array[String]): Unit = {

    val argsMap = Utils.args2Map(args)

    argsMap.foreach(println)

    val runtimeInitializer = new RuntimeInitializer(argsMap)

    val tableNames = runtimeInitializer.initialize[TableNamesCapitalized]
    println(tableNames)

    val shParams = runtimeInitializer.initialize[StatisticsHandlerParams]
    println(shParams)

  }
}