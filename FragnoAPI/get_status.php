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
    
    $sql = "SELECT valore FROM general WHERE chiave='statocaldaia'";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
        // output data of each row
<<<<<<< HEAD
        $row = $result->fetch_assoc();
=======
        $result->fetch_assoc();
>>>>>>> master
        $res = $row['valore'];
    } else {
        $res = "Risorsa non trovata";
    }
    $conn->close();

    echo($res);
?>