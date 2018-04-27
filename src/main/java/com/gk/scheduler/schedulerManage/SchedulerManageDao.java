package com.gk.scheduler.schedulerManage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SchedulerManageDao {

    @Resource(name = "jdbcTemplateQuartzAdmin")
    private JdbcTemplate jdbcTemplate;

    public Long addDispatchLog(String clusterName, String instanceName, Long beginTime, Long endTime, String jobName, Integer status, Long executeDuration, String exception) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                    (Connection connection) -> {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO dispatch_log (cluster_name, instance_name, begin_time, end_time, job_name, status," +
                            "execute_duration, exception)" +
                            "VALUES (?, ? , ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, clusterName);
                    statement.setString(2, instanceName);
                    statement.setLong(3, beginTime);
                    statement.setLong(4, endTime);
                    statement.setString(5, jobName);
                    statement.setInt(6, status);
                    statement.setLong(7, executeDuration);
                    statement.setString(8, exception);
                    return statement;
            }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * 根据id修改调度日志
     */
    public void updateDispatchLogById(Long id, Integer status, Long endTime, Long executeDuration, String exception) {
        StringBuilder updateSql = new StringBuilder("update dispatch_log set status = ? ,end_time = ? ,execute_duration = ?,");
        List<Object> values = new ArrayList<>();
        values.add(status);
        values.add(endTime);
        values.add(executeDuration);
        if (exception != null) {
            updateSql.append("exception = ? ,");
            values.add(exception);
        }
        String sql = updateSql.substring(0, updateSql.length() - 1);
        values.add(id);
        jdbcTemplate.update(sql + " where id = ?",values.toArray());
    }

}
