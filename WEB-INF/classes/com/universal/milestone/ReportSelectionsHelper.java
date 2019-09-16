package WEB-INF.classes.com.universal.milestone;

import com.techempower.gemini.Context;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormDropDownMenu;
import com.universal.milestone.Company;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneFormDropDownMenu;
import com.universal.milestone.ReportSelectionsHelper;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.User;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class ReportSelectionsHelper {
  public ReportSelectionsHelper(Context context) {}
  
  public static void addReleasingFamilyLabelFamilySelectForAudit(String formName, Context context, StringBuffer query, Form form, String tableName) {
    User user = (User)context.getSession().getAttribute("user");
    Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
    SelectionManager.addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
    MilestoneFormDropDownMenu famDD = (MilestoneFormDropDownMenu)form.getElement(formName);
    ArrayList familyAL = famDD.getStringValues();
    if (familyAL != null && !familyAL.get(0).equals("") && !familyAL.get(0).equals("0") && 
      !familyAL.get(0).equals("-1")) {
      query.append(" AND (");
      for (int i = 0; i < familyAL.size(); i++) {
        String selValue = (String)familyAL.get(i);
        if (i > 0)
          query.append(" OR "); 
        query.append("(" + tableName + ".Release_Family_id = " + selValue);
        addLabelFamilySelectForAudit(selValue, relFamilyLabelFamilyHash, query, tableName);
        query.append(")");
      } 
      query.append(") ");
    } else {
      String[] valueList = famDD.getValueList();
      if (valueList != null && valueList.length > 0) {
        query.append(" AND (");
        for (int i = 0; i < valueList.length; i++) {
          if (i > 0)
            query.append(" OR "); 
          query.append("(" + tableName + ".Release_Family_id = " + valueList[i]);
          addLabelFamilySelectForAudit(valueList[i], relFamilyLabelFamilyHash, query, tableName);
          query.append(")");
        } 
        query.append(") ");
      } else {
        query.append(" AND (" + tableName + ".Release_Family_id = 0 and " + tableName + ".family_id = 0) ");
      } 
    } 
  }
  
  public static void addLabelFamilySelectForAudit(String relFamilyStr, Hashtable relFamilyLabelFamilyHash, StringBuffer query, String tableName) {
    if (relFamilyLabelFamilyHash.containsKey(relFamilyStr)) {
      Vector labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyStr);
      if (labelFamilies != null && labelFamilies.size() > 0) {
        for (int i = 0; i < labelFamilies.size(); i++) {
          if (i == 0) {
            query.append(" AND " + tableName + ".family_id in (" + (String)labelFamilies.get(i));
          } else {
            query.append("," + (String)labelFamilies.get(i));
          } 
        } 
        query.append(")");
      } 
    } else {
      query.append(" AND " + tableName + ".family_id in (" + relFamilyStr + ")");
    } 
  }
  
  public static boolean addMultSelectionToStringBuffer(StringBuffer query, String columnName, String[] stringArray, boolean isNumeric, String formElementName, Form reportForm, boolean isAddAll, boolean isAddAND) {
    boolean isSelected = false;
    if (stringArray != null && !stringArray[0].equalsIgnoreCase("") && 
      !stringArray[0].equalsIgnoreCase("0") && !stringArray[0].equalsIgnoreCase("-1") && 
      stringArray.length > 0) {
      isSelected = true;
      if (isAddAND)
        query.append(" AND "); 
      query.append(" " + columnName + " in (");
      for (int x = 0; x < stringArray.length; x++) {
        if (x > 0)
          query.append(","); 
        if (isNumeric) {
          query.append(stringArray[x].trim());
        } else {
          query.append("'" + stringArray[x].trim() + "'");
        } 
      } 
      query.append(") ");
    } 
    if (!isSelected && isAddAll) {
      String envStr = getValueListFromDropDownToQuery(formElementName, columnName, reportForm);
      if (!envStr.equals(""))
        query.append(" " + envStr); 
    } 
    return isSelected;
  }
  
  public static String getValueListFromDropDownToQuery(String formElementName, String columnName, Form reportForm) {
    StringBuffer returnString = new StringBuffer("");
    FormDropDownMenu formDropDown = (FormDropDownMenu)reportForm.getElement(
        formElementName);
    if (formDropDown != null) {
      String[] valueList = formDropDown.getValueList();
      if (valueList != null && valueList.length > 0) {
        returnString.append(String.valueOf(columnName) + " in (");
        boolean firstPass = false;
        for (int i = 0; i < valueList.length; i++) {
          if (!valueList[i].equals("0") && !valueList[i].equals("")) {
            if (firstPass)
              returnString.append(","); 
            returnString.append(valueList[i]);
            firstPass = true;
          } 
        } 
        returnString.append(") ");
      } 
    } 
    return returnString.toString();
  }
  
  public static String[] getMultiSelectionListValues(String multSelectListName, Form reportForm) {
    String[] returnArray = (String[])null;
    try {
      MilestoneFormDropDownMenu multFormDropDown = (MilestoneFormDropDownMenu)reportForm.getElement(multSelectListName);
      if (multFormDropDown != null) {
        ArrayList arrayList = multFormDropDown.getStringValues();
        if (arrayList != null) {
          returnArray = new String[arrayList.size()];
          returnArray = (String[])arrayList.toArray(returnArray);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("<<< getMultSelectionListBox vector exeception " + e.getMessage());
    } 
    return returnArray;
  }
  
  public static boolean isMultSelectFound(String dataElement, String[] multSelectArray) {
    boolean result = true;
    if (multSelectArray != null && !multSelectArray[0].equalsIgnoreCase("") && 
      !multSelectArray[0].equalsIgnoreCase("0") && 
      !multSelectArray[0].equalsIgnoreCase("-1")) {
      result = false;
      for (int e = 0; e < multSelectArray.length; e++) {
        ArrayList strList = getDelimitedStr(multSelectArray[e], ",");
        for (int i = 0; i < strList.size(); i++) {
          if (strList.get(i).toString().equalsIgnoreCase(dataElement)) {
            result = true;
            break;
          } 
        } 
        if (result)
          break; 
      } 
    } 
    return result;
  }
  
  public static void addCompaniesToStringBuffer(StringBuffer query, String columnName, Vector companies) {
    if (companies != null) {
      query.append(" " + columnName);
      query.append(" in (");
      for (int x = 0; x < companies.size(); x++) {
        Company company = (Company)companies.get(x);
        if (company != null) {
          if (x > 0)
            query.append(","); 
          query.append(Integer.toString(company.getStructureID()));
        } 
      } 
      query.append(") ");
    } 
  }
  
  public static boolean isValueInDropDownList(String value, String formElementName, Form reportForm) {
    boolean result = false;
    FormDropDownMenu formDropDown = (FormDropDownMenu)reportForm.getElement(
        formElementName);
    if (formDropDown != null) {
      String[] valueList = formDropDown.getValueList();
      if (valueList != null && valueList.length > 0)
        for (int i = 0; i < valueList.length; i++) {
          if (value.equals(valueList[i])) {
            result = true;
            break;
          } 
        }  
    } 
    return result;
  }
  
  public static ArrayList getDelimitedStr(String inputStr, String delimiter) {
    ArrayList returnList = new ArrayList();
    StringTokenizer tokenizer = new StringTokenizer(inputStr, delimiter, false);
    if (!tokenizer.hasMoreTokens()) {
      returnList.add(inputStr);
      return returnList;
    } 
    while (tokenizer.hasMoreTokens())
      returnList.add(tokenizer.nextToken()); 
    return returnList;
  }
  
  public static void addReleasingFamilyLabelFamilyMultSelect(String formName, Context context, StringBuffer query, Form form) {
    User user = (User)context.getSession().getAttribute("user");
    Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
    SelectionManager.addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
    MilestoneFormDropDownMenu famDD = (MilestoneFormDropDownMenu)form.getElement(formName);
    ArrayList familyAL = famDD.getStringValues();
    if (familyAL != null && !familyAL.get(0).equals("") && !familyAL.get(0).equals("0") && 
      !familyAL.get(0).equals("-1")) {
      query.append(" AND (");
      for (int i = 0; i < familyAL.size(); i++) {
        String selValue = (String)familyAL.get(i);
        if (i > 0)
          query.append(" OR "); 
        query.append("(Release_Family_id = " + selValue);
        SelectionManager.addLabelFamilySelect(selValue, relFamilyLabelFamilyHash, query);
        query.append(")");
      } 
      query.append(") ");
    } else {
      String[] valueList = famDD.getValueList();
      if (valueList != null && valueList.length > 0) {
        query.append(" AND (");
        for (int i = 0; i < valueList.length; i++) {
          if (i > 0)
            query.append(" OR "); 
          query.append("(Release_Family_id = " + valueList[i]);
          SelectionManager.addLabelFamilySelect(valueList[i], relFamilyLabelFamilyHash, query);
          query.append(")");
        } 
        query.append(") ");
      } else {
        query.append(" AND (Release_Family_id = 0 and family_id = 0) ");
      } 
    } 
  }
  
  public static void deliverMultSelectValuesForRefresh(String formElementName, Form reportForm, Context context) {
    if (formElementName == null || formElementName.equals(""))
      return; 
    StringBuffer result = new StringBuffer(100);
    result.append("\n");
    result.append("var " + formElementName + "_ValuesArray = new Array();\n");
    ArrayList arrayL = new ArrayList();
    MilestoneFormDropDownMenu elementDD = (MilestoneFormDropDownMenu)reportForm.getElement("family");
    arrayL = elementDD.getStringValues();
    if (arrayL != null && arrayL.size() > 0)
      for (int i = 0; i < arrayL.size(); i++) {
        String selValue = (String)arrayL.get(i);
        result.append(String.valueOf(formElementName) + "_ValuesArray[");
        result.append(i);
        result.append("] = " + selValue + ";\n");
      }  
    context.putDelivery(String.valueOf(formElementName) + "_ValuesArray", result.toString());
  }
  
  public static void setReportHiddenValues(String formElementName, String hiddenName, Context context, Form reportForm) {
    MilestoneFormDropDownMenu formElem = (MilestoneFormDropDownMenu)reportForm.getElement(formElementName);
    if (formElem != null)
      if (context.getRequest().getParameter(hiddenName) != null) {
        String valueStr = context.getRequest().getParameter(hiddenName);
        formElem.setMultSelections(getDelimitedStr(valueStr, "|"));
      }  
  }
  
  public static void addStatusToSelect(Form reportForm, StringBuffer query) {
    boolean allChk = (reportForm.getElement("status_all") != null) ? ((FormCheckBox)reportForm.getElement("status_all")).isChecked() : 0;
    boolean actChk = (reportForm.getElement("status_active") != null) ? ((FormCheckBox)reportForm.getElement("status_active")).isChecked() : 0;
    boolean tbsChk = (reportForm.getElement("status_tbs") != null) ? ((FormCheckBox)reportForm.getElement("status_tbs")).isChecked() : 0;
    boolean clsChk = (reportForm.getElement("status_closed") != null) ? ((FormCheckBox)reportForm.getElement("status_closed")).isChecked() : 0;
    boolean cnlChk = (reportForm.getElement("status_cancelled") != null) ? ((FormCheckBox)reportForm.getElement("status_cancelled")).isChecked() : 0;
    if (allChk || (!actChk && !tbsChk && !clsChk && !cnlChk))
      return; 
    query.append(" AND (");
    boolean isAddOR = false;
    if (actChk) {
      query.append("header.status = 'ACTIVE'");
      isAddOR = true;
    } 
    if (tbsChk) {
      if (isAddOR)
        query.append(" OR "); 
      query.append("header.status = 'TBS'");
      isAddOR = true;
    } 
    if (clsChk) {
      if (isAddOR)
        query.append(" OR "); 
      query.append("header.status = 'CLOSED'");
      isAddOR = true;
    } 
    if (cnlChk) {
      if (isAddOR)
        query.append(" OR "); 
      query.append("header.status = 'CANCEL'");
      isAddOR = true;
    } 
    query.append(") ");
  }
  
  public static boolean isStatusFound(Form reportForm, Selection selObj) {
    boolean allChk = (reportForm.getElement("status_all") != null) ? ((FormCheckBox)reportForm.getElement("status_all")).isChecked() : 0;
    boolean actChk = (reportForm.getElement("status_active") != null) ? ((FormCheckBox)reportForm.getElement("status_active")).isChecked() : 0;
    boolean tbsChk = (reportForm.getElement("status_tbs") != null) ? ((FormCheckBox)reportForm.getElement("status_tbs")).isChecked() : 0;
    boolean clsChk = (reportForm.getElement("status_closed") != null) ? ((FormCheckBox)reportForm.getElement("status_closed")).isChecked() : 0;
    boolean cnlChk = (reportForm.getElement("status_cancelled") != null) ? ((FormCheckBox)reportForm.getElement("status_cancelled")).isChecked() : 0;
    if (allChk)
      return true; 
    String selectionStatus = SelectionManager.getLookupObjectValue(selObj.getSelectionStatus()).trim();
    boolean returnVar = false;
    if (actChk && selectionStatus.equalsIgnoreCase("ACTIVE"))
      returnVar = true; 
    if (tbsChk && selectionStatus.equalsIgnoreCase("TBS"))
      returnVar = true; 
    if (clsChk && selectionStatus.equalsIgnoreCase("CLOSED"))
      returnVar = true; 
    if (cnlChk && selectionStatus.equalsIgnoreCase("CANCEL"))
      returnVar = true; 
    return returnVar;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\ReportSelectionsHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */