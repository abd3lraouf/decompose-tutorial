# Decompose Tutorial

![Kotlin](https://img.shields.io/badge/kotlin-2.1.20-blue.svg?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack_Compose-2025.04.01-green.svg?logo=jetpackcompose)
![Decompose](https://img.shields.io/badge/Decompose-3.3.0-purple.svg)

A comprehensive tutorial project showcasing **Decompose** for building reactive, component-based architectures in Kotlin Android development. This project demonstrates how to create modular, maintainable, and testable applications with proper separation of concerns.

<p align="center">
  <img src="assets/main.png" width="300" alt="Todo App Main Screen"/>
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

### Creating New Tasks

<p align="center">
  <img src="assets/07_create_task.png" width="270" alt="Create Task Form - Light Mode"/>
  <img src="assets/14_create_task_dark.png" width="270" alt="Create Task Form - Dark Mode"/>
</p>

Task creation includes:
- Customizable titles and descriptions
- Tag selection for categorization
- Deadline setting with date picker
- Priority selection (Low/Medium/High)

### Task Details and Editing

<div align="center">
  <table>
    <tr>
      <td align="center"><img src="assets/08_task_details.png" width="250" alt="Task Details View"/></td>
      <td align="center"><img src="assets/09_edit_task.png" width="250" alt="Task Editing Interface"/></td>
    </tr>
    <tr>
      <td align="center"><b>Task Details</b></td>
      <td align="center"><b>Task Editing</b></td>
    </tr>
  </table>
</div>

The app supports:
- Detailed task information viewing
- Status transitions between workflow stages
- Tag and metadata editing
- Priority level adjustments

### Multiple Task Views

<p align="center">
  <img src="assets/10_todo_tasks.png" width="230" alt="Todo Task List"/>
  <img src="assets/11_in_progress_view.png" width="230" alt="In Progress Task View"/>
  <img src="assets/12_multiple_tasks_view.png" width="230" alt="Multiple Tasks in Different States"/>
</p>

Task organization features:
- Grouped by current status
- Sorting options for different views
- Clear visual separation between states
- Intuitive transitions between states

## 📊 Statistics and Insights

Track your productivity with comprehensive statistics:

<div align="center">
  <table>
    <tr>
      <td align="center"><img src="assets/04_statistics_overview.png" width="250" alt="Task Statistics - Light Mode"/></td>
      <td align="center"><img src="assets/13_statistics_detailed.png" width="250" alt="Task Statistics - Dark Mode"/></td>
    </tr>
    <tr>
      <td align="center"><b>Light Mode Statistics</b></td>
      <td align="center"><b>Dark Mode Statistics</b></td>
    </tr>
  </table>
</div>

The statistics dashboard provides insights on:
- **Total task count** with visual distribution
- **Completion rates** shown as percentage
- **Task distribution by priority** (High/Medium/Low)
- **Progress tracking** across workflow stages

## ⚙️ Settings and Customization

Personalize your experience with various settings:

<p align="center">
  <img src="assets/05_settings_light_mode.png" width="250" alt="Settings Screen - Light Mode"/>
  <img src="assets/06_settings_dark_mode.png" width="250" alt="Settings Screen - Dark Mode"/>
</p>

Customization options include:
- **Theme switching** between light and dark modes
- **Documentation access** for app usage help
- **Feedback mechanism** for app rating
- **Version information** and release details

## 🖥️ Dark Mode Support

The app offers a complete dark mode experience across all screens:

<div align="center">
  <table>
    <tr>
      <td align="center"><img src="assets/15_task_details_dark.png" width="250" alt="Task Details in Dark Mode"/></td>
      <td align="center"><img src="assets/16_edit_task_dark.png" width="250" alt="Task Editing in Dark Mode"/></td>
    </tr>
    <tr>
      <td align="center"><b>Task Details</b></td>
      <td align="center"><b>Task Editing</b></td>
    </tr>
  </table>
</div>

Dark mode provides:
- Reduced eye strain in low-light environments
- Battery savings on OLED screens
- Consistent visual styling throughout the app
- Automatic adaptation to system settings

## 🧩 Technical Architecture

This application demonstrates:

1. **Component Hierarchy**: Root → Screen → Child Components
2. **State Management**: Each component has its own StateKeeper
3. **Navigation**: Type-safe navigation using Decompose
4. **Dependency Injection**: Clean provision of dependencies to components
5. **SaveInstanceState**: Automatic state restoration across configuration changes

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
