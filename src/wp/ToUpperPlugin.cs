using System;

namespace WPCordovaClassLib.Cordova.Commands
{
    public class ToUpperPlugin : BaseCommand
    {
        public void ToUpper(string options)
        {
            string title = "";
            string message = "";

            string[] args  = JSON.JsonHelper.Deserialize<string[]>(options);
            title = args[0].ToString();
            message = args[1].ToString();

            MessageBox.Show(title+message);

            PluginResult result;
            if (title != "" && message != "")
            {
                result = new PluginResult(PluginResult.Status.OK, args);
            } else
            {
                result = new PluginResult(PluginResult.Status.ERROR, args);
            }

            DispatchCommandResult(result);
        }
    }
}

