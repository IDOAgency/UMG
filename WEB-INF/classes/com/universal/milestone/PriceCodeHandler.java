package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.gemini.Context;
import com.techempower.gemini.Dispatcher;
import com.techempower.gemini.FormCheckBox;
import com.techempower.gemini.FormHidden;
import com.techempower.gemini.FormRadioButtonGroup;
import com.techempower.gemini.FormTextField;
import com.techempower.gemini.FormValidation;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Cache;
import com.universal.milestone.Form;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneMessage;
import com.universal.milestone.MilestoneSecurity;
import com.universal.milestone.Notepad;
import com.universal.milestone.PriceCode;
import com.universal.milestone.PriceCodeHandler;
import com.universal.milestone.PriceCodeManager;
import com.universal.milestone.SecureHandler;
import com.universal.milestone.User;
import java.util.Vector;

public class PriceCodeHandler extends SecureHandler {
  public static final String COMPONENT_CODE = "hPrc";
  
  public GeminiApplication application;
  
  public ComponentLog log;
  
  public PriceCodeHandler(GeminiApplication application) {
    this.application = application;
    this.log = application.getLog("hPrc");
  }
  
  public String getDescription() { return "Price Code Handler"; }
  
  public boolean acceptRequest(Dispatcher dispatcher, Context context, String command) {
    if (super.acceptRequest(dispatcher, context, command))
      if (command.startsWith("price-code"))
        return handleRequest(dispatcher, context, command);  
    return false;
  }
  
  public boolean handleRequest(Dispatcher dispatcher, Context context, String command) {
    if (command.equalsIgnoreCase("price-code-search"))
      search(dispatcher, context, command); 
    if (command.equalsIgnoreCase("price-code-sort")) {
      sort(dispatcher, context);
    } else if (command.equalsIgnoreCase("price-code-editor")) {
      edit(context);
    } else if (command.equalsIgnoreCase("price-code-edit-save")) {
      save(context);
    } else if (command.equalsIgnoreCase("price-code-edit-save-new")) {
      saveNew(context);
    } else if (command.equalsIgnoreCase("price-code-edit-delete")) {
      delete(context);
    } else if (command.equalsIgnoreCase("price-code-edit-new")) {
      newForm(context);
    } 
    return true;
  }
  
  private boolean search(Dispatcher dispatcher, Context context, String command) {
    Notepad notepad = MilestoneHelper.getNotepadFromSession(16, context);
    notepad.setAllContents(null);
    notepad.setSelected(null);
    PriceCodeManager.getInstance().setNotepadQuery(context, MilestoneSecurity.getUser(context).getUserId(), notepad);
    dispatcher.redispatch(context, "price-code-editor");
    return true;
  }
  
  private boolean sort(Dispatcher dispatcher, Context context) {
    int sort = Integer.parseInt(context.getParameter("OrderBy"));
    Notepad notepad = MilestoneHelper.getNotepadFromSession(16, context);
    if (notepad.getSearchQuery() == null || notepad.getSearchQuery().equals("")) {
      PriceCodeManager.getInstance();
      notepad.setSearchQuery("SELECT sell_code, retail_code, description, isDigital FROM vi_Price_Code");
    } 
    notepad.setOrderBy(" ORDER BY " + MilestoneConstants.SORT_PRICE_CODE[sort]);
    notepad.setAllContents(null);
    notepad = getPriceCodeNotepad(context, MilestoneSecurity.getUser(context).getUserId());
    notepad.goToSelectedPage();
    dispatcher.redispatch(context, "price-code-editor");
    return true;
  }
  
  private boolean edit(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Vector contents = new Vector();
    Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    PriceCode priceCode = MilestoneHelper.getScreenPriceCode(context);
    if (priceCode != null) {
      Form form = null;
      if (priceCode != null) {
        form = buildForm(context, priceCode);
      } else {
        form = buildNewForm(context);
      } 
      context.putDelivery("Form", form);
      return context.includeJSP("price-code-editor.jsp");
    } 
    return goToBlank(context);
  }
  
