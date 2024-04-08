package com.dsi.todowithspringboot.repository;

import com.dsi.todowithspringboot.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    public Long countByIsCompleted(Boolean isCompleted);

    public Long countByIsStarred(Boolean isStarred);
}