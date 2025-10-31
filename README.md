SwiftBot QR Code Dance Project Overview
The SwiftBot QR Code Dance Project is a Java-based program that enables the SwiftBot robot to scan QR codes containing hexadecimal values and convert them into movement and LED patterns. The robot performs a dance based on these conversions, combining computer vision, number systems, and robotics control.
This project demonstrates key principles of object-oriented programming (OOP), hardware integration, and data validation using the SwiftBot API.
Features
QR Code Scanning – Reads hexadecimal values from QR codes using the SwiftBot camera.
Validation & Conversion – Validates the hex input and converts it to binary, decimal, and octal equivalents.
Movement Control –
Forward motion for binary 1
Spin motion for binary 0
LED Control – LED colors are determined using the decimal equivalent (red, green, blue formula).
Speed Calculation – Derived from the octal equivalent; automatically capped or boosted if out of range.
Saving & Logging – Saves all scanned hex values to a text file (hex_log.txt).
Repeat Functionality – Option to repeat the last dance or scan a new QR code.
Error Handling – Detects invalid hex codes, excessive values, or missing QR scans.
Why It’s Useful
This project demonstrates how software logic can control hardware behaviour. It is especially useful for students or developers learning about:
Data representation (hexadecimal, binary, octal, decimal)
Java OOP architecture and modular design
Real-world API integration with robotics hardware
Robust error handling and file management
Getting Started Prerequisites
Hardware: SwiftBot robot with camera and underlights enabled
Software:
Java 17 or newer
SwiftBot API library installed
Raspberry Pi OS (with I2C enabled)
Running the Program
Clone or download this repository:
git clone https://github.com/Hmz441/SwiftBot-QR-Project.git cd swiftbot-dance
Compile and run the program:
javac dance_project.java java dance_project
Follow the on-screen menu:
1: Scan QR Code
2: Save Hex Values
3: Exit
Example QR Code Format
Each QR code can contain up to five colon-separated hexadecimal values.
1F:2A:3B:4C:5D
Project Structure File/Class Purpose dance_project.java Main entry point; handles menu navigation, button logic, and orchestration. QRText.java Scans and validates QR code input. Converter.java Converts hexadecimal to binary, decimal, and octal. Speed.java Calculates and regulates movement speed. LED.java Manages LED color settings and fade effects. Dance.java Executes the robot’s dance routine based on binary values. Saving.java Handles file saving and viewing of scanned hex logs. How It Works
User scans a QR code → QRText decodes it into hex values.
Hex values are validated → Only proper 1–2 digit hex codes are accepted.
Values are converted → Converter produces binary, decimal, and octal outputs.
Speed and LEDs are set → Calculated from conversions using Speed and LED.
Dance routine plays → Dance executes moves for each binary digit.
Results are saved → Saving logs all hex values to a text file.
Error Handling Error Type Response Invalid QR code Displays an error and waits for a new scan. More than 5 values Prompts user to rescan a smaller set. Invalid hex values Ignores invalid entries with a warning. File I/O errors Displays an error message but keeps program running. Maintainer
Author: Hamza Abdullahi Module: Programming Applications / Software Design and Implementation Institution: Brunel University London
License
This project was developed for educational use and demonstration purposes. You may freely modify or extend it for academic or research applications.
