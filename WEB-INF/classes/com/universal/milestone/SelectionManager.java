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
/* 1226 */       String productionGroupCode = "";
/* 1227 */       if (connector.getFieldByName("Production_Group_Code") != null)
/* 1228 */         productionGroupCode = connector.getFieldByName("Production_Group_Code"); 
/* 1229 */       selection.setProductionGroupCode(productionGroupCode);
/*      */       
/* 1231 */       selection.setProjectID(connector.getField("project_no", ""));
/*      */       
/* 1233 */       String titleId = "";
/* 1234 */       if (connector.getFieldByName("title_id") != null)
/* 1235 */         titleId = connector.getFieldByName("title_id"); 
/* 1236 */       selection.setTitleID(titleId);
/*      */       
/* 1238 */       selection.setTitle(connector.getField("title", ""));
/* 1239 */       selection.setArtistFirstName(connector.getField("artist_first_name", ""));
/* 1240 */       selection.setArtistLastName(connector.getField("artist_last_name", ""));
/* 1241 */       selection.setArtist(connector.getField("artist", ""));
/* 1242 */       selection.setFlArtist(connector.getField("fl_artist", ""));
/* 1243 */       selection.setASide(connector.getField("side_a_title", ""));
/* 1244 */       selection.setBSide(connector.getField("side_b_title", ""));
/*      */       
/* 1246 */       selection.setProductCategory((ProductCategory)getLookupObject(connector.getField("product_line"), 
/* 1247 */             Cache.getProductCategories()));
/* 1248 */       selection.setReleaseType((ReleaseType)getLookupObject(connector.getField("release_type"), 
/* 1249 */             Cache.getReleaseTypes()));
/*      */       
/* 1251 */       selection.setSelectionConfig(getSelectionConfigObject(connector.getField("configuration"), 
/* 1252 */             Cache.getSelectionConfigs()));
/*      */       
/* 1254 */       selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), 
/* 1255 */             selection.getSelectionConfig()));
/*      */       
/* 1257 */       selection.setUpc(connector.getField("upc", ""));
/*      */       
/* 1259 */       String sellCodeString = connector.getFieldByName("price_code");
/* 1260 */       if (sellCodeString != null)
/*      */       {
/* 1262 */         selection.setPriceCode(getPriceCode(sellCodeString));
/*      */       }
/*      */       
/* 1265 */       selection.setSellCode(sellCodeString);
/*      */ 
/*      */       
/* 1268 */       String sellCodeStringDPC = connector.getFieldByName("digital_price_code");
/* 1269 */       if (sellCodeStringDPC != null)
/*      */       {
/* 1271 */         selection.setPriceCodeDPC(getPriceCode(sellCodeStringDPC));
/*      */       }
/* 1273 */       selection.setSellCodeDPC(sellCodeStringDPC);
/*      */ 
/*      */       
/* 1276 */       selection.setGenre((Genre)getLookupObject(connector.getField("genre"), 
/* 1277 */             Cache.getMusicTypes()));
/* 1278 */       selection.setFamily((Family)MilestoneHelper.getStructureObject(connector.getIntegerField("family_id")));
/* 1279 */       selection.setEnvironment((Environment)MilestoneHelper.getStructureObject(connector.getIntegerField("environment_id")));
/* 1280 */       selection.setCompany((Company)MilestoneHelper.getStructureObject(connector.getIntegerField("company_id")));
/* 1281 */       selection.setDivision((Division)MilestoneHelper.getStructureObject(connector.getIntegerField("division_id")));
/* 1282 */       selection.setLabel((Label)MilestoneHelper.getStructureObject(connector.getIntegerField("label_id")));
/*      */       
/* 1284 */       String streetDateString = connector.getFieldByName("street_date");
/* 1285 */       if (streetDateString != null) {
/* 1286 */         selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */       }
/* 1288 */       String internationalDateString = connector.getFieldByName("international_date");
/* 1289 */       if (internationalDateString != null) {
/* 1290 */         selection.setInternationalDate(MilestoneHelper.getDatabaseDate(internationalDateString));
/*      */       }
/* 1292 */       selection.setOtherContact(connector.getField("coordinator_contacts", ""));
/*      */       
/* 1294 */       if (UserManager.getInstance().getUser(connector.getIntegerField("contact_id")) != null) {
/* 1295 */         selection.setLabelContact(UserManager.getInstance().getUser(connector.getIntegerField("contact_id")));
/*      */       }
/* 1297 */       selection.setSelectionStatus((SelectionStatus)getLookupObject(connector.getField("status"), 
/* 1298 */             Cache.getSelectionStatusList()));
/* 1299 */       selection.setHoldSelection(connector.getBoolean("hold_indicator"));
/*      */       
/* 1301 */       selection.setHoldReason(connector.getField("hold_reason", ""));
/* 1302 */       selection.setComments(connector.getField("comments", ""));
/* 1303 */       selection.setSpecialPackaging(connector.getBoolean("special_pkg_indicator"));
/*      */ 
/*      */       
/* 1306 */       selection.setNumberOfUnits(connector.getIntegerField("units"));
/*      */       
/* 1308 */       selection.setPressAndDistribution(connector.getBoolean("pd_indicator"));
/*      */       
/* 1310 */       selection.setNoDigitalRelease(connector.getBoolean("no_digital_release"));
/*      */       
/* 1312 */       selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 1313 */       selection.setSelectionPackaging(connector.getField("packaging", ""));
/*      */       
/* 1315 */       String impactDateString = connector.getFieldByName("impact_date");
/* 1316 */       if (impactDateString != null) {
/* 1317 */         selection.setImpactDate(MilestoneHelper.getDatabaseDate(impactDateString));
/*      */       }
/* 1319 */       String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/* 1320 */       if (lastUpdateDateString != null) {
/* 1321 */         selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString));
/*      */       }
/* 1323 */       selection.setLastUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*      */       
/* 1325 */       long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck"), 16);
/*      */       
/* 1327 */       selection.setLastUpdatedCheck(lastUpdatedLong);
/*      */       
/* 1329 */       selection.setPrice(connector.getFloat("list_price"));
/*      */ 
/*      */       
/* 1332 */       String lastChangedOn = connector.getFieldByName("last_changed_on");
/* 1333 */       if (lastChangedOn != null) {
/* 1334 */         selection.setLastStreetUpdateDate(MilestoneHelper.getDatabaseDate(connector.getField("last_changed_on")));
/*      */       }
/*      */       
/* 1337 */       String originDateString = connector.getFieldByName("entered_on");
/* 1338 */       if (originDateString != null) {
/* 1339 */         selection.setOriginDate(MilestoneHelper.getDatabaseDate(originDateString));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1348 */       selection.setTemplateId(connector.getInt("templateId", -1));
/*      */       
/* 1350 */       selection.setParentalGuidance(connector.getBoolean("parental_indicator"));
/* 1351 */       selection.setSelectionTerritory(connector.getField("territory", ""));
/*      */       
/* 1353 */       String lastSchedUpdateDateString = connector.getFieldByName("last_sched_updated_on");
/* 1354 */       if (lastSchedUpdateDateString != null)
/* 1355 */         selection.setLastSchedUpdateDate(MilestoneHelper.getDatabaseDate(lastSchedUpdateDateString)); 
/* 1356 */       selection.setLastSchedUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_sched_updated_by")));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1363 */       String digitalRlsString = connector.getFieldByName("digital_rls_date");
/* 1364 */       if (digitalRlsString != null) {
/* 1365 */         selection.setDigitalRlsDateString(digitalRlsString);
/* 1366 */         selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(digitalRlsString));
/*      */       } 
/*      */       
/* 1369 */       selection.setInternationalFlag(connector.getBoolean("international_flag"));
/*      */       
/* 1371 */       selection.setOperCompany(connector.getField("oper_company", ""));
/*      */       
/* 1373 */       selection.setSuperLabel(connector.getField("super_label", ""));
/*      */       
/* 1375 */       selection.setSubLabel(connector.getField("sub_label", ""));
/*      */       
/* 1377 */       selection.setConfigCode(connector.getField("config_code", ""));
/*      */       
/* 1379 */       selection.setSoundScanGrp(connector.getField("soundscan_grp", ""));
/*      */ 
/*      */ 
/*      */       
/* 1383 */       selection.setImprint(connector.getField("imprint", ""));
/* 1384 */       selection.setNewBundleFlag(connector.getBoolean("new_bundle_flag"));
/* 1385 */       selection.setGridNumber(connector.getField("grid_number", ""));
/* 1386 */       selection.setSpecialInstructions(connector.getField("special_instructions", ""));
/* 1387 */       selection.setIsDigital(connector.getBoolean("digital_flag"));
/* 1388 */       selection.setPriority(connector.getBoolean("priority_flag"));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1393 */       selection.setArchimedesId(connector.getInt("archimedes_id", -1));
/* 1394 */       selection.setReleaseFamilyId(connector.getInt("release_family_id", -1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1409 */       String impactSql = "sp_get_Schedule_ImpactDates " + id;
/*      */       
/* 1411 */       JdbcConnector impactConnector = MilestoneHelper.getConnector(impactSql);
/* 1412 */       impactConnector.runQuery();
/*      */       
/* 1414 */       Vector impactDates = new Vector();
/*      */       
/* 1416 */       while (impactConnector.more()) {
/*      */         
/* 1418 */         ImpactDate impact = new ImpactDate();
/*      */         
/* 1420 */         impact.setImpactDateID(impactConnector.getInt("impactDate_Id", -1));
/* 1421 */         impact.setSelectionID(impactConnector.getInt("selection_Id", -1));
/* 1422 */         impact.setFormat(impactConnector.getField("format", ""));
/* 1423 */         impact.setFormatDescription(impactConnector.getField("description", ""));
/*      */         
/* 1425 */         String impactString = impactConnector.getFieldByName("impactDate");
/* 1426 */         if (impactString != null) {
/* 1427 */           impact.setImpactDate(MilestoneHelper.getDatabaseDate(impactString));
/*      */         }
/* 1429 */         impact.setTbi(impactConnector.getBoolean("tbi"));
/*      */         
/* 1431 */         impactDates.add(impact);
/*      */         
/* 1433 */         impactConnector.next();
/*      */       } 
/*      */       
/* 1436 */       impactConnector.close();
/*      */       
/* 1438 */       selection.setImpactDates(impactDates);
/*      */ 
/*      */ 
/*      */       
/* 1442 */       selection.setMultSelections(getMultSelections(id));
/*      */ 
/*      */       
/* 1445 */       selection.setMultOtherContacts(getMultOtherContacts(id));
/*      */ 
/*      */       
/* 1448 */       selection.setFullSelection(true);
/*      */ 
/*      */ 
/*      */       
/* 1452 */       JdbcConnector archieConnector = MilestoneHelper.getConnector("sp_get_archie_last_update_date " + id);
/* 1453 */       archieConnector.runQuery();
/*      */       
/* 1455 */       if (archieConnector.more()) {
/*      */         
/* 1457 */         String archieString = archieConnector.getFieldByName("audit_date");
/*      */ 
/*      */         
/* 1460 */         if (archieString != null) {
/* 1461 */           selection.setArchieDate(MilestoneHelper.getDatabaseDate(archieString));
/*      */         }
/*      */       } 
/* 1464 */       archieConnector.close();
/*      */ 
/*      */       
/* 1467 */       JdbcConnector autoCloseConnector = MilestoneHelper.getConnector("sp_get_auto_close_date " + id);
/* 1468 */       autoCloseConnector.runQuery();
/* 1469 */       if (autoCloseConnector.more()) {
/*      */         
/* 1471 */         String autoCloseStr = autoCloseConnector.getFieldByName("auto_closed_date");
/* 1472 */         if (autoCloseStr != null)
/* 1473 */           selection.setAutoCloseDate(MilestoneHelper.getDatabaseDate(autoCloseStr)); 
/*      */       } 
/* 1475 */       autoCloseConnector.close();
/*      */ 
/*      */ 
/*      */       
/* 1479 */       JdbcConnector lastLegacyUpdateConnector = MilestoneHelper.getConnector("sp_get_last_legacy_update_date " + id);
/* 1480 */       lastLegacyUpdateConnector.runQuery();
/* 1481 */       if (lastLegacyUpdateConnector.more()) {
/*      */         
/* 1483 */         String lastLegacyUpdateStr = lastLegacyUpdateConnector.getFieldByName("legacy_date");
/* 1484 */         if (lastLegacyUpdateStr != null)
/* 1485 */           selection.setLastLegacyUpdateDate(MilestoneHelper.getDatabaseDate(lastLegacyUpdateStr)); 
/*      */       } 
/* 1487 */       lastLegacyUpdateConnector.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1492 */     connector.close();
/*      */     
/* 1494 */     return selection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSelectionManufacturingSubDetail(Selection selection) {
/* 1503 */     boolean newFlag = true;
/*      */     
/* 1505 */     if (selection != null) {
/*      */       
/* 1507 */       int selectionId = selection.getSelectionID();
/*      */       
/* 1509 */       String manufacturingQuery = "SELECT * FROM vi_Release_Subdetail WHERE release_id = " + 
/*      */         
/* 1511 */         selectionId + 
/* 1512 */         ";";
/*      */       
/* 1514 */       JdbcConnector connector = MilestoneHelper.getConnector(manufacturingQuery);
/* 1515 */       connector.runQuery();
/*      */ 
/*      */ 
/*      */       
/* 1519 */       if (connector.more()) {
/*      */         
/* 1521 */         newFlag = false;
/* 1522 */         User umlContact = UserManager.getInstance().getUser(connector.getIntegerField("uml_id"));
/* 1523 */         selection.setUmlContact(umlContact);
/* 1524 */         selection.setPlant(getLookupObject(connector.getField("plant"), Cache.getVendors()));
/* 1525 */         selection.setDistribution(getLookupObject(connector.getField("distribution"), Cache.getDistributionCodes()));
/* 1526 */         selection.setPoQuantity(connector.getIntegerField("order_qty"));
/*      */         
/* 1528 */         selection.setCompletedQuantity(connector.getIntegerField("complete_qty"));
/* 1529 */         selection.setManufacturingComments(connector.getField("order_comments", ""));
/*      */         
/* 1531 */         String lastUpdateDateString = connector.getFieldByName("last_updated_on");
/* 1532 */         if (lastUpdateDateString != null) {
/* 1533 */           selection.setLastMfgUpdateDate(MilestoneHelper.getDatabaseDate(lastUpdateDateString));
/*      */         }
/* 1535 */         selection.setLastMfgUpdatingUser(UserManager.getInstance().getUser(connector.getIntegerField("last_updated_by")));
/*      */         
/* 1537 */         long lastUpdatedLong = Long.parseLong(connector.getField("last_updated_ck", "0"), 16);
/* 1538 */         selection.setLastMfgUpdatedCheck(lastUpdatedLong);
/*      */       } 
/*      */ 
/*      */       
/* 1542 */       connector.close();
/*      */ 
/*      */       
/* 1545 */       String plantSql = "select * from Manufacturing_Plants where release_id = " + selection.getSelectionID();
/* 1546 */       JdbcConnector plantConnector = MilestoneHelper.getConnector(plantSql);
/* 1547 */       plantConnector.runQuery();
/*      */       
/* 1549 */       Vector plants = new Vector();
/*      */       
/* 1551 */       while (plantConnector.more()) {
/*      */         
/* 1553 */         Plant plant = new Plant();
/*      */         
/* 1555 */         plant.setPlantID(plantConnector.getInt("plantId", -1));
/* 1556 */         plant.setSelectionID(plantConnector.getField("selection_Id"));
/* 1557 */         plant.setReleaseID(plantConnector.getInt("release_Id", -1));
/* 1558 */         plant.setPlant(getLookupObject(plantConnector.getField("plant"), Cache.getVendors()));
/* 1559 */         plant.setOrderQty(plantConnector.getInt("order_qty", -1));
/* 1560 */         plant.setCompletedQty(plantConnector.getInt("complete_qty", -1));
/*      */         
/* 1562 */         plants.add(plant);
/*      */         
/* 1564 */         plantConnector.next();
/*      */       } 
/*      */       
/* 1567 */       plantConnector.close();
/*      */       
/* 1569 */       selection.setManufacturingPlants(plants);
/*      */     } 
/*      */     
/* 1572 */     return newFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PriceCode getPriceCode(String sellCode) {
/* 1580 */     PriceCode priceCode = null;
/*      */     
/* 1582 */     Vector priceCodes = Cache.getPriceCodes();
/* 1583 */     for (int i = 0; i < priceCodes.size(); i++) {
/*      */       
/* 1585 */       PriceCode pc = (PriceCode)priceCodes.get(i);
/* 1586 */       if (pc.getSellCode().equalsIgnoreCase(sellCode)) {
/*      */         
/* 1588 */         priceCode = pc;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1636 */     return priceCode;
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
/* 1648 */     System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + selection.getCompanyId());
/* 1649 */     System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + selection.getEnvironmentId());
/*      */     
/* 1651 */     int holdSelection = 0;
/* 1652 */     int specialPackaging = 0;
/* 1653 */     int pressAndDistribution = 0;
/* 1654 */     int parentalGuidance = 0;
/*      */     
/* 1656 */     int internationalFlag = 0;
/* 1657 */     int noDigitalRelease = 0;
/*      */ 
/*      */     
/* 1660 */     if (selection.getNoDigitalRelease()) {
/* 1661 */       noDigitalRelease = 1;
/*      */     }
/* 1663 */     if (selection.getPressAndDistribution()) {
/* 1664 */       pressAndDistribution = 1;
/*      */     }
/* 1666 */     if (selection.getSpecialPackaging()) {
/* 1667 */       specialPackaging = 1;
/*      */     }
/* 1669 */     if (selection.getHoldSelection()) {
/* 1670 */       holdSelection = 1;
/*      */     }
/* 1672 */     if (selection.getParentalGuidance()) {
/* 1673 */       parentalGuidance = 1;
/*      */     }
/*      */     
/* 1676 */     if (selection.getInternationalFlag()) {
/* 1677 */       internationalFlag = 1;
/*      */     }
/* 1679 */     String prefix = "";
/* 1680 */     if (selection.getPrefixID() != null) {
/* 1681 */       prefix = selection.getPrefixID().getAbbreviation();
/*      */     }
/* 1683 */     long timestamp = selection.getLastUpdatedCheck();
/* 1684 */     String familyID = "-1";
/* 1685 */     String companyID = "-1";
/* 1686 */     String divisionID = "-1";
/* 1687 */     String labelID = "-1";
/* 1688 */     String environmentId = "-1";
/* 1689 */     String labelContactId = "-1";
/* 1690 */     String territory = selection.getSelectionTerritory();
/*      */ 
/*      */     
/* 1693 */     String imprint = selection.getImprint();
/* 1694 */     boolean isDigital = selection.getIsDigital();
/* 1695 */     boolean new_bundle_flag = selection.getNewBundleFlag();
/* 1696 */     String grid_number = selection.getGridNumber();
/* 1697 */     String special_instructions = selection.getSpecialInstructions();
/*      */     
/* 1699 */     int isDigitalFlag = 0;
/* 1700 */     if (selection.getIsDigital()) {
/* 1701 */       isDigitalFlag = 1;
/*      */     }
/* 1703 */     int new_bundle = 0;
/* 1704 */     if (selection.getNewBundleFlag()) {
/* 1705 */       new_bundle = 1;
/*      */     }
/* 1707 */     int priority = 0;
/* 1708 */     if (selection.getPriority()) {
/* 1709 */       priority = 1;
/*      */     }
/*      */ 
/*      */     
/* 1713 */     System.out.println(">>>>>>>>>>>>>>>>>>>>>>> CompanyId: " + selection.getCompany().getStructureID());
/* 1714 */     System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Company: " + selection.getCompany().getName());
/* 1715 */     System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + selection.getEnvironment().getStructureID());
/* 1716 */     System.out.println(">>>>>>>>>>>>>>>>>>>>>>> enviroment: " + selection.getEnvironment().getName());
/*      */     
/* 1718 */     if (selection.getEnvironment() != null) {
/* 1719 */       environmentId = String.valueOf(selection.getEnvironment().getStructureID());
/*      */     }
/*      */     
/* 1722 */     if (selection.getCompany() != null) {
/* 1723 */       companyID = String.valueOf(selection.getCompany().getStructureID());
/*      */     }
/* 1725 */     if (selection.getDivision() != null) {
/* 1726 */       divisionID = String.valueOf(selection.getDivision().getStructureID());
/*      */     }
/* 1728 */     if (selection.getLabel() != null) {
/* 1729 */       labelID = String.valueOf(selection.getLabel().getStructureID());
/*      */     }
/* 1731 */     if (selection.getCompany() != null && selection.getCompany().getParentEnvironment() != null) {
/*      */       
/* 1733 */       familyID = String.valueOf(selection.getCompany().getParentEnvironment().getParentID());
/*      */ 
/*      */ 
/*      */       
/* 1737 */       selection.setFamily((Family)MilestoneHelper.getStructureObject(selection.getCompany().getParentEnvironment().getParentID()));
/*      */     } 
/*      */ 
/*      */     
/* 1741 */     if (selection.getLabelContact() != null) {
/* 1742 */       labelContactId = String.valueOf(selection.getLabelContact().getUserId());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1747 */     boolean cprsIsNew = (selection.getSelectionID() < 0);
/*      */     
/* 1749 */     String query = "sp_sav_Release_Header " + 
/* 1750 */       selection.getSelectionID() + "," + 
/* 1751 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getProjectID()) + "'," + 
/* 1752 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getTitleID()) + "'," + 
/* 1753 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getTitle()) + "'," + 
/* 1754 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getArtist()) + "'," + 
/* 1755 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getArtistLastName()) + "'," + 
/* 1756 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getArtistFirstName()) + "'," + 
/* 1757 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getASide()) + "'," + 
/* 1758 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getBSide()) + "'," + 
/* 1759 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionNo()) + "'," + 
/* 1760 */       "'" + "version number" + "'," + 
/* 1761 */       "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getProductCategory())) + "'," + 
/* 1762 */       "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getReleaseType())) + "'," + 
/* 1763 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionConfig().getSelectionConfigurationAbbreviation()) + "'," + 
/* 1764 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionSubConfig().getSelectionSubConfigurationAbbreviation()) + "'," + 
/*      */       
/* 1766 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(selection.getUpc(), "UPC", selection.getIsDigital(), true)) + "'," + 
/* 1767 */       "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getGenre())) + "'," + 
/* 1768 */       familyID + "," + 
/* 1769 */       companyID + "," + 
/* 1770 */       divisionID + "," + 
/* 1771 */       labelID + "," + 
/* 1772 */       "'" + MilestoneHelper.getFormatedDate(selection.getStreetDate()) + "'," + 
/* 1773 */       "'" + MilestoneHelper.getFormatedDate(selection.getInternationalDate()) + "'," + 
/* 1774 */       "0" + "," + 
/* 1775 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getOtherContact()) + "'," + 
/* 1776 */       labelContactId + "," + 
/* 1777 */       "'" + MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(selection.getSelectionStatus())) + "'," + 
/* 1778 */       holdSelection + "," + 
/* 1779 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getHoldReason()) + "'," + 
/* 1780 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionComments()) + "'," + 
/* 1781 */       "'" + "group" + "'," + 
/* 1782 */       specialPackaging + "," + 
/* 1783 */       selection.getPrice() + "," + 
/* 1784 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSellCode()) + "'," + 
/* 1785 */       selection.getNumberOfUnits() + "," + 
/* 1786 */       pressAndDistribution + "," + 
/* 1787 */       "'" + MilestoneHelper.escapeSingleQuotes(prefix) + "'," + 
/* 1788 */       "'" + "reference" + "'," + 
/* 1789 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSelectionPackaging()) + "'," + 
/* 1790 */       "'" + "packaging comments" + "'," + 
/* 1791 */       "'" + MilestoneHelper.getFormatedDate(selection.getImpactDate()) + "'," + 
/* 1792 */       "'" + MilestoneHelper.getFormatedDate(selection.getLastStreetUpdateDate()) + "'," + 
/*      */       
/* 1794 */       updatingUser.getUserId() + "," + 
/* 1795 */       selection.getTemplateId() + "," + 
/* 1796 */       parentalGuidance + ",'" + 
/* 1797 */       territory + "'," + 
/* 1798 */       timestamp + "," + 
/*      */       
/* 1800 */       "'" + MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()) + "'," + 
/* 1801 */       internationalFlag + "," + 
/* 1802 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getOperCompany()) + "'," + 
/* 1803 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSuperLabel()) + "'," + 
/* 1804 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getSubLabel()) + "'," + 
/* 1805 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getConfigCode()) + "'," + 
/*      */       
/* 1807 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(selection.getSoundScanGrp(), "SSG", selection.getIsDigital(), true)) + "'," + 
/* 1808 */       environmentId + "," + 
/* 1809 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getImprint()) + "'," + 
/* 1810 */       new_bundle + "," + 
/* 1811 */       "'" + MilestoneHelper.escapeSingleQuotes(grid_number) + "'," + 
/* 1812 */       "'" + MilestoneHelper.escapeSingleQuotes(special_instructions) + "'," + 
/* 1813 */       isDigitalFlag + "," + 
/* 1814 */       "'" + MilestoneHelper.getFormatedDate(selection.getDigitalRlsDate()) + "'," + 
/*      */       
/* 1816 */       getArchimedesIdFromCache(Integer.parseInt(labelID)) + "," + 
/* 1817 */       selection.getReleaseFamilyId() + "," + 
/* 1818 */       priority + "," + noDigitalRelease + ",'" + selection.getSellCodeDPC() + "' ";
/*      */ 
/*      */     
/* 1821 */     log.log("Query >>>>>>>>>>>>>> : " + query);
/*      */ 
/*      */     
/* 1824 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 1825 */     connector.runQuery();
/*      */     
/* 1827 */     int newId = connector.getIntegerField("ReturnId");
/* 1828 */     log.debug("Selection id from db " + newId);
/* 1829 */     log.debug("Selection id from db " + newId);
/*      */ 
/*      */     
/* 1832 */     if (selection.getSelectionID() < 0) {
/* 1833 */       selection.setSelectionID(newId);
/*      */     }
/* 1835 */     connector.close();
/*      */ 
/*      */     
/* 1838 */     log.debug("Flushing selection audits.");
/* 1839 */     selection.flushAudits(updatingUser.getUserId());
/*      */     
/* 1841 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
/*      */       
/* 1843 */       selection.getSelectionID() + 
/* 1844 */       ";";
/*      */     
/* 1846 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 1847 */     connectorTimestamp.runQuery();
/* 1848 */     if (connectorTimestamp.more()) {
/*      */       
/* 1850 */       selection.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 1851 */       selection.setLastUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*      */     } 
/* 1853 */     connectorTimestamp.close();
/*      */ 
/*      */     
/* 1856 */     saveImpactDates(selection, updatingUser);
/*      */ 
/*      */     
/* 1859 */     saveMultSelections(selection, updatingUser);
/*      */ 
/*      */     
/* 1862 */     saveMultOtherContacts(selection, updatingUser);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1871 */     DcGDRSResults dcGDRSResults = SelectionHandler.GDRSProductStatusGet(selection, selection.getCompany().getParentEnvironment().getStructureID());
/* 1872 */     boolean IsGDRSactive = (!dcGDRSResults.getStatus().equals("") && !dcGDRSResults.getStatus().equals("DELETE"));
/* 1873 */     if (IsPfmDraftOrFinal(selection.getSelectionID()))
/*      */     {
/*      */       
/* 1876 */       if (noDigitalRelease == 0) {
/*      */ 
/*      */         
/* 1879 */         if (!dcGDRSResults.getForceNoDigitalRelease().booleanValue())
/* 1880 */           GDRS_QueueAddReleaseId(selection, "CREATE_EDIT"); 
/* 1881 */       } else if (IsGDRSactive) {
/* 1882 */         GDRS_QueueAddReleaseId(selection, "DELETE");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1891 */       if (!selection.getProjectID().equals("0000-00000"))
/*      */       {
/* 1893 */         projectSearchSvcClient.MilestoneSnapshotProjectInsert(selection.getProjectID());
/*      */       }
/* 1895 */     } catch (Exception e) {
/* 1896 */       System.out.println(e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/* 1900 */     return selection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveImpactDates(Selection selection, User updatingUser) {
/* 1910 */     if (selection != null && selection.getImpactDates() != null) {
/*      */       
/* 1912 */       Vector impactDates = selection.getImpactDates();
/*      */       
/* 1914 */       Vector addImpactDates = new Vector();
/* 1915 */       Vector deleteImpactDates = new Vector();
/*      */       
/* 1917 */       String impactQuery = "select * from vi_ImpactDates where selection_id = " + selection.getSelectionID();
/*      */ 
/*      */ 
/*      */       
/* 1921 */       boolean delete = true;
/*      */       
/* 1923 */       for (int a = 0; a < impactDates.size(); a++) {
/*      */         
/* 1925 */         ImpactDate impactDate = (ImpactDate)impactDates.get(a);
/*      */ 
/*      */ 
/*      */         
/* 1929 */         JdbcConnector connectorImpactCount = MilestoneHelper.getConnector(impactQuery);
/* 1930 */         connectorImpactCount.runQuery();
/*      */         
/* 1932 */         if (connectorImpactCount.more()) {
/*      */           
/* 1934 */           while (connectorImpactCount.more())
/*      */           {
/*      */ 
/*      */             
/* 1938 */             if (impactDate.getImpactDateID() == -1 || impactDate.getImpactDateID() == connectorImpactCount.getInt("impactDate_Id", -2)) {
/*      */               
/* 1940 */               addImpactDates.add(impactDate);
/*      */               break;
/*      */             } 
/* 1943 */             connectorImpactCount.next();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1948 */           addImpactDates.add(impactDate);
/*      */         } 
/*      */         
/* 1951 */         connectorImpactCount.close();
/*      */       } 
/*      */       
/* 1954 */       JdbcConnector connectorImpactDelete = MilestoneHelper.getConnector(impactQuery);
/* 1955 */       connectorImpactDelete.runQuery();
/*      */       
/* 1957 */       while (connectorImpactDelete.more()) {
/*      */         
/* 1959 */         delete = true;
/*      */         
/* 1961 */         for (int b = 0; b < impactDates.size(); b++) {
/*      */           
/* 1963 */           ImpactDate impactDateDelete = (ImpactDate)impactDates.get(b);
/*      */           
/* 1965 */           if (connectorImpactDelete.getInt("impactDate_Id", -2) == impactDateDelete.getImpactDateID())
/*      */           {
/* 1967 */             delete = false;
/*      */           }
/*      */         } 
/*      */         
/* 1971 */         if (delete) {
/*      */           
/* 1973 */           ImpactDate delImpactDate = new ImpactDate();
/* 1974 */           delImpactDate.setImpactDateID(connectorImpactDelete.getInt("impactDate_Id", -1));
/* 1975 */           deleteImpactDates.add(delImpactDate);
/*      */         } 
/*      */         
/* 1978 */         connectorImpactDelete.next();
/*      */       } 
/* 1980 */       connectorImpactDelete.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1986 */       for (int i = 0; i < addImpactDates.size(); i++) {
/*      */         
/* 1988 */         ImpactDate impact = (ImpactDate)addImpactDates.get(i);
/*      */         
/* 1990 */         int tbi = impact.getTbi() ? 1 : 0;
/*      */         
/* 1992 */         int selId = impact.getSelectionID();
/* 1993 */         int impactDateId = impact.getImpactDateID();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1998 */         if (selId < 0) {
/* 1999 */           selId = selection.getSelectionID();
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
/* 2010 */         String impactSql = "sp_sav_ImpactDate " + impact.getImpactDateID() + "," + 
/* 2011 */           selId + "," + 
/* 2012 */           "'" + impact.getFormat() + "'," + 
/* 2013 */           "'" + MilestoneHelper.getFormatedDate(impact.getImpactDate()) + "'," + 
/* 2014 */           tbi;
/*      */ 
/*      */ 
/*      */         
/* 2018 */         JdbcConnector connectorAddImpact = MilestoneHelper.getConnector(impactSql);
/* 2019 */         connectorAddImpact.runQuery();
/* 2020 */         connectorAddImpact.close();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2025 */       JdbcConnector connectorDeleteImpact = null;
/* 2026 */       for (int k = 0; k < deleteImpactDates.size(); k++) {
/*      */         
/* 2028 */         ImpactDate impact = (ImpactDate)deleteImpactDates.get(k);
/*      */         
/* 2030 */         String impactDeleteSql = "sp_del_ImpactDate " + impact.getImpactDateID();
/*      */ 
/*      */ 
/*      */         
/* 2034 */         connectorDeleteImpact = MilestoneHelper.getConnector(impactDeleteSql);
/* 2035 */         connectorDeleteImpact.runQuery();
/* 2036 */         connectorDeleteImpact.close();
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
/* 2052 */     if (selection != null && selection.getManufacturingPlants() != null) {
/*      */       
/* 2054 */       Vector plants = selection.getManufacturingPlants();
/*      */       
/* 2056 */       Vector addPlants = new Vector();
/* 2057 */       Vector deletePlants = new Vector();
/*      */       
/* 2059 */       String plantQuery = "select * from Manufacturing_Plants where release_id = " + selection.getSelectionID();
/*      */ 
/*      */ 
/*      */       
/* 2063 */       boolean delete = true;
/*      */       
/* 2065 */       for (int a = 0; a < plants.size(); a++) {
/*      */         
/* 2067 */         Plant plant = (Plant)plants.get(a);
/*      */         
/* 2069 */         JdbcConnector connectorPlantCount = MilestoneHelper.getConnector(plantQuery);
/* 2070 */         connectorPlantCount.runQuery();
/*      */         
/* 2072 */         if (connectorPlantCount.more()) {
/*      */           
/* 2074 */           while (connectorPlantCount.more())
/*      */           {
/*      */ 
/*      */             
/* 2078 */             if (plant.getPlantID() == -1 || plant.getPlantID() == connectorPlantCount.getInt("plantId", -2)) {
/*      */               
/* 2080 */               addPlants.add(plant);
/*      */               break;
/*      */             } 
/* 2083 */             connectorPlantCount.next();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2088 */           addPlants.add(plant);
/*      */         } 
/*      */         
/* 2091 */         connectorPlantCount.close();
/*      */       } 
/*      */       
/* 2094 */       JdbcConnector connectorPlantDelete = MilestoneHelper.getConnector(plantQuery);
/* 2095 */       connectorPlantDelete.runQuery();
/*      */       
/* 2097 */       while (connectorPlantDelete.more()) {
/*      */         
/* 2099 */         delete = true;
/*      */         
/* 2101 */         for (int b = 0; b < plants.size(); b++) {
/*      */           
/* 2103 */           Plant plantDelete = (Plant)plants.get(b);
/*      */           
/* 2105 */           if (connectorPlantDelete.getInt("plantId", -2) == plantDelete.getPlantID())
/*      */           {
/* 2107 */             delete = false;
/*      */           }
/*      */         } 
/*      */         
/* 2111 */         if (delete) {
/*      */           
/* 2113 */           Plant delPlant = new Plant();
/* 2114 */           delPlant.setPlantID(connectorPlantDelete.getInt("plantId", -1));
/* 2115 */           deletePlants.add(delPlant);
/*      */         } 
/*      */         
/* 2118 */         connectorPlantDelete.next();
/*      */       } 
/* 2120 */       connectorPlantDelete.close();
/*      */ 
/*      */       
/* 2123 */       for (int i = 0; i < addPlants.size(); i++) {
/*      */         
/* 2125 */         Plant plant = (Plant)addPlants.get(i);
/*      */         
/* 2127 */         String plantId = "-1";
/* 2128 */         if (plant.getPlant() != null) {
/* 2129 */           plantId = plant.getPlant().getAbbreviation();
/*      */         }
/*      */         
/* 2132 */         String plantSql = "sp_sav_Plant " + plant.getPlantID() + ",'" + 
/* 2133 */           plant.getSelectionID() + "'," + 
/* 2134 */           plantId + "," + 
/* 2135 */           plant.getOrderQty() + "," + 
/* 2136 */           plant.getCompletedQty() + "," + 
/* 2137 */           plant.getReleaseID();
/*      */ 
/*      */ 
/*      */         
/* 2141 */         JdbcConnector connectorAddPlant = MilestoneHelper.getConnector(plantSql);
/* 2142 */         connectorAddPlant.runQuery();
/* 2143 */         connectorAddPlant.close();
/*      */       } 
/*      */ 
/*      */       
/* 2147 */       JdbcConnector connectorDeletePlant = null;
/* 2148 */       for (int k = 0; k < deletePlants.size(); k++) {
/*      */         
/* 2150 */         Plant plant = (Plant)deletePlants.get(k);
/*      */         
/* 2152 */         String plantDeleteSql = "sp_del_Plant " + plant.getPlantID();
/*      */ 
/*      */ 
/*      */         
/* 2156 */         connectorDeletePlant = MilestoneHelper.getConnector(plantDeleteSql);
/* 2157 */         connectorDeletePlant.runQuery();
/* 2158 */         connectorDeletePlant.close();
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
/* 2172 */     String query = "UPDATE Release_Header SET templateId = " + selection.getTemplateId() + 
/* 2173 */       " WHERE release_id = " + selection.getSelectionID();
/*      */     
/* 2175 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2176 */     connector.runQuery();
/* 2177 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateStatusToClose(Selection selection) {
/* 2186 */     String query = "UPDATE Release_Header SET status = 'CLOSED' WHERE release_id = " + 
/* 2187 */       selection.getSelectionID();
/*      */     
/* 2189 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2190 */     connector.runQuery();
/* 2191 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateComment(Selection selection) {
/* 2201 */     String query = "UPDATE Release_Header SET comments = '" + 
/* 2202 */       MilestoneHelper.escapeSingleQuotes(selection.getSelectionComments()) + "' " + 
/* 2203 */       " WHERE release_id = " + selection.getSelectionID();
/*      */     
/* 2205 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2206 */     connector.runQuery();
/* 2207 */     connector.close();
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
/* 2221 */     if (selection == null)
/* 2222 */       return ""; 
/* 2223 */     String query = "sp_get_Schedule_Template_Header " + selection.getTemplateId();
/*      */     
/* 2225 */     String templateName = "";
/*      */     
/* 2227 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2228 */     connector.runQuery();
/*      */ 
/*      */ 
/*      */     
/* 2232 */     if (connector.more()) {
/* 2233 */       templateName = connector.getField("name", "");
/*      */     }
/* 2235 */     connector.close();
/*      */     
/* 2237 */     return templateName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Selection saveManufacturingSelection(Selection selection, User updatingUser, boolean newFlag) {
/* 2245 */     long timestamp = selection.getLastMfgUpdatedCheck();
/*      */     
/* 2247 */     String spName = "sp_upd_Release_Subdetail";
/*      */ 
/*      */     
/* 2250 */     if (newFlag) {
/* 2251 */       spName = "sp_ins_Release_Subdetail";
/*      */     }
/* 2253 */     int umlContactId = 0;
/* 2254 */     if (selection.getUmlContact() != null) {
/* 2255 */       umlContactId = selection.getUmlContact().getUserId();
/*      */     }
/* 2257 */     String plantId = "";
/* 2258 */     if (selection.getPlant() != null) {
/* 2259 */       plantId = selection.getPlant().getAbbreviation();
/*      */     }
/* 2261 */     String distributionId = "";
/* 2262 */     if (selection.getDistribution() != null) {
/* 2263 */       distributionId = selection.getDistribution().getAbbreviation();
/*      */     }
/* 2265 */     String query = String.valueOf(spName) + " " + 
/* 2266 */       selection.getSelectionID() + "," + 
/* 2267 */       umlContactId + "," + 
/* 2268 */       "'" + MilestoneHelper.escapeSingleQuotes(plantId) + "'," + 
/* 2269 */       "'" + MilestoneHelper.escapeSingleQuotes(distributionId) + "'," + 
/* 2270 */       selection.getPoQuantity() + "," + 
/* 2271 */       selection.getNumberOfUnits() + "," + 
/* 2272 */       selection.getCompletedQuantity() + "," + 
/* 2273 */       "'" + MilestoneHelper.escapeSingleQuotes(selection.getManufacturingComments()) + "'," + 
/* 2274 */       updatingUser.getUserId();
/*      */     
/* 2276 */     if (!newFlag) {
/* 2277 */       query = String.valueOf(query) + "," + timestamp;
/*      */     }
/* 2279 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2280 */     connector.runQuery();
/* 2281 */     connector.close();
/*      */     
/* 2283 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_subdetail WHERE release_id = " + 
/*      */       
/* 2285 */       selection.getSelectionID() + ";";
/*      */     
/* 2287 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 2288 */     connectorTimestamp.runQuery();
/* 2289 */     if (connectorTimestamp.more()) {
/*      */       
/* 2291 */       selection.setLastMfgUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 2292 */       selection.setLastMfgUpdateDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/*      */     } 
/* 2294 */     connectorTimestamp.close();
/*      */ 
/*      */     
/* 2297 */     savePlants(selection, updatingUser);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2305 */     return selection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean deleteSelection(Selection selection, User updatingUser) {
/* 2315 */     boolean isDeletable = true;
/* 2316 */     int selectionId = selection.getSelectionID();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2321 */     if (selection.getSchedule() == null) {
/* 2322 */       selection.setSchedule(ScheduleManager.getInstance().getSchedule(selectionId));
/*      */     }
/*      */     
/*      */     try {
/* 2326 */       if (selection.getSchedule().getTasks().size() > 0) {
/* 2327 */         isDeletable = false;
/*      */       }
/* 2329 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2334 */     if (isDeletable) {
/*      */       
/* 2336 */       String query = "sp_del_Releases " + 
/* 2337 */         selectionId;
/*      */       
/* 2339 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2340 */       connector.runQuery();
/* 2341 */       connector.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2347 */       GDRS_QueueAddReleaseId(selection, "DELETE");
/*      */     } 
/*      */     
/* 2350 */     return isDeletable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Pfm savePfm(Pfm pfm, User updatingUser, boolean isDigital) {
/* 2360 */     long timestamp = pfm.getLastUpdatedCk();
/*      */ 
/*      */ 
/*      */     
/* 2364 */     int userID = -1;
/* 2365 */     if (updatingUser != null) {
/* 2366 */       userID = updatingUser.getUserId();
/*      */     }
/* 2368 */     String query = "sp_upd_PFM_Selection " + 
/* 2369 */       pfm.getReleaseId() + ",'" + 
/* 2370 */       MilestoneHelper.escapeSingleQuotes(pfm.getReleaseType()) + "','" + 
/* 2371 */       MilestoneHelper.escapeSingleQuotes(pfm.getMode()) + "','" + 
/* 2372 */       MilestoneHelper.escapeSingleQuotes(pfm.getPrintOption()) + "','" + 
/* 2373 */       MilestoneHelper.escapeSingleQuotes(pfm.getPreparedBy()) + "','" + 
/* 2374 */       MilestoneHelper.escapeSingleQuotes(pfm.getEmail()) + "','" + 
/* 2375 */       MilestoneHelper.escapeSingleQuotes(pfm.getPhone()) + "','" + 
/* 2376 */       MilestoneHelper.escapeSingleQuotes(pfm.getFaxNumber()) + "','" + 
/* 2377 */       MilestoneHelper.escapeSingleQuotes(pfm.getComments()) + "','" + 
/* 2378 */       MilestoneHelper.escapeSingleQuotes(pfm.getOperatingCompany()) + "','" + 
/* 2379 */       MilestoneHelper.escapeSingleQuotes(pfm.getProductNumber()) + "','" + 
/* 2380 */       MilestoneHelper.escapeSingleQuotes(pfm.getConfigCode()) + "','" + 
/* 2381 */       MilestoneHelper.escapeSingleQuotes(pfm.getModifier()) + "','" + 
/* 2382 */       MilestoneHelper.escapeSingleQuotes(pfm.getTitle()) + "','" + 
/* 2383 */       MilestoneHelper.escapeSingleQuotes(pfm.getArtist()) + "','" + 
/* 2384 */       MilestoneHelper.escapeSingleQuotes(pfm.getTitleId()) + "','" + 
/* 2385 */       MilestoneHelper.escapeSingleQuotes(pfm.getSuperLabel()) + "','" + 
/* 2386 */       MilestoneHelper.escapeSingleQuotes(pfm.getLabelCode()) + "','" + 
/* 2387 */       MilestoneHelper.escapeSingleQuotes(pfm.getCompanyCode()) + "','" + 
/* 2388 */       MilestoneHelper.escapeSingleQuotes(pfm.getPoMergeCode()) + "'," + 
/* 2389 */       pfm.getUnitsPerSet() + "," + 
/* 2390 */       pfm.getSetsPerCarton() + ",'" + 
/* 2391 */       MilestoneHelper.escapeSingleQuotes(pfm.getSupplier()) + "','" + 
/* 2392 */       MilestoneHelper.escapeSingleQuotes(pfm.getImportIndicator()) + "','" + 
/*      */       
/* 2394 */       MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getUpc(), "UPC", isDigital, true)) + "','" + 
/* 2395 */       MilestoneHelper.escapeSingleQuotes(pfm.getMusicLine()) + "','" + 
/* 2396 */       MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireOwner()) + "','" + 
/* 2397 */       MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireClass()) + "','" + 
/* 2398 */       MilestoneHelper.escapeSingleQuotes(pfm.getReturnCode()) + "','" + 
/* 2399 */       MilestoneHelper.escapeSingleQuotes(pfm.getExportFlag()) + "','" + 
/* 2400 */       MilestoneHelper.escapeSingleQuotes(pfm.getCountries()) + "','" + 
/* 2401 */       MilestoneHelper.escapeSingleQuotes(pfm.getSpineTitle()) + "','" + 
/* 2402 */       MilestoneHelper.escapeSingleQuotes(pfm.getSpineArtist()) + "','" + 
/* 2403 */       MilestoneHelper.escapeSingleQuotes(pfm.getPriceCode()) + "','" + 
/* 2404 */       MilestoneHelper.escapeSingleQuotes(pfm.getGuaranteeCode()) + "','" + 
/* 2405 */       MilestoneHelper.escapeSingleQuotes(pfm.getLoosePickExemptCode()) + "','" + 
/* 2406 */       MilestoneHelper.escapeSingleQuotes(pfm.getCompilationCode()) + "','" + 
/* 2407 */       MilestoneHelper.escapeSingleQuotes(pfm.getImpRateCode()) + "','" + 
/*      */       
/* 2409 */       MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(pfm.getMusicType())) + "','" + 
/* 2410 */       MilestoneHelper.escapeSingleQuotes(pfm.getNarmFlag()) + "','" + 
/* 2411 */       MilestoneHelper.escapeSingleQuotes(pfm.getPricePoint()) + "','" + 
/* 2412 */       MilestoneHelper.escapeSingleQuotes(pfm.getApprovedByName()) + "','" + 
/* 2413 */       MilestoneHelper.escapeSingleQuotes(pfm.getEnteredByName()) + "','" + 
/* 2414 */       MilestoneHelper.escapeSingleQuotes(pfm.getVerifiedByName()) + "','" + 
/* 2415 */       MilestoneHelper.escapeSingleQuotes(pfm.getChangeNumber()) + "','" + 
/* 2416 */       MilestoneHelper.getFormatedDate(pfm.getStreetDate()) + "','" + 
/* 2417 */       MilestoneHelper.escapeSingleQuotes(pfm.getProjectID()) + "'," + (
/* 2418 */       pfm.getParentalGuidance() ? 1 : 0) + ",'" + 
/*      */       
/* 2420 */       MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getSoundScanGrp(), "SSG", isDigital, true)) + "'," + 
/* 2421 */       userID + "," + 
/* 2422 */       timestamp + "," + (
/* 2423 */       pfm.getValueAdded() ? 1 : 0) + "," + (
/* 2424 */       pfm.getBoxSet() ? 1 : 0) + ",'" + 
/* 2425 */       MilestoneHelper.escapeSingleQuotes(pfm.getEncryptionFlag()) + "','" + 
/* 2426 */       MilestoneHelper.escapeSingleQuotes(pfm.getStatus()) + "','" + 
/* 2427 */       MilestoneHelper.escapeSingleQuotes(pfm.getPriceCodeDPC()) + "'";
/*      */ 
/*      */ 
/*      */     
/* 2431 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2432 */     connector.runQuery();
/* 2433 */     connector.close();
/*      */     
/* 2435 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on, last_updated_by  FROM vi_PFM_Selection WHERE release_id = " + 
/*      */       
/* 2437 */       pfm.getReleaseId() + 
/* 2438 */       ";";
/*      */ 
/*      */     
/* 2441 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 2442 */     connectorTimestamp.runQuery();
/*      */     
/* 2444 */     if (connectorTimestamp.more()) {
/*      */       
/* 2446 */       pfm.setLastUpdatedCk(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/* 2447 */       pfm.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(connectorTimestamp.getField("last_updated_on")));
/* 2448 */       pfm.setLastUpdatingUser(connectorTimestamp.getIntegerField("last_updated_by"));
/*      */     } 
/* 2450 */     connectorTimestamp.close();
/*      */     
/* 2452 */     return pfm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Pfm saveNewPfm(Pfm pfm, User updatingUser, boolean isDigital) {
/* 2461 */     String query = "sp_ins_PFM_Selection " + 
/* 2462 */       pfm.getReleaseId() + ",'" + 
/* 2463 */       MilestoneHelper.escapeSingleQuotes(pfm.getReleaseType()) + "','" + 
/* 2464 */       MilestoneHelper.escapeSingleQuotes(pfm.getMode()) + "','" + 
/* 2465 */       MilestoneHelper.escapeSingleQuotes(pfm.getPrintOption()) + "','" + 
/* 2466 */       MilestoneHelper.escapeSingleQuotes(pfm.getPreparedBy()) + "','" + 
/* 2467 */       MilestoneHelper.escapeSingleQuotes(pfm.getEmail()) + "','" + 
/* 2468 */       MilestoneHelper.escapeSingleQuotes(pfm.getPhone()) + "','" + 
/* 2469 */       MilestoneHelper.escapeSingleQuotes(pfm.getFaxNumber()) + "','" + 
/* 2470 */       MilestoneHelper.escapeSingleQuotes(pfm.getComments()) + "','" + 
/* 2471 */       MilestoneHelper.escapeSingleQuotes(pfm.getOperatingCompany()) + "','" + 
/* 2472 */       MilestoneHelper.escapeSingleQuotes(pfm.getProductNumber()) + "','" + 
/* 2473 */       MilestoneHelper.escapeSingleQuotes(pfm.getConfigCode()) + "','" + 
/* 2474 */       MilestoneHelper.escapeSingleQuotes(pfm.getModifier()) + "','" + 
/* 2475 */       MilestoneHelper.escapeSingleQuotes(pfm.getTitle()) + "','" + 
/* 2476 */       MilestoneHelper.escapeSingleQuotes(pfm.getArtist()) + "','" + 
/* 2477 */       MilestoneHelper.escapeSingleQuotes(pfm.getTitleId()) + "','" + 
/* 2478 */       MilestoneHelper.escapeSingleQuotes(pfm.getSuperLabel()) + "','" + 
/* 2479 */       MilestoneHelper.escapeSingleQuotes(pfm.getLabelCode()) + "','" + 
/* 2480 */       MilestoneHelper.escapeSingleQuotes(pfm.getCompanyCode()) + "','" + 
/* 2481 */       MilestoneHelper.escapeSingleQuotes(pfm.getPoMergeCode()) + "'," + 
/* 2482 */       pfm.getUnitsPerSet() + "," + 
/* 2483 */       pfm.getSetsPerCarton() + ",'" + 
/* 2484 */       MilestoneHelper.escapeSingleQuotes(pfm.getSupplier()) + "','" + 
/* 2485 */       MilestoneHelper.escapeSingleQuotes(pfm.getImportIndicator()) + "','" + 
/*      */       
/* 2487 */       MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getUpc(), "UPC", isDigital, true)) + "','" + 
/* 2488 */       MilestoneHelper.escapeSingleQuotes(pfm.getMusicLine()) + "','" + 
/* 2489 */       MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireOwner()) + "','" + 
/* 2490 */       MilestoneHelper.escapeSingleQuotes(pfm.getRepertoireClass()) + "','" + 
/* 2491 */       MilestoneHelper.escapeSingleQuotes(pfm.getReturnCode()) + "','" + 
/* 2492 */       MilestoneHelper.escapeSingleQuotes(pfm.getExportFlag()) + "','" + 
/* 2493 */       MilestoneHelper.escapeSingleQuotes(pfm.getCountries()) + "','" + 
/* 2494 */       MilestoneHelper.escapeSingleQuotes(pfm.getSpineTitle()) + "','" + 
/* 2495 */       MilestoneHelper.escapeSingleQuotes(pfm.getSpineArtist()) + "','" + 
/* 2496 */       MilestoneHelper.escapeSingleQuotes(pfm.getPriceCode()) + "','" + 
/* 2497 */       MilestoneHelper.escapeSingleQuotes(pfm.getGuaranteeCode()) + "','" + 
/* 2498 */       MilestoneHelper.escapeSingleQuotes(pfm.getLoosePickExemptCode()) + "','" + 
/* 2499 */       MilestoneHelper.escapeSingleQuotes(pfm.getCompilationCode()) + "','" + 
/* 2500 */       MilestoneHelper.escapeSingleQuotes(pfm.getImpRateCode()) + "','" + 
/*      */       
/* 2502 */       MilestoneHelper.escapeSingleQuotes(getLookupObjectValue(pfm.getMusicType())) + "','" + 
/* 2503 */       MilestoneHelper.escapeSingleQuotes(pfm.getNarmFlag()) + "','" + 
/* 2504 */       MilestoneHelper.escapeSingleQuotes(pfm.getPricePoint()) + "','" + 
/* 2505 */       MilestoneHelper.escapeSingleQuotes(pfm.getApprovedByName()) + "','" + 
/* 2506 */       MilestoneHelper.escapeSingleQuotes(pfm.getEnteredByName()) + "','" + 
/* 2507 */       MilestoneHelper.escapeSingleQuotes(pfm.getChangeNumber()) + "','" + 
/* 2508 */       MilestoneHelper.getFormatedDate(pfm.getStreetDate()) + "','" + 
/* 2509 */       MilestoneHelper.escapeSingleQuotes(pfm.getProjectID()) + "'," + (
/* 2510 */       pfm.getParentalGuidance() ? 1 : 0) + ",'" + 
/*      */       
/* 2512 */       MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(pfm.getSoundScanGrp(), "SSG", isDigital, true)) + "','" + 
/* 2513 */       MilestoneHelper.escapeSingleQuotes(pfm.getVerifiedByName()) + "'," + 
/* 2514 */       updatingUser.getUserId() + "," + (
/* 2515 */       pfm.getValueAdded() ? 1 : 0) + "," + (
/* 2516 */       pfm.getBoxSet() ? 1 : 0) + ",'" + 
/* 2517 */       MilestoneHelper.escapeSingleQuotes(pfm.getEncryptionFlag()) + "','" + 
/* 2518 */       MilestoneHelper.escapeSingleQuotes(pfm.getStatus()) + "','" + 
/* 2519 */       MilestoneHelper.escapeSingleQuotes(pfm.getPriceCodeDPC()) + "'";
/*      */ 
/*      */ 
/*      */     
/* 2523 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2524 */     connector.runQuery();
/*      */ 
/*      */     
/* 2527 */     if (pfm.getReleaseId() != -1) {
/*      */       
/* 2529 */       String lastUpdatedQuery = "SELECT last_updated_on, last_updated_by  FROM Pfm_Selection WHERE release_id = " + 
/*      */         
/* 2531 */         pfm.getReleaseId() + 
/* 2532 */         ";";
/*      */       
/* 2534 */       JdbcConnector connectorLastUpdated = MilestoneHelper.getConnector(lastUpdatedQuery);
/* 2535 */       connectorLastUpdated.runQuery();
/* 2536 */       if (connectorLastUpdated.more()) {
/*      */ 
/*      */         
/* 2539 */         String lastDateString = connectorLastUpdated.getFieldByName("last_updated_on");
/* 2540 */         if (lastDateString != null) {
/* 2541 */           pfm.setLastUpdatedDate(MilestoneHelper.getDatabaseDate(lastDateString));
/*      */         }
/*      */         
/* 2544 */         pfm.setLastUpdatingUser(connectorLastUpdated.getIntegerField("last_updated_by"));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2550 */       connectorLastUpdated.close();
/*      */     } 
/*      */     
/* 2553 */     connector.close();
/*      */     
/* 2555 */     return pfm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Bom saveBom(Bom bom, Selection currentSelection, int userID) {
/* 2564 */     long timestamp = bom.getLastUpdatedCheck();
/*      */     
/* 2566 */     if (bom.getBomId() <= 0) {
/* 2567 */       bom.setBomId(-1);
/*      */     }
/* 2569 */     String hasSpineSticker = "0";
/* 2570 */     if (bom.hasSpineSticker())
/*      */     {
/* 2572 */       hasSpineSticker = "1";
/*      */     }
/*      */     
/* 2575 */     String useShrinkWrap = "0";
/* 2576 */     if (bom.useShrinkWrap())
/*      */     {
/* 2578 */       useShrinkWrap = "1";
/*      */     }
/*      */     
/* 2581 */     String isRetail = "0";
/* 2582 */     if (bom.isRetail())
/*      */     {
/* 2584 */       isRetail = "1";
/*      */     }
/*      */     
/* 2587 */     if (bom.getLabelId() <= 0) {
/*      */       
/* 2589 */       int labelId = -1;
/*      */       
/* 2591 */       if (currentSelection.getLabel() != null) {
/* 2592 */         labelId = currentSelection.getLabel().getStructureID();
/*      */       }
/* 2594 */       bom.setLabelId(labelId);
/*      */     } 
/*      */     
/* 2597 */     String bomSelectionDate = MilestoneHelper.getFormatedDate(bom.getStreetDateOnBom());
/* 2598 */     if (bomSelectionDate.equals("")) {
/* 2599 */       bomSelectionDate = "null,";
/*      */     } else {
/* 2601 */       bomSelectionDate = "'" + MilestoneHelper.getFormatedDate(bom.getStreetDateOnBom()) + "',";
/*      */     } 
/*      */     
/* 2604 */     String query = "sp_sav_Bom_Header " + 
/* 2605 */       bom.getBomId() + "," + 
/* 2606 */       bom.getReleaseId() + "," + 
/* 2607 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getFormat()) + "'," + 
/* 2608 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getPrintOption()) + "'," + 
/* 2609 */       "'" + MilestoneHelper.getFormatedDate(bom.getDate()) + "'," + 
/* 2610 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getType()) + "'," + 
/* 2611 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getChangeNumber()) + "'," + 
/* 2612 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bom.getSubmitter())) + "'," + 
/* 2613 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getEmail()) + "'," + 
/* 2614 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getPhone()) + "'," + 
/* 2615 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getComments()) + "'," + 
/* 2616 */       bom.getLabelId() + "," + 
/* 2617 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getReleasingCompanyId()) + "'," + 
/* 2618 */       isRetail + "," + 
/* 2619 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getSelectionNumber()) + "'," + 
/*      */       
/* 2621 */       bomSelectionDate + 
/* 2622 */       bom.getUnitsPerKG() + "," + 
/* 2623 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getRunTime()) + "'," + 
/* 2624 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getConfiguration()) + "'," + 
/* 2625 */       hasSpineSticker + "," + 
/* 2626 */       useShrinkWrap + "," + 
/* 2627 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bom.getSpecialInstructions())) + "'," + 
/* 2628 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getArtist()) + "'," + 
/* 2629 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getTitle()) + "'," + 
/* 2630 */       userID + "," + 
/* 2631 */       timestamp + "," + 
/* 2632 */       "'" + MilestoneHelper.escapeSingleQuotes(bom.getStatus()) + "'," + 
/*      */       
/* 2634 */       "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(bom.getUpc(), "UPC", currentSelection.getIsDigital(), true)) + "'";
/*      */ 
/*      */ 
/*      */     
/* 2638 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 2639 */     connector.runQuery();
/*      */     
/* 2641 */     if (bom.getBomId() < 0)
/*      */     {
/* 2643 */       bom.setBomId(connector.getIntegerField("ReturnId"));
/*      */     }
/* 2645 */     connector.close();
/*      */     
/* 2647 */     String timestampQuery = "SELECT last_updated_ck, last_updated_on, last_updated_by  FROM bom_header WHERE bom_id = " + 
/*      */       
/* 2649 */       bom.getBomId() + 
/* 2650 */       ";";
/*      */     
/* 2652 */     JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 2653 */     connectorTimestamp.runQuery();
/* 2654 */     if (connectorTimestamp.more()) {
/*      */       
/* 2656 */       bom.setLastUpdatedCheck(Long.parseLong(connectorTimestamp.getField("last_updated_ck"), 16));
/*      */ 
/*      */       
/* 2659 */       bom.setModifiedBy(connectorTimestamp.getIntegerField("last_updated_by"));
/*      */       
/* 2661 */       String modifiedOnString = connectorTimestamp.getFieldByName("last_updated_on");
/* 2662 */       if (modifiedOnString != null)
/* 2663 */         bom.setModifiedOn(MilestoneHelper.getDatabaseDate(modifiedOnString)); 
/*      */     } 
/* 2665 */     connectorTimestamp.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2672 */     BomCassetteDetail cassetteDetail = bom.getBomCassetteDetail();
/*      */ 
/*      */     
/* 2675 */     if (cassetteDetail != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2680 */       String coStatusIndicator = "0";
/* 2681 */       if (cassetteDetail.coStatusIndicator)
/*      */       {
/* 2683 */         coStatusIndicator = "1";
/*      */       }
/*      */       
/* 2686 */       String sp = "sp_upd_Bom_Detail ";
/*      */       
/* 2688 */       if (cassetteDetail.coPartId < 0) {
/* 2689 */         sp = "sp_ins_Bom_Detail ";
/*      */       }
/* 2691 */       String queryCO = String.valueOf(sp) + 
/* 2692 */         bom.getBomId() + "," + 
/* 2693 */         "5" + "," + 
/* 2694 */         cassetteDetail.coParSupplierId + "," + 
/* 2695 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.coInk1) + "'," + 
/* 2696 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.coInk2) + "'," + 
/* 2697 */         "''," + 
/* 2698 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.coColor) + "'," + 
/* 2699 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.coInfo)) + "'," + 
/* 2700 */         coStatusIndicator + "," + 
/* 2701 */         userID;
/*      */       
/* 2703 */       JdbcConnector connectorCO = MilestoneHelper.getConnector(queryCO);
/* 2704 */       connectorCO.runQuery();
/* 2705 */       connectorCO.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2710 */       String norelcoStatusIndicator = "0";
/* 2711 */       if (cassetteDetail.norelcoStatusIndicator)
/*      */       {
/* 2713 */         norelcoStatusIndicator = "1";
/*      */       }
/*      */       
/* 2716 */       String sp1 = "sp_upd_Bom_Detail ";
/*      */       
/* 2718 */       if (cassetteDetail.norelcoPartId < 0) {
/* 2719 */         sp1 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2721 */       String queryNorelco = String.valueOf(sp1) + 
/* 2722 */         bom.getBomId() + "," + 
/* 2723 */         "16" + "," + 
/* 2724 */         cassetteDetail.norelcoSupplierId + "," + 
/* 2725 */         "''," + 
/* 2726 */         "''," + 
/* 2727 */         "''," + 
/* 2728 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.norelcoColor) + "'," + 
/* 2729 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.norelcoInfo)) + "'," + 
/* 2730 */         norelcoStatusIndicator + "," + 
/* 2731 */         userID;
/*      */       
/* 2733 */       JdbcConnector connectorNorelco = MilestoneHelper.getConnector(queryNorelco);
/* 2734 */       connectorNorelco.runQuery();
/* 2735 */       connectorNorelco.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2741 */       String jCardStatusIndicator = "0";
/* 2742 */       if (cassetteDetail.jCardStatusIndicator)
/*      */       {
/* 2744 */         jCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2747 */       String sp2 = "sp_upd_Bom_Detail ";
/*      */       
/* 2749 */       if (cassetteDetail.jCardPartId < 0) {
/* 2750 */         sp2 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2752 */       String queryJCARD = String.valueOf(sp2) + 
/* 2753 */         bom.getBomId() + "," + 
/* 2754 */         "13" + "," + 
/* 2755 */         cassetteDetail.jCardSupplierId + "," + 
/* 2756 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.jCardInk1) + "'," + 
/* 2757 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.jCardInk2) + "'," + 
/* 2758 */         "''," + 
/* 2759 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.jCardPanels)) + "'," + 
/* 2760 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.jCardInfo)) + "'," + 
/* 2761 */         jCardStatusIndicator + "," + 
/* 2762 */         userID;
/*      */       
/* 2764 */       JdbcConnector connectorJCARD = MilestoneHelper.getConnector(queryJCARD);
/* 2765 */       connectorJCARD.runQuery();
/* 2766 */       connectorJCARD.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2771 */       String uCardStatusIndicator = "0";
/* 2772 */       if (cassetteDetail.uCardStatusIndicator)
/*      */       {
/* 2774 */         uCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2777 */       String sp3 = "sp_upd_Bom_Detail ";
/*      */       
/* 2779 */       if (cassetteDetail.uCardPartId < 0) {
/* 2780 */         sp3 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2782 */       String queryUCARD = String.valueOf(sp3) + 
/* 2783 */         bom.getBomId() + "," + 
/* 2784 */         "24" + "," + 
/* 2785 */         cassetteDetail.uCardSupplierId + "," + 
/* 2786 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.uCardInk1) + "'," + 
/* 2787 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.uCardInk2) + "'," + 
/* 2788 */         "''," + 
/* 2789 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.uCardPanels)) + "'," + 
/* 2790 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.uCardInfo)) + "'," + 
/* 2791 */         uCardStatusIndicator + "," + 
/* 2792 */         userID;
/*      */       
/* 2794 */       JdbcConnector connectorUCARD = MilestoneHelper.getConnector(queryUCARD);
/* 2795 */       connectorUCARD.runQuery();
/* 2796 */       connectorUCARD.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2801 */       String oCardStatusIndicator = "0";
/* 2802 */       if (cassetteDetail.oCardStatusIndicator)
/*      */       {
/* 2804 */         oCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2807 */       String sp4 = "sp_upd_Bom_Detail ";
/*      */       
/* 2809 */       if (cassetteDetail.oCardPartId < 0) {
/* 2810 */         sp4 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2812 */       String queryOCARD = String.valueOf(sp4) + 
/* 2813 */         bom.getBomId() + "," + 
/* 2814 */         "17" + "," + 
/* 2815 */         cassetteDetail.oCardSupplierId + "," + 
/* 2816 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.oCardInk1) + "'," + 
/* 2817 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.oCardInk2) + "'," + 
/* 2818 */         "''," + 
/* 2819 */         "''," + 
/* 2820 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.oCardInfo)) + "'," + 
/* 2821 */         oCardStatusIndicator + "," + 
/* 2822 */         userID;
/*      */       
/* 2824 */       JdbcConnector connectorOCARD = MilestoneHelper.getConnector(queryOCARD);
/* 2825 */       connectorOCARD.runQuery();
/* 2826 */       connectorOCARD.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2831 */       String stickerOneCardStatusIndicator = "0";
/* 2832 */       if (cassetteDetail.stickerOneCardStatusIndicator)
/*      */       {
/* 2834 */         stickerOneCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2837 */       String sp5 = "sp_upd_Bom_Detail ";
/*      */       
/* 2839 */       if (cassetteDetail.stickerOneCardPartId < 0) {
/* 2840 */         sp5 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2842 */       String queryStickerOne = String.valueOf(sp5) + 
/* 2843 */         bom.getBomId() + "," + 
/* 2844 */         "21" + "," + 
/* 2845 */         cassetteDetail.stickerOneCardSupplierId + "," + 
/* 2846 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerOneCardInk1) + "'," + 
/* 2847 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerOneCardInk2) + "'," + 
/* 2848 */         "''," + 
/* 2849 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerOneCardPlaces)) + "'," + 
/* 2850 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerOneCardInfo)) + "'," + 
/* 2851 */         stickerOneCardStatusIndicator + "," + 
/* 2852 */         userID;
/*      */       
/* 2854 */       JdbcConnector connectorSticker1 = MilestoneHelper.getConnector(queryStickerOne);
/* 2855 */       connectorSticker1.runQuery();
/* 2856 */       connectorSticker1.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2861 */       String stickerTwoCardStatusIndicator = "0";
/* 2862 */       if (cassetteDetail.stickerTwoCardStatusIndicator)
/*      */       {
/* 2864 */         stickerTwoCardStatusIndicator = "1";
/*      */       }
/*      */       
/* 2867 */       String sp6 = "sp_upd_Bom_Detail ";
/*      */       
/* 2869 */       if (cassetteDetail.stickerTwoCardPartId < 0) {
/* 2870 */         sp6 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2872 */       String queryStickerTwo = String.valueOf(sp6) + 
/* 2873 */         bom.getBomId() + "," + 
/* 2874 */         "22" + "," + 
/* 2875 */         cassetteDetail.stickerTwoCardSupplierId + "," + 
/* 2876 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerTwoCardInk1) + "'," + 
/* 2877 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.stickerTwoCardInk2) + "'," + 
/* 2878 */         "''," + 
/* 2879 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerTwoCardPlaces)) + "'," + 
/* 2880 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.stickerTwoCardInfo)) + "'," + 
/* 2881 */         stickerTwoCardStatusIndicator + "," + 
/* 2882 */         userID;
/*      */       
/* 2884 */       JdbcConnector connectorSticker2 = MilestoneHelper.getConnector(queryStickerTwo);
/* 2885 */       connectorSticker2.runQuery();
/* 2886 */       connectorSticker2.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2891 */       String otherStatusIndicator = "0";
/* 2892 */       if (cassetteDetail.otherStatusIndicator)
/*      */       {
/* 2894 */         otherStatusIndicator = "1";
/*      */       }
/*      */       
/* 2897 */       String sp7 = "sp_upd_Bom_Detail ";
/*      */       
/* 2899 */       if (cassetteDetail.otherPartId < 0) {
/* 2900 */         sp7 = "sp_ins_Bom_Detail ";
/*      */       }
/* 2902 */       String queryOther = String.valueOf(sp7) + 
/* 2903 */         bom.getBomId() + "," + 
/* 2904 */         "18" + "," + 
/* 2905 */         cassetteDetail.otherSupplierId + "," + 
/* 2906 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.otherInk1) + "'," + 
/* 2907 */         "'" + MilestoneHelper.escapeSingleQuotes(cassetteDetail.otherInk2) + "'," + 
/* 2908 */         "''," + 
/* 2909 */         "''," + 
/* 2910 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(cassetteDetail.otherInfo)) + "'," + 
/* 2911 */         otherStatusIndicator + "," + 
/* 2912 */         userID;
/*      */       
/* 2914 */       JdbcConnector connectorOther = MilestoneHelper.getConnector(queryOther);
/* 2915 */       connectorOther.runQuery();
/* 2916 */       connectorOther.close();
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
/* 2927 */     if (bom.getBomDiskDetail() != null || bom.getBomDVDDetail() != null) {
/*      */       BomDVDDetail bomDVDDetail;
/*      */ 
/*      */       
/* 2931 */       if (bom.getBomDiskDetail() != null) {
/* 2932 */         bomDVDDetail = bom.getBomDiskDetail();
/*      */       } else {
/* 2934 */         bomDVDDetail = bom.getBomDVDDetail();
/*      */       } 
/*      */       
/* 2937 */       String DVDDiscSelectionText = "";
/* 2938 */       String DVDCaseSelectionText = "";
/* 2939 */       String blueRayCaseSelectionText = "";
/* 2940 */       if (bom.getBomDVDDetail() != null) {
/*      */ 
/*      */         
/* 2943 */         DVDDiscSelectionText = bom.getBomDVDDetail().getDiscSelectionInfo();
/* 2944 */         DVDCaseSelectionText = bom.getBomDVDDetail().getDVDSelectionInfo();
/*      */ 
/*      */ 
/*      */         
/* 2948 */         String dvdVideoStatusIndicator = "0";
/* 2949 */         if ((bom.getBomDVDDetail()).dvdStatusIndicator) {
/* 2950 */           dvdVideoStatusIndicator = "1";
/*      */         }
/*      */         
/* 2953 */         String spDVDCase = "sp_upd_Bom_Detail ";
/* 2954 */         if ((bom.getBomDVDDetail()).dvdCasePartId < 0) {
/* 2955 */           spDVDCase = "sp_ins_Bom_Detail ";
/*      */         }
/* 2957 */         String query2DVDCase = String.valueOf(spDVDCase) + 
/* 2958 */           bom.getBomId() + "," + 
/* 2959 */           "30" + "," + 
/* 2960 */           "''," + 
/* 2961 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).dvdInk1) + 
/* 2962 */           "'," + 
/* 2963 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).dvdInk2) + 
/* 2964 */           "'," + 
/* 2965 */           "''," + 
/* 2966 */           "'" + MilestoneHelper.escapeSingleQuotes(DVDCaseSelectionText) + "'," + 
/* 2967 */           "'" + 
/* 2968 */           MilestoneHelper.escapeSingleQuotes(
/* 2969 */             MilestoneHelper.escapeDoubleQuotesForHtml(
/* 2970 */               (bom.getBomDVDDetail())
/* 2971 */               .dvdInfo)) + "'," + 
/* 2972 */           dvdVideoStatusIndicator + "," + 
/* 2973 */           userID;
/*      */ 
/*      */         
/* 2976 */         JdbcConnector connector2DVDCase = MilestoneHelper.getConnector(query2DVDCase);
/* 2977 */         connector2DVDCase.runQuery();
/* 2978 */         connector2DVDCase.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2984 */         blueRayCaseSelectionText = bom.getBomDVDDetail().getBluRaySelectionInfo();
/*      */ 
/*      */         
/* 2987 */         String bluRayStatusIndicator = "0";
/* 2988 */         if ((bom.getBomDVDDetail()).bluRayStatusIndicator) {
/* 2989 */           bluRayStatusIndicator = "1";
/*      */         }
/*      */         
/* 2992 */         String spBluRayCase = "sp_upd_Bom_Detail ";
/* 2993 */         if ((bom.getBomDVDDetail()).bluRayCasePartId < 0) {
/* 2994 */           spBluRayCase = "sp_ins_Bom_Detail ";
/*      */         }
/* 2996 */         String query2BluRayCase = String.valueOf(spBluRayCase) + 
/* 2997 */           bom.getBomId() + "," + 
/* 2998 */           "32" + "," + 
/* 2999 */           "''," + 
/* 3000 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).bluRayInk1) + 
/* 3001 */           "'," + 
/* 3002 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).bluRayInk2) + 
/* 3003 */           "'," + 
/* 3004 */           "''," + 
/* 3005 */           "'" + MilestoneHelper.escapeSingleQuotes(blueRayCaseSelectionText) + "'," + 
/* 3006 */           "'" + 
/* 3007 */           MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml((bom.getBomDVDDetail()).bluRayInfo)) + "'," + bluRayStatusIndicator + "," + userID;
/*      */ 
/*      */         
/* 3010 */         JdbcConnector connector2BluRayCase = MilestoneHelper.getConnector(query2BluRayCase);
/* 3011 */         connector2BluRayCase.runQuery();
/* 3012 */         connector2BluRayCase.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3022 */         String wrapStatusIndicator = "0";
/* 3023 */         if ((bom.getBomDVDDetail()).wrapStatusIndicator)
/*      */         {
/* 3025 */           wrapStatusIndicator = "1";
/*      */         }
/*      */         
/* 3028 */         String spWrap = "sp_upd_Bom_Detail ";
/* 3029 */         if ((bom.getBomDVDDetail()).wrapPartId < 0) {
/* 3030 */           spWrap = "sp_ins_Bom_Detail ";
/*      */         }
/* 3032 */         String query2Wrap = String.valueOf(spWrap) + 
/* 3033 */           bom.getBomId() + "," + 
/* 3034 */           "29" + "," + 
/* 3035 */           (bom.getBomDVDDetail()).wrapSupplierId + "," + 
/* 3036 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).wrapInk1) + "'," + 
/* 3037 */           "'" + MilestoneHelper.escapeSingleQuotes((bom.getBomDVDDetail()).wrapInk2) + "'," + 
/* 3038 */           "''," + 
/* 3039 */           "''," + 
/* 3040 */           "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml((bom.getBomDVDDetail()).wrapInfo)) + "'," + 
/* 3041 */           wrapStatusIndicator + "," + 
/* 3042 */           userID;
/*      */ 
/*      */         
/* 3045 */         JdbcConnector connector2Wrap = MilestoneHelper.getConnector(query2Wrap);
/* 3046 */         connector2Wrap.runQuery();
/* 3047 */         connector2Wrap.close();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3055 */       String discStatusIndicator = "0";
/* 3056 */       if (bomDVDDetail.discStatusIndicator)
/*      */       {
/* 3058 */         discStatusIndicator = "1";
/*      */       }
/*      */       
/* 3061 */       String sp8 = "sp_upd_Bom_Detail ";
/* 3062 */       if (bomDVDDetail.discPartId < 0) {
/* 3063 */         sp8 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3065 */       String query2Disk = String.valueOf(sp8) + 
/* 3066 */         bom.getBomId() + "," + 
/* 3067 */         "7" + "," + 
/* 3068 */         bomDVDDetail.diskSupplierId + "," + 
/* 3069 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.discInk1) + "'," + 
/* 3070 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.discInk2) + "'," + 
/* 3071 */         "''," + 
/* 3072 */         "'" + MilestoneHelper.escapeSingleQuotes(DVDDiscSelectionText) + "'," + 
/* 3073 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.discInfo)) + "'," + 
/* 3074 */         discStatusIndicator + "," + 
/* 3075 */         userID;
/*      */       
/* 3077 */       JdbcConnector connector2Disk = MilestoneHelper.getConnector(query2Disk);
/* 3078 */       connector2Disk.runQuery();
/* 3079 */       connector2Disk.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3084 */       String jewelStatusIndicator = "0";
/* 3085 */       if (bomDVDDetail.jewelStatusIndicator)
/*      */       {
/* 3087 */         jewelStatusIndicator = "1";
/*      */       }
/*      */       
/* 3090 */       String sp9 = "sp_upd_Bom_Detail ";
/* 3091 */       if (bomDVDDetail.jewelPartId < 0) {
/* 3092 */         sp9 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3094 */       String query2Jewel = String.valueOf(sp9) + 
/* 3095 */         bom.getBomId() + "," + 
/* 3096 */         "12" + "," + 
/* 3097 */         "''," + 
/* 3098 */         "''," + 
/* 3099 */         "''," + 
/* 3100 */         "''," + 
/* 3101 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.jewelColor) + "'," + 
/* 3102 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.jewelInfo)) + "'," + 
/* 3103 */         jewelStatusIndicator + "," + 
/* 3104 */         userID;
/*      */       
/* 3106 */       JdbcConnector connector2Jewel = MilestoneHelper.getConnector(query2Jewel);
/* 3107 */       connector2Jewel.runQuery();
/* 3108 */       connector2Jewel.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3113 */       String trayStatusIndicator = "0";
/* 3114 */       if (bomDVDDetail.trayStatusIndicator)
/*      */       {
/* 3116 */         trayStatusIndicator = "1";
/*      */       }
/*      */       
/* 3119 */       String sp10 = "sp_upd_Bom_Detail ";
/* 3120 */       if (bomDVDDetail.trayPartId < 0) {
/* 3121 */         sp10 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3123 */       String query2Tray = String.valueOf(sp10) + 
/* 3124 */         bom.getBomId() + "," + 
/* 3125 */         "23" + "," + 
/* 3126 */         "''," + 
/* 3127 */         "''," + 
/* 3128 */         "''," + 
/* 3129 */         "''," + 
/* 3130 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.trayColor) + "'," + 
/* 3131 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.trayInfo)) + "'," + 
/* 3132 */         trayStatusIndicator + "," + 
/* 3133 */         userID;
/*      */       
/* 3135 */       JdbcConnector connector2Tray = MilestoneHelper.getConnector(query2Tray);
/* 3136 */       connector2Tray.runQuery();
/* 3137 */       connector2Tray.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3142 */       String inlayStatusIndicator = "0";
/* 3143 */       if (bomDVDDetail.inlayStatusIndicator)
/*      */       {
/* 3145 */         inlayStatusIndicator = "1";
/*      */       }
/*      */       
/* 3148 */       String sp11 = "sp_upd_Bom_Detail ";
/* 3149 */       if (bomDVDDetail.inlayPartId < 0) {
/* 3150 */         sp11 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3152 */       String query2Inlay = String.valueOf(sp11) + 
/* 3153 */         bom.getBomId() + "," + 
/* 3154 */         "10" + "," + 
/* 3155 */         bomDVDDetail.inlaySupplierId + "," + 
/* 3156 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.inlayInk1) + "'," + 
/* 3157 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.inlayInk2) + "'," + 
/* 3158 */         "''," + 
/* 3159 */         "''," + 
/* 3160 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.inlayInfo)) + "'," + 
/* 3161 */         inlayStatusIndicator + "," + 
/* 3162 */         userID;
/*      */       
/* 3164 */       JdbcConnector connector2Inlay = MilestoneHelper.getConnector(query2Inlay);
/* 3165 */       connector2Inlay.runQuery();
/* 3166 */       connector2Inlay.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3171 */       String frontStatusIndicator = "0";
/* 3172 */       if (bomDVDDetail.frontStatusIndicator)
/*      */       {
/* 3174 */         frontStatusIndicator = "1";
/*      */       }
/*      */       
/* 3177 */       String sp12 = "sp_upd_Bom_Detail ";
/* 3178 */       if (bomDVDDetail.frontPartId < 0) {
/* 3179 */         sp12 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3181 */       String query2Front = String.valueOf(sp12) + 
/* 3182 */         bom.getBomId() + "," + 
/* 3183 */         "9" + "," + 
/* 3184 */         bomDVDDetail.frontSupplierId + "," + 
/* 3185 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.frontInk1) + "'," + 
/* 3186 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.frontInk2) + "'," + 
/* 3187 */         "''," + 
/* 3188 */         "''," + 
/* 3189 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.frontInfo)) + "'," + 
/* 3190 */         frontStatusIndicator + "," + 
/* 3191 */         userID;
/*      */       
/* 3193 */       JdbcConnector connector2Front = MilestoneHelper.getConnector(query2Front);
/* 3194 */       connector2Front.runQuery();
/* 3195 */       connector2Front.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3200 */       String folderStatusIndicator = "0";
/* 3201 */       if (bomDVDDetail.folderStatusIndicator)
/*      */       {
/* 3203 */         folderStatusIndicator = "1";
/*      */       }
/*      */       
/* 3206 */       String sp13 = "sp_upd_Bom_Detail ";
/* 3207 */       if (bomDVDDetail.folderPartId < 0) {
/* 3208 */         sp13 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3210 */       String query2Folder = String.valueOf(sp13) + 
/* 3211 */         bom.getBomId() + "," + 
/* 3212 */         "8" + "," + 
/* 3213 */         bomDVDDetail.folderSupplierId + "," + 
/* 3214 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.folderInk1) + "'," + 
/* 3215 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.folderInk2) + "'," + 
/* 3216 */         "''," + 
/* 3217 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.folderPages) + "'," + 
/* 3218 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.folderInfo)) + "'," + 
/* 3219 */         folderStatusIndicator + "," + 
/* 3220 */         userID;
/*      */       
/* 3222 */       JdbcConnector connector2Folder = MilestoneHelper.getConnector(query2Folder);
/* 3223 */       connector2Folder.runQuery();
/* 3224 */       connector2Folder.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3229 */       String bookletStatusIndicator = "0";
/* 3230 */       if (bomDVDDetail.bookletStatusIndicator)
/*      */       {
/* 3232 */         bookletStatusIndicator = "1";
/*      */       }
/*      */       
/* 3235 */       String sp14 = "sp_upd_Bom_Detail ";
/* 3236 */       if (bomDVDDetail.bookletPartId < 0) {
/* 3237 */         sp14 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3239 */       String query2Booklet = String.valueOf(sp14) + 
/* 3240 */         bom.getBomId() + "," + 
/* 3241 */         "1" + "," + 
/* 3242 */         bomDVDDetail.bookletSupplierId + "," + 
/* 3243 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookletInk1) + "'," + 
/* 3244 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookletInk2) + "'," + 
/* 3245 */         "''," + 
/* 3246 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookletPages) + "'," + 
/* 3247 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.bookletInfo)) + "'," + 
/* 3248 */         bookletStatusIndicator + "," + 
/* 3249 */         userID;
/*      */       
/* 3251 */       JdbcConnector connector2Booklet = MilestoneHelper.getConnector(query2Booklet);
/* 3252 */       connector2Booklet.runQuery();
/* 3253 */       connector2Booklet.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3258 */       String brcStatusIndicator = "0";
/* 3259 */       if (bomDVDDetail.brcStatusIndicator)
/*      */       {
/* 3261 */         brcStatusIndicator = "1";
/*      */       }
/*      */       
/* 3264 */       String sp15 = "sp_upd_Bom_Detail ";
/* 3265 */       if (bomDVDDetail.brcPartId < 0) {
/* 3266 */         sp15 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3268 */       String query2Brc = String.valueOf(sp15) + 
/* 3269 */         bom.getBomId() + "," + 
/* 3270 */         "4" + "," + 
/* 3271 */         bomDVDDetail.brcSupplierId + "," + 
/* 3272 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.brcInk1) + "'," + 
/* 3273 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.brcInk2) + "'," + 
/* 3274 */         "''," + 
/* 3275 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.brcSize)) + "'," + 
/* 3276 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.brcInfo)) + "'," + 
/* 3277 */         brcStatusIndicator + "," + 
/* 3278 */         userID;
/*      */       
/* 3280 */       JdbcConnector connector2Brc = MilestoneHelper.getConnector(query2Brc);
/* 3281 */       connector2Brc.runQuery();
/* 3282 */       connector2Brc.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3287 */       String miniStatusIndicator = "0";
/* 3288 */       if (bomDVDDetail.miniStatusIndicator)
/*      */       {
/* 3290 */         miniStatusIndicator = "1";
/*      */       }
/*      */       
/* 3293 */       String sp16 = "sp_upd_Bom_Detail ";
/* 3294 */       if (bomDVDDetail.miniPartId < 0) {
/* 3295 */         sp16 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3297 */       String query2Mini = String.valueOf(sp16) + 
/* 3298 */         bom.getBomId() + "," + 
/* 3299 */         "15" + "," + 
/* 3300 */         bomDVDDetail.miniSupplierId + "," + 
/* 3301 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.miniInk1) + "'," + 
/* 3302 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.miniInk2) + "'," + 
/* 3303 */         "''," + 
/* 3304 */         "''," + 
/* 3305 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.miniInfo)) + "'," + 
/* 3306 */         miniStatusIndicator + "," + 
/* 3307 */         userID;
/*      */       
/* 3309 */       JdbcConnector connector2Mini = MilestoneHelper.getConnector(query2Mini);
/* 3310 */       connector2Mini.runQuery();
/* 3311 */       connector2Mini.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3316 */       String digiPakStatusIndicator = "0";
/* 3317 */       if (bomDVDDetail.digiPakStatusIndicator)
/*      */       {
/* 3319 */         digiPakStatusIndicator = "1";
/*      */       }
/*      */       
/* 3322 */       String sp17 = "sp_upd_Bom_Detail ";
/* 3323 */       if (bomDVDDetail.digiPakPartId < 0) {
/* 3324 */         sp17 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3326 */       String query2Digi = String.valueOf(sp17) + 
/* 3327 */         bom.getBomId() + "," + 
/* 3328 */         "6" + "," + 
/* 3329 */         bomDVDDetail.digiPakSupplierId + "," + 
/* 3330 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.digiPakInk1) + "'," + 
/* 3331 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.digiPakInk2) + "'," + 
/* 3332 */         "''," + 
/* 3333 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.digiPakTray)) + "'," + 
/* 3334 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.digiPakInfo)) + "'," + 
/* 3335 */         digiPakStatusIndicator + "," + 
/* 3336 */         userID;
/*      */       
/* 3338 */       JdbcConnector connector2Digi = MilestoneHelper.getConnector(query2Digi);
/* 3339 */       connector2Digi.runQuery();
/* 3340 */       connector2Digi.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3347 */       String softPakStatusIndicator = "0";
/* 3348 */       if (bomDVDDetail.softPakStatusIndicator)
/*      */       {
/* 3350 */         softPakStatusIndicator = "1";
/*      */       }
/*      */       
/* 3353 */       String sp31 = "sp_upd_Bom_Detail ";
/* 3354 */       if (bomDVDDetail.softPakPartId < 0) {
/* 3355 */         sp31 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3357 */       String query2softPak = String.valueOf(sp31) + 
/* 3358 */         bom.getBomId() + "," + 
/* 3359 */         "31" + "," + 
/* 3360 */         bomDVDDetail.softPakSupplierId + "," + 
/* 3361 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.softPakInk1) + "'," + 
/* 3362 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.softPakInk2) + "'," + 
/* 3363 */         "''," + 
/* 3364 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.softPakTray)) + "'," + 
/* 3365 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.softPakInfo)) + "'," + 
/* 3366 */         softPakStatusIndicator + "," + 
/* 3367 */         userID;
/*      */       
/* 3369 */       JdbcConnector connector2Soft = MilestoneHelper.getConnector(query2softPak);
/* 3370 */       connector2Soft.runQuery();
/* 3371 */       connector2Soft.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3383 */       String stickerOneStatusIndicator = "0";
/* 3384 */       if (bomDVDDetail.stickerOneStatusIndicator)
/*      */       {
/* 3386 */         stickerOneStatusIndicator = "1";
/*      */       }
/*      */       
/* 3389 */       String sp18 = "sp_upd_Bom_Detail ";
/* 3390 */       if (bomDVDDetail.stickerOnePartId < 0) {
/* 3391 */         sp18 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3393 */       String query2StickerOne = String.valueOf(sp18) + 
/* 3394 */         bom.getBomId() + "," + 
/* 3395 */         "21" + "," + 
/* 3396 */         bomDVDDetail.stickerOneSupplierId + "," + 
/* 3397 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerOneInk1) + "'," + 
/* 3398 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerOneInk2) + "'," + 
/* 3399 */         "''," + 
/* 3400 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerOnePlaces)) + "'," + 
/* 3401 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerOneInfo)) + "'," + 
/* 3402 */         stickerOneStatusIndicator + "," + 
/* 3403 */         userID;
/*      */       
/* 3405 */       JdbcConnector connector2StickerOne = MilestoneHelper.getConnector(query2StickerOne);
/* 3406 */       connector2StickerOne.runQuery();
/* 3407 */       connector2StickerOne.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3412 */       String stickerTwoStatusIndicator = "0";
/* 3413 */       if (bomDVDDetail.stickerTwoStatusIndicator)
/*      */       {
/* 3415 */         stickerTwoStatusIndicator = "1";
/*      */       }
/*      */       
/* 3418 */       String sp19 = "sp_upd_Bom_Detail ";
/* 3419 */       if (bomDVDDetail.stickerTwoPartId < 0) {
/* 3420 */         sp19 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3422 */       String query2StickerTwo = String.valueOf(sp19) + 
/* 3423 */         bom.getBomId() + "," + 
/* 3424 */         "22" + "," + 
/* 3425 */         bomDVDDetail.stickerTwoSupplierId + "," + 
/* 3426 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerTwoInk1) + "'," + 
/* 3427 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.stickerTwoInk2) + "'," + 
/* 3428 */         "''," + 
/* 3429 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerTwoPlaces)) + "'," + 
/* 3430 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.stickerTwoInfo)) + "'," + 
/* 3431 */         stickerTwoStatusIndicator + "," + 
/* 3432 */         userID;
/*      */       
/* 3434 */       JdbcConnector connector2StickerTwo = MilestoneHelper.getConnector(query2StickerTwo);
/* 3435 */       connector2StickerTwo.runQuery();
/* 3436 */       connector2StickerTwo.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3441 */       String bookStatusIndicator = "0";
/* 3442 */       if (bomDVDDetail.bookStatusIndicator)
/*      */       {
/* 3444 */         bookStatusIndicator = "1";
/*      */       }
/*      */       
/* 3447 */       String sp20 = "sp_upd_Bom_Detail ";
/* 3448 */       if (bomDVDDetail.bookPartId < 0) {
/* 3449 */         sp20 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3451 */       String query2Book = String.valueOf(sp20) + 
/* 3452 */         bom.getBomId() + "," + 
/* 3453 */         "2" + "," + 
/* 3454 */         bomDVDDetail.bookSupplierId + "," + 
/* 3455 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookInk1) + "'," + 
/* 3456 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.bookInk2) + "'," + 
/* 3457 */         "''," + 
/* 3458 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.bookPages)) + "'," + 
/* 3459 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.bookInfo)) + "'," + 
/* 3460 */         bookStatusIndicator + "," + 
/* 3461 */         userID;
/*      */       
/* 3463 */       JdbcConnector connector2Book = MilestoneHelper.getConnector(query2Book);
/* 3464 */       connector2Book.runQuery();
/* 3465 */       connector2Book.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3470 */       String boxStatusIndicator = "0";
/* 3471 */       if (bomDVDDetail.boxStatusIndicator)
/*      */       {
/* 3473 */         boxStatusIndicator = "1";
/*      */       }
/*      */       
/* 3476 */       String sp21 = "sp_upd_Bom_Detail ";
/* 3477 */       if (bomDVDDetail.boxPartId < 0) {
/* 3478 */         sp21 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3480 */       String query2Box = String.valueOf(sp21) + 
/* 3481 */         bom.getBomId() + "," + 
/* 3482 */         "3" + "," + 
/* 3483 */         bomDVDDetail.boxSupplierId + "," + 
/* 3484 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.boxInk1) + "'," + 
/* 3485 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.boxInk2) + "'," + 
/* 3486 */         "''," + 
/* 3487 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.boxSizes)) + "'," + 
/* 3488 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.boxInfo)) + "'," + 
/* 3489 */         boxStatusIndicator + "," + 
/* 3490 */         userID;
/*      */       
/* 3492 */       JdbcConnector connector2Box = MilestoneHelper.getConnector(query2Box);
/* 3493 */       connector2Box.runQuery();
/* 3494 */       connector2Box.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3499 */       String otherStatusIndicatorDisc = "0";
/* 3500 */       if (bomDVDDetail.otherStatusIndicator)
/*      */       {
/* 3502 */         otherStatusIndicatorDisc = "1";
/*      */       }
/*      */       
/* 3505 */       String sp22 = "sp_upd_Bom_Detail ";
/* 3506 */       if (bomDVDDetail.otherPartId < 0) {
/* 3507 */         sp22 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3509 */       String query2Other = String.valueOf(sp22) + 
/* 3510 */         bom.getBomId() + "," + 
/* 3511 */         "18" + "," + 
/* 3512 */         bomDVDDetail.otherSupplierId + "," + 
/* 3513 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.otherInk1) + "'," + 
/* 3514 */         "'" + MilestoneHelper.escapeSingleQuotes(bomDVDDetail.otherInk2) + "'," + 
/* 3515 */         "''," + 
/* 3516 */         "''," + 
/* 3517 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(bomDVDDetail.otherInfo)) + "'," + 
/* 3518 */         otherStatusIndicatorDisc + "," + 
/* 3519 */         userID;
/*      */       
/* 3521 */       JdbcConnector connector2Other = MilestoneHelper.getConnector(query2Other);
/* 3522 */       connector2Other.runQuery();
/* 3523 */       connector2Other.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3531 */     BomVinylDetail vinylDetail = bom.getBomVinylDetail();
/*      */     
/* 3533 */     if (vinylDetail != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3538 */       String recordStatusIndicator = "0";
/* 3539 */       if (vinylDetail.recordStatusIndicator)
/*      */       {
/* 3541 */         recordStatusIndicator = "1";
/*      */       }
/*      */       
/* 3544 */       String sp23 = "sp_upd_Bom_Detail ";
/* 3545 */       if (vinylDetail.recordPartId < 0) {
/* 3546 */         sp23 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3548 */       String query3Record = String.valueOf(sp23) + 
/* 3549 */         bom.getBomId() + "," + 
/* 3550 */         "19" + "," + 
/* 3551 */         vinylDetail.recordSupplierId + "," + 
/* 3552 */         "''," + 
/* 3553 */         "''," + 
/* 3554 */         "''," + 
/* 3555 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.recordColor) + "'," + 
/* 3556 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.recordInfo)) + "'," + 
/* 3557 */         recordStatusIndicator + "," + 
/* 3558 */         userID;
/*      */       
/* 3560 */       JdbcConnector connector3Record = MilestoneHelper.getConnector(query3Record);
/* 3561 */       connector3Record.runQuery();
/* 3562 */       connector3Record.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3567 */       String labelStatusIndicator = "0";
/* 3568 */       if (vinylDetail.labelStatusIndicator)
/*      */       {
/* 3570 */         labelStatusIndicator = "1";
/*      */       }
/*      */       
/* 3573 */       String sp24 = "sp_upd_Bom_Detail ";
/* 3574 */       if (vinylDetail.labelPartId < 0) {
/* 3575 */         sp24 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3577 */       String query3Label = String.valueOf(sp24) + 
/* 3578 */         bom.getBomId() + "," + 
/* 3579 */         "14" + "," + 
/* 3580 */         vinylDetail.labelSupplierId + "," + 
/* 3581 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.labelInk1) + "'," + 
/* 3582 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.labelInk2) + "'," + 
/* 3583 */         "''," + 
/* 3584 */         "''," + 
/* 3585 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.labelInfo)) + "'," + 
/* 3586 */         labelStatusIndicator + "," + 
/* 3587 */         userID;
/*      */       
/* 3589 */       JdbcConnector connector3Label = MilestoneHelper.getConnector(query3Label);
/* 3590 */       connector3Label.runQuery();
/* 3591 */       connector3Label.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3596 */       String sleeveStatusIndicator = "0";
/* 3597 */       if (vinylDetail.sleeveStatusIndicator)
/*      */       {
/* 3599 */         sleeveStatusIndicator = "1";
/*      */       }
/*      */       
/* 3602 */       String sp25 = "sp_upd_Bom_Detail ";
/* 3603 */       if (vinylDetail.sleevePartId < 0) {
/* 3604 */         sp25 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3606 */       String query3Sleeve = String.valueOf(sp25) + 
/* 3607 */         bom.getBomId() + "," + 
/* 3608 */         "20" + "," + 
/* 3609 */         vinylDetail.sleeveSupplierId + "," + 
/* 3610 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.sleeveInk1) + "'," + 
/* 3611 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.sleeveInk2) + "'," + 
/* 3612 */         "''," + 
/* 3613 */         "''," + 
/* 3614 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.sleeveInfo)) + "'," + 
/* 3615 */         sleeveStatusIndicator + "," + 
/* 3616 */         userID;
/*      */       
/* 3618 */       JdbcConnector connector3Sleeve = MilestoneHelper.getConnector(query3Sleeve);
/* 3619 */       connector3Sleeve.runQuery();
/* 3620 */       connector3Sleeve.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3625 */       String jacketStatusIndicator = "0";
/* 3626 */       if (vinylDetail.jacketStatusIndicator)
/*      */       {
/* 3628 */         jacketStatusIndicator = "1";
/*      */       }
/*      */       
/* 3631 */       String sp26 = "sp_upd_Bom_Detail ";
/* 3632 */       if (vinylDetail.jacketPartId < 0) {
/* 3633 */         sp26 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3635 */       String query3Jacket = String.valueOf(sp26) + 
/* 3636 */         bom.getBomId() + "," + 
/* 3637 */         "11" + "," + 
/* 3638 */         vinylDetail.jacketSupplierId + "," + 
/* 3639 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.jacketInk1) + "'," + 
/* 3640 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.jacketInk2) + "'," + 
/* 3641 */         "''," + 
/* 3642 */         "''," + 
/* 3643 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.jacketInfo)) + "'," + 
/* 3644 */         jacketStatusIndicator + "," + 
/* 3645 */         userID;
/*      */       
/* 3647 */       JdbcConnector connector3Jacket = MilestoneHelper.getConnector(query3Jacket);
/* 3648 */       connector3Jacket.runQuery();
/* 3649 */       connector3Jacket.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3656 */       String insertStatusIndicator = "0";
/* 3657 */       if (vinylDetail.insertStatusIndicator)
/*      */       {
/* 3659 */         insertStatusIndicator = "1";
/*      */       }
/*      */       
/* 3662 */       String sp33 = "sp_upd_Bom_Detail ";
/* 3663 */       if (vinylDetail.insertPartId < 0) {
/* 3664 */         sp33 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3666 */       String query3Insert = String.valueOf(sp33) + 
/* 3667 */         bom.getBomId() + "," + 
/* 3668 */         "33" + "," + 
/* 3669 */         vinylDetail.insertSupplierId + "," + 
/* 3670 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.insertInk1) + "'," + 
/* 3671 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.insertInk2) + "'," + 
/* 3672 */         "''," + 
/* 3673 */         "''," + 
/* 3674 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.insertInfo)) + "'," + 
/* 3675 */         insertStatusIndicator + "," + 
/* 3676 */         userID;
/*      */       
/* 3678 */       JdbcConnector connector3Insert = MilestoneHelper.getConnector(query3Insert);
/* 3679 */       connector3Insert.runQuery();
/* 3680 */       connector3Insert.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3685 */       String stickerOneStatusIndicatorVinyl = "0";
/* 3686 */       if (vinylDetail.stickerOneStatusIndicator)
/*      */       {
/* 3688 */         stickerOneStatusIndicatorVinyl = "1";
/*      */       }
/*      */       
/* 3691 */       String sp27 = "sp_upd_Bom_Detail ";
/* 3692 */       if (vinylDetail.stickerOnePartId < 0) {
/* 3693 */         sp27 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3695 */       String query3StickerOne = String.valueOf(sp27) + 
/* 3696 */         bom.getBomId() + "," + 
/* 3697 */         "21" + "," + 
/* 3698 */         vinylDetail.stickerOneSupplierId + "," + 
/* 3699 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerOneInk1) + "'," + 
/* 3700 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerOneInk2) + "'," + 
/* 3701 */         "''," + 
/* 3702 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerOnePlaces)) + "'," + 
/* 3703 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerOneInfo)) + "'," + 
/* 3704 */         stickerOneStatusIndicatorVinyl + "," + 
/* 3705 */         userID;
/*      */       
/* 3707 */       JdbcConnector connector3StickerOne = MilestoneHelper.getConnector(query3StickerOne);
/* 3708 */       connector3StickerOne.runQuery();
/* 3709 */       connector3StickerOne.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3714 */       String stickerTwoStatusIndicatorVinyl = "0";
/* 3715 */       if (vinylDetail.stickerTwoStatusIndicator)
/*      */       {
/* 3717 */         stickerTwoStatusIndicatorVinyl = "1";
/*      */       }
/*      */       
/* 3720 */       String sp28 = "sp_upd_Bom_Detail ";
/* 3721 */       if (vinylDetail.stickerTwoPartId < 0) {
/* 3722 */         sp28 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3724 */       String query3StickerTwo = String.valueOf(sp28) + 
/* 3725 */         bom.getBomId() + "," + 
/* 3726 */         "22" + "," + 
/* 3727 */         vinylDetail.stickerTwoSupplierId + "," + 
/* 3728 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerTwoInk1) + "'," + 
/* 3729 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.stickerTwoInk2) + "'," + 
/* 3730 */         "''," + 
/* 3731 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerTwoPlaces)) + "'," + 
/* 3732 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.stickerTwoInfo)) + "'," + 
/* 3733 */         stickerTwoStatusIndicatorVinyl + "," + 
/* 3734 */         userID;
/*      */       
/* 3736 */       JdbcConnector connector3StickerTwo = MilestoneHelper.getConnector(query3StickerTwo);
/* 3737 */       connector3StickerTwo.runQuery();
/* 3738 */       connector3StickerTwo.close();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3743 */       String otherStatusIndicatorVinyl = "0";
/* 3744 */       if (vinylDetail.otherStatusIndicator)
/*      */       {
/* 3746 */         otherStatusIndicatorVinyl = "1";
/*      */       }
/*      */       
/* 3749 */       String sp29 = "sp_upd_Bom_Detail ";
/* 3750 */       if (vinylDetail.otherPartId < 0) {
/* 3751 */         sp29 = "sp_ins_Bom_Detail ";
/*      */       }
/* 3753 */       String query3Other = String.valueOf(sp29) + 
/* 3754 */         bom.getBomId() + "," + 
/* 3755 */         "18" + "," + 
/* 3756 */         vinylDetail.otherSupplierId + "," + 
/* 3757 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.otherInk1) + "'," + 
/* 3758 */         "'" + MilestoneHelper.escapeSingleQuotes(vinylDetail.otherInk2) + "'," + 
/* 3759 */         "''," + 
/* 3760 */         "''," + 
/* 3761 */         "'" + MilestoneHelper.escapeSingleQuotes(MilestoneHelper.escapeDoubleQuotesForHtml(vinylDetail.otherInfo)) + "'," + 
/* 3762 */         otherStatusIndicatorVinyl + "," + 
/* 3763 */         userID;
/*      */       
/* 3765 */       JdbcConnector connector3Other = MilestoneHelper.getConnector(query3Other);
/* 3766 */       connector3Other.runQuery();
/* 3767 */       connector3Other.close();
/*      */     } 
/*      */ 
/*      */     
/* 3771 */     return bom;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vector getSelectionNotepadList(int UserId, Notepad notepad, Context context) {
/* 3776 */     Vector companies = MilestoneHelper.getUserCompanies(context);
/* 3777 */     Company company = null;
/* 3778 */     Vector precache = new Vector();
/* 3779 */     Selection selection = null;
/* 3780 */     String query = "";
/*      */     
/* 3782 */     String queryReset = "";
/*      */     
/* 3784 */     int maxRecords = 225;
/*      */     
/* 3786 */     if (notepad != null) {
/* 3787 */       maxRecords = notepad.getMaxRecords();
/*      */     }
/* 3789 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*      */       
/* 3791 */       query = notepad.getSearchQuery();
/* 3792 */       queryReset = new String(query);
/* 3793 */       query = String.valueOf(query) + notepad.getOrderBy();
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 3800 */       query = String.valueOf(getDefaultQuery(context)) + " ORDER BY artist, title, selection_no, street_date ";
/* 3801 */       queryReset = new String(query);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3808 */     User user = (User)context.getSessionValue("user");
/* 3809 */     String userDefaultsApplied = (String)context.getSessionValue("UserDefaultsApplied");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3815 */     if (userDefaultsApplied != null && userDefaultsApplied.equalsIgnoreCase("true")) {
/*      */       
/* 3817 */       user.SS_searchInitiated = false;
/*      */       
/* 3819 */       if (notepad != null) {
/*      */ 
/*      */         
/* 3822 */         notepad.setSelected(null);
/* 3823 */         notepad.setAllContents(null);
/*      */         
/* 3825 */         Notepad schNotepad = MilestoneHelper.getNotepadFromSession(1, context);
/* 3826 */         if (schNotepad != null) {
/*      */           
/* 3828 */           schNotepad.setSelected(null);
/* 3829 */           schNotepad.setAllContents(null);
/*      */         } 
/*      */       } 
/* 3832 */       context.removeSessionValue("UserDefaultsApplied");
/*      */     } 
/* 3834 */     if (user != null && !user.SS_searchInitiated) {
/*      */       
/* 3836 */       query = getInstance().getSelectionNotepadQueryUserDefaults(context);
/* 3837 */       queryReset = new String(query);
/* 3838 */       String order = getInstance().getSelectionNotepadQueryUserDefaultsOrderBy(context);
/*      */       
/* 3840 */       if (notepad != null) {
/*      */         
/* 3842 */         notepad.setSearchQuery(query);
/* 3843 */         notepad.setOrderBy(order);
/*      */       } 
/*      */       
/* 3846 */       query = String.valueOf(query) + order;
/*      */     } 
/*      */ 
/*      */     
/* 3850 */     if (user != null && user.SS_searchInitiated && context.getSessionValue("ResetSelectionSortOrder") != null) {
/*      */       
/* 3852 */       String orderReset = getInstance().getSelectionNotepadQueryUserDefaultsOrderBy(context);
/*      */       
/* 3854 */       if (notepad != null) {
/*      */         
/* 3856 */         notepad.setSearchQuery(queryReset);
/* 3857 */         notepad.setOrderBy(orderReset);
/*      */       } 
/* 3859 */       query = String.valueOf(queryReset) + orderReset;
/* 3860 */       user.SS_searchInitiated = true;
/* 3861 */       context.removeSessionValue("ResetSelectionSortOrder");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3866 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 3867 */     connector.setMaxRows(maxRecords);
/* 3868 */     connector.runQuery();
/*      */     
/* 3870 */     while (connector.more()) {
/*      */       
/* 3872 */       selection = new Selection();
/*      */ 
/*      */       
/* 3875 */       selection.setSelectionID(connector.getIntegerField("release_id"));
/*      */       
/* 3877 */       selection.setTitle(connector.getField("title", ""));
/*      */       
/* 3879 */       selection.setArtist(connector.getField("artist", ""));
/*      */       
/* 3881 */       selection.setUpc(connector.getField("upc", ""));
/*      */       
/* 3883 */       selection.setSelectionConfig(
/* 3884 */           getSelectionConfigObject(connector.getField("configuration"), 
/* 3885 */             Cache.getSelectionConfigs()));
/*      */ 
/*      */       
/* 3888 */       selection.setSelectionSubConfig(getSelectionSubConfigObject(connector.getField("sub_configuration"), selection.getSelectionConfig()));
/* 3889 */       selection.setPrefixID((PrefixCode)getLookupObject(connector.getField("prefix"), Cache.getPrefixCodes()));
/* 3890 */       selection.setSelectionNo(connector.getField("selection_no"));
/*      */       
/* 3892 */       selection.setIsDigital(connector.getBoolean("digital_flag"));
/*      */       
/* 3894 */       if (selection.getIsDigital()) {
/*      */         
/* 3896 */         String digitalReleaseDateString = connector.getFieldByName("street_date");
/* 3897 */         if (digitalReleaseDateString != null) {
/* 3898 */           selection.setDigitalRlsDateString(digitalReleaseDateString);
/* 3899 */           selection.setDigitalRlsDate(MilestoneHelper.getDatabaseDate(
/* 3900 */                 digitalReleaseDateString));
/*      */         } 
/*      */       } else {
/*      */         
/* 3904 */         String streetDateString = connector.getFieldByName("street_date");
/* 3905 */         if (streetDateString != null) {
/* 3906 */           selection.setStreetDateString(streetDateString);
/* 3907 */           selection.setStreetDate(MilestoneHelper.getDatabaseDate(streetDateString));
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3914 */       selection.setArchimedesId(connector.getInt("Archimedes_id", -1));
/* 3915 */       selection.setReleaseFamilyId(connector.getInt("Release_Family_id", -1));
/*      */ 
/*      */ 
/*      */       
/* 3919 */       precache.add(selection);
/* 3920 */       selection = null;
/* 3921 */       connector.next();
/*      */     } 
/*      */     
/* 3924 */     connector.close();
/* 3925 */     company = null;
/*      */     
/* 3927 */     if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY title, artist, selection_no, street_date"))
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
/* 3948 */       if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY street_date, artist, title, selection_no"))
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
/* 3969 */         if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY street_date DESC, artist, title, selection_no"))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3975 */           if (notepad == null || !notepad.getOrderBy().equalsIgnoreCase(" ORDER BY title DESC, artist, selection_no, street_date"))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3981 */             if (notepad != null) notepad.getOrderBy().equalsIgnoreCase(" ORDER BY artist DESC, title, selection_no, street_date");
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
/* 4011 */     return precache;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNotepadTotalCount(Notepad notepad, Context context) {
/* 4016 */     int count = 0;
/* 4017 */     String query = "";
/* 4018 */     if (notepad != null && !notepad.getSearchQuery().equals("")) {
/*      */       
/* 4020 */       query = notepad.getSearchQuery();
/* 4021 */       query = String.valueOf(query) + notepad.getOrderBy();
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 4028 */       query = String.valueOf(getDefaultQuery(context)) + " ORDER BY artist, title, selection_no, street_date ";
/*      */     } 
/*      */     
/* 4031 */     String newCountQuery = "";
/*      */     
/* 4033 */     int start = query.toUpperCase().indexOf("SELECT");
/* 4034 */     int end = query.toUpperCase().indexOf("FROM") - 1;
/* 4035 */     int endLength = query.toUpperCase().indexOf("ORDER BY") - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 4041 */       newCountQuery = String.valueOf(query.substring(start, start + 6)) + " count(*) as totalRecords " + query.substring(end, endLength);
/*      */       
/* 4043 */       JdbcConnector connector = MilestoneHelper.getConnector(newCountQuery);
/* 4044 */       connector.runQuery();
/*      */       
/* 4046 */       if (connector.more()) {
/* 4047 */         count = connector.getIntegerField("totalRecords");
/*      */       }
/* 4049 */       connector.close();
/*      */     }
/* 4051 */     catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4059 */     return count;
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
/* 4071 */     User user = (User)context.getSessionValue("user");
/*      */     
/* 4073 */     if (notepad != null)
/*      */     {
/*      */       
/* 4076 */       setSelectionNotepadQueryHelper(context, notepad, form, user);
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
/* 4094 */     String artistSearch = context.getParameter("macArtistSearch");
/*      */     
/* 4096 */     String titleSearch = context.getParameter("macTitleSearch");
/*      */     
/* 4098 */     String selectionNoSearch = context.getParameter("macSelectionSearch");
/*      */     
/* 4100 */     String prefixIDSearch = context.getParameter("macPrefixSearch");
/*      */     
/* 4102 */     String upcSearch = context.getParameter("macUPCSearch");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4107 */     upcSearch = MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(upcSearch, "UPC", false, false);
/*      */ 
/*      */ 
/*      */     
/* 4111 */     String streetDateSearch = "";
/* 4112 */     String streetDateSearch2 = context.getParameter("macStreetDateSearch");
/* 4113 */     if (streetDateSearch2 != null && !streetDateSearch2.equals("")) {
/* 4114 */       StringTokenizer st = new StringTokenizer(streetDateSearch2, "/");
/* 4115 */       String token = "";
/* 4116 */       while (st.hasMoreTokens()) {
/* 4117 */         token = st.nextToken();
/* 4118 */         if (!token.equals("*") && token.length() == 1)
/* 4119 */           token = "0" + token; 
/* 4120 */         if (streetDateSearch.length() == 0) {
/* 4121 */           streetDateSearch = String.valueOf(streetDateSearch) + token; continue;
/*      */         } 
/* 4123 */         streetDateSearch = String.valueOf(streetDateSearch) + "/" + token;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4128 */     String streetEndDateSearch = "";
/* 4129 */     String streetEndDateSearch2 = context.getParameter("macStreetEndDateSearch");
/* 4130 */     if (streetEndDateSearch2 != null && !streetEndDateSearch2.equals("")) {
/* 4131 */       StringTokenizer st = new StringTokenizer(streetEndDateSearch2, "/");
/* 4132 */       String token = "";
/* 4133 */       while (st.hasMoreTokens()) {
/* 4134 */         token = st.nextToken();
/* 4135 */         if (!token.equals("*") && token.length() == 1)
/* 4136 */           token = "0" + token; 
/* 4137 */         if (streetEndDateSearch.length() == 0) {
/* 4138 */           streetEndDateSearch = String.valueOf(streetEndDateSearch) + token; continue;
/*      */         } 
/* 4140 */         streetEndDateSearch = String.valueOf(streetEndDateSearch) + "/" + token;
/*      */       } 
/*      */     } 
/*      */     
/* 4144 */     String configSearch = context.getParameter("ConfigSearch");
/*      */     
/* 4146 */     String subconfigSearch = context.getParameter("macSubconfigSearch");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4151 */     String showAllSearch = context.getParameter("ShowAllSearch");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4157 */     String contactSearch = context.getParameter("ContactSearch");
/*      */ 
/*      */     
/* 4160 */     String familySearch = "";
/*      */     
/* 4162 */     if (context.getParameter("FamilySearch") == null || context.getParameter("FamilySearch").equals("") || context.getParameter("FamilySearch").equals("0") || context.getParameter("FamilySearch").equals("-1")) {
/*      */       
/* 4164 */       FormDropDownMenu familyDD = (FormDropDownMenu)form.getElement("FamilySearch");
/* 4165 */       for (int f = 0; f < familyDD.getValueList().length; f++) {
/* 4166 */         if (f == 0) {
/* 4167 */           familySearch = String.valueOf(familySearch) + familyDD.getValueList()[f];
/*      */         } else {
/* 4169 */           familySearch = String.valueOf(familySearch) + "," + familyDD.getValueList()[f];
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 4174 */       familySearch = context.getParameter("FamilySearch");
/*      */     } 
/*      */     
/* 4177 */     String environmentSearch = "";
/* 4178 */     environmentSearch = context.getParameter("EnvironmentSearch");
/*      */     
/* 4180 */     String productType = context.getParameter("ProdType");
/* 4181 */     String productTypeSearch = "";
/* 4182 */     if (productType.equalsIgnoreCase("physical")) {
/* 4183 */       productTypeSearch = "0";
/* 4184 */     } else if (productType.equalsIgnoreCase("digital")) {
/* 4185 */       productTypeSearch = "1";
/*      */     } 
/*      */     
/* 4188 */     String companyVar = context.getParameter("company");
/* 4189 */     String companyTypeSearch = "";
/* 4190 */     String labelSearch = "";
/* 4191 */     if (productType.equalsIgnoreCase("All")) {
/*      */ 
/*      */       
/* 4194 */       labelSearch = "0";
/*      */     
/*      */     }
/* 4197 */     else if (productType.equalsIgnoreCase("Select")) {
/*      */       
/* 4199 */       labelSearch = context.getParameter("macLabelSearch");
/*      */     } 
/*      */ 
/*      */     
/* 4203 */     String labelVar = context.getParameter("Label");
/* 4204 */     String labelTypeSearch = "";
/* 4205 */     String companySearch = "";
/* 4206 */     if (productType.equalsIgnoreCase("All")) {
/*      */ 
/*      */       
/* 4209 */       companySearch = "0";
/*      */     
/*      */     }
/* 4212 */     else if (productType.equalsIgnoreCase("Select")) {
/*      */       
/* 4214 */       companySearch = context.getParameter("macCompanySearch");
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
/* 4232 */     String projectIDSearch = "";
/* 4233 */     projectIDSearch = context.getParameter("ProjectIDSearch");
/*      */     
/* 4235 */     boolean startsWithAsterisk = false;
/* 4236 */     boolean endsWithAsterisk = false;
/* 4237 */     boolean containsNA = false;
/*      */ 
/*      */ 
/*      */     
/* 4241 */     if (streetDateSearch.indexOf("*") > -1)
/*      */     {
/*      */       
/* 4244 */       startsWithAsterisk = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4252 */     if (streetEndDateSearch.indexOf("*") > -1)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4258 */       endsWithAsterisk = true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4263 */     if (streetDateSearch.toUpperCase().indexOf("N/A") > -1)
/*      */     {
/* 4265 */       containsNA = true;
/*      */     }
/*      */     
/* 4268 */     if (selectionNoSearch.length() > 0) {
/*      */       
/* 4270 */       context.putSessionValue("selectionNotepadColumn", "1");
/*      */     }
/* 4272 */     else if (upcSearch.length() > 0) {
/*      */       
/* 4274 */       context.putSessionValue("selectionNotepadColumn", "2");
/*      */     }
/* 4276 */     else if (prefixIDSearch.length() > 0) {
/*      */       
/* 4278 */       context.putSessionValue("selectionNotepadColumn", "3");
/*      */     }
/*      */     else {
/*      */       
/* 4282 */       context.removeSessionValue("selectionNotepadColumn");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4289 */     if (user != null && user.getPreferences() != null && !user.SS_searchInitiated) {
/*      */ 
/*      */       
/* 4292 */       if (user.getPreferences().getSelectionStatus() > 0) {
/*      */         
/* 4294 */         showAllSearch = "true";
/* 4295 */         user.SS_showAllSearch = "true";
/*      */       } 
/*      */       
/* 4298 */       if (user.getPreferences().getSelectionReleasingFamily() > 0) {
/*      */         
/* 4300 */         familySearch = String.valueOf(user.getPreferences().getSelectionReleasingFamily());
/* 4301 */         user.SS_familySearch = familySearch;
/*      */       } 
/*      */       
/* 4304 */       if (user.getPreferences().getSelectionEnvironment() > 0) {
/*      */         
/* 4306 */         environmentSearch = String.valueOf(user.getPreferences().getSelectionEnvironment());
/* 4307 */         user.SS_environmentSearch = environmentSearch;
/*      */       } 
/*      */       
/* 4310 */       if (user.getPreferences().getSelectionLabelContact() > 0) {
/*      */         
/* 4312 */         contactSearch = String.valueOf(user.getPreferences().getSelectionLabelContact());
/* 4313 */         user.SS_contactSearch = contactSearch;
/*      */       } 
/*      */       
/* 4316 */       if (user.getPreferences().getSelectionProductType() > -1) {
/* 4317 */         productTypeSearch = "";
/* 4318 */         user.SS_productTypeSearch = "";
/* 4319 */         if (user.getPreferences().getSelectionProductType() == 0) {
/*      */           
/* 4321 */           productTypeSearch = "0";
/* 4322 */           user.SS_productTypeSearch = "physical";
/*      */         } 
/* 4324 */         if (user.getPreferences().getSelectionProductType() == 1) {
/*      */           
/* 4326 */           productTypeSearch = "1";
/* 4327 */           user.SS_productTypeSearch = "digital";
/*      */         } 
/* 4329 */         if (user.getPreferences().getSelectionProductType() == 2) {
/* 4330 */           user.SS_productTypeSearch = "both";
/*      */         }
/*      */       } 
/*      */       
/* 4334 */       UserManager.getInstance().setUserPreferenceReleaseCalendar(user);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4341 */     StringBuffer query = new StringBuffer();
/* 4342 */     query.append("SELECT release_id, title, artist, configuration, sub_configuration, upc, prefix, selection_no, digital_flag, street_date =  CASE digital_flag  WHEN 0 THEN street_date   WHEN 1 THEN digital_rls_date  END ,archimedes_id, Release_Family_id from vi_Release_Header WHERE ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4353 */     if (environmentSearch == null || environmentSearch.equals("") || environmentSearch.equals("0") || environmentSearch.equals("-1")) {
/*      */       
/* 4355 */       FormDropDownMenu envDD = (FormDropDownMenu)form.getElement(
/* 4356 */           "EnvironmentSearch");
/* 4357 */       query.append(" environment_id in (");
/* 4358 */       for (int f = 0; f < envDD.getValueList().length; f++) {
/* 4359 */         if (f == 0) {
/* 4360 */           query.append(envDD.getValueList()[f]);
/*      */         } else {
/* 4362 */           query.append("," + envDD.getValueList()[f]);
/*      */         } 
/* 4364 */       }  query.append(")");
/*      */     } else {
/*      */       
/* 4367 */       query.append(" environment_id in (" + environmentSearch + ")");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4373 */     if (showAllSearch == null || !showAllSearch.equals("true")) {
/* 4374 */       query.append(" AND NOT ( status = 'CLOSED' OR  status = 'CANCEL' )");
/*      */     }
/* 4376 */     if (artistSearch != null && !artistSearch.equals("")) {
/* 4377 */       query.append(" AND artist " + MilestoneHelper.setWildCardsEscapeSingleQuotes(artistSearch));
/*      */     }
/* 4379 */     if (titleSearch != null && !titleSearch.equals("")) {
/* 4380 */       query.append(" AND title " + MilestoneHelper.setWildCardsEscapeSingleQuotes(titleSearch));
/*      */     }
/* 4382 */     if (selectionNoSearch != null && !selectionNoSearch.equals("")) {
/* 4383 */       query.append(" AND selection_no " + MilestoneHelper.setWildCardsEscapeSingleQuotes(selectionNoSearch));
/*      */     }
/* 4385 */     if (prefixIDSearch != null && !prefixIDSearch.equals("")) {
/* 4386 */       query.append(" AND prefix " + MilestoneHelper.setWildCardsEscapeSingleQuotes(prefixIDSearch));
/*      */     }
/* 4388 */     if (upcSearch != null && !upcSearch.equals("")) {
/* 4389 */       query.append(" AND upc " + MilestoneHelper.setWildCardsEscapeSingleQuotes(upcSearch));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4396 */     if (streetEndDateSearch != null && !streetEndDateSearch.equals("")) {
/*      */       
/* 4398 */       String streetStartDateSearch = "";
/* 4399 */       if (streetDateSearch != null && !streetDateSearch.equals(""))
/*      */       {
/* 4401 */         query.append(" AND (   ( digital_flag = 0 and street_date between '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "')");
/* 4402 */         query.append("      OR ( digital_flag = 1 and digital_rls_date between '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "'))");
/*      */       }
/*      */       else
/*      */       {
/* 4406 */         query.append(" AND (   ( digital_flag = 0 and street_date between '01/01/1900' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "')");
/* 4407 */         query.append("      OR ( digital flag = 1 and digital_rls_date between '01/01/1900' and '" + MilestoneHelper.escapeSingleQuotes(streetEndDateSearch) + "'))");
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
/* 4425 */     else if (streetDateSearch != null && !streetDateSearch.equals("")) {
/*      */ 
/*      */ 
/*      */       
/* 4429 */       if (containsNA) {
/*      */         
/* 4431 */         query.append(" AND ( ( digital_flag = 0 and street_date IS NULL) OR ( digital_flag = 1 and digital_rls_date IS NULL))");
/*      */       }
/* 4433 */       else if (startsWithAsterisk) {
/*      */ 
/*      */         
/* 4436 */         String cleanStreetDate = streetDateSearch.replace('*', '%');
/* 4437 */         query.append(" AND (    ( digital_flag = 0 and CONVERT(varchar, street_date, 1) LIKE '" + cleanStreetDate + "')");
/* 4438 */         query.append("       OR ( digital_flag = 1 and CONVERT(varchar, digital_rls_date, 1) LIKE '" + cleanStreetDate + "'))");
/*      */       }
/*      */       else {
/*      */         
/* 4442 */         query.append(" AND (   ( digital_flag = 0 and street_date = '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "')");
/* 4443 */         query.append("      OR ( digital_flag = 1 and digital_rls_date = '" + MilestoneHelper.escapeSingleQuotes(streetDateSearch) + "'))");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4449 */     if (configSearch != null && !configSearch.equals("") && !configSearch.equals("0")) {
/* 4450 */       query.append(" AND configuration = '" + configSearch + "'");
/*      */     }
/*      */     
/* 4453 */     if (subconfigSearch != null && !subconfigSearch.equals("") && !subconfigSearch.equals("0")) {
/* 4454 */       query.append(" AND sub_configuration = '" + subconfigSearch + "'");
/*      */     }
/*      */ 
/*      */     
/* 4458 */     if (labelSearch != null && !labelSearch.equals("") && !labelSearch.equals("0")) {
/* 4459 */       query.append(" AND label_id in (" + labelSearch + ")");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4465 */     if (companySearch != null && !companySearch.equals("") && !companySearch.equals("0")) {
/* 4466 */       query.append(" AND company_id in (" + companySearch + ")");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4473 */     addReleasingFamilyLabelFamilySelect("FamilySearch", context, query, form);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4479 */     if (!productTypeSearch.equalsIgnoreCase("")) {
/* 4480 */       query.append(" AND digital_flag = " + productTypeSearch);
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
/* 4501 */     if (contactSearch != null && !contactSearch.equals("") && !contactSearch.equals("0")) {
/* 4502 */       query.append(" AND contact_id = '" + contactSearch + "'");
/*      */     }
/*      */     
/* 4505 */     if (projectIDSearch != null && !projectIDSearch.equals("")) {
/* 4506 */       query.append(" AND project_no " + MilestoneHelper.setWildCardsEscapeSingleQuotes(projectIDSearch));
/*      */     }
/*      */ 
/*      */     
/* 4510 */     query.append(ShowOrHideDigitalProductGet(user));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4516 */     String order = "";
/*      */ 
/*      */ 
/*      */     
/* 4520 */     NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
/*      */ 
/*      */     
/* 4523 */     boolean descending = false;
/* 4524 */     if (notepad.getOrderBy().indexOf(" DESC ") != -1) {
/* 4525 */       descending = true;
/*      */     }
/* 4527 */     if (artistSearch.length() > 0) {
/*      */       
/* 4529 */       notepadSortOrder.setSelectionOrderCol("Artist");
/* 4530 */       notepadSortOrder.setShowGroupButtons(true);
/* 4531 */       if (!descending) {
/*      */         
/* 4533 */         order = " ORDER BY artist, title, selection_no, street_date";
/* 4534 */         notepadSortOrder.setSelectionOrderColNo(0);
/*      */       } else {
/* 4536 */         order = " ORDER BY artist DESC , title, selection_no, street_date";
/* 4537 */         notepadSortOrder.setSelectionOrderColNo(7);
/*      */       }
/*      */     
/* 4540 */     } else if (titleSearch.length() > 0) {
/*      */       
/* 4542 */       if (!descending) {
/*      */         
/* 4544 */         order = " ORDER BY title, artist, selection_no, street_date";
/* 4545 */         notepadSortOrder.setSelectionOrderColNo(1);
/*      */       } else {
/* 4547 */         order = " ORDER BY title DESC , artist, selection_no, street_date";
/* 4548 */         notepadSortOrder.setSelectionOrderColNo(8);
/*      */       } 
/* 4550 */       notepadSortOrder.setSelectionOrderCol("Title");
/* 4551 */       notepadSortOrder.setShowGroupButtons(true);
/*      */     }
/* 4553 */     else if (streetDateSearch.length() > 0) {
/*      */       
/* 4555 */       if (!descending) {
/*      */         
/* 4557 */         order = " ORDER BY street_date, artist, title, selection_no";
/* 4558 */         notepadSortOrder.setSelectionOrderColNo(5);
/*      */       } else {
/* 4560 */         order = " ORDER BY street_date DESC , artist, title, selection_no";
/* 4561 */         notepadSortOrder.setSelectionOrderColNo(5);
/*      */       } 
/* 4563 */       notepadSortOrder.setSelectionOrderCol("Str Dt");
/*      */     }
/* 4565 */     else if (selectionNoSearch.length() > 0) {
/*      */       
/* 4567 */       if (!descending) {
/*      */         
/* 4569 */         order = " ORDER BY selection_no";
/* 4570 */         notepadSortOrder.setSelectionOrderColNo(2);
/*      */       } else {
/* 4572 */         order = " ORDER BY selection_no DESC ";
/* 4573 */         notepadSortOrder.setSelectionOrderColNo(2);
/*      */       } 
/* 4575 */       notepadSortOrder.setSelectionOrderCol("Local Prod #");
/*      */     }
/* 4577 */     else if (prefixIDSearch.length() > 0) {
/*      */       
/* 4579 */       if (!descending) {
/*      */         
/* 4581 */         order = " ORDER BY prefix,selection_no";
/* 4582 */         notepadSortOrder.setSelectionOrderColNo(4);
/*      */       } else {
/* 4584 */         order = " ORDER BY prefix DESC ,selection_no";
/* 4585 */         notepadSortOrder.setSelectionOrderColNo(4);
/*      */       } 
/* 4587 */       notepadSortOrder.setSelectionOrderCol("Prefix");
/*      */     }
/* 4589 */     else if (upcSearch.length() > 0) {
/*      */       
/* 4591 */       if (!descending) {
/*      */         
/* 4593 */         order = " ORDER BY upc,selection_no";
/* 4594 */         notepadSortOrder.setSelectionOrderColNo(3);
/*      */       } else {
/* 4596 */         order = " ORDER BY upc DESC ,selection_no";
/* 4597 */         notepadSortOrder.setSelectionOrderColNo(3);
/*      */       } 
/* 4599 */       notepadSortOrder.setSelectionOrderCol("UPC");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 4607 */     else if (user != null && !user.SS_searchInitiated) {
/*      */       
/* 4609 */       order = getSelectionNotepadQueryUserDefaultsOrderBy(context);
/*      */     }
/* 4611 */     else if (!notepad.getOrderBy().equals("") && (
/* 4612 */       notepadSortOrder.getSelectionOrderCol().equals("Artist") || 
/* 4613 */       notepadSortOrder.getSelectionOrderCol().equals("Title") || 
/* 4614 */       notepadSortOrder.getSelectionOrderCol().equals("Str Dt"))) {
/* 4615 */       order = notepad.getOrderBy();
/*      */     } else {
/*      */       
/* 4618 */       if (!descending) {
/*      */         
/* 4620 */         order = " ORDER BY artist, title, selection_no, street_date ";
/* 4621 */         notepadSortOrder.setSelectionOrderColNo(0);
/*      */       } else {
/* 4623 */         order = " ORDER BY artist DESC , title, selection_no, street_date ";
/* 4624 */         notepadSortOrder.setSelectionOrderColNo(7);
/*      */       } 
/* 4626 */       notepadSortOrder.setSelectionOrderCol("Artist");
/* 4627 */       notepadSortOrder.setShowGroupButtons(true);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4634 */     if (user != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4639 */       Hashtable prevSearchValues = new Hashtable();
/* 4640 */       prevSearchValues.put("SS_artistSearch", user.SS_artistSearch);
/* 4641 */       prevSearchValues.put("SS_titleSearch ", user.SS_titleSearch);
/* 4642 */       prevSearchValues.put("SS_selectionNoSearch", user.SS_selectionNoSearch);
/* 4643 */       prevSearchValues.put("SS_prefixIDSearch", user.SS_prefixIDSearch);
/* 4644 */       prevSearchValues.put("SS_upcSearch", user.SS_upcSearch);
/* 4645 */       prevSearchValues.put("SS_streetDateSearch", user.SS_streetDateSearch);
/* 4646 */       prevSearchValues.put("SS_streetEndDateSearch", user.SS_streetEndDateSearch);
/* 4647 */       prevSearchValues.put("SS_configSearch", user.SS_configSearch);
/* 4648 */       prevSearchValues.put("SS_subconfigSearch", user.SS_subconfigSearch);
/* 4649 */       prevSearchValues.put("SS_labelSearch", user.SS_labelSearch);
/* 4650 */       prevSearchValues.put("SS_companySearch", user.SS_companySearch);
/* 4651 */       prevSearchValues.put("SS_contactSearch", user.SS_contactSearch);
/* 4652 */       prevSearchValues.put("SS_familySearch", user.SS_familySearch);
/* 4653 */       prevSearchValues.put("SS_environmentSearch", user.SS_environmentSearch);
/* 4654 */       prevSearchValues.put("SS_projectIDSearch", user.SS_projectIDSearch);
/* 4655 */       prevSearchValues.put("SS_productTypeSearch", user.SS_productTypeSearch);
/* 4656 */       prevSearchValues.put("SS_showAllSearch", user.SS_showAllSearch);
/* 4657 */       context.putDelivery("prevSearchValues", prevSearchValues);
/*      */ 
/*      */       
/* 4660 */       user.SS_artistSearch = artistSearch;
/* 4661 */       user.SS_titleSearch = titleSearch;
/* 4662 */       user.SS_selectionNoSearch = selectionNoSearch;
/* 4663 */       user.SS_prefixIDSearch = prefixIDSearch;
/* 4664 */       user.SS_upcSearch = upcSearch;
/* 4665 */       user.SS_streetDateSearch = streetDateSearch;
/* 4666 */       user.SS_streetEndDateSearch = streetEndDateSearch;
/* 4667 */       user.SS_configSearch = configSearch;
/* 4668 */       user.SS_subconfigSearch = subconfigSearch;
/* 4669 */       user.SS_labelSearch = labelSearch;
/* 4670 */       user.SS_companySearch = companySearch;
/* 4671 */       user.SS_contactSearch = contactSearch;
/* 4672 */       user.SS_familySearch = familySearch;
/* 4673 */       user.SS_environmentSearch = environmentSearch;
/* 4674 */       user.SS_projectIDSearch = projectIDSearch;
/* 4675 */       user.SS_productTypeSearch = productType;
/* 4676 */       user.SS_showAllSearch = showAllSearch;
/*      */       
/* 4678 */       user.RC_environment = environmentSearch;
/* 4679 */       user.RC_releasingFamily = familySearch;
/* 4680 */       user.RC_labelContact = contactSearch;
/* 4681 */       user.RC_productType = MilestoneHelper_2.convertProductTypeToReleaseCalendar(productType);
/*      */     } 
/*      */     
/* 4684 */     notepad.setSearchQuery(query.toString());
/* 4685 */     notepad.setOrderBy(order);
/* 4686 */     notepadSortOrder.setSelectionOrderBy(order);
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
/* 4700 */     User user = (User)context.getSessionValue("user");
/*      */ 
/*      */     
/* 4703 */     Notepad notepad = null;
/*      */     
/* 4705 */     notepad = MilestoneHelper.getNotepadFromSession(0, context);
/*      */     
/* 4707 */     if (notepad != null) {
/*      */ 
/*      */       
/* 4710 */       String query = notepad.getSearchQuery();
/* 4711 */       String order = notepad.getOrderBy();
/* 4712 */       String orderBy = "";
/* 4713 */       String orderCol = "";
/* 4714 */       int orderColNo = 0;
/* 4715 */       boolean showGrpButs = true;
/*      */       
/* 4717 */       NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
/* 4718 */       if (notepadSortOrder != null) {
/*      */         
/* 4720 */         orderBy = notepadSortOrder.getSelectionOrderBy();
/* 4721 */         showGrpButs = notepadSortOrder.getShowGroupButtons();
/* 4722 */         orderCol = notepadSortOrder.getSelectionOrderCol();
/* 4723 */         orderColNo = notepadSortOrder.getSelectionOrderColNo();
/*      */       } 
/*      */ 
/*      */       
/* 4727 */       Form searchForm = new Form(application, "searchForm", 
/* 4728 */           application.getInfrastructure().getServletURL(), 
/* 4729 */           "POST");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4737 */       Vector families = SelectionHandler.filterCSO(ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context));
/* 4738 */       FormDropDownMenu Family = MilestoneHelper.getCorporateStructureDropDown("FamilySearch", families, "-1", false, true);
/* 4739 */       searchForm.addElement(Family);
/*      */ 
/*      */       
/* 4742 */       Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 4743 */       Vector myCompanies = MilestoneHelper.getUserCompanies(context);
/* 4744 */       environments = SelectionHandler.filterSelectionEnvironments(myCompanies);
/*      */ 
/*      */       
/* 4747 */       environments = MilestoneHelper.removeUnusedCSO(environments, context, -1);
/*      */ 
/*      */       
/* 4750 */       FormDropDownMenu envMenu = MilestoneHelper.getCorporateStructureDropDown("EnvironmentSearch", environments, "-1", false, true);
/* 4751 */       searchForm.addElement(envMenu);
/*      */ 
/*      */       
/* 4754 */       searchForm.setValues(context);
/*      */ 
/*      */       
/* 4757 */       setSelectionNotepadQueryHelper(context, notepad, searchForm, user);
/*      */ 
/*      */       
/* 4760 */       if (getNotepadTotalCount(notepad, context) > 0)
/*      */       {
/* 4762 */         return true;
/*      */       }
/* 4764 */       notepad.setSearchQuery(query);
/* 4765 */       notepad.setOrderBy(order);
/* 4766 */       if (notepadSortOrder != null) {
/*      */         
/* 4768 */         notepadSortOrder.setSelectionOrderBy(orderBy);
/* 4769 */         notepadSortOrder.setShowGroupButtons(showGrpButs);
/* 4770 */         notepadSortOrder.setSelectionOrderCol(orderCol);
/* 4771 */         notepadSortOrder.setSelectionOrderColNo(orderColNo);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4776 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LookupObject getLookupObject(String abbreviation, Vector lookupVector) {
/* 4785 */     for (int j = 0; j < lookupVector.size(); j++) {
/*      */       
/* 4787 */       LookupObject lookupObject = (LookupObject)lookupVector.get(j);
/*      */       
/* 4789 */       if (lookupObject.getAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 4791 */         return lookupObject;
/*      */       }
/*      */     } 
/*      */     
/* 4795 */     return null;
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
/* 4807 */     String lookupValue = "";
/*      */     
/* 4809 */     if (lookupObject != null)
/*      */     {
/* 4811 */       lookupValue = lookupObject.getAbbreviation();
/*      */     }
/*      */     
/* 4814 */     return lookupValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SelectionConfiguration getSelectionConfigObject(String abbreviation, Vector configs) {
/* 4822 */     for (int j = 0; j < configs.size(); j++) {
/*      */       
/* 4824 */       SelectionConfiguration selectionConfiguration = (SelectionConfiguration)configs.get(j);
/*      */       
/* 4826 */       if (selectionConfiguration.getSelectionConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 4828 */         return selectionConfiguration;
/*      */       }
/*      */     } 
/*      */     
/* 4832 */     return new SelectionConfiguration("", "");
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
/* 4846 */     Vector subConfigs = config.getSubConfigurations();
/*      */     
/* 4848 */     for (int j = 0; j < subConfigs.size(); j++) {
/*      */       
/* 4850 */       SelectionSubConfiguration subConfig = (SelectionSubConfiguration)subConfigs.get(j);
/*      */       
/* 4852 */       if (subConfig.getSelectionSubConfigurationAbbreviation().equalsIgnoreCase(abbreviation))
/*      */       {
/* 4854 */         return subConfig;
/*      */       }
/*      */     } 
/*      */     
/* 4858 */     return new SelectionSubConfiguration("", "", 2);
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
/* 4870 */     Vector labelUsers = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4875 */     int companyId = -1;
/*      */     
/* 4877 */     if (selection.getCompany() != null) {
/* 4878 */       companyId = selection.getCompany().getStructureID();
/*      */     }
/*      */     
/* 4881 */     String query = "sp_get_Selection_LabelContacts " + companyId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4895 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 4896 */     connector.runQuery();
/*      */     
/* 4898 */     while (connector.more()) {
/*      */       
/* 4900 */       User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
/* 4901 */       labelUsers.add(labelUser);
/* 4902 */       connector.next();
/*      */     } 
/*      */     
/* 4905 */     connector.close();
/*      */     
/* 4907 */     return labelUsers;
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
/* 4919 */     Vector labelUsers = new Vector();
/*      */     
/* 4921 */     int umlId = MilestoneHelper.getStructureId("uml", 1);
/* 4922 */     int enterpriseId = MilestoneHelper.getStructureId("Enterprise", 1);
/* 4923 */     int companyId = -1;
/*      */     
/* 4925 */     String companyClause = "( ";
/*      */     
/* 4927 */     Vector userCompanies = new Vector();
/*      */     
/* 4929 */     userCompanies = MilestoneHelper.getUserCompanies(context);
/*      */     
/* 4931 */     for (int i = 0; i < userCompanies.size(); i++) {
/*      */       
/* 4933 */       if (i == 0) {
/* 4934 */         companyClause = String.valueOf(companyClause) + " company_id = " + ((Company)userCompanies.get(i)).getStructureID();
/*      */       } else {
/* 4936 */         companyClause = String.valueOf(companyClause) + " OR company_id = " + ((Company)userCompanies.get(i)).getStructureID();
/*      */       } 
/* 4938 */     }  companyClause = String.valueOf(companyClause) + ")";
/*      */     
/* 4940 */     String query = "SELECT DISTINCT vi_User.Name,vi_User.Full_Name, vi_User.User_Id FROM vi_User, vi_User_Company WHERE (vi_User.User_ID = vi_User_Company.User_ID) AND (menu_access LIKE '[1,2]%') AND " + 
/*      */ 
/*      */ 
/*      */       
/* 4944 */       companyClause + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4950 */       " ORDER BY vi_User.Full_Name;";
/*      */ 
/*      */ 
/*      */     
/* 4954 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 4955 */     connector.runQuery();
/*      */     
/* 4957 */     while (connector.more()) {
/*      */       
/* 4959 */       User labelUser = UserManager.getInstance().getUser(connector.getIntegerField("user_id"));
/* 4960 */       labelUsers.add(labelUser);
/* 4961 */       connector.next();
/*      */     } 
/*      */     
/* 4964 */     connector.close();
/*      */     
/* 4966 */     return labelUsers;
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
/* 4983 */     Vector labelUsers = Cache.getInstance().getLabelUsers();
/* 4984 */     Vector retLabelUsers = new Vector();
/* 4985 */     Vector userCompanies = MilestoneHelper.getUserCompanies(context);
/* 4986 */     if (labelUsers != null && userCompanies != null)
/*      */     {
/* 4988 */       for (int i = 0; i < labelUsers.size(); i++) {
/* 4989 */         User user = (User)labelUsers.get(i);
/* 4990 */         if (user != null) {
/* 4991 */           for (int j = 0; j < userCompanies.size(); j++) {
/* 4992 */             int familyId = ((Company)userCompanies.get(j)).getParentEnvironment().getParentFamily().getStructureID();
/* 4993 */             if (familyId == user.getEmployedBy()) {
/* 4994 */               retLabelUsers.add(user);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/* 5001 */     return retLabelUsers;
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
/* 5064 */     boolean applied = false;
/*      */     
/* 5066 */     if (selection.getSelectionID() > 0) {
/*      */       
/* 5068 */       String query = "SELECT * FROM vi_release_detail WHERE release_id = " + 
/*      */         
/* 5070 */         selection.getSelectionID() + 
/* 5071 */         ";";
/*      */       
/* 5073 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 5074 */       connector.runQuery();
/* 5075 */       applied = connector.more();
/* 5076 */       connector.close();
/*      */     } 
/*      */     
/* 5079 */     return applied;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Notepad getSelectionNotepad(Context context, int userId, int type) {
/* 5088 */     Vector contents = new Vector();
/*      */     
/* 5090 */     if (MilestoneHelper.getNotepadFromSession(type, context) != null) {
/*      */ 
/*      */       
/* 5093 */       Notepad notepad = MilestoneHelper.getNotepadFromSession(type, context);
/*      */ 
/*      */       
/* 5096 */       if (notepad.getAllContents() == null || context.getSessionValue("ResetSelectionSortOrder") != null) {
/*      */ 
/*      */         
/* 5099 */         contents = getInstance().getSelectionNotepadList(userId, notepad, context);
/* 5100 */         notepad.setAllContents(contents, getNotepadTotalCount(notepad, context));
/*      */       } 
/*      */       
/* 5103 */       if (context.getSessionValue("selectionNotepadColumn") != null) {
/*      */         
/* 5105 */         if (context.getSessionValue("selectionNotepadColumn") == "1")
/*      */         {
/* 5107 */           notepad.setColumnNames(new String[] { "Artist", "Title", "Local Prod #", "Str Dt" });
/*      */         }
/* 5109 */         else if (context.getSessionValue("selectionNotepadColumn") == "2")
/*      */         {
/* 5111 */           notepad.setColumnNames(new String[] { "Artist", "Title", "UPC", "Str Dt" });
/*      */         }
/* 5113 */         else if (context.getSessionValue("selectionNotepadColumn") == "3")
/*      */         {
/* 5115 */           notepad.setColumnNames(new String[] { "Artist", "Title", "Prefix", "Str Dt" });
/*      */         }
/*      */         else
/*      */         {
/* 5119 */           notepad.setColumnNames(new String[] { "Artist", "Title", "Str Dt" });
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 5124 */         notepad.setColumnNames(new String[] { "Artist", "Title", "Str Dt" });
/*      */       } 
/*      */       
/* 5127 */       notepad.setNotePadType(type);
/* 5128 */       return notepad;
/*      */     } 
/*      */ 
/*      */     
/* 5132 */     String[] columnNames = { "Artist", "Title", "Str Dt" };
/*      */     
/* 5134 */     if (context.getSessionValue("selectionNotepadColumn") != null)
/*      */     {
/* 5136 */       if (context.getSessionValue("selectionNotepadColumn") == "1") {
/*      */         
/* 5138 */         columnNames = new String[] { "Artist", "Title", "Selection", "Str Dt" };
/*      */       }
/* 5140 */       else if (context.getSessionValue("selectionNotepadColumn") == "2") {
/*      */         
/* 5142 */         columnNames = new String[] { "Artist", "Title", "Upc", "Str Dt" };
/*      */       }
/* 5144 */       else if (context.getSessionValue("selectionNotepadColumn") == "3") {
/*      */         
/* 5146 */         columnNames = new String[] { "Artist", "Title", "Prefix", "Str Dt" };
/*      */       } 
/*      */     }
/*      */     
/* 5150 */     System.out.println("<<< new selection notepad ");
/* 5151 */     contents = getInstance().getSelectionNotepadList(userId, null, context);
/* 5152 */     Notepad newNotepad = new Notepad(contents, 0, 15, "Selections", type, columnNames);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5157 */     User user = (User)context.getSessionValue("user");
/* 5158 */     if (user != null && !user.SS_searchInitiated) {
/*      */       
/* 5160 */       String query = getInstance().getSelectionNotepadQueryUserDefaults(context);
/* 5161 */       String order = getInstance().getSelectionNotepadQueryUserDefaultsOrderBy(context);
/* 5162 */       newNotepad.setSearchQuery(query);
/* 5163 */       newNotepad.setOrderBy(order);
/*      */     } 
/* 5165 */     newNotepad.setPageStats(getNotepadTotalCount(newNotepad, context));
/* 5166 */     return newNotepad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getDefaultQuery(Context context) {
/* 5176 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 5180 */     Environment environment = null;
/* 5181 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/*      */ 
/*      */     
/* 5184 */     query.append("SELECT release_id, title, artist, configuration, sub_configuration, upc, prefix, selection_no, digital_flag, street_date =  CASE digital_flag  WHEN 0 THEN street_date   WHEN 1 THEN digital_rls_date  END ,archimedes_id, Release_Family_id from vi_Release_Header WHERE NOT ( status = 'CLOSED' OR  status = 'CANCEL' ) AND ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5219 */     if (environments.size() > 0) {
/* 5220 */       query.append(" environment_id in (");
/*      */     }
/* 5222 */     for (int i = 0; i < environments.size(); i++) {
/*      */       
/* 5224 */       environment = (Environment)environments.get(i);
/* 5225 */       if (environment != null)
/*      */       {
/* 5227 */         if (i == 0) {
/* 5228 */           query.append(environment.getStructureID());
/*      */         } else {
/* 5230 */           query.append("," + environment.getStructureID());
/*      */         } 
/*      */       }
/*      */     } 
/* 5234 */     if (environments.size() > 0) {
/* 5235 */       query.append(" )");
/*      */     }
/*      */     
/* 5238 */     Vector rFamilies = ReleasingFamily.getUserReleasingFamiliesVectorOfReleasingFamilies(context);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5256 */     User user = (User)context.getSession().getAttribute("user");
/* 5257 */     Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
/*      */ 
/*      */     
/* 5260 */     addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*      */     
/* 5262 */     if (rFamilies != null && rFamilies.size() > 0) {
/*      */       
/* 5264 */       query.append(" AND (");
/* 5265 */       for (int i = 0; i < rFamilies.size(); i++) {
/*      */         
/* 5267 */         ReleasingFamily rf = (ReleasingFamily)rFamilies.get(i);
/* 5268 */         if (i > 0)
/* 5269 */           query.append(" OR "); 
/* 5270 */         query.append("(Release_Family_id = " + rf.getReleasingFamilyId());
/* 5271 */         addLabelFamilySelect(Integer.toString(rf.getReleasingFamilyId()), relFamilyLabelFamilyHash, query);
/* 5272 */         query.append(")");
/*      */       } 
/* 5274 */       query.append(") ");
/*      */     } else {
/*      */       
/* 5277 */       query.append(" AND (Release_Family_id = -1 and family_id = -1) ");
/*      */     } 
/*      */ 
/*      */     
/* 5281 */     query.append(ShowOrHideDigitalProductGet(user));
/*      */ 
/*      */     
/* 5284 */     log.log("selection query " + query.toString());
/* 5285 */     return query.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String ShowOrHideDigitalProductGet(User user) {
/* 5295 */     String digitalProdStr = "";
/*      */ 
/*      */ 
/*      */     
/* 5299 */     if (user == null || user.getAdministrator() != 1) {
/* 5300 */       digitalProdStr = " AND (digital_flag = 0) ";
/*      */     }
/* 5302 */     return digitalProdStr;
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
/* 5318 */     query = "sp_get_Sequence selection_no";
/*      */     
/* 5320 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 5321 */     connector.runQuery();
/*      */     
/* 5323 */     String prefixString = "";
/* 5324 */     int number = -1;
/*      */     
/* 5326 */     if (connector.more()) {
/*      */       
/* 5328 */       prefixString = connector.getField("Prefix");
/* 5329 */       number = connector.getIntegerField("SeqNo");
/*      */     } 
/*      */     
/* 5332 */     connector.close();
/*      */     
/* 5334 */     String result = "";
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 5339 */       NumberFormat form = new DecimalFormat("0000000");
/* 5340 */       result = String.valueOf(prefixString) + form.format(number);
/*      */     }
/* 5342 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5347 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTimestampValid(Selection selection) {
/* 5356 */     if (selection != null) {
/*      */       
/* 5358 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM release_header WHERE release_id = " + 
/*      */         
/* 5360 */         selection.getSelectionID() + 
/* 5361 */         ";";
/* 5362 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 5363 */       connectorTimestamp.runQuery();
/* 5364 */       if (connectorTimestamp.more())
/*      */       {
/* 5366 */         if (selection.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*      */           
/* 5368 */           connectorTimestamp.close();
/* 5369 */           return false;
/*      */         } 
/*      */       }
/* 5372 */       connectorTimestamp.close();
/* 5373 */       return true;
/*      */     } 
/* 5375 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTimestampValid(Bom bom) {
/* 5384 */     if (bom != null) {
/*      */       
/* 5386 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM bom_header WHERE bom_id = " + 
/*      */         
/* 5388 */         bom.getBomId() + 
/* 5389 */         ";";
/*      */       
/* 5391 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 5392 */       connectorTimestamp.runQuery();
/* 5393 */       if (connectorTimestamp.more())
/*      */       {
/* 5395 */         if (bom.getLastUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/* 5396 */           connectorTimestamp.close();
/* 5397 */           return false;
/*      */         } 
/*      */       }
/* 5400 */       connectorTimestamp.close();
/* 5401 */       return true;
/*      */     } 
/* 5403 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTimestampValid(Pfm pfm) {
/* 5412 */     if (pfm != null) {
/*      */       
/* 5414 */       String timestampQuery = "SELECT last_updated_ck, last_updated_on FROM pfm_selection WHERE release_id = " + 
/*      */         
/* 5416 */         pfm.getReleaseId() + 
/* 5417 */         ";";
/* 5418 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 5419 */       connectorTimestamp.runQuery();
/* 5420 */       if (connectorTimestamp.more())
/*      */       {
/* 5422 */         if (pfm.getLastUpdatedCk() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*      */           
/* 5424 */           connectorTimestamp.close();
/* 5425 */           return false;
/*      */         } 
/*      */       }
/* 5428 */       connectorTimestamp.close();
/* 5429 */       return true;
/*      */     } 
/* 5431 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isManufacturingTimestampValid(Selection selection) {
/* 5440 */     if (selection != null) {
/*      */       
/* 5442 */       String timestampQuery = "SELECT last_updated_ck  FROM release_subdetail WHERE release_id = " + 
/*      */         
/* 5444 */         selection.getSelectionID() + ";";
/*      */       
/* 5446 */       JdbcConnector connectorTimestamp = MilestoneHelper.getConnector(timestampQuery);
/* 5447 */       connectorTimestamp.runQuery();
/* 5448 */       if (connectorTimestamp.more())
/*      */       {
/* 5450 */         if (selection.getLastMfgUpdatedCheck() != Long.parseLong(connectorTimestamp.getField("last_updated_ck", ""), 16)) {
/*      */           
/* 5452 */           connectorTimestamp.close();
/* 5453 */           return false;
/*      */         } 
/*      */       }
/* 5456 */       connectorTimestamp.close();
/* 5457 */       return true;
/*      */     } 
/* 5459 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Selection getSelectionAndSchedule(int id) {
/* 5467 */     Selection selection = getSelectionHeader(id);
/* 5468 */     Schedule schedule = ScheduleManager.getInstance().getSchedule(id);
/* 5469 */     selection.setSchedule(schedule);
/*      */     
/* 5471 */     return selection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSelectionsSchedule(Selection selection) {
/* 5479 */     Schedule schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/* 5480 */     selection.setSchedule(schedule);
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
/* 5493 */     int units = 0;
/*      */     
/* 5495 */     if (selection != null) {
/*      */       
/* 5497 */       int selectionId = selection.getSelectionID();
/*      */       
/* 5499 */       String manufacturingQuery = "SELECT units_per_set FROM vi_pfm_selection  WHERE release_id = " + 
/* 5500 */         selectionId;
/*      */       
/* 5502 */       JdbcConnector connector = MilestoneHelper.getConnector(manufacturingQuery);
/* 5503 */       connector.runQuery();
/*      */ 
/*      */       
/*      */       try {
/* 5507 */         units = connector.getIntegerField("units_per_set");
/*      */       }
/* 5509 */       catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5514 */       connector.close();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 5519 */     return units;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSelectionOkToClose(Context context) {
/* 5528 */     boolean close = true;
/*      */     
/* 5530 */     Selection selection = (Selection)context.getSessionValue("Selection");
/*      */     
/* 5532 */     Schedule schedule = null;
/*      */     
/* 5534 */     if (selection != null) {
/* 5535 */       schedule = ScheduleManager.getInstance().getSchedule(selection.getSelectionID());
/*      */     }
/* 5537 */     if (schedule != null) {
/*      */       
/* 5539 */       Vector tasks = schedule.getTasks();
/* 5540 */       if (tasks != null) {
/*      */         
/* 5542 */         ScheduledTask task = null;
/*      */         
/* 5544 */         for (int i = 0; i < tasks.size(); i++) {
/*      */           
/* 5546 */           task = (ScheduledTask)tasks.get(i);
/*      */           
/* 5548 */           if (task.getCompletionDate() == null)
/*      */           {
/*      */ 
/*      */             
/* 5552 */             if (MilestoneHelper.isUml(task) || MilestoneHelper.isEcommerce(task)) {
/*      */               
/* 5554 */               close = false;
/*      */               
/*      */               break;
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 5563 */     return close;
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
/* 5578 */     String sqlStr = "sp_get_Schedule_MutSelections " + id;
/* 5579 */     JdbcConnector multSelectionsConnector = MilestoneHelper.getConnector(sqlStr);
/* 5580 */     multSelectionsConnector.runQuery();
/*      */     
/* 5582 */     Vector multSelections = new Vector();
/*      */     
/* 5584 */     while (multSelectionsConnector.more()) {
/*      */       
/* 5586 */       MultSelection multSel = new MultSelection();
/*      */       
/* 5588 */       multSel.setMultSelectionPK(multSelectionsConnector.getInt("multSelectionsPK", -1));
/* 5589 */       multSel.setRelease_id(multSelectionsConnector.getInt("release_id", -1));
/* 5590 */       multSel.setSelectionNo(multSelectionsConnector.getField("selectionNo", ""));
/* 5591 */       multSel.setUpc(multSelectionsConnector.getField("upc", ""));
/* 5592 */       multSel.setDescription(multSelectionsConnector.getField("description", ""));
/*      */       
/* 5594 */       multSelections.add(multSel);
/*      */       
/* 5596 */       multSelectionsConnector.next();
/*      */     } 
/*      */     
/* 5599 */     multSelectionsConnector.close();
/*      */     
/* 5601 */     return multSelections;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveMultSelections(Selection selection, User updatingUser) {
/* 5611 */     if (selection != null && selection.getMultSelections() != null) {
/*      */       
/* 5613 */       Vector multSelections = selection.getMultSelections();
/*      */       
/* 5615 */       Vector addMultSelections = new Vector();
/* 5616 */       Vector deleteMultSelections = new Vector();
/*      */       
/* 5618 */       String sqlQuery = "select * from vi_MultSelections where release_id = " + selection.getSelectionID();
/*      */ 
/*      */ 
/*      */       
/* 5622 */       boolean delete = true;
/*      */       
/* 5624 */       for (int a = 0; a < multSelections.size(); a++) {
/*      */         
/* 5626 */         MultSelection multSel = (MultSelection)multSelections.get(a);
/*      */ 
/*      */ 
/*      */         
/* 5630 */         JdbcConnector connectorMultSelectionCount = MilestoneHelper.getConnector(sqlQuery);
/* 5631 */         connectorMultSelectionCount.runQuery();
/*      */         
/* 5633 */         if (connectorMultSelectionCount.more()) {
/*      */           
/* 5635 */           while (connectorMultSelectionCount.more())
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 5640 */             if (multSel.getMultSelectionsPK() == -1 || 
/* 5641 */               multSel.getMultSelectionsPK() == connectorMultSelectionCount.getInt("multSelectionsPK", -2)) {
/*      */               
/* 5643 */               addMultSelections.add(multSel);
/*      */               break;
/*      */             } 
/* 5646 */             connectorMultSelectionCount.next();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 5651 */           addMultSelections.add(multSel);
/*      */         } 
/*      */         
/* 5654 */         connectorMultSelectionCount.close();
/*      */       } 
/*      */       
/* 5657 */       JdbcConnector connectorMultSelectionDelete = MilestoneHelper.getConnector(sqlQuery);
/* 5658 */       connectorMultSelectionDelete.runQuery();
/*      */       
/* 5660 */       while (connectorMultSelectionDelete.more()) {
/*      */         
/* 5662 */         delete = true;
/*      */         
/* 5664 */         for (int b = 0; b < multSelections.size(); b++) {
/*      */           
/* 5666 */           MultSelection multSelDelete = (MultSelection)multSelections.get(b);
/*      */           
/* 5668 */           if (connectorMultSelectionDelete.getInt("multSelectionsPK", -2) == multSelDelete.getMultSelectionsPK())
/*      */           {
/* 5670 */             delete = false;
/*      */           }
/*      */         } 
/*      */         
/* 5674 */         if (delete) {
/*      */           
/* 5676 */           MultSelection delMultSelection = new MultSelection();
/* 5677 */           delMultSelection.setMultSelectionPK(connectorMultSelectionDelete.getInt("multSelectionsPK", -1));
/* 5678 */           deleteMultSelections.add(delMultSelection);
/*      */         } 
/*      */         
/* 5681 */         connectorMultSelectionDelete.next();
/*      */       } 
/* 5683 */       connectorMultSelectionDelete.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5689 */       for (int i = 0; i < addMultSelections.size(); i++) {
/*      */         
/* 5691 */         MultSelection multSel = (MultSelection)addMultSelections.get(i);
/*      */         
/* 5693 */         int relId = multSel.getRealease_id();
/* 5694 */         int multSelectionsPK = multSel.getMultSelectionsPK();
/*      */         
/* 5696 */         if (relId < 0) {
/* 5697 */           relId = selection.getSelectionID();
/*      */         }
/*      */         
/* 5700 */         String sqlStr = "sp_sav_MultSelections " + multSel.getMultSelectionsPK() + "," + 
/* 5701 */           relId + "," + 
/* 5702 */           "'" + multSel.getSelectionNo() + "'," + 
/*      */           
/* 5704 */           "'" + MilestoneHelper_2.reformat_UPC_SSG_SGC_forSave(multSel.getUpc(), "UPC", false, true) + "'," + 
/* 5705 */           "'" + multSel.getDescription() + "'";
/*      */ 
/*      */ 
/*      */         
/* 5709 */         JdbcConnector connectorAddMultSelection = MilestoneHelper.getConnector(sqlStr);
/* 5710 */         connectorAddMultSelection.runQuery();
/* 5711 */         connectorAddMultSelection.close();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5719 */       JdbcConnector connectorDeleteMultSelection = null;
/* 5720 */       for (int k = 0; k < deleteMultSelections.size(); k++) {
/*      */         
/* 5722 */         MultSelection multSel = (MultSelection)deleteMultSelections.get(k);
/*      */         
/* 5724 */         String deleteSql = "sp_del_MultSelections " + multSel.getMultSelectionsPK();
/*      */ 
/*      */ 
/*      */         
/* 5728 */         connectorDeleteMultSelection = MilestoneHelper.getConnector(deleteSql);
/* 5729 */         connectorDeleteMultSelection.runQuery();
/* 5730 */         connectorDeleteMultSelection.close();
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
/* 5749 */     String sqlStr = "sp_get_Schedule_MultOtherContacts " + id;
/* 5750 */     JdbcConnector multOtherContactsConnector = MilestoneHelper.getConnector(sqlStr);
/* 5751 */     multOtherContactsConnector.runQuery();
/*      */     
/* 5753 */     Vector multOtherContacts = new Vector();
/*      */     
/* 5755 */     while (multOtherContactsConnector.more()) {
/*      */       
/* 5757 */       MultOtherContact otherContact = new MultOtherContact();
/*      */       
/* 5759 */       otherContact.setMultOtherContactsPK(multOtherContactsConnector.getInt("multOtherContactsPK", -1));
/* 5760 */       otherContact.setRelease_id(multOtherContactsConnector.getInt("release_id", -1));
/* 5761 */       otherContact.setName(multOtherContactsConnector.getField("name", ""));
/* 5762 */       otherContact.setDescription(multOtherContactsConnector.getField("description", ""));
/*      */       
/* 5764 */       multOtherContacts.add(otherContact);
/*      */       
/* 5766 */       multOtherContactsConnector.next();
/*      */     } 
/*      */     
/* 5769 */     multOtherContactsConnector.close();
/*      */     
/* 5771 */     return multOtherContacts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveMultOtherContacts(Selection selection, User updatingUser) {
/* 5781 */     if (selection != null && selection.getMultOtherContacts() != null) {
/*      */       
/* 5783 */       Vector multOtherContacts = selection.getMultOtherContacts();
/*      */       
/* 5785 */       Vector addMultOtherContacts = new Vector();
/* 5786 */       Vector deleteMultOtherContacts = new Vector();
/*      */       
/* 5788 */       String sqlQuery = "select * from vi_MultOtherContacts where release_id = " + selection.getSelectionID();
/*      */ 
/*      */ 
/*      */       
/* 5792 */       boolean delete = true;
/*      */       
/* 5794 */       for (int a = 0; a < multOtherContacts.size(); a++) {
/*      */         
/* 5796 */         MultOtherContact otherContact = (MultOtherContact)multOtherContacts.get(a);
/*      */ 
/*      */ 
/*      */         
/* 5800 */         JdbcConnector connectorMultOtherContactCount = MilestoneHelper.getConnector(sqlQuery);
/* 5801 */         connectorMultOtherContactCount.runQuery();
/*      */         
/* 5803 */         if (connectorMultOtherContactCount.more()) {
/*      */           
/* 5805 */           while (connectorMultOtherContactCount.more())
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 5810 */             if (otherContact.getMultOtherContactsPK() == -1 || 
/* 5811 */               otherContact.getMultOtherContactsPK() == connectorMultOtherContactCount.getInt("multOtherContactsPK", -2)) {
/*      */               
/* 5813 */               addMultOtherContacts.add(otherContact);
/*      */               break;
/*      */             } 
/* 5816 */             connectorMultOtherContactCount.next();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 5821 */           addMultOtherContacts.add(otherContact);
/*      */         } 
/*      */         
/* 5824 */         connectorMultOtherContactCount.close();
/*      */       } 
/*      */       
/* 5827 */       JdbcConnector connectorMultOtherContactDelete = MilestoneHelper.getConnector(sqlQuery);
/* 5828 */       connectorMultOtherContactDelete.runQuery();
/*      */       
/* 5830 */       while (connectorMultOtherContactDelete.more()) {
/*      */         
/* 5832 */         delete = true;
/*      */         
/* 5834 */         for (int b = 0; b < multOtherContacts.size(); b++) {
/*      */           
/* 5836 */           MultOtherContact multOtherContactDelete = (MultOtherContact)multOtherContacts.get(b);
/*      */           
/* 5838 */           if (connectorMultOtherContactDelete.getInt("multOtherContactsPK", -2) == multOtherContactDelete.getMultOtherContactsPK())
/*      */           {
/* 5840 */             delete = false;
/*      */           }
/*      */         } 
/*      */         
/* 5844 */         if (delete) {
/*      */           
/* 5846 */           MultOtherContact delMultOtherContact = new MultOtherContact();
/* 5847 */           delMultOtherContact.setMultOtherContactsPK(connectorMultOtherContactDelete.getInt("multOtherContactsPK", -1));
/* 5848 */           deleteMultOtherContacts.add(delMultOtherContact);
/*      */         } 
/*      */         
/* 5851 */         connectorMultOtherContactDelete.next();
/*      */       } 
/* 5853 */       connectorMultOtherContactDelete.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5859 */       for (int i = 0; i < addMultOtherContacts.size(); i++) {
/*      */         
/* 5861 */         MultOtherContact otherContact = (MultOtherContact)addMultOtherContacts.get(i);
/*      */         
/* 5863 */         int relId = otherContact.getRealease_id();
/* 5864 */         int multOtherContactsPK = otherContact.getMultOtherContactsPK();
/*      */         
/* 5866 */         if (relId < 0) {
/* 5867 */           relId = selection.getSelectionID();
/*      */         }
/*      */         
/* 5870 */         String sqlStr = "sp_sav_MultOtherContacts " + otherContact.getMultOtherContactsPK() + "," + 
/* 5871 */           relId + "," + 
/* 5872 */           "'" + otherContact.getName() + "'," + 
/* 5873 */           "'" + otherContact.getDescription() + "'";
/*      */ 
/*      */ 
/*      */         
/* 5877 */         JdbcConnector connectorAddMultOtherContact = MilestoneHelper.getConnector(sqlStr);
/* 5878 */         connectorAddMultOtherContact.runQuery();
/* 5879 */         connectorAddMultOtherContact.close();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5887 */       JdbcConnector connectorDeleteMultOtherContact = null;
/* 5888 */       for (int k = 0; k < deleteMultOtherContacts.size(); k++) {
/*      */         
/* 5890 */         MultOtherContact otherContact = (MultOtherContact)deleteMultOtherContacts.get(k);
/*      */         
/* 5892 */         String deleteSql = "sp_del_MultOtherContacts " + otherContact.getMultOtherContactsPK();
/*      */ 
/*      */ 
/*      */         
/* 5896 */         connectorDeleteMultOtherContact = MilestoneHelper.getConnector(deleteSql);
/* 5897 */         connectorDeleteMultOtherContact.runQuery();
/* 5898 */         connectorDeleteMultOtherContact.close();
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
/* 5910 */     Vector notepadList = notepad.getAllContents();
/* 5911 */     boolean fndChar = false;
/*      */     
/* 5913 */     if (notepad != null && notepadList != null && notepadList.size() > 0) {
/*      */ 
/*      */       
/* 5916 */       int x = 0;
/*      */ 
/*      */ 
/*      */       
/* 5920 */       NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
/*      */       
/* 5922 */       if (sortOrder == 0 || sortOrder == 7) {
/* 5923 */         notepadSortOrder.setSelectionOrderCol("Artist");
/*      */       }
/* 5925 */       if (sortOrder == 1 || sortOrder == 8) {
/* 5926 */         notepadSortOrder.setSelectionOrderCol("Title");
/*      */       }
/* 5928 */       notepadSortOrder.setSelectionOrderColNo(sortOrder);
/*      */ 
/*      */       
/* 5931 */       for (Iterator i = notepadList.iterator(); i.hasNext(); x++) {
/*      */         
/* 5933 */         Selection selection = (Selection)i.next();
/* 5934 */         String scanStr = "";
/*      */         
/* 5936 */         if (sortOrder == 0 || sortOrder == 7)
/* 5937 */           scanStr = selection.getArtist(); 
/* 5938 */         if (sortOrder == 1 || sortOrder == 8) {
/* 5939 */           scanStr = selection.getTitle();
/*      */         }
/*      */         
/* 5942 */         int result = scanStr.substring(0).compareToIgnoreCase(alphaChar.substring(0));
/*      */         
/* 5944 */         if (sortOrder == 0 || sortOrder == 1) {
/*      */           
/* 5946 */           if (result == 0 || result > 0) {
/* 5947 */             notepad.setSelected(selection);
/* 5948 */             fndChar = true;
/*      */             
/*      */             break;
/*      */           } 
/* 5952 */         } else if (result < 0) {
/*      */           
/* 5954 */           Selection upOne = null;
/* 5955 */           if (x > 0)
/* 5956 */             upOne = (Selection)notepadList.get(x - 1); 
/* 5957 */           notepad.setSelected(upOne);
/* 5958 */           fndChar = true;
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 5966 */       if (!fndChar) {
/* 5967 */         if (x > 0) {
/* 5968 */           Selection upOne = (Selection)notepadList.get(x - 1);
/* 5969 */           notepad.setSelected(upOne);
/*      */         } 
/* 5971 */         context.putDelivery("AlertMessage", "There is no record that matches the search criteria");
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
/* 5985 */     Vector userCompanies = MilestoneHelper.getUserCompanies(context);
/* 5986 */     Vector userEnvironments = new Vector();
/*      */     
/* 5988 */     Environment env = null;
/* 5989 */     boolean duplicate = false;
/* 5990 */     for (int i = 0; i < userCompanies.size(); i++) {
/*      */       
/* 5992 */       duplicate = false;
/* 5993 */       env = ((Company)userCompanies.get(i)).getParentEnvironment();
/*      */       
/* 5995 */       for (int j = 0; j < userEnvironments.size(); j++) {
/*      */         
/* 5997 */         if (env.getName() == ((Environment)userEnvironments.get(j)).getName()) {
/*      */           
/* 5999 */           duplicate = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 6004 */       if (!duplicate) {
/* 6005 */         userEnvironments.add(env);
/*      */       }
/*      */     } 
/* 6008 */     return userEnvironments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isSelectionIDDuplicate(String prefix, String selectionNo, int selectionID, boolean isDigital) {
/* 6015 */     int prodType = 0;
/*      */     
/* 6017 */     if (isDigital) {
/* 6018 */       prodType = 1;
/*      */     }
/*      */     
/* 6021 */     if (isDigital && selectionNo.length() == 0) {
/* 6022 */       return false;
/*      */     }
/*      */     
/* 6025 */     String sqlStr = "SELECT count(*) as myCount FROM release_header with(nolock)  WHERE prefix = '" + 
/* 6026 */       prefix + "' " + 
/* 6027 */       " AND NOT status = 'CLOSED' " + 
/* 6028 */       " AND selection_no = '" + selectionNo + "'" + 
/* 6029 */       " AND release_id <> " + selectionID + 
/* 6030 */       " AND digital_flag = " + prodType;
/*      */ 
/*      */     
/* 6033 */     JdbcConnector isDup = MilestoneHelper.getConnector(sqlStr);
/* 6034 */     isDup.runQuery();
/*      */     
/* 6036 */     int count = 0;
/* 6037 */     if (isDup.more()) {
/* 6038 */       count = isDup.getInt("myCount");
/*      */     }
/* 6040 */     isDup.close();
/* 6041 */     return (count > 0);
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
/* 6053 */     String query = "UPDATE dbo.Release_Header SET UPC = '" + 
/* 6054 */       MilestoneHelper.escapeSingleQuotes(upc) + "', config_code = '" + 
/* 6055 */       MilestoneHelper.escapeSingleQuotes(configCode) + "', soundscan_grp = '" + 
/* 6056 */       MilestoneHelper.escapeSingleQuotes(soundscan) + "', prefix = '" + 
/* 6057 */       getLookupObjectValue(prefix) + "', selection_no = '" + 
/* 6058 */       MilestoneHelper.escapeSingleQuotes(selectionNo) + "'" + 
/* 6059 */       " WHERE release_id = " + String.valueOf(releaseID);
/*      */     
/* 6061 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 6062 */     connector.runQuery();
/* 6063 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveSelectionDataFromPhysicalPfm(int releaseID, String streetDate) {
/* 6073 */     String query = "UPDATE dbo.Release_Header SET ";
/* 6074 */     query = String.valueOf(query) + " street_date = '" + MilestoneHelper.escapeSingleQuotes(streetDate) + "'";
/* 6075 */     query = String.valueOf(query) + " WHERE release_id = " + String.valueOf(releaseID);
/*      */ 
/*      */ 
/*      */     
/* 6079 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 6080 */     connector.runQuery();
/* 6081 */     connector.close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isProjectNumberValidFromMilestoneSnapshot(Selection selection) {
/* 6090 */     String projectsQuery = "";
/* 6091 */     String projectNumber = selection.getProjectID();
/*      */     
/* 6093 */     if (selection.getFamily().getName().equalsIgnoreCase("UMVD")) {
/*      */       
/* 6095 */       projectNumber = ProjectSearchManager.replaceStringInString(projectNumber, "-", "");
/* 6096 */       if (projectNumber.length() > 8)
/* 6097 */         projectNumber = projectNumber.substring(1); 
/* 6098 */       projectsQuery = String.valueOf(projectsQuery) + "select * from ArchimedesProjects where [JDE Project#] = '" + projectNumber + "'";
/*      */     }
/*      */     else {
/*      */       
/* 6102 */       projectsQuery = String.valueOf(projectsQuery) + "select * from ArchimedesProjects where [RMS Project#] = '" + projectNumber + "'";
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6107 */     JdbcConnector connector = MilestoneHelper.getConnector(projectsQuery);
/*      */ 
/*      */     
/*      */     try {
/* 6111 */       connector.runQuery();
/*      */     }
/* 6113 */     catch (Exception e) {
/*      */       
/* 6115 */       System.out.println("****exception raised in isProjectNumberValidFromMilestoneSnapshot****");
/*      */     } 
/*      */ 
/*      */     
/* 6119 */     boolean isValid = false;
/* 6120 */     if (connector.more()) {
/* 6121 */       isValid = true;
/*      */     }
/* 6123 */     connector.close();
/* 6124 */     return isValid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Selection isProjectNumberValid(Selection selection) {
/* 6133 */     String projectsQuery = "";
/* 6134 */     String projectNumber = selection.getProjectID();
/* 6135 */     Selection updatedSel = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6320 */     boolean IsUMVD = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 6330 */       updatedSel = projectSearchSvcClient.ProjectSearchValidation(projectNumber, Boolean.valueOf(IsUMVD));
/* 6331 */       System.out.println("Project Search from WCF: " + updatedSel.scheduleId);
/* 6332 */     } catch (Exception e) {
/* 6333 */       System.out.println(e.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6338 */     return updatedSel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getArchimedesIdFromCache(int label_id) {
/* 6348 */     int returnId = -1;
/*      */ 
/*      */     
/* 6351 */     Vector labels = Cache.getLabels();
/* 6352 */     if (labels != null)
/*      */     {
/* 6354 */       for (int i = 0; i < labels.size(); i++) {
/*      */ 
/*      */         
/* 6357 */         Label label = (Label)labels.get(i);
/*      */         
/* 6359 */         if (label.getStructureID() == label_id) {
/*      */           
/* 6361 */           returnId = label.getArchimedesId();
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 6366 */     return returnId;
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
/* 6378 */     User user = (User)context.getSession().getAttribute("user");
/* 6379 */     Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
/*      */ 
/*      */     
/* 6382 */     addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*      */     
/* 6384 */     FormDropDownMenu famDD = (FormDropDownMenu)form.getElement(formName);
/* 6385 */     String familySearch = famDD.getStringValue();
/* 6386 */     if (familySearch != null && !familySearch.equals("") && !familySearch.equals("0") && 
/* 6387 */       !familySearch.equals("-1")) {
/*      */       
/* 6389 */       query.append(" AND (Release_Family_id = " + familySearch);
/*      */       
/* 6391 */       addLabelFamilySelect(familySearch, relFamilyLabelFamilyHash, query);
/* 6392 */       query.append(") ");
/*      */     }
/*      */     else {
/*      */       
/* 6396 */       String[] valueList = famDD.getValueList();
/* 6397 */       if (valueList != null && valueList.length > 0) {
/*      */         
/* 6399 */         query.append(" AND (");
/* 6400 */         for (int i = 0; i < valueList.length; i++) {
/*      */           
/* 6402 */           if (i > 0)
/* 6403 */             query.append(" OR "); 
/* 6404 */           query.append("(Release_Family_id = " + valueList[i]);
/* 6405 */           addLabelFamilySelect(valueList[i], relFamilyLabelFamilyHash, query);
/* 6406 */           query.append(")");
/*      */         } 
/* 6408 */         query.append(") ");
/*      */       } else {
/*      */         
/* 6411 */         query.append(" AND (Release_Family_id = -1 and family_id = -1) ");
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
/* 6426 */     if (relFamilyLabelFamilyHash.containsKey(relFamilyStr)) {
/*      */       
/* 6428 */       Vector labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyStr);
/* 6429 */       if (labelFamilies != null && labelFamilies.size() > 0) {
/* 6430 */         for (int i = 0; i < labelFamilies.size(); i++) {
/*      */           
/* 6432 */           if (i == 0) {
/* 6433 */             query.append(" AND family_id in (" + (String)labelFamilies.get(i));
/*      */           } else {
/* 6435 */             query.append("," + (String)labelFamilies.get(i));
/*      */           } 
/* 6437 */         }  query.append(")");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 6442 */       query.append(" AND family_id in (" + relFamilyStr + ")");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addUndefinedReleasingFamilies(Hashtable relFamilyLabelFamilyHash, Context context) {
/* 6449 */     Vector labelFamilies = null;
/* 6450 */     Vector families = ReleasingFamily.getUserReleasingFamiliesVectorOfFamilies(context);
/*      */     
/* 6452 */     if (families != null)
/*      */     {
/* 6454 */       for (int i = 0; i < families.size(); i++) {
/*      */         
/* 6456 */         Family family = (Family)families.get(i);
/*      */         
/* 6458 */         if (family != null) {
/*      */ 
/*      */           
/* 6461 */           String relFamilyIdStr = Integer.toString(family.getStructureID());
/* 6462 */           if (relFamilyLabelFamilyHash.containsKey(relFamilyIdStr)) {
/* 6463 */             boolean addToHash = true;
/* 6464 */             labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyIdStr);
/* 6465 */             for (int c = 0; c < labelFamilies.size(); c++) {
/*      */               
/* 6467 */               String labelFamilyIdStr = (String)labelFamilies.get(c);
/*      */               
/* 6469 */               if (labelFamilyIdStr.equals(relFamilyIdStr)) {
/*      */                 
/* 6471 */                 addToHash = false;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 6476 */             if (addToHash) {
/*      */               
/* 6478 */               labelFamilies = (Vector)relFamilyLabelFamilyHash.get(relFamilyIdStr);
/* 6479 */               labelFamilies.add(relFamilyIdStr);
/*      */             } 
/*      */           } else {
/*      */             
/* 6483 */             labelFamilies = new Vector();
/* 6484 */             labelFamilies.add(relFamilyIdStr);
/* 6485 */             relFamilyLabelFamilyHash.put(relFamilyIdStr, labelFamilies);
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
/* 6497 */     StringBuffer query = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/* 6501 */     Environment environment = null;
/* 6502 */     Vector environments = MilestoneHelper.getUserEnvironments(context);
/* 6503 */     int year = Calendar.getInstance().get(1);
/* 6504 */     String date = "01/01/" + year;
/*      */ 
/*      */     
/* 6507 */     query.append("SELECT release_id, title, artist, configuration, sub_configuration, upc, prefix, selection_no, digital_flag, street_date =  CASE digital_flag  WHEN 0 THEN street_date   WHEN 1 THEN digital_rls_date  END ,archimedes_id, Release_Family_id from vi_Release_Header WHERE ");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6519 */     User user = (User)context.getSession().getAttribute("user");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6524 */     if (user.getPreferences().getSelectionEnvironment() > 0) {
/*      */       
/* 6526 */       int envId = user.getPreferences().getSelectionEnvironment();
/* 6527 */       query.append(" environment_id in (" + envId + ") ");
/* 6528 */       user.SS_environmentSearch = String.valueOf(envId);
/* 6529 */       user.RC_environment = String.valueOf(envId);
/*      */     } else {
/* 6531 */       if (environments.size() > 0) {
/* 6532 */         query.append(" environment_id in (");
/*      */       }
/* 6534 */       for (int i = 0; i < environments.size(); i++) {
/* 6535 */         environment = (Environment)environments.get(i);
/* 6536 */         if (environment != null) {
/* 6537 */           if (i == 0) {
/* 6538 */             query.append(environment.getStructureID());
/*      */           } else {
/* 6540 */             query.append("," + environment.getStructureID());
/*      */           } 
/*      */         }
/*      */       } 
/* 6544 */       if (environments.size() > 0) {
/* 6545 */         query.append(" )");
/*      */       }
/*      */     } 
/* 6548 */     Vector rFamilies = ReleasingFamily.getUserReleasingFamiliesVectorOfReleasingFamilies(context);
/*      */     
/* 6550 */     Hashtable relFamilyLabelFamilyHash = (Hashtable)user.getReleasingFamilyLabelFamily().clone();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6555 */     if (user.getPreferences().getSelectionReleasingFamily() > 0) {
/*      */ 
/*      */       
/* 6558 */       addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*      */       
/* 6560 */       int relFamId = user.getPreferences().getSelectionReleasingFamily();
/* 6561 */       query.append(" AND (Release_Family_id = " + relFamId);
/* 6562 */       addLabelFamilySelect(Integer.toString(relFamId), relFamilyLabelFamilyHash, query);
/* 6563 */       query.append(") ");
/* 6564 */       user.SS_familySearch = String.valueOf(relFamId);
/* 6565 */       user.RC_releasingFamily = String.valueOf(relFamId);
/*      */     }
/*      */     else {
/*      */       
/* 6569 */       addUndefinedReleasingFamilies(relFamilyLabelFamilyHash, context);
/*      */       
/* 6571 */       if (rFamilies != null && rFamilies.size() > 0) {
/* 6572 */         query.append(" AND (");
/* 6573 */         for (int i = 0; i < rFamilies.size(); i++) {
/* 6574 */           ReleasingFamily rf = (ReleasingFamily)rFamilies.get(i);
/* 6575 */           if (i > 0)
/* 6576 */             query.append(" OR "); 
/* 6577 */           query.append("(Release_Family_id = " + rf.getReleasingFamilyId());
/* 6578 */           addLabelFamilySelect(Integer.toString(rf.getReleasingFamilyId()), 
/* 6579 */               relFamilyLabelFamilyHash, query);
/* 6580 */           query.append(")");
/*      */         } 
/* 6582 */         query.append(") ");
/*      */       } else {
/*      */         
/* 6585 */         query.append(" AND (Release_Family_id = -1 and family_id = -1) ");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6591 */     if (user != null && user.getPreferences() != null) {
/*      */ 
/*      */       
/* 6594 */       user.SS_showAllSearch = "true";
/* 6595 */       if (user.getPreferences().getSelectionStatus() == 0) {
/*      */         
/* 6597 */         query.append(" AND NOT ( status = 'CLOSED' OR  status = 'CANCEL' )");
/* 6598 */         user.SS_showAllSearch = "false";
/*      */       } 
/*      */ 
/*      */       
/* 6602 */       if (user.getPreferences().getSelectionLabelContact() > 0) {
/*      */         
/* 6604 */         query.append(" AND contact_id = " + user.getPreferences().getSelectionLabelContact());
/* 6605 */         user.SS_contactSearch = String.valueOf(user.getPreferences().getSelectionLabelContact());
/* 6606 */         user.RC_labelContact = String.valueOf(user.getPreferences().getSelectionLabelContact());
/*      */       } 
/*      */ 
/*      */       
/* 6610 */       if (user.getPreferences().getSelectionProductType() > -1) {
/*      */ 
/*      */         
/* 6613 */         String productTypeSearch = "";
/* 6614 */         if (user.getPreferences().getSelectionProductType() == 0) {
/*      */           
/* 6616 */           productTypeSearch = "physical";
/* 6617 */           query.append(" AND digital_flag = 0 ");
/*      */         } 
/* 6619 */         if (user.getPreferences().getSelectionProductType() == 1) {
/*      */           
/* 6621 */           productTypeSearch = "digital";
/* 6622 */           query.append(" AND digital_flag = 1 ");
/*      */         } 
/* 6624 */         if (user.getPreferences().getSelectionProductType() == 2)
/*      */         {
/* 6626 */           productTypeSearch = "both";
/*      */         }
/* 6628 */         user.SS_productTypeSearch = productTypeSearch;
/* 6629 */         user.RC_productType = MilestoneHelper_2.convertProductTypeToReleaseCalendar(productTypeSearch);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6635 */     query.append(ShowOrHideDigitalProductGet(user));
/*      */ 
/*      */     
/* 6638 */     log.log("selection query " + query.toString());
/*      */     
/* 6640 */     return query.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSelectionNotepadQueryUserDefaultsOrderBy(Context context) {
/* 6650 */     User user = (User)context.getSession().getAttribute("user");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6656 */     String order = " ORDER BY artist, title, selection_no, street_date";
/*      */ 
/*      */     
/* 6659 */     if (user != null && user.getPreferences() != null) {
/*      */ 
/*      */       
/* 6662 */       NotepadSortOrder notepadSortOrder = NotepadSortOrder.getNotepadSortOrderFromSession(context);
/* 6663 */       notepadSortOrder.setSelectionOrderCol("Artist");
/* 6664 */       notepadSortOrder.setSelectionOrderBy(order);
/* 6665 */       notepadSortOrder.setShowGroupButtons(true);
/* 6666 */       notepadSortOrder.setSelectionOrderColNo(0);
/*      */ 
/*      */       
/* 6669 */       if (user.getPreferences().getNotepadSortBy() == 1) {
/* 6670 */         if (user.getPreferences().getNotepadOrder() == 1) {
/* 6671 */           order = " ORDER BY artist ASC , title, selection_no, street_date";
/* 6672 */         } else if (user.getPreferences().getNotepadOrder() == 2) {
/*      */           
/* 6674 */           order = " ORDER BY artist DESC , title, selection_no, street_date";
/* 6675 */           notepadSortOrder.setSelectionOrderColNo(7);
/*      */         } else {
/* 6677 */           order = " ORDER BY artist, title, selection_no, street_date";
/*      */         } 
/*      */         
/* 6680 */         notepadSortOrder.setSelectionOrderCol("Artist");
/* 6681 */         notepadSortOrder.setSelectionOrderBy(order);
/* 6682 */         notepadSortOrder.setShowGroupButtons(true);
/*      */       }
/* 6684 */       else if (user.getPreferences().getNotepadSortBy() == 2) {
/* 6685 */         notepadSortOrder.setSelectionOrderColNo(1);
/* 6686 */         if (user.getPreferences().getNotepadOrder() == 1) {
/* 6687 */           order = " ORDER BY title ASC , artist, selection_no, street_date";
/* 6688 */         } else if (user.getPreferences().getNotepadOrder() == 2) {
/*      */           
/* 6690 */           order = " ORDER BY title DESC , artist, selection_no, street_date";
/* 6691 */           notepadSortOrder.setSelectionOrderColNo(8);
/*      */         } else {
/* 6693 */           order = " ORDER BY title, artist, selection_no, street_date";
/*      */         } 
/* 6695 */         notepadSortOrder.setSelectionOrderCol("Title");
/* 6696 */         notepadSortOrder.setSelectionOrderBy(order);
/* 6697 */         notepadSortOrder.setShowGroupButtons(true);
/*      */       }
/* 6699 */       else if (user.getPreferences().getNotepadSortBy() == 3) {
/* 6700 */         notepadSortOrder.setSelectionOrderColNo(5);
/* 6701 */         if (user.getPreferences().getNotepadOrder() == 1) {
/* 6702 */           order = " ORDER BY street_date ASC , artist, title, selection_no";
/* 6703 */         } else if (user.getPreferences().getNotepadOrder() == 2) {
/* 6704 */           order = " ORDER BY street_date DESC , artist, title, selection_no";
/*      */         } else {
/* 6706 */           order = " ORDER BY street_date, artist, title, selection_no";
/*      */         } 
/* 6708 */         notepadSortOrder.setShowGroupButtons(false);
/* 6709 */         notepadSortOrder.setSelectionOrderCol("Str Dt");
/* 6710 */         notepadSortOrder.setSelectionOrderBy(order);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 6715 */     return order;
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
/* 6726 */     Notepad selNotepad = MilestoneHelper.getNotepadFromSession(0, context);
/*      */     
/* 6728 */     if (selNotepad == null) {
/*      */       
/* 6730 */       User user = (User)context.getSession().getAttribute("user");
/* 6731 */       if (user != null) {
/* 6732 */         selNotepad = getInstance().getSelectionNotepad(context, user.getUserId(), 0);
/* 6733 */         MilestoneHelper.putNotepadIntoSession(selNotepad, context);
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
/* 6745 */     if ((selection != null && selection.getReleaseFamilyId() != 820 && IsDigitalEquivalent(selection) && (
/* 6746 */       !selection.releaseType.abbreviation.equals("PR") || !selection.getUpc().trim().equals(""))) || statusCode.equals("DELETE")) {
/*      */       
/* 6748 */       String query = "INSERT INTO [dbo].[MilestoneCPRSD] ([release_id],[statusCode]) VALUES (" + 
/* 6749 */         selection.getSelectionID() + ",'" + statusCode + "')";
/* 6750 */       JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 6751 */       connector.runQuery();
/* 6752 */       connector.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean IsDigitalEquivalent(Selection selection) {
/* 6762 */     boolean results = false;
/*      */     
/* 6764 */     Vector configCodes = Cache.getConfigCodes();
/* 6765 */     for (int x = 0; x < configCodes.size(); x++) {
/*      */       
/* 6767 */       LookupObject lo = (LookupObject)configCodes.get(x);
/* 6768 */       if (lo != null && lo.getAbbreviation().equals(selection.getConfigCode()) && lo.getIsDigitalEquivalent()) {
/*      */         
/* 6770 */         results = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/* 6776 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String IsDigitalEquivalentGetConfigs() {
/* 6785 */     results = "";
/*      */     
/* 6787 */     Vector configCodes = Cache.getConfigCodes();
/* 6788 */     for (int x = 0; x < configCodes.size(); x++) {
/*      */       
/* 6790 */       LookupObject lo = (LookupObject)configCodes.get(x);
/* 6791 */       if (lo != null && lo.getIsDigitalEquivalent())
/* 6792 */         results = String.valueOf(results) + lo.getAbbreviation() + ";"; 
/*      */     } 
/* 6794 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean IsPfmDraftOrFinal(int releaseId) {
/* 6803 */     boolean result = false;
/* 6804 */     String query = "SELECT isnull(print_flag,'') as print_flag FROM dbo.Pfm_Selection WHERE (release_id = " + releaseId + ")";
/* 6805 */     JdbcConnector connector = MilestoneHelper.getConnector(query);
/* 6806 */     connector.runQuery();
/*      */     
/* 6808 */     String printFlag = "";
/* 6809 */     if (connector.more())
/*      */     {
/*      */ 
/*      */       
/* 6813 */       result = true;
/*      */     }
/* 6815 */     connector.close();
/*      */     
/* 6817 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\SelectionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */