<?php include 'LIB_http.php'; ?>
<?php 
$con = mysql_connect("mysql16.000webhost.com","a4176562_philip","MOBILEproject18");
mysql_select_db("a4176562_tutor");
$arr = http_get("http://www.umbc.edu/catalog/2013/courses.php?dept=IS%20and%20BTA",null);
$html = $arr['FILE'];
$pattern = '/<div>\s*<h4>(.*)&nbsp;(.*)&nbsp;\(.*\)<\/h4>\s*<\/div>\s*<h3>(.*)<\/h3>/';
$match = array();
preg_match_all($pattern, $html, $match); 
for($i=0; $i<count($match[0]); $i++){
	$dept = $match[1][$i];
        $dept = mysql_real_escape_string($dept);
	$courseNum = $match[2][$i];
        $courseNum = mysql_real_escape_string($courseNum);
	$courseName = $match[3][$i];
        $courseName = mysql_real_escape_string($courseName);

        mysql_query("INSERT INTO courses (department, coursenum, coursename) VALUES ('$dept', '$courseNum', '$courseName')");
       # $retval = mysql_query( $sql, $con );
        #if(! $retval )
        #{
        #die('Could not enter data: ' . mysql_error());
        #}
        #echo "Entered data successfully\n";
}
mysql_close($con);
?>
