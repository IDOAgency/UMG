package WEB-INF.classes.com.universal.milestone.push;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface PushRemote extends EJBObject {
  String SendPFM(String paramString1, String paramString2) throws RemoteException;
  
  String TestEJB() throws RemoteException;
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\push\PushRemote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */