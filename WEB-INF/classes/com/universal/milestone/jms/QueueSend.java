/*     */ package WEB-INF.classes.com.universal.milestone.jms;
/*     */ 
/*     */ import com.universal.milestone.jms.MessageObject;
/*     */ import com.universal.milestone.jms.QueueSend;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Hashtable;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.ObjectMessage;
/*     */ import javax.jms.Queue;
/*     */ import javax.jms.QueueConnection;
/*     */ import javax.jms.QueueConnectionFactory;
/*     */ import javax.jms.QueueSender;
/*     */ import javax.jms.QueueSession;
/*     */ import javax.jms.TextMessage;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QueueSend
/*     */ {
/*     */   public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
/*     */   public static final String JMS_FACTORY = "com.universal.milestone.jms.MessageEmailConnectionFactory";
/*     */   public static final String QUEUE = "com.universal.milestone.jms.MessageEmailQueue";
/*     */   private QueueConnectionFactory qconFactory;
/*     */   private QueueConnection qcon;
/*     */   private QueueSession qsession;
/*     */   private QueueSender qsender;
/*     */   private Queue queue;
/*     */   private TextMessage msg;
/*     */   private ObjectMessage objMsg;
/*     */   
/*     */   public void init(Context ctx, String queueName) throws NamingException, JMSException {
/*  54 */     this.qconFactory = (QueueConnectionFactory)ctx.lookup("com.universal.milestone.jms.MessageEmailConnectionFactory");
/*  55 */     this.qcon = this.qconFactory.createQueueConnection();
/*  56 */     this.qsession = this.qcon.createQueueSession(false, 1);
/*  57 */     this.queue = (Queue)ctx.lookup(queueName);
/*  58 */     this.qsender = this.qsession.createSender(this.queue);
/*  59 */     this.msg = this.qsession.createTextMessage();
/*  60 */     this.objMsg = this.qsession.createObjectMessage();
/*  61 */     this.qcon.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void send(String message) throws JMSException {
/*  73 */     this.msg.setText(message);
/*  74 */     this.qsender.send(this.msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendObjMsg(MessageObject message) throws JMSException {
/*  86 */     this.objMsg.setObject(message);
/*  87 */     this.qsender.send(this.objMsg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*  98 */     this.qsender.close();
/*  99 */     this.qsession.close();
/* 100 */     this.qcon.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 114 */     InitialContext ic = getInitialContext("t3://localhost:7001");
/* 115 */     QueueSend qs = new QueueSend();
/* 116 */     qs.init(ic, "com.universal.milestone.jms.MessageEmailQueue");
/* 117 */     readAndSend(qs);
/* 118 */     qs.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void readAndSend(QueueSend qs) throws IOException, JMSException {
/* 124 */     BufferedReader msgStream = new BufferedReader(new InputStreamReader(System.in));
/* 125 */     String line = null;
/* 126 */     boolean quitNow = false;
/*     */     do {
/* 128 */       System.out.print("Enter message (\"quit\" to quit): ");
/* 129 */       line = msgStream.readLine();
/* 130 */       if (line == null || line.trim().length() == 0)
/*     */         continue; 
/* 132 */       MessageObject msgObj = new MessageObject();
/* 133 */       msgObj.setEmailBody(line);
/* 134 */       qs.sendObjMsg(msgObj);
/* 135 */       System.out.println("JMS Message Sent: " + line + "\n");
/* 136 */       quitNow = line.equalsIgnoreCase("quit");
/*     */     }
/* 138 */     while (!quitNow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static InitialContext getInitialContext(String url) throws NamingException {
/* 145 */     Hashtable env = new Hashtable();
/* 146 */     env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
/* 147 */     env.put("java.naming.provider.url", url);
/* 148 */     return new InitialContext(env);
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\jms\QueueSend.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */