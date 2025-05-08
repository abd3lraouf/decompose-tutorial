# MCP OCR - Optical Character Recognition with MCP

This document explains how to use the MCP OCR library, a production-grade OCR server built using Model Context Protocol (MCP) that provides text extraction capabilities from images.

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Basic Usage](#basic-usage)
- [Advanced Options](#advanced-options)
- [Troubleshooting](#troubleshooting)
- [API Reference](#api-reference)

## Installation

### Using pip

```bash
pip install mcp-ocr
```

### Using uv

```bash
uv pip install mcp-ocr
```

### Tesseract Requirements

MCP OCR depends on Tesseract OCR engine, which will be installed automatically on supported platforms:

- **macOS**: Installation via Homebrew
- **Linux**: Installation via apt, dnf, or pacman package managers
- **Windows**: Manual installation required (see below)

#### Manual Tesseract Installation on Windows

1. Download the installer from the [UB-Mannheim Tesseract repository](https://github.com/UB-Mannheim/tesseract/wiki)
2. Run the installer and follow the prompts
3. Make sure to add Tesseract to your system PATH during installation
4. Verify installation by running `tesseract --version` in a command prompt

## Configuration

### Configuring Claude for Desktop

To use MCP OCR with Claude for Desktop, you need to add it to your Claude configuration:

1. Open or create the configuration file at: `~/Library/Application Support/Claude/claude_desktop_config.json`
2. Add the MCP OCR server configuration:

```json
{
    "mcpServers": {
        "ocr": {
            "command": "python",
            "args": ["-m", "mcp_ocr"]
        }
    }
}
```

## Basic Usage

MCP OCR provides two main functions:

1. `perform_ocr`: Extract text from images
2. `get_supported_languages`: List available OCR languages

### Extracting Text from Images

You can extract text from three types of image sources:

#### 1. From a Local File

```python
perform_ocr("/path/to/image.jpg")
```

#### 2. From a URL

```python
perform_ocr("https://example.com/image.jpg")
```

#### 3. From Image Bytes

```python
# When you have raw image data
perform_ocr(image_bytes)
```

### Checking Supported Languages

To see which languages are available for OCR:

```python
get_supported_languages()
```

This will return a list of language codes, such as:
- "eng" (English)
- "osd" (Orientation and script detection)
- "snum" (Script numerals)

## Advanced Options

### Language Selection

You can specify the OCR language using the `language` parameter:

```python
perform_ocr("/path/to/image.jpg", language="eng")
```

The default language is "eng" (English). Use `get_supported_languages()` to see all available options.

### Tesseract Configuration

You can customize the Tesseract OCR engine behavior using the `config` parameter:

```python
perform_ocr("/path/to/image.jpg", config="--oem 3 --psm 6")
```

Common configuration options:

- `--oem` (OCR Engine Mode):
  - 0: Legacy engine only
  - 1: Neural nets LSTM engine only
  - 2: Legacy + LSTM engines
  - 3: Default, based on what is available

- `--psm` (Page Segmentation Mode):
  - 0: Orientation and script detection only
  - 1: Automatic page segmentation with OSD
  - 3: Fully automatic page segmentation, no OSD
  - 6: Assume a single uniform block of text
  - 7: Treat the image as a single text line
  - 8: Treat the image as a single word
  - 10: Treat the image as a single character

## Troubleshooting

### Common Issues

#### 1. Tesseract Not Found

If you see an error like "Tesseract not found", the package will attempt to install Tesseract automatically. If automatic installation fails:

- **macOS**: `brew install tesseract`
- **Ubuntu/Debian**: `sudo apt-get install tesseract-ocr`
- **Fedora**: `sudo dnf install tesseract`
- **Arch Linux**: `sudo pacman -S tesseract`
- **Windows**: Download from the [UB-Mannheim Tesseract repository](https://github.com/UB-Mannheim/tesseract/wiki)

#### 2. Poor OCR Results

If you're getting incorrect or no text:

- Ensure the image is clear and high quality
- Try different PSM modes (e.g., `--psm 6` for block text, `--psm 7` for single line)
- Try preprocessing the image (increase contrast, convert to grayscale)

#### 3. Language Pack Missing

If you get an "Unsupported language" error:

- Check available languages with `get_supported_languages()`
- Install additional language packs:
  - **Ubuntu/Debian**: `sudo apt-get install tesseract-ocr-<lang>` (replace `<lang>` with language code)
  - **macOS**: `brew install tesseract-lang`

## API Reference

### perform_ocr

```python
perform_ocr(
    input_data: Union[str, bytes],
    language: str = "eng",
    config: str = "--oem 3 --psm 6"
) -> str
```

**Parameters:**
- `input_data`: Path to image file, URL to image, or raw image bytes
- `language`: Tesseract language code (default: "eng")
- `config`: Tesseract configuration string (default: "--oem 3 --psm 6")

**Returns:**
- Extracted text as a string

### get_supported_languages

```python
get_supported_languages() -> list[str]
```

**Parameters:**
- None

**Returns:**
- List of supported language codes 