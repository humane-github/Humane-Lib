using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;
using NetLib.tcp;

namespace NetLib.udp
{
    public class TextUdpSender : AUdpClient
    {
        /// <summary>
        /// コンストラクタ
        /// </summary>
        /// <param name="encoding">文字コードの種類</param>
        public TextUdpSender(Encoding encoding)
        {
            createUdpClient(encoding);
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
            DataPacket packet = new DataPacket(DataPacket.TYPE_SENDMSG,data);
            UdpClient.Send(packet.m_data, packet.m_data.Length, hostname, portnum);
        }

        /// <summary>
        /// データを受信する
        /// </summary>
        /// <returns>受信データ</returns>
        public override object recive()
        {
            return null;
        }
    }
}
