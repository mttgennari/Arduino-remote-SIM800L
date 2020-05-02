refresh();

function refresh(){
    getTemperature();
    getStatus();
}

function getTemperature(){
    $.ajax({
        url: 'http://serverteknel.ddns.net:88/FragnoAPI/get_last_temperature.php',
        type: 'POST',
        data: {},
        success: function(msg) {
            var res = msg.split("&");
            var date = new Date(res[0] * 1000);
            var d = date.getDate();
            if(d<10) d = "0"+d;
            var M = date.getMonth();
            if(M<10) M = "0"+M;
            var y = date. getFullYear();
            var h = date .getHours();
            if(h<10) h = "0"+h;
            var m = date.getMinutes();
            if(m<10) m = "0"+m;
            document.getElementById("time").innerHTML = d+"/"+M+"/"+y+" "+h+":"+m;

            var temp = res[1];
            document.getElementById("temp").innerHTML = parseFloat(temp).toPrecision(4) + "°";
        }               
    });
}

function getStatus(){
    $.ajax({
        url: 'http://serverteknel.ddns.net:88/FragnoAPI/get_status.php',
        type: 'POST',
        data: {},
        success: function(msg) {
            document.getElementById("status").innerHTML = msg;
            if(msg == "Accesa")
                document.getElementById("cbStatus").checked = true;
            else
                document.getElementById("cbStatus").checked = false;
        }               
    });
}

function changeStatus(){
    var stat;
    if(document.getElementById("cbStatus").checked)
        stat = "Accesa";
    else
        stat = "Spenta";

    $.ajax({
        url: 'http://serverteknel.ddns.net:88/FragnoAPI/insert_status.php',
        type: 'POST',
        data: {
            status : stat
        },
        success: function(msg) {
            refresh();
        }               
    });
}