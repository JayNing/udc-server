/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-16")
public class OptionsInfo implements org.apache.thrift.TBase<OptionsInfo, OptionsInfo._Fields>, java.io.Serializable, Cloneable, Comparable<OptionsInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("OptionsInfo");

  private static final org.apache.thrift.protocol.TField OPTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("optionId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField EXERCISE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("exerciseId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField OPTION_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("optionCode", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField OPTION_DESC_FIELD_DESC = new org.apache.thrift.protocol.TField("optionDesc", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CRRCT_ANS_FIELD_DESC = new org.apache.thrift.protocol.TField("crrctAns", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("flag", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new OptionsInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new OptionsInfoTupleSchemeFactory();

  public int optionId; // required
  public int exerciseId; // required
  public java.lang.String optionCode; // required
  public java.lang.String optionDesc; // required
  public int crrctAns; // required
  public int flag; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OPTION_ID((short)1, "optionId"),
    EXERCISE_ID((short)2, "exerciseId"),
    OPTION_CODE((short)3, "optionCode"),
    OPTION_DESC((short)4, "optionDesc"),
    CRRCT_ANS((short)5, "crrctAns"),
    FLAG((short)6, "flag");

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
        case 1: // OPTION_ID
          return OPTION_ID;
        case 2: // EXERCISE_ID
          return EXERCISE_ID;
        case 3: // OPTION_CODE
          return OPTION_CODE;
        case 4: // OPTION_DESC
          return OPTION_DESC;
        case 5: // CRRCT_ANS
          return CRRCT_ANS;
        case 6: // FLAG
          return FLAG;
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
  private static final int __OPTIONID_ISSET_ID = 0;
  private static final int __EXERCISEID_ISSET_ID = 1;
  private static final int __CRRCTANS_ISSET_ID = 2;
  private static final int __FLAG_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OPTION_ID, new org.apache.thrift.meta_data.FieldMetaData("optionId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.EXERCISE_ID, new org.apache.thrift.meta_data.FieldMetaData("exerciseId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.OPTION_CODE, new org.apache.thrift.meta_data.FieldMetaData("optionCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OPTION_DESC, new org.apache.thrift.meta_data.FieldMetaData("optionDesc", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CRRCT_ANS, new org.apache.thrift.meta_data.FieldMetaData("crrctAns", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FLAG, new org.apache.thrift.meta_data.FieldMetaData("flag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(OptionsInfo.class, metaDataMap);
  }

  public OptionsInfo() {
  }

  public OptionsInfo(
    int optionId,
    int exerciseId,
    java.lang.String optionCode,
    java.lang.String optionDesc,
    int crrctAns,
    int flag)
  {
    this();
    this.optionId = optionId;
    setOptionIdIsSet(true);
    this.exerciseId = exerciseId;
    setExerciseIdIsSet(true);
    this.optionCode = optionCode;
    this.optionDesc = optionDesc;
    this.crrctAns = crrctAns;
    setCrrctAnsIsSet(true);
    this.flag = flag;
    setFlagIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public OptionsInfo(OptionsInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.optionId = other.optionId;
    this.exerciseId = other.exerciseId;
    if (other.isSetOptionCode()) {
      this.optionCode = other.optionCode;
    }
    if (other.isSetOptionDesc()) {
      this.optionDesc = other.optionDesc;
    }
    this.crrctAns = other.crrctAns;
    this.flag = other.flag;
  }

  public OptionsInfo deepCopy() {
    return new OptionsInfo(this);
  }

  @Override
  public void clear() {
    setOptionIdIsSet(false);
    this.optionId = 0;
    setExerciseIdIsSet(false);
    this.exerciseId = 0;
    this.optionCode = null;
    this.optionDesc = null;
    setCrrctAnsIsSet(false);
    this.crrctAns = 0;
    setFlagIsSet(false);
    this.flag = 0;
  }

  public int getOptionId() {
    return this.optionId;
  }

  public OptionsInfo setOptionId(int optionId) {
    this.optionId = optionId;
    setOptionIdIsSet(true);
    return this;
  }

  public void unsetOptionId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __OPTIONID_ISSET_ID);
  }

  /** Returns true if field optionId is set (has been assigned a value) and false otherwise */
  public boolean isSetOptionId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __OPTIONID_ISSET_ID);
  }

  public void setOptionIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __OPTIONID_ISSET_ID, value);
  }

  public int getExerciseId() {
    return this.exerciseId;
  }

  public OptionsInfo setExerciseId(int exerciseId) {
    this.exerciseId = exerciseId;
    setExerciseIdIsSet(true);
    return this;
  }

  public void unsetExerciseId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __EXERCISEID_ISSET_ID);
  }

  /** Returns true if field exerciseId is set (has been assigned a value) and false otherwise */
  public boolean isSetExerciseId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __EXERCISEID_ISSET_ID);
  }

  public void setExerciseIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __EXERCISEID_ISSET_ID, value);
  }

  public java.lang.String getOptionCode() {
    return this.optionCode;
  }

  public OptionsInfo setOptionCode(java.lang.String optionCode) {
    this.optionCode = optionCode;
    return this;
  }

  public void unsetOptionCode() {
    this.optionCode = null;
  }

  /** Returns true if field optionCode is set (has been assigned a value) and false otherwise */
  public boolean isSetOptionCode() {
    return this.optionCode != null;
  }

  public void setOptionCodeIsSet(boolean value) {
    if (!value) {
      this.optionCode = null;
    }
  }

  public java.lang.String getOptionDesc() {
    return this.optionDesc;
  }

  public OptionsInfo setOptionDesc(java.lang.String optionDesc) {
    this.optionDesc = optionDesc;
    return this;
  }

  public void unsetOptionDesc() {
    this.optionDesc = null;
  }

  /** Returns true if field optionDesc is set (has been assigned a value) and false otherwise */
  public boolean isSetOptionDesc() {
    return this.optionDesc != null;
  }

  public void setOptionDescIsSet(boolean value) {
    if (!value) {
      this.optionDesc = null;
    }
  }

  public int getCrrctAns() {
    return this.crrctAns;
  }

  public OptionsInfo setCrrctAns(int crrctAns) {
    this.crrctAns = crrctAns;
    setCrrctAnsIsSet(true);
    return this;
  }

  public void unsetCrrctAns() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CRRCTANS_ISSET_ID);
  }

  /** Returns true if field crrctAns is set (has been assigned a value) and false otherwise */
  public boolean isSetCrrctAns() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CRRCTANS_ISSET_ID);
  }

  public void setCrrctAnsIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CRRCTANS_ISSET_ID, value);
  }

  public int getFlag() {
    return this.flag;
  }

  public OptionsInfo setFlag(int flag) {
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

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case OPTION_ID:
      if (value == null) {
        unsetOptionId();
      } else {
        setOptionId((java.lang.Integer)value);
      }
      break;

    case EXERCISE_ID:
      if (value == null) {
        unsetExerciseId();
      } else {
        setExerciseId((java.lang.Integer)value);
      }
      break;

    case OPTION_CODE:
      if (value == null) {
        unsetOptionCode();
      } else {
        setOptionCode((java.lang.String)value);
      }
      break;

    case OPTION_DESC:
      if (value == null) {
        unsetOptionDesc();
      } else {
        setOptionDesc((java.lang.String)value);
      }
      break;

    case CRRCT_ANS:
      if (value == null) {
        unsetCrrctAns();
      } else {
        setCrrctAns((java.lang.Integer)value);
      }
      break;

    case FLAG:
      if (value == null) {
        unsetFlag();
      } else {
        setFlag((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case OPTION_ID:
      return getOptionId();

    case EXERCISE_ID:
      return getExerciseId();

    case OPTION_CODE:
      return getOptionCode();

    case OPTION_DESC:
      return getOptionDesc();

    case CRRCT_ANS:
      return getCrrctAns();

    case FLAG:
      return getFlag();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case OPTION_ID:
      return isSetOptionId();
    case EXERCISE_ID:
      return isSetExerciseId();
    case OPTION_CODE:
      return isSetOptionCode();
    case OPTION_DESC:
      return isSetOptionDesc();
    case CRRCT_ANS:
      return isSetCrrctAns();
    case FLAG:
      return isSetFlag();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof OptionsInfo)
      return this.equals((OptionsInfo)that);
    return false;
  }

  public boolean equals(OptionsInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_optionId = true;
    boolean that_present_optionId = true;
    if (this_present_optionId || that_present_optionId) {
      if (!(this_present_optionId && that_present_optionId))
        return false;
      if (this.optionId != that.optionId)
        return false;
    }

    boolean this_present_exerciseId = true;
    boolean that_present_exerciseId = true;
    if (this_present_exerciseId || that_present_exerciseId) {
      if (!(this_present_exerciseId && that_present_exerciseId))
        return false;
      if (this.exerciseId != that.exerciseId)
        return false;
    }

    boolean this_present_optionCode = true && this.isSetOptionCode();
    boolean that_present_optionCode = true && that.isSetOptionCode();
    if (this_present_optionCode || that_present_optionCode) {
      if (!(this_present_optionCode && that_present_optionCode))
        return false;
      if (!this.optionCode.equals(that.optionCode))
        return false;
    }

    boolean this_present_optionDesc = true && this.isSetOptionDesc();
    boolean that_present_optionDesc = true && that.isSetOptionDesc();
    if (this_present_optionDesc || that_present_optionDesc) {
      if (!(this_present_optionDesc && that_present_optionDesc))
        return false;
      if (!this.optionDesc.equals(that.optionDesc))
        return false;
    }

    boolean this_present_crrctAns = true;
    boolean that_present_crrctAns = true;
    if (this_present_crrctAns || that_present_crrctAns) {
      if (!(this_present_crrctAns && that_present_crrctAns))
        return false;
      if (this.crrctAns != that.crrctAns)
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

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + optionId;

    hashCode = hashCode * 8191 + exerciseId;

    hashCode = hashCode * 8191 + ((isSetOptionCode()) ? 131071 : 524287);
    if (isSetOptionCode())
      hashCode = hashCode * 8191 + optionCode.hashCode();

    hashCode = hashCode * 8191 + ((isSetOptionDesc()) ? 131071 : 524287);
    if (isSetOptionDesc())
      hashCode = hashCode * 8191 + optionDesc.hashCode();

    hashCode = hashCode * 8191 + crrctAns;

    hashCode = hashCode * 8191 + flag;

    return hashCode;
  }

  @Override
  public int compareTo(OptionsInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetOptionId()).compareTo(other.isSetOptionId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOptionId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.optionId, other.optionId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetExerciseId()).compareTo(other.isSetExerciseId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExerciseId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.exerciseId, other.exerciseId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetOptionCode()).compareTo(other.isSetOptionCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOptionCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.optionCode, other.optionCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetOptionDesc()).compareTo(other.isSetOptionDesc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOptionDesc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.optionDesc, other.optionDesc);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCrrctAns()).compareTo(other.isSetCrrctAns());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCrrctAns()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.crrctAns, other.crrctAns);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("OptionsInfo(");
    boolean first = true;

    sb.append("optionId:");
    sb.append(this.optionId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("exerciseId:");
    sb.append(this.exerciseId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("optionCode:");
    if (this.optionCode == null) {
      sb.append("null");
    } else {
      sb.append(this.optionCode);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("optionDesc:");
    if (this.optionDesc == null) {
      sb.append("null");
    } else {
      sb.append(this.optionDesc);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("crrctAns:");
    sb.append(this.crrctAns);
    first = false;
    if (!first) sb.append(", ");
    sb.append("flag:");
    sb.append(this.flag);
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

  private static class OptionsInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public OptionsInfoStandardScheme getScheme() {
      return new OptionsInfoStandardScheme();
    }
  }

  private static class OptionsInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<OptionsInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, OptionsInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OPTION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.optionId = iprot.readI32();
              struct.setOptionIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // EXERCISE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.exerciseId = iprot.readI32();
              struct.setExerciseIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // OPTION_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.optionCode = iprot.readString();
              struct.setOptionCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // OPTION_DESC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.optionDesc = iprot.readString();
              struct.setOptionDescIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CRRCT_ANS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.crrctAns = iprot.readI32();
              struct.setCrrctAnsIsSet(true);
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
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, OptionsInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(OPTION_ID_FIELD_DESC);
      oprot.writeI32(struct.optionId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(EXERCISE_ID_FIELD_DESC);
      oprot.writeI32(struct.exerciseId);
      oprot.writeFieldEnd();
      if (struct.optionCode != null) {
        oprot.writeFieldBegin(OPTION_CODE_FIELD_DESC);
        oprot.writeString(struct.optionCode);
        oprot.writeFieldEnd();
      }
      if (struct.optionDesc != null) {
        oprot.writeFieldBegin(OPTION_DESC_FIELD_DESC);
        oprot.writeString(struct.optionDesc);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CRRCT_ANS_FIELD_DESC);
      oprot.writeI32(struct.crrctAns);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FLAG_FIELD_DESC);
      oprot.writeI32(struct.flag);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class OptionsInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public OptionsInfoTupleScheme getScheme() {
      return new OptionsInfoTupleScheme();
    }
  }

  private static class OptionsInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<OptionsInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, OptionsInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetOptionId()) {
        optionals.set(0);
      }
      if (struct.isSetExerciseId()) {
        optionals.set(1);
      }
      if (struct.isSetOptionCode()) {
        optionals.set(2);
      }
      if (struct.isSetOptionDesc()) {
        optionals.set(3);
      }
      if (struct.isSetCrrctAns()) {
        optionals.set(4);
      }
      if (struct.isSetFlag()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetOptionId()) {
        oprot.writeI32(struct.optionId);
      }
      if (struct.isSetExerciseId()) {
        oprot.writeI32(struct.exerciseId);
      }
      if (struct.isSetOptionCode()) {
        oprot.writeString(struct.optionCode);
      }
      if (struct.isSetOptionDesc()) {
        oprot.writeString(struct.optionDesc);
      }
      if (struct.isSetCrrctAns()) {
        oprot.writeI32(struct.crrctAns);
      }
      if (struct.isSetFlag()) {
        oprot.writeI32(struct.flag);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, OptionsInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.optionId = iprot.readI32();
        struct.setOptionIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.exerciseId = iprot.readI32();
        struct.setExerciseIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.optionCode = iprot.readString();
        struct.setOptionCodeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.optionDesc = iprot.readString();
        struct.setOptionDescIsSet(true);
      }
      if (incoming.get(4)) {
        struct.crrctAns = iprot.readI32();
        struct.setCrrctAnsIsSet(true);
      }
      if (incoming.get(5)) {
        struct.flag = iprot.readI32();
        struct.setFlagIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

