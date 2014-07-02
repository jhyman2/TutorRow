<?php

//header('Content-type: application/json');
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */ 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['email']) && isset($_POST['password'])) {
 
    $email = $_POST['email'];
    $password = $_POST['password'];
    //$email = mysql_real_escape_string($email);
    //$password = mysql_real_escape_string($password);

    // include db connect class
    //define('__ROOT__', dirname(dirname(__FILE__))); 
    require_once(/*__ROOT__.'/var/www/tutorrow/*/'db_connect.php');

    // connecting to db
    $db = new DB_CONNECT();
    
    $query = "SELECT COUNT(*) FROM users WHERE email='$email' AND password='$password';";

    $result = mysql_query($query);// or die(mysql_error());

    
    if (mysql_result($result, 0) > 0){
       //echo 'found User <br />';
       $response["success"] = 1;
       $response["message"] = "Query successful";
       //return id, username, email, phone, name
       //$userSQL = mysql_query("SELECT id, username, name, email, phone FROM users WHERE email='$email';");
       $sqlId = mysql_query("SELECT id FROM users WHERE email='$email';");
       $resultId = mysql_result($sqlId,0); 
       $sqlUsername = mysql_query("SELECT username FROM users WHERE email='$email';");       
       $resultUsername = mysql_result($sqlUsername,0);       
       $sqlName = mysql_query("SELECT name FROM users WHERE email='$email';");       
       $resultName = mysql_result($sqlName,0);       
       $sqlEmail = mysql_query("SELECT email FROM users WHERE email='$email';");
       $resultEmail = mysql_result($sqlEmail,0);
       $sqlPhone = mysql_query("SELECT phone FROM users WHERE email='$email';");      
       $resultPhone = mysql_result($sqlPhone,0); 
       $studentClassesSql = mysql_query("SELECT * FROM studentClasses WHERE userID='$resultId';"); 
       $tutorClassesSql = mysql_query("SELECT * FROM tutorClasses WHERE userID='$resultId';");
       $studentRows = array();
       $tutorRows = array();
       while($r = mysql_fetch_array($studentClassesSql,MYSQL_ASSOC)) {
           $studentRows[] = $r;
       }      
      // echo json_encode($rows);

       while($r = mysql_fetch_array($tutorClassesSql,MYSQL_ASSOC)) {
           $tutorRows[] = $r;
       }     
       $response["id"] = $resultId;
       $response["username"] = $resultUsername; 
       $response["name"] = $resultName;       
       $response["email"] = $resultEmail;       
       $response["phone"] = $resultPhone;
       $response["studentClasses"] = $studentRows;
       $response["tutorClasses"] = $tutorRows;
       // echoing JSON response
       echo json_encode($response);    }

    if (mysql_result($result, 0) < 1){
       //echo 'You done fucked up <br />';      
       $response["success"] = 0;
       $response["message"] = "Oops! An error occurred.";
        // echoing JSON response
       echo json_encode($response);    }

    if(mysql_result==False){
	$response["success"] = 0;
        $response["message"] = "Does not exist";
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