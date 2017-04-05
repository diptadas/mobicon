<?php

   $host = "localhost";
   $username = "root";
   $password ="";
   $database = "mobicon_db";
   
   mysql_connect($host, $username, $password);
   mysql_select_db($database);
   
   mysql_query("SET time_zone='+6:00' ");
   mysql_query("SET CHARACTER SET utf8");
   mysql_query("SET SESSION collation_connection = 'utf8_general_ci' ") or die (mysql_error()); 
   
?>   