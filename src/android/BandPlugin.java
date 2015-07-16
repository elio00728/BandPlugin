package com.delaware.elv;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.delaware.elv.R;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandIOException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.notifications.MessageFlags;
import com.microsoft.band.tiles.BandTile;


/**
 * This class performs connection and notification called from JavaScript.
 */
public class BandPlugin extends CordovaPlugin {
	
	private BandClient client = null;
	private UUID tileId = UUID.fromString("aa0D508F-70A3-47D4-BBA3-812BADB1F8Aa");
	
	// Function execute will listen the exec function on Javascript
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    	 
    	if (action.equals("connection")) {
             this.connection(callbackContext);
             return true;
         }
    	
    	if (action.equals("notification")) {
            String title = args.getString(0);
            String text = args.getString(1);
            this.notification(title, text, callbackContext);
            return true;
        }
    	if (action.equals("delete")) {
    		Log.d("Execute-delete", "enter on execute-delete.");
           this.deleteTile();
           return true;
        }
               
        return false;
    }
    
    //Function to connect our app with the Band
    private void connection( CallbackContext callbackContext) { 	
    	//TODO connection with the BAND
        try {
			if(getConnectedBandClient()) {
			    callbackContext.success("connection successful");
			} else {
			    callbackContext.error("Band isn't connected. Please make sure bluetooth is on and the band is in range.");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    private void notification(String title, String text, CallbackContext callbackContext) {
        if(title != null && text != null) {
        	//new appTask().execute();
           callbackContext.success("title= "+title +"  text="+text);
        	
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////       	

        	try {
				if (getConnectedBandClient()) {
					if (doesTileExist(client.getTileManager().getTiles().await(), tileId)) {
						sendMessage(title, text);
					} else {
						if(addTile()) {
							sendMessage(title, text);
						}
					}
				} else {
					Log.d("BandNotification", "Band isn't connected. Please make sure bluetooth is on and the band is in range.");
				}
			} catch (BandException e) {
				String exceptionMessage="";
				switch (e.getErrorType()) {
				case DEVICE_ERROR:
					exceptionMessage = "Please make sure bluetooth is on and the band is in range.";
					break;
				case UNSUPPORTED_SDK_VERSION_ERROR:
					exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.";
					break;
				case SERVICE_ERROR:
					exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.";
					break;
				case BAND_FULL_ERROR:
					exceptionMessage = "Band is full. Please use Microsoft Health to remove a tile.";
					break;
				default:
					exceptionMessage = "Unknown error occured: " + e.getMessage();
					break;
				}
				Log.d("BandNotification",exceptionMessage);

					
			} catch (Exception e) {
				Log.d("BandNotification_Exception", e.getMessage());
			}
        	
 /////////////////////////////////////////////////////////////////////////////////////////////////       	
        	     	
        } else {
            callbackContext.error("Error: String is null");
        }
    }
    
    
/*
    private class appTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				if (getConnectedBandClient()) {
					if (doesTileExist(client.getTileManager().getTiles().await(), tileId)) {
						sendMessage("title","Send message to existing message tile");
					} else {
						if(addTile()) {
							sendMessage("title","Send message to new message tile");
						}
					}
				} else {
					Log.d("BandNotification", "Band isn't connected. Please make sure bluetooth is on and the band is in range.");
				}
			} catch (BandException e) {
				String exceptionMessage="";
				switch (e.getErrorType()) {
				case DEVICE_ERROR:
					exceptionMessage = "Please make sure bluetooth is on and the band is in range.";
					break;
				case UNSUPPORTED_SDK_VERSION_ERROR:
					exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.";
					break;
				case SERVICE_ERROR:
					exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.";
					break;
				case BAND_FULL_ERROR:
					exceptionMessage = "Band is full. Please use Microsoft Health to remove a tile.";
					break;
				default:
					exceptionMessage = "Unknown error occured: " + e.getMessage();
					break;
				}
				Log.d("BandNotification",exceptionMessage);

					
			} catch (Exception e) {
				Log.d("BandNotification_Exception", e.getMessage());
			}
			return null;
		}
	}
*/	
	
	private boolean doesTileExist(List<BandTile> tiles, UUID tileId) {
		for (BandTile tile:tiles) {
			if (tile.getTileId().equals(tileId)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean addTile() throws Exception {
        /* Set the options */
		Context context=this.cordova.getActivity().getApplicationContext(); 
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap tileIcon = BitmapFactory.decodeResource(context.getResources(),  R.raw.tile_icon_large, options);
        Bitmap badgeIcon = BitmapFactory.decodeResource(context.getResources(), R.raw.tile_icon_small, options);

		BandTile tile = new BandTile.Builder(tileId, "MessageTile", tileIcon).setTileSmallIcon(badgeIcon).build();
		Log.d("BandNotification", "Message Tile is adding ...");
		if (client.getTileManager().addTile(this.cordova.getActivity(), tile).await()) {
			Log.d("BandNotification", "Message Tile is added");
			return true;
		} else {
			Log.d("BandNotification", "Unable to add message tile to the band.");
			return false;
		}
	}
	
	private void deleteTile() {
		try {
			Log.d("deleteTile", "Inside deleteTile.");
			// get the current set of tiles
			Collection<BandTile> tiles =client.getTileManager().getTiles().await();
				for(BandTile t : tiles) {
					if(client.getTileManager().removeTile(t).await()){
					// do work if the tile was successfully removed
					}
				}
				
			} catch (BandException e) {
			// handle BandException
				Log.d("BandDelete_Exception", e.getMessage());
			} catch (InterruptedException e) {
			// handle InterruptedException
				e.printStackTrace();
			}
	}
	
	private void sendMessage(String title, String message) throws BandIOException {
		client.getNotificationManager().sendMessage(tileId, title, message, new Date(), MessageFlags.SHOW_DIALOG);

		Log.d("BandNotification_SendMessage", message);
	}
	
	private boolean getConnectedBandClient() throws InterruptedException, BandException {
		if (client == null) {
			BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
			if (devices.length == 0) {

				Log.d("BandNotification_getConnectedBandClient", "Band isn't paired with your phone");
				return false;
			}
			Context context=this.cordova.getActivity().getApplicationContext(); 
			client = BandClientManager.getInstance().create(context, devices[0]);
		} else if (ConnectionState.CONNECTED == client.getConnectionState()) {
			return true;
		}
		
		Log.d("BandNotification_getConnectedBandClient", "Band is connecting");
		return ConnectionState.CONNECTED == client.connect().await();
	}
}
