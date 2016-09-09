using System;
using System.Collections.Generic;
using System.Text;

using Apache.NMS;
using Apache.NMS.ActiveMQ;
using System.Configuration;

namespace MQListener
{
    class Program
    {

        

        static void Main(string[] args)
        {
            
            //IConnectionFactory factory = new ConnectionFactory();

            //connection = factory.CreateConnection();
            //connection.Start();
            //session = connection.CreateSession();

             

            
            System.Console.WriteLine("1 [local]: ws://localhost:8080/ncr/init");
            System.Console.WriteLine("2 [BK TEST]: ws://192.168.6.175:8080/ncr/init");
            System.Console.WriteLine("3 [BK TEST]: ws://192.168.6.174:8080/ncr_qc/init");
            System.Console.WriteLine("4 [AUstin]: ws://172.21.67.178:8080/ncr_qc/init");
            //System.Console.WriteLine("5: ws://192.168.6.174:8080/ncr_qc/init");

            System.Console.Write("Input Broker URL : ");

            string strBrokerSelection = System.Console.ReadLine();
            string strBrokerUrl = "";
            

            Apache.NMS.ActiveMQ.ConnectionFactory cf = null;
            
            switch(strBrokerSelection){
                case "1":
                    strBrokerUrl = "failover://(tcp://localhost:61616)";
                    break;

                case "2":
                    strBrokerUrl = "failover://(tcp://192.168.6.175:61616)";
                    break;

                case "3":
                    strBrokerUrl = "failover://(tcp://192.168.6.174:61616)";
                    break;

                case "4":
                    strBrokerUrl = "failover://(tcp://172.21.67.178:61616)";
                    break;

                default :
                    cf = new Apache.NMS.ActiveMQ.ConnectionFactory("failover://(tcp://localhost:61616)");
                    break;
            }

            cf = new Apache.NMS.ActiveMQ.ConnectionFactory(strBrokerUrl);
            //cf.AlwaysSessionAsync = false;
            cf.OptimizeAcknowledge = true ;
            
            IConnection connection = cf.CreateConnection();
            connection.Start();

            string strTopic = "NCR_SERVER";
            System.Console.Write("Input Topic : ");

            strTopic = System.Console.ReadLine();

            ISession session = connection.CreateSession(AcknowledgementMode.AutoAcknowledge);
            //Topic topic = session.createTopic("test.topic?consumer.prefetchSize=32766");
            IDestination idestination = session.GetTopic(strTopic);
            IMessageConsumer consumer = session.CreateConsumer(idestination);
            //final AtomicInteger count = new AtomicInteger();
            consumer.Listener += new MessageListener(consumer_Listener);
//            public void onMessage(Message message) {
//            TextMessage textMessage = (TextMessage)message;
//            try {
//            if (count.incrementAndGet()%10000==0)
//            System.err.println("Got = " + textMessage.getText());
//            } catch (JMSException e) {
//            e.printStackTrace();
//}
//}
//});
            while (true)
            {
            }

//            NMSConnectionFactory NMSFactory =
//new NMSConnectionFactory("tcp://localhost:61616");
//            IConnection connection = NMSFactory.CreateConnection();
//            ISession session =
//            connection.
//            CreateSession(AcknowledgementMode.AutoAcknowledge);
//            IDestination destination =
//            session.GetTopic("STOCKS.JAVA");
//            IMessageConsumer consumer =
//            session.CreateConsumer(destination);
//            consumer.Listener += new MessageListener(OnMessage);
//            connection.Start();
//            Console.WriteLine("Press any key to quit.");
//            Console.ReadKey();
        }

        static void consumer_Listener(IMessage message)
        {
            //throw new NotImplementedException();
            //System.Console.WriteLine(message.ToString());
            //message.NMSTimestamp
            long _BrokerInTime = ((Apache.NMS.ActiveMQ.Commands.ActiveMQMessage)(message)).BrokerInTime;
            long _BrokerOutTime = ((Apache.NMS.ActiveMQ.Commands.ActiveMQMessage)(message)).BrokerOutTime;
            DateTime dtm_BrokerInTime = DateTime.Now;
            DateTime dtm_BrokerOutTime = DateTime.Now;
            DateTime dtm_Message = DateTime.Now;
            ITextMessage textMessage = (ITextMessage)message;
            

            DateTime Epoch = new DateTime(1970, 1, 1, 0, 0, 0,DateTimeKind.Utc);
            
            dtm_BrokerInTime = Epoch.AddMilliseconds(_BrokerInTime).ToLocalTime();
            dtm_BrokerOutTime = Epoch.AddMilliseconds(_BrokerOutTime).ToLocalTime();
            string strTemp = textMessage.Text.Substring(0, 150);
            int task_id_01 = strTemp.IndexOf("task_id");
            int task_id_02 = strTemp.IndexOf("transaction_id");

            string strTask_id = "-1";
            string strTransaction_id = "-1";
            try
            {
                if (task_id_02 != -1)
                {
                    strTask_id = strTemp.Substring(task_id_01 + 10, task_id_02 - task_id_01 - 13);
                    strTransaction_id = strTemp.Substring(task_id_02 + 17, 17);
                }
            }
            catch
            {
            }

            if (strTransaction_id.Length > 10)
                dtm_Message = DateTime.ParseExact(strTransaction_id, "yyyyMMddHHmmssfff", System.Globalization.CultureInfo.CurrentCulture);

            System.Console.WriteLine(
                string.Format("{0}\tbrokerTM\t{1}ms\tactualTM\t{2}ms\tMsgTM\t{3}ms\t{4}\t{5}"
                , DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss.fff")
                , (dtm_BrokerOutTime - dtm_BrokerInTime).Milliseconds
                , (DateTime.Now - dtm_BrokerOutTime).Milliseconds
                , (DateTime.Now - dtm_Message).Milliseconds
                , strTask_id
                , strTransaction_id
                )
            );
            //System.Console.WriteLine(dtm_BrokerOutTime.ToString("yyyy-MM-dd HH:mm:ss.fff"));

            //message.te
        }

