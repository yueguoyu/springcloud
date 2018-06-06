package com.ygy.alyundao;

/**
 * @author ygy
 * @date  2018/6/2
 */
public interface AlyunDao {
    /**
     * 短信验证
     * @param number
     * @return
     * @throws Exception
     */
    boolean message(String number) throws Exception;

    /**
     * 短信验证
     * @param number
     * @return code
     *
     */
    int addMessage(String number);
}