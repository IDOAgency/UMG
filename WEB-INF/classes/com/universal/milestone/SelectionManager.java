/*      */ package WEB-INF.classes.com.universal.milestone;
/*      */ 
/*      */ import com.techempower.ComponentLog;
/*      */ import com.techempower.EnhancedProperties;
/*      */ import com.techempower.gemini.Context;
/*      */ import com.techempower.gemini.FormDropDownMenu;
/*      */ import com.techempower.gemini.GeminiApplication;
/*      */ import com.universal.milestone.Bom;
/*      */ import com.universal.milestone.BomCassetteDetail;
/*      */ import com.universal.milestone.BomDVDDetail;
/*      */ import com.universal.milestone.BomVinylDetail;
/*      */ import com.universal.milestone.Cache;
/*      */ import com.universal.milestone.Company;
/*      */ import com.universal.milestone.Division;
/*      */ import com.universal.milestone.Environment;
/*      */ import com.universal.milestone.Family;
/*      */ import com.universal.milestone.Form;
/*      */ import com.universal.milestone.Genre;
/*      */ import com.universal.milestone.ImpactDate;
/*      */ import com.universal.milestone.JdbcConnector;
/*      */ import com.universal.milestone.Label;
/*      */ import com.universal.milestone.LookupObject;
/*      */ import com.universal.milestone.MilestoneConstants;
/*      */ import com.universal.milestone.MilestoneHelper;
/*      */ import com.universal.milestone.MilestoneHelper_2;
/*      */ import com.universal.milestone.MultOtherContact;
/*      */ import com.universal.milestone.MultSelection;
/*      */ import com.universal.milestone.Notepad;
/*      */ import com.universal.milestone.NotepadSortOrder;
/*      */ import com.universal.milestone.Pfm;
/*      */ import com.universal.milestone.Plant;
/*      */ import com.universal.milestone.PrefixCode;
/*      */ import com.universal.milestone.PriceCode;
/*      */ import com.universal.milestone.ProductCategory;
/*      */ import com.universal.milestone.ProjectSearchManager;
/*      */ import com.universal.milestone.ReleaseType;
/*      */ import com.universal.milestone.ReleasingFamily;
/*      */ import com.universal.milestone.Schedule;
/*      */ import com.universal.milestone.ScheduleManager;
/*      */ import com.universal.milestone.ScheduledTask;
/*      */ import com.universal.milestone.Selection;
/*      */ import com.universal.milestone.SelectionConfiguration;
/*      */ import com.universal.milestone.SelectionHandler;
/*      */ import com.universal.milestone.SelectionManager;
/*      */ import com.universal.milestone.SelectionStatus;
/*      */ import com.universal.milestone.SelectionSubConfiguration;
/*      */ import com.universal.milestone.SellCode;
/*      */ import com.universal.milestone.Task;
/*      */ import com.universal.milestone.User;
/*      */ import com.universal.milestone.UserManager;
/*      */ import com.universal.milestone.projectSearchSvcClient;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import net.umusic.milestone.alps.DcGDRSResults;
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
/*      */ public class SelectionManager
/*      */   implements MilestoneConstants
/*      */ {
/*      */   public static final String COMPONENT_CODE = "mSel";
/*      */   public static final String DEFAULT_ORDER = " ORDER BY artist, title, selection_no, street_date ";
/*      */   public static final String DEFAULT_ORDER_DESCENDING = " ORDER BY artist DESC , title, selection_no, street_date ";
/*      */   public static final String CREATE_EDIT = "CREATE_EDIT";
/*      */   public static final String SAVE_SEND = "SAVE_SEND";
/*      */   public static final String DELETE = "DELETE";
/*   99 */   protected static SelectionManager selectionManager = null;
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
/*  111 */   public static void configure(EnhancedProperties props, GeminiApplication application) { log = application.getLog("mSel"); }
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
/*      */   public static SelectionManager getInstance() {
/*  129 */     if (selectionManager == null)
/*      */     {
/*  131 */       selectionManager = new SelectionManager();
/*      */     }
/*  133 */     return selectionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   public String[] getSelectionStatusList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   public String[] getPrefixList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   public String[] getProductCategoryList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  165 */   public String[] getReleaseTypeList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  173 */   public String[] getSelectionConfigurationList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  181 */   public String[] getSelectionSubConfigurationList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  189 */   public SellCode[] getSellCodeList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  197 */   public String[] getGenreList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  205 */   public String[] getPlantList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  213 */   public String[] getDepartmentList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  221 */   public String[] getTaskAbbreviationList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  229 */   public String[] getScheduledTaskStatusList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  237 */   public String[] getDayTypeList() { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  246 */   public Selection getSelection(int pSelectionId) { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  254 */   public Task getTask(int pTaskId) { return null; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  262 */   public Schedule getSchedule(int pScheduleId) { return new Schedule(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Pfm getPfm(int pReleaseId) {
/*  270 */     String pfmQuery = "SELECT top(50) * FROM vi_PFM_Selection WHERE release_id = " + 
/*      */       
/*  272 */       pReleaseId + 
/*  273 */       " ;";
/*      */ 
/*      */ 
/*      */     
/*  277 */     JdbcConnector connector = MilestoneHelper.getConnector(pfmQuery);
/*  278 */     connector.runQuery();
/*      */     
/*  280 */     Pfm pfm = null;
/*      */     
/*  282 */     if (connector.more()) {
/*      */       
/*  284 */       pfm = new Pfm();
/*  285 */       int myReleaseId = connector.getIntegerField("release_id");
/*  286 */       pfm.setReleaseId(myReleaseId);
/*      */       
/*  288 */       Selection mySelection = getSelectionHeader(myReleaseId);
/*  289 */       pfm.setSelection(mySelection);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  294 */       pfm.setStatus(connector.getField("status"));
/*      */ 
/*      */ 
/*      */       
/*  298 */       pfm.setEncryptionFlag(connector.getField("encryption_flag", ""));
/*      */ 
/*      */ 
/*      */       
/*  302 */       pfm.setSoundScanGrp(connector.getField("soundscan_grp", ""));
/*      */ 
/*      */ 
/*      */       
/*  306 */       pfm.setChangeNumber(connector.getField("change_number", ""));
/*      */ 
/*      */ 
/*      */       
/*  310 */       String streetDateString = connector.getFieldByName("street_date");
/*  311 */       if (streetDateString != null) {
/*  312 */         pfm.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */       }
/*      */       
/*  315 */       pfm.setProjectID(connector.getField("project_no", ""));
/*      */ 
/*      */       
/*  318 */       pfm.setParentalGuidance(connector.getBoolean("parental_indicator"));
/*      */ 
/*      */       
/*  321 */       pfm.setReleaseType(connector.getField("release_type", ""));
/*      */ 
/*      */       
/*  324 */       pfm.setMode(connector.getField("mode", ""));
/*      */ 
/*      */       
/*  327 */       pfm.setPrintOption(connector.getField("print_flag", ""));
/*      */ 
/*      */       
/*  330 */       pfm.setPreparedBy(connector.getField("prepared_by", ""));
/*      */ 
/*      */       
/*  333 */       pfm.setEmail(connector.getField("email", ""));
/*      */ 
/*      */       
/*  336 */       pfm.setPhone(connector.getField("phone", ""));
/*      */ 
/*      */       
/*  339 */       pfm.setUpc(connector.getField("UPC", ""));
/*      */ 
/*      */       
/*  342 */       pfm.setFaxNumber(connector.getField("fax", ""));
/*      */ 
/*      */       
/*  345 */       pfm.setComments(connector.getField("comments", ""));
/*      */ 
/*      */ 
/*      */       
/*  349 */       if (!connector.getField("operating_company", "").equalsIgnoreCase("-1")) {
/*  350 */         pfm.setOperatingCompany(connector.getField("operating_company", ""));
/*      */       }
/*      */       
/*  353 */       pfm.setProductNumber(connector.getField("product_number", ""));
/*      */ 
/*      */ 
/*      */       
/*  357 */       if (!connector.getField("config_code", "").equalsIgnoreCase("-1")) {
/*  358 */         pfm.setConfigCode(connector.getField("config_code", ""));
/*      */       }
/*      */ 
/*      */       
/*  362 */       if (!connector.getField("modifier", "").equalsIgnoreCase("-1")) {
/*  363 */         pfm.setModifier(connector.getField("modifier", ""));
/*      */       }
/*      */       
/*  366 */       pfm.setTitle(connector.getField("title", ""));
/*      */ 
/*      */       
/*  369 */       pfm.setArtist(connector.getField("artist", ""));
/*      */ 
/*      */       
/*  372 */       pfm.setTitleId(connector.getField("title_id", ""));
/*      */ 
/*      */       
/*  375 */       pfm.setSuperLabel(connector.getField("super_label", ""));
/*      */ 
/*      */       
/*  378 */       pfm.setLabelCode(connector.getField("label_code", ""));
/*      */ 
/*      */ 
/*      */       
/*  382 */       if (!connector.getField("company_code", "").equalsIgnoreCase("-1")) {
/*  383 */         pfm.setCompanyCode(connector.getField("company_code", ""));
/*      */       }
/*      */ 
/*      */       
/*  387 */       if (!connector.getField("po_merge_code", "").equalsIgnoreCase("-1")) {
/*  388 */         pfm.setPoMergeCode(connector.getField("po_merge_code", ""));
/*      */       }
/*      */       
/*  391 */       pfm.setUnitsPerSet(connector.getIntegerField("units_per_set"));
/*      */ 
/*      */       
/*  394 */       pfm.setSetsPerCarton(connector.getIntegerField("sets_per_carton"));
/*      */ 
/*      */ 
/*      */       
/*  398 */       if (!connector.getField("supplier", "").equalsIgnoreCase("-1")) {
/*  399 */         pfm.setSupplier(connector.getField("supplier", ""));
/*      */       }
/*      */       
/*  402 */       pfm.setImportIndicator(connector.getField("import_code", ""));
/*      */ 
/*      */ 
/*      */       
/*  406 */       if (!connector.getField("music_line", "").equalsIgnoreCase("-1")) {
/*  407 */         pfm.setMusicLine(connector.getField("music_line", ""));
/*      */       }
/*      */ 
/*      */       
/*  411 */       if (!connector.getField("repertoire_owner", "").equalsIgnoreCase("-1")) {
/*  412 */         pfm.setRepertoireOwner(connector.getField("repertoire_owner", ""));
/*      */       }
/*      */       
/*  415 */       pfm.setRepertoireClass(connector.getField("repertoire_class", ""));
/*      */ 
/*      */ 
/*      */       
/*  419 */       if (!connector.getField("return_code", "").equalsIgnoreCase("-1")) {
/*  420 */         pfm.setReturnCode(connector.getField("return_code", ""));
/*      */       }
/*      */ 
/*      */       
/*  424 */       if (!connector.getField("export_flag", "").equalsIgnoreCase("-1")) {
/*  425 */         pfm.setExportFlag(connector.getField("export_flag", ""));
/*      */       }
/*      */ 
/*      */       
/*  429 */       if (!connector.getField("countries", "").equalsIgnoreCase("-1")) {
/*  430 */         pfm.setCountries(connector.getField("countries", ""));
/*      */       }
/*      */       
/*  433 */       pfm.setSpineTitle(connector.getField("spine_title", ""));
/*      */ 
/*      */       
/*  436 */       pfm.setSpineArtist(connector.getField("spine_artist", ""));
/*      */ 
/*      */ 
/*      */       
/*  440 */       if (!connector.getField("price_code", "").equalsIgnoreCase("-1")) {
/*  441 */         pfm.setPriceCode(connector.getField("price_code", ""));
/*      */       }
/*      */ 
/*      */       
/*  445 */       if (!connector.getField("digital_price_code", "").equalsIgnoreCase("-1")) {
/*  446 */         pfm.setPriceCodeDPC(connector.getField("digital_price_code", ""));
/*      */       }
/*      */ 
/*      */       
/*  450 */       if (!connector.getField("guarantee_code", "").equalsIgnoreCase("-1")) {
/*  451 */         pfm.setGuaranteeCode(connector.getField("guarantee_code", ""));
/*      */       }
/*      */ 
/*      */       
/*  455 */       if (!connector.getField("loose_pick_exempt", "").equalsIgnoreCase("-1")) {
/*  456 */         pfm.setLoosePickExemptCode(connector.getField("loose_pick_exempt", ""));
/*      */       }
/*      */ 
/*      */       
/*  460 */       if (!connector.getField("compilation_code", "").equalsIgnoreCase("-1")) {
/*  461 */         pfm.setCompilationCode(connector.getField("compilation_code", ""));
/*      */       }
/*      */ 
/*      */       
/*  465 */       if (!connector.getField("imp_rate_code", "").equalsIgnoreCase("-1")) {
/*  466 */         pfm.setImpRateCode(connector.getField("imp_rate_code", ""));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  473 */       pfm.setMusicType((Genre)getLookupObject(connector.getField("music_type"), 
/*  474 */             Cache.getMusicTypes()));
/*      */ 
/*      */ 
/*      */       
/*  478 */       if (!connector.getField("narm_extract_flag", "").equalsIgnoreCase("-1")) {
/*  479 */         pfm.setNarmFlag(connector.getField("narm_extract_flag", ""));
/*      */       }
/*      */ 
/*      */       
/*  483 */       if (!connector.getField("price_point", "").equalsIgnoreCase("-1")) {
/*  484 */         pfm.setPricePoint(connector.getField("price_point", ""));
/*      */       }
/*      */       
/*  487 */       pfm.setApprovedByName(connector.getField("approved_by_name", ""));
/*      */ 
/*      */       
/*  490 */       pfm.setEnteredByName(connector.getField("entered_by", ""));
/*      */ 
/*      */       
/*  493 */       pfm.setVerifiedByName(connector.getField("verified_by_name", ""));
/*      */ 
/*      */       
/*  496 */       pfm.setValueAdded(connector.getBoolean("value_added"));
/*      */ 
/*      */       
/*  499 */       pfm.setBoxSet(connector.getBoolean("box_set"));
/*      */ 
/*      */       
/*  502 */       String lastDateString = connector.getFieldByName("last_updated_on");
/*  503 */       if (lastDateString != null) {
/*  504 */         pfm.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*      */       }
/*      */       
/*  507 */       pfm.setLastUpdatingUser(connector.getIntegerField("last_updated_by"));
/*      */ 
/*      */       
/*  510 */       if ((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()) != null) {
/*  511 */         pfm.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/*      */       }
/*      */ 
/*      */       
/*  515 */       pfm.setSelectionNo(connector.getField("selection_no"));
/*      */ 
/*      */       
/*  518 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/*  519 */       pfm.setLastUpdatedCk(lastUpdatedLong);
/*      */     } 
/*      */     
/*  522 */     connector.close();
/*  523 */     return pfm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Bom getBom(Selection currentSelection) {
/*  533 */     if (currentSelection != null) {
/*      */       
/*  535 */       String bomHeaderQuery = "SELECT top(50) b.[bom_id], r.[release_id], b.[format], b.[print_flag],        b.[date], b.[type], b.[change_number], b.[submitted], b.[email], b.[phone],         b.[comments], b.[label], b.[release_comp_id],         b.[retail_indicator], b.[selection_number], b.[due_date],         b.[units_per_pkg], b.[run_time], b.[configuration],         b.[spine_sticker_indicator], b.[shrink_wrap_indicator],         b.[special_instructions], b.[entered_by], b.[last_updated_by],         b.[last_updated_on], b.[last_updated_ck],         b.[label] AS release_label_id,         l.[name] AS release_label, b.[artist], b.[title],         r.[configuration] AS release_configuration,         r.[sub_configuration] AS release_sub_configuration,         r.[street_date] as release_street_date,         b.[selection_number] as release_selection_no,         r.[UPC] as release_UPC,         b.[status] as bom_status,         r.[prefix] as release_prefix, b.upc as bom_upc    FROM vi_release_header r         LEFT OUTER JOIN vi_bom_header b on r.[release_id] = b.[release_id],         vi_structure l   WHERE r.label_id = l.structure_id     AND (r.[release_id] = " + 
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
/*  556 */         currentSelection.getSelectionID() + ");";
/*      */       
/*  558 */       Bom bom = null;
/*      */       
/*  560 */       String bomDetailQuery = "";
/*      */       
/*  562 */       JdbcConnector connectorHeader = MilestoneHelper.getConnector(bomHeaderQuery);
/*  563 */       connectorHeader.runQuery();
/*      */ 
/*      */       
/*  566 */       String bomQuery = "select bom_id from vi_bom_header where release_id = " + currentSelection.getSelectionID();
/*  567 */       JdbcConnector connectorBom = MilestoneHelper.getConnector(bomQuery);
/*  568 */       connectorBom.runQuery();
/*      */       
/*  570 */       if (connectorHeader.more()) {
/*      */         
/*  572 */         bomDetailQuery = "SELECT s.description as supplier, p.description as part, d.[bom_id], d.[status_indicator], d.[part_id], d.[supplier_id],         d.[ink1], d.[ink2], d.[label], d.[selection], d.[information],         h.[last_updated_by]    FROM vi_bom_detail d,         vi_bom_header h,         vi_Part p,         vi_Supplier s   WHERE h.[bom_id] = d.[bom_id]     AND ( " + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  580 */           connectorHeader.getIntegerField("bom_id") + " < 0 OR d.[bom_id] = " + connectorHeader.getIntegerField("bom_id") + " ) " + 
/*  581 */           "    AND ( p.part_id = d.part_id ) " + 
/*  582 */           "    AND ( d.[supplier_id] = s.supplier_id );";
/*      */         
/*  584 */         bom = new Bom();
/*  585 */         bom.setBomId(connectorHeader.getIntegerField("bom_id"));
/*      */         
/*  587 */         int myReleaseId = connectorHeader.getIntegerField("release_id");
/*  588 */         bom.setReleaseId(myReleaseId);
/*      */         
/*  590 */         bom.setFormat(connectorHeader.getField("format", ""));
/*  591 */         bom.setPrintOption(connectorHeader.getField("print_flag", ""));
/*      */         
/*  593 */         String dateString = connectorHeader.getFieldByName("date");
/*  594 */         if (dateString != null)
/*      */         {
/*  596 */           bom.setDate(MilestoneHelper.getDatabaseDate(dateString));
/*      */         }
/*      */         
/*  599 */         bom.setType(connectorHeader.getField("type", ""));
/*  600 */         bom.setChangeNumber(connectorHeader.getField("change_number", ""));
/*  601 */         bom.setSubmitter(connectorHeader.getField("submitted", ""));
/*  602 */         bom.setEmail(connectorHeader.getField("email", ""));
/*  603 */         bom.setPhone(connectorHeader.getField("phone", ""));
/*  604 */         bom.setComments(connectorHeader.getField("comments", ""));
/*  605 */         bom.setLabelId(connectorHeader.getIntegerField("release_label_id"));
/*  606 */         bom.setReleasingCompanyId(connectorHeader.getField("release_comp_id", ""));
/*  607 */         bom.setIsRetail(connectorHeader.getBoolean("retail_indicator"));
/*  608 */         bom.setSelectionNumber(connectorHeader.getField("selection_number", ""));
/*      */ 
/*      */         
/*  611 */         bom.setStatus(connectorHeader.getField("bom_status", ""));
/*      */ 
/*      */         
/*  614 */         bom.setUpc(connectorHeader.getField("bom_upc", ""));
/*      */ 
/*      */         
/*  617 */         bom.setArtist(connectorHeader.getField("artist", ""));
/*  618 */         bom.setTitle(connectorHeader.getField("title", ""));
/*      */         
/*  620 */         String streetDateOnBomString = connectorHeader.getFieldByName("due_date");
/*  621 */         if (streetDateOnBomString != null)
/*      */         {
/*  623 */           bom.setStreetDateOnBom(MilestoneHelper.getDatabaseDate(streetDateOnBomString));
/*      */         }
/*      */         
/*  626 */         bom.setUnitsPerKG(connectorHeader.getIntegerField("units_per_pkg"));
/*  627 */         bom.setRunTime(connectorHeader.getField("run_time", ""));
/*  628 */         bom.setConfiguration(connectorHeader.getField("configuration", ""));
/*      */ 
/*      */         
/*  631 */         if (connectorBom.more()) {
/*      */           
/*  633 */           bom.setHasSpineSticker(connectorHeader.getBoolean("spine_sticker_indicator"));
/*  634 */           bom.setUseShrinkWrap(connectorHeader.getBoolean("shrink_wrap_indicator"));
/*      */         }
/*      */         else {
/*      */           
/*  638 */           bom.setHasSpineSticker(true);
/*  639 */           bom.setUseShrinkWrap(true);
/*      */         } 
/*      */         
/*  642 */         bom.setSpecialInstructions(connectorHeader.getField("special_instructions", ""));
/*  643 */         bom.setEnteredBy(connectorHeader.getIntegerField("entered_by"));
/*  644 */         bom.setModifiedBy(connectorHeader.getIntegerField("last_updated_by"));
/*      */         
/*  646 */         String modifiedOnString = connectorHeader.getFieldByName("last_updated_on");
/*  647 */         if (modifiedOnString != null)
/*      */         {
/*  649 */           bom.setModifiedOn(MilestoneHelper.getDatabaseDate(modifiedOnString));
/*      */         }
/*      */         
/*  652 */         long lastUpdatedLong = -1L;
/*  653 */         if (connectorHeader.getFieldByName("last_updated_ck") != null)
/*      */         {
/*  655 */           lastUpdatedLong = Long.parseLong(connectorHeader.getFieldByName("last_updated_ck"), 16);
/*      */         }
/*  657 */         bom.setLastUpdatedCheck(lastUpdatedLong);
/*      */         
/*  659 */         JdbcConnector connectorDetail = MilestoneHelper.getConnector(bomDetailQuery);
/*  660 */         connectorDetail.runQuery();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  665 */         String selectionConfiguration = currentSelection.getSelectionConfig().getSelectionConfigurationAbbreviation();
/*      */ 
/*      */         
/*  668 */         if (selectionConfiguration.equalsIgnoreCase("CAS")) {
/*      */           
/*  670 */           BomCassetteDetail bomCassetteDetail = null;
/*  671 */           if (connectorDetail.more()) {
/*  672 */             bomCassetteDetail = new BomCassetteDetail();
/*      */           }
/*  674 */           while (connectorDetail.more()) {
/*      */             
/*  676 */             if (connectorDetail.getIntegerField("part_id") == 5) {
/*      */               
/*  678 */               bomCassetteDetail.coPartId = connectorDetail.getIntegerField("part_id");
/*  679 */               bomCassetteDetail.coPart = connectorDetail.getField("part", "");
/*  680 */               bomCassetteDetail.coParSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  681 */               bomCassetteDetail.coSupplier = connectorDetail.getField("supplier");
/*  682 */               bomCassetteDetail.coInk1 = connectorDetail.getField("ink1", "");
/*  683 */               bomCassetteDetail.coInk2 = connectorDetail.getField("ink2", "");
/*  684 */               bomCassetteDetail.coColor = connectorDetail.getField("selection", "");
/*  685 */               bomCassetteDetail.coInfo = connectorDetail.getField("information", "");
/*  686 */               bomCassetteDetail.coStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  688 */             else if (connectorDetail.getIntegerField("part_id") == 16) {
/*      */               
/*  690 */               bomCassetteDetail.norelcoPartId = connectorDetail.getIntegerField("part_id");
/*  691 */               bomCassetteDetail.norelcoPart = connectorDetail.getField("part", "");
/*  692 */               bomCassetteDetail.norelcoSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  693 */               bomCassetteDetail.norelcoSuplier = connectorDetail.getField("supplier");
/*  694 */               bomCassetteDetail.norelcoColor = connectorDetail.getField("selection", "");
/*  695 */               bomCassetteDetail.norelcoInfo = connectorDetail.getField("information", "");
/*  696 */               bomCassetteDetail.norelcoStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  698 */             else if (connectorDetail.getIntegerField("part_id") == 13) {
/*      */               
/*  700 */               bomCassetteDetail.jCardPartId = connectorDetail.getIntegerField("part_id");
/*  701 */               bomCassetteDetail.jCardPart = connectorDetail.getField("part", "");
/*  702 */               bomCassetteDetail.jCardSupplier = connectorDetail.getField("supplier", "");
/*  703 */               bomCassetteDetail.jCardSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  704 */               bomCassetteDetail.jCardInk1 = connectorDetail.getField("ink1", "");
/*  705 */               bomCassetteDetail.jCardInk2 = connectorDetail.getField("ink2", "");
/*  706 */               bomCassetteDetail.jCardPanels = connectorDetail.getField("selection", "");
/*  707 */               bomCassetteDetail.jCardInfo = connectorDetail.getField("information", "");
/*  708 */               bomCassetteDetail.jCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  710 */             else if (connectorDetail.getIntegerField("part_id") == 24) {
/*      */               
/*  712 */               bomCassetteDetail.uCardPartId = connectorDetail.getIntegerField("part_id");
/*  713 */               bomCassetteDetail.uCardPart = connectorDetail.getField("part", "");
/*  714 */               bomCassetteDetail.uCardSupplier = connectorDetail.getField("supplier", "");
/*  715 */               bomCassetteDetail.uCardSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  716 */               bomCassetteDetail.uCardInk1 = connectorDetail.getField("ink1", "");
/*  717 */               bomCassetteDetail.uCardInk2 = connectorDetail.getField("ink2", "");
/*  718 */               bomCassetteDetail.uCardPanels = connectorDetail.getField("selection", "");
/*  719 */               bomCassetteDetail.uCardInfo = connectorDetail.getField("information", "");
/*  720 */               bomCassetteDetail.uCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  722 */             else if (connectorDetail.getIntegerField("part_id") == 17) {
/*      */               
/*  724 */               bomCassetteDetail.oCardPartId = connectorDetail.getIntegerField("part_id");
/*  725 */               bomCassetteDetail.oCardPart = connectorDetail.getField("part", "");
/*  726 */               bomCassetteDetail.oCardSupplier = connectorDetail.getField("supplier", "");
/*  727 */               bomCassetteDetail.oCardSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  728 */               bomCassetteDetail.oCardInk1 = connectorDetail.getField("ink1", "");
/*  729 */               bomCassetteDetail.oCardInk2 = connectorDetail.getField("ink2", "");
/*  730 */               bomCassetteDetail.oCardInfo = connectorDetail.getField("information", "");
/*  731 */               bomCassetteDetail.oCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  733 */             else if (connectorDetail.getIntegerField("part_id") == 21) {
/*      */               
/*  735 */               bomCassetteDetail.stickerOneCardPartId = connectorDetail.getIntegerField("part_id");
/*  736 */               bomCassetteDetail.stickerOneCardPart = connectorDetail.getField("part", "");
/*  737 */               bomCassetteDetail.stickerOneCardSupplier = connectorDetail.getField("supplier", "");
/*  738 */               bomCassetteDetail.stickerOneCardSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  739 */               bomCassetteDetail.stickerOneCardInk1 = connectorDetail.getField("ink1", "");
/*  740 */               bomCassetteDetail.stickerOneCardInk2 = connectorDetail.getField("ink2", "");
/*  741 */               bomCassetteDetail.stickerOneCardPlaces = connectorDetail.getField("selection", "");
/*  742 */               bomCassetteDetail.stickerOneCardInfo = connectorDetail.getField("information", "");
/*  743 */               bomCassetteDetail.stickerOneCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  745 */             else if (connectorDetail.getIntegerField("part_id") == 22) {
/*      */               
/*  747 */               bomCassetteDetail.stickerTwoCardPartId = connectorDetail.getIntegerField("part_id");
/*  748 */               bomCassetteDetail.stickerTwoCardPart = connectorDetail.getField("part", "");
/*  749 */               bomCassetteDetail.stickerTwoCardSupplier = connectorDetail.getField("supplier", "");
/*  750 */               bomCassetteDetail.stickerTwoCardSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  751 */               bomCassetteDetail.stickerTwoCardInk1 = connectorDetail.getField("ink1", "");
/*  752 */               bomCassetteDetail.stickerTwoCardInk2 = connectorDetail.getField("ink2", "");
/*  753 */               bomCassetteDetail.stickerTwoCardPlaces = connectorDetail.getField("selection", "");
/*  754 */               bomCassetteDetail.stickerTwoCardInfo = connectorDetail.getField("information", "");
/*  755 */               bomCassetteDetail.stickerTwoCardStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  757 */             else if (connectorDetail.getIntegerField("part_id") == 18) {
/*      */               
/*  759 */               bomCassetteDetail.otherPartId = connectorDetail.getIntegerField("part_id");
/*  760 */               bomCassetteDetail.otherPart = connectorDetail.getField("part", "");
/*  761 */               bomCassetteDetail.otherSupplier = connectorDetail.getField("supplier", "");
/*  762 */               bomCassetteDetail.otherSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  763 */               bomCassetteDetail.otherInk1 = connectorDetail.getField("ink1", "");
/*  764 */               bomCassetteDetail.otherInk2 = connectorDetail.getField("ink2", "");
/*  765 */               bomCassetteDetail.otherInfo = connectorDetail.getField("information", "");
/*  766 */               bomCassetteDetail.otherStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             } 
/*      */             
/*  769 */             connectorDetail.next();
/*      */           } 
/*      */           
/*  772 */           bom.setBomCassetteDetail(bomCassetteDetail);
/*      */         }
/*  774 */         else if (selectionConfiguration.equalsIgnoreCase("VIN")) {
/*      */           
/*  776 */           BomVinylDetail bomVinylDetail = null;
/*  777 */           if (connectorDetail.more()) {
/*  778 */             bomVinylDetail = new BomVinylDetail();
/*      */           }
/*  780 */           while (connectorDetail.more()) {
/*      */             
/*  782 */             if (connectorDetail.getIntegerField("part_id") == 19) {
/*      */               
/*  784 */               bomVinylDetail.recordPartId = connectorDetail.getIntegerField("part_id");
/*  785 */               bomVinylDetail.recordPart = connectorDetail.getField("part", "");
/*  786 */               bomVinylDetail.recordSupplier = connectorDetail.getField("supplier", "");
/*  787 */               bomVinylDetail.recordSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  788 */               bomVinylDetail.recordColor = connectorDetail.getField("selection", "");
/*  789 */               bomVinylDetail.recordInfo = connectorDetail.getField("information", "");
/*  790 */               bomVinylDetail.recordStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  792 */             else if (connectorDetail.getIntegerField("part_id") == 14) {
/*      */               
/*  794 */               bomVinylDetail.labelPartId = connectorDetail.getIntegerField("part_id");
/*  795 */               bomVinylDetail.labelPart = connectorDetail.getField("part", "");
/*  796 */               bomVinylDetail.labelSupplier = connectorDetail.getField("supplier", "");
/*  797 */               bomVinylDetail.labelSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  798 */               bomVinylDetail.labelInk1 = connectorDetail.getField("ink1", "");
/*  799 */               bomVinylDetail.labelInk2 = connectorDetail.getField("ink2", "");
/*  800 */               bomVinylDetail.labelInfo = connectorDetail.getField("information", "");
/*  801 */               bomVinylDetail.labelStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  803 */             else if (connectorDetail.getIntegerField("part_id") == 20) {
/*      */               
/*  805 */               bomVinylDetail.sleevePartId = connectorDetail.getIntegerField("part_id");
/*  806 */               bomVinylDetail.sleevePart = connectorDetail.getField("part", "");
/*  807 */               bomVinylDetail.sleeveSupplier = connectorDetail.getField("supplier", "");
/*  808 */               bomVinylDetail.sleeveSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  809 */               bomVinylDetail.sleeveInk1 = connectorDetail.getField("ink1", "");
/*  810 */               bomVinylDetail.sleeveInk2 = connectorDetail.getField("ink2", "");
/*  811 */               bomVinylDetail.sleeveInfo = connectorDetail.getField("information", "");
/*  812 */               bomVinylDetail.sleeveStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  814 */             else if (connectorDetail.getIntegerField("part_id") == 11) {
/*      */               
/*  816 */               bomVinylDetail.jacketPartId = connectorDetail.getIntegerField("part_id");
/*  817 */               bomVinylDetail.jacketPart = connectorDetail.getField("part", "");
/*  818 */               bomVinylDetail.jacketSupplier = connectorDetail.getField("supplier", "");
/*  819 */               bomVinylDetail.jacketSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  820 */               bomVinylDetail.jacketInk1 = connectorDetail.getField("ink1", "");
/*  821 */               bomVinylDetail.jacketInk2 = connectorDetail.getField("ink2", "");
/*  822 */               bomVinylDetail.jacketInfo = connectorDetail.getField("information", "");
/*  823 */               bomVinylDetail.jacketStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*  828 */             else if (connectorDetail.getIntegerField("part_id") == 33) {
/*      */               
/*  830 */               bomVinylDetail.insertPartId = connectorDetail.getIntegerField("part_id");
/*  831 */               bomVinylDetail.insertPart = connectorDetail.getField("part", "");
/*  832 */               bomVinylDetail.insertSupplier = connectorDetail.getField("supplier", "");
/*  833 */               bomVinylDetail.insertSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  834 */               bomVinylDetail.insertInk1 = connectorDetail.getField("ink1", "");
/*  835 */               bomVinylDetail.insertInk2 = connectorDetail.getField("ink2", "");
/*  836 */               bomVinylDetail.insertInfo = connectorDetail.getField("information", "");
/*  837 */               bomVinylDetail.insertStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*  842 */             else if (connectorDetail.getIntegerField("part_id") == 21) {
/*      */               
/*  844 */               bomVinylDetail.stickerOnePartId = connectorDetail.getIntegerField("part_id");
/*  845 */               bomVinylDetail.stickerOnePart = connectorDetail.getField("part", "");
/*  846 */               bomVinylDetail.stickerOneSupplier = connectorDetail.getField("supplier", "");
/*  847 */               bomVinylDetail.stickerOneSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  848 */               bomVinylDetail.stickerOneInk1 = connectorDetail.getField("ink1", "");
/*  849 */               bomVinylDetail.stickerOneInk2 = connectorDetail.getField("ink2", "");
/*  850 */               bomVinylDetail.stickerOnePlaces = connectorDetail.getField("selection", "");
/*  851 */               bomVinylDetail.stickerOneInfo = connectorDetail.getField("information", "");
/*  852 */               bomVinylDetail.stickerOneStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  854 */             else if (connectorDetail.getIntegerField("part_id") == 22) {
/*      */               
/*  856 */               bomVinylDetail.stickerTwoPartId = connectorDetail.getIntegerField("part_id");
/*  857 */               bomVinylDetail.stickerTwoPart = connectorDetail.getField("part", "");
/*  858 */               bomVinylDetail.stickerTwoSupplier = connectorDetail.getField("supplier", "");
/*  859 */               bomVinylDetail.stickerTwoSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  860 */               bomVinylDetail.stickerTwoInk1 = connectorDetail.getField("ink1", "");
/*  861 */               bomVinylDetail.stickerTwoInk2 = connectorDetail.getField("ink2", "");
/*  862 */               bomVinylDetail.stickerTwoPlaces = connectorDetail.getField("selection", "");
/*  863 */               bomVinylDetail.stickerTwoInfo = connectorDetail.getField("information", "");
/*  864 */               bomVinylDetail.stickerTwoStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  866 */             else if (connectorDetail.getIntegerField("part_id") == 18) {
/*      */               
/*  868 */               bomVinylDetail.otherPartId = connectorDetail.getIntegerField("part_id");
/*  869 */               bomVinylDetail.otherPart = connectorDetail.getField("part", "");
/*  870 */               bomVinylDetail.otherSupplier = connectorDetail.getField("supplier", "");
/*  871 */               bomVinylDetail.otherSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  872 */               bomVinylDetail.otherInk1 = connectorDetail.getField("ink1", "");
/*  873 */               bomVinylDetail.otherInk2 = connectorDetail.getField("ink2", "");
/*  874 */               bomVinylDetail.otherInfo = connectorDetail.getField("information", "");
/*  875 */               bomVinylDetail.otherStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             } 
/*      */             
/*  878 */             connectorDetail.next();
/*      */           } 
/*      */           
/*  881 */           bom.setBomVinylDetail(bomVinylDetail);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  887 */           BomDVDDetail bomDiskDetail = null;
/*      */           
/*  889 */           if (connectorDetail.more()) {
/*  890 */             bomDiskDetail = new BomDVDDetail();
/*      */           }
/*      */           
/*  893 */           while (connectorDetail.more()) {
/*      */ 
/*      */             
/*  896 */             if (connectorDetail.getIntegerField("part_id") == 29) {
/*      */               
/*  898 */               bomDiskDetail.wrapPartId = connectorDetail.getIntegerField("part_id");
/*  899 */               bomDiskDetail.wrapPart = connectorDetail.getField("part", "");
/*  900 */               bomDiskDetail.wrapSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  901 */               bomDiskDetail.wrapSupplier = connectorDetail.getField("supplier", "");
/*  902 */               bomDiskDetail.wrapInk1 = connectorDetail.getField("ink1", "");
/*  903 */               bomDiskDetail.wrapInk2 = connectorDetail.getField("ink2", "");
/*  904 */               bomDiskDetail.wrapInfo = connectorDetail.getField("information", "");
/*  905 */               bomDiskDetail.wrapStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             } 
/*      */             
/*  908 */             if (connectorDetail.getIntegerField("part_id") == 30) {
/*      */               
/*  910 */               bomDiskDetail.dvdCasePartId = connectorDetail.getIntegerField("part_id");
/*  911 */               bomDiskDetail.dvdPart = connectorDetail.getField("part", "");
/*  912 */               bomDiskDetail.dvdInk1 = connectorDetail.getField("ink1", "");
/*  913 */               bomDiskDetail.dvdInk2 = connectorDetail.getField("ink2", "");
/*  914 */               bomDiskDetail.dvdInfo = connectorDetail.getField("information", "");
/*  915 */               bomDiskDetail.dvdStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*  916 */               bomDiskDetail.dvdSelectionInfo = connectorDetail.getField("selection", "");
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  921 */             if (connectorDetail.getIntegerField("part_id") == 32) {
/*      */               
/*  923 */               bomDiskDetail.bluRayCasePartId = connectorDetail.getIntegerField("part_id");
/*  924 */               bomDiskDetail.bluRayPart = connectorDetail.getField("part", "");
/*  925 */               bomDiskDetail.bluRayInk1 = connectorDetail.getField("ink1", "");
/*  926 */               bomDiskDetail.bluRayInk2 = connectorDetail.getField("ink2", "");
/*  927 */               bomDiskDetail.bluRayInfo = connectorDetail.getField("information", "");
/*  928 */               bomDiskDetail.bluRayStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*  929 */               bomDiskDetail.bluRaySelectionInfo = connectorDetail.getField("selection", "");
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  935 */             if (connectorDetail.getIntegerField("part_id") == 7) {
/*      */               
/*  937 */               bomDiskDetail.discPartId = connectorDetail.getIntegerField("part_id");
/*  938 */               bomDiskDetail.discPart = connectorDetail.getField("part", "");
/*  939 */               bomDiskDetail.diskSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  940 */               bomDiskDetail.discInk1 = connectorDetail.getField("ink1", "");
/*  941 */               bomDiskDetail.discInk2 = connectorDetail.getField("ink2", "");
/*  942 */               bomDiskDetail.discSupplier = connectorDetail.getField("supplier", "");
/*  943 */               bomDiskDetail.discInfo = connectorDetail.getField("information", "");
/*  944 */               bomDiskDetail.discStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*  945 */               bomDiskDetail.discSelectionInfo = connectorDetail.getField("selection", "");
/*      */             }
/*  947 */             else if (connectorDetail.getIntegerField("part_id") == 12) {
/*      */               
/*  949 */               bomDiskDetail.jewelPartId = connectorDetail.getIntegerField("part_id");
/*  950 */               bomDiskDetail.jewelPart = connectorDetail.getField("part", "");
/*  951 */               bomDiskDetail.jewelInfo = connectorDetail.getField("information", "");
/*  952 */               bomDiskDetail.jewelColor = connectorDetail.getField("selection", "");
/*  953 */               bomDiskDetail.jewelStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  955 */             else if (connectorDetail.getIntegerField("part_id") == 23) {
/*      */               
/*  957 */               bomDiskDetail.trayPartId = connectorDetail.getIntegerField("part_id");
/*  958 */               bomDiskDetail.trayPart = connectorDetail.getField("part", "");
/*  959 */               bomDiskDetail.trayInfo = connectorDetail.getField("information", "");
/*  960 */               bomDiskDetail.trayColor = connectorDetail.getField("selection", "");
/*  961 */               bomDiskDetail.trayStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  963 */             else if (connectorDetail.getIntegerField("part_id") == 10) {
/*      */               
/*  965 */               bomDiskDetail.inlayPartId = connectorDetail.getIntegerField("part_id");
/*  966 */               bomDiskDetail.inlayPart = connectorDetail.getField("part", "");
/*  967 */               bomDiskDetail.inlaySupplierId = connectorDetail.getIntegerField("supplier_id");
/*  968 */               bomDiskDetail.inlaySupplier = connectorDetail.getField("supplier", "");
/*  969 */               bomDiskDetail.inlayInk1 = connectorDetail.getField("ink1", "");
/*  970 */               bomDiskDetail.inlayInk2 = connectorDetail.getField("ink2", "");
/*  971 */               bomDiskDetail.inlayInfo = connectorDetail.getField("information", "");
/*  972 */               bomDiskDetail.inlayStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  974 */             else if (connectorDetail.getIntegerField("part_id") == 9) {
/*      */               
/*  976 */               bomDiskDetail.frontPartId = connectorDetail.getIntegerField("part_id");
/*  977 */               bomDiskDetail.frontPart = connectorDetail.getField("part", "");
/*  978 */               bomDiskDetail.frontSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  979 */               bomDiskDetail.frontSupplier = connectorDetail.getField("supplier", "");
/*  980 */               bomDiskDetail.frontInk1 = connectorDetail.getField("ink1", "");
/*  981 */               bomDiskDetail.frontInk2 = connectorDetail.getField("ink2", "");
/*  982 */               bomDiskDetail.frontInfo = connectorDetail.getField("information", "");
/*  983 */               bomDiskDetail.frontStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  985 */             else if (connectorDetail.getIntegerField("part_id") == 8) {
/*      */               
/*  987 */               bomDiskDetail.folderPartId = connectorDetail.getIntegerField("part_id");
/*  988 */               bomDiskDetail.folderPart = connectorDetail.getField("part", "");
/*  989 */               bomDiskDetail.folderSupplierId = connectorDetail.getIntegerField("supplier_id");
/*  990 */               bomDiskDetail.folderSupplier = connectorDetail.getField("supplier", "");
/*  991 */               bomDiskDetail.folderInk1 = connectorDetail.getField("ink1", "");
/*  992 */               bomDiskDetail.folderInk2 = connectorDetail.getField("ink2", "");
/*  993 */               bomDiskDetail.folderPages = connectorDetail.getField("selection", "");
/*  994 */               bomDiskDetail.folderInfo = connectorDetail.getField("information", "");
/*  995 */               bomDiskDetail.folderStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/*  997 */             else if (connectorDetail.getIntegerField("part_id") == 1) {
/*      */               
/*  999 */               bomDiskDetail.bookletPartId = connectorDetail.getIntegerField("part_id");
/* 1000 */               bomDiskDetail.bookletPart = connectorDetail.getField("part", "");
/* 1001 */               bomDiskDetail.bookletSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1002 */               bomDiskDetail.bookletSupplier = connectorDetail.getField("supplier", "");
/* 1003 */               bomDiskDetail.bookletInk1 = connectorDetail.getField("ink1", "");
/* 1004 */               bomDiskDetail.bookletInk2 = connectorDetail.getField("ink2", "");
/* 1005 */               bomDiskDetail.bookletPages = connectorDetail.getField("selection", "");
/* 1006 */               bomDiskDetail.bookletInfo = connectorDetail.getField("information", "");
/* 1007 */               bomDiskDetail.bookletStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/* 1009 */             else if (connectorDetail.getIntegerField("part_id") == 4) {
/*      */               
/* 1011 */               bomDiskDetail.brcPartId = connectorDetail.getIntegerField("part_id");
/* 1012 */               bomDiskDetail.brcPart = connectorDetail.getField("part", "");
/* 1013 */               bomDiskDetail.brcSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1014 */               bomDiskDetail.brcSupplier = connectorDetail.getField("supplier", "");
/* 1015 */               bomDiskDetail.brcInk1 = connectorDetail.getField("ink1", "");
/* 1016 */               bomDiskDetail.brcInk2 = connectorDetail.getField("ink2", "");
/* 1017 */               bomDiskDetail.brcSize = connectorDetail.getField("selection", "");
/* 1018 */               bomDiskDetail.brcInfo = connectorDetail.getField("information", "");
/* 1019 */               bomDiskDetail.brcStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/* 1021 */             else if (connectorDetail.getIntegerField("part_id") == 15) {
/*      */               
/* 1023 */               bomDiskDetail.miniPartId = connectorDetail.getIntegerField("part_id");
/* 1024 */               bomDiskDetail.miniPart = connectorDetail.getField("part", "");
/* 1025 */               bomDiskDetail.miniSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1026 */               bomDiskDetail.miniSupplier = connectorDetail.getField("supplier", "");
/* 1027 */               bomDiskDetail.miniInk1 = connectorDetail.getField("ink1", "");
/* 1028 */               bomDiskDetail.miniInk2 = connectorDetail.getField("ink2", "");
/* 1029 */               bomDiskDetail.miniInfo = connectorDetail.getField("information", "");
/* 1030 */               bomDiskDetail.miniStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/* 1032 */             else if (connectorDetail.getIntegerField("part_id") == 6) {
/*      */               
/* 1034 */               bomDiskDetail.digiPakPartId = connectorDetail.getIntegerField("part_id");
/* 1035 */               bomDiskDetail.digiPakPart = connectorDetail.getField("part", "");
/* 1036 */               bomDiskDetail.digiPakSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1037 */               bomDiskDetail.digiPakSupplier = connectorDetail.getField("supplier", "");
/* 1038 */               bomDiskDetail.digiPakInk1 = connectorDetail.getField("ink1", "");
/* 1039 */               bomDiskDetail.digiPakInk2 = connectorDetail.getField("ink2", "");
/* 1040 */               bomDiskDetail.digiPakTray = connectorDetail.getField("selection", "");
/* 1041 */               bomDiskDetail.digiPakInfo = connectorDetail.getField("information", "");
/* 1042 */               bomDiskDetail.digiPakStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1047 */             if (connectorDetail.getIntegerField("part_id") == 31) {
/*      */               
/* 1049 */               bomDiskDetail.softPakPartId = connectorDetail.getIntegerField("part_id");
/* 1050 */               bomDiskDetail.softPakPart = connectorDetail.getField("part", "");
/* 1051 */               bomDiskDetail.softPakSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1052 */               bomDiskDetail.softPakSupplier = connectorDetail.getField("supplier", "");
/* 1053 */               bomDiskDetail.softPakInk1 = connectorDetail.getField("ink1", "");
/* 1054 */               bomDiskDetail.softPakInk2 = connectorDetail.getField("ink2", "");
/* 1055 */               bomDiskDetail.softPakTray = connectorDetail.getField("selection", "");
/* 1056 */               bomDiskDetail.softPakInfo = connectorDetail.getField("information", "");
/* 1057 */               bomDiskDetail.softPakStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */ 
/*      */             
/*      */             }
/* 1061 */             else if (connectorDetail.getIntegerField("part_id") == 21) {
/*      */               
/* 1063 */               bomDiskDetail.stickerOnePartId = connectorDetail.getIntegerField("part_id");
/* 1064 */               bomDiskDetail.stickerOnePart = connectorDetail.getField("part", "");
/* 1065 */               bomDiskDetail.stickerOneSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1066 */               bomDiskDetail.stickerOneSupplier = connectorDetail.getField("supplier", "");
/* 1067 */               bomDiskDetail.stickerOneInk1 = connectorDetail.getField("ink1", "");
/* 1068 */               bomDiskDetail.stickerOneInk2 = connectorDetail.getField("ink2", "");
/* 1069 */               bomDiskDetail.stickerOnePlaces = connectorDetail.getField("selection", "");
/* 1070 */               bomDiskDetail.stickerOneInfo = connectorDetail.getField("information", "");
/* 1071 */               bomDiskDetail.stickerOneStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/* 1073 */             else if (connectorDetail.getIntegerField("part_id") == 22) {
/*      */               
/* 1075 */               bomDiskDetail.stickerTwoPartId = connectorDetail.getIntegerField("part_id");
/* 1076 */               bomDiskDetail.stickerTwoPart = connectorDetail.getField("part", "");
/* 1077 */               bomDiskDetail.stickerTwoSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1078 */               bomDiskDetail.stickerTwoSupplier = connectorDetail.getField("supplier", "");
/* 1079 */               bomDiskDetail.stickerTwoInk1 = connectorDetail.getField("ink1", "");
/* 1080 */               bomDiskDetail.stickerTwoInk2 = connectorDetail.getField("ink2", "");
/* 1081 */               bomDiskDetail.stickerTwoPlaces = connectorDetail.getField("selection", "");
/* 1082 */               bomDiskDetail.stickerTwoInfo = connectorDetail.getField("information", "");
/* 1083 */               bomDiskDetail.stickerTwoStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/* 1085 */             else if (connectorDetail.getIntegerField("part_id") == 2) {
/*      */               
/* 1087 */               bomDiskDetail.bookPartId = connectorDetail.getIntegerField("part_id");
/* 1088 */               bomDiskDetail.bookPart = connectorDetail.getField("part", "");
/* 1089 */               bomDiskDetail.bookSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1090 */               bomDiskDetail.bookSupplier = connectorDetail.getField("supplier", "");
/* 1091 */               bomDiskDetail.bookInk1 = connectorDetail.getField("ink1", "");
/* 1092 */               bomDiskDetail.bookInk2 = connectorDetail.getField("ink2", "");
/* 1093 */               bomDiskDetail.bookPages = connectorDetail.getField("selection", "");
/* 1094 */               bomDiskDetail.bookInfo = connectorDetail.getField("information", "");
/* 1095 */               bomDiskDetail.bookStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/* 1097 */             else if (connectorDetail.getIntegerField("part_id") == 3) {
/*      */               
/* 1099 */               bomDiskDetail.boxPartId = connectorDetail.getIntegerField("part_id");
/* 1100 */               bomDiskDetail.boxPart = connectorDetail.getField("part", "");
/* 1101 */               bomDiskDetail.boxSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1102 */               bomDiskDetail.boxSupplier = connectorDetail.getField("supplier", "");
/* 1103 */               bomDiskDetail.boxInk1 = connectorDetail.getField("ink1", "");
/* 1104 */               bomDiskDetail.boxInk2 = connectorDetail.getField("ink2", "");
/* 1105 */               bomDiskDetail.boxSizes = connectorDetail.getField("selection", "");
/* 1106 */               bomDiskDetail.boxInfo = connectorDetail.getField("information", "");
/* 1107 */               bomDiskDetail.boxStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             }
/* 1109 */             else if (connectorDetail.getIntegerField("part_id") == 18) {
/*      */               
/* 1111 */               bomDiskDetail.otherPartId = connectorDetail.getIntegerField("part_id");
/* 1112 */               bomDiskDetail.otherPart = connectorDetail.getField("part", "");
/* 1113 */               bomDiskDetail.otherSupplierId = connectorDetail.getIntegerField("supplier_id");
/* 1114 */               bomDiskDetail.otherSupplier = connectorDetail.getField("supplier", "");
/* 1115 */               bomDiskDetail.otherInk1 = connectorDetail.getField("ink1", "");
/* 1116 */               bomDiskDetail.otherInk2 = connectorDetail.getField("ink2", "");
/* 1117 */               bomDiskDetail.otherInfo = connectorDetail.getField("information", "");
/* 1118 */               bomDiskDetail.otherStatusIndicator = connectorDetail.getBoolean("status_indicator");
/*      */             } 
/*      */             
/* 1121 */             connectorDetail.next();
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1127 */           if (selectionConfiguration.equalsIgnoreCase("DVV") && 
/* 1128 */             !bom.getFormat().equalsIgnoreCase("CDO")) {
/* 1129 */             bom.setBomDVDDetail(bomDiskDetail);
/*      */           } else {
/* 1131 */             bom.setBomDiskDetail(bomDiskDetail);
/*      */           } 
/*      */         } 
/*      */         
/* 1135 */         connectorDetail.close();
/*      */       } 
/*      */ 
/*      */       
/* 1139 */       connectorHeader.close();
/* 1140 */       connectorBom.close();
/* 1141 */       return bom;
/*      */     } 
/* 1143 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int mapReleaseId2BomId(int pReleaseId) {
/* 1151 */     int bomId = 0;
/*      */     
/* 1153 */     String mapReleaseId2BomIdQuery = "SELECT bom_id FROM vi_Bom_Header WHERE release_id = " + 
/*      */       
/* 1155 */       pReleaseId + 
/* 1156 */       ";";
/*      */ 
/*      */ 
/*      */     
/* 1160 */     JdbcConnector connector = MilestoneHelper.getConnector(mapReleaseId2BomIdQuery);
/* 1161 */     connector.runQuery();
/*      */     
/* 1163 */     if (connector.more())
/*      */     {
/* 1165 */       bomId = connector.getIntegerField("bom_id");
/*      */     }
/* 1167 */     connector.close();
/*      */     
/* 1169 */     return bomId;
/*      */   }
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
/*      */   public Selection getSelectionHeader(int id) {
/* 1183 */     String releaseHeaderQuery = "sp_get_Schedule_Release_Header " + id;
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
/* 1211 */     JdbcConnector connector = MilestoneHelper.getConnector(releaseHeaderQuery);
/* 1212 */     connector.runQuery();
/*      */     
/* 1214 */     Selection selection = null;
/*      */     
/* 1216 */     if (connector.more()) {
/*      */       
/* 1218 */       selection = new Selection();
/* 1219 */       selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */       
/* 1221 */       String selectionNo = "";
/* 1222 */       if (connector.getFieldByName("selection_no") != null)
/* 1223 */         selectionNo = connector.getFieldByName("selection_no"); 
/* 1224 */       selection.setSelectionNo(selectionNo);
/*      */       
/* 1226 */       selection.setProjectID(connector.getField("project_no", ""));
/*      */       
/* 1228 */       String titleId = "";
/* 1229 */       if (connector.getFieldByName("title_id") != null)
/* 1230 */         titleId = connector.getFieldByName("title_id"); 
/* 1231 */       selection.setTitleID(titleId);
/*      */       
/* 1233 */       selection.setTitle(connector.getField("title", ""));
/* 1234 */       selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 1235 */       selection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 1236 */       selection.setArtist(connector.getField("artist", ""));
/* 1237 */       selection.setFlArtist(connector.getField("fl_artist", ""));
/* 1238 */       selection.setASide(connector.getField("side_a_title", ""));
/* 1239 */       selection.setBSide(connector.getField("side_b_title", ""));
/*      */       
/* 1241 */       selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/* 1242 */             Cache.getProductCategories()));
/* 1243 */       selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
/* 1244 */             Cache.getReleaseTypes()));
/*      */       
/* 1246 */       selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
/* 1247 */             Cache.getSelectionConfigs()));
/*      */       
/* 1249 */       selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
/* 1250 */             selection.getSelectionConfig()));
/*      */       
/* 1252 */       selection.setUpc(connector.getField("upc", ""));
/*      */       
/* 1254 */       String sellCodeString = connector.getFieldByName("price_code");
/* 1255 */       if (sellCodeString != null)
/*      */       {
/* 1257 */         selection.setPriceCode(getPriceCode(sellCodeString));
/*      */       }
/*      */       
/* 1260 */       selection.setSellCode(sellCodeString);
/*      */ 
/*      */       
/* 1263 */       String sellCodeStringDPC = connector.getFieldByName("digital_price_code");
/* 1264 */       if (sellCodeStringDPC != null)
/*      */       {
/* 1266 */         selection.setPriceCodeDPC(getPriceCode(sellCodeStringDPC));
/*      */       }
/* 1268 */       selection.setSellCodeDPC(sellCodeStringDPC);
/*      */ 
/*      */       
/* 1271 */       selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
/* 1272 */             Cache.getMusicTypes()));
/* 1273 */       selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/* 1274 */       selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/* 1275 */       selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/* 1276 */       selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
/* 1277 */       selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
/*      */       
/* 1279 */       String streetDateString = connector.getFieldByName("street_date");
/* 1280 */       if (streetDateString != null) {
/* 1281 */         selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */       }
/* 1283 */       String internationalDateString = connector.getFieldByName("international_date");
/* 1284 */       if (internationalDateString != null) {
/* 1285 */         selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString));
/*      */       }
/* 1287 */       selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*      */       
/* 1289 */       if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/* 1290 */         selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*      */       }
/* 1292 */       selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 1293 */             Cache.getSelectionStatusList()));
/* 1294 */       selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*      */       
/* 1296 */       selection.setHoldReason(connector.getField("hold_reason", ""));
/* 1297 */       selection.setComments(connector.getField("comments", ""));
/* 1298 */       selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/*      */ 
/*      */       
/* 1301 */       selection.setNumberOfUnits(connector.getIntegerField("units"));
/*      */       
/* 1303 */       selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */       
/* 1305 */       selection.setNoDigitalRelease(connector.getBoolean("no_digital_release"));
/*      */       
/* 1307 */       selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 1308 */       selection.setSelectionPackaging(connector.getField("packaging", ""));
/*      */       
/* 1310 */       String impactDateString = connector.getFieldByName("impact_date");
/* 1311 */       if (impactDateString != null) {
/* 1312 */         selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString));
/*      */       }
/* 1314 */       String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/* 1315 */       if (lastUpdateDateString != null) {
/* 1316 */         selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString));
/*      */       }
/* 1318 */       selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*      */       
/* 1320 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/*      */       
/* 1322 */       selection.setLastUpdatedCheck(lastUpdatedLong);
/*      */       
/* 1324 */       selection.setPrice(connector.getFloat("list_price"));
/*      */ 
/*      */       
/* 1327 */       String lastChangedOn = connector.getFieldByName("last_changed_on");
/* 1328 */       if (lastChangedOn != null) {
/* 1329 */         selection.setLastStreetUpdateDate(MilestoneHelper.getDatabaseDate(connector.getField("last_changed_on")));
/*      */       }
/*      */       
/* 1332 */       String originDateString = connector.getFieldByName("entered_on");
/* 1333 */       if (originDateString != null) {
/* 1334 */         selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1343 */       selection.setTemplateId(connector.getInt("templateId", -1));
/*      */       
/* 1345 */       selection.setParentalGuidance(connector.getBoolean("parental_indicator"));
/* 1346 */       selection.setSelectionTerritory(connector.getField("territory", ""));
/*      */       
/* 1348 */       String lastSchedUpdateDateString = connector.getFieldByName("last_sched_updated_on");
/* 1349 */       if (lastSchedUpdateDateString != null)
/* 1350 */         selection.setLastSchedUpdateDate(MilestoneHelper.getDatabaseDate(lastSchedUpdateDateString)); 
/* 1351 */       selection.setLastSchedUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_sched_updated_by")));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1358 */       String digitalRlsString = connector.getFieldByName("digital_rls_date");
/* 1359 */       if (digitalRlsString != null) {
/* 1360 */         selection.setDigitalRlsDateString(digitalRlsString);
/* 1361 */         selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(digitalRlsString));
/*      */       } 
/*      */       
/* 1364 */       selection.setInternationalFlag(connector.getBoolean("international_flag"));
/*      */       
/* 1366 */       selection.setOperCompany(connector.getField("oper_company", ""));
/*      */       
/* 1368 */       selection.setSuperLabel(connector.getField("super_label", ""));
/*      */       
/* 1370 */       selection.setSubLabel(connector.getField("sub_label", ""));
/*      */       
/* 1372 */       selection.setConfigCode(connector.getField("config_code", ""));
/*      */       
/* 1374 */       selection.setSoundScanGrp(connector.getField("soundscan_grp", ""));
/*      */ 
/*      */ 
/*      */       
/* 1378 */       selection.setImprint(connector.getField("imprint", ""));
/* 1379 */       selection.setNewBundleFlag(connector.getBoolean("new_bundle_flag"));
/* 1380 */       selection.setGridNumber(connector.getField("grid_number", ""));
/* 1381 */       selection.setSpecialInstructions(connector.getField("special_instructions", ""));
/* 1382 */       selection.setIsDigital(connector.getBoolean("digital_flag"));
/* 1383 */       selection.setPriority(connector.getBoolean("priority_flag"));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1388 */       selection.setArchimedesId(connector.getInt("archimedes_id", -1));
/* 1389 */       selection.setReleaseFamilyId(connector.getInt("release_family_id", -1));
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
/* 1404 */       String impactSql = "sp_get_Schedule_ImpactDates " + id;
/*      */       
/* 1406 */       JdbcConnector impactConnector = MilestoneHelper.getConnector(impactSql);
/* 1407 */       impactConnector.runQuery();
/*      */       
/* 1409 */       Vector impactDates = new Vector();
/*      */       
/* 1411 */       while (impactConnector.more()) {
/*      */         
/* 1413 */         ImpactDate impact = new ImpactDate();
/*      */         
/* 1415 */         impact.setImpactDateID(impactConnector.getInt("impactDate_Id", -1));
/* 1416 */         impact.setSelectionID(impactConnector.getInt("selection_Id", -1));
/* 1417 */         impact.setFormat(impactConnector.getField("format", ""));
/* 1418 */         impact.setFormatDescription(impactConnector.getField("description", ""));
/*      */         
/* 1420 */         String impactString = impactConnector.getFieldByName("impactDate");
/* 1421 */         if (impactString != null) {
/* 1422 */           impact.setImpactDate(MilestoneHelper.getDatabaseDate(impactString));
/*      */         }
/* 1424 */         impact.setTbi(impactConnector.getBoolean("tbi"));
/*      */         
/* 1426 */         impactDates.add(impact);
/*      */         
/* 1428 */         impactConnector.next();
/*      */       } 
/*      */       
/* 1431 */       impactConnector.close();
/*      */       
/* 1433 */       selection.setImpactDates(impactDates);
/*      */ 
/*      */ 
/*      */       
/* 1437 */       selection.setMultSelections(getMultSelections(id));
/*      */ 
/*      */       
/* 1440 */       selection.setMultOtherContacts(getMultOtherContacts(id));
/*      */ 
/*      */       
/* 1443 */       selection.setFullSelection(true);
/*      */ 
/*      */ 
/*      */       
/* 1447 */       JdbcConnector archieConnector = MilestoneHelper.getConnector("sp_get_archie_last_update_date " + id);
/* 1448 */       archieConnector.runQuery();
/*      */       
/* 1450 */       if (archieConnector.more()) {
/*      */         
/* 1452 */         String archieString = archieConnector.getFieldByName("audit_date");
/*      */ 
/*      */         
/* 1455 */         if (archieString != null) {
/* 1456 */           selection.setArchieDate(MilestoneHelper.getDatabaseDate(archieString));
/*      */         }
/*      */       } 
/* 1459 */       archieConnector.close();
/*      */ 
/*      */       
/* 1462 */       JdbcConnector autoCloseConnector = MilestoneHelper.getConnector("sp_get_auto_close_date " + id);
/* 1463 */       autoCloseConnector.runQuery();
/* 1464 */       if (autoCloseConnector.more()) {
/*      */         
/* 1466 */         String autoCloseStr = autoCloseConnector.getFieldByName("auto_closed_date");
/* 1467 */         if (autoCloseStr != null)
/* 1468 */           selection.setAutoCloseDate(MilestoneHelper.getDatabaseDate(autoCloseStr)); 
/*      */       } 
/* 1470 */       autoCloseConnector.close();
/*      */ 
/*      */ 
/*      */       
/* 1474 */       JdbcConnector lastLegacyUpdateConnector = MilestoneHelper.getConnector("sp_get_last_legacy_update_date " + id);
/* 1475 */       lastLegacyUpdateConnector.runQuery();
/* 1476 */       if (lastLegacyUpdateConnector.more()) {
/*      */         
/* 1478 */         String lastLegacyUpdateStr = lastLegacyUpdateConnector.getFieldByName("legacy_date");
/* 1479 */         if (lastLegacyUpdateStr != null)
/* 1480 */           selection.setLastLegacyUpdateDate(MilestoneHelper.getDatabaseDate(lastLegacyUpdateStr)); 
/*      */       } 
/* 1482 */       lastLegacyUpdateConnector.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1487 */     connector.close();
/*      */     
/* 1489 */     return selection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSelectionManufacturingSubDetail(Selection selection) {
/* 1498 */     boolean newFlag = true;
/*      */     
/* 1500 */     if (selection != null) {
/*      */       
/* 1502 */       int selectionId = selection.getSelectionID();
/*      */       
/* 1504 */       String manufacturingQuery = "SELECT * FROM vi_Release_Subdetail WHERE release_id = " + 
/*      */         
/* 1506 */         selectionId + 
/* 1507 */         ";";
/*      */       
/* 1509 */       JdbcConnector connector = MilestoneHelper.getConnector(manufacturingQuery);
/* 1510 */       connector.runQuery();
/*      */ 
/*      */ 
/*      */       
/* 1514 */       if (connector.more()) {
/*      */         
/* 1516 */         newFlag = false;
/* 1517 */         User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("uml_id"));
/* 1518 */         selection.setUmlContact(umlContact);
/* 1519 */         selection.setPlant(getLookupObject(connector.getField("plant"), Cache.getVendors()));
/* 1520 */         selection.setDistribution(getLookupObject(connector.getField("distribution"), Cache.getDistributionCodes()));
/* 1521 */         selection.setPoQuantity(connector.getIntegerField("order_qty"));
/*      */         
/* 1523 */         selection.setCompletedQuantity(connector.getIntegerField("complete_qty"));
/* 1524 */         selection.setManufacturingComments(connector.getField("order_comments", ""));
/*      */         
/* 1526 */         String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/* 1527 */         if (lastUpdateDateString != null) {
/* 1528 */           selection.setLastMfgUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString));
/*      */         }
/* 1530 */         selection.setLastMfgUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*      */         
/* 1532 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck", "0"), 16);
/* 1533 */         selection.setLastMfgUpdatedCheck(lastUpdatedLong);
/*      */       } 
/*      */ 
/*      */       
/* 1537 */       connector.close();
/*      */ 
/*      */       
/* 1540 */       String plantSql = "select * from Manufacturing_Plants where release_id = " + selection.getSelectionID();
/* 1541 */       JdbcConnector plantConnector = MilestoneHelper.getConnector(plantSql);
/* 1542 */       plantConnector.runQuery();
/*      */       
/* 1544 */       Vector plants = new Vector();
/*      */       
/* 1546 */       while (plantConnector.more()) {
/*      */         
/* 1548 */         Plant plant = new Plant();
/*      */         
/* 1550 */         plant.setPlantID(plantConnector.getInt("plantId", -1));
/* 1551 */         plant.setSelectionID(plantConnector.getField("selection_Id"));
/* 1552 */         plant.setReleaseID(plantConnector.getInt("release_Id", -1));
/* 1553 */         plant.setPlant(getLookupObject(plantConnector.getField("plant"), Cache.getVendors()));
/* 1554 */         plant.setOrderQty(plantConnector.getInt("order_qty", -1));
/* 1555 */         plant.setCompletedQty(plantConnector.getInt("complete_qty", -1));
/*      */         
/* 1557 */         plants.add(plant);
/*      */         
/* 1559 */         plantConnector.next();
/*      */       } 
/*      */       
/* 1562 */       plantConnector.close();
/*      */       
/* 1564 */       selection.setManufacturingPlants(plants);
/*      */     } 
/*      */     
/* 1567 */     return newFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PriceCode getPriceCode(String sellCode) {
/* 1575 */     PriceCode priceCode = null;
/*      */     
/* 1577 */     Vector priceCodes = Cache.getPriceCodes();
/* 1578 */     for (int i = 0; i < priceCodes.size(); i++) {
/*      */       
/* 1580 */       PriceCode pc = (PriceCode)priceCodes.get(i);
/* 1581 */       if (pc.getSellCode().equalsIgnoreCase(sellCode)) {
/*      */         
/* 1583 */         priceCode = pc;
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
/*      */         break;
/*      */       } 
/*      */     } 
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
/* 1631 */     return priceCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Selection saveSelection(Selection selection, User updatingUser) {
/* 1643 */     System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + selection.getCompanyId());
/* 1644 */     System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + selection.getEnvironmentId());
/*      */     
/* 1646 */     int holdSelection = 0;
/* 1647 */     int specialPackaging = 0;
/* 1648 */     int pressAndDistribution = 0;
/* 1649 */     int parentalGuidance = 0;
/*      */     
/* 1651 */     int internationalFlag = 0;
/* 1652 */     int noDigitalRelease = 0;
/*      */ 
/*      */     
/* 1655 */     if (selection.getNoDigitalRelease()) {
/* 1656 */       noDigitalRelease = 1;
/*      */     }
/* 1658 */     if (selection.getPressAndDistribution()) {
/* 1659 */       pressAndDistribution = 1;
/*      */     }
/* 1661 */     if (selection.getSpecialPackaging()) {
/* 1662 */       specialPackaging = 1;
/*      */     }
/* 1664 */     if (selection.getHoldSelection()) {
/* 1665 */       holdSelection = 1;
/*      */     }
/* 1667 */     if (selection.getParentalGuidance()) {
/* 1668 */       parentalGuidance = 1;
/*      */     }
/*      */     
/* 1671 */     if (selection.getInternationalFlag()) {
/* 1672 */       internationalFlag = 1;
/*      */     }
/* 1674 */     String prefix = "";
/* 1675 */     if (selection.getPrefixID() != null) {
/* 1676 */       prefix = selection.getPrefixID().getAbbreviation();
/*      */     }
/* 1678 */     long timestamp = selection.getLastUpdatedCheck();
/* 1679 */     String familyID = "-1";
/* 1680 */     String companyID = "-1";
/* 1681 */     String divisionID = "-1";
/* 1682 */     String labelID = "-1";
/* 1683 */     String environmentId = "-1";
/* 1684 */     String labelContactId = "-1";
/* 1685 */     String territory = selection.getSelectionTerritory();
/*      */ 
/*      */     
/* 1688 */     String imprint = selection.getImprint();
/* 1689 */     boolean isDigital = selection.getIsDigital();
/* 1690 */     boolean new_bundle_flag = selection.getNewBundleFlag();
/* 1691 */     String grid_number = selection.getGridNumber();
/* 1692 */     String special_instructions = selection.getSpecialInstructions();
/*      */     
/* 1694 */     int isDigitalFlag = 0;
/* 1695 */     if (selection.getIsDigital()) {
/* 1696 */       isDigitalFlag = 1;
/*      */     }
/* 1698 */     int new_bundle = 0;
/* 1699 */     if (selection.getNewBundleFlag()) {
/* 1700 */       new_bundle = 1;
/*      */     }
/* 1702 */     int priority = 0;
/* 1703 */     if (selection.getPriority()) {
/* 1704 */       priority = 1;
/*      */     }
/*      */     
/* 1707 */     if (selection.getCompany() != null) {
/* 1708 */       environmentId = String.valueOf(selection.getCompany().getParentEnvironment().getStructureID());
/*      */     }
/*      */     
/* 1711 */     if (selection.getCompany() != null) {
/* 1712 */       companyID = String.valueOf(selection.getCompany().getStructureID());
/*      */     }
/* 1714 */     if (selection.getDivision() != null) {
/* 1715 */       divisionID = String.valueOf(selection.getDivision().getStructureID());
/*      */     }
/* 1717 */     if (selection.getLabel() != null) {
/* 1718 */       labelID = String.valueOf(selection.getLabel().getStructureID());
/*      */     }
/* 1720 */     if (selection.getCompany() != null && selection.getCompany().getParentEnvironment() != null) {
/*      */       
/* 1722 */       familyID = String.valueOf(selection.getCompany().getParentEnvironment().getParentID());
/*      */ 
/*      */ 
/*      */       
/* 1726 */       selection.setFamily((Family)MilestoneHelper.getStructureObject(selection.getCompany().getParentEnvironment().getParentID()));
/*      */     } 
/*      */ 
/*      */     
/* 1730 */     if (selection.getLabelContact() != null) {
/* 1731 */       labelContactId = String.valueOf(selection.getLabelContact().getUserId());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1736 */     boolean cprsIsNew = (selection.getSelectionID() < 0);
/*      */     
/* 1738 */     String query = "sp_sav_Release_Header " + 
/* 1739 */       selection.getSelectionID() + "," + 
/* 1740 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getProjectID()) + "'," + 
/* 1741 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getTitleID()) + "'," + 
/* 1742 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getTitle()) + "'," + 
/* 1743 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getArtist()) + "'," + 
/* 1744 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getArtistLastName()) + "'," + 
/* 1745 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getArtistFirstName()) + "'," + 
/* 1746 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getASide()) + "'," + 
/* 1747 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getBSide()) + "'," + 
/* 1748 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionNo()) + "'," + 
/* 1749 */       "'" + "version number" + "'," + 
/* 1750 */       "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getProductCategory())) + "'," + 
/* 1751 */       "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getReleaseType())) + "'," + 
/* 1752 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionConfig().getSelectionConfigurationAbbreviation()) + "'," + 
/* 1753 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation()) + "'," + 
/*      */       
/* 1755 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(selection.getUpc(), "UPC", selection.getIsDigital(), true)) + "'," + 
/* 1756 */       "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getGenre())) + "'," + 
/* 1757 */       familyID + "," + 
/* 1758 */       companyID + "," + 
/* 1759 */       divisionID + "," + 
/* 1760 */       labelID + "," + 
/* 1761 */       "'" + MilestoneHelper.getFormatedDate(selection.getStreetDate()) + "'," + 
/* 1762 */       "'" + MilestoneHelper.getFormatedDate(selection.getInternationalDate()) + "'," + 
/* 1763 */       "0" + "," + 
/* 1764 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getOtherContact()) + "'," + 
/* 1765 */       labelContactId + "," + 
/* 1766 */       "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getSelectionStatus())) + "'," + 
/* 1767 */       holdSelection + "," + 
/* 1768 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getHoldReason()) + "'," + 
/* 1769 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionComments()) + "'," + 
/* 1770 */       "'" + "group" + "'," + 
/* 1771 */       specialPackaging + "," + 
/* 1772 */       selection.getPrice() + "," + 
/* 1773 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSellCode()) + "'," + 
/* 1774 */       selection.getNumberOfUnits() + "," + 
/* 1775 */       pressAndDistribution + "," + 
/* 1776 */       "'" + MilestoneHelper.escapeSingleQuotes(prefix) + "'," + 
/* 1777 */       "'" + "reference" + "'," + 
/* 1778 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionPackaging()) + "'," + 
/* 1779 */       "'" + "packaging comments" + "'," + 
/* 1780 */       "'" + MilestoneHelper.getFormatedDate(selection.getImpactDate()) + "'," + 
/* 1781 */       "'" + MilestoneHelper.getFormatedDate(selection.getLastStreetUpdateDate()) + "'," + 
/*      */       
/* 1783 */       updatingUser.getUserId() + "," + 
/* 1784 */       selection.getTemplateId() + "," + 
/* 1785 */       parentalGuidance + ",'" + 
/* 1786 */       territory + "'," + 
/* 1787 */       timestamp + "," + 
/*      */       
/* 1789 */       "'" + MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()) + "'," + 
/* 1790 */       internationalFlag + "," + 
/* 1791 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getOperCompany()) + "'," + 
/* 1792 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSuperLabel()) + "'," + 
/* 1793 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSubLabel()) + "'," + 
/* 1794 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getConfigCode()) + "'," + 
/*      */       
/* 1796 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(selection.getSoundScanGrp(), "SSG", selection.getIsDigital(), true)) + "'," + 
/* 1797 */       environmentId + "," + 
/* 1798 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getImprint()) + "'," + 
/* 1799 */       new_bundle + "," + 
/* 1800 */       "'" + MilestoneHelper.escapeSingleQuotes(grid_number) + "'," + 
/* 1801 */       "'" + MilestoneHelper.escapeSingleQuotes(special_instructions) + "'," + 
/* 1802 */       isDigitalFlag + "," + 
/* 1803 */       "'" + MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()) + "'," + 
/*      */       
/* 1805 */       getArchimedesIdFromCache(Integer.parseInt(labelID)) + "," + 
/* 1806 */       selection.getReleaseFamilyId() + "," + 
/* 1807 */       priority + "," + noDigitalRelease + ",'" + selection.getSellCodeDPC() + "' ";
/*      */ 
/*      */     
/* 1810 */     log.log("Query >>>>>>>>>>>>>> : " + query);
/*      */ 
/*      */     
/* 1813 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1814 */     connector.runQuery();
/*      */     
/* 1816 */     int newId = connector.getIntegerField("ReturnId");
/* 1817 */     log.debug("Selection id from db " + newId);
/* 1818 */     log.debug("Selection id from db " + newId);
/*      */ 
/*      */     
/* 1821 */     if (selection.getSelectionID() < 0) {
/* 1822 */       selection.setSelectionID(newId);
/*      */     }
/* 1824 */     connector.close();
/*      */ 
/*      */     
/* 1827 */     log.debug("Flushing selection audits.");
/* 1828 */     selection.flushAudits(updatingUser.getUserId());
/*      */     
/* 1830 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
/*      */       
/* 1832 */       selection.getSelectionID() + 
/* 1833 */       ";";
/*      */     
/* 1835 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 1836 */     connectorTimestamp.runQuery();
/* 1837 */     if (connectorTimestamp.more()) {
/*      */       
/* 1839 */       selection.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 1840 */       selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*      */     } 
/* 1842 */     connectorTimestamp.close();
/*      */ 
/*      */     
/* 1845 */     saveImpactDates(selection, updatingUser);
/*      */ 
/*      */     
/* 1848 */     saveMultSelections(selection, updatingUser);
/*      */ 
/*      */     
/* 1851 */     saveMultOtherContacts(selection, updatingUser);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1860 */     DcGDRSResults dcGDRSResults = SelectionHandler.GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
/* 1861 */     boolean IsGDRSactive = (!dcGDRSResults.getStatus().equals("") && !dcGDRSResults.getStatus().equals("DELETE"));
/* 1862 */     if (IsPfmDraftOrFinal(selection.getSelectionID()))
/*      */     {
/*      */       
/* 1865 */       if (noDigitalRelease == 0) {
/*      */ 
/*      */         
/* 1868 */         if (!dcGDRSResults.getForceNoDigitalRelease().booleanValue())
/* 1869 */           GDRS_QueueAddReleaseId(selection, "CREATE_EDIT"); 
/* 1870 */       } else if (IsGDRSactive) {
/* 1871 */         GDRS_QueueAddReleaseId(selection, "DELETE");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1880 */       if (!selection.getProjectID().equals("0000-00000"))
/*      */       {
/* 1882 */         projectSearchSvcClient.MilestoneSnapshotProjectInsert(selection.getProjectID());
/*      */       }
/* 1884 */     } catch (Exception e) {
/* 1885 */       System.out.println(e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/* 1889 */     return selection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveImpactDates(Selection selection, User updatingUser) {
/* 1899 */     if (selection != null && selection.getImpactDates() != null) {
/*      */       
/* 1901 */       Vector impactDates = selection.getImpactDates();
/*      */       
/* 1903 */       Vector addImpactDates = new Vector();
/* 1904 */       Vector deleteImpactDates = new Vector();
/*      */       
/* 1906 */       String impactQuery = "select * from vi_ImpactDates where selection_id = " + selection.getSelectionID();
/*      */ 
/*      */ 
/*      */       
/* 1910 */       boolean delete = true;
/*      */       
/* 1912 */       for (int a = 0; a < impactDates.size(); a++) {
/*      */         
/* 1914 */         ImpactDate impactDate = (ImpactDate)impactDates.get(a);
/*      */ 
/*      */ 
/*      */         
/* 1918 */         JdbcConnector connectorImpactCount = MilestoneHelper.getConnector(impactQuery);
/* 1919 */         connectorImpactCount.runQuery();
/*      */         
/* 1921 */         if (connectorImpactCount.more()) {
/*      */           
/* 1923 */           while (connectorImpactCount.more())
/*      */           {
/*      */ 
/*      */             
/* 1927 */             if (impactDate.getImpactDateID() == -1 || impactDate.getImpactDateID() == connectorImpactCount.getInt("impactDate_Id", -2)) {
/*      */               
/* 1929 */               addImpactDates.add(impactDate);
/*      */               break;
/*      */             } 
/* 1932 */             connectorImpactCount.next();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1937 */           addImpactDates.add(impactDate);
/*      */         } 
/*      */         
/* 1940 */         connectorImpactCount.close();
/*      */       } 
/*      */       
/* 1943 */       JdbcConnector connectorImpactDelete = MilestoneHelper.getConnector(impactQuery);
/* 1944 */       connectorImpactDelete.runQuery();
/*      */       
/* 1946 */       while (connectorImpactDelete.more()) {
/*      */         
/* 1948 */         delete = true;
/*      */         
/* 1950 */         for (int b = 0; b < impactDates.size(); b++) {
/*      */           
/* 1952 */           ImpactDate impactDateDelete = (ImpactDate)impactDates.get(b);
/*      */           
/* 1954 */           if (connectorImpactDelete.getInt("impactDate_Id", -2) == impactDateDelete.getImpactDateID())
/*      */           {
/* 1956 */             delete = false;
/*      */           }
/*      */         } 
/*      */         
/* 1960 */         if (delete) {
/*      */           
/* 1962 */           ImpactDate delImpactDate = new ImpactDate();
/* 1963 */           delImpactDate.setImpactDateID(connectorImpactDelete.getInt("impactDate_Id", -1));
/* 1964 */           deleteImpactDates.add(delImpactDate);
/*      */         } 
/*      */         
/* 1967 */         connectorImpactDelete.next();
/*      */       } 
/* 1969 */       connectorImpactDelete.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1975 */       for (int i = 0; i < addImpactDates.size(); i++) {
/*      */         
/* 1977 */         ImpactDate impact = (ImpactDate)addImpactDates.get(i);
/*      */         
/* 1979 */         int tbi = impact.getTbi() ? 1 : 0;
/*      */         
/* 1981 */         int selId = impact.getSelectionID();
/* 1982 */         int impactDateId = impact.getImpactDateID();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1987 */         if (selId < 0) {
/* 1988 */           selId = selection.getSelectionID();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1999 */         String impactSql = "sp_sav_ImpactDate " + impact.getImpactDateID() + "," + 
/* 2000 */           selId + "," + 
/* 2001 */           "'" + impact.getFormat() + "'," + 
/* 2002 */           "'" + MilestoneHelper.getFormatedDate(impact.getImpactDate()) + "'," + 
/* 2003 */           tbi;
/*      */ 
/*      */ 
/*      */         
/* 2007 */         JdbcConnector connectorAddImpact = MilestoneHelper.getConnector(impactSql);
/* 2008 */         connectorAddImpact.runQuery();
/* 2009 */         connectorAddImpact.close();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2014 */       JdbcConnector connectorDeleteImpact = null;
/* 2015 */       for (int k = 0; k < deleteImpactDates.size(); k++) {
/*      */         
/* 2017 */         ImpactDate impact = (ImpactDate)deleteImpactDates.get(k);
/*      */         
/* 2019 */         String impactDeleteSql = "sp_del_ImpactDate " + impact.getImpactDateID();
/*      */ 
/*      */ 
/*      */         
/* 2023 */         connectorDeleteImpact = MilestoneHelper.getConnector(impactDeleteSql);
/* 2024 */         connectorDeleteImpact.runQuery();
/* 2025 */         connectorDeleteImpact.close();
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public void savePlants(Selection selection, User updatingUser) {
/* 2041 */     if (selection != null && selection.getManufacturingPlants() != null) {
/*      */       
/* 2043 */       Vector plants = selection.getManufacturingPlants();
/*      */       
/* 2045 */       Vector addPlants = new Vector();
/* 2046 */       Vector deletePlants = new Vector();
/*      */       
/* 2048 */       String plantQuery = "select * from Manufacturing_Plants where release_id = " + selection.getSelectionID();
/*      */ 
/*      */ 
/*      */       
/* 2052 */       boolean delete = true;
/*      */       
/* 2054 */       for (int a = 0; a < plants.size(); a++) {
/*      */         
/* 2056 */         Plant plant = (Plant)plants.get(a);
/*      */         
/* 2058 */         JdbcConnector connectorPlantCount = MilestoneHelper.getConnector(plantQuery);
/* 2059 */         connectorPlantCount.runQuery();
/*      */         
/* 2061 */         if (connectorPlantCount.more()) {
/*      */           
/* 2063 */           while (connectorPlantCount.more())
/*      */           {
/*      */ 
/*      */             
/* 2067 */             if (plant.getPlantID() == -1 || plant.getPlantID() == connectorPlantCount.getInt("plantId", -2)) {
/*      */               
/* 2069 */               addPlants.add(plant);
/*      */               break;
/*      */             } 
/* 2072 */             connectorPlantCount.next();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2077 */           addPlants.add(plant);
/*      */         } 
/*      */         
/* 2080 */         connectorPlantCount.close();
/*      */       } 
/*      */       
/* 2083 */       JdbcConnector connectorPlantDelete = MilestoneHelper.getConnector(plantQuery);
/* 2084 */       connectorPlantDelete.runQuery();
/*      */       
/* 2086 */       while (connectorPlantDelete.more()) {
/*      */         
/* 2088 */         delete = true;
/*      */         
/* 2090 */         for (int b = 0; b < plants.size(); b++) {
/*      */           
/* 2092 */           Plant plantDelete = (Plant)plants.get(b);
/*      */           
/* 2094 */           if (connectorPlantDelete.getInt("plantId", -2) == plantDelete.getPlantID())
/*      */           {
/* 2096 */             delete = false;
/*      */           }
/*      */         } 
/*      */         
/* 2100 */         if (delete) {
/*      */           
/* 2102 */           Plant delPlant = new Plant();
/* 2103 */           delPlant.setPlantID(connectorPlantDelete.getInt("plantId", -1));
/* 2104 */           deletePlants.add(delPlant);
/*      */         } 
/*      */         
/* 2107 */         connectorPlantDelete.next();
/*      */       } 
/* 2109 */       connectorPlantDelete.close();
/*      */ 
/*      */       
/* 2112 */       for (int i = 0; i < addPlants.size(); i++) {
/*      */         
/* 2114 */         Plant plant = (Plant)addPlants.get(i);
/*      */         
/* 2116 */         String plantId = "-1";
/* 2117 */         if (plant.getPlant() != null) {
/* 2118 */           plantId = plant.getPlant().getAbbreviation();
/*      */         }
/*      */         
/* 2121 */         String plantSql = "sp_sav_Plant " + plant.getPlantID() + ",'" + 
/* 2122 */           plant.getSelectionID() + "'," + 
/* 2123 */           plantId + "," + 
/* 2124 */           plant.getOrderQty() + "," + 
/* 2125 */           plant.getCompletedQty() + "," + 
/* 2126 */           plant.getReleaseID();
/*      */ 
/*      */ 
/*      */         
/* 2130 */         JdbcConnector connectorAddPlant = MilestoneHelper.getConnector(plantSql);
/* 2131 */         connectorAddPlant.runQuery();
/* 2132 */         connectorAddPlant.close();
/*      */       } 
/*      */ 
/*      */       
/* 2136 */       JdbcConnector connectorDeletePlant = null;
/* 2137 */       for (int k = 0; k < deletePlants.size(); k++) {
/*      */         
/* 2139 */         Plant plant = (Plant)deletePlants.get(k);
/*      */         
/* 2141 */         String plantDeleteSql = "sp_del_Plant " + plant.getPlantID();
/*      */ 
/*      */ 
/*      */         
/* 2145 */         connectorDeletePlant = MilestoneHelper.getConnector(plantDeleteSql);
/* 2146 */         connectorDeletePlant.runQuery();
/* 2147 */         connectorDeletePlant.close();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTemplateId(Selection selection, User updatingUser) {
/* 2161 */     String query = "UPDATE Release_Header SET templateId = " + selection.getTemplateId() + 
/* 2162 */       " WHERE release_id = " + selection.getSelectionID();
/*      */     
/* 2164 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2165 */     connector.runQuery();
/* 2166 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateStatusToClose(Selection selection) {
/* 2175 */     String query = "UPDATE Release_Header SET status = 'CLOSED' WHERE release_id = " + 
/* 2176 */       selection.getSelectionID();
/*      */     
/* 2178 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2179 */     connector.runQuery();
/* 2180 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateComment(Selection selection) {
/* 2190 */     String query = "UPDATE Release_Header SET comments = '" + 
/* 2191 */       MilestoneHelper.escapeSingleQuotes(selection.getSelectionComments()) + "' " + 
/* 2192 */       " WHERE release_id = " + selection.getSelectionID();
/*      */     
/* 2194 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2195 */     connector.runQuery();
/* 2196 */     connector.close();
/*      */   }
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
/*      */   public String getAssignedTemplateName(Selection selection) {
/* 2210 */     if (selection == null)
/* 2211 */       return ""; 
/* 2212 */     String query = "sp_get_Schedule_Template_Header " + selection.getTemplateId();
/*      */     
/* 2214 */     String templateName = "";
/*      */     
/* 2216 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2217 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */     
/* 2221 */     if (connector.more()) {
/* 2222 */       templateName = connector.getField("name", "");
/*      */     }
/* 2224 */     connector.close();
/*      */     
/* 2226 */     return templateName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Selection saveManufacturingSelection(Selection selection, User updatingUser, boolean newFlag) {
/* 2234 */     long timestamp = selection.getLastMfgUpdatedCheck();
/*      */     
/* 2236 */     String spName = "sp_upd_Release_Subdetail";
/*      */ 
/*      */     
/* 2239 */     if (newFlag) {
/* 2240 */       spName = "sp_ins_Release_Subdetail";
/*      */     }
/* 2242 */     int umlContactId = 0;
/* 2243 */     if (selection.getUmlContact() != null) {
/* 2244 */       umlContactId = selection.getUmlContact().getUserId();
/*      */     }
/* 2246 */     String plantId = "";
/* 2247 */     if (selection.getPlant() != null) {
/* 2248 */       plantId = selection.getPlant().getAbbreviation();
/*      */     }
/* 2250 */     String distributionId = "";
/* 2251 */     if (selection.getDistribution() != null) {
/* 2252 */       distributionId = selection.getDistribution().getAbbreviation();
/*      */     }
/* 2254 */     String query = String.valueOf(spName) + " " + 
/* 2255 */       selection.getSelectionID() + "," + 
/* 2256 */       umlContactId + "," + 
/* 2257 */       "'" + MilestoneHelper.escapeSingleQuotes(plantId) + "'," + 
/* 2258 */       "'" + MilestoneHelper.escapeSingleQuotes(distributionId) + "'," + 
/* 2259 */       selection.getPoQuantity() + "," + 
/* 2260 */       selection.getNumberOfUnits() + "," + 
/* 2261 */       selection.getCompletedQuantity() + "," + 
/* 2262 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getManufacturingComments()) + "'," + 
/* 2263 */       updatingUser.getUserId();
/*      */     
/* 2265 */     if (!newFlag) {
/* 2266 */       query = String.valueOf(query) + "," + timestamp;
/*      */     }
/* 2268 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2269 */     connector.runQuery();
/* 2270 */     connector.close();
/*      */     
/* 2272 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_subdetail WHERE release_id = " + 
/*      */       
/* 2274 */       selection.getSelectionID() + ";";
/*      */     
/* 2276 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 2277 */     connectorTimestamp.runQuery();
/* 2278 */     if (connectorTimestamp.more()) {
/*      */       
/* 2280 */       selection.setLastMfgUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 2281 */       selection.setLastMfgUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*      */     } 
/* 2283 */     connectorTimestamp.close();
/*      */ 
/*      */     
/* 2286 */     savePlants(selection, updatingUser);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2294 */     return selection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean deleteSelection(Selection selection, User updatingUser) {
/* 2304 */     boolean isDeletable = true;
/* 2305 */     int selectionId = selection.getSelectionID();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2310 */     if (selection.getSchedule() == null) {
/* 2311 */       selection.setSchedule(ScheduleManager.getInstance().getSchedule(selectionId));
/*      */     }
/*      */     
/*      */     try {
/* 2315 */       if (selection.getSchedule().getTasks().size() > 0) {
/* 2316 */         isDeletable = false;
/*      */       }
/* 2318 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2323 */     if (isDeletable) {
/*      */       
/* 2325 */       String query = "sp_del_Releases " + 
/* 2326 */         selectionId;
/*      */       
/* 2328 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2329 */       connector.runQuery();
/* 2330 */       connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2336 */       GDRS_QueueAddReleaseId(selection, "DELETE");
/*      */     } 
/*      */     
/* 2339 */     return isDeletable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Pfm savePfm(Pfm pfm, User updatingUser, boolean isDigital) {
/* 2349 */     long timestamp = pfm.getLastUpdatedCk();
/*      */ 
/*      */ 
/*      */     
/* 2353 */     int userID = -1;
/* 2354 */     if (updatingUser != null) {
/* 2355 */       userID = updatingUser.getUserId();
/*      */     }
/* 2357 */     String query = "sp_upd_PFM_Selection " + 
/* 2358 */       pfm.getReleaseId() + ",'" + 
/* 2359 */       MilestoneHelper.escapeSingleQuotes(pfm.getReleaseType()) + "','" + 
/* 2360 */       MilestoneHelper.escapeSingleQuotes(pfm.getMode()) + "','" + 
/* 2361 */       MilestoneHelper.escapeSingleQuotes(pfm.getPrintOption()) + "','" + 
/* 2362 */       MilestoneHelper.escapeSingleQuotes(pfm.getPreparedBy()) + "','" + 
/* 2363 */       MilestoneHelper.escapeSingleQuotes(pfm.getEmail()) + "','" + 
/* 2364 */       MilestoneHelper.escapeSingleQuotes(pfm.getPhone()) + "','" + 
/* 2365 */       MilestoneHelper.escapeSingleQuotes(pfm.getFaxNumber()) + "','" + 
/* 2366 */       MilestoneHelper.escapeSingleQuotes(pfm.getComments()) + "','" + 
/* 2367 */       MilestoneHelper.escapeSingleQuotes(pfm.getOperatingCompany()) + "','" + 
/* 2368 */       MilestoneHelper.escapeSingleQuotes(pfm.getProductNumber()) + "','" + 
/* 2369 */       MilestoneHelper.escapeSingleQuotes(pfm.getConfigCode()) + "','" + 
/* 2370 */       MilestoneHelper.escapeSingleQuotes(pfm.getModifier()) + "','" + 
/* 2371 */       MilestoneHelper.escapeSingleQuotes(pfm.getTitle()) + "','" + 
/* 2372 */       MilestoneHelper.escapeSingleQuotes(pfm.getArtist()) + "','" + 
/* 2373 */       MilestoneHelper.escapeSingleQuotes(pfm.getTitleId()) + "','" + 
/* 2374 */       MilestoneHelper.escapeSingleQuotes(pfm.getSuperLabel()) + "','" + 
/* 2375 */       MilestoneHelper.escapeSingleQuotes(pfm.getLabelCode()) + "','" + 
/* 2376 */       MilestoneHelper.escapeSingleQuotes(pfm.getCompanyCode()) + "','" + 
/* 2377 */       MilestoneHelper.escapeSingleQuotes(pfm.getPoMergeCode()) + "'," + 
/* 2378 */       pfm.getUnitsPerSet() + "," + 
/* 2379 */       pfm.getSetsPerCarton() + ",'" + 
/* 2380 */       MilestoneHelper.escapeSingleQuotes(pfm.getSupplier()) + "','" + 
/* 2381 */       MilestoneHelper.escapeSingleQuotes(pfm.getImportIndicator()) + "','" + 
/*      */       
/* 2383 */       MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getUpc(), "UPC", isDigital, true)) + "','" + 
/* 2384 */       MilestoneHelper.escapeSingleQuotes(pfm.getMusicLine()) + "','" + 
/* 2385 */       MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireOwner()) + "','" + 
/* 2386 */       MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireClass()) + "','" + 
/* 2387 */       MilestoneHelper.escapeSingleQuotes(pfm.getReturnCode()) + "','" + 
/* 2388 */       MilestoneHelper.escapeSingleQuotes(pfm.getExportFlag()) + "','" + 
/* 2389 */       MilestoneHelper.escapeSingleQuotes(pfm.getCountries()) + "','" + 
/* 2390 */       MilestoneHelper.escapeSingleQuotes(pfm.getSpineTitle()) + "','" + 
/* 2391 */       MilestoneHelper.escapeSingleQuotes(pfm.getSpineArtist()) + "','" + 
/* 2392 */       MilestoneHelper.escapeSingleQuotes(pfm.getPriceCode()) + "','" + 
/* 2393 */       MilestoneHelper.escapeSingleQuotes(pfm.getGuaranteeCode()) + "','" + 
/* 2394 */       MilestoneHelper.escapeSingleQuotes(pfm.getLoosePickExemptCode()) + "','" + 
/* 2395 */       MilestoneHelper.escapeSingleQuotes(pfm.getCompilationCode()) + "','" + 
/* 2396 */       MilestoneHelper.escapeSingleQuotes(pfm.getImpRateCode()) + "','" + 
/*      */       
/* 2398 */       MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(pfm.getMusicType())) + "','" + 
/* 2399 */       MilestoneHelper.escapeSingleQuotes(pfm.getNarmFlag()) + "','" + 
/* 2400 */       MilestoneHelper.escapeSingleQuotes(pfm.getPricePoint()) + "','" + 
/* 2401 */       MilestoneHelper.escapeSingleQuotes(pfm.getApprovedByName()) + "','" + 
/* 2402 */       MilestoneHelper.escapeSingleQuotes(pfm.getEnteredByName()) + "','" + 
/* 2403 */       MilestoneHelper.escapeSingleQuotes(pfm.getVerifiedByName()) + "','" + 
/* 2404 */       MilestoneHelper.escapeSingleQuotes(pfm.getChangeNumber()) + "','" + 
/* 2405 */       MilestoneHelper.getFormatedDate(pfm.getStreetDate()) + "','" + 
/* 2406 */       MilestoneHelper.escapeSingleQuotes(pfm.getProjectID()) + "'," + (
/* 2407 */       pfm.getParentalGuidance() ? 1 : 0) + ",'" + 
/*      */       
/* 2409 */       MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getSoundScanGrp(), "SSG", isDigital, true)) + "'," + 
/* 2410 */       userID + "," + 
/* 2411 */       timestamp + "," + (
/* 2412 */       pfm.getValueAdded() ? 1 : 0) + "," + (
/* 2413 */       pfm.getBoxSet() ? 1 : 0) + ",'" + 
/* 2414 */       MilestoneHelper.escapeSingleQuotes(pfm.getEncryptionFlag()) + "','" + 
/* 2415 */       MilestoneHelper.escapeSingleQuotes(pfm.getStatus()) + "','" + 
/* 2416 */       MilestoneHelper.escapeSingleQuotes(pfm.getPriceCodeDPC()) + "'";
/*      */ 
/*      */ 
/*      */     
/* 2420 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2421 */     connector.runQuery();
/* 2422 */     connector.close();
/*      */     
/* 2424 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on, last_updated_by  FROM vi_PFM_Selection WHERE release_id = " + 
/*      */       
/* 2426 */       pfm.getReleaseId() + 
/* 2427 */       ";";
/*      */ 
/*      */     
/* 2430 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 2431 */     connectorTimestamp.runQuery();
/*      */     
/* 2433 */     if (connectorTimestamp.more()) {
/*      */       
/* 2435 */       pfm.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 2436 */       pfm.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/* 2437 */       pfm.setLastUpdatingUser(connectorTimestamp.getIntegerField("last_updated_by"));
/*      */     } 
/* 2439 */     connectorTimestamp.close();
/*      */     
/* 2441 */     return pfm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Pfm saveNewPfm(Pfm pfm, User updatingUser, boolean isDigital) {
/* 2450 */     String query = "sp_ins_PFM_Selection " + 
/* 2451 */       pfm.getReleaseId() + ",'" + 
/* 2452 */       MilestoneHelper.escapeSingleQuotes(pfm.getReleaseType()) + "','" + 
/* 2453 */       MilestoneHelper.escapeSingleQuotes(pfm.getMode()) + "','" + 
/* 2454 */       MilestoneHelper.escapeSingleQuotes(pfm.getPrintOption()) + "','" + 
/* 2455 */       MilestoneHelper.escapeSingleQuotes(pfm.getPreparedBy()) + "','" + 
/* 2456 */       MilestoneHelper.escapeSingleQuotes(pfm.getEmail()) + "','" + 
/* 2457 */       MilestoneHelper.escapeSingleQuotes(pfm.getPhone()) + "','" + 
/* 2458 */       MilestoneHelper.escapeSingleQuotes(pfm.getFaxNumber()) + "','" + 
/* 2459 */       MilestoneHelper.escapeSingleQuotes(pfm.getComments()) + "','" + 
/* 2460 */       MilestoneHelper.escapeSingleQuotes(pfm.getOperatingCompany()) + "','" + 
/* 2461 */       MilestoneHelper.escapeSingleQuotes(pfm.getProductNumber()) + "','" + 
/* 2462 */       MilestoneHelper.escapeSingleQuotes(pfm.getConfigCode()) + "','" + 
/* 2463 */       MilestoneHelper.escapeSingleQuotes(pfm.getModifier()) + "','" + 
/* 2464 */       MilestoneHelper.escapeSingleQuotes(pfm.getTitle()) + "','" + 
/* 2465 */       MilestoneHelper.escapeSingleQuotes(pfm.getArtist()) + "','" + 
/* 2466 */       MilestoneHelper.escapeSingleQuotes(pfm.getTitleId()) + "','" + 
/* 2467 */       MilestoneHelper.escapeSingleQuotes(pfm.getSuperLabel()) + "','" + 
/* 2468 */       MilestoneHelper.escapeSingleQuotes(pfm.getLabelCode()) + "','" + 
/* 2469 */       MilestoneHelper.escapeSingleQuotes(pfm.getCompanyCode()) + "','" + 
/* 2470 */       MilestoneHelper.escapeSingleQuotes(pfm.getPoMergeCode()) + "'," + 
/* 2471 */       pfm.getUnitsPerSet() + "," + 
/* 2472 */       pfm.getSetsPerCarton() + ",'" + 
/* 2473 */       MilestoneHelper.escapeSingleQuotes(pfm.getSupplier()) + "','" + 
/* 2474 */       MilestoneHelper.escapeSingleQuotes(pfm.getImportIndicator()) + "','" + 
/*      */       
/* 2476 */       MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getUpc(), "UPC", isDigital, true)) + "','" + 
/* 2477 */       MilestoneHelper.escapeSingleQuotes(pfm.getMusicLine()) + "','" + 
/* 2478 */       MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireOwner()) + "','" + 
/* 2479 */       MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireClass()) + "','" + 
/* 2480 */       MilestoneHelper.escapeSingleQuotes(pfm.getReturnCode()) + "','" + 
/* 2481 */       MilestoneHelper.escapeSingleQuotes(pfm.getExportFlag()) + "','" + 
/* 2482 */       MilestoneHelper.escapeSingleQuotes(pfm.getCountries()) + "','" + 
/* 2483 */       MilestoneHelper.escapeSingleQuotes(pfm.getSpineTitle()) + "','" + 
/* 2484 */       MilestoneHelper.escapeSingleQuotes(pfm.getSpineArtist()) + "','" + 
/* 2485 */       MilestoneHelper.escapeSingleQuotes(pfm.getPriceCode()) + "','" + 
/* 2486 */       MilestoneHelper.escapeSingleQuotes(pfm.getGuaranteeCode()) + "','" + 
/* 2487 */       MilestoneHelper.escapeSingleQuotes(pfm.getLoosePickExemptCode()) + "','" + 
/* 2488 */       MilestoneHelper.escapeSingleQuotes(pfm.getCompilationCode()) + "','" + 
/* 2489 */       MilestoneHelper.escapeSingleQuotes(pfm.getImpRateCode()) + "','" + 
/*      */       
/* 2491 */       MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(pfm.getMusicType())) + "','" + 
/* 2492 */       MilestoneHelper.escapeSingleQuotes(pfm.getNarmFlag()) + "','" + 
/* 2493 */       MilestoneHelper.escapeSingleQuotes(pfm.getPricePoint()) + "','" + 
/* 2494 */       MilestoneHelper.escapeSingleQuotes(pfm.getApprovedByName()) + "','" + 
/* 2495 */       MilestoneHelper.escapeSingleQuotes(pfm.getEnteredByName()) + "','" + 
/* 2496 */       MilestoneHelper.escapeSingleQuotes(pfm.getChangeNumber()) + "','" + 
/* 2497 */       MilestoneHelper.getFormatedDate(pfm.getStreetDate()) + "','" + 
/* 2498 */       MilestoneHelper.escapeSingleQuotes(pfm.getProjectID()) + "'," + (
/* 2499 */       pfm.getParentalGuidance() ? 1 : 0) + ",'" + 
/*      */       
/* 2501 */       MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getSoundScanGrp(), "SSG", isDigital, true)) + "','" + 
/* 2502 */       MilestoneHelper.escapeSingleQuotes(pfm.getVerifiedByName()) + "'," + 
/* 2503 */       updatingUser.getUserId() + "," + (
/* 2504 */       pfm.getValueAdded() ? 1 : 0) + "," + (
/* 2505 */       pfm.getBoxSet() ? 1 : 0) + ",'" + 
/* 2506 */       MilestoneHelper.escapeSingleQuotes(pfm.getEncryptionFlag()) + "','" + 
/* 2507 */       MilestoneHelper.escapeSingleQuotes(pfm.getStatus()) + "','" + 
/* 2508 */       MilestoneHelper.escapeSingleQuotes(pfm.getPriceCodeDPC()) + "'";
/*      */ 
/*      */ 
/*      */     
/* 2512 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2513 */     connector.runQuery();
/*      */ 
/*      */     
/* 2516 */     if (pfm.getReleaseId() != -1) {
/*      */       
/* 2518 */       String lastUpdatedQuery = "SELECT last_updated_on, last_updated_by  FROM Pfm_Selection WHERE release_id = " + 
/*      */         
/* 2520 */         pfm.getReleaseId() + 
/* 2521 */         ";";
/*      */       
/* 2523 */       JdbcConnector connectorLastUpdated = MilestoneHelper.getConnector(lastUpdatedQuery);
/* 2524 */       connectorLastUpdated.runQuery();
/* 2525 */       if (connectorLastUpdated.more()) {
/*      */ 
/*      */         
/* 2528 */         String lastDateString = connectorLastUpdated.getFieldByName("last_updated_on");
/* 2529 */         if (lastDateString != null) {
/* 2530 */           pfm.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*      */         }
/*      */         
/* 2533 */         pfm.setLastUpdatingUser(connectorLastUpdated.getIntegerField("last_updated_by"));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2539 */       connectorLastUpdated.close();
/*      */     } 
/*      */     
/* 2542 */     connector.close();
/*      */     
/* 2544 */     return pfm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Bom saveBom(Bom bom, Selection currentSelection, int userID) {
/* 2553 */     long timestamp = bom.getLastUpdatedCheck();
/*      */     
/* 2555 */     if (bom.getBomId() <= 0) {
/* 2556 */       bom.setBomId(-1);
/*      */     }
/* 2558 */     String hasSpineSticker = "0";
/* 2559 */     if (bom.hasSpineSticker())
/*      */     {
/* 2561 */       hasSpineSticker = "1";
/*      */     }
/*      */     
/* 2564 */     String useShrinkWrap = "0";
/* 2565 */     if (bom.useShrinkWrap())
/*      */     {
/* 2567 */       useShrinkWrap = "1";
/*      */     }
/*      */     
/* 2570 */     String isRetail = "0";
/* 2571 */     if (bom.isRetail())
/*      */     {
/* 2573 */       isRetail = "1";
/*      */     }
/*      */     
/* 2576 */     if (bom.getLabelId() <= 0) {
/*      */       
/* 2578 */       int labelId = -1;
/*      */       
/* 2580 */       if (currentSelection.getLabel() != null) {
/* 2581 */         labelId = currentSelection.getLabel().getStructureID();
/*      */       }
/* 2583 */       bom.setLabelId(labelId);
/*      */     } 
/*      */     
/* 2586 */     String bomSelectionDate = MilestoneHelper.getFormatedDate(bom.getStreetDateOnBom());
/* 2587 */     if (bomSelectionDate.equals("")) {
/* 2588 */       bomSelectionDate = "null,";
/*      */     } else {
/* 2590 */       bomSelectionDate = "'" + MilestoneHelper.getFormatedDate(bom.getStreetDateOnBom()) + "',";
/*      */     } 
/*      */     
/* 2593 */     String query = "sp_sav_Bom_Header " + 
/* 2594 */       bom.getBomId() + "," + 
/* 2595 */       bom.getReleaseId() + "," + 
/* 2596 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getFormat()) + "'," + 
/* 2597 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getPrintOption()) + "'," + 
/* 2598 */       "'" + MilestoneHelper.getFormatedDate(bom.getDate()) + "'," + 
/* 2599 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getType()) + "'," + 
/* 2600 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getChangeNumber()) + "'," + 
/* 2601 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bom.getSubmitter())) + "'," + 
/* 2602 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getEmail()) + "'," + 
/* 2603 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getPhone()) + "'," + 
/* 2604 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getComments()) + "'," + 
/* 2605 */       bom.getLabelId() + "," + 
/* 2606 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getReleasingCompanyId()) + "'," + 
/* 2607 */       isRetail + "," + 
/* 2608 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getSelectionNumber()) + "'," + 
/*      */       
/* 2610 */       bomSelectionDate + 
/* 2611 */       bom.getUnitsPerKG() + "," + 
/* 2612 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getRunTime()) + "'," + 
/* 2613 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getConfiguration()) + "'," + 
/* 2614 */       hasSpineSticker + "," + 
/* 2615 */       useShrinkWrap + "," + 
/* 2616 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bom.getSpecialInstructions())) + "'," + 
/* 2617 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getArtist()) + "'," + 
/* 2618 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getTitle()) + "'," + 
/* 2619 */       userID + "," + 
/* 2620 */       timestamp + "," + 
/* 2621 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getStatus()) + "'," + 
/*      */       
/* 2623 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(bom.getUpc(), "UPC", currentSelection.getIsDigital(), true)) + "'";
/*      */ 
/*      */ 
/*      */     
/* 2627 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2628 */     connector.runQuery();
/*      */     
/* 2630 */     if (bom.getBomId() < 0)
/*      */     {
/* 2632 */       bom.setBomId(connector.getIntegerField("ReturnId"));
/*      */     }
/* 2634 */     connector.close();
/*      */     
/* 2636 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on, last_updated_by  FROM bom_header WHERE bom_id = " + 
/*      */       
/* 2638 */       bom.getBomId() + 
/* 2639 */       ";";
/*      */     
/* 2641 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 2642 */     connectorTimestamp.runQuery();
/* 2643 */     if (connectorTimestamp.more()) {
/*      */       
/* 2645 */       bom.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/*      */ 
/*      */       
/* 2648 */       bom.setModifiedBy(connectorTimestamp.getIntegerField("last_updated_by"));
/*      */       
/* 2650 */       String modifiedOnString = connectorTimestamp.getFieldByName("last_updated_on");
/* 2651 */       if (modifiedOnString != null)
/* 2652 */         bom.setModifiedOn(MilestoneHelper.getDatabaseDate(modifiedOnString)); 
/*      */     } 
/* 2654 */     connectorTimestamp.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2661 */     BomCassetteDetail cassetteDetail = bom.getBomCassetteDetail();
/*      */ 
/*      */     
/* 2664 */     if (cassetteDetail != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2669 */       String coStatusIndicator = "0";
/* 2670 */       if (cassetteDetail.coStatusIndicator)
/*      */       {
/* 2672 */         coStatusIndicator = "1";
/*      */       }
/*      */       
/* 2675 */       String sp = "sp_upd_Bom_Detail ";
/*      */       
/* 2677 */       if (cassetteDetail.coPartId < 0) {
/* 2678 */         sp = "sp_ins_Bom_Detail ";
/*      */       }
/* 2680 */       String queryCO = String.valueOf(sp) + 
/* 2681 */         bom.getBomId() + "," + 
/* 2682 */         "5" + "," + 
/* 2683 */         cassetteDetail.coParSupplierId + "," + 
/* 2684 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.coInk1) + "'," + 
/* 2685 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.coInk2) + "'," + 
/* 2686 */         "''," + 
/* 2687 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.coColor) + "'," + 
/* 2688 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.coInfo)) + "'," + 
/* 2689 */         coStatusIndicator + "," + 
/* 2690 */         userID;
/*      */       
/* 2692 */       JdbcConnector connectorCO = MilestoneHelper.getConnector(queryCO);
/* 2693 */       connectorCO.runQuery();
/* 2694 */       connectorCO.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2699 */       String norelcoStatusIndicator = "0";
/* 2700 */       if (cassetteDetail.norelcoStatusIndicator)
/*      */       {
/* 2702 */         norelcoStatusIndicator = "1";
/*      */       }
/*      */       
/* 2705 */       String sp1 = "sp_upd_Bom_Detail ";
/*      */       
/* 2707 */       if (cassetteDetail.norelcoPartId < 0) {
/* 2708 */         sp1 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2710 */       String queryNorelco = String.valueOf(sp1) + 
/* 2711 */         bom.getBomId() + "," + 
/* 2712 */         "16" + "," + 
/* 2713 */         cassetteDetail.norelcoSupplierId + "," + 
/* 2714 */         "''," + 
/* 2715 */         "''," + 
/* 2716 */         "''," + 
/* 2717 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.norelcoColor) + "'," + 
/* 2718 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.norelcoInfo)) + "'," + 
/* 2719 */         norelcoStatusIndicator + "," + 
/* 2720 */         userID;
/*      */       
/* 2722 */       JdbcConnector connectorNorelco = MilestoneHelper.getConnector(queryNorelco);
/* 2723 */       connectorNorelco.runQuery();
/* 2724 */       connectorNorelco.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2730 */       String jCardStatusIndicator = "0";
/* 2731 */       if (cassetteDetail.jCardStatusIndicator)
/*      */       {
/* 2733 */         jCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2736 */       String sp2 = "sp_upd_Bom_Detail ";
/*      */       
/* 2738 */       if (cassetteDetail.jCardPartId < 0) {
/* 2739 */         sp2 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2741 */       String queryJCARD = String.valueOf(sp2) + 
/* 2742 */         bom.getBomId() + "," + 
/* 2743 */         "13" + "," + 
/* 2744 */         cassetteDetail.jCardSupplierId + "," + 
/* 2745 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.jCardInk1) + "'," + 
/* 2746 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.jCardInk2) + "'," + 
/* 2747 */         "''," + 
/* 2748 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.jCardPanels)) + "'," + 
/* 2749 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.jCardInfo)) + "'," + 
/* 2750 */         jCardStatusIndicator + "," + 
/* 2751 */         userID;
/*      */       
/* 2753 */       JdbcConnector connectorJCARD = MilestoneHelper.getConnector(queryJCARD);
/* 2754 */       connectorJCARD.runQuery();
/* 2755 */       connectorJCARD.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2760 */       String uCardStatusIndicator = "0";
/* 2761 */       if (cassetteDetail.uCardStatusIndicator)
/*      */       {
/* 2763 */         uCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2766 */       String sp3 = "sp_upd_Bom_Detail ";
/*      */       
/* 2768 */       if (cassetteDetail.uCardPartId < 0) {
/* 2769 */         sp3 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2771 */       String queryUCARD = String.valueOf(sp3) + 
/* 2772 */         bom.getBomId() + "," + 
/* 2773 */         "24" + "," + 
/* 2774 */         cassetteDetail.uCardSupplierId + "," + 
/* 2775 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.uCardInk1) + "'," + 
/* 2776 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.uCardInk2) + "'," + 
/* 2777 */         "''," + 
/* 2778 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.uCardPanels)) + "'," + 
/* 2779 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.uCardInfo)) + "'," + 
/* 2780 */         uCardStatusIndicator + "," + 
/* 2781 */         userID;
/*      */       
/* 2783 */       JdbcConnector connectorUCARD = MilestoneHelper.getConnector(queryUCARD);
/* 2784 */       connectorUCARD.runQuery();
/* 2785 */       connectorUCARD.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2790 */       String oCardStatusIndicator = "0";
/* 2791 */       if (cassetteDetail.oCardStatusIndicator)
/*      */       {
/* 2793 */         oCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2796 */       String sp4 = "sp_upd_Bom_Detail ";
/*      */       
/* 2798 */       if (cassetteDetail.oCardPartId < 0) {
/* 2799 */         sp4 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2801 */       String queryOCARD = String.valueOf(sp4) + 
/* 2802 */         bom.getBomId() + "," + 
/* 2803 */         "17" + "," + 
/* 2804 */         cassetteDetail.oCardSupplierId + "," + 
/* 2805 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.oCardInk1) + "'," + 
/* 2806 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.oCardInk2) + "'," + 
/* 2807 */         "''," + 
/* 2808 */         "''," + 
/* 2809 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.oCardInfo)) + "'," + 
/* 2810 */         oCardStatusIndicator + "," + 
/* 2811 */         userID;
/*      */       
/* 2813 */       JdbcConnector connectorOCARD = MilestoneHelper.getConnector(queryOCARD);
/* 2814 */       connectorOCARD.runQuery();
/* 2815 */       connectorOCARD.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2820 */       String stickerOneCardStatusIndicator = "0";
/* 2821 */       if (cassetteDetail.stickerOneCardStatusIndicator)
/*      */       {
/* 2823 */         stickerOneCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2826 */       String sp5 = "sp_upd_Bom_Detail ";
/*      */       
/* 2828 */       if (cassetteDetail.stickerOneCardPartId < 0) {
/* 2829 */         sp5 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2831 */       String queryStickerOne = String.valueOf(sp5) + 
/* 2832 */         bom.getBomId() + "," + 
/* 2833 */         "21" + "," + 
/* 2834 */         cassetteDetail.stickerOneCardSupplierId + "," + 
/* 2835 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerOneCardInk1) + "'," + 
/* 2836 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerOneCardInk2) + "'," + 
/* 2837 */         "''," + 
/* 2838 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerOneCardPlaces)) + "'," + 
/* 2839 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerOneCardInfo)) + "'," + 
/* 2840 */         stickerOneCardStatusIndicator + "," + 
/* 2841 */         userID;
/*      */       
/* 2843 */       JdbcConnector connectorSticker1 = MilestoneHelper.getConnector(queryStickerOne);
/* 2844 */       connectorSticker1.runQuery();
/* 2845 */       connectorSticker1.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2850 */       String stickerTwoCardStatusIndicator = "0";
/* 2851 */       if (cassetteDetail.stickerTwoCardStatusIndicator)
/*      */       {
/* 2853 */         stickerTwoCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2856 */       String sp6 = "sp_upd_Bom_Detail ";
/*      */       
/* 2858 */       if (cassetteDetail.stickerTwoCardPartId < 0) {
/* 2859 */         sp6 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2861 */       String queryStickerTwo = String.valueOf(sp6) + 
/* 2862 */         bom.getBomId() + "," + 
/* 2863 */         "22" + "," + 
/* 2864 */         cassetteDetail.stickerTwoCardSupplierId + "," + 
/* 2865 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerTwoCardInk1) + "'," + 
/* 2866 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerTwoCardInk2) + "'," + 
/* 2867 */         "''," + 
/* 2868 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerTwoCardPlaces)) + "'," + 
/* 2869 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerTwoCardInfo)) + "'," + 
/* 2870 */         stickerTwoCardStatusIndicator + "," + 
/* 2871 */         userID;
/*      */       
/* 2873 */       JdbcConnector connectorSticker2 = MilestoneHelper.getConnector(queryStickerTwo);
/* 2874 */       connectorSticker2.runQuery();
/* 2875 */       connectorSticker2.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2880 */       String otherStatusIndicator = "0";
/* 2881 */       if (cassetteDetail.otherStatusIndicator)
/*      */       {
/* 2883 */         otherStatusIndicator = "1";
/*      */       }
/*      */       
/* 2886 */       String sp7 = "sp_upd_Bom_Detail ";
/*      */       
/* 2888 */       if (cassetteDetail.otherPartId < 0) {
/* 2889 */         sp7 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2891 */       String queryOther = String.valueOf(sp7) + 
/* 2892 */         bom.getBomId() + "," + 
/* 2893 */         "18" + "," + 
/* 2894 */         cassetteDetail.otherSupplierId + "," + 
/* 2895 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.otherInk1) + "'," + 
/* 2896 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.otherInk2) + "'," + 
/* 2897 */         "''," + 
/* 2898 */         "''," + 
/* 2899 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.otherInfo)) + "'," + 
/* 2900 */         otherStatusIndicator + "," + 
/* 2901 */         userID;
/*      */       
/* 2903 */       JdbcConnector connectorOther = MilestoneHelper.getConnector(queryOther);
/* 2904 */       connectorOther.runQuery();
/* 2905 */       connectorOther.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2916 */     if (bom.getBomDiskDetail() != null || bom.getBomDVDDetail() != null) {
/*      */       BomDVDDetail bomDVDDetail;
/*      */ 
/*      */       
/* 2920 */       if (bom.getBomDiskDetail() != null) {
/* 2921 */         bomDVDDetail = bom.getBomDiskDetail();
/*      */       } else {
/* 2923 */         bomDVDDetail = bom.getBomDVDDetail();
/*      */       } 
/*      */       
/* 2926 */       String DVDDiscSelectionText = "";
/* 2927 */       String DVDCaseSelectionText = "";
/* 2928 */       String blueRayCaseSelectionText = "";
/* 2929 */       if (bom.getBomDVDDetail() != null) {
/*      */ 
/*      */         
/* 2932 */         DVDDiscSelectionText = bom.getBomDVDDetail().getDiscSelectionInfo();
/* 2933 */         DVDCaseSelectionText = bom.getBomDVDDetail().getDVDSelectionInfo();
/*      */ 
/*      */ 
/*      */         
/* 2937 */         String dvdVideoStatusIndicator = "0";
/* 2938 */         if ((bom.getBomDVDDetail()).dvdStatusIndicator) {
/* 2939 */           dvdVideoStatusIndicator = "1";
/*      */         }
/*      */         
/* 2942 */         String spDVDCase = "sp_upd_Bom_Detail ";
/* 2943 */         if ((bom.getBomDVDDetail()).dvdCasePartId < 0) {
/* 2944 */           spDVDCase = "sp_ins_Bom_Detail ";
/*      */         }
/* 2946 */         String query2DVDCase = String.valueOf(spDVDCase) + 
/* 2947 */           bom.getBomId() + "," + 
/* 2948 */           "30" + "," + 
/* 2949 */           "''," + 
/* 2950 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).dvdInk1) + 
/* 2951 */           "'," + 
/* 2952 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).dvdInk2) + 
/* 2953 */           "'," + 
/* 2954 */           "''," + 
/* 2955 */           "'" + MilestoneHelper.escapeSingleQuotes(DVDCaseSelectionText) + "'," + 
/* 2956 */           "'" + 
/* 2957 */           MilestoneHelper.escapeSingleQuotes(
/* 2958 */             MilestoneHelper.escapeDoubleQuotesForHtml(
/* 2959 */               (bom.getBomDVDDetail())
/* 2960 */               .dvdInfo)) + "'," + 
/* 2961 */           dvdVideoStatusIndicator + "," + 
/* 2962 */           userID;
/*      */ 
/*      */         
/* 2965 */         JdbcConnector connector2DVDCase = MilestoneHelper.getConnector(query2DVDCase);
/* 2966 */         connector2DVDCase.runQuery();
/* 2967 */         connector2DVDCase.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2973 */         blueRayCaseSelectionText = bom.getBomDVDDetail().getBluRaySelectionInfo();
/*      */ 
/*      */         
/* 2976 */         String bluRayStatusIndicator = "0";
/* 2977 */         if ((bom.getBomDVDDetail()).bluRayStatusIndicator) {
/* 2978 */           bluRayStatusIndicator = "1";
/*      */         }
/*      */         
/* 2981 */         String spBluRayCase = "sp_upd_Bom_Detail ";
/* 2982 */         if ((bom.getBomDVDDetail()).bluRayCasePartId < 0) {
/* 2983 */           spBluRayCase = "sp_ins_Bom_Detail ";
/*      */         }
/* 2985 */         String query2BluRayCase = String.valueOf(spBluRayCase) + 
/* 2986 */           bom.getBomId() + "," + 
/* 2987 */           "32" + "," + 
/* 2988 */           "''," + 
/* 2989 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).bluRayInk1) + 
/* 2990 */           "'," + 
/* 2991 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).bluRayInk2) + 
/* 2992 */           "'," + 
/* 2993 */           "''," + 
/* 2994 */           "'" + MilestoneHelper.escapeSingleQuotes(blueRayCaseSelectionText) + "'," + 
/* 2995 */           "'" + 
/* 2996 */           MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml((bom.getBomDVDDetail()).bluRayInfo)) + "'," + bluRayStatusIndicator + "," + userID;
/*      */ 
/*      */         
/* 2999 */         JdbcConnector connector2BluRayCase = MilestoneHelper.getConnector(query2BluRayCase);
/* 3000 */         connector2BluRayCase.runQuery();
/* 3001 */         connector2BluRayCase.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3011 */         String wrapStatusIndicator = "0";
/* 3012 */         if ((bom.getBomDVDDetail()).wrapStatusIndicator)
/*      */         {
/* 3014 */           wrapStatusIndicator = "1";
/*      */         }
/*      */         
/* 3017 */         String spWrap = "sp_upd_Bom_Detail ";
/* 3018 */         if ((bom.getBomDVDDetail()).wrapPartId < 0) {
/* 3019 */           spWrap = "sp_ins_Bom_Detail ";
/*      */         }
/* 3021 */         String query2Wrap = String.valueOf(spWrap) + 
/* 3022 */           bom.getBomId() + "," + 
/* 3023 */           "29" + "," + 
/* 3024 */           (bom.getBomDVDDetail()).wrapSupplierId + "," + 
/* 3025 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).wrapInk1) + "'," + 
/* 3026 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).wrapInk2) + "'," + 
/* 3027 */           "''," + 
/* 3028 */           "''," + 
/* 3029 */           "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml((bom.getBomDVDDetail()).wrapInfo)) + "'," + 
/* 3030 */           wrapStatusIndicator + "," + 
/* 3031 */           userID;
/*      */ 
/*      */         
/* 3034 */         JdbcConnector connector2Wrap = MilestoneHelper.getConnector(query2Wrap);
/* 3035 */         connector2Wrap.runQuery();
/* 3036 */         connector2Wrap.close();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3044 */       String discStatusIndicator = "0";
/* 3045 */       if (bomDVDDetail.discStatusIndicator)
/*      */       {
/* 3047 */         discStatusIndicator = "1";
/*      */       }
/*      */       
/* 3050 */       String sp8 = "sp_upd_Bom_Detail ";
/* 3051 */       if (bomDVDDetail.discPartId < 0) {
/* 3052 */         sp8 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3054 */       String query2Disk = String.valueOf(sp8) + 
/* 3055 */         bom.getBomId() + "," + 
/* 3056 */         "7" + "," + 
/* 3057 */         bomDVDDetail.diskSupplierId + "," + 
/* 3058 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.discInk1) + "'," + 
/* 3059 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.discInk2) + "'," + 
/* 3060 */         "''," + 
/* 3061 */         "'" + MilestoneHelper.escapeSingleQuotes(DVDDiscSelectionText) + "'," + 
/* 3062 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.discInfo)) + "'," + 
/* 3063 */         discStatusIndicator + "," + 
/* 3064 */         userID;
/*      */       
/* 3066 */       JdbcConnector connector2Disk = MilestoneHelper.getConnector(query2Disk);
/* 3067 */       connector2Disk.runQuery();
/* 3068 */       connector2Disk.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3073 */       String jewelStatusIndicator = "0";
/* 3074 */       if (bomDVDDetail.jewelStatusIndicator)
/*      */       {
/* 3076 */         jewelStatusIndicator = "1";
/*      */       }
/*      */       
/* 3079 */       String sp9 = "sp_upd_Bom_Detail ";
/* 3080 */       if (bomDVDDetail.jewelPartId < 0) {
/* 3081 */         sp9 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3083 */       String query2Jewel = String.valueOf(sp9) + 
/* 3084 */         bom.getBomId() + "," + 
/* 3085 */         "12" + "," + 
/* 3086 */         "''," + 
/* 3087 */         "''," + 
/* 3088 */         "''," + 
/* 3089 */         "''," + 
/* 3090 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.jewelColor) + "'," + 
/* 3091 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.jewelInfo)) + "'," + 
/* 3092 */         jewelStatusIndicator + "," + 
/* 3093 */         userID;
/*      */       
/* 3095 */       JdbcConnector connector2Jewel = MilestoneHelper.getConnector(query2Jewel);
/* 3096 */       connector2Jewel.runQuery();
/* 3097 */       connector2Jewel.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3102 */       String trayStatusIndicator = "0";
/* 3103 */       if (bomDVDDetail.trayStatusIndicator)
/*      */       {
/* 3105 */         trayStatusIndicator = "1";
/*      */       }
/*      */       
/* 3108 */       String sp10 = "sp_upd_Bom_Detail ";
/* 3109 */       if (bomDVDDetail.trayPartId < 0) {
/* 3110 */         sp10 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3112 */       String query2Tray = String.valueOf(sp10) + 
/* 3113 */         bom.getBomId() + "," + 
/* 3114 */         "23" + "," + 
/* 3115 */         "''," + 
/* 3116 */         "''," + 
/* 3117 */         "''," + 
/* 3118 */         "''," + 
/* 3119 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.trayColor) + "'," + 
/* 3120 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.trayInfo)) + "'," + 
/* 3121 */         trayStatusIndicator + "," + 
/* 3122 */         userID;
/*      */       
/* 3124 */       JdbcConnector connector2Tray = MilestoneHelper.getConnector(query2Tray);
/* 3125 */       connector2Tray.runQuery();
/* 3126 */       connector2Tray.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3131 */       String inlayStatusIndicator = "0";
/* 3132 */       if (bomDVDDetail.inlayStatusIndicator)
/*      */       {
/* 3134 */         inlayStatusIndicator = "1";
/*      */       }
/*      */       
/* 3137 */       String sp11 = "sp_upd_Bom_Detail ";
/* 3138 */       if (bomDVDDetail.inlayPartId < 0) {
/* 3139 */         sp11 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3141 */       String query2Inlay = String.valueOf(sp11) + 
/* 3142 */         bom.getBomId() + "," + 
/* 3143 */         "10" + "," + 
/* 3144 */         bomDVDDetail.inlaySupplierId + "," + 
/* 3145 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.inlayInk1) + "'," + 
/* 3146 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.inlayInk2) + "'," + 
/* 3147 */         "''," + 
/* 3148 */         "''," + 
/* 3149 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.inlayInfo)) + "'," + 
/* 3150 */         inlayStatusIndicator + "," + 
/* 3151 */         userID;
/*      */       
/* 3153 */       JdbcConnector connector2Inlay = MilestoneHelper.getConnector(query2Inlay);
/* 3154 */       connector2Inlay.runQuery();
/* 3155 */       connector2Inlay.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3160 */       String frontStatusIndicator = "0";
/* 3161 */       if (bomDVDDetail.frontStatusIndicator)
/*      */       {
/* 3163 */         frontStatusIndicator = "1";
/*      */       }
/*      */       
/* 3166 */       String sp12 = "sp_upd_Bom_Detail ";
/* 3167 */       if (bomDVDDetail.frontPartId < 0) {
/* 3168 */         sp12 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3170 */       String query2Front = String.valueOf(sp12) + 
/* 3171 */         bom.getBomId() + "," + 
/* 3172 */         "9" + "," + 
/* 3173 */         bomDVDDetail.frontSupplierId + "," + 
/* 3174 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.frontInk1) + "'," + 
/* 3175 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.frontInk2) + "'," + 
/* 3176 */         "''," + 
/* 3177 */         "''," + 
/* 3178 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.frontInfo)) + "'," + 
/* 3179 */         frontStatusIndicator + "," + 
/* 3180 */         userID;
/*      */       
/* 3182 */       JdbcConnector connector2Front = MilestoneHelper.getConnector(query2Front);
/* 3183 */       connector2Front.runQuery();
/* 3184 */       connector2Front.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3189 */       String folderStatusIndicator = "0";
/* 3190 */       if (bomDVDDetail.folderStatusIndicator)
/*      */       {
/* 3192 */         folderStatusIndicator = "1";
/*      */       }
/*      */       
/* 3195 */       String sp13 = "sp_upd_Bom_Detail ";
/* 3196 */       if (bomDVDDetail.folderPartId < 0) {
/* 3197 */         sp13 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3199 */       String query2Folder = String.valueOf(sp13) + 
/* 3200 */         bom.getBomId() + "," + 
/* 3201 */         "8" + "," + 
/* 3202 */         bomDVDDetail.folderSupplierId + "," + 
/* 3203 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.folderInk1) + "'," + 
/* 3204 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.folderInk2) + "'," + 
/* 3205 */         "''," + 
/* 3206 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.folderPages) + "'," + 
/* 3207 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.folderInfo)) + "'," + 
/* 3208 */         folderStatusIndicator + "," + 
/* 3209 */         userID;
/*      */       
/* 3211 */       JdbcConnector connector2Folder = MilestoneHelper.getConnector(query2Folder);
/* 3212 */       connector2Folder.runQuery();
/* 3213 */       connector2Folder.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3218 */       String bookletStatusIndicator = "0";
/* 3219 */       if (bomDVDDetail.bookletStatusIndicator)
/*      */       {
/* 3221 */         bookletStatusIndicator = "1";
/*      */       }
/*      */       
/* 3224 */       String sp14 = "sp_upd_Bom_Detail ";
/* 3225 */       if (bomDVDDetail.bookletPartId < 0) {
/* 3226 */         sp14 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3228 */       String query2Booklet = String.valueOf(sp14) + 
/* 3229 */         bom.getBomId() + "," + 
/* 3230 */         "1" + "," + 
/* 3231 */         bomDVDDetail.bookletSupplierId + "," + 
/* 3232 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookletInk1) + "'," + 
/* 3233 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookletInk2) + "'," + 
/* 3234 */         "''," + 
/* 3235 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookletPages) + "'," + 
/* 3236 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.bookletInfo)) + "'," + 
/* 3237 */         bookletStatusIndicator + "," + 
/* 3238 */         userID;
/*      */       
/* 3240 */       JdbcConnector connector2Booklet = MilestoneHelper.getConnector(query2Booklet);
/* 3241 */       connector2Booklet.runQuery();
/* 3242 */       connector2Booklet.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3247 */       String brcStatusIndicator = "0";
/* 3248 */       if (bomDVDDetail.brcStatusIndicator)
/*      */       {
/* 3250 */         brcStatusIndicator = "1";
/*      */       }
/*      */       
/* 3253 */       String sp15 = "sp_upd_Bom_Detail ";
/* 3254 */       if (bomDVDDetail.brcPartId < 0) {
/* 3255 */         sp15 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3257 */       String query2Brc = String.valueOf(sp15) + 
/* 3258 */         bom.getBomId() + "," + 
/* 3259 */         "4" + "," + 
/* 3260 */         bomDVDDetail.brcSupplierId + "," + 
/* 3261 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.brcInk1) + "'," + 
/* 3262 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.brcInk2) + "'," + 
/* 3263 */         "''," + 
/* 3264 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.brcSize)) + "'," + 
/* 3265 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.brcInfo)) + "'," + 
/* 3266 */         brcStatusIndicator + "," + 
/* 3267 */         userID;
/*      */       
/* 3269 */       JdbcConnector connector2Brc = MilestoneHelper.getConnector(query2Brc);
/* 3270 */       connector2Brc.runQuery();
/* 3271 */       connector2Brc.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3276 */       String miniStatusIndicator = "0";
/* 3277 */       if (bomDVDDetail.miniStatusIndicator)
/*      */       {
/* 3279 */         miniStatusIndicator = "1";
/*      */       }
/*      */       
/* 3282 */       String sp16 = "sp_upd_Bom_Detail ";
/* 3283 */       if (bomDVDDetail.miniPartId < 0) {
/* 3284 */         sp16 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3286 */       String query2Mini = String.valueOf(sp16) + 
/* 3287 */         bom.getBomId() + "," + 
/* 3288 */         "15" + "," + 
/* 3289 */         bomDVDDetail.miniSupplierId + "," + 
/* 3290 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.miniInk1) + "'," + 
/* 3291 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.miniInk2) + "'," + 
/* 3292 */         "''," + 
/* 3293 */         "''," + 
/* 3294 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.miniInfo)) + "'," + 
/* 3295 */         miniStatusIndicator + "," + 
/* 3296 */         userID;
/*      */       
/* 3298 */       JdbcConnector connector2Mini = MilestoneHelper.getConnector(query2Mini);
/* 3299 */       connector2Mini.runQuery();
/* 3300 */       connector2Mini.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3305 */       String digiPakStatusIndicator = "0";
/* 3306 */       if (bomDVDDetail.digiPakStatusIndicator)
/*      */       {
/* 3308 */         digiPakStatusIndicator = "1";
/*      */       }
/*      */       
/* 3311 */       String sp17 = "sp_upd_Bom_Detail ";
/* 3312 */       if (bomDVDDetail.digiPakPartId < 0) {
/* 3313 */         sp17 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3315 */       String query2Digi = String.valueOf(sp17) + 
/* 3316 */         bom.getBomId() + "," + 
/* 3317 */         "6" + "," + 
/* 3318 */         bomDVDDetail.digiPakSupplierId + "," + 
/* 3319 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.digiPakInk1) + "'," + 
/* 3320 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.digiPakInk2) + "'," + 
/* 3321 */         "''," + 
/* 3322 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.digiPakTray)) + "'," + 
/* 3323 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.digiPakInfo)) + "'," + 
/* 3324 */         digiPakStatusIndicator + "," + 
/* 3325 */         userID;
/*      */       
/* 3327 */       JdbcConnector connector2Digi = MilestoneHelper.getConnector(query2Digi);
/* 3328 */       connector2Digi.runQuery();
/* 3329 */       connector2Digi.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3336 */       String softPakStatusIndicator = "0";
/* 3337 */       if (bomDVDDetail.softPakStatusIndicator)
/*      */       {
/* 3339 */         softPakStatusIndicator = "1";
/*      */       }
/*      */       
/* 3342 */       String sp31 = "sp_upd_Bom_Detail ";
/* 3343 */       if (bomDVDDetail.softPakPartId < 0) {
/* 3344 */         sp31 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3346 */       String query2softPak = String.valueOf(sp31) + 
/* 3347 */         bom.getBomId() + "," + 
/* 3348 */         "31" + "," + 
/* 3349 */         bomDVDDetail.softPakSupplierId + "," + 
/* 3350 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.softPakInk1) + "'," + 
/* 3351 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.softPakInk2) + "'," + 
/* 3352 */         "''," + 
/* 3353 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.softPakTray)) + "'," + 
/* 3354 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.softPakInfo)) + "'," + 
/* 3355 */         softPakStatusIndicator + "," + 
/* 3356 */         userID;
/*      */       
/* 3358 */       JdbcConnector connector2Soft = MilestoneHelper.getConnector(query2softPak);
/* 3359 */       connector2Soft.runQuery();
/* 3360 */       connector2Soft.close();
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
/* 3372 */       String stickerOneStatusIndicator = "0";
/* 3373 */       if (bomDVDDetail.stickerOneStatusIndicator)
/*      */       {
/* 3375 */         stickerOneStatusIndicator = "1";
/*      */       }
/*      */       
/* 3378 */       String sp18 = "sp_upd_Bom_Detail ";
/* 3379 */       if (bomDVDDetail.stickerOnePartId < 0) {
/* 3380 */         sp18 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3382 */       String query2StickerOne = String.valueOf(sp18) + 
/* 3383 */         bom.getBomId() + "," + 
/* 3384 */         "21" + "," + 
/* 3385 */         bomDVDDetail.stickerOneSupplierId + "," + 
/* 3386 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerOneInk1) + "'," + 
/* 3387 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerOneInk2) + "'," + 
/* 3388 */         "''," + 
/* 3389 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerOnePlaces)) + "'," + 
/* 3390 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerOneInfo)) + "'," + 
/* 3391 */         stickerOneStatusIndicator + "," + 
/* 3392 */         userID;
/*      */       
/* 3394 */       JdbcConnector connector2StickerOne = MilestoneHelper.getConnector(query2StickerOne);
/* 3395 */       connector2StickerOne.runQuery();
/* 3396 */       connector2StickerOne.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3401 */       String stickerTwoStatusIndicator = "0";
/* 3402 */       if (bomDVDDetail.stickerTwoStatusIndicator)
/*      */       {
/* 3404 */         stickerTwoStatusIndicator = "1";
/*      */       }
/*      */       
/* 3407 */       String sp19 = "sp_upd_Bom_Detail ";
/* 3408 */       if (bomDVDDetail.stickerTwoPartId < 0) {
/* 3409 */         sp19 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3411 */       String query2StickerTwo = String.valueOf(sp19) + 
/* 3412 */         bom.getBomId() + "," + 
/* 3413 */         "22" + "," + 
/* 3414 */         bomDVDDetail.stickerTwoSupplierId + "," + 
/* 3415 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerTwoInk1) + "'," + 
/* 3416 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerTwoInk2) + "'," + 
/* 3417 */         "''," + 
/* 3418 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerTwoPlaces)) + "'," + 
/* 3419 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerTwoInfo)) + "'," + 
/* 3420 */         stickerTwoStatusIndicator + "," + 
/* 3421 */         userID;
/*      */       
/* 3423 */       JdbcConnector connector2StickerTwo = MilestoneHelper.getConnector(query2StickerTwo);
/* 3424 */       connector2StickerTwo.runQuery();
/* 3425 */       connector2StickerTwo.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3430 */       String bookStatusIndicator = "0";
/* 3431 */       if (bomDVDDetail.bookStatusIndicator)
/*      */       {
/* 3433 */         bookStatusIndicator = "1";
/*      */       }
/*      */       
/* 3436 */       String sp20 = "sp_upd_Bom_Detail ";
/* 3437 */       if (bomDVDDetail.bookPartId < 0) {
/* 3438 */         sp20 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3440 */       String query2Book = String.valueOf(sp20) + 
/* 3441 */         bom.getBomId() + "," + 
/* 3442 */         "2" + "," + 
/* 3443 */         bomDVDDetail.bookSupplierId + "," + 
/* 3444 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookInk1) + "'," + 
/* 3445 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookInk2) + "'," + 
/* 3446 */         "''," + 
/* 3447 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.bookPages)) + "'," + 
/* 3448 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.bookInfo)) + "'," + 
/* 3449 */         bookStatusIndicator + "," + 
/* 3450 */         userID;
/*      */       
/* 3452 */       JdbcConnector connector2Book = MilestoneHelper.getConnector(query2Book);
/* 3453 */       connector2Book.runQuery();
/* 3454 */       connector2Book.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3459 */       String boxStatusIndicator = "0";
/* 3460 */       if (bomDVDDetail.boxStatusIndicator)
/*      */       {
/* 3462 */         boxStatusIndicator = "1";
/*      */       }
/*      */       
/* 3465 */       String sp21 = "sp_upd_Bom_Detail ";
/* 3466 */       if (bomDVDDetail.boxPartId < 0) {
/* 3467 */         sp21 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3469 */       String query2Box = String.valueOf(sp21) + 
/* 3470 */         bom.getBomId() + "," + 
/* 3471 */         "3" + "," + 
/* 3472 */         bomDVDDetail.boxSupplierId + "," + 
/* 3473 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.boxInk1) + "'," + 
/* 3474 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.boxInk2) + "'," + 
/* 3475 */         "''," + 
/* 3476 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.boxSizes)) + "'," + 
/* 3477 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.boxInfo)) + "'," + 
/* 3478 */         boxStatusIndicator + "," + 
/* 3479 */         userID;
/*      */       
/* 3481 */       JdbcConnector connector2Box = MilestoneHelper.getConnector(query2Box);
/* 3482 */       connector2Box.runQuery();
/* 3483 */       connector2Box.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3488 */       String otherStatusIndicatorDisc = "0";
/* 3489 */       if (bomDVDDetail.otherStatusIndicator)
/*      */       {
/* 3491 */         otherStatusIndicatorDisc = "1";
/*      */       }
/*      */       
/* 3494 */       String sp22 = "sp_upd_Bom_Detail ";
/* 3495 */       if (bomDVDDetail.otherPartId < 0) {
/* 3496 */         sp22 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3498 */       String query2Other = String.valueOf(sp22) + 
/* 3499 */         bom.getBomId() + "," + 
/* 3500 */         "18" + "," + 
/* 3501 */         bomDVDDetail.otherSupplierId + "," + 
/* 3502 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.otherInk1) + "'," + 
/* 3503 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.otherInk2) + "'," + 
/* 3504 */         "''," + 
/* 3505 */         "''," + 
/* 3506 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.otherInfo)) + "'," + 
/* 3507 */         otherStatusIndicatorDisc + "," + 
/* 3508 */         userID;
/*      */       
/* 3510 */       JdbcConnector connector2Other = MilestoneHelper.getConnector(query2Other);
/* 3511 */       connector2Other.runQuery();
/* 3512 */       connector2Other.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3520 */     BomVinylDetail vinylDetail = bom.getBomVinylDetail();
/*      */     
/* 3522 */     if (vinylDetail != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3527 */       String recordStatusIndicator = "0";
/* 3528 */       if (vinylDetail.recordStatusIndicator)
/*      */       {
/* 3530 */         recordStatusIndicator = "1";
/*      */       }
/*      */       
/* 3533 */       String sp23 = "sp_upd_Bom_Detail ";
/* 3534 */       if (vinylDetail.recordPartId < 0) {
/* 3535 */         sp23 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3537 */       String query3Record = String.valueOf(sp23) + 
/* 3538 */         bom.getBomId() + "," + 
/* 3539 */         "19" + "," + 
/* 3540 */         vinylDetail.recordSupplierId + "," + 
/* 3541 */         "''," + 
/* 3542 */         "''," + 
/* 3543 */         "''," + 
/* 3544 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.recordColor) + "'," + 
/* 3545 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.recordInfo)) + "'," + 
/* 3546 */         recordStatusIndicator + "," + 
/* 3547 */         userID;
/*      */       
/* 3549 */       JdbcConnector connector3Record = MilestoneHelper.getConnector(query3Record);
/* 3550 */       connector3Record.runQuery();
/* 3551 */       connector3Record.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3556 */       String labelStatusIndicator = "0";
/* 3557 */       if (vinylDetail.labelStatusIndicator)
/*      */       {
/* 3559 */         labelStatusIndicator = "1";
/*      */       }
/*      */       
/* 3562 */       String sp24 = "sp_upd_Bom_Detail ";
/* 3563 */       if (vinylDetail.labelPartId < 0) {
/* 3564 */         sp24 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3566 */       String query3Label = String.valueOf(sp24) + 
/* 3567 */         bom.getBomId() + "," + 
/* 3568 */         "14" + "," + 
/* 3569 */         vinylDetail.labelSupplierId + "," + 
/* 3570 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.labelInk1) + "'," + 
/* 3571 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.labelInk2) + "'," + 
/* 3572 */         "''," + 
/* 3573 */         "''," + 
/* 3574 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.labelInfo)) + "'," + 
/* 3575 */         labelStatusIndicator + "," + 
/* 3576 */         userID;
/*      */       
/* 3578 */       JdbcConnector connector3Label = MilestoneHelper.getConnector(query3Label);
/* 3579 */       connector3Label.runQuery();
/* 3580 */       connector3Label.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3585 */       String sleeveStatusIndicator = "0";
/* 3586 */       if (vinylDetail.sleeveStatusIndicator)
/*      */       {
/* 3588 */         sleeveStatusIndicator = "1";
/*      */       }
/*      */       
/* 3591 */       String sp25 = "sp_upd_Bom_Detail ";
/* 3592 */       if (vinylDetail.sleevePartId < 0) {
/* 3593 */         sp25 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3595 */       String query3Sleeve = String.valueOf(sp25) + 
/* 3596 */         bom.getBomId() + "," + 
/* 3597 */         "20" + "," + 
/* 3598 */         vinylDetail.sleeveSupplierId + "," + 
/* 3599 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.sleeveInk1) + "'," + 
/* 3600 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.sleeveInk2) + "'," + 
/* 3601 */         "''," + 
/* 3602 */         "''," + 
/* 3603 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.sleeveInfo)) + "'," + 
/* 3604 */         sleeveStatusIndicator + "," + 
/* 3605 */         userID;
/*      */       
/* 3607 */       JdbcConnector connector3Sleeve = MilestoneHelper.getConnector(query3Sleeve);
/* 3608 */       connector3Sleeve.runQuery();
/* 3609 */       connector3Sleeve.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3614 */       String jacketStatusIndicator = "0";
/* 3615 */       if (vinylDetail.jacketStatusIndicator)
/*      */       {
/* 3617 */         jacketStatusIndicator = "1";
/*      */       }
/*      */       
/* 3620 */       String sp26 = "sp_upd_Bom_Detail ";
/* 3621 */       if (vinylDetail.jacketPartId < 0) {
/* 3622 */         sp26 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3624 */       String query3Jacket = String.valueOf(sp26) + 
/* 3625 */         bom.getBomId() + "," + 
/* 3626 */         "11" + "," + 
/* 3627 */         vinylDetail.jacketSupplierId + "," + 
/* 3628 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.jacketInk1) + "'," + 
/* 3629 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.jacketInk2) + "'," + 
/* 3630 */         "''," + 
/* 3631 */         "''," + 
/* 3632 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.jacketInfo)) + "'," + 
/* 3633 */         jacketStatusIndicator + "," + 
/* 3634 */         userID;
/*      */       
/* 3636 */       JdbcConnector connector3Jacket = MilestoneHelper.getConnector(query3Jacket);
/* 3637 */       connector3Jacket.runQuery();
/* 3638 */       connector3Jacket.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3645 */       String insertStatusIndicator = "0";
/* 3646 */       if (vinylDetail.insertStatusIndicator)
/*      */       {
/* 3648 */         insertStatusIndicator = "1";
/*      */       }
/*      */       
/* 3651 */       String sp33 = "sp_upd_Bom_Detail ";
/* 3652 */       if (vinylDetail.insertPartId < 0) {
/* 3653 */         sp33 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3655 */       String query3Insert = String.valueOf(sp33) + 
/* 3656 */         bom.getBomId() + "," + 
/* 3657 */         "33" + "," + 
/* 3658 */         vinylDetail.insertSupplierId + "," + 
/* 3659 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.insertInk1) + "'," + 
/* 3660 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.insertInk2) + "'," + 
/* 3661 */         "''," + 
/* 3662 */         "''," + 
/* 3663 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.insertInfo)) + "'," + 
/* 3664 */         insertStatusIndicator + "," + 
/* 3665 */         userID;
/*      */       
/* 3667 */       JdbcConnector connector3Insert = MilestoneHelper.getConnector(query3Insert);
/* 3668 */       connector3Insert.runQuery();
/* 3669 */       connector3Insert.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3674 */       String stickerOneStatusIndicatorVinyl = "0";
/* 3675 */       if (vinylDetail.stickerOneStatusIndicator)
/*      */       {
/* 3677 */         stickerOneStatusIndicatorVinyl = "1";
/*      */       }
/*      */       
/* 3680 */       String sp27 = "sp_upd_Bom_Detail ";
/* 3681 */       if (vinylDetail.stickerOnePartId < 0) {
/* 3682 */         sp27 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3684 */       String query3StickerOne = String.valueOf(sp27) + 
/* 3685 */         bom.getBomId() + "," + 
/* 3686 */         "21" + "," + 
/* 3687 */         vinylDetail.stickerOneSupplierId + "," + 
/* 3688 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerOneInk1) + "'," + 
/* 3689 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerOneInk2) + "'," + 
/* 3690 */         "''," + 
/* 3691 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerOnePlaces)) + "'," + 
/* 3692 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerOneInfo)) + "'," + 
/* 3693 */         stickerOneStatusIndicatorVinyl + "," + 
/* 3694 */         userID;
/*      */       
/* 3696 */       JdbcConnector connector3StickerOne = MilestoneHelper.getConnector(query3StickerOne);
/* 3697 */       connector3StickerOne.runQuery();
/* 3698 */       connector3StickerOne.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3703 */       String stickerTwoStatusIndicatorVinyl = "0";
/* 3704 */       if (vinylDetail.stickerTwoStatusIndicator)
/*      */       {
/* 3706 */         stickerTwoStatusIndicatorVinyl = "1";
/*      */       }
/*      */       
/* 3709 */       String sp28 = "sp_upd_Bom_Detail ";
/* 3710 */       if (vinylDetail.stickerTwoPartId < 0) {
/* 3711 */         sp28 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3713 */       String query3StickerTwo = String.valueOf(sp28) + 
/* 3714 */         bom.getBomId() + "," + 
/* 3715 */         "22" + "," + 
/* 3716 */         vinylDetail.stickerTwoSupplierId + "," + 
/* 3717 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerTwoInk1) + "'," + 
/* 3718 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerTwoInk2) + "'," + 
/* 3719 */         "''," + 
/* 3720 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerTwoPlaces)) + "'," + 
/* 3721 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerTwoInfo)) + "'," + 
/* 3722 */         stickerTwoStatusIndicatorVinyl + "," + 
/* 3723 */         userID;
/*      */       
/* 3725 */       JdbcConnector connector3StickerTwo = MilestoneHelper.getConnector(query3StickerTwo);
/* 3726 */       connector3StickerTwo.runQuery();
/* 3727 */       connector3StickerTwo.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3732 */       String otherStatusIndicatorVinyl = "0";
/* 3733 */       if (vinylDetail.otherStatusIndicator)
/*      */       {
/* 3735 */         otherStatusIndicatorVinyl = "1";
/*      */       }
/*      */       
/* 3738 */       String sp29 = "sp_upd_Bom_Detail ";
/* 3739 */       if (vinylDetail.otherPartId < 0) {
/* 3740 */         sp29 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3742 */       String query3Other = String.valueOf(sp29) + 
/* 3743 */         bom.getBomId() + "," + 
/* 3744 */         "18" + "," + 
/* 3745 */         vinylDetail.otherSupplierId + "," + 
/* 3746 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.otherInk1) + "'," + 
/* 3747 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.otherInk2) + "'," + 
/* 3748 */         "''," + 
/* 3749 */         "''," + 
/* 3750 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.otherInfo)) + "'," + 
/* 3751 */         otherStatusIndicatorVinyl + "," + 
/* 3752 */         userID;
/*      */       
/* 3754 */       JdbcConnector connector3Other = MilestoneHelper.getConnector(query3Other);
/* 3755 */       connector3Other.runQuery();
/* 3756 */       connector3Other.close();
/*      */     } 
/*      */ 
/*      */     
/* 3760 */     return bom;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vector getSelectionNotepadList(int UserId, Notepad notepad, Context context) {
/* 3765 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 3766 */     Company company = null;
/* 3767 */     Vector precache = new Vector();
/* 3768 */     Selection selection = null;
/* 3769 */     String query = "";
/*      */     
/* 3771 */     String queryReset = "";
/*      */     
/* 3773 */     int maxRecords = 225;
/*      */     
/* 3775 */     if (notepad != null) {
/* 3776 */       maxRecords = notepad.getMaxRecords();
/*      */     }
/* 3778 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*      */       
/* 3780 */       query = notepad.getSearchQuery();
/* 3781 */       queryReset = new String(query);
/* 3782 */       query = String.valueOf(query) + notepad.getOrderBy();
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 3789 */       query = String.valueOf(getDefaultQuery(context)) + " ORDER BY artist, title, selection_no, street_date ";
/* 3790 */       queryReset = new String(query);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3797 */     User user = (User)context.getSessionValue("user");
/* 3798 */     String userDefaultsApplied = (String)context.getSessionValue("UserDefaultsApplied");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3804 */     if (userDefaultsApplied != null && userDefaultsApplied.equalsIgnoreCase("true")) {
/*      */       
/* 3806 */       user.SS_searchInitiated = false;
/*      */       
/* 3808 */       if (notepad != null) {
/*      */ 
/*      */         
/* 3811 */         notepad.setSelected(null);
/* 3812 */         notepad.setAllContents(null);
/*      */         
/* 3814 */         Notepad schNotepad = MilestoneHelper.getNotepadFromSession(1, context);
/* 3815 */         if (schNotepad != null) {
/*      */           
/* 3817 */           schNotepad.setSelected(null);
/* 3818 */           schNotepad.setAllContents(null);
/*      */         } 
/*      */       } 
/* 3821 */       context.removeSessionValue("UserDefaultsApplied");
/*      */     } 
/* 3823 */     if (user != null && !user.SS_searchInitiated) {
/*      */       
/* 3825 */       query = getInstance().getSelectionNotepadQueryUserDefaults(context);
/* 3826 */       queryReset = new String(query);
/* 3827 */       String order = getInstance().getSelectionNotepadQueryUserDefaultsOrderBy(context);
/*      */       
/* 3829 */       if (notepad != null) {
/*      */         
/* 3831 */         notepad.setSearchQuery(query);
/* 3832 */         notepad.setOrderBy(order);
/*      */       } 
/*      */       
/* 3835 */       query = String.valueOf(query) + order;
/*      */     } 
/*      */ 
/*      */     
/* 3839 */     if (user != null && user.SS_searchInitiated && context.getSessionValue("ResetSelectionSortOrder") != null) {
/*      */       
/* 3841 */       String orderReset = getInstance().getSelectionNotepadQueryUserDefaultsOrderBy(context);
/*      */       
/* 3843 */       if (notepad != null) {
/*      */         
/* 3845 */         notepad.setSearchQuery(queryReset);
/* 3846 */         notepad.setOrderBy(orderReset);
/*      */       } 
/* 3848 */       query = String.valueOf(queryReset) + orderReset;
/* 3849 */       user.SS_searchInitiated = true;
/* 3850 */       context.removeSessionValue("ResetSelectionSortOrder");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3855 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 3856 */     connector.setMaxRows(maxRecords);
/* 3857 */     connector.runQuery();
/*      */     
/* 3859 */     while (connector.more()) {
/*      */       
/* 3861 */       selection = new Selection();
/*      */ 
/*      */       
/* 3864 */       selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */       
/* 3866 */       selection.setTitle(connector.getField("title", ""));
/*      */       
/* 3868 */       selection.setArtist(connector.getField("artist", ""));
/*      */       
/* 3870 */       selection.setUpc(connector.getField("upc", ""));
/*      */       
/* 3872 */       selection.setSelectionConfig(
/* 3873 */           getSelectionConfigObject(connector.getField("configuration"), 
/* 3874 */             Cache.getSelectionConfigs()));
/*      */ 
/*      */       
/* 3877 */       selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 3878 */       selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 3879 */       selection.setSelectionNo(connector.getField("selection_no"));
/*      */       
/* 3881 */       selection.setIsDigital(connector.getBoolean("digital_flag"));
/*      */       
/* 3883 */       if (selection.getIsDigital()) {
/*      */         
/* 3885 */         String digitalReleaseDateString = connector.getFieldByName("street_date");
/* 3886 */         if (digitalReleaseDateString != null) {
/* 3887 */           selection.setDigitalRlsDateString(digitalReleaseDateString);
/* 3888 */           selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(
/* 3889 */                 digitalReleaseDateString));
/*      */         } 
/*      */       } else {
/*      */         
/* 3893 */         String streetDateString = connector.getFieldByName("street_date");
/* 3894 */         if (streetDateString != null) {
/* 3895 */           selection.setStreetDateString(streetDateString);
/* 3896 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3903 */       selection.setArchimedesId(connector.getInt("Archimedes_id", -1));
/* 3904 */       selection.setReleaseFamilyId(connector.getInt("Release_Family_id", -1));
/*      */ 
/*      */ 
/*      */       
/* 3908 */       precache.add(selection);
/* 3909 */       selection = null;
/* 3910 */       connector.next();
/*      */     } 
/*      */     
/* 3913 */     connector.close();
/* 3914 */     company = null;
/*      */     
/* 3916 */     if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY title, artist, selection_no, street_date"))
/*      */     {
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
/* 3937 */       if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY street_date, artist, title, selection_no"))
/*      */       {
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
/* 3958 */         if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY street_date DESC, artist, title, selection_no"))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3964 */           if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY title DESC, artist, selection_no, street_date"))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3970 */             if (notepad != null) notepad.getOrderBy().equalsIgnoreCase(" ORDER BY artist DESC, title, selection_no, street_date");
/*      */           
/*      */           }
/*      */         }
/*      */       }
/*      */     }
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
/* 4000 */     return precache;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNotepadTotalCount(Notepad notepad, Context context) {
/* 4005 */     int count = 0;
/* 4006 */     String query = "";
/* 4007 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*      */       
/* 4009 */       query = notepad.getSearchQuery();
/* 4010 */       query = String.valueOf(query) + notepad.getOrderBy();
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 4017 */       query = String.valueOf(getDefaultQuery(context)) + " ORDER BY artist, title, selection_no, street_date ";
/*      */     } 
/*      */     
/* 4020 */     String newCountQuery = "";
/*      */     
/* 4022 */     int start = query.toUpperCase().indexOf("SELECT");
/* 4023 */     int end = query.toUpperCase().indexOf("FROM") - 1;
/* 4024 */     int endLength = query.toUpperCase().indexOf("ORDER BY") - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 4030 */       newCountQuery = String.valueOf(query.substring(start, start + 6)) + " count(*) as totalRecords " + query.substring(end, endLength);
/*      */       
/* 4032 */       JdbcConnector connector = MilestoneHelper.getConnector(newCountQuery);
/* 4033 */       connector.runQuery();
/*      */       
/* 4035 */       if (connector.more()) {
/* 4036 */         count = connector.getIntegerField("totalRecords");
/*      */       }
/* 4038 */       connector.close();
/*      */     }
/* 4040 */     catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4048 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectionNotepadQuery(Context context, int UserId, Notepad notepad, Form form) {
/* 4060 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 4062 */     if (notepad != null)
/*      */     {
/*      */       
/* 4065 */       setSelectionNotepadQueryHelper(context, notepad, form, user);
/*      */     }
/*      */   }
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
/*      */   public void setSelectionNotepadQueryHelper(Context context, Notepad notepad, Form form, User user) {
/* 4083 */     String artistSearch = context.getParameter("macArtistSearch");
/*      */     
/* 4085 */     String titleSearch = context.getParameter("macTitleSearch");
/*      */     
/* 4087 */     String selectionNoSearch = context.getParameter("macSelectionSearch");
/*      */     
/* 4089 */     String prefixIDSearch = context.getParameter("macPrefixSearch");
/*      */     
/* 4091 */     String upcSearch = context.getParameter("macUPCSearch");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4096 */     upcSearch = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(upcSearch, "UPC", false, false);
/*      */ 
/*      */ 
/*      */     
/* 4100 */     String streetDateSearch = "";
/* 4101 */     String streetDateSearch2 = context.getParameter("macStreetDateSearch");
/* 4102 */     if (streetDateSearch2 != null && !streetDateSearch2.equals("")) {
/* 4103 */       StringTokenizer st = new StringTokenizer(streetDateSearch2, "/");
/* 4104 */       String token = "";
/* 4105 */       while (st.hasMoreTokens()) {
/* 4106 */         token = st.nextToken();
/* 4107 */         if (!token.equals("*") && token.length() == 1)
/* 4108 */           token = "0" + token; 
/* 4109 */         if (streetDateSearch.length() == 0) {
/* 4110 */           streetDateSearch = String.valueOf(streetDateSearch) + token; continue;
/*      */         } 
/* 4112 */         streetDateSearch = String.valueOf(streetDateSearch) + "/" + token;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4117 */     String streetEndDateSearch = "";
/* 4118 */     String streetEndDateSearch2 = context.getParameter("macStreetEndDateSearch");
/* 4119 */     if (streetEndDateSearch2 != null && !streetEndDateSearch2.equals("")) {
/* 4120 */       StringTokenizer st = new StringTokenizer(streetEndDateSearch2, "/");
/* 4121 */       String token = "";
/* 4122 */       while (st.hasMoreTokens()) {
/* 4123 */         token = st.nextToken();
/* 4124 */         if (!token.equals("*") && token.length() == 1)
/* 4125 */           token = "0" + token; 
/* 4126 */         if (streetEndDateSearch.length() == 0) {
/* 4127 */           streetEndDateSearch = String.valueOf(streetEndDateSearch) + token; continue;
/*      */         } 
/* 4129 */         streetEndDateSearch = String.valueOf(streetEndDateSearch) + "/" + token;
/*      */       } 
/*      */     } 
/*      */     
/* 4133 */     String configSearch = context.getParameter("ConfigSearch");
/*      */     
/* 4135 */     String subconfigSearch = context.getParameter("macSubconfigSearch");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4140 */     String showAllSearch = context.getParameter("ShowAllSearch");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4146 */     String contactSearch = context.getParameter("ContactSearch");
/*      */ 
/*      */     
/* 4149 */     String familySearch = "";
/*      */     
/* 4151 */     if (context.getParameter("FamilySearch") == null || context.getParameter("FamilySearch").equals("") || context.getParameter("FamilySearch").equals("0") || context.getParameter("FamilySearch").equals("-1")) {
/*      */       
/* 4153 */       FormDropDownMenu familyDD = (FormDropDownMenu)form.getElement("FamilySearch");
/* 4154 */       for (int f = 0; f < familyDD.getValueList().length; f++) {
/* 4155 */         if (f == 0) {
/* 4156 */           familySearch = String.valueOf(familySearch) + familyDD.getValueList()[f];
/*      */         } else {
/* 4158 */           familySearch = String.valueOf(familySearch) + "," + familyDD.getValueList()[f];
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 4163 */       familySearch = context.getParameter("FamilySearch");
/*      */     } 
/*      */     
/* 4166 */     String environmentSearch = "";
/* 4167 */     environmentSearch = context.getParameter("EnvironmentSearch");
/*      */     
/* 4169 */     String productType = context.getParameter("ProdType");
/* 4170 */     String productTypeSearch = "";
/* 4171 */     if (productType.equalsIgnoreCase("physical")) {
/* 4172 */       productTypeSearch = "0";
/* 4173 */     } else if (productType.equalsIgnoreCase("digital")) {
/* 4174 */       productTypeSearch = "1";
/*      */     } 
/*      */     
/* 4177 */     String companyVar = context.getParameter("company");
/* 4178 */     String companyTypeSearch = "";
/* 4179 */     String labelSearch = "";
/* 4180 */     if (productType.equalsIgnoreCase("All")) {
/*      */ 
/*      */       
/* 4183 */       labelSearch = "0";
/*      */     
/*      */     }
/* 4186 */     else if (productType.equalsIgnoreCase("Select")) {
/*      */       
/* 4188 */       labelSearch = context.getParameter("macLabelSearch");
/*      */     } 
/*      */ 
/*      */     
/* 4192 */     String labelVar = context.getParameter("Label");
/* 4193 */     String labelTypeSearch = "";
/* 4194 */     String companySearch = "";
/* 4195 */     if (productType.equalsIgnoreCase("All")) {
/*      */ 
/*      */       
/* 4198 */       companySearch = "0";
/*      */     
/*      */     }
/* 4201 */     else if (productType.equalsIgnoreCase("Select")) {
/*      */       
/* 4203 */       companySearch = context.getParameter("macCompanySearch");
/*      */     } 
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
/* 4221 */     String projectIDSearch = "";
/* 4222 */     projectIDSearch = context.getParameter("ProjectIDSearch");
/*      */     
/* 4224 */     boolean startsWithAsterisk = false;
/* 4225 */     boolean endsWithAsterisk = false;
/* 4226 */     boolean containsNA = false;
/*      */ 
/*      */ 
/*      */     
/* 4230 */     if (streetDateSearch.indexOf("*") > -1)
/*      */     {
/*      */       
/* 4233 */       startsWithAsterisk = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4241 */     if (streetEndDateSearch.indexOf("*") > -1)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4247 */       endsWithAsterisk = true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4252 */     if (streetDateSearch.toUpperCase().indexOf("N/A") > -1)
/*      */     {
/* 4254 */       containsNA = true;
/*      */     }
/*      */     
/* 4257 */     if (selectionNoSearch.length() > 0) {
/*      */       
/* 4259 */       context.putSessionValue("selectionNotepadColumn", "1");
/*      */     }
/* 4261 */     else if (upcSearch.length() > 0) {
/*      */       
/* 4263 */       context.putSessionValue("selectionNotepadColumn", "2");
/*      */     }
/* 4265 */     else if (prefixIDSearch.length() > 0) {
/*      */       
/* 4267 */       context.putSessionValue("selectionNotepadColumn", "3");
/*      */     }
/*      */     else {
/*      */       
/* 4271 */       context.removeSessionValue("selectionNotepadColumn");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4278 */     if (user != null && user.getPreferences() != null && !user.SS_searchInitiated) {
/*      */ 
/*      */       
/* 4281 */       if (user.getPreferences().getSelectionStatus() > 0) {
/*      */         
/* 4283 */         showAllSearch = "true";
/* 4284 */         user.SS_showAllSearch = "true";
/*      */       } 
/*      */       
/* 4287 */       if (user.getPreferences().getSelectionReleasingFamily() > 0) {
/*      */         
/* 4289 */         familySearch = String.valueOf(user.getPreferences().getSelectionReleasingFamily());
/* 4290 */         user.SS_familySearch = familySearch;
/*      */       } 
/*      */       
/* 4293 */       if (user.getPreferences().getSelectionEnvironment() > 0) {
/*      */         
/* 4295 */         environmentSearch = String.valueOf(user.getPreferences().getSelectionEnvironment());
/* 4296 */         user.SS_environmentSearch = environmentSearch;
/*      */       } 
/*      */       
/* 4299 */       if (user.getPreferences().getSelectionLabelContact() > 0) {
/*      */         
/* 4301 */         contactSearch = String.valueOf(user.getPreferences().getSelectionLabelContact());
/* 4302 */         user.SS_contactSearch = contactSearch;
/*      */       } 
/*      */       
/* 4305 */       if (user.getPreferences().getSelectionProductType() > -1) {
/* 4306 */         productTypeSearch = "";
/* 4307 */         user.SS_productTypeSearch = "";
/* 4308 */         if (user.getPreferences().getSelectionProductType() == 0) {
/*      */           
/* 4310 */           productTypeSearch = "0";
/* 4311 */           user.SS_productTypeSearch = "physical";
/*      */         } 
/* 4313 */         if (user.getPreferences().getSelectionProductType() == 1) {
/*      */           
/* 4315 */           productTypeSearch = "1";
/* 4316 */           user.SS_productTypeSearch = "digital";
/*      */         } 
/* 4318 */         if (user.getPreferences().getSelectionProductType() == 2) {
/* 4319 */           user.SS_productTypeSearch = "both";
/*      */         }
/*      */       } 
/*      */       
/* 4323 */       UserManager.getInstance().setUserPreferenceReleaseCalendar(user);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4330 */     StringBuffer query = new StringBuffer();
/* 4331 */     query.append("SELECT release_id, title, artist, configuration, sub_configuration, upc, prefix, selection_no, digital_flag, street_date =  CASE digital_flag  WHEN 0 THEN street_date   WHEN 1 THEN digital_rls_date  END ,archimedes_id, Release_Family_id from vi_Release_Header WHERE ");
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
/* 4342 */     if (environmentSearch == null || environmentSearch.equals("") || environmentSearch.equals("0") || environmentSearch.equals("-1")) {
/*      */       
/* 4344 */       FormDropDownMenu envDD = (FormDropDownMenu)form.getElement(
/* 4345 */           "EnvironmentSearch");
/* 4346 */       query.append(" environment_id in (");
/* 4347 */       for (int f = 0; f < envDD.getValueList().length; f++) {
/* 4348 */         if (f == 0) {
/* 4349 */           query.append(envDD.getValueList()[f]);
/*      */         } else {
/* 4351 */           query.append("," + envDD.getValueList()[f]);
/*      */         } 
/* 4353 */       }  query.append(")");
/*      */     } else {
/*      */       
/* 4356 */       query.append(" environment_id in (" + environmentSearch + ")");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4362 */     if (showAllSearch == null || !showAllSearch.equals("true")) {
/* 4363 */       query.append(" AND NOT ( status = 'CLOSED' OR  status = 'CANCEL' )");
/*      */     }
/* 4365 */     if (artistSearch != null && !artistSearch.equals("")) {
/* 4366 */       query.append(" AND artist " + MilestoneHelper.setWildCardsEscapeSingleQuotes(artistSearch));
/*      */     }
/* 4368 */     if (titleSearch != null && !titleSearch.equals("")) {
/* 4369 */       query.append(" AND title " + MilestoneHelper.setWildCardsEscapeSingleQuotes(titleSearch));
/*      */     }
/* 4371 */     if (selectionNoSearch != null && !selectionNoSearch.equals("")) {
/* 4372 */       query.append(" AND selection_no " + MilestoneHelper.setWildCardsEscapeSingleQuotes(selectionNoSearch));
/*      */     }
/* 4374 */     if (prefixIDSearch != null && !prefixIDSearch.equals("")) {
/* 4375 */       query.append(" AND prefix " + MilestoneHelper.setWildCardsEscapeSingleQuotes(prefixIDSearch));
/*      */     }
/* 4377 */     if (upcSearch != null && !upcSearch.equals("")) {
/* 4378 */       query.append(" AND upc " + MilestoneHelper.setWildCardsEscapeSingleQuotes(upcSearch));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4385 */     if (streetEndDateSearch != null && !streetEndDateSearch.equals("")) {
/*      */       
/* 4387 */       String streetStartDateSearch = "";
/* 4388 */       if (streetDateSearch != null && !streetDateSearch.equals(""))
/*      */       {
/* 4390 */         query.append(" AND (   ( digital_flag = 0 and street_date between '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "')");
/* 4391 */         query.append("      OR ( digital_flag = 1 and digital_rls_date between '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "'))");
/*      */       }
/*      */       else
/*      */       {
/* 4395 */         query.append(" AND (   ( digital_flag = 0 and street_date between '01/01/1900' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "')");
/* 4396 */         query.append("      OR ( digital flag = 1 and digital_rls_date between '01/01/1900' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "'))");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 4414 */     else if (streetDateSearch != null && !streetDateSearch.equals("")) {
/*      */ 
/*      */ 
/*      */       
/* 4418 */       if (containsNA) {
/*      */         
/* 4420 */         query.append(" AND ( ( digital_flag = 0 and street_date IS NULL) OR ( digital_flag = 1 and digital_rls_date IS NULL))");
/*      */       }
/* 4422 */       else if (startsWithAsterisk) {
/*      */ 
/*      */         
/* 4425 */         String cleanStreetDate = streetDateSearch.replace('*', '%');
/* 4426 */         query.append(" AND (    ( digital_flag = 0 and CONVERT(varchar, street_date, 1) LIKE '" + cleanStreetDate + "')");
/* 4427 */         query.append("       OR ( digital_flag = 1 and CONVERT(varchar, digital_rls_date, 1) LIKE '" + cleanStreetDate + "'))");
/*      */       }
/*      */       else {
/*      */         
/* 4431 */         query.append(" AND (   ( digital_flag = 0 and street_date = '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "')");
/* 4432 */         query.append("      OR ( digital_flag = 1 and digital_rls_date = '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "'))");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4438 */     if (configSearch != null && !configSearch.equals("") && !configSearch.equals("0")) {
/* 4439 */       query.append(" AND configuration = '" + configSearch + "'");
/*      */     }
/*      */     
/* 4442 */     if (subconfigSearch != null && !subconfigSearch.equals("") && !subconfigSearch.equals("0")) {
/* 4443 */       query.append(" AND sub_configuration = '" + subconfigSearch + "'");
/*      */     }
/*      */ 
/*      */     
/* 4447 */     if (labelSearch != null && !labelSearch.equals("") && !labelSearch.equals("0")) {
/* 4448 */       query.append(" AND label_id in (" + labelSearch + ")");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4454 */     if (companySearch != null && !companySearch.equals("") && !companySearch.equals("0")) {
/* 4455 */       query.append(" AND company_id in (" + companySearch + ")");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4462 */     addReleasingFamilyLabelFamilySelect("FamilySearch", context, query, form);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4468 */     if (!productTypeSearch.equalsIgnoreCase("")) {
/* 4469 */       query.append(" AND digital_flag = " + productTypeSearch);
/*      */     }
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
/* 4490 */     if (contactSearch != null && !contactSearch.equals("") && !contactSearch.equals("0")) {
/* 4491 */       query.append(" AND contact_id = '" + contactSearch + "'");
/*      */     }
/*      */     
/* 4494 */     if (projectIDSearch != null && !projectIDSearch.equals("")) {
/* 4495 */       query.append(" AND project_no " + MilestoneHelper.setWildCardsEscapeSingleQuotes(projectIDSearch));
/*      */     }
/*      */ 
/*      */     
/* 4499 */     query.append(ShowOrHideDigitalProductGet(user));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4505 */     String order = "";
/*      */ 
/*      */ 
/*      */     
/* 4509 */     NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
/*      */ 
/*      */     
/* 4512 */     boolean descending = false;
/* 4513 */     if (notepad.getOrderBy().indexOf(" DESC ") != -1) {
/* 4514 */       descending = true;
/*      */     }
/* 4516 */     if (artistSearch.length() > 0) {
/*      */       
/* 4518 */       notepadSortOrder.setSelectionOrderCol("Artist");
/* 4519 */       notepadSortOrder.setShowGroupButtons(true);
/* 4520 */       if (!descending) {
/*      */         
/* 4522 */         order = " ORDER BY artist, title, selection_no, street_date";
/* 4523 */         notepadSortOrder.setSelectionOrderColNo(0);
/*      */       } else {
/* 4525 */         order = " ORDER BY artist DESC , title, selection_no, street_date";
/* 4526 */         notepadSortOrder.setSelectionOrderColNo(7);
/*      */       }
/*      */     
/* 4529 */     } else if (titleSearch.length() > 0) {
/*      */       
/* 4531 */       if (!descending) {
/*      */         
/* 4533 */         order = " ORDER BY title, artist, selection_no, street_date";
/* 4534 */         notepadSortOrder.setSelectionOrderColNo(1);
/*      */       } else {
/* 4536 */         order = " ORDER BY title DESC , artist, selection_no, street_date";
/* 4537 */         notepadSortOrder.setSelectionOrderColNo(8);
/*      */       } 
/* 4539 */       notepadSortOrder.setSelectionOrderCol("Title");
/* 4540 */       notepadSortOrder.setShowGroupButtons(true);
/*      */     }
/* 4542 */     else if (streetDateSearch.length() > 0) {
/*      */       
/* 4544 */       if (!descending) {
/*      */         
/* 4546 */         order = " ORDER BY street_date, artist, title, selection_no";
/* 4547 */         notepadSortOrder.setSelectionOrderColNo(5);
/*      */       } else {
/* 4549 */         order = " ORDER BY street_date DESC , artist, title, selection_no";
/* 4550 */         notepadSortOrder.setSelectionOrderColNo(5);
/*      */       } 
/* 4552 */       notepadSortOrder.setSelectionOrderCol("Str Dt");
/*      */     }
/* 4554 */     else if (selectionNoSearch.length() > 0) {
/*      */       
/* 4556 */       if (!descending) {
/*      */         
/* 4558 */         order = " ORDER BY selection_no";
/* 4559 */         notepadSortOrder.setSelectionOrderColNo(2);
/*      */       } else {
/* 4561 */         order = " ORDER BY selection_no DESC ";
/* 4562 */         notepadSortOrder.setSelectionOrderColNo(2);
/*      */       } 
/* 4564 */       notepadSortOrder.setSelectionOrderCol("Local Prod #");
/*      */     }
/* 4566 */     else if (prefixIDSearch.length() > 0) {
/*      */       
/* 4568 */       if (!descending) {
/*      */         
/* 4570 */         order = " ORDER BY prefix,selection_no";
/* 4571 */         notepadSortOrder.setSelectionOrderColNo(4);
/*      */       } else {
/* 4573 */         order = " ORDER BY prefix DESC ,selection_no";
/* 4574 */         notepadSortOrder.setSelectionOrderColNo(4);
/*      */       } 
/* 4576 */       notepadSortOrder.setSelectionOrderCol("Prefix");
/*      */     }
/* 4578 */     else if (upcSearch.length() > 0) {
/*      */       
/* 4580 */       if (!descending) {
/*      */         
/* 4582 */         order = " ORDER BY upc,selection_no";
/* 4583 */         notepadSortOrder.setSelectionOrderColNo(3);
/*      */       } else {
/* 4585 */         order = " ORDER BY upc DESC ,selection_no";
/* 4586 */         notepadSortOrder.setSelectionOrderColNo(3);
/*      */       } 
/* 4588 */       notepadSortOrder.setSelectionOrderCol("UPC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 4596 */     else if (user != null && !user.SS_searchInitiated) {
/*      */       
/* 4598 */       order = getSelectionNotepadQueryUserDefaultsOrderBy(context);
/*      */     }
/* 4600 */     else if (!notepad.getOrderBy().equals("") && (
/* 4601 */       notepadSortOrder.getSelectionOrderCol().equals("Artist") || 
/* 4602 */       notepadSortOrder.getSelectionOrderCol().equals("Title") || 
/* 4603 */       notepadSortOrder.getSelectionOrderCol().equals("Str Dt"))) {
/* 4604 */       order = notepad.getOrderBy();
/*      */     } else {
/*      */       
/* 4607 */       if (!descending) {
/*      */         
/* 4609 */         order = " ORDER BY artist, title, selection_no, street_date ";
/* 4610 */         notepadSortOrder.setSelectionOrderColNo(0);
/*      */       } else {
/* 4612 */         order = " ORDER BY artist DESC , title, selection_no, street_date ";
/* 4613 */         notepadSortOrder.setSelectionOrderColNo(7);
/*      */       } 
/* 4615 */       notepadSortOrder.setSelectionOrderCol("Artist");
/* 4616 */       notepadSortOrder.setShowGroupButtons(true);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4623 */     if (user != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4628 */       Hashtable prevSearchValues = new Hashtable();
/* 4629 */       prevSearchValues.put("SS_artistSearch", user.SS_artistSearch);
/* 4630 */       prevSearchValues.put("SS_titleSearch ", user.SS_titleSearch);
/* 4631 */       prevSearchValues.put("SS_selectionNoSearch", user.SS_selectionNoSearch);
/* 4632 */       prevSearchValues.put("SS_prefixIDSearch", user.SS_prefixIDSearch);
/* 4633 */       prevSearchValues.put("SS_upcSearch", user.SS_upcSearch);
/* 4634 */       prevSearchValues.put("SS_streetDateSearch", user.SS_streetDateSearch);
/* 4635 */       prevSearchValues.put("SS_streetEndDateSearch", user.SS_streetEndDateSearch);
/* 4636 */       prevSearchValues.put("SS_configSearch", user.SS_configSearch);
/* 4637 */       prevSearchValues.put("SS_subconfigSearch", user.SS_subconfigSearch);
/* 4638 */       prevSearchValues.put("SS_labelSearch", user.SS_labelSearch);
/* 4639 */       prevSearchValues.put("SS_companySearch", user.SS_companySearch);
/* 4640 */       prevSearchValues.put("SS_contactSearch", user.SS_contactSearch);
/* 4641 */       prevSearchValues.put("SS_familySearch", user.SS_familySearch);
/* 4642 */       prevSearchValues.put("SS_environmentSearch", user.SS_environmentSearch);
/* 4643 */       prevSearchValues.put("SS_projectIDSearch", user.SS_projectIDSearch);
/* 4644 */       prevSearchValues.put("SS_productTypeSearch", user.SS_productTypeSearch);
/* 4645 */       prevSearchValues.put("SS_showAllSearch", user.SS_showAllSearch);
/* 4646 */       context.putDelivery("prevSearchValues", prevSearchValues);
/*      */ 
/*      */       
/* 4649 */       user.SS_artistSearch = artistSearch;
/* 4650 */       user.SS_titleSearch = titleSearch;
/* 4651 */       user.SS_selectionNoSearch = selectionNoSearch;
/* 4652 */       user.SS_prefixIDSearch = prefixIDSearch;
/* 4653 */       user.SS_upcSearch = upcSearch;
/* 4654 */       user.SS_streetDateSearch = streetDateSearch;
/* 4655 */       user.SS_streetEndDateSearch = streetEndDateSearch;
/* 4656 */       user.SS_configSearch = configSearch;
/* 4657 */       user.SS_subconfigSearch = subconfigSearch;
/* 4658 */       user.SS_labelSearch = labelSearch;
/* 4659 */       user.SS_companySearch = companySearch;
/* 4660 */       user.SS_contactSearch = contactSearch;
/* 4661 */       user.SS_familySearch = familySearch;
/* 4662 */       user.SS_environmentSearch = environmentSearch;
/* 4663 */       user.SS_projectIDSearch = projectIDSearch;
/* 4664 */       user.SS_productTypeSearch = productType;
/* 4665 */       user.SS_showAllSearch = showAllSearch;
/*      */       
/* 4667 */       user.RC_environment = environmentSearch;
/* 4668 */       user.RC_releasingFamily = familySearch;
/* 4669 */       user.RC_labelContact = contactSearch;
/* 4670 */       user.RC_productType = MilestoneHelper_2.convertProductTypeToReleaseCalendar(productType);
/*      */     } 
/*      */     
/* 4673 */     notepad.setSearchQuery(query.toString());
/* 4674 */     notepad.setOrderBy(order);
/* 4675 */     notepadSortOrder.setSelectionOrderBy(order);
/*      */   }
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
/*      */   public boolean getSelectionSearchResults(GeminiApplication application, Context context) {
/* 4689 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 4692 */     Notepad notepad = null;
/*      */     
/* 4694 */     notepad = MilestoneHelper.getNotepadFromSession(0, context);
/*      */     
/* 4696 */     if (notepad != null) {
/*      */ 
/*      */       
/* 4699 */       String query = notepad.getSearchQuery();
/* 4700 */       String order = notepad.getOrderBy();
/* 4701 */       String orderBy = "";
/* 4702 */       String orderCol = "";
/* 4703 */       int orderColNo = 0;
/* 4704 */       boolean showGrpButs = true;
/*      */       
/* 4706 */       NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
/* 4707 */       if (notepadSortOrder != null) {
/*      */         
/* 4709 */         orderBy = notepadSortOrder.getSelectionOrderBy();
/* 4710 */         showGrpButs = notepadSortOrder.getShowGroupButtons();
/* 4711 */         orderCol = notepadSortOrder.getSelectionOrderCol();
/* 4712 */         orderColNo = notepadSortOrder.getSelectionOrderColNo();
/*      */       } 
/*      */ 
/*      */       
/* 4716 */       Form searchForm = new Form(application, "searchForm", 
/* 4717 */           application.getInfrastructure().getServletURL(), 
/* 4718 */           "POST");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4726 */       Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/* 4727 */       FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, "-1", false, true);
/* 4728 */       searchForm.addElement(Family);
/*      */ 
/*      */       
/* 4731 */       Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 4732 */       Vector myCompanies = MilestoneHelper.getUserCompanies(context);
/* 4733 */       environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
/*      */ 
/*      */       
/* 4736 */       environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */ 
/*      */       
/* 4739 */       FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, "-1", false, true);
/* 4740 */       searchForm.addElement(envMenu);
/*      */ 
/*      */       
/* 4743 */       searchForm.setValues(context);
/*      */ 
/*      */       
/* 4746 */       setSelectionNotepadQueryHelper(context, notepad, searchForm, user);
/*      */ 
/*      */       
/* 4749 */       if (getNotepadTotalCount(notepad, context) > 0)
/*      */       {
/* 4751 */         return true;
/*      */       }
/* 4753 */       notepad.setSearchQuery(query);
/* 4754 */       notepad.setOrderBy(order);
/* 4755 */       if (notepadSortOrder != null) {
/*      */         
/* 4757 */         notepadSortOrder.setSelectionOrderBy(orderBy);
/* 4758 */         notepadSortOrder.setShowGroupButtons(showGrpButs);
/* 4759 */         notepadSortOrder.setSelectionOrderCol(orderCol);
/* 4760 */         notepadSortOrder.setSelectionOrderColNo(orderColNo);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4765 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
/* 4774 */     for (int j = 0; j < lookupVector.size(); j++) {
/*      */       
/* 4776 */       LookupObject lookupObject = (LookupObject)lookupVector.get(j);
/*      */       
/* 4778 */       if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 4780 */         return lookupObject;
/*      */       }
/*      */     } 
/*      */     
/* 4784 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getLookupObjectValue(LookupObject lookupObject) {
/* 4796 */     String lookupValue = "";
/*      */     
/* 4798 */     if (lookupObject != null)
/*      */     {
/* 4800 */       lookupValue = lookupObject.getAbbreviation();
/*      */     }
/*      */     
/* 4803 */     return lookupValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SelectionConfiguration getSelectionConfigObject(String abbreviation, Vector configs) {
/* 4811 */     for (int j = 0; j < configs.size(); j++) {
/*      */       
/* 4813 */       SelectionConfiguration selectionConfiguration = (SelectionConfiguration)configs.get(j);
/*      */       
/* 4815 */       if (selectionConfiguration.getSelectionConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 4817 */         return selectionConfiguration;
/*      */       }
/*      */     } 
/*      */     
/* 4821 */     return new SelectionConfiguration("", "");
/*      */   }
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
/*      */   public static SelectionSubConfiguration getSelectionSubConfigObject(String abbreviation, SelectionConfiguration config) {
/* 4835 */     Vector subConfigs = config.getSubConfigurations();
/*      */     
/* 4837 */     for (int j = 0; j < subConfigs.size(); j++) {
/*      */       
/* 4839 */       SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.get(j);
/*      */       
/* 4841 */       if (subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 4843 */         return subConfig;
/*      */       }
/*      */     } 
/*      */     
/* 4847 */     return new SelectionSubConfiguration("", "", 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getLabelContacts(Selection selection) {
/* 4859 */     Vector labelUsers = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4864 */     int companyId = -1;
/*      */     
/* 4866 */     if (selection.getCompany() != null) {
/* 4867 */       companyId = selection.getCompany().getStructureID();
/*      */     }
/*      */     
/* 4870 */     String query = "sp_get_Selection_LabelContacts " + companyId;
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
/* 4884 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 4885 */     connector.runQuery();
/*      */     
/* 4887 */     while (connector.more()) {
/*      */       
/* 4889 */       User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
/* 4890 */       labelUsers.add(labelUser);
/* 4891 */       connector.next();
/*      */     } 
/*      */     
/* 4894 */     connector.close();
/*      */     
/* 4896 */     return labelUsers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Vector getLabelContacts(Context context) {
/* 4908 */     Vector labelUsers = new Vector();
/*      */     
/* 4910 */     int umlId = MilestoneHelper.getStructureId("uml", 1);
/* 4911 */     int enterpriseId = MilestoneHelper.getStructureId("Enterprise", 1);
/* 4912 */     int companyId = -1;
/*      */     
/* 4914 */     String companyClause = "( ";
/*      */     
/* 4916 */     Vector userCompanies = new Vector();
/*      */     
/* 4918 */     userCompanies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 4920 */     for (int i = 0; i < userCompanies.size(); i++) {
/*      */       
/* 4922 */       if (i == 0) {
/* 4923 */         companyClause = String.valueOf(companyClause) + " company_id = " + ((Company)userCompanies.get(i)).getStructureID();
/*      */       } else {
/* 4925 */         companyClause = String.valueOf(companyClause) + " OR company_id = " + ((Company)userCompanies.get(i)).getStructureID();
/*      */       } 
/* 4927 */     }  companyClause = String.valueOf(companyClause) + ")";
/*      */     
/* 4929 */     String query = "SELECT DISTINCT vi_User.Name,vi_User.Full_Name, vi_User.User_Id FROM vi_User, vi_User_Company WHERE (vi_User.User_ID = vi_User_Company.User_ID) AND (menu_access LIKE '[1,2]%') AND " + 
/*      */ 
/*      */ 
/*      */       
/* 4933 */       companyClause + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4939 */       " ORDER BY vi_User.Full_Name;";
/*      */ 
/*      */ 
/*      */     
/* 4943 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 4944 */     connector.runQuery();
/*      */     
/* 4946 */     while (connector.more()) {
/*      */       
/* 4948 */       User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
/* 4949 */       labelUsers.add(labelUser);
/* 4950 */       connector.next();
/*      */     } 
/*      */     
/* 4953 */     connector.close();
/*      */     
/* 4955 */     return labelUsers;
/*      */   }
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
/*      */   public static Vector getLabelContactsExcludeUml(Context context) {
/* 4972 */     Vector labelUsers = Cache.getInstance().getLabelUsers();
/* 4973 */     Vector retLabelUsers = new Vector();
/* 4974 */     Vector userCompanies = MilestoneHelper.getUserCompanies(context);
/* 4975 */     if (labelUsers != null && userCompanies != null)
/*      */     {
/* 4977 */       for (int i = 0; i < labelUsers.size(); i++) {
/* 4978 */         User user = (User)labelUsers.get(i);
/* 4979 */         if (user != null) {
/* 4980 */           for (int j = 0; j < userCompanies.size(); j++) {
/* 4981 */             int familyId = ((Company)userCompanies.get(j)).getParentEnvironment().getParentFamily().getStructureID();
/* 4982 */             if (familyId == user.getEmployedBy()) {
/* 4983 */               retLabelUsers.add(user);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/* 4990 */     return retLabelUsers;
/*      */   }
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
/*      */   public boolean isScheduleApplied(Selection selection) {
/* 5053 */     boolean applied = false;
/*      */     
/* 5055 */     if (selection.getSelectionID() > 0) {
/*      */       
/* 5057 */       String query = "SELECT * FROM vi_release_detail WHERE release_id = " + 
/*      */         
/* 5059 */         selection.getSelectionID() + 
/* 5060 */         ";";
/*      */       
/* 5062 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 5063 */       connector.runQuery();
/* 5064 */       applied = connector.more();
/* 5065 */       connector.close();
/*      */     } 
/*      */     
/* 5068 */     return applied;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getSelectionNotepad(Context context, int userId, int type) {
/* 5077 */     Vector contents = new Vector();
/*      */     
/* 5079 */     if (MilestoneHelper.getNotepadFromSession(type, context) != null) {
/*      */ 
/*      */       
/* 5082 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(type, context);
/*      */ 
/*      */       
/* 5085 */       if (notepad.getAllContents() == null || context.getSessionValue("ResetSelectionSortOrder") != null) {
/*      */ 
/*      */         
/* 5088 */         contents = getInstance().getSelectionNotepadList(userId, notepad, context);
/* 5089 */         notepad.setAllContents(contents, getNotepadTotalCount(notepad, context));
/*      */       } 
/*      */       
/* 5092 */       if (context.getSessionValue("selectionNotepadColumn") != null) {
/*      */         
/* 5094 */         if (context.getSessionValue("selectionNotepadColumn") == "1")
/*      */         {
/* 5096 */           notepad.setColumnNames(new String[] { "Artist", "Title", "Local Prod #", "Str Dt" });
/*      */         }
/* 5098 */         else if (context.getSessionValue("selectionNotepadColumn") == "2")
/*      */         {
/* 5100 */           notepad.setColumnNames(new String[] { "Artist", "Title", "UPC", "Str Dt" });
/*      */         }
/* 5102 */         else if (context.getSessionValue("selectionNotepadColumn") == "3")
/*      */         {
/* 5104 */           notepad.setColumnNames(new String[] { "Artist", "Title", "Prefix", "Str Dt" });
/*      */         }
/*      */         else
/*      */         {
/* 5108 */           notepad.setColumnNames(new String[] { "Artist", "Title", "Str Dt" });
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 5113 */         notepad.setColumnNames(new String[] { "Artist", "Title", "Str Dt" });
/*      */       } 
/*      */       
/* 5116 */       notepad.setNotePadType(type);
/* 5117 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 5121 */     String[] columnNames = { "Artist", "Title", "Str Dt" };
/*      */     
/* 5123 */     if (context.getSessionValue("selectionNotepadColumn") != null)
/*      */     {
/* 5125 */       if (context.getSessionValue("selectionNotepadColumn") == "1") {
/*      */         
/* 5127 */         columnNames = new String[] { "Artist", "Title", "Selection", "Str Dt" };
/*      */       }
/* 5129 */       else if (context.getSessionValue("selectionNotepadColumn") == "2") {
/*      */         
/* 5131 */         columnNames = new String[] { "Artist", "Title", "Upc", "Str Dt" };
/*      */       }
/* 5133 */       else if (context.getSessionValue("selectionNotepadColumn") == "3") {
/*      */         
/* 5135 */         columnNames = new String[] { "Artist", "Title", "Prefix", "Str Dt" };
/*      */       } 
/*      */     }
/*      */     
/* 5139 */     System.out.println("<<< new selection notepad ");
/* 5140 */     contents = getInstance().getSelectionNotepadList(userId, null, context);
/* 5141 */     Notepad newNotepad = new Notepad(contents, 0, 15, "Selections", type, columnNames);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5146 */     User user = (User)context.getSessionValue("user");
/* 5147 */     if (user != null && !user.SS_searchInitiated) {
/*      */       
/* 5149 */       String query = getInstance().getSelectionNotepadQueryUserDefaults(context);
/* 5150 */       String order = getInstance().getSelectionNotepadQueryUserDefaultsOrderBy(context);
/* 5151 */       newNotepad.setSearchQuery(query);
/* 5152 */       newNotepad.setOrderBy(order);
/*      */     } 
/* 5154 */     newNotepad.setPageStats(getNotepadTotalCount(newNotepad, context));
/* 5155 */     return newNotepad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getDefaultQuery(Context context) {
/* 5165 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 5169 */     Environment environment = null;
/* 5170 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/*      */ 
/*      */     
/* 5173 */     query.append("SELECT release_id, title, artist, configuration, sub_configuration, upc, prefix, selection_no, digital_flag, street_date =  CASE digital_flag  WHEN 0 THEN street_date   WHEN 1 THEN digital_rls_date  END ,archimedes_id, Release_Family_id from vi_Release_Header WHERE NOT ( status = 'CLOSED' OR  status = 'CANCEL' ) AND ");
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
/* 5208 */     if (environments.size() > 0) {
/* 5209 */       query.append(" environment_id in (");
/*      */     }
/* 5211 */     for (int i = 0; i < environments.size(); i++) {
/*      */       
/* 5213 */       environment = (Environment)environments.get(i);
/* 5214 */       if (environment != null)
/*      */       {
/* 5216 */         if (i == 0) {
/* 5217 */           query.append(environment.getStructureID());
/*      */         } else {
/* 5219 */           query.append("," + environment.getStructureID());
/*      */         } 
/*      */       }
/*      */     } 
/* 5223 */     if (environments.size() > 0) {
/* 5224 */       query.append(" )");
/*      */     }
/*      */     
/* 5227 */     Vector rFamilies = ReleasingFamily.getUserReleasingFamiliesVectorOfReleasingFamilies(context);
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
/* 5245 */     User user = (User)context.getSession().getAttribute("user");
/* 5246 */     Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
/*      */ 
/*      */     
/* 5249 */     addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*      */     
/* 5251 */     if (rFamilies != null && rFamilies.size() > 0) {
/*      */       
/* 5253 */       query.append(" AND (");
/* 5254 */       for (int i = 0; i < rFamilies.size(); i++) {
/*      */         
/* 5256 */         ReleasingFamily rf = (ReleasingFamily)rFamilies.get(i);
/* 5257 */         if (i > 0)
/* 5258 */           query.append(" OR "); 
/* 5259 */         query.append("(Release_Family_id = " + rf.getReleasingFamilyId());
/* 5260 */         addLabelFamilySelect(Integer.toString(rf.getReleasingFamilyId()), relFamilyLabelFamilyHash, query);
/* 5261 */         query.append(")");
/*      */       } 
/* 5263 */       query.append(") ");
/*      */     } else {
/*      */       
/* 5266 */       query.append(" AND (Release_Family_id = -1 and family_id = -1) ");
/*      */     } 
/*      */ 
/*      */     
/* 5270 */     query.append(ShowOrHideDigitalProductGet(user));
/*      */ 
/*      */     
/* 5273 */     log.log("selection query " + query.toString());
/* 5274 */     return query.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String ShowOrHideDigitalProductGet(User user) {
/* 5284 */     String digitalProdStr = "";
/*      */ 
/*      */ 
/*      */     
/* 5288 */     if (user == null || user.getAdministrator() != 1) {
/* 5289 */       digitalProdStr = " AND (digital_flag = 0) ";
/*      */     }
/* 5291 */     return digitalProdStr;
/*      */   }
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
/*      */   public static String getSequencedSelectionNumber() {
/* 5307 */     query = "sp_get_Sequence selection_no";
/*      */     
/* 5309 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 5310 */     connector.runQuery();
/*      */     
/* 5312 */     String prefixString = "";
/* 5313 */     int number = -1;
/*      */     
/* 5315 */     if (connector.more()) {
/*      */       
/* 5317 */       prefixString = connector.getField("Prefix");
/* 5318 */       number = connector.getIntegerField("SeqNo");
/*      */     } 
/*      */     
/* 5321 */     connector.close();
/*      */     
/* 5323 */     String result = "";
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 5328 */       NumberFormat form = new DecimalFormat("0000000");
/* 5329 */       result = String.valueOf(prefixString) + form.format(number);
/*      */     }
/* 5331 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5336 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTimestampValid(Selection selection) {
/* 5345 */     if (selection != null) {
/*      */       
/* 5347 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
/*      */         
/* 5349 */         selection.getSelectionID() + 
/* 5350 */         ";";
/* 5351 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 5352 */       connectorTimestamp.runQuery();
/* 5353 */       if (connectorTimestamp.more())
/*      */       {
/* 5355 */         if (selection.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*      */           
/* 5357 */           connectorTimestamp.close();
/* 5358 */           return false;
/*      */         } 
/*      */       }
/* 5361 */       connectorTimestamp.close();
/* 5362 */       return true;
/*      */     } 
/* 5364 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTimestampValid(Bom bom) {
/* 5373 */     if (bom != null) {
/*      */       
/* 5375 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM bom_header WHERE bom_id = " + 
/*      */         
/* 5377 */         bom.getBomId() + 
/* 5378 */         ";";
/*      */       
/* 5380 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 5381 */       connectorTimestamp.runQuery();
/* 5382 */       if (connectorTimestamp.more())
/*      */       {
/* 5384 */         if (bom.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/* 5385 */           connectorTimestamp.close();
/* 5386 */           return false;
/*      */         } 
/*      */       }
/* 5389 */       connectorTimestamp.close();
/* 5390 */       return true;
/*      */     } 
/* 5392 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTimestampValid(Pfm pfm) {
/* 5401 */     if (pfm != null) {
/*      */       
/* 5403 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM pfm_selection WHERE release_id = " + 
/*      */         
/* 5405 */         pfm.getReleaseId() + 
/* 5406 */         ";";
/* 5407 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 5408 */       connectorTimestamp.runQuery();
/* 5409 */       if (connectorTimestamp.more())
/*      */       {
/* 5411 */         if (pfm.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*      */           
/* 5413 */           connectorTimestamp.close();
/* 5414 */           return false;
/*      */         } 
/*      */       }
/* 5417 */       connectorTimestamp.close();
/* 5418 */       return true;
/*      */     } 
/* 5420 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isManufacturingTimestampValid(Selection selection) {
/* 5429 */     if (selection != null) {
/*      */       
/* 5431 */       String timestampQuery = "SELECT last_updated_ck  FROM release_subdetail WHERE release_id = " + 
/*      */         
/* 5433 */         selection.getSelectionID() + ";";
/*      */       
/* 5435 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 5436 */       connectorTimestamp.runQuery();
/* 5437 */       if (connectorTimestamp.more())
/*      */       {
/* 5439 */         if (selection.getLastMfgUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*      */           
/* 5441 */           connectorTimestamp.close();
/* 5442 */           return false;
/*      */         } 
/*      */       }
/* 5445 */       connectorTimestamp.close();
/* 5446 */       return true;
/*      */     } 
/* 5448 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Selection getSelectionAndSchedule(int id) {
/* 5456 */     Selection selection = getSelectionHeader(id);
/* 5457 */     Schedule schedule = ScheduleManager.getInstance().getSchedule(id);
/* 5458 */     selection.setSchedule(schedule);
/*      */     
/* 5460 */     return selection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSelectionsSchedule(Selection selection) {
/* 5468 */     Schedule schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/* 5469 */     selection.setSchedule(schedule);
/*      */   }
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
/*      */   public int getUnitsFromPfm(Selection selection) {
/* 5482 */     int units = 0;
/*      */     
/* 5484 */     if (selection != null) {
/*      */       
/* 5486 */       int selectionId = selection.getSelectionID();
/*      */       
/* 5488 */       String manufacturingQuery = "SELECT units_per_set FROM vi_pfm_selection  WHERE release_id = " + 
/* 5489 */         selectionId;
/*      */       
/* 5491 */       JdbcConnector connector = MilestoneHelper.getConnector(manufacturingQuery);
/* 5492 */       connector.runQuery();
/*      */ 
/*      */       
/*      */       try {
/* 5496 */         units = connector.getIntegerField("units_per_set");
/*      */       }
/* 5498 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5503 */       connector.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 5508 */     return units;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSelectionOkToClose(Context context) {
/* 5517 */     boolean close = true;
/*      */     
/* 5519 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5521 */     Schedule schedule = null;
/*      */     
/* 5523 */     if (selection != null) {
/* 5524 */       schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/*      */     }
/* 5526 */     if (schedule != null) {
/*      */       
/* 5528 */       Vector tasks = schedule.getTasks();
/* 5529 */       if (tasks != null) {
/*      */         
/* 5531 */         ScheduledTask task = null;
/*      */         
/* 5533 */         for (int i = 0; i < tasks.size(); i++) {
/*      */           
/* 5535 */           task = (ScheduledTask)tasks.get(i);
/*      */           
/* 5537 */           if (task.getCompletionDate() == null)
/*      */           {
/*      */ 
/*      */             
/* 5541 */             if (MilestoneHelper.isUml(task) || MilestoneHelper.isEcommerce(task)) {
/*      */               
/* 5543 */               close = false;
/*      */               
/*      */               break;
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 5552 */     return close;
/*      */   }
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
/*      */   public Vector getMultSelections(int id) {
/* 5567 */     String sqlStr = "sp_get_Schedule_MutSelections " + id;
/* 5568 */     JdbcConnector multSelectionsConnector = MilestoneHelper.getConnector(sqlStr);
/* 5569 */     multSelectionsConnector.runQuery();
/*      */     
/* 5571 */     Vector multSelections = new Vector();
/*      */     
/* 5573 */     while (multSelectionsConnector.more()) {
/*      */       
/* 5575 */       MultSelection multSel = new MultSelection();
/*      */       
/* 5577 */       multSel.setMultSelectionPK(multSelectionsConnector.getInt("multSelectionsPK", -1));
/* 5578 */       multSel.setRelease_id(multSelectionsConnector.getInt("release_id", -1));
/* 5579 */       multSel.setSelectionNo(multSelectionsConnector.getField("selectionNo", ""));
/* 5580 */       multSel.setUpc(multSelectionsConnector.getField("upc", ""));
/* 5581 */       multSel.setDescription(multSelectionsConnector.getField("description", ""));
/*      */       
/* 5583 */       multSelections.add(multSel);
/*      */       
/* 5585 */       multSelectionsConnector.next();
/*      */     } 
/*      */     
/* 5588 */     multSelectionsConnector.close();
/*      */     
/* 5590 */     return multSelections;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveMultSelections(Selection selection, User updatingUser) {
/* 5600 */     if (selection != null && selection.getMultSelections() != null) {
/*      */       
/* 5602 */       Vector multSelections = selection.getMultSelections();
/*      */       
/* 5604 */       Vector addMultSelections = new Vector();
/* 5605 */       Vector deleteMultSelections = new Vector();
/*      */       
/* 5607 */       String sqlQuery = "select * from vi_MultSelections where release_id = " + selection.getSelectionID();
/*      */ 
/*      */ 
/*      */       
/* 5611 */       boolean delete = true;
/*      */       
/* 5613 */       for (int a = 0; a < multSelections.size(); a++) {
/*      */         
/* 5615 */         MultSelection multSel = (MultSelection)multSelections.get(a);
/*      */ 
/*      */ 
/*      */         
/* 5619 */         JdbcConnector connectorMultSelectionCount = MilestoneHelper.getConnector(sqlQuery);
/* 5620 */         connectorMultSelectionCount.runQuery();
/*      */         
/* 5622 */         if (connectorMultSelectionCount.more()) {
/*      */           
/* 5624 */           while (connectorMultSelectionCount.more())
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 5629 */             if (multSel.getMultSelectionsPK() == -1 || 
/* 5630 */               multSel.getMultSelectionsPK() == connectorMultSelectionCount.getInt("multSelectionsPK", -2)) {
/*      */               
/* 5632 */               addMultSelections.add(multSel);
/*      */               break;
/*      */             } 
/* 5635 */             connectorMultSelectionCount.next();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 5640 */           addMultSelections.add(multSel);
/*      */         } 
/*      */         
/* 5643 */         connectorMultSelectionCount.close();
/*      */       } 
/*      */       
/* 5646 */       JdbcConnector connectorMultSelectionDelete = MilestoneHelper.getConnector(sqlQuery);
/* 5647 */       connectorMultSelectionDelete.runQuery();
/*      */       
/* 5649 */       while (connectorMultSelectionDelete.more()) {
/*      */         
/* 5651 */         delete = true;
/*      */         
/* 5653 */         for (int b = 0; b < multSelections.size(); b++) {
/*      */           
/* 5655 */           MultSelection multSelDelete = (MultSelection)multSelections.get(b);
/*      */           
/* 5657 */           if (connectorMultSelectionDelete.getInt("multSelectionsPK", -2) == multSelDelete.getMultSelectionsPK())
/*      */           {
/* 5659 */             delete = false;
/*      */           }
/*      */         } 
/*      */         
/* 5663 */         if (delete) {
/*      */           
/* 5665 */           MultSelection delMultSelection = new MultSelection();
/* 5666 */           delMultSelection.setMultSelectionPK(connectorMultSelectionDelete.getInt("multSelectionsPK", -1));
/* 5667 */           deleteMultSelections.add(delMultSelection);
/*      */         } 
/*      */         
/* 5670 */         connectorMultSelectionDelete.next();
/*      */       } 
/* 5672 */       connectorMultSelectionDelete.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5678 */       for (int i = 0; i < addMultSelections.size(); i++) {
/*      */         
/* 5680 */         MultSelection multSel = (MultSelection)addMultSelections.get(i);
/*      */         
/* 5682 */         int relId = multSel.getRealease_id();
/* 5683 */         int multSelectionsPK = multSel.getMultSelectionsPK();
/*      */         
/* 5685 */         if (relId < 0) {
/* 5686 */           relId = selection.getSelectionID();
/*      */         }
/*      */         
/* 5689 */         String sqlStr = "sp_sav_MultSelections " + multSel.getMultSelectionsPK() + "," + 
/* 5690 */           relId + "," + 
/* 5691 */           "'" + multSel.getSelectionNo() + "'," + 
/*      */           
/* 5693 */           "'" + MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(multSel.getUpc(), "UPC", false, true) + "'," + 
/* 5694 */           "'" + multSel.getDescription() + "'";
/*      */ 
/*      */ 
/*      */         
/* 5698 */         JdbcConnector connectorAddMultSelection = MilestoneHelper.getConnector(sqlStr);
/* 5699 */         connectorAddMultSelection.runQuery();
/* 5700 */         connectorAddMultSelection.close();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5708 */       JdbcConnector connectorDeleteMultSelection = null;
/* 5709 */       for (int k = 0; k < deleteMultSelections.size(); k++) {
/*      */         
/* 5711 */         MultSelection multSel = (MultSelection)deleteMultSelections.get(k);
/*      */         
/* 5713 */         String deleteSql = "sp_del_MultSelections " + multSel.getMultSelectionsPK();
/*      */ 
/*      */ 
/*      */         
/* 5717 */         connectorDeleteMultSelection = MilestoneHelper.getConnector(deleteSql);
/* 5718 */         connectorDeleteMultSelection.runQuery();
/* 5719 */         connectorDeleteMultSelection.close();
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public Vector getMultOtherContacts(int id) {
/* 5738 */     String sqlStr = "sp_get_Schedule_MultOtherContacts " + id;
/* 5739 */     JdbcConnector multOtherContactsConnector = MilestoneHelper.getConnector(sqlStr);
/* 5740 */     multOtherContactsConnector.runQuery();
/*      */     
/* 5742 */     Vector multOtherContacts = new Vector();
/*      */     
/* 5744 */     while (multOtherContactsConnector.more()) {
/*      */       
/* 5746 */       MultOtherContact otherContact = new MultOtherContact();
/*      */       
/* 5748 */       otherContact.setMultOtherContactsPK(multOtherContactsConnector.getInt("multOtherContactsPK", -1));
/* 5749 */       otherContact.setRelease_id(multOtherContactsConnector.getInt("release_id", -1));
/* 5750 */       otherContact.setName(multOtherContactsConnector.getField("name", ""));
/* 5751 */       otherContact.setDescription(multOtherContactsConnector.getField("description", ""));
/*      */       
/* 5753 */       multOtherContacts.add(otherContact);
/*      */       
/* 5755 */       multOtherContactsConnector.next();
/*      */     } 
/*      */     
/* 5758 */     multOtherContactsConnector.close();
/*      */     
/* 5760 */     return multOtherContacts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveMultOtherContacts(Selection selection, User updatingUser) {
/* 5770 */     if (selection != null && selection.getMultOtherContacts() != null) {
/*      */       
/* 5772 */       Vector multOtherContacts = selection.getMultOtherContacts();
/*      */       
/* 5774 */       Vector addMultOtherContacts = new Vector();
/* 5775 */       Vector deleteMultOtherContacts = new Vector();
/*      */       
/* 5777 */       String sqlQuery = "select * from vi_MultOtherContacts where release_id = " + selection.getSelectionID();
/*      */ 
/*      */ 
/*      */       
/* 5781 */       boolean delete = true;
/*      */       
/* 5783 */       for (int a = 0; a < multOtherContacts.size(); a++) {
/*      */         
/* 5785 */         MultOtherContact otherContact = (MultOtherContact)multOtherContacts.get(a);
/*      */ 
/*      */ 
/*      */         
/* 5789 */         JdbcConnector connectorMultOtherContactCount = MilestoneHelper.getConnector(sqlQuery);
/* 5790 */         connectorMultOtherContactCount.runQuery();
/*      */         
/* 5792 */         if (connectorMultOtherContactCount.more()) {
/*      */           
/* 5794 */           while (connectorMultOtherContactCount.more())
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 5799 */             if (otherContact.getMultOtherContactsPK() == -1 || 
/* 5800 */               otherContact.getMultOtherContactsPK() == connectorMultOtherContactCount.getInt("multOtherContactsPK", -2)) {
/*      */               
/* 5802 */               addMultOtherContacts.add(otherContact);
/*      */               break;
/*      */             } 
/* 5805 */             connectorMultOtherContactCount.next();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 5810 */           addMultOtherContacts.add(otherContact);
/*      */         } 
/*      */         
/* 5813 */         connectorMultOtherContactCount.close();
/*      */       } 
/*      */       
/* 5816 */       JdbcConnector connectorMultOtherContactDelete = MilestoneHelper.getConnector(sqlQuery);
/* 5817 */       connectorMultOtherContactDelete.runQuery();
/*      */       
/* 5819 */       while (connectorMultOtherContactDelete.more()) {
/*      */         
/* 5821 */         delete = true;
/*      */         
/* 5823 */         for (int b = 0; b < multOtherContacts.size(); b++) {
/*      */           
/* 5825 */           MultOtherContact multOtherContactDelete = (MultOtherContact)multOtherContacts.get(b);
/*      */           
/* 5827 */           if (connectorMultOtherContactDelete.getInt("multOtherContactsPK", -2) == multOtherContactDelete.getMultOtherContactsPK())
/*      */           {
/* 5829 */             delete = false;
/*      */           }
/*      */         } 
/*      */         
/* 5833 */         if (delete) {
/*      */           
/* 5835 */           MultOtherContact delMultOtherContact = new MultOtherContact();
/* 5836 */           delMultOtherContact.setMultOtherContactsPK(connectorMultOtherContactDelete.getInt("multOtherContactsPK", -1));
/* 5837 */           deleteMultOtherContacts.add(delMultOtherContact);
/*      */         } 
/*      */         
/* 5840 */         connectorMultOtherContactDelete.next();
/*      */       } 
/* 5842 */       connectorMultOtherContactDelete.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5848 */       for (int i = 0; i < addMultOtherContacts.size(); i++) {
/*      */         
/* 5850 */         MultOtherContact otherContact = (MultOtherContact)addMultOtherContacts.get(i);
/*      */         
/* 5852 */         int relId = otherContact.getRealease_id();
/* 5853 */         int multOtherContactsPK = otherContact.getMultOtherContactsPK();
/*      */         
/* 5855 */         if (relId < 0) {
/* 5856 */           relId = selection.getSelectionID();
/*      */         }
/*      */         
/* 5859 */         String sqlStr = "sp_sav_MultOtherContacts " + otherContact.getMultOtherContactsPK() + "," + 
/* 5860 */           relId + "," + 
/* 5861 */           "'" + otherContact.getName() + "'," + 
/* 5862 */           "'" + otherContact.getDescription() + "'";
/*      */ 
/*      */ 
/*      */         
/* 5866 */         JdbcConnector connectorAddMultOtherContact = MilestoneHelper.getConnector(sqlStr);
/* 5867 */         connectorAddMultOtherContact.runQuery();
/* 5868 */         connectorAddMultOtherContact.close();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5876 */       JdbcConnector connectorDeleteMultOtherContact = null;
/* 5877 */       for (int k = 0; k < deleteMultOtherContacts.size(); k++) {
/*      */         
/* 5879 */         MultOtherContact otherContact = (MultOtherContact)deleteMultOtherContacts.get(k);
/*      */         
/* 5881 */         String deleteSql = "sp_del_MultOtherContacts " + otherContact.getMultOtherContactsPK();
/*      */ 
/*      */ 
/*      */         
/* 5885 */         connectorDeleteMultOtherContact = MilestoneHelper.getConnector(deleteSql);
/* 5886 */         connectorDeleteMultOtherContact.runQuery();
/* 5887 */         connectorDeleteMultOtherContact.close();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getAlphaGroupPosition(Context context, Notepad notepad, String alphaChar, int sortOrder) {
/* 5899 */     Vector notepadList = notepad.getAllContents();
/* 5900 */     boolean fndChar = false;
/*      */     
/* 5902 */     if (notepad != null && notepadList != null && notepadList.size() > 0) {
/*      */ 
/*      */       
/* 5905 */       int x = 0;
/*      */ 
/*      */ 
/*      */       
/* 5909 */       NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
/*      */       
/* 5911 */       if (sortOrder == 0 || sortOrder == 7) {
/* 5912 */         notepadSortOrder.setSelectionOrderCol("Artist");
/*      */       }
/* 5914 */       if (sortOrder == 1 || sortOrder == 8) {
/* 5915 */         notepadSortOrder.setSelectionOrderCol("Title");
/*      */       }
/* 5917 */       notepadSortOrder.setSelectionOrderColNo(sortOrder);
/*      */ 
/*      */       
/* 5920 */       for (Iterator i = notepadList.iterator(); i.hasNext(); x++) {
/*      */         
/* 5922 */         Selection selection = (Selection)i.next();
/* 5923 */         String scanStr = "";
/*      */         
/* 5925 */         if (sortOrder == 0 || sortOrder == 7)
/* 5926 */           scanStr = selection.getArtist(); 
/* 5927 */         if (sortOrder == 1 || sortOrder == 8) {
/* 5928 */           scanStr = selection.getTitle();
/*      */         }
/*      */         
/* 5931 */         int result = scanStr.substring(0).compareToIgnoreCase(alphaChar.substring(0));
/*      */         
/* 5933 */         if (sortOrder == 0 || sortOrder == 1) {
/*      */           
/* 5935 */           if (result == 0 || result > 0) {
/* 5936 */             notepad.setSelected(selection);
/* 5937 */             fndChar = true;
/*      */             
/*      */             break;
/*      */           } 
/* 5941 */         } else if (result < 0) {
/*      */           
/* 5943 */           Selection upOne = null;
/* 5944 */           if (x > 0)
/* 5945 */             upOne = (Selection)notepadList.get(x - 1); 
/* 5946 */           notepad.setSelected(upOne);
/* 5947 */           fndChar = true;
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 5955 */       if (!fndChar) {
/* 5956 */         if (x > 0) {
/* 5957 */           Selection upOne = (Selection)notepadList.get(x - 1);
/* 5958 */           notepad.setSelected(upOne);
/*      */         } 
/* 5960 */         context.putDelivery("AlertMessage", "There is no record that matches the search criteria");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector getUserEnvironments(Context context) {
/* 5974 */     Vector userCompanies = MilestoneHelper.getUserCompanies(context);
/* 5975 */     Vector userEnvironments = new Vector();
/*      */     
/* 5977 */     Environment env = null;
/* 5978 */     boolean duplicate = false;
/* 5979 */     for (int i = 0; i < userCompanies.size(); i++) {
/*      */       
/* 5981 */       duplicate = false;
/* 5982 */       env = ((Company)userCompanies.get(i)).getParentEnvironment();
/*      */       
/* 5984 */       for (int j = 0; j < userEnvironments.size(); j++) {
/*      */         
/* 5986 */         if (env.getName() == ((Environment)userEnvironments.get(j)).getName()) {
/*      */           
/* 5988 */           duplicate = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 5993 */       if (!duplicate) {
/* 5994 */         userEnvironments.add(env);
/*      */       }
/*      */     } 
/* 5997 */     return userEnvironments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isSelectionIDDuplicate(String prefix, String selectionNo, int selectionID, boolean isDigital) {
/* 6004 */     int prodType = 0;
/*      */     
/* 6006 */     if (isDigital) {
/* 6007 */       prodType = 1;
/*      */     }
/*      */     
/* 6010 */     if (isDigital && selectionNo.length() == 0) {
/* 6011 */       return false;
/*      */     }
/*      */     
/* 6014 */     String sqlStr = "SELECT count(*) as myCount FROM release_header with(nolock)  WHERE prefix = '" + 
/* 6015 */       prefix + "' " + 
/* 6016 */       " AND NOT status = 'CLOSED' " + 
/* 6017 */       " AND selection_no = '" + selectionNo + "'" + 
/* 6018 */       " AND release_id <> " + selectionID + 
/* 6019 */       " AND digital_flag = " + prodType;
/*      */ 
/*      */     
/* 6022 */     JdbcConnector isDup = MilestoneHelper.getConnector(sqlStr);
/* 6023 */     isDup.runQuery();
/*      */     
/* 6025 */     int count = 0;
/* 6026 */     if (isDup.more()) {
/* 6027 */       count = isDup.getInt("myCount");
/*      */     }
/* 6029 */     isDup.close();
/* 6030 */     return (count > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveSelectionDataFromDigitalPfm(int releaseID, PrefixCode prefix, String selectionNo, String upc, String configCode, String soundscan) {
/* 6042 */     String query = "UPDATE dbo.Release_Header SET UPC = '" + 
/* 6043 */       MilestoneHelper.escapeSingleQuotes(upc) + "', config_code = '" + 
/* 6044 */       MilestoneHelper.escapeSingleQuotes(configCode) + "', soundscan_grp = '" + 
/* 6045 */       MilestoneHelper.escapeSingleQuotes(soundscan) + "', prefix = '" + 
/* 6046 */       getLookupObjectValue(prefix) + "', selection_no = '" + 
/* 6047 */       MilestoneHelper.escapeSingleQuotes(selectionNo) + "'" + 
/* 6048 */       " WHERE release_id = " + String.valueOf(releaseID);
/*      */     
/* 6050 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 6051 */     connector.runQuery();
/* 6052 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveSelectionDataFromPhysicalPfm(int releaseID, String streetDate) {
/* 6062 */     String query = "UPDATE dbo.Release_Header SET ";
/* 6063 */     query = String.valueOf(query) + " street_date = '" + MilestoneHelper.escapeSingleQuotes(streetDate) + "'";
/* 6064 */     query = String.valueOf(query) + " WHERE release_id = " + String.valueOf(releaseID);
/*      */ 
/*      */ 
/*      */     
/* 6068 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 6069 */     connector.runQuery();
/* 6070 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isProjectNumberValidFromMilestoneSnapshot(Selection selection) {
/* 6079 */     String projectsQuery = "";
/* 6080 */     String projectNumber = selection.getProjectID();
/*      */     
/* 6082 */     if (selection.getFamily().getName().equalsIgnoreCase("UMVD")) {
/*      */       
/* 6084 */       projectNumber = ProjectSearchManager.replaceStringInString(projectNumber, "-", "");
/* 6085 */       if (projectNumber.length() > 8)
/* 6086 */         projectNumber = projectNumber.substring(1); 
/* 6087 */       projectsQuery = String.valueOf(projectsQuery) + "select * from ArchimedesProjects where [JDE Project#] = '" + projectNumber + "'";
/*      */     }
/*      */     else {
/*      */       
/* 6091 */       projectsQuery = String.valueOf(projectsQuery) + "select * from ArchimedesProjects where [RMS Project#] = '" + projectNumber + "'";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6096 */     JdbcConnector connector = MilestoneHelper.getConnector(projectsQuery);
/*      */ 
/*      */     
/*      */     try {
/* 6100 */       connector.runQuery();
/*      */     }
/* 6102 */     catch (Exception e) {
/*      */       
/* 6104 */       System.out.println("****exception raised in isProjectNumberValidFromMilestoneSnapshot****");
/*      */     } 
/*      */ 
/*      */     
/* 6108 */     boolean isValid = false;
/* 6109 */     if (connector.more()) {
/* 6110 */       isValid = true;
/*      */     }
/* 6112 */     connector.close();
/* 6113 */     return isValid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Selection isProjectNumberValid(Selection selection) {
/* 6122 */     String projectsQuery = "";
/* 6123 */     String projectNumber = selection.getProjectID();
/* 6124 */     Selection updatedSel = null;
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
/* 6309 */     boolean IsUMVD = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 6319 */       updatedSel = projectSearchSvcClient.ProjectSearchValidation(projectNumber, Boolean.valueOf(IsUMVD));
/* 6320 */       System.out.println("Project Search from WCF: " + updatedSel.scheduleId);
/* 6321 */     } catch (Exception e) {
/* 6322 */       System.out.println(e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6327 */     return updatedSel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getArchimedesIdFromCache(int label_id) {
/* 6337 */     int returnId = -1;
/*      */ 
/*      */     
/* 6340 */     Vector labels = Cache.getLabels();
/* 6341 */     if (labels != null)
/*      */     {
/* 6343 */       for (int i = 0; i < labels.size(); i++) {
/*      */ 
/*      */         
/* 6346 */         Label label = (Label)labels.get(i);
/*      */         
/* 6348 */         if (label.getStructureID() == label_id) {
/*      */           
/* 6350 */           returnId = label.getArchimedesId();
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 6355 */     return returnId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addReleasingFamilyLabelFamilySelect(String formName, Context context, StringBuffer query, Form form) {
/* 6367 */     User user = (User)context.getSession().getAttribute("user");
/* 6368 */     Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
/*      */ 
/*      */     
/* 6371 */     addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*      */     
/* 6373 */     FormDropDownMenu famDD = (FormDropDownMenu)form.getElement(formName);
/* 6374 */     String familySearch = famDD.getStringValue();
/* 6375 */     if (familySearch != null && !familySearch.equals("") && !familySearch.equals("0") && 
/* 6376 */       !familySearch.equals("-1")) {
/*      */       
/* 6378 */       query.append(" AND (Release_Family_id = " + familySearch);
/*      */       
/* 6380 */       addLabelFamilySelect(familySearch, relFamilyLabelFamilyHash, query);
/* 6381 */       query.append(") ");
/*      */     }
/*      */     else {
/*      */       
/* 6385 */       String[] valueList = famDD.getValueList();
/* 6386 */       if (valueList != null && valueList.length > 0) {
/*      */         
/* 6388 */         query.append(" AND (");
/* 6389 */         for (int i = 0; i < valueList.length; i++) {
/*      */           
/* 6391 */           if (i > 0)
/* 6392 */             query.append(" OR "); 
/* 6393 */           query.append("(Release_Family_id = " + valueList[i]);
/* 6394 */           addLabelFamilySelect(valueList[i], relFamilyLabelFamilyHash, query);
/* 6395 */           query.append(")");
/*      */         } 
/* 6397 */         query.append(") ");
/*      */       } else {
/*      */         
/* 6400 */         query.append(" AND (Release_Family_id = -1 and family_id = -1) ");
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void addLabelFamilySelect(String relFamilyStr, Hashtable relFamilyLabelFamilyHash, StringBuffer query) {
/* 6415 */     if (relFamilyLabelFamilyHash.containsKey(relFamilyStr)) {
/*      */       
/* 6417 */       Vector labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyStr);
/* 6418 */       if (labelFamilies != null && labelFamilies.size() > 0) {
/* 6419 */         for (int i = 0; i < labelFamilies.size(); i++) {
/*      */           
/* 6421 */           if (i == 0) {
/* 6422 */             query.append(" AND family_id in (" + (String)labelFamilies.get(i));
/*      */           } else {
/* 6424 */             query.append("," + (String)labelFamilies.get(i));
/*      */           } 
/* 6426 */         }  query.append(")");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 6431 */       query.append(" AND family_id in (" + relFamilyStr + ")");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addUndefinedReleasingFamilies(Hashtable relFamilyLabelFamilyHash, Context context) {
/* 6438 */     Vector labelFamilies = null;
/* 6439 */     Vector families = ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context);
/*      */     
/* 6441 */     if (families != null)
/*      */     {
/* 6443 */       for (int i = 0; i < families.size(); i++) {
/*      */         
/* 6445 */         Family family = (Family)families.get(i);
/*      */         
/* 6447 */         if (family != null) {
/*      */ 
/*      */           
/* 6450 */           String relFamilyIdStr = Integer.toString(family.getStructureID());
/* 6451 */           if (relFamilyLabelFamilyHash.containsKey(relFamilyIdStr)) {
/* 6452 */             boolean addToHash = true;
/* 6453 */             labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyIdStr);
/* 6454 */             for (int c = 0; c < labelFamilies.size(); c++) {
/*      */               
/* 6456 */               String labelFamilyIdStr = (String)labelFamilies.get(c);
/*      */               
/* 6458 */               if (labelFamilyIdStr.equals(relFamilyIdStr)) {
/*      */                 
/* 6460 */                 addToHash = false;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 6465 */             if (addToHash) {
/*      */               
/* 6467 */               labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyIdStr);
/* 6468 */               labelFamilies.add(relFamilyIdStr);
/*      */             } 
/*      */           } else {
/*      */             
/* 6472 */             labelFamilies = new Vector();
/* 6473 */             labelFamilies.add(relFamilyIdStr);
/* 6474 */             relFamilyLabelFamilyHash.put(relFamilyIdStr, labelFamilies);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSelectionNotepadQueryUserDefaults(Context context) {
/* 6486 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 6490 */     Environment environment = null;
/* 6491 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 6492 */     int year = Calendar.getInstance().get(1);
/* 6493 */     String date = "01/01/" + year;
/*      */ 
/*      */     
/* 6496 */     query.append("SELECT release_id, title, artist, configuration, sub_configuration, upc, prefix, selection_no, digital_flag, street_date =  CASE digital_flag  WHEN 0 THEN street_date   WHEN 1 THEN digital_rls_date  END ,archimedes_id, Release_Family_id from vi_Release_Header WHERE ");
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
/* 6508 */     User user = (User)context.getSession().getAttribute("user");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6513 */     if (user.getPreferences().getSelectionEnvironment() > 0) {
/*      */       
/* 6515 */       int envId = user.getPreferences().getSelectionEnvironment();
/* 6516 */       query.append(" environment_id in (" + envId + ") ");
/* 6517 */       user.SS_environmentSearch = String.valueOf(envId);
/* 6518 */       user.RC_environment = String.valueOf(envId);
/*      */     } else {
/* 6520 */       if (environments.size() > 0) {
/* 6521 */         query.append(" environment_id in (");
/*      */       }
/* 6523 */       for (int i = 0; i < environments.size(); i++) {
/* 6524 */         environment = (Environment)environments.get(i);
/* 6525 */         if (environment != null) {
/* 6526 */           if (i == 0) {
/* 6527 */             query.append(environment.getStructureID());
/*      */           } else {
/* 6529 */             query.append("," + environment.getStructureID());
/*      */           } 
/*      */         }
/*      */       } 
/* 6533 */       if (environments.size() > 0) {
/* 6534 */         query.append(" )");
/*      */       }
/*      */     } 
/* 6537 */     Vector rFamilies = ReleasingFamily.getUserReleasingFamiliesVectorOfReleasingFamilies(context);
/*      */     
/* 6539 */     Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6544 */     if (user.getPreferences().getSelectionReleasingFamily() > 0) {
/*      */ 
/*      */       
/* 6547 */       addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*      */       
/* 6549 */       int relFamId = user.getPreferences().getSelectionReleasingFamily();
/* 6550 */       query.append(" AND (Release_Family_id = " + relFamId);
/* 6551 */       addLabelFamilySelect(Integer.toString(relFamId), relFamilyLabelFamilyHash, query);
/* 6552 */       query.append(") ");
/* 6553 */       user.SS_familySearch = String.valueOf(relFamId);
/* 6554 */       user.RC_releasingFamily = String.valueOf(relFamId);
/*      */     }
/*      */     else {
/*      */       
/* 6558 */       addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*      */       
/* 6560 */       if (rFamilies != null && rFamilies.size() > 0) {
/* 6561 */         query.append(" AND (");
/* 6562 */         for (int i = 0; i < rFamilies.size(); i++) {
/* 6563 */           ReleasingFamily rf = (ReleasingFamily)rFamilies.get(i);
/* 6564 */           if (i > 0)
/* 6565 */             query.append(" OR "); 
/* 6566 */           query.append("(Release_Family_id = " + rf.getReleasingFamilyId());
/* 6567 */           addLabelFamilySelect(Integer.toString(rf.getReleasingFamilyId()), 
/* 6568 */               relFamilyLabelFamilyHash, query);
/* 6569 */           query.append(")");
/*      */         } 
/* 6571 */         query.append(") ");
/*      */       } else {
/*      */         
/* 6574 */         query.append(" AND (Release_Family_id = -1 and family_id = -1) ");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6580 */     if (user != null && user.getPreferences() != null) {
/*      */ 
/*      */       
/* 6583 */       user.SS_showAllSearch = "true";
/* 6584 */       if (user.getPreferences().getSelectionStatus() == 0) {
/*      */         
/* 6586 */         query.append(" AND NOT ( status = 'CLOSED' OR  status = 'CANCEL' )");
/* 6587 */         user.SS_showAllSearch = "false";
/*      */       } 
/*      */ 
/*      */       
/* 6591 */       if (user.getPreferences().getSelectionLabelContact() > 0) {
/*      */         
/* 6593 */         query.append(" AND contact_id = " + user.getPreferences().getSelectionLabelContact());
/* 6594 */         user.SS_contactSearch = String.valueOf(user.getPreferences().getSelectionLabelContact());
/* 6595 */         user.RC_labelContact = String.valueOf(user.getPreferences().getSelectionLabelContact());
/*      */       } 
/*      */ 
/*      */       
/* 6599 */       if (user.getPreferences().getSelectionProductType() > -1) {
/*      */ 
/*      */         
/* 6602 */         String productTypeSearch = "";
/* 6603 */         if (user.getPreferences().getSelectionProductType() == 0) {
/*      */           
/* 6605 */           productTypeSearch = "physical";
/* 6606 */           query.append(" AND digital_flag = 0 ");
/*      */         } 
/* 6608 */         if (user.getPreferences().getSelectionProductType() == 1) {
/*      */           
/* 6610 */           productTypeSearch = "digital";
/* 6611 */           query.append(" AND digital_flag = 1 ");
/*      */         } 
/* 6613 */         if (user.getPreferences().getSelectionProductType() == 2)
/*      */         {
/* 6615 */           productTypeSearch = "both";
/*      */         }
/* 6617 */         user.SS_productTypeSearch = productTypeSearch;
/* 6618 */         user.RC_productType = MilestoneHelper_2.convertProductTypeToReleaseCalendar(productTypeSearch);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6624 */     query.append(ShowOrHideDigitalProductGet(user));
/*      */ 
/*      */     
/* 6627 */     log.log("selection query " + query.toString());
/*      */     
/* 6629 */     return query.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSelectionNotepadQueryUserDefaultsOrderBy(Context context) {
/* 6639 */     User user = (User)context.getSession().getAttribute("user");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6645 */     String order = " ORDER BY artist, title, selection_no, street_date";
/*      */ 
/*      */     
/* 6648 */     if (user != null && user.getPreferences() != null) {
/*      */ 
/*      */       
/* 6651 */       NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
/* 6652 */       notepadSortOrder.setSelectionOrderCol("Artist");
/* 6653 */       notepadSortOrder.setSelectionOrderBy(order);
/* 6654 */       notepadSortOrder.setShowGroupButtons(true);
/* 6655 */       notepadSortOrder.setSelectionOrderColNo(0);
/*      */ 
/*      */       
/* 6658 */       if (user.getPreferences().getNotepadSortBy() == 1) {
/* 6659 */         if (user.getPreferences().getNotepadOrder() == 1) {
/* 6660 */           order = " ORDER BY artist ASC , title, selection_no, street_date";
/* 6661 */         } else if (user.getPreferences().getNotepadOrder() == 2) {
/*      */           
/* 6663 */           order = " ORDER BY artist DESC , title, selection_no, street_date";
/* 6664 */           notepadSortOrder.setSelectionOrderColNo(7);
/*      */         } else {
/* 6666 */           order = " ORDER BY artist, title, selection_no, street_date";
/*      */         } 
/*      */         
/* 6669 */         notepadSortOrder.setSelectionOrderCol("Artist");
/* 6670 */         notepadSortOrder.setSelectionOrderBy(order);
/* 6671 */         notepadSortOrder.setShowGroupButtons(true);
/*      */       }
/* 6673 */       else if (user.getPreferences().getNotepadSortBy() == 2) {
/* 6674 */         notepadSortOrder.setSelectionOrderColNo(1);
/* 6675 */         if (user.getPreferences().getNotepadOrder() == 1) {
/* 6676 */           order = " ORDER BY title ASC , artist, selection_no, street_date";
/* 6677 */         } else if (user.getPreferences().getNotepadOrder() == 2) {
/*      */           
/* 6679 */           order = " ORDER BY title DESC , artist, selection_no, street_date";
/* 6680 */           notepadSortOrder.setSelectionOrderColNo(8);
/*      */         } else {
/* 6682 */           order = " ORDER BY title, artist, selection_no, street_date";
/*      */         } 
/* 6684 */         notepadSortOrder.setSelectionOrderCol("Title");
/* 6685 */         notepadSortOrder.setSelectionOrderBy(order);
/* 6686 */         notepadSortOrder.setShowGroupButtons(true);
/*      */       }
/* 6688 */       else if (user.getPreferences().getNotepadSortBy() == 3) {
/* 6689 */         notepadSortOrder.setSelectionOrderColNo(5);
/* 6690 */         if (user.getPreferences().getNotepadOrder() == 1) {
/* 6691 */           order = " ORDER BY street_date ASC , artist, title, selection_no";
/* 6692 */         } else if (user.getPreferences().getNotepadOrder() == 2) {
/* 6693 */           order = " ORDER BY street_date DESC , artist, title, selection_no";
/*      */         } else {
/* 6695 */           order = " ORDER BY street_date, artist, title, selection_no";
/*      */         } 
/* 6697 */         notepadSortOrder.setShowGroupButtons(false);
/* 6698 */         notepadSortOrder.setSelectionOrderCol("Str Dt");
/* 6699 */         notepadSortOrder.setSelectionOrderBy(order);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 6704 */     return order;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setSelectionNotepadUserDefaults(Context context) {
/* 6715 */     Notepad selNotepad = MilestoneHelper.getNotepadFromSession(0, context);
/*      */     
/* 6717 */     if (selNotepad == null) {
/*      */       
/* 6719 */       User user = (User)context.getSession().getAttribute("user");
/* 6720 */       if (user != null) {
/* 6721 */         selNotepad = getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 6722 */         MilestoneHelper.putNotepadIntoSession(selNotepad, context);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void GDRS_QueueAddReleaseId(Selection selection, String statusCode) {
/* 6734 */     if ((selection != null && selection.getReleaseFamilyId() != 820 && IsDigitalEquivalent(selection) && (
/* 6735 */       !selection.releaseType.abbreviation.equals("PR") || !selection.getUpc().trim().equals(""))) || statusCode.equals("DELETE")) {
/*      */       
/* 6737 */       String query = "INSERT INTO [dbo].[MilestoneCPRSD] ([release_id],[statusCode]) VALUES (" + 
/* 6738 */         selection.getSelectionID() + ",'" + statusCode + "')";
/* 6739 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 6740 */       connector.runQuery();
/* 6741 */       connector.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean IsDigitalEquivalent(Selection selection) {
/* 6751 */     boolean results = false;
/*      */     
/* 6753 */     Vector configCodes = Cache.getConfigCodes();
/* 6754 */     for (int x = 0; x < configCodes.size(); x++) {
/*      */       
/* 6756 */       LookupObject lo = (LookupObject)configCodes.get(x);
/* 6757 */       if (lo != null && lo.getAbbreviation().equals(selection.getConfigCode()) && lo.getIsDigitalEquivalent()) {
/*      */         
/* 6759 */         results = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 6765 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String IsDigitalEquivalentGetConfigs() {
/* 6774 */     results = "";
/*      */     
/* 6776 */     Vector configCodes = Cache.getConfigCodes();
/* 6777 */     for (int x = 0; x < configCodes.size(); x++) {
/*      */       
/* 6779 */       LookupObject lo = (LookupObject)configCodes.get(x);
/* 6780 */       if (lo != null && lo.getIsDigitalEquivalent())
/* 6781 */         results = String.valueOf(results) + lo.getAbbreviation() + ";"; 
/*      */     } 
/* 6783 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean IsPfmDraftOrFinal(int releaseId) {
/* 6792 */     boolean result = false;
/* 6793 */     String query = "SELECT isnull(print_flag,'') as print_flag FROM dbo.Pfm_Selection WHERE (release_id = " + releaseId + ")";
/* 6794 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 6795 */     connector.runQuery();
/*      */     
/* 6797 */     String printFlag = "";
/* 6798 */     if (connector.more())
/*      */     {
/*      */ 
/*      */       
/* 6802 */       result = true;
/*      */     }
/* 6804 */     connector.close();
/*      */     
/* 6806 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */