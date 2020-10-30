<?php
    //test
    $now = time();
    //sleep(1);
    //$now = $now-60;
    //$temp = rand(20, 40);

    $server = "localhost";
    $user = "site";
    $password = "gnnmtt94";
    
    $db = "Fragno";
    $table = "general";
    $comando = "";

    $conn = new mysqli($server, $user, $password, $db);

    if ($conn->connect_error) {
        $res = "ERRORE!\nConnessione fallita";
    }

    $sql = "SELECT * FROM ".$table." WHERE chiave='comando'";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $comando = $row["valore"];
    }

    $sql = "UPDATE ".$table." SET timestamp=".$now.", valore='".$comando."' WHERE chiave='statocaldaia'";
    
    if ($conn->query($sql) === TRUE) {
        $res = "0";
    } else {
        $res = "ERRORE! ".$conn->error."\nErrore, impossibile inviare i dati al server\nContattare l'amministratore o riprovare più tardi.";
    }
    $conn->close();
    
    echo($res);
?>