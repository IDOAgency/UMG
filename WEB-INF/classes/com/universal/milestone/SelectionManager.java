package WEB-INF.classes.com.universal.milestone;

import com.techempower.ComponentLog;
import com.techempower.EnhancedProperties;
import com.techempower.gemini.Context;
import com.techempower.gemini.FormDropDownMenu;
import com.techempower.gemini.GeminiApplication;
import com.universal.milestone.Bom;
import com.universal.milestone.BomCassetteDetail;
import com.universal.milestone.BomDVDDetail;
import com.universal.milestone.BomVinylDetail;
import com.universal.milestone.Cache;
import com.universal.milestone.Company;
import com.universal.milestone.Division;
import com.universal.milestone.Environment;
import com.universal.milestone.Family;
import com.universal.milestone.Form;
import com.universal.milestone.Genre;
import com.universal.milestone.ImpactDate;
import com.universal.milestone.JdbcConnector;
import com.universal.milestone.Label;
import com.universal.milestone.LookupObject;
import com.universal.milestone.MilestoneConstants;
import com.universal.milestone.MilestoneHelper;
import com.universal.milestone.MilestoneHelper_2;
import com.universal.milestone.MultOtherContact;
import com.universal.milestone.MultSelection;
import com.universal.milestone.Notepad;
import com.universal.milestone.NotepadSortOrder;
import com.universal.milestone.Pfm;
import com.universal.milestone.Plant;
import com.universal.milestone.PrefixCode;
import com.universal.milestone.PriceCode;
import com.universal.milestone.ProductCategory;
import com.universal.milestone.ProjectSearchManager;
import com.universal.milestone.ReleaseType;
import com.universal.milestone.ReleasingFamily;
import com.universal.milestone.Schedule;
import com.universal.milestone.ScheduleManager;
import com.universal.milestone.ScheduledTask;
import com.universal.milestone.Selection;
import com.universal.milestone.SelectionConfiguration;
import com.universal.milestone.SelectionHandler;
import com.universal.milestone.SelectionManager;
import com.universal.milestone.SelectionStatus;
import com.universal.milestone.SelectionSubConfiguration;
import com.universal.milestone.SellCode;
import com.universal.milestone.Task;
import com.universal.milestone.User;
import com.universal.milestone.UserManager;
import com.universal.milestone.projectSearchSvcClient;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import net.umusic.milestone.alps.DcGDRSResults;

public class SelectionManager implements MilestoneConstants {
  public static final String COMPONENT_CODE = "mSel";
  
  public static final String DEFAULT_ORDER = " ORDER BY artist, title, selection_no, street_date ";
  
  public static final String DEFAULT_ORDER_DESCENDING = " ORDER BY artist DESC , title, selection_no, street_date ";
  
  public static final String CREATE_EDIT = "CREATE_EDIT";
  
  public static final String SAVE_SEND = "SAVE_SEND";
  
  public static final String DELETE = "DELETE";
  
  protected static SelectionManager selectionManager = null;
  
  protected static ComponentLog log;
  
  public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mSel"); }
  
  public static SelectionManager getInstance() {
    if (selectionManager == null)
      selectionManager = new SelectionManager(); 
    return selectionManager;
  }
  
  public String[] getSelectionStatusList() { return null; }
  
  public String[] getPrefixList() { return null; }
  
  public String[] getProductCategoryList() { return null; }
  
  public String[] getReleaseTypeList() { return null; }
  
  public String[] getSelectionConfigurationList() { return null; }
  
  public String[] getSelectionSubConfigurationList() { return null; }
  
  public SellCode[] getSellCodeList() { return null; }
  
  public String[] getGenreList() { return null; }
  
  public String[] getPlantList() { return null; }
  
  public String[] getDepartmentList() { return null; }
  
  public String[] getTaskAbbreviationList() { return null; }
  
  public String[] getScheduledTaskStatusList() { return null; }
  
  public String[] getDayTypeList() { return null; }
  
  public Selection getSelection(int pSelectionId) { return null; }
  
  public Task getTask(int pTaskId) { return null; }
  
  public Schedule getSchedule(int pScheduleId) { return new Schedule(); }
  
