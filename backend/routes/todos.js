const express = require('express');
const router = express.Router();
const todos = require('../data/todos');

router.get('/', (req, res) => res.json(todos));

router.post('/', (req, res) => {
  const newTodo = { id: Date.now().toString(), text: req.body.text };
  todos.unshift(newTodo);
  res.status(201).json(newTodo);
});

router.put('/:id', (req, res) => {
  const todo = todos.find(t => t.id === req.params.id);
  if (!todo) return res.status(404).send();
  todo.text = req.body.text;
  res.json(todo);
});

router.delete('/:id', (req, res) => {
  const index = todos.findIndex(t => t.id === req.params.id);
  if (index === -1) return res.status(404).send();
  todos.splice(index, 1);
  res.status(204).send();
});

module.exports = router;
