package com.disney.studios.service;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * DB health monitor, relies on DB connection pool validation. If we get a
 * connection we're good. Accessible from /health
 *
 * @author hzineddin
 *
 */

@Component
public class DBHealthMonitor extends AbstractHealthIndicator {

    @Autowired
    private DataSource ds;

    @Override
    protected void doHealthCheck(Health.Builder bldr) throws Exception {

        /**
         * we disable DataSourceHealthIndicator that comes with spring boot as
         * it's calling db directly. See management.health.db.enabled in app
         * props. No need to do that since db connection pool does validation
         * for us, why do it twice?
         */
        Connection conn = ds.getConnection();
        if (conn != null && !conn.isClosed()) {
            conn.close(); // clean up, no need to be in finally clause
            bldr.up().withDetail("DB", "up");
        } else {
            bldr.outOfService().withDetail("DB", "down"); // if db is down,
                                                          // we're out of
                                                          // service :(
        }
    }
}
