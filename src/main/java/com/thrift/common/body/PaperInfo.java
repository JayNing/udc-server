/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-16")
public class PaperInfo implements org.apache.thrift.TBase<PaperInfo, PaperInfo._Fields>, java.io.Serializable, Cloneable, Comparable<PaperInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PaperInfo");

  private static final org.apache.thrift.protocol.TField PAPER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("paperId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PAPER_TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("paperTitle", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CATEGORY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("categoryId", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField CREATE_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("createUserId", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("flag", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField LAST_EDIT_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("lastEditUserId", org.apache.thrift.protocol.TType.I32, (short)7);
  private static final org.apache.thrift.protocol.TField LAST_EDIT_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("lastEditTime", org.apache.thrift.protocol.TType.I64, (short)8);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PaperInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PaperInfoTupleSchemeFactory();

  public int paperId; // required
  public java.lang.String paperTitle; // required
  public int categoryId; // required
  public int createUserId; // required
  public long createTime; // required
  public int flag; // required
  public int lastEditUserId; // required
  public long lastEditTime; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAPER_ID((short)1, "paperId"),
    PAPER_TITLE((short)2, "paperTitle"),
    CATEGORY_ID((short)3, "categoryId"),
    CREATE_USER_ID((short)4, "createUserId"),
    CREATE_TIME((short)5, "createTime"),
    FLAG((short)6, "flag"),
    LAST_EDIT_USER_ID((short)7, "lastEditUserId"),
    LAST_EDIT_TIME((short)8, "lastEditTime");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // PAPER_ID
          return PAPER_ID;
        case 2: // PAPER_TITLE
          return PAPER_TITLE;
        case 3: // CATEGORY_ID
          return CATEGORY_ID;
        case 4: // CREATE_USER_ID
          return CREATE_USER_ID;
        case 5: // CREATE_TIME
          return CREATE_TIME;
        case 6: // FLAG
          return FLAG;
        case 7: // LAST_EDIT_USER_ID
          return LAST_EDIT_USER_ID;
        case 8: // LAST_EDIT_TIME
          return LAST_EDIT_TIME;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __PAPERID_ISSET_ID = 0;
  private static final int __CATEGORYID_ISSET_ID = 1;
  private static final int __CREATEUSERID_ISSET_ID = 2;
  private static final int __CREATETIME_ISSET_ID = 3;
  private static final int __FLAG_ISSET_ID = 4;
  private static final int __LASTEDITUSERID_ISSET_ID = 5;
  private static final int __LASTEDITTIME_ISSET_ID = 6;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAPER_ID, new org.apache.thrift.meta_data.FieldMetaData("paperId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PAPER_TITLE, new org.apache.thrift.meta_data.FieldMetaData("paperTitle", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CATEGORY_ID, new org.apache.thrift.meta_data.FieldMetaData("categoryId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("createUserId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.FLAG, new org.apache.thrift.meta_data.FieldMetaData("flag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LAST_EDIT_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("lastEditUserId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LAST_EDIT_TIME, new org.apache.thrift.meta_data.FieldMetaData("lastEditTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PaperInfo.class, metaDataMap);
  }

  public PaperInfo() {
  }

  public PaperInfo(
    int paperId,
    java.lang.String paperTitle,
    int categoryId,
    int createUserId,
    long createTime,
    int flag,
    int lastEditUserId,
    long lastEditTime)
  {
    this();
    this.paperId = paperId;
    setPaperIdIsSet(true);
    this.paperTitle = paperTitle;
    this.categoryId = categoryId;
    setCategoryIdIsSet(true);
    this.createUserId = createUserId;
    setCreateUserIdIsSet(true);
    this.createTime = createTime;
    setCreateTimeIsSet(true);
    this.flag = flag;
    setFlagIsSet(true);
    this.lastEditUserId = lastEditUserId;
    setLastEditUserIdIsSet(true);
    this.lastEditTime = lastEditTime;
    setLastEditTimeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PaperInfo(PaperInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.paperId = other.paperId;
    if (other.isSetPaperTitle()) {
      this.paperTitle = other.paperTitle;
    }
    this.categoryId = other.categoryId;
    this.createUserId = other.createUserId;
    this.createTime = other.createTime;
    this.flag = other.flag;
    this.lastEditUserId = other.lastEditUserId;
    this.lastEditTime = other.lastEditTime;
  }

  public PaperInfo deepCopy() {
    return new PaperInfo(this);
  }

  @Override
  public void clear() {
    setPaperIdIsSet(false);
    this.paperId = 0;
    this.paperTitle = null;
    setCategoryIdIsSet(false);
    this.categoryId = 0;
    setCreateUserIdIsSet(false);
    this.createUserId = 0;
    setCreateTimeIsSet(false);
    this.createTime = 0;
    setFlagIsSet(false);
    this.flag = 0;
    setLastEditUserIdIsSet(false);
    this.lastEditUserId = 0;
    setLastEditTimeIsSet(false);
    this.lastEditTime = 0;
  }

  public int getPaperId() {
    return this.paperId;
  }

  public PaperInfo setPaperId(int paperId) {
    this.paperId = paperId;
    setPaperIdIsSet(true);
    return this;
  }

  public void unsetPaperId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PAPERID_ISSET_ID);
  }

  /** Returns true if field paperId is set (has been assigned a value) and false otherwise */
  public boolean isSetPaperId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PAPERID_ISSET_ID);
  }

  public void setPaperIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PAPERID_ISSET_ID, value);
  }

  public java.lang.String getPaperTitle() {
    return this.paperTitle;
  }

  public PaperInfo setPaperTitle(java.lang.String paperTitle) {
    this.paperTitle = paperTitle;
    return this;
  }

  public void unsetPaperTitle() {
    this.paperTitle = null;
  }

  /** Returns true if field paperTitle is set (has been assigned a value) and false otherwise */
  public boolean isSetPaperTitle() {
    return this.paperTitle != null;
  }

  public void setPaperTitleIsSet(boolean value) {
    if (!value) {
      this.paperTitle = null;
    }
  }

  public int getCategoryId() {
    return this.categoryId;
  }

  public PaperInfo setCategoryId(int categoryId) {
    this.categoryId = categoryId;
    setCategoryIdIsSet(true);
    return this;
  }

  public void unsetCategoryId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CATEGORYID_ISSET_ID);
  }

  /** Returns true if field categoryId is set (has been assigned a value) and false otherwise */
  public boolean isSetCategoryId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CATEGORYID_ISSET_ID);
  }

  public void setCategoryIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CATEGORYID_ISSET_ID, value);
  }

  public int getCreateUserId() {
    return this.createUserId;
  }

  public PaperInfo setCreateUserId(int createUserId) {
    this.createUserId = createUserId;
    setCreateUserIdIsSet(true);
    return this;
  }

  public void unsetCreateUserId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CREATEUSERID_ISSET_ID);
  }

  /** Returns true if field createUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateUserId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CREATEUSERID_ISSET_ID);
  }

  public void setCreateUserIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CREATEUSERID_ISSET_ID, value);
  }

  public long getCreateTime() {
    return this.createTime;
  }

  public PaperInfo setCreateTime(long createTime) {
    this.createTime = createTime;
    setCreateTimeIsSet(true);
    return this;
  }

  public void unsetCreateTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CREATETIME_ISSET_ID);
  }

  /** Returns true if field createTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CREATETIME_ISSET_ID);
  }

  public void setCreateTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CREATETIME_ISSET_ID, value);
  }

  public int getFlag() {
    return this.flag;
  }

  public PaperInfo setFlag(int flag) {
    this.flag = flag;
    setFlagIsSet(true);
    return this;
  }

  public void unsetFlag() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __FLAG_ISSET_ID);
  }

  /** Returns true if field flag is set (has been assigned a value) and false otherwise */
  public boolean isSetFlag() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __FLAG_ISSET_ID);
  }

  public void setFlagIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __FLAG_ISSET_ID, value);
  }

  public int getLastEditUserId() {
    return this.lastEditUserId;
  }

  public PaperInfo setLastEditUserId(int lastEditUserId) {
    this.lastEditUserId = lastEditUserId;
    setLastEditUserIdIsSet(true);
    return this;
  }

  public void unsetLastEditUserId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __LASTEDITUSERID_ISSET_ID);
  }

  /** Returns true if field lastEditUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetLastEditUserId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __LASTEDITUSERID_ISSET_ID);
  }

  public void setLastEditUserIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __LASTEDITUSERID_ISSET_ID, value);
  }

  public long getLastEditTime() {
    return this.lastEditTime;
  }

  public PaperInfo setLastEditTime(long lastEditTime) {
    this.lastEditTime = lastEditTime;
    setLastEditTimeIsSet(true);
    return this;
  }

  public void unsetLastEditTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __LASTEDITTIME_ISSET_ID);
  }

  /** Returns true if field lastEditTime is set (has been assigned a value) and false otherwise */
  public boolean isSetLastEditTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __LASTEDITTIME_ISSET_ID);
  }

  public void setLastEditTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __LASTEDITTIME_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case PAPER_ID:
      if (value == null) {
        unsetPaperId();
      } else {
        setPaperId((java.lang.Integer)value);
      }
      break;

    case PAPER_TITLE:
      if (value == null) {
        unsetPaperTitle();
      } else {
        setPaperTitle((java.lang.String)value);
      }
      break;

    case CATEGORY_ID:
      if (value == null) {
        unsetCategoryId();
      } else {
        setCategoryId((java.lang.Integer)value);
      }
      break;

    case CREATE_USER_ID:
      if (value == null) {
        unsetCreateUserId();
      } else {
        setCreateUserId((java.lang.Integer)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((java.lang.Long)value);
      }
      break;

    case FLAG:
      if (value == null) {
        unsetFlag();
      } else {
        setFlag((java.lang.Integer)value);
      }
      break;

    case LAST_EDIT_USER_ID:
      if (value == null) {
        unsetLastEditUserId();
      } else {
        setLastEditUserId((java.lang.Integer)value);
      }
      break;

    case LAST_EDIT_TIME:
      if (value == null) {
        unsetLastEditTime();
      } else {
        setLastEditTime((java.lang.Long)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PAPER_ID:
      return getPaperId();

    case PAPER_TITLE:
      return getPaperTitle();

    case CATEGORY_ID:
      return getCategoryId();

    case CREATE_USER_ID:
      return getCreateUserId();

    case CREATE_TIME:
      return getCreateTime();

    case FLAG:
      return getFlag();

    case LAST_EDIT_USER_ID:
      return getLastEditUserId();

    case LAST_EDIT_TIME:
      return getLastEditTime();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PAPER_ID:
      return isSetPaperId();
    case PAPER_TITLE:
      return isSetPaperTitle();
    case CATEGORY_ID:
      return isSetCategoryId();
    case CREATE_USER_ID:
      return isSetCreateUserId();
    case CREATE_TIME:
      return isSetCreateTime();
    case FLAG:
      return isSetFlag();
    case LAST_EDIT_USER_ID:
      return isSetLastEditUserId();
    case LAST_EDIT_TIME:
      return isSetLastEditTime();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PaperInfo)
      return this.equals((PaperInfo)that);
    return false;
  }

  public boolean equals(PaperInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_paperId = true;
    boolean that_present_paperId = true;
    if (this_present_paperId || that_present_paperId) {
      if (!(this_present_paperId && that_present_paperId))
        return false;
      if (this.paperId != that.paperId)
        return false;
    }

    boolean this_present_paperTitle = true && this.isSetPaperTitle();
    boolean that_present_paperTitle = true && that.isSetPaperTitle();
    if (this_present_paperTitle || that_present_paperTitle) {
      if (!(this_present_paperTitle && that_present_paperTitle))
        return false;
      if (!this.paperTitle.equals(that.paperTitle))
        return false;
    }

    boolean this_present_categoryId = true;
    boolean that_present_categoryId = true;
    if (this_present_categoryId || that_present_categoryId) {
      if (!(this_present_categoryId && that_present_categoryId))
        return false;
      if (this.categoryId != that.categoryId)
        return false;
    }

    boolean this_present_createUserId = true;
    boolean that_present_createUserId = true;
    if (this_present_createUserId || that_present_createUserId) {
      if (!(this_present_createUserId && that_present_createUserId))
        return false;
      if (this.createUserId != that.createUserId)
        return false;
    }

    boolean this_present_createTime = true;
    boolean that_present_createTime = true;
    if (this_present_createTime || that_present_createTime) {
      if (!(this_present_createTime && that_present_createTime))
        return false;
      if (this.createTime != that.createTime)
        return false;
    }

    boolean this_present_flag = true;
    boolean that_present_flag = true;
    if (this_present_flag || that_present_flag) {
      if (!(this_present_flag && that_present_flag))
        return false;
      if (this.flag != that.flag)
        return false;
    }

    boolean this_present_lastEditUserId = true;
    boolean that_present_lastEditUserId = true;
    if (this_present_lastEditUserId || that_present_lastEditUserId) {
      if (!(this_present_lastEditUserId && that_present_lastEditUserId))
        return false;
      if (this.lastEditUserId != that.lastEditUserId)
        return false;
    }

    boolean this_present_lastEditTime = true;
    boolean that_present_lastEditTime = true;
    if (this_present_lastEditTime || that_present_lastEditTime) {
      if (!(this_present_lastEditTime && that_present_lastEditTime))
        return false;
      if (this.lastEditTime != that.lastEditTime)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + paperId;

    hashCode = hashCode * 8191 + ((isSetPaperTitle()) ? 131071 : 524287);
    if (isSetPaperTitle())
      hashCode = hashCode * 8191 + paperTitle.hashCode();

    hashCode = hashCode * 8191 + categoryId;

    hashCode = hashCode * 8191 + createUserId;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(createTime);

    hashCode = hashCode * 8191 + flag;

    hashCode = hashCode * 8191 + lastEditUserId;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(lastEditTime);

    return hashCode;
  }

  @Override
  public int compareTo(PaperInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPaperId()).compareTo(other.isSetPaperId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPaperId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paperId, other.paperId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPaperTitle()).compareTo(other.isSetPaperTitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPaperTitle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.paperTitle, other.paperTitle);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCategoryId()).compareTo(other.isSetCategoryId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCategoryId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.categoryId, other.categoryId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCreateUserId()).compareTo(other.isSetCreateUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createUserId, other.createUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCreateTime()).compareTo(other.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTime, other.createTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetFlag()).compareTo(other.isSetFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFlag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.flag, other.flag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLastEditUserId()).compareTo(other.isSetLastEditUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastEditUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastEditUserId, other.lastEditUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLastEditTime()).compareTo(other.isSetLastEditTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastEditTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastEditTime, other.lastEditTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PaperInfo(");
    boolean first = true;

    sb.append("paperId:");
    sb.append(this.paperId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("paperTitle:");
    if (this.paperTitle == null) {
      sb.append("null");
    } else {
      sb.append(this.paperTitle);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("categoryId:");
    sb.append(this.categoryId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("createUserId:");
    sb.append(this.createUserId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("createTime:");
    sb.append(this.createTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("flag:");
    sb.append(this.flag);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lastEditUserId:");
    sb.append(this.lastEditUserId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lastEditTime:");
    sb.append(this.lastEditTime);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PaperInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PaperInfoStandardScheme getScheme() {
      return new PaperInfoStandardScheme();
    }
  }

  private static class PaperInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<PaperInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PaperInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAPER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.paperId = iprot.readI32();
              struct.setPaperIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PAPER_TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.paperTitle = iprot.readString();
              struct.setPaperTitleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CATEGORY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.categoryId = iprot.readI32();
              struct.setCategoryIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CREATE_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createUserId = iprot.readI32();
              struct.setCreateUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTime = iprot.readI64();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.flag = iprot.readI32();
              struct.setFlagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // LAST_EDIT_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lastEditUserId = iprot.readI32();
              struct.setLastEditUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // LAST_EDIT_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.lastEditTime = iprot.readI64();
              struct.setLastEditTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, PaperInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PAPER_ID_FIELD_DESC);
      oprot.writeI32(struct.paperId);
      oprot.writeFieldEnd();
      if (struct.paperTitle != null) {
        oprot.writeFieldBegin(PAPER_TITLE_FIELD_DESC);
        oprot.writeString(struct.paperTitle);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CATEGORY_ID_FIELD_DESC);
      oprot.writeI32(struct.categoryId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CREATE_USER_ID_FIELD_DESC);
      oprot.writeI32(struct.createUserId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
      oprot.writeI64(struct.createTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FLAG_FIELD_DESC);
      oprot.writeI32(struct.flag);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LAST_EDIT_USER_ID_FIELD_DESC);
      oprot.writeI32(struct.lastEditUserId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LAST_EDIT_TIME_FIELD_DESC);
      oprot.writeI64(struct.lastEditTime);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PaperInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PaperInfoTupleScheme getScheme() {
      return new PaperInfoTupleScheme();
    }
  }

  private static class PaperInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<PaperInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PaperInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPaperId()) {
        optionals.set(0);
      }
      if (struct.isSetPaperTitle()) {
        optionals.set(1);
      }
      if (struct.isSetCategoryId()) {
        optionals.set(2);
      }
      if (struct.isSetCreateUserId()) {
        optionals.set(3);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(4);
      }
      if (struct.isSetFlag()) {
        optionals.set(5);
      }
      if (struct.isSetLastEditUserId()) {
        optionals.set(6);
      }
      if (struct.isSetLastEditTime()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetPaperId()) {
        oprot.writeI32(struct.paperId);
      }
      if (struct.isSetPaperTitle()) {
        oprot.writeString(struct.paperTitle);
      }
      if (struct.isSetCategoryId()) {
        oprot.writeI32(struct.categoryId);
      }
      if (struct.isSetCreateUserId()) {
        oprot.writeI32(struct.createUserId);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeI64(struct.createTime);
      }
      if (struct.isSetFlag()) {
        oprot.writeI32(struct.flag);
      }
      if (struct.isSetLastEditUserId()) {
        oprot.writeI32(struct.lastEditUserId);
      }
      if (struct.isSetLastEditTime()) {
        oprot.writeI64(struct.lastEditTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PaperInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.paperId = iprot.readI32();
        struct.setPaperIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.paperTitle = iprot.readString();
        struct.setPaperTitleIsSet(true);
      }
      if (incoming.get(2)) {
        struct.categoryId = iprot.readI32();
        struct.setCategoryIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.createUserId = iprot.readI32();
        struct.setCreateUserIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.createTime = iprot.readI64();
        struct.setCreateTimeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.flag = iprot.readI32();
        struct.setFlagIsSet(true);
      }
      if (incoming.get(6)) {
        struct.lastEditUserId = iprot.readI32();
        struct.setLastEditUserIdIsSet(true);
      }
      if (incoming.get(7)) {
        struct.lastEditTime = iprot.readI64();
        struct.setLastEditTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

