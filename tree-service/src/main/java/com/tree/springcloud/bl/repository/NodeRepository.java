package com.tree.springcloud.bl.repository;

import com.tree.springcloud.bl.domain.Node;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

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

    @Modifying
    @Query("update Node n  " +
            "set n.childString = concat(" +
            "  substring(n.childString, 0, locate(:#{'|' + #id + '|'}, n.childString)), " +
            "  :#{#newId}," +
            "  substring(childString, locate(:#{'|' + #id + '|'}, childString) + length(:#{#id}) - 2 )) " +
            "where n.childString like %:#{'|' + #id + '|'}%")
    int updateChildInParents(@Param("id") Long id, @Param("newId") Long newId);

    int deleteByIdIn(List<Long> ids);

    Set<Node> findByChildStringLike(String regId);
    List<Node> findByParentId(@Nullable Long id);
}
