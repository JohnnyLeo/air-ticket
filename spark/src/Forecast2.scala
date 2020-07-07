import org.apache.spark.ml.Pipeline
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, IndexToString, LabeledPoint, StringIndexer, Tokenizer, VectorAssembler, VectorIndexer}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.mllib.regression.LinearRegressionWithSGD
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.sql.SparkSession

/**
 * @author jleo
 * @date 2020/7/3
 */
object Forecast2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("forecast2").
      getOrCreate()
    val rdd = spark.sparkContext.textFile("spark/train.txt")

    val df = spark.createDataFrame(rdd.map(x => {
      val temp = x.split(" ")
      (Array(temp(3).split("-")(1).toInt), temp(7).toDouble)
      // temp(6).toDouble,, temp(4).split(":").mkString(".").toDouble
      // "time",, "startTime", "startTime"
    })).toDF("month", "price")

//    val colArray = Array("month")
//    val assembler = new VectorAssembler().setInputCols(colArray).setOutputCol("features")
//    val vecDF = assembler.transform(df)
    val hashingTF = new HashingTF().
      setNumFeatures(1000).
      setInputCol("month").
      setOutputCol("features")
    val vecDF = hashingTF.transform(df)

    val lr1 = new LogisticRegression().setFamily("multinomial")
    val lr2 = lr1.setFeaturesCol("features").setLabelCol("price")
    val lr = lr2.setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8)
    val lrModel = lr.fit(vecDF)

    val testRdd = spark.sparkContext.textFile("spark/test.txt")
    val testDF = spark.createDataFrame(testRdd.map(x => {
      val temp = x.split(" ")
      Tuple1(Array(temp(3).split("-")(1).toInt))
      //, temp(6).toDouble, temp(4).split(":").mkString(".").toDouble
      //, "time", "startTime"
    })).toDF("month")
    val testVecDF = hashingTF.transform(testDF)

    val data = lrModel.transform(testVecDF)
    vecDF.show()
    data.show()

    spark.stop()

  }
}
