package com.book.pong_v1;

import com.book.simplegameengine_v1.SGActivity;
import com.book.simplegameengine_v1.SGInputPublisher;
import com.book.simplegameengine_v1.SGInputSubscriber;
import com.book.simplegameengine_v1.SGPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class GameActivity extends SGActivity implements SGInputSubscriber {
	
	private GameView mView;
	private SGInputPublisher mInputPublisher;
	
	public static final String TAG = "PongV1";
	
	
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
		
		mInputPublisher = new SGInputPublisher(this);
		mInputPublisher.registerSubscriber(this);
		setInputPublisher(mInputPublisher);
		
		mView = new GameView(this);
		setContentView(mView);
		
//		StringBuilder stringBuilder = new StringBuilder();
		
//		stringBuilder.append(preferences.getInt("difficulty", 0));
//		Log.d(TAG, stringBuilder.toString());
//		stringBuilder.setLength(0);
//		stringBuilder.append("High score: ");
//		stringBuilder.append(preferences.getInt("high_score", 0));
//		Log.d(TAG, stringBuilder.toString());
		
		
		
	}
	
	@Override
	public void onDown(MotionEvent event) { }
	
	@Override
	public void onUp(MotionEvent event) { }
	
	private boolean relative = true;
	
	@Override
	public void onScroll(MotionEvent downEvent, MotionEvent moveEvent, float distanceX,
			float distanceY) {
		mView.movePlayer(-distanceX,  -distanceY, relative);
	}

	@Override
	public void onLongPress(MotionEvent event) {
		relative = relative ? false : true;		
	}
	
	
	
}
