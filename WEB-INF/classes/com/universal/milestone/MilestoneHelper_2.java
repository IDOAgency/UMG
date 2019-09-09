/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.ComponentLog;
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.FormDropDownMenu;
/*     */ import com.universal.milestone.Acl;
/*     */ import com.universal.milestone.Cache;
/*     */ import com.universal.milestone.CompanyAcl;
/*     */ import com.universal.milestone.CorporateStructureObject;
/*     */ import com.universal.milestone.JdbcConnector;
/*     */ import com.universal.milestone.Label;
/*     */ import com.universal.milestone.LookupObject;
/*     */ import com.universal.milestone.MilestoneConstants;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.MilestoneHelper_2;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.User;
/*     */ import com.universal.milestone.projectSearchSvcClient;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
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
/*     */ public class MilestoneHelper_2
/*     */   implements MilestoneConstants
/*     */ {
/*     */   public static final String COMPONENT_CODE = "help_2";
/*  45 */   public static String MILESTONE_CONFIG_FILE = "milestone.conf";
/*     */ 
/*     */   
/*     */   protected static ComponentLog log;
/*     */ 
/*     */   
/*  51 */   protected static int[] UPC_SSG_Dashes = { 1, 6, 13 };
/*     */   
/*  53 */   protected static int[] ISRC_Dashes = { 2, 5, 7 };
/*     */   
/*  55 */   protected static int UPC_maxLen = 14;
/*  56 */   protected static int SSG_maxLen = 14;
/*  57 */   protected static int ISRC_maxLen = 12;
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
/*     */   public static String getRMSReportFormat(String inputString, String formatType, boolean isDigital) {
/*  74 */     if (inputString == null || inputString.length() == 0) {
/*  75 */       return inputString;
/*     */     }
/*  77 */     StringBuffer rmsFormat = new StringBuffer();
/*     */ 
/*     */     
/*  80 */     Hashtable dashsHash = new Hashtable();
/*     */ 
/*     */     
/*  83 */     if (isDigital && formatType.equalsIgnoreCase("SSG"))
/*     */     {
/*  85 */       if (isISRC(inputString, isDigital)) {
/*  86 */         formatType = "ISRC";
/*     */       } else {
/*  88 */         formatType = "UPC";
/*     */       } 
/*     */     }
/*     */     
/*  92 */     if (formatType.equalsIgnoreCase("UPC") || formatType.equalsIgnoreCase("SSG"))
/*     */     {
/*  94 */       for (int i = 0; i < UPC_SSG_Dashes.length; i++) {
/*  95 */         dashsHash.put(new Integer(UPC_SSG_Dashes[i]), "-");
/*     */       }
/*     */     }
/*  98 */     if (formatType.equalsIgnoreCase("ISRC"))
/*     */     {
/* 100 */       for (int i = 0; i < ISRC_Dashes.length; i++) {
/* 101 */         dashsHash.put(new Integer(ISRC_Dashes[i]), "-");
/*     */       }
/*     */     }
/*     */     
/* 105 */     for (int i = 0; i < inputString.length(); i++) {
/*     */       
/* 107 */       Integer d = new Integer(i);
/*     */       
/* 109 */       if (dashsHash.containsKey(d)) {
/* 110 */         rmsFormat.append((String)dashsHash.get(d));
/*     */       }
/*     */       
/* 113 */       rmsFormat.append(inputString.charAt(i));
/*     */     } 
/*     */     
/* 116 */     return rmsFormat.toString();
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
/*     */   public static String reformat_UPC_SSG_SGC_forSave(String inputString, String formatType, boolean isDigital, boolean zeroFill) {
/* 129 */     if (inputString == null || inputString.length() == 0) {
/* 130 */       return inputString;
/*     */     }
/* 132 */     StringBuffer newStringBuf = new StringBuffer();
/* 133 */     boolean isISRC = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     if (isDigital && formatType.equalsIgnoreCase("SSG"))
/*     */     {
/* 140 */       if (isISRC(inputString, isDigital)) {
/*     */         
/* 142 */         isISRC = true;
/* 143 */         formatType = "ISRC";
/*     */       }
/*     */       else {
/*     */         
/* 147 */         isISRC = false;
/* 148 */         formatType = "UPC";
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 153 */     for (int i = 0; i < inputString.length(); i++) {
/*     */       
/* 155 */       char c = inputString.charAt(i);
/*     */       
/* 157 */       if (isISRC) {
/*     */ 
/*     */ 
/*     */         
/* 161 */         if (Character.isLetterOrDigit(c)) {
/* 162 */           newStringBuf.append(inputString.charAt(i));
/*     */         
/*     */         }
/*     */       }
/* 166 */       else if (Character.isDigit(c)) {
/* 167 */         newStringBuf.append(inputString.charAt(i));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 172 */     int maxLen = -1;
/* 173 */     if (formatType.equalsIgnoreCase("UPC"))
/* 174 */       maxLen = UPC_maxLen; 
/* 175 */     if (formatType.equalsIgnoreCase("SSG"))
/* 176 */       maxLen = SSG_maxLen; 
/* 177 */     if (formatType.equalsIgnoreCase("ISRC")) {
/* 178 */       maxLen = ISRC_maxLen;
/*     */     }
/*     */ 
/*     */     
/* 182 */     if (zeroFill && !isISRC) {
/*     */ 
/*     */       
/* 185 */       int zeros = maxLen - newStringBuf.toString().length();
/* 186 */       for (int i = 0; i < zeros; i++) {
/* 187 */         newStringBuf.insert(0, '0');
/*     */       }
/*     */     } 
/*     */     
/* 191 */     if (newStringBuf.length() > maxLen && maxLen > 0) {
/* 192 */       newStringBuf.setLength(maxLen);
/*     */     }
/*     */     
/* 195 */     return newStringBuf.toString();
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
/*     */   public static boolean isISRC(String inputString, boolean isDigital) {
/* 207 */     boolean isISRC = false;
/*     */ 
/*     */     
/* 210 */     if (inputString == null || inputString.length() < 2) {
/* 211 */       return isISRC;
/*     */     }
/*     */ 
/*     */     
/* 215 */     if (isDigital)
/*     */     {
/*     */       
/* 218 */       if (!Character.isDigit(inputString.charAt(0)) && !Character.isDigit(inputString.charAt(1))) {
/* 219 */         isISRC = true;
/*     */       }
/*     */     }
/*     */     
/* 223 */     return isISRC;
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
/*     */   public static boolean userHasSelectionEditPermission(Context context) {
/* 235 */     User user = (User)context.getSession().getAttribute("user");
/*     */     
/* 237 */     boolean hasSelPermission = false;
/*     */ 
/*     */     
/* 240 */     if (user == null) {
/* 241 */       return false;
/*     */     }
/*     */     
/* 244 */     Acl acl = user.getAcl();
/* 245 */     if (acl == null) {
/* 246 */       return false;
/*     */     }
/*     */     
/* 249 */     Vector companyAcls = acl.getCompanyAcl();
/* 250 */     if (companyAcls == null) {
/* 251 */       return false;
/*     */     }
/*     */     
/* 254 */     for (int i = 0; i < companyAcls.size(); i++) {
/*     */       
/* 256 */       CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
/* 257 */       if (companyAcl != null)
/*     */       {
/* 259 */         if (companyAcl.getAccessSelection() == 2) {
/* 260 */           return true;
/*     */         }
/*     */       }
/*     */     } 
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean userHasSelectionEditPermission(Acl acl) {
/* 274 */     boolean hasSelPermission = false;
/*     */ 
/*     */     
/* 277 */     if (acl == null) {
/* 278 */       return false;
/*     */     }
/*     */     
/* 281 */     Vector companyAcls = acl.getCompanyAcl();
/* 282 */     if (companyAcls == null) {
/* 283 */       return false;
/*     */     }
/*     */     
/* 286 */     for (int i = 0; i < companyAcls.size(); i++) {
/*     */       
/* 288 */       CompanyAcl companyAcl = (CompanyAcl)companyAcls.get(i);
/* 289 */       if (companyAcl != null)
/*     */       {
/* 291 */         if (companyAcl.getAccessSelection() == 2) {
/* 292 */           return true;
/*     */         }
/*     */       }
/*     */     } 
/* 296 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIsStructureArchimedes(int structureId) {
/* 304 */     int result = 0;
/*     */     
/* 306 */     String query = "sp_get_Structure_IsArchimedes " + structureId;
/* 307 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 308 */     connector.runQuery();
/*     */ 
/*     */     
/* 311 */     result = connector.getIntegerField("ReturnId");
/*     */ 
/*     */ 
/*     */     
/* 315 */     connector.close();
/*     */     
/* 317 */     return result;
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
/*     */   public static Vector filterCorporateByEntityName(Vector corporateObjects, String filter) {
/* 332 */     if (MilestoneHelper.isStringNotEmpty(filter)) {
/*     */       
/* 334 */       int searchType = 0;
/* 335 */       filter = filter.replace('*', '%');
/* 336 */       int firstWildOccur = filter.indexOf("%");
/* 337 */       int lastWildOccur = filter.lastIndexOf("%");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 343 */       if (firstWildOccur < 0) {
/*     */ 
/*     */         
/* 346 */         searchType = 0;
/*     */       }
/* 348 */       else if (firstWildOccur > -1 && lastWildOccur > -1 && firstWildOccur != lastWildOccur) {
/*     */ 
/*     */         
/* 351 */         searchType = 1;
/* 352 */         filter = filter.substring(firstWildOccur + 1, lastWildOccur);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 357 */       else if (firstWildOccur == 0) {
/*     */ 
/*     */         
/* 360 */         searchType = 2;
/* 361 */         filter = filter.substring(1, filter.length());
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 367 */         searchType = 3;
/* 368 */         filter = filter.substring(0, filter.length() - 1);
/*     */       } 
/*     */ 
/*     */       
/* 372 */       Vector filteredObjects = new Vector();
/* 373 */       filter = filter.toUpperCase();
/*     */       
/* 375 */       for (int i = 0; i < corporateObjects.size(); i++) {
/*     */         
/* 377 */         Label corporateObject = (Label)corporateObjects.get(i);
/* 378 */         String description = corporateObject.getEntityName().toUpperCase();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 383 */         switch (searchType) {
/*     */           
/*     */           case 0:
/* 386 */             if (description.indexOf(filter) >= 0) {
/* 387 */               filteredObjects.add(corporateObject);
/*     */             }
/*     */             break;
/*     */           case 1:
/* 391 */             if (description.indexOf(filter) >= 0) {
/* 392 */               filteredObjects.add(corporateObject);
/*     */             }
/*     */             break;
/*     */           
/*     */           case 2:
/*     */             try {
/* 398 */               if (description.indexOf(filter) > -1 && 
/* 399 */                 description.indexOf(filter) == description.length() - filter.length()) {
/* 400 */                 filteredObjects.add(corporateObject);
/*     */               }
/* 402 */             } catch (Exception exception) {}
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 3:
/* 409 */             if (description.indexOf(filter) == 0) {
/* 410 */               filteredObjects.add(corporateObject);
/*     */             }
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 419 */       return filteredObjects;
/*     */     } 
/*     */ 
/*     */     
/* 423 */     return corporateObjects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initializeCorporateStructure(CorporateStructureObject COS, JdbcConnector connector) {
/* 433 */     COS.setStructureID(connector.getInt("structure_id"));
/* 434 */     COS.setParentID(connector.getInt("parent_id"));
/* 435 */     COS.setStructureType(connector.getInt("type"));
/* 436 */     COS.setName(connector.getField("name"));
/* 437 */     COS.setStructureAbbreviation(connector.getField("abbreviation"));
/* 438 */     Calendar lastUpdatedOn = Calendar.getInstance();
/* 439 */     lastUpdatedOn.setTime(connector.getTimestamp("last_updated_on"));
/* 440 */     COS.setLastUpdateDate(lastUpdatedOn);
/* 441 */     COS.setLastUpdatingUser(connector.getInt("last_updated_by"));
/* 442 */     COS.setArchimedesId(connector.getInt("archimedes_id", -1));
/*     */     
/* 444 */     COS.setLastUpdatedCk(Long.parseLong(connector.getField("last_updated_ck"), 16));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasSchedule(int selectionID) {
/* 454 */     boolean exists = true;
/*     */     
/* 456 */     String sqlQuery = "Select top 1 release_id from release_detail where release_id = " + 
/* 457 */       selectionID;
/* 458 */     JdbcConnector connector = MilestoneHelper.getConnector(sqlQuery);
/* 459 */     connector.runQuery();
/*     */     
/* 461 */     if (!connector.more())
/*     */     {
/* 463 */       exists = false;
/*     */     }
/* 465 */     connector.close();
/* 466 */     return exists;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertProductTypeToReleaseCalendar(String productType) {
/* 476 */     String retType = productType;
/* 477 */     if (productType.equalsIgnoreCase("physical"))
/* 478 */       retType = "Physical"; 
/* 479 */     if (productType.equalsIgnoreCase("digital"))
/* 480 */       retType = "Digital"; 
/* 481 */     if (productType.equalsIgnoreCase("both"))
/* 482 */       retType = "All"; 
/* 483 */     return retType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertProductTypeToUserPref(String productType) {
/* 492 */     String retType = productType;
/* 493 */     if (productType.equalsIgnoreCase("Physical"))
/* 494 */       retType = "physical"; 
/* 495 */     if (productType.equalsIgnoreCase("Digital"))
/* 496 */       retType = "digital"; 
/* 497 */     if (productType.equalsIgnoreCase("All"))
/* 498 */       retType = "both"; 
/* 499 */     return retType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String cleanText(String str) {
/* 507 */     int s = 0;
/* 508 */     int e = 0;
/* 509 */     String cleanString = "";
/* 510 */     boolean cleaned = false;
/*     */ 
/*     */     
/*     */     try {
/* 514 */       byte[] utf8Bytes = str.getBytes("UTF8");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 524 */       for (int b = 0; b < utf8Bytes.length; b++) {
/* 525 */         if (utf8Bytes[b] == -62 && 
/* 526 */           b < utf8Bytes.length && (utf8Bytes[b + 1] == -108 || utf8Bytes[b + 1] == -109)) {
/*     */ 
/*     */           
/* 529 */           utf8Bytes = shiftArray(utf8Bytes, b, b + 1, (byte)34);
/* 530 */           cleaned = true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 537 */       if (cleaned) {
/* 538 */         cleanString = new String(utf8Bytes);
/*     */       } else {
/* 540 */         cleanString = str;
/*     */       }
/*     */     
/* 543 */     } catch (UnsupportedEncodingException eN) {
/* 544 */       eN.printStackTrace();
/*     */     } 
/*     */     
/* 547 */     return cleanString;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] shiftArray(byte[] byteArray, int startIndex, int endIndex, byte replacementValue) {
/* 571 */     byte[] returnArray = new byte[byteArray.length - 1];
/*     */ 
/*     */     
/* 574 */     for (int j = 0; j < startIndex; j++) {
/* 575 */       returnArray[j] = byteArray[j];
/*     */     }
/*     */ 
/*     */     
/* 579 */     returnArray[startIndex] = replacementValue;
/*     */ 
/*     */     
/* 582 */     for (int k = endIndex + 1; k < byteArray.length; k++) {
/* 583 */       returnArray[k - 1] = byteArray[k];
/*     */     }
/*     */     
/* 586 */     return returnArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getLabelDistCo(int labelId) {
/* 597 */     String retDistCo = "";
/* 598 */     Integer id = new Integer(labelId);
/*     */     
/* 600 */     if (Cache.cachedCorporateStructureHash.containsKey(id)) {
/*     */       
/*     */       try {
/* 603 */         Label label = (Label)Cache.cachedCorporateStructureHash.get(id);
/* 604 */         retDistCo = label.getDistCoName();
/* 605 */       } catch (Exception le) {
/*     */         
/* 607 */         System.out.println("    getLabelDistCo - ID: " + id.toString() + "  Msg: " + le.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 611 */     return retDistCo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLabelDistCoId(int labelId) {
/* 620 */     int retDistCoId = -1;
/* 621 */     Integer id = new Integer(labelId);
/*     */     
/* 623 */     if (Cache.cachedCorporateStructureHash.containsKey(id)) {
/*     */       
/*     */       try {
/* 626 */         Label label = (Label)Cache.cachedCorporateStructureHash.get(id);
/* 627 */         retDistCoId = label.getDistCoId();
/* 628 */       } catch (Exception le) {
/*     */         
/* 630 */         System.out.println("   getLabelDistCoId - ID: " + id.toString() + "  Msg: " + le.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 634 */     return retDistCoId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Hashtable<Integer, String> getDistCoNames() {
/* 644 */     names = new Hashtable();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 667 */       names = projectSearchSvcClient.DistributionCodGet();
/* 668 */       System.out.println("Distribution Co from WCF: " + names.size());
/* 669 */     } catch (Exception e) {
/* 670 */       System.out.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 674 */     return names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FormDropDownMenu BuildDistCoDropDown() {
/* 685 */     distCoHash = getDistCoNames();
/* 686 */     StringBuffer keys = new StringBuffer("-1");
/* 687 */     StringBuffer values = new StringBuffer("All");
/* 688 */     for (Enumeration e = distCoHash.keys(); e.hasMoreElements(); ) {
/*     */ 
/*     */       
/* 691 */       Integer key = (Integer)e.nextElement();
/*     */       
/* 693 */       String value = (String)distCoHash.get(key);
/*     */       
/* 695 */       keys.append("," + key.toString());
/* 696 */       values.append("," + value);
/*     */     } 
/* 698 */     return new FormDropDownMenu("DistCoNames", keys.toString(), values.toString(), false);
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
/*     */   public static Hashtable groupSelectionsForMexicoProductionByTypeConfigAndStreetDate(Vector selections) {
/* 711 */     Hashtable groupSelectionsByTypeAndStreetDate = new Hashtable();
/* 712 */     Vector finalVector = new Vector();
/*     */     
/* 714 */     if (selections == null) {
/* 715 */       return groupSelectionsByTypeAndStreetDate;
/*     */     }
/* 717 */     for (int i = 0; i < selections.size(); i++) {
/*     */       
/* 719 */       Selection sel = (Selection)selections.elementAt(i);
/* 720 */       if (sel != null) {
/*     */         
/* 722 */         sel = SelectionManager.getInstance().getSelectionAndSchedule(sel.getSelectionID());
/*     */ 
/*     */         
/* 725 */         String typeConfigString = "";
/* 726 */         String dayString = "";
/*     */         
/* 728 */         String status = (sel.getSelectionStatus() != null && sel.getSelectionStatus().getName() != null) ? 
/* 729 */           sel.getSelectionStatus().getName() : "";
/*     */         
/* 731 */         String typeString = (sel.getReleaseType() != null) ? sel.getReleaseType().getName() : "";
/* 732 */         String categoryString = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
/* 733 */         String configString = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/* 734 */         String labelName = "";
/* 735 */         boolean pAndD = sel.getPressAndDistribution();
/* 736 */         boolean intFlag = sel.getInternationalFlag();
/* 737 */         if (sel.getLabel() != null) {
/* 738 */           labelName = sel.getLabel().getName().trim();
/*     */         }
/* 740 */         String selProductCategory = (sel.getProductCategory() != null) ? sel.getProductCategory().getName() : "";
/*     */         
/* 742 */         int releasingFamilyId = sel.getReleaseFamilyId();
/*     */ 
/*     */         
/* 745 */         if (selProductCategory.equals("Special Products")) {
/* 746 */           typeConfigString = "Special Products";
/*     */ 
/*     */         
/*     */         }
/* 750 */         else if (typeString.startsWith("Promo") && 
/* 751 */           !labelName.equals("Polydor") && !pAndD && !intFlag) {
/*     */           
/* 753 */           typeConfigString = "Promos in Spanish";
/*     */ 
/*     */         
/*     */         }
/* 757 */         else if (typeString.startsWith("Promo") && 
/* 758 */           labelName.equalsIgnoreCase("Polydor") && !pAndD && !intFlag) {
/* 759 */           typeConfigString = "Promos in English";
/*     */ 
/*     */         
/*     */         }
/* 763 */         else if (typeString.startsWith("Commercial") && status.equalsIgnoreCase("TBS") && 
/* 764 */           !pAndD && !intFlag) {
/* 765 */           typeConfigString = "Pending Albums";
/*     */ 
/*     */         
/*     */         }
/* 769 */         else if (typeString.startsWith("Commercial") && (
/* 770 */           status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Closed") || status.equalsIgnoreCase("Cancel")) && 
/* 771 */           !labelName.equalsIgnoreCase("Decca") && !labelName.equalsIgnoreCase("Deutsche Grammophon") && !labelName.equalsIgnoreCase("Philips") && 
/* 772 */           !pAndD && !intFlag && (!configString.equals("DVV") || !labelName.equalsIgnoreCase("Polystar"))) {
/* 773 */           typeConfigString = "Commercial";
/*     */ 
/*     */         
/*     */         }
/* 777 */         else if (typeString.startsWith("Commercial") && (
/* 778 */           labelName.equalsIgnoreCase("Decca") || labelName.equalsIgnoreCase("Deutsche Grammophon") || labelName.equalsIgnoreCase("Philips")) && 
/* 779 */           !pAndD && !intFlag) {
/*     */           
/* 781 */           typeConfigString = "National Classical";
/*     */ 
/*     */         
/*     */         }
/* 785 */         else if (typeString.startsWith("Commercial") && intFlag) {
/*     */           
/* 787 */           typeConfigString = "Popular Imports";
/*     */ 
/*     */         
/*     */         }
/* 791 */         else if (typeString.startsWith("Commercial") && configString.equals("DVV") && 
/* 792 */           labelName.equalsIgnoreCase("Polystar") && !pAndD && !intFlag) {
/*     */ 
/*     */           
/* 795 */           typeConfigString = "DVDs";
/*     */ 
/*     */         
/*     */         }
/* 799 */         else if (typeString.startsWith("Promo") && pAndD) {
/* 800 */           typeConfigString = "Distributed Promos";
/*     */ 
/*     */         
/*     */         }
/* 804 */         else if (typeString.startsWith("Commercial") && pAndD) {
/* 805 */           typeConfigString = "Distributed Commercial";
/*     */         } 
/*     */         
/* 808 */         if (sel.getStreetDate() != null && !status.equals("TBS")) {
/*     */           
/* 810 */           SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
/* 811 */           dayString = MilestoneHelper.getFormatedDate(sel.getStreetDate());
/*     */         } else {
/*     */           
/* 814 */           dayString = "TBS";
/*     */         } 
/*     */ 
/*     */         
/* 818 */         String compositeName = String.valueOf(typeConfigString) + "|" + dayString;
/*     */ 
/*     */         
/* 821 */         Vector selectionsForDates = (Vector)groupSelectionsByTypeAndStreetDate.get(compositeName);
/* 822 */         if (selectionsForDates == null) {
/*     */ 
/*     */           
/* 825 */           selectionsForDates = new Vector();
/* 826 */           groupSelectionsByTypeAndStreetDate.put(compositeName, selectionsForDates);
/*     */         } 
/*     */ 
/*     */         
/* 830 */         selectionsForDates.addElement(sel);
/*     */       } 
/*     */     } 
/*     */     
/* 834 */     return groupSelectionsByTypeAndStreetDate;
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
/*     */   public static String getAbbrevAndDescription(String idVal, Vector descriptionVector) {
/* 847 */     String description = "";
/* 848 */     if (idVal != null && idVal.length() > 0) {
/* 849 */       LookupObject lookupObject = MilestoneHelper.getLookupObject(idVal, descriptionVector);
/* 850 */       if (lookupObject != null)
/* 851 */         description = String.valueOf(lookupObject.getAbbreviation()) + ":" + lookupObject.getName(); 
/*     */     } 
/* 853 */     return description;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Properties readConfigFile(String configFile) throws Exception {
/* 859 */     InputStream in = ClassLoader.getSystemResourceAsStream(configFile);
/*     */ 
/*     */     
/* 862 */     if (in == null) {
/* 863 */       in = new FileInputStream(configFile);
/*     */     }
/* 865 */     Properties defaultProps = new Properties();
/* 866 */     defaultProps.load(in);
/*     */     
/* 868 */     if (in != null) {
/* 869 */       in.close();
/*     */     }
/* 871 */     return defaultProps;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\MilestoneHelper_2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */