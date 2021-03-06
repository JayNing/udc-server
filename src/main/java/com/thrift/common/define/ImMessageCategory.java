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

public enum ImMessageCategory implements org.apache.thrift.TEnum {
  ImMessageCategory_Check(1),
  ImMessageCategory_Heartbeat(2),
  ImMessageCategory_Point(3),
  ImMessageCategory_Flock(4);

  private final int value;

  private ImMessageCategory(int value) {
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
  public static ImMessageCategory findByValue(int value) { 
    switch (value) {
      case 1:
        return ImMessageCategory_Check;
      case 2:
        return ImMessageCategory_Heartbeat;
      case 3:
        return ImMessageCategory_Point;
      case 4:
        return ImMessageCategory_Flock;
      default:
        return null;
    }
  }
}
