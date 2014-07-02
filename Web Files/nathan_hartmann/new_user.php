<?php
 
header('Content-type: application/json');
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */ 
// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['username']) && isset($_POST['password']) && isset($_POST['name']) && isset($_POST['email']) && isset($_POST['phone'])) {

    $username = $_POST['username'];
    $password = md5($_POST['password']);//password_hash($_POST['password'], PASSWORD_DEFAULT);
    $name = $_POST['name'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
 
    // include db connect class
    define('__ROOT__', dirname(dirname(__FILE__))); 
//    require_once(__ROOT__.'/public_html/db_connect.php');
 require_once(/*__ROOT__.'/var/www/tutorrow/*/'db_connect.php');	
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO users(username, password, name, email, phone) VALUES('$username', '$password', '$name', '$email', '$phone')");
    
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} 
else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>