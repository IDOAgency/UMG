/*     */ package WEB-INF.classes.net.umusic.milestone.alps;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.xml.namespace.QName;
/*     */ import net.umusic.milestone.alps.DcDistributionCoData;
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
/*     */ public class DcDistributionCoData
/*     */   implements Serializable
/*     */ {
/*     */   private String name;
/*     */   private Integer ID;
/*     */   private Integer sortOrder;
/*     */   
/*     */   public DcDistributionCoData(String name, Integer ID, Integer sortOrder) {
/*  24 */     this.name = name;
/*  25 */     this.ID = ID;
/*  26 */     this.sortOrder = sortOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   public void setName(String name) { this.name = name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public Integer getID() { return this.ID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public void setID(Integer ID) { this.ID = ID; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public Integer getSortOrder() { return this.sortOrder; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
/*     */ 
/*     */   
/*  89 */   private Object __equalsCalc = null;
/*     */   public boolean equals(Object obj) {
/*  91 */     if (!(obj instanceof DcDistributionCoData)) return false; 
/*  92 */     DcDistributionCoData other = (DcDistributionCoData)obj;
/*  93 */     if (obj == null) return false; 
/*  94 */     if (this == obj) return true; 
/*  95 */     if (this.__equalsCalc != null) {
/*  96 */       return (this.__equalsCalc == obj);
/*     */     }
/*  98 */     this.__equalsCalc = obj;
/*     */     
/* 100 */     boolean _equals = 
/* 101 */       (((this.name == null && other.getName() == null) || (
/* 102 */       this.name != null && 
/* 103 */       this.name.equals(other.getName()))) && ((
/* 104 */       this.ID == null && other.getID() == null) || (
/* 105 */       this.ID != null && 
/* 106 */       this.ID.equals(other.getID()))) && ((
/* 107 */       this.sortOrder == null && other.getSortOrder() == null) || (
/* 108 */       this.sortOrder != null && 
/* 109 */       this.sortOrder.equals(other.getSortOrder()))));
/* 110 */     this.__equalsCalc = null;
/* 111 */     return _equals;
/*     */   }
/*     */   private boolean __hashCodeCalc = false;
/*     */   
/*     */   public int hashCode() {
/* 116 */     if (this.__hashCodeCalc) {
/* 117 */       return 0;
/*     */     }
/* 119 */     this.__hashCodeCalc = true;
/* 120 */     int _hashCode = 1;
/* 121 */     if (getName() != null) {
/* 122 */       _hashCode += getName().hashCode();
/*     */     }
/* 124 */     if (getID() != null) {
/* 125 */       _hashCode += getID().hashCode();
/*     */     }
/* 127 */     if (getSortOrder() != null) {
/* 128 */       _hashCode += getSortOrder().hashCode();
/*     */     }
/* 130 */     this.__hashCodeCalc = false;
/* 131 */     return _hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 136 */   private static TypeDesc typeDesc = new TypeDesc(DcDistributionCoData.class, true);
/*     */   
/*     */   static  {
/* 139 */     typeDesc.setXmlType(new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData"));
/* 140 */     elemField = new ElementDesc();
/* 141 */     elemField.setFieldName("name");
/* 142 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "Name"));
/* 143 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 144 */     elemField.setMinOccurs(0);
/* 145 */     elemField.setNillable(true);
/* 146 */     typeDesc.addFieldDesc(elemField);
/* 147 */     elemField = new ElementDesc();
/* 148 */     elemField.setFieldName("ID");
/* 149 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "ID"));
/* 150 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 151 */     elemField.setMinOccurs(0);
/* 152 */     elemField.setNillable(false);
/* 153 */     typeDesc.addFieldDesc(elemField);
/* 154 */     elemField = new ElementDesc();
/* 155 */     elemField.setFieldName("sortOrder");
/* 156 */     elemField.setXmlName(new QName("http://alps.milestone.umusic.net/", "SortOrder"));
/* 157 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
/* 158 */     elemField.setMinOccurs(0);
/* 159 */     elemField.setNillable(false);
/* 160 */     typeDesc.addFieldDesc(elemField);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public static TypeDesc getTypeDesc() { return typeDesc; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
/* 177 */     return 
/* 178 */       new BeanSerializer(
/* 179 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
/* 189 */     return 
/* 190 */       new BeanDeserializer(
/* 191 */         _javaType, _xmlType, typeDesc);
/*     */   }
/*     */   
/*     */   public DcDistributionCoData() {}
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\ne\\umusic\milestone\alps\DcDistributionCoData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */