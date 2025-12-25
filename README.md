# Student Record System

A comprehensive database management system for university student and lecturer records, built with JavaFX and MySQL.

## 📋 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Database Schema](#database-schema)
- [Architecture](#architecture)
- [Contributing](#contributing)

## ✨ Features

- **Student Management**
  - View student information
  - Retrieve student courses
  - Find student supervisors

- **Lecturer Management**
  - View lecturer profiles
  - Display lecturer courses
  - List supervised students

- **Database Integration**
  - Secure MySQL database connections
  - Prepared statements for SQL injection prevention
  - Proper resource management

## 📁 Project Structure

```
DB-PROJECT-BACKEND/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── app/
│   │   │   │   ├── Start.java              # Application entry point
│   │   │   │   ├── controller/
│   │   │   │   │   └── MainController.java # UI controller
│   │   │   │   ├── model/
│   │   │   │   │   ├── Student.java        # Student entity
│   │   │   │   │   ├── Lecturer.java       # Lecturer entity
│   │   │   │   │   ├── Course.java         # Course entity
│   │   │   │   │   ├── Department.java     # Department entity
│   │   │   │   │   └── Enrollment.java     # Enrollment entity
│   │   │   │   ├── database/
│   │   │   │   │   └── DatabaseHelper.java # Database operations
│   │   │   └── module-info.java            # Java module configuration
│   │   └── resources/
│   │       └── app/
│   │           └── mainPage.fxml           # UI layout
│   └── test/                               # Test files
├── DB_MySQL.sql                            # Database schema (MySQL)
├── Examples_MySQL.sql                      # Sample data (MySQL)
├── QUERY.txt                               # SQL query reference
├── pom.xml                                 # Maven configuration
└── README.md                               # This file
```

## 🔧 Prerequisites

- **Java Development Kit (JDK) 21** or higher
- **Maven 3.6+**
- **MySQL Server 8.0+**
- **JavaFX SDK 21** (included via Maven dependencies)

## 📦 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd DB-PROJECT-BACKEND
   ```

2. **Set up the database**
   ```bash
   # Create the database
   mysql -u root -p < DB_MySQL.sql
   
   # Load sample data
   mysql -u root -p < Examples_MySQL.sql
   ```

3. **Configure database connection**
   
   Edit `src/main/java/app/database/DatabaseHelper.java`:
   ```java
   String url = "jdbc:mysql://localhost:3306/StudentSystemDB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
   String user = "root";
   String password = "your_password";
   ```

## ⚙️ Configuration

### Database Configuration

Edit `src/main/java/app/database/DatabaseHelper.java` to set your MySQL credentials:
```java
String url = "jdbc:mysql://localhost:3306/StudentSystemDB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
String user = "root";
String password = "your_password";
```

## 🚀 Usage

### Running with Maven

```bash
# Compile and run
mvn clean javafx:run

# Or compile first, then run
mvn clean compile
mvn javafx:run
```

### Building an Executable JAR

```bash
# Package the application
mvn clean package

# Run the JAR
java --module-path <path-to-javafx-sdk>/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar target/student-record-system-1.0-SNAPSHOT.jar
```

## 🗄️ Database Schema

The application uses the following main tables:
- `Student` - Student information
- `Lecturer` - Lecturer information
- `Course` - Course details
- `Department` - Department information
- `Enrollment` - Student course enrollments

See `DB_MySQL.sql` for the complete schema definition.

## 🏗️ Architecture

The project follows a **layered architecture**:

- **Presentation Layer**: JavaFX FXML views and controllers
- **Business Logic Layer**: Controllers handle user interactions
- **Data Access Layer**: `DatabaseHelper` manages database operations
- **Model Layer**: Entity classes representing database tables

### Package Organization

- `app` - Main application entry point
- `app.controller` - UI controllers (MVC pattern)
- `app.model` - Data models/entities
- `app.database` - Database access layer

## 🔒 Security Notes

- Database credentials should never be committed to version control
- Use environment variables or secure configuration files in production
- All database queries use prepared statements to prevent SQL injection

## 📝 Development

### Code Style

- Follow Java naming conventions (PascalCase for classes, camelCase for methods)
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods focused and single-purpose

### Adding New Features

1. Create model classes in `app.model`
2. Add database methods in `app.database.DatabaseHelper`
3. Update controller in `app.controller.mainController`
4. Modify FXML if UI changes are needed

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is part of a Database Systems course assignment.

## 👥 Authors

- Database Systems Project Team

## 🙏 Acknowledgments

- JavaFX team for the excellent UI framework
- MySQL for the robust database system

---

**Note**: This is an academic project. For production use, additional security measures, error handling, and testing would be required.
