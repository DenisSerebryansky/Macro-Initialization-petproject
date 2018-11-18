object Utils {

  def args2Map(args: Array[String]): Map[String, String] = {
    args.map { arg => val splitted = arg.split('='); splitted.head -> splitted.last }.toMap
  }
}