
window.connection = function(num1, num2, successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, "BandPlugin", "connection", []);
};

window.notification = function(num1, num2, successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, "BandPlugin", "notification", [num1, num2]);
};

window.delete = function(num1, num2, successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, "BandPlugin", "delete", []);
};