  protected Form buildForm(Context context, PriceCode priceCode) {
    Form priceCodeForm = new Form(this.application, "priceCodeForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    String sellCode = "";
    sellCode = priceCode.getSellCode();
    FormTextField SellCode = new FormTextField("SellCode", sellCode, true, 10, 10);
    SellCode.setTabIndex(1);
    priceCodeForm.addElement(SellCode);
    String retailCode = "";
    retailCode = priceCode.getRetailCode();
    FormTextField RetailCode = new FormTextField("RetailCode", retailCode, false, 8, 5);
    RetailCode.setTabIndex(2);
    priceCodeForm.addElement(RetailCode);
    int units = 0;
    units = priceCode.getUnits();
    FormTextField Units = new FormTextField("Units", String.valueOf(units), false, 10, 10);
    Units.addFormEvent("onBlur", "JavaScript:checkField( this )");
    Units.setTabIndex(3);
    priceCodeForm.addElement(Units);
    String pricepoint = "";
    pricepoint = priceCode.getPricePoint();
    FormTextField PricePoint = new FormTextField("PricePoint", pricepoint, false, 5, 5);
    PricePoint.addFormEvent("onBlur", "JavaScript:checkField( this )");
    PricePoint.setTabIndex(4);
    priceCodeForm.addElement(PricePoint);
    String description = "";
    description = priceCode.getDescription();
    FormTextField Description = new FormTextField("Description", description, false, 50, 50);
    Description.addFormEvent("onBlur", "JavaScript:checkField( this )");
    Description.setTabIndex(5);
    priceCodeForm.addElement(Description);
    float unitcost = 0.0F;
    unitcost = priceCode.getUnitCost();
    FormTextField UnitCost = new FormTextField("UnitCost", String.valueOf(unitcost), false, 10, 10);
    UnitCost.addFormEvent("onBlur", "JavaScript:checkField( this )");
    UnitCost.setTabIndex(6);
    priceCodeForm.addElement(UnitCost);
    float totalcost = 0.0F;
    totalcost = priceCode.getTotalCost();
    FormTextField TotalCost = new FormTextField("TotalCost", String.valueOf(totalcost), false, 10, 10);
    TotalCost.addFormEvent("onBlur", "JavaScript:checkField( this )");
    TotalCost.setTabIndex(7);
    priceCodeForm.addElement(TotalCost);
    FormCheckBox IsDigital = new FormCheckBox("IsDigital", "", false, priceCode.getIsDigital());
    IsDigital.setTabIndex(8);
    priceCodeForm.addElement(IsDigital);
    FormTextField SellCodeSearch = new FormTextField("SellCodeSearch", "", false, 5);
    SellCodeSearch.setId("SellCodeSearch");
    priceCodeForm.addElement(SellCodeSearch);
    FormTextField RetailCodeSearch = new FormTextField("RetailCodeSearch", "", false, 5);
    RetailCodeSearch.setId("RetailCodeSearch");
    priceCodeForm.addElement(RetailCodeSearch);
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 5);
    DescriptionSearch.setId("DescriptionSearch");
    priceCodeForm.addElement(DescriptionSearch);
    String[] dvalues = new String[3];
    dvalues[0] = "0";
    dvalues[1] = "1";
    dvalues[2] = "2";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    FormRadioButtonGroup IsDigitalSearch = new FormRadioButtonGroup("IsDigitalSearch", "Both", dvalues, dlabels, false);
    IsDigitalSearch.setId("IsDigitalSearch");
    priceCodeForm.addElement(IsDigitalSearch);
    priceCodeForm.addElement(new FormHidden("cmd", "price-code-editor", true));
    priceCodeForm.addElement(new FormHidden("OrderBy", "", true));
    context.putSessionValue("priceCode", priceCode);
    if (context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE")); 
    return priceCodeForm;
  }
  
  private boolean save(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Vector contents = new Vector();
    Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    PriceCode priceCode = MilestoneHelper.getScreenPriceCode(context);
    Form form = buildForm(context, priceCode);
    if (PriceCodeManager.getInstance().isTimestampValid(priceCode)) {
      form.setValues(context);
      String retailString = form.getStringValue("RetailCode");
      int unitsInt = 0;
      try {
        unitsInt = Integer.parseInt(form.getStringValue("Units"));
      } catch (Exception e) {
        System.out.println("Exception occurres while converting Units.");
      } 
      String pricepointString = form.getStringValue("PricePoint");
      String descriptionString = form.getStringValue("Description");
      float unitcostInt = 0.0F;
      try {
        unitcostInt = Float.parseFloat(form.getStringValue("UnitCost"));
      } catch (Exception e) {
        System.out.println("Exception occurres while converting Unit Cost.");
      } 
      float totalcostInt = 0.0F;
      try {
        totalcostInt = Float.parseFloat(form.getStringValue("TotalCost"));
      } catch (Exception e) {
        System.out.println("Exception occurres while converting Total Cost.");
      } 
      priceCode.setRetailCode(retailString);
      priceCode.setUnits(unitsInt);
      priceCode.setPricePoint(pricepointString);
      priceCode.setDescription(descriptionString);
      priceCode.setUnitCost(unitcostInt);
      priceCode.setTotalCost(totalcostInt);
      priceCode.setIsDigital(((FormCheckBox)form.getElement("IsDigital")).isChecked());
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          PriceCode savePriceCode = PriceCodeManager.getInstance().savePriceCode(priceCode, user.getUserId());
          notepad.setAllContents(null);
          Cache.flushSellCode();
          notepad = getPriceCodeNotepad(context, user.getUserId());
          notepad.goToSelectedPage();
          notepad.setSelected(savePriceCode);
          priceCode = (PriceCode)notepad.validateSelected();
          if (context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE") != null)
            context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE")); 
          context.putSessionValue("PriceCode", priceCode);
          if (priceCode == null)
            return goToBlank(context); 
          form = buildForm(context, priceCode);
        } else {
          context.putDelivery("FormValidation", formValidation);
        } 
      } 
      form.addElement(new FormHidden("OrderBy", "", true));
      context.putDelivery("Form", form);
      return edit(context);
    } 
    context.putDelivery("AlertMessage", "The record is unavailable for editing at this time.  Please refresh your screen and edit it at a later time.");
    context.putDelivery("Form", form);
    return context.includeJSP("price-code-editor.jsp");
  }
  
