package com.ai.opt.sso.service;

import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sso.util.IPHelper;
import com.alibaba.fastjson.JSON;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.services.RegexRegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义service认证策略.
 */
public class CustomRegisteredService extends RegexRegisteredService{

    private static final Logger LOG = LoggerFactory.getLogger(CustomRegisteredService.class);

    private static String[] whiteList;

    static {
        try {
            Properties properties = new Properties();
            ClassLoader loader = CustomRegisteredService.class.getClassLoader();
            properties.load(loader.getResourceAsStream("whitelist.properties"));
            String whiteList1 = properties.getProperty("whiteList");
            if(!StringUtil.isBlank(whiteList1)){
                whiteList = whiteList1.split(",");
            }
            LOG.error("init service whiteList success,whiteList is " + JSON.toJSONString(whiteList));
        } catch (IOException e) {
            LOG.error("init service whiteList failure",e);
        }
    }

    @Override
    public boolean matches(Service service) {
        LOG.error("service=["+service.getId()+"]开始进行认证");
        if(super.matches(service)){//正则表达式认证成功，进行自定义service白名单认证
            Pattern p =  Pattern.compile("//([^/]+)(/.*)+");
            Matcher matcher = p.matcher(service.getId());
            while (matcher.find()){
                String host = matcher.group(1);
                if(host.indexOf(':')>0){
                    host = host.substring(0,host.indexOf(":"));
                }
                LOG.error("service=["+service.getId()+"]中的host部分为："+host);
                if(IPHelper.isWhiteList(host,whiteList)){
                    LOG.error("service=["+service.getId()+"]认证通过");
                    return true;
                }
            }
        }
        LOG.error("service=["+service.getId()+"]认证失败");
        return false;
    }
}
