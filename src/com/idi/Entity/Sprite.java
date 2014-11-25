package com.idi.Entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Iterator;

public class Sprite
{
	protected Bitmap sprite;
	protected Rect   bounds;

	public Sprite (Bitmap base, Rect place)
	{
		sprite = Bitmap.createBitmap(base, place.left, place.top, place.width(), place.height());
		bounds = new Rect(0, 0, place.width(), place.height());
	}

	public Sprite (Bitmap base, Rect place, int scale)
	{
		Bitmap _bmp = Bitmap.createBitmap(base, place.left, place.top, place.width(), place.height());
		sprite = Bitmap.createScaledBitmap(_bmp
				, place.width() * scale
				, place.height() * scale
				, true);

		bounds = new Rect(0, 0, place.width() * scale, place.height() * scale);
	}

	public void draw (Canvas c)
	{
		c.translate(0, 0);
		c.rotate(0+90);

		Paint p = new Paint();
		p.setColor(Color.GREEN);
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(2);

		c.drawBitmap(sprite, -bounds.width() / 2, -bounds.height() / 2, null);
//		if (t.gameObject.collider != null) c.drawCircle(0, 0, t.gameObject.collider.getRadius(), p);

		c.rotate(-0-90);
		c.translate(-0, -0);

		//t.draw(c);

		//t.getMatrix().reset();
	}

	public void draw ( Canvas c, Paint p)
	{
		c.translate(0, 0);
		c.rotate(0+90);

		c.drawBitmap(sprite, -bounds.width() / 2, -bounds.height() / 2, p);

		c.rotate(-0-90);
		c.translate(-0, -0);

		//t.draw(c);

		//t.getMatrix().reset();
	}

	public Bitmap getSprite() {
		return sprite;
	}

	public void setSprite(Bitmap sprite) {
		this.sprite = sprite;
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect bounds) {
		this.bounds = bounds;
	}
	
}
