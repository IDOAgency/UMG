package WEB-INF.classes.org.tempuri;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;
import net.umusic.milestone.alps.IArchimedesProject;

public interface ArchimedesProject extends Service {
  String getbasicAPAddress();
  
  IArchimedesProject getbasicAP() throws ServiceException;
  
  IArchimedesProject getbasicAP(URL paramURL) throws ServiceException;
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\org\tempuri\ArchimedesProject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */