package WEB-INF.classes.com.universal.milestone.b2b;

import com.universal.milestone.b2b.B2bRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface B2bHome extends EJBHome {
  B2bRemote create() throws CreateException, RemoteException;
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\b2b\B2bHome.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */