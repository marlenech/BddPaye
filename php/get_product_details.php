<?php

/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();


// include db connect class
require_once 'db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_GET["pid"])) {
    $pid = $_GET['pid'];

    // get a product from products table
    $result = mysql_query("SELECT *FROM tauxx WHERE pid = $pid");

    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $taux = array();
            $taux["pid"] = $result["pid"];
            $taux["nom"] = $result["nom"];
            $taux["nvtaux"] = $result["nvtaux"];
            $taux["ancientaux"] = $result["ancientaux"];
            $taux["date"] = $result["date"];
            $taux["created_at"] = $result["created_at"];
            
            // success
            $response["success"] = 1;

            // user node
            $response["taux"] = array();

            array_push($response["taux"], $taux);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>