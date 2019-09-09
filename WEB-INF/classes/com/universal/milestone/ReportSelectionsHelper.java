/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.techempower.gemini.Context;
/*     */ import com.techempower.gemini.FormCheckBox;
/*     */ import com.techempower.gemini.FormDropDownMenu;
/*     */ import com.universal.milestone.Company;
/*     */ import com.universal.milestone.Form;
/*     */ import com.universal.milestone.MilestoneFormDropDownMenu;
/*     */ import com.universal.milestone.ReportSelectionsHelper;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.User;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class ReportSelectionsHelper
/*     */ {
/*     */   public ReportSelectionsHelper(Context context) {}
/*     */   
/*     */   public static void addReleasingFamilyLabelFamilySelectForAudit(String formName, Context context, StringBuffer query, Form form, String tableName) {
/*  49 */     User user = (User)context.getSession().getAttribute("user");
/*  50 */     Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
/*     */ 
/*     */     
/*  53 */     SelectionManager.addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*     */     
/*  55 */     MilestoneFormDropDownMenu famDD = (MilestoneFormDropDownMenu)form.getElement(formName);
/*  56 */     ArrayList familyAL = famDD.getStringValues();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     if (familyAL != null && !familyAL.get(0).equals("") && !familyAL.get(0).equals("0") && 
/*  63 */       !familyAL.get(0).equals("-1")) {
/*     */       
/*  65 */       query.append(" AND (");
/*  66 */       for (int i = 0; i < familyAL.size(); i++) {
/*     */ 
/*     */         
/*  69 */         String selValue = (String)familyAL.get(i);
/*  70 */         if (i > 0)
/*  71 */           query.append(" OR "); 
/*  72 */         query.append("(" + tableName + ".Release_Family_id = " + selValue);
/*     */         
/*  74 */         addLabelFamilySelectForAudit(selValue, relFamilyLabelFamilyHash, query, tableName);
/*  75 */         query.append(")");
/*     */       } 
/*  77 */       query.append(") ");
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/*  85 */       String[] valueList = famDD.getValueList();
/*  86 */       if (valueList != null && valueList.length > 0) {
/*     */         
/*  88 */         query.append(" AND (");
/*  89 */         for (int i = 0; i < valueList.length; i++) {
/*     */           
/*  91 */           if (i > 0)
/*  92 */             query.append(" OR "); 
/*  93 */           query.append("(" + tableName + ".Release_Family_id = " + valueList[i]);
/*     */           
/*  95 */           addLabelFamilySelectForAudit(valueList[i], relFamilyLabelFamilyHash, query, tableName);
/*  96 */           query.append(")");
/*     */         } 
/*  98 */         query.append(") ");
/*     */       } else {
/*     */         
/* 101 */         query.append(" AND (" + tableName + ".Release_Family_id = 0 and " + tableName + ".family_id = 0) ");
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addLabelFamilySelectForAudit(String relFamilyStr, Hashtable relFamilyLabelFamilyHash, StringBuffer query, String tableName) {
/* 121 */     if (relFamilyLabelFamilyHash.containsKey(relFamilyStr)) {
/*     */       
/* 123 */       Vector labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyStr);
/* 124 */       if (labelFamilies != null && labelFamilies.size() > 0) {
/* 125 */         for (int i = 0; i < labelFamilies.size(); i++) {
/*     */           
/* 127 */           if (i == 0) {
/* 128 */             query.append(" AND " + tableName + ".family_id in (" + (String)labelFamilies.get(i));
/*     */           } else {
/* 130 */             query.append("," + (String)labelFamilies.get(i));
/*     */           } 
/* 132 */         }  query.append(")");
/*     */       } 
/*     */     } else {
/*     */       
/* 136 */       query.append(" AND " + tableName + ".family_id in (" + relFamilyStr + ")");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean addMultSelectionToStringBuffer(StringBuffer query, String columnName, String[] stringArray, boolean isNumeric, String formElementName, Form reportForm, boolean isAddAll, boolean isAddAND) {
/* 155 */     boolean isSelected = false;
/* 156 */     if (stringArray != null && !stringArray[0].equalsIgnoreCase("") && 
/* 157 */       !stringArray[0].equalsIgnoreCase("0") && !stringArray[0].equalsIgnoreCase("-1") && 
/* 158 */       stringArray.length > 0) {
/*     */       
/* 160 */       isSelected = true;
/*     */       
/* 162 */       if (isAddAND)
/* 163 */         query.append(" AND "); 
/* 164 */       query.append(" " + columnName + " in (");
/* 165 */       for (int x = 0; x < stringArray.length; x++) {
/*     */ 
/*     */         
/* 168 */         if (x > 0) query.append(",");
/*     */         
/* 170 */         if (isNumeric) {
/* 171 */           query.append(stringArray[x].trim());
/*     */         } else {
/* 173 */           query.append("'" + stringArray[x].trim() + "'");
/*     */         } 
/* 175 */       }  query.append(") ");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (!isSelected && isAddAll) {
/*     */       
/* 182 */       String envStr = getValueListFromDropDownToQuery(formElementName, columnName, reportForm);
/* 183 */       if (!envStr.equals("")) {
/* 184 */         query.append(" " + envStr);
/*     */       }
/*     */     } 
/* 187 */     return isSelected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getValueListFromDropDownToQuery(String formElementName, String columnName, Form reportForm) {
/* 198 */     StringBuffer returnString = new StringBuffer("");
/*     */     
/* 200 */     FormDropDownMenu formDropDown = (FormDropDownMenu)reportForm.getElement(
/* 201 */         formElementName);
/* 202 */     if (formDropDown != null) {
/* 203 */       String[] valueList = formDropDown.getValueList();
/* 204 */       if (valueList != null && valueList.length > 0) {
/* 205 */         returnString.append(String.valueOf(columnName) + " in (");
/* 206 */         boolean firstPass = false;
/* 207 */         for (int i = 0; i < valueList.length; i++) {
/* 208 */           if (!valueList[i].equals("0") && !valueList[i].equals("")) {
/* 209 */             if (firstPass)
/* 210 */               returnString.append(","); 
/* 211 */             returnString.append(valueList[i]);
/* 212 */             firstPass = true;
/*     */           } 
/*     */         } 
/* 215 */         returnString.append(") ");
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     return returnString.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getMultiSelectionListValues(String multSelectListName, Form reportForm) {
/* 230 */     String[] returnArray = (String[])null;
/*     */     try {
/* 232 */       MilestoneFormDropDownMenu multFormDropDown = (MilestoneFormDropDownMenu)reportForm.getElement(multSelectListName);
/* 233 */       if (multFormDropDown != null) {
/* 234 */         ArrayList arrayList = multFormDropDown.getStringValues();
/* 235 */         if (arrayList != null) {
/* 236 */           returnArray = new String[arrayList.size()];
/* 237 */           returnArray = (String[])arrayList.toArray(returnArray);
/*     */         }
/*     */       
/*     */       } 
/* 241 */     } catch (Exception e) {
/* 242 */       e.printStackTrace();
/* 243 */       System.out.println("<<< getMultSelectionListBox vector exeception " + e.getMessage());
/*     */     } 
/* 245 */     return returnArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMultSelectFound(String dataElement, String[] multSelectArray) {
/* 255 */     boolean result = true;
/* 256 */     if (multSelectArray != null && !multSelectArray[0].equalsIgnoreCase("") && 
/* 257 */       !multSelectArray[0].equalsIgnoreCase("0") && 
/* 258 */       !multSelectArray[0].equalsIgnoreCase("-1")) {
/* 259 */       result = false;
/* 260 */       for (int e = 0; e < multSelectArray.length; e++) {
/*     */         
/* 262 */         ArrayList strList = getDelimitedStr(multSelectArray[e], ",");
/*     */         
/* 264 */         for (int i = 0; i < strList.size(); i++) {
/*     */ 
/*     */           
/* 267 */           if (strList.get(i).toString().equalsIgnoreCase(dataElement)) {
/* 268 */             result = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 273 */         if (result) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 278 */     return result;
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
/*     */   public static void addCompaniesToStringBuffer(StringBuffer query, String columnName, Vector companies) {
/* 290 */     if (companies != null) {
/* 291 */       query.append(" " + columnName);
/* 292 */       query.append(" in (");
/* 293 */       for (int x = 0; x < companies.size(); x++) {
/* 294 */         Company company = (Company)companies.get(x);
/*     */         
/* 296 */         if (company != null) {
/* 297 */           if (x > 0)
/* 298 */             query.append(","); 
/* 299 */           query.append(Integer.toString(company.getStructureID()));
/*     */         } 
/*     */       } 
/* 302 */       query.append(") ");
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
/*     */   
/*     */   public static boolean isValueInDropDownList(String value, String formElementName, Form reportForm) {
/* 317 */     boolean result = false;
/*     */     
/* 319 */     FormDropDownMenu formDropDown = (FormDropDownMenu)reportForm.getElement(
/* 320 */         formElementName);
/* 321 */     if (formDropDown != null) {
/* 322 */       String[] valueList = formDropDown.getValueList();
/* 323 */       if (valueList != null && valueList.length > 0) {
/* 324 */         for (int i = 0; i < valueList.length; i++) {
/* 325 */           if (value.equals(valueList[i])) {
/* 326 */             result = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 333 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList getDelimitedStr(String inputStr, String delimiter) {
/* 343 */     ArrayList returnList = new ArrayList();
/* 344 */     StringTokenizer tokenizer = new StringTokenizer(inputStr, delimiter, false);
/*     */ 
/*     */     
/* 347 */     if (!tokenizer.hasMoreTokens()) {
/*     */       
/* 349 */       returnList.add(inputStr);
/* 350 */       return returnList;
/*     */     } 
/*     */ 
/*     */     
/* 354 */     while (tokenizer.hasMoreTokens())
/*     */     {
/* 356 */       returnList.add(tokenizer.nextToken());
/*     */     }
/*     */ 
/*     */     
/* 360 */     return returnList;
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
/*     */   public static void addReleasingFamilyLabelFamilyMultSelect(String formName, Context context, StringBuffer query, Form form) {
/* 373 */     User user = (User)context.getSession().getAttribute("user");
/* 374 */     Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
/*     */ 
/*     */     
/* 377 */     SelectionManager.addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*     */     
/* 379 */     MilestoneFormDropDownMenu famDD = (MilestoneFormDropDownMenu)form.getElement(formName);
/* 380 */     ArrayList familyAL = famDD.getStringValues();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 386 */     if (familyAL != null && !familyAL.get(0).equals("") && !familyAL.get(0).equals("0") && 
/* 387 */       !familyAL.get(0).equals("-1")) {
/*     */       
/* 389 */       query.append(" AND (");
/* 390 */       for (int i = 0; i < familyAL.size(); i++) {
/*     */ 
/*     */         
/* 393 */         String selValue = (String)familyAL.get(i);
/* 394 */         if (i > 0)
/* 395 */           query.append(" OR "); 
/* 396 */         query.append("(Release_Family_id = " + selValue);
/*     */         
/* 398 */         SelectionManager.addLabelFamilySelect(selValue, relFamilyLabelFamilyHash, query);
/* 399 */         query.append(")");
/*     */       } 
/* 401 */       query.append(") ");
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 409 */       String[] valueList = famDD.getValueList();
/* 410 */       if (valueList != null && valueList.length > 0) {
/*     */         
/* 412 */         query.append(" AND (");
/* 413 */         for (int i = 0; i < valueList.length; i++) {
/*     */           
/* 415 */           if (i > 0)
/* 416 */             query.append(" OR "); 
/* 417 */           query.append("(Release_Family_id = " + valueList[i]);
/*     */           
/* 419 */           SelectionManager.addLabelFamilySelect(valueList[i], relFamilyLabelFamilyHash, query);
/* 420 */           query.append(")");
/*     */         } 
/* 422 */         query.append(") ");
/*     */       } else {
/*     */         
/* 425 */         query.append(" AND (Release_Family_id = 0 and family_id = 0) ");
/*     */       } 
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
/*     */   
/*     */   public static void deliverMultSelectValuesForRefresh(String formElementName, Form reportForm, Context context) {
/* 441 */     if (formElementName == null || formElementName.equals("")) {
/*     */       return;
/*     */     }
/* 444 */     StringBuffer result = new StringBuffer(100);
/*     */ 
/*     */     
/* 447 */     result.append("\n");
/* 448 */     result.append("var " + formElementName + "_ValuesArray = new Array();\n");
/*     */     
/* 450 */     ArrayList arrayL = new ArrayList();
/* 451 */     MilestoneFormDropDownMenu elementDD = (MilestoneFormDropDownMenu)reportForm.getElement("family");
/* 452 */     arrayL = elementDD.getStringValues();
/* 453 */     if (arrayL != null && arrayL.size() > 0)
/*     */     {
/* 455 */       for (int i = 0; i < arrayL.size(); i++) {
/*     */         
/* 457 */         String selValue = (String)arrayL.get(i);
/* 458 */         result.append(String.valueOf(formElementName) + "_ValuesArray[");
/* 459 */         result.append(i);
/* 460 */         result.append("] = " + selValue + ";\n");
/*     */       } 
/*     */     }
/*     */     
/* 464 */     context.putDelivery(String.valueOf(formElementName) + "_ValuesArray", result.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setReportHiddenValues(String formElementName, String hiddenName, Context context, Form reportForm) {
/* 470 */     MilestoneFormDropDownMenu formElem = (MilestoneFormDropDownMenu)reportForm.getElement(formElementName);
/* 471 */     if (formElem != null)
/*     */     {
/* 473 */       if (context.getRequest().getParameter(hiddenName) != null) {
/*     */         
/* 475 */         String valueStr = context.getRequest().getParameter(hiddenName);
/* 476 */         formElem.setMultSelections(getDelimitedStr(valueStr, "|"));
/*     */       } 
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
/*     */   public static void addStatusToSelect(Form reportForm, StringBuffer query) {
/* 490 */     boolean allChk = (reportForm.getElement("status_all") != null) ? ((FormCheckBox)reportForm.getElement("status_all")).isChecked() : 0;
/* 491 */     boolean actChk = (reportForm.getElement("status_active") != null) ? ((FormCheckBox)reportForm.getElement("status_active")).isChecked() : 0;
/* 492 */     boolean tbsChk = (reportForm.getElement("status_tbs") != null) ? ((FormCheckBox)reportForm.getElement("status_tbs")).isChecked() : 0;
/* 493 */     boolean clsChk = (reportForm.getElement("status_closed") != null) ? ((FormCheckBox)reportForm.getElement("status_closed")).isChecked() : 0;
/* 494 */     boolean cnlChk = (reportForm.getElement("status_cancelled") != null) ? ((FormCheckBox)reportForm.getElement("status_cancelled")).isChecked() : 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 499 */     if (allChk || (!actChk && !tbsChk && !clsChk && !cnlChk)) {
/*     */       return;
/*     */     }
/*     */     
/* 503 */     query.append(" AND (");
/*     */     
/* 505 */     boolean isAddOR = false;
/*     */ 
/*     */ 
/*     */     
/* 509 */     if (actChk) {
/*     */       
/* 511 */       query.append("header.status = 'ACTIVE'");
/* 512 */       isAddOR = true;
/*     */     } 
/*     */ 
/*     */     
/* 516 */     if (tbsChk) {
/*     */       
/* 518 */       if (isAddOR)
/* 519 */         query.append(" OR "); 
/* 520 */       query.append("header.status = 'TBS'");
/* 521 */       isAddOR = true;
/*     */     } 
/*     */ 
/*     */     
/* 525 */     if (clsChk) {
/*     */       
/* 527 */       if (isAddOR)
/* 528 */         query.append(" OR "); 
/* 529 */       query.append("header.status = 'CLOSED'");
/* 530 */       isAddOR = true;
/*     */     } 
/*     */ 
/*     */     
/* 534 */     if (cnlChk) {
/* 535 */       if (isAddOR)
/* 536 */         query.append(" OR "); 
/* 537 */       query.append("header.status = 'CANCEL'");
/* 538 */       isAddOR = true;
/*     */     } 
/*     */ 
/*     */     
/* 542 */     query.append(") ");
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
/*     */   public static boolean isStatusFound(Form reportForm, Selection selObj) {
/* 555 */     boolean allChk = (reportForm.getElement("status_all") != null) ? ((FormCheckBox)reportForm.getElement("status_all")).isChecked() : 0;
/* 556 */     boolean actChk = (reportForm.getElement("status_active") != null) ? ((FormCheckBox)reportForm.getElement("status_active")).isChecked() : 0;
/* 557 */     boolean tbsChk = (reportForm.getElement("status_tbs") != null) ? ((FormCheckBox)reportForm.getElement("status_tbs")).isChecked() : 0;
/* 558 */     boolean clsChk = (reportForm.getElement("status_closed") != null) ? ((FormCheckBox)reportForm.getElement("status_closed")).isChecked() : 0;
/* 559 */     boolean cnlChk = (reportForm.getElement("status_cancelled") != null) ? ((FormCheckBox)reportForm.getElement("status_cancelled")).isChecked() : 0;
/*     */ 
/*     */     
/* 562 */     if (allChk) {
/* 563 */       return true;
/*     */     }
/*     */     
/* 566 */     String selectionStatus = SelectionManager.getLookupObjectValue(selObj.getSelectionStatus()).trim();
/*     */     
/* 568 */     boolean returnVar = false;
/*     */ 
/*     */     
/* 571 */     if (actChk && selectionStatus.equalsIgnoreCase("ACTIVE")) {
/* 572 */       returnVar = true;
/*     */     }
/*     */     
/* 575 */     if (tbsChk && selectionStatus.equalsIgnoreCase("TBS")) {
/* 576 */       returnVar = true;
/*     */     }
/*     */     
/* 579 */     if (clsChk && selectionStatus.equalsIgnoreCase("CLOSED")) {
/* 580 */       returnVar = true;
/*     */     }
/*     */     
/* 583 */     if (cnlChk && selectionStatus.equalsIgnoreCase("CANCEL")) {
/* 584 */       returnVar = true;
/*     */     }
/*     */     
/* 587 */     return returnVar;
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportSelectionsHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */