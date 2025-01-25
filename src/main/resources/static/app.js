// API base URL
const API_URL = "/to-do/api/todos";

// Select DOM Elements
const todoForm = document.getElementById("todo-form");
const todoInput = document.getElementById("todo-input");
const todoList = document.getElementById("todo-list");
const markCompletedButton = document.getElementById("mark-completed");

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

// Event: Mark Selected as Completed
markCompletedButton.addEventListener("click", async () => {
    const selectedIds = Array.from(document.querySelectorAll(".todo-checkbox:checked"))
        .map((checkbox) => checkbox.closest(".todo-item").dataset.id);

    if (selectedIds.length === 0) {
        alert("No tasks selected!");
        return;
    }

    try {
        const success = await markAsCompleted(selectedIds);
        if (success) {
            selectedIds.forEach((id) => {
                const todoItem = document.querySelector(`.todo-item[data-id='${id}']`);
                if (todoItem) {
                    const span = todoItem.querySelector("span");
                    span.classList.add("completed");
                }
            });
        } else {
            alert("Failed to mark tasks as completed.");
        }
    } catch (error) {
        console.error("Error in Mark as Completed:", error);
        alert("An error occurred while marking tasks as completed.");
    }
});

// Function: Mark Todos as Completed
async function markAsCompleted(ids) {
    try {
        const response = await fetch(`${API_URL}/status/COMPLETED`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(ids),
        });

        return response.ok;
    } catch (error) {
        console.error("Error marking todos as completed:", error);
        return false;
    }
}

// Fetch Todos
async function fetchTodos() {
    try {
        const response = await fetch(API_URL);
        const todos = await response.json();
        todos.forEach(addTodoToDOM);
    } catch (error) {
        console.error("Error fetching todos:", error);
    }
}

// Create Todo
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

// Update Todo
async function updateTodoInBackend(id, updatedTodo) {
    const response = await fetch(`/to-do/api/todos/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedTodo),
    });

    if (!response.ok) {
        throw new Error("Failed to update todo");
    }

    return response.json(); // Ensure this matches the backend response
}


// Delete Todo
async function deleteTodoFromBackend(id) {
    try {
        await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    } catch (error) {
        console.error("Error deleting todo:", error);
    }
}

// Add Todo to DOM
function addTodoToDOM(todo) {
    const todoItem = document.createElement("li");
    todoItem.classList.add("todo-item");
    todoItem.dataset.id = todo.id;

    // Checkbox for selection
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.classList.add("todo-checkbox");

    // Task Title
    const span = document.createElement("span");
    span.textContent = todo.title;
    if (todo.completed) {
        span.classList.add("completed");
    }

    // Actions
    const actions = document.createElement("div");
    actions.classList.add("actions");

    const editButton = document.createElement("button");
    editButton.textContent = "Edit";
    editButton.classList.add("edit");

    const deleteButton = document.createElement("button");
    deleteButton.textContent = "Delete";
    deleteButton.classList.add("delete");

    actions.appendChild(editButton);
    actions.appendChild(deleteButton);

    todoItem.appendChild(checkbox);
    todoItem.appendChild(span);
    todoItem.appendChild(actions);

    todoList.appendChild(todoItem);

    // Add event listeners for Edit and Delete
    editButton.addEventListener("click", () => editTodo(todoItem));
    deleteButton.addEventListener("click", () => deleteTodo(todoItem));
}

// Edit Todo
async function editTodo(todoItem) {
    const span = todoItem.querySelector("span");
    const editButton = todoItem.querySelector(".edit");
    const id = todoItem.dataset.id;

    if (editButton.textContent === "Edit") {
        const input = document.createElement("input");
        input.type = "text";
        input.value = span.textContent || ""; // Set default value
        input.classList.add("todo-edit-input"); // Add a unique class
        todoItem.insertBefore(input, span);
        todoItem.removeChild(span);
        editButton.textContent = "Save";

        // Debugging: Log the created input
        console.log("Created input value:", input.value);
    } else {
        // Select the correct input by class
        const input = todoItem.querySelector(".todo-edit-input");

        // Debugging: Log the input value and confirm selection
        console.log("Selected input value before PUT:", input?.value);
        console.log("Selected input element:", input);

        if (!input || !input.value) {
            console.error("Input element is missing or has no value!");
            return;
        }

        const updatedTodo = { title: input.value };
        try {
            // Debugging: Log the object being sent to the backend
            console.log("Sending updatedTodo to backend:", updatedTodo);

            const updatedData = await updateTodoInBackend(id, updatedTodo);

            // Debugging: Log response from backend
            console.log("Response from backend:", updatedData);

            const newSpan = document.createElement("span");
            newSpan.textContent = updatedData.title;
            todoItem.insertBefore(newSpan, input);
            todoItem.removeChild(input);
            editButton.textContent = "Edit";
        } catch (error) {
            console.error("Failed to update todo:", error);
        }
    }
}

// Delete Todo
async function deleteTodo(todoItem) {
    const id = todoItem.dataset.id;
    await deleteTodoFromBackend(id);
    todoList.removeChild(todoItem);
}
