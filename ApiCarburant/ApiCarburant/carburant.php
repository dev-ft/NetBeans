<!DOCTYPE html>
<html>
	<meta charset="utf-8">
<head>
	<title>Carburant</title>
</head>
<body>


<?php

/*$file = file_get_contents('PrixCarburantsQuotidien.xml');
echo $file;*/


//affiche tout les cp des pdv

if (file_exists('PrixCarburantsQuotidien.xml')) {
    $xml = simplexml_load_file('PrixCarburantsQuotidien.xml');
}


foreach ($xml->pdv as $pdv) {
		
	if ($_REQUEST["cp"]==strval($pdv->attributes()->cp)) {
		   		echo(strval($pdv->adresse).'<br>');
	}

	}



?>
</body>
</html>