package com.platformcommons.platform.service.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class NoOpDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return new DataSource() {
            @Override
            public Connection getConnection() throws SQLException {
                throw new UnsupportedOperationException("No-op DataSource: not connected to any DB");
            }

            @Override
            public Connection getConnection(String username, String password) throws SQLException {
                throw new UnsupportedOperationException("No-op DataSource: not connected to any DB");
            }

            // Optional: implement other methods as no-ops or throw UnsupportedOperationException

            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                throw new SQLException("No-op");
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                return false;
            }

            @Override
            public java.io.PrintWriter getLogWriter() throws SQLException {
                return null;
            }

            @Override
            public void setLogWriter(java.io.PrintWriter out) throws SQLException {
            }

            @Override
            public void setLoginTimeout(int seconds) throws SQLException {
            }

            @Override
            public int getLoginTimeout() throws SQLException {
                return 0;
            }

            @Override
            public java.util.logging.Logger getParentLogger() {
                return java.util.logging.Logger.getGlobal();
            }
        };
    }
}