  public Pfm getPfm(int pReleaseId) {
    String pfmQuery = "SELECT top(50) * FROM vi_PFM_Selection WHERE release_id = " + 
      
      pReleaseId + 
      " ;";
    JdbcConnector connector = MilestoneHelper.getConnector(pfmQuery);
    connector.runQuery();
    Pfm pfm = null;
    if (connector.more()) {
      pfm = new Pfm();
      int myReleaseId = connector.getIntegerField("release_id");
      pfm.setReleaseId(myReleaseId);
      Selection mySelection = getSelectionHeader(myReleaseId);
      pfm.setSelection(mySelection);
      pfm.setStatus(connector.getField("status"));
      pfm.setEncryptionFlag(connector.getField("encryption_flag", ""));
      pfm.setSoundScanGrp(connector.getField("soundscan_grp", ""));
      pfm.setChangeNumber(connector.getField("change_number", ""));
      String streetDateString = connector.getFieldByName("street_date");
      if (streetDateString != null)
        pfm.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
      pfm.setProjectID(connector.getField("project_no", ""));
      pfm.setParentalGuidance(connector.getBoolean("parental_indicator"));
      pfm.setReleaseType(connector.getField("release_type", ""));
      pfm.setMode(connector.getField("mode", ""));
      pfm.setPrintOption(connector.getField("print_flag", ""));
      pfm.setPreparedBy(connector.getField("prepared_by", ""));
      pfm.setEmail(connector.getField("email", ""));
      pfm.setPhone(connector.getField("phone", ""));
      pfm.setUpc(connector.getField("UPC", ""));
      pfm.setFaxNumber(connector.getField("fax", ""));
      pfm.setComments(connector.getField("comments", ""));
      if (!connector.getField("operating_company", "").equalsIgnoreCase("-1"))
        pfm.setOperatingCompany(connector.getField("operating_company", "")); 
      pfm.setProductNumber(connector.getField("product_number", ""));
      if (!connector.getField("config_code", "").equalsIgnoreCase("-1"))
        pfm.setConfigCode(connector.getField("config_code", "")); 
      if (!connector.getField("modifier", "").equalsIgnoreCase("-1"))
        pfm.setModifier(connector.getField("modifier", "")); 
      pfm.setTitle(connector.getField("title", ""));
      pfm.setArtist(connector.getField("artist", ""));
      pfm.setTitleId(connector.getField("title_id", ""));
      pfm.setSuperLabel(connector.getField("super_label", ""));
      pfm.setLabelCode(connector.getField("label_code", ""));
      if (!connector.getField("company_code", "").equalsIgnoreCase("-1"))
        pfm.setCompanyCode(connector.getField("company_code", "")); 
      if (!connector.getField("po_merge_code", "").equalsIgnoreCase("-1"))
        pfm.setPoMergeCode(connector.getField("po_merge_code", "")); 
      pfm.setUnitsPerSet(connector.getIntegerField("units_per_set"));
      pfm.setSetsPerCarton(connector.getIntegerField("sets_per_carton"));
      if (!connector.getField("supplier", "").equalsIgnoreCase("-1"))
        pfm.setSupplier(connector.getField("supplier", "")); 
      pfm.setImportIndicator(connector.getField("import_code", ""));
      if (!connector.getField("music_line", "").equalsIgnoreCase("-1"))
        pfm.setMusicLine(connector.getField("music_line", "")); 
      if (!connector.getField("repertoire_owner", "").equalsIgnoreCase("-1"))
        pfm.setRepertoireOwner(connector.getField("repertoire_owner", "")); 
      pfm.setRepertoireClass(connector.getField("repertoire_class", ""));
      if (!connector.getField("return_code", "").equalsIgnoreCase("-1"))
        pfm.setReturnCode(connector.getField("return_code", "")); 
      if (!connector.getField("export_flag", "").equalsIgnoreCase("-1"))
        pfm.setExportFlag(connector.getField("export_flag", "")); 
      if (!connector.getField("countries", "").equalsIgnoreCase("-1"))
        pfm.setCountries(connector.getField("countries", "")); 
      pfm.setSpineTitle(connector.getField("spine_title", ""));
      pfm.setSpineArtist(connector.getField("spine_artist", ""));
      if (!connector.getField("price_code", "").equalsIgnoreCase("-1"))
        pfm.setPriceCode(connector.getField("price_code", "")); 
      if (!connector.getField("digital_price_code", "").equalsIgnoreCase("-1"))
        pfm.setPriceCodeDPC(connector.getField("digital_price_code", "")); 
      if (!connector.getField("guarantee_code", "").equalsIgnoreCase("-1"))
        pfm.setGuaranteeCode(connector.getField("guarantee_code", "")); 
      if (!connector.getField("loose_pick_exempt", "").equalsIgnoreCase("-1"))
        pfm.setLoosePickExemptCode(connector.getField("loose_pick_exempt", "")); 
      if (!connector.getField("compilation_code", "").equalsIgnoreCase("-1"))
        pfm.setCompilationCode(connector.getField("compilation_code", "")); 
      if (!connector.getField("imp_rate_code", "").equalsIgnoreCase("-1"))
        pfm.setImpRateCode(connector.getField("imp_rate_code", "")); 
      pfm.setMusicType((Genre)getLookupObject(connector.getField("music_type"), 
            Cache.getMusicTypes()));
      if (!connector.getField("narm_extract_flag", "").equalsIgnoreCase("-1"))
        pfm.setNarmFlag(connector.getField("narm_extract_flag", "")); 
      if (!connector.getField("price_point", "").equalsIgnoreCase("-1"))
        pfm.setPricePoint(connector.getField("price_point", "")); 
      pfm.setApprovedByName(connector.getField("approved_by_name", ""));
      pfm.setEnteredByName(connector.getField("entered_by", ""));
      pfm.setVerifiedByName(connector.getField("verified_by_name", ""));
      pfm.setValueAdded(connector.getBoolean("value_added"));
      pfm.setBoxSet(connector.getBoolean("box_set"));
      String lastDateString = connector.getFieldByName("last_updated_on");
      if (lastDateString != null)
        pfm.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
      pfm.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
      if ((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()) != null)
        pfm.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes())); 
      pfm.setSelectionNo(connector.getField("selection_no"));
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      pfm.setLastUpdatedCk(lastUpdatedLong);
    } 
    connector.close();
    return pfm;
  }
  
  public Bom getBom(Selection currentSelection) {
    if (currentSelection != null) {
      String bomHeaderQuery = "SELECT top(50) b.[bom_id], r.[release_id], b.[format], b.[print_flag],        b.[date], b.[type], b.[change_number], b.[submitted], b.[email], b.[phone],         b.[comments], b.[label], b.[release_comp_id],         b.[retail_indicator], b.[selection_number], b.[due_date],         b.[units_per_pkg], b.[run_time], b.[configuration],         b.[spine_sticker_indicator], b.[shrink_wrap_indicator],         b.[special_instructions], b.[entered_by], b.[last_updated_by],         b.[last_updated_on], b.[last_updated_ck],         b.[label] AS release_label_id,         l.[name] AS release_label, b.[artist], b.[title],         r.[configuration] AS release_configuration,         r.[sub_configuration] AS release_sub_configuration,         r.[street_date] as release_street_date,         b.[selection_number] as release_selection_no,         r.[UPC] as release_UPC,         b.[status] as bom_status,         r.[prefix] as release_prefix, b.upc as bom_upc    FROM vi_release_header r         LEFT OUTER JOIN vi_bom_header b on r.[release_id] = b.[release_id],         vi_structure l   WHERE r.label_id = l.structure_id     AND (r.[release_id] = " + 
        
        currentSelection.getSelectionID() + ");";
      Bom bom = null;
      String bomDetailQuery = "";
      JdbcConnector connectorHeader = MilestoneHelper.getConnector(bomHeaderQuery);
      connectorHeader.runQuery();
      String bomQuery = "select bom_id from vi_bom_header where release_id = " + currentSelection.getSelectionID();
      JdbcConnector connectorBom = MilestoneHelper.getConnector(bomQuery);
      connectorBom.runQuery();
      if (connectorHeader.more()) {
        bomDetailQuery = "SELECT s.description as supplier, p.description as part, d.[bom_id], d.[status_indicator], d.[part_id], d.[supplier_id],         d.[ink1], d.[ink2], d.[label], d.[selection], d.[information],         h.[last_updated_by]    FROM vi_bom_detail d,         vi_bom_header h,         vi_Part p,         vi_Supplier s   WHERE h.[bom_id] = d.[bom_id]     AND ( " + 
          
          connectorHeader.getIntegerField("bom_id") + " < 0 OR d.[bom_id] = " + connectorHeader.getIntegerField("bom_id") + " ) " + 
          "    AND ( p.part_id = d.part_id ) " + 
          "    AND ( d.[supplier_id] = s.supplier_id );";
        bom = new Bom();
        bom.setBomId(connectorHeader.getIntegerField("bom_id"));
        int myReleaseId = connectorHeader.getIntegerField("release_id");
        bom.setReleaseId(myReleaseId);
        bom.setFormat(connectorHeader.getField("format", ""));
        bom.setPrintOption(connectorHeader.getField("print_flag", ""));
        String dateString = connectorHeader.getFieldByName("date");
        if (dateString != null)
          bom.setDate(MilestoneHelper.getDatabaseDate(dateString)); 
        bom.setType(connectorHeader.getField("type", ""));
        bom.setChangeNumber(connectorHeader.getField("change_number", ""));
        bom.setSubmitter(connectorHeader.getField("submitted", ""));
        bom.setEmail(connectorHeader.getField("email", ""));
        bom.setPhone(connectorHeader.getField("phone", ""));
        bom.setComments(connectorHeader.getField("comments", ""));
        bom.setLabelId(connectorHeader.getIntegerField("release_label_id"));
        bom.setReleasingCompanyId(connectorHeader.getField("release_comp_id", ""));
        bom.setIsRetail(connectorHeader.getBoolean("retail_indicator"));
        bom.setSelectionNumber(connectorHeader.getField("selection_number", ""));
        bom.setStatus(connectorHeader.getField("bom_status", ""));
        bom.setUpc(connectorHeader.getField("bom_upc", ""));
        bom.setArtist(connectorHeader.getField("artist", ""));
        bom.setTitle(connectorHeader.getField("title", ""));
        String streetDateOnBomString = connectorHeader.getFieldByName("due_date");
        if (streetDateOnBomString != null)
          bom.setStreetDateOnBom(MilestoneHelper.getDatabaseDate(streetDateOnBomString)); 
        bom.setUnitsPerKG(connectorHeader.getIntegerField("units_per_pkg"));
        bom.setRunTime(connectorHeader.getField("run_time", ""));
        bom.setConfiguration(connectorHeader.getField("configuration", ""));
        if (connectorBom.more()) {
          bom.setHasSpineSticker(connectorHeader.getBoolean("spine_sticker_indicator"));
          bom.setUseShrinkWrap(connectorHeader.getBoolean("shrink_wrap_indicator"));
        } else {
          bom.setHasSpineSticker(true);
          bom.setUseShrinkWrap(true);
        } 
        bom.setSpecialInstructions(connectorHeader.getField("special_instructions", ""));
        bom.setEnteredBy(connectorHeader.getIntegerField("entered_by"));
        bom.setModifiedBy(connectorHeader.getIntegerField("last_updated_by"));
        String modifiedOnString = connectorHeader.getFieldByName("last_updated_on");
        if (modifiedOnString != null)
          bom.setModifiedOn(MilestoneHelper.getDatabaseDate(modifiedOnString)); 
        long lastUpdatedLong = -1L;
        if (connectorHeader.getFieldByName("last_updated_ck") != null)
          lastUpdatedLong = Long.parseLong(connectorHeader.getFieldByName("last_updated_ck"), 16); 
        bom.setLastUpdatedCheck(lastUpdatedLong);
        JdbcConnector connectorDetail = MilestoneHelper.getConnector(bomDetailQuery);
        connectorDetail.runQuery();
        String selectionConfiguration = currentSelection.getSelectionConfig().getSelectionConfigurationAbbreviation();
        if (selectionConfiguration.equalsIgnoreCase("CAS")) {
          BomCassetteDetail bomCassetteDetail = null;
          if (connectorDetail.more())
            bomCassetteDetail = new BomCassetteDetail(); 
          while (connectorDetail.more()) {
            if (connectorDetail.getIntegerField("part_id") == 5) {
              bomCassetteDetail.coPartId = connectorDetail.getIntegerField("part_id");
              bomCassetteDetail.coPart = connectorDetail.getField("part", "");
              bomCassetteDetail.coParSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomCassetteDetail.coSupplier = connectorDetail.getField("supplier");
              bomCassetteDetail.coInk1 = connectorDetail.getField("ink1", "");
              bomCassetteDetail.coInk2 = connectorDetail.getField("ink2", "");
              bomCassetteDetail.coColor = connectorDetail.getField("selection", "");
              bomCassetteDetail.coInfo = connectorDetail.getField("information", "");
              bomCassetteDetail.coStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 16) {
              bomCassetteDetail.norelcoPartId = connectorDetail.getIntegerField("part_id");
              bomCassetteDetail.norelcoPart = connectorDetail.getField("part", "");
              bomCassetteDetail.norelcoSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomCassetteDetail.norelcoSuplier = connectorDetail.getField("supplier");
              bomCassetteDetail.norelcoColor = connectorDetail.getField("selection", "");
              bomCassetteDetail.norelcoInfo = connectorDetail.getField("information", "");
              bomCassetteDetail.norelcoStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 13) {
              bomCassetteDetail.jCardPartId = connectorDetail.getIntegerField("part_id");
              bomCassetteDetail.jCardPart = connectorDetail.getField("part", "");
              bomCassetteDetail.jCardSupplier = connectorDetail.getField("supplier", "");
              bomCassetteDetail.jCardSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomCassetteDetail.jCardInk1 = connectorDetail.getField("ink1", "");
              bomCassetteDetail.jCardInk2 = connectorDetail.getField("ink2", "");
              bomCassetteDetail.jCardPanels = connectorDetail.getField("selection", "");
              bomCassetteDetail.jCardInfo = connectorDetail.getField("information", "");
              bomCassetteDetail.jCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 24) {
              bomCassetteDetail.uCardPartId = connectorDetail.getIntegerField("part_id");
              bomCassetteDetail.uCardPart = connectorDetail.getField("part", "");
              bomCassetteDetail.uCardSupplier = connectorDetail.getField("supplier", "");
              bomCassetteDetail.uCardSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomCassetteDetail.uCardInk1 = connectorDetail.getField("ink1", "");
              bomCassetteDetail.uCardInk2 = connectorDetail.getField("ink2", "");
              bomCassetteDetail.uCardPanels = connectorDetail.getField("selection", "");
              bomCassetteDetail.uCardInfo = connectorDetail.getField("information", "");
              bomCassetteDetail.uCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 17) {
              bomCassetteDetail.oCardPartId = connectorDetail.getIntegerField("part_id");
              bomCassetteDetail.oCardPart = connectorDetail.getField("part", "");
              bomCassetteDetail.oCardSupplier = connectorDetail.getField("supplier", "");
              bomCassetteDetail.oCardSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomCassetteDetail.oCardInk1 = connectorDetail.getField("ink1", "");
              bomCassetteDetail.oCardInk2 = connectorDetail.getField("ink2", "");
              bomCassetteDetail.oCardInfo = connectorDetail.getField("information", "");
              bomCassetteDetail.oCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 21) {
              bomCassetteDetail.stickerOneCardPartId = connectorDetail.getIntegerField("part_id");
              bomCassetteDetail.stickerOneCardPart = connectorDetail.getField("part", "");
              bomCassetteDetail.stickerOneCardSupplier = connectorDetail.getField("supplier", "");
              bomCassetteDetail.stickerOneCardSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomCassetteDetail.stickerOneCardInk1 = connectorDetail.getField("ink1", "");
              bomCassetteDetail.stickerOneCardInk2 = connectorDetail.getField("ink2", "");
              bomCassetteDetail.stickerOneCardPlaces = connectorDetail.getField("selection", "");
              bomCassetteDetail.stickerOneCardInfo = connectorDetail.getField("information", "");
              bomCassetteDetail.stickerOneCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 22) {
              bomCassetteDetail.stickerTwoCardPartId = connectorDetail.getIntegerField("part_id");
              bomCassetteDetail.stickerTwoCardPart = connectorDetail.getField("part", "");
              bomCassetteDetail.stickerTwoCardSupplier = connectorDetail.getField("supplier", "");
              bomCassetteDetail.stickerTwoCardSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomCassetteDetail.stickerTwoCardInk1 = connectorDetail.getField("ink1", "");
              bomCassetteDetail.stickerTwoCardInk2 = connectorDetail.getField("ink2", "");
              bomCassetteDetail.stickerTwoCardPlaces = connectorDetail.getField("selection", "");
              bomCassetteDetail.stickerTwoCardInfo = connectorDetail.getField("information", "");
              bomCassetteDetail.stickerTwoCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 18) {
              bomCassetteDetail.otherPartId = connectorDetail.getIntegerField("part_id");
              bomCassetteDetail.otherPart = connectorDetail.getField("part", "");
              bomCassetteDetail.otherSupplier = connectorDetail.getField("supplier", "");
              bomCassetteDetail.otherSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomCassetteDetail.otherInk1 = connectorDetail.getField("ink1", "");
              bomCassetteDetail.otherInk2 = connectorDetail.getField("ink2", "");
              bomCassetteDetail.otherInfo = connectorDetail.getField("information", "");
              bomCassetteDetail.otherStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } 
            connectorDetail.next();
          } 
          bom.setBomCassetteDetail(bomCassetteDetail);
        } else if (selectionConfiguration.equalsIgnoreCase("VIN")) {
          BomVinylDetail bomVinylDetail = null;
          if (connectorDetail.more())
            bomVinylDetail = new BomVinylDetail(); 
          while (connectorDetail.more()) {
            if (connectorDetail.getIntegerField("part_id") == 19) {
              bomVinylDetail.recordPartId = connectorDetail.getIntegerField("part_id");
              bomVinylDetail.recordPart = connectorDetail.getField("part", "");
              bomVinylDetail.recordSupplier = connectorDetail.getField("supplier", "");
              bomVinylDetail.recordSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomVinylDetail.recordColor = connectorDetail.getField("selection", "");
              bomVinylDetail.recordInfo = connectorDetail.getField("information", "");
              bomVinylDetail.recordStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 14) {
              bomVinylDetail.labelPartId = connectorDetail.getIntegerField("part_id");
              bomVinylDetail.labelPart = connectorDetail.getField("part", "");
              bomVinylDetail.labelSupplier = connectorDetail.getField("supplier", "");
              bomVinylDetail.labelSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomVinylDetail.labelInk1 = connectorDetail.getField("ink1", "");
              bomVinylDetail.labelInk2 = connectorDetail.getField("ink2", "");
              bomVinylDetail.labelInfo = connectorDetail.getField("information", "");
              bomVinylDetail.labelStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 20) {
              bomVinylDetail.sleevePartId = connectorDetail.getIntegerField("part_id");
              bomVinylDetail.sleevePart = connectorDetail.getField("part", "");
              bomVinylDetail.sleeveSupplier = connectorDetail.getField("supplier", "");
              bomVinylDetail.sleeveSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomVinylDetail.sleeveInk1 = connectorDetail.getField("ink1", "");
              bomVinylDetail.sleeveInk2 = connectorDetail.getField("ink2", "");
              bomVinylDetail.sleeveInfo = connectorDetail.getField("information", "");
              bomVinylDetail.sleeveStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 11) {
              bomVinylDetail.jacketPartId = connectorDetail.getIntegerField("part_id");
              bomVinylDetail.jacketPart = connectorDetail.getField("part", "");
              bomVinylDetail.jacketSupplier = connectorDetail.getField("supplier", "");
              bomVinylDetail.jacketSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomVinylDetail.jacketInk1 = connectorDetail.getField("ink1", "");
              bomVinylDetail.jacketInk2 = connectorDetail.getField("ink2", "");
              bomVinylDetail.jacketInfo = connectorDetail.getField("information", "");
              bomVinylDetail.jacketStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 33) {
              bomVinylDetail.insertPartId = connectorDetail.getIntegerField("part_id");
              bomVinylDetail.insertPart = connectorDetail.getField("part", "");
              bomVinylDetail.insertSupplier = connectorDetail.getField("supplier", "");
              bomVinylDetail.insertSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomVinylDetail.insertInk1 = connectorDetail.getField("ink1", "");
              bomVinylDetail.insertInk2 = connectorDetail.getField("ink2", "");
              bomVinylDetail.insertInfo = connectorDetail.getField("information", "");
              bomVinylDetail.insertStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 21) {
              bomVinylDetail.stickerOnePartId = connectorDetail.getIntegerField("part_id");
              bomVinylDetail.stickerOnePart = connectorDetail.getField("part", "");
              bomVinylDetail.stickerOneSupplier = connectorDetail.getField("supplier", "");
              bomVinylDetail.stickerOneSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomVinylDetail.stickerOneInk1 = connectorDetail.getField("ink1", "");
              bomVinylDetail.stickerOneInk2 = connectorDetail.getField("ink2", "");
              bomVinylDetail.stickerOnePlaces = connectorDetail.getField("selection", "");
              bomVinylDetail.stickerOneInfo = connectorDetail.getField("information", "");
              bomVinylDetail.stickerOneStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 22) {
              bomVinylDetail.stickerTwoPartId = connectorDetail.getIntegerField("part_id");
              bomVinylDetail.stickerTwoPart = connectorDetail.getField("part", "");
              bomVinylDetail.stickerTwoSupplier = connectorDetail.getField("supplier", "");
              bomVinylDetail.stickerTwoSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomVinylDetail.stickerTwoInk1 = connectorDetail.getField("ink1", "");
              bomVinylDetail.stickerTwoInk2 = connectorDetail.getField("ink2", "");
              bomVinylDetail.stickerTwoPlaces = connectorDetail.getField("selection", "");
              bomVinylDetail.stickerTwoInfo = connectorDetail.getField("information", "");
              bomVinylDetail.stickerTwoStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 18) {
              bomVinylDetail.otherPartId = connectorDetail.getIntegerField("part_id");
              bomVinylDetail.otherPart = connectorDetail.getField("part", "");
              bomVinylDetail.otherSupplier = connectorDetail.getField("supplier", "");
              bomVinylDetail.otherSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomVinylDetail.otherInk1 = connectorDetail.getField("ink1", "");
              bomVinylDetail.otherInk2 = connectorDetail.getField("ink2", "");
              bomVinylDetail.otherInfo = connectorDetail.getField("information", "");
              bomVinylDetail.otherStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } 
            connectorDetail.next();
          } 
          bom.setBomVinylDetail(bomVinylDetail);
        } else {
          BomDVDDetail bomDiskDetail = null;
          if (connectorDetail.more())
            bomDiskDetail = new BomDVDDetail(); 
          while (connectorDetail.more()) {
            if (connectorDetail.getIntegerField("part_id") == 29) {
              bomDiskDetail.wrapPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.wrapPart = connectorDetail.getField("part", "");
              bomDiskDetail.wrapSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.wrapSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.wrapInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.wrapInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.wrapInfo = connectorDetail.getField("information", "");
              bomDiskDetail.wrapStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } 
            if (connectorDetail.getIntegerField("part_id") == 30) {
              bomDiskDetail.dvdCasePartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.dvdPart = connectorDetail.getField("part", "");
              bomDiskDetail.dvdInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.dvdInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.dvdInfo = connectorDetail.getField("information", "");
              bomDiskDetail.dvdStatusIndicator = connectorDetail.getBoolean("status_indicator");
              bomDiskDetail.dvdSelectionInfo = connectorDetail.getField("selection", "");
            } 
            if (connectorDetail.getIntegerField("part_id") == 32) {
              bomDiskDetail.bluRayCasePartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.bluRayPart = connectorDetail.getField("part", "");
              bomDiskDetail.bluRayInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.bluRayInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.bluRayInfo = connectorDetail.getField("information", "");
              bomDiskDetail.bluRayStatusIndicator = connectorDetail.getBoolean("status_indicator");
              bomDiskDetail.bluRaySelectionInfo = connectorDetail.getField("selection", "");
            } 
            if (connectorDetail.getIntegerField("part_id") == 7) {
              bomDiskDetail.discPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.discPart = connectorDetail.getField("part", "");
              bomDiskDetail.diskSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.discInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.discInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.discSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.discInfo = connectorDetail.getField("information", "");
              bomDiskDetail.discStatusIndicator = connectorDetail.getBoolean("status_indicator");
              bomDiskDetail.discSelectionInfo = connectorDetail.getField("selection", "");
            } else if (connectorDetail.getIntegerField("part_id") == 12) {
              bomDiskDetail.jewelPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.jewelPart = connectorDetail.getField("part", "");
              bomDiskDetail.jewelInfo = connectorDetail.getField("information", "");
              bomDiskDetail.jewelColor = connectorDetail.getField("selection", "");
              bomDiskDetail.jewelStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 23) {
              bomDiskDetail.trayPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.trayPart = connectorDetail.getField("part", "");
              bomDiskDetail.trayInfo = connectorDetail.getField("information", "");
              bomDiskDetail.trayColor = connectorDetail.getField("selection", "");
              bomDiskDetail.trayStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 10) {
              bomDiskDetail.inlayPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.inlayPart = connectorDetail.getField("part", "");
              bomDiskDetail.inlaySupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.inlaySupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.inlayInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.inlayInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.inlayInfo = connectorDetail.getField("information", "");
              bomDiskDetail.inlayStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 9) {
              bomDiskDetail.frontPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.frontPart = connectorDetail.getField("part", "");
              bomDiskDetail.frontSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.frontSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.frontInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.frontInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.frontInfo = connectorDetail.getField("information", "");
              bomDiskDetail.frontStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 8) {
              bomDiskDetail.folderPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.folderPart = connectorDetail.getField("part", "");
              bomDiskDetail.folderSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.folderSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.folderInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.folderInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.folderPages = connectorDetail.getField("selection", "");
              bomDiskDetail.folderInfo = connectorDetail.getField("information", "");
              bomDiskDetail.folderStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 1) {
              bomDiskDetail.bookletPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.bookletPart = connectorDetail.getField("part", "");
              bomDiskDetail.bookletSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.bookletSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.bookletInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.bookletInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.bookletPages = connectorDetail.getField("selection", "");
              bomDiskDetail.bookletInfo = connectorDetail.getField("information", "");
              bomDiskDetail.bookletStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 4) {
              bomDiskDetail.brcPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.brcPart = connectorDetail.getField("part", "");
              bomDiskDetail.brcSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.brcSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.brcInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.brcInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.brcSize = connectorDetail.getField("selection", "");
              bomDiskDetail.brcInfo = connectorDetail.getField("information", "");
              bomDiskDetail.brcStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 15) {
              bomDiskDetail.miniPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.miniPart = connectorDetail.getField("part", "");
              bomDiskDetail.miniSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.miniSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.miniInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.miniInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.miniInfo = connectorDetail.getField("information", "");
              bomDiskDetail.miniStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 6) {
              bomDiskDetail.digiPakPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.digiPakPart = connectorDetail.getField("part", "");
              bomDiskDetail.digiPakSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.digiPakSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.digiPakInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.digiPakInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.digiPakTray = connectorDetail.getField("selection", "");
              bomDiskDetail.digiPakInfo = connectorDetail.getField("information", "");
              bomDiskDetail.digiPakStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } 
            if (connectorDetail.getIntegerField("part_id") == 31) {
              bomDiskDetail.softPakPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.softPakPart = connectorDetail.getField("part", "");
              bomDiskDetail.softPakSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.softPakSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.softPakInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.softPakInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.softPakTray = connectorDetail.getField("selection", "");
              bomDiskDetail.softPakInfo = connectorDetail.getField("information", "");
              bomDiskDetail.softPakStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 21) {
              bomDiskDetail.stickerOnePartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.stickerOnePart = connectorDetail.getField("part", "");
              bomDiskDetail.stickerOneSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.stickerOneSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.stickerOneInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.stickerOneInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.stickerOnePlaces = connectorDetail.getField("selection", "");
              bomDiskDetail.stickerOneInfo = connectorDetail.getField("information", "");
              bomDiskDetail.stickerOneStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 22) {
              bomDiskDetail.stickerTwoPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.stickerTwoPart = connectorDetail.getField("part", "");
              bomDiskDetail.stickerTwoSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.stickerTwoSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.stickerTwoInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.stickerTwoInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.stickerTwoPlaces = connectorDetail.getField("selection", "");
              bomDiskDetail.stickerTwoInfo = connectorDetail.getField("information", "");
              bomDiskDetail.stickerTwoStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 2) {
              bomDiskDetail.bookPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.bookPart = connectorDetail.getField("part", "");
              bomDiskDetail.bookSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.bookSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.bookInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.bookInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.bookPages = connectorDetail.getField("selection", "");
              bomDiskDetail.bookInfo = connectorDetail.getField("information", "");
              bomDiskDetail.bookStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 3) {
              bomDiskDetail.boxPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.boxPart = connectorDetail.getField("part", "");
              bomDiskDetail.boxSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.boxSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.boxInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.boxInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.boxSizes = connectorDetail.getField("selection", "");
              bomDiskDetail.boxInfo = connectorDetail.getField("information", "");
              bomDiskDetail.boxStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } else if (connectorDetail.getIntegerField("part_id") == 18) {
              bomDiskDetail.otherPartId = connectorDetail.getIntegerField("part_id");
              bomDiskDetail.otherPart = connectorDetail.getField("part", "");
              bomDiskDetail.otherSupplierId = connectorDetail.getIntegerField("supplier_id");
              bomDiskDetail.otherSupplier = connectorDetail.getField("supplier", "");
              bomDiskDetail.otherInk1 = connectorDetail.getField("ink1", "");
              bomDiskDetail.otherInk2 = connectorDetail.getField("ink2", "");
              bomDiskDetail.otherInfo = connectorDetail.getField("information", "");
              bomDiskDetail.otherStatusIndicator = connectorDetail.getBoolean("status_indicator");
            } 
            connectorDetail.next();
          } 
          if (selectionConfiguration.equalsIgnoreCase("DVV") && 
            !bom.getFormat().equalsIgnoreCase("CDO")) {
            bom.setBomDVDDetail(bomDiskDetail);
          } else {
            bom.setBomDiskDetail(bomDiskDetail);
          } 
        } 
        connectorDetail.close();
      } 
      connectorHeader.close();
      connectorBom.close();
      return bom;
    } 
    return null;
  }
  
  public int mapReleaseId2BomId(int pReleaseId) {
    int bomId = 0;
    String mapReleaseId2BomIdQuery = "SELECT bom_id FROM vi_Bom_Header WHERE release_id = " + 
      
      pReleaseId + 
      ";";
    JdbcConnector connector = MilestoneHelper.getConnector(mapReleaseId2BomIdQuery);
    connector.runQuery();
    if (connector.more())
      bomId = connector.getIntegerField("bom_id"); 
    connector.close();
    return bomId;
  }
  
  public Selection getSelectionHeader(int id) {
    String releaseHeaderQuery = "sp_get_Schedule_Release_Header " + id;
    JdbcConnector connector = MilestoneHelper.getConnector(releaseHeaderQuery);
    connector.runQuery();
    Selection selection = null;
    if (connector.more()) {
      selection = new Selection();
      selection.setSelectionID(connector.getIntegerField("release_id"));
      String selectionNo = "";
      if (connector.getFieldByName("selection_no") != null)
        selectionNo = connector.getFieldByName("selection_no"); 
      selection.setSelectionNo(selectionNo);
      selection.setProjectID(connector.getField("project_no", ""));
      String titleId = "";
      if (connector.getFieldByName("title_id") != null)
        titleId = connector.getFieldByName("title_id"); 
      selection.setTitleID(titleId);
      selection.setTitle(connector.getField("title", ""));
      selection.setArtistFirstName(connector.getField("artist_first_name", ""));
      selection.setArtistLastName(connector.getField("artist_last_name", ""));
      selection.setArtist(connector.getField("artist", ""));
      selection.setFlArtist(connector.getField("fl_artist", ""));
      selection.setASide(connector.getField("side_a_title", ""));
      selection.setBSide(connector.getField("side_b_title", ""));
      selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
            Cache.getProductCategories()));
      selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
            Cache.getReleaseTypes()));
      selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
            Cache.getSelectionConfigs()));
      selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
            selection.getSelectionConfig()));
      selection.setUpc(connector.getField("upc", ""));
      String sellCodeString = connector.getFieldByName("price_code");
      if (sellCodeString != null)
        selection.setPriceCode(getPriceCode(sellCodeString)); 
      selection.setSellCode(sellCodeString);
      String sellCodeStringDPC = connector.getFieldByName("digital_price_code");
      if (sellCodeStringDPC != null)
        selection.setPriceCodeDPC(getPriceCode(sellCodeStringDPC)); 
      selection.setSellCodeDPC(sellCodeStringDPC);
      selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
            Cache.getMusicTypes()));
      selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
      selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
      selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
      selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
      selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
      String streetDateString = connector.getFieldByName("street_date");
      if (streetDateString != null)
        selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString)); 
      String internationalDateString = connector.getFieldByName("international_date");
      if (internationalDateString != null)
        selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString)); 
      selection.setOtherContact(connector.getField("coordinator_contacts", ""));
      if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null)
        selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id"))); 
      selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
            Cache.getSelectionStatusList()));
      selection.setHoldSelection(connector.getBoolean("hold_indicator"));
      selection.setHoldReason(connector.getField("hold_reason", ""));
      selection.setComments(connector.getField("comments", ""));
      selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
      selection.setNumberOfUnits(connector.getIntegerField("units"));
      selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
      selection.setNoDigitalRelease(connector.getBoolean("no_digital_release"));
      selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
      selection.setSelectionPackaging(connector.getField("packaging", ""));
      String impactDateString = connector.getFieldByName("impact_date");
      if (impactDateString != null)
        selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString)); 
      String lastUpdateDateString = connector.getFieldByName("last_updated_on");
      if (lastUpdateDateString != null)
        selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString)); 
      selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
      long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
      selection.setLastUpdatedCheck(lastUpdatedLong);
      selection.setPrice(connector.getFloat("list_price"));
      String lastChangedOn = connector.getFieldByName("last_changed_on");
      if (lastChangedOn != null)
        selection.setLastStreetUpdateDate(MilestoneHelper.getDatabaseDate(connector.getField("last_changed_on"))); 
      String originDateString = connector.getFieldByName("entered_on");
      if (originDateString != null)
        selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString)); 
      selection.setTemplateId(connector.getInt("templateId", -1));
      selection.setParentalGuidance(connector.getBoolean("parental_indicator"));
      selection.setSelectionTerritory(connector.getField("territory", ""));
      String lastSchedUpdateDateString = connector.getFieldByName("last_sched_updated_on");
      if (lastSchedUpdateDateString != null)
        selection.setLastSchedUpdateDate(MilestoneHelper.getDatabaseDate(lastSchedUpdateDateString)); 
      selection.setLastSchedUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_sched_updated_by")));
      String digitalRlsString = connector.getFieldByName("digital_rls_date");
      if (digitalRlsString != null) {
        selection.setDigitalRlsDateString(digitalRlsString);
        selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(digitalRlsString));
      } 
      selection.setInternationalFlag(connector.getBoolean("international_flag"));
      selection.setOperCompany(connector.getField("oper_company", ""));
      selection.setSuperLabel(connector.getField("super_label", ""));
      selection.setSubLabel(connector.getField("sub_label", ""));
      selection.setConfigCode(connector.getField("config_code", ""));
      selection.setSoundScanGrp(connector.getField("soundscan_grp", ""));
      selection.setImprint(connector.getField("imprint", ""));
      selection.setNewBundleFlag(connector.getBoolean("new_bundle_flag"));
      selection.setGridNumber(connector.getField("grid_number", ""));
      selection.setSpecialInstructions(connector.getField("special_instructions", ""));
      selection.setIsDigital(connector.getBoolean("digital_flag"));
      selection.setPriority(connector.getBoolean("priority_flag"));
      selection.setArchimedesId(connector.getInt("archimedes_id", -1));
      selection.setReleaseFamilyId(connector.getInt("release_family_id", -1));
      String impactSql = "sp_get_Schedule_ImpactDates " + id;
      JdbcConnector impactConnector = MilestoneHelper.getConnector(impactSql);
      impactConnector.runQuery();
      Vector impactDates = new Vector();
      while (impactConnector.more()) {
        ImpactDate impact = new ImpactDate();
        impact.setImpactDateID(impactConnector.getInt("impactDate_Id", -1));
        impact.setSelectionID(impactConnector.getInt("selection_Id", -1));
        impact.setFormat(impactConnector.getField("format", ""));
        impact.setFormatDescription(impactConnector.getField("description", ""));
        String impactString = impactConnector.getFieldByName("impactDate");
        if (impactString != null)
          impact.setImpactDate(MilestoneHelper.getDatabaseDate(impactString)); 
        impact.setTbi(impactConnector.getBoolean("tbi"));
        impactDates.add(impact);
        impactConnector.next();
      } 
      impactConnector.close();
      selection.setImpactDates(impactDates);
      selection.setMultSelections(getMultSelections(id));
      selection.setMultOtherContacts(getMultOtherContacts(id));
      selection.setFullSelection(true);
      JdbcConnector archieConnector = MilestoneHelper.getConnector("sp_get_archie_last_update_date " + id);
      archieConnector.runQuery();
      if (archieConnector.more()) {
        String archieString = archieConnector.getFieldByName("audit_date");
        if (archieString != null)
          selection.setArchieDate(MilestoneHelper.getDatabaseDate(archieString)); 
      } 
      archieConnector.close();
      JdbcConnector autoCloseConnector = MilestoneHelper.getConnector("sp_get_auto_close_date " + id);
      autoCloseConnector.runQuery();
      if (autoCloseConnector.more()) {
        String autoCloseStr = autoCloseConnector.getFieldByName("auto_closed_date");
        if (autoCloseStr != null)
          selection.setAutoCloseDate(MilestoneHelper.getDatabaseDate(autoCloseStr)); 
      } 
      autoCloseConnector.close();
      JdbcConnector lastLegacyUpdateConnector = MilestoneHelper.getConnector("sp_get_last_legacy_update_date " + id);
      lastLegacyUpdateConnector.runQuery();
      if (lastLegacyUpdateConnector.more()) {
        String lastLegacyUpdateStr = lastLegacyUpdateConnector.getFieldByName("legacy_date");
        if (lastLegacyUpdateStr != null)
          selection.setLastLegacyUpdateDate(MilestoneHelper.getDatabaseDate(lastLegacyUpdateStr)); 
      } 
      lastLegacyUpdateConnector.close();
    } 
    connector.close();
    return selection;
  }
  
  public boolean getSelectionManufacturingSubDetail(Selection selection) {
    boolean newFlag = true;
    if (selection != null) {
      int selectionId = selection.getSelectionID();
      String manufacturingQuery = "SELECT * FROM vi_Release_Subdetail WHERE release_id = " + 
        
        selectionId + 
        ";";
      JdbcConnector connector = MilestoneHelper.getConnector(manufacturingQuery);
      connector.runQuery();
      if (connector.more()) {
        newFlag = false;
        User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("uml_id"));
        selection.setUmlContact(umlContact);
        selection.setPlant(getLookupObject(connector.getField("plant"), Cache.getVendors()));
        selection.setDistribution(getLookupObject(connector.getField("distribution"), Cache.getDistributionCodes()));
        selection.setPoQuantity(connector.getIntegerField("order_qty"));
        selection.setCompletedQuantity(connector.getIntegerField("complete_qty"));
        selection.setManufacturingComments(connector.getField("order_comments", ""));
        String lastUpdateDateString = connector.getFieldByName("last_updated_on");
        if (lastUpdateDateString != null)
          selection.setLastMfgUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString)); 
        selection.setLastMfgUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
        long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck", "0"), 16);
        selection.setLastMfgUpdatedCheck(lastUpdatedLong);
      } 
      connector.close();
      String plantSql = "select * from Manufacturing_Plants where release_id = " + selection.getSelectionID();
      JdbcConnector plantConnector = MilestoneHelper.getConnector(plantSql);
      plantConnector.runQuery();
      Vector plants = new Vector();
      while (plantConnector.more()) {
        Plant plant = new Plant();
        plant.setPlantID(plantConnector.getInt("plantId", -1));
        plant.setSelectionID(plantConnector.getField("selection_Id"));
        plant.setReleaseID(plantConnector.getInt("release_Id", -1));
        plant.setPlant(getLookupObject(plantConnector.getField("plant"), Cache.getVendors()));
        plant.setOrderQty(plantConnector.getInt("order_qty", -1));
        plant.setCompletedQty(plantConnector.getInt("complete_qty", -1));
        plants.add(plant);
        plantConnector.next();
      } 
      plantConnector.close();
      selection.setManufacturingPlants(plants);
    } 
    return newFlag;
  }
  
  public PriceCode getPriceCode(String sellCode) {
    PriceCode priceCode = null;
    Vector priceCodes = Cache.getPriceCodes();
    for (int i = 0; i < priceCodes.size(); i++) {
      PriceCode pc = (PriceCode)priceCodes.get(i);
      if (pc.getSellCode().equalsIgnoreCase(sellCode)) {
        priceCode = pc;
        break;
      } 
    } 
    return priceCode;
  }
  
  public Selection saveSelection(Selection selection, User updatingUser) {
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + selection.getCompanyId());
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + selection.getEnvironmentId());
    int holdSelection = 0;
    int specialPackaging = 0;
    int pressAndDistribution = 0;
    int parentalGuidance = 0;
    int internationalFlag = 0;
    int noDigitalRelease = 0;
    if (selection.getNoDigitalRelease())
      noDigitalRelease = 1; 
    if (selection.getPressAndDistribution())
      pressAndDistribution = 1; 
    if (selection.getSpecialPackaging())
      specialPackaging = 1; 
    if (selection.getHoldSelection())
      holdSelection = 1; 
    if (selection.getParentalGuidance())
      parentalGuidance = 1; 
    if (selection.getInternationalFlag())
      internationalFlag = 1; 
    String prefix = "";
    if (selection.getPrefixID() != null)
      prefix = selection.getPrefixID().getAbbreviation(); 
    long timestamp = selection.getLastUpdatedCheck();
    String familyID = "-1";
    String companyID = "-1";
    String divisionID = "-1";
    String labelID = "-1";
    String environmentId = "-1";
    String labelContactId = "-1";
    String territory = selection.getSelectionTerritory();
    String imprint = selection.getImprint();
    boolean isDigital = selection.getIsDigital();
    boolean new_bundle_flag = selection.getNewBundleFlag();
    String grid_number = selection.getGridNumber();
    String special_instructions = selection.getSpecialInstructions();
    int isDigitalFlag = 0;
    if (selection.getIsDigital())
      isDigitalFlag = 1; 
    int new_bundle = 0;
    if (selection.getNewBundleFlag())
      new_bundle = 1; 
    int priority = 0;
    if (selection.getPriority())
      priority = 1; 
    if (selection.getCompany() != null)
      environmentId = String.valueOf(selection.getCompany().getParentEnvironment().getStructureID()); 
    if (selection.getCompany() != null)
      companyID = String.valueOf(selection.getCompany().getStructureID()); 
    if (selection.getDivision() != null)
      divisionID = String.valueOf(selection.getDivision().getStructureID()); 
    if (selection.getLabel() != null)
      labelID = String.valueOf(selection.getLabel().getStructureID()); 
    if (selection.getCompany() != null && selection.getCompany().getParentEnvironment() != null) {
      familyID = String.valueOf(selection.getCompany().getParentEnvironment().getParentID());
      selection.setFamily((Family)MilestoneHelper.getStructureObject(selection.getCompany().getParentEnvironment().getParentID()));
    } 
    if (selection.getLabelContact() != null)
      labelContactId = String.valueOf(selection.getLabelContact().getUserId()); 
    boolean cprsIsNew = (selection.getSelectionID() < 0);
    String query = "sp_sav_Release_Header " + 
      selection.getSelectionID() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getProjectID()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getTitleID()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getTitle()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getArtist()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getArtistLastName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getArtistFirstName()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getASide()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getBSide()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionNo()) + "'," + 
      "'" + "version number" + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getProductCategory())) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getReleaseType())) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionConfig().getSelectionConfigurationAbbreviation()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation()) + "'," + 
      
      "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(selection.getUpc(), "UPC", selection.getIsDigital(), true)) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getGenre())) + "'," + 
      familyID + "," + 
      companyID + "," + 
      divisionID + "," + 
      labelID + "," + 
      "'" + MilestoneHelper.getFormatedDate(selection.getStreetDate()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(selection.getInternationalDate()) + "'," + 
      "0" + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getOtherContact()) + "'," + 
      labelContactId + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getSelectionStatus())) + "'," + 
      holdSelection + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getHoldReason()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionComments()) + "'," + 
      "'" + "group" + "'," + 
      specialPackaging + "," + 
      selection.getPrice() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getSellCode()) + "'," + 
      selection.getNumberOfUnits() + "," + 
      pressAndDistribution + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(prefix) + "'," + 
      "'" + "reference" + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionPackaging()) + "'," + 
      "'" + "packaging comments" + "'," + 
      "'" + MilestoneHelper.getFormatedDate(selection.getImpactDate()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(selection.getLastStreetUpdateDate()) + "'," + 
      
      updatingUser.getUserId() + "," + 
      selection.getTemplateId() + "," + 
      parentalGuidance + ",'" + 
      territory + "'," + 
      timestamp + "," + 
      
      "'" + MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()) + "'," + 
      internationalFlag + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getOperCompany()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getSuperLabel()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getSubLabel()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getConfigCode()) + "'," + 
      
      "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(selection.getSoundScanGrp(), "SSG", selection.getIsDigital(), true)) + "'," + 
      environmentId + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getImprint()) + "'," + 
      new_bundle + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(grid_number) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(special_instructions) + "'," + 
      isDigitalFlag + "," + 
      "'" + MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()) + "'," + 
      
      getArchimedesIdFromCache(Integer.parseInt(labelID)) + "," + 
      selection.getReleaseFamilyId() + "," + 
      priority + "," + noDigitalRelease + ",'" + selection.getSellCodeDPC() + "' ";
    log.log("Query >>>>>>>>>>>>>> : " + query);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    int newId = connector.getIntegerField("ReturnId");
    log.debug("Selection id from db " + newId);
    log.debug("Selection id from db " + newId);
    if (selection.getSelectionID() < 0)
      selection.setSelectionID(newId); 
    connector.close();
    log.debug("Flushing selection audits.");
    selection.flushAudits(updatingUser.getUserId());
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
      
      selection.getSelectionID() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      selection.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    saveImpactDates(selection, updatingUser);
    saveMultSelections(selection, updatingUser);
    saveMultOtherContacts(selection, updatingUser);
    DcGDRSResults dcGDRSResults = SelectionHandler.GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
    boolean IsGDRSactive = (!dcGDRSResults.getStatus().equals("") && !dcGDRSResults.getStatus().equals("DELETE"));
    if (IsPfmDraftOrFinal(selection.getSelectionID()))
      if (noDigitalRelease == 0) {
        if (!dcGDRSResults.getForceNoDigitalRelease().booleanValue())
          GDRS_QueueAddReleaseId(selection, "CREATE_EDIT"); 
      } else if (IsGDRSactive) {
        GDRS_QueueAddReleaseId(selection, "DELETE");
      }  
    try {
      if (!selection.getProjectID().equals("0000-00000"))
        projectSearchSvcClient.MilestoneSnapshotProjectInsert(selection.getProjectID()); 
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return selection;
  }
  
  public void saveImpactDates(Selection selection, User updatingUser) {
    if (selection != null && selection.getImpactDates() != null) {
      Vector impactDates = selection.getImpactDates();
      Vector addImpactDates = new Vector();
      Vector deleteImpactDates = new Vector();
      String impactQuery = "select * from vi_ImpactDates where selection_id = " + selection.getSelectionID();
      boolean delete = true;
      for (int a = 0; a < impactDates.size(); a++) {
        ImpactDate impactDate = (ImpactDate)impactDates.get(a);
        JdbcConnector connectorImpactCount = MilestoneHelper.getConnector(impactQuery);
        connectorImpactCount.runQuery();
        if (connectorImpactCount.more()) {
          while (connectorImpactCount.more()) {
            if (impactDate.getImpactDateID() == -1 || impactDate.getImpactDateID() == connectorImpactCount.getInt("impactDate_Id", -2)) {
              addImpactDates.add(impactDate);
              break;
            } 
            connectorImpactCount.next();
          } 
        } else {
          addImpactDates.add(impactDate);
        } 
        connectorImpactCount.close();
      } 
      JdbcConnector connectorImpactDelete = MilestoneHelper.getConnector(impactQuery);
      connectorImpactDelete.runQuery();
      while (connectorImpactDelete.more()) {
        delete = true;
        for (int b = 0; b < impactDates.size(); b++) {
          ImpactDate impactDateDelete = (ImpactDate)impactDates.get(b);
          if (connectorImpactDelete.getInt("impactDate_Id", -2) == impactDateDelete.getImpactDateID())
            delete = false; 
        } 
        if (delete) {
          ImpactDate delImpactDate = new ImpactDate();
          delImpactDate.setImpactDateID(connectorImpactDelete.getInt("impactDate_Id", -1));
          deleteImpactDates.add(delImpactDate);
        } 
        connectorImpactDelete.next();
      } 
      connectorImpactDelete.close();
      for (int i = 0; i < addImpactDates.size(); i++) {
        ImpactDate impact = (ImpactDate)addImpactDates.get(i);
        int tbi = impact.getTbi() ? 1 : 0;
        int selId = impact.getSelectionID();
        int impactDateId = impact.getImpactDateID();
        if (selId < 0)
          selId = selection.getSelectionID(); 
        String impactSql = "sp_sav_ImpactDate " + impact.getImpactDateID() + "," + 
          selId + "," + 
          "'" + impact.getFormat() + "'," + 
          "'" + MilestoneHelper.getFormatedDate(impact.getImpactDate()) + "'," + 
          tbi;
        JdbcConnector connectorAddImpact = MilestoneHelper.getConnector(impactSql);
        connectorAddImpact.runQuery();
        connectorAddImpact.close();
      } 
      JdbcConnector connectorDeleteImpact = null;
      for (int k = 0; k < deleteImpactDates.size(); k++) {
        ImpactDate impact = (ImpactDate)deleteImpactDates.get(k);
        String impactDeleteSql = "sp_del_ImpactDate " + impact.getImpactDateID();
        connectorDeleteImpact = MilestoneHelper.getConnector(impactDeleteSql);
        connectorDeleteImpact.runQuery();
        connectorDeleteImpact.close();
      } 
    } 
  }
  
  public void savePlants(Selection selection, User updatingUser) {
    if (selection != null && selection.getManufacturingPlants() != null) {
      Vector plants = selection.getManufacturingPlants();
      Vector addPlants = new Vector();
      Vector deletePlants = new Vector();
      String plantQuery = "select * from Manufacturing_Plants where release_id = " + selection.getSelectionID();
      boolean delete = true;
      for (int a = 0; a < plants.size(); a++) {
        Plant plant = (Plant)plants.get(a);
        JdbcConnector connectorPlantCount = MilestoneHelper.getConnector(plantQuery);
        connectorPlantCount.runQuery();
        if (connectorPlantCount.more()) {
          while (connectorPlantCount.more()) {
            if (plant.getPlantID() == -1 || plant.getPlantID() == connectorPlantCount.getInt("plantId", -2)) {
              addPlants.add(plant);
              break;
            } 
            connectorPlantCount.next();
          } 
        } else {
          addPlants.add(plant);
        } 
        connectorPlantCount.close();
      } 
      JdbcConnector connectorPlantDelete = MilestoneHelper.getConnector(plantQuery);
      connectorPlantDelete.runQuery();
      while (connectorPlantDelete.more()) {
        delete = true;
        for (int b = 0; b < plants.size(); b++) {
          Plant plantDelete = (Plant)plants.get(b);
          if (connectorPlantDelete.getInt("plantId", -2) == plantDelete.getPlantID())
            delete = false; 
        } 
        if (delete) {
          Plant delPlant = new Plant();
          delPlant.setPlantID(connectorPlantDelete.getInt("plantId", -1));
          deletePlants.add(delPlant);
        } 
        connectorPlantDelete.next();
      } 
      connectorPlantDelete.close();
      for (int i = 0; i < addPlants.size(); i++) {
        Plant plant = (Plant)addPlants.get(i);
        String plantId = "-1";
        if (plant.getPlant() != null)
          plantId = plant.getPlant().getAbbreviation(); 
        String plantSql = "sp_sav_Plant " + plant.getPlantID() + ",'" + 
          plant.getSelectionID() + "'," + 
          plantId + "," + 
          plant.getOrderQty() + "," + 
          plant.getCompletedQty() + "," + 
          plant.getReleaseID();
        JdbcConnector connectorAddPlant = MilestoneHelper.getConnector(plantSql);
        connectorAddPlant.runQuery();
        connectorAddPlant.close();
      } 
      JdbcConnector connectorDeletePlant = null;
      for (int k = 0; k < deletePlants.size(); k++) {
        Plant plant = (Plant)deletePlants.get(k);
        String plantDeleteSql = "sp_del_Plant " + plant.getPlantID();
        connectorDeletePlant = MilestoneHelper.getConnector(plantDeleteSql);
        connectorDeletePlant.runQuery();
        connectorDeletePlant.close();
      } 
    } 
  }
  
  public void updateTemplateId(Selection selection, User updatingUser) {
    String query = "UPDATE Release_Header SET templateId = " + selection.getTemplateId() + 
      " WHERE release_id = " + selection.getSelectionID();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void updateStatusToClose(Selection selection) {
    String query = "UPDATE Release_Header SET status = 'CLOSED' WHERE release_id = " + 
      selection.getSelectionID();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void updateComment(Selection selection) {
    String query = "UPDATE Release_Header SET comments = '" + 
      MilestoneHelper.escapeSingleQuotes(selection.getSelectionComments()) + "' " + 
      " WHERE release_id = " + selection.getSelectionID();
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public String getAssignedTemplateName(Selection selection) {
    if (selection == null)
      return ""; 
    String query = "sp_get_Schedule_Template_Header " + selection.getTemplateId();
    String templateName = "";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (connector.more())
      templateName = connector.getField("name", ""); 
    connector.close();
    return templateName;
  }
  
  public Selection saveManufacturingSelection(Selection selection, User updatingUser, boolean newFlag) {
    long timestamp = selection.getLastMfgUpdatedCheck();
    String spName = "sp_upd_Release_Subdetail";
    if (newFlag)
      spName = "sp_ins_Release_Subdetail"; 
    int umlContactId = 0;
    if (selection.getUmlContact() != null)
      umlContactId = selection.getUmlContact().getUserId(); 
    String plantId = "";
    if (selection.getPlant() != null)
      plantId = selection.getPlant().getAbbreviation(); 
    String distributionId = "";
    if (selection.getDistribution() != null)
      distributionId = selection.getDistribution().getAbbreviation(); 
    String query = String.valueOf(spName) + " " + 
      selection.getSelectionID() + "," + 
      umlContactId + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(plantId) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(distributionId) + "'," + 
      selection.getPoQuantity() + "," + 
      selection.getNumberOfUnits() + "," + 
      selection.getCompletedQuantity() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(selection.getManufacturingComments()) + "'," + 
      updatingUser.getUserId();
    if (!newFlag)
      query = String.valueOf(query) + "," + timestamp; 
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_subdetail WHERE release_id = " + 
      
      selection.getSelectionID() + ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      selection.setLastMfgUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      selection.setLastMfgUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
    } 
    connectorTimestamp.close();
    savePlants(selection, updatingUser);
    return selection;
  }
  
  public boolean deleteSelection(Selection selection, User updatingUser) {
    boolean isDeletable = true;
    int selectionId = selection.getSelectionID();
    if (selection.getSchedule() == null)
      selection.setSchedule(ScheduleManager.getInstance().getSchedule(selectionId)); 
    try {
      if (selection.getSchedule().getTasks().size() > 0)
        isDeletable = false; 
    } catch (Exception exception) {}
    if (isDeletable) {
      String query = "sp_del_Releases " + 
        selectionId;
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      connector.close();
      GDRS_QueueAddReleaseId(selection, "DELETE");
    } 
    return isDeletable;
  }
  
  public Pfm savePfm(Pfm pfm, User updatingUser, boolean isDigital) {
    long timestamp = pfm.getLastUpdatedCk();
    int userID = -1;
    if (updatingUser != null)
      userID = updatingUser.getUserId(); 
    String query = "sp_upd_PFM_Selection " + 
      pfm.getReleaseId() + ",'" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getReleaseType()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getMode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPrintOption()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPreparedBy()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getEmail()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPhone()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getFaxNumber()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getComments()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getOperatingCompany()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getProductNumber()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getConfigCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getModifier()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getTitle()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getArtist()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getTitleId()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getSuperLabel()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getLabelCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getCompanyCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPoMergeCode()) + "'," + 
      pfm.getUnitsPerSet() + "," + 
      pfm.getSetsPerCarton() + ",'" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getSupplier()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getImportIndicator()) + "','" + 
      
      MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getUpc(), "UPC", isDigital, true)) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getMusicLine()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireOwner()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireClass()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getReturnCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getExportFlag()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getCountries()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getSpineTitle()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getSpineArtist()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPriceCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getGuaranteeCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getLoosePickExemptCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getCompilationCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getImpRateCode()) + "','" + 
      
      MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(pfm.getMusicType())) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getNarmFlag()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPricePoint()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getApprovedByName()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getEnteredByName()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getVerifiedByName()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getChangeNumber()) + "','" + 
      MilestoneHelper.getFormatedDate(pfm.getStreetDate()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getProjectID()) + "'," + (
      pfm.getParentalGuidance() ? 1 : 0) + ",'" + 
      
      MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getSoundScanGrp(), "SSG", isDigital, true)) + "'," + 
      userID + "," + 
      timestamp + "," + (
      pfm.getValueAdded() ? 1 : 0) + "," + (
      pfm.getBoxSet() ? 1 : 0) + ",'" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getEncryptionFlag()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getStatus()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPriceCodeDPC()) + "'";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
    String timestampQuery = "SELECT last_updated_ck, last_updated_on, last_updated_by  FROM vi_PFM_Selection WHERE release_id = " + 
      
      pfm.getReleaseId() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      pfm.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      pfm.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
      pfm.setLastUpdatingUser(connectorTimestamp.getIntegerField("last_updated_by"));
    } 
    connectorTimestamp.close();
    return pfm;
  }
  
  public Pfm saveNewPfm(Pfm pfm, User updatingUser, boolean isDigital) {
    String query = "sp_ins_PFM_Selection " + 
      pfm.getReleaseId() + ",'" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getReleaseType()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getMode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPrintOption()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPreparedBy()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getEmail()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPhone()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getFaxNumber()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getComments()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getOperatingCompany()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getProductNumber()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getConfigCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getModifier()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getTitle()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getArtist()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getTitleId()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getSuperLabel()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getLabelCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getCompanyCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPoMergeCode()) + "'," + 
      pfm.getUnitsPerSet() + "," + 
      pfm.getSetsPerCarton() + ",'" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getSupplier()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getImportIndicator()) + "','" + 
      
      MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getUpc(), "UPC", isDigital, true)) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getMusicLine()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireOwner()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireClass()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getReturnCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getExportFlag()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getCountries()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getSpineTitle()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getSpineArtist()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPriceCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getGuaranteeCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getLoosePickExemptCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getCompilationCode()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getImpRateCode()) + "','" + 
      
      MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(pfm.getMusicType())) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getNarmFlag()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPricePoint()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getApprovedByName()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getEnteredByName()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getChangeNumber()) + "','" + 
      MilestoneHelper.getFormatedDate(pfm.getStreetDate()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getProjectID()) + "'," + (
      pfm.getParentalGuidance() ? 1 : 0) + ",'" + 
      
      MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getSoundScanGrp(), "SSG", isDigital, true)) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getVerifiedByName()) + "'," + 
      updatingUser.getUserId() + "," + (
      pfm.getValueAdded() ? 1 : 0) + "," + (
      pfm.getBoxSet() ? 1 : 0) + ",'" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getEncryptionFlag()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getStatus()) + "','" + 
      MilestoneHelper.escapeSingleQuotes(pfm.getPriceCodeDPC()) + "'";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (pfm.getReleaseId() != -1) {
      String lastUpdatedQuery = "SELECT last_updated_on, last_updated_by  FROM Pfm_Selection WHERE release_id = " + 
        
        pfm.getReleaseId() + 
        ";";
      JdbcConnector connectorLastUpdated = MilestoneHelper.getConnector(lastUpdatedQuery);
      connectorLastUpdated.runQuery();
      if (connectorLastUpdated.more()) {
        String lastDateString = connectorLastUpdated.getFieldByName("last_updated_on");
        if (lastDateString != null)
          pfm.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(lastDateString)); 
        pfm.setLastUpdatingUser(connectorLastUpdated.getIntegerField("last_updated_by"));
      } 
      connectorLastUpdated.close();
    } 
    connector.close();
    return pfm;
  }
  
  public Bom saveBom(Bom bom, Selection currentSelection, int userID) {
    long timestamp = bom.getLastUpdatedCheck();
    if (bom.getBomId() <= 0)
      bom.setBomId(-1); 
    String hasSpineSticker = "0";
    if (bom.hasSpineSticker())
      hasSpineSticker = "1"; 
    String useShrinkWrap = "0";
    if (bom.useShrinkWrap())
      useShrinkWrap = "1"; 
    String isRetail = "0";
    if (bom.isRetail())
      isRetail = "1"; 
    if (bom.getLabelId() <= 0) {
      int labelId = -1;
      if (currentSelection.getLabel() != null)
        labelId = currentSelection.getLabel().getStructureID(); 
      bom.setLabelId(labelId);
    } 
    String bomSelectionDate = MilestoneHelper.getFormatedDate(bom.getStreetDateOnBom());
    if (bomSelectionDate.equals("")) {
      bomSelectionDate = "null,";
    } else {
      bomSelectionDate = "'" + MilestoneHelper.getFormatedDate(bom.getStreetDateOnBom()) + "',";
    } 
    String query = "sp_sav_Bom_Header " + 
      bom.getBomId() + "," + 
      bom.getReleaseId() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getFormat()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getPrintOption()) + "'," + 
      "'" + MilestoneHelper.getFormatedDate(bom.getDate()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getType()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getChangeNumber()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bom.getSubmitter())) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getEmail()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getPhone()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getComments()) + "'," + 
      bom.getLabelId() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getReleasingCompanyId()) + "'," + 
      isRetail + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getSelectionNumber()) + "'," + 
      
      bomSelectionDate + 
      bom.getUnitsPerKG() + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getRunTime()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getConfiguration()) + "'," + 
      hasSpineSticker + "," + 
      useShrinkWrap + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bom.getSpecialInstructions())) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getArtist()) + "'," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getTitle()) + "'," + 
      userID + "," + 
      timestamp + "," + 
      "'" + MilestoneHelper.escapeSingleQuotes(bom.getStatus()) + "'," + 
      
      "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(bom.getUpc(), "UPC", currentSelection.getIsDigital(), true)) + "'";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    if (bom.getBomId() < 0)
      bom.setBomId(connector.getIntegerField("ReturnId")); 
    connector.close();
    String timestampQuery = "SELECT last_updated_ck, last_updated_on, last_updated_by  FROM bom_header WHERE bom_id = " + 
      
      bom.getBomId() + 
      ";";
    JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
    connectorTimestamp.runQuery();
    if (connectorTimestamp.more()) {
      bom.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
      bom.setModifiedBy(connectorTimestamp.getIntegerField("last_updated_by"));
      String modifiedOnString = connectorTimestamp.getFieldByName("last_updated_on");
      if (modifiedOnString != null)
        bom.setModifiedOn(MilestoneHelper.getDatabaseDate(modifiedOnString)); 
    } 
    connectorTimestamp.close();
    BomCassetteDetail cassetteDetail = bom.getBomCassetteDetail();
    if (cassetteDetail != null) {
      String coStatusIndicator = "0";
      if (cassetteDetail.coStatusIndicator)
        coStatusIndicator = "1"; 
      String sp = "sp_upd_Bom_Detail ";
      if (cassetteDetail.coPartId < 0)
        sp = "sp_ins_Bom_Detail "; 
      String queryCO = String.valueOf(sp) + 
        bom.getBomId() + "," + 
        "5" + "," + 
        cassetteDetail.coParSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.coInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.coInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.coColor) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.coInfo)) + "'," + 
        coStatusIndicator + "," + 
        userID;
      JdbcConnector connectorCO = MilestoneHelper.getConnector(queryCO);
      connectorCO.runQuery();
      connectorCO.close();
      String norelcoStatusIndicator = "0";
      if (cassetteDetail.norelcoStatusIndicator)
        norelcoStatusIndicator = "1"; 
      String sp1 = "sp_upd_Bom_Detail ";
      if (cassetteDetail.norelcoPartId < 0)
        sp1 = "sp_ins_Bom_Detail "; 
      String queryNorelco = String.valueOf(sp1) + 
        bom.getBomId() + "," + 
        "16" + "," + 
        cassetteDetail.norelcoSupplierId + "," + 
        "''," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.norelcoColor) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.norelcoInfo)) + "'," + 
        norelcoStatusIndicator + "," + 
        userID;
      JdbcConnector connectorNorelco = MilestoneHelper.getConnector(queryNorelco);
      connectorNorelco.runQuery();
      connectorNorelco.close();
      String jCardStatusIndicator = "0";
      if (cassetteDetail.jCardStatusIndicator)
        jCardStatusIndicator = "1"; 
      String sp2 = "sp_upd_Bom_Detail ";
      if (cassetteDetail.jCardPartId < 0)
        sp2 = "sp_ins_Bom_Detail "; 
      String queryJCARD = String.valueOf(sp2) + 
        bom.getBomId() + "," + 
        "13" + "," + 
        cassetteDetail.jCardSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.jCardInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.jCardInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.jCardPanels)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.jCardInfo)) + "'," + 
        jCardStatusIndicator + "," + 
        userID;
      JdbcConnector connectorJCARD = MilestoneHelper.getConnector(queryJCARD);
      connectorJCARD.runQuery();
      connectorJCARD.close();
      String uCardStatusIndicator = "0";
      if (cassetteDetail.uCardStatusIndicator)
        uCardStatusIndicator = "1"; 
      String sp3 = "sp_upd_Bom_Detail ";
      if (cassetteDetail.uCardPartId < 0)
        sp3 = "sp_ins_Bom_Detail "; 
      String queryUCARD = String.valueOf(sp3) + 
        bom.getBomId() + "," + 
        "24" + "," + 
        cassetteDetail.uCardSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.uCardInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.uCardInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.uCardPanels)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.uCardInfo)) + "'," + 
        uCardStatusIndicator + "," + 
        userID;
      JdbcConnector connectorUCARD = MilestoneHelper.getConnector(queryUCARD);
      connectorUCARD.runQuery();
      connectorUCARD.close();
      String oCardStatusIndicator = "0";
      if (cassetteDetail.oCardStatusIndicator)
        oCardStatusIndicator = "1"; 
      String sp4 = "sp_upd_Bom_Detail ";
      if (cassetteDetail.oCardPartId < 0)
        sp4 = "sp_ins_Bom_Detail "; 
      String queryOCARD = String.valueOf(sp4) + 
        bom.getBomId() + "," + 
        "17" + "," + 
        cassetteDetail.oCardSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.oCardInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.oCardInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.oCardInfo)) + "'," + 
        oCardStatusIndicator + "," + 
        userID;
      JdbcConnector connectorOCARD = MilestoneHelper.getConnector(queryOCARD);
      connectorOCARD.runQuery();
      connectorOCARD.close();
      String stickerOneCardStatusIndicator = "0";
      if (cassetteDetail.stickerOneCardStatusIndicator)
        stickerOneCardStatusIndicator = "1"; 
      String sp5 = "sp_upd_Bom_Detail ";
      if (cassetteDetail.stickerOneCardPartId < 0)
        sp5 = "sp_ins_Bom_Detail "; 
      String queryStickerOne = String.valueOf(sp5) + 
        bom.getBomId() + "," + 
        "21" + "," + 
        cassetteDetail.stickerOneCardSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerOneCardInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerOneCardInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerOneCardPlaces)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerOneCardInfo)) + "'," + 
        stickerOneCardStatusIndicator + "," + 
        userID;
      JdbcConnector connectorSticker1 = MilestoneHelper.getConnector(queryStickerOne);
      connectorSticker1.runQuery();
      connectorSticker1.close();
      String stickerTwoCardStatusIndicator = "0";
      if (cassetteDetail.stickerTwoCardStatusIndicator)
        stickerTwoCardStatusIndicator = "1"; 
      String sp6 = "sp_upd_Bom_Detail ";
      if (cassetteDetail.stickerTwoCardPartId < 0)
        sp6 = "sp_ins_Bom_Detail "; 
      String queryStickerTwo = String.valueOf(sp6) + 
        bom.getBomId() + "," + 
        "22" + "," + 
        cassetteDetail.stickerTwoCardSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerTwoCardInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerTwoCardInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerTwoCardPlaces)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerTwoCardInfo)) + "'," + 
        stickerTwoCardStatusIndicator + "," + 
        userID;
      JdbcConnector connectorSticker2 = MilestoneHelper.getConnector(queryStickerTwo);
      connectorSticker2.runQuery();
      connectorSticker2.close();
      String otherStatusIndicator = "0";
      if (cassetteDetail.otherStatusIndicator)
        otherStatusIndicator = "1"; 
      String sp7 = "sp_upd_Bom_Detail ";
      if (cassetteDetail.otherPartId < 0)
        sp7 = "sp_ins_Bom_Detail "; 
      String queryOther = String.valueOf(sp7) + 
        bom.getBomId() + "," + 
        "18" + "," + 
        cassetteDetail.otherSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.otherInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.otherInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.otherInfo)) + "'," + 
        otherStatusIndicator + "," + 
        userID;
      JdbcConnector connectorOther = MilestoneHelper.getConnector(queryOther);
      connectorOther.runQuery();
      connectorOther.close();
    } 
    if (bom.getBomDiskDetail() != null || bom.getBomDVDDetail() != null) {
      BomDVDDetail bomDVDDetail;
      if (bom.getBomDiskDetail() != null) {
        bomDVDDetail = bom.getBomDiskDetail();
      } else {
        bomDVDDetail = bom.getBomDVDDetail();
      } 
      String DVDDiscSelectionText = "";
      String DVDCaseSelectionText = "";
      String blueRayCaseSelectionText = "";
      if (bom.getBomDVDDetail() != null) {
        DVDDiscSelectionText = bom.getBomDVDDetail().getDiscSelectionInfo();
        DVDCaseSelectionText = bom.getBomDVDDetail().getDVDSelectionInfo();
        String dvdVideoStatusIndicator = "0";
        if ((bom.getBomDVDDetail()).dvdStatusIndicator)
          dvdVideoStatusIndicator = "1"; 
        String spDVDCase = "sp_upd_Bom_Detail ";
        if ((bom.getBomDVDDetail()).dvdCasePartId < 0)
          spDVDCase = "sp_ins_Bom_Detail "; 
        String query2DVDCase = String.valueOf(spDVDCase) + 
          bom.getBomId() + "," + 
          "30" + "," + 
          "''," + 
          "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).dvdInk1) + 
          "'," + 
          "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).dvdInk2) + 
          "'," + 
          "''," + 
          "'" + MilestoneHelper.escapeSingleQuotes(DVDCaseSelectionText) + "'," + 
          "'" + 
          MilestoneHelper.escapeSingleQuotes(
            MilestoneHelper.escapeDoubleQuotesForHtml(
              (bom.getBomDVDDetail())
              .dvdInfo)) + "'," + 
          dvdVideoStatusIndicator + "," + 
          userID;
        JdbcConnector connector2DVDCase = MilestoneHelper.getConnector(query2DVDCase);
        connector2DVDCase.runQuery();
        connector2DVDCase.close();
        blueRayCaseSelectionText = bom.getBomDVDDetail().getBluRaySelectionInfo();
        String bluRayStatusIndicator = "0";
        if ((bom.getBomDVDDetail()).bluRayStatusIndicator)
          bluRayStatusIndicator = "1"; 
        String spBluRayCase = "sp_upd_Bom_Detail ";
        if ((bom.getBomDVDDetail()).bluRayCasePartId < 0)
          spBluRayCase = "sp_ins_Bom_Detail "; 
        String query2BluRayCase = String.valueOf(spBluRayCase) + 
          bom.getBomId() + "," + 
          "32" + "," + 
          "''," + 
          "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).bluRayInk1) + 
          "'," + 
          "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).bluRayInk2) + 
          "'," + 
          "''," + 
          "'" + MilestoneHelper.escapeSingleQuotes(blueRayCaseSelectionText) + "'," + 
          "'" + 
          MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml((bom.getBomDVDDetail()).bluRayInfo)) + "'," + bluRayStatusIndicator + "," + userID;
        JdbcConnector connector2BluRayCase = MilestoneHelper.getConnector(query2BluRayCase);
        connector2BluRayCase.runQuery();
        connector2BluRayCase.close();
        String wrapStatusIndicator = "0";
        if ((bom.getBomDVDDetail()).wrapStatusIndicator)
          wrapStatusIndicator = "1"; 
        String spWrap = "sp_upd_Bom_Detail ";
        if ((bom.getBomDVDDetail()).wrapPartId < 0)
          spWrap = "sp_ins_Bom_Detail "; 
        String query2Wrap = String.valueOf(spWrap) + 
          bom.getBomId() + "," + 
          "29" + "," + 
          (bom.getBomDVDDetail()).wrapSupplierId + "," + 
          "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).wrapInk1) + "'," + 
          "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).wrapInk2) + "'," + 
          "''," + 
          "''," + 
          "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml((bom.getBomDVDDetail()).wrapInfo)) + "'," + 
          wrapStatusIndicator + "," + 
          userID;
        JdbcConnector connector2Wrap = MilestoneHelper.getConnector(query2Wrap);
        connector2Wrap.runQuery();
        connector2Wrap.close();
      } 
      String discStatusIndicator = "0";
      if (bomDVDDetail.discStatusIndicator)
        discStatusIndicator = "1"; 
      String sp8 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.discPartId < 0)
        sp8 = "sp_ins_Bom_Detail "; 
      String query2Disk = String.valueOf(sp8) + 
        bom.getBomId() + "," + 
        "7" + "," + 
        bomDVDDetail.diskSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.discInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.discInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(DVDDiscSelectionText) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.discInfo)) + "'," + 
        discStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Disk = MilestoneHelper.getConnector(query2Disk);
      connector2Disk.runQuery();
      connector2Disk.close();
      String jewelStatusIndicator = "0";
      if (bomDVDDetail.jewelStatusIndicator)
        jewelStatusIndicator = "1"; 
      String sp9 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.jewelPartId < 0)
        sp9 = "sp_ins_Bom_Detail "; 
      String query2Jewel = String.valueOf(sp9) + 
        bom.getBomId() + "," + 
        "12" + "," + 
        "''," + 
        "''," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.jewelColor) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.jewelInfo)) + "'," + 
        jewelStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Jewel = MilestoneHelper.getConnector(query2Jewel);
      connector2Jewel.runQuery();
      connector2Jewel.close();
      String trayStatusIndicator = "0";
      if (bomDVDDetail.trayStatusIndicator)
        trayStatusIndicator = "1"; 
      String sp10 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.trayPartId < 0)
        sp10 = "sp_ins_Bom_Detail "; 
      String query2Tray = String.valueOf(sp10) + 
        bom.getBomId() + "," + 
        "23" + "," + 
        "''," + 
        "''," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.trayColor) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.trayInfo)) + "'," + 
        trayStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Tray = MilestoneHelper.getConnector(query2Tray);
      connector2Tray.runQuery();
      connector2Tray.close();
      String inlayStatusIndicator = "0";
      if (bomDVDDetail.inlayStatusIndicator)
        inlayStatusIndicator = "1"; 
      String sp11 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.inlayPartId < 0)
        sp11 = "sp_ins_Bom_Detail "; 
      String query2Inlay = String.valueOf(sp11) + 
        bom.getBomId() + "," + 
        "10" + "," + 
        bomDVDDetail.inlaySupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.inlayInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.inlayInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.inlayInfo)) + "'," + 
        inlayStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Inlay = MilestoneHelper.getConnector(query2Inlay);
      connector2Inlay.runQuery();
      connector2Inlay.close();
      String frontStatusIndicator = "0";
      if (bomDVDDetail.frontStatusIndicator)
        frontStatusIndicator = "1"; 
      String sp12 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.frontPartId < 0)
        sp12 = "sp_ins_Bom_Detail "; 
      String query2Front = String.valueOf(sp12) + 
        bom.getBomId() + "," + 
        "9" + "," + 
        bomDVDDetail.frontSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.frontInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.frontInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.frontInfo)) + "'," + 
        frontStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Front = MilestoneHelper.getConnector(query2Front);
      connector2Front.runQuery();
      connector2Front.close();
      String folderStatusIndicator = "0";
      if (bomDVDDetail.folderStatusIndicator)
        folderStatusIndicator = "1"; 
      String sp13 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.folderPartId < 0)
        sp13 = "sp_ins_Bom_Detail "; 
      String query2Folder = String.valueOf(sp13) + 
        bom.getBomId() + "," + 
        "8" + "," + 
        bomDVDDetail.folderSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.folderInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.folderInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.folderPages) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.folderInfo)) + "'," + 
        folderStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Folder = MilestoneHelper.getConnector(query2Folder);
      connector2Folder.runQuery();
      connector2Folder.close();
      String bookletStatusIndicator = "0";
      if (bomDVDDetail.bookletStatusIndicator)
        bookletStatusIndicator = "1"; 
      String sp14 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.bookletPartId < 0)
        sp14 = "sp_ins_Bom_Detail "; 
      String query2Booklet = String.valueOf(sp14) + 
        bom.getBomId() + "," + 
        "1" + "," + 
        bomDVDDetail.bookletSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookletInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookletInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookletPages) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.bookletInfo)) + "'," + 
        bookletStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Booklet = MilestoneHelper.getConnector(query2Booklet);
      connector2Booklet.runQuery();
      connector2Booklet.close();
      String brcStatusIndicator = "0";
      if (bomDVDDetail.brcStatusIndicator)
        brcStatusIndicator = "1"; 
      String sp15 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.brcPartId < 0)
        sp15 = "sp_ins_Bom_Detail "; 
      String query2Brc = String.valueOf(sp15) + 
        bom.getBomId() + "," + 
        "4" + "," + 
        bomDVDDetail.brcSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.brcInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.brcInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.brcSize)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.brcInfo)) + "'," + 
        brcStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Brc = MilestoneHelper.getConnector(query2Brc);
      connector2Brc.runQuery();
      connector2Brc.close();
      String miniStatusIndicator = "0";
      if (bomDVDDetail.miniStatusIndicator)
        miniStatusIndicator = "1"; 
      String sp16 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.miniPartId < 0)
        sp16 = "sp_ins_Bom_Detail "; 
      String query2Mini = String.valueOf(sp16) + 
        bom.getBomId() + "," + 
        "15" + "," + 
        bomDVDDetail.miniSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.miniInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.miniInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.miniInfo)) + "'," + 
        miniStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Mini = MilestoneHelper.getConnector(query2Mini);
      connector2Mini.runQuery();
      connector2Mini.close();
      String digiPakStatusIndicator = "0";
      if (bomDVDDetail.digiPakStatusIndicator)
        digiPakStatusIndicator = "1"; 
      String sp17 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.digiPakPartId < 0)
        sp17 = "sp_ins_Bom_Detail "; 
      String query2Digi = String.valueOf(sp17) + 
        bom.getBomId() + "," + 
        "6" + "," + 
        bomDVDDetail.digiPakSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.digiPakInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.digiPakInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.digiPakTray)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.digiPakInfo)) + "'," + 
        digiPakStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Digi = MilestoneHelper.getConnector(query2Digi);
      connector2Digi.runQuery();
      connector2Digi.close();
      String softPakStatusIndicator = "0";
      if (bomDVDDetail.softPakStatusIndicator)
        softPakStatusIndicator = "1"; 
      String sp31 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.softPakPartId < 0)
        sp31 = "sp_ins_Bom_Detail "; 
      String query2softPak = String.valueOf(sp31) + 
        bom.getBomId() + "," + 
        "31" + "," + 
        bomDVDDetail.softPakSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.softPakInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.softPakInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.softPakTray)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.softPakInfo)) + "'," + 
        softPakStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Soft = MilestoneHelper.getConnector(query2softPak);
      connector2Soft.runQuery();
      connector2Soft.close();
      String stickerOneStatusIndicator = "0";
      if (bomDVDDetail.stickerOneStatusIndicator)
        stickerOneStatusIndicator = "1"; 
      String sp18 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.stickerOnePartId < 0)
        sp18 = "sp_ins_Bom_Detail "; 
      String query2StickerOne = String.valueOf(sp18) + 
        bom.getBomId() + "," + 
        "21" + "," + 
        bomDVDDetail.stickerOneSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerOneInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerOneInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerOnePlaces)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerOneInfo)) + "'," + 
        stickerOneStatusIndicator + "," + 
        userID;
      JdbcConnector connector2StickerOne = MilestoneHelper.getConnector(query2StickerOne);
      connector2StickerOne.runQuery();
      connector2StickerOne.close();
      String stickerTwoStatusIndicator = "0";
      if (bomDVDDetail.stickerTwoStatusIndicator)
        stickerTwoStatusIndicator = "1"; 
      String sp19 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.stickerTwoPartId < 0)
        sp19 = "sp_ins_Bom_Detail "; 
      String query2StickerTwo = String.valueOf(sp19) + 
        bom.getBomId() + "," + 
        "22" + "," + 
        bomDVDDetail.stickerTwoSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerTwoInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerTwoInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerTwoPlaces)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerTwoInfo)) + "'," + 
        stickerTwoStatusIndicator + "," + 
        userID;
      JdbcConnector connector2StickerTwo = MilestoneHelper.getConnector(query2StickerTwo);
      connector2StickerTwo.runQuery();
      connector2StickerTwo.close();
      String bookStatusIndicator = "0";
      if (bomDVDDetail.bookStatusIndicator)
        bookStatusIndicator = "1"; 
      String sp20 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.bookPartId < 0)
        sp20 = "sp_ins_Bom_Detail "; 
      String query2Book = String.valueOf(sp20) + 
        bom.getBomId() + "," + 
        "2" + "," + 
        bomDVDDetail.bookSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.bookPages)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.bookInfo)) + "'," + 
        bookStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Book = MilestoneHelper.getConnector(query2Book);
      connector2Book.runQuery();
      connector2Book.close();
      String boxStatusIndicator = "0";
      if (bomDVDDetail.boxStatusIndicator)
        boxStatusIndicator = "1"; 
      String sp21 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.boxPartId < 0)
        sp21 = "sp_ins_Bom_Detail "; 
      String query2Box = String.valueOf(sp21) + 
        bom.getBomId() + "," + 
        "3" + "," + 
        bomDVDDetail.boxSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.boxInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.boxInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.boxSizes)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.boxInfo)) + "'," + 
        boxStatusIndicator + "," + 
        userID;
      JdbcConnector connector2Box = MilestoneHelper.getConnector(query2Box);
      connector2Box.runQuery();
      connector2Box.close();
      String otherStatusIndicatorDisc = "0";
      if (bomDVDDetail.otherStatusIndicator)
        otherStatusIndicatorDisc = "1"; 
      String sp22 = "sp_upd_Bom_Detail ";
      if (bomDVDDetail.otherPartId < 0)
        sp22 = "sp_ins_Bom_Detail "; 
      String query2Other = String.valueOf(sp22) + 
        bom.getBomId() + "," + 
        "18" + "," + 
        bomDVDDetail.otherSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.otherInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.otherInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.otherInfo)) + "'," + 
        otherStatusIndicatorDisc + "," + 
        userID;
      JdbcConnector connector2Other = MilestoneHelper.getConnector(query2Other);
      connector2Other.runQuery();
      connector2Other.close();
    } 
    BomVinylDetail vinylDetail = bom.getBomVinylDetail();
    if (vinylDetail != null) {
      String recordStatusIndicator = "0";
      if (vinylDetail.recordStatusIndicator)
        recordStatusIndicator = "1"; 
      String sp23 = "sp_upd_Bom_Detail ";
      if (vinylDetail.recordPartId < 0)
        sp23 = "sp_ins_Bom_Detail "; 
      String query3Record = String.valueOf(sp23) + 
        bom.getBomId() + "," + 
        "19" + "," + 
        vinylDetail.recordSupplierId + "," + 
        "''," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.recordColor) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.recordInfo)) + "'," + 
        recordStatusIndicator + "," + 
        userID;
      JdbcConnector connector3Record = MilestoneHelper.getConnector(query3Record);
      connector3Record.runQuery();
      connector3Record.close();
      String labelStatusIndicator = "0";
      if (vinylDetail.labelStatusIndicator)
        labelStatusIndicator = "1"; 
      String sp24 = "sp_upd_Bom_Detail ";
      if (vinylDetail.labelPartId < 0)
        sp24 = "sp_ins_Bom_Detail "; 
      String query3Label = String.valueOf(sp24) + 
        bom.getBomId() + "," + 
        "14" + "," + 
        vinylDetail.labelSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.labelInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.labelInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.labelInfo)) + "'," + 
        labelStatusIndicator + "," + 
        userID;
      JdbcConnector connector3Label = MilestoneHelper.getConnector(query3Label);
      connector3Label.runQuery();
      connector3Label.close();
      String sleeveStatusIndicator = "0";
      if (vinylDetail.sleeveStatusIndicator)
        sleeveStatusIndicator = "1"; 
      String sp25 = "sp_upd_Bom_Detail ";
      if (vinylDetail.sleevePartId < 0)
        sp25 = "sp_ins_Bom_Detail "; 
      String query3Sleeve = String.valueOf(sp25) + 
        bom.getBomId() + "," + 
        "20" + "," + 
        vinylDetail.sleeveSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.sleeveInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.sleeveInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.sleeveInfo)) + "'," + 
        sleeveStatusIndicator + "," + 
        userID;
      JdbcConnector connector3Sleeve = MilestoneHelper.getConnector(query3Sleeve);
      connector3Sleeve.runQuery();
      connector3Sleeve.close();
      String jacketStatusIndicator = "0";
      if (vinylDetail.jacketStatusIndicator)
        jacketStatusIndicator = "1"; 
      String sp26 = "sp_upd_Bom_Detail ";
      if (vinylDetail.jacketPartId < 0)
        sp26 = "sp_ins_Bom_Detail "; 
      String query3Jacket = String.valueOf(sp26) + 
        bom.getBomId() + "," + 
        "11" + "," + 
        vinylDetail.jacketSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.jacketInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.jacketInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.jacketInfo)) + "'," + 
        jacketStatusIndicator + "," + 
        userID;
      JdbcConnector connector3Jacket = MilestoneHelper.getConnector(query3Jacket);
      connector3Jacket.runQuery();
      connector3Jacket.close();
      String insertStatusIndicator = "0";
      if (vinylDetail.insertStatusIndicator)
        insertStatusIndicator = "1"; 
      String sp33 = "sp_upd_Bom_Detail ";
      if (vinylDetail.insertPartId < 0)
        sp33 = "sp_ins_Bom_Detail "; 
      String query3Insert = String.valueOf(sp33) + 
        bom.getBomId() + "," + 
        "33" + "," + 
        vinylDetail.insertSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.insertInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.insertInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.insertInfo)) + "'," + 
        insertStatusIndicator + "," + 
        userID;
      JdbcConnector connector3Insert = MilestoneHelper.getConnector(query3Insert);
      connector3Insert.runQuery();
      connector3Insert.close();
      String stickerOneStatusIndicatorVinyl = "0";
      if (vinylDetail.stickerOneStatusIndicator)
        stickerOneStatusIndicatorVinyl = "1"; 
      String sp27 = "sp_upd_Bom_Detail ";
      if (vinylDetail.stickerOnePartId < 0)
        sp27 = "sp_ins_Bom_Detail "; 
      String query3StickerOne = String.valueOf(sp27) + 
        bom.getBomId() + "," + 
        "21" + "," + 
        vinylDetail.stickerOneSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerOneInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerOneInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerOnePlaces)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerOneInfo)) + "'," + 
        stickerOneStatusIndicatorVinyl + "," + 
        userID;
      JdbcConnector connector3StickerOne = MilestoneHelper.getConnector(query3StickerOne);
      connector3StickerOne.runQuery();
      connector3StickerOne.close();
      String stickerTwoStatusIndicatorVinyl = "0";
      if (vinylDetail.stickerTwoStatusIndicator)
        stickerTwoStatusIndicatorVinyl = "1"; 
      String sp28 = "sp_upd_Bom_Detail ";
      if (vinylDetail.stickerTwoPartId < 0)
        sp28 = "sp_ins_Bom_Detail "; 
      String query3StickerTwo = String.valueOf(sp28) + 
        bom.getBomId() + "," + 
        "22" + "," + 
        vinylDetail.stickerTwoSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerTwoInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerTwoInk2) + "'," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerTwoPlaces)) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerTwoInfo)) + "'," + 
        stickerTwoStatusIndicatorVinyl + "," + 
        userID;
      JdbcConnector connector3StickerTwo = MilestoneHelper.getConnector(query3StickerTwo);
      connector3StickerTwo.runQuery();
      connector3StickerTwo.close();
      String otherStatusIndicatorVinyl = "0";
      if (vinylDetail.otherStatusIndicator)
        otherStatusIndicatorVinyl = "1"; 
      String sp29 = "sp_upd_Bom_Detail ";
      if (vinylDetail.otherPartId < 0)
        sp29 = "sp_ins_Bom_Detail "; 
      String query3Other = String.valueOf(sp29) + 
        bom.getBomId() + "," + 
        "18" + "," + 
        vinylDetail.otherSupplierId + "," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.otherInk1) + "'," + 
        "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.otherInk2) + "'," + 
        "''," + 
        "''," + 
        "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.otherInfo)) + "'," + 
        otherStatusIndicatorVinyl + "," + 
        userID;
      JdbcConnector connector3Other = MilestoneHelper.getConnector(query3Other);
      connector3Other.runQuery();
      connector3Other.close();
    } 
    return bom;
  }
  
  public Vector getSelectionNotepadList(int UserId, Notepad notepad, Context context) {
    Vector companies = MilestoneHelper.getUserCompanies(context);
    Company company = null;
    Vector precache = new Vector();
    Selection selection = null;
    String query = "";
    String queryReset = "";
    int maxRecords = 225;
    if (notepad != null)
      maxRecords = notepad.getMaxRecords(); 
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      queryReset = new String(query);
      query = String.valueOf(query) + notepad.getOrderBy();
    } else {
      query = String.valueOf(getDefaultQuery(context)) + " ORDER BY artist, title, selection_no, street_date ";
      queryReset = new String(query);
    } 
    User user = (User)context.getSessionValue("user");
    String userDefaultsApplied = (String)context.getSessionValue("UserDefaultsApplied");
    if (userDefaultsApplied != null && userDefaultsApplied.equalsIgnoreCase("true")) {
      user.SS_searchInitiated = false;
      if (notepad != null) {
        notepad.setSelected(null);
        notepad.setAllContents(null);
        Notepad schNotepad = MilestoneHelper.getNotepadFromSession(1, context);
        if (schNotepad != null) {
          schNotepad.setSelected(null);
          schNotepad.setAllContents(null);
        } 
      } 
      context.removeSessionValue("UserDefaultsApplied");
    } 
    if (user != null && !user.SS_searchInitiated) {
      query = getInstance().getSelectionNotepadQueryUserDefaults(context);
      queryReset = new String(query);
      String order = getInstance().getSelectionNotepadQueryUserDefaultsOrderBy(context);
      if (notepad != null) {
        notepad.setSearchQuery(query);
        notepad.setOrderBy(order);
      } 
      query = String.valueOf(query) + order;
    } 
    if (user != null && user.SS_searchInitiated && context.getSessionValue("ResetSelectionSortOrder") != null) {
      String orderReset = getInstance().getSelectionNotepadQueryUserDefaultsOrderBy(context);
      if (notepad != null) {
        notepad.setSearchQuery(queryReset);
        notepad.setOrderBy(orderReset);
      } 
      query = String.valueOf(queryReset) + orderReset;
      user.SS_searchInitiated = true;
      context.removeSessionValue("ResetSelectionSortOrder");
    } 
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.setMaxRows(maxRecords);
    connector.runQuery();
    while (connector.more()) {
      selection = new Selection();
      selection.setSelectionID(connector.getIntegerField("release_id"));
      selection.setTitle(connector.getField("title", ""));
      selection.setArtist(connector.getField("artist", ""));
      selection.setUpc(connector.getField("upc", ""));
      selection.setSelectionConfig(
          getSelectionConfigObject(connector.getField("configuration"), 
            Cache.getSelectionConfigs()));
      selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
      selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
      selection.setSelectionNo(connector.getField("selection_no"));
      selection.setIsDigital(connector.getBoolean("digital_flag"));
      if (selection.getIsDigital()) {
        String digitalReleaseDateString = connector.getFieldByName("street_date");
        if (digitalReleaseDateString != null) {
          selection.setDigitalRlsDateString(digitalReleaseDateString);
          selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(
                digitalReleaseDateString));
        } 
      } else {
        String streetDateString = connector.getFieldByName("street_date");
        if (streetDateString != null) {
          selection.setStreetDateString(streetDateString);
          selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
        } 
      } 
      selection.setArchimedesId(connector.getInt("Archimedes_id", -1));
      selection.setReleaseFamilyId(connector.getInt("Release_Family_id", -1));
      precache.add(selection);
      selection = null;
      connector.next();
    } 
    connector.close();
    company = null;
    if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY title, artist, selection_no, street_date"))
      if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY street_date, artist, title, selection_no"))
        if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY street_date DESC, artist, title, selection_no"))
          if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY title DESC, artist, selection_no, street_date"))
            if (notepad != null)
              notepad.getOrderBy().equalsIgnoreCase(" ORDER BY artist DESC, title, selection_no, street_date");     
    return precache;
  }
  
  public int getNotepadTotalCount(Notepad notepad, Context context) {
    int count = 0;
    String query = "";
    if (notepad != null && !notepad.getSearchQuery().equals("")) {
      query = notepad.getSearchQuery();
      query = String.valueOf(query) + notepad.getOrderBy();
    } else {
      query = String.valueOf(getDefaultQuery(context)) + " ORDER BY artist, title, selection_no, street_date ";
    } 
    String newCountQuery = "";
    int start = query.toUpperCase().indexOf("SELECT");
    int end = query.toUpperCase().indexOf("FROM") - 1;
    int endLength = query.toUpperCase().indexOf("ORDER BY") - 1;
    try {
      newCountQuery = String.valueOf(query.substring(start, start + 6)) + " count(*) as totalRecords " + query.substring(end, endLength);
      JdbcConnector connector = MilestoneHelper.getConnector(newCountQuery);
      connector.runQuery();
      if (connector.more())
        count = connector.getIntegerField("totalRecords"); 
      connector.close();
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
    return count;
  }
  
  public void setSelectionNotepadQuery(Context context, int UserId, Notepad notepad, Form form) {
    User user = (User)context.getSessionValue("user");
    if (notepad != null)
      setSelectionNotepadQueryHelper(context, notepad, form, user); 
  }
  
  public void setSelectionNotepadQueryHelper(Context context, Notepad notepad, Form form, User user) {
    String artistSearch = context.getParameter("macArtistSearch");
    String titleSearch = context.getParameter("macTitleSearch");
    String selectionNoSearch = context.getParameter("macSelectionSearch");
    String prefixIDSearch = context.getParameter("macPrefixSearch");
    String upcSearch = context.getParameter("macUPCSearch");
    upcSearch = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(upcSearch, "UPC", false, false);
    String streetDateSearch = "";
    String streetDateSearch2 = context.getParameter("macStreetDateSearch");
    if (streetDateSearch2 != null && !streetDateSearch2.equals("")) {
      StringTokenizer st = new StringTokenizer(streetDateSearch2, "/");
      String token = "";
      while (st.hasMoreTokens()) {
        token = st.nextToken();
        if (!token.equals("*") && token.length() == 1)
          token = "0" + token; 
        if (streetDateSearch.length() == 0) {
          streetDateSearch = String.valueOf(streetDateSearch) + token;
          continue;
        } 
        streetDateSearch = String.valueOf(streetDateSearch) + "/" + token;
      } 
    } 
    String streetEndDateSearch = "";
    String streetEndDateSearch2 = context.getParameter("macStreetEndDateSearch");
    if (streetEndDateSearch2 != null && !streetEndDateSearch2.equals("")) {
      StringTokenizer st = new StringTokenizer(streetEndDateSearch2, "/");
      String token = "";
      while (st.hasMoreTokens()) {
        token = st.nextToken();
        if (!token.equals("*") && token.length() == 1)
          token = "0" + token; 
        if (streetEndDateSearch.length() == 0) {
          streetEndDateSearch = String.valueOf(streetEndDateSearch) + token;
          continue;
        } 
        streetEndDateSearch = String.valueOf(streetEndDateSearch) + "/" + token;
      } 
    } 
    String configSearch = context.getParameter("ConfigSearch");
    String subconfigSearch = context.getParameter("macSubconfigSearch");
    String showAllSearch = context.getParameter("ShowAllSearch");
    String contactSearch = context.getParameter("ContactSearch");
    String familySearch = "";
    if (context.getParameter("FamilySearch") == null || context.getParameter("FamilySearch").equals("") || context.getParameter("FamilySearch").equals("0") || context.getParameter("FamilySearch").equals("-1")) {
      FormDropDownMenu familyDD = (FormDropDownMenu)form.getElement("FamilySearch");
      for (int f = 0; f < familyDD.getValueList().length; f++) {
        if (f == 0) {
          familySearch = String.valueOf(familySearch) + familyDD.getValueList()[f];
        } else {
          familySearch = String.valueOf(familySearch) + "," + familyDD.getValueList()[f];
        } 
      } 
    } else {
      familySearch = context.getParameter("FamilySearch");
    } 
    String environmentSearch = "";
    environmentSearch = context.getParameter("EnvironmentSearch");
    String productType = context.getParameter("ProdType");
    String productTypeSearch = "";
    if (productType.equalsIgnoreCase("physical")) {
      productTypeSearch = "0";
    } else if (productType.equalsIgnoreCase("digital")) {
      productTypeSearch = "1";
    } 
    String companyVar = context.getParameter("company");
    String companyTypeSearch = "";
    String labelSearch = "";
    if (productType.equalsIgnoreCase("All")) {
      labelSearch = "0";
    } else if (productType.equalsIgnoreCase("Select")) {
      labelSearch = context.getParameter("macLabelSearch");
    } 
    String labelVar = context.getParameter("Label");
    String labelTypeSearch = "";
    String companySearch = "";
    if (productType.equalsIgnoreCase("All")) {
      companySearch = "0";
    } else if (productType.equalsIgnoreCase("Select")) {
      companySearch = context.getParameter("macCompanySearch");
    } 
    String projectIDSearch = "";
    projectIDSearch = context.getParameter("ProjectIDSearch");
    boolean startsWithAsterisk = false;
    boolean endsWithAsterisk = false;
    boolean containsNA = false;
    if (streetDateSearch.indexOf("*") > -1)
      startsWithAsterisk = true; 
    if (streetEndDateSearch.indexOf("*") > -1)
      endsWithAsterisk = true; 
    if (streetDateSearch.toUpperCase().indexOf("N/A") > -1)
      containsNA = true; 
    if (selectionNoSearch.length() > 0) {
      context.putSessionValue("selectionNotepadColumn", "1");
    } else if (upcSearch.length() > 0) {
      context.putSessionValue("selectionNotepadColumn", "2");
    } else if (prefixIDSearch.length() > 0) {
      context.putSessionValue("selectionNotepadColumn", "3");
    } else {
      context.removeSessionValue("selectionNotepadColumn");
    } 
    if (user != null && user.getPreferences() != null && !user.SS_searchInitiated) {
      if (user.getPreferences().getSelectionStatus() > 0) {
        showAllSearch = "true";
        user.SS_showAllSearch = "true";
      } 
      if (user.getPreferences().getSelectionReleasingFamily() > 0) {
        familySearch = String.valueOf(user.getPreferences().getSelectionReleasingFamily());
        user.SS_familySearch = familySearch;
      } 
      if (user.getPreferences().getSelectionEnvironment() > 0) {
        environmentSearch = String.valueOf(user.getPreferences().getSelectionEnvironment());
        user.SS_environmentSearch = environmentSearch;
      } 
      if (user.getPreferences().getSelectionLabelContact() > 0) {
        contactSearch = String.valueOf(user.getPreferences().getSelectionLabelContact());
        user.SS_contactSearch = contactSearch;
      } 
      if (user.getPreferences().getSelectionProductType() > -1) {
        productTypeSearch = "";
        user.SS_productTypeSearch = "";
        if (user.getPreferences().getSelectionProductType() == 0) {
          productTypeSearch = "0";
          user.SS_productTypeSearch = "physical";
        } 
        if (user.getPreferences().getSelectionProductType() == 1) {
          productTypeSearch = "1";
          user.SS_productTypeSearch = "digital";
        } 
        if (user.getPreferences().getSelectionProductType() == 2)
          user.SS_productTypeSearch = "both"; 
      } 
      UserManager.getInstance().setUserPreferenceReleaseCalendar(user);
    } 
    StringBuffer query = new StringBuffer();
    query.append("SELECT release_id, title, artist, configuration, sub_configuration, upc, prefix, selection_no, digital_flag, street_date =  CASE digital_flag  WHEN 0 THEN street_date   WHEN 1 THEN digital_rls_date  END ,archimedes_id, Release_Family_id from vi_Release_Header WHERE ");
    if (environmentSearch == null || environmentSearch.equals("") || environmentSearch.equals("0") || environmentSearch.equals("-1")) {
      FormDropDownMenu envDD = (FormDropDownMenu)form.getElement(
          "EnvironmentSearch");
      query.append(" environment_id in (");
      for (int f = 0; f < envDD.getValueList().length; f++) {
        if (f == 0) {
          query.append(envDD.getValueList()[f]);
        } else {
          query.append("," + envDD.getValueList()[f]);
        } 
      } 
      query.append(")");
    } else {
      query.append(" environment_id in (" + environmentSearch + ")");
    } 
    if (showAllSearch == null || !showAllSearch.equals("true"))
      query.append(" AND NOT ( status = 'CLOSED' OR  status = 'CANCEL' )"); 
    if (artistSearch != null && !artistSearch.equals(""))
      query.append(" AND artist " + MilestoneHelper.setWildCardsEscapeSingleQuotes(artistSearch)); 
    if (titleSearch != null && !titleSearch.equals(""))
      query.append(" AND title " + MilestoneHelper.setWildCardsEscapeSingleQuotes(titleSearch)); 
    if (selectionNoSearch != null && !selectionNoSearch.equals(""))
      query.append(" AND selection_no " + MilestoneHelper.setWildCardsEscapeSingleQuotes(selectionNoSearch)); 
    if (prefixIDSearch != null && !prefixIDSearch.equals(""))
      query.append(" AND prefix " + MilestoneHelper.setWildCardsEscapeSingleQuotes(prefixIDSearch)); 
    if (upcSearch != null && !upcSearch.equals(""))
      query.append(" AND upc " + MilestoneHelper.setWildCardsEscapeSingleQuotes(upcSearch)); 
    if (streetEndDateSearch != null && !streetEndDateSearch.equals("")) {
      String streetStartDateSearch = "";
      if (streetDateSearch != null && !streetDateSearch.equals("")) {
        query.append(" AND (   ( digital_flag = 0 and street_date between '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "')");
        query.append("      OR ( digital_flag = 1 and digital_rls_date between '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "'))");
      } else {
        query.append(" AND (   ( digital_flag = 0 and street_date between '01/01/1900' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "')");
        query.append("      OR ( digital flag = 1 and digital_rls_date between '01/01/1900' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "'))");
      } 
    } else if (streetDateSearch != null && !streetDateSearch.equals("")) {
      if (containsNA) {
        query.append(" AND ( ( digital_flag = 0 and street_date IS NULL) OR ( digital_flag = 1 and digital_rls_date IS NULL))");
      } else if (startsWithAsterisk) {
        String cleanStreetDate = streetDateSearch.replace('*', '%');
        query.append(" AND (    ( digital_flag = 0 and CONVERT(varchar, street_date, 1) LIKE '" + cleanStreetDate + "')");
        query.append("       OR ( digital_flag = 1 and CONVERT(varchar, digital_rls_date, 1) LIKE '" + cleanStreetDate + "'))");
      } else {
        query.append(" AND (   ( digital_flag = 0 and street_date = '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "')");
        query.append("      OR ( digital_flag = 1 and digital_rls_date = '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "'))");
      } 
    } 
    if (configSearch != null && !configSearch.equals("") && !configSearch.equals("0"))
      query.append(" AND configuration = '" + configSearch + "'"); 
    if (subconfigSearch != null && !subconfigSearch.equals("") && !subconfigSearch.equals("0"))
      query.append(" AND sub_configuration = '" + subconfigSearch + "'"); 
    if (labelSearch != null && !labelSearch.equals("") && !labelSearch.equals("0"))
      query.append(" AND label_id in (" + labelSearch + ")"); 
    if (companySearch != null && !companySearch.equals("") && !companySearch.equals("0"))
      query.append(" AND company_id in (" + companySearch + ")"); 
    addReleasingFamilyLabelFamilySelect("FamilySearch", context, query, form);
    if (!productTypeSearch.equalsIgnoreCase(""))
      query.append(" AND digital_flag = " + productTypeSearch); 
    if (contactSearch != null && !contactSearch.equals("") && !contactSearch.equals("0"))
      query.append(" AND contact_id = '" + contactSearch + "'"); 
    if (projectIDSearch != null && !projectIDSearch.equals(""))
      query.append(" AND project_no " + MilestoneHelper.setWildCardsEscapeSingleQuotes(projectIDSearch)); 
    query.append(ShowOrHideDigitalProductGet(user));
    String order = "";
    NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
    boolean descending = false;
    if (notepad.getOrderBy().indexOf(" DESC ") != -1)
      descending = true; 
    if (artistSearch.length() > 0) {
      notepadSortOrder.setSelectionOrderCol("Artist");
      notepadSortOrder.setShowGroupButtons(true);
      if (!descending) {
        order = " ORDER BY artist, title, selection_no, street_date";
        notepadSortOrder.setSelectionOrderColNo(0);
      } else {
        order = " ORDER BY artist DESC , title, selection_no, street_date";
        notepadSortOrder.setSelectionOrderColNo(7);
      } 
    } else if (titleSearch.length() > 0) {
      if (!descending) {
        order = " ORDER BY title, artist, selection_no, street_date";
        notepadSortOrder.setSelectionOrderColNo(1);
      } else {
        order = " ORDER BY title DESC , artist, selection_no, street_date";
        notepadSortOrder.setSelectionOrderColNo(8);
      } 
      notepadSortOrder.setSelectionOrderCol("Title");
      notepadSortOrder.setShowGroupButtons(true);
    } else if (streetDateSearch.length() > 0) {
      if (!descending) {
        order = " ORDER BY street_date, artist, title, selection_no";
        notepadSortOrder.setSelectionOrderColNo(5);
      } else {
        order = " ORDER BY street_date DESC , artist, title, selection_no";
        notepadSortOrder.setSelectionOrderColNo(5);
      } 
      notepadSortOrder.setSelectionOrderCol("Str Dt");
    } else if (selectionNoSearch.length() > 0) {
      if (!descending) {
        order = " ORDER BY selection_no";
        notepadSortOrder.setSelectionOrderColNo(2);
      } else {
        order = " ORDER BY selection_no DESC ";
        notepadSortOrder.setSelectionOrderColNo(2);
      } 
      notepadSortOrder.setSelectionOrderCol("Local Prod #");
    } else if (prefixIDSearch.length() > 0) {
      if (!descending) {
        order = " ORDER BY prefix,selection_no";
        notepadSortOrder.setSelectionOrderColNo(4);
      } else {
        order = " ORDER BY prefix DESC ,selection_no";
        notepadSortOrder.setSelectionOrderColNo(4);
      } 
      notepadSortOrder.setSelectionOrderCol("Prefix");
    } else if (upcSearch.length() > 0) {
      if (!descending) {
        order = " ORDER BY upc,selection_no";
        notepadSortOrder.setSelectionOrderColNo(3);
      } else {
        order = " ORDER BY upc DESC ,selection_no";
        notepadSortOrder.setSelectionOrderColNo(3);
      } 
      notepadSortOrder.setSelectionOrderCol("UPC");
    } else if (user != null && !user.SS_searchInitiated) {
      order = getSelectionNotepadQueryUserDefaultsOrderBy(context);
    } else if (!notepad.getOrderBy().equals("") && (
      notepadSortOrder.getSelectionOrderCol().equals("Artist") || 
      notepadSortOrder.getSelectionOrderCol().equals("Title") || 
      notepadSortOrder.getSelectionOrderCol().equals("Str Dt"))) {
      order = notepad.getOrderBy();
    } else {
      if (!descending) {
        order = " ORDER BY artist, title, selection_no, street_date ";
        notepadSortOrder.setSelectionOrderColNo(0);
      } else {
        order = " ORDER BY artist DESC , title, selection_no, street_date ";
        notepadSortOrder.setSelectionOrderColNo(7);
      } 
      notepadSortOrder.setSelectionOrderCol("Artist");
      notepadSortOrder.setShowGroupButtons(true);
    } 
    if (user != null) {
      Hashtable prevSearchValues = new Hashtable();
      prevSearchValues.put("SS_artistSearch", user.SS_artistSearch);
      prevSearchValues.put("SS_titleSearch ", user.SS_titleSearch);
      prevSearchValues.put("SS_selectionNoSearch", user.SS_selectionNoSearch);
      prevSearchValues.put("SS_prefixIDSearch", user.SS_prefixIDSearch);
      prevSearchValues.put("SS_upcSearch", user.SS_upcSearch);
      prevSearchValues.put("SS_streetDateSearch", user.SS_streetDateSearch);
      prevSearchValues.put("SS_streetEndDateSearch", user.SS_streetEndDateSearch);
      prevSearchValues.put("SS_configSearch", user.SS_configSearch);
      prevSearchValues.put("SS_subconfigSearch", user.SS_subconfigSearch);
      prevSearchValues.put("SS_labelSearch", user.SS_labelSearch);
      prevSearchValues.put("SS_companySearch", user.SS_companySearch);
      prevSearchValues.put("SS_contactSearch", user.SS_contactSearch);
      prevSearchValues.put("SS_familySearch", user.SS_familySearch);
      prevSearchValues.put("SS_environmentSearch", user.SS_environmentSearch);
      prevSearchValues.put("SS_projectIDSearch", user.SS_projectIDSearch);
      prevSearchValues.put("SS_productTypeSearch", user.SS_productTypeSearch);
      prevSearchValues.put("SS_showAllSearch", user.SS_showAllSearch);
      context.putDelivery("prevSearchValues", prevSearchValues);
      user.SS_artistSearch = artistSearch;
      user.SS_titleSearch = titleSearch;
      user.SS_selectionNoSearch = selectionNoSearch;
      user.SS_prefixIDSearch = prefixIDSearch;
      user.SS_upcSearch = upcSearch;
      user.SS_streetDateSearch = streetDateSearch;
      user.SS_streetEndDateSearch = streetEndDateSearch;
      user.SS_configSearch = configSearch;
      user.SS_subconfigSearch = subconfigSearch;
      user.SS_labelSearch = labelSearch;
      user.SS_companySearch = companySearch;
      user.SS_contactSearch = contactSearch;
      user.SS_familySearch = familySearch;
      user.SS_environmentSearch = environmentSearch;
      user.SS_projectIDSearch = projectIDSearch;
      user.SS_productTypeSearch = productType;
      user.SS_showAllSearch = showAllSearch;
      user.RC_environment = environmentSearch;
      user.RC_releasingFamily = familySearch;
      user.RC_labelContact = contactSearch;
      user.RC_productType = MilestoneHelper_2.convertProductTypeToReleaseCalendar(productType);
    } 
    notepad.setSearchQuery(query.toString());
    notepad.setOrderBy(order);
    notepadSortOrder.setSelectionOrderBy(order);
  }
  
  public boolean getSelectionSearchResults(GeminiApplication application, Context context) {
    User user = (User)context.getSessionValue("user");
    Notepad notepad = null;
    notepad = MilestoneHelper.getNotepadFromSession(0, context);
    if (notepad != null) {
      String query = notepad.getSearchQuery();
      String order = notepad.getOrderBy();
      String orderBy = "";
      String orderCol = "";
      int orderColNo = 0;
      boolean showGrpButs = true;
      NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
      if (notepadSortOrder != null) {
        orderBy = notepadSortOrder.getSelectionOrderBy();
        showGrpButs = notepadSortOrder.getShowGroupButtons();
        orderCol = notepadSortOrder.getSelectionOrderCol();
        orderColNo = notepadSortOrder.getSelectionOrderColNo();
      } 
      Form searchForm = new Form(application, "searchForm", 
          application.getInfrastructure().getServletURL(), 
          "POST");
      Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
      FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, "-1", false, true);
      searchForm.addElement(Family);
      Vector environments = MilestoneHelper.getUserEnvironments(context);
      Vector myCompanies = MilestoneHelper.getUserCompanies(context);
      environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
      environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
      FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, "-1", false, true);
      searchForm.addElement(envMenu);
      searchForm.setValues(context);
      setSelectionNotepadQueryHelper(context, notepad, searchForm, user);
      if (getNotepadTotalCount(notepad, context) > 0)
        return true; 
      notepad.setSearchQuery(query);
      notepad.setOrderBy(order);
      if (notepadSortOrder != null) {
        notepadSortOrder.setSelectionOrderBy(orderBy);
        notepadSortOrder.setShowGroupButtons(showGrpButs);
        notepadSortOrder.setSelectionOrderCol(orderCol);
        notepadSortOrder.setSelectionOrderColNo(orderColNo);
      } 
    } 
    return false;
  }
  
  public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
    for (int j = 0; j < lookupVector.size(); j++) {
      LookupObject lookupObject = (LookupObject)lookupVector.get(j);
      if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
        return lookupObject; 
    } 
    return null;
  }
  
  public static String getLookupObjectValue(LookupObject lookupObject) {
    String lookupValue = "";
    if (lookupObject != null)
      lookupValue = lookupObject.getAbbreviation(); 
    return lookupValue;
  }
  
  public static SelectionConfiguration getSelectionConfigObject(String abbreviation, Vector configs) {
    for (int j = 0; j < configs.size(); j++) {
      SelectionConfiguration selectionConfiguration = (SelectionConfiguration)configs.get(j);
      if (selectionConfiguration.getSelectionConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
        return selectionConfiguration; 
    } 
    return new SelectionConfiguration("", "");
  }
  
  public static SelectionSubConfiguration getSelectionSubConfigObject(String abbreviation, SelectionConfiguration config) {
    Vector subConfigs = config.getSubConfigurations();
    for (int j = 0; j < subConfigs.size(); j++) {
      SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.get(j);
      if (subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
        return subConfig; 
    } 
    return new SelectionSubConfiguration("", "", 2);
  }
  
  public static Vector getLabelContacts(Selection selection) {
    Vector labelUsers = new Vector();
    int companyId = -1;
    if (selection.getCompany() != null)
      companyId = selection.getCompany().getStructureID(); 
    String query = "sp_get_Selection_LabelContacts " + companyId;
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
      labelUsers.add(labelUser);
      connector.next();
    } 
    connector.close();
    return labelUsers;
  }
  
  public static Vector getLabelContacts(Context context) {
    Vector labelUsers = new Vector();
    int umlId = MilestoneHelper.getStructureId("uml", 1);
    int enterpriseId = MilestoneHelper.getStructureId("Enterprise", 1);
    int companyId = -1;
    String companyClause = "( ";
    Vector userCompanies = new Vector();
    userCompanies = MilestoneHelper.getUserCompanies(context);
    for (int i = 0; i < userCompanies.size(); i++) {
      if (i == 0) {
        companyClause = String.valueOf(companyClause) + " company_id = " + ((Company)userCompanies.get(i)).getStructureID();
      } else {
        companyClause = String.valueOf(companyClause) + " OR company_id = " + ((Company)userCompanies.get(i)).getStructureID();
      } 
    } 
    companyClause = String.valueOf(companyClause) + ")";
    String query = "SELECT DISTINCT vi_User.Name,vi_User.Full_Name, vi_User.User_Id FROM vi_User, vi_User_Company WHERE (vi_User.User_ID = vi_User_Company.User_ID) AND (menu_access LIKE '[1,2]%') AND " + 
      
      companyClause + 
      
      " ORDER BY vi_User.Full_Name;";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    while (connector.more()) {
      User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
      labelUsers.add(labelUser);
      connector.next();
    } 
    connector.close();
    return labelUsers;
  }
  
  public static Vector getLabelContactsExcludeUml(Context context) {
    Vector labelUsers = Cache.getInstance().getLabelUsers();
    Vector retLabelUsers = new Vector();
    Vector userCompanies = MilestoneHelper.getUserCompanies(context);
    if (labelUsers != null && userCompanies != null)
      for (int i = 0; i < labelUsers.size(); i++) {
        User user = (User)labelUsers.get(i);
        if (user != null)
          for (int j = 0; j < userCompanies.size(); j++) {
            int familyId = ((Company)userCompanies.get(j)).getParentEnvironment().getParentFamily().getStructureID();
            if (familyId == user.getEmployedBy()) {
              retLabelUsers.add(user);
              break;
            } 
          }  
      }  
    return retLabelUsers;
  }
  
  public boolean isScheduleApplied(Selection selection) {
    boolean applied = false;
    if (selection.getSelectionID() > 0) {
      String query = "SELECT * FROM vi_release_detail WHERE release_id = " + 
        
        selection.getSelectionID() + 
        ";";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      applied = connector.more();
      connector.close();
    } 
    return applied;
  }
  
  public Notepad getSelectionNotepad(Context context, int userId, int type) {
    Vector contents = new Vector();
    if (MilestoneHelper.getNotepadFromSession(type, context) != null) {
      Notepad notepad = MilestoneHelper.getNotepadFromSession(type, context);
      if (notepad.getAllContents() == null || context.getSessionValue("ResetSelectionSortOrder") != null) {
        contents = getInstance().getSelectionNotepadList(userId, notepad, context);
        notepad.setAllContents(contents, getNotepadTotalCount(notepad, context));
      } 
      if (context.getSessionValue("selectionNotepadColumn") != null) {
        if (context.getSessionValue("selectionNotepadColumn") == "1") {
          notepad.setColumnNames(new String[] { "Artist", "Title", "Local Prod #", "Str Dt" });
        } else if (context.getSessionValue("selectionNotepadColumn") == "2") {
          notepad.setColumnNames(new String[] { "Artist", "Title", "UPC", "Str Dt" });
        } else if (context.getSessionValue("selectionNotepadColumn") == "3") {
          notepad.setColumnNames(new String[] { "Artist", "Title", "Prefix", "Str Dt" });
        } else {
          notepad.setColumnNames(new String[] { "Artist", "Title", "Str Dt" });
        } 
      } else {
        notepad.setColumnNames(new String[] { "Artist", "Title", "Str Dt" });
      } 
      notepad.setNotePadType(type);
      return notepad;
    } 
    String[] columnNames = { "Artist", "Title", "Str Dt" };
    if (context.getSessionValue("selectionNotepadColumn") != null)
      if (context.getSessionValue("selectionNotepadColumn") == "1") {
        columnNames = new String[] { "Artist", "Title", "Selection", "Str Dt" };
      } else if (context.getSessionValue("selectionNotepadColumn") == "2") {
        columnNames = new String[] { "Artist", "Title", "Upc", "Str Dt" };
      } else if (context.getSessionValue("selectionNotepadColumn") == "3") {
        columnNames = new String[] { "Artist", "Title", "Prefix", "Str Dt" };
      }  
    System.out.println("<<< new selection notepad ");
    contents = getInstance().getSelectionNotepadList(userId, null, context);
    Notepad newNotepad = new Notepad(contents, 0, 15, "Selections", type, columnNames);
    User user = (User)context.getSessionValue("user");
    if (user != null && !user.SS_searchInitiated) {
      String query = getInstance().getSelectionNotepadQueryUserDefaults(context);
      String order = getInstance().getSelectionNotepadQueryUserDefaultsOrderBy(context);
      newNotepad.setSearchQuery(query);
      newNotepad.setOrderBy(order);
    } 
    newNotepad.setPageStats(getNotepadTotalCount(newNotepad, context));
    return newNotepad;
  }
  
  public static String getDefaultQuery(Context context) {
    StringBuffer query = new StringBuffer();
    Environment environment = null;
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    query.append("SELECT release_id, title, artist, configuration, sub_configuration, upc, prefix, selection_no, digital_flag, street_date =  CASE digital_flag  WHEN 0 THEN street_date   WHEN 1 THEN digital_rls_date  END ,archimedes_id, Release_Family_id from vi_Release_Header WHERE NOT ( status = 'CLOSED' OR  status = 'CANCEL' ) AND ");
    if (environments.size() > 0)
      query.append(" environment_id in ("); 
    for (int i = 0; i < environments.size(); i++) {
      environment = (Environment)environments.get(i);
      if (environment != null)
        if (i == 0) {
          query.append(environment.getStructureID());
        } else {
          query.append("," + environment.getStructureID());
        }  
    } 
    if (environments.size() > 0)
      query.append(" )"); 
    Vector rFamilies = ReleasingFamily.getUserReleasingFamiliesVectorOfReleasingFamilies(context);
    User user = (User)context.getSession().getAttribute("user");
    Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
    addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
    if (rFamilies != null && rFamilies.size() > 0) {
      query.append(" AND (");
      for (int i = 0; i < rFamilies.size(); i++) {
        ReleasingFamily rf = (ReleasingFamily)rFamilies.get(i);
        if (i > 0)
          query.append(" OR "); 
        query.append("(Release_Family_id = " + rf.getReleasingFamilyId());
        addLabelFamilySelect(Integer.toString(rf.getReleasingFamilyId()), relFamilyLabelFamilyHash, query);
        query.append(")");
      } 
      query.append(") ");
    } else {
      query.append(" AND (Release_Family_id = -1 and family_id = -1) ");
    } 
    query.append(ShowOrHideDigitalProductGet(user));
    log.log("selection query " + query.toString());
    return query.toString();
  }
  
  public static String ShowOrHideDigitalProductGet(User user) {
    String digitalProdStr = "";
    if (user == null || user.getAdministrator() != 1)
      digitalProdStr = " AND (digital_flag = 0) "; 
    return digitalProdStr;
  }
  
  public static String getSequencedSelectionNumber() {
    query = "sp_get_Sequence selection_no";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    String prefixString = "";
    int number = -1;
    if (connector.more()) {
      prefixString = connector.getField("Prefix");
      number = connector.getIntegerField("SeqNo");
    } 
    connector.close();
    String result = "";
    try {
      NumberFormat form = new DecimalFormat("0000000");
      result = String.valueOf(prefixString) + form.format(number);
    } catch (Exception exception) {}
    return result;
  }
  
  public boolean isTimestampValid(Selection selection) {
    if (selection != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
        
        selection.getSelectionID() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (selection.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isTimestampValid(Bom bom) {
    if (bom != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM bom_header WHERE bom_id = " + 
        
        bom.getBomId() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (bom.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isTimestampValid(Pfm pfm) {
    if (pfm != null) {
      String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM pfm_selection WHERE release_id = " + 
        
        pfm.getReleaseId() + 
        ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (pfm.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public boolean isManufacturingTimestampValid(Selection selection) {
    if (selection != null) {
      String timestampQuery = "SELECT last_updated_ck  FROM release_subdetail WHERE release_id = " + 
        
        selection.getSelectionID() + ";";
      JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
      connectorTimestamp.runQuery();
      if (connectorTimestamp.more())
        if (selection.getLastMfgUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
          connectorTimestamp.close();
          return false;
        }  
      connectorTimestamp.close();
      return true;
    } 
    return false;
  }
  
  public Selection getSelectionAndSchedule(int id) {
    Selection selection = getSelectionHeader(id);
    Schedule schedule = ScheduleManager.getInstance().getSchedule(id);
    selection.setSchedule(schedule);
    return selection;
  }
  
  public void getSelectionsSchedule(Selection selection) {
    Schedule schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
    selection.setSchedule(schedule);
  }
  
  public int getUnitsFromPfm(Selection selection) {
    int units = 0;
    if (selection != null) {
      int selectionId = selection.getSelectionID();
      String manufacturingQuery = "SELECT units_per_set FROM vi_pfm_selection  WHERE release_id = " + 
        selectionId;
      JdbcConnector connector = MilestoneHelper.getConnector(manufacturingQuery);
      connector.runQuery();
      try {
        units = connector.getIntegerField("units_per_set");
      } catch (Exception exception) {}
      connector.close();
    } 
    return units;
  }
  
  public boolean isSelectionOkToClose(Context context) {
    boolean close = true;
    Selection selection = (Selection)context.getSessionValue("Selection");
    Schedule schedule = null;
    if (selection != null)
      schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID()); 
    if (schedule != null) {
      Vector tasks = schedule.getTasks();
      if (tasks != null) {
        ScheduledTask task = null;
        for (int i = 0; i < tasks.size(); i++) {
          task = (ScheduledTask)tasks.get(i);
          if (task.getCompletionDate() == null)
            if (MilestoneHelper.isUml(task) || MilestoneHelper.isEcommerce(task)) {
              close = false;
              break;
            }  
        } 
      } 
    } 
    return close;
  }
  
  public Vector getMultSelections(int id) {
    String sqlStr = "sp_get_Schedule_MutSelections " + id;
    JdbcConnector multSelectionsConnector = MilestoneHelper.getConnector(sqlStr);
    multSelectionsConnector.runQuery();
    Vector multSelections = new Vector();
    while (multSelectionsConnector.more()) {
      MultSelection multSel = new MultSelection();
      multSel.setMultSelectionPK(multSelectionsConnector.getInt("multSelectionsPK", -1));
      multSel.setRelease_id(multSelectionsConnector.getInt("release_id", -1));
      multSel.setSelectionNo(multSelectionsConnector.getField("selectionNo", ""));
      multSel.setUpc(multSelectionsConnector.getField("upc", ""));
      multSel.setDescription(multSelectionsConnector.getField("description", ""));
      multSelections.add(multSel);
      multSelectionsConnector.next();
    } 
    multSelectionsConnector.close();
    return multSelections;
  }
  
  public void saveMultSelections(Selection selection, User updatingUser) {
    if (selection != null && selection.getMultSelections() != null) {
      Vector multSelections = selection.getMultSelections();
      Vector addMultSelections = new Vector();
      Vector deleteMultSelections = new Vector();
      String sqlQuery = "select * from vi_MultSelections where release_id = " + selection.getSelectionID();
      boolean delete = true;
      for (int a = 0; a < multSelections.size(); a++) {
        MultSelection multSel = (MultSelection)multSelections.get(a);
        JdbcConnector connectorMultSelectionCount = MilestoneHelper.getConnector(sqlQuery);
        connectorMultSelectionCount.runQuery();
        if (connectorMultSelectionCount.more()) {
          while (connectorMultSelectionCount.more()) {
            if (multSel.getMultSelectionsPK() == -1 || 
              multSel.getMultSelectionsPK() == connectorMultSelectionCount.getInt("multSelectionsPK", -2)) {
              addMultSelections.add(multSel);
              break;
            } 
            connectorMultSelectionCount.next();
          } 
        } else {
          addMultSelections.add(multSel);
        } 
        connectorMultSelectionCount.close();
      } 
      JdbcConnector connectorMultSelectionDelete = MilestoneHelper.getConnector(sqlQuery);
      connectorMultSelectionDelete.runQuery();
      while (connectorMultSelectionDelete.more()) {
        delete = true;
        for (int b = 0; b < multSelections.size(); b++) {
          MultSelection multSelDelete = (MultSelection)multSelections.get(b);
          if (connectorMultSelectionDelete.getInt("multSelectionsPK", -2) == multSelDelete.getMultSelectionsPK())
            delete = false; 
        } 
        if (delete) {
          MultSelection delMultSelection = new MultSelection();
          delMultSelection.setMultSelectionPK(connectorMultSelectionDelete.getInt("multSelectionsPK", -1));
          deleteMultSelections.add(delMultSelection);
        } 
        connectorMultSelectionDelete.next();
      } 
      connectorMultSelectionDelete.close();
      for (int i = 0; i < addMultSelections.size(); i++) {
        MultSelection multSel = (MultSelection)addMultSelections.get(i);
        int relId = multSel.getRealease_id();
        int multSelectionsPK = multSel.getMultSelectionsPK();
        if (relId < 0)
          relId = selection.getSelectionID(); 
        String sqlStr = "sp_sav_MultSelections " + multSel.getMultSelectionsPK() + "," + 
          relId + "," + 
          "'" + multSel.getSelectionNo() + "'," + 
          
          "'" + MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(multSel.getUpc(), "UPC", false, true) + "'," + 
          "'" + multSel.getDescription() + "'";
        JdbcConnector connectorAddMultSelection = MilestoneHelper.getConnector(sqlStr);
        connectorAddMultSelection.runQuery();
        connectorAddMultSelection.close();
      } 
      JdbcConnector connectorDeleteMultSelection = null;
      for (int k = 0; k < deleteMultSelections.size(); k++) {
        MultSelection multSel = (MultSelection)deleteMultSelections.get(k);
        String deleteSql = "sp_del_MultSelections " + multSel.getMultSelectionsPK();
        connectorDeleteMultSelection = MilestoneHelper.getConnector(deleteSql);
        connectorDeleteMultSelection.runQuery();
        connectorDeleteMultSelection.close();
      } 
    } 
  }
  
  public Vector getMultOtherContacts(int id) {
    String sqlStr = "sp_get_Schedule_MultOtherContacts " + id;
    JdbcConnector multOtherContactsConnector = MilestoneHelper.getConnector(sqlStr);
    multOtherContactsConnector.runQuery();
    Vector multOtherContacts = new Vector();
    while (multOtherContactsConnector.more()) {
      MultOtherContact otherContact = new MultOtherContact();
      otherContact.setMultOtherContactsPK(multOtherContactsConnector.getInt("multOtherContactsPK", -1));
      otherContact.setRelease_id(multOtherContactsConnector.getInt("release_id", -1));
      otherContact.setName(multOtherContactsConnector.getField("name", ""));
      otherContact.setDescription(multOtherContactsConnector.getField("description", ""));
      multOtherContacts.add(otherContact);
      multOtherContactsConnector.next();
    } 
    multOtherContactsConnector.close();
    return multOtherContacts;
  }
  
  public void saveMultOtherContacts(Selection selection, User updatingUser) {
    if (selection != null && selection.getMultOtherContacts() != null) {
      Vector multOtherContacts = selection.getMultOtherContacts();
      Vector addMultOtherContacts = new Vector();
      Vector deleteMultOtherContacts = new Vector();
      String sqlQuery = "select * from vi_MultOtherContacts where release_id = " + selection.getSelectionID();
      boolean delete = true;
      for (int a = 0; a < multOtherContacts.size(); a++) {
        MultOtherContact otherContact = (MultOtherContact)multOtherContacts.get(a);
        JdbcConnector connectorMultOtherContactCount = MilestoneHelper.getConnector(sqlQuery);
        connectorMultOtherContactCount.runQuery();
        if (connectorMultOtherContactCount.more()) {
          while (connectorMultOtherContactCount.more()) {
            if (otherContact.getMultOtherContactsPK() == -1 || 
              otherContact.getMultOtherContactsPK() == connectorMultOtherContactCount.getInt("multOtherContactsPK", -2)) {
              addMultOtherContacts.add(otherContact);
              break;
            } 
            connectorMultOtherContactCount.next();
          } 
        } else {
          addMultOtherContacts.add(otherContact);
        } 
        connectorMultOtherContactCount.close();
      } 
      JdbcConnector connectorMultOtherContactDelete = MilestoneHelper.getConnector(sqlQuery);
      connectorMultOtherContactDelete.runQuery();
      while (connectorMultOtherContactDelete.more()) {
        delete = true;
        for (int b = 0; b < multOtherContacts.size(); b++) {
          MultOtherContact multOtherContactDelete = (MultOtherContact)multOtherContacts.get(b);
          if (connectorMultOtherContactDelete.getInt("multOtherContactsPK", -2) == multOtherContactDelete.getMultOtherContactsPK())
            delete = false; 
        } 
        if (delete) {
          MultOtherContact delMultOtherContact = new MultOtherContact();
          delMultOtherContact.setMultOtherContactsPK(connectorMultOtherContactDelete.getInt("multOtherContactsPK", -1));
          deleteMultOtherContacts.add(delMultOtherContact);
        } 
        connectorMultOtherContactDelete.next();
      } 
      connectorMultOtherContactDelete.close();
      for (int i = 0; i < addMultOtherContacts.size(); i++) {
        MultOtherContact otherContact = (MultOtherContact)addMultOtherContacts.get(i);
        int relId = otherContact.getRealease_id();
        int multOtherContactsPK = otherContact.getMultOtherContactsPK();
        if (relId < 0)
          relId = selection.getSelectionID(); 
        String sqlStr = "sp_sav_MultOtherContacts " + otherContact.getMultOtherContactsPK() + "," + 
          relId + "," + 
          "'" + otherContact.getName() + "'," + 
          "'" + otherContact.getDescription() + "'";
        JdbcConnector connectorAddMultOtherContact = MilestoneHelper.getConnector(sqlStr);
        connectorAddMultOtherContact.runQuery();
        connectorAddMultOtherContact.close();
      } 
      JdbcConnector connectorDeleteMultOtherContact = null;
      for (int k = 0; k < deleteMultOtherContacts.size(); k++) {
        MultOtherContact otherContact = (MultOtherContact)deleteMultOtherContacts.get(k);
        String deleteSql = "sp_del_MultOtherContacts " + otherContact.getMultOtherContactsPK();
        connectorDeleteMultOtherContact = MilestoneHelper.getConnector(deleteSql);
        connectorDeleteMultOtherContact.runQuery();
        connectorDeleteMultOtherContact.close();
      } 
    } 
  }
  
  public void getAlphaGroupPosition(Context context, Notepad notepad, String alphaChar, int sortOrder) {
    Vector notepadList = notepad.getAllContents();
    boolean fndChar = false;
    if (notepad != null && notepadList != null && notepadList.size() > 0) {
      int x = 0;
      NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
      if (sortOrder == 0 || sortOrder == 7)
        notepadSortOrder.setSelectionOrderCol("Artist"); 
      if (sortOrder == 1 || sortOrder == 8)
        notepadSortOrder.setSelectionOrderCol("Title"); 
      notepadSortOrder.setSelectionOrderColNo(sortOrder);
      for (Iterator i = notepadList.iterator(); i.hasNext(); x++) {
        Selection selection = (Selection)i.next();
        String scanStr = "";
        if (sortOrder == 0 || sortOrder == 7)
          scanStr = selection.getArtist(); 
        if (sortOrder == 1 || sortOrder == 8)
          scanStr = selection.getTitle(); 
        int result = scanStr.substring(0).compareToIgnoreCase(alphaChar.substring(0));
        if (sortOrder == 0 || sortOrder == 1) {
          if (result == 0 || result > 0) {
            notepad.setSelected(selection);
            fndChar = true;
            break;
          } 
        } else if (result < 0) {
          Selection upOne = null;
          if (x > 0)
            upOne = (Selection)notepadList.get(x - 1); 
          notepad.setSelected(upOne);
          fndChar = true;
          break;
        } 
      } 
      if (!fndChar) {
        if (x > 0) {
          Selection upOne = (Selection)notepadList.get(x - 1);
          notepad.setSelected(upOne);
        } 
        context.putDelivery("AlertMessage", "There is no record that matches the search criteria");
      } 
    } 
  }
  
  public Vector getUserEnvironments(Context context) {
    Vector userCompanies = MilestoneHelper.getUserCompanies(context);
    Vector userEnvironments = new Vector();
    Environment env = null;
    boolean duplicate = false;
    for (int i = 0; i < userCompanies.size(); i++) {
      duplicate = false;
      env = ((Company)userCompanies.get(i)).getParentEnvironment();
      for (int j = 0; j < userEnvironments.size(); j++) {
        if (env.getName() == ((Environment)userEnvironments.get(j)).getName()) {
          duplicate = true;
          break;
        } 
      } 
      if (!duplicate)
        userEnvironments.add(env); 
    } 
    return userEnvironments;
  }
  
  protected boolean isSelectionIDDuplicate(String prefix, String selectionNo, int selectionID, boolean isDigital) {
    int prodType = 0;
    if (isDigital)
      prodType = 1; 
    if (isDigital && selectionNo.length() == 0)
      return false; 
    String sqlStr = "SELECT count(*) as myCount FROM release_header with(nolock)  WHERE prefix = '" + 
      prefix + "' " + 
      " AND NOT status = 'CLOSED' " + 
      " AND selection_no = '" + selectionNo + "'" + 
      " AND release_id <> " + selectionID + 
      " AND digital_flag = " + prodType;
    JdbcConnector isDup = MilestoneHelper.getConnector(sqlStr);
    isDup.runQuery();
    int count = 0;
    if (isDup.more())
      count = isDup.getInt("myCount"); 
    isDup.close();
    return (count > 0);
  }
  
  public void saveSelectionDataFromDigitalPfm(int releaseID, PrefixCode prefix, String selectionNo, String upc, String configCode, String soundscan) {
    String query = "UPDATE dbo.Release_Header SET UPC = '" + 
      MilestoneHelper.escapeSingleQuotes(upc) + "', config_code = '" + 
      MilestoneHelper.escapeSingleQuotes(configCode) + "', soundscan_grp = '" + 
      MilestoneHelper.escapeSingleQuotes(soundscan) + "', prefix = '" + 
      getLookupObjectValue(prefix) + "', selection_no = '" + 
      MilestoneHelper.escapeSingleQuotes(selectionNo) + "'" + 
      " WHERE release_id = " + String.valueOf(releaseID);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public void saveSelectionDataFromPhysicalPfm(int releaseID, String streetDate) {
    String query = "UPDATE dbo.Release_Header SET ";
    query = String.valueOf(query) + " street_date = '" + MilestoneHelper.escapeSingleQuotes(streetDate) + "'";
    query = String.valueOf(query) + " WHERE release_id = " + String.valueOf(releaseID);
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    connector.close();
  }
  
  public static boolean isProjectNumberValidFromMilestoneSnapshot(Selection selection) {
    String projectsQuery = "";
    String projectNumber = selection.getProjectID();
    if (selection.getFamily().getName().equalsIgnoreCase("UMVD")) {
      projectNumber = ProjectSearchManager.replaceStringInString(projectNumber, "-", "");
      if (projectNumber.length() > 8)
        projectNumber = projectNumber.substring(1); 
      projectsQuery = String.valueOf(projectsQuery) + "select * from ArchimedesProjects where [JDE Project#] = '" + projectNumber + "'";
    } else {
      projectsQuery = String.valueOf(projectsQuery) + "select * from ArchimedesProjects where [RMS Project#] = '" + projectNumber + "'";
    } 
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
  
  public static Selection isProjectNumberValid(Selection selection) {
    String projectsQuery = "";
    String projectNumber = selection.getProjectID();
    Selection updatedSel = null;
    boolean IsUMVD = false;
    try {
      updatedSel = projectSearchSvcClient.ProjectSearchValidation(projectNumber, Boolean.valueOf(IsUMVD));
      System.out.println("Project Search from WCF: " + updatedSel.scheduleId);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    return updatedSel;
  }
  
  public int getArchimedesIdFromCache(int label_id) {
    int returnId = -1;
    Vector labels = Cache.getLabels();
    if (labels != null)
      for (int i = 0; i < labels.size(); i++) {
        Label label = (Label)labels.get(i);
        if (label.getStructureID() == label_id) {
          returnId = label.getArchimedesId();
          break;
        } 
      }  
    return returnId;
  }
  
  public static void addReleasingFamilyLabelFamilySelect(String formName, Context context, StringBuffer query, Form form) {
    User user = (User)context.getSession().getAttribute("user");
    Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
    addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
    FormDropDownMenu famDD = (FormDropDownMenu)form.getElement(formName);
    String familySearch = famDD.getStringValue();
    if (familySearch != null && !familySearch.equals("") && !familySearch.equals("0") && 
      !familySearch.equals("-1")) {
      query.append(" AND (Release_Family_id = " + familySearch);
      addLabelFamilySelect(familySearch, relFamilyLabelFamilyHash, query);
      query.append(") ");
    } else {
      String[] valueList = famDD.getValueList();
      if (valueList != null && valueList.length > 0) {
        query.append(" AND (");
        for (int i = 0; i < valueList.length; i++) {
          if (i > 0)
            query.append(" OR "); 
          query.append("(Release_Family_id = " + valueList[i]);
          addLabelFamilySelect(valueList[i], relFamilyLabelFamilyHash, query);
          query.append(")");
        } 
        query.append(") ");
      } else {
        query.append(" AND (Release_Family_id = -1 and family_id = -1) ");
      } 
    } 
  }
  
  public static void addLabelFamilySelect(String relFamilyStr, Hashtable relFamilyLabelFamilyHash, StringBuffer query) {
    if (relFamilyLabelFamilyHash.containsKey(relFamilyStr)) {
      Vector labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyStr);
      if (labelFamilies != null && labelFamilies.size() > 0) {
        for (int i = 0; i < labelFamilies.size(); i++) {
          if (i == 0) {
            query.append(" AND family_id in (" + (String)labelFamilies.get(i));
          } else {
            query.append("," + (String)labelFamilies.get(i));
          } 
        } 
        query.append(")");
      } 
    } else {
      query.append(" AND family_id in (" + relFamilyStr + ")");
    } 
  }
  
  public static void addUndefinedReleasingFamilies(Hashtable relFamilyLabelFamilyHash, Context context) {
    Vector labelFamilies = null;
    Vector families = ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context);
    if (families != null)
      for (int i = 0; i < families.size(); i++) {
        Family family = (Family)families.get(i);
        if (family != null) {
          String relFamilyIdStr = Integer.toString(family.getStructureID());
          if (relFamilyLabelFamilyHash.containsKey(relFamilyIdStr)) {
            boolean addToHash = true;
            labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyIdStr);
            for (int c = 0; c < labelFamilies.size(); c++) {
              String labelFamilyIdStr = (String)labelFamilies.get(c);
              if (labelFamilyIdStr.equals(relFamilyIdStr)) {
                addToHash = false;
                break;
              } 
            } 
            if (addToHash) {
              labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyIdStr);
              labelFamilies.add(relFamilyIdStr);
            } 
          } else {
            labelFamilies = new Vector();
            labelFamilies.add(relFamilyIdStr);
            relFamilyLabelFamilyHash.put(relFamilyIdStr, labelFamilies);
          } 
        } 
      }  
  }
  
  public static String getSelectionNotepadQueryUserDefaults(Context context) {
    StringBuffer query = new StringBuffer();
    Environment environment = null;
    Vector environments = MilestoneHelper.getUserEnvironments(context);
    int year = Calendar.getInstance().get(1);
    String date = "01/01/" + year;
    query.append("SELECT release_id, title, artist, configuration, sub_configuration, upc, prefix, selection_no, digital_flag, street_date =  CASE digital_flag  WHEN 0 THEN street_date   WHEN 1 THEN digital_rls_date  END ,archimedes_id, Release_Family_id from vi_Release_Header WHERE ");
    User user = (User)context.getSession().getAttribute("user");
    if (user.getPreferences().getSelectionEnvironment() > 0) {
      int envId = user.getPreferences().getSelectionEnvironment();
      query.append(" environment_id in (" + envId + ") ");
      user.SS_environmentSearch = String.valueOf(envId);
      user.RC_environment = String.valueOf(envId);
    } else {
      if (environments.size() > 0)
        query.append(" environment_id in ("); 
      for (int i = 0; i < environments.size(); i++) {
        environment = (Environment)environments.get(i);
        if (environment != null)
          if (i == 0) {
            query.append(environment.getStructureID());
          } else {
            query.append("," + environment.getStructureID());
          }  
      } 
      if (environments.size() > 0)
        query.append(" )"); 
    } 
    Vector rFamilies = ReleasingFamily.getUserReleasingFamiliesVectorOfReleasingFamilies(context);
    Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
    if (user.getPreferences().getSelectionReleasingFamily() > 0) {
      addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
      int relFamId = user.getPreferences().getSelectionReleasingFamily();
      query.append(" AND (Release_Family_id = " + relFamId);
      addLabelFamilySelect(Integer.toString(relFamId), relFamilyLabelFamilyHash, query);
      query.append(") ");
      user.SS_familySearch = String.valueOf(relFamId);
      user.RC_releasingFamily = String.valueOf(relFamId);
    } else {
      addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
      if (rFamilies != null && rFamilies.size() > 0) {
        query.append(" AND (");
        for (int i = 0; i < rFamilies.size(); i++) {
          ReleasingFamily rf = (ReleasingFamily)rFamilies.get(i);
          if (i > 0)
            query.append(" OR "); 
          query.append("(Release_Family_id = " + rf.getReleasingFamilyId());
          addLabelFamilySelect(Integer.toString(rf.getReleasingFamilyId()), 
              relFamilyLabelFamilyHash, query);
          query.append(")");
        } 
        query.append(") ");
      } else {
        query.append(" AND (Release_Family_id = -1 and family_id = -1) ");
      } 
    } 
    if (user != null && user.getPreferences() != null) {
      user.SS_showAllSearch = "true";
      if (user.getPreferences().getSelectionStatus() == 0) {
        query.append(" AND NOT ( status = 'CLOSED' OR  status = 'CANCEL' )");
        user.SS_showAllSearch = "false";
      } 
      if (user.getPreferences().getSelectionLabelContact() > 0) {
        query.append(" AND contact_id = " + user.getPreferences().getSelectionLabelContact());
        user.SS_contactSearch = String.valueOf(user.getPreferences().getSelectionLabelContact());
        user.RC_labelContact = String.valueOf(user.getPreferences().getSelectionLabelContact());
      } 
      if (user.getPreferences().getSelectionProductType() > -1) {
        String productTypeSearch = "";
        if (user.getPreferences().getSelectionProductType() == 0) {
          productTypeSearch = "physical";
          query.append(" AND digital_flag = 0 ");
        } 
        if (user.getPreferences().getSelectionProductType() == 1) {
          productTypeSearch = "digital";
          query.append(" AND digital_flag = 1 ");
        } 
        if (user.getPreferences().getSelectionProductType() == 2)
          productTypeSearch = "both"; 
        user.SS_productTypeSearch = productTypeSearch;
        user.RC_productType = MilestoneHelper_2.convertProductTypeToReleaseCalendar(productTypeSearch);
      } 
    } 
    query.append(ShowOrHideDigitalProductGet(user));
    log.log("selection query " + query.toString());
    return query.toString();
  }
  
  public static String getSelectionNotepadQueryUserDefaultsOrderBy(Context context) {
    User user = (User)context.getSession().getAttribute("user");
    String order = " ORDER BY artist, title, selection_no, street_date";
    if (user != null && user.getPreferences() != null) {
      NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
      notepadSortOrder.setSelectionOrderCol("Artist");
      notepadSortOrder.setSelectionOrderBy(order);
      notepadSortOrder.setShowGroupButtons(true);
      notepadSortOrder.setSelectionOrderColNo(0);
      if (user.getPreferences().getNotepadSortBy() == 1) {
        if (user.getPreferences().getNotepadOrder() == 1) {
          order = " ORDER BY artist ASC , title, selection_no, street_date";
        } else if (user.getPreferences().getNotepadOrder() == 2) {
          order = " ORDER BY artist DESC , title, selection_no, street_date";
          notepadSortOrder.setSelectionOrderColNo(7);
        } else {
          order = " ORDER BY artist, title, selection_no, street_date";
        } 
        notepadSortOrder.setSelectionOrderCol("Artist");
        notepadSortOrder.setSelectionOrderBy(order);
        notepadSortOrder.setShowGroupButtons(true);
      } else if (user.getPreferences().getNotepadSortBy() == 2) {
        notepadSortOrder.setSelectionOrderColNo(1);
        if (user.getPreferences().getNotepadOrder() == 1) {
          order = " ORDER BY title ASC , artist, selection_no, street_date";
        } else if (user.getPreferences().getNotepadOrder() == 2) {
          order = " ORDER BY title DESC , artist, selection_no, street_date";
          notepadSortOrder.setSelectionOrderColNo(8);
        } else {
          order = " ORDER BY title, artist, selection_no, street_date";
        } 
        notepadSortOrder.setSelectionOrderCol("Title");
        notepadSortOrder.setSelectionOrderBy(order);
        notepadSortOrder.setShowGroupButtons(true);
      } else if (user.getPreferences().getNotepadSortBy() == 3) {
        notepadSortOrder.setSelectionOrderColNo(5);
        if (user.getPreferences().getNotepadOrder() == 1) {
          order = " ORDER BY street_date ASC , artist, title, selection_no";
        } else if (user.getPreferences().getNotepadOrder() == 2) {
          order = " ORDER BY street_date DESC , artist, title, selection_no";
        } else {
          order = " ORDER BY street_date, artist, title, selection_no";
        } 
        notepadSortOrder.setShowGroupButtons(false);
        notepadSortOrder.setSelectionOrderCol("Str Dt");
        notepadSortOrder.setSelectionOrderBy(order);
      } 
    } 
    return order;
  }
  
  public static void setSelectionNotepadUserDefaults(Context context) {
    Notepad selNotepad = MilestoneHelper.getNotepadFromSession(0, context);
    if (selNotepad == null) {
      User user = (User)context.getSession().getAttribute("user");
      if (user != null) {
        selNotepad = getInstance().getSelectionNotepad(context, user.getUserId(), 0);
        MilestoneHelper.putNotepadIntoSession(selNotepad, context);
      } 
    } 
  }
  
  public static void GDRS_QueueAddReleaseId(Selection selection, String statusCode) {
    if ((selection != null && selection.getReleaseFamilyId() != 820 && IsDigitalEquivalent(selection) && (
      !selection.releaseType.abbreviation.equals("PR") || !selection.getUpc().trim().equals(""))) || statusCode.equals("DELETE")) {
      String query = "INSERT INTO [dbo].[MilestoneCPRSD] ([release_id],[statusCode]) VALUES (" + 
        selection.getSelectionID() + ",'" + statusCode + "')";
      JdbcConnector connector = MilestoneHelper.getConnector(query);
      connector.runQuery();
      connector.close();
    } 
  }
  
  public static boolean IsDigitalEquivalent(Selection selection) {
    boolean results = false;
    Vector configCodes = Cache.getConfigCodes();
    for (int x = 0; x < configCodes.size(); x++) {
      LookupObject lo = (LookupObject)configCodes.get(x);
      if (lo != null && lo.getAbbreviation().equals(selection.getConfigCode()) && lo.getIsDigitalEquivalent()) {
        results = true;
        break;
      } 
    } 
    return results;
  }
  
  public static String IsDigitalEquivalentGetConfigs() {
    results = "";
    Vector configCodes = Cache.getConfigCodes();
    for (int x = 0; x < configCodes.size(); x++) {
      LookupObject lo = (LookupObject)configCodes.get(x);
      if (lo != null && lo.getIsDigitalEquivalent())
        results = String.valueOf(results) + lo.getAbbreviation() + ";"; 
    } 
    return results;
  }
  
  public static boolean IsPfmDraftOrFinal(int releaseId) {
    boolean result = false;
    String query = "SELECT isnull(print_flag,'') as print_flag FROM dbo.Pfm_Selection WHERE (release_id = " + releaseId + ")";
    JdbcConnector connector = MilestoneHelper.getConnector(query);
    connector.runQuery();
    String printFlag = "";
    if (connector.more())
      result = true; 
    connector.close();
    return result;
  }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */