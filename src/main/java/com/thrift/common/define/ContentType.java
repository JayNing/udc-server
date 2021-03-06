/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.thrift.common.define;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * 消息内容类型
 * 
 */
public enum ContentType implements org.apache.thrift.TEnum {
  ContentType_Unknown(0),
  ContentType_Text(1),
  ContentType_Image(2),
  ContentType_Document(3),
  ContentType_URL(4);

  private final int value;

  private ContentType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static ContentType findByValue(int value) { 
    switch (value) {
      case 0:
        return ContentType_Unknown;
      case 1:
        return ContentType_Text;
      case 2:
        return ContentType_Image;
      case 3:
        return ContentType_Document;
      case 4:
        return ContentType_URL;
      default:
        return null;
    }
  }
}
