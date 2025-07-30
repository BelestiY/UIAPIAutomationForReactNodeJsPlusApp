const express = require('express');
const cors = require('cors');
const session = require('express-session');
const app = express();
const PORT = 5000;

app.use(express.json());

app.use(
  cors({
    origin: 'http://localhost:3000',
    credentials: true,
  })
);

app.use(
  session({
    secret: 'my-secret',
    resave: false,
    saveUninitialized: true,
    cookie: { secure: false },
  })
);

let todos = [];
let users = [
  { username: 'admin', password: 'password' } // hardcoded user
];

app.post('/api/login', (req, res) => {
  const { username, password } = req.body;
  const user = users.find(
    (u) => u.username === username && u.password === password
  );
  if (user) {
    req.session.user = username;
    res.json({ success: true });
  } else {
    res.status(401).json({ message: 'Invalid credentials' });
  }
});

app.post('/api/logout', (req, res) => {
  req.session.destroy();
  res.json({ success: true });
});

app.get('/api/check-auth', (req, res) => {
  if (req.session.user) {
    res.json({ loggedIn: true });
  } else {
    res.status(401).json({ loggedIn: false });
  }
});

app.get('/api/todos', (req, res) => {
  if (!req.session.user) return res.status(401).json({ message: 'Unauthorized' });
  res.json(todos);
});

app.post('/api/todos', (req, res) => {
  if (!req.session.user) return res.status(401).json({ message: 'Unauthorized' });
  const { text } = req.body;
  const newTodo = { id: Date.now(), text };
  todos.unshift(newTodo); // newest first
  res.status(201).json(newTodo);
});

app.put('/api/todos/:id', (req, res) => {
  if (!req.session.user) return res.status(401).json({ message: 'Unauthorized' });
  const { id } = req.params;
  const { text } = req.body;
  const todo = todos.find((t) => t.id === parseInt(id));
  if (todo) {
    todo.text = text;
    res.json(todo);
  } else {
    res.status(404).json({ message: 'Todo not found' });
  }
});

app.delete('/api/todos/:id', (req, res) => {
  if (!req.session.user) return res.status(401).json({ message: 'Unauthorized' });
  const { id } = req.params;
  todos = todos.filter((t) => t.id !== parseInt(id));
  res.json({ success: true });
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
