# Decompose Tutorial

![Kotlin](https://img.shields.io/badge/kotlin-2.1.20-blue.svg?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack_Compose-2025.04.01-green.svg?logo=jetpackcompose)
![Decompose](https://img.shields.io/badge/Decompose-3.3.0-purple.svg)

A comprehensive tutorial project showcasing **Decompose** for building reactive, component-based architectures in Kotlin Android development. This project demonstrates how to create modular, maintainable, and testable applications with proper separation of concerns.

## 🚀 Overview

Decompose provides powerful tools for creating independent UI components with their own lifecycle, state management, and navigation capabilities. This tutorial walks through implementing a Todo application with clean architecture principles and Decompose integration.

![App Overview](assets/9.Screenshot_20250508_072837.png)

## ✨ Key Features

- **Component-Based Architecture**: Modular UI components with encapsulated logic
- **State Management**: Reactive state with unidirectional data flow
- **Type-Safe Navigation**: Navigation between components without Fragments
- **Back Stack Management**: Support for navigation history
- **Lifecycle Management**: Clean component lifecycle handling

## 📱 Screenshot Gallery

Below is a comprehensive table of all screenshots demonstrating different aspects of the application:

| Category | Screenshot | Description | Key Concepts |
|----------|------------|-------------|-------------|
| **Overview** | ![App Overview](assets/9.Screenshot_20250508_072837.png) | Main app overview showing the Todo list interface | Root component visualization, Overall app layout |
| **Todo Management** | ![Todo List](assets/10.Screenshot_20250508_072856.png) | Todo list displaying items in different stages | List component, Item rendering |
| | ![Creating Todo](assets/11.Screenshot_20250508_072905.png) | Interface for creating a new Todo item | Form handling, User input |
| **Workflow Stages** | ![Todo Stage](assets/12.Screenshot_20250508_072916.png) | Todo in initial stage with play button | State representation, Action buttons |
| | ![In Progress Stage](assets/13.Screenshot_20250508_072923.png) | Todo in "In Progress" stage | State transition, Progress tracking |
| | ![Done Stage](assets/14.Screenshot_20250508_072929.png) | Todo in "Done" stage with checked checkbox | Completion state, Visual feedback |
| **Item Details** | ![Todo Details](assets/15.Screenshot_20250508_072938.png) | Detailed view of a Todo item | Detail component, Information display |
| | ![Edit Todo](assets/16.Screenshot_20250508_072944.png) | Interface for editing a Todo item | Edit mode, Form validation |
| **Navigation** | ![Navigation 1](assets/17.Screenshot_20250508_073045.png) | Navigation between screens | Stack navigation, Component transition |
| | ![Navigation 2](assets/18.Screenshot_20250508_073051.png) | Back navigation handling | Back stack, Component preservation |
| **Component Interaction** | ![Interaction 1](assets/19.Screenshot_20250508_073058.png) | Components communicating with each other | Event handling, Component communication |
| | ![Interaction 2](assets/20.Screenshot_20250508_073102.png) | State update between components | State propagation, Reactive updates |
| **Advanced Features** | ![Advanced 1](assets/21.Screenshot_20250508_073108.png) | Filtering and sorting capabilities | Filter component, State manipulation |
| | ![Advanced 2](assets/22.Screenshot_20250508_073120.png) | Todo statistics and metrics | Analytics component, Data aggregation |
| | ![Advanced 3](assets/23.Screenshot_20250508_073124.png) | Settings and configuration options | Preferences component, User customization |
| | ![Advanced 4](assets/24.Screenshot_20250508_073127.png) | Theme switching and appearance options | Theme component, Visual customization |

### Visual Tour of Todo States

The following sequence illustrates the lifecycle of a Todo item as it progresses through different states:

<p align="center">
  <img src="assets/12.Screenshot_20250508_072916.png" width="250" alt="Todo Stage"/>
  <img src="assets/13.Screenshot_20250508_072923.png" width="250" alt="In Progress Stage"/>
  <img src="assets/14.Screenshot_20250508_072929.png" width="250" alt="Done Stage"/>
</p>

1. **Todo Stage**: Initial state with play button to begin task
2. **In Progress Stage**: Active task with unchecked checkbox
3. **Done Stage**: Completed task with checked checkbox

## 🏗️ Architecture

This project follows Clean Architecture principles with a feature-based package structure combined with Decompose component architecture. This approach allows for better separation of concerns, high cohesion, and easier maintainability.

### Component Architecture

```mermaid
graph TD
    A[Application] --> B[Root Component]
    B --> C[TodoList Component]
    B --> D[TodoDetail Component]
    C --> E[Todo Items]
    D --> F[Todo Edit]
    
    style A fill:#f9f,stroke:#333,stroke-width:2px
    style B fill:#bbf,stroke:#333,stroke-width:2px
    style C fill:#dfd,stroke:#333,stroke-width:2px
    style D fill:#dfd,stroke:#333,stroke-width:2px
    style E fill:#ffd,stroke:#333,stroke-width:1px
    style F fill:#ffd,stroke:#333,stroke-width:1px
```

### Clean Architecture Layers

```mermaid
graph TB
    A[UI Layer<br>Compose/Components] --> B[Domain Layer<br>Use Cases]
    B --> C[Data Layer<br>Repositories]
    C --> D[Data Sources<br>Local/Remote]
    
    style A fill:#bbf,stroke:#333,stroke-width:2px
    style B fill:#dfd,stroke:#333,stroke-width:2px
    style C fill:#ffd,stroke:#333,stroke-width:2px
    style D fill:#fcc,stroke:#333,stroke-width:2px
```

### Package Structure

```
app/src/main/java/dev/abd3lraouf/learn/decompose/
├── core/                   # Shared core components
│   ├── data/               # Data layer implementations
│   │   └── repository/     # Repository implementations
│   ├── di/                 # Dependency injection
│   ├── domain/             # Domain layer (business logic)
│   │   ├── model/          # Domain models
│   │   └── repository/     # Repository interfaces
│   └── presentation/       # Shared UI components
├── features/               # Feature modules
│   ├── todo/               # Todo feature
│   │   ├── data/           # Todo data layer
│   │   ├── domain/         # Todo domain layer
│   │   │   └── usecase/    # Todo use cases
│   │   └── presentation/   # Todo UI components
│   │       ├── create/     # Todo creation
│   │       ├── details/    # Todo details
│   │       └── list/       # Todo list
│   ├── statistics/         # Statistics feature
│   │   ├── data/           # Statistics data layer
│   │   ├── domain/         # Statistics domain layer
│   │   │   ├── model/      # Statistics models
│   │   │   └── usecase/    # Statistics use cases
│   │   └── presentation/   # Statistics UI components
│   └── settings/           # Settings feature
│       ├── data/           # Settings data layer
│       ├── domain/         # Settings domain layer
│       │   └── model/      # Settings models
│       └── presentation/   # Settings UI components
├── navigation/             # Navigation components
└── ui/                     # UI components and themes
```

### Architecture Layers

1. **Domain Layer**:
   - Contains business logic and domain models
   - Defines repository interfaces
   - Contains use cases that orchestrate data flow between repositories and UI

2. **Data Layer**:
   - Implements repository interfaces from the domain layer
   - Manages data sources (local, remote, memory)

3. **Presentation Layer**:
   - Contains UI components built with Jetpack Compose
   - Implements Decompose components for UI logic
   - Follows the MVU (Model-View-Update) pattern with Decompose

## 🧭 Navigation with Decompose

Decompose handles navigation through a component hierarchy:

```mermaid
graph TD
    A[Root Navigation<br>RootComponent] --> B[Tab Navigation<br>TabComponent]
    B --> C[Todo Stack<br>TodoStackComponent]
    B --> D[Statistics Stack<br>StatisticsComponent]
    C --> E[Todo List]
    C --> F[Todo Details]
    C --> G[Todo Creation]
    
    style A fill:#bbf,stroke:#333,stroke-width:2px
    style B fill:#dfd,stroke:#333,stroke-width:2px
    style C fill:#ffd,stroke:#333,stroke-width:2px
    style D fill:#ffd,stroke:#333,stroke-width:2px
```

## 📋 Todo App Implementation

This tutorial implements a Todo application with a three-stage workflow:

```mermaid
stateDiagram-v2
    [*] --> Todo
    Todo --> InProgress: Click Play Button
    InProgress --> Done: Check Checkbox
    Done --> Todo: Uncheck Checkbox
```

### Stage Implementation 

1. **Todo Stage**:
   - Display: Play button (no checkbox)
   - Action: Clicking play button moves todo to "In Progress" stage

2. **In Progress Stage**:
   - Display: Checkbox
   - Action: Checking the box moves todo to "Done" stage

3. **Done Stage**:
   - Display: Checkbox (checked)
   - Action: Unchecking the box moves todo back to "Todo" stage

## 💻 Component Implementation

A simple Decompose component looks like:

```kotlin
interface TodoListComponent {
    val models: Value<Model>
    
    fun onTodoClicked(id: String)
    fun onAddTodoClicked()
    
    data class Model(
        val todos: List<TodoItem>,
        val isLoading: Boolean
    )
}

class DefaultTodoListComponent(
    private val todoRepository: TodoRepository,
    private val componentContext: ComponentContext,
    private val onTodoSelected: (String) -> Unit,
    private val onAddTodo: () -> Unit
) : TodoListComponent, ComponentContext by componentContext {
    // Implementation
}
```

## 🛠️ Setup Instructions

1. Clone the repository
   ```bash
   git clone https://github.com/abd3lraouf/decompose-tutorial.git
   ```
   
2. Open the project in Android Studio

3. Build and run the app on a device or emulator

## 📚 Technologies

- **Kotlin**: Modern programming language
- **Jetpack Compose**: UI toolkit
- **Decompose**: Component architecture
- **Kotlin Coroutines**: Asynchronous programming
- **Koin**: Dependency injection

## 🧪 Testing

Decompose's component-based architecture makes testing more straightforward:

```kotlin
@Test
fun `when todo is clicked, it navigates to details screen`() {
    // Create test component
    val component = DefaultTodoListComponent(
        mockRepository,
        TestComponentContext(),
        onTodoSelected = { selectedId ->
            // Verify correct ID is passed
            assertEquals("todo-1", selectedId)
        },
        onAddTodo = {}
    )
    
    // Trigger action
    component.onTodoClicked("todo-1")
}
```

## 👨‍💻 About the Author

**Abdelraouf Sabri** - Senior Android Developer

- Website: [abd3lraouf.dev](https://abd3lraouf.dev)
- GitHub: [@abd3lraouf](https://github.com/abd3lraouf)

---

⭐️ Star this repository if you find it helpful!
