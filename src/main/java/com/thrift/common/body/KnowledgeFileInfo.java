/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-05-11")
public class KnowledgeFileInfo implements org.apache.thrift.TBase<KnowledgeFileInfo, KnowledgeFileInfo._Fields>, java.io.Serializable, Cloneable, Comparable<KnowledgeFileInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("KnowledgeFileInfo");

  private static final org.apache.thrift.protocol.TField FILE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("fileId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField FILE_URL_FIELD_DESC = new org.apache.thrift.protocol.TField("fileUrl", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField FILE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("fileType", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new KnowledgeFileInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new KnowledgeFileInfoTupleSchemeFactory();

  public int fileId; // required
  public java.lang.String fileUrl; // required
  public int fileType; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FILE_ID((short)1, "fileId"),
    FILE_URL((short)2, "fileUrl"),
    FILE_TYPE((short)3, "fileType");

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
        case 1: // FILE_ID
          return FILE_ID;
        case 2: // FILE_URL
          return FILE_URL;
        case 3: // FILE_TYPE
          return FILE_TYPE;
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
  private static final int __FILEID_ISSET_ID = 0;
  private static final int __FILETYPE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FILE_ID, new org.apache.thrift.meta_data.FieldMetaData("fileId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FILE_URL, new org.apache.thrift.meta_data.FieldMetaData("fileUrl", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FILE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("fileType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(KnowledgeFileInfo.class, metaDataMap);
  }

  public KnowledgeFileInfo() {
  }

  public KnowledgeFileInfo(
    int fileId,
    java.lang.String fileUrl,
    int fileType)
  {
    this();
    this.fileId = fileId;
    setFileIdIsSet(true);
    this.fileUrl = fileUrl;
    this.fileType = fileType;
    setFileTypeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public KnowledgeFileInfo(KnowledgeFileInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.fileId = other.fileId;
    if (other.isSetFileUrl()) {
      this.fileUrl = other.fileUrl;
    }
    this.fileType = other.fileType;
  }

  public KnowledgeFileInfo deepCopy() {
    return new KnowledgeFileInfo(this);
  }

  @Override
  public void clear() {
    setFileIdIsSet(false);
    this.fileId = 0;
    this.fileUrl = null;
    setFileTypeIsSet(false);
    this.fileType = 0;
  }

  public int getFileId() {
    return this.fileId;
  }

  public KnowledgeFileInfo setFileId(int fileId) {
    this.fileId = fileId;
    setFileIdIsSet(true);
    return this;
  }

  public void unsetFileId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __FILEID_ISSET_ID);
  }

  /** Returns true if field fileId is set (has been assigned a value) and false otherwise */
  public boolean isSetFileId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __FILEID_ISSET_ID);
  }

  public void setFileIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __FILEID_ISSET_ID, value);
  }

  public java.lang.String getFileUrl() {
    return this.fileUrl;
  }

  public KnowledgeFileInfo setFileUrl(java.lang.String fileUrl) {
    this.fileUrl = fileUrl;
    return this;
  }

  public void unsetFileUrl() {
    this.fileUrl = null;
  }

  /** Returns true if field fileUrl is set (has been assigned a value) and false otherwise */
  public boolean isSetFileUrl() {
    return this.fileUrl != null;
  }

  public void setFileUrlIsSet(boolean value) {
    if (!value) {
      this.fileUrl = null;
    }
  }

  public int getFileType() {
    return this.fileType;
  }

  public KnowledgeFileInfo setFileType(int fileType) {
    this.fileType = fileType;
    setFileTypeIsSet(true);
    return this;
  }

  public void unsetFileType() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __FILETYPE_ISSET_ID);
  }

  /** Returns true if field fileType is set (has been assigned a value) and false otherwise */
  public boolean isSetFileType() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __FILETYPE_ISSET_ID);
  }

  public void setFileTypeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __FILETYPE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case FILE_ID:
      if (value == null) {
        unsetFileId();
      } else {
        setFileId((java.lang.Integer)value);
      }
      break;

    case FILE_URL:
      if (value == null) {
        unsetFileUrl();
      } else {
        setFileUrl((java.lang.String)value);
      }
      break;

    case FILE_TYPE:
      if (value == null) {
        unsetFileType();
      } else {
        setFileType((java.lang.Integer)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FILE_ID:
      return getFileId();

    case FILE_URL:
      return getFileUrl();

    case FILE_TYPE:
      return getFileType();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FILE_ID:
      return isSetFileId();
    case FILE_URL:
      return isSetFileUrl();
    case FILE_TYPE:
      return isSetFileType();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof KnowledgeFileInfo)
      return this.equals((KnowledgeFileInfo)that);
    return false;
  }

  public boolean equals(KnowledgeFileInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_fileId = true;
    boolean that_present_fileId = true;
    if (this_present_fileId || that_present_fileId) {
      if (!(this_present_fileId && that_present_fileId))
        return false;
      if (this.fileId != that.fileId)
        return false;
    }

    boolean this_present_fileUrl = true && this.isSetFileUrl();
    boolean that_present_fileUrl = true && that.isSetFileUrl();
    if (this_present_fileUrl || that_present_fileUrl) {
      if (!(this_present_fileUrl && that_present_fileUrl))
        return false;
      if (!this.fileUrl.equals(that.fileUrl))
        return false;
    }

    boolean this_present_fileType = true;
    boolean that_present_fileType = true;
    if (this_present_fileType || that_present_fileType) {
      if (!(this_present_fileType && that_present_fileType))
        return false;
      if (this.fileType != that.fileType)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + fileId;

    hashCode = hashCode * 8191 + ((isSetFileUrl()) ? 131071 : 524287);
    if (isSetFileUrl())
      hashCode = hashCode * 8191 + fileUrl.hashCode();

    hashCode = hashCode * 8191 + fileType;

    return hashCode;
  }

  @Override
  public int compareTo(KnowledgeFileInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetFileId()).compareTo(other.isSetFileId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileId, other.fileId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetFileUrl()).compareTo(other.isSetFileUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileUrl, other.fileUrl);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetFileType()).compareTo(other.isSetFileType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileType, other.fileType);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("KnowledgeFileInfo(");
    boolean first = true;

    sb.append("fileId:");
    sb.append(this.fileId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileUrl:");
    if (this.fileUrl == null) {
      sb.append("null");
    } else {
      sb.append(this.fileUrl);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileType:");
    sb.append(this.fileType);
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

  private static class KnowledgeFileInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public KnowledgeFileInfoStandardScheme getScheme() {
      return new KnowledgeFileInfoStandardScheme();
    }
  }

  private static class KnowledgeFileInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<KnowledgeFileInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, KnowledgeFileInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.fileId = iprot.readI32();
              struct.setFileIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FILE_URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fileUrl = iprot.readString();
              struct.setFileUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FILE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.fileType = iprot.readI32();
              struct.setFileTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, KnowledgeFileInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(FILE_ID_FIELD_DESC);
      oprot.writeI32(struct.fileId);
      oprot.writeFieldEnd();
      if (struct.fileUrl != null) {
        oprot.writeFieldBegin(FILE_URL_FIELD_DESC);
        oprot.writeString(struct.fileUrl);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(FILE_TYPE_FIELD_DESC);
      oprot.writeI32(struct.fileType);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class KnowledgeFileInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public KnowledgeFileInfoTupleScheme getScheme() {
      return new KnowledgeFileInfoTupleScheme();
    }
  }

  private static class KnowledgeFileInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<KnowledgeFileInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, KnowledgeFileInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetFileId()) {
        optionals.set(0);
      }
      if (struct.isSetFileUrl()) {
        optionals.set(1);
      }
      if (struct.isSetFileType()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetFileId()) {
        oprot.writeI32(struct.fileId);
      }
      if (struct.isSetFileUrl()) {
        oprot.writeString(struct.fileUrl);
      }
      if (struct.isSetFileType()) {
        oprot.writeI32(struct.fileType);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, KnowledgeFileInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.fileId = iprot.readI32();
        struct.setFileIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.fileUrl = iprot.readString();
        struct.setFileUrlIsSet(true);
      }
      if (incoming.get(2)) {
        struct.fileType = iprot.readI32();
        struct.setFileTypeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

