package com.test.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;




public class startHbase {
	private static String currentStock;
	
	 private static List<Put> currentImport;  // cache for bulk load
	 
	public final static byte[] COLUMN_FAMILY = "info".getBytes();
	
	public final static byte[] TABLE_NAME = "users".getBytes();
	public final static byte[] COL_NAME = "name".getBytes();
    public final static byte[] COL_EMAIL = "email".getBytes();
    public final static byte[] COL_PASSWORD = "password".getBytes();
	
    public static void main(String []args){
        Configuration config = HBaseConfiguration.create();
        //myConf.set("hbase.master", "127.0.0.1");
        config.addResource(new Path(System.getenv("HBASE_CONF_DIR"),
                "hbase-site.xml"));
        
        try {
//        	HTablePool pool = new HTablePool();
//            HTableInterface userTable = pool.getTable("users");
        	
        	try (Connection connection = ConnectionFactory.createConnection(config);
                    Admin admin = connection.getAdmin()) {

                   HTableDescriptor table =
                       new HTableDescriptor(TableName.valueOf(TABLE_NAME));
                   table.addFamily(new HColumnDescriptor(COLUMN_FAMILY));
                   
                   if (admin.tableExists(table.getTableName())) {
                       System.out.print("Dropping table. ");
                       // a table must be disabled before it can be dropped
                       admin.disableTable(table.getTableName());
                       admin.deleteTable(table.getTableName());
                       System.out.println(" Done.");
                   }
               }
            
        	try (Connection connection = ConnectionFactory.createConnection(config);
                    Admin admin = connection.getAdmin()) {

                   HTableDescriptor table = 
                       new HTableDescriptor(TableName.valueOf(TABLE_NAME));
                   table.addFamily(new HColumnDescriptor(COLUMN_FAMILY));

                   if (!admin.tableExists(table.getTableName())) {
                       System.out.print("Creating table. ");
                       admin.createTable(table);
                       System.out.println(" Done.");
                   }
               }
        	
            System.out.println("1");
            Put p = new Put(Bytes.toBytes("TheRealMT"));  
            p.addColumn(COLUMN_FAMILY, COL_NAME, Bytes.toBytes("Mark Twain"));
            //save "MarkTwain" in info:name cell
            p.addColumn(COLUMN_FAMILY, COL_EMAIL, Bytes.toBytes("samuel@clemens.org"));
            p.addColumn(COLUMN_FAMILY, COL_PASSWORD, Bytes.toBytes("Langhorne"));
            
            currentImport = new ArrayList<Put>();
            currentImport.add(p);
            
            System.out.println("p : "+p);
            
            try (Connection conn = ConnectionFactory.createConnection(config);
            		Admin admin = conn.getAdmin()) {
            	
                Table table = conn.getTable(TableName.valueOf("users"));
                table.put(currentImport);
                
                
                table.close();
                
                Get g = new Get(Bytes.toBytes("TheRealMT"));
                g.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"));
                Result _result = table.get(g);
                System.out.println("r : "+ _result);
                byte[]b = _result.getValue(Bytes.toBytes("info"),Bytes.toBytes("name"));
                String email = Bytes.toString(b);
                System.out.println(email);
                
                
                Scan s =new Scan();
                ResultScanner rs = null;
        		try {
        			rs = table.getScanner(s);
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		
                for (Result r : rs) {
                    for (KeyValue kv : r.raw()) {
                        System.out.println("row:" + new String(kv.getRow()) +"");
                        System.out.println("family:" + new String(kv.getFamily()) +":");
                        System.out.println("qualifier:" + new String(kv.getQualifier()) +"");
                        System.out.println("value:" + new String(kv.getValue()));
                        System.out.println("timestamp:" + kv.getTimestamp() +"");
                        System.out.println("-------------------------------------------");
                    }
                }
            } 
            finally {
                currentImport.clear();
            }
            
            
            System.out.println("finish");
            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(Exception es){
            es.printStackTrace();
        }
        
    
        
        
    }
}

