
var ToUpperPlugin = {
    ToUpper: function (successCallback, errorCallback, title, message) {
        var args = [title, message];
        cordova.exec(successCallback, errorCallback, "ToUpperPlugin", "ToUpper", [args]);
    }
}

module.exports = ToUpperPlugin;
