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

public enum ClickType implements org.apache.thrift.TEnum {
  ClickType_Unknown(0),
  ClickType_Article(1),
  ClickType_Video(2);

  private final int value;

  private ClickType(int value) {
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
  public static ClickType findByValue(int value) { 
    switch (value) {
      case 0:
        return ClickType_Unknown;
      case 1:
        return ClickType_Article;
      case 2:
        return ClickType_Video;
      default:
        return null;
    }
  }
}
