<?php

    $temp = $_POST['temp'];

    $now = time();
    //sleep(1);
    //$now = $now-60;
    //$temp = rand(20, 40);

    $server = "serverteknel.ddns.net";
    $user = "site";
    $password = "gnnmtt94";
    
    $db = "Fragno";
    $table = "temperatura";

    $conn = new mysqli($server, $user, $password, $db);

    if ($conn->connect_error) {
        $res = "ERRORE!\nConnessione fallita";
    }

    $sql = "INSERT INTO ".$table." VALUES ($now, $temp, 0)";
    
    if ($conn->query($sql) == TRUE) {
        $res = "0";
    } else {
        $res = "ERRORE! ".$conn->error."\nErrore, impossibile inviare i dati al server\nContattare l'amministratore o riprovare più tardi.";
    }
    $conn->close();
    
    echo($res);
?>