import java.text.SimpleDateFormat

import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegressionModel
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.util.DateFormatter
import org.apache.spark.sql.functions.regexp_replace

import scala.reflect.internal.util.TableDef.Column

/**
 * @author jleo
 * @date 2020/7/5
 */
object MergeRecommend {
  def main(args: Array[String]): Unit = {

    System.getProperties.setProperty("HADOOP_USER_NAME", "suncaper")

    val spark = SparkSession.builder()
      .master("local")
      .appName("recommend")
      .getOrCreate()

    /*
    算得tripTime航程时间
     */
    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val rdd = spark.sparkContext.textFile("spark/ticket.csv")
    val header = rdd.first()
    val body = rdd.filter(l => l != header).map(line => {
      val strs = line.split(",")
      val time = dateFormatter.parse(strs(4)).getTime - dateFormatter.parse(strs(3)).getTime
      val hour = time / 1000 / 3600
      val minute = (time - (hour * 1000 * 3600)) / 1000 /  60
      val tripTime = hour.toString + ":" + minute.toString
      val departure = strs(3)
      val arrival = strs(4)
      strs.slice(0, 3).mkString(",") + "," +
        departure.split(" ").mkString(",") + "," +
        arrival.split(" ").mkString(",") + "," +
        strs.slice(5, strs.length).mkString(",") + "," + tripTime
    })

    /*
    添加hdfs保存的模型
     */
    val model = LinearRegressionModel.read.load("hdfs://192.168.101.122:9000/spark/model")

    /*
    将原数据用模型训练得到排序
     */
    val arr = header.split(",")
    val newHeader = arr.slice(0, 4).mkString(",") +
    ",departureTime," + arr(4) + ",arrivalTime," +
      arr.slice(5, arr.length).mkString(",") + ",tripTime"
    val dfWithTripTime = spark.createDataFrame(body.map(line => {
      val temp = line.split(",")
      (temp(0), temp(1), temp(2), temp(3),
        temp(4).split(":").slice(0, 2).mkString(".").toDouble,
        temp(5), temp(6), temp(7), temp(8), temp(9),
        temp(10), temp(11), temp(12), temp(13), temp(14), temp(15), temp(16),
        temp(17).toDouble,
        temp(18).split(":").mkString(".").toDouble)
    })).toDF(newHeader.split(","): _*)
    val colArray = Array("departureTime", "price", "tripTime")
    val assembler = new VectorAssembler().setInputCols(colArray).setOutputCol("features")
    val testVecDF = assembler.transform(dfWithTripTime)
    val predictions = model.transform(testVecDF)

    /*
    去掉无用的列，修改排序列名
     */
    val dfWithOrder = predictions.drop("features").withColumnRenamed("prediction", "order")

    /*
    恢复数据格式
     */
    //val departureTime = concat(dfWithOrder("departureTime"))
    val result = dfWithOrder.withColumn("departureTime", regexp_replace(dfWithOrder("departureTime"), "\\.", ":"))
        .withColumn("tripTime", regexp_replace(dfWithOrder("tripTime"), "\\.", ":"))

    //result.coalesce(1).write.option("header", "true").csv("hdfs://192.168.101.122:9000/air/ticket.csv")
  result.show(3)
  }
}
