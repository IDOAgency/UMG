/*     */ package WEB-INF.classes.net.umusic.milestone.alps;public class IArchimedesProjectProxy implements IArchimedesProject { private String _endpoint;
/*     */   
/*     */   public IArchimedesProjectProxy() {
/*   4 */     this._endpoint = null;
/*   5 */     this.iArchimedesProject = null;
/*     */ 
/*     */     
/*   8 */     _initIArchimedesProjectProxy();
/*     */   } private IArchimedesProject iArchimedesProject; public IArchimedesProjectProxy(String endpoint) {
/*     */     this._endpoint = null;
/*     */     this.iArchimedesProject = null;
/*  12 */     this._endpoint = endpoint;
/*  13 */     _initIArchimedesProjectProxy();
/*     */   }
/*     */   
/*     */   private void _initIArchimedesProjectProxy() {
/*     */     try {
/*  18 */       this.iArchimedesProject = (new ArchimedesProjectLocator()).getbasicAP();
/*  19 */       if (this.iArchimedesProject != null) {
/*  20 */         if (this._endpoint != null) {
/*  21 */           ((Stub)this.iArchimedesProject)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
/*     */         } else {
/*  23 */           this._endpoint = (String)((Stub)this.iArchimedesProject)._getProperty("javax.xml.rpc.service.endpoint.address");
/*     */         }
/*     */       
/*     */       }
/*  27 */     } catch (ServiceException serviceException) {}
/*     */   }
/*     */ 
/*     */   
/*  31 */   public String getEndpoint() { return this._endpoint; }
/*     */ 
/*     */   
/*     */   public void setEndpoint(String endpoint) {
/*  35 */     this._endpoint = endpoint;
/*  36 */     if (this.iArchimedesProject != null) {
/*  37 */       ((Stub)this.iArchimedesProject)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
/*     */     }
/*     */   }
/*     */   
/*     */   public IArchimedesProject getIArchimedesProject() {
/*  42 */     if (this.iArchimedesProject == null)
/*  43 */       _initIArchimedesProjectProxy(); 
/*  44 */     return this.iArchimedesProject;
/*     */   }
/*     */   
/*     */   public DcProjectSearchResults[] projectSearchGetDataContract(DcProjectSearchFilter searchParam) throws RemoteException {
/*  48 */     if (this.iArchimedesProject == null)
/*  49 */       _initIArchimedesProjectProxy(); 
/*  50 */     return this.iArchimedesProject.projectSearchGetDataContract(searchParam);
/*     */   }
/*     */   
/*     */   public DcProjectSearchResults[] projectSearchUMVDGetDataContract(DcProjectSearchFilter searchParam) throws RemoteException {
/*  54 */     if (this.iArchimedesProject == null)
/*  55 */       _initIArchimedesProjectProxy(); 
/*  56 */     return this.iArchimedesProject.projectSearchUMVDGetDataContract(searchParam);
/*     */   }
/*     */   
/*     */   public DcProjectSearchResults[] projectSearchMilestoneGetDataContract(DcMilestoneProjectSearchFilter searchParam) throws RemoteException {
/*  60 */     if (this.iArchimedesProject == null)
/*  61 */       _initIArchimedesProjectProxy(); 
/*  62 */     return this.iArchimedesProject.projectSearchMilestoneGetDataContract(searchParam);
/*     */   }
/*     */   
/*     */   public Boolean projectNumberValidationGet(String projectNumber) throws RemoteException {
/*  66 */     if (this.iArchimedesProject == null)
/*  67 */       _initIArchimedesProjectProxy(); 
/*  68 */     return this.iArchimedesProject.projectNumberValidationGet(projectNumber);
/*     */   }
/*     */   
/*     */   public DcLabelData labelDataGetDataContractById(Integer labelID) throws RemoteException {
/*  72 */     if (this.iArchimedesProject == null)
/*  73 */       _initIArchimedesProjectProxy(); 
/*  74 */     return this.iArchimedesProject.labelDataGetDataContractById(labelID);
/*     */   }
/*     */   
/*     */   public DcLabelData[] labelDataGetDataContract() throws RemoteException {
/*  78 */     if (this.iArchimedesProject == null)
/*  79 */       _initIArchimedesProjectProxy(); 
/*  80 */     return this.iArchimedesProject.labelDataGetDataContract();
/*     */   }
/*     */   
/*     */   public DsProjectStructureInterface projectGetByID(Integer archieID) throws RemoteException {
/*  84 */     if (this.iArchimedesProject == null)
/*  85 */       _initIArchimedesProjectProxy(); 
/*  86 */     return this.iArchimedesProject.projectGetByID(archieID);
/*     */   }
/*     */   
/*     */   public DsLabelStructureInterface labelGetByID(Integer archieID) throws RemoteException {
/*  90 */     if (this.iArchimedesProject == null)
/*  91 */       _initIArchimedesProjectProxy(); 
/*  92 */     return this.iArchimedesProject.labelGetByID(archieID);
/*     */   }
/*     */   
/*     */   public ProjectNumberValidResults projectSearchValidation(String projectNumber, Boolean isUMVD) throws RemoteException {
/*  96 */     if (this.iArchimedesProject == null)
/*  97 */       _initIArchimedesProjectProxy(); 
/*  98 */     return this.iArchimedesProject.projectSearchValidation(projectNumber, isUMVD);
/*     */   }
/*     */   
/*     */   public DcDistributionCoData[] distributionCoGetDataContract() throws RemoteException {
/* 102 */     if (this.iArchimedesProject == null)
/* 103 */       _initIArchimedesProjectProxy(); 
/* 104 */     return this.iArchimedesProject.distributionCoGetDataContract();
/*     */   }
/*     */   
/*     */   public Boolean milestoneSnapshotProjectInsert(String projectID) throws RemoteException {
/* 108 */     if (this.iArchimedesProject == null)
/* 109 */       _initIArchimedesProjectProxy(); 
/* 110 */     return this.iArchimedesProject.milestoneSnapshotProjectInsert(projectID);
/*     */   }
/*     */   
/*     */   public DcGDRSResults GDRSProductStatusGet(Integer releaseID, Integer releaseFamilyID, Integer environmentID) throws RemoteException {
/* 114 */     if (this.iArchimedesProject == null)
/* 115 */       _initIArchimedesProjectProxy(); 
/* 116 */     return this.iArchimedesProject.GDRSProductStatusGet(releaseID, releaseFamilyID, environmentID);
/*     */   } }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\IArchimedesProjectProxy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */