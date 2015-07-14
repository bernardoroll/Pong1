package com.book.pong_v1;

import com.book.simplegameengine_v1.SGView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

public class GameView extends SGView {
	
	private Paint mTempPaint = new Paint();
	
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
	
	public GameView(Context context) {
		super(context);
	}
	
	@Override
	public void step(Canvas canvas, float elapsedTimeInSeconds) {
		moveBall(elapsedTimeInSeconds);
		moveOpponent(elapsedTimeInSeconds);
		mTempPaint.setColor(Color.RED);		
		canvas.drawRect(mPlayerDestination, mTempPaint);		
		mTempPaint.setColor(Color.BLACK);
		// Bola redonda
		canvas.drawCircle(mBallDestination.centerX(), mBallDestination.centerY(), BALL_SIZE, mTempPaint);
		//canvas.drawCircle(mBallDestination.exactCenterX(), mBallDestination.exactCenterY(), BALL_SIZE, mTempPaint);
		// Bola "quadrada"
		//canvas.drawRect(mBallDestination, mTempPaint);
		mTempPaint.setColor(Color.BLUE);
		canvas.drawRect(mOpponentDestination, mTempPaint);
	}
	
	@Override
	protected void setup() {
		Point viewDimensions = getDimensions();
		Point viewCenter = new Point(viewDimensions.x / 2, viewDimensions.y / 2);
		
		int halfBall = BALL_SIZE / 2;
		int halfPaddleHeight = PADDLE_HEIGHT / 2;
		
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
			if(mBallDestination.right >= viewDimensisons.x) {
				mBallDestination.left = viewDimensisons.x - BALL_SIZE;
				mBallDestination.right = viewDimensisons.x;
				mBallMoveRight = false;
			}
		} else {
			mBallDestination.left -= BALL_SPEED * elapsedTimeInSeconds;
			mBallDestination.right -= BALL_SPEED * elapsedTimeInSeconds;
			if(mBallDestination.left < 0) {
				mBallDestination.left = 0;
				mBallDestination.right = BALL_SIZE;
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

}
