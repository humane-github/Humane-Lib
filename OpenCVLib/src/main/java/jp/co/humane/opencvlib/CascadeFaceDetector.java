package jp.co.humane.opencvlib;

import java.util.HashMap;

import jp.co.humane.opencvlib.exception.CascadeClassifierException;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class CascadeFaceDetector
{
	private HashMap<String,CascadeClassifier> cascadeMap = null;
	
	/**
	 * コンストラクタ
	 * **/
	public CascadeFaceDetector(){}
	
	/**
	 * Cascadeを作成する
	 * 
	 * @param name	名称
	 * @param path	Cascadeファイルの絶対パス
	 * **/
	public void addCascade(String name,String path) throws CascadeClassifierException
	{
		if( name == null || name.length() < 1 ){return;}
		if( path == null || path.length() < 1 ){return;}		
		if( cascadeMap == null ){cascadeMap = new HashMap<String,CascadeClassifier>();}
		
		CascadeClassifier cascade = new CascadeClassifier();
		if( !cascade.load(path) ){throw new CascadeClassifierException(path+" load error", null);}
		else
		{
			cascadeMap.put(name, cascade);
		}
	}

	/**
	 * 指定した画像中から顔検出を行う
	 * 
	 * @param	targetMat	処理対象の画像（Mat形式）
	 * @return	int			検出した顔の数
	 * **/
	public int detect(Mat targetMat)
	{
		for( String name : cascadeMap.keySet() )
		{
			int faces = workDetect(name,targetMat);
			if( faces > 0 ){return faces;}
		}
		return 0;
	}
	
	private int workDetect(String name,Mat targetMat)
	{
		MatOfRect faces = new MatOfRect();
		CascadeClassifier cascade = cascadeMap.get(name);
		cascade.detectMultiScale(targetMat, faces, 1.1, 2,Objdetect.CASCADE_SCALE_IMAGE , new Size(30,30),new Size());
		return faces.toArray().length;
	}
}
