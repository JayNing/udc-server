/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-04-27")
public class RepositoryCategory implements org.apache.thrift.TBase<RepositoryCategory, RepositoryCategory._Fields>, java.io.Serializable, Cloneable, Comparable<RepositoryCategory> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RepositoryCategory");

  private static final org.apache.thrift.protocol.TField REP_CAT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("repCatId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField REP_CAT_PARENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("repCatParentId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField REP_CAT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("repCatName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("flag", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.I64, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new RepositoryCategoryStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new RepositoryCategoryTupleSchemeFactory();

  public int repCatId; // required
  public int repCatParentId; // required
  public java.lang.String repCatName; // required
  public int flag; // required
  public long createTime; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    REP_CAT_ID((short)1, "repCatId"),
    REP_CAT_PARENT_ID((short)2, "repCatParentId"),
    REP_CAT_NAME((short)3, "repCatName"),
    FLAG((short)4, "flag"),
    CREATE_TIME((short)5, "createTime");

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
        case 1: // REP_CAT_ID
          return REP_CAT_ID;
        case 2: // REP_CAT_PARENT_ID
          return REP_CAT_PARENT_ID;
        case 3: // REP_CAT_NAME
          return REP_CAT_NAME;
        case 4: // FLAG
          return FLAG;
        case 5: // CREATE_TIME
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
  private static final int __REPCATID_ISSET_ID = 0;
  private static final int __REPCATPARENTID_ISSET_ID = 1;
  private static final int __FLAG_ISSET_ID = 2;
  private static final int __CREATETIME_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.REP_CAT_ID, new org.apache.thrift.meta_data.FieldMetaData("repCatId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REP_CAT_PARENT_ID, new org.apache.thrift.meta_data.FieldMetaData("repCatParentId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REP_CAT_NAME, new org.apache.thrift.meta_data.FieldMetaData("repCatName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FLAG, new org.apache.thrift.meta_data.FieldMetaData("flag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RepositoryCategory.class, metaDataMap);
  }

  public RepositoryCategory() {
  }

  public RepositoryCategory(
    int repCatId,
    int repCatParentId,
    java.lang.String repCatName,
    int flag,
    long createTime)
  {
    this();
    this.repCatId = repCatId;
    setRepCatIdIsSet(true);
    this.repCatParentId = repCatParentId;
    setRepCatParentIdIsSet(true);
    this.repCatName = repCatName;
    this.flag = flag;
    setFlagIsSet(true);
    this.createTime = createTime;
    setCreateTimeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RepositoryCategory(RepositoryCategory other) {
    __isset_bitfield = other.__isset_bitfield;
    this.repCatId = other.repCatId;
    this.repCatParentId = other.repCatParentId;
    if (other.isSetRepCatName()) {
      this.repCatName = other.repCatName;
    }
    this.flag = other.flag;
    this.createTime = other.createTime;
  }

  public RepositoryCategory deepCopy() {
    return new RepositoryCategory(this);
  }

  @Override
  public void clear() {
    setRepCatIdIsSet(false);
    this.repCatId = 0;
    setRepCatParentIdIsSet(false);
    this.repCatParentId = 0;
    this.repCatName = null;
    setFlagIsSet(false);
    this.flag = 0;
    setCreateTimeIsSet(false);
    this.createTime = 0;
  }

  public int getRepCatId() {
    return this.repCatId;
  }

  public RepositoryCategory setRepCatId(int repCatId) {
    this.repCatId = repCatId;
    setRepCatIdIsSet(true);
    return this;
  }

  public void unsetRepCatId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __REPCATID_ISSET_ID);
  }

  /** Returns true if field repCatId is set (has been assigned a value) and false otherwise */
  public boolean isSetRepCatId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __REPCATID_ISSET_ID);
  }

  public void setRepCatIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __REPCATID_ISSET_ID, value);
  }

  public int getRepCatParentId() {
    return this.repCatParentId;
  }

  public RepositoryCategory setRepCatParentId(int repCatParentId) {
    this.repCatParentId = repCatParentId;
    setRepCatParentIdIsSet(true);
    return this;
  }

  public void unsetRepCatParentId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __REPCATPARENTID_ISSET_ID);
  }

  /** Returns true if field repCatParentId is set (has been assigned a value) and false otherwise */
  public boolean isSetRepCatParentId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __REPCATPARENTID_ISSET_ID);
  }

  public void setRepCatParentIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __REPCATPARENTID_ISSET_ID, value);
  }

  public java.lang.String getRepCatName() {
    return this.repCatName;
  }

  public RepositoryCategory setRepCatName(java.lang.String repCatName) {
    this.repCatName = repCatName;
    return this;
  }

  public void unsetRepCatName() {
    this.repCatName = null;
  }

  /** Returns true if field repCatName is set (has been assigned a value) and false otherwise */
  public boolean isSetRepCatName() {
    return this.repCatName != null;
  }

  public void setRepCatNameIsSet(boolean value) {
    if (!value) {
      this.repCatName = null;
    }
  }

  public int getFlag() {
    return this.flag;
  }

  public RepositoryCategory setFlag(int flag) {
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

  public RepositoryCategory setCreateTime(long createTime) {
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
    case REP_CAT_ID:
      if (value == null) {
        unsetRepCatId();
      } else {
        setRepCatId((java.lang.Integer)value);
      }
      break;

    case REP_CAT_PARENT_ID:
      if (value == null) {
        unsetRepCatParentId();
      } else {
        setRepCatParentId((java.lang.Integer)value);
      }
      break;

    case REP_CAT_NAME:
      if (value == null) {
        unsetRepCatName();
      } else {
        setRepCatName((java.lang.String)value);
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
    case REP_CAT_ID:
      return getRepCatId();

    case REP_CAT_PARENT_ID:
      return getRepCatParentId();

    case REP_CAT_NAME:
      return getRepCatName();

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
    case REP_CAT_ID:
      return isSetRepCatId();
    case REP_CAT_PARENT_ID:
      return isSetRepCatParentId();
    case REP_CAT_NAME:
      return isSetRepCatName();
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
    if (that instanceof RepositoryCategory)
      return this.equals((RepositoryCategory)that);
    return false;
  }

  public boolean equals(RepositoryCategory that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_repCatId = true;
    boolean that_present_repCatId = true;
    if (this_present_repCatId || that_present_repCatId) {
      if (!(this_present_repCatId && that_present_repCatId))
        return false;
      if (this.repCatId != that.repCatId)
        return false;
    }

    boolean this_present_repCatParentId = true;
    boolean that_present_repCatParentId = true;
    if (this_present_repCatParentId || that_present_repCatParentId) {
      if (!(this_present_repCatParentId && that_present_repCatParentId))
        return false;
      if (this.repCatParentId != that.repCatParentId)
        return false;
    }

    boolean this_present_repCatName = true && this.isSetRepCatName();
    boolean that_present_repCatName = true && that.isSetRepCatName();
    if (this_present_repCatName || that_present_repCatName) {
      if (!(this_present_repCatName && that_present_repCatName))
        return false;
      if (!this.repCatName.equals(that.repCatName))
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

    hashCode = hashCode * 8191 + repCatId;

    hashCode = hashCode * 8191 + repCatParentId;

    hashCode = hashCode * 8191 + ((isSetRepCatName()) ? 131071 : 524287);
    if (isSetRepCatName())
      hashCode = hashCode * 8191 + repCatName.hashCode();

    hashCode = hashCode * 8191 + flag;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(createTime);

    return hashCode;
  }

  @Override
  public int compareTo(RepositoryCategory other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetRepCatId()).compareTo(other.isSetRepCatId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRepCatId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.repCatId, other.repCatId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetRepCatParentId()).compareTo(other.isSetRepCatParentId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRepCatParentId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.repCatParentId, other.repCatParentId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetRepCatName()).compareTo(other.isSetRepCatName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRepCatName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.repCatName, other.repCatName);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("RepositoryCategory(");
    boolean first = true;

    sb.append("repCatId:");
    sb.append(this.repCatId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("repCatParentId:");
    sb.append(this.repCatParentId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("repCatName:");
    if (this.repCatName == null) {
      sb.append("null");
    } else {
      sb.append(this.repCatName);
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

  private static class RepositoryCategoryStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public RepositoryCategoryStandardScheme getScheme() {
      return new RepositoryCategoryStandardScheme();
    }
  }

  private static class RepositoryCategoryStandardScheme extends org.apache.thrift.scheme.StandardScheme<RepositoryCategory> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RepositoryCategory struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // REP_CAT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.repCatId = iprot.readI32();
              struct.setRepCatIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // REP_CAT_PARENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.repCatParentId = iprot.readI32();
              struct.setRepCatParentIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // REP_CAT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.repCatName = iprot.readString();
              struct.setRepCatNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.flag = iprot.readI32();
              struct.setFlagIsSet(true);
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
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, RepositoryCategory struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(REP_CAT_ID_FIELD_DESC);
      oprot.writeI32(struct.repCatId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(REP_CAT_PARENT_ID_FIELD_DESC);
      oprot.writeI32(struct.repCatParentId);
      oprot.writeFieldEnd();
      if (struct.repCatName != null) {
        oprot.writeFieldBegin(REP_CAT_NAME_FIELD_DESC);
        oprot.writeString(struct.repCatName);
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

  private static class RepositoryCategoryTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public RepositoryCategoryTupleScheme getScheme() {
      return new RepositoryCategoryTupleScheme();
    }
  }

  private static class RepositoryCategoryTupleScheme extends org.apache.thrift.scheme.TupleScheme<RepositoryCategory> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RepositoryCategory struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetRepCatId()) {
        optionals.set(0);
      }
      if (struct.isSetRepCatParentId()) {
        optionals.set(1);
      }
      if (struct.isSetRepCatName()) {
        optionals.set(2);
      }
      if (struct.isSetFlag()) {
        optionals.set(3);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetRepCatId()) {
        oprot.writeI32(struct.repCatId);
      }
      if (struct.isSetRepCatParentId()) {
        oprot.writeI32(struct.repCatParentId);
      }
      if (struct.isSetRepCatName()) {
        oprot.writeString(struct.repCatName);
      }
      if (struct.isSetFlag()) {
        oprot.writeI32(struct.flag);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeI64(struct.createTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RepositoryCategory struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.repCatId = iprot.readI32();
        struct.setRepCatIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.repCatParentId = iprot.readI32();
        struct.setRepCatParentIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.repCatName = iprot.readString();
        struct.setRepCatNameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.flag = iprot.readI32();
        struct.setFlagIsSet(true);
      }
      if (incoming.get(4)) {
        struct.createTime = iprot.readI64();
        struct.setCreateTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

