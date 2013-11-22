package com.lost.util;

public interface Constants {
    String CONFIG_NAME = "service.xml";
    
    String DATA_BASE = "";
    
    String CONFIG_FILE_PATH = "./conf";
    
    /**
     * 空字符串，无实际意义
     */
    String STRING_EMPTY = "";
    
    /**
     * http请求头域： Content-Type
     */
    final String CONTENT_TYPE_KEY = "Content-Type";
    
    /**
     * http请求头域： Content-Type值
     */
    final String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=utf-8";
    
    /**
     * 删除状态
     */
    int STATUS_DELETE = 0;
    
    interface DataBase {
        String FILE_NAME = "dbConfig.properties";
        
        String USER = "user";
        
        String PASSWORD = "password";
        
        String NAME = "name";
        
        String DRIVER = "driver";
        
        String ADDRESS = "address";
        
        String PORT = "port";
    }
    
    /**
     * 请求命令码
     * 
     * @author FFRL
     * 
     */
    interface Code {
        int SUECCESS = 0;
        
        /**
         * 调用接口失败
         */
        int FAIL_1 = -1;
        
        /**
         * 不支持请求
         */
        int FAIL_2 = -2;
        
        /**
         * 成功
         */
        String DESC_SUCCESS = "Success";
        
        /**
         * 失败
         */
        String DESC_FAIL = "Fail";
        
        /**
         * 不支持请求
         */
        String DESC_NO_SUPPORT = "Not support !";
    }
    
    /**
     * 订单状态 <br>
     * 0.数据被删除<br>
     * 1.待定<br>
     * 2.新单 <br>
     * 3.已发货 <br>
     * 4.已签收 <br>
     * 5.拒签 <br>
     * 6.已确认收款<br>
     * 7.拒签返货中 <br>
     * 8.拒签货已退回 <br>
     * 9.已退款<br>
     */
    interface OrdersStatus {
        /**
         * 待定
         */
        int WAIT = 1;
        
        /**
         * 新单
         */
        int NEW_ORDER = 2;
        
        /**
         * 已发货
         */
        int SHIPPED = 3;
        
        /**
         * 已签收
         */
        int RECEIVED = 4;
        
        /**
         * 拒签
         */
        int REFUSED_PRODUCT = 5;
        
        /**
         * 已确认收款
         */
        int MOENY_ACCEPTED = 6;
        
        /**
         * 拒签返货中
         */
        int PRODUCT_RETURN = 7;
        
        /**
         * 拒签货已退回
         */
        int PRODUCT_RETURNED = 8;
        
        /**
         * 已退款
         */
        int MOENY_RETURN = 9;
    }
    
}
