import java.sql.Timestamp

import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.{DataType, DataTypes, StructField, StructType}

/**
 * @author jleo
 * @date 2020/7/7
 */
object Splice {

  val spark = SparkSession.builder()
    .master("local")
    .appName("recommend")
    .getOrCreate()

  val splice2_schema = StructType(List(
    StructField("departureAirportTlc", DataTypes.StringType),
    StructField("departureAirportName", DataTypes.StringType),
    StructField("arrivalAirportTlc", DataTypes.StringType),
    StructField("arrivalAirportName", DataTypes.StringType),
    StructField("departureDate", DataTypes.TimestampType),
    StructField("arrivalDate", DataTypes.TimestampType),
    StructField("firstFlightNumber", DataTypes.StringType),
    StructField("secondFlightNumber", DataTypes.StringType),
    StructField("transferStationTlc", DataTypes.StringType),
    StructField("transferStationName", DataTypes.StringType),
    StructField("price", DataTypes.DoubleType)))
  var splice2 = spark.createDataFrame(spark.sparkContext.emptyRDD[Row], splice2_schema)

  def main(args: Array[String]): Unit = {

    System.getProperties.setProperty("HADOOP_USER_NAME", "suncaper")

    val df = spark.sqlContext.read.option("header", "true").option("inferSchema", "true")
      .csv("spark/ticket.csv")

    //df.printSchema()

    df.createGlobalTempView("ticket")
    spark.sql("select * from global_temp.ticket where departureCityTlc = 'AAT'").collect()



    val list = spark.sql(
      """select distinct departureAirportTlc, departureAirportName, arrivalAirportTlc, arrivalAirportName, departureDate
        |from global_temp.ticket """.stripMargin)

    list.collect().foreach(line => {
      //selectSplice(line.get(0).toString, line.get(1).toString, Timestamp.valueOf(line.get(2).toString))
      val departureAirportTlc = line.get(0).toString
      val arrivalAirportTlc = line.get(2).toString
      val departureDate = Timestamp.valueOf(line.get(4).toString)
      var minPrice = spark.sql(
        s"""select min(price) from global_temp.ticket
          |where departureAirportTlc = '$departureAirportTlc'
          |and arrivalAirportTlc = '$arrivalAirportTlc'
          |and departureDate = timestamp('$departureDate')""".stripMargin).collect()(0).get(0).toString.toDouble
      print(minPrice)
      //getFirstFlight(departureAirportTlc, arrivalAirportTlc, minPrice)
      val list1 = spark.sql(
        s"""select flightNumber, arrivalAirportTlc, arrivalDate, price from global_temp.ticket
           |where departureAirportTlc = '$departureAirportTlc'
           |and arrivalAirportTlc != '$arrivalAirportTlc'
           |and price < '$minPrice'""".stripMargin)

      list1.collect().foreach(line1 => {
        //getSecondFlight(line1.get(1).toString, arrivalAirportTlc,
        //Timestamp.valueOf(line1.get(2).toString), minPrice - line1.get(3).toString.toDouble)
        val sd = line1.get(1).toString
        val st = Timestamp.valueOf(line1.get(2).toString)
        val p = minPrice - line1.get(3).toString.toDouble
        val list2 = spark.sql(
          s"""select flightNumber, arrivalAirportTlc, arrivalDate, price from global_temp.ticket
             |where departureAirportTlc = '$sd'
             |and arrivalAirportTlc = '$arrivalAirportTlc'
             |and departureDate > timestamp('$st')
             |and price < '$p'""".stripMargin)

        list2.collect().foreach(line2 => {
          val row = spark.createDataFrame(spark.sparkContext.parallelize(List(Row(departureAirportTlc,
            //getDepartureAirportNameByTlc(departureAirportTlc),
            line.get(1).toString,
            arrivalAirportTlc,
            //getArrivalAirportNameByTlc(arrivalAirportTlc),
            line.get(3).toString,
            departureDate, Timestamp.valueOf(line2.get(2).toString), line1.get(0).toString, line2.get(0).toString,
            line1.get(1).toString,
            //getDepartureAirportNameByTlc(line1.get(1).toString),
            spark.sql(s"select departureAirportName from global_temp.ticket where departureAirportTlc = '$sd'")
              .collect()(0).get(0).toString,
            line1.get(3).toString.toDouble + line2.get(3).toString.toDouble
          ))), splice2_schema)
          splice2 = splice2.union(row)
        })
      })


    })

    splice2.coalesce(1).write.option("header", "true").csv("hdfs://192.168.101.122:9000/air/splice2.csv")

  }

  def selectSplice(departureAirportTlc: String, arrivalAirportTlc: String, departureDate: Timestamp): Unit = {


  }

  def getFirstFlight(departureAirportTlc: String,arrivalAirportTlc: String, price: Double): DataFrame = {
    val list = spark.sql(
      s"""select flightNumber, arrivalAirportTlc, arrivalDate, price from ticket
         |where departureAirportTlc = '$departureAirportTlc'
         |and arrivalAirportTlc != '$arrivalAirportTlc'
         |and price < '$price'""".stripMargin)
    list
  }

  def getSecondFlight(departureAirportTlc: String,arrivalAirportTlc: String, departureDate: Timestamp, price: Double): DataFrame = {
    val list = spark.sql(
      s"""select flightNumber, arrivalAirportTlc, arrivalDate, price from ticket
         |where departureAirportTlc = '$departureAirportTlc'
         |and arrivalAirportTlc = '$arrivalAirportTlc'
         |and departureDate > '$departureDate'
         |and price < '$price'""".stripMargin)
    list
  }

  def getDepartureAirportNameByTlc(departureAirportTlc: String): String = {
    spark.sql(s"select departureAirportName from ticket where departureAirportTlc = '$departureAirportTlc'")
      .collect()(0).get(0).toString
  }

  def getArrivalAirportNameByTlc(arrivalAirportTlc: String): String = {
    spark.sql(s"select arrivalAirportName from ticket where arrivalAirportTlc = '$arrivalAirportTlc'")
      .collect()(0).get(0).toString
  }
 }
