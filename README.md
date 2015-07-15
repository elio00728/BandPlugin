# ToUpperPlugin

HTML
Open index.html and add this code before the closing tag </body>.




Input text: <input type="text" id="inputText" style="font-size: xx-large" /><br/>
<button type="button" style="font-size: xx-large" onclick="toUpperClicked()">To Upper</button>
<p id="toUpperResult"></p>

<script>

var successCallback = function () {
        document.getElementById("toUpperResult").innerText =
        'ToUpperPlugin successCallback ' + arguments[0];
};

var errorCallback = function (arguments) {
    document.getElementById("toUpperResult").innerText =
        'ToUpperPlugin errorCallback ' + arguments[0];
};

function toUpperClicked(arguments) {
    ToUpperPlugin.ToUpper(successCallback, errorCallback,
        document.getElementById("inputText").value);
}

</script>
