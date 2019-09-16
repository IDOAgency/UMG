package WEB-INF.classes.com.universal.milestone.jms;

import com.universal.milestone.jms.MessageObject;
import com.universal.milestone.jms.QueueSend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueSend {
  public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
  
  public static final String JMS_FACTORY = "com.universal.milestone.jms.MessageEmailConnectionFactory";
  
  public static final String QUEUE = "com.universal.milestone.jms.MessageEmailQueue";
  
  private QueueConnectionFactory qconFactory;
  
  private QueueConnection qcon;
  
  private QueueSession qsession;
  
  private QueueSender qsender;
  
  private Queue queue;
  
  private TextMessage msg;
  
  private ObjectMessage objMsg;
  
  public void init(Context ctx, String queueName) throws NamingException, JMSException {
    this.qconFactory = (QueueConnectionFactory)ctx.lookup("com.universal.milestone.jms.MessageEmailConnectionFactory");
    this.qcon = this.qconFactory.createQueueConnection();
    this.qsession = this.qcon.createQueueSession(false, 1);
    this.queue = (Queue)ctx.lookup(queueName);
    this.qsender = this.qsession.createSender(this.queue);
    this.msg = this.qsession.createTextMessage();
    this.objMsg = this.qsession.createObjectMessage();
    this.qcon.start();
  }
  
  public void send(String message) throws JMSException {
    this.msg.setText(message);
    this.qsender.send(this.msg);
  }
  
  public void sendObjMsg(MessageObject message) throws JMSException {
    this.objMsg.setObject(message);
    this.qsender.send(this.objMsg);
  }
  
  public void close() {
    this.qsender.close();
    this.qsession.close();
    this.qcon.close();
  }
  
  public static void main(String[] args) throws Exception {
    InitialContext ic = getInitialContext("t3://localhost:7001");
    QueueSend qs = new QueueSend();
    qs.init(ic, "com.universal.milestone.jms.MessageEmailQueue");
    readAndSend(qs);
    qs.close();
  }
  
  private static void readAndSend(QueueSend qs) throws IOException, JMSException {
    BufferedReader msgStream = new BufferedReader(new InputStreamReader(System.in));
    String line = null;
    boolean quitNow = false;
    do {
      System.out.print("Enter message (\"quit\" to quit): ");
      line = msgStream.readLine();
      if (line == null || line.trim().length() == 0)
        continue; 
      MessageObject msgObj = new MessageObject();
      msgObj.setEmailBody(line);
      qs.sendObjMsg(msgObj);
      System.out.println("JMS Message Sent: " + line + "\n");
      quitNow = line.equalsIgnoreCase("quit");
    } while (!quitNow);
  }
  
  private static InitialContext getInitialContext(String url) throws NamingException {
    Hashtable env = new Hashtable();
    env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
    env.put("java.naming.provider.url", url);
    return new InitialContext(env);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\jms\QueueSend.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */