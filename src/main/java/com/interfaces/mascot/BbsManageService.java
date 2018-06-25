package com.interfaces.mascot;

import com.thrift.common.body.UserInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 社区管理接口
 *
 * @author zhangmengyu
 * 2018/5/11
 */
public interface BbsManageService extends BasicService {

    /**
     * 查询社区管理列表
     *
     * @param userInfo  调用接口用户信息
     * @param title     社区贴文/提问标题
     * @param disType   社区帖子类型：1，贴文；2，提问
     * @param typId     分类Id
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> queryDiscussionManageList(UserInfo userInfo, String title, Integer disType, Integer typId, Date startTime,
                                                        Date endTime, Integer pageIndex, Integer pageSize);

    /**
     * 查询社区管理列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param title     社区贴文/提问标题
     * @param disType   社区帖子类型：1，贴文；2，提问
     * @param typId     分类Id
     * @param startTime 起始时间
     * @param endTime   截止时间
     * @return count
     */
    Integer queryDiscussionManageListCount(UserInfo userInfo, String title, Integer disType, Integer typId, Date startTime, Date endTime);

    /**
     * 查询社区界面搜索列表
     *
     * @param userInfo  调用接口用户信息
     * @param param     搜索框参数
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> queryDiscussionList(UserInfo userInfo, String param, Integer pageIndex, Integer pageSize);

    /**
     * 查询社区界面搜索列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param param    搜索框参数
     * @return list
     */
    Integer queryDiscussionListCount(UserInfo userInfo, String param);

    /**
     * 社区标签搜索列表
     *
     * @param userInfo  调用接口用户信息
     * @param param     标签
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> showBbsByTags(UserInfo userInfo, String param, Integer pageIndex, Integer pageSize);

    /**
     * 社区标签搜索列表总条数
     *
     * @param userInfo  调用接口用户信息
     * @param param     标签
     * @return list
     */
    Integer queryBbsByTagsCount(UserInfo userInfo, String param);

    /**
     * 社区置顶
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区帖子Id
     * @return count 2：置顶成功；1：取消置顶成功；其他失败
     */
    Integer addTopDiscussion(UserInfo userInfo, Integer disId);

    /**
     * 删除帖子
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区帖子Id
     * @return result 1:成功；其他：失败
     */
    Integer deleteDiscussion(UserInfo userInfo, Integer disId);

    /**
     * 我的帖子
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @param disType   1:  贴文/分享;2: 提问
     * @return list
     */
    List<Map<String, Object>> queryMyDiscussion(UserInfo userInfo, Integer pageIndex, Integer pageSize, Integer disType);

    /**
     * 查询我的帖子总条数
     *
     * @param userInfo 调用接口用户信息
     * @param disType  1:  贴文/分享;2: 提问
     * @return list
     */
    Integer queryMyDiscussionCount(UserInfo userInfo, Integer disType);

    /**
     * 查询我收藏的帖子
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @param disType   1:  贴文/分享;2: 提问
     * @return list
     */
    List<Map<String, Object>> queryCollectDiscussion(UserInfo userInfo, Integer pageIndex, Integer pageSize, Integer disType);

    /**
     * 查询我收藏的帖子总条数
     *
     * @param userInfo 调用接口用户信息
     * @param disType  1:  贴文/分享;2: 提问
     * @return list
     */
    Integer queryCollectDiscussionCount(UserInfo userInfo, Integer disType);

    /**
     * 我关注的人
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<UserInfo> queryFollowUser(UserInfo userInfo, Integer pageIndex, Integer pageSize);

    /**
     * 查询我关注的人总条数
     *
     * @param userInfo 调用接口用户信息
     * @return list
     */
    Integer queryFollowUserCount(UserInfo userInfo);

    /**
     * 积分排行榜
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @return list
     */
    List<Map<String, Object>> queryIntegralRanking(UserInfo userInfo, Integer pageIndex, Integer pageSize);

    /**
     * 积分排行榜总条数
     *
     * @param userInfo 调用接口用户信息
     * @return list
     */
    Integer queryIntegralRankingCount(UserInfo userInfo);

    /**
     * 提问题
     *
     * @param userInfo    调用接口用户信息
     * @param title       标题
     * @param queDesc     问题描述
     * @param tagStrings  标签名(字符串)
     * @param essayTypeId 帖子分类
     * @param repCatId1   一级知识分类
     * @param repCatId2   二级知识分类
     * @param flowId      流程分类
     * @return result -1:包含中文逗号，则返回关键字不合法 ; 1:成功
     */
    Integer addQuestion(UserInfo userInfo, String title, String queDesc, String tagStrings, Integer essayTypeId, Integer repCatId1, Integer repCatId2, Integer flowId);

