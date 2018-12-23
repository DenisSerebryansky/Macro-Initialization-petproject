package dto

import reflection.annotations.Bind

case class TableNames(@Bind("SOURCE_FIRST_TABLE")  firstTable  : String,
                      @Bind("SOURCE_SECOND_TABLE") secondTable : String,
                      @Bind("SOURCE_THIRD_TABLE")  thirdTable  : String,
                      @Bind("SOURCE_FOURTH_TABLE") fourthTable : String)