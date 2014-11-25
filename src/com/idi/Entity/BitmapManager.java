package com.idi.Entity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class BitmapManager
{

	private static BitmapManager bitmapManager;
	private final String TAG = "BitmapManager";
	private AssetManager am;

	private BitmapManager ()
	{
	}

	public static BitmapManager getInstance ()
	{
		return BMHolder.instance;
	}

	public void setAssetManager (AssetManager a)
	{
		am = a;

	}

	@SuppressWarnings ("deprecation")
	public Bitmap loadBitmap (String path, AssetManager asset)
	{
		Bitmap bmp = null;
		am=asset;
		try
		{
			InputStream is = am.open(path);
			bmp = BitmapFactory.decodeStream(is);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return bmp;
	}

	private static class BMHolder
	{
		private final static BitmapManager instance = new BitmapManager();
	}
}