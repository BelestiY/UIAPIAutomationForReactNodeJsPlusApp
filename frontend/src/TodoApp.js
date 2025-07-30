import React, { useState, useEffect } from "react";
import axios from "axios";
import "./TodoApp.css";

function TodoApp({ onLogout }) {
  const [todos, setTodos] = useState([]);
  const [newTodo, setNewTodo] = useState("");

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const res = await axios.get("http://localhost:5000/api/todos", {
        withCredentials: true,
      });
      // Sort by id descending to show latest on top (assumes higher ID = newer)
      const sorted = [...res.data].sort((a, b) => b.id - a.id);
      setTodos(sorted);
    } catch (err) {
      console.error("Failed to fetch todos", err);
    }
  };

  const handleAddTodo = async () => {
    if (!newTodo.trim()) return;
    try {
      const res = await axios.post(
        "http://localhost:5000/api/todos",
        { text: newTodo },
        { withCredentials: true }
      );
      setTodos([res.data, ...todos]); // Add new item at top
      setNewTodo("");
    } catch (err) {
      console.error("Failed to add todo", err);
    }
  };

  const handleEdit = async (id, newText) => {
    try {
      const res = await axios.put(
        `http://localhost:5000/api/todos/${id}`,
        { text: newText },
        { withCredentials: true }
      );
      setTodos(todos.map((todo) => (todo.id === id ? res.data : todo)));
    } catch (err) {
      console.error("Failed to edit todo", err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:5000/api/todos/${id}`, {
        withCredentials: true,
      });
      setTodos(todos.filter((todo) => todo.id !== id));
    } catch (err) {
      console.error("Failed to delete todo", err);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleAddTodo();
    }
  };

  return (
    <div className="todo-container">
      <div className="logout-top">
        <button onClick={onLogout} className="logout-button">
          Logout
        </button>
      </div>

      <div className="new-todo-container">
        <input
          type="text"
          value={newTodo}
          onChange={(e) => setNewTodo(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Enter a new todo"
        />
        <button onClick={handleAddTodo}>Add</button>
      </div>

      <h2>Todo List</h2>
      
      <ul className="todo-list">
        {todos.map((todo) => (
          <li key={todo.id} className="todo-item">
            <input
              type="text"
              value={todo.text}
              onChange={(e) => handleEdit(todo.id, e.target.value)}
            />
            <div className="todo-actions">
              <button onClick={() => handleEdit(todo.id, todo.text)}>Edit</button>
              <button onClick={() => handleDelete(todo.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default TodoApp;
