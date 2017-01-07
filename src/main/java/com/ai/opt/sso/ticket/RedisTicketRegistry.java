package com.ai.opt.sso.ticket;

import java.util.Collection;

import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.AbstractDistributedTicketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class RedisTicketRegistry extends AbstractDistributedTicketRegistry {

	private static final Logger LOG = LoggerFactory.getLogger(RedisTicketRegistry.class);
	private static final int TGT_TIME = 43200000; // TGT最大空闲时间 12小时

	//2017-01-07 gucl修改：之前为5分钟，改为和TGT_TIME一样，12小时
	private static final int ST_TIME = 43200000; // ST最大空闲时间

	private static final  String TICKECKKEY = "SSO-";
	
	@Override
	public void addTicket(final Ticket paramTicket) {
		LOG.error("===【RedisTicketRegistry】===addTicket===begin");
		if(paramTicket == null){
			LOG.error("ticket is null");
			return;
		}
		LOG.error("===ticket="+paramTicket+",jsondata:"+JSON.toJSONString(paramTicket));
		String ticketKey = TICKECKKEY+paramTicket.getId();
		LOG.error("===ticketKey="+ticketKey);
		int seconds = 0;
		if(paramTicket instanceof TicketGrantingTicket){
			seconds = TGT_TIME/1000;
			LOG.error("【【当前Ticket为TicketGrantingTicket】】");
		}else{
			seconds = ST_TIME/1000;
			LOG.error("【【当前Ticket为ServiceTicket】】");
		}
		
		try {
			new TicketService().saveTicket(ticketKey,paramTicket,seconds);
			LOG.error("===saveTicket ok===");
		} catch (Exception e) {
			LOG.error("===adding ticket to redis error.具体原因："+e.getMessage(),e);
		}
		LOG.error("===【RedisTicketRegistry】===addTicket===end");
	}

	@Override
	public boolean deleteTicket(final String ticketId) {
		if(ticketId == null){
			return false;
		}
		try {
			new TicketService().deleteTicket(TICKECKKEY+ticketId);
			LOG.error("===【deleteTicket】删除 ticket["+TICKECKKEY+ticketId+"] 成功!===");
			return true;
		} catch (Exception e) {
			LOG.error("===【deleteTicket】删除 ticket["+TICKECKKEY+ticketId+"] 失败!"+e.getMessage(),e);
		}
		return false;
	}

	@Override
	public Ticket getTicket(final String ticketId) {
		return getProxiedTicketInstance(getRawTicket(ticketId));
	}

	private Ticket getRawTicket(String ticketId) {
		if(ticketId == null){
			LOG.error("===【getRawTicket】ticketId为空");
			return null;
		}
		Ticket ticket = null;
		try {
			ticket = new TicketService().getTicket(TICKECKKEY+ticketId);
			LOG.error("===【getRawTicket】获取ticket["+TICKECKKEY+ticketId+"]="+JSON.toJSONString(ticket));
		} catch (Exception e) {
			LOG.error("===【getRawTicket】getting ticket from redis error.具体原因："+e.getMessage(),e);
		}
		return ticket;
	}

	@Override
	public Collection<Ticket> getTickets() {
		throw new UnsupportedOperationException("GetTickets not supported.");
	}

	@Override
	protected boolean needsCallback() {
		return false;
	}

	@Override
	protected void updateTicket(final Ticket ticket) {
		addTicket(ticket);
	}

}
