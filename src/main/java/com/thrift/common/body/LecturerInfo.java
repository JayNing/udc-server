/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-16")
public class LecturerInfo implements org.apache.thrift.TBase<LecturerInfo, LecturerInfo._Fields>, java.io.Serializable, Cloneable, Comparable<LecturerInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LecturerInfo");

  private static final org.apache.thrift.protocol.TField LECTURER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("lecturerId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField LECTURER_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("lecturerName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField LEC_HEAD_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("lecHeadUrl", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField LEC_INTRODUCTION_FIELD_DESC = new org.apache.thrift.protocol.TField("lecIntroduction", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("flag", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.I64, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new LecturerInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new LecturerInfoTupleSchemeFactory();

  public int lecturerId; // required
  public java.lang.String lecturerName; // required
  public java.lang.String lecHeadUrl; // required
  public java.lang.String lecIntroduction; // required
  public int flag; // required
  public long createTime; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LECTURER_ID((short)1, "lecturerId"),
    LECTURER_NAME((short)2, "lecturerName"),
    LEC_HEAD_URL((short)3, "lecHeadUrl"),
    LEC_INTRODUCTION((short)4, "lecIntroduction"),
    FLAG((short)5, "flag"),
    CREATE_TIME((short)6, "createTime");

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
        case 1: // LECTURER_ID
          return LECTURER_ID;
        case 2: // LECTURER_NAME
          return LECTURER_NAME;
        case 3: // LEC_HEAD_URL
          return LEC_HEAD_URL;
        case 4: // LEC_INTRODUCTION
          return LEC_INTRODUCTION;
        case 5: // FLAG
          return FLAG;
        case 6: // CREATE_TIME
          return CREATE_TIME;
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
  private static final int __LECTURERID_ISSET_ID = 0;
  private static final int __FLAG_ISSET_ID = 1;
  private static final int __CREATETIME_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LECTURER_ID, new org.apache.thrift.meta_data.FieldMetaData("lecturerId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LECTURER_NAME, new org.apache.thrift.meta_data.FieldMetaData("lecturerName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LEC_HEAD_URL, new org.apache.thrift.meta_data.FieldMetaData("lecHeadUrl", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LEC_INTRODUCTION, new org.apache.thrift.meta_data.FieldMetaData("lecIntroduction", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FLAG, new org.apache.thrift.meta_data.FieldMetaData("flag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LecturerInfo.class, metaDataMap);
  }

  public LecturerInfo() {
  }

  public LecturerInfo(
    int lecturerId,
    java.lang.String lecturerName,
    java.lang.String lecHeadUrl,
    java.lang.String lecIntroduction,
    int flag,
    long createTime)
  {
    this();
    this.lecturerId = lecturerId;
    setLecturerIdIsSet(true);
    this.lecturerName = lecturerName;
    this.lecHeadUrl = lecHeadUrl;
    this.lecIntroduction = lecIntroduction;
    this.flag = flag;
    setFlagIsSet(true);
    this.createTime = createTime;
    setCreateTimeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LecturerInfo(LecturerInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.lecturerId = other.lecturerId;
    if (other.isSetLecturerName()) {
      this.lecturerName = other.lecturerName;
    }
    if (other.isSetLecHeadUrl()) {
      this.lecHeadUrl = other.lecHeadUrl;
    }
    if (other.isSetLecIntroduction()) {
      this.lecIntroduction = other.lecIntroduction;
    }
    this.flag = other.flag;
    this.createTime = other.createTime;
  }

  public LecturerInfo deepCopy() {
    return new LecturerInfo(this);
  }

  @Override
  public void clear() {
    setLecturerIdIsSet(false);
    this.lecturerId = 0;
    this.lecturerName = null;
    this.lecHeadUrl = null;
    this.lecIntroduction = null;
    setFlagIsSet(false);
    this.flag = 0;
    setCreateTimeIsSet(false);
    this.createTime = 0;
  }

  public int getLecturerId() {
    return this.lecturerId;
  }

  public LecturerInfo setLecturerId(int lecturerId) {
    this.lecturerId = lecturerId;
    setLecturerIdIsSet(true);
    return this;
  }

  public void unsetLecturerId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __LECTURERID_ISSET_ID);
  }

  /** Returns true if field lecturerId is set (has been assigned a value) and false otherwise */
  public boolean isSetLecturerId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __LECTURERID_ISSET_ID);
  }

  public void setLecturerIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __LECTURERID_ISSET_ID, value);
  }

  public java.lang.String getLecturerName() {
    return this.lecturerName;
  }

  public LecturerInfo setLecturerName(java.lang.String lecturerName) {
    this.lecturerName = lecturerName;
    return this;
  }

  public void unsetLecturerName() {
    this.lecturerName = null;
  }

  /** Returns true if field lecturerName is set (has been assigned a value) and false otherwise */
  public boolean isSetLecturerName() {
    return this.lecturerName != null;
  }

  public void setLecturerNameIsSet(boolean value) {
    if (!value) {
      this.lecturerName = null;
    }
  }

  public java.lang.String getLecHeadUrl() {
    return this.lecHeadUrl;
  }

  public LecturerInfo setLecHeadUrl(java.lang.String lecHeadUrl) {
    this.lecHeadUrl = lecHeadUrl;
    return this;
  }

  public void unsetLecHeadUrl() {
    this.lecHeadUrl = null;
  }

  /** Returns true if field lecHeadUrl is set (has been assigned a value) and false otherwise */
  public boolean isSetLecHeadUrl() {
    return this.lecHeadUrl != null;
  }

  public void setLecHeadUrlIsSet(boolean value) {
    if (!value) {
      this.lecHeadUrl = null;
    }
  }

  public java.lang.String getLecIntroduction() {
    return this.lecIntroduction;
  }

  public LecturerInfo setLecIntroduction(java.lang.String lecIntroduction) {
    this.lecIntroduction = lecIntroduction;
    return this;
  }

  public void unsetLecIntroduction() {
    this.lecIntroduction = null;
  }

  /** Returns true if field lecIntroduction is set (has been assigned a value) and false otherwise */
  public boolean isSetLecIntroduction() {
    return this.lecIntroduction != null;
  }

  public void setLecIntroductionIsSet(boolean value) {
    if (!value) {
      this.lecIntroduction = null;
    }
  }

  public int getFlag() {
    return this.flag;
  }

  public LecturerInfo setFlag(int flag) {
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

  public long getCreateTime() {
    return this.createTime;
  }

  public LecturerInfo setCreateTime(long createTime) {
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

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case LECTURER_ID:
      if (value == null) {
        unsetLecturerId();
      } else {
        setLecturerId((java.lang.Integer)value);
      }
      break;

    case LECTURER_NAME:
      if (value == null) {
        unsetLecturerName();
      } else {
        setLecturerName((java.lang.String)value);
      }
      break;

    case LEC_HEAD_URL:
      if (value == null) {
        unsetLecHeadUrl();
      } else {
        setLecHeadUrl((java.lang.String)value);
      }
      break;

    case LEC_INTRODUCTION:
      if (value == null) {
        unsetLecIntroduction();
      } else {
        setLecIntroduction((java.lang.String)value);
      }
      break;

    case FLAG:
      if (value == null) {
        unsetFlag();
      } else {
        setFlag((java.lang.Integer)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((java.lang.Long)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case LECTURER_ID:
      return getLecturerId();

    case LECTURER_NAME:
      return getLecturerName();

    case LEC_HEAD_URL:
      return getLecHeadUrl();

    case LEC_INTRODUCTION:
      return getLecIntroduction();

    case FLAG:
      return getFlag();

    case CREATE_TIME:
      return getCreateTime();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case LECTURER_ID:
      return isSetLecturerId();
    case LECTURER_NAME:
      return isSetLecturerName();
    case LEC_HEAD_URL:
      return isSetLecHeadUrl();
    case LEC_INTRODUCTION:
      return isSetLecIntroduction();
    case FLAG:
      return isSetFlag();
    case CREATE_TIME:
      return isSetCreateTime();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof LecturerInfo)
      return this.equals((LecturerInfo)that);
    return false;
  }

  public boolean equals(LecturerInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_lecturerId = true;
    boolean that_present_lecturerId = true;
    if (this_present_lecturerId || that_present_lecturerId) {
      if (!(this_present_lecturerId && that_present_lecturerId))
        return false;
      if (this.lecturerId != that.lecturerId)
        return false;
    }

    boolean this_present_lecturerName = true && this.isSetLecturerName();
    boolean that_present_lecturerName = true && that.isSetLecturerName();
    if (this_present_lecturerName || that_present_lecturerName) {
      if (!(this_present_lecturerName && that_present_lecturerName))
        return false;
      if (!this.lecturerName.equals(that.lecturerName))
        return false;
    }

    boolean this_present_lecHeadUrl = true && this.isSetLecHeadUrl();
    boolean that_present_lecHeadUrl = true && that.isSetLecHeadUrl();
    if (this_present_lecHeadUrl || that_present_lecHeadUrl) {
      if (!(this_present_lecHeadUrl && that_present_lecHeadUrl))
        return false;
      if (!this.lecHeadUrl.equals(that.lecHeadUrl))
        return false;
    }

    boolean this_present_lecIntroduction = true && this.isSetLecIntroduction();
    boolean that_present_lecIntroduction = true && that.isSetLecIntroduction();
    if (this_present_lecIntroduction || that_present_lecIntroduction) {
      if (!(this_present_lecIntroduction && that_present_lecIntroduction))
        return false;
      if (!this.lecIntroduction.equals(that.lecIntroduction))
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

    boolean this_present_createTime = true;
    boolean that_present_createTime = true;
    if (this_present_createTime || that_present_createTime) {
      if (!(this_present_createTime && that_present_createTime))
        return false;
      if (this.createTime != that.createTime)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + lecturerId;

    hashCode = hashCode * 8191 + ((isSetLecturerName()) ? 131071 : 524287);
    if (isSetLecturerName())
      hashCode = hashCode * 8191 + lecturerName.hashCode();

    hashCode = hashCode * 8191 + ((isSetLecHeadUrl()) ? 131071 : 524287);
    if (isSetLecHeadUrl())
      hashCode = hashCode * 8191 + lecHeadUrl.hashCode();

    hashCode = hashCode * 8191 + ((isSetLecIntroduction()) ? 131071 : 524287);
    if (isSetLecIntroduction())
      hashCode = hashCode * 8191 + lecIntroduction.hashCode();

    hashCode = hashCode * 8191 + flag;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(createTime);

    return hashCode;
  }

  @Override
  public int compareTo(LecturerInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetLecturerId()).compareTo(other.isSetLecturerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLecturerId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lecturerId, other.lecturerId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLecturerName()).compareTo(other.isSetLecturerName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLecturerName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lecturerName, other.lecturerName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLecHeadUrl()).compareTo(other.isSetLecHeadUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLecHeadUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lecHeadUrl, other.lecHeadUrl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLecIntroduction()).compareTo(other.isSetLecIntroduction());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLecIntroduction()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lecIntroduction, other.lecIntroduction);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("LecturerInfo(");
    boolean first = true;

    sb.append("lecturerId:");
    sb.append(this.lecturerId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lecturerName:");
    if (this.lecturerName == null) {
      sb.append("null");
    } else {
      sb.append(this.lecturerName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("lecHeadUrl:");
    if (this.lecHeadUrl == null) {
      sb.append("null");
    } else {
      sb.append(this.lecHeadUrl);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("lecIntroduction:");
    if (this.lecIntroduction == null) {
      sb.append("null");
    } else {
      sb.append(this.lecIntroduction);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("flag:");
    sb.append(this.flag);
    first = false;
    if (!first) sb.append(", ");
    sb.append("createTime:");
    sb.append(this.createTime);
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

  private static class LecturerInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LecturerInfoStandardScheme getScheme() {
      return new LecturerInfoStandardScheme();
    }
  }

  private static class LecturerInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<LecturerInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LecturerInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LECTURER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.lecturerId = iprot.readI32();
              struct.setLecturerIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LECTURER_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.lecturerName = iprot.readString();
              struct.setLecturerNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LEC_HEAD_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.lecHeadUrl = iprot.readString();
              struct.setLecHeadUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LEC_INTRODUCTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.lecIntroduction = iprot.readString();
              struct.setLecIntroductionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.flag = iprot.readI32();
              struct.setFlagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTime = iprot.readI64();
              struct.setCreateTimeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, LecturerInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(LECTURER_ID_FIELD_DESC);
      oprot.writeI32(struct.lecturerId);
      oprot.writeFieldEnd();
      if (struct.lecturerName != null) {
        oprot.writeFieldBegin(LECTURER_NAME_FIELD_DESC);
        oprot.writeString(struct.lecturerName);
        oprot.writeFieldEnd();
      }
      if (struct.lecHeadUrl != null) {
        oprot.writeFieldBegin(LEC_HEAD_URL_FIELD_DESC);
        oprot.writeString(struct.lecHeadUrl);
        oprot.writeFieldEnd();
      }
      if (struct.lecIntroduction != null) {
        oprot.writeFieldBegin(LEC_INTRODUCTION_FIELD_DESC);
        oprot.writeString(struct.lecIntroduction);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(FLAG_FIELD_DESC);
      oprot.writeI32(struct.flag);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
      oprot.writeI64(struct.createTime);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LecturerInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public LecturerInfoTupleScheme getScheme() {
      return new LecturerInfoTupleScheme();
    }
  }

  private static class LecturerInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<LecturerInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LecturerInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetLecturerId()) {
        optionals.set(0);
      }
      if (struct.isSetLecturerName()) {
        optionals.set(1);
      }
      if (struct.isSetLecHeadUrl()) {
        optionals.set(2);
      }
      if (struct.isSetLecIntroduction()) {
        optionals.set(3);
      }
      if (struct.isSetFlag()) {
        optionals.set(4);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetLecturerId()) {
        oprot.writeI32(struct.lecturerId);
      }
      if (struct.isSetLecturerName()) {
        oprot.writeString(struct.lecturerName);
      }
      if (struct.isSetLecHeadUrl()) {
        oprot.writeString(struct.lecHeadUrl);
      }
      if (struct.isSetLecIntroduction()) {
        oprot.writeString(struct.lecIntroduction);
      }
      if (struct.isSetFlag()) {
        oprot.writeI32(struct.flag);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeI64(struct.createTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LecturerInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.lecturerId = iprot.readI32();
        struct.setLecturerIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.lecturerName = iprot.readString();
        struct.setLecturerNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.lecHeadUrl = iprot.readString();
        struct.setLecHeadUrlIsSet(true);
      }
      if (incoming.get(3)) {
        struct.lecIntroduction = iprot.readString();
        struct.setLecIntroductionIsSet(true);
      }
      if (incoming.get(4)) {
        struct.flag = iprot.readI32();
        struct.setFlagIsSet(true);
      }
      if (incoming.get(5)) {
        struct.createTime = iprot.readI64();
        struct.setCreateTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
