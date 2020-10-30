<?php

    $errCodes = [400, 001];
    $errDescs = ["Server irraggiungibile", "Riavvio del sistema"];

    $errCode = $_POST['errcode'];

    $index = array_search($errCodes, $errCodes);

    $errDesc = $errDescs[$index];
    //test
    $now = time();
    //sleep(1);
    //$now = $now-60;
    //$temp = rand(20, 40);

    $server = "localhost";
    $user = "site";
    $password = "gnnmtt94";
    
    $db = "Fragno";
    $table = "errorlog";

    $conn = new mysqli($server, $user, $password, $db);

    if ($conn->connect_error) {
        $res = "ERRORE!\nConnessione fallita";
    }

    $sql = "INSERT INTO ".$table." VALUES (".$now.", ".$errCode.", \"".$errDesc."\")";
    
    if ($conn->query($sql) === TRUE) {
        $res = "0";
    } else {
        $res = "ERRORE! ".$conn->error."\nErrore, impossibile inviare i dati al server\nContattare l'amministratore o riprovare più tardi.";
    }
    $conn->close();
    
    echo($res);
?>