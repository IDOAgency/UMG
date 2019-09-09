/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ import com.universal.milestone.Label;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.ProjectSearch;
/*     */ import com.universal.milestone.ProjectSearchManager;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.projectSearchSvcClient;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.xml.rpc.ServiceException;
/*     */ import net.umusic.milestone.alps.DcDistributionCoData;
/*     */ import net.umusic.milestone.alps.DcGDRSResults;
/*     */ import net.umusic.milestone.alps.DcLabelData;
/*     */ import net.umusic.milestone.alps.DcMilestoneProjectSearchFilter;
/*     */ import net.umusic.milestone.alps.DcProjectSearchFilter;
/*     */ import net.umusic.milestone.alps.DcProjectSearchResults;
/*     */ import net.umusic.milestone.alps.IArchimedesProject;
/*     */ import net.umusic.milestone.alps.ProjectNumberValidResults;
/*     */ import org.tempuri.ArchimedesProjectLocator;
/*     */ 
/*     */ public class projectSearchSvcClient {
/*  27 */   public static Hashtable<Integer, DcLabelData> cachedLabelData = null;
/*  28 */   public static IArchimedesProject cachedBasicClient = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector ProjectSearchGetDataContract(Vector archiIdVector, String firstName, String lastName, String projDesc, String title, String projectID) throws Exception {
/*  46 */     Vector searchResults = new Vector();
/*     */     
/*  48 */     System.out.println("ProjectSearchGetDataContract");
/*     */ 
/*     */ 
/*     */     
/*  52 */     IArchimedesProject client = ServiceBasicClientGet();
/*     */ 
/*     */     
/*  55 */     DcMilestoneProjectSearchFilter filter = new DcMilestoneProjectSearchFilter();
/*     */ 
/*     */     
/*  58 */     filter.setRowsToReturn(200);
/*  59 */     filter.setActive(true);
/*     */ 
/*     */     
/*  62 */     int[] loa = ProjSearchPropertIntArrayGet("LOA");
/*  63 */     filter.setLevelOfActivityID(loa);
/*     */ 
/*     */     
/*  66 */     int[] ids = new int[archiIdVector.size()];
/*  67 */     for (int k = 0; k < archiIdVector.size(); k++) {
/*  68 */       ids[k] = Integer.parseInt((String)archiIdVector.elementAt(k));
/*     */     }
/*     */ 
/*     */     
/*  72 */     filter.setArtistFirstName(firstName);
/*  73 */     filter.setArtistLastNameGroupName(lastName);
/*  74 */     filter.setProjectDescription(projDesc);
/*  75 */     filter.setProjectTitle(title);
/*  76 */     filter.setProjectID(projectID);
/*     */     
/*  78 */     System.out.println("************ARCHI ID's PASSED TO ATLAS:\n" + archiIdVector.size() + "\n**************");
/*     */     
/*  80 */     System.out.println("------------- I am here in search function to Service and following are my parameters ------------- ");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     System.out.print(firstName); System.out.print("\t");
/*  86 */     System.out.print(lastName); System.out.print("\t");
/*  87 */     System.out.print(projDesc); System.out.print("\t");
/*  88 */     System.out.print(title); System.out.print("\t");
/*  89 */     System.out.print(projectID);
/*  90 */     System.out.println("------------- SAP number end ------------- ");
/*     */     
/*  92 */     DcProjectSearchResults[] aResults = client.projectSearchMilestoneGetDataContract(filter);
/*     */ 
/*     */     
/*  95 */     if (aResults != null)
/*     */     {
/*  97 */       for (int c = 0; c < aResults.length; c++) {
/*     */         
/*  99 */         ProjectSearch ps = new ProjectSearch();
/* 100 */         ps.setArchimedesID(String.valueOf(aResults[c].getArchimedes_ID()));
/* 101 */         ps.setArtistFirstName(aResults[c].getArtist_First_Name());
/* 102 */         ps.setArtistLastName(aResults[c].getArtist_Last_Name_Group_Name());
/* 103 */         ps.setProjectID(String.valueOf(aResults[c].getProject_ID()));
/* 104 */         ps.setTitle(aResults[c].getProject_Title());
/* 105 */         ps.setProjectDesc(aResults[c].getProject_Description());
/* 106 */         if (aResults[c].getCreate_Date() != null)
/* 107 */           ps.createDate = aResults[c].getCreate_Date(); 
/* 108 */         ps.setImprint(aResults[c].getImprint());
/*     */         
/* 110 */         if (aResults[c].getJDE_Project_() == null || aResults[c].getJDE_Project_() == "") {
/*     */           
/* 112 */           if (aResults[c].getRMS_Project_() != null || aResults[c].getRMS_Project_() != "") {
/*     */             
/* 114 */             System.out.println(" JDE Project is null - But RMS is not null " + aResults[c].getRMS_Project_().toString());
/* 115 */             ps.setJDEProjectNo(aResults[c].getRMS_Project_().replace("-", "").substring(1));
/*     */           } else {
/*     */             
/* 118 */             ps.setJDEProjectNo(aResults[c].getSAPProjectNumber());
/*     */           } 
/*     */         } else {
/* 121 */           ps.setJDEProjectNo(aResults[c].getJDE_Project_().trim());
/*     */         } 
/* 123 */         if (aResults[c].getJDE_Project_() != null)
/* 124 */           ps.setJDEProjectNo(aResults[c].getJDE_Project_().trim()); 
/* 125 */         if (aResults[c].getSAPProjectNumber() != null)
/* 126 */           ps.setSAPProjectNo(aResults[c].getSAPProjectNumber().trim()); 
/* 127 */         ps.setRMSProjectNo(aResults[c].getRMS_Project_());
/* 128 */         ps.setfinancialLabelDescription(aResults[c].getFinancialLabelDescription());
/*     */         
/* 130 */         System.out.println("------------- SAP number begin ------------- " + ps.getRMSProjectNo().toString());
/* 131 */         if (!aResults[c].getRMS_Project_().isEmpty()) {
/* 132 */           System.out.println("RMS#:" + aResults[c].getRMS_Project_() + "--value");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 137 */         System.out.println("------------- SAP number end ------------- ");
/*     */ 
/*     */ 
/*     */         
/* 141 */         searchResults.add(ps);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     return searchResults;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector ProjectSearchGetUMVDDataContract(Vector archiIdVector, String projectID) throws Exception {
/* 167 */     Vector searchResults = new Vector();
/* 168 */     System.out.println("ProjectSearchGetUMVDDataContract");
/*     */ 
/*     */     
/*     */     try {
/* 172 */       IArchimedesProject client = ServiceBasicClientGet();
/*     */ 
/*     */       
/* 175 */       DcProjectSearchFilter filter = new DcProjectSearchFilter();
/*     */ 
/*     */       
/* 178 */       filter.setRowsToReturn(200);
/* 179 */       filter.setActive(true);
/*     */ 
/*     */ 
/*     */       
/* 183 */       int[] loa = ProjSearchPropertIntArrayGet("LOA");
/* 184 */       filter.setLevelOfActivityID(loa);
/*     */ 
/*     */       
/* 187 */       int[] ids = new int[archiIdVector.size()];
/* 188 */       for (int k = 0; k < archiIdVector.size(); k++)
/* 189 */         ids[k] = Integer.parseInt((String)archiIdVector.elementAt(k)); 
/* 190 */       filter.setArchimedesIDs(ids);
/*     */       
/* 192 */       System.out.println("************UMVD ARCHI ID's PASSED TO ATLAS:\n" + archiIdVector.size() + "\n**************");
/*     */       
/* 194 */       filter.setProjectID(projectID);
/* 195 */       System.out.println("------------- I am here in UMVD search function to Service and following are my parameters ------------- ");
/* 196 */       for (int l = 0; l < ids.length; l++) {
/*     */         
/* 198 */         System.out.print(ids[l]); System.out.print(",");
/*     */       } 
/* 200 */       System.out.println("");
/* 201 */       System.out.print(projectID);
/* 202 */       System.out.println("------------- SAP number end ------------- ");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 207 */       DcProjectSearchResults[] aResults = client.projectSearchUMVDGetDataContract(filter);
/*     */       
/* 209 */       if (aResults != null)
/*     */       {
/*     */         
/* 212 */         for (int c = 0; c < aResults.length; c++)
/*     */         {
/* 214 */           ProjectSearch ps = new ProjectSearch();
/* 215 */           ps.setArchimedesID(String.valueOf(aResults[c].getArchimedes_ID()));
/*     */           
/* 217 */           if (aResults[c].getJDE_Project_() != null)
/* 218 */             ps.setJDEProjectNo(aResults[c].getJDE_Project_().trim()); 
/* 219 */           if (aResults[c].getRMS_Project_() != null) {
/* 220 */             ps.setRMSProjectNo(aResults[c].getRMS_Project_().trim());
/*     */           
/*     */           }
/* 223 */           else if (aResults[c].getJDE_Project_() != null) {
/* 224 */             ps.setRMSProjectNo(ProjectSearchManager.formatJDEProjectNumber(aResults[c].getJDE_Project_()));
/*     */           } 
/*     */           
/* 227 */           if (aResults[c].getSAPProjectNumber() != null)
/* 228 */             ps.setSAPProjectNo(aResults[c].getSAPProjectNumber().trim()); 
/* 229 */           if (aResults[c].getRMS_Project_() == null && aResults[c].getSAPProjectNumber() != null) {
/* 230 */             ps.setRMSProjectNo(ProjectSearchManager.formatJDEProjectNumber(aResults[c].getSAPProjectNumber().trim()));
/*     */           }
/* 232 */           ps.setfinancialLabelDescription(aResults[c].getFinancialLabelDescription());
/* 233 */           System.out.println("------------- SAP number begin ------------- ");
/* 234 */           if (aResults[c].getSAPProjectNumber() != null)
/* 235 */             System.out.println("SAP#:" + aResults[c].getSAPProjectNumber() + "--umvd function value"); 
/* 236 */           if (aResults[c].getRMS_Project_() != null)
/* 237 */             System.out.println("RMS#:" + aResults[c].getRMS_Project_() + "--umvd function value"); 
/* 238 */           if (aResults[c].getJDE_Project_() != null)
/* 239 */             System.out.println("JDE#:" + aResults[c].getJDE_Project_() + "--umvd function value"); 
/* 240 */           System.out.println("------------- SAP number end ------------- ");
/*     */           
/* 242 */           searchResults.add(ps);
/*     */         }
/*     */       
/*     */       }
/* 246 */     } catch (RemoteException re) {
/*     */       
/* 248 */       System.out.println(re.getMessage());
/* 249 */     } catch (Exception e) {
/* 250 */       System.out.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 254 */     return searchResults;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Boolean ProjectNumberValidationGet(String projectNumber) throws ServiceException, RemoteException {
/* 268 */     Boolean results = Boolean.valueOf(false);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 273 */       IArchimedesProject client = ServiceBasicClientGet();
/*     */ 
/*     */       
/* 276 */       results = client.projectNumberValidationGet(projectNumber);
/*     */     }
/* 278 */     catch (RemoteException re) {
/*     */       
/* 280 */       System.out.println(re.getMessage());
/* 281 */     } catch (Exception e) {
/* 282 */       System.out.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 286 */     return results;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DcLabelData LabelDataGetById(int labelID) throws ServiceException, RemoteException {
/* 299 */     DcLabelData dataContract = new DcLabelData();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 304 */       IArchimedesProject client = ServiceBasicClientGet();
/*     */ 
/*     */       
/* 307 */       dataContract = client.labelDataGetDataContractById(Integer.valueOf(labelID));
/* 308 */     } catch (RemoteException re) {
/*     */       
/* 310 */       System.out.println(re.getMessage());
/* 311 */     } catch (Exception e) {
/* 312 */       System.out.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 316 */     return dataContract;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hashtable<Integer, DcLabelData> LabelDataGet() throws ServiceException, RemoteException {
/* 328 */     if (cachedLabelData == null) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 333 */         client = ServiceBasicClientGet();
/*     */ 
/*     */         
/* 336 */         DcLabelData[] aResults = client.labelDataGetDataContract();
/*     */         
/* 338 */         if (aResults != null) {
/*     */           
/* 340 */           cachedLabelData = new Hashtable();
/*     */ 
/*     */           
/* 343 */           for (int c = 0; c < aResults.length; c++) {
/* 344 */             cachedLabelData.put(aResults[c].getArchimedesID(), aResults[c]);
/*     */           }
/*     */         } 
/* 347 */       } catch (RemoteException re) {
/*     */         
/* 349 */         System.out.println(e.getMessage());
/* 350 */       } catch (Exception re) {
/* 351 */         System.out.println(re.getMessage());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 356 */     return cachedLabelData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IArchimedesProject ServiceBasicClientGet() throws Exception {
/* 367 */     if (cachedBasicClient == null) {
/*     */       
/* 369 */       System.out.println("Getting Basic Service Client");
/*     */ 
/*     */       
/* 372 */       url = ProjSearchPropertStrGet("ALPS_ProjectSearchSvc_URL");
/*     */ 
/*     */       
/* 375 */       ArchimedesProjectLocator locator = new ArchimedesProjectLocator();
/* 376 */       IArchimedesProject service = locator.getbasicAP(new URL(url));
/* 377 */       synchronized (service) {
/* 378 */         service.wait(50000L);
/*     */       } 
/*     */       
/* 381 */       cachedBasicClient = locator.getbasicAP();
/*     */ 
/*     */       
/* 384 */       System.out.println("Basic Service Client from cache URL " + url);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 395 */       System.out.println("Basic Service Client from cache");
/*     */     } 
/*     */     
/* 398 */     return cachedBasicClient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Selection ProjectSearchValidation(String projectNumber, Boolean IsUMVD) throws ServiceException, RemoteException {
/* 413 */     Selection updatedSel = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 418 */       IArchimedesProject client = ServiceBasicClientGet();
/*     */ 
/*     */       
/* 421 */       ProjectNumberValidResults sResults = client.projectSearchValidation(projectNumber, IsUMVD);
/*     */ 
/*     */       
/* 424 */       if (sResults != null && sResults.getIsValid().booleanValue())
/*     */       {
/* 426 */         updatedSel = new Selection();
/*     */ 
/*     */         
/* 429 */         updatedSel.setArtistFirstName(sResults.getFirstName());
/*     */         
/* 431 */         updatedSel.setArtistLastName(sResults.getLastName());
/*     */         
/* 433 */         updatedSel.setLabel((Label)MilestoneHelper.getStructureObject(sResults.getLabelID().intValue()));
/* 434 */         updatedSel.setDivision((Division)MilestoneHelper.getStructureObject(sResults.getDivisionID().intValue()));
/* 435 */         updatedSel.setCompany((Company)MilestoneHelper.getStructureObject(sResults.getCompanyID().intValue()));
/* 436 */         updatedSel.setEnvironment((Environment)MilestoneHelper.getStructureObject(sResults.getEnvironmentID().intValue()));
/* 437 */         updatedSel.setFamily((Family)MilestoneHelper.getStructureObject(sResults.getFamilyID().intValue()));
/*     */ 
/*     */         
/* 440 */         updatedSel.setOperCompany(sResults.getOperatingCo());
/*     */ 
/*     */         
/* 443 */         updatedSel.setSuperLabel(sResults.getSuperLabel());
/*     */ 
/*     */         
/* 446 */         updatedSel.setSubLabel(sResults.getSubLabel());
/*     */ 
/*     */         
/* 449 */         updatedSel.setImprint(sResults.getImprint());
/*     */       }
/*     */     
/* 452 */     } catch (RemoteException re) {
/*     */       
/* 454 */       System.out.println(re.getMessage());
/* 455 */     } catch (Exception e) {
/* 456 */       System.out.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 460 */     return updatedSel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hashtable<Integer, String> DistributionCodGet() throws ServiceException, RemoteException {
/* 472 */     results = new Hashtable();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 477 */       IArchimedesProject client = ServiceBasicClientGet();
/*     */ 
/*     */       
/* 480 */       DcDistributionCoData[] aResults = client.distributionCoGetDataContract();
/*     */       
/* 482 */       if (aResults != null)
/*     */       {
/*     */         
/* 485 */         for (int c = 0; c < aResults.length; c++) {
/* 486 */           results.put(aResults[c].getID(), aResults[c].getName());
/*     */         }
/*     */       }
/*     */     }
/* 490 */     catch (RemoteException re) {
/*     */       
/* 492 */       System.out.println(re.getMessage());
/* 493 */     } catch (Exception e) {
/* 494 */       System.out.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 499 */     return results;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void MilestoneSnapshotProjectInsert(String projectNo) throws ServiceException, RemoteException {
/*     */     try {
/* 512 */       IArchimedesProject client = ServiceBasicClientGet();
/*     */ 
/*     */       
/* 515 */       boolean result = client.milestoneSnapshotProjectInsert(projectNo).booleanValue();
/*     */       
/* 517 */       if (result) {
/* 518 */         System.out.println("Project No (" + projectNo + ") added to Snapshot");
/*     */       } else {
/* 520 */         System.out.println("Project No (" + projectNo + ") not added to Snapshot");
/*     */       } 
/* 522 */     } catch (RemoteException re) {
/*     */       
/* 524 */       System.out.println(re.getMessage());
/* 525 */     } catch (Exception e) {
/* 526 */       System.out.println(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DcGDRSResults GDRSProductStatusGet(int releaseID, int releaseFamilyID, int environmentID) throws ServiceException, RemoteException {
/* 540 */     DcGDRSResults results = new DcGDRSResults();
/*     */ 
/*     */     
/*     */     try {
/* 544 */       IArchimedesProject client = ServiceBasicClientGet();
/*     */ 
/*     */       
/* 547 */       results = client.GDRSProductStatusGet(Integer.valueOf(releaseID), Integer.valueOf(releaseFamilyID), Integer.valueOf(environmentID));
/*     */     }
/* 549 */     catch (RemoteException re) {
/*     */       
/* 551 */       System.out.println(re.getMessage());
/* 552 */     } catch (Exception e) {
/* 553 */       System.out.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 557 */     return results;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] ProjSearchPropertIntArrayGet(String prop) {
/* 566 */     int[] result = { -99 };
/*     */ 
/*     */     
/*     */     try {
/* 570 */       String configFile = "milestone.conf";
/* 571 */       InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
/* 572 */       String loaStr = "";
/*     */ 
/*     */       
/* 575 */       if (in == null) in = new FileInputStream(configFile); 
/* 576 */       Properties defaultProps = new Properties();
/* 577 */       defaultProps.load(in);
/*     */ 
/*     */       
/* 580 */       if (defaultProps.getProperty(prop) != null) {
/* 581 */         loaStr = defaultProps.getProperty(prop);
/*     */       }
/* 583 */       in.close();
/*     */ 
/*     */       
/* 586 */       if (loaStr != null && !loaStr.equals("")) {
/*     */ 
/*     */         
/* 589 */         String[] loaA = loaStr.split(",");
/*     */ 
/*     */         
/* 592 */         result = new int[loaA.length];
/*     */ 
/*     */         
/* 595 */         for (int x = 0; x < loaA.length; x++) {
/* 596 */           result[x] = Integer.parseInt(loaA[x]);
/*     */         }
/*     */       } 
/* 599 */       System.out.println("Project Search property " + prop + " retrieved - " + result.toString());
/*     */     }
/* 601 */     catch (Exception e) {
/* 602 */       System.out.println("Error loading " + prop + " as an array from milestone.conf");
/*     */     } 
/*     */ 
/*     */     
/* 606 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ProjSearchPropertStrGet(String prop) {
/* 615 */     String result = "";
/*     */ 
/*     */     
/*     */     try {
/* 619 */       String configFile = "milestone.conf";
/* 620 */       InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
/*     */ 
/*     */       
/* 623 */       if (in == null) in = new FileInputStream(configFile); 
/* 624 */       Properties defaultProps = new Properties();
/* 625 */       defaultProps.load(in);
/*     */ 
/*     */       
/* 628 */       if (defaultProps.getProperty(prop) != null) {
/* 629 */         result = defaultProps.getProperty(prop);
/*     */       }
/* 631 */       in.close();
/*     */       
/* 633 */       System.out.println("Project Search property " + prop + " retrieved - " + result);
/*     */     }
/* 635 */     catch (Exception e) {
/* 636 */       System.out.println("Error loading " + prop + " as a string from milestone.conf");
/*     */     } 
/*     */     
/* 639 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\projectSearchSvcClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */