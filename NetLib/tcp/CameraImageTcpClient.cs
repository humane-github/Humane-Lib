using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Drawing;

namespace NetLib.tcp
{
    public class CameraImageTcpClient : HTcpClient
    {
        /// <summary>
        /// カメラ映像を取得する
        /// </summary>
        /// <returns></returns>
        public Bitmap getImage()
        {
            Bitmap result = null;
            DataPacket packet = null;
            byte[] bytes = base.recv();
            packet = new DataPacket(bytes);
            if (packet.isSendIMG())
            {
                byte[] recv = packet.m_body;
                MemoryStream ms = new MemoryStream(recv);
                try
                {
                    result = new Bitmap(ms);
                }
                catch (ArgumentException ex)
                {
                    Console.WriteLine("不正パケットのため破棄");
                }
            }
            return result;
        }
    }
}
