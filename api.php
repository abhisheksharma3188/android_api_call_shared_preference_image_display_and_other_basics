<?php
    header('content-type:application/json');
    header('Access-Control-Allow-Origin:*');
    
    if(isset($_REQUEST['number1'])){
        $number1=$_REQUEST['number1'];
    }else{
        $number1=0;
    }
    if(isset($_REQUEST['number2'])){
        $number2=$_REQUEST['number2'];
    }else{
        $number2=0;
    }

    if($number1==$number2){
        $responseArray=[['name'=>'Abhishek','age'=>"35"],['name'=>'Chetana','age'=>"32"],['name'=>'Aadhvik','age'=>"4"]];
    }else if($number1>$number2){
        $responseArray=[['name'=>'Sumit','age'=>"35"],['name'=>'Prashant','age'=>"32"],['name'=>'Pushpendra','age'=>"4"]];
    }else{
        $responseArray=[['name'=>'Akash','age'=>"35"],['name'=>'Shanil','age'=>"32"],['name'=>'Meenal','age'=>"4"]];
    }
    
    $responseJSON = json_encode($responseArray);
    echo $responseJSON;
?>
