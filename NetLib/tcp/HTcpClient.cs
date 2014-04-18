using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.IO;
using System.Drawing;

namespace NetLib.tcp
{
    public class HTcpClient
    {
        private static int RECV_BYTE = 256;
        private TcpClient m_tcpClient { get; set; }
        private NetworkStream m_netStream { get; set; }
        protected Encoding m_encoding { get; set; }

        /// <summary>
        /// コンストラクタ
        /// </summary>
        public HTcpClient()
        {
            m_encoding = Encoding.UTF8;
        }

        /// <summary>
        /// サーバーに接続する
        /// </summary>
        /// <param name="hostname"></param>
        /// <param name="port"></param>
        public bool connect(String hostname, int port) 
        {
            try
            {
                m_tcpClient = new TcpClient(hostname, port);
                m_netStream = m_tcpClient.GetStream();
            }
            catch (SocketException ex)
            {
                return false;
            }
            return true;
        }

        /// <summary>
        /// 切断します
        /// </summary>
        public void close()
        {
            m_netStream.Dispose();
            m_tcpClient.Close();
            
        }

        public bool isConnect()
        {
            if (m_tcpClient == null) { return false; }
            return m_tcpClient.Connected;
        }

        /// <summary>
        /// 読み取りデータが存在するか確認する
        /// </summary>
        /// <returns></returns>
        public bool available()
        {
            try
            {
                return m_netStream.DataAvailable;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        /// <summary>
        /// データを送信する
        /// </summary>
        /// <param name="data"></param>
        public void send(String data)
        {
            byte[] sendBytes = m_encoding.GetBytes(data);
            send(sendBytes);
        }
        /// <summary>
        /// データを送信する
        /// </summary>
        /// <param name="data"></param>
        public void send(byte[] data)
        {
            m_netStream.Write(data, 0, data.Length);
        }

        /// <summary>
        /// データを受信する
        /// </summary>
        /// <returns></returns>
        public byte[] recv()
        {
            byte[] result = null;
            if (!isConnect()) { return result; }
            MemoryStream ms = new MemoryStream();
            byte[] recvBytes = new byte[RECV_BYTE];
            do
            {
                int recvSize = m_netStream.Read(recvBytes, 0, recvBytes.Length);
                if (recvSize != 0)
                {
                    ms.Write(recvBytes, 0, recvSize);
                }
            }while(m_netStream.DataAvailable);
            result = ms.GetBuffer();
            ms.Close();
            return result;
        }

    }
}
