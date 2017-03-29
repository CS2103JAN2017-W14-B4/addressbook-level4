#!/usr/bin/env bash
java -jar Collate-TUI.jar collate from src/main to collated/main include java, fxml, css
java -jar Collate-TUI.jar collate from src/test to collated/test include java
java -jar Collate-TUI.jar collate from unused to collated/unused include java, fxml, css
