package com.ai.opt.sso.ticket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sso.unicache.UniCache;
import com.ai.opt.sso.util.SerializeUtil;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;

public final class CommonService {
	private static final Logger LOG = LoggerFactory.getLogger(CommonService.class);
	private static final String CHARSET = "UTF-8";
	private static CommonService instance;
	private CommonService(){}
	
	public static synchronized CommonService getInstance(){
		if(instance == null){
			instance = new CommonService();
		}
		return instance;
	}
	
	public ICacheClient getCache(){
		return UniCache.getCache();
		//return RedisClient.getInstance().getJedis();
	}
	
	public Object getValue(String key) {
		Object val = null;
		try {
			byte[] data = getCache().get(key.getBytes(CHARSET));
			val = SerializeUtil.unserialize(data);
		} catch (Exception e) {
			LOG.error("===CommonService.getValue 获取 obj 失败,具体原因："+e.getMessage(),e);
		}
		return val;
	}

	public void removeObj(String key) {
		try {
			getCache().del(key.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			LOG.error("===CommonService.removeObj 删除 obj 失败,具体原因："+e.getMessage(),e);
		}
	}

	public void saveObj(String key,
			Object obj, int maxInactiveInterval) {
		try {
			getCache().set(key.getBytes(CHARSET), SerializeUtil.serialize(obj));
			getCache().expire(key.getBytes(CHARSET), maxInactiveInterval);
		} catch (Exception e) {
			LOG.error("---CommonService.saveObj("+key+","+obj+","+maxInactiveInterval+") 保存至redis异常,具体原因："+e.getMessage(),e);
		}
	}

	public void saveObj(String key, String ticketId) {
		try {
			getCache().set(key.getBytes(CHARSET), SerializeUtil.serialize(ticketId));
		} catch (IOException e) {
			LOG.error("---CommonService.saveObj 保存至redis异常,具体原因："+e.getMessage(),e);
		}
	}
}
