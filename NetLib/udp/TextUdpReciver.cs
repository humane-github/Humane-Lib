using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;
using NetLib.tcp;

namespace NetLib.udp
{
    public class TextUdpReciver : AUdpClient
    {
        /// <summary>
        /// コンストラクタ
        /// </summary>
        /// <param name="port">ポート番号</param>
        /// <param name="encoding">文字コードの種類</param>
        public TextUdpReciver(int port,Encoding encoding)
        {
            createUdpClient(port, encoding);
        }

        /// <summary>
        /// データを送信する
        /// </summary>
        /// <param name="data"></param>
        /// <param name="length"></param>
        /// <param name="hostname"></param>
        /// <param name="portnum"></param>
        public override void send(byte[] data, int length, String hostname, int portnum)
        {
        }

        /// <summary>
        /// データを受信する
        /// </summary>
        /// <returns>受信データ</returns>
        public override object recive()
        {
            IPEndPoint remoteEP = null;
            byte[] recvByte = UdpClient.Receive(ref remoteEP);
            DataPacket packet = null;
            packet = new DataPacket(recvByte);
            if (packet.isSendMSG())
            {
                return Encoding.GetString(packet.m_body);
            }
            else
            {
                return null;
            }
        }
    }
}
