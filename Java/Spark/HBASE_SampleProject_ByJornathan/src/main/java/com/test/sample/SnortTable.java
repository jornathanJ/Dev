package com.test.sample;

import java.io.IOException;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class SnortTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HTableDescriptor descriptor = new HTableDescriptor("SnortTable");
		descriptor.addFamily(new HColumnDescriptor("sig"));
		descriptor.addFamily(new HColumnDescriptor("priority"));
		
		try {
			Configuration config = HBaseConfiguration.create();
			config.addResource(new Path("C:/cygwin64/root/usr/local/hbase-1.1.2/conf/hbase-default.xml"));
			config.addResource(new Path("C:/cygwin64/root/usr/local/hbase-1.1.2/conf/hbase-site.xml"));
			HBaseAdmin admin = new HBaseAdmin(config);
			// Create Table
			admin.createTable(descriptor);
			System.out.println("Table Created...");
		} catch (IOException e) {
			System.out.println("IOExeption: cannot create table");
			e.printStackTrace();
		}
	}
}
