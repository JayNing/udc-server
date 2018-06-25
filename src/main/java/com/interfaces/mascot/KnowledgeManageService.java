package com.interfaces.mascot;

import com.thrift.common.body.TagInfo;
import com.thrift.common.body.UserInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 知识管理接口
 *
 * @author zhangmengyu
 * 2018/4/28
 */
public interface KnowledgeManageService extends BasicService {

    /**
     * 录入知识
     *
     * @param userInfo   调用接口用户信息
     * @param title      知识标题
     * @param author     知识作者 - 可为空
     * @param profile    知识简介
     * @param knlgType   知识类型：1.文章，2.非文章
     * @param artContent 文章内容
     * @param tagStrings 标签名(字符串)
     * @param fileList   附件列表 参数类型为List<Map<String,Object>> 其中每个map包括"FileUrl"（附件路径）字段和"FileType"（附件类型：1.视频;2.PPT;3.PDF;4.WORD）字段
     * @param repCatId1  一级知识分类
     * @param repCatId2  二级知识分类
     * @param flowId     流程分类
     * @param bbsFlag    是否社区沉淀
     * @return result  -1 : 包含英文逗号，则返回关键字不合法,请用中文逗号分隔
     */
    Integer addKnowledge(UserInfo userInfo, String title, String author, String profile, Integer knlgType,
                         String artContent, String tagStrings, List<Map<String, Object>> fileList, Integer repCatId1,
                         Integer repCatId2, Integer flowId, Integer bbsFlag);

    /**
     * 查询知识仓库管理列表/分类搜索列表
     *
     * @param userInfo  调用接口用户信息
     * @param title     知识标题
     * @param repCatId1 一级分类
     * @param repCatId2 二级分类
     * @param flowId    流程分类
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> queryKnowledgeInfo(UserInfo userInfo, String title, Integer repCatId1, Integer repCatId2, Integer flowId,
                                                 Date startTime, Date endTime, Integer pageIndex, Integer pageSize);

    /**
     * 查询知识仓库管理列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param title     知识标题
     * @param repCatId1 一级分类
     * @param repCatId2 二级分类
     * @param flowId    流程分类
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return count
     */
    Integer queryKnowledgeInfoCount(UserInfo userInfo, String title, Integer repCatId1, Integer repCatId2, Integer flowId,
                                    Date startTime, Date endTime);

    /**
     * 智能搜索知识仓库列表
     *
     * @param userInfo  调用接口用户信息
     * @param param     搜索框参数 --- 不为空
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> queryKnowledgeInfoIntelligent(UserInfo userInfo, String param, Integer pageIndex, Integer pageSize);

    /**
     * 智能搜索知识仓库列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param param    搜索框参数 --- 不为空
     * @return list
     *
     */
    Integer queryKnowledgeInfoIntelligentCount(UserInfo userInfo, String param);

    /**
     * 最近浏览知识列表
     *
     * @param userInfo 调用接口用户信息
     * @param num      最大展示条数
     * @return list
     */
    List<Map<String, Object>> queryKnowledgeInfoRecently(UserInfo userInfo, Integer num);

    /**
     * 标签搜索列表
     *
     * @param userInfo  调用接口用户信息
     * @param param     标签
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> showKnowledgeByTags(UserInfo userInfo, String param, Integer pageIndex, Integer pageSize);

    /**
     * 标签搜索列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param param     标签
     * @return list
     */
    Integer queryKnowledgeByTagsCount(UserInfo userInfo, String param);

    /**
     * 公司热点知识列表
     *
     * @param userInfo 调用接口用户信息
     * @param num      最大展示条数
     * @return list
     */
    List<Map<String, Object>> queryKnowledgeInfoHot(UserInfo userInfo, Integer num);

    /**
     * 智能推荐知识列表
     *
     * @param userInfo 调用接口用户信息
     * @param num      最大展示条数
     * @return list
     */
    List<Map<String, Object>> queryKnowledgeInfoIntelligently(UserInfo userInfo, Integer num);

    /**
     * 热门标签
     *
     * @param userInfo 调用接口用户信息
     * @param num      最大展示数量
     * @return tagInfoList
     */
    List<TagInfo> updateTagInfoCounts(UserInfo userInfo, Integer num);

    /**
     * 查看知识详情
     *
     * @param userInfo  调用接口用户信息
     * @param kId       知识编号
     * @param viewOrUpd 标志：1，修改时需要点开的详情界面（不计浏览量）；2，浏览时点开的详情界面（计浏览量）
     * @return knowledgeInfo
     */
    Map<String, Object> showKnowledgeInfoDetails(UserInfo userInfo, Integer kId, Integer viewOrUpd);

    /**
     * 修改知识
     *
     * @param userInfo   调用接口用户信息
     * @param kId        知识编号
     * @param title      知识标题
     * @param author     知识作者 - 可为空
     * @param profile    知识简介
     * @param knlgType   知识类型：1.文章，2.非文章
     * @param artContent 文章内容
     * @param tagStrings 标签名(字符串),逗号分隔
     * @param repCatId1  一级知识分类
     * @param repCatId2  二级知识分类
     * @param flowId     流程分类
     * @param fileList   附件列表 参数类型为List<Map<String,Object>> 其中每个map包括"FileUrl"（附件路径）字段和"FileType"（附件类型：1.视频;2.PPT;3.PDF;4.WORD）字段
     * @return 1:成功；其他：失败
     */
    Integer updateKnowledge(UserInfo userInfo, Integer kId, String title, String author, String profile,
                            Integer knlgType, String artContent, String tagStrings, Integer repCatId1,
                            Integer repCatId2, Integer flowId, List<Map<String, Object>> fileList);

    /**
     * 删除知识
     *
     * @param userInfo 调用接口用户信息
     * @param kId      知识编号
     * @return 1:成功；其他：失败
     */
    Integer deleteKnowledge(UserInfo userInfo, Integer kId);

    /**
     * 根据标签名/关键字查询标签信息
     *
     * @param userInfo 调用接口用户信息
     * @param tagName  标签名/关键字
     * @return tagInfo
     */
    TagInfo queryTagInfoByTagName(UserInfo userInfo, String tagName);

    /**
     * 相关知识推荐
     * 规则：相同知识库分类下，浏览数最多的知识
     *
     * @param userInfo 调用接口用户信息
     * @param kId      知识Id
     * @param num      最大展示条数
     * @return list
     */
    List<Map<String, Object>> queryRelatedKnowledge(UserInfo userInfo, Integer kId, Integer num);

    /**
     * 更新标签的搜索数
     *
     * @param userInfo 调用接口用户信息
     * @param tagName  标签名
     * @return result
     */
    Integer updateTagInfo(UserInfo userInfo, String tagName);

}
