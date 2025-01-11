// API base URL
const API_URL = "/to-do/api/todos";

// Select DOM Elements
const todoForm = document.getElementById("todo-form");
const todoInput = document.getElementById("todo-input");
const todoList = document.getElementById("todo-list");

// Load Todos on Page Load
document.addEventListener("DOMContentLoaded", fetchTodos);

// Event: Add ToDo
todoForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const todoText = todoInput.value.trim();
  if (todoText) {
    const newTodo = await createTodo({ title: todoText });
    addTodoToDOM(newTodo);
    todoInput.value = ""; // Clear input field
  }
});

// Function: Fetch Todos from Backend
async function fetchTodos() {
  try {
    const response = await fetch(API_URL);
    const todos = await response.json();
    todos.forEach((todo) => addTodoToDOM(todo));
  } catch (error) {
    console.error("Error fetching todos:", error);
  }
}

// Function: Add Todo to Backend
async function createTodo(todo) {
  try {
    const response = await fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(todo),
    });
    return await response.json();
  } catch (error) {
    console.error("Error creating todo:", error);
  }
}

// Function: Update Todo in Backend
async function updateTodoInBackend(id, updatedTodo) {
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(updatedTodo),
    });
    return await response.json();
  } catch (error) {
    console.error("Error updating todo:", error);
  }
}

// Function: Delete Todo from Backend
async function deleteTodoFromBackend(id) {
  try {
    await fetch(`${API_URL}/${id}`, { method: "DELETE" });
  } catch (error) {
    console.error("Error deleting todo:", error);
  }
}

// Function: Add Todo to DOM
function addTodoToDOM(todo) {
  const todoItem = document.createElement("li");
  todoItem.classList.add("todo-item");
  todoItem.dataset.id = todo.id;

  todoItem.innerHTML = `
    <span>${todo.title}</span>
    <button class="edit">Edit</button>
    <button class="delete">Delete</button>
  `;

  todoList.appendChild(todoItem);
  addEventListeners(todoItem);
}

// Function: Add Event Listeners to ToDo Actions
function addEventListeners(todoItem) {
  const editButton = todoItem.querySelector(".edit");
  const deleteButton = todoItem.querySelector(".delete");

  editButton.addEventListener("click", () => editTodo(todoItem));
  deleteButton.addEventListener("click", () => deleteTodo(todoItem));
}

// Function: Edit ToDo
async function editTodo(todoItem) {
  const span = todoItem.querySelector("span");
  const editButton = todoItem.querySelector(".edit");
  const id = todoItem.dataset.id;

  if (editButton.textContent === "Edit") {
    const input = document.createElement("input");
    input.type = "text";
    input.value = span.textContent;
    todoItem.insertBefore(input, span);
    todoItem.removeChild(span);
    editButton.textContent = "Save";
  } else {
    const input = todoItem.querySelector("input");
    const updatedTodo = { title: input.value };
    const updatedData = await updateTodoInBackend(id, updatedTodo);

    const newSpan = document.createElement("span");
    newSpan.textContent = updatedData.title;
    todoItem.insertBefore(newSpan, input);
    todoItem.removeChild(input);
    editButton.textContent = "Edit";
  }
}

// Function: Delete ToDo
async function deleteTodo(todoItem) {
  const id = todoItem.dataset.id;
  await deleteTodoFromBackend(id);
  todoList.removeChild(todoItem);
}