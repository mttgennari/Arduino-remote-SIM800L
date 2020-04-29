<?php
    $url = 'http://serverteknel.ddns.net:88/FragnoAPI/insert_temp_with_data.php';
    $now = 1577836800;
    for($i=0; $i<8760; $i++){
        $temp = rand(20.00, 37.50);
        $data = array('data' => $now, 'temp' => $temp, 'status' => 0);

        // use key 'http' even if you send the request to https://...
        $options = array(
            'http' => array(
                'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
                'method'  => 'POST',
                'content' => http_build_query($data)
            )
        );
        $context  = stream_context_create($options);
        $result = file_get_contents($url, false, $context);
        if ($result === FALSE) { 
            var_dump($result);
            break; 
        }
        $now += 3600;
        //var_dump($result);
    }
    echo("done")
?>