package jp.co.humane.rtc.common.io.notify;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 別スレッドでBufferedReader#readLine()を実施し、読み取れたときに通知するクラス。
 * @author terada.
 *
 */
public class NotifyReader {

    /** 読み込み結果の通知先 */
    private NotifyReaderListener listener = null;

    /** 読み取り状態 */
    private boolean[] isAlive = new boolean[] { false };

    /** エラー情報 */
    private Exception[] exception = new Exception[0];

    /**
     * コンストラクタ。
     * @param listener 読み込み結果の通知先。
     */
    public NotifyReader(NotifyReaderListener listener) {
        this.listener = listener;
    }

    /**
     * 入力ストリームの入力を監視する。
     * @param is InputStream.
     */
    public void watch(InputStream is) {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        isAlive[0] = true;

        // 読み込み処理を別スレッドで実行
        new Thread(new WatchReader(br, listener, isAlive, exception)).start();

    }

    /**
     * ストリームから読み込みを行うクラス。
     * @author terada.
     *
     */
    protected class WatchReader implements Runnable {

        /** 読み込みクラス */
        private BufferedReader br = null;

        /** 読み込み結果の通知先 */
        private NotifyReaderListener listener = null;

        /** 読み取り状態 */
        private boolean[] isAlive = null;

        /** エラー情報 */
        private Exception[] exception = null;

        /**
         * コンストラクタ。
         * @param br       読み込みクラス。
         * @param listener 読み込み結果の通知先。
         */
        public WatchReader(BufferedReader br, NotifyReaderListener listener,
                            boolean[] isAlive, Exception[] exception) {
            this.br = br;
            this.listener = listener;
            this.isAlive = isAlive;
        }

        /**
         * 読み取り処理の実施。
         */
        @Override
        public void run() {

            try {
                String line = null;
                while((line = br.readLine()) != null){
                    // 1行ごとに読み込んだ情報をリスナーに通知
                    listener.onReadLine(line);
                }

              } catch (Exception ex) {
                  exception = new Exception[] { ex };
              } finally {
                  isAlive[0] = false;
              }
        }
    }

    /**
     * 読み込み処理が続いているかを取得する。
     * @return 処理の継続しているか否か。
     */
    public boolean isAlive() {
        return isAlive[0];
    }

    /**
     * 読み込み処理中に発生した例外があればそれを取得する。
     * 無ければnullを返す。
     * @return 処理中に発生した例外。
     */
    public Exception getException() {
        if (0 == exception.length) {
            return null;
        } else {
            return exception[0];
        }
    }


}
