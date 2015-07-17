# BandPlugin

This code shows how to send a message from your Javascript to the native part of windows and to receive the  message back in your Javascript. 

1- Add wp8 and windows platforms to your Cordova project:


>cordova platforms add wp8

>cordova paltforms add windows

2- Add this plugin to your Cordova project:

>cordova plugin add https://github.com/elio00728/BandPlugin.git

3- Modify your html file for this code:

```
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>gitapachecordova</title>

    <!-- gitapachecordova references -->
    <link href="css/index.css" rel="stylesheet" />
</head>
<body>

    <!-- Cordova reference, this is added to your app when it's built. -->
    <script src="cordova.js"></script>
    <script src="scripts/platformOverrides.js"></script>

    <script src="scripts/index.js"></script>

    <script>
                var successCallback = function (arguments) {
                        document.getElementById("BandResult").innerText =
                        'Plugin success: ' + arguments[0];
                        document.getElementById("checked").innerText = "Received it!";
                };

                var errorCallback = function (arguments) {
                    document.getElementById("BandResult").innerText =
                        'Plugin error ' + arguments[0] + arguments[1];
                };

                function BandClicked() {
                    document.getElementById("checked").innerText = "Send it!";
                    BandPlugin.Band(successCallback, errorCallback,
                        document.getElementById("title").value, document.getElementById("message").value);
                }
    </script>
    Title: <input type="text" id="title" style="font-size: xx-large" /><br />
    Message: <input type="text" id="message" style="font-size: xx-large" /><br />
    <button type="button" style="font-size: xx-large" onclick="BandClicked()">Send</button>
    <p id="checked"></p>
    <p id="BandResult"></p>
    
       
</body>
</html>






```
