package com.interfaces.mascot;

import com.thrift.common.body.Department;
import com.thrift.common.body.UserInfo;

import java.util.List;

/**
 * 部门管理接口
 *
 * @author zhangmengyu
 * 2018/4/27
 */
public interface DepartmentManageService extends BasicService {

    /**
     * 获取部门列表
     *
     * @param userInfo 调用接口用户信息
     */
    List<Department> getDepartmentList(UserInfo userInfo);

    /**
     * 新增部门
     *
     * @param userInfo       调用接口用户信息
     * @param departmentName 部门名称
     * @return result -1:已存在同名部门
     */
    Integer addDepartment(UserInfo userInfo, String departmentName);

    /**
     * 获取部门详情
     *
     * @param userInfo     调用接口用户信息
     * @param departmentId 部门编号
     * @return department
     */
    Department getDepartmentDetail(UserInfo userInfo, Integer departmentId);

    /**
     * 编辑修改部门
     *
     * @param userInfo       调用接口用户信息
     * @param departmentId   部门编号
     * @param departmentName 部门名称
     * @return result
     */
    Integer updateDepartment(UserInfo userInfo, Integer departmentId, String departmentName);

    /**
     * 删除部门
     *
     * @param userInfo     调用接口用户信息
     * @param departmentId 部门编号
     * @return result
     */
    Integer deleteDepartment(UserInfo userInfo, Integer departmentId);

    /**
     * 根据部门Id获取部门成员列表
     *
     * @param userInfo     调用接口用户信息
     * @param departmentId 部门编号
     * @return department
     */
    List<UserInfo> getUserInfoByDepartment(UserInfo userInfo, Integer departmentId);



}
