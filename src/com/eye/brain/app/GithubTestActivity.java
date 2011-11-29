package com.eye.brain.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class GithubTestActivity extends Activity implements OnTouchListener {
	ImageView target;
	private Animation anime;
	int startX;
	int startY;
	int currentX;
	int currentY;
	int offsetX;
	int offsetY;
	private Rect rect = new Rect();
	private Button bt2;
	private boolean isBt2Click;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources r = getResources();
		bt2 = (Button) findViewById(R.id.Button02);
		Bitmap bmp = BitmapFactory.decodeResource(r, R.drawable.ic_launcher);
		anime = AnimationUtils.loadAnimation(this, R.anim.sample);
		anime.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (isBt2Click) {
					bt2.performClick();
					isBt2Click = false;
				}
			}
		});

		target = (ImageView) findViewById(R.id.ImageView01);
		target.setImageBitmap(bmp);
		this.target.setOnTouchListener(this);

	}

	public void onClickShowButton(View v) {

		Rect rect = new Rect();
		Point globalOffset = new Point();
		target.getGlobalVisibleRect(rect, globalOffset);
		currentX = startX;
		currentY = startY;
		target.layout(currentX, currentY, currentX + target.getWidth(),
				currentY + target.getHeight());
		target.setVisibility(View.VISIBLE);
	}

	public void onClickButton02(View v) {
		// Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
//		Intent intent = new Intent(DragAndDropSample.this, Next.class);
//		startActivity(intent);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) event.getRawX();
		int y = (int) event.getRawY();

		if (event.getAction() == MotionEvent.ACTION_MOVE) {

			int diffX = offsetX - x;
			int diffY = offsetY - y;

			currentX -= diffX;
			currentY -= diffY;
			target.layout(currentX, currentY, currentX + target.getWidth(),
					currentY + target.getHeight());

			offsetX = x;
			offsetY = y;

		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			offsetX = x;
			offsetY = y;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			target.setAnimation(anime);
			target.startAnimation(anime);
			target.setVisibility(View.GONE);

			bt2.getHitRect(rect);
			if (rect.contains(x, y)) {
				isBt2Click = true;
			}
		}

		return true;
	}
}