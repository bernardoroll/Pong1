package com.book.pong_v1;

import com.book.simplegameengine_v1.SGView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class GameView extends SGView {
	
	private Paint mTempPaint = new Paint();
	
	private final static int BALL_SIZE = 16;
	private final static int DISTANCE_FROM_EDGE = 16;
	private final static int PADDLE_HEIGHT = 92;
	private final static int PADDLE_WIDTH = 23;
	private final static int BALL_SPEED = 2;
	private final static int OPPONENT_SPEED = 2;
	
	private boolean mBallMoveRight = true;
	private boolean mOpponentMoveDown = true;
	
	private Rect mBallDestination = new Rect();	
	private Rect mOpponentDestination = new Rect();
	private Rect mPlayerDestination = new Rect();
	
	public GameView(Context context) {
		super(context);
	}
	
	@Override
	public void step(Canvas canvas) {
		moveBall();
		moveOpponent();
		mTempPaint.setColor(Color.RED);		
		canvas.drawRect(mPlayerDestination, mTempPaint);		
		mTempPaint.setColor(Color.BLACK);
		// Bola redonda
		canvas.drawCircle(mBallDestination.exactCenterX(), mBallDestination.exactCenterY(), BALL_SIZE, mTempPaint);
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
	
	public void moveBall() {
		
		Point viewDimensisons = getDimensions();
		
		if(mBallMoveRight == true) {
			mBallDestination.left += BALL_SPEED;
			mBallDestination.right += BALL_SPEED;
			if(mBallDestination.right >= viewDimensisons.x) {
				mBallDestination.left = viewDimensisons.x - BALL_SIZE;
				mBallDestination.right = viewDimensisons.x;
				mBallMoveRight = false;
			}
		} else {
			mBallDestination.left -= BALL_SPEED;
			mBallDestination.right -= BALL_SPEED;
			if(mBallDestination.left < 0) {
				mBallDestination.left = 0;
				mBallDestination.right = BALL_SIZE;
				mBallMoveRight = true;
			}
					
		}
		
	}
	
	public void moveOpponent() {
		Point viewDimensioins = getDimensions();
		
		if(mOpponentMoveDown == true) {
			mOpponentDestination.top += OPPONENT_SPEED;
			mOpponentDestination.bottom += OPPONENT_SPEED;
			if(mOpponentDestination.bottom >= viewDimensioins.y) {
				mOpponentDestination.top = viewDimensioins.y - PADDLE_HEIGHT;
				mOpponentDestination.bottom = viewDimensioins.y;
				mOpponentMoveDown = false;
			}
			
		} else {
			mOpponentDestination.top -= OPPONENT_SPEED;
			mOpponentDestination.bottom -= OPPONENT_SPEED;
			if(mOpponentDestination.top < 0) {
				mOpponentDestination.top = 0;
				mOpponentDestination.bottom = PADDLE_HEIGHT;
				mOpponentMoveDown = true;
			}
		}
		
	}

}