  private boolean delete(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Vector contents = new Vector();
    Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    PriceCode priceCode = MilestoneHelper.getScreenPriceCode(context);
    if (priceCode != null) {
      PriceCodeManager.getInstance().deletePriceCode(priceCode, user.getUserId());
      notepad.setAllContents(null);
      Cache.flushSellCode();
      notepad = getPriceCodeNotepad(context, user.getUserId());
      notepad.setSelected(null);
    } 
    return edit(context);
  }
  
  private boolean newForm(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    Form form = buildNewForm(context);
    context.putDelivery("Form", form);
    return context.includeJSP("price-code-editor.jsp");
  }
  
  private boolean saveNew(Context context) {
    User user = MilestoneSecurity.getUser(context);
    Notepad notepad = getPriceCodeNotepad(context, user.getUserId());
    MilestoneHelper.putNotepadIntoSession(notepad, context);
    PriceCode priceCode = new PriceCode();
    Form form = buildNewForm(context);
    form.setValues(context);
    String sellString = form.getStringValue("SellCode");
    String retailString = form.getStringValue("RetailCode");
    int unitsInt = 0;
    try {
      unitsInt = Integer.parseInt(form.getStringValue("Units"));
    } catch (Exception e) {
      System.out.println("Exception occurres while converting Units.");
    } 
    String pricepointString = form.getStringValue("PricePoint");
    String descriptionString = form.getStringValue("Description");
    float unitcostInt = 0.0F;
    try {
      unitcostInt = Float.parseFloat(form.getStringValue("UnitCost"));
    } catch (Exception e) {
      System.out.println("Exception occurres while converting Unit Cost.");
    } 
    float totalcostInt = 0.0F;
    try {
      totalcostInt = Float.parseFloat(form.getStringValue("TotalCost"));
    } catch (Exception e) {
      System.out.println("Exception occurres while converting Total Cost.");
    } 
    priceCode.setSellCode(sellString);
    priceCode.setRetailCode(retailString);
    priceCode.setUnits(unitsInt);
    priceCode.setPricePoint(pricepointString);
    priceCode.setDescription(descriptionString);
    priceCode.setUnitCost(unitcostInt);
    priceCode.setTotalCost(totalcostInt);
    priceCode.setIsDigital(((FormCheckBox)form.getElement("IsDigital")).isChecked());
    if (!PriceCodeManager.getInstance().isDuplicate(priceCode)) {
      if (!form.isUnchanged()) {
        FormValidation formValidation = form.validate();
        if (formValidation.isGood()) {
          PriceCode saveNewPriceCode = PriceCodeManager.getInstance().saveNewPriceCode(priceCode, user.getUserId());
          context.putSessionValue("PriceCode", saveNewPriceCode);
          if (notepad.getAllContents() == null || notepad.getAllContents().size() < 1)
            notepad.setSearchQuery(""); 
          notepad.setAllContents(null);
          notepad.newSelectedReset();
          Cache.flushSellCode();
          notepad = getPriceCodeNotepad(context, user.getUserId());
          notepad.setSelected(saveNewPriceCode);
        } else {
          context.putDelivery("FormValidation", formValidation);
          form.addElement(new FormHidden("OrderBy", "", true));
          context.putDelivery("Form", form);
          return context.includeJSP("price-code-editor.jsp");
        } 
      } 
    } else {
      context.putDelivery("AlertMessage", 
          MilestoneMessage.getMessage(5, 
            
            new String[] { "Price Code", priceCode.getSellCode() }));
    } 
    return edit(context);
  }
  
