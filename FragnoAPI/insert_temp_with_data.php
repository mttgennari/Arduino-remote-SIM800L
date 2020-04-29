<?php
    $now = $_POST['data'];
    $temp = $_POST['temp'];
    $stato = $_POST['status'];

    echo("$now, $temp, $stato");

    //$now = time();
    //sleep(1);
    //$now = $now-60;
    //$temp = rand(20, 40);

    $server = "localhost";
    $user = "site";
    $password = "gnnmtt94";
    
    $db = "Fragno";
    $table = "temperatura";

    $conn = new mysqli($server, $user, $password, $db);

    if ($conn->connect_error) {
        $res = "ERRORE!\nConnessione fallita";
    }

    $sql = "INSERT INTO ".$table." VALUES ($now, $temp, $stato)";
    
    if ($conn->query($sql) == TRUE) {
        $res = "0";
    } else {
        $res = "ERRORE! \n$sql\n".$conn->error."\nErrore, impossibile inviare i dati al server\nContattare l'amministratore o riprovare più tardi.";
    }
    $conn->close();
    
    echo($res);
?>