package WEB-INF.classes.net.umusic.milestone.alps;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import net.umusic.milestone.alps.DcDistributionCoData;
import net.umusic.milestone.alps.DcGDRSResults;
import net.umusic.milestone.alps.DcLabelData;
import net.umusic.milestone.alps.DcMilestoneProjectSearchFilter;
import net.umusic.milestone.alps.DcProjectSearchFilter;
import net.umusic.milestone.alps.DcProjectSearchResults;
import net.umusic.milestone.alps.DsLabelStructureInterface;
import net.umusic.milestone.alps.DsProjectStructureInterface;
import net.umusic.milestone.alps.IArchimedesProject;
import net.umusic.milestone.alps.IArchimedesProjectProxy;
import net.umusic.milestone.alps.ProjectNumberValidResults;
import org.tempuri.ArchimedesProjectLocator;

public class IArchimedesProjectProxy implements IArchimedesProject {
  private String _endpoint;
  
  private IArchimedesProject iArchimedesProject;
  
  public IArchimedesProjectProxy() {
    this._endpoint = null;
    this.iArchimedesProject = null;
    _initIArchimedesProjectProxy();
  }
  
  public IArchimedesProjectProxy(String endpoint) {
    this._endpoint = null;
    this.iArchimedesProject = null;
    this._endpoint = endpoint;
    _initIArchimedesProjectProxy();
  }
  
  private void _initIArchimedesProjectProxy() {
    try {
      this.iArchimedesProject = (new ArchimedesProjectLocator()).getbasicAP();
      if (this.iArchimedesProject != null)
        if (this._endpoint != null) {
          ((Stub)this.iArchimedesProject)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
        } else {
          this._endpoint = (String)((Stub)this.iArchimedesProject)._getProperty("javax.xml.rpc.service.endpoint.address");
        }  
    } catch (ServiceException serviceException) {}
  }
  
  public String getEndpoint() { return this._endpoint; }
  
  public void setEndpoint(String endpoint) {
    this._endpoint = endpoint;
    if (this.iArchimedesProject != null)
      ((Stub)this.iArchimedesProject)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint); 
  }
  
  public IArchimedesProject getIArchimedesProject() {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject;
  }
  
  public DcProjectSearchResults[] projectSearchGetDataContract(DcProjectSearchFilter searchParam) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.projectSearchGetDataContract(searchParam);
  }
  
  public DcProjectSearchResults[] projectSearchUMVDGetDataContract(DcProjectSearchFilter searchParam) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.projectSearchUMVDGetDataContract(searchParam);
  }
  
  public DcProjectSearchResults[] projectSearchMilestoneGetDataContract(DcMilestoneProjectSearchFilter searchParam) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.projectSearchMilestoneGetDataContract(searchParam);
  }
  
  public Boolean projectNumberValidationGet(String projectNumber) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.projectNumberValidationGet(projectNumber);
  }
  
  public DcLabelData labelDataGetDataContractById(Integer labelID) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.labelDataGetDataContractById(labelID);
  }
  
  public DcLabelData[] labelDataGetDataContract() throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.labelDataGetDataContract();
  }
  
  public DsProjectStructureInterface projectGetByID(Integer archieID) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.projectGetByID(archieID);
  }
  
  public DsLabelStructureInterface labelGetByID(Integer archieID) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.labelGetByID(archieID);
  }
  
  public ProjectNumberValidResults projectSearchValidation(String projectNumber, Boolean isUMVD) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.projectSearchValidation(projectNumber, isUMVD);
  }
  
  public DcDistributionCoData[] distributionCoGetDataContract() throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.distributionCoGetDataContract();
  }
  
  public Boolean milestoneSnapshotProjectInsert(String projectID) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.milestoneSnapshotProjectInsert(projectID);
  }
  
  public DcGDRSResults GDRSProductStatusGet(Integer releaseID, Integer releaseFamilyID, Integer environmentID) throws RemoteException {
    if (this.iArchimedesProject == null)
      _initIArchimedesProjectProxy(); 
    return this.iArchimedesProject.GDRSProductStatusGet(releaseID, releaseFamilyID, environmentID);
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\IArchimedesProjectProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */