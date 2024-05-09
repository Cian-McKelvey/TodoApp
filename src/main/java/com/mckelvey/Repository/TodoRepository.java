package com.mckelvey.Repository;

import com.google.common.collect.ImmutableList;
import com.mckelvey.api.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoRepository {

    private List<Todo> todos;

    public TodoRepository(List<Todo> todos) {
        this.todos = ImmutableList.copyOf(todos);
    }

    // Returns the complete list of todos
    public List<Todo> getTodos() {
        return todos;
    }

    // Data for this application needs to be immutable, to make this happen, we need to copy and make a new list
    public boolean addNewTodo(Todo newTodo) {
        List<Todo> updatedTodos = new ArrayList<>(this.todos);
        updatedTodos.add(newTodo);
        try {
            this.todos = ImmutableList.copyOf(updatedTodos);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Finds a todo by id
    public Optional<Todo> getTodoByID(String id) {
        return todos.stream().filter(todo -> todo.getTodoID().equals(id)).findFirst();
    }

}
