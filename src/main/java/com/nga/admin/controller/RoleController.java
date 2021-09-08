package com.nga.admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.nga.admin.entity.po.Role;
import com.nga.admin.service.IRoleService;
import com.nga.admin.common.utils.PageUtils;
import com.nga.admin.common.utils.R;



/**
 * 
 *
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
@Slf4j
@Api(tags="RoleController的接口")
@RestController
@RequestMapping("api/admin/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;




    /**
     * 列表
     */
    @GetMapping("/page")
    @ApiOperation(value="按页获取",notes = "第一个参数page表示第几页，第二个参数limit表示每页几条记录")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleService.queryPage(params);
        return R.ok().put(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{roleId}")
    @ApiOperation(value="根据主键获取")
    public R info(@PathVariable("roleId") Integer roleId){
		Role role = roleService.getById(roleId);
        return R.ok().put(role);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "添加")
    public R save(@RequestBody Role role){
        if (roleService.save(role))return R.ok("保存成功");
        return R.error("保存失败");
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改")
    public R update(@RequestBody Role role){
        if (roleService.updateById(role))return R.ok("修改成功");
        return R.error("修改失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除")
    public R delete(Integer[] ids){
        if (roleService.removeByIds(Arrays.asList(ids)))return R.ok("删除成功");
        return R.error("删除失败");
    }

}
