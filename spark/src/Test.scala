import org.apache.spark.ml.Pipeline
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, LabeledPoint, Tokenizer, VectorAssembler}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.mllib.regression.LinearRegressionWithSGD
import org.apache.spark.sql.SparkSession

/**
 * @author jleo
 * @date 2020/7/3
 */
object Test {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().
      master("local").
      appName("recommend").
      getOrCreate()
    val rdd = spark.sparkContext.textFile("spark/train.txt")

    val df = spark.createDataFrame(rdd.map(x => {
      val temp = x.split(" ")
      (temp(0), Array(temp(4), temp(6).toInt, temp(7).toDouble).mkString(" "), temp(8).toInt)
    })).toDF("id", "text", "label")

    val tokenizer = new Tokenizer().
      setInputCol("text").
      setOutputCol("words")

    val hashingTF = new HashingTF().setNumFeatures(10)
      .setInputCol(tokenizer.getOutputCol).setOutputCol("features")

    //featurizedData.show()

    val lr = new LogisticRegression().setMaxIter(10).setRegParam(0.01)

    val pipeline = new Pipeline().
      setStages(Array(tokenizer, hashingTF, lr))

    val lrModel = pipeline.fit(df)

    //lrModel.save("spark/model")

    val test = spark.sparkContext.textFile("spark/test.txt")
    val testDF = spark.createDataFrame(test.map(x => {
      val temp = x.split(" ")
      (temp(0), Array(temp(4), temp(6).toInt, temp(7).toDouble).mkString(" "))
    })).toDF("id", "text")

    //    val testArrDF = tokenizer.transform(testDF)
    //
    //    val testFeaturizedData = hashingTF.transform(testArrDF)

    lrModel.transform(testDF).select("id", "text")
      .collect()
      .foreach(println)

    spark.stop()

  }
}
