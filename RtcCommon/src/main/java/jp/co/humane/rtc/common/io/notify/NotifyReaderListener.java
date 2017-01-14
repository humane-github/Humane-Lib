package jp.co.humane.rtc.common.io.notify;

/**
 * NotifyReaderの通知用インタフェース。
 * @author terada.
 *
 */
public interface NotifyReaderListener {

    /**
     * 1行読み取れた際に呼ばれるメソッド。
     * @param line 行情報。
     */
    public void onReadLine(String line);
}