    /**
     * 分享知识/帖子
     *
     * @param userInfo    调用接口用户信息
     * @param title       标题
     * @param artContent  问题描述
     * @param tagStrings  标签名(字符串)
     * @param essayTypeId 帖子分类
     * @param repCatId1   一级知识分类
     * @param repCatId2   二级知识分类
     * @param flowId      流程分类
     * @return result -1:包含中文逗号，则返回关键字不合法 ; 1:成功
     */
    Integer addEssayInfo(UserInfo userInfo, String title, String artContent, String tagStrings, Integer essayTypeId, Integer repCatId1, Integer repCatId2, Integer flowId);

    /**
     * 关注/取消关注
     *
     * @param userInfo 调用接口用户信息
     * @param userId   被关注者Id
     * @return result result 1：关注成功；2取消关注成功；其他：失败
     */
    Integer addFollow(UserInfo userInfo, Integer userId);

    /**
     * 收藏/取消收藏
     *
     * @param userInfo   调用接口用户信息
     * @param targetType 类型：1，贴文，2，提问
     * @param disId      社区Id
     * @return result result 1：收藏成功；2：取消收藏成功；其他失败
     */
    Integer addCollect(UserInfo userInfo, Integer targetType, Integer disId);

    /**
     * 评论贴文
     *
     * @param userInfo   调用接口用户信息
     * @param comContent 评论内容
     * @param essayId    贴文Id
     * @param disId      社区Id
     * @return result
     */
    Integer addCommentInfo(UserInfo userInfo, String comContent, Integer essayId, Integer disId);

    /**
     * 回答提问
     *
     * @param userInfo   调用接口用户信息
     * @param ansContent 回答内容
     * @param queId      提问Id
     * @return result
     */
    Integer addAnswerInfo(UserInfo userInfo, String ansContent, Integer queId);

    /**
     * 设为最佳答案
     *
     * @param userInfo 调用接口用户信息
     * @param ansId    回答Id
     * @param disId    社区Id
     * @return result -1：已有最佳答案，不可再设;1:成功
     */
    Integer addBestAnswer(UserInfo userInfo, Integer ansId, Integer queId, Integer disId);

    /**
     * 查询评论/回答列表
     *
     * @param userInfo  调用接口用户信息
     * @param pageIndex 当前页
     * @param pageSize  每页条数
     * @param disId     社区Id
     * @return list
     */
    List<Map<String, Object>> queryComOrAnsList(UserInfo userInfo, Integer pageIndex, Integer pageSize, Integer disId);

    /**
     * 查询评论/回答列表总条数
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区Id
     * @return list
     */
    Integer queryComOrAnsListCount(UserInfo userInfo, Integer disId);

    /**
     * 点赞/取消点赞
     *
     * @param userInfo   调用接口用户信息
     * @param UsefulTyp  点赞对象：1.贴文评论；2.提问回答
     * @param relationId 对象Id
     * @return result 1：成功，其他：失败
     */
    Map<String, Object> addDisctLike(UserInfo userInfo, Integer UsefulTyp, Integer relationId);

    /**
     * 有用
     *
     * @param userInfo   调用接口用户信息
     * @param UsefulTyp  有用对象：1.贴文；2.最佳回答
     * @param relationId 对象Id：提问Id/贴文Id
     * @param disId      社区Id
     * @return result 1：成功，-1：已点无用，请先取消； 其他：失败
     */
    Integer addUseful(UserInfo userInfo, Integer UsefulTyp, Integer relationId, Integer disId);

    /**
     * 无用
     *
     * @param userInfo   调用接口用户信息
     * @param UsefulTyp  有用对象：1.贴文；2.最佳回答
     * @param relationId 对象Id：提问Id/贴文Id
     * @param disId      社区Id
     * @return result 1：成功，-1：已点有用，请先取消； 其他：失败
     */
    Integer addUnUseful(UserInfo userInfo, Integer UsefulTyp, Integer relationId, Integer disId);

    /**
     * 通过disId查询有用数和无用数
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区帖子Id
     * @return discussionInfo
     */
    Map<String, Object> queryUsefulCountByDisId(UserInfo userInfo, Integer disId);

    /**
     * 通过disId查询社区详细
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区帖子Id
     * @return discussionInfo
     */
    Map<String, Object> queryDisInfoByDisId(UserInfo userInfo, Integer disId);

    /**
     * 社区加入知识仓库
     *
     * @param userInfo 调用接口用户信息
     * @param dis      社区对象Id
     * @return result 1：成功，-2：已加入知识仓库，-3：该提问没有最佳答案，无法加入知识仓库，其他失败
     */
    Integer addBbsToKnlg(UserInfo userInfo, Integer dis);

    /**
     * 相关推荐 --- 社区
     *
     * @param userInfo 调用接口用户信息
     * @param disId    社区Id
     * @return list
     */
    List<Map<String, Object>> queryBbsRecommendByDisId(UserInfo userInfo, Integer disId, Integer pageSize);

    /**
     * 发布社区公告
     *
     * @param userInfo       调用接口用户信息
     * @param messageContent 消息内容
     * @return result 1：成功，其他:失败
     */
    Integer addBbsNotice(UserInfo userInfo, String messageContent);

}
