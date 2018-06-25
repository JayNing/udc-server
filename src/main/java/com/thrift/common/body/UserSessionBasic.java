/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-29")
public class UserSessionBasic implements org.apache.thrift.TBase<UserSessionBasic, UserSessionBasic._Fields>, java.io.Serializable, Cloneable, Comparable<UserSessionBasic> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserSessionBasic");

  private static final org.apache.thrift.protocol.TField TOKEN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tokenId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PLATFORM_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("platformType", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField APP_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("appType", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField LOGIN_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("loginTime", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField LAST_OPERATION_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("lastOperationTime", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("flag", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new UserSessionBasicStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new UserSessionBasicTupleSchemeFactory();

  public java.lang.String tokenId; // required
  /**
   * 
   * @see com.thrift.common.define.PlatformType
   */
  public com.thrift.common.define.PlatformType platformType; // required
  /**
   * 
   * @see com.thrift.common.define.AppType
   */
  public com.thrift.common.define.AppType appType; // required
  public long loginTime; // required
  public long lastOperationTime; // required
  public int flag; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOKEN_ID((short)1, "tokenId"),
    /**
     * 
     * @see com.thrift.common.define.PlatformType
     */
    PLATFORM_TYPE((short)2, "platformType"),
    /**
     * 
     * @see com.thrift.common.define.AppType
     */
    APP_TYPE((short)3, "appType"),
    LOGIN_TIME((short)4, "loginTime"),
    LAST_OPERATION_TIME((short)5, "lastOperationTime"),
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
        case 1: // TOKEN_ID
          return TOKEN_ID;
        case 2: // PLATFORM_TYPE
          return PLATFORM_TYPE;
        case 3: // APP_TYPE
          return APP_TYPE;
        case 4: // LOGIN_TIME
          return LOGIN_TIME;
        case 5: // LAST_OPERATION_TIME
          return LAST_OPERATION_TIME;
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
  private static final int __LOGINTIME_ISSET_ID = 0;
  private static final int __LASTOPERATIONTIME_ISSET_ID = 1;
  private static final int __FLAG_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOKEN_ID, new org.apache.thrift.meta_data.FieldMetaData("tokenId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PLATFORM_TYPE, new org.apache.thrift.meta_data.FieldMetaData("platformType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.thrift.common.define.PlatformType.class)));
    tmpMap.put(_Fields.APP_TYPE, new org.apache.thrift.meta_data.FieldMetaData("appType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.thrift.common.define.AppType.class)));
    tmpMap.put(_Fields.LOGIN_TIME, new org.apache.thrift.meta_data.FieldMetaData("loginTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LAST_OPERATION_TIME, new org.apache.thrift.meta_data.FieldMetaData("lastOperationTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.FLAG, new org.apache.thrift.meta_data.FieldMetaData("flag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserSessionBasic.class, metaDataMap);
  }

  public UserSessionBasic() {
  }

  public UserSessionBasic(
    java.lang.String tokenId,
    com.thrift.common.define.PlatformType platformType,
    com.thrift.common.define.AppType appType,
    long loginTime,
    long lastOperationTime,
    int flag)
  {
    this();
    this.tokenId = tokenId;
    this.platformType = platformType;
    this.appType = appType;
    this.loginTime = loginTime;
    setLoginTimeIsSet(true);
    this.lastOperationTime = lastOperationTime;
    setLastOperationTimeIsSet(true);
    this.flag = flag;
    setFlagIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserSessionBasic(UserSessionBasic other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTokenId()) {
      this.tokenId = other.tokenId;
    }
    if (other.isSetPlatformType()) {
      this.platformType = other.platformType;
    }
    if (other.isSetAppType()) {
      this.appType = other.appType;
    }
    this.loginTime = other.loginTime;
    this.lastOperationTime = other.lastOperationTime;
    this.flag = other.flag;
  }

  public UserSessionBasic deepCopy() {
    return new UserSessionBasic(this);
  }

  @Override
  public void clear() {
    this.tokenId = null;
    this.platformType = null;
    this.appType = null;
    setLoginTimeIsSet(false);
    this.loginTime = 0;
    setLastOperationTimeIsSet(false);
    this.lastOperationTime = 0;
    setFlagIsSet(false);
    this.flag = 0;
  }

  public java.lang.String getTokenId() {
    return this.tokenId;
  }

  public UserSessionBasic setTokenId(java.lang.String tokenId) {
    this.tokenId = tokenId;
    return this;
  }

  public void unsetTokenId() {
    this.tokenId = null;
  }

  /** Returns true if field tokenId is set (has been assigned a value) and false otherwise */
  public boolean isSetTokenId() {
    return this.tokenId != null;
  }

  public void setTokenIdIsSet(boolean value) {
    if (!value) {
      this.tokenId = null;
    }
  }

  /**
   * 
   * @see com.thrift.common.define.PlatformType
   */
  public com.thrift.common.define.PlatformType getPlatformType() {
    return this.platformType;
  }

  /**
   * 
   * @see com.thrift.common.define.PlatformType
   */
  public UserSessionBasic setPlatformType(com.thrift.common.define.PlatformType platformType) {
    this.platformType = platformType;
    return this;
  }

  public void unsetPlatformType() {
    this.platformType = null;
  }

  /** Returns true if field platformType is set (has been assigned a value) and false otherwise */
  public boolean isSetPlatformType() {
    return this.platformType != null;
  }

  public void setPlatformTypeIsSet(boolean value) {
    if (!value) {
      this.platformType = null;
    }
  }

  /**
   * 
   * @see com.thrift.common.define.AppType
   */
  public com.thrift.common.define.AppType getAppType() {
    return this.appType;
  }

  /**
   * 
   * @see com.thrift.common.define.AppType
   */
  public UserSessionBasic setAppType(com.thrift.common.define.AppType appType) {
    this.appType = appType;
    return this;
  }

  public void unsetAppType() {
    this.appType = null;
  }

  /** Returns true if field appType is set (has been assigned a value) and false otherwise */
  public boolean isSetAppType() {
    return this.appType != null;
  }

  public void setAppTypeIsSet(boolean value) {
    if (!value) {
      this.appType = null;
    }
  }

  public long getLoginTime() {
    return this.loginTime;
  }

  public UserSessionBasic setLoginTime(long loginTime) {
    this.loginTime = loginTime;
    setLoginTimeIsSet(true);
    return this;
  }

  public void unsetLoginTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __LOGINTIME_ISSET_ID);
  }

  /** Returns true if field loginTime is set (has been assigned a value) and false otherwise */
  public boolean isSetLoginTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __LOGINTIME_ISSET_ID);
  }

  public void setLoginTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __LOGINTIME_ISSET_ID, value);
  }

  public long getLastOperationTime() {
    return this.lastOperationTime;
  }

  public UserSessionBasic setLastOperationTime(long lastOperationTime) {
    this.lastOperationTime = lastOperationTime;
    setLastOperationTimeIsSet(true);
    return this;
  }

  public void unsetLastOperationTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __LASTOPERATIONTIME_ISSET_ID);
  }

  /** Returns true if field lastOperationTime is set (has been assigned a value) and false otherwise */
  public boolean isSetLastOperationTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __LASTOPERATIONTIME_ISSET_ID);
  }

  public void setLastOperationTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __LASTOPERATIONTIME_ISSET_ID, value);
  }

  public int getFlag() {
    return this.flag;
  }

  public UserSessionBasic setFlag(int flag) {
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
    case TOKEN_ID:
      if (value == null) {
        unsetTokenId();
      } else {
        setTokenId((java.lang.String)value);
      }
      break;

    case PLATFORM_TYPE:
      if (value == null) {
        unsetPlatformType();
      } else {
        setPlatformType((com.thrift.common.define.PlatformType)value);
      }
      break;

    case APP_TYPE:
      if (value == null) {
        unsetAppType();
      } else {
        setAppType((com.thrift.common.define.AppType)value);
      }
      break;

    case LOGIN_TIME:
      if (value == null) {
        unsetLoginTime();
      } else {
        setLoginTime((java.lang.Long)value);
      }
      break;

    case LAST_OPERATION_TIME:
      if (value == null) {
        unsetLastOperationTime();
      } else {
        setLastOperationTime((java.lang.Long)value);
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
    case TOKEN_ID:
      return getTokenId();

    case PLATFORM_TYPE:
      return getPlatformType();

    case APP_TYPE:
      return getAppType();

    case LOGIN_TIME:
      return getLoginTime();

    case LAST_OPERATION_TIME:
      return getLastOperationTime();

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
    case TOKEN_ID:
      return isSetTokenId();
    case PLATFORM_TYPE:
      return isSetPlatformType();
    case APP_TYPE:
      return isSetAppType();
    case LOGIN_TIME:
      return isSetLoginTime();
    case LAST_OPERATION_TIME:
      return isSetLastOperationTime();
    case FLAG:
      return isSetFlag();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof UserSessionBasic)
      return this.equals((UserSessionBasic)that);
    return false;
  }

  public boolean equals(UserSessionBasic that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_tokenId = true && this.isSetTokenId();
    boolean that_present_tokenId = true && that.isSetTokenId();
    if (this_present_tokenId || that_present_tokenId) {
      if (!(this_present_tokenId && that_present_tokenId))
        return false;
      if (!this.tokenId.equals(that.tokenId))
        return false;
    }

    boolean this_present_platformType = true && this.isSetPlatformType();
    boolean that_present_platformType = true && that.isSetPlatformType();
    if (this_present_platformType || that_present_platformType) {
      if (!(this_present_platformType && that_present_platformType))
        return false;
      if (!this.platformType.equals(that.platformType))
        return false;
    }

    boolean this_present_appType = true && this.isSetAppType();
    boolean that_present_appType = true && that.isSetAppType();
    if (this_present_appType || that_present_appType) {
      if (!(this_present_appType && that_present_appType))
        return false;
      if (!this.appType.equals(that.appType))
        return false;
    }

    boolean this_present_loginTime = true;
    boolean that_present_loginTime = true;
    if (this_present_loginTime || that_present_loginTime) {
      if (!(this_present_loginTime && that_present_loginTime))
        return false;
      if (this.loginTime != that.loginTime)
        return false;
    }

    boolean this_present_lastOperationTime = true;
    boolean that_present_lastOperationTime = true;
    if (this_present_lastOperationTime || that_present_lastOperationTime) {
      if (!(this_present_lastOperationTime && that_present_lastOperationTime))
        return false;
      if (this.lastOperationTime != that.lastOperationTime)
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

    hashCode = hashCode * 8191 + ((isSetTokenId()) ? 131071 : 524287);
    if (isSetTokenId())
      hashCode = hashCode * 8191 + tokenId.hashCode();

    hashCode = hashCode * 8191 + ((isSetPlatformType()) ? 131071 : 524287);
    if (isSetPlatformType())
      hashCode = hashCode * 8191 + platformType.getValue();

    hashCode = hashCode * 8191 + ((isSetAppType()) ? 131071 : 524287);
    if (isSetAppType())
      hashCode = hashCode * 8191 + appType.getValue();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(loginTime);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(lastOperationTime);

    hashCode = hashCode * 8191 + flag;

    return hashCode;
  }

  @Override
  public int compareTo(UserSessionBasic other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetTokenId()).compareTo(other.isSetTokenId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTokenId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tokenId, other.tokenId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPlatformType()).compareTo(other.isSetPlatformType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlatformType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.platformType, other.platformType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetAppType()).compareTo(other.isSetAppType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAppType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.appType, other.appType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLoginTime()).compareTo(other.isSetLoginTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoginTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.loginTime, other.loginTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLastOperationTime()).compareTo(other.isSetLastOperationTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastOperationTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastOperationTime, other.lastOperationTime);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("UserSessionBasic(");
    boolean first = true;

    sb.append("tokenId:");
    if (this.tokenId == null) {
      sb.append("null");
    } else {
      sb.append(this.tokenId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("platformType:");
    if (this.platformType == null) {
      sb.append("null");
    } else {
      sb.append(this.platformType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("appType:");
    if (this.appType == null) {
      sb.append("null");
    } else {
      sb.append(this.appType);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("loginTime:");
    sb.append(this.loginTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lastOperationTime:");
    sb.append(this.lastOperationTime);
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

  private static class UserSessionBasicStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserSessionBasicStandardScheme getScheme() {
      return new UserSessionBasicStandardScheme();
    }
  }

  private static class UserSessionBasicStandardScheme extends org.apache.thrift.scheme.StandardScheme<UserSessionBasic> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserSessionBasic struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOKEN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tokenId = iprot.readString();
              struct.setTokenIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PLATFORM_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.platformType = com.thrift.common.define.PlatformType.findByValue(iprot.readI32());
              struct.setPlatformTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // APP_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.appType = com.thrift.common.define.AppType.findByValue(iprot.readI32());
              struct.setAppTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LOGIN_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.loginTime = iprot.readI64();
              struct.setLoginTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // LAST_OPERATION_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.lastOperationTime = iprot.readI64();
              struct.setLastOperationTimeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserSessionBasic struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.tokenId != null) {
        oprot.writeFieldBegin(TOKEN_ID_FIELD_DESC);
        oprot.writeString(struct.tokenId);
        oprot.writeFieldEnd();
      }
      if (struct.platformType != null) {
        oprot.writeFieldBegin(PLATFORM_TYPE_FIELD_DESC);
        oprot.writeI32(struct.platformType.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.appType != null) {
        oprot.writeFieldBegin(APP_TYPE_FIELD_DESC);
        oprot.writeI32(struct.appType.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(LOGIN_TIME_FIELD_DESC);
      oprot.writeI64(struct.loginTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LAST_OPERATION_TIME_FIELD_DESC);
      oprot.writeI64(struct.lastOperationTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FLAG_FIELD_DESC);
      oprot.writeI32(struct.flag);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserSessionBasicTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserSessionBasicTupleScheme getScheme() {
      return new UserSessionBasicTupleScheme();
    }
  }

  private static class UserSessionBasicTupleScheme extends org.apache.thrift.scheme.TupleScheme<UserSessionBasic> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserSessionBasic struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTokenId()) {
        optionals.set(0);
      }
      if (struct.isSetPlatformType()) {
        optionals.set(1);
      }
      if (struct.isSetAppType()) {
        optionals.set(2);
      }
      if (struct.isSetLoginTime()) {
        optionals.set(3);
      }
      if (struct.isSetLastOperationTime()) {
        optionals.set(4);
      }
      if (struct.isSetFlag()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetTokenId()) {
        oprot.writeString(struct.tokenId);
      }
      if (struct.isSetPlatformType()) {
        oprot.writeI32(struct.platformType.getValue());
      }
      if (struct.isSetAppType()) {
        oprot.writeI32(struct.appType.getValue());
      }
      if (struct.isSetLoginTime()) {
        oprot.writeI64(struct.loginTime);
      }
      if (struct.isSetLastOperationTime()) {
        oprot.writeI64(struct.lastOperationTime);
      }
      if (struct.isSetFlag()) {
        oprot.writeI32(struct.flag);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserSessionBasic struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.tokenId = iprot.readString();
        struct.setTokenIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.platformType = com.thrift.common.define.PlatformType.findByValue(iprot.readI32());
        struct.setPlatformTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.appType = com.thrift.common.define.AppType.findByValue(iprot.readI32());
        struct.setAppTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.loginTime = iprot.readI64();
        struct.setLoginTimeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.lastOperationTime = iprot.readI64();
        struct.setLastOperationTimeIsSet(true);
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

