package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizVolunteer;

/**
 * 志愿者申请与档案Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-21
 */
public interface BizVolunteerMapper 
{
    /**
     * 查询志愿者申请与档案
     * 
     * @param volunteerId 志愿者申请与档案主键
     * @return 志愿者申请与档案
     */
    public BizVolunteer selectBizVolunteerByVolunteerId(Long volunteerId);

    /**
     * 查询志愿者申请与档案列表
     * 
     * @param bizVolunteer 志愿者申请与档案
     * @return 志愿者申请与档案集合
     */
    public List<BizVolunteer> selectBizVolunteerList(BizVolunteer bizVolunteer);

    /**
     * 新增志愿者申请与档案
     * 
     * @param bizVolunteer 志愿者申请与档案
     * @return 结果
     */
    public int insertBizVolunteer(BizVolunteer bizVolunteer);

    /**
     * 修改志愿者申请与档案
     * 
     * @param bizVolunteer 志愿者申请与档案
     * @return 结果
     */
    public int updateBizVolunteer(BizVolunteer bizVolunteer);

    /**
     * 删除志愿者申请与档案
     * 
     * @param volunteerId 志愿者申请与档案主键
     * @return 结果
     */
    public int deleteBizVolunteerByVolunteerId(Long volunteerId);

    /**
     * 批量删除志愿者申请与档案
     * 
     * @param volunteerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizVolunteerByVolunteerIds(Long[] volunteerIds);
}
