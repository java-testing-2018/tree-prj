package com.tree.springcloud.bl.controller;

import com.tree.springcloud.bl.service.TreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.tree.springcloud.bl.controller.TreeSearchApiController.URI;

@RestController
@RequestMapping(URI)
public class TreeSearchApiController {
    public static final String URI = "/api/list";

    private static Logger logger = LoggerFactory.getLogger(TreeCrudApiController.class);

    private TreeService treeService;

    @Autowired
    public TreeSearchApiController(TreeService treeCrudService) {
        this.treeService = treeCrudService;
    }


    @GetMapping("childs/{id}")
    public Collection<Long> getChildList(@PathVariable("id") Long id) {
        return treeService.findChildren(id);
    }

    @GetMapping("parents/{id}")
    public Collection<Long> getParentList(@PathVariable("id") Long id) {
        return treeService.findParents(id);
    }
}

