using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace NetLib.tcp
{
    public class CommandTcpClient : HTcpClient
    {
        /// <summary>
        /// 文字列を取得する
        /// </summary>
        /// <returns></returns>
        public String getText()
        {
            String result = null;
            DataPacket packet = null;
            byte[] bytes = base.recv();
            packet = new DataPacket(bytes);
            if (packet.isSendPROPERTY())
            {
                result = m_encoding.GetString(packet.m_body);
                result = result.Replace('\0',' ');
            }
            return result;
        }
    }
}
