package com.book.pong_v1;

import com.book.simplegameengine_v1.SGImage;
import com.book.simplegameengine_v1.SGImageFactory;
import com.book.simplegameengine_v1.SGRenderer;
import com.book.simplegameengine_v1.SGView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

public class GameView extends SGView {
	
	private final static int BALL_SIZE = 16;
	private final static int DISTANCE_FROM_EDGE = 16;
	private final static int PADDLE_HEIGHT = 92;
	private final static int PADDLE_WIDTH = 23;
	private final static int BALL_SPEED = 120;
	private final static int OPPONENT_SPEED = 120;
	
	private boolean mBallMoveRight = true;
	private boolean mOpponentMoveDown = true;
	
	private RectF mBallDestination = new RectF();	
	private RectF mOpponentDestination = new RectF();
	private RectF mPlayerDestination = new RectF();
	
	private SGImage mBallImage;
	private SGImage mOpponentImage;
	private SGImage mPlayerImage;
	
	private Rect mTempImageSource = new Rect();
	
	//private static int iteration = 0;
	
	public GameView(Context context) {
		super(context);
	}
	
	@Override
	public void step(Canvas canvas, float elapsedTimeInSeconds) {
		moveBall(elapsedTimeInSeconds);
		moveOpponent(elapsedTimeInSeconds);
		
		SGRenderer renderer = getRenderer();
		renderer.beginDrawing(canvas, Color.BLACK);
		
		mTempImageSource.set(0, 0, BALL_SIZE, BALL_SIZE);
		renderer.drawImage(mBallImage, mTempImageSource, mBallDestination);
		
		mTempImageSource.set(0, 0, PADDLE_WIDTH, PADDLE_HEIGHT);
		renderer.drawImage(mPlayerImage, mTempImageSource, mPlayerDestination);
		
		mTempImageSource.set(0, 0, PADDLE_WIDTH, PADDLE_HEIGHT);
		renderer.drawImage(mOpponentImage, mTempImageSource, mOpponentDestination);
		
		renderer.endDrawing();
		
//		mTempPaint.setColor(Color.RED);		
//		
//		mTempImageSource.set(0, 0, PADDLE_WIDTH, PADDLE_HEIGHT);
//		
//		if(mPlayerImage != null) {
//			canvas.drawBitmap(mPlayerImage.getBitmap(), mTempImageSource, mPlayerDestination, mTempPaint);
//		} else {
//			canvas.drawRect(mPlayerDestination, mTempPaint);
//		}
//		if(mOpponentImage != null) {
//			canvas.drawBitmap(mOpponentImage.getBitmap(), mTempImageSource, mOpponentDestination, mTempPaint);
//		} else {
//			canvas.drawRect(mOpponentDestination, mTempPaint);
//		}		
//		mTempImageSource.set(0, 0, BALL_SIZE, BALL_SIZE);
//		if(mBallImage != null) {
//			canvas.drawBitmap(mBallImage.getBitmap(), mTempImageSource, mBallDestination, mTempPaint);
//		} else {
//			canvas.drawRect(mBallDestination, mTempPaint);
//		}
//
		
		
		
		
//		canvas.drawRect(mPlayerDestination, mTempPaint);
//		if(iteration <= 255) {
//			mTempPaint.setColor(Color.rgb(iteration, 0, 0));
//			iteration++;
//		} else if(iteration <= 511) {
//			mTempPaint.setColor(Color.rgb(255, iteration - 256, 0));
//			iteration++;
//		} else if(iteration <= 767){
//			mTempPaint.setColor(Color.rgb(255, 255, iteration - 512));
//			iteration++;
//		} else {
//			iteration = 0;
//		}
//		
//		//mTempPaint.setColor(Color.BLACK);
//		// Bola redonda
//		canvas.drawCircle(mBallDestination.centerX(), mBallDestination.centerY(), BALL_SIZE, mTempPaint);
//		
//		//canvas.drawCircle(mBallDestination.exactCenterX(), mBallDestination.exactCenterY(), BALL_SIZE, mTempPaint);
//		// Bola "quadrada"
//		//canvas.drawRect(mBallDestination, mTempPaint);
//		mTempPaint.setColor(Color.BLUE);
//		canvas.drawRect(mOpponentDestination, mTempPaint);
	}
	
