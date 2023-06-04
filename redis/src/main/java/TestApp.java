/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestApp {
  public static void main(String[] args) throws URISyntaxException, ClassNotFoundException {

    String absolutePath = "/Users/yoonji_kim/latest_calcite/redis/src/test/resources/redis-mix-model.json";

    // Load Calcite jdbc driver
    Class.forName("org.apache.calcite.jdbc.Driver");

    // Make a calcite connection of which model is "model-redis.json"
    // and Execute queries
    Scanner sc = new Scanner(System.in);
    System.out.println("Please write a query statement.");
    String query = sc.nextLine();



    while(!query.equals("q")){
      try(Connection connection = DriverManager.getConnection("jdbc:calcite:model=" + absolutePath);
          Statement statement = connection.createStatement();
          ResultSet rs = statement.executeQuery(query);
      ) {

        // Get the metadata of the result set
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> columns = new ArrayList<String>(rsmd.getColumnCount());

        // Add the column names of the result set to a list
        System.out.println("\n=== Column names ===\n");
        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
          // Print column names
          System.out.println(rsmd.getColumnName(i));
          columns.add(rsmd.getColumnName(i));
        }

        System.out.println("\n=== Result of the query ===\n");
        while (rs.next()) {
          for (String col : columns) {
            System.out.println("" + col + " : " + rs.getString(col));
          }
          System.out.println("---------");
        }

        System.out.println("\n\nPlease write a query statement.");
        query = sc.nextLine();
      }catch (Exception e){
        System.out.println("\n\nPlease correct the query statement.");
        e.printStackTrace();
        query = sc.nextLine();
        continue;
      }
    }
  }
}
