/*     */ package WEB-INF.classes.net.umusic.milestone.alps;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Calendar;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.umusic.milestone.alps.DsProjectStructureInterface;
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
/*     */ public class DsProjectStructureInterface
/*     */   implements Serializable
/*     */ {
/*     */   private Integer ARLevelOfActivityID;
/*     */   private Boolean active;
/*     */   private Calendar completedDate;
/*     */   private String firstName;
/*     */   private String imprint;
/*     */   private Calendar initiatedDate;
/*     */   private String JDEProjectNumber;
/*     */   private Integer labelID;
/*     */   private String lastName;
/*     */   private String name;
/*     */   private Integer projectID;
/*     */   private String RMSProjectNumber;
/*     */   private String serviceException;
/*     */   private String title;
/*     */   
/*     */   public DsProjectStructureInterface(Integer ARLevelOfActivityID, Boolean active, Calendar completedDate, String firstName, String imprint, Calendar initiatedDate, String JDEProjectNumber, Integer labelID, String lastName, String name, Integer projectID, String RMSProjectNumber, String serviceException, String title) {
/*  57 */     this.ARLevelOfActivityID = ARLevelOfActivityID;
/*  58 */     this.active = active;
/*  59 */     this.completedDate = completedDate;
/*  60 */     this.firstName = firstName;
/*  61 */     this.imprint = imprint;
/*  62 */     this.initiatedDate = initiatedDate;
/*  63 */     this.JDEProjectNumber = JDEProjectNumber;
/*  64 */     this.labelID = labelID;
/*  65 */     this.lastName = lastName;
/*  66 */     this.name = name;
/*  67 */     this.projectID = projectID;
/*  68 */     this.RMSProjectNumber = RMSProjectNumber;
/*  69 */     this.serviceException = serviceException;
/*  70 */     this.title = title;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public Integer getARLevelOfActivityID() { return this.ARLevelOfActivityID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void setARLevelOfActivityID(Integer ARLevelOfActivityID) { this.ARLevelOfActivityID = ARLevelOfActivityID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public Boolean getActive() { return this.active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public void setActive(Boolean active) { this.active = active; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public Calendar getCompletedDate() { return this.completedDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public void setCompletedDate(Calendar completedDate) { this.completedDate = completedDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   public String getFirstName() { return this.firstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public void setFirstName(String firstName) { this.firstName = firstName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public String getImprint() { return this.imprint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 170 */   public void setImprint(String imprint) { this.imprint = imprint; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   public Calendar getInitiatedDate() { return this.initiatedDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   public void setInitiatedDate(Calendar initiatedDate) { this.initiatedDate = initiatedDate; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public String getJDEProjectNumber() { return this.JDEProjectNumber; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public void setJDEProjectNumber(String JDEProjectNumber) { this.JDEProjectNumber = JDEProjectNumber; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public Integer getLabelID() { return this.labelID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   public void setLabelID(Integer labelID) { this.labelID = labelID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 240 */   public String getLastName() { return this.lastName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public void setLastName(String lastName) { this.lastName = lastName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public void setName(String name) { this.name = name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 280 */   public Integer getProjectID() { return this.projectID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 290 */   public void setProjectID(Integer projectID) { this.projectID = projectID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   public String getRMSProjectNumber() { return this.RMSProjectNumber; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   public void setRMSProjectNumber(String RMSProjectNumber) { this.RMSProjectNumber = RMSProjectNumber; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public String getServiceException() { return this.serviceException; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 330 */   public void setServiceException(String serviceException) { this.serviceException = serviceException; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 340 */   public String getTitle() { return this.title; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 350 */   public void setTitle(String title) { this.title = title; }
/*     */ 
/*     */   
/* 353 */   private Object __equalsCalc = null;
/*     */   public boolean equals(Object obj) {
/* 355 */     if (!(obj instanceof DsProjectStructureInterface)) return false; 
/* 356 */     DsProjectStructureInterface other = (DsProjectStructureInterface)obj;
/* 357 */     if (obj == null) return false; 
/* 358 */     if (this == obj) return true; 
/* 359 */     if (this.__equalsCalc != null) {
/* 360 */       return (this.__equalsCalc == obj);
/*     */     }
/* 362 */     this.__equalsCalc = obj;
/*     */     
/* 364 */     boolean _equals = 
/* 365 */       (((this.ARLevelOfActivityID == null && other.getARLevelOfActivityID() == null) || (
/* 366 */       this.ARLevelOfActivityID != null && 
/* 367 */       this.ARLevelOfActivityID.equals(other.getARLevelOfActivityID()))) && ((
/* 368 */       this.active == null && other.getActive() == null) || (
/* 369 */       this.active != null && 
/* 370 */       this.active.equals(other.getActive()))) && ((
/* 371 */       this.completedDate == null && other.getCompletedDate() == null) || (
/* 372 */       this.completedDate != null && 
/* 373 */       this.completedDate.equals(other.getCompletedDate()))) && ((
/* 374 */       this.firstName == null && other.getFirstName() == null) || (
/* 375 */       this.firstName != null && 
/* 376 */       this.firstName.equals(other.getFirstName()))) && ((
/* 377 */       this.imprint == null && other.getImprint() == null) || (
/* 378 */       this.imprint != null && 
/* 379 */       this.imprint.equals(other.getImprint()))) && ((
/* 380 */       this.initiatedDate == null && other.getInitiatedDate() == null) || (
/* 381 */       this.initiatedDate != null && 
/* 382 */       this.initiatedDate.equals(other.getInitiatedDate()))) && ((
/* 383 */       this.JDEProjectNumber == null && other.getJDEProjectNumber() == null) || (
/* 384 */       this.JDEProjectNumber != null && 
/* 385 */       this.JDEProjectNumber.equals(other.getJDEProjectNumber()))) && ((
/* 386 */       this.labelID == null && other.getLabelID() == null) || (
/* 387 */       this.labelID != null && 
/* 388 */       this.labelID.equals(other.getLabelID()))) && ((
/* 389 */       this.lastName == null && other.getLastName() == null) || (
/* 390 */       this.lastName != null && 
/* 391 */       this.lastName.equals(other.getLastName()))) && ((
/* 392 */       this.name == null && other.getName() == null) || (
/* 393 */       this.name != null && 
/* 394 */       this.name.equals(other.getName()))) && ((
/* 395 */       this.projectID == null && other.getProjectID() == null) || (
/* 396 */       this.projectID != null && 
/* 397 */       this.projectID.equals(other.getProjectID()))) && ((
/* 398 */       this.RMSProjectNumber == null && other.getRMSProjectNumber() == null) || (
/* 399 */       this.RMSProjectNumber != null && 
/* 400 */       this.RMSProjectNumber.equals(other.getRMSProjectNumber()))) && ((
/* 401 */       this.serviceException == null && other.getServiceException() == null) || (
/* 402 */       this.serviceException != null && 
/* 403 */       this.serviceException.equals(other.getServiceException()))) && ((
/* 404 */       this.title == null && other.getTitle() == null) || (
/* 405 */       this.title != null && 
/* 406 */       this.title.equals(other.getTitle()))));
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
/* 418 */     if (getARLevelOfActivityID() != null) {
/* 419 */       _hashCode += getARLevelOfActivityID().hashCode();
/*     */     }
/* 421 */     if (getActive() != null) {
/* 422 */       _hashCode += getActive().hashCode();
/*     */     }
/* 424 */     if (getCompletedDate() != null) {
/* 425 */       _hashCode += getCompletedDate().hashCode();
/*     */     }
/* 427 */     if (getFirstName() != null) {
/* 428 */       _hashCode += getFirstName().hashCode();
/*     */     }
/* 430 */     if (getImprint() != null) {
/* 431 */       _hashCode += getImprint().hashCode();
/*     */     }
/* 433 */     if (getInitiatedDate() != null) {
/* 434 */       _hashCode += getInitiatedDate().hashCode();
/*     */     }
/* 436 */     if (getJDEProjectNumber() != null) {
/* 437 */       _hashCode += getJDEProjectNumber().hashCode();
/*     */     }
/* 439 */     if (getLabelID() != null) {
/* 440 */       _hashCode += getLabelID().hashCode();
/*     */     }
/* 442 */     if (getLastName() != null) {
/* 443 */       _hashCode += getLastName().hashCode();
/*     */     }
/* 445 */     if (getName() != null) {
/* 446 */       _hashCode += getName().hashCode();
/*     */     }
/* 448 */     if (getProjectID() != null) {
/* 449 */       _hashCode += getProjectID().hashCode();
/*     */     }
/* 451 */     if (getRMSProjectNumber() != null) {
/* 452 */       _hashCode += getRMSProjectNumber().hashCode();
/*     */     }
/* 454 */     if (getServiceException() != null) {
/* 455 */       _hashCode += getServiceException().hashCode();
/*     */     }
/* 457 */     if (getTitle() != null) {
/* 458 */       _hashCode += getTitle().hashCode();
/*     */     }
/* 460 */     this.__hashCodeCalc = false;
/* 461 */     return _hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 466 */   private static TypeDesc typeDesc = new TypeDesc(DsProjectStructureInterface.class, true);
/*     */   
/*     */   static  {
/* 469 */     typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dsProjectStructureInterface"));
/* 470 */     elemField = new ElementDesc();
/* 471 */     elemField.setFieldName("ARLevelOfActivityID");
/* 472 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ARLevelOfActivityID"));
/* 473 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 474 */     elemField.setMinOccurs(0);
/* 475 */     elemField.setNillable(true);
/* 476 */     typeDesc.addFieldDesc(elemField);
/* 477 */     elemField = new ElementDesc();
/* 478 */     elemField.setFieldName("active");
/* 479 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Active"));
/* 480 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 481 */     elemField.setMinOccurs(0);
/* 482 */     elemField.setNillable(true);
/* 483 */     typeDesc.addFieldDesc(elemField);
/* 484 */     elemField = new ElementDesc();
/* 485 */     elemField.setFieldName("completedDate");
/* 486 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "CompletedDate"));
/* 487 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
/* 488 */     elemField.setMinOccurs(0);
/* 489 */     elemField.setNillable(true);
/* 490 */     typeDesc.addFieldDesc(elemField);
/* 491 */     elemField = new ElementDesc();
/* 492 */     elemField.setFieldName("firstName");
/* 493 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "FirstName"));
/* 494 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 495 */     elemField.setMinOccurs(0);
/* 496 */     elemField.setNillable(true);
/* 497 */     typeDesc.addFieldDesc(elemField);
/* 498 */     elemField = new ElementDesc();
/* 499 */     elemField.setFieldName("imprint");
/* 500 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Imprint"));
/* 501 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 502 */     elemField.setMinOccurs(0);
/* 503 */     elemField.setNillable(true);
/* 504 */     typeDesc.addFieldDesc(elemField);
/* 505 */     elemField = new ElementDesc();
/* 506 */     elemField.setFieldName("initiatedDate");
/* 507 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "InitiatedDate"));
/* 508 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
/* 509 */     elemField.setMinOccurs(0);
/* 510 */     elemField.setNillable(true);
/* 511 */     typeDesc.addFieldDesc(elemField);
/* 512 */     elemField = new ElementDesc();
/* 513 */     elemField.setFieldName("JDEProjectNumber");
/* 514 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "JDEProjectNumber"));
/* 515 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 516 */     elemField.setMinOccurs(0);
/* 517 */     elemField.setNillable(true);
/* 518 */     typeDesc.addFieldDesc(elemField);
/* 519 */     elemField = new ElementDesc();
/* 520 */     elemField.setFieldName("labelID");
/* 521 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LabelID"));
/* 522 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 523 */     elemField.setMinOccurs(0);
/* 524 */     elemField.setNillable(true);
/* 525 */     typeDesc.addFieldDesc(elemField);
/* 526 */     elemField = new ElementDesc();
/* 527 */     elemField.setFieldName("lastName");
/* 528 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LastName"));
/* 529 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 530 */     elemField.setMinOccurs(0);
/* 531 */     elemField.setNillable(true);
/* 532 */     typeDesc.addFieldDesc(elemField);
/* 533 */     elemField = new ElementDesc();
/* 534 */     elemField.setFieldName("name");
/* 535 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Name"));
/* 536 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 537 */     elemField.setMinOccurs(0);
/* 538 */     elemField.setNillable(true);
/* 539 */     typeDesc.addFieldDesc(elemField);
/* 540 */     elemField = new ElementDesc();
/* 541 */     elemField.setFieldName("projectID");
/* 542 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProjectID"));
/* 543 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 544 */     elemField.setMinOccurs(0);
/* 545 */     elemField.setNillable(false);
/* 546 */     typeDesc.addFieldDesc(elemField);
/* 547 */     elemField = new ElementDesc();
/* 548 */     elemField.setFieldName("RMSProjectNumber");
/* 549 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "RMSProjectNumber"));
/* 550 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 551 */     elemField.setMinOccurs(0);
/* 552 */     elemField.setNillable(true);
/* 553 */     typeDesc.addFieldDesc(elemField);
/* 554 */     elemField = new ElementDesc();
/* 555 */     elemField.setFieldName("serviceException");
/* 556 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ServiceException"));
/* 557 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 558 */     elemField.setMinOccurs(0);
/* 559 */     elemField.setNillable(true);
/* 560 */     typeDesc.addFieldDesc(elemField);
/* 561 */     elemField = new ElementDesc();
/* 562 */     elemField.setFieldName("title");
/* 563 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Title"));
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
/*     */   public DsProjectStructureInterface() {}
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DsProjectStructureInterface.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */