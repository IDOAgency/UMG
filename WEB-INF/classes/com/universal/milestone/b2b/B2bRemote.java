package WEB-INF.classes.com.universal.milestone.b2b;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface B2bRemote extends EJBObject {
  String getRelease(String paramString) throws RemoteException;
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\b2b\B2bRemote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */