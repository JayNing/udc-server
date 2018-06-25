/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-25")
public class EssayInfo implements org.apache.thrift.TBase<EssayInfo, EssayInfo._Fields>, java.io.Serializable, Cloneable, Comparable<EssayInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EssayInfo");

  private static final org.apache.thrift.protocol.TField ESSAY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("essayId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ESSAY_CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("essayContent", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField ESS_FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("essFlag", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new EssayInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new EssayInfoTupleSchemeFactory();

  public int essayId; // required
  public java.lang.String essayContent; // required
  public long createTime; // required
  public int essFlag; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ESSAY_ID((short)1, "essayId"),
    ESSAY_CONTENT((short)2, "essayContent"),
    CREATE_TIME((short)3, "createTime"),
    ESS_FLAG((short)4, "essFlag");

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
        case 1: // ESSAY_ID
          return ESSAY_ID;
        case 2: // ESSAY_CONTENT
          return ESSAY_CONTENT;
        case 3: // CREATE_TIME
          return CREATE_TIME;
        case 4: // ESS_FLAG
          return ESS_FLAG;
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
  private static final int __ESSAYID_ISSET_ID = 0;
  private static final int __CREATETIME_ISSET_ID = 1;
  private static final int __ESSFLAG_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ESSAY_ID, new org.apache.thrift.meta_data.FieldMetaData("essayId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ESSAY_CONTENT, new org.apache.thrift.meta_data.FieldMetaData("essayContent", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ESS_FLAG, new org.apache.thrift.meta_data.FieldMetaData("essFlag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EssayInfo.class, metaDataMap);
  }

  public EssayInfo() {
  }

  public EssayInfo(
    int essayId,
    java.lang.String essayContent,
    long createTime,
    int essFlag)
  {
    this();
    this.essayId = essayId;
    setEssayIdIsSet(true);
    this.essayContent = essayContent;
    this.createTime = createTime;
    setCreateTimeIsSet(true);
    this.essFlag = essFlag;
    setEssFlagIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EssayInfo(EssayInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.essayId = other.essayId;
    if (other.isSetEssayContent()) {
      this.essayContent = other.essayContent;
    }
    this.createTime = other.createTime;
    this.essFlag = other.essFlag;
  }

  public EssayInfo deepCopy() {
    return new EssayInfo(this);
  }

  @Override
  public void clear() {
    setEssayIdIsSet(false);
    this.essayId = 0;
    this.essayContent = null;
    setCreateTimeIsSet(false);
    this.createTime = 0;
    setEssFlagIsSet(false);
    this.essFlag = 0;
  }

  public int getEssayId() {
    return this.essayId;
  }

  public EssayInfo setEssayId(int essayId) {
    this.essayId = essayId;
    setEssayIdIsSet(true);
    return this;
  }

  public void unsetEssayId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ESSAYID_ISSET_ID);
  }

  /** Returns true if field essayId is set (has been assigned a value) and false otherwise */
  public boolean isSetEssayId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ESSAYID_ISSET_ID);
  }

  public void setEssayIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ESSAYID_ISSET_ID, value);
  }

  public java.lang.String getEssayContent() {
    return this.essayContent;
  }

  public EssayInfo setEssayContent(java.lang.String essayContent) {
    this.essayContent = essayContent;
    return this;
  }

  public void unsetEssayContent() {
    this.essayContent = null;
  }

  /** Returns true if field essayContent is set (has been assigned a value) and false otherwise */
  public boolean isSetEssayContent() {
    return this.essayContent != null;
  }

  public void setEssayContentIsSet(boolean value) {
    if (!value) {
      this.essayContent = null;
    }
  }

  public long getCreateTime() {
    return this.createTime;
  }

  public EssayInfo setCreateTime(long createTime) {
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

  public int getEssFlag() {
    return this.essFlag;
  }

  public EssayInfo setEssFlag(int essFlag) {
    this.essFlag = essFlag;
    setEssFlagIsSet(true);
    return this;
  }

  public void unsetEssFlag() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ESSFLAG_ISSET_ID);
  }

  /** Returns true if field essFlag is set (has been assigned a value) and false otherwise */
  public boolean isSetEssFlag() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ESSFLAG_ISSET_ID);
  }

  public void setEssFlagIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ESSFLAG_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ESSAY_ID:
      if (value == null) {
        unsetEssayId();
      } else {
        setEssayId((java.lang.Integer)value);
      }
      break;

    case ESSAY_CONTENT:
      if (value == null) {
        unsetEssayContent();
      } else {
        setEssayContent((java.lang.String)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((java.lang.Long)value);
      }
      break;

    case ESS_FLAG:
      if (value == null) {
        unsetEssFlag();
      } else {
        setEssFlag((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ESSAY_ID:
      return getEssayId();

    case ESSAY_CONTENT:
      return getEssayContent();

    case CREATE_TIME:
      return getCreateTime();

    case ESS_FLAG:
      return getEssFlag();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ESSAY_ID:
      return isSetEssayId();
    case ESSAY_CONTENT:
      return isSetEssayContent();
    case CREATE_TIME:
      return isSetCreateTime();
    case ESS_FLAG:
      return isSetEssFlag();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof EssayInfo)
      return this.equals((EssayInfo)that);
    return false;
  }

  public boolean equals(EssayInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_essayId = true;
    boolean that_present_essayId = true;
    if (this_present_essayId || that_present_essayId) {
      if (!(this_present_essayId && that_present_essayId))
        return false;
      if (this.essayId != that.essayId)
        return false;
    }

    boolean this_present_essayContent = true && this.isSetEssayContent();
    boolean that_present_essayContent = true && that.isSetEssayContent();
    if (this_present_essayContent || that_present_essayContent) {
      if (!(this_present_essayContent && that_present_essayContent))
        return false;
      if (!this.essayContent.equals(that.essayContent))
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

    boolean this_present_essFlag = true;
    boolean that_present_essFlag = true;
    if (this_present_essFlag || that_present_essFlag) {
      if (!(this_present_essFlag && that_present_essFlag))
        return false;
      if (this.essFlag != that.essFlag)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + essayId;

    hashCode = hashCode * 8191 + ((isSetEssayContent()) ? 131071 : 524287);
    if (isSetEssayContent())
      hashCode = hashCode * 8191 + essayContent.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(createTime);

    hashCode = hashCode * 8191 + essFlag;

    return hashCode;
  }

  @Override
  public int compareTo(EssayInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetEssayId()).compareTo(other.isSetEssayId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEssayId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.essayId, other.essayId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetEssayContent()).compareTo(other.isSetEssayContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEssayContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.essayContent, other.essayContent);
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
    lastComparison = java.lang.Boolean.valueOf(isSetEssFlag()).compareTo(other.isSetEssFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEssFlag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.essFlag, other.essFlag);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("EssayInfo(");
    boolean first = true;

    sb.append("essayId:");
    sb.append(this.essayId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("essayContent:");
    if (this.essayContent == null) {
      sb.append("null");
    } else {
      sb.append(this.essayContent);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("createTime:");
    sb.append(this.createTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("essFlag:");
    sb.append(this.essFlag);
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

  private static class EssayInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public EssayInfoStandardScheme getScheme() {
      return new EssayInfoStandardScheme();
    }
  }

  private static class EssayInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<EssayInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EssayInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ESSAY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.essayId = iprot.readI32();
              struct.setEssayIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ESSAY_CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.essayContent = iprot.readString();
              struct.setEssayContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTime = iprot.readI64();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ESS_FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.essFlag = iprot.readI32();
              struct.setEssFlagIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, EssayInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ESSAY_ID_FIELD_DESC);
      oprot.writeI32(struct.essayId);
      oprot.writeFieldEnd();
      if (struct.essayContent != null) {
        oprot.writeFieldBegin(ESSAY_CONTENT_FIELD_DESC);
        oprot.writeString(struct.essayContent);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
      oprot.writeI64(struct.createTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ESS_FLAG_FIELD_DESC);
      oprot.writeI32(struct.essFlag);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class EssayInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public EssayInfoTupleScheme getScheme() {
      return new EssayInfoTupleScheme();
    }
  }

  private static class EssayInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<EssayInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EssayInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetEssayId()) {
        optionals.set(0);
      }
      if (struct.isSetEssayContent()) {
        optionals.set(1);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(2);
      }
      if (struct.isSetEssFlag()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetEssayId()) {
        oprot.writeI32(struct.essayId);
      }
      if (struct.isSetEssayContent()) {
        oprot.writeString(struct.essayContent);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeI64(struct.createTime);
      }
      if (struct.isSetEssFlag()) {
        oprot.writeI32(struct.essFlag);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EssayInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.essayId = iprot.readI32();
        struct.setEssayIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.essayContent = iprot.readString();
        struct.setEssayContentIsSet(true);
      }
      if (incoming.get(2)) {
        struct.createTime = iprot.readI64();
        struct.setCreateTimeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.essFlag = iprot.readI32();
        struct.setEssFlagIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

