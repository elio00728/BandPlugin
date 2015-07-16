
var BandPlugin = {
    Band: function (successCallback, errorCallback, title, message) {
        var args = [title, message];
        cordova.exec(successCallback, errorCallback, "BandPlugin", "Band", [args]);
    }
}

module.exports = BandPlugin;
