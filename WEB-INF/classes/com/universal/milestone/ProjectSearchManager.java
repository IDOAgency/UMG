package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Company;
import com.universal.milestone.CompanyAcl;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.ProjectSearch;
import com.universal.milestone.ProjectSearchComparator;
import com.universal.milestone.ProjectSearchManager;
import com.universal.milestone.SessionUserEmail;
import com.universal.milestone.SessionUserEmailObj;
import com.universal.milestone.User;
import com.universal.milestone.projectSearchSvcClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class ProjectSearchManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mSel";
  
  public static final String DEFAULT_ORDER = " ORDER BY artist, title, selection_no, street_date ";
  
  protected static ProjectSearchManager projectSearchManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mSel"); }
  
  public static ProjectSearchManager getInstance() {
    if (projectSearchManager == null)
      projectSearchManager = new ProjectSearchManager(); 
    return projectSearchManager;
  }
  
  public static Vector accessArchieView(Context context, User user) throws Exception {
    Vector userCompanies = MilestoneHelper.getUserCompanies(context);
    Vector editableUserCompanies = filterSelectionCompaniesWithEditRights(userCompanies, user);
    Vector jdeExceptionFamilies = getProjectSearchJDEFamilies();
    boolean isUmvdUser = isUmvdProjectSearchUser(user, context, jdeExceptionFamilies);
    boolean umvdLabelSelected = false;
    Vector archimedesProjects = new Vector();
    String structureIdQuery = " SELECT s4.Archimedes_ID, MS_FamilyId, MS_EnvironmentId,  MS_CompanyId, MS_DivisionId, MS_LabelId, EntityType, LegacyOperatingCo,  LegacySuperLabel, LegacySubLabel FROM structure s4  INNER JOIN structure s3 ON s3.structure_id = s4.parent_id  INNER JOIN structure s2 ON s2.structure_id = s3.parent_id  INNER JOIN ArchimedesLabels al ON al.ArchimedesID = s4.Archimedes_Id  WHERE ";
    if (!context.getParameter("labels").equals("") && !context.getParameter("labels").equals("-1") && !context.getParameter("labels").equals("0")) {
      structureIdQuery = String.valueOf(structureIdQuery) + " (s4.structure_id IN (" + context.getParameter("labels") + ") )";
    } else {
      String companyAccessQuery = "";
      for (int j = 0; j < editableUserCompanies.size(); j++) {
        if (j == 0) {
          companyAccessQuery = String.valueOf(companyAccessQuery) + " s2.structure_id IN ( " + ((Company)editableUserCompanies.elementAt(j)).getIdentity();
        } else {
          companyAccessQuery = String.valueOf(companyAccessQuery) + " , " + ((Company)editableUserCompanies.elementAt(j)).getIdentity();
        } 
      } 
      companyAccessQuery = String.valueOf(companyAccessQuery) + " ) ";
      structureIdQuery = String.valueOf(structureIdQuery) + companyAccessQuery;
    } 
    structureIdQuery = String.valueOf(structureIdQuery) + " AND ( al.LevelOfActivity = 'Active' OR (NOT al.LevelOfActivity = 'Active' AND s4.sort=0 )) ";
    System.out.println("***Structure Query:[" + structureIdQuery + "]");
    Hashtable labelHashtable = new Hashtable();
    JdbcConnector connector = MilestoneHelper.getConnector(structureIdQuery);
    connector.runQuery();
    while (connector.more()) {
      ProjectSearch projectSearchRow = new ProjectSearch();
      projectSearchRow.setMSFamilyId(connector.getIntegerField("MS_FamilyId"));
      projectSearchRow.setMSEnvironmentId(connector.getIntegerField("MS_EnvironmentId"));
      projectSearchRow.setMSCompanyId(connector.getIntegerField("MS_CompanyId"));
      projectSearchRow.setMSDivisionId(connector.getIntegerField("MS_DivisionId"));
      projectSearchRow.setMSLabelId(connector.getIntegerField("MS_LabelId"));
      projectSearchRow.setOperCompany(connector.getField("LegacyOperatingCo", ""));
      projectSearchRow.setSuperLabel(connector.getField("LegacySuperLabel", ""));
      projectSearchRow.setSubLabel(connector.getField("LegacySubLabel", ""));
      String entityType = connector.getField("EntityType", "");
      int pd_indicator = 0;
      if (entityType.startsWith("P&D") || entityType.equals("Distribution Deal"))
        pd_indicator = 1; 
      projectSearchRow.setPD_Indicator(pd_indicator);
      int archimedesID = connector.getIntegerField("Archimedes_ID");
      String archiID = String.valueOf(archimedesID);
      labelHashtable.put(archiID, projectSearchRow);
      if (!context.getParameter("labels").equals("") && !context.getParameter("labels").equals("-1") && !context.getParameter("labels").equals("0"))
        if (jdeExceptionFamilies.contains(new Integer(connector.getIntegerField("MS_FamilyId"))))
          umvdLabelSelected = true;  
      connector.next();
    } 
    connector.close();
    String projectsQuery = "";
    Enumeration archiIdsEnum = labelHashtable.keys();
    Vector archiIdVector = new Vector();
    while (archiIdsEnum.hasMoreElements())
      archiIdVector.addElement(archiIdsEnum.nextElement()); 
    String archiIdQuery = " WHERE ";
    for (int k = 0; k < archiIdVector.size(); k++) {
      if (k == 0) {
        archiIdQuery = String.valueOf(archiIdQuery) + " [labelId] IN ( " + archiIdVector.elementAt(k);
      } else {
        archiIdQuery = String.valueOf(archiIdQuery) + " , " + archiIdVector.elementAt(k);
      } 
    } 
    archiIdQuery = String.valueOf(archiIdQuery) + " ) ";
    projectsQuery = String.valueOf(projectsQuery) + archiIdQuery;
    projectsQuery = String.valueOf(projectsQuery) + " AND ( tblARProject.Active = 1) ";
    if (!context.getParameter("artistFirstName2").equals(""))
      projectsQuery = String.valueOf(projectsQuery) + " AND [FirstName] LIKE '%" + context.getParameter("artistFirstName2") + "%' "; 
    if (!context.getParameter("artistLastName").equals("")) {
      String artistLastName = context.getParameter("artistLastName").trim();
      if (artistLastName.startsWith("the ") && !artistLastName.equalsIgnoreCase("the the"))
        artistLastName = artistLastName.substring(4, artistLastName.length()); 
      projectsQuery = String.valueOf(projectsQuery) + " AND [LastName/GroupName] LIKE '%" + artistLastName + "%' ";
    } 
    if (!context.getParameter("projectDesc").equals(""))
      projectsQuery = String.valueOf(projectsQuery) + " AND [Name] LIKE '%" + context.getParameter("projectDesc") + "%' "; 
    if (!context.getParameter("title").equals(""))
      projectsQuery = String.valueOf(projectsQuery) + " AND [Title] LIKE '%" + context.getParameter("title") + "%' "; 
    if (!context.getParameter("projectID").equals(""))
      projectsQuery = String.valueOf(projectsQuery) + " AND [RMSProjectNumber] LIKE '%" + context.getParameter("projectID") + "%' "; 
    projectsQuery = String.valueOf(projectsQuery) + " AND NOT  [LastName/GroupName] = 'Miscellaneous' ";
    String loaStr = projectSearchSvcClient.ProjSearchPropertStrGet("LOA");
    projectsQuery = String.valueOf(projectsQuery) + " AND tblArtistRoster.ARLevelOfActivityID IN (" + loaStr + ") ";
    if (isUmvdUser || umvdLabelSelected) {
      Vector umvdProjectSearchResults = new Vector();
      Vector umvdLabel = new Vector();
      String umvdQuery = " WHERE ";
      for (int k = 0; k < archiIdVector.size(); k++) {
        if (k == 0) {
          umvdQuery = String.valueOf(umvdQuery) + " [ID] IN ( " + archiIdVector.elementAt(k);
        } else {
          umvdQuery = String.valueOf(umvdQuery) + " , " + archiIdVector.elementAt(k);
        } 
      } 
      umvdQuery = String.valueOf(umvdQuery) + " ) ";
      if (!context.getParameter("projectID").equals(""))
        umvdQuery = String.valueOf(umvdQuery) + " AND [ProjectNo] LIKE '%" + context.getParameter("projectID") + "%' "; 
      umvdProjectSearchResults = new Vector();
      umvdProjectSearchResults = projectSearchSvcClient.ProjectSearchGetUMVDDataContract(archiIdVector, context.getParameter("projectID"));
      System.out.println("Only Project ID Passed : Projects from WCF: " + umvdProjectSearchResults.size());
      return buildUMVDProjectSearchVector(umvdProjectSearchResults, labelHashtable);
    } 
    Vector archiProjectsVector = new Vector();
    System.out.println("All Parameters Passed : Projects from WCF: ");
    archiProjectsVector = new Vector();
    archiProjectsVector = projectSearchSvcClient.ProjectSearchGetDataContract(archiIdVector, 
        context.getParameter("artistFirstName2"), 
        context.getParameter("artistLastName"), 
        context.getParameter("projectDesc"), 
        context.getParameter("title"), 
        context.getParameter("projectID"));
    System.out.println("All Parameters Passed : Projects from WCF: " + archiProjectsVector.size());
    if (archiProjectsVector.size() == 0) {
      archiProjectsVector = projectSearchSvcClient.ProjectSearchGetUMVDDataContract(archiIdVector, 
          unformatForJDEProjectNumber(context.getParameter("projectID")));
      System.out.println(" In case if there are no result from above search - look in UMVD Labels : Projects from WCF: " + archiProjectsVector.size());
    } 
    return (combineProjectLabelDetails(archiProjectsVector, labelHashtable) != null) ? combineProjectLabelDetails(archiProjectsVector, labelHashtable) : archiProjectsVector;
  }
  
  public static Vector buildUMVDProjectSearchVector(Vector projectSearchFromLabelsView, Hashtable labHash) {
    Vector projectSearchVector = new Vector();
    if (projectSearchFromLabelsView.size() == 0)
      return projectSearchVector; 
    for (int counter = 0; counter < projectSearchFromLabelsView.size(); counter++) {
      ProjectSearch projectSearchObject = (ProjectSearch)projectSearchFromLabelsView.elementAt(counter);
      ProjectSearch labelDetail = (ProjectSearch)labHash.get(projectSearchObject.getArchimedesID());
      labelDetail.setRMSProjectNo(projectSearchObject.getRMSProjectNo());
      if (projectSearchObject.getJDEProjectNo() != null) {
        labelDetail.setProjectID(projectSearchObject.getJDEProjectNo());
      } else {
        labelDetail.setProjectID(projectSearchObject.getSAPProjectNo());
      } 
      labelDetail.setJDEProjectNo(projectSearchObject.getJDEProjectNo());
      labelDetail.setSAPProjectNo(projectSearchObject.getSAPProjectNo());
      labelDetail.setfinancialLabelDescription(projectSearchObject.getfinancialLabelDescription());
      labelDetail.setArtistFirstName("");
      labelDetail.setArtistLastName("");
      labelDetail.setCreateDate(MilestoneHelper.getDate("9/9/99"));
      labelDetail.setProjectDesc("");
      projectSearchVector.add(labelDetail);
    } 
    return projectSearchVector;
  }
  
  public static Vector combineProjectLabelDetails(Vector projectsVector, Hashtable labHash) {
    Vector completeArchiVector = new Vector();
    for (int i = 0; i < projectsVector.size(); i++) {
      ProjectSearch currentProj = (ProjectSearch)projectsVector.elementAt(i);
      String archiStr = currentProj.getArchimedesID();
      ProjectSearch labelDetail = (ProjectSearch)labHash.get(archiStr);
      if (labelDetail != null) {
        currentProj.setMSFamilyId(labelDetail.getMSFamilyId());
        currentProj.setMSEnvironmentId(labelDetail.getMSEnvironmentId());
        currentProj.setMSCompanyId(labelDetail.getMSCompanyId());
        currentProj.setMSDivisionId(labelDetail.getMSDivisionId());
        currentProj.setMSLabelId(labelDetail.getMSLabelId());
        currentProj.setOperCompany(labelDetail.getOperCompany());
        currentProj.setSuperLabel(labelDetail.getSuperLabel());
        currentProj.setSubLabel(labelDetail.getSubLabel());
        currentProj.setPD_Indicator(labelDetail.getPD_Indicator());
        completeArchiVector.addElement(currentProj);
      } 
    } 
    return completeArchiVector;
  }
  
  public static Vector getUmvdArchieResultsFromMilestoneSnapshot(Context context, String archiClause, Hashtable labelHashtable) {
    String archiUmvdQuery = "SELECT * FROM dbo.[ArchimedesLabels] ";
    archiUmvdQuery = String.valueOf(archiUmvdQuery) + archiClause;
    archiUmvdQuery = replaceStringInString(archiUmvdQuery, "[ID]", "[ArchimedesID]");
    JdbcConnector connector = MilestoneHelper.getConnector(archiUmvdQuery);
    try {
      connector.runQuery();
    } catch (Exception e) {
      System.out.println("****exception raised in getAllArchieResultsFromMilestoneSnapshot****");
    } 
    Vector umvdProjectSearchResults = new Vector();
    while (connector.more()) {
      ProjectSearch newProjectSearch = new ProjectSearch();
      newProjectSearch.setJDEProjectNo(connector.getField("ProjectNo", ""));
      newProjectSearch.setArchimedesID(connector.getField("ArchimedesID", ""));
      umvdProjectSearchResults.add(newProjectSearch);
      connector.next();
    } 
    connector.close();
    return buildUMVDProjectSearchVector(umvdProjectSearchResults, labelHashtable);
  }
  
  public static Vector getAllArchieResultsFromMilestoneSnapshot(Context context, String query, Hashtable labHash) {
    String projectsQuery = " SELECT TOP 200 * FROM dbo.ArchimedesProjects " + query;
    String newQuery = "";
    newQuery = replaceStringInString(projectsQuery, "[labelId]", "[Archimedes ID]");
    newQuery = replaceStringInString(newQuery, "[FirstName]", "[Artist First Name]");
    newQuery = replaceStringInString(newQuery, "[LastName/GroupName]", "[Artist Last Name/Group Name]");
    newQuery = replaceStringInString(newQuery, "[Name]", "[Project Description]");
    newQuery = replaceStringInString(newQuery, "[Title]", "[Project Title]");
    newQuery = replaceStringInString(newQuery, "[RMSProjectNumber]", "[RMS Project#]");
    newQuery = replaceStringInString(newQuery, "[LastName/GroupName]", "[Artist Last Name/Group Name]");
    newQuery = replaceStringInString(newQuery, "tblARProject.Active", "Active");
    newQuery = replaceStringInString(newQuery, "tblArtistRoster.ARLevelOfActivityID", "ArtistRosterActivity");
    JdbcConnector connector = MilestoneHelper.getConnector(newQuery);
    try {
      connector.runQuery();
    } catch (Exception e) {
      System.out.println("****exception raised in getAllArchieResultsFromMilestoneSnapshot****");
    } 
    Vector archiProjectsVector = new Vector();
    while (connector.more()) {
      ProjectSearch newProjectSearch = new ProjectSearch();
      newProjectSearch.setArtistFirstName(connector.getField("Artist First Name", ""));
      newProjectSearch.setArtistLastName(connector.getField("Artist Last Name/Group Name", ""));
      newProjectSearch.setProjectID(connector.getField("Project ID"));
      newProjectSearch.setTitle(connector.getField("Project Title", ""));
      newProjectSearch.setProjectDesc(connector.getField("Project Description", ""));
      String createDateString = connector.getFieldByName("CompletedDate");
      if (createDateString != null)
        newProjectSearch.setCreateDate(MilestoneHelper.getDatabaseDate(createDateString)); 
      newProjectSearch.setArchimedesID(String.valueOf(connector.getIntegerField("Archimedes ID")));
      newProjectSearch.setJDEProjectNo(connector.getField("JDE Project#", ""));
      newProjectSearch.setRMSProjectNo(connector.getField("RMS Project#", ""));
      newProjectSearch.setImprint(connector.getField("Imprint", ""));
      archiProjectsVector.addElement(newProjectSearch);
      connector.next();
    } 
    connector.close();
    return combineProjectLabelDetails(archiProjectsVector, labHash);
  }
  
  public static Vector getArchieProjects(Context context, User user) throws Exception {
    String query = "";
    Vector precache = new Vector();
    Company company = null;
    Vector archieVector = new Vector();
    archieVector = accessArchieView(context, user);
    Vector sortedVector = new Vector();
    sortedVector = sortResults(context, user, archieVector, true);
    return addUnknowns(context, user, sortedVector);
  }
  
  public static Vector addUnknowns(Context context, User user, Vector sortedVector) {
    Vector userCompanies = MilestoneHelper.getUserCompanies(context);
    Vector editableUserCompanies = filterSelectionCompaniesWithEditRights(userCompanies, user);
    Vector editableUserFamilies = new Vector();
    for (int i = 0; i < editableUserCompanies.size(); i++) {
      Company editCompany = (Company)editableUserCompanies.elementAt(i);
      Environment editEnvironment = editCompany.getParentEnvironment();
      Family editFamily = editEnvironment.getParentFamily();
      editableUserFamilies.add(editFamily);
    } 
    String unknownquery = " SELECT a.MS_LabelName, a.MS_FamilyId, a.MS_EnvironmentId, a.MS_CompanyId,  a.MS_DivisionId, a.MS_LabelId  FROM ArchimedesLabels a  JOIN Structure b on a.MS_LabelId = b.structure_id  WHERE a.unknownLabel = 1  AND b.sort = 0 ";
    if (context.getParameter("isPhysical").equals("Digital"))
      unknownquery = String.valueOf(unknownquery) + " AND NOT b.name LIKE '%fontana%' "; 
    for (int j = 0; j < editableUserFamilies.size(); j++) {
      if (j == 0) {
        unknownquery = String.valueOf(unknownquery) + " AND a.MS_FamilyId IN ( " + ((Family)editableUserFamilies.elementAt(j)).getIdentity();
      } else {
        unknownquery = String.valueOf(unknownquery) + " , " + ((Family)editableUserFamilies.elementAt(j)).getIdentity();
      } 
    } 
    unknownquery = String.valueOf(unknownquery) + " ) ";
    unknownquery = String.valueOf(unknownquery) + " ORDER BY a.MS_LabelName ";
    Vector unknownProjectsVector = new Vector();
    JdbcConnector connector = MilestoneHelper.getConnector(unknownquery);
    connector.setForwardOnly(false);
    connector.runQuery();
    while (connector.more()) {
      ProjectSearch unknownProjectObject = new ProjectSearch();
      unknownProjectObject.setProjectDesc(connector.getField("MS_LabelName", ""));
      unknownProjectObject.setSubLabel("***");
      unknownProjectObject.setSuperLabel("***");
      unknownProjectObject.setOperCompany("***");
      unknownProjectObject.setProjectID("0000-00000");
      unknownProjectObject.setRMSProjectNo("0000-00000");
      unknownProjectObject.setJDEProjectNo("0000-00000");
      unknownProjectObject.setMSFamilyId(connector.getIntegerField("MS_FamilyId"));
      unknownProjectObject.setMSEnvironmentId(connector.getIntegerField("MS_EnvironmentId"));
      unknownProjectObject.setMSCompanyId(connector.getIntegerField("MS_CompanyId"));
      unknownProjectObject.setMSDivisionId(connector.getIntegerField("MS_DivisionId"));
      unknownProjectObject.setMSLabelId(connector.getIntegerField("MS_LabelId"));
      unknownProjectObject.setArtistFirstName("");
      unknownProjectObject.setArtistLastName("Unknown Project");
      unknownProjectObject.setCreateDate(MilestoneHelper.getDate("9/9/99"));
      unknownProjectObject.setPD_Indicator(0);
      unknownProjectObject.setTitle("Unknown Project");
      String imprintName = MilestoneHelper.getStructureName(connector.getIntegerField("MS_LabelId"));
      unknownProjectObject.setImprint(imprintName);
      unknownProjectsVector.addElement(unknownProjectObject);
      connector.next();
    } 
    String numberRows = Integer.toString(connector.getRowCount());
    context.putSessionValue("number_unknowns", numberRows);
    connector.close();
    sortedVector.addAll(unknownProjectsVector);
    return sortedVector;
  }
  
  public static Vector sortResults(Context context, User user, Vector searchResults, boolean newSort) {
    String sortColumn = "6";
    String sortDirection = "Descending";
    if (context.getParameter("sortColumn") != null)
      sortColumn = context.getParameter("sortColumn"); 
    if (context.getParameter("sortDirection") != null)
      sortDirection = context.getParameter("sortDirection"); 
    Vector unknownRows = new Vector();
    if (!newSort) {
      String numberRows = "0";
      if (context.getSessionValue("number_unknowns") != null)
        numberRows = (String)context.getSessionValue("number_unknowns"); 
      int numberRowsInt = Integer.parseInt(numberRows);
      int startingPosition = searchResults.size() - Integer.parseInt(numberRows);
      int endingPosition = searchResults.size();
      for (int j = startingPosition; j < endingPosition; j++) {
        unknownRows.add((ProjectSearch)searchResults.elementAt(startingPosition));
        searchResults.remove(startingPosition);
      } 
    } 
    int iSortColumn = Integer.parseInt(sortColumn);
    Object[] projectsArray = searchResults.toArray();
    Arrays.sort(projectsArray, new ProjectSearchComparator(iSortColumn, sortDirection));
    Vector sortedResults = new Vector();
    for (int i = 0; i < projectsArray.length; i++)
      sortedResults.addElement((ProjectSearch)projectsArray[i]); 
    if (!newSort)
      sortedResults.addAll(unknownRows); 
    return sortedResults;
  }
  
  public static Vector filterSelectionCompaniesWithEditRights(Vector companies, User user) {
    Vector result = new Vector();
    Vector cmpAclList = user.getAcl().getCompanyAcl();
    HashMap cmpEditRight = new HashMap();
    if (cmpAclList != null)
      for (int n = 0; n < cmpAclList.size(); n++) {
        CompanyAcl cmpAcl = (CompanyAcl)cmpAclList.get(n);
        if (cmpAcl.getAccessSelection() == 2)
          cmpEditRight.put(new Integer(cmpAcl.getCompanyId()), new Integer(n)); 
      }  
    if (companies != null && companies.size() > 0)
      for (int i = 0; i < companies.size(); i++) {
        Company company = (Company)companies.get(i);
        String name = company.getName();
        if (cmpAclList == null) {
          if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise"))
            result.add(company); 
        } else if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise") && 
          cmpEditRight.containsKey(new Integer(company.getStructureID()))) {
          result.add(company);
        } 
      }  
    return result;
  }
  
  public static boolean isUmvdProjectSearchUser(User user, Context context, Vector jdeExceptionFamilies) {
    Vector userCompanies = MilestoneHelper.getUserCompanies(context);
    Vector editableUserCompanies = filterSelectionCompaniesWithEditRights(userCompanies, user);
    boolean isUmvdUser = false;
    String employedByString = "";
    if (user.getEmployedBy() > 0)
      employedByString = String.valueOf(user.getEmployedBy()); 
    boolean umvdFamilyFlag = true;
    if (employedByString.equalsIgnoreCase("417")) {
      if (editableUserCompanies.size() == 0) {
        umvdFamilyFlag = false;
      } else {
        for (int t = 0; t < editableUserCompanies.size() && umvdFamilyFlag; t++) {
          Company eCompany = (Company)editableUserCompanies.elementAt(t);
          Environment eEnvironment = eCompany.getParentEnvironment();
          Family eFamily = eEnvironment.getParentFamily();
          int familyId = eFamily.getStructureID();
          if (!jdeExceptionFamilies.contains(new Integer(familyId)))
            umvdFamilyFlag = false; 
        } 
      } 
      isUmvdUser = umvdFamilyFlag;
    } 
    return isUmvdUser;
  }
  
  public static String formatJDEProjectNumber(String rawString) {
    if (!rawString.isEmpty()) {
      if (rawString.length() == 8)
        return "0" + rawString.substring(0, 3) + "-" + rawString.substring(3, 8); 
      return rawString;
    } 
    return "";
  }
  
  public static String unformatForJDEProjectNumber(String rawString) {
    if (!rawString.isEmpty()) {
      if (rawString.length() > 8) {
        String formattedString = "";
        if (rawString.contains("-")) {
          formattedString = rawString.replace("-", "");
          formattedString = formattedString.substring(1, formattedString.length());
        } else {
          formattedString = rawString;
        } 
        return formattedString;
      } 
      return rawString;
    } 
    return "";
  }
  
  public static boolean isProjectNumberValid(String projectNumber) {
    boolean isValid = false;
    try {
      isValid = projectSearchSvcClient.ProjectNumberValidationGet(projectNumber).booleanValue();
      System.out.println("Project Is Valid: " + isValid);
    } catch (Exception e) {
      System.out.println("****exception raised in accessArchieView2****");
    } 
    return isValid;
  }
  
  public static boolean isProjectNumberValidFromMilestoneSnapshot(String projectNumber) {
    String projectsQuery = "select projectNo from ArchimedesLabels where projectNo = " + projectNumber;
    JdbcConnector connector = MilestoneHelper.getConnector(projectsQuery);
    try {
      connector.runQuery();
    } catch (Exception e) {
      System.out.println("****exception raised in isProjectNumberValidFromMilestoneSnapshot****");
    } 
    boolean isValid = false;
    if (connector.more())
      isValid = true; 
    connector.close();
    return isValid;
  }
  
  public static String replaceStringInString(String fullStr, String searchForStr, String replaceWithStr) {
    int fullLength = fullStr.length();
    int searchForStrLength = searchForStr.length();
    int index = fullStr.indexOf(searchForStr);
    if (index != -1)
      fullStr = String.valueOf(fullStr.substring(0, index)) + replaceWithStr + fullStr.substring(index + searchForStrLength, fullLength); 
    return fullStr;
  }
  
  public static boolean isCanadaUser(User user, Context context) {
    boolean isCanadaUser = false;
    String employedByName = "";
    if (user.getEmployedBy() > 0) {
      employedByName = MilestoneHelper.getStructureName(user.getEmployedBy());
      if (employedByName.equals("Canada"))
        isCanadaUser = true; 
    } 
    return isCanadaUser;
  }
  
  public static boolean isMexicoUser(User user, Context context) {
    boolean isMexicoUser = false;
    String employedByName = "";
    if (user.getEmployedBy() > 0) {
      employedByName = MilestoneHelper.getStructureName(user.getEmployedBy());
      if (employedByName.equals("Mexico"))
        isMexicoUser = true; 
    } 
    return isMexicoUser;
  }
  
  public static boolean sendEmailDistribution(Context context) {
    String envName = "";
    if (context.getSessionValue("environment_name") != null)
      envName = (String)context.getSessionValue("environment_name"); 
    String subject = String.valueOf(envName.toUpperCase()) + " Project Search: Archimedes DB Connection Down ";
    String body = " The " + envName.toUpperCase() + " database connection from Milestone to Archimedes is down. " + 
      envName.toUpperCase() + " Milestone will be querying against the ArchimedesProjects snapshot table";
    String recipients = "cgareca@hpe.com";
    String copyRecipients = "cgareca@hpe.com;shanmuga.selvaraj@umusic.com";
    String attachment = "";
    boolean result = false;
    try {
      StringTokenizer valueTokenizer = new StringTokenizer(recipients, ";");
      ArrayList destList = new ArrayList();
      while (valueTokenizer.hasMoreTokens())
        destList.add(new SessionUserEmailObj(valueTokenizer.nextToken().trim(), true)); 
      StringTokenizer ccValueTokenizer = new StringTokenizer(copyRecipients, ";");
      ArrayList ccList = new ArrayList();
      while (ccValueTokenizer.hasMoreTokens())
        ccList.add(new SessionUserEmailObj(ccValueTokenizer.nextToken().trim(), true)); 
      SessionUserEmail sue = new SessionUserEmail();
      body = "<font face='arial'>" + body + "</font>";
      result = sue.sendEmail(destList, body, subject, attachment, ccList);
    } catch (Exception e) {
      System.out.println("send email exception...");
    } 
    return result;
  }
  
  public static Vector getProjectSearchJDEFamilies() {
    returnFamilies = new Vector();
    String jdeQuery = "select value from vi_lookup_detail where field_id = 65";
    JdbcConnector connector = MilestoneHelper.getConnector(jdeQuery);
    try {
      connector.runQuery();
    } catch (Exception e) {
      System.out.println("****exception raised in getProjectSearchJDEFamilies****");
    } 
    while (connector.more()) {
      returnFamilies.add(new Integer(connector.getField("value")));
      connector.next();
    } 
    connector.close();
    return returnFamilies;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ProjectSearchManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */