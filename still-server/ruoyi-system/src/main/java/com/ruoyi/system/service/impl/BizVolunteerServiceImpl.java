package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BizVolunteerMapper;
import com.ruoyi.system.domain.BizVolunteer;
import com.ruoyi.system.service.IBizVolunteerService;

/**
 * 志愿者申请与档案Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
@Service
public class BizVolunteerServiceImpl implements IBizVolunteerService 
{
    @Autowired
    private BizVolunteerMapper bizVolunteerMapper;

    /**
     * 查询志愿者申请与档案
     * 
     * @param volunteerId 志愿者申请与档案主键
     * @return 志愿者申请与档案
     */
    @Override
    public BizVolunteer selectBizVolunteerByVolunteerId(Long volunteerId)
    {
        return bizVolunteerMapper.selectBizVolunteerByVolunteerId(volunteerId);
    }

    /**
     * 查询志愿者申请与档案列表
     * 
     * @param bizVolunteer 志愿者申请与档案
     * @return 志愿者申请与档案
     */
    @Override
    public List<BizVolunteer> selectBizVolunteerList(BizVolunteer bizVolunteer)
    {
        return bizVolunteerMapper.selectBizVolunteerList(bizVolunteer);
    }

    /**
     * 新增志愿者申请与档案
     * 
     * @param bizVolunteer 志愿者申请与档案
     * @return 结果
     */
    @Override
    public int insertBizVolunteer(BizVolunteer bizVolunteer)
    {
        bizVolunteer.setCreateTime(DateUtils.getNowDate());
        return bizVolunteerMapper.insertBizVolunteer(bizVolunteer);
    }

    /**
     * 修改志愿者申请与档案
     * 
     * @param bizVolunteer 志愿者申请与档案
     * @return 结果
     */
    @Override
    public int updateBizVolunteer(BizVolunteer bizVolunteer)
    {
        return bizVolunteerMapper.updateBizVolunteer(bizVolunteer);
    }

    /**
     * 批量删除志愿者申请与档案
     * 
     * @param volunteerIds 需要删除的志愿者申请与档案主键
     * @return 结果
     */
    @Override
    public int deleteBizVolunteerByVolunteerIds(Long[] volunteerIds)
    {
        return bizVolunteerMapper.deleteBizVolunteerByVolunteerIds(volunteerIds);
    }

    /**
     * 删除志愿者申请与档案信息
     * 
     * @param volunteerId 志愿者申请与档案主键
     * @return 结果
     */
    @Override
    public int deleteBizVolunteerByVolunteerId(Long volunteerId)
    {
        return bizVolunteerMapper.deleteBizVolunteerByVolunteerId(volunteerId);
    }
}
