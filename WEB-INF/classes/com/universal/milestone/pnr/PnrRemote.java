package WEB-INF.classes.com.universal.milestone.pnr;

import java.rmi.RemoteException;
import java.util.Date;
import javax.ejb.EJBObject;

public interface PnrRemote extends EJBObject {
  String getPnr(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, Date paramDate, String paramString9, String paramString10, String paramString11, String paramString12) throws RemoteException;
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\PnrRemote.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */