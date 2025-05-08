# Decompose Tutorial

![Kotlin](https://img.shields.io/badge/kotlin-2.1.20-blue.svg?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack_Compose-2025.04.01-green.svg?logo=jetpackcompose)
![Decompose](https://img.shields.io/badge/Decompose-3.3.0-purple.svg)

<p align="center">
  <img src="assets/organize.png" width="22%" alt="Organize"/>
  <img src="assets/focus.png" width="22%" alt="Focus"/>
  <img src="assets/dont-overwhelm.png" width="22%" alt="Don't Overwhelm"/>
  <img src="assets/enjoy.png" width="22%" alt="Enjoy"/>
</p>

<p align="center">
  <em>Decompose helps you build applications that are organized, focused, not overwhelming, and enjoyable to develop.</em>
</p>

A comprehensive tutorial project showcasing **Decompose** for building reactive, component-based architectures in Kotlin Android development. This project demonstrates how to create modular, maintainable, and testable applications with proper separation of concerns.

<p align="center">
  <img src="assets/01_main_todo_list.png" width="250" alt="Todo State - Tasks Ready to Start"/>
</p>

## 📱 App Overview

This Todo app demonstrates Decompose's capabilities to manage component lifecycles, state, and navigation in a clean, predictable way. It follows reactive programming principles and showcases proper separation of concerns.

The app allows users to:
- Create and manage tasks with different priorities
- Track task progress through multiple states
- View statistics about task completion
- Customize the app appearance

## ✨ Key Features

- **Component-Based Architecture**: Independent UI components with their own lifecycle
- **Predictable State Management**: Immutable state and unidirectional data flow
- **Type-Safe Navigation**: No more fragment transactions or intent flags
- **Multi-Module Support**: Clean separation between features and layers
- **Composable-Friendly Design**: Natural integration with Jetpack Compose
- **Workflow Management**: Task state progression from Todo → In Progress → Done

## 📋 Task Workflow Management

The app demonstrates a clear task progression workflow with different states:

<div align="center">
  <h3>Todo → In Progress → Done</h3>
</div>

<p align="center">
  <img src="assets/01_main_todo_list.png" width="250" alt="Todo State - Tasks Ready to Start"/>
  <img src="assets/02_in_progress_tasks.png" width="250" alt="In Progress State - Active Tasks"/>
  <img src="assets/03_productivity_tip.png" width="250" alt="Productivity Tips for Task Management"/>
</p>

The app provides smart workflow features:
- **Contextual tips** for better productivity
- **Visual indicators** for task status
- **Priority levels** shown with color coding
- **Deadline tracking** for time-sensitive tasks

## 🛠️ Task Management Features

<div align="center">
  <table>
    <tr>
      <td align="center"><img src="assets/07_create_task.png" width="250" alt="Create Task Form"/></td>
      <td align="center"><img src="assets/08_task_details.png" width="250" alt="Task Details View"/></td>
    </tr>
    <tr>
      <td align="center"><b>Create New Tasks</b><br>Add titles, descriptions, tags, deadlines, and priorities</td>
      <td align="center"><b>Task Details</b><br>View complete information and manage task status</td>
    </tr>
  </table>
</div>

## 📊 Statistics and Customization

<div align="center">
  <table>
    <tr>
      <td align="center"><img src="assets/04_statistics_overview.png" width="250" alt="Task Statistics"/></td>
      <td align="center"><img src="assets/05_settings_light_mode.png" width="250" alt="Settings Screen"/></td>
    </tr>
    <tr>
      <td align="center"><b>Productivity Stats</b><br>Track completion rates and task distribution</td>
      <td align="center"><b>App Settings</b><br>Customize theme and app preferences</td>
    </tr>
  </table>
</div>

## 🖥️ Dark Mode Support

<p align="center">
  <img src="assets/15_task_details_dark.png" width="250" alt="Task Details in Dark Mode"/>
  <img src="assets/16_edit_task_dark.png" width="250" alt="Task Editing in Dark Mode"/>
</p>

## 🧩 Technical Architecture

This application demonstrates Decompose's powerful capabilities for component-based architecture:

### Core Architecture

```mermaid
graph TD
    A[Application] --> B[RootComponent]
    B --> C[TodoListComponent]
    B --> D[StatisticsComponent]
    B --> E[SettingsComponent]
    
    C --> F[TaskItemComponent]
    C --> G[TaskEditorComponent]
    
    style A fill:#f9f,stroke:#333,stroke-width:2px
    style B fill:#bbf,stroke:#333,stroke-width:2px
    style C fill:#dfd,stroke:#333,stroke-width:2px
    style D fill:#dfd,stroke:#333,stroke-width:2px
    style E fill:#dfd,stroke:#333,stroke-width:2px
```

### Task Workflow State Management

```mermaid
stateDiagram-v2
    classDef todo fill:#ffcccb,stroke:#333,stroke-width:1px
    classDef inProgress fill:#ffffcc,stroke:#333,stroke-width:1px
    classDef done fill:#ccffcc,stroke:#333,stroke-width:1px
    
    [*] --> TODO : New Task
    
    TODO --> IN_PROGRESS : Start Task
    IN_PROGRESS --> DONE : Complete Task
    IN_PROGRESS --> TODO : Return to Todo
    DONE --> IN_PROGRESS : Reopen Task
    
    class TODO todo
    class IN_PROGRESS inProgress
    class DONE done
```

### Decompose Integration with Clean Architecture

```mermaid
graph TD
    subgraph Presentation
        A[Jetpack Compose UI] -->|Observes| B[Decompose Component]
        B -->|Contains| C[Component State]
    end
    
    subgraph Domain
        E[Use Cases]
        F[Domain Models]
        G[Repository Interfaces]
    end
    
    subgraph Data
        H[Repository Implementations]
        I[Data Sources]
    end
    
    B -->|Invokes| E
    E -->|Uses| F
    E -->|Calls| G
    G <--Implementation--> H
    H -->|Accesses| I
    
    style A fill:#dfd,stroke:#333,stroke-width:2px
    style B fill:#bbf,stroke:#333,stroke-width:2px
    style E fill:#ffd,stroke:#333,stroke-width:2px
    style H fill:#fcc,stroke:#333,stroke-width:2px
```

## 📚 Additional Resources

- [Decompose Documentation](https://arkivanov.github.io/Decompose/)
- [Sample Code Repository](https://github.com/arkivanov/Decompose-samples)
- [Component-Based Architecture Guide](https://arkivanov.github.io/Decompose/component/overview/)

## 🔧 Setup and Installation

1. Clone the repository
2. Open in Android Studio Iguana or later
3. Build and run on your device or emulator

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.
