package WEB-INF.classes.com.universal.milestone;

import com.universal.milestone.Company;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Label;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.ProjectSearch;
import com.universal.milestone.ProjectSearchManager;
import com.universal.milestone.Selection;
import com.universal.milestone.projectSearchSvcClient;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import javax.xml.rpc.ServiceException;
import net.umusic.milestone.alps.DcDistributionCoData;
import net.umusic.milestone.alps.DcGDRSResults;
import net.umusic.milestone.alps.DcLabelData;
import net.umusic.milestone.alps.DcMilestoneProjectSearchFilter;
import net.umusic.milestone.alps.DcProjectSearchFilter;
import net.umusic.milestone.alps.DcProjectSearchResults;
import net.umusic.milestone.alps.IArchimedesProject;
import net.umusic.milestone.alps.ProjectNumberValidResults;
import org.tempuri.ArchimedesProjectLocator;

public class projectSearchSvcClient {
  public static Hashtable<Integer, DcLabelData> cachedLabelData = null;
  
  public static IArchimedesProject cachedBasicClient = null;
  
  public static Vector ProjectSearchGetDataContract(Vector archiIdVector, String firstName, String lastName, String projDesc, String title, String projectID) throws Exception {
    Vector searchResults = new Vector();
    System.out.println("ProjectSearchGetDataContract");
    IArchimedesProject client = ServiceBasicClientGet();
    DcMilestoneProjectSearchFilter filter = new DcMilestoneProjectSearchFilter();
    filter.setRowsToReturn(200);
    filter.setActive(true);
    int[] loa = ProjSearchPropertIntArrayGet("LOA");
    filter.setLevelOfActivityID(loa);
    int[] ids = new int[archiIdVector.size()];
    for (int k = 0; k < archiIdVector.size(); k++)
      ids[k] = Integer.parseInt((String)archiIdVector.elementAt(k)); 
    filter.setArtistFirstName(firstName);
    filter.setArtistLastNameGroupName(lastName);
    filter.setProjectDescription(projDesc);
    filter.setProjectTitle(title);
    filter.setProjectID(projectID);
    System.out.println("************ARCHI ID's PASSED TO ATLAS:\n" + archiIdVector.size() + "\n**************");
    System.out.println("------------- I am here in search function to Service and following are my parameters ------------- ");
    System.out.print(firstName);
    System.out.print("\t");
    System.out.print(lastName);
    System.out.print("\t");
    System.out.print(projDesc);
    System.out.print("\t");
    System.out.print(title);
    System.out.print("\t");
    System.out.print(projectID);
    System.out.println("------------- SAP number end ------------- ");
    DcProjectSearchResults[] aResults = client.projectSearchMilestoneGetDataContract(filter);
    if (aResults != null)
      for (int c = 0; c < aResults.length; c++) {
        ProjectSearch ps = new ProjectSearch();
        ps.setArchimedesID(String.valueOf(aResults[c].getArchimedes_ID()));
        ps.setArtistFirstName(aResults[c].getArtist_First_Name());
        ps.setArtistLastName(aResults[c].getArtist_Last_Name_Group_Name());
        ps.setProjectID(String.valueOf(aResults[c].getProject_ID()));
        ps.setTitle(aResults[c].getProject_Title());
        ps.setProjectDesc(aResults[c].getProject_Description());
        if (aResults[c].getCreate_Date() != null)
          ps.createDate = aResults[c].getCreate_Date(); 
        ps.setImprint(aResults[c].getImprint());
        if (aResults[c].getJDE_Project_() == null || aResults[c].getJDE_Project_() == "") {
          if (aResults[c].getRMS_Project_() != null || aResults[c].getRMS_Project_() != "") {
            System.out.println(" JDE Project is null - But RMS is not null " + aResults[c].getRMS_Project_().toString());
            ps.setJDEProjectNo(aResults[c].getRMS_Project_().replace("-", "").substring(1));
          } else {
            ps.setJDEProjectNo(aResults[c].getSAPProjectNumber());
          } 
        } else {
          ps.setJDEProjectNo(aResults[c].getJDE_Project_().trim());
        } 
        if (aResults[c].getJDE_Project_() != null)
          ps.setJDEProjectNo(aResults[c].getJDE_Project_().trim()); 
        if (aResults[c].getSAPProjectNumber() != null)
          ps.setSAPProjectNo(aResults[c].getSAPProjectNumber().trim()); 
        ps.setRMSProjectNo(aResults[c].getRMS_Project_());
        ps.setfinancialLabelDescription(aResults[c].getFinancialLabelDescription());
        System.out.println("------------- SAP number begin ------------- " + ps.getRMSProjectNo().toString());
        if (!aResults[c].getRMS_Project_().isEmpty())
          System.out.println("RMS#:" + aResults[c].getRMS_Project_() + "--value"); 
        System.out.println("------------- SAP number end ------------- ");
        searchResults.add(ps);
      }  
    return searchResults;
  }
  