  protected Form buildNewForm(Context context) {
    Form priceCodeForm = new Form(this.application, "priceCodeForm", 
        this.application.getInfrastructure().getServletURL(), "POST");
    User user = MilestoneSecurity.getUser(context);
    FormTextField SellCode = new FormTextField("SellCode", "", true, 10, 10);
    SellCode.setTabIndex(1);
    priceCodeForm.addElement(SellCode);
    FormTextField RetailCode = new FormTextField("RetailCode", "", false, 8, 5);
    RetailCode.setTabIndex(2);
    priceCodeForm.addElement(RetailCode);
    FormTextField Units = new FormTextField("Units", "", false, 10, 10);
    Units.addFormEvent("onBlur", "JavaScript:checkField( this )");
    Units.setTabIndex(3);
    priceCodeForm.addElement(Units);
    FormTextField PricePoint = new FormTextField("PricePoint", "", false, 5, 5);
    PricePoint.addFormEvent("onBlur", "JavaScript:checkField( this )");
    PricePoint.setTabIndex(4);
    priceCodeForm.addElement(PricePoint);
    FormTextField Description = new FormTextField("Description", "", false, 50, 50);
    Description.addFormEvent("onBlur", "JavaScript:checkField( this )");
    Description.setTabIndex(5);
    priceCodeForm.addElement(Description);
    FormTextField UnitCost = new FormTextField("UnitCost", "0.00", false, 10, 10);
    UnitCost.addFormEvent("onBlur", "JavaScript:checkField( this )");
    UnitCost.setTabIndex(6);
    priceCodeForm.addElement(UnitCost);
    FormTextField TotalCost = new FormTextField("TotalCost", "0.00", false, 10, 10);
    TotalCost.addFormEvent("onBlur", "JavaScript:checkField( this )");
    TotalCost.setTabIndex(7);
    priceCodeForm.addElement(TotalCost);
    FormCheckBox IsDigital = new FormCheckBox("IsDigital", "", false, false);
    IsDigital.setTabIndex(8);
    priceCodeForm.addElement(IsDigital);
    FormTextField SellCodeSearch = new FormTextField("SellCodeSearch", "", false, 5);
    SellCodeSearch.setId("SellCodeSearch");
    priceCodeForm.addElement(SellCodeSearch);
    FormTextField RetailCodeSearch = new FormTextField("RetailCodeSearch", "", false, 5);
    RetailCodeSearch.setId("RetailCodeSearch");
    priceCodeForm.addElement(RetailCodeSearch);
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", false, 5);
    DescriptionSearch.setId("DescriptionSearch");
    priceCodeForm.addElement(DescriptionSearch);
    String[] dvalues = new String[3];
    dvalues[0] = "0";
    dvalues[1] = "1";
    dvalues[2] = "2";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    FormRadioButtonGroup IsDigitalSearch = new FormRadioButtonGroup("IsDigitalSearch", "Both", dvalues, dlabels, false);
    IsDigitalSearch.setId("IsDigitalSearch");
    priceCodeForm.addElement(IsDigitalSearch);
    priceCodeForm.addElement(new FormHidden("cmd", "price-code-edit-new", true));
    priceCodeForm.addElement(new FormHidden("OrderBy", "", true));
    if (context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE") != null)
      context.putDelivery("isNotePadVisible", (Boolean)context.getSessionValue("NOTEPAD_PRICECODE_VISIBLE")); 
    return priceCodeForm;
  }
  
  public Notepad getPriceCodeNotepad(Context context, int userId) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(16, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(16, context);
      if (notepad.getAllContents() == null) {
        contents = PriceCodeManager.getInstance().getPriceCodeNotepadList(userId, notepad);
        notepad.setAllContents(contents);
      } 
      return notepad;
    } 
    String[] columnNames = { "Sell Code", "Ret. Code", "Description" };
    contents = PriceCodeManager.getInstance().getPriceCodeNotepadList(userId, null);
    return new Notepad(contents, 0, 15, "Price Code", 16, columnNames);
  }
  
