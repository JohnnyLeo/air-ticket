import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession

/**
 * @author jleo
 * @date 2020/7/2
 */
object Recommend {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("recommend").
      getOrCreate()
    val rdd = spark.sparkContext.textFile("spark/train.txt")

    val df = spark.createDataFrame(rdd.map(x => {
      val temp = x.split(" ")
      (temp(4).split(":").mkString(".").toDouble, temp(6).toDouble, temp(7).toDouble, temp(8).toInt)
    })).toDF("startTime", "time", "price", "order")

    val colArray = Array("startTime", "time", "price")
    val assembler = new VectorAssembler().setInputCols(colArray).setOutputCol("features")
    val vecDF = assembler.transform(df)

    val lr1 = new LinearRegression()
    val lr2 = lr1.setFeaturesCol("features").setLabelCol("order").setFitIntercept(true)
    val lr = lr2.setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8)
    val lrModel = lr.fit(vecDF)

    val test = spark.sparkContext.textFile("spark/test.txt")
    val testDF = spark.createDataFrame(test.map(x => {
      val temp = x.split(" ")
      (temp(4).split(":").mkString(".").toDouble, temp(6).toDouble, temp(7).toDouble)
    })).toDF("startTime", "time", "price")

    val testVecDF = assembler.transform(testDF)

    val predictions = lrModel.transform(testVecDF)
    predictions.show()

    spark.stop()

  }
}
