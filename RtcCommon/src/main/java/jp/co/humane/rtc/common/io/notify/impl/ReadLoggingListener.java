package jp.co.humane.rtc.common.io.notify.impl;

import java.text.MessageFormat;

import jp.co.humane.rtc.common.io.notify.NotifyReaderListener;
import jp.co.humane.rtc.common.logger.RtcLogger;

/**
 * 読み込んだ情報をログに出力するリスナー。
 * @author terada.
 *
 */
public class ReadLoggingListener implements NotifyReaderListener {

    /** ロガー */
    private RtcLogger logger = null;

    /** ログレベル */
    private String logLevel = RtcLogger.LEVEL_INFO;

    /** ログメッセージフォーマット */
    private MessageFormat format = new MessageFormat("{0}");

    /**
     * コンストラクタ。
     * @param logger    ロガー。
     * @param logLevel  ログレベル。
     */
    public ReadLoggingListener(RtcLogger logger, String logLevel) {
        this.logger = logger;
        this.logLevel = logLevel;
    }

    /**
     * コンストラクタ。
     * @param logger    ロガー。
     */
    public ReadLoggingListener(RtcLogger logger) {
        this.logger = logger;
    }

    /**
     * ログ出力するメッセージのフォーマットを指定する。
     * @param str フォーマット。
     */
    public void setFormat(String str) {
        format = new MessageFormat(str);
    }

    /**
     * 読み込んだ時の処理。
     * 指定レベルでログに出力。
     * @param line 読み込んだ文字列。
     */
    @Override
    public void onReadLine(String line) {

        Object[] params = new Object[] { line };
        String message = format.format(params);

        switch (logLevel) {
        case RtcLogger.LEVEL_ERROR:
            logger.error(message);
            break;

        case RtcLogger.LEVEL_WARN:
            logger.warn(message);
            break;

        case RtcLogger.LEVEL_INFO:
            logger.info(message);
            break;

        default:
            logger.debug(message);
        }
    }
}
