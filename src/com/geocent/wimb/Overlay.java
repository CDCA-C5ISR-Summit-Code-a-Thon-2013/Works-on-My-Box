package com.geocent.wimb;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Overlay extends View 
{

	ArrayList<Rect> rects = new ArrayList<Rect>();
	
	public Overlay(Context context) 
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void draw(Canvas canvas) 
	{
		super.draw(canvas);

		Paint p = new Paint();
		p.setColor(Color.RED);
		p.setAlpha(255);
		p.setStrokeWidth(4.0f);
		canvas.drawText("PREVIEW", canvas.getWidth() / 2,
								canvas.getHeight() / 2, p);
		
		for( Rect r : rects )
		{
			canvas.drawRect(r, p);
		}
	}

	public void reset()
	{
		rects.clear();
	}
	
	public void drawRect(Rect rect) 
	{
		rects.add(rect);
	}

}
