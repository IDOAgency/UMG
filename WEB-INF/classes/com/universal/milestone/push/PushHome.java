package WEB-INF.classes.com.universal.milestone.push;

import com.universal.milestone.push.PushRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface PushHome extends EJBHome {
  PushRemote create() throws CreateException, RemoteException;
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\push\PushHome.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */