package com.tree.springcloud.bl.dto;

import com.tree.springcloud.bl.domain.Node;

import java.util.Objects;

public class NodeDto {

    private Long id;
    private Long parentId;
    private String name;

    public NodeDto() {
    }

    public NodeDto(Long id, String name, Long parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public NodeDto(Node node) {
        this.id = node.getId();
        this.parentId = node.getParentId();
        this.name = node.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeDto node = (NodeDto) o;
        return Objects.equals(name, node.name) &&
                Objects.equals(parentId, node.parentId) &&
                Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, parentId, id);
    }
}
