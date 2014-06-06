package jp.co.humane.opencvlib;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MatFactory
{
	public static enum MatType
	{
		DEFAULT,
		DEFAULT2,
		COLOR_8BIT,
		MONO_8BIT
	};

	/**
	 * Matを作成する
	 * @param width		画像の幅
	 * @param height	画像の高さ
	 * @param type		画像の種類（モノクロorカラー)
	 * **/
	public static Mat create(int width,int height,MatType type)
	{
		int bpp = 24;
		if( type == MatType.MONO_8BIT ){bpp = 8;}
		else if( type == MatType.COLOR_8BIT ){bpp = 24;}
		return MatFactory.create(width, height, bpp);
	}
	
	/**
	 * Matを作成する
	 * @param width		画像の幅
	 * @param height	画像の高さ
	 * @param bpp		ビット深度（8=モノクロ、24=カラー）デフォルトはカラー
	 * **/
	public static Mat create(int width,int height,int bpp)
	{
		int t = 0;
		switch(bpp)
		{
			case 8:
				t = CvType.CV_8UC1;
        		break;
			case 24:
				t = CvType.CV_8UC3;
				break;
			default:
				t = CvType.CV_8UC3;
				break;
		}
		return new Mat(height,width,t);
	}
	
	/**
	 * Matを作成する
	 * @param width		画像の幅
	 * @param height	画像の高さ
	 * @param bpp		ビット深度（8=モノクロ、24=カラー）デフォルトはカラー
	 * @param pixels	画像データ
	 * **/
	public static Mat create(int width,int height,int bpp,byte[] pixels)
	{
		Mat m = MatFactory.create(width, height,bpp);
		m.put(0,0, pixels);
		return m;
	}
}
