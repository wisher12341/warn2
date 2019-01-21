package com.warn.controller;

import com.warn.dto.DataGrid;
import com.warn.dto.PageHelper;
import com.warn.entity.Bed;
import com.warn.entity.OldMan;
import com.warn.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/bed")
public class BedController {
    @Autowired
    BedService bedService;

    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String list_bed(){
        return "user/table_bed";
    }

    @RequestMapping(value = "/oldman/user/list",method = RequestMethod.GET)
    public String list_bed_oldman(){return "user/table_bed_oldman";}

    @ResponseBody
    @RequestMapping(value = "/oldman/datagrid", method = RequestMethod.POST)
    public DataGrid dataGridOld(PageHelper page,Bed bed){
        DataGrid dg = new DataGrid();
        dg.setTotal(bedService.dataGridBedOldTotal(bed));
        List<Bed> BedList = bedService.dataGridBedOld(page,bed);
        dg.setRows(BedList);
        return dg;
    }

    @ResponseBody
    @RequestMapping(value="/datagrid", method = RequestMethod.POST)
    public DataGrid datagrid(PageHelper page, Bed bed) {
        DataGrid dg = new DataGrid();
        Long size = new Long("20");
        dg.setTotal(size);
        List<Bed> BedList = bedService.dataGridBed(page,bed);
        dg.setRows(BedList);
        return dg;
    }
}
