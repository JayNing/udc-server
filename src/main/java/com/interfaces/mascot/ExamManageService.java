package com.interfaces.mascot;

import com.thrift.common.body.UserInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 知识管理接口
 *
 * @author zhangmengyu
 * 2018/5/16
 */
public interface ExamManageService extends BasicService {

    /**
     * 添加题目
     *
     * @param userInfo          调用接口用户信息
     * @param type              题目类型，1单选2多选3判断4简答
     * @param excsTitle         题目内容
     * @param repCatId1         知识库一级分类
     * @param repCatId2         知识库二级分类
     * @param optionList        选项列表 OptionCode 选项名；OptionDesc 选项值；CrrctAns 是否正确答案
     * @param currAnswerContent 参考答案
     * @return result 1:成功 -1：标题重复
     */
    Integer addExercise(UserInfo userInfo, Integer type, String excsTitle, Integer repCatId1, Integer repCatId2, List<Map<String, Object>> optionList, String currAnswerContent);

    /**
     * 查询题目详情
     *
     * @param userInfo    调用接口用户信息
     * @param exercisesId 题目Id
     * @return exercisesInfo
     */
    Map<String, Object> queryExerciseDetail(UserInfo userInfo, Integer exercisesId);

    /**
     * 修改题目
     *
     * @param userInfo          调用接口用户信息
     * @param exercisesId       题号
     * @param type              题目类型，1选择2判断3简答  ---  不可更改
     * @param excsTitle         题目内容
     * @param repCatId1         知识库一级分类
     * @param repCatId2         知识库二级分类
     * @param optionList        选项列表 OptionCode 选项名；OptionDesc 选项值；CrrctAns 是否正确答案；
     * @param currAnswerContent 参考答案
     * @return result 1:成功;-1：标题重复
     */
    Integer updateExercise(UserInfo userInfo, Integer exercisesId, Integer type, String excsTitle, Integer repCatId1, Integer repCatId2, List<Map<String, Object>> optionList, String currAnswerContent);

    /**
     * 删除题目
     *
     * @param userInfo    调用接口用户信息
     * @param exercisesId 题号
     * @return result 1:成功
     */
    Integer deleteExercise(UserInfo userInfo, Integer exercisesId);

    /**
     * 查询试题管理列表
     *
     * @param userInfo   调用接口用户信息
     * @param exeType    题目类型 1单选2多选3判断4简答
     * @param title      标题
     * @param repCatId1  一级分类
     * @param repCatId2  二级分类
     * @param createName 创建人
     * @param startTime  起始时间
     * @param endTime    截止时间
     * @param pageIndex  当前页
     * @param pageSize   每页条数
     * @return list
     */
    List<Map<String, Object>> queryExerciseList(UserInfo userInfo, Integer exeType, String title, Integer repCatId1, Integer repCatId2, String createName,
                                                       Date startTime, Date endTime, Integer pageIndex, Integer pageSize);

    /**
     * 查询试题管理列表总条数
     *
     * @param userInfo   调用接口用户信息
     * @param exeType    题目类型
     * @param title      标题
     * @param repCatId1  一级分类
     * @param repCatId2  二级分类
     * @param createName 创建人
     * @param startTime  起始时间
     * @param endTime    截止时间
     * @return list
     */
    Integer queryExerciseListCount(UserInfo userInfo, Integer exeType, String title, Integer repCatId1, Integer repCatId2, String createName,
                                          Date startTime, Date endTime);

    /**
     * 添加试卷模板
     *
     * @param userInfo   调用接口用户信息
     * @param paperTitle 题目内容
     * @param repCatId1  试卷一级分类
     * @param repCatId2  试卷二级分类
     * @param exerList   题目列表 参数1：ExerType 题目模块（题目类型）；参数2：ExerTypeScore 每题分值；参数3：List<Map<String, Object>> ExerList该类型下的题号列表--参数3.1：ExerId 题目编号 --参数3.2：Sort 题目位置（正序）
     * @return result 1:成功; -1:传入的模块下试题列表参数为空或异常；-2:分值不是100; -3:标题重复
     */
    Integer addPaper(UserInfo userInfo, String paperTitle, Integer repCatId1, Integer repCatId2, List<Map<String, Object>> exerList);

