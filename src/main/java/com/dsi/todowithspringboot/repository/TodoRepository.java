package com.dsi.todowithspringboot.repository;

import com.dsi.todowithspringboot.entity.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByIsCompletedOrderByCreatedAtAsc(Boolean isCompleted);

    List<Todo> findAllByIsStarredOrderByCreatedAtAsc(Boolean isStarred);
}
