<?php
    class ErrData{
        public $data;
        public $errCode;
        public $errDesc;
    }

    $errList = array();

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
    
    $sql = "SELECT * FROM errorlog";
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
        // output data of each row
        while($row = $result->fetch_assoc()){
            $errData = new ErrData();
            $errData->data = $row['timestamp'];
            $errData->errCode=$row['errcode'];
            $errData->errDesc=$row['errdesc'];

            array_push($errList, $errData);
        }
    } else {
        $res = "Risorsa non trovata";
    }
    $conn->close();

    $myJSON = json_encode($errList);

    echo $myJSON;
?>