	@Override
	protected void setup() {
		Point viewDimensions = getDimensions();
		Point viewCenter = new Point(viewDimensions.x / 2, viewDimensions.y / 2);
		
		int halfBall = BALL_SIZE / 2;
		int halfPaddleHeight = PADDLE_HEIGHT / 2;
		
		SGImageFactory imageFactory = getImageFactory();
		
		mBallImage = imageFactory.createImage(R.drawable.ball);
		mPlayerImage = imageFactory.createImage("player.png");
		mOpponentImage = imageFactory.createImage("opponent.png");
		
		mBallDestination.set(viewCenter.x - halfBall, // left
				viewCenter.y - halfBall, // top
				viewCenter.x + halfBall, // right
				viewCenter.y + halfBall); // bottom
		
		mPlayerDestination.set(DISTANCE_FROM_EDGE, // left 
				viewCenter.y - halfPaddleHeight, // top
				DISTANCE_FROM_EDGE + PADDLE_WIDTH, // right
				viewCenter.y + halfPaddleHeight); // bottom
		
		mOpponentDestination.set(viewDimensions.x -(DISTANCE_FROM_EDGE + PADDLE_WIDTH), // left
				viewCenter.y - halfPaddleHeight, // top
				viewDimensions.x - DISTANCE_FROM_EDGE, // right
				viewCenter.y + halfPaddleHeight); // bottom
	}
	
	public void moveBall(float elapsedTimeInSeconds) {
		
		Point viewDimensisons = getDimensions();
		
		if(mBallMoveRight == true) {
			mBallDestination.left += BALL_SPEED * elapsedTimeInSeconds;
			mBallDestination.right += BALL_SPEED * elapsedTimeInSeconds;
			mBallDestination.top -= 2;
			mBallDestination.bottom -= 2;
			if(mBallDestination.right >= viewDimensisons.x || mBallDestination.top <= 0) {
				mBallDestination.left = viewDimensisons.x - BALL_SIZE;
				mBallDestination.top = 0;
				mBallDestination.right = viewDimensisons.x;
				mBallDestination.bottom = BALL_SIZE; 
				mBallMoveRight = false;
			}
		} else {
			mBallDestination.left -= BALL_SPEED * elapsedTimeInSeconds;
			mBallDestination.right -= BALL_SPEED * elapsedTimeInSeconds;
			mBallDestination.top += 2;
			mBallDestination.bottom += 2;
			if(mBallDestination.left < 0 || mBallDestination.bottom >= viewDimensisons.y) {
				mBallDestination.left = 0;
				mBallDestination.top = viewDimensisons.y - BALL_SIZE;
				mBallDestination.right = BALL_SIZE;
				mBallDestination.bottom = viewDimensisons.y;
				mBallMoveRight = true;
			}
					
		}
		
	}
	
	public void moveOpponent(float elapsedTimeInSeconds) {
		Point viewDimensioins = getDimensions();
		
		if(mOpponentMoveDown == true) {
			mOpponentDestination.top += OPPONENT_SPEED * elapsedTimeInSeconds;
			mOpponentDestination.bottom += OPPONENT_SPEED * elapsedTimeInSeconds;
			if(mOpponentDestination.bottom >= viewDimensioins.y) {
				mOpponentDestination.top = viewDimensioins.y - PADDLE_HEIGHT;
				mOpponentDestination.bottom = viewDimensioins.y;
				mOpponentMoveDown = false;
			}
			
		} else {
			mOpponentDestination.top -= OPPONENT_SPEED * elapsedTimeInSeconds;
			mOpponentDestination.bottom -= OPPONENT_SPEED * elapsedTimeInSeconds;
			if(mOpponentDestination.top < 0) {
				mOpponentDestination.top = 0;
				mOpponentDestination.bottom = PADDLE_HEIGHT;
				mOpponentMoveDown = true;
			}
		}
		
	}
	
	private static final int MULTIPLIER = 1;
	
	public void movePlayer(float x, float y, boolean relative) {
		Point viewDimensions = getDimensions();
		mPlayerDestination.top += y * MULTIPLIER;
		mPlayerDestination.bottom += y * MULTIPLIER;
		
		if(relative) {
			if(mPlayerDestination.top < 0) {
				mPlayerDestination.top = 0;
				mPlayerDestination.bottom = PADDLE_HEIGHT;
			}
			else if(mPlayerDestination.bottom > viewDimensions.y) {
				mPlayerDestination.top = viewDimensions.y - PADDLE_HEIGHT;
				mPlayerDestination.bottom = viewDimensions.y;
			}
		}
	}

}