    /**
     * 查询试卷模板详情
     *
     * @param userInfo    调用接口用户信息
     * @param paperId 试卷Id
     * @return  paperInfo
     */
    Map<String, Object> queryPaperDetail(UserInfo userInfo, Integer paperId);

    /**
     * 修改试卷模板
     *
     * @param userInfo   调用接口用户信息
     * @param paperId    试卷Id
     * @param paperTitle 题目内容
     * @param repCatId1  试卷一级分类
     * @param repCatId2  试卷二级分类
     * @param exerList   题目列表 参数1：ExerType 题目模块（题目类型）；参数2：ExerTypeScore 每题分值；参数3：List<Map<String, Object>> ExerInfoList该类型下的题号列表--参数3.1：ExerId 题目编号 --参数3.2：Sort 题目位置（正序）
     * @return result 1:成功; -1:传入的模块下试题列表参数为空或异常；-2:分值不是100; -3:标题重复
     */
    Integer updatePaper(UserInfo userInfo, Integer paperId, String paperTitle, Integer repCatId1, Integer repCatId2, List<Map<String, Object>> exerList);

    /**
     * 删除试卷模板
     *
     * @param userInfo 调用接口用户信息
     * @param paperId  试卷号
     * @return result 1:成功
     */
    Integer deletePaper(UserInfo userInfo, Integer paperId);

    /**
     * 查询试卷（模板）管理列表
     *
     * @param userInfo  调用接口用户信息
     * @param title     标题
     * @param repCatId1 一级分类
     * @param repCatId2 二级分类
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> queryPaperList(UserInfo userInfo, String title, Integer repCatId1, Integer repCatId2, Date startTime, Date endTime, Integer pageIndex, Integer pageSize);

    /**
     * 查询试卷（模板）管理列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param title     标题
     * @param repCatId1 一级分类
     * @param repCatId2 二级分类
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return list
     */
    Integer queryPaperListCount(UserInfo userInfo, String title, Integer repCatId1, Integer repCatId2, Date startTime, Date endTime);

    /**
     * 发起考试
     *
     * @param userInfo     调用接口用户信息
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param time         考试时长（分钟）
     * @param examTitle    考试标题
     * @param userIdList   参试人员
     * @param paperId      模板试卷编号
     * @param readerIdList 阅卷人员
     * @return result 1:成功；其他失败； -1：标题重复
     */
    Integer addExam(UserInfo userInfo, Date startTime, Date endTime, Integer time, String examTitle, List<Integer> userIdList,
                           Integer paperId, List<Integer> readerIdList);

    /**
     * 删除考试
     *
     * @param userInfo 调用接口用户信息
     * @param examId   试卷号
     * @return result 1:成功
     */
    Integer deleteExam(UserInfo userInfo, Integer examId);

    /**
     * 查询考试管理列表
     *
     * @param userInfo   调用接口用户信息
     * @param title      标题
     * @param startTime  起始时间
     * @param endTime    截止时间
     * @param assmStatus 评估状态：1.未评估 ；2，已评估
     * @param pageIndex  当前页
     * @param pageSize   每页条数
     * @return list
     */
    List<Map<String, Object>> queryExamManageList(UserInfo userInfo, String title, Date startTime, Date endTime, Integer assmStatus, Integer pageIndex, Integer pageSize);

    /**
     * 查询考试管理列表总条数
     *
     * @param userInfo   调用接口用户信息
     * @param title      标题
     * @param startTime  起始时间
     * @param endTime    截止时间
     * @param assmStatus 评估状态：1.未评估 ；2，已评估
     * @return count
     */
    Integer queryExamManageListCount(UserInfo userInfo, String title, Date startTime, Date endTime, Integer assmStatus);

    /**
     * 考试评估
     *
     * @param userInfo   调用接口用户信息
     * @param examId      考试编号
     * @return result 1:评估成功；2：已评估，无需再评估； 其他，失败
     */
    Integer addAssessment(UserInfo userInfo, Integer examId, String assmContent);

