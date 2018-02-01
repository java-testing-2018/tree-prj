package com.tree.springcloud.bl.controller;

import com.tree.springcloud.bl.dto.NodeDto;
import com.tree.springcloud.bl.dto.UpdateNodeDto;
import com.tree.springcloud.bl.service.TreeService;
import com.tree.springcloud.bl.service.exception.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tree.springcloud.bl.controller.TreeCrudApiController.URI;

@RestController
@RequestMapping(URI)
public class TreeCrudApiController {

    public static final String URI = "/api/crud";

    private TreeService treeService;

    @Autowired
    public TreeCrudApiController(TreeService treeCrudService) {
        this.treeService = treeCrudService;
    }

    @PostMapping
    public NodeDto findOrCreate(@RequestBody NodeDto node) {
        return new NodeDto(treeService.findOrCreate(node.getId(), node.getParentId()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        return treeService.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.unprocessableEntity().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UpdateNodeDto body)
            throws AlreadyExistsException {

        return treeService.updateId(id, body.getId())
                ? ResponseEntity.ok().build()
                : ResponseEntity.unprocessableEntity().build();
    }
}

