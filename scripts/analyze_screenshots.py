#!/usr/bin/env python3
"""
Script to analyze screenshots using OCR and categorize them for README organization.
"""

import os
import asyncio
import json
from mcp_ocr import perform_ocr
from collections import defaultdict

# Categories for organizing screenshots
CATEGORIES = {
    "app_overview": ["Todo List", "overview", "main screen"],
    "task_management": ["task", "todo", "create", "edit", "add", "delete", "complete"],
    "task_states": ["TODO", "IN_PROGRESS", "DONE", "status"],
    "statistics": ["Statistics", "stats", "completed", "pending", "priority"],
    "navigation": ["navigation", "screen", "back", "menu"],
    "settings": ["settings", "preferences", "theme", "options"],
    "details": ["details", "view", "description", "info"]
}

async def analyze_screenshot(filename):
    """Analyze a single screenshot using OCR and return its content and categories."""
    filepath = os.path.join("assets", filename)
    print(f"Analyzing {filename}...")
    
    try:
        # Extract text using OCR
        text = await perform_ocr(filepath, config="--psm 6")
        
        # Determine categories based on content
        detected_categories = []
        text_lower = text.lower()
        
        for category, keywords in CATEGORIES.items():
            for keyword in keywords:
                if keyword.lower() in text_lower:
                    detected_categories.append(category)
                    break
        
        # If no categories were detected, mark as "other"
        if not detected_categories:
            detected_categories = ["other"]
            
        return {
            "filename": filename,
            "text": text,
            "categories": detected_categories,
            "priority": get_priority(filename, detected_categories, text_lower)
        }
    except Exception as e:
        print(f"Error processing {filename}: {e}")
        return {
            "filename": filename,
            "text": f"Error: {str(e)}",
            "categories": ["error"],
            "priority": 0
        }

def get_priority(filename, categories, text_lower):
    """Determine display priority of screenshot based on content and filename."""
    priority = 0
    
    # Extract numerical order from filename
    try:
        num = int(filename.split('.')[0])
        # Lower numbers should appear first
        priority += 100 - num
    except:
        pass
    
    # Prioritize overview and main screens
    if "app_overview" in categories:
        priority += 50
    
    # Prioritize task states explanation
    if "task_states" in categories and ("workflow" in text_lower or "stages" in text_lower):
        priority += 40
        
    # Prioritize task management features
    if "task_management" in categories:
        priority += 30
        
    # Statistics are important explanatory screens
    if "statistics" in categories:
        priority += 25
        
    return priority

async def process_all_screenshots():
    """Process all screenshots in the assets folder."""
    screenshots = [f for f in os.listdir("assets") if f.endswith(".png")]
    results = []
    
    # Process screenshots in parallel
    tasks = [analyze_screenshot(filename) for filename in screenshots]
    results = await asyncio.gather(*tasks)
    
    # Sort by priority
    results.sort(key=lambda x: x["priority"], reverse=True)
    
    # Group by category for easier organization
    categories = defaultdict(list)
    for result in results:
        for category in result["categories"]:
            categories[category].append(result)
    
    # Save results to file
    with open("temp_ocr_results/screenshot_analysis.json", "w") as f:
        json.dump(results, f, indent=2)
        
    # Save category groupings
    with open("temp_ocr_results/screenshot_categories.json", "w") as f:
        categories_dict = {k: [r["filename"] for r in v] for k, v in categories.items()}
        json.dump(categories_dict, f, indent=2)
        
    # Generate recommended README organization
    generate_readme_recommendations(results, categories)
    
def generate_readme_recommendations(results, categories):
    """Generate recommendations for README organization."""
    with open("temp_ocr_results/readme_recommendations.md", "w") as f:
        f.write("# Screenshot Organization Recommendations\n\n")
        
        # Overview screens
        f.write("## App Overview\n\n")
        overview_shots = categories.get("app_overview", [])
        for shot in overview_shots[:2]:  # Limit to top 2
            f.write(f"- `{shot['filename']}`: {shot['text'].splitlines()[0][:50]}...\n")
        
        # Task States section
        f.write("\n## Task Workflow\n\n")
        task_state_shots = [r for r in results if "task_states" in r["categories"]]
        for shot in task_state_shots[:3]:  # Limit to top 3
            f.write(f"- `{shot['filename']}`: {shot['text'].splitlines()[0][:50]}...\n")
        
        # Task Management
        f.write("\n## Task Management\n\n")
        task_shots = categories.get("task_management", [])
        for shot in task_shots[:4]:  # Top 4
            f.write(f"- `{shot['filename']}`: {shot['text'].splitlines()[0][:50]}...\n")
        
        # Statistics
        f.write("\n## Statistics and Reports\n\n")
        stat_shots = categories.get("statistics", [])
        for shot in stat_shots[:2]:  # Top 2
            f.write(f"- `{shot['filename']}`: {shot['text'].splitlines()[0][:50]}...\n")
        
        # Navigation and Other Features
        f.write("\n## Other Features\n\n")
        other_shots = categories.get("navigation", []) + categories.get("settings", [])
        for shot in other_shots[:3]:  # Top 3
            f.write(f"- `{shot['filename']}`: {shot['text'].splitlines()[0][:50]}...\n")
        
        # Suggested README structure
        f.write("\n\n## Suggested README Structure\n\n")
        f.write("```markdown\n")
        f.write("# Decompose Tutorial\n\n")
        f.write("![App Overview](assets/MAIN_OVERVIEW_SCREENSHOT.png)\n\n")
        f.write("## Key Features\n\n")
        f.write("- Feature 1\n- Feature 2\n\n")
        f.write("## Todo App Workflow\n\n")
        f.write("<p align=\"center\">\n")
        f.write("  <img src=\"assets/TODO_STATE.png\" width=\"250\" alt=\"Todo Stage\"/>\n")
        f.write("  <img src=\"assets/IN_PROGRESS_STATE.png\" width=\"250\" alt=\"In Progress Stage\"/>\n")
        f.write("  <img src=\"assets/DONE_STATE.png\" width=\"250\" alt=\"Done Stage\"/>\n")
        f.write("</p>\n\n")
        f.write("## Task Management\n\n")
        f.write("### Creating Tasks\n")
        f.write("<img src=\"assets/CREATE_TASK.png\" width=\"250\" alt=\"Create Task\"/>\n\n")
        f.write("### Task Details and Editing\n")
        f.write("<img src=\"assets/TASK_DETAILS.png\" width=\"250\" alt=\"Task Details\"/>\n\n")
        f.write("## Statistics and Insights\n")
        f.write("<img src=\"assets/STATISTICS.png\" width=\"250\" alt=\"Statistics\"/>\n\n")
        f.write("```\n")

# Run the script
if __name__ == "__main__":
    asyncio.run(process_all_screenshots()) 