using System;
using System.Collections.Generic;
using System.Text;

namespace KillWebSocketTester
{
    class Program
    {
        static void Main(string[] args)
        {
            System.Diagnostics.Process[] processList = System.Diagnostics.Process.GetProcessesByName("WebSocketTest");


            foreach (System.Diagnostics.Process p in processList)
            {
                p.CloseMainWindow();
                //p.Kill();
            }
        }
    }
}
