package com.young.elasticsearch.config;

/*************************
 * @author: YoungLu
 * @date: 2019/12/17 11:21
 * @description:
 **************************/
public class ElasticsearchProperties {
    //集群名称
    private String clusterName;
    private String host = "127.0.0.1:9200";
    private String username = "elastic";
    private String password;

    public ElasticsearchProperties() {

    }

    public ElasticsearchProperties(String clusterName, String host, String username, String password) {
        this.clusterName = clusterName;
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
