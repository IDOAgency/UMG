/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.CompanyAcl;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.ProjectSearch;
/*      */ import com.universal.milestone.ProjectSearchComparator;
/*      */ import com.universal.milestone.ProjectSearchManager;
/*      */ import com.universal.milestone.SessionUserEmail;
/*      */ import com.universal.milestone.SessionUserEmailObj;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.projectSearchSvcClient;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ProjectSearchManager
/*      */   implements MilestoneConstants
/*      */ {
/*      */   public static final String COMPONENT_CODE = "mSel";
/*      */   public static final String DEFAULT_ORDER = " ORDER BY artist, title, selection_no, street_date ";
/*   61 */   protected static ProjectSearchManager projectSearchManager = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static ComponentLog log;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mSel"); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ProjectSearchManager getInstance() {
/*   91 */     if (projectSearchManager == null)
/*      */     {
/*   93 */       projectSearchManager = new ProjectSearchManager();
/*      */     }
/*   95 */     return projectSearchManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector accessArchieView(Context context, User user) throws Exception {
/*  104 */     Vector userCompanies = MilestoneHelper.getUserCompanies(context);
/*  105 */     Vector editableUserCompanies = filterSelectionCompaniesWithEditRights(userCompanies, user);
/*      */ 
/*      */ 
/*      */     
/*  109 */     Vector jdeExceptionFamilies = getProjectSearchJDEFamilies();
/*      */ 
/*      */     
/*  112 */     boolean isUmvdUser = isUmvdProjectSearchUser(user, context, jdeExceptionFamilies);
/*  113 */     boolean umvdLabelSelected = false;
/*      */ 
/*      */     
/*  116 */     Vector archimedesProjects = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  125 */     String structureIdQuery = " SELECT s4.Archimedes_ID, MS_FamilyId, MS_EnvironmentId,  MS_CompanyId, MS_DivisionId, MS_LabelId, EntityType, LegacyOperatingCo,  LegacySuperLabel, LegacySubLabel FROM structure s4  INNER JOIN structure s3 ON s3.structure_id = s4.parent_id  INNER JOIN structure s2 ON s2.structure_id = s3.parent_id  INNER JOIN ArchimedesLabels al ON al.ArchimedesID = s4.Archimedes_Id  WHERE ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  137 */     if (!context.getParameter("labels").equals("") && !context.getParameter("labels").equals("-1") && !context.getParameter("labels").equals("0")) {
/*      */       
/*  139 */       structureIdQuery = String.valueOf(structureIdQuery) + " (s4.structure_id IN (" + context.getParameter("labels") + ") )";
/*      */     }
/*      */     else {
/*      */       
/*  143 */       String companyAccessQuery = "";
/*      */ 
/*      */       
/*  146 */       for (int j = 0; j < editableUserCompanies.size(); j++) {
/*  147 */         if (j == 0) {
/*  148 */           companyAccessQuery = String.valueOf(companyAccessQuery) + " s2.structure_id IN ( " + ((Company)editableUserCompanies.elementAt(j)).getIdentity();
/*      */         } else {
/*  150 */           companyAccessQuery = String.valueOf(companyAccessQuery) + " , " + ((Company)editableUserCompanies.elementAt(j)).getIdentity();
/*      */         } 
/*  152 */       }  companyAccessQuery = String.valueOf(companyAccessQuery) + " ) ";
/*      */       
/*  154 */       structureIdQuery = String.valueOf(structureIdQuery) + companyAccessQuery;
/*      */     } 
/*      */ 
/*      */     
/*  158 */     structureIdQuery = String.valueOf(structureIdQuery) + " AND ( al.LevelOfActivity = 'Active' OR (NOT al.LevelOfActivity = 'Active' AND s4.sort=0 )) ";
/*      */     
/*  160 */     System.out.println("***Structure Query:[" + structureIdQuery + "]");
/*      */ 
/*      */     
/*  163 */     Hashtable labelHashtable = new Hashtable();
/*      */ 
/*      */     
/*  166 */     JdbcConnector connector = MilestoneHelper.getConnector(structureIdQuery);
/*  167 */     connector.runQuery();
/*      */     
/*  169 */     while (connector.more()) {
/*      */       
/*  171 */       ProjectSearch projectSearchRow = new ProjectSearch();
/*      */ 
/*      */ 
/*      */       
/*  175 */       projectSearchRow.setMSFamilyId(connector.getIntegerField("MS_FamilyId"));
/*      */       
/*  177 */       projectSearchRow.setMSEnvironmentId(connector.getIntegerField("MS_EnvironmentId"));
/*      */       
/*  179 */       projectSearchRow.setMSCompanyId(connector.getIntegerField("MS_CompanyId"));
/*      */       
/*  181 */       projectSearchRow.setMSDivisionId(connector.getIntegerField("MS_DivisionId"));
/*      */       
/*  183 */       projectSearchRow.setMSLabelId(connector.getIntegerField("MS_LabelId"));
/*      */       
/*  185 */       projectSearchRow.setOperCompany(connector.getField("LegacyOperatingCo", ""));
/*      */       
/*  187 */       projectSearchRow.setSuperLabel(connector.getField("LegacySuperLabel", ""));
/*      */       
/*  189 */       projectSearchRow.setSubLabel(connector.getField("LegacySubLabel", ""));
/*      */ 
/*      */       
/*  192 */       String entityType = connector.getField("EntityType", "");
/*  193 */       int pd_indicator = 0;
/*  194 */       if (entityType.startsWith("P&D") || entityType.equals("Distribution Deal")) {
/*  195 */         pd_indicator = 1;
/*      */       }
/*  197 */       projectSearchRow.setPD_Indicator(pd_indicator);
/*      */       
/*  199 */       int archimedesID = connector.getIntegerField("Archimedes_ID");
/*  200 */       String archiID = String.valueOf(archimedesID);
/*      */ 
/*      */ 
/*      */       
/*  204 */       labelHashtable.put(archiID, projectSearchRow);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  209 */       if (!context.getParameter("labels").equals("") && !context.getParameter("labels").equals("-1") && !context.getParameter("labels").equals("0"))
/*      */       {
/*  211 */         if (jdeExceptionFamilies.contains(new Integer(connector.getIntegerField("MS_FamilyId")))) {
/*  212 */           umvdLabelSelected = true;
/*      */         }
/*      */       }
/*  215 */       connector.next();
/*      */     } 
/*  217 */     connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  222 */     String projectsQuery = "";
/*      */ 
/*      */     
/*  225 */     Enumeration archiIdsEnum = labelHashtable.keys();
/*  226 */     Vector archiIdVector = new Vector();
/*  227 */     while (archiIdsEnum.hasMoreElements()) {
/*  228 */       archiIdVector.addElement(archiIdsEnum.nextElement());
/*      */     }
/*  230 */     String archiIdQuery = " WHERE ";
/*  231 */     for (int k = 0; k < archiIdVector.size(); k++) {
/*  232 */       if (k == 0) {
/*  233 */         archiIdQuery = String.valueOf(archiIdQuery) + " [labelId] IN ( " + archiIdVector.elementAt(k);
/*      */       } else {
/*  235 */         archiIdQuery = String.valueOf(archiIdQuery) + " , " + archiIdVector.elementAt(k);
/*      */       } 
/*  237 */     }  archiIdQuery = String.valueOf(archiIdQuery) + " ) ";
/*      */     
/*  239 */     projectsQuery = String.valueOf(projectsQuery) + archiIdQuery;
/*      */ 
/*      */     
/*  242 */     projectsQuery = String.valueOf(projectsQuery) + " AND ( tblARProject.Active = 1) ";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  247 */     if (!context.getParameter("artistFirstName2").equals(""))
/*      */     {
/*  249 */       projectsQuery = String.valueOf(projectsQuery) + " AND [FirstName] LIKE '%" + context.getParameter("artistFirstName2") + "%' ";
/*      */     }
/*  251 */     if (!context.getParameter("artistLastName").equals("")) {
/*      */ 
/*      */ 
/*      */       
/*  255 */       String artistLastName = context.getParameter("artistLastName").trim();
/*  256 */       if (artistLastName.startsWith("the ") && !artistLastName.equalsIgnoreCase("the the")) {
/*  257 */         artistLastName = artistLastName.substring(4, artistLastName.length());
/*      */       }
/*      */       
/*  260 */       projectsQuery = String.valueOf(projectsQuery) + " AND [LastName/GroupName] LIKE '%" + artistLastName + "%' ";
/*      */     } 
/*  262 */     if (!context.getParameter("projectDesc").equals(""))
/*      */     {
/*  264 */       projectsQuery = String.valueOf(projectsQuery) + " AND [Name] LIKE '%" + context.getParameter("projectDesc") + "%' ";
/*      */     }
/*  266 */     if (!context.getParameter("title").equals(""))
/*      */     {
/*  268 */       projectsQuery = String.valueOf(projectsQuery) + " AND [Title] LIKE '%" + context.getParameter("title") + "%' ";
/*      */     }
/*  270 */     if (!context.getParameter("projectID").equals(""))
/*      */     {
/*      */       
/*  273 */       projectsQuery = String.valueOf(projectsQuery) + " AND [RMSProjectNumber] LIKE '%" + context.getParameter("projectID") + "%' ";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  278 */     projectsQuery = String.valueOf(projectsQuery) + " AND NOT  [LastName/GroupName] = 'Miscellaneous' ";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  283 */     String loaStr = projectSearchSvcClient.ProjSearchPropertStrGet("LOA");
/*  284 */     projectsQuery = String.valueOf(projectsQuery) + " AND tblArtistRoster.ARLevelOfActivityID IN (" + loaStr + ") ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  290 */     if (isUmvdUser || umvdLabelSelected) {
/*      */ 
/*      */       
/*  293 */       Vector umvdProjectSearchResults = new Vector();
/*      */ 
/*      */       
/*  296 */       Vector umvdLabel = new Vector();
/*      */ 
/*      */ 
/*      */       
/*  300 */       String umvdQuery = " WHERE ";
/*  301 */       for (int k = 0; k < archiIdVector.size(); k++) {
/*  302 */         if (k == 0) {
/*  303 */           umvdQuery = String.valueOf(umvdQuery) + " [ID] IN ( " + archiIdVector.elementAt(k);
/*      */         } else {
/*  305 */           umvdQuery = String.valueOf(umvdQuery) + " , " + archiIdVector.elementAt(k);
/*      */         } 
/*  307 */       }  umvdQuery = String.valueOf(umvdQuery) + " ) ";
/*      */       
/*  309 */       if (!context.getParameter("projectID").equals("")) {
/*  310 */         umvdQuery = String.valueOf(umvdQuery) + " AND [ProjectNo] LIKE '%" + context.getParameter("projectID") + "%' ";
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  362 */       umvdProjectSearchResults = new Vector();
/*  363 */       umvdProjectSearchResults = projectSearchSvcClient.ProjectSearchGetUMVDDataContract(archiIdVector, context.getParameter("projectID"));
/*  364 */       System.out.println("Only Project ID Passed : Projects from WCF: " + umvdProjectSearchResults.size());
/*      */       
/*  366 */       return buildUMVDProjectSearchVector(umvdProjectSearchResults, labelHashtable);
/*      */     } 
/*      */ 
/*      */     
/*  370 */     Vector archiProjectsVector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  473 */     System.out.println("All Parameters Passed : Projects from WCF: ");
/*      */ 
/*      */ 
/*      */     
/*  477 */     archiProjectsVector = new Vector();
/*  478 */     archiProjectsVector = projectSearchSvcClient.ProjectSearchGetDataContract(archiIdVector, 
/*  479 */         context.getParameter("artistFirstName2"), 
/*  480 */         context.getParameter("artistLastName"), 
/*  481 */         context.getParameter("projectDesc"), 
/*  482 */         context.getParameter("title"), 
/*  483 */         context.getParameter("projectID"));
/*  484 */     System.out.println("All Parameters Passed : Projects from WCF: " + archiProjectsVector.size());
/*      */ 
/*      */ 
/*      */     
/*  488 */     if (archiProjectsVector.size() == 0) {
/*      */       
/*  490 */       archiProjectsVector = projectSearchSvcClient.ProjectSearchGetUMVDDataContract(archiIdVector, 
/*  491 */           unformatForJDEProjectNumber(context.getParameter("projectID")));
/*  492 */       System.out.println(" In case if there are no result from above search - look in UMVD Labels : Projects from WCF: " + archiProjectsVector.size());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  499 */     return (combineProjectLabelDetails(archiProjectsVector, labelHashtable) != null) ? combineProjectLabelDetails(archiProjectsVector, labelHashtable) : archiProjectsVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector buildUMVDProjectSearchVector(Vector projectSearchFromLabelsView, Hashtable labHash) {
/*  510 */     Vector projectSearchVector = new Vector();
/*      */ 
/*      */     
/*  513 */     if (projectSearchFromLabelsView.size() == 0) {
/*  514 */       return projectSearchVector;
/*      */     }
/*      */     
/*  517 */     for (int counter = 0; counter < projectSearchFromLabelsView.size(); counter++) {
/*      */       
/*  519 */       ProjectSearch projectSearchObject = (ProjectSearch)projectSearchFromLabelsView.elementAt(counter);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  524 */       ProjectSearch labelDetail = (ProjectSearch)labHash.get(projectSearchObject.getArchimedesID());
/*      */       
/*  526 */       labelDetail.setRMSProjectNo(projectSearchObject.getRMSProjectNo());
/*      */       
/*  528 */       if (projectSearchObject.getJDEProjectNo() != null) {
/*      */         
/*  530 */         labelDetail.setProjectID(projectSearchObject.getJDEProjectNo());
/*      */       }
/*      */       else {
/*      */         
/*  534 */         labelDetail.setProjectID(projectSearchObject.getSAPProjectNo());
/*      */       } 
/*  536 */       labelDetail.setJDEProjectNo(projectSearchObject.getJDEProjectNo());
/*      */       
/*  538 */       labelDetail.setSAPProjectNo(projectSearchObject.getSAPProjectNo());
/*  539 */       labelDetail.setfinancialLabelDescription(projectSearchObject.getfinancialLabelDescription());
/*      */ 
/*      */       
/*  542 */       labelDetail.setArtistFirstName("");
/*  543 */       labelDetail.setArtistLastName("");
/*      */       
/*  545 */       labelDetail.setCreateDate(MilestoneHelper.getDate("9/9/99"));
/*      */       
/*  547 */       labelDetail.setProjectDesc("");
/*      */ 
/*      */ 
/*      */       
/*  551 */       projectSearchVector.add(labelDetail);
/*      */     } 
/*      */     
/*  554 */     return projectSearchVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector combineProjectLabelDetails(Vector projectsVector, Hashtable labHash) {
/*  565 */     Vector completeArchiVector = new Vector();
/*      */     
/*  567 */     for (int i = 0; i < projectsVector.size(); i++) {
/*      */       
/*  569 */       ProjectSearch currentProj = (ProjectSearch)projectsVector.elementAt(i);
/*  570 */       String archiStr = currentProj.getArchimedesID();
/*      */       
/*  572 */       ProjectSearch labelDetail = (ProjectSearch)labHash.get(archiStr);
/*      */       
/*  574 */       if (labelDetail != null) {
/*  575 */         currentProj.setMSFamilyId(labelDetail.getMSFamilyId());
/*  576 */         currentProj.setMSEnvironmentId(labelDetail.getMSEnvironmentId());
/*  577 */         currentProj.setMSCompanyId(labelDetail.getMSCompanyId());
/*  578 */         currentProj.setMSDivisionId(labelDetail.getMSDivisionId());
/*  579 */         currentProj.setMSLabelId(labelDetail.getMSLabelId());
/*  580 */         currentProj.setOperCompany(labelDetail.getOperCompany());
/*  581 */         currentProj.setSuperLabel(labelDetail.getSuperLabel());
/*  582 */         currentProj.setSubLabel(labelDetail.getSubLabel());
/*  583 */         currentProj.setPD_Indicator(labelDetail.getPD_Indicator());
/*      */         
/*  585 */         completeArchiVector.addElement(currentProj);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  590 */     return completeArchiVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getUmvdArchieResultsFromMilestoneSnapshot(Context context, String archiClause, Hashtable labelHashtable) {
/*  598 */     String archiUmvdQuery = "SELECT * FROM dbo.[ArchimedesLabels] ";
/*  599 */     archiUmvdQuery = String.valueOf(archiUmvdQuery) + archiClause;
/*      */ 
/*      */     
/*  602 */     archiUmvdQuery = replaceStringInString(archiUmvdQuery, "[ID]", "[ArchimedesID]");
/*      */     
/*  604 */     JdbcConnector connector = MilestoneHelper.getConnector(archiUmvdQuery);
/*      */     
/*      */     try {
/*  607 */       connector.runQuery();
/*  608 */     } catch (Exception e) {
/*  609 */       System.out.println("****exception raised in getAllArchieResultsFromMilestoneSnapshot****");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  616 */     Vector umvdProjectSearchResults = new Vector();
/*  617 */     while (connector.more()) {
/*      */       
/*  619 */       ProjectSearch newProjectSearch = new ProjectSearch();
/*  620 */       newProjectSearch.setJDEProjectNo(connector.getField("ProjectNo", ""));
/*  621 */       newProjectSearch.setArchimedesID(connector.getField("ArchimedesID", ""));
/*  622 */       umvdProjectSearchResults.add(newProjectSearch);
/*  623 */       connector.next();
/*      */     } 
/*  625 */     connector.close();
/*  626 */     return buildUMVDProjectSearchVector(umvdProjectSearchResults, labelHashtable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getAllArchieResultsFromMilestoneSnapshot(Context context, String query, Hashtable labHash) {
/*  635 */     String projectsQuery = " SELECT TOP 200 * FROM dbo.ArchimedesProjects " + query;
/*      */ 
/*      */     
/*  638 */     String newQuery = "";
/*  639 */     newQuery = replaceStringInString(projectsQuery, "[labelId]", "[Archimedes ID]");
/*  640 */     newQuery = replaceStringInString(newQuery, "[FirstName]", "[Artist First Name]");
/*  641 */     newQuery = replaceStringInString(newQuery, "[LastName/GroupName]", "[Artist Last Name/Group Name]");
/*  642 */     newQuery = replaceStringInString(newQuery, "[Name]", "[Project Description]");
/*  643 */     newQuery = replaceStringInString(newQuery, "[Title]", "[Project Title]");
/*  644 */     newQuery = replaceStringInString(newQuery, "[RMSProjectNumber]", "[RMS Project#]");
/*      */     
/*  646 */     newQuery = replaceStringInString(newQuery, "[LastName/GroupName]", "[Artist Last Name/Group Name]");
/*  647 */     newQuery = replaceStringInString(newQuery, "tblARProject.Active", "Active");
/*  648 */     newQuery = replaceStringInString(newQuery, "tblArtistRoster.ARLevelOfActivityID", "ArtistRosterActivity");
/*      */     
/*  650 */     JdbcConnector connector = MilestoneHelper.getConnector(newQuery);
/*      */ 
/*      */     
/*      */     try {
/*  654 */       connector.runQuery();
/*  655 */     } catch (Exception e) {
/*  656 */       System.out.println("****exception raised in getAllArchieResultsFromMilestoneSnapshot****");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  663 */     Vector archiProjectsVector = new Vector();
/*      */     
/*  665 */     while (connector.more()) {
/*      */       
/*  667 */       ProjectSearch newProjectSearch = new ProjectSearch();
/*      */ 
/*      */ 
/*      */       
/*  671 */       newProjectSearch.setArtistFirstName(connector.getField("Artist First Name", ""));
/*      */       
/*  673 */       newProjectSearch.setArtistLastName(connector.getField("Artist Last Name/Group Name", ""));
/*      */       
/*  675 */       newProjectSearch.setProjectID(connector.getField("Project ID"));
/*      */       
/*  677 */       newProjectSearch.setTitle(connector.getField("Project Title", ""));
/*      */       
/*  679 */       newProjectSearch.setProjectDesc(connector.getField("Project Description", ""));
/*      */       
/*  681 */       String createDateString = connector.getFieldByName("CompletedDate");
/*  682 */       if (createDateString != null) {
/*  683 */         newProjectSearch.setCreateDate(MilestoneHelper.getDatabaseDate(createDateString));
/*      */       }
/*  685 */       newProjectSearch.setArchimedesID(String.valueOf(connector.getIntegerField("Archimedes ID")));
/*      */ 
/*      */       
/*  688 */       newProjectSearch.setJDEProjectNo(connector.getField("JDE Project#", ""));
/*  689 */       newProjectSearch.setRMSProjectNo(connector.getField("RMS Project#", ""));
/*  690 */       newProjectSearch.setImprint(connector.getField("Imprint", ""));
/*      */       
/*  692 */       archiProjectsVector.addElement(newProjectSearch);
/*      */       
/*  694 */       connector.next();
/*      */     } 
/*  696 */     connector.close();
/*      */ 
/*      */     
/*  699 */     return combineProjectLabelDetails(archiProjectsVector, labHash);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getArchieProjects(Context context, User user) throws Exception {
/*  705 */     String query = "";
/*  706 */     Vector precache = new Vector();
/*  707 */     Company company = null;
/*      */ 
/*      */     
/*  710 */     Vector archieVector = new Vector();
/*  711 */     archieVector = accessArchieView(context, user);
/*      */ 
/*      */     
/*  714 */     Vector sortedVector = new Vector();
/*  715 */     sortedVector = sortResults(context, user, archieVector, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  720 */     return addUnknowns(context, user, sortedVector);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector addUnknowns(Context context, User user, Vector sortedVector) {
/*  730 */     Vector userCompanies = MilestoneHelper.getUserCompanies(context);
/*  731 */     Vector editableUserCompanies = filterSelectionCompaniesWithEditRights(userCompanies, user);
/*  732 */     Vector editableUserFamilies = new Vector();
/*  733 */     for (int i = 0; i < editableUserCompanies.size(); i++) {
/*  734 */       Company editCompany = (Company)editableUserCompanies.elementAt(i);
/*  735 */       Environment editEnvironment = editCompany.getParentEnvironment();
/*  736 */       Family editFamily = editEnvironment.getParentFamily();
/*  737 */       editableUserFamilies.add(editFamily);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  742 */     String unknownquery = " SELECT a.MS_LabelName, a.MS_FamilyId, a.MS_EnvironmentId, a.MS_CompanyId,  a.MS_DivisionId, a.MS_LabelId  FROM ArchimedesLabels a  JOIN Structure b on a.MS_LabelId = b.structure_id  WHERE a.unknownLabel = 1  AND b.sort = 0 ";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  750 */     if (context.getParameter("isPhysical").equals("Digital")) {
/*  751 */       unknownquery = String.valueOf(unknownquery) + " AND NOT b.name LIKE '%fontana%' ";
/*      */     }
/*      */     
/*  754 */     for (int j = 0; j < editableUserFamilies.size(); j++) {
/*  755 */       if (j == 0) {
/*  756 */         unknownquery = String.valueOf(unknownquery) + " AND a.MS_FamilyId IN ( " + ((Family)editableUserFamilies.elementAt(j)).getIdentity();
/*      */       } else {
/*  758 */         unknownquery = String.valueOf(unknownquery) + " , " + ((Family)editableUserFamilies.elementAt(j)).getIdentity();
/*      */       } 
/*  760 */     }  unknownquery = String.valueOf(unknownquery) + " ) ";
/*      */ 
/*      */     
/*  763 */     unknownquery = String.valueOf(unknownquery) + " ORDER BY a.MS_LabelName ";
/*      */ 
/*      */     
/*  766 */     Vector unknownProjectsVector = new Vector();
/*  767 */     JdbcConnector connector = MilestoneHelper.getConnector(unknownquery);
/*  768 */     connector.setForwardOnly(false);
/*  769 */     connector.runQuery();
/*  770 */     while (connector.more()) {
/*      */       
/*  772 */       ProjectSearch unknownProjectObject = new ProjectSearch();
/*      */ 
/*      */       
/*  775 */       unknownProjectObject.setProjectDesc(connector.getField("MS_LabelName", ""));
/*      */       
/*  777 */       unknownProjectObject.setSubLabel("***");
/*  778 */       unknownProjectObject.setSuperLabel("***");
/*  779 */       unknownProjectObject.setOperCompany("***");
/*      */       
/*  781 */       unknownProjectObject.setProjectID("0000-00000");
/*  782 */       unknownProjectObject.setRMSProjectNo("0000-00000");
/*  783 */       unknownProjectObject.setJDEProjectNo("0000-00000");
/*      */       
/*  785 */       unknownProjectObject.setMSFamilyId(connector.getIntegerField("MS_FamilyId"));
/*  786 */       unknownProjectObject.setMSEnvironmentId(connector.getIntegerField("MS_EnvironmentId"));
/*  787 */       unknownProjectObject.setMSCompanyId(connector.getIntegerField("MS_CompanyId"));
/*  788 */       unknownProjectObject.setMSDivisionId(connector.getIntegerField("MS_DivisionId"));
/*  789 */       unknownProjectObject.setMSLabelId(connector.getIntegerField("MS_LabelId"));
/*      */       
/*  791 */       unknownProjectObject.setArtistFirstName("");
/*  792 */       unknownProjectObject.setArtistLastName("Unknown Project");
/*  793 */       unknownProjectObject.setCreateDate(MilestoneHelper.getDate("9/9/99"));
/*  794 */       unknownProjectObject.setPD_Indicator(0);
/*  795 */       unknownProjectObject.setTitle("Unknown Project");
/*      */ 
/*      */       
/*  798 */       String imprintName = MilestoneHelper.getStructureName(connector.getIntegerField("MS_LabelId"));
/*  799 */       unknownProjectObject.setImprint(imprintName);
/*      */       
/*  801 */       unknownProjectsVector.addElement(unknownProjectObject);
/*  802 */       connector.next();
/*      */     } 
/*      */     
/*  805 */     String numberRows = Integer.toString(connector.getRowCount());
/*      */ 
/*      */     
/*  808 */     context.putSessionValue("number_unknowns", numberRows);
/*      */     
/*  810 */     connector.close();
/*      */ 
/*      */     
/*  813 */     sortedVector.addAll(unknownProjectsVector);
/*      */     
/*  815 */     return sortedVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector sortResults(Context context, User user, Vector searchResults, boolean newSort) {
/*  825 */     String sortColumn = "6";
/*  826 */     String sortDirection = "Descending";
/*  827 */     if (context.getParameter("sortColumn") != null) {
/*  828 */       sortColumn = context.getParameter("sortColumn");
/*      */     }
/*  830 */     if (context.getParameter("sortDirection") != null) {
/*  831 */       sortDirection = context.getParameter("sortDirection");
/*      */     }
/*      */ 
/*      */     
/*  835 */     Vector unknownRows = new Vector();
/*      */ 
/*      */ 
/*      */     
/*  839 */     if (!newSort) {
/*      */       
/*  841 */       String numberRows = "0";
/*  842 */       if (context.getSessionValue("number_unknowns") != null) {
/*  843 */         numberRows = (String)context.getSessionValue("number_unknowns");
/*      */       }
/*      */       
/*  846 */       int numberRowsInt = Integer.parseInt(numberRows);
/*  847 */       int startingPosition = searchResults.size() - Integer.parseInt(numberRows);
/*  848 */       int endingPosition = searchResults.size();
/*      */       
/*  850 */       for (int j = startingPosition; j < endingPosition; j++) {
/*  851 */         unknownRows.add((ProjectSearch)searchResults.elementAt(startingPosition));
/*  852 */         searchResults.remove(startingPosition);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  858 */     int iSortColumn = Integer.parseInt(sortColumn);
/*      */     
/*  860 */     Object[] projectsArray = searchResults.toArray();
/*      */ 
/*      */     
/*  863 */     Arrays.sort(projectsArray, new ProjectSearchComparator(iSortColumn, sortDirection));
/*      */ 
/*      */     
/*  866 */     Vector sortedResults = new Vector();
/*  867 */     for (int i = 0; i < projectsArray.length; i++) {
/*  868 */       sortedResults.addElement((ProjectSearch)projectsArray[i]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  874 */     if (!newSort) {
/*  875 */       sortedResults.addAll(unknownRows);
/*      */     }
/*      */     
/*  878 */     return sortedResults;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector filterSelectionCompaniesWithEditRights(Vector companies, User user) {
/*  886 */     Vector result = new Vector();
/*  887 */     Vector cmpAclList = user.getAcl().getCompanyAcl();
/*  888 */     HashMap cmpEditRight = new HashMap();
/*      */ 
/*      */ 
/*      */     
/*  892 */     if (cmpAclList != null) {
/*  893 */       for (int n = 0; n < cmpAclList.size(); n++) {
/*      */         
/*  895 */         CompanyAcl cmpAcl = (CompanyAcl)cmpAclList.get(n);
/*  896 */         if (cmpAcl.getAccessSelection() == 2) {
/*  897 */           cmpEditRight.put(new Integer(cmpAcl.getCompanyId()), new Integer(n));
/*      */         }
/*      */       } 
/*      */     }
/*  901 */     if (companies != null && companies.size() > 0)
/*      */     {
/*  903 */       for (int i = 0; i < companies.size(); i++) {
/*      */ 
/*      */         
/*  906 */         Company company = (Company)companies.get(i);
/*  907 */         String name = company.getName();
/*      */         
/*  909 */         if (cmpAclList == null) {
/*  910 */           if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise")) {
/*  911 */             result.add(company);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  916 */         else if (!name.equalsIgnoreCase("UML") && !name.equalsIgnoreCase("Enterprise") && 
/*  917 */           cmpEditRight.containsKey(new Integer(company.getStructureID()))) {
/*  918 */           result.add(company);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  923 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isUmvdProjectSearchUser(User user, Context context, Vector jdeExceptionFamilies) {
/*  928 */     Vector userCompanies = MilestoneHelper.getUserCompanies(context);
/*  929 */     Vector editableUserCompanies = filterSelectionCompaniesWithEditRights(userCompanies, user);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  934 */     boolean isUmvdUser = false;
/*  935 */     String employedByString = "";
/*  936 */     if (user.getEmployedBy() > 0) {
/*  937 */       employedByString = String.valueOf(user.getEmployedBy());
/*      */     }
/*      */     
/*  940 */     boolean umvdFamilyFlag = true;
/*      */     
/*  942 */     if (employedByString.equalsIgnoreCase("417")) {
/*      */ 
/*      */ 
/*      */       
/*  946 */       if (editableUserCompanies.size() == 0) {
/*  947 */         umvdFamilyFlag = false;
/*      */       }
/*      */       else {
/*      */         
/*  951 */         for (int t = 0; t < editableUserCompanies.size() && umvdFamilyFlag; t++) {
/*      */           
/*  953 */           Company eCompany = (Company)editableUserCompanies.elementAt(t);
/*  954 */           Environment eEnvironment = eCompany.getParentEnvironment();
/*  955 */           Family eFamily = eEnvironment.getParentFamily();
/*      */ 
/*      */ 
/*      */           
/*  959 */           int familyId = eFamily.getStructureID();
/*      */ 
/*      */           
/*  962 */           if (!jdeExceptionFamilies.contains(new Integer(familyId))) {
/*  963 */             umvdFamilyFlag = false;
/*      */           }
/*      */         } 
/*      */       } 
/*  967 */       isUmvdUser = umvdFamilyFlag;
/*      */     } 
/*      */     
/*  970 */     return isUmvdUser;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String formatJDEProjectNumber(String rawString) {
/*  976 */     if (!rawString.isEmpty()) {
/*      */       
/*  978 */       if (rawString.length() == 8) {
/*  979 */         return "0" + rawString.substring(0, 3) + "-" + rawString.substring(3, 8);
/*      */       }
/*      */ 
/*      */       
/*  983 */       return rawString;
/*      */     } 
/*      */ 
/*      */     
/*  987 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String unformatForJDEProjectNumber(String rawString) {
/*  995 */     if (!rawString.isEmpty()) {
/*      */       
/*  997 */       if (rawString.length() > 8) {
/*  998 */         String formattedString = "";
/*  999 */         if (rawString.contains("-")) {
/*      */           
/* 1001 */           formattedString = rawString.replace("-", "");
/* 1002 */           formattedString = formattedString.substring(1, formattedString.length());
/*      */         }
/*      */         else {
/*      */           
/* 1006 */           formattedString = rawString;
/*      */         } 
/* 1008 */         return formattedString;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1013 */       return rawString;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1018 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isProjectNumberValid(String projectNumber) {
/* 1027 */     boolean isValid = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1064 */       isValid = projectSearchSvcClient.ProjectNumberValidationGet(projectNumber).booleanValue();
/* 1065 */       System.out.println("Project Is Valid: " + isValid);
/*      */     }
/* 1067 */     catch (Exception e) {
/*      */       
/* 1069 */       System.out.println("****exception raised in accessArchieView2****");
/*      */     } 
/*      */ 
/*      */     
/* 1073 */     return isValid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isProjectNumberValidFromMilestoneSnapshot(String projectNumber) {
/* 1082 */     String projectsQuery = "select projectNo from ArchimedesLabels where projectNo = " + projectNumber;
/*      */     
/* 1084 */     JdbcConnector connector = MilestoneHelper.getConnector(projectsQuery);
/*      */ 
/*      */     
/*      */     try {
/* 1088 */       connector.runQuery();
/* 1089 */     } catch (Exception e) {
/* 1090 */       System.out.println("****exception raised in isProjectNumberValidFromMilestoneSnapshot****");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1095 */     boolean isValid = false;
/* 1096 */     if (connector.more()) {
/* 1097 */       isValid = true;
/*      */     }
/* 1099 */     connector.close();
/* 1100 */     return isValid;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String replaceStringInString(String fullStr, String searchForStr, String replaceWithStr) {
/* 1105 */     int fullLength = fullStr.length();
/* 1106 */     int searchForStrLength = searchForStr.length();
/*      */     
/* 1108 */     int index = fullStr.indexOf(searchForStr);
/*      */ 
/*      */     
/* 1111 */     if (index != -1) {
/* 1112 */       fullStr = String.valueOf(fullStr.substring(0, index)) + replaceWithStr + fullStr.substring(index + searchForStrLength, fullLength);
/*      */     }
/*      */     
/* 1115 */     return fullStr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCanadaUser(User user, Context context) {
/* 1122 */     boolean isCanadaUser = false;
/* 1123 */     String employedByName = "";
/* 1124 */     if (user.getEmployedBy() > 0) {
/* 1125 */       employedByName = MilestoneHelper.getStructureName(user.getEmployedBy());
/* 1126 */       if (employedByName.equals("Canada")) {
/* 1127 */         isCanadaUser = true;
/*      */       }
/*      */     } 
/* 1130 */     return isCanadaUser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMexicoUser(User user, Context context) {
/* 1137 */     boolean isMexicoUser = false;
/* 1138 */     String employedByName = "";
/* 1139 */     if (user.getEmployedBy() > 0) {
/* 1140 */       employedByName = MilestoneHelper.getStructureName(user.getEmployedBy());
/* 1141 */       if (employedByName.equals("Mexico")) {
/* 1142 */         isMexicoUser = true;
/*      */       }
/*      */     } 
/* 1145 */     return isMexicoUser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean sendEmailDistribution(Context context) {
/* 1156 */     String envName = "";
/* 1157 */     if (context.getSessionValue("environment_name") != null) {
/* 1158 */       envName = (String)context.getSessionValue("environment_name");
/*      */     }
/*      */     
/* 1161 */     String subject = String.valueOf(envName.toUpperCase()) + " Project Search: Archimedes DB Connection Down ";
/* 1162 */     String body = " The " + envName.toUpperCase() + " database connection from Milestone to Archimedes is down. " + 
/* 1163 */       envName.toUpperCase() + " Milestone will be querying against the ArchimedesProjects snapshot table";
/* 1164 */     String recipients = "cgareca@hpe.com";
/* 1165 */     String copyRecipients = "cgareca@hpe.com;shanmuga.selvaraj@umusic.com";
/* 1166 */     String attachment = "";
/*      */     
/* 1168 */     boolean result = false;
/*      */     
/*      */     try {
/* 1171 */       StringTokenizer valueTokenizer = new StringTokenizer(recipients, ";");
/* 1172 */       ArrayList destList = new ArrayList();
/*      */       
/* 1174 */       while (valueTokenizer.hasMoreTokens()) {
/* 1175 */         destList.add(new SessionUserEmailObj(valueTokenizer.nextToken().trim(), true));
/*      */       }
/*      */       
/* 1178 */       StringTokenizer ccValueTokenizer = new StringTokenizer(copyRecipients, ";");
/* 1179 */       ArrayList ccList = new ArrayList();
/*      */       
/* 1181 */       while (ccValueTokenizer.hasMoreTokens()) {
/* 1182 */         ccList.add(new SessionUserEmailObj(ccValueTokenizer.nextToken().trim(), true));
/*      */       }
/* 1184 */       SessionUserEmail sue = new SessionUserEmail();
/* 1185 */       body = "<font face='arial'>" + body + "</font>";
/* 1186 */       result = sue.sendEmail(destList, body, subject, attachment, ccList);
/*      */     }
/* 1188 */     catch (Exception e) {
/*      */       
/* 1190 */       System.out.println("send email exception...");
/*      */     } 
/* 1192 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getProjectSearchJDEFamilies() {
/* 1199 */     returnFamilies = new Vector();
/*      */     
/* 1201 */     String jdeQuery = "select value from vi_lookup_detail where field_id = 65";
/* 1202 */     JdbcConnector connector = MilestoneHelper.getConnector(jdeQuery);
/*      */     
/*      */     try {
/* 1205 */       connector.runQuery();
/* 1206 */     } catch (Exception e) {
/* 1207 */       System.out.println("****exception raised in getProjectSearchJDEFamilies****");
/*      */     } 
/*      */     
/* 1210 */     while (connector.more()) {
/*      */       
/* 1212 */       returnFamilies.add(new Integer(connector.getField("value")));
/* 1213 */       connector.next();
/*      */     } 
/* 1215 */     connector.close();
/*      */     
/* 1217 */     return returnFamilies;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ProjectSearchManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */