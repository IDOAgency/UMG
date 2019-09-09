/*     */ package WEB-INF.classes.org.tempuri;
/*     */ import java.rmi.RemoteException;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.apache.axis.AxisFault;
/*     */ import org.apache.axis.client.Call;
/*     */ import org.apache.axis.description.OperationDesc;
/*     */ import org.apache.axis.description.ParameterDesc;
/*     */ import org.tempuri.BasicAPStub;
/*     */ 
/*     */ public class BasicAPStub extends Stub implements IArchimedesProject {
/*  11 */   private Vector cachedSerClasses = new Vector();
/*  12 */   private Vector cachedSerQNames = new Vector();
/*  13 */   private Vector cachedSerFactories = new Vector();
/*  14 */   private Vector cachedDeserFactories = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  19 */   static OperationDesc[] _operations = new OperationDesc[12]; static  {
/*  20 */     _initOperationDesc1();
/*  21 */     _initOperationDesc2();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void _initOperationDesc1() {
/*  27 */     oper = new OperationDesc();
/*  28 */     oper.setName("ProjectSearchGetDataContract");
/*  29 */     ParameterDesc param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "searchParam"), (byte)1, new QName("http://alps.milestone.umusic.net/", "dcProjectSearchFilter"), DcProjectSearchFilter.class, false, false);
/*  30 */     param.setOmittable(true);
/*  31 */     param.setNillable(true);
/*  32 */     oper.addParameter(param);
/*  33 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcProjectSearchResults"));
/*  34 */     oper.setReturnClass(DcProjectSearchResults[].class);
/*  35 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchGetDataContractResult"));
/*  36 */     param = oper.getReturnParamDesc();
/*  37 */     param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults"));
/*  38 */     oper.setStyle(Style.WRAPPED);
/*  39 */     oper.setUse(Use.LITERAL);
/*  40 */     _operations[0] = oper;
/*     */     
/*  42 */     oper = new OperationDesc();
/*  43 */     oper.setName("ProjectSearchUMVDGetDataContract");
/*  44 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "searchParam"), (byte)1, new QName("http://alps.milestone.umusic.net/", "dcProjectSearchFilter"), DcProjectSearchFilter.class, false, false);
/*  45 */     param.setOmittable(true);
/*  46 */     param.setNillable(true);
/*  47 */     oper.addParameter(param);
/*  48 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcProjectSearchResults"));
/*  49 */     oper.setReturnClass(DcProjectSearchResults[].class);
/*  50 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchUMVDGetDataContractResult"));
/*  51 */     param = oper.getReturnParamDesc();
/*  52 */     param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults"));
/*  53 */     oper.setStyle(Style.WRAPPED);
/*  54 */     oper.setUse(Use.LITERAL);
/*  55 */     _operations[1] = oper;
/*     */     
/*  57 */     oper = new OperationDesc();
/*  58 */     oper.setName("ProjectSearchMilestoneGetDataContract");
/*  59 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "searchParam"), (byte)1, new QName("http://alps.milestone.umusic.net/", "dcMilestoneProjectSearchFilter"), DcMilestoneProjectSearchFilter.class, false, false);
/*  60 */     param.setOmittable(true);
/*  61 */     param.setNillable(true);
/*  62 */     oper.addParameter(param);
/*  63 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcProjectSearchResults"));
/*  64 */     oper.setReturnClass(DcProjectSearchResults[].class);
/*  65 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchMilestoneGetDataContractResult"));
/*  66 */     param = oper.getReturnParamDesc();
/*  67 */     param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults"));
/*  68 */     oper.setStyle(Style.WRAPPED);
/*  69 */     oper.setUse(Use.LITERAL);
/*  70 */     _operations[2] = oper;
/*     */     
/*  72 */     oper = new OperationDesc();
/*  73 */     oper.setName("ProjectNumberValidationGet");
/*  74 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "projectNumber"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  75 */     param.setOmittable(true);
/*  76 */     param.setNillable(true);
/*  77 */     oper.addParameter(param);
/*  78 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/*  79 */     oper.setReturnClass(Boolean.class);
/*  80 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidationGetResult"));
/*  81 */     oper.setStyle(Style.WRAPPED);
/*  82 */     oper.setUse(Use.LITERAL);
/*  83 */     _operations[3] = oper;
/*     */     
/*  85 */     oper = new OperationDesc();
/*  86 */     oper.setName("LabelDataGetDataContractById");
/*  87 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "labelID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
/*  88 */     param.setOmittable(true);
/*  89 */     oper.addParameter(param);
/*  90 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "dcLabelData"));
/*  91 */     oper.setReturnClass(DcLabelData.class);
/*  92 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "LabelDataGetDataContractByIdResult"));
/*  93 */     oper.setStyle(Style.WRAPPED);
/*  94 */     oper.setUse(Use.LITERAL);
/*  95 */     _operations[4] = oper;
/*     */     
/*  97 */     oper = new OperationDesc();
/*  98 */     oper.setName("LabelDataGetDataContract");
/*  99 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcLabelData"));
/* 100 */     oper.setReturnClass(DcLabelData[].class);
/* 101 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "LabelDataGetDataContractResult"));
/* 102 */     param = oper.getReturnParamDesc();
/* 103 */     param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcLabelData"));
/* 104 */     oper.setStyle(Style.WRAPPED);
/* 105 */     oper.setUse(Use.LITERAL);
/* 106 */     _operations[5] = oper;
/*     */     
/* 108 */     oper = new OperationDesc();
/* 109 */     oper.setName("ProjectGetByID");
/* 110 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "ArchieID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
/* 111 */     param.setOmittable(true);
/* 112 */     oper.addParameter(param);
/* 113 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "dsProjectStructureInterface"));
/* 114 */     oper.setReturnClass(DsProjectStructureInterface.class);
/* 115 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectGetByIDResult"));
/* 116 */     oper.setStyle(Style.WRAPPED);
/* 117 */     oper.setUse(Use.LITERAL);
/* 118 */     _operations[6] = oper;
/*     */     
/* 120 */     oper = new OperationDesc();
/* 121 */     oper.setName("LabelGetByID");
/* 122 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "archieID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
/* 123 */     param.setOmittable(true);
/* 124 */     oper.addParameter(param);
/* 125 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "dsLabelStructureInterface"));
/* 126 */     oper.setReturnClass(DsLabelStructureInterface.class);
/* 127 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "LabelGetByIDResult"));
/* 128 */     oper.setStyle(Style.WRAPPED);
/* 129 */     oper.setUse(Use.LITERAL);
/* 130 */     _operations[7] = oper;
/*     */     
/* 132 */     oper = new OperationDesc();
/* 133 */     oper.setName("ProjectSearchValidation");
/* 134 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "projectNumber"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/* 135 */     param.setOmittable(true);
/* 136 */     param.setNillable(true);
/* 137 */     oper.addParameter(param);
/* 138 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "IsUMVD"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "boolean"), Boolean.class, false, false);
/* 139 */     param.setOmittable(true);
/* 140 */     oper.addParameter(param);
/* 141 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidResults"));
/* 142 */     oper.setReturnClass(ProjectNumberValidResults.class);
/* 143 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchValidationResult"));
/* 144 */     oper.setStyle(Style.WRAPPED);
/* 145 */     oper.setUse(Use.LITERAL);
/* 146 */     _operations[8] = oper;
/*     */     
/* 148 */     oper = new OperationDesc();
/* 149 */     oper.setName("DistributionCoGetDataContract");
/* 150 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "ArrayOfdcDistributionCoData"));
/* 151 */     oper.setReturnClass(DcDistributionCoData[].class);
/* 152 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "DistributionCoGetDataContractResult"));
/* 153 */     param = oper.getReturnParamDesc();
/* 154 */     param.setItemQName(new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData"));
/* 155 */     oper.setStyle(Style.WRAPPED);
/* 156 */     oper.setUse(Use.LITERAL);
/* 157 */     _operations[9] = oper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void _initOperationDesc2() {
/* 164 */     oper = new OperationDesc();
/* 165 */     oper.setName("MilestoneSnapshotProjectInsert");
/* 166 */     ParameterDesc param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "projectID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/* 167 */     param.setOmittable(true);
/* 168 */     param.setNillable(true);
/* 169 */     oper.addParameter(param);
/* 170 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/* 171 */     oper.setReturnClass(Boolean.class);
/* 172 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "MilestoneSnapshotProjectInsertResult"));
/* 173 */     oper.setStyle(Style.WRAPPED);
/* 174 */     oper.setUse(Use.LITERAL);
/* 175 */     _operations[10] = oper;
/*     */     
/* 177 */     oper = new OperationDesc();
/* 178 */     oper.setName("GDRSProductStatusGet");
/* 179 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "releaseID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
/* 180 */     param.setOmittable(true);
/* 181 */     oper.addParameter(param);
/* 182 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "releaseFamilyID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
/* 183 */     param.setOmittable(true);
/* 184 */     oper.addParameter(param);
/* 185 */     param = new ParameterDesc(new QName("http://alps.milestone.umusic.net/", "environmentID"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "int"), Integer.class, false, false);
/* 186 */     param.setOmittable(true);
/* 187 */     oper.addParameter(param);
/* 188 */     oper.setReturnType(new QName("http://alps.milestone.umusic.net/", "dcGDRSResults"));
/* 189 */     oper.setReturnClass(DcGDRSResults.class);
/* 190 */     oper.setReturnQName(new QName("http://alps.milestone.umusic.net/", "GDRSProductStatusGetResult"));
/* 191 */     oper.setStyle(Style.WRAPPED);
/* 192 */     oper.setUse(Use.LITERAL);
/* 193 */     _operations[11] = oper;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 198 */   public BasicAPStub() { this(null); }
/*     */ 
/*     */   
/*     */   public BasicAPStub(URL endpointURL, Service service) throws AxisFault {
/* 202 */     this(service);
/* 203 */     this.cachedEndpoint = endpointURL;
/*     */   }
/*     */   
/*     */   public BasicAPStub(Service service) throws AxisFault {
/* 207 */     if (service == null) {
/* 208 */       this.service = new Service();
/*     */     } else {
/* 210 */       this.service = service;
/*     */     } 
/* 212 */     ((Service)this.service).setTypeMappingVersion("1.2");
/*     */ 
/*     */ 
/*     */     
/* 216 */     Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
/* 217 */     Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
/* 218 */     Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
/* 219 */     Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
/* 220 */     Class arraysf = ArraySerializerFactory.class;
/* 221 */     Class arraydf = ArrayDeserializerFactory.class;
/* 222 */     Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
/* 223 */     Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
/* 224 */     Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
/* 225 */     Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
/* 226 */     QName qName = new QName("http://alps.milestone.umusic.net/", "ArrayOfdcDistributionCoData");
/* 227 */     this.cachedSerQNames.add(qName);
/* 228 */     Class cls = DcDistributionCoData[].class;
/* 229 */     this.cachedSerClasses.add(cls);
/* 230 */     qName = new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData");
/* 231 */     QName qName2 = new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData");
/* 232 */     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
/* 233 */     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
/*     */     
/* 235 */     qName = new QName("http://alps.milestone.umusic.net/", "ArrayOfdcLabelData");
/* 236 */     this.cachedSerQNames.add(qName);
/* 237 */     cls = DcLabelData[].class;
/* 238 */     this.cachedSerClasses.add(cls);
/* 239 */     qName = new QName("http://alps.milestone.umusic.net/", "dcLabelData");
/* 240 */     qName2 = new QName("http://alps.milestone.umusic.net/", "dcLabelData");
/* 241 */     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
/* 242 */     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
/*     */     
/* 244 */     qName = new QName("http://alps.milestone.umusic.net/", "ArrayOfdcProjectSearchResults");
/* 245 */     this.cachedSerQNames.add(qName);
/* 246 */     cls = DcProjectSearchResults[].class;
/* 247 */     this.cachedSerClasses.add(cls);
/* 248 */     qName = new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults");
/* 249 */     qName2 = new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults");
/* 250 */     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
/* 251 */     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
/*     */     
/* 253 */     qName = new QName("http://alps.milestone.umusic.net/", "dcDistributionCoData");
/* 254 */     this.cachedSerQNames.add(qName);
/* 255 */     cls = DcDistributionCoData.class;
/* 256 */     this.cachedSerClasses.add(cls);
/* 257 */     this.cachedSerFactories.add(beansf);
/* 258 */     this.cachedDeserFactories.add(beandf);
/*     */     
/* 260 */     qName = new QName("http://alps.milestone.umusic.net/", "dcGDRSResults");
/* 261 */     this.cachedSerQNames.add(qName);
/* 262 */     cls = DcGDRSResults.class;
/* 263 */     this.cachedSerClasses.add(cls);
/* 264 */     this.cachedSerFactories.add(beansf);
/* 265 */     this.cachedDeserFactories.add(beandf);
/*     */     
/* 267 */     qName = new QName("http://alps.milestone.umusic.net/", "dcLabelData");
/* 268 */     this.cachedSerQNames.add(qName);
/* 269 */     cls = DcLabelData.class;
/* 270 */     this.cachedSerClasses.add(cls);
/* 271 */     this.cachedSerFactories.add(beansf);
/* 272 */     this.cachedDeserFactories.add(beandf);
/*     */     
/* 274 */     qName = new QName("http://alps.milestone.umusic.net/", "dcMilestoneProjectSearchFilter");
/* 275 */     this.cachedSerQNames.add(qName);
/* 276 */     cls = DcMilestoneProjectSearchFilter.class;
/* 277 */     this.cachedSerClasses.add(cls);
/* 278 */     this.cachedSerFactories.add(beansf);
/* 279 */     this.cachedDeserFactories.add(beandf);
/*     */     
/* 281 */     qName = new QName("http://alps.milestone.umusic.net/", "dcProjectSearchFilter");
/* 282 */     this.cachedSerQNames.add(qName);
/* 283 */     cls = DcProjectSearchFilter.class;
/* 284 */     this.cachedSerClasses.add(cls);
/* 285 */     this.cachedSerFactories.add(beansf);
/* 286 */     this.cachedDeserFactories.add(beandf);
/*     */     
/* 288 */     qName = new QName("http://alps.milestone.umusic.net/", "dcProjectSearchResults");
/* 289 */     this.cachedSerQNames.add(qName);
/* 290 */     cls = DcProjectSearchResults.class;
/* 291 */     this.cachedSerClasses.add(cls);
/* 292 */     this.cachedSerFactories.add(beansf);
/* 293 */     this.cachedDeserFactories.add(beandf);
/*     */     
/* 295 */     qName = new QName("http://alps.milestone.umusic.net/", "dsLabelStructureInterface");
/* 296 */     this.cachedSerQNames.add(qName);
/* 297 */     cls = DsLabelStructureInterface.class;
/* 298 */     this.cachedSerClasses.add(cls);
/* 299 */     this.cachedSerFactories.add(beansf);
/* 300 */     this.cachedDeserFactories.add(beandf);
/*     */     
/* 302 */     qName = new QName("http://alps.milestone.umusic.net/", "dsProjectStructureInterface");
/* 303 */     this.cachedSerQNames.add(qName);
/* 304 */     cls = DsProjectStructureInterface.class;
/* 305 */     this.cachedSerClasses.add(cls);
/* 306 */     this.cachedSerFactories.add(beansf);
/* 307 */     this.cachedDeserFactories.add(beandf);
/*     */     
/* 309 */     qName = new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidResults");
/* 310 */     this.cachedSerQNames.add(qName);
/* 311 */     cls = ProjectNumberValidResults.class;
/* 312 */     this.cachedSerClasses.add(cls);
/* 313 */     this.cachedSerFactories.add(beansf);
/* 314 */     this.cachedDeserFactories.add(beandf);
/*     */     
/* 316 */     qName = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfint");
/* 317 */     this.cachedSerQNames.add(qName);
/* 318 */     cls = int[].class;
/* 319 */     this.cachedSerClasses.add(cls);
/* 320 */     qName = new QName("http://www.w3.org/2001/XMLSchema", "int");
/* 321 */     qName2 = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "int");
/* 322 */     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
/* 323 */     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Call createCall() throws RemoteException {
/*     */     try {
/* 329 */       Call _call = _createCall();
/* 330 */       if (this.maintainSessionSet) {
/* 331 */         _call.setMaintainSession(this.maintainSession);
/*     */       }
/* 333 */       if (this.cachedUsername != null) {
/* 334 */         _call.setUsername(this.cachedUsername);
/*     */       }
/* 336 */       if (this.cachedPassword != null) {
/* 337 */         _call.setPassword(this.cachedPassword);
/*     */       }
/* 339 */       if (this.cachedEndpoint != null) {
/* 340 */         _call.setTargetEndpointAddress(this.cachedEndpoint);
/*     */       }
/* 342 */       if (this.cachedTimeout != null) {
/* 343 */         _call.setTimeout(this.cachedTimeout);
/*     */       }
/* 345 */       if (this.cachedPortName != null) {
/* 346 */         _call.setPortName(this.cachedPortName);
/*     */       }
/* 348 */       Enumeration keys = this.cachedProperties.keys();
/* 349 */       while (keys.hasMoreElements()) {
/* 350 */         String key = (String)keys.nextElement();
/* 351 */         _call.setProperty(key, this.cachedProperties.get(key));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 358 */       synchronized (this) {
/* 359 */         if (firstCall()) {
/*     */           
/* 361 */           _call.setEncodingStyle(null);
/* 362 */           for (int i = 0; i < this.cachedSerFactories.size(); i++) {
/* 363 */             Class cls = (Class)this.cachedSerClasses.get(i);
/* 364 */             QName qName = 
/* 365 */               (QName)this.cachedSerQNames.get(i);
/* 366 */             Object x = this.cachedSerFactories.get(i);
/* 367 */             if (x instanceof Class) {
/* 368 */               Class sf = 
/* 369 */                 (Class)this.cachedSerFactories.get(i);
/* 370 */               Class df = 
/* 371 */                 (Class)this.cachedDeserFactories.get(i);
/* 372 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*     */             }
/* 374 */             else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
/* 375 */               SerializerFactory sf = 
/* 376 */                 (SerializerFactory)this.cachedSerFactories.get(i);
/* 377 */               DeserializerFactory df = 
/* 378 */                 (DeserializerFactory)this.cachedDeserFactories.get(i);
/* 379 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 384 */       return _call;
/*     */     }
/* 386 */     catch (Throwable _t) {
/* 387 */       throw new AxisFault("Failure trying to get the Call object", _t);
/*     */     } 
/*     */   }
/*     */   
/*     */   public DcProjectSearchResults[] projectSearchGetDataContract(DcProjectSearchFilter searchParam) throws RemoteException {
/* 392 */     if (this.cachedEndpoint == null) {
/* 393 */       throw new NoEndPointException();
/*     */     }
/* 395 */     Call _call = createCall();
/* 396 */     _call.setOperation(_operations[0]);
/* 397 */     _call.setUseSOAPAction(true);
/* 398 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectSearchGetDataContract");
/* 399 */     _call.setEncodingStyle(null);
/* 400 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 401 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 402 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 403 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchGetDataContract"));
/*     */     
/* 405 */     setRequestHeaders(_call);
/* 406 */     setAttachments(_call); try {
/* 407 */       Object _resp = _call.invoke(new Object[] { searchParam });
/*     */       
/* 409 */       if (_resp instanceof RemoteException) {
/* 410 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 413 */       extractAttachments(_call);
/*     */       try {
/* 415 */         return (DcProjectSearchResults[])_resp;
/* 416 */       } catch (Exception _exception) {
/* 417 */         return (DcProjectSearchResults[])JavaUtils.convert(_resp, DcProjectSearchResults[].class);
/*     */       }
/*     */     
/* 420 */     } catch (AxisFault axisFaultException) {
/* 421 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DcProjectSearchResults[] projectSearchUMVDGetDataContract(DcProjectSearchFilter searchParam) throws RemoteException {
/* 426 */     if (this.cachedEndpoint == null) {
/* 427 */       throw new NoEndPointException();
/*     */     }
/* 429 */     Call _call = createCall();
/* 430 */     _call.setOperation(_operations[1]);
/* 431 */     _call.setUseSOAPAction(true);
/* 432 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectSearchUMVDGetDataContract");
/* 433 */     _call.setEncodingStyle(null);
/* 434 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 435 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 436 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 437 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchUMVDGetDataContract"));
/*     */     
/* 439 */     setRequestHeaders(_call);
/* 440 */     setAttachments(_call); try {
/* 441 */       Object _resp = _call.invoke(new Object[] { searchParam });
/*     */       
/* 443 */       if (_resp instanceof RemoteException) {
/* 444 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 447 */       extractAttachments(_call);
/*     */       try {
/* 449 */         return (DcProjectSearchResults[])_resp;
/* 450 */       } catch (Exception _exception) {
/* 451 */         return (DcProjectSearchResults[])JavaUtils.convert(_resp, DcProjectSearchResults[].class);
/*     */       }
/*     */     
/* 454 */     } catch (AxisFault axisFaultException) {
/* 455 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DcProjectSearchResults[] projectSearchMilestoneGetDataContract(DcMilestoneProjectSearchFilter searchParam) throws RemoteException {
/* 460 */     if (this.cachedEndpoint == null) {
/* 461 */       throw new NoEndPointException();
/*     */     }
/* 463 */     Call _call = createCall();
/* 464 */     _call.setOperation(_operations[2]);
/* 465 */     _call.setUseSOAPAction(true);
/* 466 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectSearchMilestoneGetDataContract");
/* 467 */     _call.setEncodingStyle(null);
/* 468 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 469 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 470 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 471 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchMilestoneGetDataContract"));
/*     */     
/* 473 */     setRequestHeaders(_call);
/* 474 */     setAttachments(_call); try {
/* 475 */       Object _resp = _call.invoke(new Object[] { searchParam });
/*     */       
/* 477 */       if (_resp instanceof RemoteException) {
/* 478 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 481 */       extractAttachments(_call);
/*     */       try {
/* 483 */         return (DcProjectSearchResults[])_resp;
/* 484 */       } catch (Exception _exception) {
/* 485 */         return (DcProjectSearchResults[])JavaUtils.convert(_resp, DcProjectSearchResults[].class);
/*     */       }
/*     */     
/* 488 */     } catch (AxisFault axisFaultException) {
/* 489 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Boolean projectNumberValidationGet(String projectNumber) throws RemoteException {
/* 494 */     if (this.cachedEndpoint == null) {
/* 495 */       throw new NoEndPointException();
/*     */     }
/* 497 */     Call _call = createCall();
/* 498 */     _call.setOperation(_operations[3]);
/* 499 */     _call.setUseSOAPAction(true);
/* 500 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectNumberValidationGet");
/* 501 */     _call.setEncodingStyle(null);
/* 502 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 503 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 504 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 505 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectNumberValidationGet"));
/*     */     
/* 507 */     setRequestHeaders(_call);
/* 508 */     setAttachments(_call); try {
/* 509 */       Object _resp = _call.invoke(new Object[] { projectNumber });
/*     */       
/* 511 */       if (_resp instanceof RemoteException) {
/* 512 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 515 */       extractAttachments(_call);
/*     */       try {
/* 517 */         return (Boolean)_resp;
/* 518 */       } catch (Exception _exception) {
/* 519 */         return (Boolean)JavaUtils.convert(_resp, Boolean.class);
/*     */       }
/*     */     
/* 522 */     } catch (AxisFault axisFaultException) {
/* 523 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DcLabelData labelDataGetDataContractById(Integer labelID) throws RemoteException {
/* 528 */     if (this.cachedEndpoint == null) {
/* 529 */       throw new NoEndPointException();
/*     */     }
/* 531 */     Call _call = createCall();
/* 532 */     _call.setOperation(_operations[4]);
/* 533 */     _call.setUseSOAPAction(true);
/* 534 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/LabelDataGetDataContractById");
/* 535 */     _call.setEncodingStyle(null);
/* 536 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 537 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 538 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 539 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "LabelDataGetDataContractById"));
/*     */     
/* 541 */     setRequestHeaders(_call);
/* 542 */     setAttachments(_call); try {
/* 543 */       Object _resp = _call.invoke(new Object[] { labelID });
/*     */       
/* 545 */       if (_resp instanceof RemoteException) {
/* 546 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 549 */       extractAttachments(_call);
/*     */       try {
/* 551 */         return (DcLabelData)_resp;
/* 552 */       } catch (Exception _exception) {
/* 553 */         return (DcLabelData)JavaUtils.convert(_resp, DcLabelData.class);
/*     */       }
/*     */     
/* 556 */     } catch (AxisFault axisFaultException) {
/* 557 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DcLabelData[] labelDataGetDataContract() throws RemoteException {
/* 562 */     if (this.cachedEndpoint == null) {
/* 563 */       throw new NoEndPointException();
/*     */     }
/* 565 */     Call _call = createCall();
/* 566 */     _call.setOperation(_operations[5]);
/* 567 */     _call.setUseSOAPAction(true);
/* 568 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/LabelDataGetDataContract");
/* 569 */     _call.setEncodingStyle(null);
/* 570 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 571 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 572 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 573 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "LabelDataGetDataContract"));
/*     */     
/* 575 */     setRequestHeaders(_call);
/* 576 */     setAttachments(_call); try {
/* 577 */       Object _resp = _call.invoke(new Object[0]);
/*     */       
/* 579 */       if (_resp instanceof RemoteException) {
/* 580 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 583 */       extractAttachments(_call);
/*     */       try {
/* 585 */         return (DcLabelData[])_resp;
/* 586 */       } catch (Exception _exception) {
/* 587 */         return (DcLabelData[])JavaUtils.convert(_resp, DcLabelData[].class);
/*     */       }
/*     */     
/* 590 */     } catch (AxisFault axisFaultException) {
/* 591 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DsProjectStructureInterface projectGetByID(Integer archieID) throws RemoteException {
/* 596 */     if (this.cachedEndpoint == null) {
/* 597 */       throw new NoEndPointException();
/*     */     }
/* 599 */     Call _call = createCall();
/* 600 */     _call.setOperation(_operations[6]);
/* 601 */     _call.setUseSOAPAction(true);
/* 602 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectGetByID");
/* 603 */     _call.setEncodingStyle(null);
/* 604 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 605 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 606 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 607 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectGetByID"));
/*     */     
/* 609 */     setRequestHeaders(_call);
/* 610 */     setAttachments(_call); try {
/* 611 */       Object _resp = _call.invoke(new Object[] { archieID });
/*     */       
/* 613 */       if (_resp instanceof RemoteException) {
/* 614 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 617 */       extractAttachments(_call);
/*     */       try {
/* 619 */         return (DsProjectStructureInterface)_resp;
/* 620 */       } catch (Exception _exception) {
/* 621 */         return (DsProjectStructureInterface)JavaUtils.convert(_resp, DsProjectStructureInterface.class);
/*     */       }
/*     */     
/* 624 */     } catch (AxisFault axisFaultException) {
/* 625 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DsLabelStructureInterface labelGetByID(Integer archieID) throws RemoteException {
/* 630 */     if (this.cachedEndpoint == null) {
/* 631 */       throw new NoEndPointException();
/*     */     }
/* 633 */     Call _call = createCall();
/* 634 */     _call.setOperation(_operations[7]);
/* 635 */     _call.setUseSOAPAction(true);
/* 636 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/LabelGetByID");
/* 637 */     _call.setEncodingStyle(null);
/* 638 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 639 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 640 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 641 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "LabelGetByID"));
/*     */     
/* 643 */     setRequestHeaders(_call);
/* 644 */     setAttachments(_call); try {
/* 645 */       Object _resp = _call.invoke(new Object[] { archieID });
/*     */       
/* 647 */       if (_resp instanceof RemoteException) {
/* 648 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 651 */       extractAttachments(_call);
/*     */       try {
/* 653 */         return (DsLabelStructureInterface)_resp;
/* 654 */       } catch (Exception _exception) {
/* 655 */         return (DsLabelStructureInterface)JavaUtils.convert(_resp, DsLabelStructureInterface.class);
/*     */       }
/*     */     
/* 658 */     } catch (AxisFault axisFaultException) {
/* 659 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ProjectNumberValidResults projectSearchValidation(String projectNumber, Boolean isUMVD) throws RemoteException {
/* 664 */     if (this.cachedEndpoint == null) {
/* 665 */       throw new NoEndPointException();
/*     */     }
/* 667 */     Call _call = createCall();
/* 668 */     _call.setOperation(_operations[8]);
/* 669 */     _call.setUseSOAPAction(true);
/* 670 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/ProjectSearchValidation");
/* 671 */     _call.setEncodingStyle(null);
/* 672 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 673 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 674 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 675 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "ProjectSearchValidation"));
/*     */     
/* 677 */     setRequestHeaders(_call);
/* 678 */     setAttachments(_call); try {
/* 679 */       Object _resp = _call.invoke(new Object[] { projectNumber, isUMVD });
/*     */       
/* 681 */       if (_resp instanceof RemoteException) {
/* 682 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 685 */       extractAttachments(_call);
/*     */       try {
/* 687 */         return (ProjectNumberValidResults)_resp;
/* 688 */       } catch (Exception _exception) {
/* 689 */         return (ProjectNumberValidResults)JavaUtils.convert(_resp, ProjectNumberValidResults.class);
/*     */       }
/*     */     
/* 692 */     } catch (AxisFault axisFaultException) {
/* 693 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DcDistributionCoData[] distributionCoGetDataContract() throws RemoteException {
/* 698 */     if (this.cachedEndpoint == null) {
/* 699 */       throw new NoEndPointException();
/*     */     }
/* 701 */     Call _call = createCall();
/* 702 */     _call.setOperation(_operations[9]);
/* 703 */     _call.setUseSOAPAction(true);
/* 704 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/DistributionCoGetDataContract");
/* 705 */     _call.setEncodingStyle(null);
/* 706 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 707 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 708 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 709 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "DistributionCoGetDataContract"));
/*     */     
/* 711 */     setRequestHeaders(_call);
/* 712 */     setAttachments(_call); try {
/* 713 */       Object _resp = _call.invoke(new Object[0]);
/*     */       
/* 715 */       if (_resp instanceof RemoteException) {
/* 716 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 719 */       extractAttachments(_call);
/*     */       try {
/* 721 */         return (DcDistributionCoData[])_resp;
/* 722 */       } catch (Exception _exception) {
/* 723 */         return (DcDistributionCoData[])JavaUtils.convert(_resp, DcDistributionCoData[].class);
/*     */       }
/*     */     
/* 726 */     } catch (AxisFault axisFaultException) {
/* 727 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Boolean milestoneSnapshotProjectInsert(String projectID) throws RemoteException {
/* 732 */     if (this.cachedEndpoint == null) {
/* 733 */       throw new NoEndPointException();
/*     */     }
/* 735 */     Call _call = createCall();
/* 736 */     _call.setOperation(_operations[10]);
/* 737 */     _call.setUseSOAPAction(true);
/* 738 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/MilestoneSnapshotProjectInsert");
/* 739 */     _call.setEncodingStyle(null);
/* 740 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 741 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 742 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 743 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "MilestoneSnapshotProjectInsert"));
/*     */     
/* 745 */     setRequestHeaders(_call);
/* 746 */     setAttachments(_call); try {
/* 747 */       Object _resp = _call.invoke(new Object[] { projectID });
/*     */       
/* 749 */       if (_resp instanceof RemoteException) {
/* 750 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 753 */       extractAttachments(_call);
/*     */       try {
/* 755 */         return (Boolean)_resp;
/* 756 */       } catch (Exception _exception) {
/* 757 */         return (Boolean)JavaUtils.convert(_resp, Boolean.class);
/*     */       }
/*     */     
/* 760 */     } catch (AxisFault axisFaultException) {
/* 761 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DcGDRSResults GDRSProductStatusGet(Integer releaseID, Integer releaseFamilyID, Integer environmentID) throws RemoteException {
/* 766 */     if (this.cachedEndpoint == null) {
/* 767 */       throw new NoEndPointException();
/*     */     }
/* 769 */     Call _call = createCall();
/* 770 */     _call.setOperation(_operations[11]);
/* 771 */     _call.setUseSOAPAction(true);
/* 772 */     _call.setSOAPActionURI("http://alps.milestone.umusic.net/IArchimedesProject/GDRSProductStatusGet");
/* 773 */     _call.setEncodingStyle(null);
/* 774 */     _call.setProperty("sendXsiTypes", Boolean.FALSE);
/* 775 */     _call.setProperty("sendMultiRefs", Boolean.FALSE);
/* 776 */     _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
/* 777 */     _call.setOperationName(new QName("http://alps.milestone.umusic.net/", "GDRSProductStatusGet"));
/*     */     
/* 779 */     setRequestHeaders(_call);
/* 780 */     setAttachments(_call); try {
/* 781 */       Object _resp = _call.invoke(new Object[] { releaseID, releaseFamilyID, environmentID });
/*     */       
/* 783 */       if (_resp instanceof RemoteException) {
/* 784 */         throw (RemoteException)_resp;
/*     */       }
/*     */       
/* 787 */       extractAttachments(_call);
/*     */       try {
/* 789 */         return (DcGDRSResults)_resp;
/* 790 */       } catch (Exception _exception) {
/* 791 */         return (DcGDRSResults)JavaUtils.convert(_resp, DcGDRSResults.class);
/*     */       }
/*     */     
/* 794 */     } catch (AxisFault axisFaultException) {
/* 795 */       throw axisFaultException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\org\tempuri\BasicAPStub.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */