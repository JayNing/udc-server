/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-06-02")
public class MessageInfo implements org.apache.thrift.TBase<MessageInfo, MessageInfo._Fields>, java.io.Serializable, Cloneable, Comparable<MessageInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MessageInfo");

  private static final org.apache.thrift.protocol.TField MESSAGE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("messageId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField MESSAGE_CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("messageContent", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MESSAGE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("messageType", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField MESSAGE_FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("messageFlag", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MessageInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MessageInfoTupleSchemeFactory();

  public int messageId; // required
  public java.lang.String messageContent; // required
  public int messageType; // required
  public long createTime; // required
  public int messageFlag; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MESSAGE_ID((short)1, "messageId"),
    MESSAGE_CONTENT((short)2, "messageContent"),
    MESSAGE_TYPE((short)3, "messageType"),
    CREATE_TIME((short)4, "createTime"),
    MESSAGE_FLAG((short)5, "messageFlag");

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
        case 1: // MESSAGE_ID
          return MESSAGE_ID;
        case 2: // MESSAGE_CONTENT
          return MESSAGE_CONTENT;
        case 3: // MESSAGE_TYPE
          return MESSAGE_TYPE;
        case 4: // CREATE_TIME
          return CREATE_TIME;
        case 5: // MESSAGE_FLAG
          return MESSAGE_FLAG;
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
  private static final int __MESSAGEID_ISSET_ID = 0;
  private static final int __MESSAGETYPE_ISSET_ID = 1;
  private static final int __CREATETIME_ISSET_ID = 2;
  private static final int __MESSAGEFLAG_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MESSAGE_ID, new org.apache.thrift.meta_data.FieldMetaData("messageId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MESSAGE_CONTENT, new org.apache.thrift.meta_data.FieldMetaData("messageContent", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MESSAGE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("messageType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.MESSAGE_FLAG, new org.apache.thrift.meta_data.FieldMetaData("messageFlag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MessageInfo.class, metaDataMap);
  }

  public MessageInfo() {
  }

  public MessageInfo(
    int messageId,
    java.lang.String messageContent,
    int messageType,
    long createTime,
    int messageFlag)
  {
    this();
    this.messageId = messageId;
    setMessageIdIsSet(true);
    this.messageContent = messageContent;
    this.messageType = messageType;
    setMessageTypeIsSet(true);
    this.createTime = createTime;
    setCreateTimeIsSet(true);
    this.messageFlag = messageFlag;
    setMessageFlagIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MessageInfo(MessageInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.messageId = other.messageId;
    if (other.isSetMessageContent()) {
      this.messageContent = other.messageContent;
    }
    this.messageType = other.messageType;
    this.createTime = other.createTime;
    this.messageFlag = other.messageFlag;
  }

  public MessageInfo deepCopy() {
    return new MessageInfo(this);
  }

  @Override
  public void clear() {
    setMessageIdIsSet(false);
    this.messageId = 0;
    this.messageContent = null;
    setMessageTypeIsSet(false);
    this.messageType = 0;
    setCreateTimeIsSet(false);
    this.createTime = 0;
    setMessageFlagIsSet(false);
    this.messageFlag = 0;
  }

  public int getMessageId() {
    return this.messageId;
  }

  public MessageInfo setMessageId(int messageId) {
    this.messageId = messageId;
    setMessageIdIsSet(true);
    return this;
  }

  public void unsetMessageId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __MESSAGEID_ISSET_ID);
  }

  /** Returns true if field messageId is set (has been assigned a value) and false otherwise */
  public boolean isSetMessageId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __MESSAGEID_ISSET_ID);
  }

  public void setMessageIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __MESSAGEID_ISSET_ID, value);
  }

  public java.lang.String getMessageContent() {
    return this.messageContent;
  }

  public MessageInfo setMessageContent(java.lang.String messageContent) {
    this.messageContent = messageContent;
    return this;
  }

  public void unsetMessageContent() {
    this.messageContent = null;
  }

  /** Returns true if field messageContent is set (has been assigned a value) and false otherwise */
  public boolean isSetMessageContent() {
    return this.messageContent != null;
  }

  public void setMessageContentIsSet(boolean value) {
    if (!value) {
      this.messageContent = null;
    }
  }

  public int getMessageType() {
    return this.messageType;
  }

  public MessageInfo setMessageType(int messageType) {
    this.messageType = messageType;
    setMessageTypeIsSet(true);
    return this;
  }

  public void unsetMessageType() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __MESSAGETYPE_ISSET_ID);
  }

  /** Returns true if field messageType is set (has been assigned a value) and false otherwise */
  public boolean isSetMessageType() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __MESSAGETYPE_ISSET_ID);
  }

  public void setMessageTypeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __MESSAGETYPE_ISSET_ID, value);
  }

  public long getCreateTime() {
    return this.createTime;
  }

  public MessageInfo setCreateTime(long createTime) {
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

  public int getMessageFlag() {
    return this.messageFlag;
  }

  public MessageInfo setMessageFlag(int messageFlag) {
    this.messageFlag = messageFlag;
    setMessageFlagIsSet(true);
    return this;
  }

  public void unsetMessageFlag() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __MESSAGEFLAG_ISSET_ID);
  }

  /** Returns true if field messageFlag is set (has been assigned a value) and false otherwise */
  public boolean isSetMessageFlag() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __MESSAGEFLAG_ISSET_ID);
  }

  public void setMessageFlagIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __MESSAGEFLAG_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case MESSAGE_ID:
      if (value == null) {
        unsetMessageId();
      } else {
        setMessageId((java.lang.Integer)value);
      }
      break;

    case MESSAGE_CONTENT:
      if (value == null) {
        unsetMessageContent();
      } else {
        setMessageContent((java.lang.String)value);
      }
      break;

    case MESSAGE_TYPE:
      if (value == null) {
        unsetMessageType();
      } else {
        setMessageType((java.lang.Integer)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((java.lang.Long)value);
      }
      break;

    case MESSAGE_FLAG:
      if (value == null) {
        unsetMessageFlag();
      } else {
        setMessageFlag((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case MESSAGE_ID:
      return getMessageId();

    case MESSAGE_CONTENT:
      return getMessageContent();

    case MESSAGE_TYPE:
      return getMessageType();

    case CREATE_TIME:
      return getCreateTime();

    case MESSAGE_FLAG:
      return getMessageFlag();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case MESSAGE_ID:
      return isSetMessageId();
    case MESSAGE_CONTENT:
      return isSetMessageContent();
    case MESSAGE_TYPE:
      return isSetMessageType();
    case CREATE_TIME:
      return isSetCreateTime();
    case MESSAGE_FLAG:
      return isSetMessageFlag();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof MessageInfo)
      return this.equals((MessageInfo)that);
    return false;
  }

  public boolean equals(MessageInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_messageId = true;
    boolean that_present_messageId = true;
    if (this_present_messageId || that_present_messageId) {
      if (!(this_present_messageId && that_present_messageId))
        return false;
      if (this.messageId != that.messageId)
        return false;
    }

    boolean this_present_messageContent = true && this.isSetMessageContent();
    boolean that_present_messageContent = true && that.isSetMessageContent();
    if (this_present_messageContent || that_present_messageContent) {
      if (!(this_present_messageContent && that_present_messageContent))
        return false;
      if (!this.messageContent.equals(that.messageContent))
        return false;
    }

    boolean this_present_messageType = true;
    boolean that_present_messageType = true;
    if (this_present_messageType || that_present_messageType) {
      if (!(this_present_messageType && that_present_messageType))
        return false;
      if (this.messageType != that.messageType)
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

    boolean this_present_messageFlag = true;
    boolean that_present_messageFlag = true;
    if (this_present_messageFlag || that_present_messageFlag) {
      if (!(this_present_messageFlag && that_present_messageFlag))
        return false;
      if (this.messageFlag != that.messageFlag)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + messageId;

    hashCode = hashCode * 8191 + ((isSetMessageContent()) ? 131071 : 524287);
    if (isSetMessageContent())
      hashCode = hashCode * 8191 + messageContent.hashCode();

    hashCode = hashCode * 8191 + messageType;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(createTime);

    hashCode = hashCode * 8191 + messageFlag;

    return hashCode;
  }

  @Override
  public int compareTo(MessageInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetMessageId()).compareTo(other.isSetMessageId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessageId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messageId, other.messageId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMessageContent()).compareTo(other.isSetMessageContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessageContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messageContent, other.messageContent);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMessageType()).compareTo(other.isSetMessageType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessageType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messageType, other.messageType);
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
    lastComparison = java.lang.Boolean.valueOf(isSetMessageFlag()).compareTo(other.isSetMessageFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessageFlag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.messageFlag, other.messageFlag);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("MessageInfo(");
    boolean first = true;

    sb.append("messageId:");
    sb.append(this.messageId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("messageContent:");
    if (this.messageContent == null) {
      sb.append("null");
    } else {
      sb.append(this.messageContent);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("messageType:");
    sb.append(this.messageType);
    first = false;
    if (!first) sb.append(", ");
    sb.append("createTime:");
    sb.append(this.createTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("messageFlag:");
    sb.append(this.messageFlag);
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

  private static class MessageInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MessageInfoStandardScheme getScheme() {
      return new MessageInfoStandardScheme();
    }
  }

  private static class MessageInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<MessageInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MessageInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MESSAGE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.messageId = iprot.readI32();
              struct.setMessageIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MESSAGE_CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.messageContent = iprot.readString();
              struct.setMessageContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MESSAGE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.messageType = iprot.readI32();
              struct.setMessageTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTime = iprot.readI64();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MESSAGE_FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.messageFlag = iprot.readI32();
              struct.setMessageFlagIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MessageInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(MESSAGE_ID_FIELD_DESC);
      oprot.writeI32(struct.messageId);
      oprot.writeFieldEnd();
      if (struct.messageContent != null) {
        oprot.writeFieldBegin(MESSAGE_CONTENT_FIELD_DESC);
        oprot.writeString(struct.messageContent);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(MESSAGE_TYPE_FIELD_DESC);
      oprot.writeI32(struct.messageType);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
      oprot.writeI64(struct.createTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MESSAGE_FLAG_FIELD_DESC);
      oprot.writeI32(struct.messageFlag);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MessageInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MessageInfoTupleScheme getScheme() {
      return new MessageInfoTupleScheme();
    }
  }

  private static class MessageInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<MessageInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MessageInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetMessageId()) {
        optionals.set(0);
      }
      if (struct.isSetMessageContent()) {
        optionals.set(1);
      }
      if (struct.isSetMessageType()) {
        optionals.set(2);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(3);
      }
      if (struct.isSetMessageFlag()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetMessageId()) {
        oprot.writeI32(struct.messageId);
      }
      if (struct.isSetMessageContent()) {
        oprot.writeString(struct.messageContent);
      }
      if (struct.isSetMessageType()) {
        oprot.writeI32(struct.messageType);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeI64(struct.createTime);
      }
      if (struct.isSetMessageFlag()) {
        oprot.writeI32(struct.messageFlag);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MessageInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.messageId = iprot.readI32();
        struct.setMessageIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.messageContent = iprot.readString();
        struct.setMessageContentIsSet(true);
      }
      if (incoming.get(2)) {
        struct.messageType = iprot.readI32();
        struct.setMessageTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.createTime = iprot.readI64();
        struct.setCreateTimeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.messageFlag = iprot.readI32();
        struct.setMessageFlagIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
