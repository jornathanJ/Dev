package my.test.spark.samplecode;
 
import java.net.InetAddress;
import java.net.UnknownHostException;
 
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
 
public class LineNumber {
    public static void main(String[] args) throws UnknownHostException {
         
        SparkConf conf = new SparkConf().setAppName("Line Number").setMaster("spark://a3-spark-master:7077").setSparkHome("/usr/local/spark");
        conf.set("spark.driver.host", InetAddress.getLocalHost().getHostAddress().toString());
        JavaSparkContext sc = new JavaSparkContext(conf);
         
        sc.addJar("/home/spark/jars/myExample.jar");
         
        JavaRDD<String> logData = sc.textFile("/home/spark/data/README.md");
         
        long numAs = logData.filter(new Function<String, Boolean>(){
            public Boolean call(String s){return s.contains("a");}
        }).count();
         
        long numBs = logData.filter(new Function<String, Boolean>(){
            public Boolean call(String s){return s.contains("b");}
        }).count();
         
        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
        
        sc.close();
    }
}