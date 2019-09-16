package WEB-INF.classes.com.universal.milestone.pnr;

import com.softwareag.entirex.aci.Broker;
import com.softwareag.entirex.aci.BrokerMessage;
import com.softwareag.entirex.aci.BrokerService;
import com.softwareag.entirex.aci.Conversation;
import com.universal.milestone.pnr.ClientMark;

public class ClientMark {
  static String sServer = "ACLASS/ASERVER/ASERVICE";
  
  public static void main(String[] argv) {
    try {
      String brokerID = "10.130.109.122:18023";
      Broker broker = new Broker(brokerID, "JavaUser");
      BrokerService bService = new BrokerService(broker, sServer);
      BrokerMessage bRequest = new BrokerMessage();
      bService.setMaxReceiveLen(156);
      bService.setDefaultWaittime("10s");
      broker.logon();
      System.out.println("Client started: " + broker.getConnInfo());
      Conversation conv = new Conversation(bService);
      bRequest.setMessage("s1dv".toUpperCase());
      BrokerMessage bReply = conv.sendReceive(bRequest);
      System.out.println("Returned: " + bReply);
      conv.end();
      broker.logoff();
    } catch (Exception bE) {
      System.out.println(bE);
      System.out.println(String.valueOf(bE.toString()) + " " + bE.toString().length());
      System.out.println(bE.getMessage());
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\ClientMark.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */