package com.tree.springcloud.bl.dto;

import com.tree.springcloud.bl.domain.Node;

import java.util.Objects;

public class UpdateNodeDto {

    private Long id;

    public UpdateNodeDto() {
    }

    public UpdateNodeDto(Long id) {
        this.id = id;
    }

    public UpdateNodeDto(Node node) {
        this.id = node.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateNodeDto node = (UpdateNodeDto) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UpdateNodeDto{" +
                "id=" + id +
                '}';
    }
}
