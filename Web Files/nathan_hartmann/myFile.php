<?php
mysql_connect("mysql16.000webhost.com","a4176562_philip","MOBILEproject18");
mysql_select_db("a4176562_tutor");
$sql=mysql_query("select * from users");
while($row=mysql_fetch_assoc($sql))
$output[]=$row;
print(json_encode($output));
mysql_close();
?>