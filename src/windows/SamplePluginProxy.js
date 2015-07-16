var cordova = require('cordova'),
    SamplePlugin= require('./SamplePlugin');

module.exports = {

    Sample: function (successCallback, errorCallback, strInput) {

        var sampleCase = strInput[0].SampleCase();
        if(sampleCase != "") {
            successCallback(sampleCase);
        }
        else {
            errorCallback(sampleCase);
        }
    }
};

require("cordova/exec/proxy").add("SamplePlugin", module.exports);