    /**
     * 查询批卷列表
     *
     * @param userInfo  调用接口用户信息
     * @param examId    考试编号
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> queryCheckPaperList(UserInfo userInfo, Integer examId, Integer pageIndex, Integer pageSize);

    /**
     * 查询批卷列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param examId    考试编号
     * @return list
     */
    Integer queryCheckPaperListCount(UserInfo userInfo, Integer examId);

    /**
     * 我的考试列表
     *
     * @param userInfo  调用接口用户信息
     * @param title     搜索参数
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> showMyPaperList(UserInfo userInfo, String title, Date startTime, Date endTime, Integer pageIndex, Integer pageSize);

    /**
     * 我的考试列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param title     搜索参数
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return list
     */
    Integer queryMyPaperListCount(UserInfo userInfo, String title, Date startTime, Date endTime);

    /**
     * 开始考试---查询试卷实例
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试Id
     * @return paperInfo
     */
    Map<String, Object> showPaperInstanceDetail(UserInfo userInfo, Integer examId);

    /**
     * 开始考试 --- 提交答案
     *
     * @param userInfo   调用接口用户信息
     * @param examId     模板试卷编号
     * @param answerList 作答列表 参数1：ExerType 题目模块（题目类型）；参数2：List<Map<String, Object>> ExerInfoList该类型下的题号列表--参数2.1：ExerId 题目编号 --参数2.2：AnsContent 作答内容，多选用英文逗号","分隔
     * @return result 1:成功；其他失败；
     */
    Integer addExamAnswer(UserInfo userInfo, Integer examId, List<Map<String,Object>> answerList);

    /**
     * 阅卷---展示答案
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试Id
     * @param userId   考试人Id
     * @return paperInfo
     */
    Map<String, Object> updateExamAnswer(UserInfo userInfo, Integer examId, Integer userId);

    /**
     * 阅卷---提交
     *
     * @param userInfo  调用接口用户信息
     * @param examId    考试Id
     * @param userId    考试人Id
     * @param scoreList 问答题评分列表 参数1：ExerId 题目编号 --参数2：ActualScore 阅卷人对该题目的打分
     * @return result 1:成功 其他失败
     */
    Integer updateUserTotalScore(UserInfo userInfo, Integer examId, Integer userId, List<Map<String, Object>> scoreList);

    /**
     * 考试分析
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试Id
     * @return paperInfo
     */
    Map<String, Object> queryExamAnalysis(UserInfo userInfo, Integer examId);

    /**
     * 问答题作答情况
     *
     * @param userInfo   调用接口用户信息
     * @param examId     考试Id
     * @param exerciseId 题目Id
     * @param realName   模糊搜索真实姓名
     * @param pageIndex  当前页
     * @param pageSize   每页条数
     * @return list
     */
    List<Map<String, Object>> queryShortAnswerAnalysis(UserInfo userInfo, Integer examId, Integer exerciseId, String realName, Integer pageIndex, Integer pageSize);

    /**
     * 问答题作答情况总条数
     *
     * @param userInfo   调用接口用户信息
     * @param examId     考试Id
     * @param exerciseId 题目Id
     * @param realName   模糊搜索真实姓名
     * @return count
     */
    Integer queryShortAnswerAnalysisCount(UserInfo userInfo, Integer examId, Integer exerciseId, String realName);

    /**
     * 考试统计 --- 柱状图
     *
     * @param userInfo 调用接口用户信息
     * @param examId   考试Id
     * @return map
     */
    List<Map<String, Object>> queryExamBarChart(UserInfo userInfo, Integer examId);

    /**
     * 考试统计 --- 分数详情
     *
     * @param userInfo   调用接口用户信息
     * @param examId     考试Id
     * @param pageIndex  当前页
     * @param pageSize   每页条数
     * @return list
     */
    List<Map<String, Object>> queryScoreAnalysis(UserInfo userInfo, Integer examId, Integer pageIndex, Integer pageSize);

    /**
     * 考试统计 --- 分数详情总条数
     *
     * @param userInfo   调用接口用户信息
     * @param examId     考试Id
     * @return list
     */
    Integer queryScoreAnalysisCount(UserInfo userInfo, Integer examId);



}
