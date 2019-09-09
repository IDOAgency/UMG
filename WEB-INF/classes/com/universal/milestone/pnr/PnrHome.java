package WEB-INF.classes.com.universal.milestone.pnr;

import com.universal.milestone.pnr.PnrRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface PnrHome extends EJBHome {
  PnrRemote create() throws CreateException, RemoteException;
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\pnr\PnrHome.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */