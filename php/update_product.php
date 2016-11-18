<?php

/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['pid']) && isset($_POST['nom']) && isset($_POST['nvtaux']) && isset($_POST['ancientaux']) && isset($_POST['date'])) {
    
    $pid = $_POST['pid'];
    $nom = $_POST['nom'];
    $nvtaux = $_POST['nvtaux'];
    $ancientaux = $_POST['ancientaux'];
    $date = $_POST['date'];

    // include db connect class
    require_once 'db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("UPDATE tauxx SET nom = '$nom', nvtaux = '$nvtaux', ancientaux = '$ancientaux', date = '$date' WHERE pid = $pid");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
        
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