        public virtual void connect()
        {
            //_factory = new NMSConnectionFactory(new Uri(this._broker));
            //_connection = this._factory.CreateConnection();
            //_connection.Start();
            //_session = this._connection.CreateSession(AcknowledgementMode.ClientAcknowledge);
            //_initialized = true;
        }

        public void OpenListener(string sBrokerUrl, string sDestinationType, string sSubject)
        {
            //int num = 0;
            //if (this._listener != null)
            //{
            //    this._listener = null;
            //}
            //string[] strArrays = sBrokerUrl.Split(new char[] { ',' });
            //this._listener = new ActiveMQListener[(int)strArrays.Length];
            //for (int i = 0; i < (int)strArrays.Length; i++)
            //{
            //    if ((this._listener[i] == null ? true : !this._listener[i].Connection.IsStarted))
            //    {
            //        this._listener[i] = new ActiveMQListener(strArrays[i]);
            //        try
            //        {
            //            this._listener[i].connect();
            //            if (sDestinationType.ToUpper().Equals("QUEUE"))
            //            {
            //                this._listener[i].addQueueListener(sSubject);
            //                this._listener[i].onQueueMsg += new MsgEventHandler(this.onListenMsg);
            //            }
            //            else if (sDestinationType.ToUpper().Equals("TOPIC"))
            //            {
            //                this._listener[i].addTopicListener(sSubject);
            //                this._listener[i].onTopicMsg += new MsgEventHandler(this.onListenMsg);
            //            }
            //        }
            //        catch
            //        {
            //            num++;
            //        }
            //    }
            //}
            //if (num == (int)this._listener.Length)
            //{
            //    throw new Exception("All broker is downed.");
            //}
        }

        private void LoadConfig()
        {
            //List<string> strUrl = new List<string>();
            //string[] strSetingKeys = ConfigurationManager.AppSettings.AllKeys;

            //string[] strCommandList = new string[strSetingKeys.Length];

            //if (strCommandList != null && strCommandList.Length > 0)
            //{
            //    for (int index = 0; index < strSetingKeys.Length; index++)
            //    {
            //        string strKeyName = strSetingKeys[index];
            //        if (strKeyName.IndexOf("Command") == 0)
            //        {
            //            strCommandList[index] = ConfigurationManager.AppSettings[strKeyName].ToString();
            //        }
            //        else if (strKeyName.IndexOf("TimeInterval") == 0)
            //        {
            //            this.tbxTimeInterval.Text = ConfigurationManager.AppSettings[strKeyName].ToString();
            //        }
            //        else if (strKeyName.IndexOf("StartMSG") == 0)
            //        {
            //            this.strStartMessage = ConfigurationManager.AppSettings[strKeyName].ToString();
            //        }
            //        else if (strKeyName.IndexOf("ConnectWhenStartUp") == 0)
            //        {
            //            this.bAutoConnect = Convert.ToBoolean(ConfigurationManager.AppSettings[strKeyName].ToString());
            //        }
            //        else if (strKeyName.IndexOf("MaxLoopCount") == 0)
            //        {
            //            this.tbxLoopCnt.Text = ConfigurationManager.AppSettings[strKeyName].ToString();
            //        }
            //        else if (strKeyName.IndexOf("StartUpMode") == 0)
            //        {
            //            this.strStartUpMode = ConfigurationManager.AppSettings[strKeyName].ToString();
            //        }
            //        else if (strKeyName.IndexOf("Connection_URL") == 0)
            //        {
            //            strUrl.Add(ConfigurationManager.AppSettings[strKeyName].ToString());
            //        }
            //        else if (strKeyName.IndexOf("TARGET_URL") == 0)
            //        {
            //            this.strTargetURL = ConfigurationManager.AppSettings[strKeyName].ToString();
            //        }

            //    }

            //    strURL_List = new string[strUrl.Count];
            //    strUrl.CopyTo(strURL_List);
            //    //cboCommand.Items.AddRange(strCommandList);
            //}
        }
    }
}
