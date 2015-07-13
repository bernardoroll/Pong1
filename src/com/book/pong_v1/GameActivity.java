package com.book.pong_v1;

import com.book.simplegameengine_v1.SGActivity;
import com.book.simplegameengine_v1.SGPreferences;

import android.os.Bundle;
import android.util.Log;

public class GameActivity extends SGActivity {
	
	public static final String TAG = "PongV1";
	private GameView mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		enableFullScreen();
		enableKeepScreenOn();
		
		SGPreferences preferences = getPreferences();
		
		if(preferences.getInt("first_time", -1) == -1){
			preferences.begin()
					.putInt("first_time", 1)
					.putInt("difficulty", 0)
					.putInt("high_score", 0)
					.end();
			Log.d(TAG,  "Primeira inicialização.");
		}
		
		mView = new GameView(this);
		setContentView(mView);
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(preferences.getInt("difficulty", 0));
		Log.d(TAG, stringBuilder.toString());
		stringBuilder.setLength(0);
		stringBuilder.append("High score: ");
		stringBuilder.append(preferences.getInt("high_score", 0));
		Log.d(TAG, stringBuilder.toString());
		
		
		
	}
	
}
