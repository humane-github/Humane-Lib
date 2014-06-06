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
	 * Mat���쐬����
	 * @param width		�摜�̕�
	 * @param height	�摜�̍���
	 * @param type		�摜�̎�ށi���m�N��or�J���[)
	 * **/
	public static Mat create(int width,int height,MatType type)
	{
		int bpp = 24;
		if( type == MatType.MONO_8BIT ){bpp = 8;}
		else if( type == MatType.COLOR_8BIT ){bpp = 24;}
		return MatFactory.create(width, height, bpp);
	}
	
	/**
	 * Mat���쐬����
	 * @param width		�摜�̕�
	 * @param height	�摜�̍���
	 * @param bpp		�r�b�g�[�x�i8=���m�N���A24=�J���[�j�f�t�H���g�̓J���[
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
	 * Mat���쐬����
	 * @param width		�摜�̕�
	 * @param height	�摜�̍���
	 * @param bpp		�r�b�g�[�x�i8=���m�N���A24=�J���[�j�f�t�H���g�̓J���[
	 * @param pixels	�摜�f�[�^
	 * **/
	public static Mat create(int width,int height,int bpp,byte[] pixels)
	{
		Mat m = MatFactory.create(width, height,bpp);
		m.put(0,0, pixels);
		return m;
	}
}
