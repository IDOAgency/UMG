/*     */ package WEB-INF.classes.com.universal.milestone;
/*     */ 
/*     */ import com.universal.milestone.IDJSelectionSortComparator;
/*     */ import com.universal.milestone.MilestoneHelper;
/*     */ import com.universal.milestone.ReleaseType;
/*     */ import com.universal.milestone.Selection;
/*     */ import com.universal.milestone.SelectionManager;
/*     */ import com.universal.milestone.SelectionSubConfiguration;
/*     */ import java.util.Calendar;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IDJSelectionSortComparator
/*     */   implements Comparator
/*     */ {
/*     */   public int compare(Object o1, Object o2) {
/*  24 */     Selection sel1 = (Selection)o1;
/*  25 */     Selection sel2 = (Selection)o2;
/*     */ 
/*     */ 
/*     */     
/*  29 */     boolean sel1TBS = isTBS(sel1);
/*  30 */     boolean sel2TBS = isTBS(sel2);
/*     */ 
/*     */     
/*  33 */     if (sel1TBS && !sel2TBS) {
/*  34 */       return 1;
/*     */     }
/*  36 */     if (!sel1TBS && sel2TBS) {
/*  37 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*  41 */     if (sel1TBS && sel2TBS) {
/*  42 */       return compareTBSSelections(sel1, sel2);
/*     */     }
/*     */     
/*  45 */     boolean sel1Physical = !sel1.getIsDigital();
/*  46 */     boolean sel2Physical = !sel2.getIsDigital();
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
/*  61 */     return compareDateAndTitle(sel1, sel2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareDateAndTitle(Selection sel1, Selection sel2) {
/*     */     Calendar calEntry2;
/*     */     Calendar calEntry1;
/*  74 */     if (sel1.getIsDigital()) {
/*  75 */       calEntry1 = sel1.getDigitalRlsDate();
/*     */     } else {
/*  77 */       calEntry1 = sel1.getStreetDate();
/*     */     } 
/*     */     
/*  80 */     if (sel2.getIsDigital()) {
/*  81 */       calEntry2 = sel2.getDigitalRlsDate();
/*     */     } else {
/*  83 */       calEntry2 = sel2.getStreetDate();
/*     */     } 
/*     */ 
/*     */     
/*  87 */     if ((calEntry1 == null && calEntry2 == null) || calEntry1.equals(calEntry2))
/*     */     {
/*  89 */       return compareSelectionsWithSameDate(sel1, sel2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  94 */     if (calEntry1 == null || calEntry2 == null) {
/*     */       
/*  96 */       if (calEntry1 == null)
/*  97 */         return 1; 
/*  98 */       if (calEntry2 == null) {
/*  99 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/* 103 */     if (calEntry1.before(calEntry2)) {
/* 104 */       return -1;
/*     */     }
/* 106 */     if (calEntry1.after(calEntry2)) {
/* 107 */       return 1;
/*     */     }
/*     */     
/* 110 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareSelectionsWithSameDate(Selection sel1, Selection sel2) {
/* 120 */     boolean sel1NonActive = (isTBS(sel1) && !isActiveStreetDate(sel1));
/* 121 */     boolean sel2NonActive = (isTBS(sel2) && !isActiveStreetDate(sel1));
/* 122 */     if (sel1NonActive && sel2NonActive) {
/* 123 */       String titleEntry1 = sel1.getTitle().toUpperCase();
/* 124 */       String titleEntry2 = sel2.getTitle().toUpperCase();
/* 125 */       return titleEntry1.compareTo(titleEntry2);
/*     */     } 
/*     */     
/* 128 */     boolean isSel1CommercialPhysicalFullLength = (isPhysicalFullLength(sel1) && isCommercial(sel1));
/* 129 */     boolean isSel2CommercialPhysicalFullLength = (isPhysicalFullLength(sel2) && isCommercial(sel2));
/* 130 */     boolean isSel1PromoPhysicalFullLength = (isPhysicalFullLength(sel1) && !isCommercial(sel1));
/* 131 */     boolean isSel2PromoPhysicalFullLength = (isPhysicalFullLength(sel2) && !isCommercial(sel2));
/*     */     
/* 133 */     boolean isSel1DigitalFullLength = isDigitalFullLength(sel1);
/* 134 */     boolean isSel2DigitalFullLength = isDigitalFullLength(sel2);
/*     */     
/* 136 */     boolean isSel1CommercialPhysicalSingle = (isPhysicalSingle(sel1) && isCommercial(sel1));
/* 137 */     boolean isSel2CommercialPhysicalSingle = (isPhysicalSingle(sel2) && isCommercial(sel2));
/* 138 */     boolean isSel1PromoPhysicalSingle = (isPhysicalSingle(sel1) && !isCommercial(sel1));
/* 139 */     boolean isSel2PromoPhysicalSingle = (isPhysicalSingle(sel2) && !isCommercial(sel2));
/*     */     
/* 141 */     boolean isSel1DigitalSingle = isDigitalSingle(sel1);
/* 142 */     boolean isSel2DigitalSingle = isDigitalSingle(sel2);
/*     */ 
/*     */     
/* 145 */     if (isSel1CommercialPhysicalFullLength && !isSel2CommercialPhysicalFullLength) {
/* 146 */       return -1;
/*     */     }
/* 148 */     if (!isSel1CommercialPhysicalFullLength && isSel2CommercialPhysicalFullLength) {
/* 149 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 154 */     if (isSel1PromoPhysicalFullLength && !isSel2PromoPhysicalFullLength) {
/* 155 */       return -1;
/*     */     }
/* 157 */     if (!isSel1PromoPhysicalFullLength && isSel2PromoPhysicalFullLength) {
/* 158 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 163 */     if (isSel1PromoPhysicalSingle && !isSel2PromoPhysicalSingle) {
/* 164 */       return -1;
/*     */     }
/* 166 */     if (!isSel1PromoPhysicalSingle && isSel2PromoPhysicalSingle) {
/* 167 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 172 */     if (isSel1CommercialPhysicalSingle && !isSel2CommercialPhysicalSingle) {
/* 173 */       return -1;
/*     */     }
/* 175 */     if (!isSel1CommercialPhysicalSingle && isSel2CommercialPhysicalSingle) {
/* 176 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (isSel1DigitalFullLength && !isSel2DigitalFullLength) {
/* 182 */       return -1;
/*     */     }
/* 184 */     if (!isSel1DigitalFullLength && isSel2DigitalFullLength) {
/* 185 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 190 */     if (isSel1DigitalSingle && !isSel2DigitalSingle) {
/* 191 */       return -1;
/*     */     }
/* 193 */     if (!isSel1DigitalSingle && isSel2DigitalSingle) {
/* 194 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 199 */     String subConfigString1 = (sel1.getSelectionSubConfig().getSelectionSubConfigurationName() != null) ? sel1.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
/* 200 */     String subConfigString2 = (sel2.getSelectionSubConfig().getSelectionSubConfigurationName() != null) ? sel2.getSelectionSubConfig().getSelectionSubConfigurationName() : "";
/* 201 */     if (subConfigString1.equals("eVideo") && !subConfigString2.equals("eVideo")) {
/* 202 */       return -1;
/*     */     }
/* 204 */     if (!subConfigString1.equals("eVideo") && subConfigString2.equals("eVideo")) {
/* 205 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 209 */     String titleEntry1 = sel1.getTitle().toUpperCase();
/* 210 */     String titleEntry2 = sel2.getTitle().toUpperCase();
/*     */     
/* 212 */     if (!titleEntry1.equals(titleEntry2))
/*     */     {
/* 214 */       return titleEntry1.compareTo(titleEntry2);
/*     */     }
/*     */     
/* 217 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 222 */   public boolean isTBS(Selection sel) { return SelectionManager.getLookupObjectValue(sel.getSelectionStatus())
/* 223 */       .equalsIgnoreCase("TBS"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTBSSelections(Selection sel1, Selection sel2) {
/* 231 */     boolean sel1Active = isActiveStreetDate(sel1);
/* 232 */     boolean sel2Active = isActiveStreetDate(sel2);
/* 233 */     if (sel1Active && sel2Active)
/*     */     {
/* 235 */       return compareDateAndTitle(sel1, sel2);
/*     */     }
/* 237 */     if (sel1Active && !sel2Active)
/*     */     {
/* 239 */       return -1;
/*     */     }
/* 241 */     if (!sel1Active && sel2Active)
/*     */     {
/* 243 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 247 */     return compareSelectionsWithSameDate(sel1, sel2);
/*     */   }
/*     */   public boolean isActiveStreetDate(Selection sel) {
/*     */     Calendar streetCal;
/* 251 */     String streetDate = "";
/*     */     
/* 253 */     if (sel.getIsDigital()) {
/* 254 */       streetCal = sel.getDigitalRlsDate();
/*     */     } else {
/*     */       
/* 257 */       streetCal = sel.getStreetDate();
/*     */     } 
/* 259 */     streetDate = MilestoneHelper.getFormatedDate(streetCal);
/*     */     
/* 261 */     return !streetDate.equals("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPhysicalFullLength(Selection sel) {
/* 269 */     boolean fullLength = false;
/* 270 */     String subconfigAbbreviation = "";
/* 271 */     SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
/* 272 */     if (subconfig != null)
/* 273 */       subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
/* 274 */         "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
/* 275 */     String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*     */     
/* 277 */     if (subconfigAbbreviation.equalsIgnoreCase("DVDVID") || 
/* 278 */       subconfigAbbreviation.equalsIgnoreCase("ECD") || 
/* 279 */       subconfigAbbreviation.equalsIgnoreCase("ECDEP") || 
/* 280 */       subconfigAbbreviation.equalsIgnoreCase("CASS") || 
/* 281 */       subconfigAbbreviation.equalsIgnoreCase("CASSEP") || 
/* 282 */       subconfigAbbreviation.equalsIgnoreCase("8TRK") || 
/* 283 */       subconfigAbbreviation.equalsIgnoreCase("CDROM") || 
/* 284 */       subconfigAbbreviation.equalsIgnoreCase("CDVID") || 
/* 285 */       subconfigAbbreviation.equalsIgnoreCase("DCCASS") || 
/* 286 */       subconfigAbbreviation.equalsIgnoreCase("LASER") || 
/* 287 */       subconfigAbbreviation.equalsIgnoreCase("MIXED") || 
/* 288 */       subconfigAbbreviation.equalsIgnoreCase("VIDEO") || 
/* 289 */       subconfigAbbreviation.equalsIgnoreCase("CD") || 
/* 290 */       subconfigAbbreviation.equalsIgnoreCase("CDEP") || 
/* 291 */       subconfigAbbreviation.equalsIgnoreCase("CDADVD") || 
/* 292 */       subconfigAbbreviation.equalsIgnoreCase("DP") || 
/* 293 */       subconfigAbbreviation.equalsIgnoreCase("DVDAUD") || 
/* 294 */       subconfigAbbreviation.equalsIgnoreCase("SACD1") || 
/* 295 */       subconfigAbbreviation.equalsIgnoreCase("SACD2") || 
/* 296 */       subconfigAbbreviation.equalsIgnoreCase("SACD3") || 
/* 297 */       subconfigAbbreviation.equalsIgnoreCase("SACD4") || 
/* 298 */       subconfigAbbreviation.equalsIgnoreCase("ALBUM") || 
/* 299 */       subconfigAbbreviation.equalsIgnoreCase("DualDisc"))
/*     */     {
/*     */       
/* 302 */       fullLength = true;
/*     */     }
/* 304 */     return fullLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPhysicalSingle(Selection sel) {
/* 310 */     boolean fullLength = false;
/* 311 */     String subconfigAbbreviation = "";
/* 312 */     SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
/* 313 */     if (subconfig != null)
/* 314 */       subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
/* 315 */         "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
/* 316 */     String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*     */     
/* 318 */     if (subconfigAbbreviation.equalsIgnoreCase("DVDVSL") || 
/* 319 */       subconfigAbbreviation.equalsIgnoreCase("ECDMX") || 
/* 320 */       subconfigAbbreviation.equalsIgnoreCase("ECDSGL") || 
/* 321 */       subconfigAbbreviation.equalsIgnoreCase("CASSGL") || 
/* 322 */       subconfigAbbreviation.equalsIgnoreCase("CASSMX") || 
/* 323 */       subconfigAbbreviation.equalsIgnoreCase("eSNGL") || 
/* 324 */       subconfigAbbreviation.equalsIgnoreCase("CDMX") || 
/* 325 */       subconfigAbbreviation.equalsIgnoreCase("CDSGL") || 
/* 326 */       subconfigAbbreviation.equalsIgnoreCase("DVDASL") || 
/* 327 */       subconfigAbbreviation.equalsIgnoreCase("VNYL10") || 
/* 328 */       subconfigAbbreviation.equalsIgnoreCase("VNYL12") || 
/* 329 */       subconfigAbbreviation.equalsIgnoreCase("VNYL7"))
/*     */     {
/*     */       
/* 332 */       fullLength = true;
/*     */     }
/* 334 */     return fullLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDigitalFullLength(Selection sel) {
/* 340 */     boolean fullLength = false;
/* 341 */     String subconfigAbbreviation = "";
/* 342 */     SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
/* 343 */     if (subconfig != null)
/* 344 */       subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
/* 345 */         "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
/* 346 */     String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*     */     
/* 348 */     if (subconfigAbbreviation.equalsIgnoreCase("DOALB") || 
/* 349 */       subconfigAbbreviation.equalsIgnoreCase("PTCALB") || 
/* 350 */       subconfigAbbreviation.equalsIgnoreCase("PTBALB") || 
/* 351 */       subconfigAbbreviation.equalsIgnoreCase("SWPALB"))
/*     */     {
/*     */       
/* 354 */       fullLength = true;
/*     */     }
/* 356 */     return fullLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDigitalSingle(Selection sel) {
/* 362 */     boolean fullLength = false;
/* 363 */     String subconfigAbbreviation = "";
/* 364 */     SelectionSubConfiguration subconfig = sel.getSelectionSubConfig();
/* 365 */     if (subconfig != null)
/* 366 */       subconfigAbbreviation = (subconfig.getSelectionSubConfigurationAbbreviation() == null) ? 
/* 367 */         "" : subconfig.getSelectionSubConfigurationAbbreviation(); 
/* 368 */     String realConfig = (sel.getSelectionConfig().getSelectionConfigurationAbbreviation() != null) ? sel.getSelectionConfig().getSelectionConfigurationAbbreviation() : "";
/*     */     
/* 370 */     if (subconfigAbbreviation.equalsIgnoreCase("DOSGL") || 
/* 371 */       subconfigAbbreviation.equalsIgnoreCase("PTCSGL") || 
/* 372 */       subconfigAbbreviation.equalsIgnoreCase("PTPSGL") || 
/* 373 */       subconfigAbbreviation.equalsIgnoreCase("SWPSGL"))
/*     */     {
/*     */       
/* 376 */       fullLength = true;
/*     */     }
/* 378 */     return fullLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCommercial(Selection sel) {
/* 383 */     String releaseTypeString = "";
/* 384 */     ReleaseType releaseType = sel.getReleaseType();
/* 385 */     releaseTypeString = releaseType.getName();
/*     */     
/* 387 */     return releaseTypeString.equals("Promotional");
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\IDJSelectionSortComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */