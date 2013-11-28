package com.alibaba.otter.canal.parse.inbound.mysql;

import junit.framework.Assert;

import org.junit.Test;

import com.alibaba.otter.canal.parse.inbound.mysql.dbsync.SimpleDdlParser;
import com.alibaba.otter.canal.parse.inbound.mysql.dbsync.SimpleDdlParser.DdlResult;

public class SimpleDdlParserTest {

    @Test
    public void testCreate() {
        String queryString = "CREATE TABLE retl_mark ( `ID` int(11)";
        DdlResult result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "CREATE TABLE IF NOT EXISTS retl.retl_mark ( `ID` int(11)";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "CREATE TABLE IF NOT EXISTS `retl_mark` ( `ID` int(11)";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "CREATE TABLE IF NOT EXISTS `retl.retl_mark` ( `ID` int(11)";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "CREATE TABLE  `retl`.`retl_mark` (\n  `ID` int(10) unsigned NOT NULL";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "CREATE TABLE  `retl`.`retl_mark`(\n  `ID` int(10) unsigned NOT NULL";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "CREATE table `bak591`.`j_order_log_back_201309` like j_order_log";
        result = SimpleDdlParser.parse(queryString, "bak");
        Assert.assertNotNull(result);
        Assert.assertEquals("bak591", result.getSchemaName());
        Assert.assertEquals("j_order_log_back_201309", result.getTableName());

        queryString = "CREATE TABLE `bak591`.`cm_settle_incash` (    `batch_id` bigint(20) NOT NULL  DEFAULT '0.00'";
        result = SimpleDdlParser.parse(queryString, "bak");
        Assert.assertNotNull(result);
        Assert.assertEquals("bak591", result.getSchemaName());
        Assert.assertEquals("cm_settle_incash", result.getTableName());
    }

    @Test
    public void testDrop() {
        String queryString = "DROP TABLE retl_mark";
        DdlResult result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "DROP TABLE IF EXISTS retl.retl_mark;";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "DROP TABLE IF EXISTS \n `retl.retl_mark` /;";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl_mark", result.getTableName());
    }

    @Test
    public void testAlert() {
        String queryString = "alter table retl_mark drop index emp_name";
        DdlResult result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "alter table retl.retl_mark drop index emp_name";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "alter table \n `retl.retl_mark` drop index emp_name;";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl_mark", result.getTableName());
    }

    @Test
    public void testTruncate() {
        String queryString = "truncate table retl_mark";
        DdlResult result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "truncate table retl.retl_mark";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "truncate \n  `retl.retl_mark` ";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl_mark", result.getTableName());
    }

    @Test
    public void testRename() {
        String queryString = "rename table retl_mark to retl_mark2";
        DdlResult result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getOriSchemaName());
        Assert.assertEquals("retl", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getOriTableName());
        Assert.assertEquals("retl_mark2", result.getTableName());

        queryString = "rename table retl.retl_mark to retl2.retl_mark2";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getOriSchemaName());
        Assert.assertEquals("retl2", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getOriTableName());
        Assert.assertEquals("retl_mark2", result.getTableName());

        queryString = "rename \n table \n `retl`.`retl_mark` to `retl2.retl_mark2`;";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getOriSchemaName());
        Assert.assertEquals("retl2", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getOriTableName());
        Assert.assertEquals("retl_mark2", result.getTableName());
    }

    @Test
    public void testIndex() {
        String queryString = "CREATE UNIQUE INDEX index_1 ON retl_mark(id,x)";
        DdlResult result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getTableName());

        queryString = "DROP INDEX index_str ON retl_mark";
        result = SimpleDdlParser.parse(queryString, "retl");
        Assert.assertNotNull(result);
        Assert.assertEquals("retl", result.getSchemaName());
        Assert.assertEquals("retl_mark", result.getTableName());
    }
}