  private boolean goToBlank(Context context) {
    context.putDelivery("isNotePadVisible", new Boolean(MilestoneHelper.getNotePadVisiblitiy(16, context)));
    Form form = new Form(this.application, "form", this.application.getInfrastructure().getServletURL(), "Post");
    form.addElement(new FormHidden("cmd", "price-code-editor"));
    form.addElement(new FormHidden("OrderBy", "", true));
    FormTextField SellCodeSearch = new FormTextField("SellCodeSearch", "", true, 5);
    SellCodeSearch.setId("SellCodeSearch");
    form.addElement(SellCodeSearch);
    FormTextField RetailCodeSearch = new FormTextField("RetailCodeSearch", "", true, 5);
    RetailCodeSearch.setId("RetailCodeSearch");
    form.addElement(RetailCodeSearch);
    FormTextField DescriptionSearch = new FormTextField("DescriptionSearch", "", true, 5);
    DescriptionSearch.setId("DescriptionSearch");
    form.addElement(DescriptionSearch);
    String[] dvalues = new String[3];
    dvalues[0] = "0";
    dvalues[1] = "1";
    dvalues[2] = "2";
    String[] dlabels = new String[3];
    dlabels[0] = "Physical";
    dlabels[1] = "Digital";
    dlabels[2] = "Both";
    FormRadioButtonGroup IsDigitalSearch = new FormRadioButtonGroup("IsDigitalSearch", "Both", dvalues, dlabels, false);
    IsDigitalSearch.setId("IsDigitalSearch");
    form.addElement(IsDigitalSearch);
    context.putDelivery("Form", form);
    return context.includeJSP("blank-price-code-editor.jsp");
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Local.war!\WEB-INF\classes\co\\universal\milestone\PriceCodeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */