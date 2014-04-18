using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.IO;
using System.Drawing;

namespace NetLib.udp
{
    public class ImageUdpReciver : AUdpClient
    {
        /// <summary>
        /// コンストラクタ
        /// </summary>
        /// <param name="port">ポート番号</param>
        public ImageUdpReciver(int port)
        {
            createUdpClient(port, Encoding.Default);
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
            MemoryStream ms = new MemoryStream(recvByte);
            Bitmap bmp = new Bitmap(ms);
            ms.Close();
            return bmp;
        }
    }
}
