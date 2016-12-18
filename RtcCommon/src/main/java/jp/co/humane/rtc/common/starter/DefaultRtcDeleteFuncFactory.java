package jp.co.humane.rtc.common.starter;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;

/**
 * RtcDeleteFuncのファクトリクラス。
 * @author terada.
 *
 */
public class DefaultRtcDeleteFuncFactory implements RtcDeleteFuncFactory {

    /**
     * デフォルトコンストラクタ。
     */
    public DefaultRtcDeleteFuncFactory() {
        super();
    }

    /**
     * Manager経由でRtcDeleteFuncを生成するファクトリメソッド。
     * @param manager RTCマネージャ。
     * @return RTC生成処理。
     */
    @Override
    public RtcDeleteFunc create(Manager manager) {

        RtcDeleteFunc rtcDeleteFunc = new RtcDeleteFunc() {

            /**
             * 特に処理は行わない。
             * @param rtcBase RTCインスタンス。
             */
            @Override
            public void deleteRtc(RTObject_impl rtcBase) {
                rtcBase = null;
            }
        };

        return rtcDeleteFunc;
    }
}
