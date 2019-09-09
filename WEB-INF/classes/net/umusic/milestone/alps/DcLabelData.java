/*     */ package WEB-INF.classes.net.umusic.milestone.alps;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.umusic.milestone.alps.DcLabelData;
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
/*     */ public class DcLabelData
/*     */   implements Serializable
/*     */ {
/*     */   private String distCoName;
/*     */   private Integer distCoID;
/*     */   private Boolean APNGInd;
/*     */   private Integer archimedesID;
/*     */   private String entityName;
/*     */   private String legacyOperatingCo;
/*     */   private String legacyOperatingUnit;
/*     */   private String legacySuperlabel;
/*     */   private String legacySublabel;
/*     */   private String levelOfActivity;
/*     */   private String productionGroupCode;
/*     */   private String entityType;
/*     */   
/*     */   public DcLabelData(String distCoName, Integer distCoID, Boolean APNGInd, Integer archimedesID, String entityName, String legacyOperatingCo, String legacyOperatingUnit, String legacySuperlabel, String legacySublabel, String levelOfActivity, String productionGroupCode, String entityType) {
/*  51 */     this.distCoName = distCoName;
/*  52 */     this.distCoID = distCoID;
/*  53 */     this.APNGInd = APNGInd;
/*  54 */     this.archimedesID = archimedesID;
/*  55 */     this.entityName = entityName;
/*  56 */     this.legacyOperatingCo = legacyOperatingCo;
/*  57 */     this.legacyOperatingUnit = legacyOperatingUnit;
/*  58 */     this.legacySuperlabel = legacySuperlabel;
/*  59 */     this.legacySublabel = legacySublabel;
/*  60 */     this.levelOfActivity = levelOfActivity;
/*  61 */     this.productionGroupCode = productionGroupCode;
/*  62 */     this.entityType = entityType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public String getDistCoName() { return this.distCoName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public void setDistCoName(String distCoName) { this.distCoName = distCoName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public Integer getDistCoID() { return this.distCoID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public void setDistCoID(Integer distCoID) { this.distCoID = distCoID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public Boolean getAPNGInd() { return this.APNGInd; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void setAPNGInd(Boolean APNGInd) { this.APNGInd = APNGInd; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   public Integer getArchimedesID() { return this.archimedesID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public void setArchimedesID(Integer archimedesID) { this.archimedesID = archimedesID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public String getEntityName() { return this.entityName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public void setEntityName(String entityName) { this.entityName = entityName; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public String getLegacyOperatingCo() { return this.legacyOperatingCo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public void setLegacyOperatingCo(String legacyOperatingCo) { this.legacyOperatingCo = legacyOperatingCo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public String getLegacyOperatingUnit() { return this.legacyOperatingUnit; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public void setLegacyOperatingUnit(String legacyOperatingUnit) { this.legacyOperatingUnit = legacyOperatingUnit; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 212 */   public String getLegacySuperlabel() { return this.legacySuperlabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 222 */   public void setLegacySuperlabel(String legacySuperlabel) { this.legacySuperlabel = legacySuperlabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 232 */   public String getLegacySublabel() { return this.legacySublabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 242 */   public void setLegacySublabel(String legacySublabel) { this.legacySublabel = legacySublabel; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public String getLevelOfActivity() { return this.levelOfActivity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 262 */   public void setLevelOfActivity(String levelOfActivity) { this.levelOfActivity = levelOfActivity; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 272 */   public String getProductionGroupCode() { return this.productionGroupCode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   public void setProductionGroupCode(String productionGroupCode) { this.productionGroupCode = productionGroupCode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   public String getEntityType() { return this.entityType; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 302 */   public void setEntityType(String entityType) { this.entityType = entityType; }
/*     */ 
/*     */   
/* 305 */   private Object __equalsCalc = null;
/*     */   public boolean equals(Object obj) {
/* 307 */     if (!(obj instanceof DcLabelData)) return false; 
/* 308 */     DcLabelData other = (DcLabelData)obj;
/* 309 */     if (obj == null) return false; 
/* 310 */     if (this == obj) return true; 
/* 311 */     if (this.__equalsCalc != null) {
/* 312 */       return (this.__equalsCalc == obj);
/*     */     }
/* 314 */     this.__equalsCalc = obj;
/*     */     
/* 316 */     boolean _equals = 
/* 317 */       (((this.distCoName == null && other.getDistCoName() == null) || (
/* 318 */       this.distCoName != null && 
/* 319 */       this.distCoName.equals(other.getDistCoName()))) && ((
/* 320 */       this.distCoID == null && other.getDistCoID() == null) || (
/* 321 */       this.distCoID != null && 
/* 322 */       this.distCoID.equals(other.getDistCoID()))) && ((
/* 323 */       this.APNGInd == null && other.getAPNGInd() == null) || (
/* 324 */       this.APNGInd != null && 
/* 325 */       this.APNGInd.equals(other.getAPNGInd()))) && ((
/* 326 */       this.archimedesID == null && other.getArchimedesID() == null) || (
/* 327 */       this.archimedesID != null && 
/* 328 */       this.archimedesID.equals(other.getArchimedesID()))) && ((
/* 329 */       this.entityName == null && other.getEntityName() == null) || (
/* 330 */       this.entityName != null && 
/* 331 */       this.entityName.equals(other.getEntityName()))) && ((
/* 332 */       this.legacyOperatingCo == null && other.getLegacyOperatingCo() == null) || (
/* 333 */       this.legacyOperatingCo != null && 
/* 334 */       this.legacyOperatingCo.equals(other.getLegacyOperatingCo()))) && ((
/* 335 */       this.legacyOperatingUnit == null && other.getLegacyOperatingUnit() == null) || (
/* 336 */       this.legacyOperatingUnit != null && 
/* 337 */       this.legacyOperatingUnit.equals(other.getLegacyOperatingUnit()))) && ((
/* 338 */       this.legacySuperlabel == null && other.getLegacySuperlabel() == null) || (
/* 339 */       this.legacySuperlabel != null && 
/* 340 */       this.legacySuperlabel.equals(other.getLegacySuperlabel()))) && ((
/* 341 */       this.legacySublabel == null && other.getLegacySublabel() == null) || (
/* 342 */       this.legacySublabel != null && 
/* 343 */       this.legacySublabel.equals(other.getLegacySublabel()))) && ((
/* 344 */       this.levelOfActivity == null && other.getLevelOfActivity() == null) || (
/* 345 */       this.levelOfActivity != null && 
/* 346 */       this.levelOfActivity.equals(other.getLevelOfActivity()))) && ((
/* 347 */       this.productionGroupCode == null && other.getProductionGroupCode() == null) || (
/* 348 */       this.productionGroupCode != null && 
/* 349 */       this.productionGroupCode.equals(other.getProductionGroupCode()))) && ((
/* 350 */       this.entityType == null && other.getEntityType() == null) || (
/* 351 */       this.entityType != null && 
/* 352 */       this.entityType.equals(other.getEntityType()))));
/* 353 */     this.__equalsCalc = null;
/* 354 */     return _equals;
/*     */   }
/*     */   private boolean __hashCodeCalc = false;
/*     */   
/*     */   public int hashCode() {
/* 359 */     if (this.__hashCodeCalc) {
/* 360 */       return 0;
/*     */     }
/* 362 */     this.__hashCodeCalc = true;
/* 363 */     int _hashCode = 1;
/* 364 */     if (getDistCoName() != null) {
/* 365 */       _hashCode += getDistCoName().hashCode();
/*     */     }
/* 367 */     if (getDistCoID() != null) {
/* 368 */       _hashCode += getDistCoID().hashCode();
/*     */     }
/* 370 */     if (getAPNGInd() != null) {
/* 371 */       _hashCode += getAPNGInd().hashCode();
/*     */     }
/* 373 */     if (getArchimedesID() != null) {
/* 374 */       _hashCode += getArchimedesID().hashCode();
/*     */     }
/* 376 */     if (getEntityName() != null) {
/* 377 */       _hashCode += getEntityName().hashCode();
/*     */     }
/* 379 */     if (getLegacyOperatingCo() != null) {
/* 380 */       _hashCode += getLegacyOperatingCo().hashCode();
/*     */     }
/* 382 */     if (getLegacyOperatingUnit() != null) {
/* 383 */       _hashCode += getLegacyOperatingUnit().hashCode();
/*     */     }
/* 385 */     if (getLegacySuperlabel() != null) {
/* 386 */       _hashCode += getLegacySuperlabel().hashCode();
/*     */     }
/* 388 */     if (getLegacySublabel() != null) {
/* 389 */       _hashCode += getLegacySublabel().hashCode();
/*     */     }
/* 391 */     if (getLevelOfActivity() != null) {
/* 392 */       _hashCode += getLevelOfActivity().hashCode();
/*     */     }
/* 394 */     if (getProductionGroupCode() != null) {
/* 395 */       _hashCode += getProductionGroupCode().hashCode();
/*     */     }
/* 397 */     if (getEntityType() != null) {
/* 398 */       _hashCode += getEntityType().hashCode();
/*     */     }
/* 400 */     this.__hashCodeCalc = false;
/* 401 */     return _hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 406 */   private static TypeDesc typeDesc = new TypeDesc(DcLabelData.class, true);
/*     */   
/*     */   static  {
/* 409 */     typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcLabelData"));
/* 410 */     elemField = new ElementDesc();
/* 411 */     elemField.setFieldName("distCoName");
/* 412 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "DistCoName"));
/* 413 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 414 */     elemField.setMinOccurs(0);
/* 415 */     elemField.setNillable(true);
/* 416 */     typeDesc.addFieldDesc(elemField);
/* 417 */     elemField = new ElementDesc();
/* 418 */     elemField.setFieldName("distCoID");
/* 419 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "DistCoID"));
/* 420 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 421 */     elemField.setMinOccurs(0);
/* 422 */     elemField.setNillable(false);
/* 423 */     typeDesc.addFieldDesc(elemField);
/* 424 */     elemField = new ElementDesc();
/* 425 */     elemField.setFieldName("APNGInd");
/* 426 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "APNGInd"));
/* 427 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 428 */     elemField.setMinOccurs(0);
/* 429 */     elemField.setNillable(false);
/* 430 */     typeDesc.addFieldDesc(elemField);
/* 431 */     elemField = new ElementDesc();
/* 432 */     elemField.setFieldName("archimedesID");
/* 433 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ArchimedesID"));
/* 434 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 435 */     elemField.setMinOccurs(0);
/* 436 */     elemField.setNillable(false);
/* 437 */     typeDesc.addFieldDesc(elemField);
/* 438 */     elemField = new ElementDesc();
/* 439 */     elemField.setFieldName("entityName");
/* 440 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EntityName"));
/* 441 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 442 */     elemField.setMinOccurs(0);
/* 443 */     elemField.setNillable(true);
/* 444 */     typeDesc.addFieldDesc(elemField);
/* 445 */     elemField = new ElementDesc();
/* 446 */     elemField.setFieldName("legacyOperatingCo");
/* 447 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacyOperatingCo"));
/* 448 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 449 */     elemField.setMinOccurs(0);
/* 450 */     elemField.setNillable(true);
/* 451 */     typeDesc.addFieldDesc(elemField);
/* 452 */     elemField = new ElementDesc();
/* 453 */     elemField.setFieldName("legacyOperatingUnit");
/* 454 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacyOperatingUnit"));
/* 455 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 456 */     elemField.setMinOccurs(0);
/* 457 */     elemField.setNillable(true);
/* 458 */     typeDesc.addFieldDesc(elemField);
/* 459 */     elemField = new ElementDesc();
/* 460 */     elemField.setFieldName("legacySuperlabel");
/* 461 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacySuperlabel"));
/* 462 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 463 */     elemField.setMinOccurs(0);
/* 464 */     elemField.setNillable(true);
/* 465 */     typeDesc.addFieldDesc(elemField);
/* 466 */     elemField = new ElementDesc();
/* 467 */     elemField.setFieldName("legacySublabel");
/* 468 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LegacySublabel"));
/* 469 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 470 */     elemField.setMinOccurs(0);
/* 471 */     elemField.setNillable(true);
/* 472 */     typeDesc.addFieldDesc(elemField);
/* 473 */     elemField = new ElementDesc();
/* 474 */     elemField.setFieldName("levelOfActivity");
/* 475 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "LevelOfActivity"));
/* 476 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 477 */     elemField.setMinOccurs(0);
/* 478 */     elemField.setNillable(true);
/* 479 */     typeDesc.addFieldDesc(elemField);
/* 480 */     elemField = new ElementDesc();
/* 481 */     elemField.setFieldName("productionGroupCode");
/* 482 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ProductionGroupCode"));
/* 483 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 484 */     elemField.setMinOccurs(0);
/* 485 */     elemField.setNillable(true);
/* 486 */     typeDesc.addFieldDesc(elemField);
/* 487 */     elemField = new ElementDesc();
/* 488 */     elemField.setFieldName("entityType");
/* 489 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "EntityType"));
/* 490 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 491 */     elemField.setMinOccurs(0);
/* 492 */     elemField.setNillable(true);
/* 493 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 500 */   public static TypeDesc getTypeDesc() { return typeDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
/* 510 */     return 
/* 511 */       new BeanSerializer(
/* 512 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
/* 522 */     return 
/* 523 */       new BeanDeserializer(
/* 524 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */   
/*     */   public DcLabelData() {}
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcLabelData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */