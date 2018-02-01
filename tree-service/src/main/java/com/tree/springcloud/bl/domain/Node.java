package com.tree.springcloud.bl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Node {

    @Id
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(nullable = false)
    private String name;

    public Node() {
    }

    public Node(Long id, String name, Long parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
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
        Node node = (Node) o;
        return Objects.equals(name, node.name) &&
                Objects.equals(parentId, node.parentId) &&
                Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentId, id);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                '}';
    }
}
