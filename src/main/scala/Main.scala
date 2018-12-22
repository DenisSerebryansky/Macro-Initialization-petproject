import compiletime.MacroInitializer
import compiletime.Mappable._
import dto.TableNames

object Main {

  case class Item(name: String, price: Double)

  def main(args: Array[String]): Unit = {

    val argsMap = Utils.args2Map(args)

    println("Args map:\n")
    argsMap.foreach(println)

    println("\n\nInitialized classes:\n")

//    val runtimeInitializer = new RuntimeReflectionInitializer(argsMap)
//
//    val shParams = runtimeInitializer.initialize[StatisticsHandlerParams]
//    println(shParams)
//
//    val tableNames = runtimeInitializer.initialize[TableNames]
//    println(tableNames)


    //val macroInitializer = new MacroInitializer/*(argsMap)*/


    //val tableNames = MacroInitializer.initialize[TableNames](argsMap)
//    val tableNames = MacroInitializer.initialize[TableNames]
//
//    println(tableNames)

    val argsMap1 =
      Map(
        "name" -> "dinner2",
        "price" -> 25.8
      )

    //val initializer = new MacroInitializer(argsMap)
    val initializer = new MacroInitializer(argsMap1)

    val tableNames = initializer.initialize[Item]
    //val shParams = initializer[StatisticsHandlerParams]

    //println(shParams)
    println(tableNames)
  }
}