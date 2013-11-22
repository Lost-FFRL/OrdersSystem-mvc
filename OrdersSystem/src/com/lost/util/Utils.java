package com.lost.util;

import java.io.IOException;


import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


import com.lost.bean.JsonResult;


/**
 * 工具类
 * 
 * @author FFRL
 * 
 */
public class Utils
{
    /**
     * 日志记录
     */
    private static Logger LOG = Logger.getLogger(Utils.class);
    
    public static boolean isNotEmpty(String str)
    {
        if (null == str || str.length() == 0 || "".equals(str))
        {
            return false;
        }
        return true;
    }
    
    public static boolean isEmpty(String str)
    {
        return !isNotEmpty(str);
    }
    
    /**
     * 字符串替换<br>
     * Example: <br> 
     *  source = "{0},{1},{2}" <br>
     *  value = new String[]{"first","two","three"} <br>
     *  return = "first,two,three"<br>
     * @param source 
     * @param value new String[]{3,4,5}
     * @return "345"
     */
    public static String template(String source, String... value)
    {
        if (value.length == 0)
        {
            LOG.info("Param value is null.");
            return source;
        }
        String result = source;
        for (int i = 0, size = value.length; i < size; i++)
        {
            result = result.replaceAll("\\{"+i+"\\}", value[i]);
        }
        return result;
    }
    
    public static String getRespJson(int code, int type, String desc,Object content)
    {
        String result = "";
        try
        {
            ObjectMapper om = new ObjectMapper();
            JsonResult js = new JsonResult();
            js.setCode(String.valueOf(code));
            js.setType(String.valueOf(type));
            js.setContent(content);
            js.setDesc(desc);
            result = om.writeValueAsString(js);
        }
        catch (JsonGenerationException e)
        {
            LOG.error(e);
        }
        catch (JsonMappingException e)
        {
            LOG.error(e);
        }
        catch (IOException e)
        {
            LOG.error(e);
        }
        return result;
    }
    
    /**
     * 响应成功或失败，不返回数据
     * @param code
     * @param type
     * @param desc
     * @return
     */
    public static String getRespJson(int code, int type, String desc)
    {
        String result = "";
        try
        {
            ObjectMapper om = new ObjectMapper();
            JsonResult js = new JsonResult();
            js.setCode(String.valueOf(code));
            js.setType(String.valueOf(type));
            js.setDesc(desc);
            js.setContent("");
            result = om.writeValueAsString(js);
        }
        catch (JsonGenerationException e)
        {
            LOG.error(e);
        }
        catch (JsonMappingException e)
        {
            LOG.error(e);
        }
        catch (IOException e)
        {
            LOG.error(e);
        }
        return result;
    }
    
    /**
     * 拆分匹配，给搜索条件增加'%'
     * @param word
     * @return
     */
    public static String splitCharMatch(String word){
        if (isEmpty(word)){
            return completeMatch(word);
        }
        StringBuffer sb = new StringBuffer("%");
        String[] wordArr = word.trim().split("");
        for (String str : wordArr){
            if ("".equals(str)){
                continue;
            }
            sb.append(str).append("%");
        }
        return sb.toString();
    }
    
    /**
     * 全量匹配，搜索条件前后加'%'
     * @param word
     * @return
     */
    public static String completeMatch(String word){
        // TODO 需要过滤 %
        return isEmpty(word) ? "%" : ("%" + word.trim() + "%");
    }
    
    /**
     * 拆分词语匹配：'%word%word%'
     * 拆分搜索条件为词语，进行匹配
     * @param word
     * @return
     */
    public static String splitWordMatch(String word){
        //TODO 需要根据"词语库"来进行匹配
        if (isEmpty(word)){
            return completeMatch(word);
        }
        StringBuffer sb = new StringBuffer("%");
        String[] wordArr = word.trim().split(" ");
        for (String str : wordArr){
            if ("".equals(str)){
                continue;
            }
            sb.append(str).append("%");
        }
        return sb.toString();
    }
    
    public static String objectToJson(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            LOG.error("Object = " + obj.toString() + "; Error = ", e);
            return "";
        } catch (JsonMappingException e) {
            LOG.error("Object = " + obj.toString() + "; Error = ", e);
            return "";
        } catch (IOException e) {
            LOG.error("Object = " + obj.toString() + "; Error = ", e);
            return "";
        }
    }
    
    /**
     * 订单状态转化为文字描述
     * @param status
     * @return
     */
    public static String ordersStatusToText(int status){
        String text = "删除";
        switch (status) {
            case Constants.OrdersStatus.WAIT:
                text = "待定";
                break;
            case Constants.OrdersStatus.NEW_ORDER:
                text = "新单";
                break;
            case Constants.OrdersStatus.SHIPPED:
                text = "已发货";
                break;
            case Constants.OrdersStatus.RECEIVED:
                text = "已签收";
                break;
            case Constants.OrdersStatus.REFUSED_PRODUCT:
                text = "拒签";
                break;
            case Constants.OrdersStatus.MOENY_ACCEPTED:
                text = "已确认收款";
                break;
            case Constants.OrdersStatus.PRODUCT_RETURN:
                text = "拒签,返货中";
                break;
            case Constants.OrdersStatus.PRODUCT_RETURNED:
                text = "拒签货已退回";
                break;
            case Constants.OrdersStatus.MOENY_RETURN:
                text = "已退款";
                break;
            default:
                break;
        }
        return text;
    }
}
