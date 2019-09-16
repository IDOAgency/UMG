package WEB-INF.classes.net.umusic.milestone.alps;

import java.rmi.Remote;
import java.rmi.RemoteException;
import net.umusic.milestone.alps.DcDistributionCoData;
import net.umusic.milestone.alps.DcGDRSResults;
import net.umusic.milestone.alps.DcLabelData;
import net.umusic.milestone.alps.DcMilestoneProjectSearchFilter;
import net.umusic.milestone.alps.DcProjectSearchFilter;
import net.umusic.milestone.alps.DcProjectSearchResults;
import net.umusic.milestone.alps.DsLabelStructureInterface;
import net.umusic.milestone.alps.DsProjectStructureInterface;
import net.umusic.milestone.alps.ProjectNumberValidResults;

public interface IArchimedesProject extends Remote {
  DcProjectSearchResults[] projectSearchGetDataContract(DcProjectSearchFilter paramDcProjectSearchFilter) throws RemoteException;
  
  DcProjectSearchResults[] projectSearchUMVDGetDataContract(DcProjectSearchFilter paramDcProjectSearchFilter) throws RemoteException;
  
  DcProjectSearchResults[] projectSearchMilestoneGetDataContract(DcMilestoneProjectSearchFilter paramDcMilestoneProjectSearchFilter) throws RemoteException;
  
  Boolean projectNumberValidationGet(String paramString) throws RemoteException;
  
  DcLabelData labelDataGetDataContractById(Integer paramInteger) throws RemoteException;
  
  DcLabelData[] labelDataGetDataContract() throws RemoteException;
  
  DsProjectStructureInterface projectGetByID(Integer paramInteger) throws RemoteException;
  
  DsLabelStructureInterface labelGetByID(Integer paramInteger) throws RemoteException;
  
  ProjectNumberValidResults projectSearchValidation(String paramString, Boolean paramBoolean) throws RemoteException;
  
  DcDistributionCoData[] distributionCoGetDataContract() throws RemoteException;
  
  Boolean milestoneSnapshotProjectInsert(String paramString) throws RemoteException;
  
  DcGDRSResults GDRSProductStatusGet(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3) throws RemoteException;
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\IArchimedesProject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */