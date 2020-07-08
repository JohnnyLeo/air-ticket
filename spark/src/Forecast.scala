import org.apache.spark.ml.classification.{LogisticRegression, MultilayerPerceptronClassifier}
import org.apache.spark.ml.feature.{StandardScaler, StringIndexer, VectorAssembler, VectorIndexer}
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession

/**
 * @author jleo
 * @date 2020/7/4
 */
object Forecast {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("forecast").
      getOrCreate()
    val rdd = spark.sparkContext.textFile("spark/train.txt")

    // 通过价格，预测月份
    val df = spark.createDataFrame(rdd.map(x => {
      val temp = x.split(" ")
      (temp(3).split("-")(1).toInt, temp(4).split(":").mkString(".").toDouble, temp(7).toDouble)
      // temp(6).toDouble,
      // "time",
    })).toDF("month", "startTime", "price")

    val colArray = Array("price", "startTime")
    val assembler = new VectorAssembler().setInputCols(colArray).setOutputCol("features")
    val vecDF = assembler.transform(df)

    val lr1 = new LinearRegression()
    val lr2 = lr1.setFeaturesCol("features").setLabelCol("month")
    val lr = lr2.setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8)
    val lrModel = lr.fit(vecDF)

    val testRdd = spark.sparkContext.textFile("spark/test.txt")
    val testDF = spark.createDataFrame(testRdd.map(x => {
      val temp = x.split(" ")
      (temp(4).split(":").mkString(".").toDouble, temp(7).toDouble)
      //, temp(6).toDouble
      //, "time"
    })).toDF("startTime", "price")
    val testVecDF = assembler.transform(testDF)

    // 预测时，用最低价格去预测
    val data = lrModel.transform(testVecDF)
    vecDF.show()
    data.show()

    spark.stop()

  }
}