  public static Vector ProjectSearchGetUMVDDataContract(Vector archiIdVector, String projectID) throws Exception {
    Vector searchResults = new Vector();
    System.out.println("ProjectSearchGetUMVDDataContract");
    try {
      IArchimedesProject client = ServiceBasicClientGet();
      DcProjectSearchFilter filter = new DcProjectSearchFilter();
      filter.setRowsToReturn(200);
      filter.setActive(true);
      int[] loa = ProjSearchPropertIntArrayGet("LOA");
      filter.setLevelOfActivityID(loa);
      int[] ids = new int[archiIdVector.size()];
      for (int k = 0; k < archiIdVector.size(); k++)
        ids[k] = Integer.parseInt((String)archiIdVector.elementAt(k)); 
      filter.setArchimedesIDs(ids);
      System.out.println("************UMVD ARCHI ID's PASSED TO ATLAS:\n" + archiIdVector.size() + "\n**************");
      filter.setProjectID(projectID);
      System.out.println("------------- I am here in UMVD search function to Service and following are my parameters ------------- ");
      for (int l = 0; l < ids.length; l++) {
        System.out.print(ids[l]);
        System.out.print(",");
      } 
      System.out.println("");
      System.out.print(projectID);
      System.out.println("------------- SAP number end ------------- ");
      DcProjectSearchResults[] aResults = client.projectSearchUMVDGetDataContract(filter);
      if (aResults != null)
        for (int c = 0; c < aResults.length; c++) {
          ProjectSearch ps = new ProjectSearch();
          ps.setArchimedesID(String.valueOf(aResults[c].getArchimedes_ID()));
          if (aResults[c].getJDE_Project_() != null)
            ps.setJDEProjectNo(aResults[c].getJDE_Project_().trim()); 
          if (aResults[c].getRMS_Project_() != null) {
            ps.setRMSProjectNo(aResults[c].getRMS_Project_().trim());
          } else if (aResults[c].getJDE_Project_() != null) {
            ps.setRMSProjectNo(ProjectSearchManager.formatJDEProjectNumber(aResults[c].getJDE_Project_()));
          } 
          if (aResults[c].getSAPProjectNumber() != null)
            ps.setSAPProjectNo(aResults[c].getSAPProjectNumber().trim()); 
          if (aResults[c].getRMS_Project_() == null && aResults[c].getSAPProjectNumber() != null)
            ps.setRMSProjectNo(ProjectSearchManager.formatJDEProjectNumber(aResults[c].getSAPProjectNumber().trim())); 
          ps.setfinancialLabelDescription(aResults[c].getFinancialLabelDescription());
          System.out.println("------------- SAP number begin ------------- ");
          if (aResults[c].getSAPProjectNumber() != null)
            System.out.println("SAP#:" + aResults[c].getSAPProjectNumber() + "--umvd function value"); 
          if (aResults[c].getRMS_Project_() != null)
            System.out.println("RMS#:" + aResults[c].getRMS_Project_() + "--umvd function value"); 
          if (aResults[c].getJDE_Project_() != null)
            System.out.println("JDE#:" + aResults[c].getJDE_Project_() + "--umvd function value"); 
          System.out.println("------------- SAP number end ------------- ");
          searchResults.add(ps);
        }  
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return searchResults;
  }
  
  public static Boolean ProjectNumberValidationGet(String projectNumber) throws ServiceException, RemoteException {
    Boolean results = Boolean.valueOf(false);
    try {
      IArchimedesProject client = ServiceBasicClientGet();
      results = client.projectNumberValidationGet(projectNumber);
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return results;
  }
  
  public static DcLabelData LabelDataGetById(int labelID) throws ServiceException, RemoteException {
    DcLabelData dataContract = new DcLabelData();
    try {
      IArchimedesProject client = ServiceBasicClientGet();
      dataContract = client.labelDataGetDataContractById(Integer.valueOf(labelID));
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return dataContract;
  }
  
  public static Hashtable<Integer, DcLabelData> LabelDataGet() throws ServiceException, RemoteException {
    if (cachedLabelData == null)
      try {
        client = ServiceBasicClientGet();
        DcLabelData[] aResults = client.labelDataGetDataContract();
        if (aResults != null) {
          cachedLabelData = new Hashtable();
          for (int c = 0; c < aResults.length; c++)
            cachedLabelData.put(aResults[c].getArchimedesID(), aResults[c]); 
        } 
      } catch (RemoteException re) {
        System.out.println(e.getMessage());
      } catch (Exception re) {
        System.out.println(re.getMessage());
      }  
    return cachedLabelData;
  }
  
  public static IArchimedesProject ServiceBasicClientGet() throws Exception {
    if (cachedBasicClient == null) {
      System.out.println("Getting Basic Service Client");
      url = ProjSearchPropertStrGet("ALPS_ProjectSearchSvc_URL");
      ArchimedesProjectLocator locator = new ArchimedesProjectLocator();
      IArchimedesProject service = locator.getbasicAP(new URL(url));
      synchronized (service) {
        service.wait(50000L);
      } 
      cachedBasicClient = locator.getbasicAP();
      System.out.println("Basic Service Client from cache URL " + url);
    } else {
      System.out.println("Basic Service Client from cache");
    } 
    return cachedBasicClient;
  }
  
  public static Selection ProjectSearchValidation(String projectNumber, Boolean IsUMVD) throws ServiceException, RemoteException {
    Selection updatedSel = null;
    try {
      IArchimedesProject client = ServiceBasicClientGet();
      ProjectNumberValidResults sResults = client.projectSearchValidation(projectNumber, IsUMVD);
      if (sResults != null && sResults.getIsValid().booleanValue()) {
        updatedSel = new Selection();
        updatedSel.setArtistFirstName(sResults.getFirstName());
        updatedSel.setArtistLastName(sResults.getLastName());
        updatedSel.setLabel((Label)MilestoneHelper.getStructureObject(sResults.getLabelID().intValue()));
        updatedSel.setDivision((Division)MilestoneHelper.getStructureObject(sResults.getDivisionID().intValue()));
        updatedSel.setCompany((Company)MilestoneHelper.getStructureObject(sResults.getCompanyID().intValue()));
        updatedSel.setEnvironment((Environment)MilestoneHelper.getStructureObject(sResults.getEnvironmentID().intValue()));
        updatedSel.setFamily((Family)MilestoneHelper.getStructureObject(sResults.getFamilyID().intValue()));
        updatedSel.setOperCompany(sResults.getOperatingCo());
        updatedSel.setSuperLabel(sResults.getSuperLabel());
        updatedSel.setSubLabel(sResults.getSubLabel());
        updatedSel.setImprint(sResults.getImprint());
      } 
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return updatedSel;
  }
  
  public static Hashtable<Integer, String> DistributionCodGet() throws ServiceException, RemoteException {
    results = new Hashtable();
    try {
      IArchimedesProject client = ServiceBasicClientGet();
      DcDistributionCoData[] aResults = client.distributionCoGetDataContract();
      if (aResults != null)
        for (int c = 0; c < aResults.length; c++)
          results.put(aResults[c].getID(), aResults[c].getName());  
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return results;
  }
  
  public static void MilestoneSnapshotProjectInsert(String projectNo) throws ServiceException, RemoteException {
    try {
      IArchimedesProject client = ServiceBasicClientGet();
      boolean result = client.milestoneSnapshotProjectInsert(projectNo).booleanValue();
      if (result) {
        System.out.println("Project No (" + projectNo + ") added to Snapshot");
      } else {
        System.out.println("Project No (" + projectNo + ") not added to Snapshot");
      } 
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
  }
  
  public static DcGDRSResults GDRSProductStatusGet(int releaseID, int releaseFamilyID, int environmentID) throws ServiceException, RemoteException {
    DcGDRSResults results = new DcGDRSResults();
    try {
      IArchimedesProject client = ServiceBasicClientGet();
      results = client.GDRSProductStatusGet(Integer.valueOf(releaseID), Integer.valueOf(releaseFamilyID), Integer.valueOf(environmentID));
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return results;
  }
  
  public static int[] ProjSearchPropertIntArrayGet(String prop) {
    int[] result = { -99 };
    try {
      String configFile = "milestone.conf";
      InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
      String loaStr = "";
      if (in == null)
        in = new FileInputStream(configFile); 
      Properties defaultProps = new Properties();
      defaultProps.load(in);
      if (defaultProps.getProperty(prop) != null)
        loaStr = defaultProps.getProperty(prop); 
      in.close();
      if (loaStr != null && !loaStr.equals("")) {
        String[] loaA = loaStr.split(",");
        result = new int[loaA.length];
        for (int x = 0; x < loaA.length; x++)
          result[x] = Integer.parseInt(loaA[x]); 
      } 
      System.out.println("Project Search property " + prop + " retrieved - " + result.toString());
    } catch (Exception e) {
      System.out.println("Error loading " + prop + " as an array from milestone.conf");
    } 
    return result;
  }
  
  public static String ProjSearchPropertStrGet(String prop) {
    String result = "";
    try {
      String configFile = "milestone.conf";
      InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
      if (in == null)
        in = new FileInputStream(configFile); 
      Properties defaultProps = new Properties();
      defaultProps.load(in);
      if (defaultProps.getProperty(prop) != null)
        result = defaultProps.getProperty(prop); 
      in.close();
      System.out.println("Project Search property " + prop + " retrieved - " + result);
    } catch (Exception e) {
      System.out.println("Error loading " + prop + " as a string from milestone.conf");
    } 
    return result;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\projectSearchSvcClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */