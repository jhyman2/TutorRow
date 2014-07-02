<?php 
header('Content-type: application/json');
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */ 

$id = $_POST['id'];
$department = $_POST['department'];
$number = $_POST['number'];

//$id = 20;

// array for JSON response
$response = array();
// include db connect class
define('__ROOT__', dirname(dirname(__FILE__))); 
require_once('db_connect.php');
// connecting to db
$db = new DB_CONNECT();
// testing
$query = "SELECT COUNT(*) FROM users WHERE id='$id'";

$result = mysql_query($query);// or die(mysql_error());

if(isset($department)==false){
	$dept = "SELECT DISTINCT department FROM courses ORDER BY department;";   
	$result = mysql_query($dept);
	$jsonArray = array();
	$response = array();
	while($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
		// If you want to display all results from the query at once:
		//print_r($row);
		$jsonArray[] = $row;
	}      
	$response["department"] = $jsonArray;
	echo json_encode($response);
}
echo $department;
if(isset($department)){
	$num = "SELECT number FROM courses WHERE department='$department' ORDER BY number;";
	$result = mysql_query($num);
	$jsonArray = array();
	$response = array();
	while($row = mysql_fetch_array($result, MYSQL_ASSOC)){
		$jsonArray[] = $row;
	}
	$response["number"] = $jsonArray;
	echo json_encode($response);
}

?>