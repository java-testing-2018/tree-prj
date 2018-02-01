package com.tree.springcloud.bl.service;


import com.google.common.base.VerifyException;
import com.tree.springcloud.bl.TreeApiApplicationTest;
import com.tree.springcloud.bl.domain.Node;
import com.tree.springcloud.bl.repository.NodeRepository;
import com.tree.springcloud.bl.service.exception.AlreadyExistsException;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

// @formatter:off
// 8
// |--1
// |  |--3
// |
// |--5
//    |--2
//    |--4
//       |--7
// @formatter:on

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TreeApiApplicationTest.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:service-test.sql")
public class TreeServiceTest {

    @Autowired
    private TreeService treeService;

    @Autowired
    private NodeRepository nodeRepository;

    /**
     * Test to find existed node
     */
    @Test
    public void testFind() {
        long id = 4L;

        long expectedCount = nodeRepository.count();
        Node dbNode = nodeRepository.findOne(id);
        assertThat(dbNode, is(notNullValue()));

        Node foundNode = treeService.findOrCreate(dbNode.getId(), dbNode.getParentId());
        assertThat(dbNode, is(foundNode));
        assertThat(nodeRepository.count(), is(expectedCount));
    }

    /**
     * Test to create a first node
     */
    @Test
    public void testCreateFirstNode() {
        long id = 1L;
        nodeRepository.deleteAll();
        Node newNode = treeService.findOrCreate(1L, null);

        assertThat(newNode.getId(), is(id));
        assertThat(newNode.getParentId(), is(nullValue()));

        assertThat(nodeRepository.count(), is(1L));
    }

    /**
     * Test to create a node if the one does not exist
     */
    @Test
    public void testCreate() {
        long id = 6L;
        long parentId = 3L;

        long expectedCount = nodeRepository.count();

        Node dbNode = nodeRepository.findOne(id);
        assertThat(dbNode, is(nullValue()));
        Node dbParentNode = nodeRepository.findOne(parentId);
        assertThat(dbParentNode, is(notNullValue()));

        Node newNode = treeService.findOrCreate(id, parentId);
        assertThat(newNode.getId(), is(id));
        assertThat(newNode.getParentId(), is(parentId));

        assertThat(nodeRepository.count(), is(expectedCount + 1));
    }

    /**
     * Error Test of create node: parent id is null
     */
    @Test(expected = VerifyException.class)
    public void testCreateWithNullParent() {
        long id = 6L;
        Node dbNode = nodeRepository.findOne(id);
        assertThat(dbNode, is(nullValue()));

        treeService.findOrCreate(id, null);
    }

    /**
     * Error Test of create node: id it has no existed parent
     */
    @Test(expected = VerifyException.class)
    public void testCreateWithNoValidParent() {
        long id = 6L;
        long noValidParentId = 10L;

        long expectedCount = nodeRepository.count();

        Node dbNode = nodeRepository.findOne(id);
        assertThat(dbNode, is(nullValue()));
        Node dbParentNode = nodeRepository.findOne(noValidParentId);
        assertThat(dbParentNode, is(nullValue()));

        treeService.findOrCreate(id, noValidParentId);

        assertThat(nodeRepository.count(), is(expectedCount));
    }

    /**
     * Error Test of create node: id is null
     */
    @Test(expected = VerifyException.class)
    public void testCreateWithNoValidId() {
        long expectedCount = nodeRepository.count();

        long id = 3L;


        Node dbParentNode = nodeRepository.findOne(id);
        assertThat(dbParentNode, is(notNullValue()));

        treeService.findOrCreate(null, dbParentNode.getId());

        assertThat(nodeRepository.count(), is(expectedCount));
    }


    /**
     * The test of updating id
     * @throws AlreadyExistsException
     */
    @Test
    public void testUpdateId() throws AlreadyExistsException {
        long id = 4L;
        long newId = 100L;

        long expectedCount = nodeRepository.count();

        Node dbNode = nodeRepository.findOne(id);
        assertThat(dbNode, is(notNullValue()));

        assertTrue(treeService.updateId(id, newId));

        List<Node> result = nodeRepository.findByParentId(dbNode.getParentId());
        assertTrue(result.stream()
                .anyMatch(i -> i.getId().equals(newId)));

        assertThat(nodeRepository.count(), is(expectedCount));
    }

    /**
     * Error Test of updating node: new id already exists
     * @throws AlreadyExistsException
     */
    @Test(expected = AlreadyExistsException.class)
    public void testUpdateIdToExistedId() throws AlreadyExistsException {
        long id = 4L;
        long newId = 8L;

        Node dbNode = nodeRepository.findOne(id);
        assertThat(dbNode, is(notNullValue()));

        treeService.updateId(id, newId);
    }


    /**
     * The test to get all hierarchical parents
     */
    @Test
    public void testParents() {
        List<Long> parentIds = treeService.findParents(4L);
        assertThat(parentIds, Matchers.containsInAnyOrder(8L, 5L));
    }

    /**
     * The test to get all hierarchical parents: id is not existed
     */
    @Test(expected = VerifyException.class)
    public void testParentsNotExistedId() {
        List<Long> parentIds = treeService.findParents(111L);
        assertThat(parentIds, Matchers.containsInAnyOrder(8L, 5L));
    }

    /**
     * The test to get all nested childs
     */
    @Test
    public void testChilds() {
        List<Long> childsIds = treeService.findChildren(5L);
        assertThat(childsIds, Matchers.containsInAnyOrder(2L, 4L, 7L));
    }
    /**
     * The test to get all nested childs: id is not existed
     */
    @Test(expected = VerifyException.class)
    public void testChildsNotExistedId() {
        List<Long> childsIds = treeService.findChildren(111L);
        assertThat(childsIds, Matchers.containsInAnyOrder(2L, 4L, 7L));
    }
}

