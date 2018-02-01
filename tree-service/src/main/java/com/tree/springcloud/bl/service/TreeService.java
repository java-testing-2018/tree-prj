package com.tree.springcloud.bl.service;

import com.google.common.base.Verify;
import com.google.common.base.VerifyException;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.tree.springcloud.bl.domain.Node;
import com.tree.springcloud.bl.repository.NodeRepository;
import com.tree.springcloud.bl.service.exception.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TreeService {

    @Autowired
    private NodeRepository nodeRepository;

    public Node findOrCreate(Long id, Long parentId) {

        Verify.verifyNotNull(id);

        Node dbNode = nodeRepository.findOne(id);
        if (dbNode != null) { // find
            if (!Objects.equals(dbNode.getParentId(), parentId)) {
                throw new VerifyException("No correct parrent id");
            }
            return dbNode;
        }

        if (parentId == null) { // parnet_id==null can be if it is first node
            Verify.verify(!nodeRepository.existsByParentId(null));
        } else {
            Verify.verify(nodeRepository.exists(parentId));
        }

        return nodeRepository.save(new Node(id, parentId + ": " + id, parentId));
    }

    public boolean delete(Long id) {
        if (!nodeRepository.exists(id)) {
            return false;
        }

        List<Long> nodesToDelete = traverseChilds(id);
        nodesToDelete.add(id);
        return nodeRepository.deleteByIdIn(nodesToDelete) > 0;
    }

    public List<Long> findChildren(Long id) {
        Verify.verify(nodeRepository.exists(id), "The node is not exists");
        return traverseChilds(id);
    }

    public List<Long> findParents(Long id) {
        Verify.verify(nodeRepository.exists(id), "The node is not exists");
        return traverseParents(id);
    }

    public boolean updateId(Long id, Long newId)
            throws AlreadyExistsException {
        Verify.verifyNotNull(id);
        Verify.verifyNotNull(newId);

        if (Objects.equals(id, newId)) {
            return false;
        }

        if (nodeRepository.exists(newId)) {
            throw new AlreadyExistsException("New Id already exists");
        }

        return nodeRepository.setNewId(id, newId) > 0;
    }

    @Deprecated
    private void circularValidation(final Long id) {
        List<Long> childIds = traverseChilds(id);
        Verify.verify(childIds.contains(id), "Circular exception");
    }

    private List<Long> traverseParents(long id) {
        Long nodeId;
        List<Long> result = Lists.newArrayList();
        Node node = nodeRepository.findOne(id);
        while ((nodeId = node.getParentId()) != null) {
            result.add(nodeId);
            node = nodeRepository.findOne(nodeId);
        }
        Collections.reverse(result);
        return result;
    }

    private List<Long> traverseChilds(long id) {
        List<Long> result = Lists.newArrayList();
        Deque<Long> toTraverse = Queues.newArrayDeque();
        toTraverse.push(id);

        do {
            nodeRepository.findByParentId(toTraverse.pop()).stream()
                    .map(Node::getId)
                    .peek(toTraverse::push)
                    .forEach(result::add);

        } while (toTraverse.size() > 0);

        return result;
    }
}
