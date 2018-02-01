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

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TreeService {

    @Autowired
    private NodeRepository nodeRepository;

    public Node findOrCreate(Long id, Long parentId) {

        Verify.verifyNotNull(id);

        Node dbNode = nodeRepository.findOne(id);
        if (dbNode != null) { // find

            Node parentNode = nodeRepository.findOne(parentId);
            if (!parentNode.getChilds().contains(id)) {
                throw new VerifyException("No correct parrent id");
            }
            return dbNode;
        }

        if (parentId == null) { // parnet_id==null can be if it is first node
            Verify.verify(nodeRepository.count() == 0L);
        } else {
            Node parentNode = nodeRepository.findOne(parentId);
            Verify.verifyNotNull(parentNode);
            parentNode.addChild(id);
        }

        return nodeRepository.save(new Node(id, parentId + ": " + id, parentId));
    }

    public boolean delete(Long id) {
        if (!nodeRepository.exists(id)) {
            return false;
        }

        Node node = nodeRepository.findOne(id);
        List<Long> nodesToDelete = Lists.newArrayList(node.getChilds());
        nodesToDelete.add(id);
        return nodeRepository.deleteByIdIn(nodesToDelete) > 0;
    }

    public Collection<Long> findChildren(Long id) {
        Node node = Verify.verifyNotNull(nodeRepository.findOne(id), "The node is not exists");
        return node.getChilds();
    }

    public Collection<Long> findParents(Long id) {
        Verify.verify(nodeRepository.exists(id), "The node is not exists");
        return nodeRepository.findByChildStringLike("%" + Node.SEP + Objects.toString(id) + Node.SEP + "%").stream()
                .map(Node::getId)
                .collect(Collectors.toSet());
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

        boolean isUpdated;
        if (isUpdated = nodeRepository.setNewId(id, newId) > 0) {
            nodeRepository.updateChildInParents(id, newId);
        }
        return isUpdated;
    }

}
