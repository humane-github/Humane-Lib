package jp.co.humane.opencvlib;

import org.opencv.core.Core;

public class OpenCVLib {

    /**
     * OpenCVのDLLをロードする
     */
    public static void LoadDLL() {

        try {

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        } catch (UnsatisfiedLinkError ex) {

            String msg = "OpenCVのライブラリ [" + Core.NATIVE_LIBRARY_NAME + "] が見つかりませんでした。"
                       + "環境変数PATHにライブラリ配置ディレクトリを指定するか、"
                       + "起動引数に以下の引数を追加してください。\n"
                       + "-Djava.library.path=(ライブラリ配置ディレクトリ)";
            throw new RuntimeException(msg);

        } catch (SecurityException ex) {

            String msg = "OpenCVのライブラリ [" + Core.NATIVE_LIBRARY_NAME + "] の読み込み権限が不足しています。"
                    + "ライブラリの読み込み権限を確認してください。";
            throw new RuntimeException(msg);

        }
    }
}
