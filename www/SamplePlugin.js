
var SamplePlugin = {
    Sample: function (successCallback, errorCallback, title, message) {
        var args = [title, message];
        cordova.exec(successCallback, errorCallback, "SamplePlugin", "Sample", [args]);
    }
}

module.exports = SamplePlugin;
