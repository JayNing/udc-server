/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-29")
public class UserSessionInfo implements org.apache.thrift.TBase<UserSessionInfo, UserSessionInfo._Fields>, java.io.Serializable, Cloneable, Comparable<UserSessionInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserSessionInfo");

  private static final org.apache.thrift.protocol.TField RESPONSE_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("responseCode", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField USER_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("userInfo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField BASIC_FIELD_DESC = new org.apache.thrift.protocol.TField("basic", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField CURRENT_TOKEN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("currentTokenId", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new UserSessionInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new UserSessionInfoTupleSchemeFactory();

  /**
   * 
   * @see com.thrift.common.define.ResponseCode
   */
  public com.thrift.common.define.ResponseCode responseCode; // required
  public UserInfo userInfo; // required
  public java.util.List<UserSessionBasic> basic; // required
  public java.lang.String currentTokenId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see com.thrift.common.define.ResponseCode
     */
    RESPONSE_CODE((short)1, "responseCode"),
    USER_INFO((short)2, "userInfo"),
    BASIC((short)3, "basic"),
    CURRENT_TOKEN_ID((short)4, "currentTokenId");

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
        case 1: // RESPONSE_CODE
          return RESPONSE_CODE;
        case 2: // USER_INFO
          return USER_INFO;
        case 3: // BASIC
          return BASIC;
        case 4: // CURRENT_TOKEN_ID
          return CURRENT_TOKEN_ID;
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
  private static final _Fields optionals[] = {_Fields.CURRENT_TOKEN_ID};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RESPONSE_CODE, new org.apache.thrift.meta_data.FieldMetaData("responseCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.thrift.common.define.ResponseCode.class)));
    tmpMap.put(_Fields.USER_INFO, new org.apache.thrift.meta_data.FieldMetaData("userInfo", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, UserInfo.class)));
    tmpMap.put(_Fields.BASIC, new org.apache.thrift.meta_data.FieldMetaData("basic", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, UserSessionBasic.class))));
    tmpMap.put(_Fields.CURRENT_TOKEN_ID, new org.apache.thrift.meta_data.FieldMetaData("currentTokenId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserSessionInfo.class, metaDataMap);
  }

  public UserSessionInfo() {
  }

  public UserSessionInfo(
    com.thrift.common.define.ResponseCode responseCode,
    UserInfo userInfo,
    java.util.List<UserSessionBasic> basic)
  {
    this();
    this.responseCode = responseCode;
    this.userInfo = userInfo;
    this.basic = basic;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserSessionInfo(UserSessionInfo other) {
    if (other.isSetResponseCode()) {
      this.responseCode = other.responseCode;
    }
    if (other.isSetUserInfo()) {
      this.userInfo = new UserInfo(other.userInfo);
    }
    if (other.isSetBasic()) {
      java.util.List<UserSessionBasic> __this__basic = new java.util.ArrayList<UserSessionBasic>(other.basic.size());
      for (UserSessionBasic other_element : other.basic) {
        __this__basic.add(new UserSessionBasic(other_element));
      }
      this.basic = __this__basic;
    }
    if (other.isSetCurrentTokenId()) {
      this.currentTokenId = other.currentTokenId;
    }
  }

  public UserSessionInfo deepCopy() {
    return new UserSessionInfo(this);
  }

  @Override
  public void clear() {
    this.responseCode = null;
    this.userInfo = null;
    this.basic = null;
    this.currentTokenId = null;
  }

  /**
   * 
   * @see com.thrift.common.define.ResponseCode
   */
  public com.thrift.common.define.ResponseCode getResponseCode() {
    return this.responseCode;
  }

  /**
   * 
   * @see com.thrift.common.define.ResponseCode
   */
  public UserSessionInfo setResponseCode(com.thrift.common.define.ResponseCode responseCode) {
    this.responseCode = responseCode;
    return this;
  }

  public void unsetResponseCode() {
    this.responseCode = null;
  }

  /** Returns true if field responseCode is set (has been assigned a value) and false otherwise */
  public boolean isSetResponseCode() {
    return this.responseCode != null;
  }

  public void setResponseCodeIsSet(boolean value) {
    if (!value) {
      this.responseCode = null;
    }
  }

  public UserInfo getUserInfo() {
    return this.userInfo;
  }

  public UserSessionInfo setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
    return this;
  }

  public void unsetUserInfo() {
    this.userInfo = null;
  }

  /** Returns true if field userInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetUserInfo() {
    return this.userInfo != null;
  }

  public void setUserInfoIsSet(boolean value) {
    if (!value) {
      this.userInfo = null;
    }
  }

  public int getBasicSize() {
    return (this.basic == null) ? 0 : this.basic.size();
  }

  public java.util.Iterator<UserSessionBasic> getBasicIterator() {
    return (this.basic == null) ? null : this.basic.iterator();
  }

  public void addToBasic(UserSessionBasic elem) {
    if (this.basic == null) {
      this.basic = new java.util.ArrayList<UserSessionBasic>();
    }
    this.basic.add(elem);
  }

  public java.util.List<UserSessionBasic> getBasic() {
    return this.basic;
  }

  public UserSessionInfo setBasic(java.util.List<UserSessionBasic> basic) {
    this.basic = basic;
    return this;
  }

  public void unsetBasic() {
    this.basic = null;
  }

  /** Returns true if field basic is set (has been assigned a value) and false otherwise */
  public boolean isSetBasic() {
    return this.basic != null;
  }

  public void setBasicIsSet(boolean value) {
    if (!value) {
      this.basic = null;
    }
  }

  public java.lang.String getCurrentTokenId() {
    return this.currentTokenId;
  }

  public UserSessionInfo setCurrentTokenId(java.lang.String currentTokenId) {
    this.currentTokenId = currentTokenId;
    return this;
  }

  public void unsetCurrentTokenId() {
    this.currentTokenId = null;
  }

  /** Returns true if field currentTokenId is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrentTokenId() {
    return this.currentTokenId != null;
  }

  public void setCurrentTokenIdIsSet(boolean value) {
    if (!value) {
      this.currentTokenId = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case RESPONSE_CODE:
      if (value == null) {
        unsetResponseCode();
      } else {
        setResponseCode((com.thrift.common.define.ResponseCode)value);
      }
      break;

    case USER_INFO:
      if (value == null) {
        unsetUserInfo();
      } else {
        setUserInfo((UserInfo)value);
      }
      break;

    case BASIC:
      if (value == null) {
        unsetBasic();
      } else {
        setBasic((java.util.List<UserSessionBasic>)value);
      }
      break;

    case CURRENT_TOKEN_ID:
      if (value == null) {
        unsetCurrentTokenId();
      } else {
        setCurrentTokenId((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case RESPONSE_CODE:
      return getResponseCode();

    case USER_INFO:
      return getUserInfo();

    case BASIC:
      return getBasic();

    case CURRENT_TOKEN_ID:
      return getCurrentTokenId();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case RESPONSE_CODE:
      return isSetResponseCode();
    case USER_INFO:
      return isSetUserInfo();
    case BASIC:
      return isSetBasic();
    case CURRENT_TOKEN_ID:
      return isSetCurrentTokenId();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof UserSessionInfo)
      return this.equals((UserSessionInfo)that);
    return false;
  }

  public boolean equals(UserSessionInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_responseCode = true && this.isSetResponseCode();
    boolean that_present_responseCode = true && that.isSetResponseCode();
    if (this_present_responseCode || that_present_responseCode) {
      if (!(this_present_responseCode && that_present_responseCode))
        return false;
      if (!this.responseCode.equals(that.responseCode))
        return false;
    }

    boolean this_present_userInfo = true && this.isSetUserInfo();
    boolean that_present_userInfo = true && that.isSetUserInfo();
    if (this_present_userInfo || that_present_userInfo) {
      if (!(this_present_userInfo && that_present_userInfo))
        return false;
      if (!this.userInfo.equals(that.userInfo))
        return false;
    }

    boolean this_present_basic = true && this.isSetBasic();
    boolean that_present_basic = true && that.isSetBasic();
    if (this_present_basic || that_present_basic) {
      if (!(this_present_basic && that_present_basic))
        return false;
      if (!this.basic.equals(that.basic))
        return false;
    }

    boolean this_present_currentTokenId = true && this.isSetCurrentTokenId();
    boolean that_present_currentTokenId = true && that.isSetCurrentTokenId();
    if (this_present_currentTokenId || that_present_currentTokenId) {
      if (!(this_present_currentTokenId && that_present_currentTokenId))
        return false;
      if (!this.currentTokenId.equals(that.currentTokenId))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetResponseCode()) ? 131071 : 524287);
    if (isSetResponseCode())
      hashCode = hashCode * 8191 + responseCode.getValue();

    hashCode = hashCode * 8191 + ((isSetUserInfo()) ? 131071 : 524287);
    if (isSetUserInfo())
      hashCode = hashCode * 8191 + userInfo.hashCode();

    hashCode = hashCode * 8191 + ((isSetBasic()) ? 131071 : 524287);
    if (isSetBasic())
      hashCode = hashCode * 8191 + basic.hashCode();

    hashCode = hashCode * 8191 + ((isSetCurrentTokenId()) ? 131071 : 524287);
    if (isSetCurrentTokenId())
      hashCode = hashCode * 8191 + currentTokenId.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(UserSessionInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetResponseCode()).compareTo(other.isSetResponseCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResponseCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.responseCode, other.responseCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUserInfo()).compareTo(other.isSetUserInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userInfo, other.userInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetBasic()).compareTo(other.isSetBasic());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBasic()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.basic, other.basic);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetCurrentTokenId()).compareTo(other.isSetCurrentTokenId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrentTokenId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currentTokenId, other.currentTokenId);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("UserSessionInfo(");
    boolean first = true;

    sb.append("responseCode:");
    if (this.responseCode == null) {
      sb.append("null");
    } else {
      sb.append(this.responseCode);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("userInfo:");
    if (this.userInfo == null) {
      sb.append("null");
    } else {
      sb.append(this.userInfo);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("basic:");
    if (this.basic == null) {
      sb.append("null");
    } else {
      sb.append(this.basic);
    }
    first = false;
    if (isSetCurrentTokenId()) {
      if (!first) sb.append(", ");
      sb.append("currentTokenId:");
      if (this.currentTokenId == null) {
        sb.append("null");
      } else {
        sb.append(this.currentTokenId);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (userInfo != null) {
      userInfo.validate();
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class UserSessionInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserSessionInfoStandardScheme getScheme() {
      return new UserSessionInfoStandardScheme();
    }
  }

  private static class UserSessionInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<UserSessionInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserSessionInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RESPONSE_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.responseCode = com.thrift.common.define.ResponseCode.findByValue(iprot.readI32());
              struct.setResponseCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // USER_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.userInfo = new UserInfo();
              struct.userInfo.read(iprot);
              struct.setUserInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BASIC
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.basic = new java.util.ArrayList<UserSessionBasic>(_list8.size);
                UserSessionBasic _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = new UserSessionBasic();
                  _elem9.read(iprot);
                  struct.basic.add(_elem9);
                }
                iprot.readListEnd();
              }
              struct.setBasicIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CURRENT_TOKEN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.currentTokenId = iprot.readString();
              struct.setCurrentTokenIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserSessionInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.responseCode != null) {
        oprot.writeFieldBegin(RESPONSE_CODE_FIELD_DESC);
        oprot.writeI32(struct.responseCode.getValue());
        oprot.writeFieldEnd();
      }
      if (struct.userInfo != null) {
        oprot.writeFieldBegin(USER_INFO_FIELD_DESC);
        struct.userInfo.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.basic != null) {
        oprot.writeFieldBegin(BASIC_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.basic.size()));
          for (UserSessionBasic _iter11 : struct.basic)
          {
            _iter11.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.currentTokenId != null) {
        if (struct.isSetCurrentTokenId()) {
          oprot.writeFieldBegin(CURRENT_TOKEN_ID_FIELD_DESC);
          oprot.writeString(struct.currentTokenId);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserSessionInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public UserSessionInfoTupleScheme getScheme() {
      return new UserSessionInfoTupleScheme();
    }
  }

  private static class UserSessionInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<UserSessionInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserSessionInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetResponseCode()) {
        optionals.set(0);
      }
      if (struct.isSetUserInfo()) {
        optionals.set(1);
      }
      if (struct.isSetBasic()) {
        optionals.set(2);
      }
      if (struct.isSetCurrentTokenId()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetResponseCode()) {
        oprot.writeI32(struct.responseCode.getValue());
      }
      if (struct.isSetUserInfo()) {
        struct.userInfo.write(oprot);
      }
      if (struct.isSetBasic()) {
        {
          oprot.writeI32(struct.basic.size());
          for (UserSessionBasic _iter12 : struct.basic)
          {
            _iter12.write(oprot);
          }
        }
      }
      if (struct.isSetCurrentTokenId()) {
        oprot.writeString(struct.currentTokenId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserSessionInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.responseCode = com.thrift.common.define.ResponseCode.findByValue(iprot.readI32());
        struct.setResponseCodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.userInfo = new UserInfo();
        struct.userInfo.read(iprot);
        struct.setUserInfoIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.basic = new java.util.ArrayList<UserSessionBasic>(_list13.size);
          UserSessionBasic _elem14;
          for (int _i15 = 0; _i15 < _list13.size; ++_i15)
          {
            _elem14 = new UserSessionBasic();
            _elem14.read(iprot);
            struct.basic.add(_elem14);
          }
        }
        struct.setBasicIsSet(true);
      }
      if (incoming.get(3)) {
        struct.currentTokenId = iprot.readString();
        struct.setCurrentTokenIdIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
