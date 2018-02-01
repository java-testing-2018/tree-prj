package com.tree.springcloud.bl.domain;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Node {

    public static final char SEP = '|';

    @Id
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "child_string")
    private String childString;

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

    public String getChildString() {
        return childString;
    }

    public void setChildString(String childString) {
        this.childString = childString;
    }


    public void addChild(Long id) {

        Set<Long> childs = getChilds();
        if (childs.add(id)) {
            this.childString = SEP + StringUtils.join(childs, SEP)  + SEP;
        }
    }

    @Deprecated
    public void removeChild(Long id) {
        Set<Long> childs = getChilds();
        if (getChilds().remove(id)) {
            this.childString = SEP + StringUtils.join(childs, SEP)  + SEP;
        }
    }

    public Set<Long> getChilds() {
        return StringUtils.isNotEmpty(childString)
                ? Arrays.stream(StringUtils.split(childString, SEP))
                .map(Long::valueOf)
                .collect(Collectors.toSet())
                : Sets.newHashSet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name) &&
                Objects.equals(parentId, node.parentId) &&
                Objects.equals(childString, node.childString) &&
                Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentId, childString, id);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", childString='" + childString + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
