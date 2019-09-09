/*     */ package WEB-INF.classes.net.umusic.milestone.alps;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.umusic.milestone.alps.DsLabelStructureInterface;
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
/*     */ 
/*     */ public class DsLabelStructureInterface
/*     */   implements Serializable
/*     */ {
/*     */   private String company;
/*     */   private String division;
/*     */   private String entityName;
/*     */   private String entityType;
/*     */   private String environmentName;
/*     */   private String familyName;
/*     */   private Integer ID;
/*     */   private String JDEReportingCo;
/*     */   private String JDEReportingUnit;
/*     */   private String label;
/*     */   private String legacyOperatingCo;
/*     */   private String legacyOperatingUnit;
/*     */   private String legacySublabel;
/*     */   private String legacySuperlabel;
/*     */   
/*     */   public DsLabelStructureInterface(String company, String division, String entityName, String entityType, String environmentName, String familyName, Integer ID, String JDEReportingCo, String JDEReportingUnit, String label, String legacyOperatingCo, String legacyOperatingUnit, String legacySublabel, String legacySuperlabel) {
/*  57 */     this.company = company;
/*  58 */     this.division = division;
/*  59 */     this.entityName = entityName;
/*  60 */     this.entityType = entityType;
/*  61 */     this.environmentName = environmentName;
/*  62 */     this.familyName = familyName;
/*  63 */     this.ID = ID;
/*  64 */     this.JDEReportingCo = JDEReportingCo;
/*  65 */     this.JDEReportingUnit = JDEReportingUnit;
/*  66 */     this.label = label;
/*  67 */     this.legacyOperatingCo = legacyOperatingCo;
/*  68 */     this.legacyOperatingUnit = legacyOperatingUnit;
/*  69 */     this.legacySublabel = legacySublabel;
/*  70 */     this.legacySuperlabel = legacySuperlabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public String getCompany() { return this.company; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setCompany(String company) { this.company = company; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public String getDivision() { return this.division; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public void setDivision(String division) { this.division = division; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public String getEntityName() { return this.entityName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public void setEntityName(String entityName) { this.entityName = entityName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public String getEntityType() { return this.entityType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void setEntityType(String entityType) { this.entityType = entityType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public String getEnvironmentName() { return this.environmentName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void setEnvironmentName(String environmentName) { this.environmentName = environmentName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public String getFamilyName() { return this.familyName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public void setFamilyName(String familyName) { this.familyName = familyName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public Integer getID() { return this.ID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public void setID(Integer ID) { this.ID = ID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public String getJDEReportingCo() { return this.JDEReportingCo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   public void setJDEReportingCo(String JDEReportingCo) { this.JDEReportingCo = JDEReportingCo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public String getJDEReportingUnit() { return this.JDEReportingUnit; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public void setJDEReportingUnit(String JDEReportingUnit) { this.JDEReportingUnit = JDEReportingUnit; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public String getLabel() { return this.label; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public void setLabel(String label) { this.label = label; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public String getLegacyOperatingCo() { return this.legacyOperatingCo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 290 */   public void setLegacyOperatingCo(String legacyOperatingCo) { this.legacyOperatingCo = legacyOperatingCo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   public String getLegacyOperatingUnit() { return this.legacyOperatingUnit; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   public void setLegacyOperatingUnit(String legacyOperatingUnit) { this.legacyOperatingUnit = legacyOperatingUnit; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public String getLegacySublabel() { return this.legacySublabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 330 */   public void setLegacySublabel(String legacySublabel) { this.legacySublabel = legacySublabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 340 */   public String getLegacySuperlabel() { return this.legacySuperlabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 350 */   public void setLegacySuperlabel(String legacySuperlabel) { this.legacySuperlabel = legacySuperlabel; }
/*     */ 
/*     */   
/* 353 */   private Object __equalsCalc = null;
/*     */   public boolean equals(Object obj) {
/* 355 */     if (!(obj instanceof DsLabelStructureInterface)) return false; 
/* 356 */     DsLabelStructureInterface other = (DsLabelStructureInterface)obj;
/* 357 */     if (obj == null) return false; 
/* 358 */     if (this == obj) return true; 
/* 359 */     if (this.__equalsCalc != null) {
/* 360 */       return (this.__equalsCalc == obj);
/*     */     }
/* 362 */     this.__equalsCalc = obj;
/*     */     
/* 364 */     boolean _equals = 
/* 365 */       (((this.company == null && other.getCompany() == null) || (
/* 366 */       this.company != null && 
/* 367 */       this.company.equals(other.getCompany()))) && ((
/* 368 */       this.division == null && other.getDivision() == null) || (
/* 369 */       this.division != null && 
/* 370 */       this.division.equals(other.getDivision()))) && ((
/* 371 */       this.entityName == null && other.getEntityName() == null) || (
/* 372 */       this.entityName != null && 
/* 373 */       this.entityName.equals(other.getEntityName()))) && ((
/* 374 */       this.entityType == null && other.getEntityType() == null) || (
/* 375 */       this.entityType != null && 
/* 376 */       this.entityType.equals(other.getEntityType()))) && ((
/* 377 */       this.environmentName == null && other.getEnvironmentName() == null) || (
/* 378 */       this.environmentName != null && 
/* 379 */       this.environmentName.equals(other.getEnvironmentName()))) && ((
/* 380 */       this.familyName == null && other.getFamilyName() == null) || (
/* 381 */       this.familyName != null && 
/* 382 */       this.familyName.equals(other.getFamilyName()))) && ((
/* 383 */       this.ID == null && other.getID() == null) || (
/* 384 */       this.ID != null && 
/* 385 */       this.ID.equals(other.getID()))) && ((
/* 386 */       this.JDEReportingCo == null && other.getJDEReportingCo() == null) || (
/* 387 */       this.JDEReportingCo != null && 
/* 388 */       this.JDEReportingCo.equals(other.getJDEReportingCo()))) && ((
/* 389 */       this.JDEReportingUnit == null && other.getJDEReportingUnit() == null) || (
/* 390 */       this.JDEReportingUnit != null && 
/* 391 */       this.JDEReportingUnit.equals(other.getJDEReportingUnit()))) && ((
/* 392 */       this.label == null && other.getLabel() == null) || (
/* 393 */       this.label != null && 
/* 394 */       this.label.equals(other.getLabel()))) && ((
/* 395 */       this.legacyOperatingCo == null && other.getLegacyOperatingCo() == null) || (
/* 396 */       this.legacyOperatingCo != null && 
/* 397 */       this.legacyOperatingCo.equals(other.getLegacyOperatingCo()))) && ((
/* 398 */       this.legacyOperatingUnit == null && other.getLegacyOperatingUnit() == null) || (
/* 399 */       this.legacyOperatingUnit != null && 
/* 400 */       this.legacyOperatingUnit.equals(other.getLegacyOperatingUnit()))) && ((
/* 401 */       this.legacySublabel == null && other.getLegacySublabel() == null) || (
/* 402 */       this.legacySublabel != null && 
/* 403 */       this.legacySublabel.equals(other.getLegacySublabel()))) && ((
/* 404 */       this.legacySuperlabel == null && other.getLegacySuperlabel() == null) || (
/* 405 */       this.legacySuperlabel != null && 
/* 406 */       this.legacySuperlabel.equals(other.getLegacySuperlabel()))));
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
/* 418 */     if (getCompany() != null) {
/* 419 */       _hashCode += getCompany().hashCode();
/*     */     }
/* 421 */     if (getDivision() != null) {
/* 422 */       _hashCode += getDivision().hashCode();
/*     */     }
/* 424 */     if (getEntityName() != null) {
/* 425 */       _hashCode += getEntityName().hashCode();
/*     */     }
/* 427 */     if (getEntityType() != null) {
/* 428 */       _hashCode += getEntityType().hashCode();
/*     */     }
/* 430 */     if (getEnvironmentName() != null) {
/* 431 */       _hashCode += getEnvironmentName().hashCode();
/*     */     }
/* 433 */     if (getFamilyName() != null) {
/* 434 */       _hashCode += getFamilyName().hashCode();
/*     */     }
/* 436 */     if (getID() != null) {
/* 437 */       _hashCode += getID().hashCode();
/*     */     }
/* 439 */     if (getJDEReportingCo() != null) {
/* 440 */       _hashCode += getJDEReportingCo().hashCode();
/*     */     }
/* 442 */     if (getJDEReportingUnit() != null) {
/* 443 */       _hashCode += getJDEReportingUnit().hashCode();
/*     */     }
/* 445 */     if (getLabel() != null) {
/* 446 */       _hashCode += getLabel().hashCode();
/*     */     }
/* 448 */     if (getLegacyOperatingCo() != null) {
/* 449 */       _hashCode += getLegacyOperatingCo().hashCode();
/*     */     }
/* 451 */     if (getLegacyOperatingUnit() != null) {
/* 452 */       _hashCode += getLegacyOperatingUnit().hashCode();
/*     */     }
/* 454 */     if (getLegacySublabel() != null) {
/* 455 */       _hashCode += getLegacySublabel().hashCode();
/*     */     }
/* 457 */     if (getLegacySuperlabel() != null) {
/* 458 */       _hashCode += getLegacySuperlabel().hashCode();
/*     */     }
/* 460 */     this.__hashCodeCalc = false;
/* 461 */     return _hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 466 */   private static TypeDesc typeDesc = new TypeDesc(DsLabelStructureInterface.class, true);
/*     */   
/*     */   static  {
/* 469 */     typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dsLabelStructureInterface"));
/* 470 */     elemField = new ElementDesc();
/* 471 */     elemField.setFieldName("company");
/* 472 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Company"));
/* 473 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 474 */     elemField.setMinOccurs(0);
/* 475 */     elemField.setNillable(true);
/* 476 */     typeDesc.addFieldDesc(elemField);
/* 477 */     elemField = new ElementDesc();
/* 478 */     elemField.setFieldName("division");
/* 479 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Division"));
/* 480 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 481 */     elemField.setMinOccurs(0);
/* 482 */     elemField.setNillable(true);
/* 483 */     typeDesc.addFieldDesc(elemField);
/* 484 */     elemField = new ElementDesc();
/* 485 */     elemField.setFieldName("entityName");
/* 486 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EntityName"));
/* 487 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 488 */     elemField.setMinOccurs(0);
/* 489 */     elemField.setNillable(true);
/* 490 */     typeDesc.addFieldDesc(elemField);
/* 491 */     elemField = new ElementDesc();
/* 492 */     elemField.setFieldName("entityType");
/* 493 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EntityType"));
/* 494 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 495 */     elemField.setMinOccurs(0);
/* 496 */     elemField.setNillable(true);
/* 497 */     typeDesc.addFieldDesc(elemField);
/* 498 */     elemField = new ElementDesc();
/* 499 */     elemField.setFieldName("environmentName");
/* 500 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EnvironmentName"));
/* 501 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 502 */     elemField.setMinOccurs(0);
/* 503 */     elemField.setNillable(true);
/* 504 */     typeDesc.addFieldDesc(elemField);
/* 505 */     elemField = new ElementDesc();
/* 506 */     elemField.setFieldName("familyName");
/* 507 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FamilyName"));
/* 508 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 509 */     elemField.setMinOccurs(0);
/* 510 */     elemField.setNillable(true);
/* 511 */     typeDesc.addFieldDesc(elemField);
/* 512 */     elemField = new ElementDesc();
/* 513 */     elemField.setFieldName("ID");
/* 514 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ID"));
/* 515 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 516 */     elemField.setMinOccurs(0);
/* 517 */     elemField.setNillable(false);
/* 518 */     typeDesc.addFieldDesc(elemField);
/* 519 */     elemField = new ElementDesc();
/* 520 */     elemField.setFieldName("JDEReportingCo");
/* 521 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "JDEReportingCo"));
/* 522 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 523 */     elemField.setMinOccurs(0);
/* 524 */     elemField.setNillable(true);
/* 525 */     typeDesc.addFieldDesc(elemField);
/* 526 */     elemField = new ElementDesc();
/* 527 */     elemField.setFieldName("JDEReportingUnit");
/* 528 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "JDEReportingUnit"));
/* 529 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 530 */     elemField.setMinOccurs(0);
/* 531 */     elemField.setNillable(true);
/* 532 */     typeDesc.addFieldDesc(elemField);
/* 533 */     elemField = new ElementDesc();
/* 534 */     elemField.setFieldName("label");
/* 535 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Label"));
/* 536 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 537 */     elemField.setMinOccurs(0);
/* 538 */     elemField.setNillable(true);
/* 539 */     typeDesc.addFieldDesc(elemField);
/* 540 */     elemField = new ElementDesc();
/* 541 */     elemField.setFieldName("legacyOperatingCo");
/* 542 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacyOperatingCo"));
/* 543 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 544 */     elemField.setMinOccurs(0);
/* 545 */     elemField.setNillable(true);
/* 546 */     typeDesc.addFieldDesc(elemField);
/* 547 */     elemField = new ElementDesc();
/* 548 */     elemField.setFieldName("legacyOperatingUnit");
/* 549 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacyOperatingUnit"));
/* 550 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 551 */     elemField.setMinOccurs(0);
/* 552 */     elemField.setNillable(true);
/* 553 */     typeDesc.addFieldDesc(elemField);
/* 554 */     elemField = new ElementDesc();
/* 555 */     elemField.setFieldName("legacySublabel");
/* 556 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacySublabel"));
/* 557 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 558 */     elemField.setMinOccurs(0);
/* 559 */     elemField.setNillable(true);
/* 560 */     typeDesc.addFieldDesc(elemField);
/* 561 */     elemField = new ElementDesc();
/* 562 */     elemField.setFieldName("legacySuperlabel");
/* 563 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacySuperlabel"));
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
/*     */   public DsLabelStructureInterface() {}
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DsLabelStructureInterface.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */