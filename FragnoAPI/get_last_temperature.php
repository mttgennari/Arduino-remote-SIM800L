<?php
    $servername = "localhost";
    $username = "site";
    $password = "gnnmtt94";
    $dbname = "Fragno";
    
    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    // Check connection
    if ($conn->connect_error) {
        $res = "ERRORE!\nConnessione fallita";
    }
    
    $sql = "SELECT tempin FROM temperatura WHERE timestamp = (SELECT max(timestamp) from temperatura)";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
        // output data of each row
        $result->fetch_assoc();
        $res = $row['tempin'];
    } else {
        $res = "Risorsa non trovata";
    }
    $conn->close();

    echo($res);
?>