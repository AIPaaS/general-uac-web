package com.ai.opt.data.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.opt.data.dao.mapper.bo.SysUser;
import com.ai.opt.data.dao.mapper.factory.MapperFactory;
import com.ai.opt.data.dao.mapper.interfaces.SysUserMapper;
import com.ai.opt.data.service.atom.interfaces.ILoginAtomSV;
import com.ai.opt.sdk.util.CollectionUtil;

@Component
public class LoginAtomSVImpl implements ILoginAtomSV {
	//@Autowired
	//private SysUserMapper sysUserMapper;

    @Override
    public SysUser queryByUserName(String loginName) {
        SysUserMapper sysUserMapper = MapperFactory.getSysUserMapper();
       
        List<SysUser> list = sysUserMapper.getByLoginName(loginName);
        if (!CollectionUtil.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


}
