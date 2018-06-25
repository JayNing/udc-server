/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-09")
public class MascotUserInfo implements org.apache.thrift.TBase<MascotUserInfo, MascotUserInfo._Fields>, java.io.Serializable, Cloneable, Comparable<MascotUserInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MascotUserInfo");

  private static final org.apache.thrift.protocol.TField PK_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("pkId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField USER_SCORE_FIELD_DESC = new org.apache.thrift.protocol.TField("userScore", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MascotUserInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MascotUserInfoTupleSchemeFactory();

  public int pkId; // required
  public int userId; // required
  public int userScore; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PK_ID((short)1, "pkId"),
    USER_ID((short)2, "userId"),
    USER_SCORE((short)3, "userScore");

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
        case 1: // PK_ID
          return PK_ID;
        case 2: // USER_ID
          return USER_ID;
        case 3: // USER_SCORE
          return USER_SCORE;
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
  private static final int __PKID_ISSET_ID = 0;
  private static final int __USERID_ISSET_ID = 1;
  private static final int __USERSCORE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PK_ID, new org.apache.thrift.meta_data.FieldMetaData("pkId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USER_SCORE, new org.apache.thrift.meta_data.FieldMetaData("userScore", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MascotUserInfo.class, metaDataMap);
  }

  public MascotUserInfo() {
  }

  public MascotUserInfo(
    int pkId,
    int userId,
    int userScore)
  {
    this();
    this.pkId = pkId;
    setPkIdIsSet(true);
    this.userId = userId;
    setUserIdIsSet(true);
    this.userScore = userScore;
    setUserScoreIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MascotUserInfo(MascotUserInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pkId = other.pkId;
    this.userId = other.userId;
    this.userScore = other.userScore;
  }

  public MascotUserInfo deepCopy() {
    return new MascotUserInfo(this);
  }

  @Override
  public void clear() {
    setPkIdIsSet(false);
    this.pkId = 0;
    setUserIdIsSet(false);
    this.userId = 0;
    setUserScoreIsSet(false);
    this.userScore = 0;
  }

  public int getPkId() {
    return this.pkId;
  }

  public MascotUserInfo setPkId(int pkId) {
    this.pkId = pkId;
    setPkIdIsSet(true);
    return this;
  }

  public void unsetPkId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PKID_ISSET_ID);
  }

  /** Returns true if field pkId is set (has been assigned a value) and false otherwise */
  public boolean isSetPkId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PKID_ISSET_ID);
  }

  public void setPkIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PKID_ISSET_ID, value);
  }

  public int getUserId() {
    return this.userId;
  }

  public MascotUserInfo setUserId(int userId) {
    this.userId = userId;
    setUserIdIsSet(true);
    return this;
  }

  public void unsetUserId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  /** Returns true if field userId is set (has been assigned a value) and false otherwise */
  public boolean isSetUserId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __USERID_ISSET_ID);
  }

  public void setUserIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __USERID_ISSET_ID, value);
  }

  public int getUserScore() {
    return this.userScore;
  }

  public MascotUserInfo setUserScore(int userScore) {
    this.userScore = userScore;
    setUserScoreIsSet(true);
    return this;
  }

  public void unsetUserScore() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __USERSCORE_ISSET_ID);
  }

  /** Returns true if field userScore is set (has been assigned a value) and false otherwise */
  public boolean isSetUserScore() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __USERSCORE_ISSET_ID);
  }

  public void setUserScoreIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __USERSCORE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case PK_ID:
      if (value == null) {
        unsetPkId();
      } else {
        setPkId((java.lang.Integer)value);
      }
      break;

    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((java.lang.Integer)value);
      }
      break;

    case USER_SCORE:
      if (value == null) {
        unsetUserScore();
      } else {
        setUserScore((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PK_ID:
      return getPkId();

    case USER_ID:
      return getUserId();

    case USER_SCORE:
      return getUserScore();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PK_ID:
      return isSetPkId();
    case USER_ID:
      return isSetUserId();
    case USER_SCORE:
      return isSetUserScore();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof MascotUserInfo)
      return this.equals((MascotUserInfo)that);
    return false;
  }

  public boolean equals(MascotUserInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_pkId = true;
    boolean that_present_pkId = true;
    if (this_present_pkId || that_present_pkId) {
      if (!(this_present_pkId && that_present_pkId))
        return false;
      if (this.pkId != that.pkId)
        return false;
    }

    boolean this_present_userId = true;
    boolean that_present_userId = true;
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (this.userId != that.userId)
        return false;
    }

    boolean this_present_userScore = true;
    boolean that_present_userScore = true;
    if (this_present_userScore || that_present_userScore) {
      if (!(this_present_userScore && that_present_userScore))
        return false;
      if (this.userScore != that.userScore)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + pkId;

    hashCode = hashCode * 8191 + userId;

    hashCode = hashCode * 8191 + userScore;

    return hashCode;
  }

  @Override
  public int compareTo(MascotUserInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPkId()).compareTo(other.isSetPkId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPkId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pkId, other.pkId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUserScore()).compareTo(other.isSetUserScore());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserScore()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userScore, other.userScore);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("MascotUserInfo(");
    boolean first = true;

    sb.append("pkId:");
    sb.append(this.pkId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("userId:");
    sb.append(this.userId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("userScore:");
    sb.append(this.userScore);
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

  private static class MascotUserInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MascotUserInfoStandardScheme getScheme() {
      return new MascotUserInfoStandardScheme();
    }
  }

  private static class MascotUserInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<MascotUserInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MascotUserInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PK_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pkId = iprot.readI32();
              struct.setPkIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userId = iprot.readI32();
              struct.setUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // USER_SCORE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.userScore = iprot.readI32();
              struct.setUserScoreIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MascotUserInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PK_ID_FIELD_DESC);
      oprot.writeI32(struct.pkId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(USER_ID_FIELD_DESC);
      oprot.writeI32(struct.userId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(USER_SCORE_FIELD_DESC);
      oprot.writeI32(struct.userScore);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MascotUserInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MascotUserInfoTupleScheme getScheme() {
      return new MascotUserInfoTupleScheme();
    }
  }

  private static class MascotUserInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<MascotUserInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MascotUserInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPkId()) {
        optionals.set(0);
      }
      if (struct.isSetUserId()) {
        optionals.set(1);
      }
      if (struct.isSetUserScore()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetPkId()) {
        oprot.writeI32(struct.pkId);
      }
      if (struct.isSetUserId()) {
        oprot.writeI32(struct.userId);
      }
      if (struct.isSetUserScore()) {
        oprot.writeI32(struct.userScore);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MascotUserInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.pkId = iprot.readI32();
        struct.setPkIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.userId = iprot.readI32();
        struct.setUserIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.userScore = iprot.readI32();
        struct.setUserScoreIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
