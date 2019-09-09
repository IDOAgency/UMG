/*     */ package WEB-INF.classes.net.umusic.milestone.alps;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Calendar;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.umusic.milestone.alps.DcProjectSearchResults;
/*     */ import org.apache.axis.description.ElementDesc;
/*     */ import org.apache.axis.description.TypeDesc;
/*     */ import org.apache.axis.encoding.Deserializer;
/*     */ import org.apache.axis.encoding.Serializer;
/*     */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*     */ import org.apache.axis.encoding.ser.BeanSerializer;
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
/*     */ public class DcProjectSearchResults
/*     */   implements Serializable
/*     */ {
/*     */   private String artist_First_Name;
/*     */   private String artist_Last_Name_Group_Name;
/*     */   private Integer project_ID;
/*     */   private String project_Title;
/*     */   private String project_Description;
/*     */   private Calendar create_Date;
/*     */   private Integer archimedes_ID;
/*     */   private String JDE_Project_;
/*     */   private String RMS_Project_;
/*     */   private String imprint;
/*     */   private Boolean active;
/*     */   private Integer artistRosterActivity;
/*     */   private String SAPProjectNumber;
/*     */   private String financialLabelDescription;
/*     */   
/*     */   public DcProjectSearchResults(String artist_First_Name, String artist_Last_Name_Group_Name, Integer project_ID, String project_Title, String project_Description, Calendar create_Date, Integer archimedes_ID, String JDE_Project_, String RMS_Project_, String imprint, Boolean active, Integer artistRosterActivity, String SAPProjectNumber, String financialLabelDescription) {
/*  57 */     this.artist_First_Name = artist_First_Name;
/*  58 */     this.artist_Last_Name_Group_Name = artist_Last_Name_Group_Name;
/*  59 */     this.project_ID = project_ID;
/*  60 */     this.project_Title = project_Title;
/*  61 */     this.project_Description = project_Description;
/*  62 */     this.create_Date = create_Date;
/*  63 */     this.archimedes_ID = archimedes_ID;
/*  64 */     this.JDE_Project_ = JDE_Project_;
/*  65 */     this.RMS_Project_ = RMS_Project_;
/*  66 */     this.imprint = imprint;
/*  67 */     this.active = active;
/*  68 */     this.artistRosterActivity = artistRosterActivity;
/*  69 */     this.SAPProjectNumber = SAPProjectNumber;
/*  70 */     this.financialLabelDescription = financialLabelDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public String getArtist_First_Name() { return this.artist_First_Name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setArtist_First_Name(String artist_First_Name) { this.artist_First_Name = artist_First_Name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public String getArtist_Last_Name_Group_Name() { return this.artist_Last_Name_Group_Name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public void setArtist_Last_Name_Group_Name(String artist_Last_Name_Group_Name) { this.artist_Last_Name_Group_Name = artist_Last_Name_Group_Name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public Integer getProject_ID() { return this.project_ID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public void setProject_ID(Integer project_ID) { this.project_ID = project_ID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public String getProject_Title() { return this.project_Title; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void setProject_Title(String project_Title) { this.project_Title = project_Title; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public String getProject_Description() { return this.project_Description; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void setProject_Description(String project_Description) { this.project_Description = project_Description; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public Calendar getCreate_Date() { return this.create_Date; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public void setCreate_Date(Calendar create_Date) { this.create_Date = create_Date; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public Integer getArchimedes_ID() { return this.archimedes_ID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public void setArchimedes_ID(Integer archimedes_ID) { this.archimedes_ID = archimedes_ID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public String getJDE_Project_() { return this.JDE_Project_; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   public void setJDE_Project_(String JDE_Project_) { this.JDE_Project_ = JDE_Project_; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public String getRMS_Project_() { return this.RMS_Project_; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public void setRMS_Project_(String RMS_Project_) { this.RMS_Project_ = RMS_Project_; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public String getImprint() { return this.imprint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public void setImprint(String imprint) { this.imprint = imprint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public Boolean getActive() { return this.active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 290 */   public void setActive(Boolean active) { this.active = active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   public Integer getArtistRosterActivity() { return this.artistRosterActivity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   public void setArtistRosterActivity(Integer artistRosterActivity) { this.artistRosterActivity = artistRosterActivity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public String getSAPProjectNumber() { return this.SAPProjectNumber; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 330 */   public void setSAPProjectNumber(String SAPProjectNumber) { this.SAPProjectNumber = SAPProjectNumber; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 340 */   public String getFinancialLabelDescription() { return this.financialLabelDescription; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 350 */   public void setFinancialLabelDescription(String financialLabelDescription) { this.financialLabelDescription = financialLabelDescription; }
/*     */ 
/*     */   
/* 353 */   private Object __equalsCalc = null;
/*     */   public boolean equals(Object obj) {
/* 355 */     if (!(obj instanceof DcProjectSearchResults)) return false; 
/* 356 */     DcProjectSearchResults other = (DcProjectSearchResults)obj;
/* 357 */     if (obj == null) return false; 
/* 358 */     if (this == obj) return true; 
/* 359 */     if (this.__equalsCalc != null) {
/* 360 */       return (this.__equalsCalc == obj);
/*     */     }
/* 362 */     this.__equalsCalc = obj;
/*     */     
/* 364 */     boolean _equals = 
/* 365 */       (((this.artist_First_Name == null && other.getArtist_First_Name() == null) || (
/* 366 */       this.artist_First_Name != null && 
/* 367 */       this.artist_First_Name.equals(other.getArtist_First_Name()))) && ((
/* 368 */       this.artist_Last_Name_Group_Name == null && other.getArtist_Last_Name_Group_Name() == null) || (
/* 369 */       this.artist_Last_Name_Group_Name != null && 
/* 370 */       this.artist_Last_Name_Group_Name.equals(other.getArtist_Last_Name_Group_Name()))) && ((
/* 371 */       this.project_ID == null && other.getProject_ID() == null) || (
/* 372 */       this.project_ID != null && 
/* 373 */       this.project_ID.equals(other.getProject_ID()))) && ((
/* 374 */       this.project_Title == null && other.getProject_Title() == null) || (
/* 375 */       this.project_Title != null && 
/* 376 */       this.project_Title.equals(other.getProject_Title()))) && ((
/* 377 */       this.project_Description == null && other.getProject_Description() == null) || (
/* 378 */       this.project_Description != null && 
/* 379 */       this.project_Description.equals(other.getProject_Description()))) && ((
/* 380 */       this.create_Date == null && other.getCreate_Date() == null) || (
/* 381 */       this.create_Date != null && 
/* 382 */       this.create_Date.equals(other.getCreate_Date()))) && ((
/* 383 */       this.archimedes_ID == null && other.getArchimedes_ID() == null) || (
/* 384 */       this.archimedes_ID != null && 
/* 385 */       this.archimedes_ID.equals(other.getArchimedes_ID()))) && ((
/* 386 */       this.JDE_Project_ == null && other.getJDE_Project_() == null) || (
/* 387 */       this.JDE_Project_ != null && 
/* 388 */       this.JDE_Project_.equals(other.getJDE_Project_()))) && ((
/* 389 */       this.RMS_Project_ == null && other.getRMS_Project_() == null) || (
/* 390 */       this.RMS_Project_ != null && 
/* 391 */       this.RMS_Project_.equals(other.getRMS_Project_()))) && ((
/* 392 */       this.imprint == null && other.getImprint() == null) || (
/* 393 */       this.imprint != null && 
/* 394 */       this.imprint.equals(other.getImprint()))) && ((
/* 395 */       this.active == null && other.getActive() == null) || (
/* 396 */       this.active != null && 
/* 397 */       this.active.equals(other.getActive()))) && ((
/* 398 */       this.artistRosterActivity == null && other.getArtistRosterActivity() == null) || (
/* 399 */       this.artistRosterActivity != null && 
/* 400 */       this.artistRosterActivity.equals(other.getArtistRosterActivity()))) && ((
/* 401 */       this.SAPProjectNumber == null && other.getSAPProjectNumber() == null) || (
/* 402 */       this.SAPProjectNumber != null && 
/* 403 */       this.SAPProjectNumber.equals(other.getSAPProjectNumber()))) && ((
/* 404 */       this.financialLabelDescription == null && other.getFinancialLabelDescription() == null) || (
/* 405 */       this.financialLabelDescription != null && 
/* 406 */       this.financialLabelDescription.equals(other.getFinancialLabelDescription()))));
/* 407 */     this.__equalsCalc = null;
/* 408 */     return _equals;
/*     */   }
/*     */   private boolean __hashCodeCalc = false;
/*     */   
/*     */   public int hashCode() {
/* 413 */     if (this.__hashCodeCalc) {
/* 414 */       return 0;
/*     */     }
/* 416 */     this.__hashCodeCalc = true;
/* 417 */     int _hashCode = 1;
/* 418 */     if (getArtist_First_Name() != null) {
/* 419 */       _hashCode += getArtist_First_Name().hashCode();
/*     */     }
/* 421 */     if (getArtist_Last_Name_Group_Name() != null) {
/* 422 */       _hashCode += getArtist_Last_Name_Group_Name().hashCode();
/*     */     }
/* 424 */     if (getProject_ID() != null) {
/* 425 */       _hashCode += getProject_ID().hashCode();
/*     */     }
/* 427 */     if (getProject_Title() != null) {
/* 428 */       _hashCode += getProject_Title().hashCode();
/*     */     }
/* 430 */     if (getProject_Description() != null) {
/* 431 */       _hashCode += getProject_Description().hashCode();
/*     */     }
/* 433 */     if (getCreate_Date() != null) {
/* 434 */       _hashCode += getCreate_Date().hashCode();
/*     */     }
/* 436 */     if (getArchimedes_ID() != null) {
/* 437 */       _hashCode += getArchimedes_ID().hashCode();
/*     */     }
/* 439 */     if (getJDE_Project_() != null) {
/* 440 */       _hashCode += getJDE_Project_().hashCode();
/*     */     }
/* 442 */     if (getRMS_Project_() != null) {
/* 443 */       _hashCode += getRMS_Project_().hashCode();
/*     */     }
/* 445 */     if (getImprint() != null) {
/* 446 */       _hashCode += getImprint().hashCode();
/*     */     }
/* 448 */     if (getActive() != null) {
/* 449 */       _hashCode += getActive().hashCode();
/*     */     }
/* 451 */     if (getArtistRosterActivity() != null) {
/* 452 */       _hashCode += getArtistRosterActivity().hashCode();
/*     */     }
/* 454 */     if (getSAPProjectNumber() != null) {
/* 455 */       _hashCode += getSAPProjectNumber().hashCode();
/*     */     }
/* 457 */     if (getFinancialLabelDescription() != null) {
/* 458 */       _hashCode += getFinancialLabelDescription().hashCode();
/*     */     }
/* 460 */     this.__hashCodeCalc = false;
/* 461 */     return _hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 466 */   private static TypeDesc typeDesc = new TypeDesc(DcProjectSearchResults.class, true);
/*     */   
/*     */   static  {
/* 469 */     typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults"));
/* 470 */     elemField = new ElementDesc();
/* 471 */     elemField.setFieldName("artist_First_Name");
/* 472 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Artist_First_Name"));
/* 473 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 474 */     elemField.setMinOccurs(0);
/* 475 */     elemField.setNillable(true);
/* 476 */     typeDesc.addFieldDesc(elemField);
/* 477 */     elemField = new ElementDesc();
/* 478 */     elemField.setFieldName("artist_Last_Name_Group_Name");
/* 479 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Artist_Last_Name_Group_Name"));
/* 480 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 481 */     elemField.setMinOccurs(0);
/* 482 */     elemField.setNillable(true);
/* 483 */     typeDesc.addFieldDesc(elemField);
/* 484 */     elemField = new ElementDesc();
/* 485 */     elemField.setFieldName("project_ID");
/* 486 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Project_ID"));
/* 487 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 488 */     elemField.setMinOccurs(0);
/* 489 */     elemField.setNillable(false);
/* 490 */     typeDesc.addFieldDesc(elemField);
/* 491 */     elemField = new ElementDesc();
/* 492 */     elemField.setFieldName("project_Title");
/* 493 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Project_Title"));
/* 494 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 495 */     elemField.setMinOccurs(0);
/* 496 */     elemField.setNillable(true);
/* 497 */     typeDesc.addFieldDesc(elemField);
/* 498 */     elemField = new ElementDesc();
/* 499 */     elemField.setFieldName("project_Description");
/* 500 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Project_Description"));
/* 501 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 502 */     elemField.setMinOccurs(0);
/* 503 */     elemField.setNillable(true);
/* 504 */     typeDesc.addFieldDesc(elemField);
/* 505 */     elemField = new ElementDesc();
/* 506 */     elemField.setFieldName("create_Date");
/* 507 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Create_Date"));
/* 508 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
/* 509 */     elemField.setMinOccurs(0);
/* 510 */     elemField.setNillable(false);
/* 511 */     typeDesc.addFieldDesc(elemField);
/* 512 */     elemField = new ElementDesc();
/* 513 */     elemField.setFieldName("archimedes_ID");
/* 514 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Archimedes_ID"));
/* 515 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 516 */     elemField.setMinOccurs(0);
/* 517 */     elemField.setNillable(false);
/* 518 */     typeDesc.addFieldDesc(elemField);
/* 519 */     elemField = new ElementDesc();
/* 520 */     elemField.setFieldName("JDE_Project_");
/* 521 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "JDE_Project_"));
/* 522 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 523 */     elemField.setMinOccurs(0);
/* 524 */     elemField.setNillable(true);
/* 525 */     typeDesc.addFieldDesc(elemField);
/* 526 */     elemField = new ElementDesc();
/* 527 */     elemField.setFieldName("RMS_Project_");
/* 528 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "RMS_Project_"));
/* 529 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 530 */     elemField.setMinOccurs(0);
/* 531 */     elemField.setNillable(true);
/* 532 */     typeDesc.addFieldDesc(elemField);
/* 533 */     elemField = new ElementDesc();
/* 534 */     elemField.setFieldName("imprint");
/* 535 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Imprint"));
/* 536 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 537 */     elemField.setMinOccurs(0);
/* 538 */     elemField.setNillable(true);
/* 539 */     typeDesc.addFieldDesc(elemField);
/* 540 */     elemField = new ElementDesc();
/* 541 */     elemField.setFieldName("active");
/* 542 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Active"));
/* 543 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 544 */     elemField.setMinOccurs(0);
/* 545 */     elemField.setNillable(false);
/* 546 */     typeDesc.addFieldDesc(elemField);
/* 547 */     elemField = new ElementDesc();
/* 548 */     elemField.setFieldName("artistRosterActivity");
/* 549 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArtistRosterActivity"));
/* 550 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 551 */     elemField.setMinOccurs(0);
/* 552 */     elemField.setNillable(false);
/* 553 */     typeDesc.addFieldDesc(elemField);
/* 554 */     elemField = new ElementDesc();
/* 555 */     elemField.setFieldName("SAPProjectNumber");
/* 556 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "SAPProjectNumber"));
/* 557 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 558 */     elemField.setMinOccurs(0);
/* 559 */     elemField.setNillable(true);
/* 560 */     typeDesc.addFieldDesc(elemField);
/* 561 */     elemField = new ElementDesc();
/* 562 */     elemField.setFieldName("financialLabelDescription");
/* 563 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FinancialLabelDescription"));
/* 564 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 565 */     elemField.setMinOccurs(0);
/* 566 */     elemField.setNillable(true);
/* 567 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 574 */   public static TypeDesc getTypeDesc() { return typeDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
/* 584 */     return 
/* 585 */       new BeanSerializer(
/* 586 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
/* 596 */     return 
/* 597 */       new BeanDeserializer(
/* 598 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */   
/*     */   public DcProjectSearchResults() {}
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcProjectSearchResults.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */