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

public enum MascotFileType implements org.apache.thrift.TEnum {
  Video(1),
  Ppt(2),
  Pdf(3),
  Document(4),
  Other(5);

  private final int value;

  private MascotFileType(int value) {
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
  public static MascotFileType findByValue(int value) { 
    switch (value) {
      case 1:
        return Video;
      case 2:
        return Ppt;
      case 3:
        return Pdf;
      case 4:
        return Document;
      case 5:
        return Other;
      default:
        return null;
    }
  }
}
