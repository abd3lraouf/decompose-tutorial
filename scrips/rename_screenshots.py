#!/usr/bin/env python3
"""
Script to rename screenshots with descriptive names based on OCR content analysis.
"""

import os
import json
import shutil
import re

# Define the mapping of current filenames to new descriptive names
# Based on our OCR analysis
FILENAME_MAPPING = {
    # Main screens and overviews
    "9.Screenshot_20250508_072837.png": "01_main_todo_list.png",
    "10.Screenshot_20250508_072856.png": "02_in_progress_tasks.png",
    "11.Screenshot_20250508_072905.png": "03_productivity_tip.png",
    
    # Statistics screen
    "12.Screenshot_20250508_072916.png": "04_statistics_overview.png",
    
    # Settings screens
    "13.Screenshot_20250508_072923.png": "05_settings_light_mode.png",
    "21.Screenshot_20250508_073108.png": "06_settings_dark_mode.png",
    
    # Task management
    "14.Screenshot_20250508_072929.png": "07_create_task.png",
    "15.Screenshot_20250508_072938.png": "08_task_details.png",
    "16.Screenshot_20250508_072944.png": "09_edit_task.png",
    
    # More task views
    "17.Screenshot_20250508_073045.png": "10_todo_tasks.png",
    "18.Screenshot_20250508_073051.png": "11_in_progress_view.png", 
    "19.Screenshot_20250508_073058.png": "12_multiple_tasks_view.png",
    
    # Additional screens
    "20.Screenshot_20250508_073102.png": "13_statistics_detailed.png",
    "22.Screenshot_20250508_073120.png": "14_create_task_dark.png",
    "23.Screenshot_20250508_073124.png": "15_task_details_dark.png",
    "24.Screenshot_20250508_073127.png": "16_edit_task_dark.png"
}

def rename_screenshots():
    """Rename screenshots with more descriptive names."""
    # Create a backup directory
    backup_dir = "assets_backup"
    if not os.path.exists(backup_dir):
        os.makedirs(backup_dir)
    
    # First, create backups of all screenshots
    for filename in FILENAME_MAPPING.keys():
        src_path = os.path.join("assets", filename)
        if os.path.exists(src_path):
            backup_path = os.path.join(backup_dir, filename)
            shutil.copy2(src_path, backup_path)
            print(f"Created backup: {backup_path}")
    
    # Now rename the files
    for old_name, new_name in FILENAME_MAPPING.items():
        src_path = os.path.join("assets", old_name)
        dst_path = os.path.join("assets", new_name)
        
        if os.path.exists(src_path):
            shutil.copy2(src_path, dst_path)
            print(f"Renamed: {old_name} → {new_name}")
            
            # Remove original file after renaming
            os.remove(src_path)
            print(f"Removed original: {src_path}")
        else:
            print(f"Warning: Source file not found: {src_path}")
    
    # Update the README.md file to use the new filenames
    update_readme()

def update_readme():
    """Update README.md to use the new filenames."""
    readme_path = "README.md"
    
    if not os.path.exists(readme_path):
        print(f"Warning: README.md not found at {readme_path}")
        return
    
    with open(readme_path, 'r') as file:
        content = file.read()
    
    # Replace old filenames with new ones
    for old_name, new_name in FILENAME_MAPPING.items():
        content = content.replace(old_name, new_name)
    
    # Write updated content back to the README
    with open(readme_path, 'w') as file:
        file.write(content)
    
    print("Updated README.md with new filenames")

def cleanup_duplicate_files():
    """Remove any leftover original files if both versions exist."""
    print("Checking for duplicate files...")
    for old_name, new_name in FILENAME_MAPPING.items():
        old_path = os.path.join("assets", old_name)
        new_path = os.path.join("assets", new_name)
        
        if os.path.exists(old_path) and os.path.exists(new_path):
            os.remove(old_path)
            print(f"Removed duplicate: {old_path}")

if __name__ == "__main__":
    rename_screenshots()
    # Run cleanup to remove any leftover duplicates
    cleanup_duplicate_files()
    print("Screenshot renaming complete!") 