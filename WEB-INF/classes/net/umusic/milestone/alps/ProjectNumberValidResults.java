/*     */ package WEB-INF.classes.net.umusic.milestone.alps;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.umusic.milestone.alps.ProjectNumberValidResults;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProjectNumberValidResults
/*     */   implements Serializable
/*     */ {
/*     */   private String firstName;
/*     */   private String lastName;
/*     */   private Integer labelID;
/*     */   private Integer divisionID;
/*     */   private Integer companyID;
/*     */   private Integer environmentID;
/*     */   private Integer familyID;
/*     */   private String operatingCo;
/*     */   private String superLabel;
/*     */   private String subLabel;
/*     */   private String imprint;
/*     */   private String projectNumber;
/*     */   private Integer archieID;
/*     */   private String description;
/*     */   private String title;
/*     */   private Boolean isValid;
/*     */   
/*     */   public ProjectNumberValidResults(String firstName, String lastName, Integer labelID, Integer divisionID, Integer companyID, Integer environmentID, Integer familyID, String operatingCo, String superLabel, String subLabel, String imprint, String projectNumber, Integer archieID, String description, String title, Boolean isValid) {
/*  63 */     this.firstName = firstName;
/*  64 */     this.lastName = lastName;
/*  65 */     this.labelID = labelID;
/*  66 */     this.divisionID = divisionID;
/*  67 */     this.companyID = companyID;
/*  68 */     this.environmentID = environmentID;
/*  69 */     this.familyID = familyID;
/*  70 */     this.operatingCo = operatingCo;
/*  71 */     this.superLabel = superLabel;
/*  72 */     this.subLabel = subLabel;
/*  73 */     this.imprint = imprint;
/*  74 */     this.projectNumber = projectNumber;
/*  75 */     this.archieID = archieID;
/*  76 */     this.description = description;
/*  77 */     this.title = title;
/*  78 */     this.isValid = isValid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public String getFirstName() { return this.firstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public void setFirstName(String firstName) { this.firstName = firstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   public String getLastName() { return this.lastName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public void setLastName(String lastName) { this.lastName = lastName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public Integer getLabelID() { return this.labelID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   public void setLabelID(Integer labelID) { this.labelID = labelID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public Integer getDivisionID() { return this.divisionID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   public void setDivisionID(Integer divisionID) { this.divisionID = divisionID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 168 */   public Integer getCompanyID() { return this.companyID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public void setCompanyID(Integer companyID) { this.companyID = companyID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public Integer getEnvironmentID() { return this.environmentID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public void setEnvironmentID(Integer environmentID) { this.environmentID = environmentID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 208 */   public Integer getFamilyID() { return this.familyID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   public void setFamilyID(Integer familyID) { this.familyID = familyID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   public String getOperatingCo() { return this.operatingCo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   public void setOperatingCo(String operatingCo) { this.operatingCo = operatingCo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 248 */   public String getSuperLabel() { return this.superLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 258 */   public void setSuperLabel(String superLabel) { this.superLabel = superLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   public String getSubLabel() { return this.subLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 278 */   public void setSubLabel(String subLabel) { this.subLabel = subLabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 288 */   public String getImprint() { return this.imprint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 298 */   public void setImprint(String imprint) { this.imprint = imprint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 308 */   public String getProjectNumber() { return this.projectNumber; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 318 */   public void setProjectNumber(String projectNumber) { this.projectNumber = projectNumber; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public Integer getArchieID() { return this.archieID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 338 */   public void setArchieID(Integer archieID) { this.archieID = archieID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 348 */   public String getDescription() { return this.description; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 358 */   public void setDescription(String description) { this.description = description; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 368 */   public String getTitle() { return this.title; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 378 */   public void setTitle(String title) { this.title = title; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 388 */   public Boolean getIsValid() { return this.isValid; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 398 */   public void setIsValid(Boolean isValid) { this.isValid = isValid; }
/*     */ 
/*     */   
/* 401 */   private Object __equalsCalc = null;
/*     */   public boolean equals(Object obj) {
/* 403 */     if (!(obj instanceof ProjectNumberValidResults)) return false; 
/* 404 */     ProjectNumberValidResults other = (ProjectNumberValidResults)obj;
/* 405 */     if (obj == null) return false; 
/* 406 */     if (this == obj) return true; 
/* 407 */     if (this.__equalsCalc != null) {
/* 408 */       return (this.__equalsCalc == obj);
/*     */     }
/* 410 */     this.__equalsCalc = obj;
/*     */     
/* 412 */     boolean _equals = 
/* 413 */       (((this.firstName == null && other.getFirstName() == null) || (
/* 414 */       this.firstName != null && 
/* 415 */       this.firstName.equals(other.getFirstName()))) && ((
/* 416 */       this.lastName == null && other.getLastName() == null) || (
/* 417 */       this.lastName != null && 
/* 418 */       this.lastName.equals(other.getLastName()))) && ((
/* 419 */       this.labelID == null && other.getLabelID() == null) || (
/* 420 */       this.labelID != null && 
/* 421 */       this.labelID.equals(other.getLabelID()))) && ((
/* 422 */       this.divisionID == null && other.getDivisionID() == null) || (
/* 423 */       this.divisionID != null && 
/* 424 */       this.divisionID.equals(other.getDivisionID()))) && ((
/* 425 */       this.companyID == null && other.getCompanyID() == null) || (
/* 426 */       this.companyID != null && 
/* 427 */       this.companyID.equals(other.getCompanyID()))) && ((
/* 428 */       this.environmentID == null && other.getEnvironmentID() == null) || (
/* 429 */       this.environmentID != null && 
/* 430 */       this.environmentID.equals(other.getEnvironmentID()))) && ((
/* 431 */       this.familyID == null && other.getFamilyID() == null) || (
/* 432 */       this.familyID != null && 
/* 433 */       this.familyID.equals(other.getFamilyID()))) && ((
/* 434 */       this.operatingCo == null && other.getOperatingCo() == null) || (
/* 435 */       this.operatingCo != null && 
/* 436 */       this.operatingCo.equals(other.getOperatingCo()))) && ((
/* 437 */       this.superLabel == null && other.getSuperLabel() == null) || (
/* 438 */       this.superLabel != null && 
/* 439 */       this.superLabel.equals(other.getSuperLabel()))) && ((
/* 440 */       this.subLabel == null && other.getSubLabel() == null) || (
/* 441 */       this.subLabel != null && 
/* 442 */       this.subLabel.equals(other.getSubLabel()))) && ((
/* 443 */       this.imprint == null && other.getImprint() == null) || (
/* 444 */       this.imprint != null && 
/* 445 */       this.imprint.equals(other.getImprint()))) && ((
/* 446 */       this.projectNumber == null && other.getProjectNumber() == null) || (
/* 447 */       this.projectNumber != null && 
/* 448 */       this.projectNumber.equals(other.getProjectNumber()))) && ((
/* 449 */       this.archieID == null && other.getArchieID() == null) || (
/* 450 */       this.archieID != null && 
/* 451 */       this.archieID.equals(other.getArchieID()))) && ((
/* 452 */       this.description == null && other.getDescription() == null) || (
/* 453 */       this.description != null && 
/* 454 */       this.description.equals(other.getDescription()))) && ((
/* 455 */       this.title == null && other.getTitle() == null) || (
/* 456 */       this.title != null && 
/* 457 */       this.title.equals(other.getTitle()))) && ((
/* 458 */       this.isValid == null && other.getIsValid() == null) || (
/* 459 */       this.isValid != null && 
/* 460 */       this.isValid.equals(other.getIsValid()))));
/* 461 */     this.__equalsCalc = null;
/* 462 */     return _equals;
/*     */   }
/*     */   private boolean __hashCodeCalc = false;
/*     */   
/*     */   public int hashCode() {
/* 467 */     if (this.__hashCodeCalc) {
/* 468 */       return 0;
/*     */     }
/* 470 */     this.__hashCodeCalc = true;
/* 471 */     int _hashCode = 1;
/* 472 */     if (getFirstName() != null) {
/* 473 */       _hashCode += getFirstName().hashCode();
/*     */     }
/* 475 */     if (getLastName() != null) {
/* 476 */       _hashCode += getLastName().hashCode();
/*     */     }
/* 478 */     if (getLabelID() != null) {
/* 479 */       _hashCode += getLabelID().hashCode();
/*     */     }
/* 481 */     if (getDivisionID() != null) {
/* 482 */       _hashCode += getDivisionID().hashCode();
/*     */     }
/* 484 */     if (getCompanyID() != null) {
/* 485 */       _hashCode += getCompanyID().hashCode();
/*     */     }
/* 487 */     if (getEnvironmentID() != null) {
/* 488 */       _hashCode += getEnvironmentID().hashCode();
/*     */     }
/* 490 */     if (getFamilyID() != null) {
/* 491 */       _hashCode += getFamilyID().hashCode();
/*     */     }
/* 493 */     if (getOperatingCo() != null) {
/* 494 */       _hashCode += getOperatingCo().hashCode();
/*     */     }
/* 496 */     if (getSuperLabel() != null) {
/* 497 */       _hashCode += getSuperLabel().hashCode();
/*     */     }
/* 499 */     if (getSubLabel() != null) {
/* 500 */       _hashCode += getSubLabel().hashCode();
/*     */     }
/* 502 */     if (getImprint() != null) {
/* 503 */       _hashCode += getImprint().hashCode();
/*     */     }
/* 505 */     if (getProjectNumber() != null) {
/* 506 */       _hashCode += getProjectNumber().hashCode();
/*     */     }
/* 508 */     if (getArchieID() != null) {
/* 509 */       _hashCode += getArchieID().hashCode();
/*     */     }
/* 511 */     if (getDescription() != null) {
/* 512 */       _hashCode += getDescription().hashCode();
/*     */     }
/* 514 */     if (getTitle() != null) {
/* 515 */       _hashCode += getTitle().hashCode();
/*     */     }
/* 517 */     if (getIsValid() != null) {
/* 518 */       _hashCode += getIsValid().hashCode();
/*     */     }
/* 520 */     this.__hashCodeCalc = false;
/* 521 */     return _hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 526 */   private static TypeDesc typeDesc = new TypeDesc(ProjectNumberValidResults.class, true);
/*     */   
/*     */   static  {
/* 529 */     typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidResults"));
/* 530 */     elemField = new ElementDesc();
/* 531 */     elemField.setFieldName("firstName");
/* 532 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FirstName"));
/* 533 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 534 */     elemField.setMinOccurs(0);
/* 535 */     elemField.setNillable(true);
/* 536 */     typeDesc.addFieldDesc(elemField);
/* 537 */     elemField = new ElementDesc();
/* 538 */     elemField.setFieldName("lastName");
/* 539 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LastName"));
/* 540 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 541 */     elemField.setMinOccurs(0);
/* 542 */     elemField.setNillable(true);
/* 543 */     typeDesc.addFieldDesc(elemField);
/* 544 */     elemField = new ElementDesc();
/* 545 */     elemField.setFieldName("labelID");
/* 546 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LabelID"));
/* 547 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 548 */     elemField.setMinOccurs(0);
/* 549 */     elemField.setNillable(false);
/* 550 */     typeDesc.addFieldDesc(elemField);
/* 551 */     elemField = new ElementDesc();
/* 552 */     elemField.setFieldName("divisionID");
/* 553 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "DivisionID"));
/* 554 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 555 */     elemField.setMinOccurs(0);
/* 556 */     elemField.setNillable(false);
/* 557 */     typeDesc.addFieldDesc(elemField);
/* 558 */     elemField = new ElementDesc();
/* 559 */     elemField.setFieldName("companyID");
/* 560 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "CompanyID"));
/* 561 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 562 */     elemField.setMinOccurs(0);
/* 563 */     elemField.setNillable(false);
/* 564 */     typeDesc.addFieldDesc(elemField);
/* 565 */     elemField = new ElementDesc();
/* 566 */     elemField.setFieldName("environmentID");
/* 567 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EnvironmentID"));
/* 568 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 569 */     elemField.setMinOccurs(0);
/* 570 */     elemField.setNillable(false);
/* 571 */     typeDesc.addFieldDesc(elemField);
/* 572 */     elemField = new ElementDesc();
/* 573 */     elemField.setFieldName("familyID");
/* 574 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FamilyID"));
/* 575 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 576 */     elemField.setMinOccurs(0);
/* 577 */     elemField.setNillable(false);
/* 578 */     typeDesc.addFieldDesc(elemField);
/* 579 */     elemField = new ElementDesc();
/* 580 */     elemField.setFieldName("operatingCo");
/* 581 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "OperatingCo"));
/* 582 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 583 */     elemField.setMinOccurs(0);
/* 584 */     elemField.setNillable(true);
/* 585 */     typeDesc.addFieldDesc(elemField);
/* 586 */     elemField = new ElementDesc();
/* 587 */     elemField.setFieldName("superLabel");
/* 588 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "SuperLabel"));
/* 589 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 590 */     elemField.setMinOccurs(0);
/* 591 */     elemField.setNillable(true);
/* 592 */     typeDesc.addFieldDesc(elemField);
/* 593 */     elemField = new ElementDesc();
/* 594 */     elemField.setFieldName("subLabel");
/* 595 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "SubLabel"));
/* 596 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 597 */     elemField.setMinOccurs(0);
/* 598 */     elemField.setNillable(true);
/* 599 */     typeDesc.addFieldDesc(elemField);
/* 600 */     elemField = new ElementDesc();
/* 601 */     elemField.setFieldName("imprint");
/* 602 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Imprint"));
/* 603 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 604 */     elemField.setMinOccurs(0);
/* 605 */     elemField.setNillable(true);
/* 606 */     typeDesc.addFieldDesc(elemField);
/* 607 */     elemField = new ElementDesc();
/* 608 */     elemField.setFieldName("projectNumber");
/* 609 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectNumber"));
/* 610 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 611 */     elemField.setMinOccurs(0);
/* 612 */     elemField.setNillable(true);
/* 613 */     typeDesc.addFieldDesc(elemField);
/* 614 */     elemField = new ElementDesc();
/* 615 */     elemField.setFieldName("archieID");
/* 616 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArchieID"));
/* 617 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 618 */     elemField.setMinOccurs(0);
/* 619 */     elemField.setNillable(true);
/* 620 */     typeDesc.addFieldDesc(elemField);
/* 621 */     elemField = new ElementDesc();
/* 622 */     elemField.setFieldName("description");
/* 623 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Description"));
/* 624 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 625 */     elemField.setMinOccurs(0);
/* 626 */     elemField.setNillable(true);
/* 627 */     typeDesc.addFieldDesc(elemField);
/* 628 */     elemField = new ElementDesc();
/* 629 */     elemField.setFieldName("title");
/* 630 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Title"));
/* 631 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 632 */     elemField.setMinOccurs(0);
/* 633 */     elemField.setNillable(true);
/* 634 */     typeDesc.addFieldDesc(elemField);
/* 635 */     elemField = new ElementDesc();
/* 636 */     elemField.setFieldName("isValid");
/* 637 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "IsValid"));
/* 638 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 639 */     elemField.setMinOccurs(0);
/* 640 */     elemField.setNillable(false);
/* 641 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 648 */   public static TypeDesc getTypeDesc() { return typeDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
/* 658 */     return 
/* 659 */       new BeanSerializer(
/* 660 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
/* 670 */     return 
/* 671 */       new BeanDeserializer(
/* 672 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */   
/*     */   public ProjectNumberValidResults() {}
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\ProjectNumberValidResults.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */