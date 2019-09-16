package WEB-INF.classes.com.universal.milestone.pnr;

import com.universal.milestone.pnr.PnrHome;
import com.universal.milestone.pnr.PnrRemote;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PnrClient {
  public static void main(String[] arg) throws Exception {
    PnrHome home = null;
    PnrRemote pnr = null;
    System.out.println("Hello from PnrClient.");
    try {
      Properties h = new Properties();
      h.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      h.put("java.naming.provider.url", "t3://localhost:7001");
      Context initial = new InitialContext(h);
      home = (PnrHome)initial.lookup("PnrBean");
      if (home == null) {
        System.out.println("NULL PnrHome.");
      } else {
        System.out.println("PnrHome not null.");
        pnr = home.create();
        System.out.println("PnrBean Created.");
        SimpleDateFormat df = new SimpleDateFormat("m/d/yy");
        String strReply = pnr.getPnr(arg[0], 
            arg[1], 
            arg[2], 
            arg[3], 
            arg[4], 
            arg[5], 
            arg[6], 
            arg[7], 
            df.parse(arg[8]), 
            arg[9], 
            arg[10], 
            arg[11], 
            arg[12]);
        System.out.println("PnrBean getPnr: " + strReply);
      } 
    } catch (Exception e) {
      System.out.println(e.toString());
    } 
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\PnrClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */