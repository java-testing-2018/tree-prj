package com.tree.springcloud.bl.repository;

import com.tree.springcloud.bl.domain.Node;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nullable;
import java.util.List;

public interface NodeRepository extends CrudRepository<Node, Long> {

    /**
     * Rename id for node
     * @param id
     * @param newId
     * @return
     */
    @Modifying
    @Query("update Node n set n.id = ?2 where n.id = ?1")
    int setNewId(Long id, Long newId);

    int deleteByIdIn(List<Long> ids);

    List<Node> findByParentId(@Nullable Long id);

    boolean existsByParentId(@Nullable Long id);
}
