# 📊 Zeotap Assignment: ClickHouse & CSV Integration Dashboard

This is a full-stack assignment to integrate and test data source connectivity via a web interface. The user can either connect to a ClickHouse database or upload a CSV file. The backend is built with Spring Boot and the frontend with vanilla JavaScript, HTML, and CSS.

---

## 📁 Project Structure

zeotap-assignment/ │ ├── backend/ # Spring Boot application │ ├── src/main/java/com/example/zeotap_assignment/ │ │ ├── ClickHouseController.java │ │ ├── ClickHouseConnectionRequest.java │ │ └── FileUploadController.java │ └── ... │ ├── static/ # Static web interface │ ├── index.html │ ├── styles.css │ └── script.js │ └── README.md


---

## ⚙️ Setup Instructions

### 🧱 Prerequisites

- Java 17+
- Maven 3.6+
- ClickHouse instance (optional, for real DB testing)

---

## 🚀 Running the Backend (Spring Boot)

1. **Navigate to the Zeotap-assignment folder**

```bash
cd zeotap-assignment/

2. **Build and run the application
mvn clean install
mvn spring-boot:run

Backend will start at:

http://localhost:8080

🌐 Running the Frontend
Open the static/index.html file directly in your browser

It will start a local frontend server 

🔁 API Endpoints
1. 📡 Connect to ClickHouse
Endpoint:
POST http://localhost:8080/api/connect-clickhouse

Body (JSON):

json
{
  "host": "localhost",
  "port": 8123,
  "database": "default"
}

2. 📤 Upload CSV File
Endpoint:
POST http://localhost:8080/api/upload-flatfile
Form Data:

file: your CSV file

💡 Features
Dynamic UI: Choose between ClickHouse and CSV

ClickHouse connection test (simulated for now)

CSV upload with status handling

Simple JavaScript-based client

Clean status messages with emojis 😊

🙌 Author
Kashish Ahuja
Passionate about backend engineering, databases, and new technologies.
