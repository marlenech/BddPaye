<?php

 /*
  * Following code will list all the products
  */

// array for JSON response
$response = array();


  // include db connect class
  require_once 'db_connect.php';

 // connecting to db
 $db = new DB_CONNECT();

 // get all products from products table
$result = mysql_query("SELECT *FROM tauxx") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
// looping through all results
// products node
$response["tauxx"] = array();

 while ($row = mysql_fetch_array($result)) {
    // temp user array
    $taux = array();
    $taux["pid"] = $row["pid"];
    $taux["nom"] = $row["nom"];
    $taux["nvtaux"] = $row["nvtaux"];
    $taux["ancientaux"] = $row["ancientaux"];
    $taux["date"] = $row["date"];
    $taux["created_at"] = $row["created_at"];



    // push single product into final response array
    array_push($response["tauxx"], $taux);
  }
  // success
   $response["success"] = 1;

  // echoing JSON response
  echo json_encode($response);
  } else {
 // no products found
 $response["success"] = 0;
 $response["message"] = "No products found";

// echo no users JSON
echo json_encode($response);
}
?>