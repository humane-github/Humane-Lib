using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;

namespace NetLib.udp
{
    public abstract class AUdpClient
    {
        protected UdpClient UdpClient { get; set; }
        protected Encoding Encoding { get; set; }

        /// <summary>
        /// UDPクライアントを作る
        /// </summary>
        /// <param name="encoding">文字コード</param>
        protected void createUdpClient(Encoding encoding)
        {
            createUdpClient(0, encoding);
        }

        /// <summary>
        /// UDPクライアントを作る
        /// </summary>
        /// <param name="port">ポート番号（0以下を指定した場合は送信用UDPクライアントとなる）</param>
        /// <param name="encoding">文字コード</param>
        protected void createUdpClient(int port, Encoding encoding)
        {
            if (port > 0)
            {
                UdpClient = new UdpClient(port);
            }
            else
            {
                UdpClient = new UdpClient();
            }
            UdpClient.DontFragment = true;
            UdpClient.EnableBroadcast = true;
            Encoding = encoding;
        }

        /// <summary>
        /// ポートを閉じる
        /// </summary>
        /// <returns></returns>
        public void close()
        {
            if (UdpClient == null) { return; }
            UdpClient.Close();
        }

        /// <summary>
        /// 受信データ量を返す
        /// </summary>
        /// <returns>受信データバイト数</returns>
        public int available()
        {
            if (UdpClient == null) { return -1; }
            return UdpClient.Available;
        }

        /// <summary>
        /// データを送信する
        /// </summary>
        /// <param name="data"></param>
        /// <param name="length"></param>
        /// <param name="hostname"></param>
        /// <param name="portnum"></param>
        abstract public void send(byte[] data,int length,String hostname,int portnum);

        /// <summary>
        /// メッセージを受信する
        /// </summary>
        abstract public Object recive();

    }
}
