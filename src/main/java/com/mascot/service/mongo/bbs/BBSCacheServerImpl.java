package com.mascot.service.mongo.bbs;


import com.mascot.service.mongo.CacheServer;
import com.mongodb.WriteResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service(value = "bbsCacheServer")
public class BBSCacheServerImpl implements CacheServer<BBSMongoBean> {

    private static final Log logger = LogFactory.getLog(BBSCacheServerImpl.class);
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Boolean saveOrUpdate(BBSMongoBean mongoObject) {
        Boolean result = false;
        if (mongoObject.getFlag() == 1) {
            //新增
            mongoTemplate.save(mongoObject);
            result = true;
        } else if (mongoObject.getFlag() == 2) {
            //修改
            //条件
            Query query = new Query();
            query.addCriteria(Criteria.where("disId").is(mongoObject.getDisId()));

            //修改的参数
            Update update = new Update();
            if (mongoObject.getEssayTypeName() != null) {
                update.set("essayTypeName", mongoObject.getEssayTypeName());
            }
            if (mongoObject.getTitle() != null) {
                update.set("title", mongoObject.getTitle());
            }
            if (mongoObject.getEssayContent() != null) {
                update.set("essayContent", mongoObject.getEssayContent());
            }
            if (mongoObject.getComContent() != null) {
                update.set("comContent", mongoObject.getComContent());
            }
            update.set("date", mongoObject.getDate());

            WriteResult upsert = mongoTemplate.upsert(query, update, BBSMongoBean.class);

            result = true;

        }

        return result;
    }

    @Override
    public Boolean saveOrUpdateList(List<BBSMongoBean> mongoObjectList) {


//        SysListener.getExecutor().execute(() -> {
//            synchronized (this) {
//
//            }
//        });

        return null;
    }

    @Override
    public List<BBSMongoBean> getCacheObjectListByPage(Map<String, Object> params, Integer pageIndex, Integer pageSize) {

        Query query = new Query();
        if (params.containsKey("param")) {
            String keyword = "^.*" + params.get("param").toString() + ".*$";
            Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);

            Criteria criteria = new Criteria();
            criteria.orOperator(
                    Criteria.where("essayTypeName").regex(pattern),
                    Criteria.where("title").regex(pattern),
                    Criteria.where("essayContent").regex(pattern),
                    Criteria.where("comContent").regex(pattern)
            );
            query.addCriteria(criteria);
            query.limit(pageSize).skip((pageIndex - 1) * pageSize);

        }

        List<BBSMongoBean> bbsMongoBeans = mongoTemplate.find(query, BBSMongoBean.class);

        return bbsMongoBeans;
    }

    @Override
    public Long getCacheObjectCountByPage(Map<String, Object> params) {
        Query query = new Query();
        if (params.containsKey("param")) {
            String keyword = "^.*" + params.get("param") + ".*$";
            Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
            Criteria criteria = new Criteria();
            criteria.orOperator(
                    Criteria.where("essayTypeName").regex(pattern),
                    Criteria.where("title").regex(pattern),
                    Criteria.where("essayContent").regex(pattern),
                    Criteria.where("comContent").regex(pattern)
            );
            query.addCriteria(criteria);
        }

        long count = mongoTemplate.count(query, BBSMongoBean.class);

        return count;
    }

    @Override
    public Boolean initCacheObject() {
        //将10天前的所有数据删除
        Date day = new Date();
        Date date = new Date(day.getTime() - 10 * 24 * 60 * 60 * 1000L);

        Query query = Query.query(Criteria.where("date").lte(date));
        mongoTemplate.remove(query, BBSMongoBean.class);
        //查询50条知识库数据
        //TODO

        return true;
    }
}
