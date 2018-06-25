/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.body;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-04-27")
public class ArticleInfo implements org.apache.thrift.TBase<ArticleInfo, ArticleInfo._Fields>, java.io.Serializable, Cloneable, Comparable<ArticleInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ArticleInfo");

  private static final org.apache.thrift.protocol.TField ART_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("artId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ART_CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("artContent", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ArticleInfoStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ArticleInfoTupleSchemeFactory();

  public int artId; // required
  public java.lang.String artContent; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ART_ID((short)1, "artId"),
    ART_CONTENT((short)2, "artContent");

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
        case 1: // ART_ID
          return ART_ID;
        case 2: // ART_CONTENT
          return ART_CONTENT;
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
  private static final int __ARTID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ART_ID, new org.apache.thrift.meta_data.FieldMetaData("artId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ART_CONTENT, new org.apache.thrift.meta_data.FieldMetaData("artContent", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ArticleInfo.class, metaDataMap);
  }

  public ArticleInfo() {
  }

  public ArticleInfo(
    int artId,
    java.lang.String artContent)
  {
    this();
    this.artId = artId;
    setArtIdIsSet(true);
    this.artContent = artContent;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ArticleInfo(ArticleInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.artId = other.artId;
    if (other.isSetArtContent()) {
      this.artContent = other.artContent;
    }
  }

  public ArticleInfo deepCopy() {
    return new ArticleInfo(this);
  }

  @Override
  public void clear() {
    setArtIdIsSet(false);
    this.artId = 0;
    this.artContent = null;
  }

  public int getArtId() {
    return this.artId;
  }

  public ArticleInfo setArtId(int artId) {
    this.artId = artId;
    setArtIdIsSet(true);
    return this;
  }

  public void unsetArtId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ARTID_ISSET_ID);
  }

  /** Returns true if field artId is set (has been assigned a value) and false otherwise */
  public boolean isSetArtId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ARTID_ISSET_ID);
  }

  public void setArtIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ARTID_ISSET_ID, value);
  }

  public java.lang.String getArtContent() {
    return this.artContent;
  }

  public ArticleInfo setArtContent(java.lang.String artContent) {
    this.artContent = artContent;
    return this;
  }

  public void unsetArtContent() {
    this.artContent = null;
  }

  /** Returns true if field artContent is set (has been assigned a value) and false otherwise */
  public boolean isSetArtContent() {
    return this.artContent != null;
  }

  public void setArtContentIsSet(boolean value) {
    if (!value) {
      this.artContent = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ART_ID:
      if (value == null) {
        unsetArtId();
      } else {
        setArtId((java.lang.Integer)value);
      }
      break;

    case ART_CONTENT:
      if (value == null) {
        unsetArtContent();
      } else {
        setArtContent((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ART_ID:
      return getArtId();

    case ART_CONTENT:
      return getArtContent();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ART_ID:
      return isSetArtId();
    case ART_CONTENT:
      return isSetArtContent();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof ArticleInfo)
      return this.equals((ArticleInfo)that);
    return false;
  }

  public boolean equals(ArticleInfo that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_artId = true;
    boolean that_present_artId = true;
    if (this_present_artId || that_present_artId) {
      if (!(this_present_artId && that_present_artId))
        return false;
      if (this.artId != that.artId)
        return false;
    }

    boolean this_present_artContent = true && this.isSetArtContent();
    boolean that_present_artContent = true && that.isSetArtContent();
    if (this_present_artContent || that_present_artContent) {
      if (!(this_present_artContent && that_present_artContent))
        return false;
      if (!this.artContent.equals(that.artContent))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + artId;

    hashCode = hashCode * 8191 + ((isSetArtContent()) ? 131071 : 524287);
    if (isSetArtContent())
      hashCode = hashCode * 8191 + artContent.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(ArticleInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetArtId()).compareTo(other.isSetArtId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArtId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.artId, other.artId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetArtContent()).compareTo(other.isSetArtContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArtContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.artContent, other.artContent);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ArticleInfo(");
    boolean first = true;

    sb.append("artId:");
    sb.append(this.artId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("artContent:");
    if (this.artContent == null) {
      sb.append("null");
    } else {
      sb.append(this.artContent);
    }
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

  private static class ArticleInfoStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ArticleInfoStandardScheme getScheme() {
      return new ArticleInfoStandardScheme();
    }
  }

  private static class ArticleInfoStandardScheme extends org.apache.thrift.scheme.StandardScheme<ArticleInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ArticleInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ART_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.artId = iprot.readI32();
              struct.setArtIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ART_CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.artContent = iprot.readString();
              struct.setArtContentIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ArticleInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ART_ID_FIELD_DESC);
      oprot.writeI32(struct.artId);
      oprot.writeFieldEnd();
      if (struct.artContent != null) {
        oprot.writeFieldBegin(ART_CONTENT_FIELD_DESC);
        oprot.writeString(struct.artContent);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ArticleInfoTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ArticleInfoTupleScheme getScheme() {
      return new ArticleInfoTupleScheme();
    }
  }

  private static class ArticleInfoTupleScheme extends org.apache.thrift.scheme.TupleScheme<ArticleInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ArticleInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetArtId()) {
        optionals.set(0);
      }
      if (struct.isSetArtContent()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetArtId()) {
        oprot.writeI32(struct.artId);
      }
      if (struct.isSetArtContent()) {
        oprot.writeString(struct.artContent);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ArticleInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.artId = iprot.readI32();
        struct.setArtIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.artContent = iprot.readString();
        struct.setArtContentIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
