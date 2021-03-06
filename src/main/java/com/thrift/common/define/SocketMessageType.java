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

public enum SocketMessageType implements org.apache.thrift.TEnum {
  SocketDefine(0),
  SocketLogin(1001),
  SocketHeartbeat(1002),
  SocketNotificationTask(2001);

  private final int value;

  private SocketMessageType(int value) {
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
  public static SocketMessageType findByValue(int value) { 
    switch (value) {
      case 0:
        return SocketDefine;
      case 1001:
        return SocketLogin;
      case 1002:
        return SocketHeartbeat;
      case 2001:
        return SocketNotificationTask;
      default:
        return null;
    }
  }
}
