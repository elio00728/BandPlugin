var cordova = require('cordova'),
    BandPlugin= require('./BandPlugin');

module.exports = {

    Sample: function (successCallback, errorCallback, strInput) {

        var bandCase = strInput[0].BandCase();
        if(bandCase != "") {
            successCallback(bandCase);
        }
        else {
            errorCallback(bandCase);
        }
    }
};

require("cordova/exec/proxy").add("BandPlugin", module.exports);
